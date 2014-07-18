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
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import java.util.List;

/**
 * @author Andrey Plotnikov
 */
public class HeaderPropertiesPanelViewImpl extends HeaderPropertiesPanelView {

    interface HeaderPropertiesPanelViewImplUiBinder extends UiBinder<Widget, HeaderPropertiesPanelViewImpl> {
    }

    @UiField
    ListBox headerAction;
    @UiField
    ListBox scope;
    @UiField
    ListBox valueType;
    @UiField
    TextBox valueLiteral;
    @UiField
    TextBox headerName;

    @Inject
    public HeaderPropertiesPanelViewImpl(HeaderPropertiesPanelViewImplUiBinder ourUiBinder) {
        widget = ourUiBinder.createAndBindUi(this);
    }

    /** {@inheritDoc} */
    @Override
    public String getHeaderAction() {
        int index = headerAction.getSelectedIndex();
        return index != -1 ? headerAction.getValue(headerAction.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setHeaderAction(List<String> headerAction) {
        if (headerAction == null) {
            return;
        }
        this.headerAction.clear();
        for (String value : headerAction) {
            this.headerAction.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectHeaderAction(String headerAction) {
        for (int i = 0; i < this.headerAction.getItemCount(); i++) {
            if (this.headerAction.getValue(i).equals(headerAction)) {
                this.headerAction.setItemSelected(i, true);
                return;
            }
        }
    }

    @UiHandler("headerAction")
    public void onHeaderActionChanged(ChangeEvent event) {
        delegate.onHeaderActionChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getScope() {
        int index = scope.getSelectedIndex();
        return index != -1 ? scope.getValue(scope.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setScope(List<String> scope) {
        if (scope == null) {
            return;
        }
        this.scope.clear();
        for (String value : scope) {
            this.scope.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectScope(String scope) {
        for (int i = 0; i < this.scope.getItemCount(); i++) {
            if (this.scope.getValue(i).equals(scope)) {
                this.scope.setItemSelected(i, true);
                return;
            }
        }
    }

    @UiHandler("scope")
    public void onScopeChanged(ChangeEvent event) {
        delegate.onScopeChanged();
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
    public String getValueLiteral() {
        return String.valueOf(valueLiteral.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setValueLiteral(String valueLiteral) {
        this.valueLiteral.setText(valueLiteral);
    }

    @UiHandler("valueLiteral")
    public void onValueLiteralChanged(KeyUpEvent event) {
        delegate.onValueLiteralChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getHeaderName() {
        return String.valueOf(headerName.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setHeaderName(String headerName) {
        this.headerName.setText(headerName);
    }

    @UiHandler("headerName")
    public void onHeaderNameChanged(KeyUpEvent event) {
        delegate.onHeaderNameChanged();
    }

}