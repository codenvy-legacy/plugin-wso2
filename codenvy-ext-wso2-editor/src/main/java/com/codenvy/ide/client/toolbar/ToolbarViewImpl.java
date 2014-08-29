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
package com.codenvy.ide.client.toolbar;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class ToolbarViewImpl extends ToolbarView {

    @Singleton
    interface ToolbarViewImplUiBinder extends UiBinder<Widget, ToolbarViewImpl> {
    }

    @Inject
    public ToolbarViewImpl(ToolbarViewImplUiBinder ourUiBinder) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @UiHandler("log")
    public void onLogButtonClicked(ClickEvent event) {
        delegate.onLogButtonClicked();
    }

    @UiHandler("property")
    public void onPropertyButtonClicked(ClickEvent event) {
        delegate.onPropertyButtonClicked();
    }

    @UiHandler("payloadFactory")
    public void onPayloadFactoryButtonClicked(ClickEvent event) {
        delegate.onPayloadFactoryButtonClicked();
    }

    @UiHandler("send")
    public void onSendButtonClicked(ClickEvent event) {
        delegate.onSendButtonClicked();
    }

    @UiHandler("header")
    public void onHeaderButtonClicked(ClickEvent event) {
        delegate.onHeaderButtonClicked();
    }

    @UiHandler("respond")
    public void onRespondButtonClicked(ClickEvent event) {
        delegate.onRespondButtonClicked();
    }

    @UiHandler("filter")
    public void onFilterButtonClicked(ClickEvent event) {
        delegate.onFilterButtonClicked();
    }

    @UiHandler("switchMediator")
    public void onSwitch_mediatorButtonClicked(ClickEvent event) {
        delegate.onSwitchButtonClicked();
    }

    @UiHandler("sequence")
    public void onSequenceButtonClicked(ClickEvent event) {
        delegate.onSequenceButtonClicked();
    }

    @UiHandler("enrich")
    public void onEnrichButtonClicked(ClickEvent event) {
        delegate.onEnrichButtonClicked();
    }

    @UiHandler("loopBack")
    public void onLoopBackButtonClicked(ClickEvent event) {
        delegate.onLoopBackButtonClicked();
    }

    @UiHandler("callTemplate")
    public void onCallTemplateButtonClicked(ClickEvent event) {
        delegate.onCallTemplateButtonClicked();
    }

    @UiHandler("call")
    public void onCallButtonClicked(ClickEvent event) {
        delegate.onCallButtonClicked();
    }

    @UiHandler("addressEndpoint")
    public void onAddressEndpointButtonClicked(ClickEvent event) {
        delegate.onAddressEndpointButtonClicked();
    }

    @UiHandler("salesforceInitConnector")
    public void onSalesforceInitClicked(ClickEvent event) {
        delegate.onSalesforceInitClicked();
    }

    @UiHandler("salesforceCreateConnector")
    public void onSalesforceCreateClicked(ClickEvent event) {
        delegate.onSalesforceCreateClicked();
    }

    @UiHandler("salesforceUpdateConnector")
    public void onSalesforceUpdateClicked(ClickEvent event) {
        delegate.onSalesforceUpdateClicked();
    }

}