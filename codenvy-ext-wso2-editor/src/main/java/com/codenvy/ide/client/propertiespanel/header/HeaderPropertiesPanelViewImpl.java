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
package com.codenvy.ide.client.propertiespanel.header;

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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class HeaderPropertiesPanelViewImpl extends HeaderPropertiesPanelView {

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

    @Inject
    public HeaderPropertiesPanelViewImpl(HeaderPropertiesPanelViewImplUiBinder ourUiBinder) {
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
        int index = action.getSelectedIndex();
        return index != -1 ? action.getValue(action.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setAction(@Nullable List<String> actions) {
        if (actions == null) {
            return;
        }

        action.clear();

        for (String value : actions) {
            action.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectHeaderAction(@Nullable String headerAction) {
        for (int i = 0; i < action.getItemCount(); i++) {
            if (action.getValue(i).equals(headerAction)) {
                action.setItemSelected(i, true);
                return;
            }
        }
    }

    @UiHandler("action")
    public void onHeaderActionChanged(ChangeEvent event) {
        delegate.onHeaderActionChanged();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getScope() {
        int index = scope.getSelectedIndex();
        return index != -1 ? scope.getValue(scope.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setScope(@Nullable List<String> scopes) {
        if (scopes == null) {
            return;
        }

        scope.clear();

        for (String value : scopes) {
            scope.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectScope(@Nullable String selectedScope) {
        for (int i = 0; i < scope.getItemCount(); i++) {
            if (scope.getValue(i).equals(selectedScope)) {
                scope.setItemSelected(i, true);
                return;
            }
        }
    }

    @UiHandler("scope")
    public void onScopeChanged(ChangeEvent event) {
        delegate.onScopeChanged();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getValueType() {
        int index = valueType.getSelectedIndex();
        return index != -1 ? valueType.getValue(valueType.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setValueType(@Nullable List<String> valueTypes) {
        if (valueTypes == null) {
            return;
        }

        valueType.clear();

        for (String value : valueTypes) {
            valueType.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectValueType(@Nullable String selectedType) {
        for (int i = 0; i < valueType.getItemCount(); i++) {
            if (valueType.getValue(i).equals(selectedType)) {
                valueType.setItemSelected(i, true);
                return;
            }
        }
    }

    @UiHandler("valueType")
    public void onValueTypeChanged(ChangeEvent event) {
        delegate.onValueTypeChanged();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getValue() {
        return String.valueOf(value.getText());
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
        return String.valueOf(headerName.getText());
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

    @UiHandler("action")
    public void onActionChanged(ChangeEvent event) {
        delegate.onActionChanged();
    }

    @UiHandler("valueType")
    public void onTypeChanged(ChangeEvent event) {
        delegate.onTypeChanged();
    }

}