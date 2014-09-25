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
import com.codenvy.ide.client.elements.connectors.twitter.GetFollowersIds;
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
import static com.codenvy.ide.client.elements.connectors.twitter.GetFollowersIds.CURSOR_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.GetFollowersIds.CURSOR_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.GetFollowersIds.CURSOR_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.GetFollowersIds.SCREEN_NAME_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.GetFollowersIds.SCREEN_NAME_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.GetFollowersIds.SCREEN_NAME_NS;
import static com.codenvy.ide.client.elements.connectors.twitter.GetFollowersIds.USER_ID_EXPR;
import static com.codenvy.ide.client.elements.connectors.twitter.GetFollowersIds.USER_ID_INL;
import static com.codenvy.ide.client.elements.connectors.twitter.GetFollowersIds.USER_ID_NS;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class GetFollowersIdsConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<GetFollowersIds> {

    private SimplePropertyPresenter screenNameInl;
    private SimplePropertyPresenter userIdInl;
    private SimplePropertyPresenter cursorInl;

    private ComplexPropertyPresenter screenNameExpr;
    private ComplexPropertyPresenter userIdExpr;
    private ComplexPropertyPresenter cursorExpr;

    @Inject
    public GetFollowersIdsConnectorPresenter(WSO2EditorLocalizationConstant locale,
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
        screenNameInl = createSimplePanel(locale.twitterScreenName(), SCREEN_NAME_INL);
        userIdInl = createSimplePanel(locale.twitterUserId(), USER_ID_INL);
        cursorInl = createSimplePanel(locale.twitterCursor(), CURSOR_INL);

        screenNameExpr = createComplexPanel(locale.twitterScreenName(), SCREEN_NAME_NS, SCREEN_NAME_EXPR);
        userIdExpr = createComplexPanel(locale.twitterUserId(), USER_ID_NS, USER_ID_EXPR);
        cursorExpr = createComplexPanel(locale.twitterCursor(), CURSOR_NS, CURSOR_EXPR);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        boolean isVisible = INLINE.equals(element.getProperty(PARAMETER_EDITOR_TYPE));

        screenNameInl.setVisible(isVisible);
        userIdInl.setVisible(isVisible);
        cursorInl.setVisible(isVisible);

        screenNameExpr.setVisible(!isVisible);
        userIdExpr.setVisible(!isVisible);
        cursorExpr.setVisible(!isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        screenNameInl.setProperty(element.getProperty(SCREEN_NAME_INL));
        userIdInl.setProperty(element.getProperty(USER_ID_INL));
        cursorInl.setProperty(element.getProperty(CURSOR_INL));

        screenNameExpr.setProperty(element.getProperty(SCREEN_NAME_EXPR));
        userIdExpr.setProperty(element.getProperty(USER_ID_EXPR));
        cursorExpr.setProperty(element.getProperty(CURSOR_EXPR));
    }
}
