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
public class PropertyPropertiesPanelPresenter extends AbstractPropertiesPanel<Property, PropertyPropertiesPanelView>
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
        element.setPropertyName(view.getPropertyName());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertyActionChanged() {
        element.setPropertyAction(view.getPropertyAction());

        String propertyValue = view.getPropertyAction();
        element.setPropertyAction(propertyValue);

        if (set.name().equals(propertyValue)) {
            view.updatePropertyPanel(true);
            updateValueTypes(view.getValueType());
        } else {
            view.updatePropertyPanel(false);
        }

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onValueTypeChanged() {
        element.setValueType(view.getValueType());

        String propertyValue = view.getValueType();
        element.setValueType(propertyValue);

        updateValueTypes(propertyValue);

        notifyListeners();
    }

    private void updateValueTypes(String propertyValue) {
        if (set.name().equals(element.getPropertyAction())) {
            boolean isExpression = EXPRESSION.name().equals(propertyValue);

            view.setVisibleExpressionPanel(isExpression);
            view.setVisibleLiteralPanel(!isExpression);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertyDataTypeChanged() {
        element.setPropertyDataType(view.getPropertyDataType());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onValueLiteralChanged() {
        element.setValueLiteral(view.getValueLiteral());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onValueExpressionChanged() {
        element.setValueExpression(view.getValueExpression());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onValueStringPatternChanged() {
        element.setValueStringPattern(view.getValueStringPattern());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onValueStringCaptureGroupChanged() {
        element.setValueStringCaptureGroup(view.getValueStringCaptureGroup());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertyScopeChanged() {
        element.setPropertyScope(view.getPropertyScope());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onDescriptionChanged() {
        element.setDescription(view.getDescription());
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

        view.setPropertyName(element.getPropertyName());
        view.setValueType(propertyTypeManager.getValuesOfTypeByName(ValueType.TYPE_NAME));
        view.setPropertyAction(propertyTypeManager.getValuesOfTypeByName(PropertyAction.TYPE_NAME));
        view.selectPropertyAction(element.getPropertyAction());
        view.selectValueType(element.getValueType());
        view.setPropertyDataType(propertyTypeManager.getValuesOfTypeByName(DataType.TYPE_NAME));
        view.selectPropertyDataType(element.getPropertyDataType());
        view.setValueLiteral(element.getValueLiteral());
        view.setValueExpression(element.getValueExpression());
        view.setValueStringPattern(element.getValueStringPattern());
        view.setValueStringCaptureGroup(element.getValueStringCaptureGroup());
        view.setPropertyScope(propertyTypeManager.getValuesOfTypeByName(PropertyScope.TYPE_NAME));
        view.selectPropertyScope(element.getPropertyScope());
        view.setDescription(element.getDescription());

        view.updatePropertyPanel(set.name().equals(element.getPropertyAction()));

        updateValueTypes(element.getValueType());
    }

}