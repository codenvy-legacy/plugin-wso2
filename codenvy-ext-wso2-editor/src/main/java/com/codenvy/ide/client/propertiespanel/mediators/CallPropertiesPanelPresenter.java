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
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.mediators.Call;
import com.codenvy.ide.client.elements.mediators.Switch;
import com.codenvy.ide.client.inject.factories.PropertiesPanelWidgetFactory;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
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

import static com.codenvy.ide.client.elements.mediators.Call.DESCRIPTION;
import static com.codenvy.ide.client.elements.mediators.Call.ENDPOINT_TYPE;
import static com.codenvy.ide.client.elements.mediators.Call.EndpointType;
import static com.codenvy.ide.client.elements.mediators.Call.NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.Call.REGISTRY_KEY;
import static com.codenvy.ide.client.elements.mediators.Call.X_PATH;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Call mediator
 * depending on user's changes of properties.
 *
 * @author Valeriy Svydenko
 */
public class CallPropertiesPanelPresenter extends AbstractPropertiesPanel<Call, PropertiesPanelView> {

    private final NameSpaceEditorPresenter       nameSpaceEditorPresenter;
    private final WSO2EditorLocalizationConstant locale;

    private ListPropertyPresenter    endpointType;
    private SimplePropertyPresenter  endpointRegistryKeyPanel;
    private ComplexPropertyPresenter endpointXpathPanel;
    private SimplePropertyPresenter  description;

    @Inject
    public CallPropertiesPanelPresenter(PropertiesPanelView view,
                                        PropertyTypeManager propertyTypeManager,
                                        NameSpaceEditorPresenter nameSpaceEditorPresenter,
                                        WSO2EditorLocalizationConstant localizationConstant,
                                        PropertiesPanelWidgetFactory propertiesPanelWidgetFactory,
                                        final ComplexPropertyPresenter endpointXpathPanel,
                                        Provider<SimplePropertyPresenter> simplePropertyPresenterProvider,
                                        final ListPropertyPresenter endpointType) {
        super(view, propertyTypeManager);

        this.nameSpaceEditorPresenter = nameSpaceEditorPresenter;
        this.locale = localizationConstant;

        prepareView(propertiesPanelWidgetFactory,
                    simplePropertyPresenterProvider,
                    endpointType,
                    endpointXpathPanel);
    }

    private void prepareView(PropertiesPanelWidgetFactory propertiesPanelWidgetFactory,
                             Provider<SimplePropertyPresenter> simplePropertyPresenterProvider,
                             ListPropertyPresenter endpointType,
                             final ComplexPropertyPresenter endpointXpathPanel) {

        PropertyGroupPresenter basicGroup = propertiesPanelWidgetFactory.createPropertyGroupPresenter(locale.miscGroupTitle());
        this.view.addGroup(basicGroup);

        this.endpointType = endpointType;
        this.endpointType.setTitle(locale.endpointType());
        this.endpointType.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                redesignViewToEndpointType(property);

                notifyListeners();
            }
        });
        basicGroup.addItem(this.endpointType);

        endpointRegistryKeyPanel = simplePropertyPresenterProvider.get();
        endpointRegistryKeyPanel.setTitle(locale.endpointRegistryKey());
        endpointRegistryKeyPanel.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(REGISTRY_KEY, property);

                notifyListeners();
            }
        });
        basicGroup.addItem(endpointRegistryKeyPanel);

        final AddNameSpacesCallBack addNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nullable String expression) {
                element.putProperty(NAMESPACES, nameSpaces);
                element.putProperty(X_PATH, expression != null ? expression : "");

                endpointXpathPanel.setProperty(expression);

                notifyListeners();
            }
        };

        this.endpointXpathPanel = endpointXpathPanel;
        this.endpointXpathPanel.setTitle(locale.endpointXpath());
        this.endpointXpathPanel.addEditButtonClickedListener(new ComplexPropertyPresenter.EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                Array<NameSpace> nameSpaces = element.getProperty(Switch.NAMESPACES);
                String xPath = element.getProperty(X_PATH);

                if (xPath != null && nameSpaces != null) {
                    nameSpaceEditorPresenter.showWindowWithParameters(nameSpaces,
                                                                      addNameSpacesCallBack,
                                                                      locale.callExpressionTitle(),
                                                                      xPath);
                }
            }
        });
        basicGroup.addItem(this.endpointXpathPanel);

        description = simplePropertyPresenterProvider.get();
        description.setTitle(locale.addressEndpointDescription());
        description.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(DESCRIPTION, property);

                notifyListeners();
            }
        });
        basicGroup.addItem(description);
    }

    /** Modifies the view of the panel depending on the type of endpoint of call element. */
    private void redesignViewToEndpointType(@Nonnull String property) {
        EndpointType newProperty = EndpointType.valueOf(property);
        element.putProperty(ENDPOINT_TYPE, newProperty);

        switch (newProperty) {
            case REGISTRYKEY:
                endpointXpathPanel.setVisible(false);
                endpointRegistryKeyPanel.setVisible(true);

                String rKey = element.getProperty(REGISTRY_KEY);
                endpointRegistryKeyPanel.setProperty(rKey != null ? rKey : "");
                break;

            case XPATH:
                endpointXpathPanel.setVisible(true);
                endpointRegistryKeyPanel.setVisible(false);

                String xPath = element.getProperty(X_PATH);
                endpointXpathPanel.setProperty(xPath);
                break;

            case NONE:
            case INLINE:
            default:
                endpointXpathPanel.setVisible(false);
                endpointRegistryKeyPanel.setVisible(false);
                break;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        endpointType.setValues(propertyTypeManager.getValuesByName(EndpointType.TYPE_NAME));

        EndpointType type = element.getProperty(ENDPOINT_TYPE);
        if (type != null) {
            endpointType.selectValue(type.name());
            redesignViewToEndpointType(type.name());
        }

        String description = element.getProperty(DESCRIPTION);
        this.description.setProperty(description != null ? description : "");
    }

}