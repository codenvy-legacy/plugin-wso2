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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
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
public class EnrichPropertiesPanelViewImpl extends EnrichPropertiesPanelView {

    interface EnrichPropertiesPanelViewImplUiBinder extends UiBinder<Widget, EnrichPropertiesPanelViewImpl> {
    }

    @UiField
    ListBox cloneSource;
    @UiField
    ListBox sourceType;
    @UiField
    ListBox targetAction;
    @UiField
    ListBox inlineType;
    @UiField
    ListBox targetType;

    @UiField
    TextBox sourceXpath;
    @UiField
    TextBox targetXpath;
    @UiField
    TextBox description;
    @UiField
    TextBox srcProperty;
    @UiField
    TextBox targetProperty;
    @UiField
    TextBox srcInlineKey;
    @UiField
    TextBox srcXML;

    @UiField
    FlowPanel srcXMLPanel;
    @UiField
    FlowPanel srcXpathPanel;
    @UiField
    FlowPanel srcInlRegPanel;
    @UiField
    FlowPanel srcInlTypePanel;
    @UiField
    FlowPanel srcPropPanel;
    @UiField
    FlowPanel targetXpathPanel;
    @UiField
    FlowPanel targetPropPanel;

    @UiField
    Button btnTargetXpath;
    @UiField
    Button btnInlRegKey;
    @UiField
    Button btnSrcXML;
    @UiField
    Button btnSrcXPath;

    @Inject
    public EnrichPropertiesPanelViewImpl(EnrichPropertiesPanelViewImplUiBinder ourUiBinder) {
        widget = ourUiBinder.createAndBindUi(this);
    }

    @UiHandler("btnTargetXpath")
    public void onTargetXpathBtnClicked(ClickEvent event) {
        delegate.onTargetXPathBtnClicked();
    }

    @UiHandler("btnInlRegKey")
    public void onSrcInlineRegBtnClicked(ClickEvent event) {
        delegate.onSrcRegistryKeyBtnClicked();
    }

    @UiHandler("btnSrcXML")
    public void onSrcXMLBtnClicked(ClickEvent event) {
        delegate.onSrcXMLBtnClicked();
    }

    @UiHandler("btnSrcXPath")
    public void onSrcXpathBtnClicked(ClickEvent event) {
        delegate.onSrcXPathBtnClicked();
    }

    @UiHandler("sourceType")
    public void onSrcTypeChanged(ChangeEvent event) {
        delegate.onSrcTypeChanged();
    }

    @UiHandler("targetType")
    public void onTrtTypeChanged(ChangeEvent event) {
        delegate.onTgtTypeChanged();
    }

    @UiHandler("inlineType")
    public void onInlineTypeChanged(ChangeEvent event) {
        delegate.onSrcInlineTypeChanged();
    }

    @UiHandler("srcProperty")
    public void onSrcPropertyChanged(KeyUpEvent event) {
        delegate.onSrcPropertyChanged();
    }

    @UiHandler("targetProperty")
    public void onTargetPropertyChanged(KeyUpEvent event) {
        delegate.onTargetPropertyChanged();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getTargetProperty() {
        return targetProperty.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setTargetProperty(@Nonnull String property) {
        targetProperty.setText(property);
    }

    /** {@inheritDoc} */
    @Override
    public void setSrcXml(@Nonnull String xml) {
        srcXML.setText(xml);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getSrcProperty() {
        return srcProperty.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setProperty(@Nonnull String property) {
        srcProperty.setText(property);
    }

    /** {@inheritDoc} */
    @Override
    public void setInlineRegisterKey(@Nonnull String key) {
        srcInlineKey.setText(key);
    }

    /** {@inheritDoc} */
    @Override
    public void setInlineXml(@Nonnull String inlineXml) {
        srcXML.setText(inlineXml);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getInlineType() {
        int index = inlineType.getSelectedIndex();
        return index != -1 ? inlineType.getValue(inlineType.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setInlineType(@Nullable List<String> inlineType) {
        if (inlineType == null) {
            return;
        }

        this.inlineType.clear();

        for (String value : inlineType) {
            this.inlineType.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectInlineType(@Nonnull String inlineType) {
        for (int i = 0; i < this.inlineType.getItemCount(); i++) {
            if (this.inlineType.getValue(i).equals(inlineType)) {
                this.inlineType.setItemSelected(i, true);
                return;
            }
        }
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getCloneSource() {
        int index = cloneSource.getSelectedIndex();
        return index != -1 ? cloneSource.getValue(cloneSource.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setCloneSource(@Nullable List<String> cloneSource) {
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
    public void selectCloneSource(@Nonnull String cloneSource) {
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
    @Nonnull
    @Override
    public String getSourceType() {
        int index = sourceType.getSelectedIndex();
        return index != -1 ? sourceType.getValue(sourceType.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setSourceType(@Nullable List<String> sourceType) {
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
    public void selectSourceType(@Nonnull String sourceType) {
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
    @Nonnull
    @Override
    public String getSourceXpath() {
        return String.valueOf(sourceXpath.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setSourceXpath(@Nonnull String sourceXpath) {
        this.sourceXpath.setText(sourceXpath);
    }

    @UiHandler("sourceXpath")
    public void onSourceXpathChanged(KeyUpEvent event) {
        delegate.onSourceXpathChanged();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getTargetAction() {
        int index = targetAction.getSelectedIndex();
        return index != -1 ? targetAction.getValue(targetAction.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setTargetAction(@Nullable List<String> targetAction) {
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
    public void selectTargetAction(@Nonnull String targetAction) {
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
    @Nonnull
    @Override
    public String getTargetType() {
        int index = targetType.getSelectedIndex();
        return index != -1 ? targetType.getValue(targetType.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setTargetType(@Nullable List<String> targetType) {
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
    public void selectTargetType(@Nonnull String targetType) {
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
    @Nonnull
    @Override
    public String getTargetXpath() {
        return String.valueOf(targetXpath.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setTargetXpath(@Nonnull String targetXpath) {
        this.targetXpath.setText(targetXpath);
    }

    @UiHandler("targetXpath")
    public void onTargetXpathChanged(KeyUpEvent event) {
        delegate.onTargetXpathChanged();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getDescription() {
        return String.valueOf(description.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setDescription(@Nonnull String description) {
        this.description.setText(description);
    }

    @UiHandler("description")
    public void onDescriptionChanged(KeyUpEvent event) {
        delegate.onDescriptionChanged();
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleSrcXMLPanel(boolean isVisible) {
        srcXMLPanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleSrcXPathPanel(boolean isVisible) {
        srcXpathPanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleSrcInlineRegisterPanel(boolean isVisible) {
        srcInlRegPanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleSrcInlineTypePanel(boolean isVisible) {
        srcInlTypePanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleSrcPropertyPanel(boolean isVisible) {
        srcPropPanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleTargetXPathPanel(boolean isVisible) {
        targetXpathPanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleTargetPropertyPanel(boolean isVisible) {
        targetPropPanel.setVisible(isVisible);
    }

}