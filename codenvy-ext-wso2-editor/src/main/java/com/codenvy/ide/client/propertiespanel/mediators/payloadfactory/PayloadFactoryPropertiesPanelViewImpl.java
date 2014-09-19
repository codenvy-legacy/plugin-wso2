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
package com.codenvy.ide.client.propertiespanel.mediators.payloadfactory;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.mvp.AbstractView;
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
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Provides a graphical representation of 'Payload factory' property panel for editing property of 'Payload factory' mediator.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class PayloadFactoryPropertiesPanelViewImpl extends AbstractView<PayloadFactoryPropertiesPanelView.ActionDelegate>
        implements PayloadFactoryPropertiesPanelView {

    @Singleton
    interface PayloadFactoryPropertiesPanelViewImplUiBinder extends UiBinder<Widget, PayloadFactoryPropertiesPanelViewImpl> {
    }

    @UiField
    ListBox mediaType;
    @UiField
    ListBox payloadFormat;

    @UiField
    TextBox format;
    @UiField
    TextBox args;
    @UiField
    TextBox description;
    @UiField
    TextBox formatKey;

    @UiField
    FlowPanel formatPanel;
    @UiField
    FlowPanel formatKeyPanel;

    @UiField(provided = true)
    final EditorResources                res;
    @UiField(provided = true)
    final WSO2EditorLocalizationConstant locale;

    @Inject
    public PayloadFactoryPropertiesPanelViewImpl(PayloadFactoryPropertiesPanelViewImplUiBinder ourUiBinder,
                                                 EditorResources res,
                                                 WSO2EditorLocalizationConstant locale) {
        this.res = res;
        this.locale = locale;

        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getPayloadFormat() {
        return getSelectedItem(payloadFormat);
    }

    /** {@inheritDoc} */
    @Override
    public void setPayloadFormats(@Nullable List<String> payloadFormats) {
        setTypes(payloadFormat, payloadFormats);
    }

    /** {@inheritDoc} */
    @Override
    public void selectPayloadFormat(@Nullable String payloadFormat) {
        selectType(this.payloadFormat, payloadFormat);
    }

    @UiHandler("payloadFormat")
    public void onPayloadFormatChanged(ChangeEvent event) {
        delegate.onPayloadFormatChanged();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getFormat() {
        return String.valueOf(format.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setFormat(@Nonnull String format) {
        this.format.setText(format);
    }

    /** {@inheritDoc} */
    @Override
    public void setFormatKey(@Nullable String formatKey) {
        this.formatKey.setText(formatKey);
    }

    @UiHandler("format")
    public void onFormatChanged(KeyUpEvent event) {
        delegate.onPayloadFormatChanged();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getArgs() {
        return String.valueOf(args.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setArgs(@Nonnull String args) {
        this.args.setText(args);
    }

    @UiHandler("formatButton")
    public void onFormatButtonClicked(ClickEvent event) {
        delegate.showFormatConfigurationWindow();
    }

    @UiHandler("formatKeyButton")
    public void onFormatKeyButtonClicked(ClickEvent event) {
        delegate.showKeyEditorWindow(formatKey.getText());
    }

    @UiHandler("argsButton")
    public void onArgsButtonClicked(ClickEvent event) {
        delegate.showArgsConfigurationWindow();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getMediaType() {
        return getSelectedItem(mediaType);
    }

    /** {@inheritDoc} */
    @Override
    public void setMediaTypes(@Nullable List<String> mediaTypes) {
        setTypes(mediaType, mediaTypes);
    }

    /** {@inheritDoc} */
    @Override
    public void selectMediaType(@Nullable String mediaType) {
        selectType(this.mediaType, mediaType);
    }

    @UiHandler("mediaType")
    public void onMediaTypeChanged(ChangeEvent event) {
        delegate.onMediaTypeChanged();
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

    /** {@inheritDoc} */
    @Override
    public void setVisibleFormatPanel(boolean isVisible) {
        formatPanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleFormatKeyPanel(boolean isVisible) {
        formatKeyPanel.setVisible(isVisible);
    }

    @UiHandler("description")
    public void onDescriptionChanged(KeyUpEvent event) {
        delegate.onDescriptionChanged();
    }

}