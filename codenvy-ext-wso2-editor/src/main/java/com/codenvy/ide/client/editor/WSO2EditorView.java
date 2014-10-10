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
package com.codenvy.ide.client.editor;

import com.codenvy.ide.api.mvp.View;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;

/**
 * The general presentation of editor view. It contains a few general places for toolbar, properties panel and workspace.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
@ImplementedBy(WSO2EditorViewImpl.class)
public interface WSO2EditorView extends View<WSO2EditorView.ActionDelegate> {

    /**
     * Interface defines methods of WSO2Editor's presenter which calls from view. These methods defines
     * some actions when user click show or hide button of properties panel.
     */
    public interface ActionDelegate {

        /** Performs some actions in response to user's clicking on hide toolbar button. */
        void onCloseToolbarButtonClicked();

        /** Performs some actions in response to user's clicking on hide panel button. */
        void onHidePanelButtonClicked();

        /** Performs some actions in response to DOM of editor is changed. */
        void onEditorDOMChanged();
    }

    /**
     * Changes visible state of the toolbar panel.
     *
     * @param isVisible
     *         <code>true</code> the panel will be shown, <code>false</code> it will not
     */
    void setToolbarPanelVisibility(boolean isVisible);

    /** @return place where toolbar need to be located */
    @Nonnull
    AcceptsOneWidget getToolbarPanel();

    /** @return place where properties panel need to be located */
    @Nonnull
    AcceptsOneWidget getPropertiesPanel();

    /** @return place where workspace need to be located */
    @Nonnull
    AcceptsOneWidget getWorkspacePanel();

    /**
     * Changes visible state of the property panel.
     *
     * @param isVisible
     *         <code>true</code> the panel will be shown, <code>false</code> it will not
     */
    void setVisiblePropertyPanel(boolean isVisible);

    /** Hides the toolbar panel. */
    void hideToolbarPanel();

}