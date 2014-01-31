/*
* CODENVY CONFIDENTIAL
* __________________
*
*  [2012] - [2013] Codenvy, S.A.
*  All Rights Reserved.
*
* NOTICE:  All information contained herein is, and remains
* the property of Codenvy S.A. and its suppliers,
* if any.  The intellectual and technical concepts contained
* herein are proprietary to Codenvy S.A.
* and its suppliers and may be covered by U.S. and Foreign Patents,
* patents in process, and are protected by trade secret or copyright law.
* Dissemination of this information or reproduction of this material
* is strictly forbidden unless prior written permission is obtained
* from Codenvy S.A..
*/
package com.codenvy.ide.ext.wso2.client.upload;

import com.codenvy.ide.api.event.ResourceChangedEvent;
import com.codenvy.ide.api.notification.Notification;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.parts.ConsolePart;
import com.codenvy.ide.api.resources.ResourceProvider;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.dto.DtoFactory;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2ClientService;
import com.codenvy.ide.ext.wso2.client.commons.WSO2AsyncCallback;
import com.codenvy.ide.ext.wso2.client.commons.WSO2AsyncRequestCallback;
import com.codenvy.ide.ext.wso2.client.upload.overwrite.OverwriteFilePresenter;
import com.codenvy.ide.ext.wso2.shared.FileInfo;
import com.codenvy.ide.resources.model.File;
import com.codenvy.ide.resources.model.Folder;
import com.codenvy.ide.resources.model.Project;
import com.codenvy.ide.resources.model.Resource;
import com.codenvy.ide.rest.AsyncRequestCallback;
import com.codenvy.ide.rest.StringUnmarshaller;
import com.codenvy.ide.util.Utils;
import com.google.gwt.http.client.RequestException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.web.bindery.event.shared.EventBus;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import static com.codenvy.ide.api.notification.Notification.Type.ERROR;
import static com.codenvy.ide.ext.wso2.shared.Constants.MAIN_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SRC_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SYNAPSE_CONFIG_FOLDER_NAME;

/**
 * The presenter for import configuration files.
 *
 * @author Valeriy Svydenko
 */
@Singleton
public class ImportFilePresenter implements ImportFileView.ActionDelegate {

    /** Required for delegating close function in another model. */
    public interface ViewCloseHandler {
        /** Call when need close the view. */
        void onCloseView();
    }

    private ImportFileView         view;
    private EventBus               eventBus;
    private ConsolePart            console;
    private NotificationManager    notificationManager;
    private String                 restContext;
    private ResourceProvider       resourceProvider;
    private WSO2ClientService      service;
    private DtoFactory             dtoFactory;
    private LocalizationConstant   local;
    private OverwriteFilePresenter overwrite;
    private ViewCloseHandler       viewCloseHandler;

    @Inject
    public ImportFilePresenter(final ImportFileView view,
                               OverwriteFilePresenter overwrite,
                               WSO2ClientService service,
                               ConsolePart console,
                               @Named("restContext") String restContext,
                               NotificationManager notificationManager,
                               ResourceProvider resourceProvider,
                               DtoFactory dtoFactory,
                               LocalizationConstant local,
                               EventBus eventBus) {
        this.view = view;
        this.eventBus = eventBus;
        this.view.setDelegate(this);
        this.console = console;
        this.notificationManager = notificationManager;
        this.restContext = restContext;
        this.resourceProvider = resourceProvider;
        this.service = service;
        this.dtoFactory = dtoFactory;
        this.local = local;
        this.overwrite = overwrite;
        viewCloseHandler = new ViewCloseHandler() {
            @Override
            public void onCloseView() {
                view.close();
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onCancelClicked() {
        view.close();
    }

    /** {@inheritDoc} */
    @Override
    public void onImportClicked() {
        final Project activeProject = resourceProvider.getActiveProject();

        if (view.isUseLocalPath()) {
            view.setAction(restContext + "/vfs/" + Utils.getWorkspaceId() + "/v2/uploadfile/" + activeProject.getId());

            view.submit();
        } else {
            final FileInfo fileInfo = dtoFactory.createDto(FileInfo.class)
                                                .withFileName(view.getUrl())
                                                .withProjectName(activeProject.getName());

            try {
                service.uploadFile(fileInfo, new WSO2AsyncRequestCallback<String>(new StringUnmarshaller(), notificationManager) {
                    @Override
                    protected void onSuccess(String callback) {
                        refreshTreeWithParentFolder(callback, fileInfo.getFileName().substring(fileInfo.getFileName().lastIndexOf('/') + 1,
                                                                                               fileInfo.getFileName().length()));
                    }
                });
            } catch (RequestException e) {
                showError(e);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onSubmitComplete(@NotNull String result) {
        if (result.isEmpty()) {
            final FileInfo fileInfo = dtoFactory.createDto(FileInfo.class)
                                                .withFileName(view.getFileName())
                                                .withProjectName(resourceProvider.getActiveProject().getName());
            try {
                service.detectConfigurationFile(fileInfo, new AsyncRequestCallback<String>(new StringUnmarshaller()) {
                    @Override
                    protected void onSuccess(final String callback) {
                        refreshTreeWithParentFolder(callback, fileInfo.getFileName());
                    }

                    @Override
                    protected void onFailure(Throwable throwable) {
                        view.setMessage(local.wso2ImportDialogError());
                    }
                });
            } catch (RequestException e) {
                showError(e);
            }
        } else {
            if (result.startsWith("<pre>") && result.endsWith("</pre>")) {
                result = result.substring(5, (result.length() - 6));
            }
            console.print(result);
            Notification notification = new Notification(result, ERROR);
            notificationManager.showNotification(notification);
        }
    }

    /**
     * Refresh a parent tree
     *
     * @param response
     *         the name of parent folder
     * @param fileName
     *         name of the file
     */
    private void refreshTreeWithParentFolder(String response, final String fileName) {
        if (response.endsWith("already exists. ")) {
            overwrite.showDialog(fileName, viewCloseHandler);
        } else {
            final Folder parentFolder;
            boolean parentIsExist = false;

            Project activeProject = resourceProvider.getActiveProject();

            Resource src = getResourceByName(activeProject, SRC_FOLDER_NAME);
            if (src != null) {
                Resource main = getResourceByName((Folder)src, MAIN_FOLDER_NAME);
                if (main != null) {
                    Resource synapse_config = getResourceByName((Folder)main, SYNAPSE_CONFIG_FOLDER_NAME);
                    if (synapse_config != null) {
                        if (response.isEmpty()) {
                            parentFolder = (Folder)synapse_config;
                        } else {
                            parentFolder = (Folder)getResourceByName((Folder)synapse_config, response);
                        }
                        if (parentFolder != null) {
                            parentIsExist = true;
                            activeProject.refreshTree(parentFolder, new WSO2AsyncCallback<Folder>(notificationManager) {
                                @Override
                                public void onSuccess(Folder folder) {
                                    File file = (File)parentFolder.findResourceByName(fileName, "file");
                                    eventBus.fireEvent(ResourceChangedEvent.createResourceCreatedEvent(file));
                                    view.close();
                                }
                            });
                        }
                    }
                }
            }
            if (!parentIsExist) {
                refreshProject();
            }
        }
    }

    /** Refresh a project tree. */
    private void refreshProject() {
        resourceProvider.getActiveProject().refreshTree(new WSO2AsyncCallback<Project>(notificationManager) {
            @Override
            public void onSuccess(Project result) {
                view.close();
            }
        });
    }

    /**
     * Find resource by name in parent folder
     *
     * @param parent
     *         place where child should be
     * @param name
     *         name that child should have
     * @return {@link com.codenvy.ide.resources.model.Resource}
     */
    @Nullable
    private Resource getResourceByName(@NotNull Folder parent, @NotNull String name) {
        Array<Resource> children = parent.getChildren();

        for (Resource child : children.asIterable()) {
            if (name.equals(child.getName())) {
                return child;
            }
        }

        return null;
    }

    private void showError(@NotNull Throwable throwable) {
        Notification notification = new Notification(throwable.getMessage(), ERROR);
        notificationManager.showNotification(notification);
    }

    /** {@inheritDoc} */
    @Override
    public void onFileNameChanged() {
        checkValidFileName();
    }

    @Override
    public void onFileNameChangedWithInvalidFormat() {
        view.setMessage(local.wso2ImportFileFormatError());
        view.setEnabledImportButton(false);
    }

    /** {@inheritDoc} */
    @Override
    public void onUrlChanged() {
        view.setEnabledImportButton(!view.getUrl().isEmpty() && view.isUseUrl());
    }

    /** {@inheritDoc} */
    @Override
    public void onUseUrlChosen() {
        view.setMessage("");
        view.setEnterUrlFieldEnabled(true);
        view.setEnabledImportButton(!view.getUrl().isEmpty());
    }

    /** {@inheritDoc} */
    @Override
    public void onUseLocalPathChosen() {
        checkValidFileName();
        view.setEnterUrlFieldEnabled(false);
    }

    /** Check format for upload file */
    private void checkValidFileName() {
        if (!view.getFileName().isEmpty()) {
            if (!view.getFileName().endsWith(".xml")) {
                view.setMessage(local.wso2ImportFileFormatError());
                view.setEnabledImportButton(false);
            } else {
                view.setMessage("");
                view.setEnabledImportButton(true);
            }
        }
    }

    /** Show dialog. */
    public void showDialog() {
        view.setUseUrl(false);
        view.setUseLocalPath(true);
        view.setMessage("");
        view.setEnabledImportButton(false);
        view.setEnterUrlFieldEnabled(false);
        view.setUrl("");
        view.showDialog();
    }
}
