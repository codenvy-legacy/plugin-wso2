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

import com.codenvy.ide.client.inject.factories.PropertiesGroupFactory;
import com.codenvy.ide.client.mvp.AbstractPresenter;
import com.codenvy.ide.client.propertiespanel.property.AbstractPropertyPresenter;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javax.annotation.Nonnull;

/**
 * The class that provides the business logic of the property group. It provides an ability to add a new property to this group.
 *
 * @author Andrey Plotnikov
 */
public class PropertyGroupPresenter extends AbstractPresenter<PropertyGroupView> implements PropertyGroupView.ActionDelegate {

    private boolean isFolded;

    @Inject
    public PropertyGroupPresenter(PropertiesGroupFactory propertiesGroupFactory, @Assisted String title) {
        super(propertiesGroupFactory.createPropertyGroupView(title));

        fold();
    }

    /** @return the GWT widget that is controlled by the presenter */
    @Nonnull
    public IsWidget getView() {
        return view;
    }

    /** The method folds group of parameters which it contains. */
    public void fold() {
        isFolded = true;
        view.setVisibleItemsPanel(false);
        view.defaultIcon();
    }

    /** The method display group of parameters which it contains. */
    public void unfold() {
        isFolded = false;
        view.setVisibleItemsPanel(true);
        view.rotateIcon();
    }

    /**
     * The method calls special method on view which set visibility of group's title part.
     *
     * @param isVisible
     *         <code>true</code> title is visible,<code>false</code> title is invisible
     */
    public void setTitleVisible(boolean isVisible) {
        view.setTitleVisible(isVisible);
    }

    /**
     * Adds a new property to this group.
     *
     * @param property
     *         property that needs to be added
     */
    public void addItem(@Nonnull AbstractPropertyPresenter property) {
        view.addProperty(property);
    }

    /**
     * Removes a property from this group.
     *
     * @param property
     *         property that needs to be removed
     */
    public void removeItem(@Nonnull AbstractPropertyPresenter property) {
        view.removeProperty(property);
    }

    /** {@inheritDoc} */
    @Override
    public void onItemClicked() {
        if (isFolded) {
            unfold();
        } else {
            fold();
        }
    }

}