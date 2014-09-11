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

import com.codenvy.ide.client.inject.EditorFactory;
import com.codenvy.ide.client.mvp.AbstractPresenter;
import com.codenvy.ide.client.toolbar.item.ToolbarItemPresenter;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javax.annotation.Nonnull;

/**
 * @author Andrey Plotnikov
 */
public class ToolbarGroupPresenter extends AbstractPresenter<ToolbarGroupView> implements ToolbarGroupView.ActionDelegate {

    private boolean isFolded;

    @Inject
    public ToolbarGroupPresenter(EditorFactory editorFactory, @Assisted String title) {
        super(editorFactory.createToolbarGroupView(title));

        fold();
    }

    @Nonnull
    public Widget getView() {
        return view;
    }

    public void fold() {
        isFolded = true;
        view.setVisibleMainPanel(false);
    }

    public void unfold() {
        isFolded = false;
        view.setVisibleMainPanel(true);
    }

    public void addItem(@Nonnull ToolbarItemPresenter toolbarItem) {
        view.addItem(toolbarItem);
    }

    /** {@inheritDoc} */
    @Override
    public void onButtonClicked() {
        if (isFolded) {
            unfold();
        } else {
            fold();
        }
    }

}