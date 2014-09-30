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
import com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages;
import com.codenvy.ide.client.elements.connectors.twitter.TwitterPropertyManager;
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
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.INLINE;
import static com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages.ACCESS_TOKEN_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages.ACCESS_TOKEN_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages.ACCESS_TOKEN_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages.ACCESS_TOKEN_SECRET_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages.ACCESS_TOKEN_SECRET_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages.ACCESS_TOKEN_SECRET_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages.CONSUMER_KEY_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages.CONSUMER_KEY_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages.CONSUMER_KEY_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages.CONSUMER_SECRET_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages.CONSUMER_SECRET_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages.CONSUMER_SECRET_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages.COUNT_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages.COUNT_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages.COUNT_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages.MAX_ID_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages.MAX_ID_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages.MAX_ID_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages.PAGE_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages.PAGE_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages.PAGE_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages.SINCE_ID_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages.SINCE_ID_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages.SINCE_ID_NS;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class GetSentDirectMessagesConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<GetSentDirectMessages> {

    private SimplePropertyPresenter consumerKey;
    private SimplePropertyPresenter consumerSecret;
    private SimplePropertyPresenter accessToken;
    private SimplePropertyPresenter accessTokenSecret;
    private SimplePropertyPresenter count;
    private SimplePropertyPresenter page;
    private SimplePropertyPresenter sinceId;
    private SimplePropertyPresenter maxId;

    private ComplexPropertyPresenter consumerKeyExpr;
    private ComplexPropertyPresenter consumerSecretExpr;
    private ComplexPropertyPresenter accessTokenExpr;
    private ComplexPropertyPresenter accessTokenSecretExpr;
    private ComplexPropertyPresenter countExpr;
    private ComplexPropertyPresenter pageExpr;
    private ComplexPropertyPresenter sinceIdExpr;
    private ComplexPropertyPresenter maxIdExpr;

    @Inject
    public GetSentDirectMessagesConnectorPresenter(WSO2EditorLocalizationConstant locale,
                                                   NameSpaceEditorPresenter nameSpacePresenter,
                                                   PropertiesPanelView view,
                                                   TwitterPropertyManager twitterPropertyManager,
                                                   ParameterPresenter parameterPresenter,
                                                   PropertyTypeManager propertyTypeManager,
                                                   PropertyPanelFactory propertyPanelFactory) {
        super(view,
              twitterPropertyManager,
              parameterPresenter,
              nameSpacePresenter,
              propertyTypeManager,
              locale,
              propertyPanelFactory);

        prepareView();
    }

    private void prepareView() {
        consumerKey = createSimpleConnectorProperty(locale.twitterConsumerKey(), CONSUMER_KEY_INL);
        consumerSecret = createSimpleConnectorProperty(locale.twitterConsumerSecret(), CONSUMER_SECRET_INL);
        accessToken = createSimpleConnectorProperty(locale.twitterAccessToken(), ACCESS_TOKEN_INL);
        accessTokenSecret = createSimpleConnectorProperty(locale.twitterAccessTokenSecret(), ACCESS_TOKEN_SECRET_INL);
        count = createSimpleConnectorProperty(locale.twitterCount(), COUNT_INL);
        page = createSimpleConnectorProperty(locale.twitterPage(), PAGE_INL);
        sinceId = createSimpleConnectorProperty(locale.twitterSinceId(), SINCE_ID_INL);
        maxId = createSimpleConnectorProperty(locale.twitterMaxId(), MAX_ID_INL);

        consumerKeyExpr = createComplexConnectorProperty(locale.twitterConsumerKey(), CONSUMER_KEY_NS, CONSUMER_KEY_EXPR);
        consumerSecretExpr = createComplexConnectorProperty(locale.twitterConsumerSecret(), CONSUMER_SECRET_NS, CONSUMER_SECRET_EXPR);
        accessTokenExpr = createComplexConnectorProperty(locale.twitterAccessToken(), ACCESS_TOKEN_NS, ACCESS_TOKEN_EXPR);
        accessTokenSecretExpr = createComplexConnectorProperty(locale.twitterAccessTokenSecret(), ACCESS_TOKEN_SECRET_NS,
                                                               ACCESS_TOKEN_SECRET_EXPR);
        countExpr = createComplexConnectorProperty(locale.twitterCount(), COUNT_NS, COUNT_EXPR);
        pageExpr = createComplexConnectorProperty(locale.twitterPage(), PAGE_NS, PAGE_EXPR);
        sinceIdExpr = createComplexConnectorProperty(locale.twitterSinceId(), SINCE_ID_NS, SINCE_ID_EXPR);
        maxIdExpr = createComplexConnectorProperty(locale.twitterMaxId(), MAX_ID_NS, MAX_ID_EXPR);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        boolean isVisible = INLINE.equals(element.getProperty(PARAMETER_EDITOR_TYPE));

        consumerKey.setVisible(isVisible);
        consumerSecret.setVisible(isVisible);
        accessToken.setVisible(isVisible);
        accessTokenSecret.setVisible(isVisible);
        count.setVisible(isVisible);
        page.setVisible(isVisible);
        sinceId.setVisible(isVisible);
        maxId.setVisible(isVisible);

        consumerKeyExpr.setVisible(!isVisible);
        consumerSecretExpr.setVisible(!isVisible);
        accessTokenExpr.setVisible(!isVisible);
        accessTokenSecretExpr.setVisible(!isVisible);
        countExpr.setVisible(!isVisible);
        pageExpr.setVisible(!isVisible);
        sinceIdExpr.setVisible(!isVisible);
        maxIdExpr.setVisible(!isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        consumerKey.setProperty(element.getProperty(CONSUMER_KEY_INL));
        consumerSecret.setProperty(element.getProperty(CONSUMER_SECRET_INL));
        accessToken.setProperty(element.getProperty(ACCESS_TOKEN_INL));
        accessTokenSecret.setProperty(element.getProperty(ACCESS_TOKEN_SECRET_INL));
        count.setProperty(element.getProperty(COUNT_INL));
        page.setProperty(element.getProperty(PAGE_INL));
        sinceId.setProperty(element.getProperty(SINCE_ID_INL));
        maxId.setProperty(element.getProperty(MAX_ID_INL));

        consumerKeyExpr.setProperty(element.getProperty(CONSUMER_KEY_EXPR));
        consumerSecretExpr.setProperty(element.getProperty(CONSUMER_SECRET_EXPR));
        accessTokenExpr.setProperty(element.getProperty(ACCESS_TOKEN_EXPR));
        accessTokenSecretExpr.setProperty(element.getProperty(ACCESS_TOKEN_SECRET_EXPR));
        countExpr.setProperty(element.getProperty(COUNT_EXPR));
        pageExpr.setProperty(element.getProperty(PAGE_EXPR));
        sinceIdExpr.setProperty(element.getProperty(SINCE_ID_EXPR));
        maxIdExpr.setProperty(element.getProperty(MAX_ID_EXPR));
    }
}
