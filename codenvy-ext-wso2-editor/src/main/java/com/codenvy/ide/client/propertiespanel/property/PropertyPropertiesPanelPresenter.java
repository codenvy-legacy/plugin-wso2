/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.client.propertiespanel.property;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.Property;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.log.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.client.propertiespanel.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertytypes.PropertyTypeManager;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.codenvy.ide.client.elements.Property.DataType;
import static com.codenvy.ide.client.elements.Property.PropertyAction;
import static com.codenvy.ide.client.elements.Property.PropertyAction.set;
import static com.codenvy.ide.client.elements.Property.PropertyScope;
import static com.codenvy.ide.client.elements.Property.ValueType;
import static com.codenvy.ide.client.elements.Property.ValueType.EXPRESSION;

/**
 * The property panel of Property mediator.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class PropertyPropertiesPanelPresenter extends AbstractPropertiesPanel<Property>
        implements PropertyPropertiesPanelView.ActionDelegate {

    private final NameSpaceEditorPresenter       nameSpaceEditorPresenter;
    private final AddNameSpacesCallBack          addNameSpacesCallBack;
    private final WSO2EditorLocalizationConstant local;

    @Inject
    public PropertyPropertiesPanelPresenter(PropertyPropertiesPanelView view,
                                            PropertyTypeManager propertyTypeManager,
                                            NameSpaceEditorPresenter nameSpaceEditorPresenter,
                                            WSO2EditorLocalizationConstant wso2EditorLocalizationConstant) {
        super(view, propertyTypeManager);

        this.nameSpaceEditorPresenter = nameSpaceEditorPresenter;
        this.local = wso2EditorLocalizationConstant;
        this.addNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nullable String expression) {
                element.setNameSpaces(nameSpaces);

                notifyListeners();
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertyNameChanged() {
        element.setPropertyName(((PropertyPropertiesPanelView)view).getPropertyName());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertyActionChanged() {
        String propertyValue = ((PropertyPropertiesPanelView)view).getPropertyAction();
        element.setPropertyAction(propertyValue);

        if (set.name().equals(propertyValue)) {
            ((PropertyPropertiesPanelView)view).updatePropertyPanel(true);
            updateValueTypes(((PropertyPropertiesPanelView)view).getValueType());
        } else {
            ((PropertyPropertiesPanelView)view).updatePropertyPanel(false);
        }

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onValueTypeChanged() {
        String propertyValue = ((PropertyPropertiesPanelView)view).getValueType();
        element.setValueType(propertyValue);

        updateValueTypes(propertyValue);

        notifyListeners();
    }

    private void updateValueTypes(String propertyValue) {
        if (set.name().equals(element.getPropertyAction())) {
            boolean isExpression = EXPRESSION.name().equals(propertyValue);

            ((PropertyPropertiesPanelView)view).setVisibleExpressionPanel(isExpression);
            ((PropertyPropertiesPanelView)view).setVisibleLiteralPanel(!isExpression);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertyDataTypeChanged() {
        element.setPropertyDataType(((PropertyPropertiesPanelView)view).getPropertyDataType());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onValueLiteralChanged() {
        element.setValueLiteral(((PropertyPropertiesPanelView)view).getValueLiteral());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onValueExpressionChanged() {
        element.setValueExpression(((PropertyPropertiesPanelView)view).getValueExpression());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onValueStringPatternChanged() {
        element.setValueStringPattern(((PropertyPropertiesPanelView)view).getValueStringPattern());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onValueStringCaptureGroupChanged() {
        element.setValueStringCaptureGroup(((PropertyPropertiesPanelView)view).getValueStringCaptureGroup());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertyScopeChanged() {
        element.setPropertyScope(((PropertyPropertiesPanelView)view).getPropertyScope());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onDescriptionChanged() {
        element.setDescription(((PropertyPropertiesPanelView)view).getDescription());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void showEditValueExpressionWindow() {
        nameSpaceEditorPresenter.showWindowWithParameters(element.getNameSpaces(), addNameSpacesCallBack,
                                                          local.propertiespanelNamespacePropertyExpression(), null);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        ((PropertyPropertiesPanelView)view).setPropertyName(element.getPropertyName());
        ((PropertyPropertiesPanelView)view).setValueType(propertyTypeManager.getValuesOfTypeByName(ValueType.TYPE_NAME));
        ((PropertyPropertiesPanelView)view).setPropertyAction(propertyTypeManager.getValuesOfTypeByName(PropertyAction.TYPE_NAME));
        ((PropertyPropertiesPanelView)view).selectPropertyAction(element.getPropertyAction());
        ((PropertyPropertiesPanelView)view).selectValueType(element.getValueType());
        ((PropertyPropertiesPanelView)view).setPropertyDataType(propertyTypeManager.getValuesOfTypeByName(DataType.TYPE_NAME));
        ((PropertyPropertiesPanelView)view).selectPropertyDataType(element.getPropertyDataType());
        ((PropertyPropertiesPanelView)view).setValueLiteral(element.getValueLiteral());
        ((PropertyPropertiesPanelView)view).setValueExpression(element.getValueExpression());
        ((PropertyPropertiesPanelView)view).setValueStringPattern(element.getValueStringPattern());
        ((PropertyPropertiesPanelView)view).setValueStringCaptureGroup(element.getValueStringCaptureGroup());
        ((PropertyPropertiesPanelView)view).setPropertyScope(propertyTypeManager.getValuesOfTypeByName(PropertyScope.TYPE_NAME));
        ((PropertyPropertiesPanelView)view).selectPropertyScope(element.getPropertyScope());
        ((PropertyPropertiesPanelView)view).setDescription(element.getDescription());

        ((PropertyPropertiesPanelView)view).updatePropertyPanel(set.name().equals(element.getPropertyAction()));

        updateValueTypes(element.getValueType());
    }

}