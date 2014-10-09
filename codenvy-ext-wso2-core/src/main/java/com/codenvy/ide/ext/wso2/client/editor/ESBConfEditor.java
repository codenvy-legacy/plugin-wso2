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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The presenter that provides a business logic of ESB editor. It provides an ability to work with all
 * properties of ESB editor.
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
        onDesignViewButtonClicked();
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
    public void doSave(@Nonnull AsyncCallback<EditorInput> callback) {
        doSave();

        // We should throw null value because for our implementation it isn't needed method. This implementation provides skipping any logic.
        callback.onSuccess(null);
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
    public void onSourceViewButtonClicked() {
        view.setEnableSourceViewButton(false);
        view.setEnableDesignViewButton(true);
        view.setEnableDualViewButton(true);

        view.showSourceView(textEditor);

        graphicEditor.resizeEditor();
    }

    /** {@inheritDoc} */
    @Override
    public void onDesignViewButtonClicked() {
        view.setEnableSourceViewButton(true);
        view.setEnableDesignViewButton(false);
        view.setEnableDualViewButton(true);

        view.showDesignView(graphicEditor);

        graphicEditor.resizeEditor();
    }

    /** {@inheritDoc} */
    @Override
    public void onDualViewButtonClicked() {
        view.setEnableSourceViewButton(true);
        view.setEnableDesignViewButton(true);
        view.setEnableDualViewButton(false);

        view.showDualView(graphicEditor, textEditor);

        graphicEditor.resizeEditor();
    }

    /** {@inheritDoc} */
    @Override
    public void onChangeToolbarVisibilityClicked() {
        graphicEditor.changeToolbarVisibility();
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertyButtonClicked() {
        graphicEditor.changePropertyPanelVisibility();
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