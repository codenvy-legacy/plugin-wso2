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

package com.codenvy.ide.client.inject.factories;

import com.codenvy.ide.client.toolbar.group.ToolbarGroupPresenter;
import com.codenvy.ide.client.toolbar.group.ToolbarGroupView;
import com.codenvy.ide.client.toolbar.item.ToolbarItemPresenter;
import com.codenvy.ide.client.toolbar.item.ToolbarItemView;
import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.Assisted;

import javax.annotation.Nonnull;

/**
 * The factory that is used for creating parts of the toolbar of WSO2 editor.
 *
 * @author Andrey Plotnikov
 */
@Singleton
public interface ToolbarFactory {

    /**
     * Create an instance of {@link ToolbarItemPresenter} with given parameters.
     *
     * @param title
     *         title that need to be shown on the toolbar for this toolbar item
     * @param tooltip
     *         tooltip that need to be shown on the toolbar for this toolbar item
     * @param icon
     *         icon that need to be shown on the toolbar for this toolbar item
     * @param newState
     *         state that need to be set when user is clicking on the item
     * @return an instance {@link ToolbarItemPresenter}
     */
    @Nonnull
    ToolbarItemPresenter createToolbarItemPresenter(@Assisted("title") @Nonnull String title,
                                                    @Assisted("tooltip") @Nonnull String tooltip,
                                                    @Nonnull ImageResource icon,
                                                    @Assisted("newSate") @Nonnull String newState);

    /**
     * Create an instance of {@link ToolbarItemView} with given parameters.
     *
     * @param title
     *         title that need to be shown on the toolbar for this toolbar item
     * @param tooltip
     *         tooltip that need to be shown on the toolbar for this toolbar item
     * @param icon
     *         icon that need to be shown on the toolbar for this toolbar item
     * @return an instance {@link ToolbarItemView}
     */
    @Nonnull
    ToolbarItemView createToolbarItemView(@Assisted("title") @Nonnull String title,
                                          @Assisted("tooltip") @Nonnull String tooltip,
                                          @Nonnull ImageResource icon);

    /**
     * Create an instance of {@link ToolbarGroupPresenter} with a given title.
     *
     * @param title
     *         title that need to be shown on the toolbar for this toolbar group
     * @return an instance {@link ToolbarGroupPresenter}
     */
    @Nonnull
    ToolbarGroupPresenter createToolbarGroupPresenter(@Nonnull String title);

    /**
     * Create an instance of {@link ToolbarGroupView} with a given title.
     *
     * @param title
     *         title that need to be shown on the toolbar for this toolbar group
     * @return an instance {@link ToolbarGroupView}
     */
    @Nonnull
    ToolbarGroupView createToolbarGroupView(@Nonnull String title);

}