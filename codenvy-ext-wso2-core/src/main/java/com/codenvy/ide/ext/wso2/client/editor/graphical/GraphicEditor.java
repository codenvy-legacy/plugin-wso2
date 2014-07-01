/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 * [2012] - [2014] Codenvy, S.A. 
 * All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
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
