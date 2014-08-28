/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.ext.wso2.client.upload.overwrite;

import com.codenvy.ide.api.event.ResourceChangedEvent;
import com.codenvy.ide.api.notification.Notification;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.resources.ResourceProvider;
import com.codenvy.ide.api.resources.model.File;
import com.codenvy.ide.api.resources.model.Folder;
import com.codenvy.ide.api.resources.model.Project;
import com.codenvy.ide.dto.DtoFactory;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2ClientService;
import com.codenvy.ide.ext.wso2.client.commons.WSO2AsyncCallback;
import com.codenvy.ide.ext.wso2.client.commons.WSO2AsyncRequestCallback;
import com.codenvy.ide.ext.wso2.client.upload.ImportFilePresenter;
import com.codenvy.ide.ext.wso2.shared.FileInfo;
import com.codenvy.ide.rest.StringUnmarshaller;
import com.google.gwt.http.client.RequestException;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import javax.annotation.Nonnull;

import static com.codenvy.ide.api.notification.Notification.Type.ERROR;
import static com.codenvy.ide.ext.wso2.shared.Constants.MAIN_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SRC_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SYNAPSE_CONFIG_FOLDER_NAME;

/**
 * The presenter for overwrite imported file.
 *
 * @author Valeriy Svydenko
 * @author Andrey Plotnikov
 */
public class OverwriteFilePresenter implements OverwriteFileView.ActionDelegate {

    private static final String DELETE_FILE_OPERATION    = "delete";
    private static final String RENAME_FILE_OPERATION    = "rename";
    private static final String OVERWRITE_FILE_OPERATION = "overwrite";

    private final OverwriteFileView    view;
    private final DtoFactory           dtoFactory;
    private final ResourceProvider     resourceProvider;
    private final NotificationManager  notificationManager;
    private final WSO2ClientService    service;
    private final EventBus             eventBus;
    private final LocalizationConstant local;

    private String                               oldFileName;
    private ImportFilePresenter.ViewCloseHandler parentViewUtils;

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
     * Refresh tree of parent folder that include new file
     *
     * @param callback
     *         name of the parent folder
     * @param fileName
     *         name of the new file
     */
    private void refreshTree(@Nonnull String callback, @Nonnull final String fileName) {
        Project activeProject = resourceProvider.getActiveProject();

        if (activeProject == null) {
            return;
        }

        Folder src = (Folder)activeProject.findChildByName(SRC_FOLDER_NAME);
        if (src == null) {
            refreshFolder(activeProject, activeProject, fileName);

            return;
        }

        Folder main = (Folder)src.findChildByName(MAIN_FOLDER_NAME);
        if (main == null) {
            refreshFolder(activeProject, src, fileName);

            return;
        }

        Folder synapse_config = (Folder)main.findChildByName(SYNAPSE_CONFIG_FOLDER_NAME);

        if (synapse_config == null) {
            refreshFolder(activeProject, main, fileName);

            return;
        }

        if (callback.isEmpty()) {
            refreshFolder(activeProject, synapse_config, fileName);

            return;
        }

        Folder parentFolder = (Folder)synapse_config.findChildByName(callback);

        if (parentFolder == null) {
            refreshFolder(activeProject, synapse_config, callback);

            return;
        }

        refreshFolder(activeProject, parentFolder, fileName);
    }

    private void refreshFolder(@Nonnull Project project, @Nonnull Folder parentFolder, @Nonnull final String resourceName) {
        project.refreshChildren(parentFolder, new WSO2AsyncCallback<Folder>(notificationManager) {
            @Override
            public void onSuccess(Folder folder) {
                File file = (File)folder.findResourceByName(resourceName, File.TYPE);

                if (file != null) {
                    eventBus.fireEvent(ResourceChangedEvent.createResourceCreatedEvent(file));
                } else {
                    eventBus.fireEvent(ResourceChangedEvent.createResourceTreeRefreshedEvent(folder));
                }

                view.close();
            }
        });
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
                                            .withProjectName(resourceProvider.getActiveProject().getName());

        try {
            service.modifyFile(fileInfo, operation, new WSO2AsyncRequestCallback<String>(new StringUnmarshaller(), notificationManager) {
                @Override
                protected void onSuccess(String callback) {
                    view.close();

                    if (!"delete".equals(operation)) {
                        refreshTree(callback, fileInfo.getFileName());
                    }
                }
            });
        } catch (RequestException e) {
            notificationManager.showNotification(new Notification(e.getMessage(), ERROR));
        }
    }

    public void showDialog(@Nonnull String fileName, @Nonnull ImportFilePresenter.ViewCloseHandler parentViewUtils) {
        this.parentViewUtils = parentViewUtils;
        oldFileName = fileName;

        view.setMessage(local.wso2ImportFileAlreadyExists());
        view.setFileName(fileName);
        view.setEnabledRenameButton(false);

        view.showDialog();
    }
}
