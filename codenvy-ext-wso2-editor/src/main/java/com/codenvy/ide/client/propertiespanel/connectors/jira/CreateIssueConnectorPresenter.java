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
import com.codenvy.ide.client.elements.connectors.jira.CreateIssue;
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
import static com.codenvy.ide.client.elements.connectors.jira.CreateIssue.DESCRIPTION_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.CreateIssue.DESCRIPTION_INL;
import static com.codenvy.ide.client.elements.connectors.jira.CreateIssue.DESCRIPTION_NS;
import static com.codenvy.ide.client.elements.connectors.jira.CreateIssue.ISSUE_TYPE_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.CreateIssue.ISSUE_TYPE_INL;
import static com.codenvy.ide.client.elements.connectors.jira.CreateIssue.ISSUE_TYPE_NS;
import static com.codenvy.ide.client.elements.connectors.jira.CreateIssue.PROJECT_KEY_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.CreateIssue.PROJECT_KEY_INL;
import static com.codenvy.ide.client.elements.connectors.jira.CreateIssue.PROJECT_KEY_NS;
import static com.codenvy.ide.client.elements.connectors.jira.CreateIssue.SUMMARY_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.CreateIssue.SUMMARY_INL;
import static com.codenvy.ide.client.elements.connectors.jira.CreateIssue.SUMMARY_NS;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class CreateIssueConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<CreateIssue> {

    private SimplePropertyPresenter projectKeyInl;
    private SimplePropertyPresenter summaryInl;
    private SimplePropertyPresenter descriptionInl;
    private SimplePropertyPresenter issueTypeInl;

    private ComplexPropertyPresenter projectKeyExpr;
    private ComplexPropertyPresenter summaryExpr;
    private ComplexPropertyPresenter descriptionExpr;
    private ComplexPropertyPresenter issueTypeExpr;

    @Inject
    public CreateIssueConnectorPresenter(WSO2EditorLocalizationConstant locale,
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
        projectKeyInl = createSimpleConnectorProperty(locale.jiraProjectKey(), PROJECT_KEY_INL);
        summaryInl = createSimpleConnectorProperty(locale.jiraSummary(), SUMMARY_INL);
        descriptionInl = createSimpleConnectorProperty(locale.jiraDescription(), DESCRIPTION_INL);
        issueTypeInl = createSimpleConnectorProperty(locale.jiraIssueType(), ISSUE_TYPE_INL);

        projectKeyExpr = createComplexConnectorProperty(locale.jiraProjectKey(), PROJECT_KEY_NS, PROJECT_KEY_EXPR);
        summaryExpr = createComplexConnectorProperty(locale.jiraSummary(), SUMMARY_NS, SUMMARY_EXPR);
        descriptionExpr = createComplexConnectorProperty(locale.jiraDescription(), DESCRIPTION_NS, DESCRIPTION_EXPR);
        issueTypeExpr = createComplexConnectorProperty(locale.jiraIssueType(), ISSUE_TYPE_NS, ISSUE_TYPE_EXPR);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        boolean isVisible = INLINE.equals(element.getProperty(PARAMETER_EDITOR_TYPE));

        projectKeyInl.setVisible(isVisible);
        summaryInl.setVisible(isVisible);
        descriptionInl.setVisible(isVisible);
        issueTypeInl.setVisible(isVisible);

        projectKeyExpr.setVisible(!isVisible);
        summaryExpr.setVisible(!isVisible);
        descriptionExpr.setVisible(!isVisible);
        issueTypeExpr.setVisible(!isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        projectKeyInl.setProperty(element.getProperty(PROJECT_KEY_INL));
        summaryInl.setProperty(element.getProperty(SUMMARY_INL));
        descriptionInl.setProperty(element.getProperty(DESCRIPTION_INL));
        issueTypeInl.setProperty(element.getProperty(ISSUE_TYPE_INL));

        projectKeyExpr.setProperty(element.getProperty(PROJECT_KEY_EXPR));
        summaryExpr.setProperty(element.getProperty(SUMMARY_EXPR));
        descriptionExpr.setProperty(element.getProperty(DESCRIPTION_EXPR));
        issueTypeExpr.setProperty(element.getProperty(ISSUE_TYPE_EXPR));
    }
}
