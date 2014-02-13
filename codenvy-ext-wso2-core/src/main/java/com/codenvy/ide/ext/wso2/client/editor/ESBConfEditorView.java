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
import com.codenvy.ide.api.mvp.View;
import com.google.inject.ImplementedBy;

import javax.validation.constraints.NotNull;

/**
 * The view of {@link ESBConfEditor}.
 *
 * @author Andrey Plotnikov
 */
@ImplementedBy(ESBConfEditorViewImpl.class)
public interface ESBConfEditorView extends View<ESBConfEditorView.ActionDelegate> {

    public interface ActionDelegate {

        void onTextEditorButtonClicked();

        void onGraphicalEditorButtonClicked();

        void onAssociateEditorButtonClicked();

    }

    void setEnableTextEditorButton(boolean enable);

    void setEnableGraphicalEditorButton(boolean enable);

    void setEnableBothEditorButton(boolean enable);

    void showTextEditor(@NotNull CodenvyTextEditor textEditor);

    void showGraphicalEditor(@NotNull AbstractEditorPresenter graphicalEditor);

    void showEditors(@NotNull AbstractEditorPresenter graphicalEditor, @NotNull CodenvyTextEditor textEditor);

}