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
import com.codenvy.ide.client.elements.connectors.jira.DoTransition;
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
import static com.codenvy.ide.client.elements.connectors.jira.DoTransition.ISSUE_ID_OR_KEY_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.DoTransition.ISSUE_ID_OR_KEY_INL;
import static com.codenvy.ide.client.elements.connectors.jira.DoTransition.ISSUE_ID_OR_KEY_NS;
import static com.codenvy.ide.client.elements.connectors.jira.DoTransition.RESOLUTION_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.DoTransition.RESOLUTION_INL;
import static com.codenvy.ide.client.elements.connectors.jira.DoTransition.RESOLUTION_NS;
import static com.codenvy.ide.client.elements.connectors.jira.DoTransition.TRANSITION_ID_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.DoTransition.TRANSITION_ID_INL;
import static com.codenvy.ide.client.elements.connectors.jira.DoTransition.TRANSITION_ID_NS;
import static com.codenvy.ide.client.elements.connectors.jira.DoTransition.UPDATE_ASSIGNEE_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.DoTransition.UPDATE_ASSIGNEE_INL;
import static com.codenvy.ide.client.elements.connectors.jira.DoTransition.UPDATE_ASSIGNEE_NS;
import static com.codenvy.ide.client.elements.connectors.jira.DoTransition.UPDATE_COMMENT_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.DoTransition.UPDATE_COMMENT_INL;
import static com.codenvy.ide.client.elements.connectors.jira.DoTransition.UPDATE_COMMENT_NS;


/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class DoTransitionConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<DoTransition> {

    private SimplePropertyPresenter issueIdOrKeyInl;
    private SimplePropertyPresenter updateCommentInl;
    private SimplePropertyPresenter updateAssigneeInl;
    private SimplePropertyPresenter resolutionInl;
    private SimplePropertyPresenter transitionIdInl;

    private ComplexPropertyPresenter issueIdOrKeyExpr;
    private ComplexPropertyPresenter updateCommentExpr;
    private ComplexPropertyPresenter updateAssigneeExpr;
    private ComplexPropertyPresenter resolutionExpr;
    private ComplexPropertyPresenter transitionIdExpr;

    @Inject
    public DoTransitionConnectorPresenter(WSO2EditorLocalizationConstant locale,
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
        issueIdOrKeyInl = createSimpleConnectorProperty(locale.jiraIssueIdOrKey(), ISSUE_ID_OR_KEY_INL);
        updateCommentInl = createSimpleConnectorProperty(locale.jiraUpdateComment(), UPDATE_COMMENT_INL);
        updateAssigneeInl = createSimpleConnectorProperty(locale.jiraUpdateAssignee(), UPDATE_ASSIGNEE_INL);
        resolutionInl = createSimpleConnectorProperty(locale.jiraResolution(), RESOLUTION_INL);
        transitionIdInl = createSimpleConnectorProperty(locale.jiraTransitionId(), TRANSITION_ID_INL);

        issueIdOrKeyExpr = createComplexConnectorProperty(locale.jiraIssueIdOrKey(), ISSUE_ID_OR_KEY_NS, ISSUE_ID_OR_KEY_EXPR);
        updateCommentExpr = createComplexConnectorProperty(locale.jiraUpdateComment(), UPDATE_COMMENT_NS, UPDATE_COMMENT_EXPR);
        updateAssigneeExpr = createComplexConnectorProperty(locale.jiraUpdateAssignee(), UPDATE_ASSIGNEE_NS, UPDATE_ASSIGNEE_EXPR);
        resolutionExpr = createComplexConnectorProperty(locale.jiraResolution(), RESOLUTION_NS, RESOLUTION_EXPR);
        transitionIdExpr = createComplexConnectorProperty(locale.jiraTransitionId(), TRANSITION_ID_NS, TRANSITION_ID_EXPR);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        boolean isVisible = INLINE.equals(element.getProperty(PARAMETER_EDITOR_TYPE));

        issueIdOrKeyInl.setVisible(isVisible);
        updateCommentInl.setVisible(isVisible);
        updateAssigneeInl.setVisible(isVisible);
        resolutionInl.setVisible(isVisible);
        transitionIdInl.setVisible(isVisible);

        issueIdOrKeyExpr.setVisible(!isVisible);
        updateCommentExpr.setVisible(!isVisible);
        updateAssigneeExpr.setVisible(!isVisible);
        resolutionExpr.setVisible(!isVisible);
        transitionIdExpr.setVisible(!isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        issueIdOrKeyInl.setProperty(element.getProperty(ISSUE_ID_OR_KEY_INL));
        updateCommentInl.setProperty(element.getProperty(UPDATE_COMMENT_INL));
        updateAssigneeInl.setProperty(element.getProperty(UPDATE_ASSIGNEE_INL));
        resolutionInl.setProperty(element.getProperty(RESOLUTION_INL));
        transitionIdInl.setProperty(element.getProperty(TRANSITION_ID_INL));

        issueIdOrKeyExpr.setProperty(element.getProperty(ISSUE_ID_OR_KEY_EXPR));
        updateCommentExpr.setProperty(element.getProperty(UPDATE_COMMENT_EXPR));
        updateAssigneeExpr.setProperty(element.getProperty(UPDATE_ASSIGNEE_EXPR));
        resolutionExpr.setProperty(element.getProperty(RESOLUTION_EXPR));
        transitionIdExpr.setProperty(element.getProperty(TRANSITION_ID_EXPR));
    }
}
