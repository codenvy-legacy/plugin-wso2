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
import com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter;
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
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.COUNT_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.COUNT_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.COUNT_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.GEOCODE_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.GEOCODE_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.GEOCODE_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.LANG_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.LANG_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.LANG_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.LOCALE_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.LOCALE_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.LOCALE_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.MAX_ID_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.MAX_ID_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.MAX_ID_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.RADIUS_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.RADIUS_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.RADIUS_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.SEARCH_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.SEARCH_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.SEARCH_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.SINCE_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.SINCE_ID_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.SINCE_ID_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.SINCE_ID_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.SINCE_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.SINCE_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.UNIT_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.UNIT_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.UNIT_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.UNTIL_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.UNTIL_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter.UNTIL_NS;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class SearchTwitterConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<SearchTwitter> {

    private SimplePropertyPresenter searchInl;
    private SimplePropertyPresenter langInl;
    private SimplePropertyPresenter localeInl;
    private SimplePropertyPresenter maxIdInl;
    private SimplePropertyPresenter sinceInl;
    private SimplePropertyPresenter sinceIdInl;
    private SimplePropertyPresenter geocodeInl;
    private SimplePropertyPresenter radiusInl;
    private SimplePropertyPresenter unitInl;
    private SimplePropertyPresenter untilInl;
    private SimplePropertyPresenter countInl;

    private ComplexPropertyPresenter searchExpr;
    private ComplexPropertyPresenter langExpr;
    private ComplexPropertyPresenter localeExpr;
    private ComplexPropertyPresenter maxIdExpr;
    private ComplexPropertyPresenter sinceExpr;
    private ComplexPropertyPresenter sinceIdExpr;
    private ComplexPropertyPresenter geocodeExpr;
    private ComplexPropertyPresenter radiusExpr;
    private ComplexPropertyPresenter unitExpr;
    private ComplexPropertyPresenter untilExpr;
    private ComplexPropertyPresenter countExpr;

    @Inject
    public SearchTwitterConnectorPresenter(WSO2EditorLocalizationConstant locale,
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

        searchInl = createSimplePanel(locale.twitterSearch(), SEARCH_INL);
        langInl = createSimplePanel(locale.twitterLang(), LANG_INL);
        localeInl = createSimplePanel(locale.twitterLocale(), LOCALE_INL);
        maxIdInl = createSimplePanel(locale.twitterMaxId(), MAX_ID_INL);
        sinceInl = createSimplePanel(locale.twitterSince(), SINCE_INL);
        sinceIdInl = createSimplePanel(locale.twitterSinceId(), SINCE_ID_INL);
        geocodeInl = createSimplePanel(locale.twitterGeocode(), GEOCODE_INL);
        radiusInl = createSimplePanel(locale.twitterRadius(), RADIUS_INL);
        unitInl = createSimplePanel(locale.twitterUnit(), UNIT_INL);
        untilInl = createSimplePanel(locale.twitterUntil(), UNTIL_INL);
        countInl = createSimplePanel(locale.twitterCount(), COUNT_INL);

        searchExpr = createComplexPanel(locale.twitterSearch(), SEARCH_NS, SEARCH_EXPR);
        langExpr = createComplexPanel(locale.twitterLang(), LANG_NS, LANG_EXPR);
        localeExpr = createComplexPanel(locale.twitterLocale(), LOCALE_NS, LOCALE_EXPR);
        maxIdExpr = createComplexPanel(locale.twitterMaxId(), MAX_ID_NS, MAX_ID_EXPR);
        sinceExpr = createComplexPanel(locale.twitterSince(), SINCE_NS, SINCE_EXPR);
        sinceIdExpr = createComplexPanel(locale.twitterSinceId(), SINCE_ID_NS, SINCE_ID_EXPR);
        geocodeExpr = createComplexPanel(locale.twitterGeocode(), GEOCODE_NS, GEOCODE_EXPR);
        radiusExpr = createComplexPanel(locale.twitterRadius(), RADIUS_NS, RADIUS_EXPR);
        unitExpr = createComplexPanel(locale.twitterUnit(), UNIT_NS, UNIT_EXPR);
        untilExpr = createComplexPanel(locale.twitterUntil(), UNTIL_NS, UNTIL_EXPR);
        countExpr = createComplexPanel(locale.twitterCount(), COUNT_NS, COUNT_EXPR);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        boolean isVisible = INLINE.equals(element.getProperty(PARAMETER_EDITOR_TYPE));

        searchInl.setVisible(isVisible);
        langInl.setVisible(isVisible);
        localeInl.setVisible(isVisible);
        maxIdInl.setVisible(isVisible);
        sinceInl.setVisible(isVisible);
        sinceIdInl.setVisible(isVisible);
        geocodeInl.setVisible(isVisible);
        radiusInl.setVisible(isVisible);
        unitInl.setVisible(isVisible);
        untilInl.setVisible(isVisible);
        countInl.setVisible(isVisible);

        searchExpr.setVisible(!isVisible);
        langExpr.setVisible(!isVisible);
        localeExpr.setVisible(!isVisible);
        maxIdExpr.setVisible(!isVisible);
        sinceExpr.setVisible(!isVisible);
        sinceIdExpr.setVisible(!isVisible);
        geocodeExpr.setVisible(!isVisible);
        radiusExpr.setVisible(!isVisible);
        unitExpr.setVisible(!isVisible);
        untilExpr.setVisible(!isVisible);
        countExpr.setVisible(!isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        searchInl.setProperty(element.getProperty(SEARCH_INL));
        langInl.setProperty(element.getProperty(LANG_INL));
        localeInl.setProperty(element.getProperty(LOCALE_INL));
        maxIdInl.setProperty(element.getProperty(MAX_ID_INL));
        sinceInl.setProperty(element.getProperty(SINCE_INL));
        sinceIdInl.setProperty(element.getProperty(SINCE_ID_INL));
        geocodeInl.setProperty(element.getProperty(GEOCODE_INL));
        radiusInl.setProperty(element.getProperty(RADIUS_INL));
        unitInl.setProperty(element.getProperty(UNIT_INL));
        untilInl.setProperty(element.getProperty(UNTIL_INL));
        countInl.setProperty(element.getProperty(COUNT_INL));

        searchExpr.setProperty(element.getProperty(SEARCH_EXPR));
        langExpr.setProperty(element.getProperty(LANG_EXPR));
        localeExpr.setProperty(element.getProperty(LOCALE_EXPR));
        maxIdExpr.setProperty(element.getProperty(MAX_ID_EXPR));
        sinceExpr.setProperty(element.getProperty(SINCE_EXPR));
        sinceIdExpr.setProperty(element.getProperty(SINCE_ID_EXPR));
        geocodeExpr.setProperty(element.getProperty(GEOCODE_EXPR));
        radiusExpr.setProperty(element.getProperty(RADIUS_EXPR));
        unitExpr.setProperty(element.getProperty(UNIT_EXPR));
        untilExpr.setProperty(element.getProperty(UNTIL_EXPR));
        countExpr.setProperty(element.getProperty(COUNT_EXPR));
    }
}
