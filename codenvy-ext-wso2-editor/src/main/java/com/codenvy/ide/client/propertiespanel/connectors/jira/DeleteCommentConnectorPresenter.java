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
import com.codenvy.ide.client.elements.connectors.jira.DeleteComment;
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
import static com.codenvy.ide.client.elements.connectors.jira.DeleteComment.COMMENT_ID_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.DeleteComment.COMMENT_ID_INL;
import static com.codenvy.ide.client.elements.connectors.jira.DeleteComment.COMMENT_ID_NS;
import static com.codenvy.ide.client.elements.connectors.jira.DeleteComment.ISSUE_ID_OR_KEY_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.DeleteComment.ISSUE_ID_OR_KEY_INL;
import static com.codenvy.ide.client.elements.connectors.jira.DeleteComment.ISSUE_ID_OR_KEY_NS;


/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class DeleteCommentConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<DeleteComment> {

    private SimplePropertyPresenter issueIdOrKeyInl;
    private SimplePropertyPresenter commentIdInl;

    private ComplexPropertyPresenter issueIdOrKeyExpr;
    private ComplexPropertyPresenter commentIdExpr;

    @Inject
    public DeleteCommentConnectorPresenter(WSO2EditorLocalizationConstant locale,
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
        commentIdInl = createSimpleConnectorProperty(locale.jiraCommentId(), COMMENT_ID_INL);

        issueIdOrKeyExpr = createComplexConnectorProperty(locale.jiraIssueIdOrKey(), ISSUE_ID_OR_KEY_NS, ISSUE_ID_OR_KEY_EXPR);
        commentIdExpr = createComplexConnectorProperty(locale.jiraCommentId(), COMMENT_ID_NS, COMMENT_ID_EXPR);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        boolean isVisible = INLINE.equals(element.getProperty(PARAMETER_EDITOR_TYPE));

        issueIdOrKeyInl.setVisible(isVisible);
        commentIdInl.setVisible(isVisible);

        issueIdOrKeyExpr.setVisible(!isVisible);
        commentIdExpr.setVisible(!isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        issueIdOrKeyInl.setProperty(element.getProperty(ISSUE_ID_OR_KEY_INL));
        commentIdInl.setProperty(element.getProperty(COMMENT_ID_INL));

        issueIdOrKeyExpr.setProperty(element.getProperty(ISSUE_ID_OR_KEY_EXPR));
        commentIdExpr.setProperty(element.getProperty(COMMENT_ID_EXPR));
    }
}
