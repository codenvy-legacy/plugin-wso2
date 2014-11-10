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
package com.codenvy.ide.client.managers;

import com.codenvy.ide.client.elements.Element;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertyChangedListener;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The manager of properties panel. It provides an ability to show a needed properties panel in response to context (current selected
 * diagram element).
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
@Singleton
public class PropertiesPanelManager implements SelectionManager.SelectionStateListener, PropertyChangedListener {

    private final Map<Class<?>, AbstractPropertiesPanel<? extends Element>> panels;
    private final List<PropertyChangedListener>                             listeners;

    private AcceptsOneWidget        container;
    private AbstractPropertiesPanel shownPanel;

    @Inject
    public PropertiesPanelManager(SelectionManager selectionManager) {
        this.panels = new HashMap<>();
        this.listeners = new ArrayList<>();

        selectionManager.addListener(this);
    }

    /**
     * Changes container of properties panel.
     *
     * @param container
     *         container which needs to be used
     */
    public void setContainer(@Nonnull AcceptsOneWidget container) {
        this.container = container;
    }

    /**
     * Register a new properties panel. In case a given kind of diagram element is selected a given properties panel will be shown.
     *
     * @param diagramElement
     *         a class of diagram element that have own properties panel
     * @param panel
     *         a panel that need to be mapped to a given diagram element
     * @param <T>
     *         type of diagram element
     */
    public <T extends Element> void register(@Nullable Class<T> diagramElement,
                                             @Nonnull AbstractPropertiesPanel<T> panel) {
        panels.put(diagramElement, panel);
    }

    /**
     * Show properties panel for a given diagram element.
     *
     * @param element
     *         diagram element that need to be shown in a special properties panel
     * @param <T>
     *         type of diagram element
     */
    public <T extends Element> void show(@Nullable T element) {
        if (shownPanel != null) {
            shownPanel.removeListener(this);
        }

        AbstractPropertiesPanel value = panels.get(element == null ? null : element.getClass());
        shownPanel = value;

        if (value != null) {
            if (element != null) {
                //noinspection unchecked
                value.setElement(element);
            }

            value.go(container);
            value.addListener(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onStateChanged(@Nullable Element element) {
        show(element);
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertyChanged() {
        notifyPropertyChangedListeners();
    }

    public void addPropertyChangedListener(@Nonnull PropertyChangedListener listener) {
        listeners.add(listener);
    }

    public void notifyPropertyChangedListeners() {
        for (PropertyChangedListener listener : listeners) {
            listener.onPropertyChanged();
        }
    }

}