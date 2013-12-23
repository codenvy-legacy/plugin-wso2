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
package com.codenvy.ide.ext.wso2.client.upload.overwrite;

import com.codenvy.ide.annotations.NotNull;
import com.codenvy.ide.annotations.Nullable;
import com.codenvy.ide.api.event.ResourceChangedEvent;
import com.codenvy.ide.api.notification.Notification;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.resources.ResourceProvider;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.dto.DtoFactory;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2ClientService;
import com.codenvy.ide.ext.wso2.client.upload.ImportFilePresenter;
import com.codenvy.ide.ext.wso2.shared.FileInfo;
import com.codenvy.ide.resources.model.File;
import com.codenvy.ide.resources.model.Folder;
import com.codenvy.ide.resources.model.Project;
import com.codenvy.ide.resources.model.Resource;
import com.codenvy.ide.rest.AsyncRequestCallback;
import com.codenvy.ide.rest.StringUnmarshaller;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;

import static com.codenvy.ide.api.notification.Notification.Type.ERROR;
import static com.codenvy.ide.ext.wso2.shared.Constants.MAIN_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SRC_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SYNAPSE_CONFIG_FOLDER_NAME;

/**
 * The presenter for overwrite imported file.
 *
 * @author Valeriy Svydenko
 */
@Singleton
public class OverwriteFilePresenter implements OverwriteFileView.ActionDelegate {

    private final String DELETE_FILE_OPERATION    = "delete";
    private final String RENAME_FILE_OPERATION    = "rename";
    private final String OVERWRITE_FILE_OPERATION = "overwrite";

    private OverwriteFileView    view;
    private DtoFactory           dtoFactory;
    private ResourceProvider     resourceProvider;
    private NotificationManager  notificationManager;
    private WSO2ClientService    service;
    private EventBus             eventBus;
    private LocalizationConstant local;

    private String oldFileName = "";
    private ImportFilePresenter.ImportFileFiewUtils parentViewUtils;

    @Inject
    public OverwriteFilePresenter(OverwriteFileView view,
                                  DtoFactory dtoFactory,
                                  ResourceProvider resourceProvider,
                                  WSO2ClientService service,
                                  NotificationManager notificationManager,
                                  EventBus eventBus,
                                  LocalizationConstant local) {
        this.view = view;
        this.dtoFactory = dtoFactory;
        this.resourceProvider = resourceProvider;
        this.service = service;
        this.notificationManager = notificationManager;
        this.view.setDelegate(this);
        this.eventBus = eventBus;
        this.local = local;

    }

    @Override
    public void onCancelButtonClicked() {
        modifyExistingFile(DELETE_FILE_OPERATION);
    }

    @Override
    public void onRenameButtonClicked() {
        modifyExistingFile(RENAME_FILE_OPERATION);
        parentViewUtils.closeView();
    }

    @Override
    public void onOverwriteButtonClicked() {
        modifyExistingFile(OVERWRITE_FILE_OPERATION);
        parentViewUtils.closeView();
    }

    @Override
    public void onNameChanged() {
        if (!oldFileName.equals(view.getFileName())) {
            view.setEnabledRenameButton(true);
        } else {
            view.setEnabledRenameButton(false);
        }
    }

    /**
     * Refresh tree of parent folder that include new file
     *
     * @param callback
     *         name of the parent folder
     * @param fileName
     *         name of the new file
     */
    private void refreshTree(String callback, final String fileName) {
        final Folder parentFolder;

        Project activeProject = resourceProvider.getActiveProject();

        Resource src = getResourceByName(activeProject, SRC_FOLDER_NAME);
        Resource main = getResourceByName((Folder)src, MAIN_FOLDER_NAME);
        Resource synapse_config = getResourceByName((Folder)main, SYNAPSE_CONFIG_FOLDER_NAME);
        if (callback.isEmpty()) {
            parentFolder = (Folder)synapse_config;
        } else {
            parentFolder = (Folder)getResourceByName((Folder)synapse_config, callback);
        }


        resourceProvider.getActiveProject().refreshTree(parentFolder, new AsyncCallback<Folder>() {
            @Override
            public void onSuccess(Folder folder) {
                File file = (File)parentFolder.findResourceByName(fileName, "file");
                eventBus.fireEvent(ResourceChangedEvent.createResourceCreatedEvent(file));
                view.close();
            }

            @Override
            public void onFailure(Throwable exception) {
                showError(exception);
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

    /**
     * Modify file if parent folder include file with the same name
     *
     * @param operation
     *         name of the modification operation
     */
    private void modifyExistingFile(final String operation) {
        final FileInfo fileInfo = dtoFactory.createDto(FileInfo.class)
                                            .withFileName(oldFileName)
                                            .withNewFileName(view.getFileName())
                                            .withProjectName(resourceProvider.getActiveProject().getName());

        try {
            service.modifyFile(fileInfo, operation, new AsyncRequestCallback<String>(new StringUnmarshaller()) {
                @Override
                protected void onSuccess(String callback) {
                    view.close();
                    if (!"delete".equals(operation)) {
                        refreshTree(callback, fileInfo.getFileName());
                    }
                }

                @Override
                protected void onFailure(Throwable throwable) {
                    showError(throwable);
                }
            });
        } catch (RequestException e) {
            showError(e);
        }
    }

    private void showError(@NotNull Throwable throwable) {
        Notification notification = new Notification(throwable.getMessage(), ERROR);
        notificationManager.showNotification(notification);
    }

    public void showDialog(String fileName, ImportFilePresenter.ImportFileFiewUtils parentViewUtils) {
        view.setMessage(local.wso2ImportFileAlreadyExists());
        view.setFileName(fileName);
        view.setEnabledRenameButton(false);
        view.showDialog();
        oldFileName = view.getFileName();
        this.parentViewUtils = parentViewUtils;
    }
}
