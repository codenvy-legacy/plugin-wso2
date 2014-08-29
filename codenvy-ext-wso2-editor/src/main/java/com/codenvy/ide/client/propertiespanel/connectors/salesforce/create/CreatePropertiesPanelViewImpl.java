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
package com.codenvy.ide.client.propertiespanel.connectors.salesforce.create;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.propertiespanel.connectors.base.BaseConnectorPanelPresenter;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.codenvy.ide.client.elements.connectors.salesforce.BaseSalesforce.ParameterEditorType;

/**
 * The implementation of {CreatePropertiesPanelView}
 *
 * @author Valeriy Svydenko
 */
public class CreatePropertiesPanelViewImpl extends CreatePropertiesPanelView {

    @Singleton
    interface CreatePropertiesPanelViewImplUiBinder extends UiBinder<Widget, CreatePropertiesPanelViewImpl> {
    }

    @UiField
    SimplePanel baseConnector;
    @UiField
    FlowPanel   allOrNoneNamespacePanel;
    @UiField
    TextBox     allOrNoneNamespace;
    @UiField
    FlowPanel   truncateNamespacePanel;
    @UiField
    TextBox     truncateNamespace;
    @UiField
    TextBox     subjectsNamespace;
    @UiField
    FlowPanel   subjectsNamespacePanel;

    @UiField(provided = true)
    final EditorResources                res;
    @UiField(provided = true)
    final WSO2EditorLocalizationConstant loc;
    @UiField
    TextBox truncate;
    @UiField
    TextBox allOrNone;
    @UiField
    TextBox subjects;

    @Inject
    public CreatePropertiesPanelViewImpl(CreatePropertiesPanelViewImplUiBinder ourUiBinder,
                                         EditorResources res,
                                         WSO2EditorLocalizationConstant loc) {

        this.res = res;
        this.loc = loc;

        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getSubjects() {
        return subjects.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setSubjects(@Nonnull String loginUrl) {
        this.subjects.setText(loginUrl);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getTruncate() {
        return truncate.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setTruncate(@Nonnull String password) {
        this.truncate.setText(password);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getAllOrNone() {
        return allOrNone.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setAllOrNone(@Nonnull String username) {
        this.allOrNone.setText(username);
    }

    /** {@inheritDoc} */
    @Override
    public void setSubjectsNamespace(@Nullable String loginUrl) {
        subjectsNamespace.setText(loginUrl);
    }

    /** {@inheritDoc} */
    @Override
    public void setTruncateNamespace(@Nullable String password) {
        truncateNamespace.setText(password);
    }

    /** {@inheritDoc} */
    @Override
    public void setAllOrNoneNamespace(@Nullable String username) {
        allOrNoneNamespace.setText(username);
    }

    /** {@inheritDoc} */
    @Override
    public void addBaseConnector(@Nonnull BaseConnectorPanelPresenter base) {
        base.go(baseConnector);
    }

    @UiHandler("allOrNoneButton")
    public void onUsernameButtonClicked(ClickEvent event) {
        delegate.allOrNoneButtonClicked();
    }

    @UiHandler("truncateButton")
    public void onPasswordButtonClicked(ClickEvent event) {
        delegate.truncateButtonClicked();
    }

    @UiHandler("subjectsButton")
    public void onLoginUrlButtonClicked(ClickEvent event) {
        delegate.subjectsButtonClicked();
    }

    @UiHandler("allOrNone")
    public void onUsernameChanged(KeyUpEvent event) {
        delegate.onAllOrNoneChanged();
    }

    @UiHandler("truncate")
    public void onPasswordChanged(KeyUpEvent event) {
        delegate.onTruncateChanged();
    }

    @UiHandler("subjects")
    public void onLoginUrlChanged(KeyUpEvent event) {
        delegate.onSubjectChanged();
    }

    /** {@inheritDoc} */
    @Override
    public void onParameterEditorTypeChanged(@Nonnull ParameterEditorType parameterEditorType) {
        boolean isNamespaceEditorParam = parameterEditorType.equals(ParameterEditorType.NamespacedPropertyEditor);

        allOrNoneNamespacePanel.setVisible(isNamespaceEditorParam);
        allOrNone.setVisible(!isNamespaceEditorParam);

        truncateNamespacePanel.setVisible(isNamespaceEditorParam);
        truncate.setVisible(!isNamespaceEditorParam);

        subjectsNamespacePanel.setVisible(isNamespaceEditorParam);
        subjects.setVisible(!isNamespaceEditorParam);
    }

}
