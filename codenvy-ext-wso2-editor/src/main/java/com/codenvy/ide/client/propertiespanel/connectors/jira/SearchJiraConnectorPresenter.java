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
import com.codenvy.ide.client.elements.connectors.jira.JiraPropertyManager;
import com.codenvy.ide.client.elements.connectors.jira.SearchJira;
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
import static com.codenvy.ide.client.elements.connectors.jira.SearchJira.MAX_RESULT_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.SearchJira.MAX_RESULT_INL;
import static com.codenvy.ide.client.elements.connectors.jira.SearchJira.MAX_RESULT_NS;
import static com.codenvy.ide.client.elements.connectors.jira.SearchJira.QUERY_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.SearchJira.QUERY_INL;
import static com.codenvy.ide.client.elements.connectors.jira.SearchJira.QUERY_NS;
import static com.codenvy.ide.client.elements.connectors.jira.SearchJira.START_FROM_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.SearchJira.START_FROM_INL;
import static com.codenvy.ide.client.elements.connectors.jira.SearchJira.START_FROM_NS;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class SearchJiraConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<SearchJira> {

    private SimplePropertyPresenter queryInl;
    private SimplePropertyPresenter maxResultInl;
    private SimplePropertyPresenter startFromInl;

    private ComplexPropertyPresenter queryExpr;
    private ComplexPropertyPresenter maxResultExpr;
    private ComplexPropertyPresenter startFromExpr;

    @Inject
    public SearchJiraConnectorPresenter(WSO2EditorLocalizationConstant locale,
                                        NameSpaceEditorPresenter nameSpacePresenter,
                                        PropertiesPanelView view,
                                        JiraPropertyManager jiraPropertyManager,
                                        ParameterPresenter parameterPresenter,
                                        PropertyTypeManager propertyTypeManager,
                                        PropertyPanelFactory propertyPanelFactory,
                                        SelectionManager selectionManager) {
        super(view,
              jiraPropertyManager,
              parameterPresenter,
              nameSpacePresenter,
              propertyTypeManager,
              locale,
              propertyPanelFactory,
              selectionManager);

        prepareView();
    }

    private void prepareView() {
        queryInl = createSimpleConnectorProperty(locale.jiraQuery(), QUERY_INL);
        maxResultInl = createSimpleConnectorProperty(locale.jiraMaxResult(), MAX_RESULT_INL);
        startFromInl = createSimpleConnectorProperty(locale.jiraStartFrom(), START_FROM_INL);

        queryExpr = createComplexConnectorProperty(locale.jiraQuery(), QUERY_NS, QUERY_EXPR);
        maxResultExpr = createComplexConnectorProperty(locale.jiraMaxResult(), MAX_RESULT_NS, MAX_RESULT_EXPR);
        startFromExpr = createComplexConnectorProperty(locale.jiraStartFrom(), START_FROM_NS, START_FROM_EXPR);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        boolean isVisible = INLINE.equals(element.getProperty(PARAMETER_EDITOR_TYPE));

        queryInl.setVisible(isVisible);
        maxResultInl.setVisible(isVisible);
        startFromInl.setVisible(isVisible);

        queryExpr.setVisible(!isVisible);
        maxResultExpr.setVisible(!isVisible);
        startFromExpr.setVisible(!isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        queryInl.setProperty(element.getProperty(QUERY_INL));
        maxResultInl.setProperty(element.getProperty(MAX_RESULT_INL));
        startFromInl.setProperty(element.getProperty(START_FROM_INL));

        queryExpr.setProperty(element.getProperty(QUERY_EXPR));
        maxResultExpr.setProperty(element.getProperty(MAX_RESULT_EXPR));
        startFromExpr.setProperty(element.getProperty(START_FROM_EXPR));
    }
}
