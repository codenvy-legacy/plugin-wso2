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

import com.codenvy.ide.client.elements.Property;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertytypes.PropertyTypeManager;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

/**
 * @author Andrey Plotnikov
 */
public class PropertyPropertiesPanelPresenter extends AbstractPropertiesPanel<Property>
        implements PropertyPropertiesPanelView.ActionDelegate {

    @Inject
    public PropertyPropertiesPanelPresenter(PropertyPropertiesPanelView view, PropertyTypeManager propertyTypeManager) {
        super(view, propertyTypeManager);
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
        element.setPropertyAction(((PropertyPropertiesPanelView)view).getPropertyAction());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onValueTypeChanged() {
        element.setValueType(((PropertyPropertiesPanelView)view).getValueType());
        notifyListeners();
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
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        ((PropertyPropertiesPanelView)view).setPropertyName(element.getPropertyName());
        ((PropertyPropertiesPanelView)view).setPropertyAction(propertyTypeManager.getValuesOfTypeByName("PropertyAction"));
        ((PropertyPropertiesPanelView)view).selectPropertyAction(element.getPropertyAction());
        ((PropertyPropertiesPanelView)view).setValueType(propertyTypeManager.getValuesOfTypeByName("PropertyValueType"));
        ((PropertyPropertiesPanelView)view).selectValueType(element.getValueType());
        ((PropertyPropertiesPanelView)view).setPropertyDataType(element.getPropertyDataType());
        ((PropertyPropertiesPanelView)view).setValueLiteral(element.getValueLiteral());
        ((PropertyPropertiesPanelView)view).setValueStringPattern(element.getValueStringPattern());
        ((PropertyPropertiesPanelView)view).setValueStringCaptureGroup(element.getValueStringCaptureGroup());
        ((PropertyPropertiesPanelView)view).setPropertyScope(propertyTypeManager.getValuesOfTypeByName("PropertyScope"));
        ((PropertyPropertiesPanelView)view).selectPropertyScope(element.getPropertyScope());
        ((PropertyPropertiesPanelView)view).setDescription(element.getDescription());
    }

}