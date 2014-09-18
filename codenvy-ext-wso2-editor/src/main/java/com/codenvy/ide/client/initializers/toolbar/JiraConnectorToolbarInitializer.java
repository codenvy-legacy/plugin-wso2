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

package com.codenvy.ide.client.initializers.toolbar;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.constants.JiraConnectorCreatingState;
import com.codenvy.ide.client.constants.ToolbarGroupIds;
import com.codenvy.ide.client.toolbar.ToolbarPresenter;
import com.google.inject.Inject;

/**
 * @author Andrey Plotnikov
 */
public class JiraConnectorToolbarInitializer extends AbstractToolbarInitializer {

    @Inject
    public JiraConnectorToolbarInitializer(ToolbarPresenter toolbar, EditorResources resources, WSO2EditorLocalizationConstant locale) {
        super(toolbar, resources, locale);
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        toolbar.addGroup(ToolbarGroupIds.JIRA_CONNECTORS, locale.toolbarGroupJiraConnector());

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraAddAttachmentToIssueIdTitle(),
                        locale.toolbarJiraAddAttachmentToIssueIdTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.ADD_ATTACHMENT_TO_ISSUE_ID);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraCreateFilterTitle(),
                        locale.toolbarJiraCreateFilterTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.CREATE_FILTER);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraCreateIssueTitle(),
                        locale.toolbarJiraCreateIssueTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.CREATE_ISSUE);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraDeleteAvatarForProjectTitle(),
                        locale.toolbarJiraDeleteAvatarForProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.DELETE_AVATAR_FOR_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraDeleteCommentTitle(),
                        locale.toolbarJiraDeleteCommentTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.DELETE_COMMENT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraDeleteFilterTitle(),
                        locale.toolbarJiraDeleteFilterTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.DELETE_FILTER);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraGetDashboardTitle(),
                        locale.toolbarJiraGetDashboardTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_DASHBOARD);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraDoTransitionTitle(),
                        locale.toolbarJiraDoTransitionTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.DO_TRANSITION);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraGetAvatarsForProjectTitle(),
                        locale.toolbarJiraGetAvatarsForProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_AVATARS_FOR_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraGetCommentsTitle(),
                        locale.toolbarJiraGetCommentsTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_COMMENTS);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraGetComponentsOfProjectTitle(),
                        locale.toolbarJiraGetComponentsOfProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_COMPONENTS_OF_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraGetDashboardByIdTitle(),
                        locale.toolbarJiraGetDashboardByIdTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_DASHBOARD_BY_ID);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraGetFavouriteFiltersTitle(),
                        locale.toolbarJiraGetFavouriteFiltersTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_FAVOURITE_FILTERS);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraGetFilterByIdTitle(),
                        locale.toolbarJiraGetFilterByIdTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_FILTER_BY_ID);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraGetGroupTitle(),
                        locale.toolbarJiraGetGroupTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_GROUP);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraGetIssueTitle(),
                        locale.toolbarJiraGetIssueTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_ISSUE);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraGetIssuePrioritiesTitle(),
                        locale.toolbarJiraGetIssuePrioritiesTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_ISSUE_PRIORITIES);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraGetIssuePriorityByIdTitle(),
                        locale.toolbarJiraGetIssuePriorityByIdTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_ISSUE_PRIORITY_BY_ID);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraGetIssueTypeByIdTitle(),
                        locale.toolbarJiraGetIssueTypeByIdTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_ISSUE_TYPE_BY_ID);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraGetIssueTypesTitle(),
                        locale.toolbarJiraGetIssueTypesTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_ISSUE_TYPES);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraGetIssuesForUserTitle(),
                        locale.toolbarJiraGetIssuesForUserTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_ISSUES_FOR_USER);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraGetProjectTitle(),
                        locale.toolbarJiraGetProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraGetRolesByIdOfProjectTitle(),
                        locale.toolbarJiraGetRolesByIdOfProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_ROLES_BY_ID_OF_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraGetRolesOfProjectTitle(),
                        locale.toolbarJiraGetRolesOfProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_ROLES_OF_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraGetStatusesOfProjectTitle(),
                        locale.toolbarJiraGetStatusesOfProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_STATUSES_OF_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraGetTransitionsTitle(),
                        locale.toolbarJiraGetTransitionsTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_TRANSITIONS);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraGetUserTitle(),
                        locale.toolbarJiraGetUserTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_USER);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraGetUserAssignableProjectsTitle(),
                        locale.toolbarJiraGetUserAssignableProjectsTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_USER_ASSIGNABLE_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraGetUserPermissionsTitle(),
                        locale.toolbarJiraGetUserPermissionsTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_USER_PERMISSIONS);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraGetVersionsOfProjectTitle(),
                        locale.toolbarJiraGetVersionsOfProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_VERSIONS_OF_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraGetVotesForIssueTitle(),
                        locale.toolbarJiraGetVotesForIssueTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_VOTES_FOR_ISSUE);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraInitTitle(),
                        locale.toolbarJiraInitTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.INIT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraPostCommentTitle(),
                        locale.toolbarJiraPostCommentTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.POST_COMMENT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraSearchAssignableUserTitle(),
                        locale.toolbarJiraSearchAssignableUserTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.SEARCH_ASSIGNABLE_USER);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraSearchAssignableUserMultiProjectTitle(),
                        locale.toolbarJiraSearchAssignableUserMultiProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.SEARCH_ASSIGNABLE_USER_MULTI_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraSearchIssueViewableUsersTitle(),
                        locale.toolbarJiraSearchIssueViewableUsersTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.SEARCH_ISSUE_VIEWABLE_USERS);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraSearchJiraTitle(),
                        locale.toolbarJiraSearchJiraTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.SEARCH_JIRA);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraSearchUserTitle(),
                        locale.toolbarJiraSearchUserTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.SEARCH_USER);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraSetActorsToRoleOfProjectTitle(),
                        locale.toolbarJiraSetActorsToRoleOfProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.SET_ACTORS_TO_ROLE_OF_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraUpdateCommentTitle(),
                        locale.toolbarJiraUpdateCommentTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.ADD_ATTACHMENT_TO_ISSUE_ID);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraUpdateCommentTitle(),
                        locale.toolbarJiraUpdateCommentTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.UPDATE_COMMENT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraUpdateFilterByIdTitle(),
                        locale.toolbarJiraUpdateFilterByIdTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.UPDATE_FILTER_BY_ID_JIRA);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraUpdateIssueTitle(),
                        locale.toolbarJiraUpdateIssueTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.UPDATE_ISSUE);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        locale.toolbarJiraUpdateIssueAssigneeTitle(),
                        locale.toolbarJiraUpdateIssueAssigneeTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.UPDATE_ISSUE_ASSIGNEE);
    }

}