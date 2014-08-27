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
import com.codenvy.ide.api.ui.workspace.PartPresenter;
import com.codenvy.ide.api.ui.workspace.PropertyListener;
import com.codenvy.ide.ext.wso2.client.editor.graphical.GraphicEditor;
import com.codenvy.ide.ext.wso2.client.editor.text.XmlEditorConfiguration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The editor for WSO2 ESB configuration.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class ESBConfEditor extends AbstractEditorPresenter implements ESBConfEditorView.ActionDelegate, PropertyListener {

    private final ESBConfEditorView view;
    private final GraphicEditor     graphicEditor;
    private final CodenvyTextEditor textEditor;

    private boolean isGraphicalEditorChanged;

    @Inject
    public ESBConfEditor(ESBConfEditorView view,
                         DocumentProvider documentProvider,
                         Provider<CodenvyTextEditor> editorProvider,
                         Provider<XmlEditorConfiguration> xmlEditorConfigurationProvider,
                         NotificationManager notificationManager,
                         GraphicEditor graphicEditor) {
        this.view = view;
        this.view.setDelegate(this);
        this.graphicEditor = graphicEditor;
        textEditor = editorProvider.get();
        textEditor.initialize(xmlEditorConfigurationProvider.get(), documentProvider, notificationManager);

        this.graphicEditor.addPropertyListener(this);
        textEditor.addPropertyListener(this);
    }

    /** {@inheritDoc} */
    @Override
    public void init(@Nonnull EditorInput input) throws EditorInitException {
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
            isGraphicalEditorChanged = true;

            textEditor.doSave();
            graphicEditor.doSave();
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
    @Nonnull
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
    public void go(@Nonnull AcceptsOneWidget container) {
        container.setWidget(view);

        isGraphicalEditorChanged = false;
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
    public void propertyChanged(@Nonnull PartPresenter source, int propId) {
        if (propId == EditorPartPresenter.PROP_DIRTY && source instanceof GraphicEditor) {
            textEditor.getDocument().set(graphicEditor.serialize());

            isGraphicalEditorChanged = true;
            updateDirtyState(true);
        } else if ((propId == EditorPartPresenter.PROP_INPUT || propId == EditorPartPresenter.PROP_DIRTY) &&
                   source instanceof CodenvyTextEditor) {
            if (isGraphicalEditorChanged) {
                isGraphicalEditorChanged = false;
            } else {
                graphicEditor.deserialize(textEditor.getDocument().get());
            }
        } else {
            firePropertyChange(propId);
        }
    }

}