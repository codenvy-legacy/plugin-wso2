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

import com.codenvy.ide.client.inject.EditorFactory;
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
 * The presenter that provides a business logic of tool bar. It provides an ability to work with all elements which it contains.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
@Singleton
public class ToolbarPresenter extends AbstractPresenter<ToolbarView> implements ToolbarView.ActionDelegate {

    private final Map<String, ToolbarGroupPresenter> groups;
    private final EditorFactory                      editorFactory;
    private final Set<String>                        states;

    @Inject
    public ToolbarPresenter(ToolbarView view, EditorFactory editorFactory) {
        super(view);

        this.editorFactory = editorFactory;
        this.groups = new LinkedHashMap<>();
        this.states = new HashSet<>();
    }

    public void addGroup(@Nonnull String groupId, @Nonnull String title) {
        if (groups.containsKey(groupId)) {
            Log.error(getClass(), "Group with the ID " + groupId + " was already registered.");
            return;
        }

        groups.put(groupId, editorFactory.createToolbarGroupPresenter(title));
    }

    public void addItem(@Nonnull String groupId,
                        @Nonnull String title,
                        @Nonnull String tooltip,
                        @Nonnull ImageResource icon,
                        @Nonnull String editorState) {
        if (!groups.containsKey(groupId)) {
            Log.error(getClass(), "Group with the ID " + groupId + " hasn't registered yet.");
            return;
        }

        if (states.contains(editorState)) {
            Log.error(getClass(), "State " + editorState + " was already added.");
            return;
        }

        ToolbarGroupPresenter group = groups.get(groupId);

        group.addItem(editorFactory.createToolbarItemPresenter(title, tooltip, icon, editorState));
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