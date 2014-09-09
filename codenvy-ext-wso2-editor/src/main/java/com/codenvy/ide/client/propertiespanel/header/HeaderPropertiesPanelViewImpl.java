/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package com.codenvy.ide.client.propertiespanel.header;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
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
 * Provides a graphical representation of 'Header' property panel for editing property of 'Header' mediator.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class HeaderPropertiesPanelViewImpl extends HeaderPropertiesPanelView {

    @Singleton
    interface HeaderPropertiesPanelViewImplUiBinder extends UiBinder<Widget, HeaderPropertiesPanelViewImpl> {
    }

    @UiField
    ListBox action;
    @UiField
    ListBox scope;
    @UiField
    ListBox valueType;

    @UiField
    TextBox value;
    @UiField
    TextBox headerName;
    @UiField
    TextBox expression;
    @UiField
    TextBox inline;

    @UiField
    Button btnAddNameSpace;
    @UiField
    Button btnAddInline;
    @UiField
    Button btnAddExpression;

    @UiField
    FlowPanel valueTypePanel;
    @UiField
    FlowPanel valueLiteralPanel;
    @UiField
    FlowPanel valueExpressionPanel;
    @UiField
    FlowPanel valueInlinePanel;
    @UiField
    FlowPanel headerNamePanel;

    @UiField(provided = true)
    final EditorResources                res;
    @UiField(provided = true)
    final WSO2EditorLocalizationConstant locale;

    @Inject
    public HeaderPropertiesPanelViewImpl(HeaderPropertiesPanelViewImplUiBinder ourUiBinder,
                                         EditorResources res,
                                         WSO2EditorLocalizationConstant locale) {
        this.res = res;
        this.locale = locale;

        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @UiHandler("btnAddNameSpace")
    public void onEditNameSpaceButtonClicked(ClickEvent event) {
        delegate.onAddHeaderNameSpaceBtnClicked();
    }

    @UiHandler("btnAddExpression")
    public void onAddExpressionBtnClicked(ClickEvent event) {
        delegate.onAddExpressionBtnClicked();
    }

    @UiHandler("btnAddInline")
    public void onAddInlineBtnClicked(ClickEvent event) {
        delegate.onAddInlineBtnClicked();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getAction() {
        return getSelectedItem(action);
    }

    /** {@inheritDoc} */
    @Override
    public void setAction(@Nullable List<String> actions) {
        setTypes(action, actions);
    }

    /** {@inheritDoc} */
    @Override
    public void selectHeaderAction(@Nullable String headerAction) {
        selectType(action, headerAction);
    }

    @UiHandler("action")
    public void onHeaderActionChanged(ChangeEvent event) {
        delegate.onActionChanged();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getScope() {
        return getSelectedItem(scope);
    }

    /** {@inheritDoc} */
    @Override
    public void setScope(@Nullable List<String> scopes) {
        setTypes(scope, scopes);
    }

    /** {@inheritDoc} */
    @Override
    public void selectScope(@Nullable String selectedScope) {
        selectType(scope, selectedScope);
    }

    @UiHandler("scope")
    public void onScopeChanged(ChangeEvent event) {
        delegate.onScopeChanged();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getValueType() {
        return getSelectedItem(valueType);
    }

    /** {@inheritDoc} */
    @Override
    public void setValueTypes(@Nullable List<String> valueTypes) {
        setTypes(valueType, valueTypes);
    }

    /** {@inheritDoc} */
    @Override
    public void selectValueType(@Nullable String selectedType) {
        selectType(valueType, selectedType);
    }

    @UiHandler("valueType")
    public void onValueTypeChanged(ChangeEvent event) {
        delegate.onTypeChanged();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getValue() {
        return value.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setValue(@Nullable String valueLiteral) {
        this.value.setText(valueLiteral);
    }

    @UiHandler("value")
    public void onValueLiteralChanged(KeyUpEvent event) {
        delegate.onValueChanged();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getHeaderName() {
        return headerName.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setHeaderName(@Nullable String headerName) {
        this.headerName.setText(headerName);
    }

    /** {@inheritDoc} */
    @Override
    public void setExpression(@Nullable String expression) {
        this.expression.setText(expression);
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public String getExpression() {
        return expression.getText();
    }

    @UiHandler("headerName")
    public void onHeaderNameChanged(KeyUpEvent event) {
        delegate.onHeaderNameChanged();
    }

    /** {@inheritDoc} */
    @Override
    public void setInlineXML(@Nullable String inlineXML) {
        inline.setText(inlineXML);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleValueTypePanel(boolean isVisible) {
        valueTypePanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleValueLiteralPanel(boolean isVisible) {
        valueLiteralPanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleValueExpressionPanel(boolean isVisible) {
        valueExpressionPanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleValueInlinePanel(boolean isVisible) {
        valueInlinePanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleHeaderNamePanel(boolean isVisible) {
        headerNamePanel.setVisible(isVisible);
    }
}