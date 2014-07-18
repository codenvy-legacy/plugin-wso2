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
package com.codenvy.ide.client.propertiespanel.send;

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
public class SendPropertiesPanelViewImpl extends SendPropertiesPanelView {

    interface SendPropertiesPanelViewImplUiBinder extends UiBinder<Widget, SendPropertiesPanelViewImpl> {
    }

    @UiField
    ListBox skipSerialization;
    @UiField
    ListBox receivingSequencerType;
    @UiField
    ListBox buildMessageBeforeSending;
    @UiField
    TextBox description;

    @Inject
    public SendPropertiesPanelViewImpl(SendPropertiesPanelViewImplUiBinder ourUiBinder) {
        widget = ourUiBinder.createAndBindUi(this);
    }

    /** {@inheritDoc} */
    @Override
    public String getSkipSerialization() {
        int index = skipSerialization.getSelectedIndex();
        return index != -1 ? skipSerialization.getValue(skipSerialization.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setSkipSerialization(List<String> skipSerialization) {
        if (skipSerialization == null) {
            return;
        }
        this.skipSerialization.clear();
        for (String value : skipSerialization) {
            this.skipSerialization.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectSkipSerialization(String skipSerialization) {
        for (int i = 0; i < this.skipSerialization.getItemCount(); i++) {
            if (this.skipSerialization.getValue(i).equals(skipSerialization)) {
                this.skipSerialization.setItemSelected(i, true);
                return;
            }
        }
    }

    @UiHandler("skipSerialization")
    public void onSkipSerializationChanged(ChangeEvent event) {
        delegate.onSkipSerializationChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getReceivingSequencerType() {
        int index = receivingSequencerType.getSelectedIndex();
        return index != -1 ? receivingSequencerType.getValue(receivingSequencerType.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setReceivingSequencerType(List<String> receivingSequencerType) {
        if (receivingSequencerType == null) {
            return;
        }
        this.receivingSequencerType.clear();
        for (String value : receivingSequencerType) {
            this.receivingSequencerType.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectReceivingSequencerType(String receivingSequencerType) {
        for (int i = 0; i < this.receivingSequencerType.getItemCount(); i++) {
            if (this.receivingSequencerType.getValue(i).equals(receivingSequencerType)) {
                this.receivingSequencerType.setItemSelected(i, true);
                return;
            }
        }
    }

    @UiHandler("receivingSequencerType")
    public void onReceivingSequencerTypeChanged(ChangeEvent event) {
        delegate.onReceivingSequencerTypeChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getBuildMessageBeforeSending() {
        int index = buildMessageBeforeSending.getSelectedIndex();
        return index != -1 ? buildMessageBeforeSending.getValue(buildMessageBeforeSending.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setBuildMessageBeforeSending(List<String> buildMessageBeforeSending) {
        if (buildMessageBeforeSending == null) {
            return;
        }
        this.buildMessageBeforeSending.clear();
        for (String value : buildMessageBeforeSending) {
            this.buildMessageBeforeSending.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectBuildMessageBeforeSending(String buildMessageBeforeSending) {
        for (int i = 0; i < this.buildMessageBeforeSending.getItemCount(); i++) {
            if (this.buildMessageBeforeSending.getValue(i).equals(buildMessageBeforeSending)) {
                this.buildMessageBeforeSending.setItemSelected(i, true);
                return;
            }
        }
    }

    @UiHandler("buildMessageBeforeSending")
    public void onBuildMessageBeforeSendingChanged(ChangeEvent event) {
        delegate.onBuildMessageBeforeSendingChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getDescription() {
        return String.valueOf(description.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setDescription(String description) {
        this.description.setText(description);
    }

    @UiHandler("description")
    public void onDescriptionChanged(KeyUpEvent event) {
        delegate.onDescriptionChanged();
    }

}