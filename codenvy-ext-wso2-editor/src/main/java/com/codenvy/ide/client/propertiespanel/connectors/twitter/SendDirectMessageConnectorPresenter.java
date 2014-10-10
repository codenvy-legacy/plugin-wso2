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
package com.codenvy.ide.client.propertiespanel.connectors.twitter;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.connectors.twitter.SendDirectMessage;
import com.codenvy.ide.client.elements.connectors.twitter.TwitterPropertyManager;
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
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.INLINE;
import static com.codenvy.ide.client.elements.connectors.twitter.SendDirectMessage.ACCESS_TOKEN_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.SendDirectMessage.ACCESS_TOKEN_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.SendDirectMessage.ACCESS_TOKEN_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.SendDirectMessage.ACCESS_TOKEN_SECRET_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.SendDirectMessage.ACCESS_TOKEN_SECRET_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.SendDirectMessage.ACCESS_TOKEN_SECRET_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.SendDirectMessage.CONSUMER_KEY_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.SendDirectMessage.CONSUMER_KEY_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.SendDirectMessage.CONSUMER_KEY_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.SendDirectMessage.CONSUMER_SECRET_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.SendDirectMessage.CONSUMER_SECRET_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.SendDirectMessage.CONSUMER_SECRET_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.SendDirectMessage.MESSAGE_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.SendDirectMessage.MESSAGE_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.SendDirectMessage.MESSAGE_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.SendDirectMessage.SCREEN_NAME_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.SendDirectMessage.SCREEN_NAME_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.SendDirectMessage.SCREEN_NAME_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.SendDirectMessage.USER_ID_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.SendDirectMessage.USER_ID_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.SendDirectMessage.USER_ID_NS;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class SendDirectMessageConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<SendDirectMessage> {

    private SimplePropertyPresenter consumerKey;
    private SimplePropertyPresenter consumerSecret;
    private SimplePropertyPresenter accessToken;
    private SimplePropertyPresenter accessTokenSecret;
    private SimplePropertyPresenter userId;
    private SimplePropertyPresenter screenName;
    private SimplePropertyPresenter message;

    private ComplexPropertyPresenter consumerKeyExpr;
    private ComplexPropertyPresenter consumerSecretExpr;
    private ComplexPropertyPresenter accessTokenExpr;
    private ComplexPropertyPresenter accessTokenSecretExpr;
    private ComplexPropertyPresenter userIdExpr;
    private ComplexPropertyPresenter screenNameExpr;
    private ComplexPropertyPresenter messageExpr;

    @Inject
    public SendDirectMessageConnectorPresenter(WSO2EditorLocalizationConstant locale,
                                               NameSpaceEditorPresenter nameSpacePresenter,
                                               PropertiesPanelView view,
                                               TwitterPropertyManager twitterPropertyManager,
                                               ParameterPresenter parameterPresenter,
                                               PropertyTypeManager propertyTypeManager,
                                               PropertyPanelFactory propertyPanelFactory,
                                               SelectionManager selectionManager) {
        super(view,
              twitterPropertyManager,
              parameterPresenter,
              nameSpacePresenter,
              propertyTypeManager,
              locale,
              propertyPanelFactory,
              selectionManager);

        prepareView();
    }

    private void prepareView() {
        consumerKey = createSimpleConnectorProperty(locale.twitterConsumerKey(), CONSUMER_KEY_INL);
        consumerSecret = createSimpleConnectorProperty(locale.twitterConsumerSecret(), CONSUMER_SECRET_INL);
        accessToken = createSimpleConnectorProperty(locale.twitterAccessToken(), ACCESS_TOKEN_INL);
        accessTokenSecret = createSimpleConnectorProperty(locale.twitterAccessTokenSecret(), ACCESS_TOKEN_SECRET_INL);
        userId = createSimpleConnectorProperty(locale.twitterUserId(), USER_ID_INL);
        screenName = createSimpleConnectorProperty(locale.twitterScreenName(), SCREEN_NAME_INL);
        message = createSimpleConnectorProperty(locale.twitterMessage(), MESSAGE_INL);

        consumerKeyExpr = createComplexConnectorProperty(locale.twitterConsumerKey(), CONSUMER_KEY_NS, CONSUMER_KEY_EXPR);
        consumerSecretExpr = createComplexConnectorProperty(locale.twitterConsumerSecret(), CONSUMER_SECRET_NS, CONSUMER_SECRET_EXPR);
        accessTokenExpr = createComplexConnectorProperty(locale.twitterAccessToken(), ACCESS_TOKEN_NS, ACCESS_TOKEN_EXPR);
        accessTokenSecretExpr = createComplexConnectorProperty(locale.twitterAccessTokenSecret(), ACCESS_TOKEN_SECRET_NS,
                                                               ACCESS_TOKEN_SECRET_EXPR);
        userIdExpr = createComplexConnectorProperty(locale.twitterUserId(), USER_ID_NS, USER_ID_EXPR);
        screenNameExpr = createComplexConnectorProperty(locale.twitterScreenName(), SCREEN_NAME_NS, SCREEN_NAME_EXPR);
        messageExpr = createComplexConnectorProperty(locale.twitterMessage(), MESSAGE_NS, MESSAGE_EXPR);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        boolean isVisible = INLINE.equals(element.getProperty(PARAMETER_EDITOR_TYPE));

        consumerKey.setVisible(isVisible);
        consumerSecret.setVisible(isVisible);
        accessToken.setVisible(isVisible);
        accessTokenSecret.setVisible(isVisible);
        userId.setVisible(isVisible);
        screenName.setVisible(isVisible);
        message.setVisible(isVisible);

        consumerKeyExpr.setVisible(!isVisible);
        consumerSecretExpr.setVisible(!isVisible);
        accessTokenExpr.setVisible(!isVisible);
        accessTokenSecretExpr.setVisible(!isVisible);
        userIdExpr.setVisible(!isVisible);
        screenNameExpr.setVisible(!isVisible);
        messageExpr.setVisible(!isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        consumerKey.setProperty(element.getProperty(CONSUMER_KEY_INL));
        consumerSecret.setProperty(element.getProperty(CONSUMER_SECRET_INL));
        accessToken.setProperty(element.getProperty(ACCESS_TOKEN_INL));
        accessTokenSecret.setProperty(element.getProperty(ACCESS_TOKEN_SECRET_INL));
        userId.setProperty(element.getProperty(USER_ID_INL));
        screenName.setProperty(element.getProperty(SCREEN_NAME_INL));
        message.setProperty(element.getProperty(MESSAGE_INL));

        consumerKeyExpr.setProperty(element.getProperty(CONSUMER_KEY_EXPR));
        consumerSecretExpr.setProperty(element.getProperty(CONSUMER_SECRET_EXPR));
        accessTokenExpr.setProperty(element.getProperty(ACCESS_TOKEN_EXPR));
        accessTokenSecretExpr.setProperty(element.getProperty(ACCESS_TOKEN_SECRET_EXPR));
        userIdExpr.setProperty(element.getProperty(USER_ID_EXPR));
        screenNameExpr.setProperty(element.getProperty(SCREEN_NAME_EXPR));
        messageExpr.setProperty(element.getProperty(MESSAGE_EXPR));
    }
}
