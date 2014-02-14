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
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import javax.validation.constraints.NotNull;

/**
 * The implementation of {@link ESBConfEditorView}.
 *
 * @author Andrey Plotnikov
 */
public class ESBConfEditorViewImpl extends Composite implements ESBConfEditorView {

    interface ESBConfEditorViewImplUiBinder extends UiBinder<Widget, ESBConfEditorViewImpl> {
    }

    @UiField
    SimplePanel mainPanel;
    @UiField
    Button      textEditorChoose;
    @UiField
    Button      graphicalEditorChoose;
    @UiField
    Button      associateEditorChoose;

    private ActionDelegate  delegate;
    private DockLayoutPanel bothEditorPanel;
    private SimplePanel     graphicalEditorPanel;
    private SimplePanel     textEditorPanel;

    @Inject
    public ESBConfEditorViewImpl(ESBConfEditorViewImplUiBinder ourUiBinder) {
        initWidget(ourUiBinder.createAndBindUi(this));

        bothEditorPanel = new DockLayoutPanel(Style.Unit.PCT);
        bothEditorPanel.setSize("100%", "100%");

        graphicalEditorPanel = new SimplePanel();
        bothEditorPanel.addWest(graphicalEditorPanel, 50);

        textEditorPanel = new SimplePanel();
        bothEditorPanel.add(textEditorPanel);
    }

    /** {@inheritDoc} */
    @Override
    public void setDelegate(ActionDelegate delegate) {
        this.delegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public void setEnableTextEditorButton(boolean enable) {
        textEditorChoose.setEnabled(enable);
    }

    /** {@inheritDoc} */
    @Override
    public void setEnableGraphicalEditorButton(boolean enable) {
        graphicalEditorChoose.setEnabled(enable);
    }

    /** {@inheritDoc} */
    @Override
    public void setEnableBothEditorButton(boolean enable) {
        associateEditorChoose.setEnabled(enable);
    }

    /** {@inheritDoc} */
    @Override
    public void showTextEditor(@NotNull CodenvyTextEditor textEditor) {
        textEditor.go(mainPanel);
    }

    /** {@inheritDoc} */
    @Override
    public void showGraphicalEditor(@NotNull AbstractEditorPresenter graphicalEditor) {
        graphicalEditor.go(mainPanel);
    }

    /** {@inheritDoc} */
    @Override
    public void showEditors(@NotNull AbstractEditorPresenter graphicalEditor, @NotNull CodenvyTextEditor textEditor) {
        graphicalEditor.go(graphicalEditorPanel);
        textEditor.go(textEditorPanel);

        mainPanel.setWidget(bothEditorPanel);
    }

    @UiHandler("textEditorChoose")
    public void onTextEditorButtonClicked(ClickEvent event) {
        delegate.onTextEditorButtonClicked();
    }

    @UiHandler("graphicalEditorChoose")
    public void onGraphicalEditorButtonClicked(ClickEvent event) {
        delegate.onGraphicalEditorButtonClicked();
    }

    @UiHandler("associateEditorChoose")
    public void onAssociateEditorButtonClicked(ClickEvent event) {
        delegate.onAssociateEditorButtonClicked();
    }
}