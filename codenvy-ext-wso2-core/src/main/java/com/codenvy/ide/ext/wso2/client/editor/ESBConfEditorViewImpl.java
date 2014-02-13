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
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
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
    SimplePanel textEditorPanel;
    @UiField
    SimplePanel graphicalEditorPanel;

    @Inject
    public ESBConfEditorViewImpl(ESBConfEditorViewImplUiBinder ourUiBinder) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Override
    public void setDelegate(ActionDelegate delegate) {
        // nothing to do for now
    }

    /** {@inheritDoc} */
    @Override
    public void showTextEditor(@NotNull CodenvyTextEditor textEditor) {
        textEditor.go(textEditorPanel);
    }

    /** {@inheritDoc} */
    @Override
    public void showGraphicalEditor(@NotNull AbstractEditorPresenter graphicalEditor) {
        graphicalEditor.go(graphicalEditorPanel);
    }
}