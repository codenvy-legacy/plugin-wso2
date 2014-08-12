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
package com.codenvy.ide.client.propertiespanel.filter;

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
public class FilterPropertiesPanelViewImpl extends FilterPropertiesPanelView {

    interface FilterPropertiesPanelViewImplUiBinder extends UiBinder<Widget, FilterPropertiesPanelViewImpl> {
    }

    @UiField
    ListBox conditionType;
    @UiField
    TextBox source;
    @UiField
    TextBox regularExpression;

    @Inject
    public FilterPropertiesPanelViewImpl(FilterPropertiesPanelViewImplUiBinder ourUiBinder) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Override
    public String getConditionType() {
        int index = conditionType.getSelectedIndex();
        return index != -1 ? conditionType.getValue(conditionType.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setConditionType(List<String> conditionType) {
        if (conditionType == null) {
            return;
        }
        this.conditionType.clear();
        for (String value : conditionType) {
            this.conditionType.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectConditionType(String conditionType) {
        for (int i = 0; i < this.conditionType.getItemCount(); i++) {
            if (this.conditionType.getValue(i).equals(conditionType)) {
                this.conditionType.setItemSelected(i, true);
                return;
            }
        }
    }

    @UiHandler("conditionType")
    public void onConditionTypeChanged(ChangeEvent event) {
        delegate.onConditionTypeChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getSource() {
        return String.valueOf(source.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setSource(String source) {
        this.source.setText(source);
    }

    @UiHandler("source")
    public void onSourceChanged(KeyUpEvent event) {
        delegate.onSourceChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getRegularExpression() {
        return String.valueOf(regularExpression.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setRegularExpression(String regularExpression) {
        this.regularExpression.setText(regularExpression);
    }

    @UiHandler("regularExpression")
    public void onRegularExpressionChanged(KeyUpEvent event) {
        delegate.onRegularExpressionChanged();
    }

}