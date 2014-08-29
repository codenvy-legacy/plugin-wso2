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
import com.codenvy.ide.client.elements.ValueType;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.codenvy.ide.client.elements.Property.Action;
import static com.codenvy.ide.client.elements.Property.DataType;
import static com.codenvy.ide.client.elements.Property.Scope;
import static com.codenvy.ide.client.elements.ValueType.EXPRESSION;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Property mediator
 * depending on user's changes of properties.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
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
                element.setValueExpression(expression);

                PropertyPropertiesPanelPresenter.this.view.setValueExpression(expression);

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
        Action propertyAction = Action.valueOf(view.getPropertyAction());
        element.setPropertyAction(propertyAction);

        applyValueTypes();

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onValueTypeChanged() {
        ValueType propertyValue = ValueType.valueOf(view.getValueType());
        element.setValueType(propertyValue);

        applyValueTypes();

        notifyListeners();
    }

    /** Sets value type to element from special place of view and displaying properties panel to a certain value of value type */
    private void applyValueTypes() {
        ValueType valueType = ValueType.valueOf(view.getValueType());

        if (Action.set.equals(element.getPropertyAction())) {
            view.setDefaultVisible(true);

            view.setVisibleExpressionPanel(valueType.equals(EXPRESSION));
            view.setVisibleLiteralPanel(!valueType.equals(EXPRESSION));
        } else {
            view.setDefaultVisible(false);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertyDataTypeChanged() {
        element.setPropertyDataType(DataType.valueOf(view.getPropertyDataType()));

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
        element.setPropertyScope(Scope.getItemByValue(view.getPropertyScope()));

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
    public void onEditValueExpressionButtonClicked() {
        nameSpaceEditorPresenter.showWindowWithParameters(element.getNameSpaces(),
                                                          addNameSpacesCallBack,
                                                          local.propertyExpression(),
                                                          element.getValueExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        view.setValueTypes(propertyTypeManager.getValuesByName(ValueType.TYPE_NAME));
        view.selectValueType(element.getValueType().name());
        applyValueTypes();

        view.setPropertyActions(propertyTypeManager.getValuesByName(Action.TYPE_NAME));
        view.selectPropertyAction(element.getPropertyAction().name());

        view.setPropertyDataTypes(propertyTypeManager.getValuesByName(DataType.TYPE_NAME));
        view.selectPropertyDataType(element.getPropertyDataType().name());

        view.setPropertyScopes(propertyTypeManager.getValuesByName(Scope.TYPE_NAME));
        view.selectPropertyScope(element.getPropertyScope().getValue());

        view.setPropertyName(element.getPropertyName());
        view.setValueLiteral(element.getValueLiteral());
        view.setValueExpression(element.getValueExpression());
        view.setValueStringPattern(element.getValueStringPattern());
        view.setValueStringCaptureGroup(element.getValueStringCaptureGroup());
        view.setDescription(element.getDescription());
    }
}