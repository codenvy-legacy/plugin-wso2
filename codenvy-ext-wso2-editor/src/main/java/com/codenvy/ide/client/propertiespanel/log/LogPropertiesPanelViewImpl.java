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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Andrey Plotnikov
 */
public class LogPropertiesPanelViewImpl extends LogPropertiesPanelView {

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

    @Inject
    public LogPropertiesPanelViewImpl(LogPropertiesPanelViewImplUiBinder ourUiBinder) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    public String getLogCategory() {
        int index = logCategory.getSelectedIndex();
        return index != -1 ? logCategory.getValue(logCategory.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setLogCategory(@Nullable List<String> logCategory) {
        if (logCategory == null) {
            return;
        }

        this.logCategory.clear();

        for (String value : logCategory) {
            this.logCategory.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectLogCategory(@Nullable String logCategory) {
        for (int i = 0; i < this.logCategory.getItemCount(); i++) {
            if (this.logCategory.getValue(i).equals(logCategory)) {
                this.logCategory.setItemSelected(i, true);
                return;
            }
        }
    }

    @UiHandler("logCategory")
    public void onLogCategoryChanged(ChangeEvent event) {
        delegate.onLogCategoryChanged();
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    public String getLogLevel() {
        int index = logLevel.getSelectedIndex();
        return index != -1 ? logLevel.getValue(logLevel.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setLogLevel(@Nullable List<String> logLevel) {
        if (logLevel == null) {
            return;
        }

        this.logLevel.clear();

        for (String value : logLevel) {
            this.logLevel.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectLogLevel(@Nullable String logLevel) {
        for (int i = 0; i < this.logLevel.getItemCount(); i++) {
            if (this.logLevel.getValue(i).equals(logLevel)) {
                this.logLevel.setItemSelected(i, true);
                return;
            }
        }
    }

    @UiHandler("logLevel")
    public void onLogLevelChanged(ChangeEvent event) {
        delegate.onLogLevelChanged();
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    public String getLogSeparator() {
        return String.valueOf(logSeparator.getText());
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

    @UiHandler("propertiesButton")
    public void onEditPropertyButtonClicked(ClickEvent event) {
        delegate.onEditButtonClicked();
    }

}