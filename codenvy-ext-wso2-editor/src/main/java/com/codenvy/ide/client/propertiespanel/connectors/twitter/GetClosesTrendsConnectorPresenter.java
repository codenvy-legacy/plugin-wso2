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
import com.codenvy.ide.client.elements.connectors.twitter.GetClosesTrends;
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
import static com.codenvy.ide.client.elements.connectors.twitter.GetClosesTrends.LATITUDE_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.GetClosesTrends.LATITUDE_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.GetClosesTrends.LATITUDE_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.GetClosesTrends.LONGITUDE_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.GetClosesTrends.LONGITUDE_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.GetClosesTrends.LONGITUDE_NS;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class GetClosesTrendsConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<GetClosesTrends> {

    private SimplePropertyPresenter  latitudeInl;
    private SimplePropertyPresenter  longitudeInl;
    private ComplexPropertyPresenter latitudeExpr;
    private ComplexPropertyPresenter longitudeExpr;

    @Inject
    public GetClosesTrendsConnectorPresenter(WSO2EditorLocalizationConstant locale,
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
        latitudeInl = createSimpleConnectorProperty(locale.twitterLatitude(), LATITUDE_INL);
        longitudeInl = createSimpleConnectorProperty(locale.twitterLongitude(), LONGITUDE_INL);

        latitudeExpr = createComplexConnectorProperty(locale.twitterLatitude(), LATITUDE_NS, LATITUDE_EXPR);
        longitudeExpr = createComplexConnectorProperty(locale.twitterLongitude(), LONGITUDE_NS, LONGITUDE_EXPR);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        boolean isVisible = INLINE.equals(element.getProperty(PARAMETER_EDITOR_TYPE));

        latitudeInl.setVisible(isVisible);
        longitudeInl.setVisible(isVisible);
        latitudeExpr.setVisible(!isVisible);
        longitudeExpr.setVisible(!isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        latitudeInl.setProperty(element.getProperty(LATITUDE_INL));
        longitudeInl.setProperty(element.getProperty(LONGITUDE_INL));
        latitudeExpr.setProperty(element.getProperty(LATITUDE_EXPR));
        longitudeExpr.setProperty(element.getProperty(LONGITUDE_EXPR));
    }
}
