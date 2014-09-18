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

package com.codenvy.ide.client.initializers.creators;

import com.codenvy.ide.client.constants.JiraConnectorCreatingState;
import com.codenvy.ide.client.elements.connectors.jira.AddAttachmentToIssueId;
import com.codenvy.ide.client.elements.connectors.jira.CreateFilter;
import com.codenvy.ide.client.elements.connectors.jira.CreateIssue;
import com.codenvy.ide.client.elements.connectors.jira.DeleteAvatarForProject;
import com.codenvy.ide.client.elements.connectors.jira.DeleteComment;
import com.codenvy.ide.client.elements.connectors.jira.DeleteFilter;
import com.codenvy.ide.client.elements.connectors.jira.DoTransition;
import com.codenvy.ide.client.elements.connectors.jira.GetAvatarsForProject;
import com.codenvy.ide.client.elements.connectors.jira.GetComments;
import com.codenvy.ide.client.elements.connectors.jira.GetComponentsOfProject;
import com.codenvy.ide.client.elements.connectors.jira.GetDashboard;
import com.codenvy.ide.client.elements.connectors.jira.GetDashboardById;
import com.codenvy.ide.client.elements.connectors.jira.GetFavouriteFilters;
import com.codenvy.ide.client.elements.connectors.jira.GetFilterById;
import com.codenvy.ide.client.elements.connectors.jira.GetGroup;
import com.codenvy.ide.client.elements.connectors.jira.GetIssue;
import com.codenvy.ide.client.elements.connectors.jira.GetIssuePriorities;
import com.codenvy.ide.client.elements.connectors.jira.GetIssuePriorityById;
import com.codenvy.ide.client.elements.connectors.jira.GetIssueTypeById;
import com.codenvy.ide.client.elements.connectors.jira.GetIssueTypes;
import com.codenvy.ide.client.elements.connectors.jira.GetIssuesForUser;
import com.codenvy.ide.client.elements.connectors.jira.GetProject;
import com.codenvy.ide.client.elements.connectors.jira.GetRolesByIdOfProject;
import com.codenvy.ide.client.elements.connectors.jira.GetRolesOfProject;
import com.codenvy.ide.client.elements.connectors.jira.GetStatusesOfProject;
import com.codenvy.ide.client.elements.connectors.jira.GetTransitions;
import com.codenvy.ide.client.elements.connectors.jira.GetUser;
import com.codenvy.ide.client.elements.connectors.jira.GetUserAssignableProjects;
import com.codenvy.ide.client.elements.connectors.jira.GetUserPermissions;
import com.codenvy.ide.client.elements.connectors.jira.GetVersionsOfProject;
import com.codenvy.ide.client.elements.connectors.jira.GetVotesForIssue;
import com.codenvy.ide.client.elements.connectors.jira.InitJira;
import com.codenvy.ide.client.elements.connectors.jira.PostComment;
import com.codenvy.ide.client.elements.connectors.jira.SearchAssignableUser;
import com.codenvy.ide.client.elements.connectors.jira.SearchAssignableUserMultiProject;
import com.codenvy.ide.client.elements.connectors.jira.SearchIssueViewableUsers;
import com.codenvy.ide.client.elements.connectors.jira.SearchJira;
import com.codenvy.ide.client.elements.connectors.jira.SearchUser;
import com.codenvy.ide.client.elements.connectors.jira.SetActorsToRoleOfProject;
import com.codenvy.ide.client.elements.connectors.jira.UpdateComment;
import com.codenvy.ide.client.elements.connectors.jira.UpdateFilterById;
import com.codenvy.ide.client.elements.connectors.jira.UpdateIssue;
import com.codenvy.ide.client.elements.connectors.jira.UpdateIssueAssignee;
import com.codenvy.ide.client.initializers.Initializer;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author Andrey Plotnikov
 */
public class JiraConnectorCreatorsInitializer implements Initializer {

    private final ElementCreatorsManager                     manager;
    private final Provider<AddAttachmentToIssueId>           addAttachmentToIssueIdProvider;
    private final Provider<CreateFilter>                     createFilterProvider;
    private final Provider<CreateIssue>                      createIssueProvider;
    private final Provider<DeleteAvatarForProject>           deleteAvatarForProjectProvider;
    private final Provider<DeleteComment>                    deleteCommentProvider;
    private final Provider<DeleteFilter>                     deleteFilterProvider;
    private final Provider<GetDashboard>                     getDashboardProvider;
    private final Provider<DoTransition>                     doTransitionProvider;
    private final Provider<GetAvatarsForProject>             getAvatarsForProjectProvider;
    private final Provider<GetComments>                      getCommentsProvider;
    private final Provider<GetComponentsOfProject>           getComponentsOfProjectProvider;
    private final Provider<GetDashboardById>                 getDashboardByIdProvider;
    private final Provider<GetFavouriteFilters>              getFavouriteFiltersProvider;
    private final Provider<GetFilterById>                    getFilterByIdProvider;
    private final Provider<GetGroup>                         getGroupProvider;
    private final Provider<GetIssue>                         getIssueProvider;
    private final Provider<GetIssuePriorities>               getIssuePrioritiesProvider;
    private final Provider<GetIssuePriorityById>             getIssuePriorityByIdProvider;
    private final Provider<GetIssueTypeById>                 getIssueTypeByIdProvider;
    private final Provider<GetIssueTypes>                    getIssueTypesProvider;
    private final Provider<GetIssuesForUser>                 getIssuesForUserProvider;
    private final Provider<GetProject>                       getProjectProvider;
    private final Provider<GetRolesByIdOfProject>            getRolesByIdOfProjectProvider;
    private final Provider<GetRolesOfProject>                getRolesOfProjectProvider;
    private final Provider<GetStatusesOfProject>             getStatusesOfProjectProvider;
    private final Provider<GetTransitions>                   getTransitionsProvider;
    private final Provider<GetUser>                          getUserProvider;
    private final Provider<GetUserAssignableProjects>        getUserAssignableProjectsProvider;
    private final Provider<GetUserPermissions>               getUserPermissionsProvider;
    private final Provider<GetVersionsOfProject>             getVersionsOfProjectProvider;
    private final Provider<GetVotesForIssue>                 getVotesForIssueProvider;
    private final Provider<InitJira>                         initJiraProvider;
    private final Provider<PostComment>                      postCommentProvider;
    private final Provider<SearchAssignableUser>             searchAssignableUserProvider;
    private final Provider<SearchAssignableUserMultiProject> searchAssignableUserMultiProjectProvider;
    private final Provider<SearchIssueViewableUsers>         searchIssueViewableUsersProvider;
    private final Provider<SearchJira>                       searchJiraProvider;
    private final Provider<SearchUser>                       searchUserProvider;
    private final Provider<SetActorsToRoleOfProject>         setActorsToRoleOfProjectProvider;
    private final Provider<UpdateComment>                    updateCommentProvider;
    private final Provider<UpdateFilterById>                 updateFilterByIdProvider;
    private final Provider<UpdateIssue>                      updateIssueProvider;
    private final Provider<UpdateIssueAssignee>              updateIssueAssigneeProvider;

    @Inject
    public JiraConnectorCreatorsInitializer(ElementCreatorsManager manager,
                                            Provider<AddAttachmentToIssueId> addAttachmentToIssueIdProvider,
                                            Provider<CreateFilter> createFilterProvider,
                                            Provider<CreateIssue> createIssueProvider,
                                            Provider<DeleteAvatarForProject> deleteAvatarForProjectProvider,
                                            Provider<DeleteComment> deleteCommentProvider,
                                            Provider<DeleteFilter> deleteFilterProvider,
                                            Provider<GetDashboard> getDashboardProvider,
                                            Provider<DoTransition> doTransitionProvider,
                                            Provider<GetAvatarsForProject> getAvatarsForProjectProvider,
                                            Provider<GetComments> getCommentsProvider,
                                            Provider<GetComponentsOfProject> getComponentsOfProjectProvider,
                                            Provider<GetDashboardById> getDashboardByIdProvider,
                                            Provider<GetFavouriteFilters> getFavouriteFiltersProvider,
                                            Provider<GetFilterById> getFilterByIdProvider,
                                            Provider<GetGroup> getGroupProvider,
                                            Provider<GetIssue> getIssueProvider,
                                            Provider<GetIssuePriorities> getIssuePrioritiesProvider,
                                            Provider<GetIssuePriorityById> getIssuePriorityByIdProvider,
                                            Provider<GetIssueTypeById> getIssueTypeByIdProvider,
                                            Provider<GetIssueTypes> getIssueTypesProvider,
                                            Provider<GetIssuesForUser> getIssuesForUserProvider,
                                            Provider<GetProject> getProjectProvider,
                                            Provider<GetRolesByIdOfProject> getRolesByIdOfProjectProvider,
                                            Provider<GetRolesOfProject> getRolesOfProjectProvider,
                                            Provider<GetStatusesOfProject> getStatusesOfProjectProvider,
                                            Provider<GetTransitions> getTransitionsProvider,
                                            Provider<GetUser> getUserProvider,
                                            Provider<GetUserAssignableProjects> getUserAssignableProjectsProvider,
                                            Provider<GetUserPermissions> getUserPermissionsProvider,
                                            Provider<GetVersionsOfProject> getVersionsOfProjectProvider,
                                            Provider<GetVotesForIssue> getVotesForIssueProvider,
                                            Provider<InitJira> initJiraProvider,
                                            Provider<PostComment> postCommentProvider,
                                            Provider<SearchAssignableUser> searchAssignableUserProvider,
                                            Provider<SearchAssignableUserMultiProject> searchAssignableUserMultiProjectProvider,
                                            Provider<SearchIssueViewableUsers> searchIssueViewableUsersProvider,
                                            Provider<SearchJira> searchJiraProvider,
                                            Provider<SearchUser> searchUserProvider,
                                            Provider<SetActorsToRoleOfProject> setActorsToRoleOfProjectProvider,
                                            Provider<UpdateComment> updateCommentProvider,
                                            Provider<UpdateFilterById> updateFilterByIdProvider,
                                            Provider<UpdateIssue> updateIssueProvider,
                                            Provider<UpdateIssueAssignee> updateIssueAssigneeProvider) {
        this.manager = manager;
        this.addAttachmentToIssueIdProvider = addAttachmentToIssueIdProvider;
        this.createFilterProvider = createFilterProvider;
        this.createIssueProvider = createIssueProvider;
        this.deleteAvatarForProjectProvider = deleteAvatarForProjectProvider;
        this.deleteCommentProvider = deleteCommentProvider;
        this.deleteFilterProvider = deleteFilterProvider;
        this.getDashboardProvider = getDashboardProvider;
        this.doTransitionProvider = doTransitionProvider;
        this.getAvatarsForProjectProvider = getAvatarsForProjectProvider;
        this.getCommentsProvider = getCommentsProvider;
        this.getComponentsOfProjectProvider = getComponentsOfProjectProvider;
        this.getDashboardByIdProvider = getDashboardByIdProvider;
        this.getFavouriteFiltersProvider = getFavouriteFiltersProvider;
        this.getFilterByIdProvider = getFilterByIdProvider;
        this.getGroupProvider = getGroupProvider;
        this.getIssueProvider = getIssueProvider;
        this.getIssuePrioritiesProvider = getIssuePrioritiesProvider;
        this.getIssuePriorityByIdProvider = getIssuePriorityByIdProvider;
        this.getIssueTypeByIdProvider = getIssueTypeByIdProvider;
        this.getIssueTypesProvider = getIssueTypesProvider;
        this.getIssuesForUserProvider = getIssuesForUserProvider;
        this.getProjectProvider = getProjectProvider;
        this.getRolesByIdOfProjectProvider = getRolesByIdOfProjectProvider;
        this.getRolesOfProjectProvider = getRolesOfProjectProvider;
        this.getStatusesOfProjectProvider = getStatusesOfProjectProvider;
        this.getTransitionsProvider = getTransitionsProvider;
        this.getUserProvider = getUserProvider;
        this.getUserAssignableProjectsProvider = getUserAssignableProjectsProvider;
        this.getUserPermissionsProvider = getUserPermissionsProvider;
        this.getVersionsOfProjectProvider = getVersionsOfProjectProvider;
        this.getVotesForIssueProvider = getVotesForIssueProvider;
        this.initJiraProvider = initJiraProvider;
        this.postCommentProvider = postCommentProvider;
        this.searchAssignableUserProvider = searchAssignableUserProvider;
        this.searchAssignableUserMultiProjectProvider = searchAssignableUserMultiProjectProvider;
        this.searchIssueViewableUsersProvider = searchIssueViewableUsersProvider;
        this.searchJiraProvider = searchJiraProvider;
        this.searchUserProvider = searchUserProvider;
        this.setActorsToRoleOfProjectProvider = setActorsToRoleOfProjectProvider;
        this.updateCommentProvider = updateCommentProvider;
        this.updateFilterByIdProvider = updateFilterByIdProvider;
        this.updateIssueProvider = updateIssueProvider;
        this.updateIssueAssigneeProvider = updateIssueAssigneeProvider;
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        manager.register(AddAttachmentToIssueId.ELEMENT_NAME,
                         AddAttachmentToIssueId.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.ADD_ATTACHMENT_TO_ISSUE_ID,
                         addAttachmentToIssueIdProvider);

        manager.register(CreateFilter.ELEMENT_NAME,
                         CreateFilter.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.CREATE_FILTER,
                         createFilterProvider);

        manager.register(CreateIssue.ELEMENT_NAME,
                         CreateIssue.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.CREATE_ISSUE,
                         createIssueProvider);

        manager.register(DeleteAvatarForProject.ELEMENT_NAME,
                         DeleteAvatarForProject.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.DELETE_AVATAR_FOR_PROJECT,
                         deleteAvatarForProjectProvider);

        manager.register(DeleteComment.ELEMENT_NAME,
                         DeleteComment.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.DELETE_COMMENT,
                         deleteCommentProvider);

        manager.register(DeleteFilter.ELEMENT_NAME,
                         DeleteFilter.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.DELETE_FILTER,
                         deleteFilterProvider);

        manager.register(GetDashboard.ELEMENT_NAME,
                         GetDashboard.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.GET_DASHBOARD,
                         getDashboardProvider);

        manager.register(DoTransition.ELEMENT_NAME,
                         DoTransition.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.DO_TRANSITION,
                         doTransitionProvider);

        manager.register(GetAvatarsForProject.ELEMENT_NAME,
                         GetAvatarsForProject.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.GET_AVATARS_FOR_PROJECT,
                         getAvatarsForProjectProvider);

        manager.register(GetComments.ELEMENT_NAME,
                         GetComments.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.GET_COMMENTS,
                         getCommentsProvider);

        manager.register(GetComponentsOfProject.ELEMENT_NAME,
                         GetComponentsOfProject.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.GET_COMPONENTS_OF_PROJECT,
                         getComponentsOfProjectProvider);

        manager.register(GetDashboardById.ELEMENT_NAME,
                         GetDashboardById.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.GET_DASHBOARD_BY_ID,
                         getDashboardByIdProvider);

        manager.register(GetFavouriteFilters.ELEMENT_NAME,
                         GetFavouriteFilters.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.GET_FAVOURITE_FILTERS,
                         getFavouriteFiltersProvider);

        manager.register(GetFilterById.ELEMENT_NAME,
                         GetFilterById.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.GET_FILTER_BY_ID,
                         getFilterByIdProvider);

        manager.register(GetGroup.ELEMENT_NAME, GetGroup.SERIALIZATION_NAME, JiraConnectorCreatingState.GET_GROUP, getGroupProvider);
        manager.register(GetIssue.ELEMENT_NAME, GetIssue.SERIALIZATION_NAME, JiraConnectorCreatingState.GET_ISSUE, getIssueProvider);

        manager.register(GetIssuePriorities.ELEMENT_NAME,
                         GetIssuePriorities.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.GET_ISSUE_PRIORITIES,
                         getIssuePrioritiesProvider);

        manager.register(GetIssuePriorityById.ELEMENT_NAME,
                         GetIssuePriorityById.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.GET_ISSUE_PRIORITY_BY_ID,
                         getIssuePriorityByIdProvider);

        manager.register(GetIssueTypeById.ELEMENT_NAME,
                         GetIssueTypeById.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.GET_ISSUE_TYPE_BY_ID,
                         getIssueTypeByIdProvider);

        manager.register(GetIssueTypes.ELEMENT_NAME,
                         GetIssueTypes.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.GET_ISSUE_TYPES,
                         getIssueTypesProvider);

        manager.register(GetIssuesForUser.ELEMENT_NAME,
                         GetIssuesForUser.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.GET_ISSUES_FOR_USER,
                         getIssuesForUserProvider);

        manager.register(GetProject.ELEMENT_NAME,
                         GetProject.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.GET_PROJECT,
                         getProjectProvider);

        manager.register(GetRolesByIdOfProject.ELEMENT_NAME,
                         GetRolesByIdOfProject.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.GET_ROLES_BY_ID_OF_PROJECT,
                         getRolesByIdOfProjectProvider);

        manager.register(GetRolesOfProject.ELEMENT_NAME,
                         GetRolesOfProject.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.GET_ROLES_OF_PROJECT,
                         getRolesOfProjectProvider);

        manager.register(GetStatusesOfProject.ELEMENT_NAME,
                         GetStatusesOfProject.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.GET_STATUSES_OF_PROJECT,
                         getStatusesOfProjectProvider);

        manager.register(GetTransitions.ELEMENT_NAME,
                         GetTransitions.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.GET_TRANSITIONS,
                         getTransitionsProvider);

        manager.register(GetUser.ELEMENT_NAME, GetUser.SERIALIZATION_NAME, JiraConnectorCreatingState.GET_USER, getUserProvider);

        manager.register(GetUserAssignableProjects.ELEMENT_NAME,
                         GetUserAssignableProjects.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.GET_USER_ASSIGNABLE_PROJECT,
                         getUserAssignableProjectsProvider);

        manager.register(GetUserPermissions.ELEMENT_NAME,
                         GetUserPermissions.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.GET_USER_PERMISSIONS,
                         getUserPermissionsProvider);

        manager.register(GetVersionsOfProject.ELEMENT_NAME,
                         GetVersionsOfProject.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.GET_VERSIONS_OF_PROJECT,
                         getVersionsOfProjectProvider);

        manager.register(GetVotesForIssue.ELEMENT_NAME,
                         GetVotesForIssue.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.GET_VOTES_FOR_ISSUE,
                         getVotesForIssueProvider);

        manager.register(InitJira.ELEMENT_NAME, InitJira.SERIALIZATION_NAME, JiraConnectorCreatingState.INIT, initJiraProvider);

        manager.register(PostComment.ELEMENT_NAME,
                         PostComment.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.POST_COMMENT,
                         postCommentProvider);

        manager.register(SearchAssignableUser.ELEMENT_NAME,
                         SearchAssignableUser.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.SEARCH_ASSIGNABLE_USER,
                         searchAssignableUserProvider);

        manager.register(SearchAssignableUserMultiProject.ELEMENT_NAME,
                         SearchAssignableUserMultiProject.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.SEARCH_ASSIGNABLE_USER_MULTI_PROJECT,
                         searchAssignableUserMultiProjectProvider);

        manager.register(SearchIssueViewableUsers.ELEMENT_NAME,
                         SearchIssueViewableUsers.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.SEARCH_ISSUE_VIEWABLE_USERS,
                         searchIssueViewableUsersProvider);

        manager.register(SearchJira.ELEMENT_NAME,
                         SearchJira.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.SEARCH_JIRA,
                         searchJiraProvider);

        manager.register(SearchUser.ELEMENT_NAME,
                         SearchUser.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.SEARCH_USER,
                         searchUserProvider);

        manager.register(SetActorsToRoleOfProject.ELEMENT_NAME,
                         SetActorsToRoleOfProject.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.SET_ACTORS_TO_ROLE_OF_PROJECT,
                         setActorsToRoleOfProjectProvider);

        manager.register(UpdateComment.ELEMENT_NAME,
                         UpdateComment.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.UPDATE_COMMENT,
                         updateCommentProvider);

        manager.register(UpdateFilterById.ELEMENT_NAME,
                         UpdateFilterById.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.UPDATE_FILTER_BY_ID_JIRA,
                         updateFilterByIdProvider);

        manager.register(UpdateIssue.ELEMENT_NAME,
                         UpdateIssue.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.UPDATE_ISSUE,
                         updateIssueProvider);

        manager.register(UpdateIssueAssignee.ELEMENT_NAME,
                         UpdateIssueAssignee.SERIALIZATION_NAME,
                         JiraConnectorCreatingState.UPDATE_ISSUE_ASSIGNEE,
                         updateIssueAssigneeProvider);
    }

}