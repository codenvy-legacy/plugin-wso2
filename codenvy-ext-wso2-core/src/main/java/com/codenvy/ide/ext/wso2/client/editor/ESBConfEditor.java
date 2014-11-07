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
import com.codenvy.ide.api.editor.EditorInitException;
import com.codenvy.ide.api.editor.EditorInput;
import com.codenvy.ide.api.editor.EditorPartPresenter;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.parts.PartPresenter;
import com.codenvy.ide.api.parts.PropertyListener;
import com.codenvy.ide.api.text.RegionImpl;
import com.codenvy.ide.ext.wso2.client.editor.graphical.GraphicEditor;
import com.codenvy.ide.jseditor.client.defaulteditor.DefaultEditorProvider;
import com.codenvy.ide.jseditor.client.document.EmbeddedDocument;
import com.codenvy.ide.jseditor.client.editorconfig.DefaultTextEditorConfiguration;
import com.codenvy.ide.jseditor.client.texteditor.EmbeddedTextEditorPartView;
import com.codenvy.ide.jseditor.client.texteditor.EmbeddedTextEditorPresenter;
import com.codenvy.ide.util.loging.Log;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import org.vectomatic.dom.svg.ui.SVGResource;

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

    private final ESBConfEditorView           view;
    private final GraphicEditor               graphicEditor;
    private final EmbeddedTextEditorPresenter textEditor;

    private boolean isGraphicalEditorChanged;

    @Inject
    public ESBConfEditor(ESBConfEditorView view,
                         DefaultEditorProvider editorProvider,
                         GraphicEditor graphicEditor,
                         NotificationManager notificationManager) {
        this.view = view;
        this.view.setDelegate(this);

        this.graphicEditor = graphicEditor;
        this.graphicEditor.addPropertyListener(this);
        this.view.addGraphicalEditorWidget(graphicEditor);

        EditorPartPresenter editor = editorProvider.getEditor();

        if (editor instanceof EmbeddedTextEditorPresenter) {
            textEditor = (EmbeddedTextEditorPresenter)editor;
            textEditor.addPropertyListener(this);
            textEditor.initialize(new DefaultTextEditorConfiguration(), notificationManager);

            this.view.addTextEditorWidget(textEditor);
        } else {
            textEditor = null;
            Log.error(getClass(), "classic implementation, no dedicated configuration available");
        }
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
        if (isDirty()) {
            isGraphicalEditorChanged = true;

            textEditor.doSave(callback);
            graphicEditor.doSave();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void doSaveAs() {
        textEditor.doSaveAs();
    }

    /** {@inheritDoc} */
    @Override
    public void activate() {
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
            return '*' + input.getName();
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
    public SVGResource getTitleSVGImage() {
        return input.getSVGResource();
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

        view.showSourceView();
    }

    /** {@inheritDoc} */
    @Override
    public void onDesignViewButtonClicked() {
        view.setEnableSourceViewButton(true);
        view.setEnableDesignViewButton(false);
        view.setEnableDualViewButton(true);

        view.showDesignView();
    }

    /** {@inheritDoc} */
    @Override
    public void onDualViewButtonClicked() {
        view.setEnableSourceViewButton(true);
        view.setEnableDesignViewButton(true);
        view.setEnableDualViewButton(false);

        view.showDualView();
    }

    /** {@inheritDoc} */
    @Override
    public void onChangeToolbarVisibilityClicked() {
        graphicEditor.changeToolbarVisibility();
    }

    /** {@inheritDoc} */
    @Override
    public void onEditorDOMChanged() {
        graphicEditor.resizeEditor();
        textEditor.getView().onResize();
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertyButtonClicked() {
        graphicEditor.changePropertyPanelVisibility();
    }

    /** {@inheritDoc} */
    @Override
    public void onClose(@Nonnull final AsyncCallback<Void> callback) {
        textEditor.onClose(callback);
        handleClose();
    }

    /** {@inheritDoc} */
    @Override
    public void propertyChanged(@Nonnull PartPresenter source, int propId) {
        if (propId == PROP_DIRTY && source instanceof GraphicEditor) {
            applyChangesToTextEditor();
        } else if ((propId == PROP_INPUT || propId == PROP_DIRTY) && source instanceof EmbeddedTextEditorPresenter) {
            applyChangesToGraphicalEditor();
        } else {
            firePropertyChange(propId);
        }
    }

    private void applyChangesToTextEditor() {
        EmbeddedTextEditorPartView textEditorView = textEditor.getView();

        EmbeddedDocument document = textEditorView.getEmbeddedDocument();
        String contents = document.getContents();
        document.replace(new RegionImpl(0, contents.length()), graphicEditor.serialize());

        isGraphicalEditorChanged = true;
        updateDirtyState(true);
    }

    private void applyChangesToGraphicalEditor() {
        if (isGraphicalEditorChanged) {
            isGraphicalEditorChanged = false;
        } else {
            graphicEditor.deserialize(textEditor.getView().getContents());
        }
    }

}