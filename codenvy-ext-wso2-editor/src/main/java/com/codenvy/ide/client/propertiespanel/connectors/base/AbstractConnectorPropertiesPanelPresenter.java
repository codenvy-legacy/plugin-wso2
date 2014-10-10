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
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.connectors.AbstractConnector;
import com.codenvy.ide.client.elements.connectors.ConnectorPropertyManager;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.managers.SelectionManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.PropertyPanelFactory;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.client.propertiespanel.connectors.base.parameter.ConnectorParameterCallBack;
import com.codenvy.ide.client.propertiespanel.connectors.base.parameter.ParameterPresenter;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.list.ListPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import javax.annotation.Nonnull;
import java.util.List;

import static com.codenvy.ide.client.elements.AbstractEntityElement.Key;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.AvailableConfigs.SELECT_FROM_CONFIG;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.CONFIG;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.PARAMETER_EDITOR_TYPE;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType;
import static com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter.EditButtonClickedListener;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties. This business logic is general for all groups of connectors.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public abstract class AbstractConnectorPropertiesPanelPresenter<T extends AbstractConnector>
        extends AbstractPropertiesPanel<T> implements ConnectorPropertyManager.ConnectorPropertyListener {

    private final NameSpaceEditorPresenter   nameSpacePresenter;
    private final ParameterPresenter         parameterPresenter;
    private final ConnectorPropertyManager   connectorPropertyManager;
    private final ConnectorParameterCallBack parameterCallBack;
    private final PropertyPanelFactory       propertyPanelFactory;

    protected final WSO2EditorLocalizationConstant locale;

    private PropertyGroupPresenter   basicGroup;
    private SimplePropertyPresenter  configRef;
    private ListPropertyPresenter    availableConfigs;
    private ComplexPropertyPresenter newConfig;
    private ListPropertyPresenter    parameterEditorType;

    protected AbstractConnectorPropertiesPanelPresenter(@Nonnull PropertiesPanelView view,
                                                        @Nonnull ConnectorPropertyManager connectorPropertyManager,
                                                        @Nonnull ParameterPresenter parameterPresenter,
                                                        @Nonnull NameSpaceEditorPresenter nameSpaceEditorPresenter,
                                                        @Nonnull PropertyTypeManager propertyTypeManager,
                                                        @Nonnull WSO2EditorLocalizationConstant localizationConstant,
                                                        @Nonnull PropertyPanelFactory propertyPanelFactory,
                                                        @Nonnull SelectionManager selectionManager) {

        super(view, propertyTypeManager, localizationConstant, propertyPanelFactory, selectionManager);

        this.nameSpacePresenter = nameSpaceEditorPresenter;
        this.parameterPresenter = parameterPresenter;

        locale = localizationConstant;

        this.connectorPropertyManager = connectorPropertyManager;
        this.connectorPropertyManager.addListener(this);

        this.propertyPanelFactory = propertyPanelFactory;

        this.parameterCallBack = new ConnectorParameterCallBack() {
            @Override
            public void onAddressPropertyChanged(@Nonnull String name) {
                AbstractConnectorPropertiesPanelPresenter.this.connectorPropertyManager.addNewConfig(name);
            }
        };

        prepareView();
    }

    private void prepareView() {
        basicGroup = createGroup(locale.miscGroupTitle());

        PropertyValueChangedListener configRefListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(CONFIG, property);

                notifyListeners();
            }
        };
        configRef = createSimpleProperty(basicGroup, locale.connectorConfigRef(), configRefListener);

        PropertyValueChangedListener availableConfigListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                if (!SELECT_FROM_CONFIG.getValue().equals(property)) {
                    configRef.setProperty(property);
                    element.putProperty(CONFIG, property);
                }

                notifyListeners();
            }
        };
        availableConfigs = createListProperty(basicGroup, locale.connectorAvailableConfigs(), availableConfigListener);

        EditButtonClickedListener newConfigListener = new EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                parameterPresenter.showDialog(parameterCallBack);
            }
        };
        newConfig = createComplexProperty(basicGroup, locale.connectorNewConfig(), newConfigListener);

        PropertyValueChangedListener parameterEditTypeListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(PARAMETER_EDITOR_TYPE, ParameterEditorType.getItemByValue(property));

                redrawPropertiesPanel();

                notifyListeners();
            }
        };
        parameterEditorType = createListProperty(basicGroup, locale.connectorParameterEditorType(), parameterEditTypeListener);
    }

    /**
     * Creates simple property panel.Created panel contains listener which allows react to change of panel parameters
     * and set it to element. Also method adds current property panel, which it creates, to general group presenter.
     *
     * @param title
     *         value which need to set as a title of panel
     * @param key
     *         value of key which allows us to get inline parameter of element
     */
    protected SimplePropertyPresenter createSimpleConnectorProperty(@Nonnull String title, @Nonnull final Key<String> key) {
        final SimplePropertyPresenter simplePanel = propertyPanelFactory.createSimplePanelWithoutListener(title);

        PropertyValueChangedListener listener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(key, property);

                simplePanel.setProperty(property);

                notifyListeners();
            }
        };
        simplePanel.addPropertyValueChangedListener(listener);
        basicGroup.addItem(simplePanel);

        return simplePanel;
    }

    /**
     * Creates complex property panel.Created panel contains listener which allows react to change of panel parameters
     * and set it to element. Also method adds current property panel, which it creates, to general group presenter. Methods
     * contains initialization of callback which need to complex panel which will be created.
     *
     * @param title
     *         value which need to set as a title of panel
     * @param nameSpaceKey
     *         value of key which allows us to get list of name space of element
     * @param expressionKey
     *         value of key which allows us to get expression parameter of element
     */
    protected ComplexPropertyPresenter createComplexConnectorProperty(@Nonnull String title,
                                                                      @Nonnull final Key<List<NameSpace>> nameSpaceKey,
                                                                      @Nonnull final Key<String> expressionKey) {

        final ComplexPropertyPresenter complexPanel = propertyPanelFactory.createComplexPanelWithoutListener(title);

        final AddNameSpacesCallBack nameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.putProperty(nameSpaceKey, nameSpaces);
                element.putProperty(expressionKey, expression);

                complexPanel.setProperty(expression);

                notifyListeners();
            }
        };

        complexPanel.addEditButtonClickedListener(new EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                List<NameSpace> nameSpaces = element.getProperty(nameSpaceKey);
                String expression = element.getProperty(expressionKey);

                if (nameSpaces == null || expression == null) {
                    return;
                }

                nameSpacePresenter.showWindowWithParameters(nameSpaces,
                                                            nameSpacesCallBack,
                                                            locale.connectorExpression(),
                                                            expression);
            }
        });
        basicGroup.addItem(complexPanel);

        return complexPanel;
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

        newConfig.setProperty(element.getProperty(CONFIG));

        ParameterEditorType parameterEditor = element.getProperty(PARAMETER_EDITOR_TYPE);
        if (parameterEditor != null) {
            parameterEditorType.selectValue(parameterEditor.getValue());
        }

        redrawPropertiesPanel();
    }

}