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

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.connectors.salesforce.Init;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.connectors.base.BaseConnectorPanelPresenter;
import com.codenvy.ide.client.propertiespanel.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.codenvy.ide.client.elements.connectors.salesforce.BaseSalesforce.ParameterEditorType;

/**
 * The presenter that provides a business logic of 'Init' connector properties panel for salesforce connectors.
 *
 * @author Valeriy Svydenko
 */
public class InitPropertiesPanelPresenter extends AbstractPropertiesPanel<Init, InitPropertiesPanelView>
        implements InitPropertiesPanelView.ActionDelegate, BaseConnectorPanelPresenter.BasePropertyChangedListener {

    private final WSO2EditorLocalizationConstant local;
    private final BaseConnectorPanelPresenter    baseConnectorPresenter;
    private final NameSpaceEditorPresenter       nameSpaceEditorPresenter;
    private final AddNameSpacesCallBack          addUsernameNameSpacesCallBack;
    private final AddNameSpacesCallBack          addPasswordNameSpacesCallBack;
    private final AddNameSpacesCallBack          addForceLoginNameSpacesCallBack;
    private final AddNameSpacesCallBack          addLoginUrlNameSpacesCallBack;

    @Inject
    public InitPropertiesPanelPresenter(InitPropertiesPanelView view,
                                        PropertyTypeManager propertyTypeManager,
                                        WSO2EditorLocalizationConstant local,
                                        BaseConnectorPanelPresenter baseConnectorPresenter,
                                        NameSpaceEditorPresenter nameSpaceEditorPresenter) {
        super(view, propertyTypeManager);

        this.local = local;
        this.nameSpaceEditorPresenter = nameSpaceEditorPresenter;
        this.baseConnectorPresenter = baseConnectorPresenter;
        this.baseConnectorPresenter.addListener(this);

        this.addUsernameNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nullable String expression) {
                element.setUsernameNameSpaces(nameSpaces);
                element.setUsername(expression);

                InitPropertiesPanelPresenter.this.view.setUsernameNamespace(expression);

                notifyListeners();
            }
        };

        this.addPasswordNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nullable String expression) {
                element.setPasswordNameSpaces(nameSpaces);
                element.setPassword(expression);

                InitPropertiesPanelPresenter.this.view.setPasswordNamespace(expression);

                notifyListeners();
            }
        };

        this.addForceLoginNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nullable String expression) {
                element.setForceLoginNameSpaces(nameSpaces);
                element.setForceLogin(expression);

                InitPropertiesPanelPresenter.this.view.setForceLoginNamespace(expression);

                notifyListeners();
            }
        };

        this.addLoginUrlNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nullable String expression) {
                element.setLoginUrlNameSpaces(nameSpaces);
                element.setLoginUrl(expression);

                InitPropertiesPanelPresenter.this.view.setLoginUrlNamespace(expression);

                notifyListeners();
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onUsernameChanged() {
        element.setUsernameInline(view.getUsername());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onPasswordChanged() {
        element.setPasswordInline(view.getPassword());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onLoginUrlChanged() {
        element.setLoginUrlInline(view.getLoginUrl());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onForceLoginChanged() {
        element.setForceLoginInline(view.getForceLogin());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void usernameButtonClicked() {
        nameSpaceEditorPresenter.showWindowWithParameters(element.getUsernameNameSpaces(),
                                                          addUsernameNameSpacesCallBack,
                                                          local.propertiespanelConnectorExpression(),
                                                          element.getUsername());
    }

    /** {@inheritDoc} */
    @Override
    public void passwordButtonClicked() {
        nameSpaceEditorPresenter.showWindowWithParameters(element.getPasswordNameSpaces(),
                                                          addPasswordNameSpacesCallBack,
                                                          local.propertiespanelConnectorExpression(),
                                                          element.getPassword());
    }

    /** {@inheritDoc} */
    @Override
    public void forceLoginButtonClicked() {
        nameSpaceEditorPresenter.showWindowWithParameters(element.getForceLoginNameSpaces(),
                                                          addForceLoginNameSpacesCallBack,
                                                          local.propertiespanelConnectorExpression(),
                                                          element.getForceLogin());
    }

    /** {@inheritDoc} */
    @Override
    public void loginUrlButtonClicked() {
        nameSpaceEditorPresenter.showWindowWithParameters(element.getLoginUrlNameSpaces(),
                                                          addLoginUrlNameSpacesCallBack,
                                                          local.propertiespanelConnectorExpression(),
                                                          element.getLoginUrl());
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        view.addBaseConnector(baseConnectorPresenter);
        baseConnectorPresenter.setConfigRef(element.getConfigRef());

        view.setUsernameNamespace(element.getUsername());
        view.setUsername(element.getUsernameInline());

        view.setPassword(element.getPasswordInline());
        view.setPasswordNamespace(element.getPassword());

        view.setLoginUrl(element.getLoginUrlInline());
        view.setLoginUrlNamespace(element.getLoginUrl());

        view.setForceLogin(element.getForceLoginInline());
        view.setForceLoginNamespace(element.getForceLogin());
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertyChanged(@Nonnull ParameterEditorType parameterEditorType, @Nonnull String configRef) {
        element.setParameterEditorType(parameterEditorType);
        element.setConfigRef(configRef);

        view.onParameterEditorTypeChanged(parameterEditorType);

        notifyListeners();
    }
}
