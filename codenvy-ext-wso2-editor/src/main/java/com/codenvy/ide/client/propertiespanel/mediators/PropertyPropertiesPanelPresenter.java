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
import com.codenvy.ide.client.elements.mediators.Action;
import com.codenvy.ide.client.elements.mediators.Property;
import com.codenvy.ide.client.elements.mediators.ValueType;
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

import static com.codenvy.ide.client.elements.mediators.Property.DESCRIPTION;
import static com.codenvy.ide.client.elements.mediators.Property.DataType;
import static com.codenvy.ide.client.elements.mediators.Property.NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.Property.PROPERTY_ACTION;
import static com.codenvy.ide.client.elements.mediators.Property.PROPERTY_DATA_TYPE;
import static com.codenvy.ide.client.elements.mediators.Property.PROPERTY_NAME;
import static com.codenvy.ide.client.elements.mediators.Property.PROPERTY_SCOPE;
import static com.codenvy.ide.client.elements.mediators.Property.Scope;
import static com.codenvy.ide.client.elements.mediators.Property.VALUE_EXPRESSION;
import static com.codenvy.ide.client.elements.mediators.Property.VALUE_LITERAL;
import static com.codenvy.ide.client.elements.mediators.Property.VALUE_STRING_CAPTURE_GROUP;
import static com.codenvy.ide.client.elements.mediators.Property.VALUE_STRING_PATTERN;
import static com.codenvy.ide.client.elements.mediators.Property.VALUE_TYPE;
import static com.codenvy.ide.client.elements.mediators.ValueType.EXPRESSION;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Property mediator
 * depending on user's changes of properties.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class PropertyPropertiesPanelPresenter extends AbstractPropertiesPanel<Property, PropertiesPanelView> {

    private final NameSpaceEditorPresenter       nameSpaceEditorPresenter;
    private final WSO2EditorLocalizationConstant locale;

    private SimplePropertyPresenter  propertyName;
    private ListPropertyPresenter    propertyAction;
    private ListPropertyPresenter    valueType;
    private ListPropertyPresenter    propertyDataType;
    private SimplePropertyPresenter  valueLiteral;
    private SimplePropertyPresenter  valueStringPattern;
    private SimplePropertyPresenter  valueStringCaptureGroup;
    private SimplePropertyPresenter  description;
    private ListPropertyPresenter    propertyScope;
    private ComplexPropertyPresenter valueExpression;

    @Inject
    public PropertyPropertiesPanelPresenter(PropertiesPanelView view,
                                            PropertyTypeManager propertyTypeManager,
                                            NameSpaceEditorPresenter nameSpaceEditorPresenter,
                                            PropertiesPanelWidgetFactory propertiesPanelWidgetFactory,
                                            ComplexPropertyPresenter valueExpression,
                                            Provider<ListPropertyPresenter> listPropertyPresenterProvider,
                                            Provider<SimplePropertyPresenter> simplePropertyPresenterProvider,
                                            WSO2EditorLocalizationConstant wso2EditorLocalizationConstant) {
        super(view, propertyTypeManager);

        this.nameSpaceEditorPresenter = nameSpaceEditorPresenter;
        this.locale = wso2EditorLocalizationConstant;

        prepareView(propertiesPanelWidgetFactory,
                    simplePropertyPresenterProvider,
                    valueExpression,
                    listPropertyPresenterProvider);
    }

    private void prepareView(@Nonnull PropertiesPanelWidgetFactory propertiesPanelWidgetFactory,
                             @Nonnull Provider<SimplePropertyPresenter> simplePropertyPresenterProvider,
                             @Nonnull final ComplexPropertyPresenter valueExpressionPropertyPresenter,
                             @Nonnull Provider<ListPropertyPresenter> listPropertyPresenterProvider) {

        PropertyGroupPresenter basicGroup = propertiesPanelWidgetFactory.createPropertyGroupPresenter(locale.miscGroupTitle());
        this.view.addGroup(basicGroup);

        propertyName = simplePropertyPresenterProvider.get();
        propertyName.setTitle(locale.propertyName());
        propertyName.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(PROPERTY_NAME, property);

                notifyListeners();
            }
        });

        basicGroup.addItem(propertyName);

        propertyAction = listPropertyPresenterProvider.get();
        propertyAction.setTitle(locale.propertyAction());
        propertyAction.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(PROPERTY_ACTION, Action.getItemByValue(property));

                applyValueTypes();

                notifyListeners();
            }
        });

        basicGroup.addItem(propertyAction);

        valueType = listPropertyPresenterProvider.get();
        valueType.setTitle(locale.propertyValueType());
        valueType.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(VALUE_TYPE, ValueType.valueOf(property));

                applyValueTypes();

                notifyListeners();
            }
        });

        basicGroup.addItem(valueType);

        propertyDataType = listPropertyPresenterProvider.get();
        propertyDataType.setTitle(locale.propertyDataType());
        propertyDataType.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(PROPERTY_DATA_TYPE, DataType.valueOf(property));

                notifyListeners();
            }
        });

        basicGroup.addItem(propertyDataType);

        valueLiteral = simplePropertyPresenterProvider.get();
        valueLiteral.setTitle(locale.propertyValueLiteral());
        valueLiteral.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(VALUE_LITERAL, property);

                notifyListeners();
            }
        });

        basicGroup.addItem(valueLiteral);

        final AddNameSpacesCallBack addNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nullable String expression) {
                element.putProperty(NAMESPACES, nameSpaces);
                element.putProperty(VALUE_EXPRESSION, expression != null ? expression : "");

                valueExpressionPropertyPresenter.setProperty(expression);

                notifyListeners();
            }
        };

        valueExpression = valueExpressionPropertyPresenter;
        valueExpression.setTitle(locale.valueStringPattern());
        this.valueExpression.addEditButtonClickedListener(new ComplexPropertyPresenter.EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                List<NameSpace> nameSpaces = element.getProperty(NAMESPACES);
                String vExpression = element.getProperty(VALUE_EXPRESSION);

                if (vExpression == null || nameSpaces == null) {
                    return;
                }

                nameSpaceEditorPresenter.showWindowWithParameters(nameSpaces,
                                                                  addNameSpacesCallBack,
                                                                  locale.propertyExpression(),
                                                                  vExpression);
            }
        });

        basicGroup.addItem(valueExpressionPropertyPresenter);

        valueStringPattern = simplePropertyPresenterProvider.get();
        valueStringPattern.setTitle(locale.valueStringPattern());
        valueStringPattern.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(VALUE_STRING_PATTERN, property);

                notifyListeners();
            }
        });

        basicGroup.addItem(valueStringPattern);

        valueStringCaptureGroup = simplePropertyPresenterProvider.get();
        valueStringCaptureGroup.setTitle(locale.capturingGroup());
        valueStringCaptureGroup.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(VALUE_STRING_CAPTURE_GROUP, property);

                notifyListeners();
            }
        });

        basicGroup.addItem(valueStringCaptureGroup);

        propertyScope = listPropertyPresenterProvider.get();
        propertyScope.setTitle(locale.propertyScope());
        propertyScope.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(PROPERTY_SCOPE, Scope.getItemByValue(property));

                notifyListeners();
            }
        });

        basicGroup.addItem(propertyScope);

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

    /** Sets value type to element from special place of view and displaying properties panel to a certain value of value type */
    private void applyValueTypes() {
        ValueType valueType = element.getProperty(VALUE_TYPE);

        boolean isSetPropertyAction = Action.SET.equals(element.getProperty(PROPERTY_ACTION));

        setDefaultVisible(isSetPropertyAction);

        if (isSetPropertyAction && valueType != null) {
            boolean isExpression = EXPRESSION.equals(valueType);

            valueExpression.setVisible(isExpression);
            valueLiteral.setVisible(!isExpression);
        }
    }

    private void setDefaultVisible(boolean isVisible) {
        valueType.setVisible(isVisible);
        propertyDataType.setVisible(isVisible);
        valueStringPattern.setVisible(isVisible);
        valueLiteral.setVisible(isVisible);
        valueExpression.setVisible(isVisible);
        valueStringCaptureGroup.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        valueType.setValues(propertyTypeManager.getValuesByName(ValueType.TYPE_NAME));
        ValueType type = element.getProperty(VALUE_TYPE);
        if (type != null) {
            valueType.selectValue(type.name());
        }

        applyValueTypes();

        propertyAction.setValues(propertyTypeManager.getValuesByName(Action.TYPE_NAME));
        Action action = element.getProperty(PROPERTY_ACTION);
        if (action != null) {
            propertyAction.selectValue(action.getValue());
        }

        propertyDataType.setValues(propertyTypeManager.getValuesByName(DataType.TYPE_NAME));
        DataType dType = element.getProperty(PROPERTY_DATA_TYPE);
        if (dType != null) {
            propertyDataType.selectValue(dType.name());
        }

        propertyScope.setValues(propertyTypeManager.getValuesByName(Scope.TYPE_NAME));
        Scope scope = element.getProperty(PROPERTY_SCOPE);
        if (scope != null) {
            propertyScope.selectValue(scope.getValue());
        }

        propertyName.setProperty(element.getProperty(PROPERTY_NAME));
        valueLiteral.setProperty(element.getProperty(VALUE_LITERAL));
        valueExpression.setProperty(element.getProperty(VALUE_EXPRESSION));
        valueStringPattern.setProperty(element.getProperty(VALUE_STRING_PATTERN));
        valueStringCaptureGroup.setProperty(element.getProperty(VALUE_STRING_CAPTURE_GROUP));
        description.setProperty(element.getProperty(DESCRIPTION));
    }

}