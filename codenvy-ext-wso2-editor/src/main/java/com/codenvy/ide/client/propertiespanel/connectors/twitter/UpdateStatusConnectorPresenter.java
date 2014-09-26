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
import com.codenvy.ide.client.elements.connectors.twitter.TwitterPropertyManager;
import com.codenvy.ide.client.elements.connectors.twitter.UpdateStatus;
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
import static com.codenvy.ide.client.elements.connectors.twitter.UpdateStatus.ACCESS_TOKEN_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.UpdateStatus.ACCESS_TOKEN_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.UpdateStatus.ACCESS_TOKEN_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.UpdateStatus.ACCESS_TOKEN_SECRET_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.UpdateStatus.ACCESS_TOKEN_SECRET_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.UpdateStatus.ACCESS_TOKEN_SECRET_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.UpdateStatus.CONSUMER_KEY_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.UpdateStatus.CONSUMER_KEY_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.UpdateStatus.CONSUMER_KEY_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.UpdateStatus.CONSUMER_SECRET_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.UpdateStatus.CONSUMER_SECRET_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.UpdateStatus.CONSUMER_SECRET_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.UpdateStatus.STATUS_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.UpdateStatus.STATUS_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.UpdateStatus.STATUS_NS;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class UpdateStatusConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<UpdateStatus> {

    private SimplePropertyPresenter consumerKey;
    private SimplePropertyPresenter consumerSecret;
    private SimplePropertyPresenter accessToken;
    private SimplePropertyPresenter accessTokenSecret;
    private SimplePropertyPresenter status;

    private ComplexPropertyPresenter consumerKeyExpr;
    private ComplexPropertyPresenter consumerSecretExpr;
    private ComplexPropertyPresenter accessTokenExpr;
    private ComplexPropertyPresenter accessTokenSecretExpr;
    private ComplexPropertyPresenter statusExpr;

    @Inject
    public UpdateStatusConnectorPresenter(WSO2EditorLocalizationConstant locale,
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
        consumerKey = createSimplePanel(locale.twitterConsumerKey(), CONSUMER_KEY_INL);
        consumerSecret = createSimplePanel(locale.twitterConsumerSecret(), CONSUMER_SECRET_INL);
        accessToken = createSimplePanel(locale.twitterAccessToken(), ACCESS_TOKEN_INL);
        accessTokenSecret = createSimplePanel(locale.twitterAccessTokenSecret(), ACCESS_TOKEN_SECRET_INL);
        status = createSimplePanel(locale.twitterStatusId(), STATUS_INL);

        consumerKeyExpr = createComplexPanel(locale.twitterConsumerKey(), CONSUMER_KEY_NS, CONSUMER_KEY_EXPR);
        consumerSecretExpr = createComplexPanel(locale.twitterConsumerSecret(), CONSUMER_SECRET_NS, CONSUMER_SECRET_EXPR);
        accessTokenExpr = createComplexPanel(locale.twitterAccessToken(), ACCESS_TOKEN_NS, ACCESS_TOKEN_EXPR);
        accessTokenSecretExpr = createComplexPanel(locale.twitterAccessTokenSecret(), ACCESS_TOKEN_SECRET_NS, ACCESS_TOKEN_SECRET_EXPR);
        statusExpr = createComplexPanel(locale.twitterStatusId(), STATUS_NS, STATUS_EXPR);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        boolean isVisible = INLINE.equals(element.getProperty(PARAMETER_EDITOR_TYPE));

        consumerKey.setVisible(isVisible);
        consumerSecret.setVisible(isVisible);
        accessToken.setVisible(isVisible);
        accessTokenSecret.setVisible(isVisible);
        status.setVisible(isVisible);

        consumerKeyExpr.setVisible(!isVisible);
        consumerSecretExpr.setVisible(!isVisible);
        accessTokenExpr.setVisible(!isVisible);
        accessTokenSecretExpr.setVisible(!isVisible);
        statusExpr.setVisible(!isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        consumerKey.setProperty(element.getProperty(CONSUMER_KEY_INL));
        consumerSecret.setProperty(element.getProperty(CONSUMER_SECRET_INL));
        accessToken.setProperty(element.getProperty(ACCESS_TOKEN_INL));
        accessTokenSecret.setProperty(element.getProperty(ACCESS_TOKEN_SECRET_INL));
        status.setProperty(element.getProperty(STATUS_INL));

        consumerKeyExpr.setProperty(element.getProperty(CONSUMER_KEY_EXPR));
        consumerSecretExpr.setProperty(element.getProperty(CONSUMER_SECRET_EXPR));
        accessTokenExpr.setProperty(element.getProperty(ACCESS_TOKEN_EXPR));
        accessTokenSecretExpr.setProperty(element.getProperty(ACCESS_TOKEN_SECRET_EXPR));
        statusExpr.setProperty(element.getProperty(STATUS_EXPR));
    }
}
