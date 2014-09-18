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

import com.codenvy.ide.client.elements.Element;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.mvp.AbstractPresenter;
import com.codenvy.ide.client.mvp.AbstractView;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * The general presentation of properties panel.
 *
 * @param <T>
 *         type of diagram element that this panel supports
 * @param <V>
 *         type of view that is needed for this panel
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public abstract class AbstractPropertiesPanel<T extends Element, V extends AbstractView> extends AbstractPresenter<V> {

    protected     T                             element;
    protected     PropertyTypeManager           propertyTypeManager;
    private final List<PropertyChangedListener> listeners;

    protected AbstractPropertiesPanel(@Nonnull V view, @Nonnull PropertyTypeManager propertyTypeManager) {
        super(view);

        listeners = new ArrayList<>();
        this.propertyTypeManager = propertyTypeManager;
    }

    /**
     * Set diagram element for showing its properties.
     *
     * @param element
     *         element which properties need to be shown
     */
    public void setElement(@Nonnull T element) {
        this.element = element;
    }

    /**
     * Adds a listener to list of listeners of property panel.
     *
     * @param listener
     *         listener which needs to be added
     */
    public void addListener(@Nonnull PropertyChangedListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a listener from list of listeners of property panel.
     *
     * @param listener
     *         listener which needs to removed
     */
    public void removeListener(@Nonnull PropertyChangedListener listener) {
        listeners.remove(listener);
    }

    /** Notify all listeners of property panel when some element on property panel was changed. */
    public void notifyListeners() {
        for (PropertyChangedListener listener : listeners) {
            listener.onPropertyChanged();
        }
    }

}