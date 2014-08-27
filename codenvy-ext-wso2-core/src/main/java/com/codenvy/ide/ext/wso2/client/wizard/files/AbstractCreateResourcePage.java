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
package com.codenvy.ide.ext.wso2.client.wizard.files;

import com.codenvy.ide.api.editor.EditorAgent;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.resources.FileType;
import com.codenvy.ide.api.resources.ResourceProvider;
import com.codenvy.ide.api.resources.model.File;
import com.codenvy.ide.api.resources.model.Folder;
import com.codenvy.ide.api.resources.model.Project;
import com.codenvy.ide.api.resources.model.Resource;
import com.codenvy.ide.api.resources.model.ResourceNameValidator;
import com.codenvy.ide.api.ui.wizard.AbstractWizardPage;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.commons.WSO2AsyncCallback;
import com.codenvy.ide.ext.wso2.client.wizard.files.view.CreateResourceView;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.codenvy.ide.ext.wso2.shared.Constants.MAIN_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SRC_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SYNAPSE_CONFIG_FOLDER_NAME;

/**
 * The abstract implementation of the wizard page. This page provides an ability to create WSO2 resources in a given place.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitriy Shnurenko
 */
public abstract class AbstractCreateResourcePage extends AbstractWizardPage implements CreateResourceView.ActionDelegate {

    private final ResourceProvider    resourceProvider;
    private final EditorAgent         editorAgent;
    private final String              parentFolderName;
    private final FileType            fileType;
    private       Project             activeProject;
    private final NotificationManager notificationManager;
    private       Folder              parentFolder;

    protected final CreateResourceView   view;
    protected       LocalizationConstant locale;
    protected       boolean              hasSameFile;
    protected       boolean              incorrectName;
    protected       String               content;

    public AbstractCreateResourcePage(@Nonnull CreateResourceView view,
                                      @Nonnull String caption,
                                      @Nullable ImageResource icon,
                                      @Nonnull LocalizationConstant locale,
                                      @Nonnull ResourceProvider resourceProvider,
                                      @Nonnull EditorAgent editorAgent,
                                      @Nonnull String parentFolderName,
                                      @Nonnull FileType fileType,
                                      @Nonnull NotificationManager notificationManager) {
        super(caption, icon);

        this.view = view;
        this.resourceProvider = resourceProvider;
        this.editorAgent = editorAgent;
        this.parentFolderName = parentFolderName;
        this.fileType = fileType;
        this.view.setDelegate(this);
        this.locale = locale;
        this.notificationManager = notificationManager;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public String getNotice() {
        if (incorrectName) {
            return locale.wizardFileResourceNoticeIncorrectName();
        }

        if (hasSameFile) {
            return locale.wizardFileResourceNoticeFileExists();
        }

        if (parentFolder == null) {
            return locale.wizardFileResourceNoticeParentFolderNotExists();
        }

        return null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isCompleted() {
        return !view.getResourceName().isEmpty() && !hasSameFile && !incorrectName && !(parentFolder == null);
    }

    /** {@inheritDoc} */
    @Override
    public void focusComponent() {
        view.setResourceName("");
    }

    /** {@inheritDoc} */
    @Override
    public void removeOptions() {
        // do nothing
    }

    /** {@inheritDoc} */
    @Override
    public void go(AcceptsOneWidget container) {
        activeProject = resourceProvider.getActiveProject();

        getResourceByName(activeProject, SRC_FOLDER_NAME, new WSO2AsyncCallback<Resource>(notificationManager) {
            @Override
            public void onSuccess(Resource result) {
                getResourceByName((Folder)result, MAIN_FOLDER_NAME, new WSO2AsyncCallback<Resource>(notificationManager) {
                    @Override
                    public void onSuccess(Resource result) {
                        getResourceByName((Folder)result, SYNAPSE_CONFIG_FOLDER_NAME, new WSO2AsyncCallback<Resource>(notificationManager) {
                            @Override
                            public void onSuccess(Resource result) {
                                getResourceByName((Folder)result, parentFolderName, new WSO2AsyncCallback<Resource>(notificationManager) {
                                    @Override
                                    public void onSuccess(Resource result) {
                                        parentFolder = (Folder)result;
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

        container.setWidget(view);
    }

    /**
     * Find resource by name in parent folder
     *
     * @param parent
     *         place where child should be
     * @param name
     *         name that child should have
     * @param callback
     */
    private void getResourceByName(@Nonnull final Folder parent,
                                   @Nonnull final String name,
                                   @Nonnull final AsyncCallback<Resource> callback) {

        activeProject.refreshChildren(parent, new WSO2AsyncCallback<Folder>(notificationManager) {
            @Override
            public void onSuccess(Folder result) {
                Resource child = result.findChildByName(name);

                if (child != null) {
                    callback.onSuccess(child);
                    return;
                }

                activeProject.createFolder(result, name, new WSO2AsyncCallback<Folder>(notificationManager) {
                    @Override
                    public void onSuccess(Folder result) {
                        callback.onSuccess(result);
                    }
                });
            }
        });
    }

    /** {@inheritDoc} */
    @Override
    public void onValueChanged() {
        String resourceName = view.getResourceName();

        incorrectName = !ResourceNameValidator.isFileNameValid(resourceName);
        Resource file = null;
        for (Resource child : parentFolder.getChildren().asIterable()) {
            if (getResourceNameWithExtension(resourceName).equals(child.getName())) {
                file = child;
            }
        }

        hasSameFile = file != null;

        delegate.updateControls();
    }

    /** @return resource name with extension */
    @Nonnull
    private String getResourceNameWithExtension(@Nonnull String resourceName) {
        return resourceName + '.' + fileType.getExtension();
    }

    /** {@inheritDoc} */
    @Override
    public void commit(@Nonnull final CommitCallback callback) {
        String mimeType = fileType.getMimeTypes().get(0);
        activeProject.createFile(parentFolder, getResourceNameWithExtension(view.getResourceName()), content, mimeType,
                                 new AsyncCallback<File>() {
                                     @Override
                                     public void onSuccess(File result) {
                                         editorAgent.openEditor(result);
                                         callback.onSuccess();
                                     }

                                     @Override
                                     public void onFailure(Throwable caught) {
                                         callback.onFailure(caught);
                                     }
                                 }
                                );
    }
}