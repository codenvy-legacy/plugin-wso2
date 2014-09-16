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
package com.codenvy.ide.client.propertiespanel.property.simple;

import com.codenvy.ide.client.propertiespanel.property.AbstractPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * The class that provides the business logic of the simple property. The simple property means that it has an ability to change the
 * property value from simple field like TextBox.
 *
 * @author Andrey Plotnikov
 */
public class SimplePropertyPresenter extends AbstractPropertyPresenter<SimplePropertyView> implements SimplePropertyView.ActionDelegate {

    private final List<PropertyValueChangedListener> listeners;

    @Inject
    public SimplePropertyPresenter(SimplePropertyView view) {
        super(view);

        listeners = new ArrayList<>();
    }

    /** {@inheritDoc} */
    @Override
    public void setTitle(@Nonnull String title) {
        view.setTitle(title);
    }

    /**
     * Changes property value on the view.
     *
     * @param property
     *         new property value that need to be set
     */
    public void setProperty(@Nonnull String property) {
        view.setProperty(property);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisible(boolean visible) {
        view.setVisible(visible);
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