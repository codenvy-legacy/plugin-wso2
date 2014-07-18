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
package com.codenvy.ide.client.propertiespanel.enrich;

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
public class EnrichPropertiesPanelViewImpl extends EnrichPropertiesPanelView {

    interface EnrichPropertiesPanelViewImplUiBinder extends UiBinder<Widget, EnrichPropertiesPanelViewImpl> {
    }

    @UiField
    ListBox cloneSource;
    @UiField
    ListBox sourceType;
    @UiField
    TextBox sourceXpath;
    @UiField
    ListBox targetAction;
    @UiField
    ListBox targetType;
    @UiField
    TextBox targetXpath;
    @UiField
    TextBox description;

    @Inject
    public EnrichPropertiesPanelViewImpl(EnrichPropertiesPanelViewImplUiBinder ourUiBinder) {
        widget = ourUiBinder.createAndBindUi(this);
    }

    @Override
    public String getCloneSource() {
        int index = cloneSource.getSelectedIndex();
        return index != -1 ? cloneSource.getValue(cloneSource.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setCloneSource(List<String> cloneSource) {
        if (cloneSource == null) {
            return;
        }
        this.cloneSource.clear();
        for (String value : cloneSource) {
            this.cloneSource.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectCloneSource(String cloneSource) {
        for (int i = 0; i < this.cloneSource.getItemCount(); i++) {
            if (this.cloneSource.getValue(i).equals(cloneSource)) {
                this.cloneSource.setItemSelected(i, true);
                return;
            }
        }
    }

    @UiHandler("cloneSource")
    public void onCloneSourceChanged(ChangeEvent event) {
        delegate.onCloneSourceChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getSourceType() {
        int index = sourceType.getSelectedIndex();
        return index != -1 ? sourceType.getValue(sourceType.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setSourceType(List<String> sourceType) {
        if (sourceType == null) {
            return;
        }
        this.sourceType.clear();
        for (String value : sourceType) {
            this.sourceType.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectSourceType(String sourceType) {
        for (int i = 0; i < this.sourceType.getItemCount(); i++) {
            if (this.sourceType.getValue(i).equals(sourceType)) {
                this.sourceType.setItemSelected(i, true);
                return;
            }
        }
    }

    @UiHandler("sourceType")
    public void onSourceTypeChanged(ChangeEvent event) {
        delegate.onSourceTypeChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getSourceXpath() {
        return String.valueOf(sourceXpath.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setSourceXpath(String sourceXpath) {
        this.sourceXpath.setText(sourceXpath);
    }

    @UiHandler("sourceXpath")
    public void onSourceXpathChanged(KeyUpEvent event) {
        delegate.onSourceXpathChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getTargetAction() {
        int index = targetAction.getSelectedIndex();
        return index != -1 ? targetAction.getValue(targetAction.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setTargetAction(List<String> targetAction) {
        if (targetAction == null) {
            return;
        }
        this.targetAction.clear();
        for (String value : targetAction) {
            this.targetAction.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectTargetAction(String targetAction) {
        for (int i = 0; i < this.targetAction.getItemCount(); i++) {
            if (this.targetAction.getValue(i).equals(targetAction)) {
                this.targetAction.setItemSelected(i, true);
                return;
            }
        }
    }

    @UiHandler("targetAction")
    public void onTargetActionChanged(ChangeEvent event) {
        delegate.onTargetActionChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getTargetType() {
        int index = targetType.getSelectedIndex();
        return index != -1 ? targetType.getValue(targetType.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setTargetType(List<String> targetType) {
        if (targetType == null) {
            return;
        }
        this.targetType.clear();
        for (String value : targetType) {
            this.targetType.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectTargetType(String targetType) {
        for (int i = 0; i < this.targetType.getItemCount(); i++) {
            if (this.targetType.getValue(i).equals(targetType)) {
                this.targetType.setItemSelected(i, true);
                return;
            }
        }
    }

    @UiHandler("targetType")
    public void onTargetTypeChanged(ChangeEvent event) {
        delegate.onTargetTypeChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getTargetXpath() {
        return String.valueOf(targetXpath.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setTargetXpath(String targetXpath) {
        this.targetXpath.setText(targetXpath);
    }

    @UiHandler("targetXpath")
    public void onTargetXpathChanged(KeyUpEvent event) {
        delegate.onTargetXpathChanged();
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