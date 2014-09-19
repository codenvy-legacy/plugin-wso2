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

package com.codenvy.ide.client.toolbar.item;

import com.codenvy.ide.client.EditorState;
import com.codenvy.ide.client.inject.factories.ToolbarFactory;
import com.codenvy.ide.client.mvp.AbstractPresenter;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import javax.annotation.Nonnull;

/**
 * The class that provides business logic of the toolbar item widget.
 *
 * @author Andrey Plotnikov
 */
public class ToolbarItemPresenter extends AbstractPresenter<ToolbarItemView> implements ToolbarItemView.ActionDelegate {

    private final EditorState editorState;
    private final String      newState;

    @AssistedInject
    public ToolbarItemPresenter(EditorState editorState,
                                ToolbarFactory toolbarFactory,
                                @Assisted("title") String title,
                                @Assisted("tooltip") String tooltip,
                                @Assisted ImageResource icon,
                                @Assisted("newSate") String newState) {
        super(toolbarFactory.createToolbarItemView(title, tooltip, icon));

        this.editorState = editorState;
        this.newState = newState;
    }

    /** @return the GWT widget that is controlled by the presenter */
    @Nonnull
    public IsWidget getView() {
        return view;
    }

    /** {@inheritDoc} */
    @Override
    public void onItemClicked() {
        editorState.setState(newState);
    }

}