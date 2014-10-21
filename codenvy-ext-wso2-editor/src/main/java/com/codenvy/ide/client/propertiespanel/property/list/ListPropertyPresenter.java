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

import com.codenvy.ide.client.propertiespanel.property.general.AbstractPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * The class that provides the business logic of the list property. The list property means that it is the property that has a list of
 * available values and select some value from the list. Also the class provides an ability to detect the moment when property is changed.
 * The class notifies all listeners about changing property value.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class ListPropertyPresenter extends AbstractPropertyPresenter<ListPropertyView> implements ListPropertyView.ActionDelegate {

    private final List<PropertyValueChangedListener> listeners;

    @Inject
    public ListPropertyPresenter(ListPropertyView view) {
        super(view);

        listeners = new ArrayList<>();
    }

    /**
     * Sets a list of available values for a property.
     *
     * @param values
     *         values that need to be added to list on the view
     */
    public void setValues(@Nullable List<String> values) {
        view.setPropertyValues(values);
    }

    /**
     * Selects a value into available values on the view.
     *
     * @param value
     *         value that needs to be selected
     */
    public void selectValue(@Nullable String value) {
        view.selectPropertyValue(value);
    }

    /**
     * Adds available value for a property.
     *
     * @param value
     *         value that needs to be added
     */
    public void addValue(@Nullable String value) {
        view.addPropertyValue(value);
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertyChanged() {
        notifyPropertyValueChangedListener();
    }

    /**
     * Adds a new listener for detecting the moment when property value is changed.
     *
     * @param listener
     *         listen that need to be added
     */
    public void addPropertyValueChangedListener(@Nonnull PropertyValueChangedListener listener) {
        listeners.add(listener);
    }

    /** Notify all listeners about changing property value. */
    public void notifyPropertyValueChangedListener() {
        String property = view.getProperty();

        for (PropertyValueChangedListener listener : listeners) {
            listener.onPropertyChanged(property);
        }
    }

}