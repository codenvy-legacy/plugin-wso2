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
import com.codenvy.ide.client.propertiespanel.property.general.AbstractPropertyPresenter;

import javax.annotation.Nonnull;

/**
 * The abstract view that represents the property group visual part of the widget.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public interface PropertyGroupView extends View<PropertyGroupView.ActionDelegate> {

    /**
     * Changes visible state of the top border.
     *
     * @param visible
     *         <code>true</code> the border will be shown, <code>false</code> it will not
     */
    void setBorderVisible(boolean visible);

    /** Changes state of property groups. When user clicks on group panel it expands. */
    void expendPropertyGroup();

    /** Changes state of property groups. When user clicks on group panel it collapses. */
    void collapsePropertyGroup();

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

    /**
     * Sets visibility part of group which contains title and icon.
     *
     * @param isVisible
     *         value of visibility which need to set.<code>true</code> part of group is visible, <code>false</code> part of group invisible
     */
    void setTitleVisible(boolean isVisible);

    public interface ActionDelegate {
        /** Calls method on view which expand or collapse property group related to current state */
        void onPropertyGroupClicked();
    }

}