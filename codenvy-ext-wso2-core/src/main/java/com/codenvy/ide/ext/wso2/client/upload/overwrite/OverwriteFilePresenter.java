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
package com.codenvy.ide.ext.wso2.client.upload.overwrite;

import com.codenvy.ide.api.app.AppContext;
import com.codenvy.ide.api.event.RefreshProjectTreeEvent;
import com.codenvy.ide.api.notification.Notification;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.dto.DtoFactory;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2ClientService;
import com.codenvy.ide.ext.wso2.client.commons.WSO2AsyncRequestCallback;
import com.codenvy.ide.ext.wso2.client.upload.ImportFilePresenter;
import com.codenvy.ide.ext.wso2.shared.FileInfo;
import com.codenvy.ide.rest.StringUnmarshaller;
import com.google.gwt.http.client.RequestException;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import javax.annotation.Nonnull;

import static com.codenvy.ide.api.notification.Notification.Type.ERROR;

/**
 * The class provides the business logic which allows us to change name of existing file, to delete file or overwrite it
 * via special dialog window.
 *
 * @author Valeriy Svydenko
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class OverwriteFilePresenter implements OverwriteFileView.ActionDelegate {

    private static final String DELETE_FILE_OPERATION    = "delete";
    private static final String RENAME_FILE_OPERATION    = "rename";
    private static final String OVERWRITE_FILE_OPERATION = "overwrite";

    private final OverwriteFileView    view;
    private final DtoFactory           dtoFactory;
    private final NotificationManager  notificationManager;
    private final WSO2ClientService    service;
    private final EventBus             eventBus;
    private final LocalizationConstant local;
    private final AppContext           appContext;

    private String                               oldFileName;
    private ImportFilePresenter.ViewCloseHandler parentViewUtils;

    @Inject
    public OverwriteFilePresenter(OverwriteFileView view,
                                  DtoFactory dtoFactory,
                                  WSO2ClientService service,
                                  NotificationManager notificationManager,
                                  EventBus eventBus,
                                  AppContext appContext,
                                  LocalizationConstant local) {
        this.view = view;
        this.dtoFactory = dtoFactory;
        this.service = service;
        this.notificationManager = notificationManager;
        this.view.setDelegate(this);
        this.eventBus = eventBus;
        this.appContext = appContext;
        this.local = local;
    }

    /** {@inheritDoc} */
    @Override
    public void onCancelButtonClicked() {
        modifyExistingFile(DELETE_FILE_OPERATION);
    }

    /** {@inheritDoc} */
    @Override
    public void onRenameButtonClicked() {
        modifyExistingFile(RENAME_FILE_OPERATION);
        parentViewUtils.onCloseView();
    }

    /** {@inheritDoc} */
    @Override
    public void onOverwriteButtonClicked() {
        modifyExistingFile(OVERWRITE_FILE_OPERATION);
        parentViewUtils.onCloseView();
    }

    /** {@inheritDoc} */
    @Override
    public void onNameChanged() {
        boolean enable = !oldFileName.equals(view.getFileName());
        view.setEnabledRenameButton(enable);
    }

    /**
     * Modify file if parent folder include file with the same name
     *
     * @param operation
     *         name of the modification operation
     */
    private void modifyExistingFile(@Nonnull final String operation) {
        final FileInfo fileInfo = dtoFactory.createDto(FileInfo.class)
                                            .withFileName(oldFileName)
                                            .withNewFileName(view.getFileName())
                                            .withProjectName(appContext.getCurrentProject().getProjectDescription().getName());
        try {
            service.modifyFile(fileInfo, operation, new WSO2AsyncRequestCallback<String>(new StringUnmarshaller(), notificationManager) {
                @Override
                protected void onSuccess(String callback) {
                    view.close();

                    if (!"delete".equals(operation)) {
                        eventBus.fireEvent(new RefreshProjectTreeEvent());
                    }
                }
            });
        } catch (RequestException e) {
            notificationManager.showNotification(new Notification(e.getMessage(), ERROR));
        }
    }

    /**
     * Shows dialog window for editing file.
     *
     * @param fileName
     *         name of file which need to set to current file
     * @param parentViewUtils
     *         need to close the view in another model
     */
    public void showDialog(@Nonnull String fileName, @Nonnull ImportFilePresenter.ViewCloseHandler parentViewUtils) {
        this.parentViewUtils = parentViewUtils;
        oldFileName = fileName;

        view.setMessage(local.wso2ImportFileAlreadyExists());
        view.setFileName(fileName);
        view.setEnabledRenameButton(false);

        view.showDialog();
    }
}