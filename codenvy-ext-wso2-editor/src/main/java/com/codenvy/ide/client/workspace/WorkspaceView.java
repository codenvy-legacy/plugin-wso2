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
package com.codenvy.ide.client.workspace;

import com.codenvy.ide.api.mvp.View;
import com.codenvy.ide.client.elements.widgets.element.ElementPresenter;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * The abstract view that represents the workspace visual part of the widget.
 *
 * @author Andrey Plotnikov
 */
@ImplementedBy(WorkspaceViewImpl.class)
public interface WorkspaceView extends View<WorkspaceView.ActionDelegate> {

    /**
     * Set element widget on the view.
     *
     * @param elementPresenter
     *         element widget that needs to be shown
     */
    void setElement(@Nonnull ElementPresenter elementPresenter);

    /** @return height of a parent widget */
    @Nonnegative
    int getHeight();

    /** @return width of a parent widget */
    @Nonnegative
    int getWidth();

    public interface ActionDelegate {
        /** Performs some actions in response to a user's resizing browser window. */
        void onWindowResize();
    }

}