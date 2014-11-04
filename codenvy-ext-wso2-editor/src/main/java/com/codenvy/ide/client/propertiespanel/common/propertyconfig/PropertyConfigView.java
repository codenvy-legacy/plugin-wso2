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
package com.codenvy.ide.client.propertiespanel.common.propertyconfig;

import com.codenvy.ide.api.mvp.View;
import com.codenvy.ide.client.elements.mediators.log.Property;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * The presentation of Property Config view. It contains a general places for properties of element.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
@ImplementedBy(PropertyConfigViewImpl.class)
public interface PropertyConfigView extends View<PropertyConfigView.ActionDelegate> {

    /** Shows message which contain info about error. */
    void showErrorMessage();

    /**
     * Sets the list to table on view.
     *
     * @param propertyList
     *         list which needs to be displayed
     */
    void setProperties(@Nonnull List<Property> propertyList);

    /**
     * Shows dialog window for editing property of element.
     *
     * @param title
     *         value of title
     */
    void showWindow(@Nonnull String title);

    /** Hides dialog window. */
    void hideWindow();

    /**
     * Interface defines methods of {@link PropertyConfigPresenter} which calls from view. These methods defines
     * some actions when user click the button on dialog window for adding, removing or editing properties.
     */
    interface ActionDelegate {
        /**
         * Performs any actions appropriate in response to the user selected the property.
         *
         * @param property
         *         selected property
         */
        void onSelectedProperty(@Nonnull Property property);

        /** Performs any actions appropriate in response to the user having pressed the Cancel button. */
        void onCancelButtonClicked();

        /** Performs any actions appropriate in response to the user having pressed the Add button. */
        void onAddPropertyButtonClicked();

        /** Performs any actions appropriate in response to the user having pressed the Remove button. */
        void onRemovePropertyButtonClicked();

        /** Performs any actions appropriate in response to the user having pressed the Ok button. */
        void onOkButtonClicked();

        /** Performs any actions appropriate in response to the user having pressed the Edit button. */
        void onEditButtonClicked();
    }

}
