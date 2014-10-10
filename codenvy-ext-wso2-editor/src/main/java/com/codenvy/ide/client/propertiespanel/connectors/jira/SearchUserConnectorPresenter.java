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
package com.codenvy.ide.client.propertiespanel.connectors.jira;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.connectors.jira.SearchUser;
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
import static com.codenvy.ide.client.elements.connectors.jira.SearchUser.INCLUDE_ACTIVE_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.SearchUser.INCLUDE_ACTIVE_INL;
import static com.codenvy.ide.client.elements.connectors.jira.SearchUser.INCLUDE_ACTIVE_NS;
import static com.codenvy.ide.client.elements.connectors.jira.SearchUser.INCLUDE_INACTIVE_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.SearchUser.INCLUDE_INACTIVE_INL;
import static com.codenvy.ide.client.elements.connectors.jira.SearchUser.INCLUDE_INACTIVE_NS;
import static com.codenvy.ide.client.elements.connectors.jira.SearchUser.MAX_RESULTS_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.SearchUser.MAX_RESULTS_INL;
import static com.codenvy.ide.client.elements.connectors.jira.SearchUser.MAX_RESULTS_NS;
import static com.codenvy.ide.client.elements.connectors.jira.SearchUser.START_AT_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.SearchUser.START_AT_INL;
import static com.codenvy.ide.client.elements.connectors.jira.SearchUser.START_AT_NS;
import static com.codenvy.ide.client.elements.connectors.jira.SearchUser.USER_NAME_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.SearchUser.USER_NAME_INL;
import static com.codenvy.ide.client.elements.connectors.jira.SearchUser.USER_NAME_NS;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class SearchUserConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<SearchUser> {

    private SimplePropertyPresenter userNameInl;
    private SimplePropertyPresenter startAtInl;
    private SimplePropertyPresenter maxResultsInl;
    private SimplePropertyPresenter includeActiveInl;
    private SimplePropertyPresenter includeInactiveInl;

    private ComplexPropertyPresenter userNameExpr;
    private ComplexPropertyPresenter startAtExpr;
    private ComplexPropertyPresenter maxResultsExpr;
    private ComplexPropertyPresenter includeActiveExpr;
    private ComplexPropertyPresenter includeInactiveExpr;

    @Inject
    public SearchUserConnectorPresenter(WSO2EditorLocalizationConstant locale,
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
        userNameInl = createSimpleConnectorProperty(locale.connectorUsername(), USER_NAME_INL);
        startAtInl = createSimpleConnectorProperty(locale.jiraStartAt(), START_AT_INL);
        maxResultsInl = createSimpleConnectorProperty(locale.jiraMaxResults(), MAX_RESULTS_INL);
        includeActiveInl = createSimpleConnectorProperty(locale.jiraIncludeActive(), INCLUDE_ACTIVE_INL);
        includeInactiveInl = createSimpleConnectorProperty(locale.jiraIncludeInactive(), INCLUDE_INACTIVE_INL);

        userNameExpr = createComplexConnectorProperty(locale.connectorUsername(), USER_NAME_NS, USER_NAME_EXPR);
        startAtExpr = createComplexConnectorProperty(locale.jiraStartAt(), START_AT_NS, START_AT_EXPR);
        maxResultsExpr = createComplexConnectorProperty(locale.jiraMaxResults(), MAX_RESULTS_NS, MAX_RESULTS_EXPR);
        includeActiveExpr = createComplexConnectorProperty(locale.jiraIncludeActive(), INCLUDE_ACTIVE_NS, INCLUDE_ACTIVE_EXPR);
        includeInactiveExpr = createComplexConnectorProperty(locale.jiraIncludeInactive(), INCLUDE_INACTIVE_NS, INCLUDE_INACTIVE_EXPR);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        boolean isVisible = INLINE.equals(element.getProperty(PARAMETER_EDITOR_TYPE));

        userNameInl.setVisible(isVisible);
        includeActiveInl.setVisible(isVisible);
        includeInactiveInl.setVisible(isVisible);
        startAtInl.setVisible(isVisible);
        maxResultsInl.setVisible(isVisible);

        userNameExpr.setVisible(!isVisible);
        includeActiveExpr.setVisible(!isVisible);
        includeInactiveExpr.setVisible(!isVisible);
        startAtExpr.setVisible(!isVisible);
        maxResultsExpr.setVisible(!isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        userNameInl.setProperty(element.getProperty(USER_NAME_INL));
        startAtInl.setProperty(element.getProperty(START_AT_INL));
        maxResultsInl.setProperty(element.getProperty(MAX_RESULTS_INL));
        includeActiveInl.setProperty(element.getProperty(INCLUDE_ACTIVE_INL));
        includeInactiveInl.setProperty(element.getProperty(INCLUDE_INACTIVE_INL));

        userNameExpr.setProperty(element.getProperty(USER_NAME_EXPR));
        startAtExpr.setProperty(element.getProperty(START_AT_EXPR));
        maxResultsExpr.setProperty(element.getProperty(MAX_RESULTS_EXPR));
        includeActiveExpr.setProperty(element.getProperty(INCLUDE_ACTIVE_EXPR));
        includeInactiveExpr.setProperty(element.getProperty(INCLUDE_INACTIVE_EXPR));
    }
}
