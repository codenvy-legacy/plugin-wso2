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
package com.codenvy.ide.client.propertiespanel.log;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.google.gwt.event.dom.client.ChangeEvent;
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
 * @author Andrey Plotnikov
 */
public class LogPropertiesPanelViewImpl extends LogPropertiesPanelView {

    @Singleton
    interface LogPropertiesPanelViewImplUiBinder extends UiBinder<Widget, LogPropertiesPanelViewImpl> {
    }

    @UiField
    ListBox logCategory;
    @UiField
    ListBox logLevel;
    @UiField
    TextBox logSeparator;
    @UiField
    TextBox logProperties;
    @UiField
    TextBox description;

    @UiField(provided = true)
    final EditorResources                res;
    @UiField(provided = true)
    final WSO2EditorLocalizationConstant loc;

    @Inject
    public LogPropertiesPanelViewImpl(LogPropertiesPanelViewImplUiBinder ourUiBinder,
                                      EditorResources res,
                                      WSO2EditorLocalizationConstant loc) {
        this.res = res;
        this.loc = loc;

        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    public String getLogCategory() {
        return getSelectedItem(logCategory);
    }

    /** {@inheritDoc} */
    @Override
    public void setLogCategories(@Nullable List<String> logCategories) {
        setTypes(this.logCategory, logCategories);
    }

    /** {@inheritDoc} */
    @Override
    public void selectLogCategory(@Nullable String logCategory) {
        selectType(this.logCategory, logCategory);
    }

    @UiHandler("logCategory")
    public void onLogCategoryChanged(ChangeEvent event) {
        delegate.onLogCategoryChanged();
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    public String getLogLevel() {
        return getSelectedItem(logLevel);
    }

    /** {@inheritDoc} */
    @Override
    public void setLogLevels(@Nullable List<String> logLevels) {
        setTypes(logLevel, logLevels);
    }

    /** {@inheritDoc} */
    @Override
    public void selectLogLevel(@Nullable String logLevel) {
        selectType(this.logLevel, logLevel);
    }

    @UiHandler("logLevel")
    public void onLogLevelChanged(ChangeEvent event) {
        delegate.onLogLevelChanged();
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    public String getLogSeparator() {
        return logSeparator.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setLogSeparator(@Nullable String logSeparator) {
        this.logSeparator.setText(logSeparator);
    }

    @UiHandler("logSeparator")
    public void onLogSeparatorChanged(KeyUpEvent event) {
        delegate.onLogSeparatorChanged();
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    public String getDescription() {
        return description.getText();
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

    @UiHandler("propertiesButton")
    public void onEditPropertyButtonClicked(ClickEvent event) {
        delegate.onEditButtonClicked();
    }

}