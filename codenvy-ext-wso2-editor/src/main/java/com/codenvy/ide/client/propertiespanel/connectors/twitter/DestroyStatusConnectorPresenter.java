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
import com.codenvy.ide.client.elements.connectors.twitter.DestroyStatus;
import com.codenvy.ide.client.elements.connectors.twitter.TwitterPropertyManager;
import com.codenvy.ide.client.inject.factories.PropertiesPanelWidgetFactory;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.base.AbstractConnectorPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.base.parameter.ParameterPresenter;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.list.ListPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.PARAMETER_EDITOR_TYPE;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.INLINE;
import static com.codenvy.ide.client.elements.connectors.twitter.DestroyStatus.KEY_ACCESS_TOKEN;
import static com.codenvy.ide.client.elements.connectors.twitter.DestroyStatus.KEY_ACCESS_TOKEN_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.DestroyStatus.KEY_ACCESS_TOKEN_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.DestroyStatus.KEY_ACCESS_TOKEN_SECRET;
import static com.codenvy.ide.client.elements.connectors.twitter.DestroyStatus.KEY_ACCESS_TOKEN_SECRET_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.DestroyStatus.KEY_ACCESS_TOKEN_SECRET_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.DestroyStatus.KEY_CONSUMER_KEY;
import static com.codenvy.ide.client.elements.connectors.twitter.DestroyStatus.KEY_CONSUMER_KEY_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.DestroyStatus.KEY_CONSUMER_KEY_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.DestroyStatus.KEY_CONSUMER_SECRET;
import static com.codenvy.ide.client.elements.connectors.twitter.DestroyStatus.KEY_CONSUMER_SECRET_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.DestroyStatus.KEY_CONSUMER_SECRET_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.DestroyStatus.KEY_STATUS_ID;
import static com.codenvy.ide.client.elements.connectors.twitter.DestroyStatus.KEY_STATUS_ID_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.DestroyStatus.KEY_STATUS_ID_NS;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class DestroyStatusConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<DestroyStatus> {

    private SimplePropertyPresenter consumerKey;
    private SimplePropertyPresenter consumerSecret;
    private SimplePropertyPresenter accessToken;
    private SimplePropertyPresenter accessTokenSecret;
    private SimplePropertyPresenter statusId;

    private ComplexPropertyPresenter consumerKeyExpr;
    private ComplexPropertyPresenter consumerSecretExpr;
    private ComplexPropertyPresenter accessTokenExpr;
    private ComplexPropertyPresenter accessTokenSecretExpr;
    private ComplexPropertyPresenter statusIdExpr;

    @Inject
    public DestroyStatusConnectorPresenter(WSO2EditorLocalizationConstant locale,
                                           NameSpaceEditorPresenter nameSpacePresenter,
                                           PropertiesPanelView view,
                                           TwitterPropertyManager twitterPropertyManager,
                                           ParameterPresenter parameterPresenter,
                                           PropertyTypeManager propertyTypeManager,
                                           PropertiesPanelWidgetFactory propertiesPanelWidgetFactory,
                                           Provider<ListPropertyPresenter> listPropertyPresenterProvider,
                                           Provider<ComplexPropertyPresenter> complexPropertyPresenterProvider,
                                           Provider<SimplePropertyPresenter> simplePropertyPresenterProvider) {
        super(view,
              twitterPropertyManager,
              parameterPresenter,
              nameSpacePresenter,
              propertyTypeManager,
              locale,
              propertiesPanelWidgetFactory,
              listPropertyPresenterProvider,
              complexPropertyPresenterProvider,
              simplePropertyPresenterProvider);

        prepareView();
    }

    private void prepareView() {
        consumerKey = createSimplePanel(locale.twitterConsumerKey(), KEY_CONSUMER_KEY);
        consumerSecret = createSimplePanel(locale.twitterConsumerSecret(), KEY_CONSUMER_SECRET);
        accessToken = createSimplePanel(locale.twitterAccessToken(), KEY_ACCESS_TOKEN);
        accessTokenSecret = createSimplePanel(locale.twitterAccessTokenSecret(), KEY_ACCESS_TOKEN_SECRET);
        statusId = createSimplePanel(locale.twitterStatusId(), KEY_STATUS_ID);

        consumerKeyExpr = createComplexPanel(locale.twitterConsumerKey(), KEY_CONSUMER_KEY_NS, KEY_CONSUMER_KEY_EXPR);
        consumerSecretExpr = createComplexPanel(locale.twitterConsumerSecret(), KEY_CONSUMER_SECRET_NS, KEY_CONSUMER_SECRET_EXPR);
        accessTokenExpr = createComplexPanel(locale.twitterAccessToken(), KEY_ACCESS_TOKEN_NS, KEY_ACCESS_TOKEN_EXPR);
        accessTokenSecretExpr = createComplexPanel(locale.twitterAccessTokenSecret(),
                                                   KEY_ACCESS_TOKEN_SECRET_NS,
                                                   KEY_ACCESS_TOKEN_SECRET_EXPR);
        statusIdExpr = createComplexPanel(locale.twitterStatusId(), KEY_STATUS_ID_NS, KEY_STATUS_ID_EXPR);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        boolean isEquals = INLINE.equals(element.getProperty(PARAMETER_EDITOR_TYPE));

        consumerKey.setVisible(isEquals);
        consumerSecret.setVisible(isEquals);
        accessToken.setVisible(isEquals);
        accessTokenSecret.setVisible(isEquals);
        statusId.setVisible(isEquals);

        consumerKeyExpr.setVisible(!isEquals);
        consumerSecretExpr.setVisible(!isEquals);
        accessTokenExpr.setVisible(!isEquals);
        accessTokenSecretExpr.setVisible(!isEquals);
        statusIdExpr.setVisible(!isEquals);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        consumerKey.setProperty(element.getProperty(KEY_CONSUMER_KEY));
        consumerSecret.setProperty(element.getProperty(KEY_CONSUMER_SECRET));
        accessToken.setProperty(element.getProperty(KEY_ACCESS_TOKEN));
        accessTokenSecret.setProperty(element.getProperty(KEY_ACCESS_TOKEN_SECRET));
        statusId.setProperty(element.getProperty(KEY_STATUS_ID));

        consumerKeyExpr.setProperty(element.getProperty(KEY_CONSUMER_KEY_EXPR));
        consumerSecretExpr.setProperty(element.getProperty(KEY_CONSUMER_SECRET_EXPR));
        accessTokenExpr.setProperty(element.getProperty(KEY_ACCESS_TOKEN_EXPR));
        accessTokenSecretExpr.setProperty(element.getProperty(KEY_ACCESS_TOKEN_SECRET_EXPR));
        statusIdExpr.setProperty(element.getProperty(KEY_STATUS_ID_EXPR));
    }
}
