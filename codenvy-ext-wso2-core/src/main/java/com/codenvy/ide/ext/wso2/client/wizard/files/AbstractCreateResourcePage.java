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
package com.codenvy.ide.ext.wso2.client.wizard.files;

import com.codenvy.api.project.gwt.client.ProjectServiceClient;
import com.codenvy.api.project.shared.dto.ItemReference;
import com.codenvy.api.project.shared.dto.TreeElement;
import com.codenvy.ide.api.ResourceNameValidator;
import com.codenvy.ide.api.app.AppContext;
import com.codenvy.ide.api.app.CurrentProject;
import com.codenvy.ide.api.editor.EditorAgent;
import com.codenvy.ide.api.event.RefreshProjectTreeEvent;
import com.codenvy.ide.api.filetypes.FileType;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.projecttree.generic.FileNode;
import com.codenvy.ide.api.wizard.AbstractWizardPage;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.codenvy.ide.ext.wso2.client.commons.WSO2AsyncRequestCallback;
import com.codenvy.ide.ext.wso2.client.wizard.files.view.CreateResourceView;
import com.codenvy.ide.rest.DtoUnmarshallerFactory;
import com.codenvy.ide.rest.Unmarshallable;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.codenvy.ide.ext.wso2.shared.Constants.MAIN_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SRC_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SYNAPSE_CONFIG_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SYNAPSE_CONFIG_PATH;

/**
 * The abstract implementation of the wizard page. This page provides an ability to create WSO2 resources in a given place.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitriy Shnurenko
 */
public abstract class AbstractCreateResourcePage extends AbstractWizardPage implements CreateResourceView.ActionDelegate {

    private final EditorAgent                   editorAgent;
    private final ProjectServiceClient          projectServiceClient;
    private final String                        parentFolderName;
    private final FileType                      fileType;
    private final EventBus                      eventBus;
    private final AppContext                    appContext;
    private final NotificationManager           notificationManager;
    private final Unmarshallable<ItemReference> itemReferenceUnmarshaller;
    private final Unmarshallable<TreeElement>   treeElementUnmarshaller;

    protected final CreateResourceView   view;
    protected final LocalizationConstant locale;
    protected final WSO2Resources        resources;

    protected boolean hasSameFile;
    protected boolean incorrectName;
    protected String  content;

    private TreeElement parentFolder;
    private String      projectPath;

    public AbstractCreateResourcePage(@Nonnull CreateResourceView view,
                                      @Nonnull String caption,
                                      @Nonnull String resourceNameTitle,
                                      @Nullable ImageResource icon,
                                      @Nonnull LocalizationConstant locale,
                                      @Nonnull EditorAgent editorAgent,
                                      @Nonnull String parentFolderName,
                                      @Nonnull FileType fileType,
                                      @Nonnull NotificationManager notificationManager,
                                      @Nonnull WSO2Resources resources,
                                      @Nonnull ProjectServiceClient projectServiceClient,
                                      @Nonnull EventBus eventBus,
                                      @Nonnull AppContext appContext,
                                      @Nonnull DtoUnmarshallerFactory dtoUnmarshallerFactory) {
        super(caption, icon);

        this.view = view;
        this.view.setDelegate(this);
        this.view.setResourceNameTitle(resourceNameTitle);

        this.editorAgent = editorAgent;
        this.parentFolderName = parentFolderName;
        this.fileType = fileType;
        this.locale = locale;
        this.notificationManager = notificationManager;
        this.resources = resources;
        this.projectServiceClient = projectServiceClient;
        this.eventBus = eventBus;
        this.appContext = appContext;

        this.itemReferenceUnmarshaller = dtoUnmarshallerFactory.newUnmarshaller(ItemReference.class);
        this.treeElementUnmarshaller = dtoUnmarshallerFactory.newUnmarshaller(TreeElement.class);
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

        return null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isCompleted() {
        return !view.getResourceName().isEmpty() && !hasSameFile && !incorrectName;
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
    public void go(@Nonnull AcceptsOneWidget container) {
        findParentFolder();

        container.setWidget(view);
    }

    private void findParentFolder() {
        projectPath = null;
        parentFolder = null;

        CurrentProject currentProject = appContext.getCurrentProject();

        if (currentProject == null) {
            return;
        }

        projectPath = currentProject.getProjectDescription().getPath();

        projectServiceClient.getTree(projectPath, 4,
                                     new WSO2AsyncRequestCallback<TreeElement>(treeElementUnmarshaller, notificationManager) {
                                         @Override
                                         protected void onSuccess(TreeElement treeElement) {
                                             onGetTreeSuccessAction(treeElement);
                                         }
                                     });
    }

    private void onGetTreeSuccessAction(@Nonnull TreeElement treeElement) {
        TreeElement srcFolder = findChildByName(treeElement, SRC_FOLDER_NAME);
        if (srcFolder == null) {
            return;
        }

        TreeElement mainFolder = findChildByName(srcFolder, MAIN_FOLDER_NAME);
        if (mainFolder == null) {
            return;
        }

        TreeElement synapseConfFolder = findChildByName(mainFolder, SYNAPSE_CONFIG_FOLDER_NAME);
        if (synapseConfFolder == null) {
            return;
        }

        parentFolder = findChildByName(synapseConfFolder, parentFolderName);
    }

    @Nullable
    private TreeElement findChildByName(@Nullable TreeElement treeElement, @Nonnull String name) {
        if (treeElement == null) {
            return null;
        }

        for (TreeElement child : treeElement.getChildren()) {
            ItemReference node = child.getNode();

            if (name.equals(node.getName())) {
                return child;
            }
        }

        return null;
    }

    /** {@inheritDoc} */
    @Override
    public void onValueChanged() {
        String resourceName = view.getResourceName();
        incorrectName = !ResourceNameValidator.isFileNameValid(resourceName);

        TreeElement file = findChildByName(parentFolder, getResourceNameWithExtension(resourceName));
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
        if (parentFolder == null) {
            projectServiceClient.createFolder(projectPath + SYNAPSE_CONFIG_PATH + parentFolderName,
                                              new WSO2AsyncRequestCallback<ItemReference>(itemReferenceUnmarshaller, notificationManager) {
                                                  @Override
                                                  protected void onSuccess(ItemReference itemReference) {
                                                      createFile(itemReference.getPath());
                                                  }
                                              });
        } else {
            createFile(parentFolder.getNode().getPath());
        }
    }

    private void createFile(@Nonnull String parentPath) {
        projectServiceClient.createFile(parentPath,
                                        getResourceNameWithExtension(view.getResourceName()),
                                        content,
                                        fileType.getMimeTypes().get(0),
                                        new WSO2AsyncRequestCallback<ItemReference>(itemReferenceUnmarshaller, notificationManager) {
                                            @Override
                                            protected void onSuccess(ItemReference result) {
                                                eventBus.fireEvent(new RefreshProjectTreeEvent());
                                                FileNode file = new FileNode(null, result, eventBus, projectServiceClient, null);
                                                editorAgent.openEditor(file);
                                            }
                                        });
    }

}