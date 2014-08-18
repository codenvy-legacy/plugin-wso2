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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * The implementation of {CallTemplatePropertiesPanelView}
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class CallTemplatePropertiesPanelViewImpl extends CallTemplatePropertiesPanelView {

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

    @Inject
    public CallTemplatePropertiesPanelViewImpl(CallTemplatePropertiesPanelViewImplUiBinder ourUiBinder) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getAvailableTemplates() {
        int index = availableTemplates.getSelectedIndex();
        return index != -1 ? availableTemplates.getValue(availableTemplates.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setAvailableTemplates(@Nullable List<String> payloadFormat) {
        if (payloadFormat == null) {
            return;
        }
        this.availableTemplates.clear();
        for (String value : payloadFormat) {
            this.availableTemplates.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectAvailableTemplate(@Nonnull String availableTemplate) {
        for (int i = 0; i < this.availableTemplates.getItemCount(); i++) {
            if (this.availableTemplates.getValue(i).equals(availableTemplate)) {
                this.availableTemplates.setItemSelected(i, true);
                return;
            }
        }
    }

    @UiHandler("availableTemplates")
    public void onAvailableTemplatesChanged(KeyUpEvent event) {
        delegate.onAvailableTemplatesChanged();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getTargetTemplate() {
        return String.valueOf(targetTemplate.getText());
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