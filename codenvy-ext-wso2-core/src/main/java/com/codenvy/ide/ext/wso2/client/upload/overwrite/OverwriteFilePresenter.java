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
import com.codenvy.ide.api.resources.model.Resource;
import com.codenvy.ide.collections.Array;
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
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.codenvy.ide.api.notification.Notification.Type.ERROR;
import static com.codenvy.ide.ext.wso2.shared.Constants.MAIN_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SRC_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SYNAPSE_CONFIG_FOLDER_NAME;

/**
 * The presenter for overwrite imported file.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
@Singleton
public class OverwriteFilePresenter implements OverwriteFileView.ActionDelegate {

    private static final String DELETE_FILE_OPERATION    = "delete";
    private static final String RENAME_FILE_OPERATION    = "rename";
    private static final String OVERWRITE_FILE_OPERATION = "overwrite";
    private static final String FILE                     = "file";

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

        activeProject.refreshChildren(parentFolder, new WSO2AsyncCallback<Folder>(notificationManager) {
            @Override
            public void onSuccess(Folder folder) {
                if (parentFolder != null) {
                    File file = (File)parentFolder.findResourceByName(fileName, FILE);
                    eventBus.fireEvent(ResourceChangedEvent.createResourceCreatedEvent(file));
                }
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
     * @return {@link Resource}
     */
    @Nullable
    private Resource getResourceByName(@Nullable Folder parent, @Nonnull String name) {
        if (parent != null) {

            Array<Resource> children = parent.getChildren();

            for (Resource child : children.asIterable()) {
                if (name.equals(child.getName())) {
                    return child;
                }
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
            Notification notification = new Notification(e.getMessage(), ERROR);
            notificationManager.showNotification(notification);
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
