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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Andrey Plotnikov
 */
public class CallPropertiesPanelViewImpl extends CallPropertiesPanelView {

    interface CallPropertiesPanelViewImplUiBinder extends UiBinder<Widget, CallPropertiesPanelViewImpl> {
    }

    @UiField
    ListBox   endpointType;
    @UiField
    TextBox   description;
    @UiField
    TextBox   endpointRegistryKey;
    @UiField
    TextBox   endpointXpath;
    @UiField
    FlowPanel endpointRegistryKeyPanel;
    @UiField
    FlowPanel endpointXpathPanel;

    @Inject
    public CallPropertiesPanelViewImpl(CallPropertiesPanelViewImplUiBinder ourUiBinder) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getEndpointType() {
        int index = endpointType.getSelectedIndex();
        return index != -1 ? endpointType.getValue(endpointType.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setEndpointTypes(@Nullable List<String> endpointTypes) {
        if (endpointTypes == null) {
            return;
        }

        this.endpointType.clear();

        for (String value : endpointTypes) {
            this.endpointType.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getEndpointRegistryKey() {
        return endpointRegistryKey.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setEndpointRegistryKey(@Nonnull String registryKey) {
        this.endpointRegistryKey.setText(registryKey);
    }

    /** {@inheritDoc} */
    @Override
    public void setEndpointXpath(@Nullable String xpath) {
        endpointXpath.setText(xpath);
    }

    /** {@inheritDoc} */
    @Override
    public void selectEndpointType(@Nonnull String endpointType) {
        for (int i = 0; i < this.endpointType.getItemCount(); i++) {
            if (this.endpointType.getValue(i).equals(endpointType)) {
                this.endpointType.setItemSelected(i, true);
                return;
            }
        }
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getDescription() {
        return String.valueOf(description.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setDescription(@Nonnull String description) {
        this.description.setText(description);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleEndpointRegistryKeyPanel(boolean visible) {
        endpointRegistryKeyPanel.setVisible(visible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleEndpointXpathPanel(boolean visible) {
        endpointXpathPanel.setVisible(visible);
    }

    @UiHandler("endpointType")
    public void onEndpointTypeChanged(ChangeEvent event) {
        delegate.onEndpointTypeChanged();
    }

    @UiHandler("endpointRegistryKey")
    public void onEndpointRegisterKeyChanged(KeyUpEvent event) {
        delegate.onEndpointRegisterKeyChanged();
    }

    @UiHandler("description")
    public void onDescriptionChanged(KeyUpEvent event) {
        delegate.onDescriptionChanged();
    }

    @UiHandler("registryXpathButton")
    public void onEditRegistryXpathButtonClicked(ClickEvent event) {
        delegate.onEditRegistryXpathButtonClicked();
    }

}