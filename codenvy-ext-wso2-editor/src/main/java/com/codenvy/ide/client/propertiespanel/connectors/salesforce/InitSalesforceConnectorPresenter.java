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
package com.codenvy.ide.client.propertiespanel.connectors.salesforce;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.connectors.salesforce.InitSalesforce;
import com.codenvy.ide.client.elements.connectors.salesforce.SalesForcePropertyManager;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.managers.SelectionManager;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.PropertyPanelFactory;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.base.AbstractConnectorPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.base.parameter.ParameterPresenter;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.PARAMETER_EDITOR_TYPE;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NAME_SPACED_PROPERTY_EDITOR;
import static com.codenvy.ide.client.elements.connectors.salesforce.InitSalesforce.FORCE_LOGIN_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.InitSalesforce.FORCE_LOGIN_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.InitSalesforce.FORCE_LOGIN_NS_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.InitSalesforce.LOGIN_URL_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.InitSalesforce.LOGIN_URL_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.InitSalesforce.LOGIN_URL_NS_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.InitSalesforce.PASSWORD_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.InitSalesforce.PASSWORD_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.InitSalesforce.PASSWORD_NS_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.InitSalesforce.USERNAME_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.InitSalesforce.USERNAME_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.InitSalesforce.USERNAME_NS_KEY;

/**
 * The presenter that provides a business logic of 'Init' connector properties panel for salesforce connectors.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class InitSalesforceConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<InitSalesforce> {
    private ComplexPropertyPresenter usernameNS;
    private ComplexPropertyPresenter passwordNS;
    private ComplexPropertyPresenter loginUrlNS;
    private ComplexPropertyPresenter forceLoginNS;
    private SimplePropertyPresenter  username;
    private SimplePropertyPresenter  password;
    private SimplePropertyPresenter  loginUrl;
    private SimplePropertyPresenter  forceLogin;

    @Inject
    public InitSalesforceConnectorPresenter(WSO2EditorLocalizationConstant locale,
                                            NameSpaceEditorPresenter nameSpacePresenter,
                                            PropertiesPanelView view,
                                            SalesForcePropertyManager salesForcePropertyManager,
                                            ParameterPresenter parameterPresenter,
                                            PropertyTypeManager propertyTypeManager,
                                            PropertyPanelFactory propertyPanelFactory,
                                            SelectionManager selectionManager) {
        super(view,
              salesForcePropertyManager,
              parameterPresenter,
              nameSpacePresenter,
              propertyTypeManager,
              locale,
              propertyPanelFactory,
              selectionManager);

        prepareView();
    }

    private void prepareView() {
        usernameNS = createComplexConnectorProperty(locale.connectorUsername(), USERNAME_NS_KEY, USERNAME_EXPRESSION_KEY);
        passwordNS = createComplexConnectorProperty(locale.connectorPassword(), PASSWORD_NS_KEY, PASSWORD_EXPRESSION_KEY);
        loginUrlNS = createComplexConnectorProperty(locale.connectorLoginUrl(), LOGIN_URL_NS_KEY, LOGIN_URL_EXPRESSION_KEY);
        forceLoginNS = createComplexConnectorProperty(locale.connectorForceLogin(), FORCE_LOGIN_NS_KEY, FORCE_LOGIN_EXPRESSION_KEY);

        username = createSimpleConnectorProperty(locale.connectorUsername(), USERNAME_KEY);
        password = createSimpleConnectorProperty(locale.connectorPassword(), PASSWORD_KEY);
        loginUrl = createSimpleConnectorProperty(locale.connectorLoginUrl(), LOGIN_URL_KEY);
        forceLogin = createSimpleConnectorProperty(locale.connectorForceLogin(), FORCE_LOGIN_KEY);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        ParameterEditorType property = element.getProperty(PARAMETER_EDITOR_TYPE);
        boolean isNameSpaced = NAME_SPACED_PROPERTY_EDITOR.equals(property);

        username.setVisible(!isNameSpaced);
        password.setVisible(!isNameSpaced);
        loginUrl.setVisible(!isNameSpaced);
        forceLogin.setVisible(!isNameSpaced);

        usernameNS.setVisible(isNameSpaced);
        passwordNS.setVisible(isNameSpaced);
        loginUrlNS.setVisible(isNameSpaced);
        forceLoginNS.setVisible(isNameSpaced);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        username.setProperty(element.getProperty(USERNAME_KEY));
        password.setProperty(element.getProperty(PASSWORD_KEY));
        loginUrl.setProperty(element.getProperty(LOGIN_URL_KEY));
        forceLogin.setProperty(element.getProperty(FORCE_LOGIN_KEY));

        usernameNS.setProperty(element.getProperty(USERNAME_EXPRESSION_KEY));
        passwordNS.setProperty(element.getProperty(PASSWORD_EXPRESSION_KEY));
        loginUrlNS.setProperty(element.getProperty(LOGIN_URL_EXPRESSION_KEY));
        forceLoginNS.setProperty(element.getProperty(FORCE_LOGIN_EXPRESSION_KEY));
    }

}