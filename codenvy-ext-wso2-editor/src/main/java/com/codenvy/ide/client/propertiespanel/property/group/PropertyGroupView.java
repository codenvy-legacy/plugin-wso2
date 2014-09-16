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

import com.codenvy.ide.client.mvp.AbstractView;
import com.codenvy.ide.client.propertiespanel.property.AbstractPropertyPresenter;

import javax.annotation.Nonnull;

/**
 * The abstract view that represents the property group visual part of the widget.
 *
 * @author Andrey Plotnikov
 */
public abstract class PropertyGroupView extends AbstractView<PropertyGroupView.ActionDelegate> {

    /**
     * Changes visible state of item's panel of the view.
     *
     * @param visible
     *         <code>true</code> the panel will be shown, <code>false</code> it will not
     */
    public abstract void setVisibleItemsPanel(boolean visible);

    /**
     * Adds a property widget on the view.
     *
     * @param property
     *         property that need to be added
     */
    public abstract void addProperty(@Nonnull AbstractPropertyPresenter property);

    /** Rotate fold/unfold icon on the view. */
    public abstract void rotateIcon();

    /** Reset default view of the fold/unfold icon on the view. */
    public abstract void defaultIcon();

    public interface ActionDelegate extends AbstractView.ActionDelegate {
        /** Performs some actions in response to a user's clicking on the main panel of the view. */
        void onItemClicked();
    }

}