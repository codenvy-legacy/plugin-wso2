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
package com.codenvy.ide.client.propertiespanel.connectors.base;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * The implementation of {BaseConnectorView}
 *
 * @author Valeriy Svydenko
 */
public class BaseConnectorPanelViewImpl extends BaseConnectorPanelView {

    @Singleton
    interface BaseConnectorViewImplUiBinder extends UiBinder<Widget, BaseConnectorPanelViewImpl> {
    }

    @UiField
    TextBox configRef;
    @UiField
    ListBox availableConfigs;
    @UiField
    TextBox newConfig;
    @UiField
    Button  newConfigButton;
    @UiField
    ListBox parameterEditorType;

    @UiField(provided = true)
    final EditorResources                res;
    @UiField(provided = true)
    final WSO2EditorLocalizationConstant loc;

    @Inject
    public BaseConnectorPanelViewImpl(BaseConnectorViewImplUiBinder ourUiBinder,
                                      EditorResources res,
                                      WSO2EditorLocalizationConstant loc) {
        this.res = res;
        this.loc = loc;

        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getParameterEditorType() {
        return getSelectedItem(parameterEditorType);
    }

    /** {@inheritDoc} */
    @Override
    public void selectParameterEditorType(@Nonnull String state) {
        selectType(parameterEditorType, state);
    }

    /** {@inheritDoc} */
    @Override
    public void setParameterEditorType(@Nonnull List<String> states) {
        setTypes(parameterEditorType, states);
    }

    /** {@inheritDoc} */
    @Override
    public void setNewConfig(@Nonnull String newConfig) {
        this.newConfig.setText(newConfig);
    }

    /** {@inheritDoc} */
    @Override
    public void selectAvailableConfigs(@Nonnull String state) {
        selectType(availableConfigs, state);
    }

    /** {@inheritDoc} */
    @Override
    public void addAvailableConfigs(@Nonnull String state) {
        availableConfigs.addItem(state);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getConfigRef() {
        return configRef.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setConfigRef(@Nonnull String configRef) {
        this.configRef.setText(configRef);
    }

    @UiHandler("parameterEditorType")
    public void onPayloadFormatChanged(ChangeEvent event) {
        delegate.onParameterEditorTypeChanged(parameterEditorType.getValue(parameterEditorType.getSelectedIndex()));
    }

    @UiHandler("availableConfigs")
    public void onAvailableConfigsChanged(ChangeEvent event) {
        delegate.onAvailableConfigsChanged(getSelectedItem(availableConfigs));
    }

    @UiHandler("configRef")
    public void onConfigRefChanged(KeyUpEvent event) {
        delegate.onConfigRefChanged(configRef.getValue());
    }

    @UiHandler("newConfigButton")
    public void onConfigButtonClicked(ClickEvent event) {
        delegate.showConfigParameterWindow();
    }

}
