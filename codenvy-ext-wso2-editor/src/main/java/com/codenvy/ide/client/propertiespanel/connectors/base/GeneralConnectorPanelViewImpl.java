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
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import java.util.List;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType;

/**
 * Provides a general graphical representation for all SalesForce connectors. The class contains methods which allows
 * to display properties panel depending on the number of element parameters .
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class GeneralConnectorPanelViewImpl extends GeneralConnectorPanelView {

    @Singleton
    interface BaseConnectorViewImplUiBinder extends UiBinder<Widget, GeneralConnectorPanelViewImpl> {
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

    @UiField
    FlowPanel firstPanel;
    @UiField
    Label     firstLabel;
    @UiField
    TextBox   firstTextBox;
    @UiField
    Button    firstButton;

    @UiField
    FlowPanel secondPanel;
    @UiField
    Label     secondLabel;
    @UiField
    TextBox   secondTextBox;
    @UiField
    Button    secondButton;

    @UiField
    FlowPanel thirdPanel;
    @UiField
    Label     thirdLabel;
    @UiField
    TextBox   thirdTextBox;
    @UiField
    Button    thirdButton;

    @UiField
    FlowPanel fourthPanel;
    @UiField
    Label     fourthLabel;
    @UiField
    TextBox   fourthTextBox;
    @UiField
    Button    fourthButton;

    @UiField(provided = true)
    final EditorResources                res;
    @UiField(provided = true)
    final WSO2EditorLocalizationConstant loc;

    @Inject
    public GeneralConnectorPanelViewImpl(BaseConnectorViewImplUiBinder ourUiBinder,
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
    @Nonnull
    @Override
    public String getAvailableConfig() {
        return getSelectedItem(availableConfigs);
    }

    /** {@inheritDoc} */
    @Override
    public void setParameterEditorType(@Nonnull List<String> states) {
        setTypes(parameterEditorType, states);
    }

    /** {@inheritDoc} */
    @Override
    public void setAvailableConfigs(@Nonnull List<String> configs) {
        setTypes(availableConfigs, configs);
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

    /** {@inheritDoc} */
    @Override
    public void selectParameterEditorType(@Nonnull ParameterEditorType parameterEditorType) {
        selectType(this.parameterEditorType, parameterEditorType.name());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getFirstTextBoxValue() {
        return firstTextBox.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setFirstTextBoxValue(@Nonnull String value) {
        firstTextBox.setText(value);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getSecondTextBoxValue() {
        return secondTextBox.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setSecondTextBoxValue(@Nonnull String value) {
        secondTextBox.setText(value);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getThirdTextBoxValue() {
        return thirdTextBox.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setThirdTextBoxValue(@Nonnull String value) {
        thirdTextBox.setText(value);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getFourthTextBoxValue() {
        return fourthTextBox.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setFourthTextBoxValue(@Nonnull String value) {
        fourthTextBox.setText(value);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleFirstPanel(boolean isVisible) {
        firstPanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleSecondPanel(boolean isVisible) {
        secondPanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleThirdPanel(boolean isVisible) {
        thirdPanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleFourthPanel(boolean isVisible) {
        fourthPanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setFirstLabelTitle(@Nonnull String title) {
        firstLabel.setText(title);
    }

    /** {@inheritDoc} */
    @Override
    public void setSecondLabelTitle(@Nonnull String title) {
        secondLabel.setText(title);
    }

    /** {@inheritDoc} */
    @Override
    public void setThirdLabelTitle(@Nonnull String title) {
        thirdLabel.setText(title);
    }

    /** {@inheritDoc} */
    @Override
    public void setFourthLabelTitle(@Nonnull String title) {
        fourthLabel.setText(title);
    }

    /** {@inheritDoc} */
    @Override
    public void setEnableFirstTextBox(boolean isEnable) {
        firstTextBox.setEnabled(isEnable);
    }

    /** {@inheritDoc} */
    @Override
    public void setEnableSecondTextBox(boolean isEnable) {
        secondTextBox.setEnabled(isEnable);
    }

    /** {@inheritDoc} */
    @Override
    public void setEnableThirdTextBox(boolean isEnable) {
        thirdTextBox.setEnabled(isEnable);
    }

    /** {@inheritDoc} */
    @Override
    public void setEnableFourthTextBox(boolean isEnable) {
        fourthTextBox.setEnabled(isEnable);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleFirstButton(boolean isVisible) {
        firstButton.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleSecondButton(boolean isVisible) {
        secondButton.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleThirdButton(boolean isVisible) {
        thirdButton.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleFourthButton(boolean isVisible) {
        fourthButton.setVisible(isVisible);
    }

    @UiHandler("parameterEditorType")
    public void onPayloadFormatChanged(ChangeEvent event) {
        delegate.onParameterEditorTypeChanged();
    }

    @UiHandler("availableConfigs")
    public void onAvailableConfigsChanged(ChangeEvent event) {
        delegate.onAvailableConfigsChanged();
    }

    @UiHandler("configRef")
    public void onConfigRefChanged(KeyUpEvent event) {
        delegate.onConfigRefChanged();
    }

    @UiHandler("newConfigButton")
    public void onConfigButtonClicked(ClickEvent event) {
        delegate.onCreateNewConfigBtnClicked();
    }

    @UiHandler("firstTextBox")
    public void onFirstTextBoxValueChanged(KeyUpEvent event) {
        delegate.onFirstTextBoxValueChanged();
    }

    @UiHandler("firstButton")
    public void onFirstPanelBtnClicked(ClickEvent event) {
        delegate.onFirstButtonClicked();
    }

    @UiHandler("secondTextBox")
    public void onSecondTextBoxValueChanged(KeyUpEvent event) {
        delegate.onSecondTextBoxValueChanged();
    }

    @UiHandler("secondButton")
    public void onSecondPanelBtnClicked(ClickEvent event) {
        delegate.onSecondButtonClicked();
    }

    @UiHandler("thirdTextBox")
    public void onThirdTextBoxValueChanged(KeyUpEvent event) {
        delegate.onThirdTextBoxValueChanged();
    }

    @UiHandler("thirdButton")
    public void onThirdPanelBtnClicked(ClickEvent event) {
        delegate.onThirdButtonClicked();
    }

    @UiHandler("fourthTextBox")
    public void onFourthTextBoxValueChanged(KeyUpEvent event) {
        delegate.onFourthTextBoxValueChanged();
    }

    @UiHandler("fourthButton")
    public void onFourthPanelBtnClicked(ClickEvent event) {
        delegate.onFourthButtonClicked();
    }

}
