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

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.Element;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.mvp.AbstractPresenter;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.list.ListPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter.EditButtonClickedListener;

/**
 * The general presentation of properties panel.
 *
 * @param <T>
 *         type of diagram element that this panel supports
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public abstract class AbstractPropertiesPanel<T extends Element> extends AbstractPresenter<PropertiesPanelView> {

    private final PropertyPanelFactory          propertyPanelFactory;
    private final List<PropertyChangedListener> listeners;
    private final List<PropertyGroupPresenter>  groups;

    protected final PropertyTypeManager            propertyTypeManager;
    protected final WSO2EditorLocalizationConstant locale;

    protected T element;

    protected AbstractPropertiesPanel(@Nonnull PropertiesPanelView view,
                                      @Nonnull PropertyTypeManager propertyTypeManager,
                                      @Nonnull WSO2EditorLocalizationConstant locale,
                                      @Nonnull PropertyPanelFactory propertyPanelFactory) {
        super(view);

        this.propertyPanelFactory = propertyPanelFactory;

        this.locale = locale;
        this.propertyTypeManager = propertyTypeManager;
        this.listeners = new ArrayList<>();
        this.groups = new ArrayList<>();
    }

    /**
     * Creates group for properties panel. Method adds adds created group on view. Also method sets group name.
     *
     * @param title
     *         value which need to set as a title of group
     */
    @Nonnull
    protected PropertyGroupPresenter createGroup(@Nonnull String title) {
        PropertyGroupPresenter basicGroup = propertyPanelFactory.createGroupProperty(title);
        view.addGroup(basicGroup);

        groups.add(basicGroup);

        return basicGroup;
    }

    /**
     * Creates simple property panel. Method adds listener to panel which allows react to change of panel parameters
     * and set it to element. Also method adds property panel, which it creates, to general group presenter.
     *
     * @param group
     *         group which need to contain created panel
     * @param title
     *         value which need to set as a title of panel
     * @param listener
     *         listener which allows to react on user's action
     */
    @Nonnull
    protected SimplePropertyPresenter createSimpleProperty(@Nonnull PropertyGroupPresenter group,
                                                           @Nonnull String title,
                                                           @Nonnull PropertyValueChangedListener listener) {
        SimplePropertyPresenter simplePanel = propertyPanelFactory.createSimpleProperty(title, listener);

        group.addItem(simplePanel);

        return simplePanel;
    }

    /**
     * Creates complex property panel. Method adds listener to panel which allows react to change of panel parameters
     * and set it to element. Also method adds property panel, which it creates, to general group presenter.
     *
     * @param group
     *         group which need to contain created panel
     * @param title
     *         value which need to set as a title of panel
     * @param clickListener
     *         listener which allows to react on user's action
     */
    @Nonnull
    protected ComplexPropertyPresenter createComplexProperty(@Nonnull PropertyGroupPresenter group,
                                                             @Nonnull String title,
                                                             @Nonnull EditButtonClickedListener clickListener) {
        ComplexPropertyPresenter complexPanel = propertyPanelFactory.createComplexProperty(title, clickListener);

        group.addItem(complexPanel);

        return complexPanel;
    }

    /**
     * Creates list property panel. Method adds listener to panel which allows react to change of panel parameters
     * and set it to element. Also method adds property panel, which it creates, to general group presenter.
     *
     * @param group
     *         group which need to contain created panel
     * @param title
     *         value which need to set as a title of panel
     * @param propertyChangedListener
     *         listener which allows to react on user's action
     */
    @Nonnull
    protected ListPropertyPresenter createListProperty(@Nonnull PropertyGroupPresenter group,
                                                       @Nonnull String title,
                                                       @Nonnull PropertyValueChangedListener propertyChangedListener) {
        ListPropertyPresenter listPanel = propertyPanelFactory.createListProperty(title, propertyChangedListener);

        group.addItem(listPanel);

        return listPanel;
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

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        if (groups.size() == 1) {
            openOneGroup();
        } else {
            openAllGroups();
        }
    }

    private void openOneGroup() {
        PropertyGroupPresenter group = groups.get(0);

        group.unfold();
        group.setTitleVisible(false);
    }

    private void openAllGroups() {
        for (PropertyGroupPresenter group : groups) {
            group.unfold();
        }
    }

}