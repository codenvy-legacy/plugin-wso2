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
package com.codenvy.ide.client.toolbar;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.inject.factories.ToolbarFactory;
import com.codenvy.ide.client.mvp.AbstractPresenter;
import com.codenvy.ide.client.toolbar.group.ToolbarGroupPresenter;
import com.codenvy.ide.util.loging.Log;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * The presenter that provides a business logic of toolbar. It provides an ability to add a toolbar item.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
@Singleton
public class ToolbarPresenter extends AbstractPresenter<ToolbarView> implements ToolbarView.ActionDelegate {

    private final Map<String, ToolbarGroupPresenter> groups;
    private final ToolbarFactory                     toolbarFactory;
    private final Set<String>                        states;
    private final WSO2EditorLocalizationConstant     locale;

    @Inject
    public ToolbarPresenter(ToolbarView view, ToolbarFactory toolbarFactory, WSO2EditorLocalizationConstant locale) {
        super(view);

        this.toolbarFactory = toolbarFactory;
        this.groups = new LinkedHashMap<>();
        this.states = new HashSet<>();
        this.locale = locale;
    }

    /**
     * Adds a new toolbar group. It is impossible to add a few group with the same id.
     *
     * @param groupId
     *         unique id of group
     * @param title
     *         title that need to be shown on the toolbar
     */
    public void addGroup(@Nonnull String groupId, @Nonnull String title) {
        if (groups.containsKey(groupId)) {
            Log.error(getClass(), locale.errorToolbarGroupWasAlreadyRegistered(groupId));
            return;
        }

        groups.put(groupId, toolbarFactory.createToolbarGroupPresenter(title));
    }

    /**
     * Adds a new toolbar item. It is impossible to add a item when a parent group hasn't been created or when adding editor state has
     * already added.
     *
     * @param groupId
     *         parent group id
     * @param title
     *         title that need to be shown on the toolbar
     * @param tooltip
     *         tooltip that need to be shown on the toolbar
     * @param icon
     *         icon that need to be shown on the toolbar
     * @param editorState
     *         editor state that will be set when one is clicking on this item
     */
    public void addItem(@Nonnull String groupId,
                        @Nonnull String title,
                        @Nonnull String tooltip,
                        @Nonnull ImageResource icon,
                        @Nonnull String editorState) {
        if (!groups.containsKey(groupId)) {
            Log.error(getClass(), locale.errorToolbarGroupHasNotRegisteredYet(groupId));
            return;
        }

        if (states.contains(editorState)) {
            Log.error(getClass(), locale.errorToolbarEditorStateWasAlreadyAdded(editorState));
            return;
        }

        ToolbarGroupPresenter group = groups.get(groupId);

        group.addItem(toolbarFactory.createToolbarItemPresenter(title, tooltip, icon, editorState));
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        for (Map.Entry<String, ToolbarGroupPresenter> item : groups.entrySet()) {
            view.addGroup(item.getValue());
        }
    }

}