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
package com.codenvy.ide.client.propertiespanel.sequence;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
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
 * Provides a graphical representation of 'Sequence' property panel for editing property of 'Sequence' mediator.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class SequencePropertiesPanelViewImpl extends SequencePropertiesPanelView {

    @Singleton
    interface SequencePropertiesPanelViewImplUiBinder extends UiBinder<Widget, SequencePropertiesPanelViewImpl> {
    }

    @UiField
    ListBox referringType;
    @UiField
    TextBox staticReferenceKey;
    @UiField
    TextBox dynamicReferenceKey;

    @UiField
    FlowPanel staticReferenceKeyPanel;
    @UiField
    FlowPanel dynamicReferenceKeyPanel;

    @UiField(provided = true)
    final EditorResources                res;
    @UiField(provided = true)
    final WSO2EditorLocalizationConstant locale;

    @Inject
    public SequencePropertiesPanelViewImpl(SequencePropertiesPanelViewImplUiBinder ourUiBinder,
                                           EditorResources res,
                                           WSO2EditorLocalizationConstant locale) {
        this.res = res;
        this.locale = locale;

        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getReferringType() {
        return getSelectedItem(referringType);
    }

    /** {@inheritDoc} */
    @Override
    public void setReferringTypes(@Nullable List<String> referringTypes) {
        setTypes(referringType, referringTypes);
    }

    /** {@inheritDoc} */
    @Override
    public void selectReferringType(@Nonnull String referringType) {
        selectType(this.referringType, referringType);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getStaticReferenceKey() {
        return staticReferenceKey.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setStaticReferenceKey(@Nullable String staticReferenceKey) {
        this.staticReferenceKey.setText(staticReferenceKey);
    }

    /** {@inheritDoc} */
    @Override
    public void setDynamicReferenceKey(@Nullable String dynamicReferenceKey) {
        this.dynamicReferenceKey.setText(dynamicReferenceKey);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleDynamicReferenceKeyPanel(boolean visible) {
        dynamicReferenceKeyPanel.setVisible(visible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleStaticReferenceKeyPanel(boolean visible) {
        staticReferenceKeyPanel.setVisible(visible);
    }

    @UiHandler("staticReferenceKey")
    public void onStaticReferenceKeyChanged(KeyUpEvent event) {
        delegate.onStaticReferenceKeyChanged();
    }

    @UiHandler("referringType")
    public void onReferringSequenceTypeChanged(ChangeEvent event) {
        delegate.onReferringTypeChanged();
    }

    @UiHandler("expressionButton")
    public void onEditExpressionButtonClicked(ClickEvent event) {
        delegate.onEditExpressionButtonClicked();
    }

}