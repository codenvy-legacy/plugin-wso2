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
package com.codenvy.ide.client.propertiespanel.property.complex;

import com.codenvy.ide.client.propertiespanel.property.general.AbstractPropertyPresenter;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * The class that provides the business logic of complex property. The complex property means that property has an ability to be changed
 * from some additional menu. For this use case the property needs some place where it can show its value and a button for opening the
 * additional menu. In response to these requirements the class provides an ability to detect the moment when the button is clicked. It
 * notifies all listeners about clicking on the button.
 *
 * @author Andrey Plotnikov
 */
public class ComplexPropertyPresenter extends AbstractPropertyPresenter<ComplexPropertyView> implements ComplexPropertyView.ActionDelegate {

    private final List<EditButtonClickedListener> listeners;

    @Inject
    public ComplexPropertyPresenter(ComplexPropertyView view) {
        super(view);

        listeners = new ArrayList<>();
    }

    /**
     * Changes property value on the view.
     *
     * @param property
     *         new property value that need to be set
     */
    public void setProperty(@Nullable String property) {
        view.setProperty(property);
    }

    /** {@inheritDoc} */
    @Override
    public void onEditButtonClicked() {
        notifyEditButtonClickedListeners();
    }

    /**
     * Adds a new listener for detecting the moment when the edit button is clicked.
     *
     * @param listener
     *         listen that need to be added
     */
    public void addEditButtonClickedListener(@Nonnull EditButtonClickedListener listener) {
        listeners.add(listener);
    }

    /** Notify all listeners about clicking the edit button. */
    public void notifyEditButtonClickedListeners() {
        for (EditButtonClickedListener listener : listeners) {
            listener.onEditButtonClicked();
        }
    }

    public interface EditButtonClickedListener {
        void onEditButtonClicked();
    }

}