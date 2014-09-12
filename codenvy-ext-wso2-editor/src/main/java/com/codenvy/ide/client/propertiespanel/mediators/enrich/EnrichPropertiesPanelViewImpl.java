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
package com.codenvy.ide.client.propertiespanel.mediators.enrich;

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
 * Provides a graphical representation of 'Enrich' property panel for editing property of 'Enrich' mediator.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class EnrichPropertiesPanelViewImpl extends EnrichPropertiesPanelView {

    @Singleton
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

    @UiField(provided = true)
    final WSO2EditorLocalizationConstant loc;
    @UiField(provided = true)
    final EditorResources                res;

    @Inject
    public EnrichPropertiesPanelViewImpl(EnrichPropertiesPanelViewImplUiBinder ourUiBinder,
                                         EditorResources res,
                                         WSO2EditorLocalizationConstant loc) {
        this.res = res;
        this.loc = loc;

        initWidget(ourUiBinder.createAndBindUi(this));
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
        delegate.onSourceTypeChanged();
    }

    @UiHandler("targetType")
    public void onTrtTypeChanged(ChangeEvent event) {
        delegate.onTargetTypeChanged();
    }

    @UiHandler("inlineType")
    public void onInlineTypeChanged(ChangeEvent event) {
        delegate.onSourceInlineTypeChanged();
    }

    @UiHandler("srcProperty")
    public void onSrcPropertyChanged(KeyUpEvent event) {
        delegate.onSourcePropertyChanged();
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
        return getSelectedItem(inlineType);
    }

    /** {@inheritDoc} */
    @Override
    public void setInlineTypes(@Nullable List<String> inlineTypes) {
        setTypes(inlineType, inlineTypes);
    }

    /** {@inheritDoc} */
    @Override
    public void selectInlineType(@Nonnull String inlineType) {
        selectType(this.inlineType, inlineType);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getCloneSource() {
        return getSelectedItem(cloneSource);
    }

    /** {@inheritDoc} */
    @Override
    public void setCloneSources(@Nullable List<String> cloneSources) {
        setTypes(cloneSource, cloneSources);
    }

    /** {@inheritDoc} */
    @Override
    public void selectCloneSource(@Nonnull String cloneSource) {
        selectType(this.cloneSource, cloneSource);
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

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getSourceXpath() {
        return String.valueOf(sourceXpath.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setSourceXpath(@Nullable String sourceXpath) {
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
        return getSelectedItem(targetAction);
    }

    /** {@inheritDoc} */
    @Override
    public void setTargetActions(@Nullable List<String> targetActions) {
        setTypes(this.targetAction, targetActions);
    }

    /** {@inheritDoc} */
    @Override
    public void selectTargetAction(@Nonnull String targetAction) {
        selectType(this.targetAction, targetAction);
    }

    @UiHandler("targetAction")
    public void onTargetActionChanged(ChangeEvent event) {
        delegate.onTargetActionChanged();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getTargetType() {
        return getSelectedItem(targetType);
    }

    /** {@inheritDoc} */
    @Override
    public void setTargetTypes(@Nullable List<String> targetTypes) {
        setTypes(targetType, targetTypes);
    }

    /** {@inheritDoc} */
    @Override
    public void selectTargetType(@Nonnull String targetType) {
        selectType(this.targetType, targetType);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getTargetXpath() {
        return String.valueOf(targetXpath.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setTargetXpath(@Nullable String targetXpath) {
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
    public void setDescription(@Nullable String description) {
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