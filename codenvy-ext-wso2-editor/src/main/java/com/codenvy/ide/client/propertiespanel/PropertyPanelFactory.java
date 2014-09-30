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

import com.codenvy.ide.client.inject.factories.PropertiesGroupFactory;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.list.ListPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter.EditButtonClickedListener;

/**
 * The class contains methods which allows us to create needed type of property panel(simple, list box or complex), and
 * also we can create a group which can contains panels.
 *
 * @author Dmitry Shnurenko
 */
public class PropertyPanelFactory {

    private final PropertiesGroupFactory             propertiesGroupFactory;
    private final Provider<SimplePropertyPresenter>  simplePropertyProvider;
    private final Provider<ListPropertyPresenter>    listPropertyProvider;
    private final Provider<ComplexPropertyPresenter> complexPropertyProvider;

    @Inject
    public PropertyPanelFactory(@Nonnull PropertiesGroupFactory propertiesGroupFactory,
                                @Nonnull Provider<SimplePropertyPresenter> simplePropertyProvider,
                                @Nonnull Provider<ListPropertyPresenter> listPropertyProvider,
                                @Nonnull Provider<ComplexPropertyPresenter> complexPropertyProvider) {

        this.propertiesGroupFactory = propertiesGroupFactory;
        this.simplePropertyProvider = simplePropertyProvider;
        this.listPropertyProvider = listPropertyProvider;
        this.complexPropertyProvider = complexPropertyProvider;
    }

    /**
     * Returns created empty group panel with set point title.
     *
     * @param title
     *         name of panel which need to set
     * @return empty group as a container for panels
     */
    @Nonnull
    public PropertyGroupPresenter createGroupProperty(@Nonnull String title) {
        return propertiesGroupFactory.createPropertyGroupPresenter(title);
    }

    /**
     * Creates simple panel with set point title. The method set transmitted listener to created panel.
     *
     * @param title
     *         title which need set to panel
     * @param listener
     *         listener which allows us to react when user change value of property on panel
     * @return simple property panel
     */
    @Nonnull
    public SimplePropertyPresenter createSimpleProperty(@Nonnull String title, @Nonnull PropertyValueChangedListener listener) {
        SimplePropertyPresenter simplePresenter = simplePropertyProvider.get();
        simplePresenter.setTitle(title);
        simplePresenter.addPropertyValueChangedListener(listener);

        return simplePresenter;
    }

    /**
     * Creates simple panel with set point title.
     *
     * @param title
     *         title which need set to panel
     * @return simple property panel without listener
     */
    @Nonnull
    public SimplePropertyPresenter createSimplePanelWithoutListener(@Nonnull String title) {
        SimplePropertyPresenter simplePresenter = simplePropertyProvider.get();
        simplePresenter.setTitle(title);

        return simplePresenter;
    }

    /**
     * Creates complex panel with set point title. The method set transmitted listener to created panel.
     *
     * @param title
     *         title which need set to panel
     * @param listener
     *         listener which allows us to react when clicked on panel's button
     * @return complex property panel
     */
    public ComplexPropertyPresenter createComplexProperty(@Nonnull String title, @Nonnull EditButtonClickedListener listener) {
        ComplexPropertyPresenter complexPresenter = complexPropertyProvider.get();
        complexPresenter.setTitle(title);
        complexPresenter.addEditButtonClickedListener(listener);

        return complexPresenter;
    }

    /**
     * Creates complex panel with set point title.
     *
     * @param title
     *         title which need set to panel
     * @return complex property panel without listener
     */
    @Nonnull
    public ComplexPropertyPresenter createComplexPanelWithoutListener(@Nonnull String title) {
        ComplexPropertyPresenter complexPresenter = complexPropertyProvider.get();
        complexPresenter.setTitle(title);

        return complexPresenter;
    }

    /**
     * Creates list panel with set point title. The method set transmitted listener to created panel.
     *
     * @param title
     *         title which need set to panel
     * @param listener
     *         listener which allows us to react when user change value of property on panel
     * @return list property panel
     */
    public ListPropertyPresenter createListProperty(@Nonnull String title, @Nonnull PropertyValueChangedListener listener) {
        ListPropertyPresenter listPresenter = listPropertyProvider.get();
        listPresenter.setTitle(title);
        listPresenter.addPropertyValueChangedListener(listener);

        return listPresenter;
    }

}