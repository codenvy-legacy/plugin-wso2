/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package com.codenvy.ide.client.propertiespanel.filter;

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
 * Provides a graphical representation of 'Filter' property panel for editing property of 'Filter' mediator.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class FilterPropertiesPanelViewImpl extends FilterPropertiesPanelView {

    @Singleton
    interface FilterPropertiesPanelViewImplUiBinder extends UiBinder<Widget, FilterPropertiesPanelViewImpl> {
    }

    @UiField
    ListBox conditionType;
    @UiField
    TextBox source;
    @UiField
    TextBox regularExpression;
    @UiField
    TextBox xPath;

    @UiField
    FlowPanel sourcePanel;
    @UiField
    FlowPanel regularExpressionPanel;
    @UiField
    FlowPanel xpathPanel;

    @UiField(provided = true)
    final EditorResources                res;
    @UiField(provided = true)
    final WSO2EditorLocalizationConstant locale;

    @Inject
    public FilterPropertiesPanelViewImpl(FilterPropertiesPanelViewImplUiBinder ourUiBinder,
                                         EditorResources res,
                                         WSO2EditorLocalizationConstant locale) {
        this.res = res;
        this.locale = locale;

        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getConditionType() {
        return getSelectedItem(conditionType);
    }

    /** {@inheritDoc} */
    @Override
    public void setConditionTypes(@Nullable List<String> conditionTypes) {
        setTypes(conditionType, conditionTypes);
    }

    /** {@inheritDoc} */
    @Override
    public void selectConditionType(@Nonnull String conditionType) {
        selectType(this.conditionType, conditionType);
    }

    /** {@inheritDoc} */
    @Override
    public void setSource(@Nullable String source) {
        this.source.setText(source);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getRegularExpression() {
        return regularExpression.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setRegularExpression(@Nonnull String regularExpression) {
        this.regularExpression.setText(regularExpression);
    }

    /** {@inheritDoc} */
    @Override
    public void setXPath(@Nullable String xPath) {
        this.xPath.setText(xPath);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleSourcePanel(boolean visible) {
        sourcePanel.setVisible(visible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleRegularExpressionPanel(boolean visible) {
        regularExpressionPanel.setVisible(visible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleXpathPanel(boolean visible) {
        xpathPanel.setVisible(visible);
    }

    @UiHandler("conditionType")
    public void onConditionTypeChanged(ChangeEvent event) {
        delegate.onConditionTypeChanged();
    }

    @UiHandler("regularExpression")
    public void onRegularExpressionChanged(KeyUpEvent event) {
        delegate.onRegularExpressionChanged();
    }

    @UiHandler("btnSource")
    public void onEditSourceButtonClicked(ClickEvent event) {
        delegate.onEditSourceButtonClicked();
    }

    @UiHandler("btnXpath")
    public void onEditXPathButtonClicked(ClickEvent event) {
        delegate.onEditXPathButtonClicked();
    }

}