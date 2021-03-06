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

import com.codenvy.ide.api.mvp.View;

/**
 * The abstract view that represents the toolbar item visual part of the widget.
 *
 * @author Andrey Plotnikov
 */
public interface ToolbarItemView extends View<ToolbarItemView.ActionDelegate> {

    public interface ActionDelegate {
        /** Performs some actions in response to a user's clicking on the main panel of the view. */
        void onItemClicked();
    }

}