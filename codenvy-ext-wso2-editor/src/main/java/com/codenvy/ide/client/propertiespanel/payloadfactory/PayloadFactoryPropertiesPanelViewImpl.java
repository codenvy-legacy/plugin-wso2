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
package com.codenvy.ide.client.propertiespanel.payloadfactory;

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
public class PayloadFactoryPropertiesPanelViewImpl extends PayloadFactoryPropertiesPanelView {

    interface PayloadFactoryPropertiesPanelViewImplUiBinder extends UiBinder<Widget, PayloadFactoryPropertiesPanelViewImpl> {
    }

    @UiField
    ListBox payloadFormat;
    @UiField
    TextBox format;
    @UiField
    TextBox args;
    @UiField
    ListBox mediaType;
    @UiField
    TextBox description;

    @Inject
    public PayloadFactoryPropertiesPanelViewImpl(PayloadFactoryPropertiesPanelViewImplUiBinder ourUiBinder) {
        widget = ourUiBinder.createAndBindUi(this);
    }

    /** {@inheritDoc} */
    @Override
    public String getPayloadFormat() {
        int index = payloadFormat.getSelectedIndex();
        return index != -1 ? payloadFormat.getValue(payloadFormat.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setPayloadFormat(List<String> payloadFormat) {
        if (payloadFormat == null) {
            return;
        }
        this.payloadFormat.clear();
        for (String value : payloadFormat) {
            this.payloadFormat.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectPayloadFormat(String payloadFormat) {
        for (int i = 0; i < this.payloadFormat.getItemCount(); i++) {
            if (this.payloadFormat.getValue(i).equals(payloadFormat)) {
                this.payloadFormat.setItemSelected(i, true);
                return;
            }
        }
    }

    @UiHandler("payloadFormat")
    public void onPayloadFormatChanged(ChangeEvent event) {
        delegate.onPayloadFormatChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getFormat() {
        return String.valueOf(format.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setFormat(String format) {
        this.format.setText(format);
    }

    @UiHandler("format")
    public void onFormatChanged(KeyUpEvent event) {
        delegate.onFormatChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getArgs() {
        return String.valueOf(args.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setArgs(String args) {
        this.args.setText(args);
    }

    @UiHandler("args")
    public void onArgsChanged(KeyUpEvent event) {
        delegate.onArgsChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getMediaType() {
        int index = mediaType.getSelectedIndex();
        return index != -1 ? mediaType.getValue(mediaType.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setMediaType(List<String> mediaType) {
        if (mediaType == null) {
            return;
        }
        this.mediaType.clear();
        for (String value : mediaType) {
            this.mediaType.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectMediaType(String mediaType) {
        for (int i = 0; i < this.mediaType.getItemCount(); i++) {
            if (this.mediaType.getValue(i).equals(mediaType)) {
                this.mediaType.setItemSelected(i, true);
                return;
            }
        }
    }

    @UiHandler("mediaType")
    public void onMediaTypeChanged(ChangeEvent event) {
        delegate.onMediaTypeChanged();
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