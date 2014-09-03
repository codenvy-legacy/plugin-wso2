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
package com.codenvy.ide.client.propertiespanel.connectors.salesforce.base.parameter;

import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.ValueType;
import com.codenvy.ide.client.propertiespanel.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.collections.Array;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.ValueType.EXPRESSION;
import static com.codenvy.ide.client.elements.ValueType.LITERAL;
import static com.codenvy.ide.collections.Collections.createArray;

/**
 * The presenter that provides a business logic of editing of new configuration parameter of connector.
 *
 * @author Valeriy Svydenko
 */
public class ParameterPresenter implements ParameterView.ActionDelegate {

    private final ParameterView            view;
    private final NameSpaceEditorPresenter nameSpaceEditorPresenter;
    private final AddNameSpacesCallBack    usernameNameSpacesCallBack;
    private final AddNameSpacesCallBack    loginUrlNameSpacesCallBack;
    private final AddNameSpacesCallBack    forceLoginNameSpacesCallBack;
    private final AddNameSpacesCallBack    passwordNameSpacesCallBack;

    private Array<NameSpace>           usernameNameSpaces;
    private Array<NameSpace>           passwordNameSpaces;
    private Array<NameSpace>           loginUrlNameSpaces;
    private Array<NameSpace>           forceLoginNameSpaces;
    private ConnectorParameterCallBack callBack;

    @Inject
    public ParameterPresenter(ParameterView view, NameSpaceEditorPresenter nameSpaceEditorPresenter) {
        this.view = view;
        this.view.setDelegate(this);

        usernameNameSpaces = createArray();
        passwordNameSpaces = createArray();
        loginUrlNameSpaces = createArray();
        forceLoginNameSpaces = createArray();

        this.nameSpaceEditorPresenter = nameSpaceEditorPresenter;

        this.usernameNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                usernameNameSpaces = nameSpaces;
                ParameterPresenter.this.view.setUsernameNamespaceValue(expression);
            }
        };

        this.passwordNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                passwordNameSpaces = nameSpaces;
                ParameterPresenter.this.view.setPasswordNamespaceValue(expression);
            }
        };

        this.forceLoginNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                forceLoginNameSpaces = nameSpaces;
                ParameterPresenter.this.view.setForceLoginNamespaceValue(expression);
            }
        };

        this.loginUrlNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                loginUrlNameSpaces = nameSpaces;
                ParameterPresenter.this.view.setLoginUrlNamespaceValue(expression);
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onOkButtonClicked() {
        callBack.onAddressPropertyChanged(view.getName());

        view.hideDialog();
    }

    /** {@inheritDoc} */
    @Override
    public void onCancelButtonClicked() {
        view.hideDialog();
    }

    /** {@inheritDoc} */
    @Override
    public void onUsernameBtnClicked() {
        nameSpaceEditorPresenter.showDefaultWindow(usernameNameSpaces, usernameNameSpacesCallBack);
    }

    /** {@inheritDoc} */
    @Override
    public void onUsernameTypeChanged() {
        ValueType type = ValueType.valueOf(view.getUsernameType());

        view.setUsernamePanelVisible(EXPRESSION.equals(type));
    }

    /** {@inheritDoc} */
    @Override
    public void onPasswordBtnClicked() {
        nameSpaceEditorPresenter.showDefaultWindow(passwordNameSpaces, passwordNameSpacesCallBack);
    }

    /** {@inheritDoc} */
    @Override
    public void onPasswordTypeChanged() {
        ValueType type = ValueType.valueOf(view.getPasswordType());

        view.setPasswordPanelVisible(EXPRESSION.equals(type));
    }

    /** {@inheritDoc} */
    @Override
    public void onLoginUrlBtnClicked() {
        nameSpaceEditorPresenter.showDefaultWindow(loginUrlNameSpaces, loginUrlNameSpacesCallBack);
    }

    /** {@inheritDoc} */
    @Override
    public void onLoginUrlTypeChanged() {
        ValueType type = ValueType.valueOf(view.getLoginUrlType());

        view.setLoginUrlPanelVisible(EXPRESSION.equals(type));
    }

    /** {@inheritDoc} */
    @Override
    public void onForceLoginBtnClicked() {
        nameSpaceEditorPresenter.showDefaultWindow(forceLoginNameSpaces, forceLoginNameSpacesCallBack);
    }

    /** {@inheritDoc} */
    @Override
    public void onForceLoginTypeChanged() {
        ValueType type = ValueType.valueOf(view.getForceLoginType());

        view.setForceLoginPanelVisible(EXPRESSION.equals(type));
    }

    /** {@inheritDoc} */
    public void showDialog(@Nonnull ConnectorParameterCallBack callBack) {
        this.callBack = callBack;

        view.setName("");

        view.selectUsernameType(LITERAL.name());
        view.setUsernamePanelVisible(false);
        view.setUsernameNamespaceValue("");
        view.setUsernameValue("");

        view.selectPasswordType(LITERAL.name());
        view.setPasswordPanelVisible(false);
        view.setPasswordNamespaceValue("");
        view.setPasswordValue("");

        view.selectLoginUrlType(LITERAL.name());
        view.setLoginUrlPanelVisible(false);
        view.setLoginUrlNamespaceValue("");
        view.setLoginUrlValue("");

        view.selectForceLoginType(LITERAL.name());
        view.setForceLoginPanelVisible(false);
        view.setForceLoginNamespaceValue("");
        view.setForceLoginValue("");

        view.showDialog();
    }

}
