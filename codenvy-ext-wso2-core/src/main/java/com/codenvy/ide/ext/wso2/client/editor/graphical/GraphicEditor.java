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
package com.codenvy.ide.ext.wso2.client.editor.graphical;

import com.codenvy.editor.api.editor.AbstractEditor;
import com.codenvy.ide.api.editor.AbstractEditorPresenter;
import com.codenvy.ide.client.WSO2Editor;
import com.codenvy.ide.client.inject.Injector;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

/**
 * The graphical editor for ESB configuration.
 *
 * @author Andrey Plotnikov
 */
public class GraphicEditor extends AbstractEditorPresenter implements GraphicEditorView.ActionDelegate,
                                                                      AbstractEditor.EditorChangeListener {

    private final GraphicEditorView view;
    private final WSO2Editor        editor;

    @Inject
    public GraphicEditor(EditorViewFactory editorViewFactory, Injector editorInjector) {
        editor = editorInjector.getEditor();
        editor.addListener(this);

        this.view = editorViewFactory.getEditorView(editor);
        this.view.setDelegate(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initializeEditor() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doSave() {
        updateDirtyState(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doSaveAs() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activate() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle() {
        return "ESB Editor: " + input.getFile().getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImageResource getTitleImage() {
        return input.getImageResource();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitleToolTip() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void go(AcceptsOneWidget container) {
        container.setWidget(view);
    }

    @Nonnull
    public String serialize() {
        return editor.serialize();
    }

    @Nonnull
    public String serializeInternalFormat() {
        return editor.serializeInternalFormat();
    }

    public void deserialize(@Nonnull String content) {
        editor.deserialize(content);
    }

    public void deserializeInternalFormat(@Nonnull String content) {
        editor.deserializeInternalFormat(content);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onChanged() {
        updateDirtyState(true);
    }

}
