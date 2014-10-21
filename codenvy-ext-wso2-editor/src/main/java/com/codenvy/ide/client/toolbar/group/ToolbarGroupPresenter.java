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
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * The class that provides business logic of the toolbar group widget.
 *
 * @author Andrey Plotnikov
 */
public class ToolbarGroupPresenter extends AbstractPresenter<ToolbarGroupView> implements ToolbarGroupView.ActionDelegate {

    private final List<OpenGroupToolbarListener> listeners;

    private boolean isFolded;

    @Inject
    public ToolbarGroupPresenter(ToolbarFactory toolbarFactory, @Assisted String title) {
        super(toolbarFactory.createToolbarGroupView(title));

        listeners = new ArrayList<>();

        fold();
    }

    /** @return the GWT widget that is controlled by the presenter */
    @Nonnull
    public IsWidget getView() {
        return view;
    }

    /** The method folds group of elements which it contains. */
    public void fold() {
        isFolded = true;
        view.expandOrCollapse(true);
    }

    /** The method display group of elements which it contains. */
    public void unfold() {
        isFolded = false;
        view.expandOrCollapse(false);
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

            notifyListeners();
        } else {
            fold();
        }
    }

    /**
     * Adds a listener to list of listeners of toolbar group presenter.
     *
     * @param listener
     *         listener which needs to be added
     */
    public void addListener(@Nonnull OpenGroupToolbarListener listener) {
        listeners.add(listener);
    }

    /** Notify all listeners of group toolbar presenter when user clicked on any group of elements. */
    public void notifyListeners() {
        for (OpenGroupToolbarListener listener : listeners) {
            listener.onOpenToolbarGroup(this);
        }
    }

    /** Listener which need to trace a toolbar group state. If one group of elements is open, another must be closed. */
    public interface OpenGroupToolbarListener {
        /**
         * Method which calls when we open any group. The method opens the group which we select and closes another.
         *
         * @param toolbarGroupPresenter
         *         selected group of properties
         */
        void onOpenToolbarGroup(@Nonnull ToolbarGroupPresenter toolbarGroupPresenter);
    }

}