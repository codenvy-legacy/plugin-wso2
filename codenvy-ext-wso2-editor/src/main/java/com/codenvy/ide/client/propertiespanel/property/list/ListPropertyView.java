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
package com.codenvy.ide.client.propertiespanel.property.list;

import com.codenvy.ide.client.propertiespanel.property.general.AbstractPropertyView;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * The abstract representation of graphical part of the list property. It provides an ability to change property title and property value.
 * Also set a list of available values for the property and select some value from the list.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
@ImplementedBy(ListPropertyViewImpl.class)
public interface ListPropertyView extends AbstractPropertyView<ListPropertyView.ActionDelegate> {

    /**
     * Adds available value.
     *
     * @param value
     *         value which need to be add
     */
    void addPropertyValue(@Nullable String value);

    /**
     * Sets a list of available values on the view.
     *
     * @param values
     *         values which need to be set
     */
    void setPropertyValues(@Nullable List<String> values);

    /**
     * Selects a value from the list of available values.
     *
     * @param value
     *         value that need to be set
     */
    void selectPropertyValue(@Nullable String value);

    /** @return selected property value */
    @Nonnull
    String getProperty();

    public interface ActionDelegate {
        /** Performs some actions in response to a user's changing property value. */
        void onPropertyChanged();
    }

}