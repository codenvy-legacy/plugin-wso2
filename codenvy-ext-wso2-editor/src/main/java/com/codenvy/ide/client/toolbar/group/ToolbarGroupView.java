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
package com.codenvy.ide.client.toolbar.group;

import com.codenvy.ide.api.mvp.View;
import com.codenvy.ide.client.toolbar.item.ToolbarItemPresenter;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;

/**
 * The abstract view that represents the toolbar group visual part of the widget.
 *
 * @author Andrey Plotnikov
 */
@ImplementedBy(ToolbarGroupViewImpl.class)
public interface ToolbarGroupView extends View<ToolbarGroupView.ActionDelegate> {

    /**
     * Changes visible state of item's panel of the view.
     *
     * @param visible
     *         <code>true</code> the panel will be shown, <code>false</code> it will not
     */
    void setVisibleItemsPanel(boolean visible);

    /**
     * Adds a toolbar item widget on the view.
     *
     * @param toolbarItem
     *         toolbar item that need to be added
     */
    void addItem(@Nonnull ToolbarItemPresenter toolbarItem);

    /** Rotate fold/unfold icon on the view. */
    void rotateIcon();

    /** Reset default view of the fold/unfold icon on the view. */
    void defaultIcon();

    /**
     * Folds or unfolds toolbar group.
     *
     * @param expanded
     *         <code>true</code> the panel is expanded, <code>false</code> the panel is collapsed
     */
    void expandOrCollapse(boolean expanded);

    public interface ActionDelegate {
        /** Performs some actions in response to a user's clicking on the main panel of the view. */
        void onItemClicked();
    }

}