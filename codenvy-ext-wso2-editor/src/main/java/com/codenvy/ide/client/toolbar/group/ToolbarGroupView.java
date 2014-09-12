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

import com.codenvy.ide.client.mvp.AbstractView;
import com.codenvy.ide.client.toolbar.item.ToolbarItemPresenter;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;

/**
 * @author Andrey Plotnikov
 */
@ImplementedBy(ToolbarGroupViewImpl.class)
public abstract class ToolbarGroupView extends AbstractView<ToolbarGroupView.ActionDelegate> {

    public abstract void setVisibleMainPanel(boolean visible);

    public abstract void addItem(@Nonnull ToolbarItemPresenter toolbarItem);

    public abstract void rotateIcon();

    public abstract void defaultIcon();

    public interface ActionDelegate extends AbstractView.ActionDelegate {
        void onItemClicked();
    }

}