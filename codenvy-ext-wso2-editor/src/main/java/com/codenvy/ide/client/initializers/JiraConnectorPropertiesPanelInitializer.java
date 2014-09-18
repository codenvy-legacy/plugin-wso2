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

package com.codenvy.ide.client.initializers;

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
import com.codenvy.ide.client.elements.connectors.salesforce.Query;
import com.codenvy.ide.client.elements.connectors.salesforce.QueryAll;
import com.codenvy.ide.client.elements.connectors.salesforce.QueryMore;
import com.codenvy.ide.client.elements.connectors.salesforce.ResetPassword;
import com.codenvy.ide.client.elements.connectors.salesforce.Retrieve;
import com.codenvy.ide.client.managers.PropertiesPanelManager;
import com.codenvy.ide.client.propertiespanel.connectors.jira.AddAttachmentConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.CreateFilterConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.CreateIssueConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.DeleteAvatarConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.DeleteCommentConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.DeleteFilterConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.DoTransitionConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.GetAvatarsConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.GetCommentsConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.GetComponentsConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.GetDashBoardByIdConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.GetDashboardConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.GetFavouriteFiltersConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.GetFilterByIdConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.GetGroupConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.GetIssueConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.GetIssuePrioritiesConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.GetIssuePriorityByIdConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.GetIssueTypeByIdConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.GetIssueTypesConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.GetIssuesForUserConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.GetProjectConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.GetRolesByIdOfProjectConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.GetRolesOfProjectConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.GetStatusesOfProjectConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.GetTransitionsConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.GetUserAssignableProjectsConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.GetUserConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.GetUserPermissionsConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.GetVersionsOfProjectConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.GetVotesForIssueConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.InitAbstractConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.PostCommentConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.SearchAssignableUserConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.SearchAssignableUserMultiProjectConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.SearchConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.SearchIssueViewAbleUsersConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.SearchUserConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.SetActorsToRoleOfProjectConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.UpdateCommentConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.UpdateFilterByIdConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.UpdateIssueAssigneeConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.jira.UpdateIssueConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.QueryAllConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.QueryConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.QueryMoreConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.ResetPasswordConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.RetrieveConnectorPresenter;
import com.google.inject.Inject;

/**
 * @author Andrey Plotnikov
 */
public class JiraConnectorPropertiesPanelInitializer implements Initializer {

    private final PropertiesPanelManager                             propertiesPanelManager;
    private final SearchConnectorPresenter                           searchPropertiesPanel;
    private final RetrieveConnectorPresenter                         retrievePropertiesPanel;
    private final QueryConnectorPresenter                            queryPropertiesPanel;
    private final QueryAllConnectorPresenter                         queryAllPropertiesPanel;
    private final QueryMoreConnectorPresenter                        queryMorePropertiesPanel;
    private final ResetPasswordConnectorPresenter                    resetPasswordPropertiesPanel;
    private final AddAttachmentConnectorPresenter                    addAttachmentPropertiesPanel;
    private final CreateFilterConnectorPresenter                     createFilterPropertiesPanel;
    private final CreateIssueConnectorPresenter                      createIssuePropertiesPanel;
    private final DeleteAvatarConnectorPresenter                     deleteAvatarPropertiesPanel;
    private final DeleteCommentConnectorPresenter                    deleteCommentPropertiesPanel;
    private final DeleteFilterConnectorPresenter                     deleteFilterPropertiesPanel;
    private final GetDashboardConnectorPresenter                     getDashboardPropertiesPanel;
    private final DoTransitionConnectorPresenter                     doTransitionPropertiesPanel;
    private final GetAvatarsConnectorPresenter                       getAvatarsPropertiesPanel;
    private final GetCommentsConnectorPresenter                      getCommentsPropertiesPanel;
    private final GetComponentsConnectorPresenter                    getComponentsPropertiesPanel;
    private final GetDashBoardByIdConnectorPresenter                 getDashBoardByIdPropertiesPanel;
    private final GetFavouriteFiltersConnectorPresenter              getFavouriteFiltersPropertiesPanel;
    private final GetFilterByIdConnectorPresenter                    getFilterByIdPropertiesPanel;
    private final GetGroupConnectorPresenter                         getGroupPropertiesPanel;
    private final GetIssueConnectorPresenter                         getIssuePropertiesPanel;
    private final GetIssuePrioritiesConnectorPresenter               getIssuePrioritiesPropertiesPanel;
    private final GetIssuePriorityByIdConnectorPresenter             getIssuePriorityByIdPropertiesPanel;
    private final GetIssueTypeByIdConnectorPresenter                 getIssueTypeByIdPropertiesPanel;
    private final GetIssueTypesConnectorPresenter                    getIssueTypesPropertiesPanel;
    private final GetIssuesForUserConnectorPresenter                 getIssuesForUserPropertiesPanel;
    private final GetProjectConnectorPresenter                       getProjectPropertiesPanel;
    private final GetRolesByIdOfProjectConnectorPresenter            getRolesByIdOfProjectPropertiesPanel;
    private final GetRolesOfProjectConnectorPresenter                getRolesOfProjectPropertiesPanel;
    private final GetStatusesOfProjectConnectorPresenter             getStatusesOfProjectPropertiesPanel;
    private final GetTransitionsConnectorPresenter                   getTransitionsPropertiesPanel;
    private final GetUserConnectorPresenter                          getUserPropertiesPanel;
    private final GetUserAssignableProjectsConnectorPresenter        getUserAssignableProjectsPropertiesPanel;
    private final GetUserPermissionsConnectorPresenter               getUserPermissionsPropertiesPanel;
    private final GetVersionsOfProjectConnectorPresenter             getVersionsOfProjectPropertiesPanel;
    private final GetVotesForIssueConnectorPresenter                 getVotesForIssuePropertiesPanel;
    private final InitAbstractConnectorPresenter                     initJiraPropertiesPanel;
    private final PostCommentConnectorPresenter                      postCommentPropertiesPanel;
    private final SearchAssignableUserConnectorPresenter             searchAssignableUserPropertiesPanel;
    private final SearchAssignableUserMultiProjectConnectorPresenter searchAssignableUserMultiPropertiesPanel;
    private final SearchIssueViewAbleUsersConnectorPresenter         searchIssueViewAbleUsersPropertiesPanel;
    private final SearchConnectorPresenter                           searchJiraPropertiesPanel;
    private final SearchUserConnectorPresenter                       searchUserPropertiesPanel;
    private final SetActorsToRoleOfProjectConnectorPresenter         setActorsToRoleOfProjectPropertiesPanel;
    private final UpdateCommentConnectorPresenter                    updateCommentPropertiesPanel;
    private final UpdateFilterByIdConnectorPresenter                 updateFilterByIdPropertiesPanel;
    private final UpdateIssueConnectorPresenter                      updateIssuePropertiesPanel;
    private final UpdateIssueAssigneeConnectorPresenter              updateIssueAssigneePropertiesPanel;

    @Inject
    public JiraConnectorPropertiesPanelInitializer(PropertiesPanelManager propertiesPanelManager,
                                                   SearchConnectorPresenter searchPropertiesPanel,
                                                   RetrieveConnectorPresenter retrievePropertiesPanel,
                                                   QueryConnectorPresenter queryPropertiesPanel,
                                                   QueryAllConnectorPresenter queryAllPropertiesPanel,
                                                   QueryMoreConnectorPresenter queryMorePropertiesPanel,
                                                   ResetPasswordConnectorPresenter resetPasswordPropertiesPanel,
                                                   AddAttachmentConnectorPresenter addAttachmentPropertiesPanel,
                                                   CreateFilterConnectorPresenter createFilterPropertiesPanel,
                                                   CreateIssueConnectorPresenter createIssuePropertiesPanel,
                                                   DeleteAvatarConnectorPresenter deleteAvatarPropertiesPanel,
                                                   DeleteCommentConnectorPresenter deleteCommentPropertiesPanel,
                                                   DeleteFilterConnectorPresenter deleteFilterPropertiesPanel,
                                                   GetDashboardConnectorPresenter getDashboardPropertiesPanel,
                                                   DoTransitionConnectorPresenter doTransitionPropertiesPanel,
                                                   GetAvatarsConnectorPresenter getAvatarsPropertiesPanel,
                                                   GetCommentsConnectorPresenter getCommentsPropertiesPanel,
                                                   GetComponentsConnectorPresenter getComponentsPropertiesPanel,
                                                   GetDashBoardByIdConnectorPresenter getDashBoardByIdPropertiesPanel,
                                                   GetFavouriteFiltersConnectorPresenter getFavouriteFiltersPropertiesPanel,
                                                   GetFilterByIdConnectorPresenter getFilterByIdPropertiesPanel,
                                                   GetGroupConnectorPresenter getGroupPropertiesPanel,
                                                   GetIssueConnectorPresenter getIssuePropertiesPanel,
                                                   GetIssuePrioritiesConnectorPresenter getIssuePrioritiesPropertiesPanel,
                                                   GetIssuePriorityByIdConnectorPresenter getIssuePriorityByIdPropertiesPanel,
                                                   GetIssueTypeByIdConnectorPresenter getIssueTypeByIdPropertiesPanel,
                                                   GetIssueTypesConnectorPresenter getIssueTypesPropertiesPanel,
                                                   GetIssuesForUserConnectorPresenter getIssuesForUserPropertiesPanel,
                                                   GetProjectConnectorPresenter getProjectPropertiesPanel,
                                                   GetRolesByIdOfProjectConnectorPresenter getRolesByIdOfProjectPropertiesPanel,
                                                   GetRolesOfProjectConnectorPresenter getRolesOfProjectPropertiesPanel,
                                                   GetStatusesOfProjectConnectorPresenter getStatusesOfProjectPropertiesPanel,
                                                   GetTransitionsConnectorPresenter getTransitionsPropertiesPanel,
                                                   GetUserConnectorPresenter getUserPropertiesPanel,
                                                   GetUserAssignableProjectsConnectorPresenter getUserAssignableProjectsPropertiesPanel,
                                                   GetUserPermissionsConnectorPresenter getUserPermissionsPropertiesPanel,
                                                   GetVersionsOfProjectConnectorPresenter getVersionsOfProjectPropertiesPanel,
                                                   GetVotesForIssueConnectorPresenter getVotesForIssuePropertiesPanel,
                                                   InitAbstractConnectorPresenter initJiraPropertiesPanel,
                                                   PostCommentConnectorPresenter postCommentPropertiesPanel,
                                                   SearchAssignableUserConnectorPresenter searchAssignableUserPropertiesPanel,
                                                   SearchAssignableUserMultiProjectConnectorPresenter searchAssignableUserMultiPropertiesPanel,
                                                   SearchIssueViewAbleUsersConnectorPresenter searchIssueViewAbleUsersPropertiesPanel,
                                                   SearchConnectorPresenter searchJiraPropertiesPanel,
                                                   SearchUserConnectorPresenter searchUserPropertiesPanel,
                                                   SetActorsToRoleOfProjectConnectorPresenter setActorsToRoleOfProjectPropertiesPanel,
                                                   UpdateCommentConnectorPresenter updateCommentPropertiesPanel,
                                                   UpdateFilterByIdConnectorPresenter updateFilterByIdPropertiesPanel,
                                                   UpdateIssueConnectorPresenter updateIssuePropertiesPanel,
                                                   UpdateIssueAssigneeConnectorPresenter updateIssueAssigneePropertiesPanel) {
        this.propertiesPanelManager = propertiesPanelManager;
        this.searchPropertiesPanel = searchPropertiesPanel;
        this.retrievePropertiesPanel = retrievePropertiesPanel;
        this.queryPropertiesPanel = queryPropertiesPanel;
        this.queryAllPropertiesPanel = queryAllPropertiesPanel;
        this.queryMorePropertiesPanel = queryMorePropertiesPanel;
        this.resetPasswordPropertiesPanel = resetPasswordPropertiesPanel;
        this.addAttachmentPropertiesPanel = addAttachmentPropertiesPanel;
        this.createFilterPropertiesPanel = createFilterPropertiesPanel;
        this.createIssuePropertiesPanel = createIssuePropertiesPanel;
        this.deleteAvatarPropertiesPanel = deleteAvatarPropertiesPanel;
        this.deleteCommentPropertiesPanel = deleteCommentPropertiesPanel;
        this.deleteFilterPropertiesPanel = deleteFilterPropertiesPanel;
        this.getDashboardPropertiesPanel = getDashboardPropertiesPanel;
        this.doTransitionPropertiesPanel = doTransitionPropertiesPanel;
        this.getAvatarsPropertiesPanel = getAvatarsPropertiesPanel;
        this.getCommentsPropertiesPanel = getCommentsPropertiesPanel;
        this.getComponentsPropertiesPanel = getComponentsPropertiesPanel;
        this.getDashBoardByIdPropertiesPanel = getDashBoardByIdPropertiesPanel;
        this.getFavouriteFiltersPropertiesPanel = getFavouriteFiltersPropertiesPanel;
        this.getFilterByIdPropertiesPanel = getFilterByIdPropertiesPanel;
        this.getGroupPropertiesPanel = getGroupPropertiesPanel;
        this.getIssuePropertiesPanel = getIssuePropertiesPanel;
        this.getIssuePrioritiesPropertiesPanel = getIssuePrioritiesPropertiesPanel;
        this.getIssuePriorityByIdPropertiesPanel = getIssuePriorityByIdPropertiesPanel;
        this.getIssueTypeByIdPropertiesPanel = getIssueTypeByIdPropertiesPanel;
        this.getIssueTypesPropertiesPanel = getIssueTypesPropertiesPanel;
        this.getIssuesForUserPropertiesPanel = getIssuesForUserPropertiesPanel;
        this.getProjectPropertiesPanel = getProjectPropertiesPanel;
        this.getRolesByIdOfProjectPropertiesPanel = getRolesByIdOfProjectPropertiesPanel;
        this.getRolesOfProjectPropertiesPanel = getRolesOfProjectPropertiesPanel;
        this.getStatusesOfProjectPropertiesPanel = getStatusesOfProjectPropertiesPanel;
        this.getTransitionsPropertiesPanel = getTransitionsPropertiesPanel;
        this.getUserPropertiesPanel = getUserPropertiesPanel;
        this.getUserAssignableProjectsPropertiesPanel = getUserAssignableProjectsPropertiesPanel;
        this.getUserPermissionsPropertiesPanel = getUserPermissionsPropertiesPanel;
        this.getVersionsOfProjectPropertiesPanel = getVersionsOfProjectPropertiesPanel;
        this.getVotesForIssuePropertiesPanel = getVotesForIssuePropertiesPanel;
        this.initJiraPropertiesPanel = initJiraPropertiesPanel;
        this.postCommentPropertiesPanel = postCommentPropertiesPanel;
        this.searchAssignableUserPropertiesPanel = searchAssignableUserPropertiesPanel;
        this.searchAssignableUserMultiPropertiesPanel = searchAssignableUserMultiPropertiesPanel;
        this.searchIssueViewAbleUsersPropertiesPanel = searchIssueViewAbleUsersPropertiesPanel;
        this.searchJiraPropertiesPanel = searchJiraPropertiesPanel;
        this.searchUserPropertiesPanel = searchUserPropertiesPanel;
        this.setActorsToRoleOfProjectPropertiesPanel = setActorsToRoleOfProjectPropertiesPanel;
        this.updateCommentPropertiesPanel = updateCommentPropertiesPanel;
        this.updateFilterByIdPropertiesPanel = updateFilterByIdPropertiesPanel;
        this.updateIssuePropertiesPanel = updateIssuePropertiesPanel;
        this.updateIssueAssigneePropertiesPanel = updateIssueAssigneePropertiesPanel;
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        propertiesPanelManager.register(Query.class, queryPropertiesPanel);
        propertiesPanelManager.register(QueryAll.class, queryAllPropertiesPanel);
        propertiesPanelManager.register(QueryMore.class, queryMorePropertiesPanel);
        propertiesPanelManager.register(ResetPassword.class, resetPasswordPropertiesPanel);
        propertiesPanelManager.register(SearchJira.class, searchPropertiesPanel);
        propertiesPanelManager.register(Retrieve.class, retrievePropertiesPanel);
        propertiesPanelManager.register(AddAttachmentToIssueId.class, addAttachmentPropertiesPanel);
        propertiesPanelManager.register(CreateFilter.class, createFilterPropertiesPanel);
        propertiesPanelManager.register(CreateIssue.class, createIssuePropertiesPanel);
        propertiesPanelManager.register(DeleteAvatarForProject.class, deleteAvatarPropertiesPanel);
        propertiesPanelManager.register(DeleteComment.class, deleteCommentPropertiesPanel);
        propertiesPanelManager.register(DeleteFilter.class, deleteFilterPropertiesPanel);
        propertiesPanelManager.register(GetDashboard.class, getDashboardPropertiesPanel);
        propertiesPanelManager.register(DoTransition.class, doTransitionPropertiesPanel);
        propertiesPanelManager.register(GetAvatarsForProject.class, getAvatarsPropertiesPanel);
        propertiesPanelManager.register(GetComments.class, getCommentsPropertiesPanel);
        propertiesPanelManager.register(GetComponentsOfProject.class, getComponentsPropertiesPanel);
        propertiesPanelManager.register(GetDashboardById.class, getDashBoardByIdPropertiesPanel);
        propertiesPanelManager.register(GetFavouriteFilters.class, getFavouriteFiltersPropertiesPanel);
        propertiesPanelManager.register(GetFilterById.class, getFilterByIdPropertiesPanel);
        propertiesPanelManager.register(GetGroup.class, getGroupPropertiesPanel);
        propertiesPanelManager.register(GetIssue.class, getIssuePropertiesPanel);
        propertiesPanelManager.register(GetIssuePriorities.class, getIssuePrioritiesPropertiesPanel);
        propertiesPanelManager.register(GetIssuePriorityById.class, getIssuePriorityByIdPropertiesPanel);
        propertiesPanelManager.register(GetIssueTypeById.class, getIssueTypeByIdPropertiesPanel);
        propertiesPanelManager.register(GetIssueTypes.class, getIssueTypesPropertiesPanel);
        propertiesPanelManager.register(GetIssuesForUser.class, getIssuesForUserPropertiesPanel);
        propertiesPanelManager.register(GetProject.class, getProjectPropertiesPanel);
        propertiesPanelManager.register(GetRolesByIdOfProject.class, getRolesByIdOfProjectPropertiesPanel);
        propertiesPanelManager.register(GetRolesOfProject.class, getRolesOfProjectPropertiesPanel);
        propertiesPanelManager.register(GetStatusesOfProject.class, getStatusesOfProjectPropertiesPanel);
        propertiesPanelManager.register(GetTransitions.class, getTransitionsPropertiesPanel);
        propertiesPanelManager.register(GetUser.class, getUserPropertiesPanel);
        propertiesPanelManager.register(GetUserAssignableProjects.class, getUserAssignableProjectsPropertiesPanel);
        propertiesPanelManager.register(GetUserPermissions.class, getUserPermissionsPropertiesPanel);
        propertiesPanelManager.register(GetVersionsOfProject.class, getVersionsOfProjectPropertiesPanel);
        propertiesPanelManager.register(GetVotesForIssue.class, getVotesForIssuePropertiesPanel);
        propertiesPanelManager.register(InitJira.class, initJiraPropertiesPanel);
        propertiesPanelManager.register(PostComment.class, postCommentPropertiesPanel);
        propertiesPanelManager.register(SearchAssignableUser.class, searchAssignableUserPropertiesPanel);
        propertiesPanelManager.register(SearchAssignableUserMultiProject.class, searchAssignableUserMultiPropertiesPanel);
        propertiesPanelManager.register(SearchIssueViewableUsers.class, searchIssueViewAbleUsersPropertiesPanel);
        propertiesPanelManager.register(SearchJira.class, searchJiraPropertiesPanel);
        propertiesPanelManager.register(SearchUser.class, searchUserPropertiesPanel);
        propertiesPanelManager.register(SetActorsToRoleOfProject.class, setActorsToRoleOfProjectPropertiesPanel);
        propertiesPanelManager.register(UpdateComment.class, updateCommentPropertiesPanel);
        propertiesPanelManager.register(UpdateFilterById.class, updateFilterByIdPropertiesPanel);
        propertiesPanelManager.register(UpdateIssue.class, updateIssuePropertiesPanel);
        propertiesPanelManager.register(UpdateIssueAssignee.class, updateIssueAssigneePropertiesPanel);
    }

}