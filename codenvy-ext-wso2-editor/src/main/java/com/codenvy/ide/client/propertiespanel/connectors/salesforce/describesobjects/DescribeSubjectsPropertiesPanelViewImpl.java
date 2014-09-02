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
package com.codenvy.ide.client.propertiespanel.connectors.salesforce.describesobjects;

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

/**
 * Provides a graphical representation of DescribeGlobal connector property panel for editing property of Create connector.
 *
 * @author Valeriy Svydenko
 */
public class DescribeSubjectsPropertiesPanelViewImpl extends DescribeSubjectsPropertiesPanelView {

    @Singleton
    interface DescribeSobjectsPropertiesPanelViewImplUiBinder extends UiBinder<Widget, DescribeSubjectsPropertiesPanelViewImpl> {
    }

    @UiField
    SimplePanel baseConnector;

    @UiField
    TextBox   subjects;
    @UiField
    TextBox   subjectsNamespace;
    @UiField
    FlowPanel subjectsNamespacePanel;

    @UiField(provided = true)
    final EditorResources                res;
    @UiField(provided = true)
    final WSO2EditorLocalizationConstant loc;

    @Inject
    public DescribeSubjectsPropertiesPanelViewImpl(DescribeSobjectsPropertiesPanelViewImplUiBinder ourUiBinder,
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
    @Override
    public void setSubjectsNamespace(@Nullable String loginUrl) {
        subjectsNamespace.setText(loginUrl);
    }

    /** {@inheritDoc} */
    @Override
    public void addBaseConnector(@Nonnull BaseConnectorPanelPresenter base) {
        base.go(baseConnector);
    }

    @UiHandler("subjects")
    public void onLoginUrlChanged(KeyUpEvent event) {
        delegate.onSubjectChanged();
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleSubjectsNamespacePanel(boolean isVisible) {
        subjectsNamespacePanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleSubjects(boolean isVisible) {
        subjects.setVisible(isVisible);
    }

    @UiHandler("subjectsButton")
    public void onLoginUrlButtonClicked(ClickEvent event) {
        delegate.subjectsButtonClicked();
    }
}
