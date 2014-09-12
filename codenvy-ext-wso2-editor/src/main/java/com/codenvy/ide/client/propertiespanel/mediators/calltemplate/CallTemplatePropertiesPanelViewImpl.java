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
package com.codenvy.ide.client.propertiespanel.mediators.calltemplate;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Provides a graphical representation of 'CallTemplate' property panel for editing property of 'CallTemplate' mediator.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class CallTemplatePropertiesPanelViewImpl extends CallTemplatePropertiesPanelView {

    @Singleton
    interface CallTemplatePropertiesPanelViewImplUiBinder extends UiBinder<Widget, CallTemplatePropertiesPanelViewImpl> {
    }

    @UiField
    ListBox availableTemplates;
    @UiField
    TextBox targetTemplate;
    @UiField
    TextBox parameters;
    @UiField
    TextBox description;

    @UiField(provided = true)
    final EditorResources                res;
    @UiField(provided = true)
    final WSO2EditorLocalizationConstant locale;

    @Inject
    public CallTemplatePropertiesPanelViewImpl(CallTemplatePropertiesPanelViewImplUiBinder ourUiBinder,
                                               EditorResources res,
                                               WSO2EditorLocalizationConstant locale) {
        this.res = res;
        this.locale = locale;

        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getAvailableTemplates() {
        return getSelectedItem(availableTemplates);
    }

    /** {@inheritDoc} */
    @Override
    public void setAvailableTemplates(@Nullable List<String> payloadFormat) {
        setTypes(availableTemplates, payloadFormat);
    }

    /** {@inheritDoc} */
    @Override
    public void selectAvailableTemplate(@Nonnull String availableTemplate) {
        selectType(availableTemplates, availableTemplate);
    }

    @UiHandler("availableTemplates")
    public void onAvailableTemplatesChanged(KeyUpEvent event) {
        delegate.onAvailableTemplatesChanged();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getTargetTemplate() {
        return targetTemplate.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setTargetTemplate(@Nonnull String targetTemplate) {
        this.targetTemplate.setText(targetTemplate);
    }

    @UiHandler("targetTemplate")
    public void onTargetTemplateChanged(KeyUpEvent event) {
        delegate.onTargetTemplateChanged();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getDescription() {
        return String.valueOf(description.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setParameters(@Nonnull String parameter) {
        parameters.setText(parameter);
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

    @UiHandler("btnParameter")
    public void onEditPropertyButtonClicked(ClickEvent event) {
        delegate.onParameterButtonClicked();
    }
}