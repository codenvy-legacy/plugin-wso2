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

import com.codenvy.ide.annotations.NotNull;
import com.codenvy.ide.api.notification.Notification;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.parts.ConsolePart;
import com.codenvy.ide.api.resources.ResourceProvider;
import com.codenvy.ide.dto.DtoFactory;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2ClientService;
import com.codenvy.ide.resources.model.Project;
import com.codenvy.ide.rest.AsyncRequestCallback;
import com.codenvy.ide.util.Utils;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import static com.codenvy.ide.api.notification.Notification.Type.ERROR;

/**
 * The presenter for import configuration files.
 *
 * @author Valeriy Svydenko
 */
@Singleton
public class ImportFilePresenter implements ImportFileView.ActionDelegate {

    private final String UPLOAD_FILE_PATH = "/vfs/v2/uploadfile/";

    private ImportFileView       view;
    private ConsolePart          console;
    private NotificationManager  notificationManager;
    private String               restContext;
    private ResourceProvider     resourceProvider;
    private WSO2ClientService    service;
    private DtoFactory           dtoFactory;
    private LocalizationConstant local;

    @Inject
    public ImportFilePresenter(ImportFileView view,
                               WSO2ClientService service,
                               ConsolePart console,
                               @Named("restContext") String restContext,
                               NotificationManager notificationManager,
                               ResourceProvider resourceProvider,
                               DtoFactory dtoFactory,
                               LocalizationConstant local) {
        this.view = view;
        this.view.setDelegate(this);
        this.console = console;
        this.notificationManager = notificationManager;
        this.restContext = restContext;
        this.resourceProvider = resourceProvider;
        this.service = service;
        this.dtoFactory = dtoFactory;
        this.local = local;
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
            view.setAction(restContext + '/' + Utils.getWorkspaceName() + UPLOAD_FILE_PATH + activeProject.getId());

            view.submit();
        } else {
            FileInfo fileInfo = dtoFactory.createDto(FileInfo.class)
                                          .withFileName(view.getUrl())
                                          .withProjectName(activeProject.getName());

            try {
                service.uploadFile(fileInfo, new AsyncRequestCallback<Void>() {
                    @Override
                    protected void onSuccess(Void result) {
                        activeProject.refreshTree(new AsyncCallback<Project>() {
                            @Override
                            public void onSuccess(Project result) {
                                view.close();
                            }

                            @Override
                            public void onFailure(Throwable caught) {
                                showError(caught);
                            }
                        });
                    }

                    @Override
                    protected void onFailure(Throwable exception) {
                        showError(exception);
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
            FileInfo fileInfo = dtoFactory.createDto(FileInfo.class)
                                          .withFileName(view.getFileName())
                                          .withProjectName(resourceProvider.getActiveProject().getName());
            try {
                service.detectConfigurationFile(fileInfo, new AsyncRequestCallback<Void>() {
                    @Override
                    protected void onSuccess(Void aVoid) {
                        resourceProvider.getActiveProject().refreshTree(new AsyncCallback<Project>() {
                            @Override
                            public void onSuccess(Project project) {
                                view.close();
                            }

                            @Override
                            public void onFailure(Throwable exception) {
                                showError(exception);
                            }
                        });
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
        view.setUseLocalPath(true);
        view.setMessage("");
        view.setEnabledImportButton(false);
        view.setEnterUrlFieldEnabled(false);
        view.showDialog();
    }
}