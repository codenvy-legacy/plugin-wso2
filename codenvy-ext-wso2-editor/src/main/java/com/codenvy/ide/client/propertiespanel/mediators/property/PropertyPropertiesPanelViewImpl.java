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
package com.codenvy.ide.client.propertiespanel.mediators.property;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
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
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Provides a graphical representation of 'Property' property panel for editing property of 'Property' mediator.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class PropertyPropertiesPanelViewImpl extends PropertyPropertiesPanelView {

    @Singleton
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

    @UiField(provided = true)
    final EditorResources                res;
    @UiField(provided = true)
    final WSO2EditorLocalizationConstant loc;

    @Inject
    public PropertyPropertiesPanelViewImpl(PropertyPropertiesPanelViewImplUiBinder ourUiBinder,
                                           EditorResources res,
                                           WSO2EditorLocalizationConstant loc) {
        this.res = res;
        this.loc = loc;

        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getPropertyName() {
        return String.valueOf(propertyName.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setPropertyName(@Nullable String propertyName) {
        this.propertyName.setText(propertyName);
    }

    @UiHandler("propertyName")
    public void onPropertyNameChanged(KeyUpEvent event) {
        delegate.onPropertyNameChanged();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getPropertyAction() {
        return getSelectedItem(propertyAction);
    }

    /** {@inheritDoc} */
    @Override
    public void setPropertyActions(@Nonnull List<String> propertyActions) {
        setTypes(propertyAction, propertyActions);
    }

    /** {@inheritDoc} */
    @Override
    public void selectPropertyAction(@Nonnull String propertyAction) {
        selectType(this.propertyAction, propertyAction);
    }

    @UiHandler("propertyAction")
    public void onPropertyActionChanged(ChangeEvent event) {
        delegate.onPropertyActionChanged();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getValueType() {
        return getSelectedItem(valueType);
    }

    /** {@inheritDoc} */
    @Override
    public void setValueTypes(@Nonnull List<String> valueTypes) {
        setTypes(valueType, valueTypes);
    }

    /** {@inheritDoc} */
    @Override
    public void selectValueType(@Nonnull String valueType) {
        selectType(this.valueType, valueType);
    }

    @UiHandler("valueType")
    public void onValueTypeChanged(ChangeEvent event) {
        delegate.onValueTypeChanged();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getPropertyDataType() {
        return getSelectedItem(propertyDataType);
    }

    /** {@inheritDoc} */
    @Override
    public void setPropertyDataTypes(@Nonnull List<String> propertyDataTypes) {
        setTypes(propertyDataType, propertyDataTypes);
    }

    /** {@inheritDoc} */
    @Override
    public void selectPropertyDataType(@Nonnull String propertyDataType) {
        selectType(this.propertyDataType, propertyDataType);
    }

    @UiHandler("propertyDataType")
    public void onPropertyDataTypeChanged(ChangeEvent event) {
        delegate.onPropertyDataTypeChanged();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getValueLiteral() {
        return valueLiteral.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setValueLiteral(@Nullable String valueLiteral) {
        this.valueLiteral.setText(valueLiteral);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getValueExpression() {
        return valueExpression.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setValueExpression(@Nullable String valueExpression) {
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
    @Nonnull
    @Override
    public String getValueStringPattern() {
        return String.valueOf(valueStringPattern.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setValueStringPattern(@Nullable String valueStringPattern) {
        this.valueStringPattern.setText(valueStringPattern);
    }

    @UiHandler("valueStringPattern")
    public void onValueStringPatternChanged(KeyUpEvent event) {
        delegate.onValueStringPatternChanged();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getValueStringCaptureGroup() {
        return valueStringCaptureGroup.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setValueStringCaptureGroup(@Nullable String valueStringCaptureGroup) {
        this.valueStringCaptureGroup.setText(valueStringCaptureGroup);
    }

    @UiHandler("valueStringCaptureGroup")
    public void onValueStringCaptureGroupChanged(KeyUpEvent event) {
        delegate.onValueStringCaptureGroupChanged();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getPropertyScope() {
        return getSelectedItem(propertyScope);
    }

    /** {@inheritDoc} */
    @Override
    public void setPropertyScopes(@Nonnull List<String> propertyScopes) {
        setTypes(propertyScope, propertyScopes);
    }

    /** {@inheritDoc} */
    @Override
    public void selectPropertyScope(@Nonnull String propertyScope) {
        selectType(this.propertyScope, propertyScope);
    }

    @UiHandler("propertyScope")
    public void onPropertyScopeChanged(ChangeEvent event) {
        delegate.onPropertyScopeChanged();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getDescription() {
        return description.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setDescription(@Nullable String description) {
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