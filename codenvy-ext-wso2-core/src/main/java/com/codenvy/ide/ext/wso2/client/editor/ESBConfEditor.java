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
package com.codenvy.ide.ext.wso2.client.editor;

import com.codenvy.ide.api.editor.AbstractEditorPresenter;
import com.codenvy.ide.api.editor.CodenvyTextEditor;
import com.codenvy.ide.api.editor.DocumentProvider;
import com.codenvy.ide.api.editor.EditorInitException;
import com.codenvy.ide.api.editor.EditorInput;
import com.codenvy.ide.api.editor.EditorPartPresenter;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.resources.ResourceProvider;
import com.codenvy.ide.api.resources.model.File;
import com.codenvy.ide.api.resources.model.Folder;
import com.codenvy.ide.api.resources.model.Project;
import com.codenvy.ide.api.ui.workspace.PartPresenter;
import com.codenvy.ide.api.ui.workspace.PropertyListener;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.ext.wso2.client.commons.WSO2AsyncCallback;
import com.codenvy.ide.ext.wso2.client.editor.graphical.GraphicEditor;
import com.codenvy.ide.ext.wso2.client.editor.text.XmlEditorConfiguration;
import com.codenvy.ide.util.StringUtils;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import static com.codenvy.ide.MimeType.APPLICATION_XML;

/**
 * The editor for WSO2 ESB configuration.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class ESBConfEditor extends AbstractEditorPresenter implements ESBConfEditorView.ActionDelegate, PropertyListener {

    private static final String CODENVY_INTERNAL_FORMAT_EXTENSION = ".c5yd";

    private final ESBConfEditorView   view;
    private final NotificationManager notificationManager;
    private final ResourceProvider    resourceProvider;
    private final GraphicEditor       graphicEditor;
    private final CodenvyTextEditor   textEditor;

    private boolean isGraphicalEditorChanged;

    @Inject
    public ESBConfEditor(ESBConfEditorView view,
                         DocumentProvider documentProvider,
                         Provider<CodenvyTextEditor> editorProvider,
                         Provider<XmlEditorConfiguration> xmlEditorConfigurationProvider,
                         NotificationManager notificationManager,
                         GraphicEditor graphicEditor,
                         ResourceProvider resourceProvider) {
        this.view = view;
        this.notificationManager = notificationManager;
        this.resourceProvider = resourceProvider;
        this.view.setDelegate(this);
        this.graphicEditor = graphicEditor;
        textEditor = editorProvider.get();
        textEditor.initialize(xmlEditorConfigurationProvider.get(), documentProvider, notificationManager);

        this.graphicEditor.addPropertyListener(this);
        textEditor.addPropertyListener(this);
    }

    /** {@inheritDoc} */
    @Override
    public void init(@NotNull EditorInput input) throws EditorInitException {
        super.init(input);
        textEditor.init(input);
        graphicEditor.init(input);
    }

    /** {@inheritDoc} */
    @Override
    protected void initializeEditor() {
        onGraphicalEditorButtonClicked();
    }

    /** {@inheritDoc} */
    @Override
    public void doSave() {
        if (isDirty()) {
            textEditor.doSave();
            graphicEditor.doSave();

            serializeToInternalFormat();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void doSaveAs() {
        // TODO check active editor and execute saveAs on it
        textEditor.doSaveAs();
    }

    /** {@inheritDoc} */
    @Override
    public void activate() {
        // TODO check active editor and execute active on it
        textEditor.activate();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isDirty() {
        return graphicEditor.isDirty() || textEditor.isDirty();
    }

    /** {@inheritDoc} */
    @Override
    public String getTitle() {
        if (isDirty()) {
            return "*" + input.getName();
        } else {
            return input.getName();
        }
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getTitleImage() {
        return input.getImageResource();
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public String getTitleToolTip() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public void go(AcceptsOneWidget container) {
        container.setWidget(view);
    }

    /** {@inheritDoc} */
    @Override
    public void onTextEditorButtonClicked() {
        view.setEnableTextEditorButton(false);
        view.setEnableGraphicalEditorButton(true);
        view.setEnableBothEditorButton(true);

        view.showTextEditor(textEditor);
    }

    /** {@inheritDoc} */
    @Override
    public void onGraphicalEditorButtonClicked() {
        view.setEnableTextEditorButton(true);
        view.setEnableGraphicalEditorButton(false);
        view.setEnableBothEditorButton(true);

        view.showGraphicalEditor(graphicEditor);
    }

    /** {@inheritDoc} */
    @Override
    public void onAssociateEditorButtonClicked() {
        view.setEnableTextEditorButton(true);
        view.setEnableGraphicalEditorButton(true);
        view.setEnableBothEditorButton(false);

        view.showEditors(graphicEditor, textEditor);
    }

    /** {@inheritDoc} */
    @Override
    public boolean onClose() {
        return graphicEditor.onClose() && textEditor.onClose() && super.onClose();
    }

    /** {@inheritDoc} */
    @Override
    public void propertyChanged(PartPresenter source, final int propId) {
        if (propId == EditorPartPresenter.PROP_DIRTY && source instanceof GraphicEditor) {
            textEditor.getDocument().set(graphicEditor.serialize());

            serializeToInternalFormat();

            isGraphicalEditorChanged = true;
            updateDirtyState(true);
        } else if ((propId == EditorPartPresenter.PROP_INPUT || propId == EditorPartPresenter.PROP_DIRTY) &&
                   source instanceof CodenvyTextEditor) {
            if (isGraphicalEditorChanged) {
                isGraphicalEditorChanged = false;
            } else {
                deserializeDiagram();
            }
        } else {
            firePropertyChange(propId);
        }
    }

    private void serializeToInternalFormat() {
        File diagramFile = textEditor.getEditorInput().getFile();

        final Folder parentFolder = diagramFile.getParent();
        String internalFileName = getFileName(diagramFile);

        final File internalFile = (File)parentFolder.findChildByName(internalFileName);
        final Project activeProject = resourceProvider.getActiveProject();

        if (internalFile == null) {
            activeProject.createFile(parentFolder,
                                     internalFileName,
                                     graphicEditor.serializeInternalFormat(),
                                     APPLICATION_XML,
                                     new WSO2AsyncCallback<File>(notificationManager) {
                                         @Override
                                         public void onSuccess(File result) {
                                             // do nothing
                                         }
                                     });
        } else {
            internalFile.setContent(graphicEditor.serializeInternalFormat());
            activeProject.updateContent(internalFile, new WSO2AsyncCallback<File>(notificationManager) {
                @Override
                public void onSuccess(File result) {
                    // do nothing
                }
            });
        }
    }

    private void deserializeDiagram() {
        File diagramFile = textEditor.getEditorInput().getFile();

        Folder parentFolder = diagramFile.getParent();
        String internalFileName = getFileName(diagramFile);

        File internalFile = (File)parentFolder.findChildByName(internalFileName);

        if (internalFile == null) {
            graphicEditor.deserialize(textEditor.getDocument().get());
        } else {
            graphicEditor.deserializeInternalFormat(internalFile.getContent());
        }
    }

    @NotNull
    private String getFileName(@NotNull File file) {
        Array<String> nameParts = StringUtils.split(file.getName(), ".");
        return nameParts.get(0) + CODENVY_INTERNAL_FORMAT_EXTENSION;
    }

}