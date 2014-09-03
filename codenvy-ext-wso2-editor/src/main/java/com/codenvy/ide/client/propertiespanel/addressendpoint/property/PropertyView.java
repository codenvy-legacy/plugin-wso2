/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.client.propertiespanel.addressendpoint.property;

import com.codenvy.ide.client.elements.endpoints.addressendpoint.Property;
import com.codenvy.ide.client.mvp.View;
import com.codenvy.ide.collections.Array;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;

/**
 * The abstract view's representation of the graphical part that provides an ability to show and edit 'Properties' property of 'Address'
 * endpoint.
 *
 * @author Andrey Plotnikov
 */
@ImplementedBy(PropertyViewImpl.class)
public interface PropertyView extends View<PropertyView.ActionDelegate> {

    /**
     * Set the complete list of values to display in the special place on the view.
     *
     * @param properties
     *         list which need to be set
     */
    void setProperties(@Nonnull Array<Property> properties);

    /** Displays the view. */
    void showDialog();

    /** Hides the view. */
    void hideDialog();

    /** Shows message which contain info about error. */
    void showErrorMessage();

    /**
     * Interface defines methods of {@link PropertyPresenter} presenter which calls from view. These methods defines
     * some actions when user click the button of dialog view for editing properties of address endpoint.
     */
    interface ActionDelegate {

        /** Performs any actions appropriate in response to the user having pressed the Ok button. */
        void onOkButtonClicked();

        /** Performs any actions appropriate in response to the user having pressed the Cancel button. */
        void onCancelButtonClicked();

        /** Performs any actions appropriate in response to the user having pressed the Add button. */
        void onAddButtonClicked();

        /** Performs any actions appropriate in response to the user having pressed the Edit button. */
        void onEditButtonClicked();

        /** Performs any actions appropriate in response to the user having pressed the Remove button. */
        void onRemoveButtonClicked();

        /**
         * Performs any actions appropriate in response to the user selected the property.
         *
         * @param property
         *         selected property
         */
        void onPropertySelected(@Nonnull Property property);

    }

}