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

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import java.util.List;

/**
 * The implementation of {@link PropertyPropertiesPanelView}
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class PropertyPropertiesPanelViewImpl extends PropertyPropertiesPanelView {

    interface PropertyPropertiesPanelViewImplUiBinder extends UiBinder<Widget, PropertyPropertiesPanelViewImpl> {
    }

    @UiField
    TextBox   propertyName;
    @UiField
    ListBox   propertyAction;
    @UiField
    ListBox   valueType;
    @UiField
    ListBox   propertyDataType;
    @UiField
    TextBox   valueLiteral;
    @UiField
    TextBox   valueStringPattern;
    @UiField
    TextBox   valueStringCaptureGroup;
    @UiField
    ListBox   propertyScope;
    @UiField
    TextBox   description;
    @UiField
    FlowPanel dataTypePanel;
    @UiField
    FlowPanel valueLiteralPanel;
    @UiField
    FlowPanel valueStringCaptureGroupPanel;
    @UiField
    FlowPanel valueTypePanel;
    @UiField
    FlowPanel valueStringPatternPanel;
    @UiField
    FlowPanel propertiesContainer;
    @UiField
    TextBox   valueExpression;
    @UiField
    FlowPanel valueExpressionPanel;

    @Inject
    public PropertyPropertiesPanelViewImpl(PropertyPropertiesPanelViewImplUiBinder ourUiBinder) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Override
    public String getPropertyName() {
        return String.valueOf(propertyName.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setPropertyName(String propertyName) {
        this.propertyName.setText(propertyName);
    }

    @UiHandler("propertyName")
    public void onPropertyNameChanged(KeyUpEvent event) {
        delegate.onPropertyNameChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getPropertyAction() {
        int index = propertyAction.getSelectedIndex();
        return index != -1 ? propertyAction.getValue(propertyAction.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setPropertyAction(List<String> propertyAction) {
        if (propertyAction == null) {
            return;
        }
        this.propertyAction.clear();
        for (String value : propertyAction) {
            this.propertyAction.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectPropertyAction(String propertyAction) {
        for (int i = 0; i < this.propertyAction.getItemCount(); i++) {
            if (this.propertyAction.getValue(i).equals(propertyAction)) {
                this.propertyAction.setItemSelected(i, true);
                return;
            }
        }
    }

    @UiHandler("propertyAction")
    public void onPropertyActionChanged(ChangeEvent event) {
        delegate.onPropertyActionChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getValueType() {
        int index = valueType.getSelectedIndex();
        return index != -1 ? valueType.getValue(valueType.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setValueType(List<String> valueType) {
        if (valueType == null) {
            return;
        }

        this.valueType.clear();

        for (String value : valueType) {
            this.valueType.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectValueType(String valueType) {
        for (int i = 0; i < this.valueType.getItemCount(); i++) {
            if (this.valueType.getValue(i).equals(valueType)) {
                this.valueType.setItemSelected(i, true);
                return;
            }
        }
    }

    @UiHandler("valueType")
    public void onValueTypeChanged(ChangeEvent event) {
        delegate.onValueTypeChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getPropertyDataType() {
        int index = propertyDataType.getSelectedIndex();
        return index != -1 ? propertyDataType.getValue(propertyDataType.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setPropertyDataType(List<String> propertyDataType) {
        if (propertyDataType == null) {
            return;
        }

        this.propertyDataType.clear();

        for (String value : propertyDataType) {
            this.propertyDataType.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectPropertyDataType(String propertyDataType) {
        for (int i = 0; i < this.propertyDataType.getItemCount(); i++) {
            if (this.propertyDataType.getValue(i).equals(propertyDataType)) {
                this.propertyDataType.setItemSelected(i, true);
                return;
            }
        }
    }

    @UiHandler("propertyDataType")
    public void onPropertyDataTypeChanged(ChangeEvent event) {
        delegate.onPropertyDataTypeChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getValueLiteral() {
        return String.valueOf(valueLiteral.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setValueLiteral(String valueLiteral) {
        this.valueLiteral.setText(valueLiteral);
    }

    /** {@inheritDoc} */
    @Override
    public String getValueExpression() {
        return String.valueOf(valueExpression.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setValueExpression(String valueExpression) {
        this.valueExpression.setText(valueExpression);
    }

    @UiHandler("valueLiteral")
    public void onValueLiteralChanged(KeyUpEvent event) {
        delegate.onValueLiteralChanged();
    }

    @UiHandler("valueExpression")
    public void onValueExpressionChanged(KeyUpEvent event) {
        delegate.onValueExpressionChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getValueStringPattern() {
        return String.valueOf(valueStringPattern.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setValueStringPattern(String valueStringPattern) {
        this.valueStringPattern.setText(valueStringPattern);
    }

    @UiHandler("valueStringPattern")
    public void onValueStringPatternChanged(KeyUpEvent event) {
        delegate.onValueStringPatternChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getValueStringCaptureGroup() {
        return valueStringCaptureGroup.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setValueStringCaptureGroup(String valueStringCaptureGroup) {
        this.valueStringCaptureGroup.setText(valueStringCaptureGroup);
    }

    @UiHandler("valueStringCaptureGroup")
    public void onValueStringCaptureGroupChanged(KeyUpEvent event) {
        delegate.onValueStringCaptureGroupChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getPropertyScope() {
        int index = propertyScope.getSelectedIndex();
        return index != -1 ? propertyScope.getValue(propertyScope.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setPropertyScope(List<String> propertyScope) {
        if (propertyScope == null) {
            return;
        }
        this.propertyScope.clear();
        for (String value : propertyScope) {
            this.propertyScope.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectPropertyScope(String propertyScope) {
        for (int i = 0; i < this.propertyScope.getItemCount(); i++) {
            if (this.propertyScope.getValue(i).equals(propertyScope)) {
                this.propertyScope.setItemSelected(i, true);
                return;
            }
        }
    }

    @UiHandler("propertyScope")
    public void onPropertyScopeChanged(ChangeEvent event) {
        delegate.onPropertyScopeChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getDescription() {
        return String.valueOf(description.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setDescription(String description) {
        this.description.setText(description);
    }

    @UiHandler("description")
    public void onDescriptionChanged(KeyUpEvent event) {
        delegate.onDescriptionChanged();
    }

    @UiHandler("expressionButton")
    public void onEditExpressionButtonClicked(ClickEvent event) {
        delegate.onEditValueExpressionButtonClicked();
    }

    /** {@inheritDoc} */
    @Override
    public void setDefaultVisible(boolean isVisible) {
        valueTypePanel.setVisible(isVisible);
        dataTypePanel.setVisible(isVisible);
        valueStringPatternPanel.setVisible(isVisible);
        valueLiteralPanel.setVisible(isVisible);
        valueExpressionPanel.setVisible(isVisible);
        valueStringCaptureGroupPanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleExpressionPanel(boolean isVisible) {
        valueExpressionPanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleLiteralPanel(boolean isVisible) {
        valueLiteralPanel.setVisible(isVisible);
    }

}