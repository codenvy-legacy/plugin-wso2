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
package com.codenvy.ide.client.propertiespanel.connectors.salesforce.init;

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
 * The implementation of {InitPropertiesPanelView}
 *
 * @author Valeriy Svydenko
 */
public class InitPropertiesPanelViewImpl extends InitPropertiesPanelView {

    @Singleton
    interface InitPropertiesPanelViewImplUiBinder extends UiBinder<Widget, InitPropertiesPanelViewImpl> {
    }

    @UiField
    SimplePanel baseConnector;
    @UiField
    TextBox     username;
    @UiField
    TextBox     password;
    @UiField
    TextBox     loginUrl;
    @UiField
    TextBox     forceLogin;
    @UiField
    FlowPanel   usernameNamespacePanel;
    @UiField
    TextBox     usernameNamespace;
    @UiField
    FlowPanel   passwordNamespacePanel;
    @UiField
    TextBox     passwordNamespace;
    @UiField
    TextBox     forceLoginNamespace;
    @UiField
    FlowPanel   forceLoginNamespacePanel;
    @UiField
    TextBox     loginUrlNamespace;
    @UiField
    FlowPanel   loginUrlNamespacePanel;

    @UiField(provided = true)
    final EditorResources                res;
    @UiField(provided = true)
    final WSO2EditorLocalizationConstant loc;

    @Inject
    public InitPropertiesPanelViewImpl(InitPropertiesPanelViewImplUiBinder ourUiBinder,
                                       EditorResources res,
                                       WSO2EditorLocalizationConstant loc) {

        this.res = res;
        this.loc = loc;

        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getForceLogin() {
        return forceLogin.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setForceLogin(@Nonnull String forceLogin) {
        this.forceLogin.setText(forceLogin);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getLoginUrl() {
        return loginUrl.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setLoginUrl(@Nonnull String loginUrl) {
        this.loginUrl.setText(loginUrl);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getPassword() {
        return password.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setPassword(@Nonnull String password) {
        this.password.setText(password);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getUsername() {
        return username.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setUsername(@Nonnull String username) {
        this.username.setText(username);
    }

    /** {@inheritDoc} */
    @Override
    public void setForceLoginNamespace(@Nullable String forceLogin) {
        forceLoginNamespace.setText(forceLogin);
    }

    /** {@inheritDoc} */
    @Override
    public void setLoginUrlNamespace(@Nullable String loginUrl) {
        loginUrlNamespace.setText(loginUrl);
    }

    /** {@inheritDoc} */
    @Override
    public void setPasswordNamespace(@Nullable String password) {
        passwordNamespace.setText(password);
    }

    /** {@inheritDoc} */
    @Override
    public void setUsernameNamespace(@Nullable String username) {
        usernameNamespace.setText(username);
    }

    /** {@inheritDoc} */
    @Override
    public void addBaseConnector(@Nonnull BaseConnectorPanelPresenter base) {
        base.go(baseConnector);
    }

    @UiHandler("usernameButton")
    public void onUsernameButtonClicked(ClickEvent event) {
        delegate.usernameButtonClicked();
    }

    @UiHandler("passwordButton")
    public void onPasswordButtonClicked(ClickEvent event) {
        delegate.passwordButtonClicked();
    }

    @UiHandler("forceLoginButton")
    public void onForceLoginButtonClicked(ClickEvent event) {
        delegate.forceLoginButtonClicked();
    }

    @UiHandler("loginUrlButton")
    public void onLoginUrlButtonClicked(ClickEvent event) {
        delegate.loginUrlButtonClicked();
    }

    @UiHandler("username")
    public void onUsernameChanged(KeyUpEvent event) {
        delegate.onUsernameChanged();
    }

    @UiHandler("password")
    public void onPasswordChanged(KeyUpEvent event) {
        delegate.onPasswordChanged();
    }

    @UiHandler("loginUrl")
    public void onLoginUrlChanged(KeyUpEvent event) {
        delegate.onLoginUrlChanged();
    }

    @UiHandler("forceLogin")
    public void onForceLoginChanged(KeyUpEvent event) {
        delegate.onForceLoginChanged();
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleUsername(boolean isVisible) {
        username.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleUsernameNamespacePanel(boolean isVisible) {
        usernameNamespacePanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisiblePasswordNamespacePanel(boolean isVisible) {
        passwordNamespacePanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisiblePassword(boolean isVisible) {
        password.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleLoginUrlNamespacePanel(boolean isVisible) {
        loginUrlNamespacePanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleLoginUrl(boolean isVisible) {
        loginUrl.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleForceLoginNamespacePanel(boolean isVisible) {
        forceLoginNamespacePanel.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleForceLogin(boolean isVisible) {
        forceLogin.setVisible(isVisible);
    }

}
