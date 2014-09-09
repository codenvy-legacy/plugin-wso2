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
package com.codenvy.ide.client.propertiespanel.connectors.base.parameter;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.ui.window.Window;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.codenvy.ide.client.elements.mediators.ValueType.EXPRESSION;
import static com.codenvy.ide.client.elements.mediators.ValueType.LITERAL;

/**
 * The implementation of {ParameterView}
 *
 * @author Valeriy Svydenko
 */
public class ParameterViewImpl extends Window implements ParameterView {

    @Singleton
    interface ParameterViewImplUiBinder extends UiBinder<Widget, ParameterViewImpl> {
    }

    @UiField(provided = true)
    final WSO2EditorLocalizationConstant locale;

    @UiField
    TextBox name;

    @UiField
    ListBox usernameType;
    @UiField
    TextBox usernameNamespaceValue;
    @UiField
    TextBox usernameValue;
    @UiField
    Button  usernameBtn;

    @UiField
    TextBox passwordValue;
    @UiField
    TextBox passwordNamespaceValue;
    @UiField
    ListBox passwordType;
    @UiField
    Button  passwordBtn;

    @UiField
    ListBox loginUrlType;
    @UiField
    TextBox loginUrlNamespaceValue;
    @UiField
    TextBox loginUrlValue;
    @UiField
    Button  loginUrlBtn;

    @UiField
    TextBox forceLoginValue;
    @UiField
    TextBox forceLoginNamespaceValue;
    @UiField
    ListBox forceLoginType;
    @UiField
    Button  forceLoginBtn;

    private ActionDelegate delegate;

    @Inject
    public ParameterViewImpl(ParameterViewImplUiBinder ourUiBinder, WSO2EditorLocalizationConstant locale) {
        this.locale = locale;

        setTitle(locale.propertiespanelConnectorConfigurationTitle());
        setWidget(ourUiBinder.createAndBindUi(this));

        initTypeListBox();

        Button btnCancel = createButton(locale.buttonCancel(), "connector-parameter-cancel", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onCancelButtonClicked();
            }
        });
        getFooter().add(btnCancel);

        Button btnOk = createButton(locale.buttonOk(), "connector-parameter-ok", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onOkButtonClicked();
            }
        });
        getFooter().add(btnOk);
    }

    /** {@inheritDoc} */
    @Override
    public void setDelegate(@Nonnull ActionDelegate delegate) {
        this.delegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public void showDialog() {
        show();
    }

    /** {@inheritDoc} */
    @Override
    public void hideDialog() {
        hide();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getUsernameType() {
        return getSelectedItem(usernameType);
    }

    /** {@inheritDoc} */
    @Override
    public void selectUsernameType(@Nonnull String parameterType) {
        selectType(usernameType, parameterType);
    }

    /** {@inheritDoc} */
    @Override
    public void setUsernamePanelVisible(boolean isVisible) {
        usernameNamespaceValue.setVisible(isVisible);
        usernameBtn.setEnabled(isVisible);
        usernameValue.setVisible(!isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setUsernameValue(@Nonnull String value) {
        usernameValue.setText(value);
    }

    /** {@inheritDoc} */
    @Override
    public void setUsernameNamespaceValue(@Nonnull String value) {
        usernameNamespaceValue.setText(value);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getLoginUrlType() {
        return getSelectedItem(loginUrlType);
    }

    /** {@inheritDoc} */
    @Override
    public void selectLoginUrlType(@Nonnull String parameterType) {
        selectType(loginUrlType, parameterType);
    }

    /** {@inheritDoc} */
    @Override
    public void setLoginUrlPanelVisible(boolean isVisible) {
        loginUrlNamespaceValue.setVisible(isVisible);
        loginUrlBtn.setEnabled(isVisible);
        loginUrlValue.setVisible(!isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setLoginUrlValue(@Nonnull String value) {
        loginUrlValue.setText(value);
    }

    /** {@inheritDoc} */
    @Override
    public void setLoginUrlNamespaceValue(@Nonnull String value) {
        loginUrlNamespaceValue.setText(value);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getPasswordType() {
        return getSelectedItem(passwordType);
    }

    /** {@inheritDoc} */
    @Override
    public void selectPasswordType(@Nonnull String parameterType) {
        selectType(passwordType, parameterType);
    }

    /** {@inheritDoc} */
    @Override
    public void setPasswordPanelVisible(boolean isVisible) {
        passwordNamespaceValue.setVisible(isVisible);
        passwordBtn.setEnabled(isVisible);
        passwordValue.setVisible(!isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setPasswordValue(@Nonnull String value) {
        passwordValue.setText(value);
    }

    /** {@inheritDoc} */
    @Override
    public void setPasswordNamespaceValue(@Nonnull String value) {
        passwordNamespaceValue.setText(value);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getForceLoginType() {
        return getSelectedItem(forceLoginType);
    }

    /** {@inheritDoc} */
    @Override
    public void selectForceLoginType(@Nonnull String parameterType) {
        selectType(forceLoginType, parameterType);
    }

    /** {@inheritDoc} */
    @Override
    public void setForceLoginPanelVisible(boolean isVisible) {
        forceLoginNamespaceValue.setVisible(isVisible);
        forceLoginBtn.setEnabled(isVisible);
        forceLoginValue.setVisible(!isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setForceLoginValue(@Nonnull String value) {
        forceLoginValue.setText(value);
    }

    /** {@inheritDoc} */
    @Override
    public void setForceLoginNamespaceValue(@Nonnull String value) {
        forceLoginNamespaceValue.setText(value);
    }

    /** {@inheritDoc} */
    @Override
    public void setName(@Nonnull String name) {
        this.name.setText(name);
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    public String getName() {
        return name.getText();
    }

    /** {@inheritDoc} */
    @Override
    protected void onClose() {
        hide();
    }

    @UiHandler("usernameBtn")
    public void onUsernameButtonClicked(ClickEvent event) {
        delegate.onUsernameBtnClicked();
    }

    @UiHandler("usernameType")
    public void onUsernameTypeChanged(ChangeEvent event) {
        delegate.onUsernameTypeChanged();
    }

    @UiHandler("passwordBtn")
    public void onPasswordButtonClicked(ClickEvent event) {
        delegate.onPasswordBtnClicked();
    }

    @UiHandler("passwordType")
    public void onPasswordTypeChanged(ChangeEvent event) {
        delegate.onPasswordTypeChanged();
    }

    @UiHandler("loginUrlBtn")
    public void onLoginUrlButtonClicked(ClickEvent event) {
        delegate.onLoginUrlBtnClicked();
    }

    @UiHandler("loginUrlType")
    public void onLoginUrlTypeChanged(ChangeEvent event) {
        delegate.onLoginUrlTypeChanged();
    }

    @UiHandler("forceLoginBtn")
    public void onForceLoginButtonClicked(ClickEvent event) {
        delegate.onForceLoginBtnClicked();
    }

    @UiHandler("forceLoginType")
    public void onForceLoginTypeChanged(ChangeEvent event) {
        delegate.onForceLoginTypeChanged();
    }

    /**
     * Select item in the field.
     *
     * @param field
     *         field that needs to be changed
     * @param type
     *         a new selected item
     */
    private void selectType(@Nonnull ListBox field, @Nullable String type) {
        for (int i = 0; i < field.getItemCount(); i++) {
            if (field.getValue(i).equals(type)) {
                field.setItemSelected(i, true);
                return;
            }
        }
    }

    /**
     * Returns a selected item of field.
     *
     * @param field
     *         field that contains item
     * @return a selected item
     */
    @Nonnull
    private String getSelectedItem(@Nonnull ListBox field) {
        int index = field.getSelectedIndex();
        return index != -1 ? field.getValue(field.getSelectedIndex()) : "";
    }

    /** Adds type parameters to list box */
    private void initTypeListBox() {
        usernameType.addItem(LITERAL.name());
        usernameType.addItem(EXPRESSION.name());

        passwordType.addItem(LITERAL.name());
        passwordType.addItem(EXPRESSION.name());

        loginUrlType.addItem(LITERAL.name());
        loginUrlType.addItem(EXPRESSION.name());

        forceLoginType.addItem(LITERAL.name());
        forceLoginType.addItem(EXPRESSION.name());
    }
}
