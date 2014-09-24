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
package com.codenvy.ide.client.propertiespanel.connectors.base;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.connectors.AbstractConnector;
import com.codenvy.ide.client.elements.connectors.ConnectorPropertyManager;
import com.codenvy.ide.client.inject.factories.PropertiesPanelWidgetFactory;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.connectors.base.parameter.ConnectorParameterCallBack;
import com.codenvy.ide.client.propertiespanel.connectors.base.parameter.ParameterPresenter;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.list.ListPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Provider;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.AvailableConfigs.SELECT_FROM_CONFIG;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.CONFIG;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.PARAMETER_EDITOR_TYPE;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties. This business logic is general for all groups of connectors.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public abstract class AbstractConnectorPropertiesPanelPresenter<T extends AbstractConnector>
        extends AbstractPropertiesPanel<T, PropertiesPanelView> implements ConnectorPropertyManager.ConnectorPropertyListener {

    protected final PropertiesPanelWidgetFactory       propertiesWidgetFactory;
    protected final Provider<ListPropertyPresenter>    listPropertyProvider;
    protected final Provider<SimplePropertyPresenter>  simplePropertyProvider;
    protected final Provider<ComplexPropertyPresenter> complexPropertyProvider;
    protected final WSO2EditorLocalizationConstant     locale;

    private final ParameterPresenter       parameterPresenter;
    private final ConnectorPropertyManager connectorPropertyManager;

    private final ConnectorParameterCallBack parameterCallBack;

    private SimplePropertyPresenter configRef;
    private ListPropertyPresenter   availableConfigs;
    private ListPropertyPresenter   parameterEditorType;


    protected AbstractConnectorPropertiesPanelPresenter(@Nonnull PropertiesPanelView view,
                                                        @Nonnull ConnectorPropertyManager connectorPropertyManager,
                                                        @Nonnull ParameterPresenter parameterPresenter,
                                                        @Nonnull PropertyTypeManager propertyTypeManager,
                                                        @Nonnull WSO2EditorLocalizationConstant localizationConstant,
                                                        @Nonnull PropertiesPanelWidgetFactory propertiesPanelWidgetFactory,
                                                        @Nonnull Provider<ListPropertyPresenter> listPropertyPresenterProvider,
                                                        @Nonnull Provider<ComplexPropertyPresenter> complexPropertyPresenterProvider,
                                                        @Nonnull Provider<SimplePropertyPresenter> simplePropertyPresenterProvider) {
        super(view, propertyTypeManager);

        propertiesWidgetFactory = propertiesPanelWidgetFactory;
        listPropertyProvider = listPropertyPresenterProvider;
        complexPropertyProvider = complexPropertyPresenterProvider;
        simplePropertyProvider = simplePropertyPresenterProvider;
        locale = localizationConstant;

        this.connectorPropertyManager = connectorPropertyManager;
        this.connectorPropertyManager.addListener(this);

        this.parameterPresenter = parameterPresenter;

        this.parameterCallBack = new ConnectorParameterCallBack() {
            @Override
            public void onAddressPropertyChanged(@Nonnull String name) {
                AbstractConnectorPropertiesPanelPresenter.this.connectorPropertyManager.addNewConfig(name);
            }
        };

        prepareView();
    }

    private void prepareView() {
        PropertyGroupPresenter basicGroup = propertiesWidgetFactory.createPropertyGroupPresenter(locale.miscGroupTitle());
        view.addGroup(basicGroup);

        configRef = simplePropertyProvider.get();
        configRef.setTitle(locale.connectorConfigRef());
        configRef.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(CONFIG, property);

                notifyListeners();
            }
        });

        basicGroup.addItem(configRef);

        availableConfigs = listPropertyProvider.get();
        availableConfigs.setTitle(locale.connectorAvailableConfigs());
        availableConfigs.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                if (!SELECT_FROM_CONFIG.getValue().equals(property)) {
                    configRef.setProperty(property);
                    element.putProperty(CONFIG, property);
                }

                notifyListeners();
            }
        });

        basicGroup.addItem(availableConfigs);

        ComplexPropertyPresenter newConfig = complexPropertyProvider.get();
        newConfig.setTitle(locale.connectorNewConfig());
        newConfig.addEditButtonClickedListener(new ComplexPropertyPresenter.EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                parameterPresenter.showDialog(parameterCallBack);
            }
        });

        basicGroup.addItem(newConfig);

        parameterEditorType = listPropertyProvider.get();
        parameterEditorType.setTitle(locale.connectorParameterEditorType());
        parameterEditorType.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(PARAMETER_EDITOR_TYPE, ParameterEditorType.valueOf(property));

                notifyListeners();
            }
        });

        basicGroup.addItem(parameterEditorType);
    }

    /** {@inheritDoc} */
    @Override
    public void onGeneralPropertyChanged(@Nonnull String property) {
        availableConfigs.addValue(property);
    }

    /** Redraw properties panel of connector depending on user's action. */
    protected void redrawPropertiesPanel() {
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        configRef.setProperty(element.getProperty(CONFIG));
        availableConfigs.setValues(connectorPropertyManager.getAvailableConfigs());
        parameterEditorType.setValues(propertyTypeManager.getValuesByName(ParameterEditorType.TYPE_NAME));

        ParameterEditorType parameterEditor = element.getProperty(PARAMETER_EDITOR_TYPE);
        if (parameterEditor != null) {
            parameterEditorType.selectValue(parameterEditor.getValue());
        }

        redrawPropertiesPanel();
    }

}