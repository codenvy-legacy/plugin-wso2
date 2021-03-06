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
package com.codenvy.ide.client.propertiespanel;

import com.codenvy.ide.api.mvp.View;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;

/**
 * The general representation of the graphical part of a properties panel.
 *
 * @author Andrey Plotnikov
 */
@ImplementedBy(PropertiesPanelViewImpl.class)
public interface PropertiesPanelView extends View<PropertiesPanelView.ActionDelegate> {

    /**
     * Adds a property group on the view.
     *
     * @param propertyGroup
     *         property group that needs to be added
     */
    void addGroup(@Nonnull PropertyGroupPresenter propertyGroup);

    public interface ActionDelegate {
        /** Performs some actions in response to a user's clicking on the main panel of the view. */
        void onMainPanelClicked();
    }

}