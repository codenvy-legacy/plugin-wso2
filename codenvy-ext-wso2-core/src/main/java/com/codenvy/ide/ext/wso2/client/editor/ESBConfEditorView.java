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
import com.codenvy.ide.api.mvp.View;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;

/**
 * The view of {@link ESBConfEditor}.
 *
 * @author Andrey Plotnikov
 */
@ImplementedBy(ESBConfEditorViewImpl.class)
public interface ESBConfEditorView extends View<ESBConfEditorView.ActionDelegate> {

    public interface ActionDelegate {

        /** Performs some actions in response to user clicked on source view button. */
        void onTextEditorButtonClicked();

        /** Performs some actions in response to user clicked on design view button. */
        void onGraphicalEditorButtonClicked();

        /** Performs some actions in response to user clicked on dual view button. */
        void onAssociateEditorButtonClicked();

    }

    /**
     * Sets enabling of source view button.
     *
     * @param enable
     *         <code>true</code> button is enable,<code>false</code> button is disable
     */
    void setEnableTextEditorButton(boolean enable);

    /**
     * Sets enabling of design view button.
     *
     * @param enable
     *         <code>true</code> button is enable,<code>false</code> button is disable
     */
    void setEnableGraphicalEditorButton(boolean enable);

    /**
     * Sets enabling of dual view button.
     *
     * @param enable
     *         <code>true</code> button is enable,<code>false</code> button is disable
     */
    void setEnableBothEditorButton(boolean enable);

    /**
     * Shows main panel for displaying text.
     *
     * @param textEditor
     *         editor which provides displaying of source view
     */
    void showTextEditor(@Nonnull CodenvyTextEditor textEditor);

    /**
     * Shows main panel for displaying design view.
     *
     * @param graphicalEditor
     *         editor which provides displaying of design view
     */
    void showGraphicalEditor(@Nonnull AbstractEditorPresenter graphicalEditor);

    /**
     * Shows main panel for displaying dual view.
     *
     * @param graphicalEditor
     *         editor which provides displaying of design view
     * @param textEditor
     *         editor which provides displaying of source view
     */
    void showEditors(@Nonnull AbstractEditorPresenter graphicalEditor, @Nonnull CodenvyTextEditor textEditor);

}