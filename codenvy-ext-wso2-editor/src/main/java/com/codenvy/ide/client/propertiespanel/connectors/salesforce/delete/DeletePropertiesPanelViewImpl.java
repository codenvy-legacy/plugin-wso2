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
package com.codenvy.ide.client.propertiespanel.connectors.salesforce.delete;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.propertiespanel.connectors.base.BaseConnectorPanelPresenter;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Provides a graphical representation of Delete connector property panel for editing property of Delete connector.
 *
 * @author Dmitry Shnurenko
 */
public class DeletePropertiesPanelViewImpl extends DeletePropertiesPanelView {

    @Singleton
    interface DeletePropertiesPanelViewImplUiBinder extends UiBinder<Widget, DeletePropertiesPanelViewImpl> {
    }

    @UiField
    SimplePanel generalPanel;

    @UiField
    TextBox allOrNone;
    @UiField
    TextBox subject;

    @UiField
    Button allOrNoneButton;
    @UiField
    Button subjectButton;

    @UiField(provided = true)
    final EditorResources                res;
    @UiField(provided = true)
    final WSO2EditorLocalizationConstant loc;

    @Inject
    public DeletePropertiesPanelViewImpl(EditorResources resources,
                                         WSO2EditorLocalizationConstant locale,
                                         DeletePropertiesPanelViewImplUiBinder uiBinder) {
        this.res = resources;
        this.loc = locale;

        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("allOrNone")
    public void onAllOrNoneChanged(KeyUpEvent event) {
        delegate.onAllOrNoneChanged();
    }

    @UiHandler("subject")
    public void onSubjectChanged(KeyUpEvent event) {
        delegate.onSubjectChanged();
    }

    @UiHandler("allOrNoneButton")
    public void onAllOrNoneBtnClicked(ClickEvent event) {
        delegate.onAllOrNoneBtnClicked();
    }

    @UiHandler("subjectButton")
    public void onSubjectBtnClicked(ClickEvent event) {
        delegate.onSubjectBtnClicked();
    }

    /** {@inheritDoc} */
    @Override
    public void setGeneralPanel(@Nonnull BaseConnectorPanelPresenter base) {
        base.go(generalPanel);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getAllOrNone() {
        return allOrNone.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setAllOrNone(@Nullable String allOrNone) {
        this.allOrNone.setText(allOrNone);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getSubjectValue() {
        return subject.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setSubjectValue(@Nullable String subject) {
        this.subject.setText(subject);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleButton(boolean isVisible) {
        allOrNoneButton.setVisible(isVisible);
        subjectButton.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setEnableTextField(boolean isVisible) {
        allOrNone.setEnabled(isVisible);
        subject.setEnabled(isVisible);
    }

}
