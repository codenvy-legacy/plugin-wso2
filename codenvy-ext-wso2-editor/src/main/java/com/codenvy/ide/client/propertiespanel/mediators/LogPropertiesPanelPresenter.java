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
package com.codenvy.ide.client.propertiespanel.mediators;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.mediators.log.Log;
import com.codenvy.ide.client.elements.mediators.log.Property;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.PropertyPanelFactory;
import com.codenvy.ide.client.propertiespanel.common.namespace.AddPropertyCallback;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.PropertyConfigPresenter;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.list.ListPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.log.Log.DESCRIPTION;
import static com.codenvy.ide.client.elements.mediators.log.Log.LOG_CATEGORY;
import static com.codenvy.ide.client.elements.mediators.log.Log.LOG_LEVEL;
import static com.codenvy.ide.client.elements.mediators.log.Log.LOG_PROPERTIES;
import static com.codenvy.ide.client.elements.mediators.log.Log.LOG_SEPARATOR;
import static com.codenvy.ide.client.elements.mediators.log.Log.LogCategory;
import static com.codenvy.ide.client.elements.mediators.log.Log.LogLevel;
import static com.codenvy.ide.client.elements.mediators.log.Property.NAME;
import static com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter.EditButtonClickedListener;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Log mediator
 * depending on user's changes of logProperties.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class LogPropertiesPanelPresenter extends AbstractPropertiesPanel<Log> {

    private final PropertyConfigPresenter propertyConfigPresenter;
    private final AddPropertyCallback     addPropertyCallback;

    private ListPropertyPresenter    category;
    private ListPropertyPresenter    level;
    private SimplePropertyPresenter  separator;
    private ComplexPropertyPresenter logProperties;
    private SimplePropertyPresenter  description;

    @Inject
    public LogPropertiesPanelPresenter(PropertiesPanelView view,
                                       PropertyTypeManager propertyTypeManager,
                                       PropertyConfigPresenter propertyConfigPresenter,
                                       WSO2EditorLocalizationConstant locale,
                                       PropertyPanelFactory propertyPanelFactory) {

        super(view, propertyTypeManager, locale, propertyPanelFactory);

        this.propertyConfigPresenter = propertyConfigPresenter;

        this.addPropertyCallback = new AddPropertyCallback() {
            @Override
            public void onPropertiesChanged(@Nonnull List<Property> properties) {
                element.putProperty(LOG_PROPERTIES, properties);

                showProperties(properties);

                notifyListeners();
            }
        };

        prepareView();
    }

    private void prepareView() {
        PropertyGroupPresenter basicGroup = createGroup(locale.miscGroupTitle());

        PropertyValueChangedListener categoryListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(LOG_CATEGORY, LogCategory.valueOf(property));

                notifyListeners();
            }
        };
        category = createListProperty(basicGroup, locale.logCategory(), categoryListener);

        PropertyValueChangedListener levelListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(LOG_LEVEL, LogLevel.valueOf(property));

                notifyListeners();
            }
        };
        level = createListProperty(basicGroup, locale.logLevel(), levelListener);

        PropertyValueChangedListener separatorListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(LOG_SEPARATOR, property);

                notifyListeners();
            }
        };
        separator = createSimpleProperty(basicGroup, locale.logSeparator(), separatorListener);

        EditButtonClickedListener propertiesBtnListener = new EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                List<Property> properties = element.getProperty(LOG_PROPERTIES);

                if (properties != null) {
                    propertyConfigPresenter.showConfigWindow(properties, locale.properties(), addPropertyCallback);
                }
            }
        };
        logProperties = createComplexProperty(basicGroup, locale.properties(), propertiesBtnListener);

        PropertyValueChangedListener descriptionListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(DESCRIPTION, property);

                notifyListeners();
            }
        };
        description = createSimpleProperty(basicGroup, locale.description(), descriptionListener);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        displayCategoryParameter();

        displayLevelValueParameter();

        separator.setProperty(element.getProperty(LOG_SEPARATOR));

        description.setProperty(element.getProperty(DESCRIPTION));
    }

    private void displayCategoryParameter() {
        category.setValues(propertyTypeManager.getValuesByName(LogCategory.TYPE_NAME));
        LogCategory categoryValue = element.getProperty(LOG_CATEGORY);

        if (categoryValue == null) {
            return;
        }

        category.selectValue(categoryValue.name());

    }

    private void displayLevelValueParameter() {
        level.setValues(propertyTypeManager.getValuesByName(LogLevel.TYPE_NAME));
        LogLevel levelValue = element.getProperty(LOG_LEVEL);

        if (levelValue == null) {
            return;
        }

        level.selectValue(levelValue.name());
    }

    /**
     * Shows value of logProperties in special place on the view.
     *
     * @param properties
     *         list of logProperties which must to be displayed
     */
    private void showProperties(@Nullable List<Property> properties) {
        if (properties == null) {
            logProperties.setProperty("");
            return;
        }

        StringBuilder content = new StringBuilder();
        int size = properties.size() - 1;

        for (int i = 0; i <= size; i++) {
            Property property = properties.get(i);

            content.append(property.getProperty(NAME));
            content.append(i != size ? ", " : "");
        }

        logProperties.setProperty(content.toString());
    }

}