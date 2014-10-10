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
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.InitSpreadsheet;
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
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.InitSpreadsheet.OAUTH_ACCESS_TOKEN_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.InitSpreadsheet.OAUTH_ACCESS_TOKEN_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.InitSpreadsheet.OAUTH_ACCESS_TOKEN_NS_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.InitSpreadsheet.OAUTH_ACCESS_TOKEN_SECRET_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.InitSpreadsheet.OAUTH_ACCESS_TOKEN_SECRET_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.InitSpreadsheet.OAUTH_ACCESS_TOKEN_SECRET_NS_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.InitSpreadsheet.OAUTH_CONSUMER_KEY_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.InitSpreadsheet.OAUTH_CONSUMER_KEY_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.InitSpreadsheet.OAUTH_CONSUMER_KEY_NS_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.InitSpreadsheet.OAUTH_CONSUMER_SECRET_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.InitSpreadsheet.OAUTH_CONSUMER_SECRET_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.InitSpreadsheet.OAUTH_CONSUMER_SECRET_NS_KEY;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class InitSpreadsheetConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<InitSpreadsheet> {
    private ComplexPropertyPresenter oauthConsumerKeyNS;
    private ComplexPropertyPresenter oauthConsumerSecretNS;
    private ComplexPropertyPresenter oauthAccessTokenNS;
    private ComplexPropertyPresenter oauthAccessTokenSecretNS;

    private SimplePropertyPresenter oauthConsumerKey;
    private SimplePropertyPresenter oauthConsumerSecret;
    private SimplePropertyPresenter oauthAccessToken;
    private SimplePropertyPresenter oauthAccessTokenSecret;

    @Inject
    public InitSpreadsheetConnectorPresenter(WSO2EditorLocalizationConstant locale,
                                             NameSpaceEditorPresenter nameSpacePresenter,
                                             PropertiesPanelView view,
                                             GoogleSpreadsheetPropertyManager googleSpreadsheetPropertyManager,
                                             ParameterPresenter parameterPresenter,
                                             PropertyTypeManager propertyTypeManager,
                                             PropertyPanelFactory propertyPanelFactory,
                                             SelectionManager selectionManager) {
        super(view,
              googleSpreadsheetPropertyManager,
              parameterPresenter,
              nameSpacePresenter,
              propertyTypeManager,
              locale,
              propertyPanelFactory,
              selectionManager);

        prepareView();
    }

    private void prepareView() {
        oauthConsumerKeyNS = createComplexConnectorProperty(locale.spreadsheetInitOauthConsumerKey(),
                                                            OAUTH_CONSUMER_KEY_NS_KEY,
                                                            OAUTH_CONSUMER_KEY_EXPRESSION_KEY);

        oauthConsumerSecretNS = createComplexConnectorProperty(locale.spreadsheetInitOauthConsumerSecret(),
                                                               OAUTH_CONSUMER_SECRET_NS_KEY,
                                                               OAUTH_CONSUMER_SECRET_EXPRESSION_KEY);
        oauthAccessTokenNS = createComplexConnectorProperty(locale.spreadsheetInitOauthAccessToken(),
                                                            OAUTH_ACCESS_TOKEN_NS_KEY,
                                                            OAUTH_ACCESS_TOKEN_EXPRESSION_KEY);
        oauthAccessTokenSecretNS = createComplexConnectorProperty(locale.spreadsheetInitOauthAccessTokenSecret(),
                                                                  OAUTH_ACCESS_TOKEN_SECRET_NS_KEY,
                                                                  OAUTH_ACCESS_TOKEN_SECRET_EXPRESSION_KEY);

        oauthConsumerKey = createSimpleConnectorProperty(locale.spreadsheetInitOauthConsumerKey(), OAUTH_CONSUMER_KEY_KEY);
        oauthConsumerSecret = createSimpleConnectorProperty(locale.spreadsheetInitOauthConsumerSecret(), OAUTH_CONSUMER_SECRET_KEY);
        oauthAccessToken = createSimpleConnectorProperty(locale.spreadsheetInitOauthAccessToken(), OAUTH_ACCESS_TOKEN_KEY);
        oauthAccessTokenSecret = createSimpleConnectorProperty(locale.spreadsheetInitOauthAccessTokenSecret(),
                                                               OAUTH_ACCESS_TOKEN_SECRET_KEY);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        ParameterEditorType property = element.getProperty(PARAMETER_EDITOR_TYPE);
        boolean isNameSpaced = NAME_SPACED_PROPERTY_EDITOR.equals(property);

        oauthConsumerKey.setVisible(!isNameSpaced);
        oauthConsumerSecret.setVisible(!isNameSpaced);
        oauthAccessToken.setVisible(!isNameSpaced);
        oauthAccessTokenSecret.setVisible(!isNameSpaced);

        oauthConsumerKeyNS.setVisible(isNameSpaced);
        oauthConsumerSecretNS.setVisible(isNameSpaced);
        oauthAccessTokenNS.setVisible(isNameSpaced);
        oauthAccessTokenSecretNS.setVisible(isNameSpaced);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        oauthConsumerKey.setProperty(element.getProperty(OAUTH_CONSUMER_KEY_KEY));
        oauthConsumerSecret.setProperty(element.getProperty(OAUTH_CONSUMER_SECRET_KEY));
        oauthAccessToken.setProperty(element.getProperty(OAUTH_ACCESS_TOKEN_KEY));
        oauthAccessTokenSecret.setProperty(element.getProperty(OAUTH_ACCESS_TOKEN_SECRET_KEY));

        oauthConsumerKeyNS.setProperty(element.getProperty(OAUTH_CONSUMER_KEY_EXPRESSION_KEY));
        oauthConsumerSecretNS.setProperty(element.getProperty(OAUTH_CONSUMER_SECRET_EXPRESSION_KEY));
        oauthAccessTokenNS.setProperty(element.getProperty(OAUTH_ACCESS_TOKEN_EXPRESSION_KEY));
        oauthAccessTokenSecretNS.setProperty(element.getProperty(OAUTH_ACCESS_TOKEN_SECRET_EXPRESSION_KEY));
    }

}
