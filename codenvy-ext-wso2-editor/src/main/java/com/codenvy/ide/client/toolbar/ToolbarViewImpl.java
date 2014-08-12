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

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class ToolbarViewImpl extends ToolbarView {

    interface ToolbarViewImplUiBinder extends UiBinder<Widget, ToolbarViewImpl> {
    }

    @UiField(provided = true)
    PushButton      log;
    @UiField(provided = true)
    PushButton      property;
    @UiField(provided = true)
    PushButton      payloadFactory;
    @UiField(provided = true)
    PushButton      send;
    @UiField(provided = true)
    PushButton      header;
    @UiField(provided = true)
    PushButton      respond;
    @UiField(provided = true)
    PushButton      filter;
    @UiField(provided = true)
    PushButton      switch_mediator;
    @UiField(provided = true)
    PushButton      sequence;
    @UiField(provided = true)
    PushButton      enrich;
    @UiField(provided = true)
    PushButton      loopBack;
    // TODO
    @UiField
    PushButton      callTemplate;
    @UiField(provided = true)
    PushButton      call;
    @UiField
    DockLayoutPanel mainPanel;

    @Inject
    public ToolbarViewImpl(ToolbarViewImplUiBinder ourUiBinder,
                           EditorResources resources,
                           WSO2EditorLocalizationConstant localizationConstant) {
        log = new PushButton(new Image(resources.logToolbar()));
        log.setTitle(localizationConstant.toolbarLogTooltip());

        property = new PushButton(new Image(resources.propertyToolbar()));
        property.setTitle(localizationConstant.toolbarPropertyTooltip());

        payloadFactory = new PushButton(new Image(resources.payloadFactoryToolbar()));
        payloadFactory.setTitle(localizationConstant.toolbarPayloadFactoryTooltip());

        send = new PushButton(new Image(resources.sendToolbar()));
        send.setTitle(localizationConstant.toolbarSendTooltip());

        header = new PushButton(new Image(resources.headerToolbar()));
        header.setTitle(localizationConstant.toolbarHeaderTooltip());

        respond = new PushButton(new Image(resources.respondToolbar()));
        respond.setTitle(localizationConstant.toolbarRespondTooltip());

        filter = new PushButton(new Image(resources.filterToolbar()));
        filter.setTitle(localizationConstant.toolbarFilterTooltip());

        switch_mediator = new PushButton(new Image(resources.switch_mediatorToolbar()));
        switch_mediator.setTitle(localizationConstant.toolbarSwitchTooltip());

        sequence = new PushButton(new Image(resources.sequenceToolbar()));
        sequence.setTitle(localizationConstant.toolbarSequenceTooltip());

        enrich = new PushButton(new Image(resources.enrichToolbar()));
        enrich.setTitle(localizationConstant.toolbarEnrichTooltip());

        loopBack = new PushButton(new Image(resources.loopBackToolbar()));
        loopBack.setTitle(localizationConstant.toolbarLoopBackTooltip());

//        callTemplate = new PushButton(new Image(resources.callTemplateToolbar()));
//        callTemplate.setTitle(localizationConstant.toolbarCallTemplateTooltip());

        call = new PushButton(new Image(resources.callToolbar()));
        call.setTitle(localizationConstant.toolbarCallTooltip());

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

    @UiHandler("switch_mediator")
    public void onSwitch_mediatorButtonClicked(ClickEvent event) {
        delegate.onSwitch_mediatorButtonClicked();
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

}