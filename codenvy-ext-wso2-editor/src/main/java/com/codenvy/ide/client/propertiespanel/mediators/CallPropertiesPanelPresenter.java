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
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

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
public class CallPropertiesPanelPresenter extends AbstractPropertiesPanel<Call> {

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
                                        final ListPropertyPresenter endpointTypeListPropertyPresenter) {
        super(view, propertyTypeManager);

        this.nameSpaceEditorPresenter = nameSpaceEditorPresenter;
        this.locale = localizationConstant;

        prepareView(propertiesPanelWidgetFactory,
                    simplePropertyPresenterProvider,
                    endpointTypeListPropertyPresenter,
                    endpointXpathPanel);
    }

    private void prepareView(@Nonnull PropertiesPanelWidgetFactory propertiesPanelWidgetFactory,
                             @Nonnull Provider<SimplePropertyPresenter> simplePropertyPresenterProvider,
                             @Nonnull ListPropertyPresenter endpointTypeListProperty,
                             @Nonnull final ComplexPropertyPresenter endpointXpathComplexProperty) {

        PropertyGroupPresenter basicGroup = propertiesPanelWidgetFactory.createPropertyGroupPresenter(locale.miscGroupTitle());
        this.view.addGroup(basicGroup);

        endpointType = endpointTypeListProperty;
        endpointType.setTitle(locale.endpointType());
        endpointType.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                redesignViewToEndpointType(property);

                notifyListeners();
            }
        });
        basicGroup.addItem(endpointType);

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
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nullable String expression) {
                element.putProperty(NAMESPACES, nameSpaces);
                element.putProperty(X_PATH, expression != null ? expression : "");

                endpointXpathComplexProperty.setProperty(expression);

                notifyListeners();
            }
        };

        endpointXpathPanel = endpointXpathComplexProperty;
        endpointXpathPanel.setTitle(locale.endpointXpath());
        endpointXpathPanel.addEditButtonClickedListener(new ComplexPropertyPresenter.EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                List<NameSpace> nameSpaces = element.getProperty(NAMESPACES);
                String xPath = element.getProperty(X_PATH);

                if (xPath == null || nameSpaces == null) {
                    return;
                }
                nameSpaceEditorPresenter.showWindowWithParameters(nameSpaces,
                                                                  addNameSpacesCallBack,
                                                                  locale.callExpressionTitle(),
                                                                  xPath);
            }
        });
        basicGroup.addItem(endpointXpathPanel);

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
                adaptRegistryKeyAttribute();
                break;

            case XPATH:
                adaptXPathAttribute();
                break;

            default:
                endpointXpathPanel.setVisible(false);
                endpointRegistryKeyPanel.setVisible(false);
                break;
        }
    }

    private void adaptRegistryKeyAttribute() {
        endpointXpathPanel.setVisible(false);
        endpointRegistryKeyPanel.setVisible(true);

        String rKey = element.getProperty(REGISTRY_KEY);
        endpointRegistryKeyPanel.setProperty(rKey != null ? rKey : "");
    }

    private void adaptXPathAttribute() {
        endpointXpathPanel.setVisible(true);
        endpointRegistryKeyPanel.setVisible(false);

        String xPath = element.getProperty(X_PATH);
        endpointXpathPanel.setProperty(xPath);
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

        String descriptionValue = element.getProperty(DESCRIPTION);
        description.setProperty(descriptionValue != null ? descriptionValue : "");
    }

}