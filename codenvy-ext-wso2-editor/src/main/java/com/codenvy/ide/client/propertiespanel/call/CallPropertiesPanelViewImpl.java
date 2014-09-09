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
package com.codenvy.ide.client.propertiespanel.call;

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
 * Provides a graphical representation of 'Call' property panel for editing property of 'Call' mediator.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class CallPropertiesPanelViewImpl extends CallPropertiesPanelView {

    @Singleton
    interface CallPropertiesPanelViewImplUiBinder extends UiBinder<Widget, CallPropertiesPanelViewImpl> {
    }

    @UiField
    ListBox endpointType;
    @UiField
    TextBox description;
    @UiField
    TextBox endpointRegistryKey;
    @UiField
    TextBox endpointXpath;

    @UiField
    FlowPanel endpointRegistryKeyPanel;
    @UiField
    FlowPanel endpointXpathPanel;

    @UiField(provided = true)
    final EditorResources                res;
    @UiField(provided = true)
    final WSO2EditorLocalizationConstant locale;

    @Inject
    public CallPropertiesPanelViewImpl(CallPropertiesPanelViewImplUiBinder ourUiBinder,
                                       EditorResources res,
                                       WSO2EditorLocalizationConstant locale) {
        this.res = res;
        this.locale = locale;

        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getEndpointType() {
        return getSelectedItem(endpointType);
    }

    /** {@inheritDoc} */
    @Override
    public void setEndpointTypes(@Nullable List<String> endpointTypes) {
        setTypes(endpointType, endpointTypes);
    }

    /** {@inheritDoc} */
    @Override
    public void selectEndpointType(@Nonnull String endpointType) {
        selectType(this.endpointType, endpointType);
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
    @Nonnull
    @Override
    public String getDescription() {
        return description.getText();
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