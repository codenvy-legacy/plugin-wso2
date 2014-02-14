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
package com.codenvy.ide.ext.wso2.client.editor;

import com.codenvy.ide.api.editor.AbstractEditorPresenter;
import com.codenvy.ide.api.editor.CodenvyTextEditor;
import com.codenvy.ide.api.editor.DocumentProvider;
import com.codenvy.ide.api.editor.EditorInitException;
import com.codenvy.ide.api.editor.EditorInput;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.ui.workspace.PartPresenter;
import com.codenvy.ide.api.ui.workspace.PropertyListener;
import com.codenvy.ide.ext.wso2.client.editor.graphical.GraphicEditor;
import com.codenvy.ide.ext.wso2.client.editor.text.XmlEditorConfiguration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

/**
 * The editor for WSO2 ESB configuration.
 *
 * @author Andrey Plotnikov
 */
public class ESBConfEditor extends AbstractEditorPresenter implements ESBConfEditorView.ActionDelegate {

    private ESBConfEditorView view;
    private GraphicEditor     graphicEditor;
    private CodenvyTextEditor textEditor;

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

        PropertyListener propertyListener = new PropertyListener() {
            @Override
            public void propertyChanged(PartPresenter source, int propId) {
                firePropertyChange(propId);
            }
        };

        this.graphicEditor.addPropertyListener(propertyListener);
        textEditor.addPropertyListener(propertyListener);
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
        view.setEnableTextEditorButton(false);
        view.setEnableGraphicalEditorButton(true);
        view.setEnableBothEditorButton(true);

        view.showTextEditor(textEditor);
    }

    /** {@inheritDoc} */
    @Override
    public void doSave() {
        // TODO need to think how to improve it
        if (textEditor.isDirty()) {
            textEditor.doSave();
        }

        if (graphicEditor.isDirty()) {
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
}