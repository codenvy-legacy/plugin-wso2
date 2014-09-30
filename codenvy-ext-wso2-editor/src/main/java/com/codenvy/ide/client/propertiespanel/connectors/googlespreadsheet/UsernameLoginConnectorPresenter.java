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
package com.codenvy.ide.client.propertiespanel.connectors.googlespreadsheet;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GoogleSpreadsheetPropertyManager;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.UsernameLogin;
import com.codenvy.ide.client.managers.PropertyTypeManager;
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
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.UsernameLogin.PASSWORD_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.UsernameLogin.PASSWORD_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.UsernameLogin.PASSWORD_NS_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.UsernameLogin.USERNAME_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.UsernameLogin.USERNAME_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.UsernameLogin.USERNAME_NS_KEY;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class UsernameLoginConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<UsernameLogin> {
    private ComplexPropertyPresenter usernameNS;
    private ComplexPropertyPresenter passwordNS;
    private SimplePropertyPresenter  username;
    private SimplePropertyPresenter  password;

    @Inject
    public UsernameLoginConnectorPresenter(WSO2EditorLocalizationConstant locale,
                                           NameSpaceEditorPresenter nameSpacePresenter,
                                           PropertiesPanelView view,
                                           GoogleSpreadsheetPropertyManager googleSpreadsheetPropertyManager,
                                           ParameterPresenter parameterPresenter,
                                           PropertyTypeManager propertyTypeManager,
                                           PropertyPanelFactory propertyPanelFactory) {
        super(view,
              googleSpreadsheetPropertyManager,
              parameterPresenter,
              nameSpacePresenter,
              propertyTypeManager,
              locale,
              propertyPanelFactory);

        prepareView();
    }

    private void prepareView() {
        usernameNS = createComplexConnectorProperty(locale.spreadsheetUsernameLoginUsername(),
                                                    USERNAME_NS_KEY,
                                                    USERNAME_EXPRESSION_KEY);

        passwordNS = createComplexConnectorProperty(locale.spreadsheetUsernameLoginPassword(),
                                                    PASSWORD_NS_KEY,
                                                    PASSWORD_EXPRESSION_KEY);

        username = createSimpleConnectorProperty(locale.spreadsheetUsernameLoginUsername(), USERNAME_KEY);

        password = createSimpleConnectorProperty(locale.spreadsheetUsernameLoginPassword(), PASSWORD_KEY);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        ParameterEditorType property = element.getProperty(PARAMETER_EDITOR_TYPE);
        boolean isNameSpaced = NAME_SPACED_PROPERTY_EDITOR.equals(property);

        username.setVisible(!isNameSpaced);
        password.setVisible(!isNameSpaced);

        usernameNS.setVisible(isNameSpaced);
        passwordNS.setVisible(isNameSpaced);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        username.setProperty(element.getProperty(USERNAME_KEY));
        password.setProperty(element.getProperty(PASSWORD_KEY));

        usernameNS.setProperty(element.getProperty(USERNAME_EXPRESSION_KEY));
        passwordNS.setProperty(element.getProperty(PASSWORD_EXPRESSION_KEY));
    }
}
