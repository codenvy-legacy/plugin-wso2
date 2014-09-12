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

import com.codenvy.ide.client.inject.factories.ToolbarFactory;
import com.codenvy.ide.client.mvp.AbstractPresenter;
import com.codenvy.ide.client.toolbar.item.ToolbarItemPresenter;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javax.annotation.Nonnull;

/**
 * The class that provides business logic of the toolbar group widget.
 *
 * @author Andrey Plotnikov
 */
public class ToolbarGroupPresenter extends AbstractPresenter<ToolbarGroupView> implements ToolbarGroupView.ActionDelegate {

    private boolean isFolded;

    @Inject
    public ToolbarGroupPresenter(ToolbarFactory toolbarFactory, @Assisted String title) {
        super(toolbarFactory.createToolbarGroupView(title));

        fold();
    }

    /** @return the GWT widget that is controlled by the presenter */
    @Nonnull
    public Widget getView() {
        return view;
    }

    private void fold() {
        isFolded = true;
        view.setVisibleItemsPanel(false);
        view.defaultIcon();
    }

    private void unfold() {
        isFolded = false;
        view.setVisibleItemsPanel(true);
        view.rotateIcon();
    }

    /**
     * Adds a new toolbar item to this group.
     *
     * @param toolbarItem
     *         toolbar item that needs to be added
     */
    public void addItem(@Nonnull ToolbarItemPresenter toolbarItem) {
        view.addItem(toolbarItem);
    }

    /** {@inheritDoc} */
    @Override
    public void onItemClicked() {
        if (isFolded) {
            unfold();
        } else {
            fold();
        }
    }

}