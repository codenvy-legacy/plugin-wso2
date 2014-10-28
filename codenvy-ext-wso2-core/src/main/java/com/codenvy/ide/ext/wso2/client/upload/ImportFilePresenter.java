/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache  License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.ext.wso2.client.upload;

import com.codenvy.ide.api.app.AppContext;
import com.codenvy.ide.api.app.CurrentProject;
import com.codenvy.ide.api.event.RefreshProjectTreeEvent;
import com.codenvy.ide.api.notification.Notification;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.dto.DtoFactory;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2ClientService;
import com.codenvy.ide.ext.wso2.client.upload.overwrite.OverwriteFilePresenter;
import com.codenvy.ide.ext.wso2.shared.FileInfo;
import com.codenvy.ide.rest.AsyncRequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.web.bindery.event.shared.EventBus;

import javax.annotation.Nonnull;

import static com.codenvy.ide.api.notification.Notification.Type.ERROR;

/**
 * The class provides the business logic which allows us to import file from different places via special dialog window.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 * @author Andrey Plotnikov
 */
@Singleton
public class ImportFilePresenter implements ImportFileView.ActionDelegate {

    /** Required for delegating close function in another model. */
    public interface ViewCloseHandler {
        /** Call when need close the view. */
        void onCloseView();
    }

    private final ImportFileView         view;
    private final EventBus               eventBus;
    private final NotificationManager    notificationManager;
    private final String                 restContext;
    private final String                 workSpaceId;
    private final WSO2ClientService      service;
    private final DtoFactory             dtoFactory;
    private final LocalizationConstant   local;
    private final OverwriteFilePresenter overwrite;
    private final ViewCloseHandler       viewCloseHandler;
    private final AppContext             appContext;

    private AsyncRequestCallback<Void> uploadFileCallback;
    private AsyncRequestCallback<Void> detectConfigurationCallback;

    private FileInfo fileUploadInfo;
    private FileInfo fileDetectConfigInfo;

    @Inject
    public ImportFilePresenter(final ImportFileView view,
                               OverwriteFilePresenter overwrite,
                               WSO2ClientService service,
                               @Named("restContext") String restContext,
                               @Named("workspaceId") String workSpaceId,
                               NotificationManager notificationManager,
                               DtoFactory dtoFactory,
                               LocalizationConstant local,
                               EventBus eventBus,
                               AppContext appContext) {
        this.view = view;
        this.eventBus = eventBus;
        this.view.setDelegate(this);
        this.notificationManager = notificationManager;
        this.restContext = restContext;
        this.workSpaceId = workSpaceId;
        this.service = service;
        this.dtoFactory = dtoFactory;
        this.local = local;
        this.overwrite = overwrite;
        this.appContext = appContext;
        viewCloseHandler = new ViewCloseHandler() {
            @Override
            public void onCloseView() {
                view.close();
            }
        };

        initializeCallbacks();
    }

    private void initializeCallbacks() {
        uploadFileCallback = new AsyncRequestCallback<Void>() {
            @Override
            protected void onSuccess(Void callback) {
                eventBus.fireEvent(new RefreshProjectTreeEvent());
                view.close();
            }

            @Override
            protected void onFailure(Throwable exception) {
                if (!needsToShowErrorMessage(exception, fileUploadInfo.getFileName())) {
                    return;
                }

                showError(exception.getMessage());
            }
        };

        detectConfigurationCallback = new AsyncRequestCallback<Void>() {
            @Override
            protected void onSuccess(final Void callback) {
                eventBus.fireEvent(new RefreshProjectTreeEvent());
                view.close();
            }

            @Override
            protected void onFailure(Throwable throwable) {
                if (!needsToShowErrorMessage(throwable, fileDetectConfigInfo.getFileName())) {
                    return;
                }

                view.setMessage(local.wso2ImportDialogError());
            }
        };
    }

    private boolean needsToShowErrorMessage(@Nonnull Throwable throwable, @Nonnull String fileName) {
        if (throwable.getMessage().endsWith("already exists. \"}")) {
            overwrite.showDialog(fileName, viewCloseHandler);

            return false;
        }

        return true;
    }

    /** {@inheritDoc} */
    @Override
    public void onCancelClicked() {
        view.close();
    }

    /** {@inheritDoc} */
    @Override
    public void onImportClicked() {
        CurrentProject currentProject = appContext.getCurrentProject();

        if (currentProject == null) {
            return;
        }

        if (view.isUseLocalPath()) {
            view.setAction(
                    restContext + "/project/" + workSpaceId + "/uploadfile" + currentProject.getProjectDescription().getPath());
            view.submit();
        } else {
            fileUploadInfo = dtoFactory.createDto(FileInfo.class)
                                       .withFileName(view.getUrl())
                                       .withProjectName(currentProject.getProjectDescription().getName());

            try {
                service.uploadFile(fileUploadInfo, uploadFileCallback);
            } catch (RequestException e) {
                showError(e.getMessage());
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onSubmitComplete(@Nonnull String result) {
        CurrentProject currentProject = appContext.getCurrentProject();

        if (currentProject == null) {
            return;
        }

        if (result.isEmpty()) {
            fileDetectConfigInfo = dtoFactory.createDto(FileInfo.class)
                                             .withFileName(view.getFileName())
                                             .withProjectName(currentProject.getProjectDescription().getName());
            try {
                service.detectConfigurationFile(fileDetectConfigInfo, detectConfigurationCallback);
            } catch (RequestException e) {
                showError(e.getMessage());
            }
        } else {
            if (result.startsWith("<pre>") && result.endsWith("</pre>")) {
                result = result.substring(5, (result.length() - 6));
            }

            showError(result);
        }
    }

    private void showError(@Nonnull String message) {
        notificationManager.showNotification(new Notification(message, ERROR));
    }

    /** {@inheritDoc} */
    @Override
    public void onFileNameChanged() {
        checkValidFileName();
    }

    /** {@inheritDoc} */
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
        String fileName = view.getFileName();
        boolean isXMLFile = fileName.endsWith(".xml");

        view.setMessage(isXMLFile ? "" : local.wso2ImportFileFormatError());
        view.setEnabledImportButton(isXMLFile);
    }

    /** Show dialog. */
    public void showDialog() {
        view.setUseUrl(false);
        view.setUseLocalPath(true);

        view.setMessage("");
        view.setUrl("");

        view.setEnabledImportButton(false);
        view.setEnterUrlFieldEnabled(false);

        view.showDialog();
    }

}