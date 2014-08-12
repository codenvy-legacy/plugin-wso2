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
package com.codenvy.ide.client.propertiespanel.call;

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
public class CallPropertiesPanelViewImpl extends CallPropertiesPanelView {

    interface CallPropertiesPanelViewImplUiBinder extends UiBinder<Widget, CallPropertiesPanelViewImpl> {
    }

    @UiField
    ListBox endpointType;
    @UiField
    TextBox description;

    @Inject
    public CallPropertiesPanelViewImpl(CallPropertiesPanelViewImplUiBinder ourUiBinder) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Override
    public String getEndpointType() {
        int index = endpointType.getSelectedIndex();
        return index != -1 ? endpointType.getValue(endpointType.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setEndpointType(List<String> endpointType) {
        if (endpointType == null) {
            return;
        }
        this.endpointType.clear();
        for (String value : endpointType) {
            this.endpointType.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectEndpointType(String endpointType) {
        for (int i = 0; i < this.endpointType.getItemCount(); i++) {
            if (this.endpointType.getValue(i).equals(endpointType)) {
                this.endpointType.setItemSelected(i, true);
                return;
            }
        }
    }

    @UiHandler("endpointType")
    public void onEndpointTypeChanged(ChangeEvent event) {
        delegate.onEndpointTypeChanged();
    }

    @Override
    public String getDescription() {
        return String.valueOf(description.getText());
    }

    @Override
    public void setDescription(String description) {
        this.description.setText(description);
    }

    @UiHandler("description")
    public void onDescriptionChanged(KeyUpEvent event) {
        delegate.onDescriptionChanged();
    }

}