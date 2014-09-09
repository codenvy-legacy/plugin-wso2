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
package com.codenvy.ide.client.toolbar;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Provides a graphical representation of tool bar.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
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

    @UiHandler("salesForceInitConnector")
    public void onSalesForceInitClicked(ClickEvent event) {
        delegate.onSalesForceInitClicked();
    }

    @UiHandler("salesForceCreateConnector")
    public void onSalesForceCreateClicked(ClickEvent event) {
        delegate.onSalesForceCreateClicked();
    }

    @UiHandler("salesForceDeleteConnector")
    public void onSalesForceDeleteClicked(ClickEvent event) {
        delegate.onSalesForceDeleteClicked();
    }

    @UiHandler("salesForceUpdateConnector")
    public void onSalesForceUpdateClicked(ClickEvent event) {
        delegate.onSalesForceUpdateClicked();
    }

    @UiHandler("salesForceDescribeGlobalConnector")
    public void onSalesForceDescribeGlobalClicked(ClickEvent event) {
        delegate.onSalesForceDescribeGlobalClicked();
    }

    @UiHandler("salesForceDescribeSobjectConnector")
    public void onSalesForceDescribeSubjectClicked(ClickEvent event) {
        delegate.onSalesForceDescribeSubjectClicked();
    }

    @UiHandler("salesForceDescribeSobjectsConnector")
    public void onSalesForceDescribeSubjectsClicked(ClickEvent event) {
        delegate.onSalesForceDescribeSubjectsClicked();
    }

    @UiHandler("salesForceQueryConnector")
    public void onSalesForceQueryClicked(ClickEvent event) {
        delegate.onSalesForceQueryClicked();
    }

    @UiHandler("salesForceQueryAllConnector")
    public void onSalesForceQueryAllClicked(ClickEvent event) {
        delegate.onSalesForceQueryAllClicked();
    }

    @UiHandler("salesForceQueryMoreConnector")
    public void onSalesForceQueryMoreClicked(ClickEvent event) {
        delegate.onSalesForceQueryMoreClicked();
    }

    @UiHandler("salesForceResetPasswordConnector")
    public void onSalesForceResetPasswordClicked(ClickEvent event) {
        delegate.onSalesForceResetPasswordClicked();
    }

    @UiHandler("salesForceRetriveConnector")
    public void onSalesForceRetriveClicked(ClickEvent event) {
        delegate.onSalesForceRetriveClicked();
    }

    @UiHandler("salesForceSearchConnector")
    public void onSalesForceSearchClicked(ClickEvent event) {
        delegate.onSalesForceSearchClicked();
    }

    @UiHandler("salesForceSendEmailConnector")
    public void onSalesForceSendEmailClicked(ClickEvent event) {
        delegate.onSalesForceSendEmailClicked();
    }

    @UiHandler("salesForceSendEmailMessageConnector")
    public void onSalesForceSendEmailMessageClicked(ClickEvent event) {
        delegate.onSalesForceSendEmailMessageClicked();
    }

    @UiHandler("salesForceSetPasswordConnector")
    public void onSalesForceSetPasswordClicked(ClickEvent event) {
        delegate.onSalesForceSetPasswordClicked();
    }

    @UiHandler("salesForceUndeleteConnector")
    public void onSalesForceUndeleteClicked(ClickEvent event) {
        delegate.onSalesForceUndeleteClicked();
    }

    @UiHandler("salesForceUpsetConnector")
    public void onSalesForceUpsetClicked(ClickEvent event) {
        delegate.onSalesForceUpsetClicked();
    }

    @UiHandler("emptyRecycleConnector")
    public void onSalesForceEmptyRecycleBinClicked(ClickEvent event) {
        delegate.onSalesForceEmptyRecycleBinClicked();
    }

    @UiHandler("logOutConnector")
    public void onSalesForceLogOutClicked(ClickEvent event) {
        delegate.onSalesForceLogOutClicked();
    }

    @UiHandler("getUserInfoConnector")
    public void onSalesForceGetUserInfoClicked(ClickEvent event) {
        delegate.onSalesForceGetUserInfoClicked();
    }

}