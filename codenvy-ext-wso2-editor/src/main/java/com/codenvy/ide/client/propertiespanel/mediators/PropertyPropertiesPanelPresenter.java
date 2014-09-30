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
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.PropertyPanelFactory;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
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
import static com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter.EditButtonClickedListener;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Property mediator
 * depending on user's changes of properties.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class PropertyPropertiesPanelPresenter extends AbstractPropertiesPanel<Property> {

    private final NameSpaceEditorPresenter nameSpaceEditorPresenter;
    private final AddNameSpacesCallBack    addNameSpacesCallBack;

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
                                            WSO2EditorLocalizationConstant locale,
                                            NameSpaceEditorPresenter nameSpaceEditorPresenter,
                                            PropertyPanelFactory propertyPanelFactory) {

        super(view, propertyTypeManager, locale, propertyPanelFactory);

        this.nameSpaceEditorPresenter = nameSpaceEditorPresenter;

        addNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nullable String expression) {
                element.putProperty(NAMESPACES, nameSpaces);
                element.putProperty(VALUE_EXPRESSION, expression != null ? expression : "");

                valueExpression.setProperty(expression);

                notifyListeners();
            }
        };

        prepareView();
    }

    private void prepareView() {
        PropertyGroupPresenter basicGroup = createGroup(locale.miscGroupTitle());

        PropertyValueChangedListener propertyNameListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(PROPERTY_NAME, property);

                notifyListeners();
            }
        };
        propertyName = createSimpleProperty(basicGroup, locale.propertyName(), propertyNameListener);

        PropertyValueChangedListener actionListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(PROPERTY_ACTION, Action.getItemByValue(property));

                applyValueTypes();

                notifyListeners();
            }
        };
        propertyAction = createListProperty(basicGroup, locale.propertyAction(), actionListener);

        PropertyValueChangedListener valueTypeListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(VALUE_TYPE, ValueType.valueOf(property));

                applyValueTypes();

                notifyListeners();
            }
        };
        valueType = createListProperty(basicGroup, locale.propertyValueType(), valueTypeListener);

        PropertyValueChangedListener dataTypeListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(PROPERTY_DATA_TYPE, DataType.valueOf(property));

                notifyListeners();
            }
        };
        propertyDataType = createListProperty(basicGroup, locale.propertyDataType(), dataTypeListener);

        PropertyValueChangedListener valueLiteralListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(VALUE_LITERAL, property);

                notifyListeners();
            }
        };
        valueLiteral = createSimpleProperty(basicGroup, locale.propertyValueLiteral(), valueLiteralListener);

        EditButtonClickedListener valueExprBtnListener = new EditButtonClickedListener() {
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
        };
        valueExpression = createComplexProperty(basicGroup, locale.valueExpression(), valueExprBtnListener);

        PropertyValueChangedListener valuePatternListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(VALUE_STRING_PATTERN, property);

                notifyListeners();
            }
        };
        valueStringPattern = createSimpleProperty(basicGroup, locale.valueStringPattern(), valuePatternListener);

        PropertyValueChangedListener valueCapturingListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(VALUE_STRING_CAPTURE_GROUP, property);

                notifyListeners();
            }
        };
        valueStringCaptureGroup = createSimpleProperty(basicGroup, locale.capturingGroup(), valueCapturingListener);

        PropertyValueChangedListener scopeListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(PROPERTY_SCOPE, Scope.getItemByValue(property));

                notifyListeners();
            }
        };
        propertyScope = createListProperty(basicGroup, locale.propertyScope(), scopeListener);

        PropertyValueChangedListener descriptionListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(DESCRIPTION, property);

                notifyListeners();
            }
        };
        description = createSimpleProperty(basicGroup, locale.description(), descriptionListener);
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

        displayValueTypeParameter();

        displayPropertyActionParameter();

        displayPropertyDataTypeParameter();

        displayPropertyScopeParameter();

        applyValueTypes();

        propertyName.setProperty(element.getProperty(PROPERTY_NAME));
        valueLiteral.setProperty(element.getProperty(VALUE_LITERAL));
        valueExpression.setProperty(element.getProperty(VALUE_EXPRESSION));
        valueStringPattern.setProperty(element.getProperty(VALUE_STRING_PATTERN));
        valueStringCaptureGroup.setProperty(element.getProperty(VALUE_STRING_CAPTURE_GROUP));
        description.setProperty(element.getProperty(DESCRIPTION));
    }

    private void displayValueTypeParameter() {
        valueType.setValues(propertyTypeManager.getValuesByName(ValueType.TYPE_NAME));
        ValueType type = element.getProperty(VALUE_TYPE);

        if (type == null) {
            return;
        }

        valueType.selectValue(type.name());
    }

    private void displayPropertyActionParameter() {
        propertyAction.setValues(propertyTypeManager.getValuesByName(Action.TYPE_NAME));
        Action action = element.getProperty(PROPERTY_ACTION);

        if (action == null) {
            return;
        }

        propertyAction.selectValue(action.getValue());
    }

    private void displayPropertyDataTypeParameter() {
        propertyDataType.setValues(propertyTypeManager.getValuesByName(DataType.TYPE_NAME));
        DataType dataType = element.getProperty(PROPERTY_DATA_TYPE);

        if (dataType == null) {
            return;
        }

        propertyDataType.selectValue(dataType.name());
    }

    private void displayPropertyScopeParameter() {
        propertyScope.setValues(propertyTypeManager.getValuesByName(Scope.TYPE_NAME));
        Scope scope = element.getProperty(PROPERTY_SCOPE);

        if (scope == null) {
            return;
        }

        propertyScope.selectValue(scope.getValue());
    }

}