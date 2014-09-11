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
package com.codenvy.ide.client.inject;

import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.Element;
import com.codenvy.ide.client.elements.widgets.branch.BranchPresenter;
import com.codenvy.ide.client.elements.widgets.element.ElementPresenter;
import com.codenvy.ide.client.elements.widgets.element.ElementView;
import com.codenvy.ide.client.managers.PropertiesPanelManager;
import com.codenvy.ide.client.propertiespanel.switchmediator.branch.BranchFiledPresenter;
import com.codenvy.ide.client.toolbar.group.ToolbarGroupPresenter;
import com.codenvy.ide.client.toolbar.group.ToolbarGroupView;
import com.codenvy.ide.client.toolbar.item.ToolbarItemPresenter;
import com.codenvy.ide.client.toolbar.item.ToolbarItemView;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.Assisted;

import javax.annotation.Nonnull;

/**
 * The factory that is used for creating elements of WSO2 editor.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
@Singleton
public interface EditorFactory {

    @Nonnull
    ToolbarItemPresenter createToolbarItemPresenter(@Assisted("title") @Nonnull String title,
                                                    @Assisted("tooltip") @Nonnull String tooltip,
                                                    @Nonnull ImageResource icon,
                                                    @Assisted("newSate") @Nonnull String newState);

    @Nonnull
    ToolbarItemView createToolbarItemView(@Assisted("title") @Nonnull String title,
                                          @Assisted("tooltip") @Nonnull String tooltip,
                                          @Nonnull ImageResource icon);

    @Nonnull
    ToolbarGroupPresenter createToolbarGroupPresenter(@Nonnull String title);

    @Nonnull
    ToolbarGroupView createToolbarGroupView(@Nonnull String title);

    /**
     * Create an instance of {@link PropertiesPanelManager} with a given widget.
     *
     * @param container
     *         widget that need to be used
     * @return an instance of {@link PropertiesPanelManager}
     */
    @Nonnull
    PropertiesPanelManager createPropertiesPanelManager(@Nonnull AcceptsOneWidget container);

    /**
     * Create an instance of {@link BranchPresenter} with a given state of editor.
     *
     * @param branch
     *         element which need to be used
     * @return an instance of {@link BranchPresenter}
     */
    @Nonnull
    BranchPresenter createContainer(@Nonnull Branch branch);

    /**
     * Create an instance of {@link ElementPresenter} for a given element.
     *
     * @param element
     *         element for which presenter will be created
     * @return an instance of {@link ElementPresenter}
     */
    @Nonnull
    ElementPresenter createElementPresenter(@Nonnull Element element);

    /**
     * Create an instance of {@link ElementView} with allow to enhance branches.
     *
     * @param isPossibleChangeCases
     *         <code>true</code> possible to add branches,<code>false</code> impossible to add branches
     * @return an instance of {@link ElementView}
     */
    @Nonnull
    ElementView createElementView(boolean isPossibleChangeCases);

    /**
     * Create an instance of {@link BranchFiledPresenter} with a given branch.
     *
     * @param branch
     *         branch for which need to create presenter
     * @param index
     *         number of branch
     * @return an instance of {@link BranchFiledPresenter}
     */
    @Nonnull
    BranchFiledPresenter createBranchFieldPresenter(@Nonnull Branch branch, int index);

}