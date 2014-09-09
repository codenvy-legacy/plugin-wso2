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
package com.codenvy.ide.client.propertiespanel.addressendpoint;

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
import java.util.List;

/**
 * Provides a graphical representation of 'Address endpoint' property panel for editing property of 'Address' endpoint.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class AddressEndpointPropertiesPanelViewImpl extends AddressEndpointPropertiesPanelView {

    @Singleton
    interface AddressEndpointPropertiesPanelViewImplUiBinder extends UiBinder<Widget, AddressEndpointPropertiesPanelViewImpl> {
    }

    @UiField
    ListBox format;
    @UiField
    TextBox uri;

    @UiField
    TextBox suspendErrorCodes;
    @UiField
    TextBox suspendInitialDuration;
    @UiField
    TextBox suspendMaximumDuration;
    @UiField
    TextBox suspendProgressionFactory;

    @UiField
    TextBox retryErrorCodes;
    @UiField
    TextBox retryCount;
    @UiField
    TextBox retryDelay;

    @UiField
    TextBox properties;
    @UiField
    Button  btnProperties;
    @UiField
    ListBox optimize;
    @UiField
    TextBox description;

    @UiField
    ListBox   reliableMessagingEnabled;
    @UiField
    FlowPanel reliableMessagingPolicyPanel;
    @UiField
    TextBox   reliableMessagingPolicy;

    @UiField
    ListBox   securityEnabled;
    @UiField
    FlowPanel securityPolicyPanel;
    @UiField
    TextBox   securityPolicy;
    @UiField
    ListBox   addressingEnabled;
    @UiField
    FlowPanel addressingPanel;
    @UiField
    ListBox   addressingVersion;
    @UiField
    ListBox   addressingSeparateListener;

    @UiField
    TextBox timeoutDuration;
    @UiField
    ListBox timeoutAction;

    @UiField(provided = true)
    final EditorResources                res;
    @UiField(provided = true)
    final WSO2EditorLocalizationConstant local;

    @Inject
    public AddressEndpointPropertiesPanelViewImpl(AddressEndpointPropertiesPanelViewImplUiBinder ourUiBinder,
                                                  EditorResources res,
                                                  WSO2EditorLocalizationConstant local) {
        this.res = res;
        this.local = local;

        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Override
    public void setFormats(@Nonnull List<String> formats) {
        setTypes(format, formats);
    }

    /** {@inheritDoc} */
    @Override
    public void selectFormat(@Nonnull String format) {
        selectType(this.format, format);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getFormat() {
        return getSelectedItem(format);
    }

    /** {@inheritDoc} */
    @Override
    public void setUri(@Nonnull String uri) {
        this.uri.setText(uri);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getUri() {
        return uri.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setSuspendErrorCodes(@Nonnull String suspendErrorCodes) {
        this.suspendErrorCodes.setText(suspendErrorCodes);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getSuspendErrorCodes() {
        return suspendErrorCodes.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setSuspendInitialDuration(@Nonnull String suspendInitialDuration) {
        this.suspendInitialDuration.setText(suspendInitialDuration);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getSuspendInitialDuration() {
        return suspendInitialDuration.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setSuspendMaximumDuration(@Nonnull String suspendMaximumDuration) {
        this.suspendMaximumDuration.setText(suspendMaximumDuration);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getSuspendMaximumDuration() {
        return suspendMaximumDuration.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setSuspendProgressionFactory(@Nonnull String suspendProgressionFactory) {
        this.suspendProgressionFactory.setText(suspendProgressionFactory);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getSuspendProgressionFactory() {
        return suspendProgressionFactory.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setRetryErrorCodes(@Nonnull String retryErrorCodes) {
        this.retryErrorCodes.setText(retryErrorCodes);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getRetryErrorCodes() {
        return retryErrorCodes.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setRetryCount(@Nonnull String retryCount) {
        this.retryCount.setText(retryCount);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getRetryCount() {
        return retryCount.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setRetryDelay(@Nonnull String retryDelay) {
        this.retryDelay.setText(retryDelay);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getRetryDelay() {
        return retryDelay.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setProperties(@Nonnull String properties) {
        this.properties.setText(properties);
    }

    /** {@inheritDoc} */
    @Override
    public void setOptimizes(@Nonnull List<String> optimizes) {
        setTypes(optimize, optimizes);
    }

    /** {@inheritDoc} */
    @Override
    public void selectOptimize(@Nonnull String optimize) {
        selectType(this.optimize, optimize);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getOptimize() {
        return getSelectedItem(optimize);
    }

    /** {@inheritDoc} */
    @Override
    public void setDescription(@Nonnull String description) {
        this.description.setText(description);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getDescription() {
        return description.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setReliableMessagingEnabledStates(@Nonnull List<String> states) {
        setTypes(reliableMessagingEnabled, states);
    }

    /** {@inheritDoc} */
    @Override
    public void selectReliableMessagingEnabledState(@Nonnull String state) {
        selectType(reliableMessagingEnabled, state);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getReliableMessagingEnabled() {
        return getSelectedItem(reliableMessagingEnabled);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleReliableMessagingEnabledPanel(boolean visible) {
        reliableMessagingPolicyPanel.setVisible(visible);
    }

    /** {@inheritDoc} */
    @Override
    public void setReliableMessagingPolicy(@Nonnull String reliableMessagingPolicy) {
        this.reliableMessagingPolicy.setText(reliableMessagingPolicy);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getReliableMessagingPolicy() {
        return reliableMessagingPolicy.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setSecurityEnabledStates(@Nonnull List<String> states) {
        setTypes(securityEnabled, states);
    }

    /** {@inheritDoc} */
    @Override
    public void selectSecurityEnabledState(@Nonnull String state) {
        selectType(securityEnabled, state);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getSecurityEnabled() {
        return getSelectedItem(securityEnabled);
    }

    /** {@inheritDoc} */
    @Override
    public void setSecurityPolicy(@Nonnull String securityPolicy) {
        this.securityPolicy.setText(securityPolicy);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getSecurityPolicy() {
        return securityPolicy.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleSecurityPolicyPanel(boolean visible) {
        securityPolicyPanel.setVisible(visible);
    }

    /** {@inheritDoc} */
    @Override
    public void setAddressingEnabledStates(@Nonnull List<String> states) {
        setTypes(addressingEnabled, states);
    }

    /** {@inheritDoc} */
    @Override
    public void selectAddressingEnabledState(@Nonnull String state) {
        selectType(addressingEnabled, state);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getAddressingEnabled() {
        return getSelectedItem(addressingEnabled);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleAddressingPanel(boolean visible) {
        addressingPanel.setVisible(visible);
    }

    /** {@inheritDoc} */
    @Override
    public void setAddressingVersions(@Nonnull List<String> addressingVersions) {
        setTypes(addressingVersion, addressingVersions);
    }

    /** {@inheritDoc} */
    @Override
    public void selectAddressingVersion(@Nonnull String addressingVersion) {
        selectType(this.addressingVersion, addressingVersion);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getAddressingVersion() {
        return getSelectedItem(addressingVersion);
    }

    /** {@inheritDoc} */
    @Override
    public void setAddressingSeparatorListenerStates(@Nonnull List<String> states) {
        setTypes(addressingSeparateListener, states);
    }

    /** {@inheritDoc} */
    @Override
    public void selectAddressingSeparatorListenerState(@Nonnull String state) {
        selectType(addressingSeparateListener, state);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getAddressingSeparatorListener() {
        return getSelectedItem(addressingSeparateListener);
    }

    /** {@inheritDoc} */
    @Override
    public void setTimeoutDuration(@Nonnull String timeoutDuration) {
        this.timeoutDuration.setText(timeoutDuration);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getTimeoutDuration() {
        return timeoutDuration.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setTimeoutActions(@Nonnull List<String> actions) {
        setTypes(timeoutAction, actions);
    }

    /** {@inheritDoc} */
    @Override
    public void selectTimeoutAction(@Nonnull String action) {
        selectType(timeoutAction, action);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getTimeoutAction() {
        return getSelectedItem(timeoutAction);
    }

    @UiHandler("format")
    public void onFormatChanged(ChangeEvent event) {
        delegate.onFormatChanged();
    }

    @UiHandler("uri")
    public void onUriChanged(KeyUpEvent event) {
        delegate.onUriChanged();
    }

    @UiHandler("suspendErrorCodes")
    public void onSuspendErrorCodesChanged(KeyUpEvent event) {
        delegate.onSuspendErrorCodesChanged();
    }

    @UiHandler("suspendInitialDuration")
    public void onSuspendInitialDuration(KeyUpEvent event) {
        delegate.onSuspendInitialDurationChanged();
    }

    @UiHandler("suspendMaximumDuration")
    public void onSuspendMaximumDuration(KeyUpEvent event) {
        delegate.onSuspendMaximumDurationChanged();
    }

    @UiHandler("suspendProgressionFactory")
    public void onSuspendProgressionFactory(KeyUpEvent event) {
        delegate.onSuspendProgressionFactoryChanged();
    }

    @UiHandler("retryErrorCodes")
    public void onRetryErrorCodesChanged(KeyUpEvent event) {
        delegate.onRetryErrorCodesChanged();
    }

    @UiHandler("retryCount")
    public void onRetryCountChanged(KeyUpEvent event) {
        delegate.onRetryCountChanged();
    }

    @UiHandler("retryDelay")
    public void onRetryDelayChanged(KeyUpEvent event) {
        delegate.onRetryDelayChanged();
    }

    @UiHandler("btnProperties")
    public void onEditPropertiesButtonClicked(ClickEvent event) {
        delegate.onEditPropertiesButtonClicked();
    }

    @UiHandler("optimize")
    public void onOptimizeChanged(ChangeEvent event) {
        delegate.onOptimizeChanged();
    }

    @UiHandler("description")
    public void onDescriptionChanged(KeyUpEvent event) {
        delegate.onDescriptionChanged();
    }

    @UiHandler("reliableMessagingEnabled")
    public void onReliableMessagingEnabledChanged(ChangeEvent event) {
        delegate.onReliableMessagingEnabledChanged();
    }

    @UiHandler("reliableMessagingPolicy")
    public void onReliableMessagingPolicyChanged(KeyUpEvent event) {
        delegate.onReliableMessagingPolicyChanged();
    }

    @UiHandler("securityEnabled")
    public void onSecurityEnabledChanged(ChangeEvent event) {
        delegate.onSecurityEnabledChanged();
    }

    @UiHandler("securityPolicy")
    public void onSecurityPolicyChanged(KeyUpEvent event) {
        delegate.onSecurityPolicyChanged();
    }

    @UiHandler("addressingEnabled")
    public void onAddressingEnabledChanged(ChangeEvent event) {
        delegate.onAddressingEnabledChanged();
    }

    @UiHandler("addressingVersion")
    public void onAddressingVersionChanged(ChangeEvent event) {
        delegate.onAddressingVersionChanged();
    }

    @UiHandler("addressingSeparateListener")
    public void onAddressingSeparatorListenerChanged(ChangeEvent event) {
        delegate.onAddressingSeparatorListenerChanged();
    }

    @UiHandler("timeoutDuration")
    public void onTimeoutDurationChanged(KeyUpEvent event) {
        delegate.onTimeoutDurationChanged();
    }

    @UiHandler("timeoutAction")
    public void onTimeoutActionChanged(ChangeEvent event) {
        delegate.onTimeoutActionChanged();
    }

}