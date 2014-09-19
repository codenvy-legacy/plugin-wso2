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

package com.codenvy.ide.client.propertiespanel.property.group;

import com.codenvy.ide.api.mvp.View;
import com.codenvy.ide.client.propertiespanel.property.AbstractPropertyPresenter;

import javax.annotation.Nonnull;

/**
 * The abstract view that represents the property group visual part of the widget.
 *
 * @author Andrey Plotnikov
 */
public interface PropertyGroupView extends View<PropertyGroupView.ActionDelegate> {

    /**
     * Changes visible state of item's panel of the view.
     *
     * @param visible
     *         <code>true</code> the panel will be shown, <code>false</code> it will not
     */
    void setVisibleItemsPanel(boolean visible);

    /**
     * Adds a property widget on the view.
     *
     * @param property
     *         property that need to be added
     */
    void addProperty(@Nonnull AbstractPropertyPresenter property);

    /**
     * Removes a property widget from the view.
     *
     * @param property
     *         property that need to be removed
     */
    void removeProperty(@Nonnull AbstractPropertyPresenter property);

    /** Rotate fold/unfold icon on the view. */
    void rotateIcon();

    /** Reset default view of the fold/unfold icon on the view. */
    void defaultIcon();

    public interface ActionDelegate {
        /** Performs some actions in response to a user's clicking on the main panel of the view. */
        void onItemClicked();
    }

}