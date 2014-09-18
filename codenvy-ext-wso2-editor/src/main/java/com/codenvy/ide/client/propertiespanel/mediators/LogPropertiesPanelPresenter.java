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
import com.codenvy.ide.client.inject.factories.PropertiesPanelWidgetFactory;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.common.namespace.AddPropertyCallback;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.PropertyConfigPresenter;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.list.ListPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.codenvy.ide.client.elements.mediators.log.Log.DESCRIPTION;
import static com.codenvy.ide.client.elements.mediators.log.Log.LOG_CATEGORY;
import static com.codenvy.ide.client.elements.mediators.log.Log.LOG_LEVEL;
import static com.codenvy.ide.client.elements.mediators.log.Log.LOG_PROPERTIES;
import static com.codenvy.ide.client.elements.mediators.log.Log.LOG_SEPARATOR;
import static com.codenvy.ide.client.elements.mediators.log.Log.LogCategory;
import static com.codenvy.ide.client.elements.mediators.log.Log.LogLevel;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Log mediator
 * depending on user's changes of logProperties.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class LogPropertiesPanelPresenter extends AbstractPropertiesPanel<Log, PropertiesPanelView> {

    private final PropertyConfigPresenter        propertyConfigPresenter;
    private final AddPropertyCallback            addPropertyCallback;
    private final WSO2EditorLocalizationConstant locale;

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
                                       PropertiesPanelWidgetFactory propertiesPanelWidgetFactory,
                                       Provider<SimplePropertyPresenter> simplePropertyPresenterProvider,
                                       Provider<ListPropertyPresenter> listPropertyPresenterProvider,
                                       Provider<ComplexPropertyPresenter> complexPropertyPresenterProvider) {
        super(view, propertyTypeManager);

        this.propertyConfigPresenter = propertyConfigPresenter;
        this.locale = locale;

        this.addPropertyCallback = new AddPropertyCallback() {
            @Override
            public void onPropertiesChanged(@Nonnull Array<Property> properties) {
                element.putProperty(LOG_PROPERTIES, properties);

                notifyListeners();
            }
        };

        prepareView(propertiesPanelWidgetFactory,
                    simplePropertyPresenterProvider,
                    listPropertyPresenterProvider,
                    complexPropertyPresenterProvider);
    }

    private void prepareView(PropertiesPanelWidgetFactory propertiesPanelWidgetFactory,
                             Provider<SimplePropertyPresenter> simplePropertyPresenterProvider,
                             Provider<ListPropertyPresenter> listPropertyPresenterProvider,
                             Provider<ComplexPropertyPresenter> complexPropertyPresenterProvider) {

        PropertyGroupPresenter basicGroup = propertiesPanelWidgetFactory.createPropertyGroupPresenter(locale.miscGroupTitle());
        view.addGroup(basicGroup);

        category = listPropertyPresenterProvider.get();
        category.setTitle(locale.logCategory());
        category.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(LOG_CATEGORY, LogCategory.valueOf(property));

                notifyListeners();
            }
        });
        basicGroup.addItem(category);

        level = listPropertyPresenterProvider.get();
        level.setTitle(locale.logLevel());
        level.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(LOG_LEVEL, LogLevel.valueOf(property));

                notifyListeners();
            }
        });
        basicGroup.addItem(level);

        separator = simplePropertyPresenterProvider.get();
        separator.setTitle(locale.logSeparator());
        separator.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(LOG_SEPARATOR, property);

                notifyListeners();
            }
        });
        basicGroup.addItem(separator);

        logProperties = complexPropertyPresenterProvider.get();
        logProperties.setTitle(locale.properties());
        logProperties.addEditButtonClickedListener(new ComplexPropertyPresenter.EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                Array<Property> properties = element.getProperty(LOG_PROPERTIES);

                if (properties != null) {
                    propertyConfigPresenter.showConfigWindow(properties, locale.properties(), addPropertyCallback);
                }
            }
        });
        basicGroup.addItem(logProperties);

        description = simplePropertyPresenterProvider.get();
        description.setTitle(locale.description());
        description.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(DESCRIPTION, property);

                notifyListeners();
            }
        });
        basicGroup.addItem(description);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        category.setValues(propertyTypeManager.getValuesByName(LogCategory.TYPE_NAME));
        LogCategory category = element.getProperty(LOG_CATEGORY);

        if (category != null) {
            this.category.selectValue(category.name());
        }

        level.setValues(propertyTypeManager.getValuesByName(LogLevel.TYPE_NAME));
        LogLevel level = element.getProperty(LOG_LEVEL);

        if (level != null) {
            this.level.selectValue(level.name());
        }

        separator.setProperty(element.getProperty(LOG_SEPARATOR));

        showProperties(element.getProperty(LOG_PROPERTIES));

        description.setProperty(element.getProperty(DESCRIPTION));
    }

    /**
     * Shows value of logProperties in special place on the view.
     *
     * @param properties
     *         list of logProperties which must to be displayed
     */
    private void showProperties(@Nullable Array<Property> properties) {
        if (properties == null) {
            this.logProperties.setProperty("");
            return;
        }

        StringBuilder content = new StringBuilder();
        int size = properties.size() - 1;

        for (int i = 0; i <= size; i++) {
            Property property = properties.get(i);

            content.append(property.getName());

            if (i != size) {
                content.append(", ");
            }
        }

        this.logProperties.setProperty(content.toString());
    }

}