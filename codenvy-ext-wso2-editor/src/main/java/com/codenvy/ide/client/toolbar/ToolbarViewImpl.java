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
import com.codenvy.ide.client.elements.Call;
import com.codenvy.ide.client.elements.CallTemplate;
import com.codenvy.ide.client.elements.Enrich;
import com.codenvy.ide.client.elements.Filter;
import com.codenvy.ide.client.elements.Header;
import com.codenvy.ide.client.elements.Log;
import com.codenvy.ide.client.elements.LoopBack;
import com.codenvy.ide.client.elements.PayloadFactory;
import com.codenvy.ide.client.elements.Property;
import com.codenvy.ide.client.elements.Respond;
import com.codenvy.ide.client.elements.Send;
import com.codenvy.ide.client.elements.Sequence;
import com.codenvy.ide.client.elements.Switch_mediator;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Andrey Plotnikov
 */
public class ToolbarViewImpl extends ToolbarView {

    interface ToolbarViewImplUiBinder extends UiBinder<Widget, ToolbarViewImpl> {
    }

    private static final int ITEM_HEIGHT = 37;

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
    @UiField(provided = true)
    PushButton      callTemplate;
    @UiField(provided = true)
    PushButton      call;
    @UiField(provided = true)
    PushButton      connection;
    @UiField
    DockLayoutPanel mainPanel;

    private final Map<String, PushButton> buttons;

    @Inject
    public ToolbarViewImpl(ToolbarViewImplUiBinder ourUiBinder, EditorResources resources) {
        buttons = new LinkedHashMap<>();

        log = new PushButton(new Image(resources.logToolbar()));
        property = new PushButton(new Image(resources.propertyToolbar()));
        payloadFactory = new PushButton(new Image(resources.payloadFactoryToolbar()));
        send = new PushButton(new Image(resources.sendToolbar()));
        header = new PushButton(new Image(resources.headerToolbar()));
        respond = new PushButton(new Image(resources.respondToolbar()));
        filter = new PushButton(new Image(resources.filterToolbar()));
        switch_mediator = new PushButton(new Image(resources.switch_mediatorToolbar()));
        sequence = new PushButton(new Image(resources.sequenceToolbar()));
        enrich = new PushButton(new Image(resources.enrichToolbar()));
        loopBack = new PushButton(new Image(resources.loopBackToolbar()));
        callTemplate = new PushButton(new Image(resources.callTemplateToolbar()));
        call = new PushButton(new Image(resources.callToolbar()));
        connection = new PushButton(new Image(resources.connection()));

        buttons.put(Log.ELEMENT_NAME, log);
        buttons.put(Property.ELEMENT_NAME, property);
        buttons.put(PayloadFactory.ELEMENT_NAME, payloadFactory);
        buttons.put(Send.ELEMENT_NAME, send);
        buttons.put(Header.ELEMENT_NAME, header);
        buttons.put(Respond.ELEMENT_NAME, respond);
        buttons.put(Filter.ELEMENT_NAME, filter);
        buttons.put(Switch_mediator.ELEMENT_NAME, switch_mediator);
        buttons.put(Sequence.ELEMENT_NAME, sequence);
        buttons.put(Enrich.ELEMENT_NAME, enrich);
        buttons.put(LoopBack.ELEMENT_NAME, loopBack);
        buttons.put(CallTemplate.ELEMENT_NAME, callTemplate);
        buttons.put(Call.ELEMENT_NAME, call);

        widget = ourUiBinder.createAndBindUi(this);
    }

    /** {@inheritDoc} */
    @Override
    public void showButtons(@Nonnull Set<String> components) {
        mainPanel.clear();

        for (Map.Entry<String, PushButton> entry : buttons.entrySet()) {
            String elementName = entry.getKey();
            PushButton button = entry.getValue();

            if (components.contains(elementName)) {
                mainPanel.addNorth(button, ITEM_HEIGHT);
            }
        }

        mainPanel.addNorth(connection, ITEM_HEIGHT);
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

    @UiHandler("connection")
    public void onConnectionButtonClicked(ClickEvent event) {
        delegate.onConnectionButtonClicked();
    }

}