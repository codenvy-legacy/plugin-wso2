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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
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
    FlowPanel receivingPanel;
    @UiField
    FlowPanel dynamicPanel;
    @UiField
    FlowPanel staticPanel;
    @UiField
    FlowPanel messagePanel;
    @UiField
    FlowPanel descriptionPanel;

    @UiField
    TextBox dynamicRec;
    @UiField
    TextBox staticRec;
    @UiField
    TextBox description;

    @Inject
    public SendPropertiesPanelViewImpl(SendPropertiesPanelViewImplUiBinder ourUiBinder) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @UiHandler("staticBtn")
    public void onStaticReceivingBtnClicked(ClickEvent event) {
        delegate.onStaticReceivingBtnClicked();
    }

    @UiHandler("dynamicBtn")
    public void onDynamicReceivingBtnClicked(ClickEvent event) {
        delegate.onDynamicReceivingBtnClicked();
    }

    /** {@inheritDoc} */
    @Override
    public void setStaticSequence(@Nullable String sequence) {
        staticRec.setText(sequence);
    }

    /** {@inheritDoc} */
    @Override
    public void setDynamicSequence(@Nullable String sequence) {
        dynamicRec.setText(sequence);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getSkipSerialization() {
        int index = skipSerialization.getSelectedIndex();
        return index != -1 ? skipSerialization.getValue(skipSerialization.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setSkipSerialization(@Nullable List<String> skipSerialization) {
        if (skipSerialization == null) {
            return;
        }
        this.skipSerialization.clear();
        for (String value : skipSerialization) {
            this.skipSerialization.addItem(value);
        }
    }

    @UiHandler("skipSerialization")
    public void onSkipSerializationChanged(ChangeEvent event) {
        delegate.onSkipSerializationChanged();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getReceivingSequencerType() {
        int index = receivingSequencerType.getSelectedIndex();
        return index != -1 ? receivingSequencerType.getValue(receivingSequencerType.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setReceivingSequencerType(@Nullable List<String> receivingSequencerType) {
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
    public void selectReceivingSequencerType(@Nullable String receivingSequencerType) {
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
    @Nonnull
    @Override
    public String getBuildMessage() {
        int index = buildMessageBeforeSending.getSelectedIndex();
        return index != -1 ? buildMessageBeforeSending.getValue(buildMessageBeforeSending.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setBuildMessageBeforeSending(@Nonnull List<String> buildMessageBeforeSending) {
        this.buildMessageBeforeSending.clear();

        for (String value : buildMessageBeforeSending) {
            this.buildMessageBeforeSending.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectBuildMessageBeforeSending(@Nullable String buildMessageBeforeSending) {
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
    @Nonnull
    @Override
    public String getDescription() {
        return String.valueOf(description.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setDescription(@Nullable String description) {
        this.description.setText(description);
    }

    @UiHandler("description")
    public void onDescriptionChanged(KeyUpEvent event) {
        delegate.onDescriptionChanged();
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleRecSeqTypePanel(boolean isVisible) {
        receivingPanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleBuildMessagePanel(boolean isVisible) {
        messagePanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleDynamicPanel(boolean isVisible) {
        dynamicPanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleStaticPanel(boolean isVisible) {
        staticPanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleDescriptionPanel(boolean isVisible) {
        descriptionPanel.setVisible(isVisible);
    }

}