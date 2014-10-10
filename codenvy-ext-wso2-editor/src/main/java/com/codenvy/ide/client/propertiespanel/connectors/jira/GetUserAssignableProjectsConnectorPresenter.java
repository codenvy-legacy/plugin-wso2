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
import com.codenvy.ide.client.elements.connectors.jira.GetUserAssignableProjects;
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
import static com.codenvy.ide.client.elements.connectors.jira.GetUserAssignableProjects.MAX_RESULT_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.GetUserAssignableProjects.MAX_RESULT_INL;
import static com.codenvy.ide.client.elements.connectors.jira.GetUserAssignableProjects.MAX_RESULT_NS;
import static com.codenvy.ide.client.elements.connectors.jira.GetUserAssignableProjects.PROJECT_KEY_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.GetUserAssignableProjects.PROJECT_KEY_INL;
import static com.codenvy.ide.client.elements.connectors.jira.GetUserAssignableProjects.PROJECT_KEY_NS;
import static com.codenvy.ide.client.elements.connectors.jira.GetUserAssignableProjects.START_FROM_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.GetUserAssignableProjects.START_FROM_INL;
import static com.codenvy.ide.client.elements.connectors.jira.GetUserAssignableProjects.START_FROM_NS;
import static com.codenvy.ide.client.elements.connectors.jira.GetUserAssignableProjects.USER_NAME_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.GetUserAssignableProjects.USER_NAME_INL;
import static com.codenvy.ide.client.elements.connectors.jira.GetUserAssignableProjects.USER_NAME_NS;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class GetUserAssignableProjectsConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<GetUserAssignableProjects> {

    private SimplePropertyPresenter userNameInl;
    private SimplePropertyPresenter projectKeyInl;
    private SimplePropertyPresenter maxResultInl;
    private SimplePropertyPresenter startFromInl;

    private ComplexPropertyPresenter userNameExpr;
    private ComplexPropertyPresenter projectKeyExpr;
    private ComplexPropertyPresenter maxResultExpr;
    private ComplexPropertyPresenter startFromExpr;

    @Inject
    public GetUserAssignableProjectsConnectorPresenter(WSO2EditorLocalizationConstant locale,
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
        projectKeyInl = createSimpleConnectorProperty(locale.jiraProjectKey(), PROJECT_KEY_INL);
        maxResultInl = createSimpleConnectorProperty(locale.jiraMaxResult(), MAX_RESULT_INL);
        startFromInl = createSimpleConnectorProperty(locale.jiraStartFrom(), START_FROM_INL);

        userNameExpr = createComplexConnectorProperty(locale.connectorUsername(), USER_NAME_NS, USER_NAME_EXPR);
        projectKeyExpr = createComplexConnectorProperty(locale.jiraProjectKey(), PROJECT_KEY_NS, PROJECT_KEY_EXPR);
        maxResultExpr = createComplexConnectorProperty(locale.jiraMaxResult(), MAX_RESULT_NS, MAX_RESULT_EXPR);
        startFromExpr = createComplexConnectorProperty(locale.jiraStartFrom(), START_FROM_NS, START_FROM_EXPR);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        boolean isVisible = INLINE.equals(element.getProperty(PARAMETER_EDITOR_TYPE));

        userNameInl.setVisible(isVisible);
        projectKeyInl.setVisible(isVisible);
        maxResultInl.setVisible(isVisible);
        startFromInl.setVisible(isVisible);

        userNameExpr.setVisible(!isVisible);
        projectKeyExpr.setVisible(!isVisible);
        maxResultExpr.setVisible(!isVisible);
        startFromExpr.setVisible(!isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        userNameInl.setProperty(element.getProperty(USER_NAME_INL));
        projectKeyInl.setProperty(element.getProperty(PROJECT_KEY_INL));
        maxResultInl.setProperty(element.getProperty(MAX_RESULT_INL));
        startFromInl.setProperty(element.getProperty(START_FROM_INL));

        userNameExpr.setProperty(element.getProperty(USER_NAME_EXPR));
        projectKeyExpr.setProperty(element.getProperty(PROJECT_KEY_EXPR));
        maxResultExpr.setProperty(element.getProperty(MAX_RESULT_EXPR));
        startFromExpr.setProperty(element.getProperty(START_FROM_EXPR));
    }
}
