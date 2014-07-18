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
package com.codenvy.ide.client.propertiespanel.calltemplate;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author Andrey Plotnikov
 */
public class CallTemplatePropertiesPanelViewImpl extends CallTemplatePropertiesPanelView {

    interface CallTemplatePropertiesPanelViewImplUiBinder extends UiBinder<Widget, CallTemplatePropertiesPanelViewImpl> {
    }

    @UiField
    TextBox availableTemplates;
    @UiField
    TextBox targetTemplate;
    @UiField
    TextBox parameters;
    @UiField
    TextBox description;

    @Inject
    public CallTemplatePropertiesPanelViewImpl(CallTemplatePropertiesPanelViewImplUiBinder ourUiBinder) {
        widget = ourUiBinder.createAndBindUi(this);
    }

    /** {@inheritDoc} */
    @Override
    public String getAvailableTemplates() {
        return String.valueOf(availableTemplates.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setAvailableTemplates(String availableTemplates) {
        this.availableTemplates.setText(availableTemplates);
    }

    @UiHandler("availableTemplates")
    public void onAvailableTemplatesChanged(KeyUpEvent event) {
        delegate.onAvailableTemplatesChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getTargetTemplate() {
        return String.valueOf(targetTemplate.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setTargetTemplate(String targetTemplate) {
        this.targetTemplate.setText(targetTemplate);
    }

    @UiHandler("targetTemplate")
    public void onTargetTemplateChanged(KeyUpEvent event) {
        delegate.onTargetTemplateChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getParameters() {
        return String.valueOf(parameters.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setParameters(String parameters) {
        this.parameters.setText(parameters);
    }

    @UiHandler("parameters")
    public void onParametersChanged(KeyUpEvent event) {
        delegate.onParametersChanged();
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