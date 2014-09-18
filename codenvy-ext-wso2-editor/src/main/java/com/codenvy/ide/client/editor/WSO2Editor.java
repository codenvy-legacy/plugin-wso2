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
package com.codenvy.ide.client.editor;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.constants.EndpointCreatingState;
import com.codenvy.ide.client.constants.GoogleSpreadsheedConnectorCreatingState;
import com.codenvy.ide.client.constants.JiraConnectorCreatingState;
import com.codenvy.ide.client.constants.MediatorCreatingState;
import com.codenvy.ide.client.constants.SalesForceConnectorCreatingState;
import com.codenvy.ide.client.constants.ToolbarGroupIds;
import com.codenvy.ide.client.constants.TwitterConnectorCreatingState;
import com.codenvy.ide.client.elements.RootElement;
import com.codenvy.ide.client.elements.widgets.element.ElementChangedListener;
import com.codenvy.ide.client.elements.widgets.element.ElementPresenter;
import com.codenvy.ide.client.initializers.Initializer;
import com.codenvy.ide.client.inject.factories.ElementWidgetFactory;
import com.codenvy.ide.client.managers.PropertiesPanelManager;
import com.codenvy.ide.client.mvp.AbstractPresenter;
import com.codenvy.ide.client.propertiespanel.PropertyChangedListener;
import com.codenvy.ide.client.toolbar.ToolbarPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The presenter that provides a business logic of WSO2Editor. It provides ability to configure the editor.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class WSO2Editor extends AbstractPresenter<WSO2EditorView> implements PropertyChangedListener,
                                                                             WSO2EditorView.ActionDelegate,
                                                                             ElementChangedListener {

    private final ToolbarPresenter           toolbar;
    private final RootElement                rootElement;
    private final ElementPresenter           rootElementPresenter;
    private final List<EditorChangeListener> listeners;

    @Inject
    public WSO2Editor(WSO2EditorView view,
                      ElementWidgetFactory elementWidgetFactory,
                      ToolbarPresenter toolbar,
                      RootElement rootElement,
                      PropertiesPanelManager propertiesPanelManager,
                      Set<Initializer> initializers) {
        super(view);
        onShowPropertyButtonClicked();

        this.listeners = new ArrayList<>();
        this.toolbar = toolbar;
        this.rootElement = rootElement;

        this.rootElementPresenter = elementWidgetFactory.createElementPresenter(rootElement);
        this.rootElementPresenter.addElementChangedListener(this);

        propertiesPanelManager.addPropertyChangedListener(this);
        propertiesPanelManager.setContainer(view.getPropertiesPanel());

        for (Initializer initializer : initializers) {
            initializer.initialize();
        }
    }

    @Inject
    private void configureToolbar(ToolbarPresenter toolbar,
                                  EditorResources resources,
                                  WSO2EditorLocalizationConstant localizationConstant) {

        toolbar.addGroup(ToolbarGroupIds.MEDIATORS, localizationConstant.toolbarGroupMediators());

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarCallTitle(),
                        localizationConstant.toolbarCallTooltip(),
                        resources.callToolbar(),
                        MediatorCreatingState.CALL_MEDIATOR);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarCallTemplateTitle(),
                        localizationConstant.toolbarCallTemplateTooltip(),
                        resources.callTemplateToolbar(),
                        MediatorCreatingState.CALLTEMPLATE);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarLogTitle(),
                        localizationConstant.toolbarLogTooltip(),
                        resources.logToolbar(),
                        MediatorCreatingState.LOG);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarLoopBackTitle(),
                        localizationConstant.toolbarLoopBackTooltip(),
                        resources.loopBackToolbar(),
                        MediatorCreatingState.LOOPBACK);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarPropertyTitle(),
                        localizationConstant.toolbarPropertyTooltip(),
                        resources.propertyToolbar(),
                        MediatorCreatingState.PROPERTY);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarRespondTitle(),
                        localizationConstant.toolbarRespondTooltip(),
                        resources.respondToolbar(),
                        MediatorCreatingState.RESPOND);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarSendTitle(),
                        localizationConstant.toolbarSendTooltip(),
                        resources.sendToolbar(),
                        MediatorCreatingState.SEND);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarSequenceTitle(),
                        localizationConstant.toolbarSequenceTooltip(),
                        resources.sequenceToolbar(),
                        MediatorCreatingState.SEQUENCE);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarFilterTitle(),
                        localizationConstant.toolbarFilterTooltip(),
                        resources.filterToolbar(),
                        MediatorCreatingState.FILTER);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarSwitchTitle(),
                        localizationConstant.toolbarSwitchTooltip(),
                        resources.switchToolbar(),
                        MediatorCreatingState.SWITCH);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarHeaderTitle(),
                        localizationConstant.toolbarHeaderTooltip(),
                        resources.headerToolbar(),
                        MediatorCreatingState.HEADER);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarPayloadFactoryTitle(),
                        localizationConstant.toolbarPayloadFactoryTooltip(),
                        resources.payloadFactoryToolbar(),
                        MediatorCreatingState.PAYLOAD);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarEnrichTitle(),
                        localizationConstant.toolbarEnrichTooltip(),
                        resources.enrichToolbar(),
                        MediatorCreatingState.ENRICH);

        toolbar.addGroup(ToolbarGroupIds.ENDPOINTS, localizationConstant.toolbarGroupEndpoints());

        toolbar.addItem(ToolbarGroupIds.ENDPOINTS,
                        localizationConstant.toolbarAddressEndpointTitle(),
                        localizationConstant.toolbarAddressEndpointTooltip(),
                        resources.addressToolbar(),
                        EndpointCreatingState.ADDRESS);

        toolbar.addGroup(ToolbarGroupIds.SALESFORCE_CONNECTORS, localizationConstant.toolbarGroupSalesforceConnector());

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceCreateConnectorTitle(),
                        localizationConstant.toolbarSalesforceCreateConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.CREATE);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesForceDeleteTitle(),
                        localizationConstant.toolbarSalesForceDeleteTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.DELETE);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceDescribeGlobalConnectorTitle(),
                        localizationConstant.toolbarSalesforceDescribeGlobalConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.DESCRIBE_GLOBAL);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceDescribeSubjectConnectorTitle(),
                        localizationConstant.toolbarSalesforceDescribeSubjectConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.DESCRIBE_SUBJECT);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceDescribeSubjectsConnectorTitle(),
                        localizationConstant.toolbarSalesforceDescribeSubjectsConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.DESCRIBE_SUBJECTS);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceEmptyRecycleBinConnectorTitle(),
                        localizationConstant.toolbarSalesforceEmptyRecycleBinConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.EMPTY_RECYCLE_BIN);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceGetUserInfoConnectorTitle(),
                        localizationConstant.toolbarSalesforceGetUserInfoConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.GET_USER_INFORMATION);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceInitConnectorTitle(),
                        localizationConstant.toolbarSalesforceInitConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.INIT);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceLogOutConnectorTitle(),
                        localizationConstant.toolbarSalesforceLogOutConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.LOGOUT);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceQueryTitle(),
                        localizationConstant.toolbarSalesforceQueryTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.QUERY);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceQueryTitle(),
                        localizationConstant.toolbarSalesforceQueryTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.QUERY);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceQueryAllTitle(),
                        localizationConstant.toolbarSalesforceQueryAllTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.QUERY_ALL);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceQueryMoreTitle(),
                        localizationConstant.toolbarSalesforceQueryMoreTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.QUERY_MORE);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceResetPasswordTitle(),
                        localizationConstant.toolbarSalesforceResetPasswordTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.RESET_PASSWORD);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceRetriveTitle(),
                        localizationConstant.toolbarSalesforceRetriveTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.RETRIVE);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceSearchTitle(),
                        localizationConstant.toolbarSalesforceSearchTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.SEARCH);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceSendEmailTitle(),
                        localizationConstant.toolbarSalesforceSendEmailTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.SEND_EMAIL);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceSendEmailMessageTitle(),
                        localizationConstant.toolbarSalesforceSendEmailMessageTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.SEND_EMAIL_MESSAGE);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceSetPasswordTitle(),
                        localizationConstant.toolbarSalesforceSetPasswordTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.SET_PASSWORD);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceUndeleteTitle(),
                        localizationConstant.toolbarSalesforceUndeleteTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.UNDELETE);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceUpdateConnectorTitle(),
                        localizationConstant.toolbarSalesforceUpdateConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.UPDATE);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceUpsetTitle(),
                        localizationConstant.toolbarSalesforceUpsetTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.UPSET);

        toolbar.addGroup(ToolbarGroupIds.JIRA_CONNECTORS, localizationConstant.toolbarGroupJiraConnector());

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraAddAttachmentToIssueIdTitle(),
                        localizationConstant.toolbarJiraAddAttachmentToIssueIdTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.ADD_ATTACHMENT_TO_ISSUE_ID);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraCreateFilterTitle(),
                        localizationConstant.toolbarJiraCreateFilterTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.CREATE_FILTER);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraCreateIssueTitle(),
                        localizationConstant.toolbarJiraCreateIssueTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.CREATE_ISSUE);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraDeleteAvatarForProjectTitle(),
                        localizationConstant.toolbarJiraDeleteAvatarForProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.DELETE_AVATAR_FOR_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraDeleteCommentTitle(),
                        localizationConstant.toolbarJiraDeleteCommentTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.DELETE_COMMENT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraDeleteFilterTitle(),
                        localizationConstant.toolbarJiraDeleteFilterTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.DELETE_FILTER);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetDashboardTitle(),
                        localizationConstant.toolbarJiraGetDashboardTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_DASHBOARD);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraDoTransitionTitle(),
                        localizationConstant.toolbarJiraDoTransitionTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.DO_TRANSITION);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetAvatarsForProjectTitle(),
                        localizationConstant.toolbarJiraGetAvatarsForProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_AVATARS_FOR_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetCommentsTitle(),
                        localizationConstant.toolbarJiraGetCommentsTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_COMMENTS);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetComponentsOfProjectTitle(),
                        localizationConstant.toolbarJiraGetComponentsOfProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_COMPONENTS_OF_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetDashboardByIdTitle(),
                        localizationConstant.toolbarJiraGetDashboardByIdTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_DASHBOARD_BY_ID);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetFavouriteFiltersTitle(),
                        localizationConstant.toolbarJiraGetFavouriteFiltersTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_FAVOURITE_FILTERS);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetFilterByIdTitle(),
                        localizationConstant.toolbarJiraGetFilterByIdTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_FILTER_BY_ID);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetGroupTitle(),
                        localizationConstant.toolbarJiraGetGroupTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_GROUP);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetIssueTitle(),
                        localizationConstant.toolbarJiraGetIssueTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_ISSUE);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetIssuePrioritiesTitle(),
                        localizationConstant.toolbarJiraGetIssuePrioritiesTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_ISSUE_PRIORITIES);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetIssuePriorityByIdTitle(),
                        localizationConstant.toolbarJiraGetIssuePriorityByIdTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_ISSUE_PRIORITY_BY_ID);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetIssueTypeByIdTitle(),
                        localizationConstant.toolbarJiraGetIssueTypeByIdTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_ISSUE_TYPE_BY_ID);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetIssueTypesTitle(),
                        localizationConstant.toolbarJiraGetIssueTypesTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_ISSUE_TYPES);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetIssuesForUserTitle(),
                        localizationConstant.toolbarJiraGetIssuesForUserTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_ISSUES_FOR_USER);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetProjectTitle(),
                        localizationConstant.toolbarJiraGetProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetRolesByIdOfProjectTitle(),
                        localizationConstant.toolbarJiraGetRolesByIdOfProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_ROLES_BY_ID_OF_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetRolesOfProjectTitle(),
                        localizationConstant.toolbarJiraGetRolesOfProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_ROLES_OF_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetStatusesOfProjectTitle(),
                        localizationConstant.toolbarJiraGetStatusesOfProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_STATUSES_OF_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetTransitionsTitle(),
                        localizationConstant.toolbarJiraGetTransitionsTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_TRANSITIONS);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetUserTitle(),
                        localizationConstant.toolbarJiraGetUserTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_USER);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetUserAssignableProjectsTitle(),
                        localizationConstant.toolbarJiraGetUserAssignableProjectsTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_USER_ASSIGNABLE_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetUserPermissionsTitle(),
                        localizationConstant.toolbarJiraGetUserPermissionsTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_USER_PERMISSIONS);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetVersionsOfProjectTitle(),
                        localizationConstant.toolbarJiraGetVersionsOfProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_VERSIONS_OF_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetVotesForIssueTitle(),
                        localizationConstant.toolbarJiraGetVotesForIssueTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_VOTES_FOR_ISSUE);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraInitTitle(),
                        localizationConstant.toolbarJiraInitTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.INIT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraPostCommentTitle(),
                        localizationConstant.toolbarJiraPostCommentTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.POST_COMMENT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraSearchAssignableUserTitle(),
                        localizationConstant.toolbarJiraSearchAssignableUserTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.SEARCH_ASSIGNABLE_USER);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraSearchAssignableUserMultiProjectTitle(),
                        localizationConstant.toolbarJiraSearchAssignableUserMultiProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.SEARCH_ASSIGNABLE_USER_MULTI_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraSearchIssueViewableUsersTitle(),
                        localizationConstant.toolbarJiraSearchIssueViewableUsersTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.SEARCH_ISSUE_VIEWABLE_USERS);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraSearchJiraTitle(),
                        localizationConstant.toolbarJiraSearchJiraTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.SEARCH_JIRA);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraSearchUserTitle(),
                        localizationConstant.toolbarJiraSearchUserTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.SEARCH_USER);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraSetActorsToRoleOfProjectTitle(),
                        localizationConstant.toolbarJiraSetActorsToRoleOfProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.SET_ACTORS_TO_ROLE_OF_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraUpdateCommentTitle(),
                        localizationConstant.toolbarJiraUpdateCommentTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.ADD_ATTACHMENT_TO_ISSUE_ID);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraUpdateCommentTitle(),
                        localizationConstant.toolbarJiraUpdateCommentTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.UPDATE_COMMENT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraUpdateFilterByIdTitle(),
                        localizationConstant.toolbarJiraUpdateFilterByIdTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.UPDATE_FILTER_BY_ID_JIRA);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraUpdateIssueTitle(),
                        localizationConstant.toolbarJiraUpdateIssueTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.UPDATE_ISSUE);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraUpdateIssueAssigneeTitle(),
                        localizationConstant.toolbarJiraUpdateIssueAssigneeTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.UPDATE_ISSUE_ASSIGNEE);

        toolbar.addGroup(ToolbarGroupIds.TWITTER_CONNECTORS, localizationConstant.toolbarGroupTwitterConnector());

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterDestroyStatusTitle(),
                        localizationConstant.toolbarTwitterDestroyStatusTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.DESTROY_STATUS);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterGetClosesTrendsTitle(),
                        localizationConstant.toolbarTwitterGetClosesTrendsTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_CLOTHES_TRENDS);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterGetDirectMessagesTitle(),
                        localizationConstant.toolbarTwitterGetDirectMessagesTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_DIRECT_MESSAGES);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterGetFollowersTitle(),
                        localizationConstant.toolbarTwitterGetFollowersTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_FOLLOWERS);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterGetFollowersIdsTitle(),
                        localizationConstant.toolbarTwitterGetFollowersIdsTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_FOLLOWERS_IDS);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterGetFriendsTitle(),
                        localizationConstant.toolbarTwitterGetFriendsTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_FRIENDS);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterGetFriendsIdsTitle(),
                        localizationConstant.toolbarTwitterGetFriendsIdsTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_FRIENDS_IDS);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterGetHomeTimeLineTitle(),
                        localizationConstant.toolbarTwitterGetHomeTimeLineTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_HOME_TIME_LINE);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterGetMentionsTimeLineTitle(),
                        localizationConstant.toolbarTwitterGetMentionsTimeLineTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_MENTIONS_TIME_LINE);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterGetRetweetsOfMineTitle(),
                        localizationConstant.toolbarTwitterGetRetweetsOfMineTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_RETWEETS_OF_MINE);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterGetSentDirectMessageTitle(),
                        localizationConstant.toolbarTwitterGetSentDirectMessageTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_SENT_DIRECT_MESSAGE);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterGetTopTrendPlacesTitle(),
                        localizationConstant.toolbarTwitterGetTopTrendPlacesTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_TOP_TREND_PLACES);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterGetUserTimeLineTitle(),
                        localizationConstant.toolbarTwitterGetUserTimeLineTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_USER_TIME_LINE);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterInitTitle(),
                        localizationConstant.toolbarTwitterInitTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.INIT);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterRetweetTitle(),
                        localizationConstant.toolbarTwitterRetweetTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.RETWEET);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterSearchTitle(),
                        localizationConstant.toolbarTwitterSearchTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.SEARCH);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterSearchPlacesTitle(),
                        localizationConstant.toolbarTwitterSearchPlacesTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.SEARCH_PLACES);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterSendDirectMessageTitle(),
                        localizationConstant.toolbarTwitterSendDirectMessageTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.SEND_DIRECT_MESSAGE);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterShowStatusTitle(),
                        localizationConstant.toolbarTwitterShowStatusTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.SHOW_STATUS);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterUpdateStatusTitle(),
                        localizationConstant.toolbarTwitterUpdateStatusTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.UPDATE_STATUS);

        toolbar.addGroup(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS, localizationConstant.toolbarGroupGoogleSpreadsheetConnector());

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetCreateSpreadsheetTitle(),
                        localizationConstant.toolbarSpreadsheetCreateSpreadsheetTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.CREATING_SPREADSHEET);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetCreateWorksheetTitle(),
                        localizationConstant.toolbarSpreadsheetCreateWorksheetTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.CREATING_WORKSHEET);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetDeleteWorksheetTitle(),
                        localizationConstant.toolbarSpreadsheetDeleteWorksheetTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.DELETE_WORKSHEET);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetGetAllCellsTitle(),
                        localizationConstant.toolbarSpreadsheetGetAllCellsTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_ALL_CELLS);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetGetAllCellsCSVTitle(),
                        localizationConstant.toolbarSpreadsheetGetAllCellsCSVTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_ALL_CELLS_CSV);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetGetAllSpreadsheetsTitle(),
                        localizationConstant.toolbarSpreadsheetGetAllSpreadsheetsTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_ALL_SPREADSHEETS);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetGetAllWorksheetsTitle(),
                        localizationConstant.toolbarSpreadsheetGetAllWorksheetsTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_ALL_WORKSHEETS);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetGetAuthorsTitle(),
                        localizationConstant.toolbarSpreadsheetGetAuthorsTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_AUTHORS);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetGetCellRangeTitle(),
                        localizationConstant.toolbarSpreadsheetGetCellRangeTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_CELL_RANGE);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetGetCellRangeCSVTitle(),
                        localizationConstant.toolbarSpreadsheetGetCellRangeCSVTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_CELL_RANGE_CSV);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetGetColumnHeadersTitle(),
                        localizationConstant.toolbarSpreadsheetGetColumnHeadersTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_COLUMN_HEADERS);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetGetSpreadsheetByTitleTitle(),
                        localizationConstant.toolbarSpreadsheetGetSpreadsheetByTitleTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_SPREADSHEET_BY_TITLE);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetGetWorksheetByTitleTitle(),
                        localizationConstant.toolbarSpreadsheetGetWorksheetByTitleTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_WORKSHEET_BY_TITLE);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetImportCSVTitle(),
                        localizationConstant.toolbarSpreadsheetImportCSVTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.IMPORT_CSV);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetInitTitle(),
                        localizationConstant.toolbarSpreadsheetInitTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.INIT);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetPurgeWorksheetTitle(),
                        localizationConstant.toolbarSpreadsheetPurgeWorksheetTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.PURGE_WORKSHEET);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarspreadsheetSearchCellTitle(),
                        localizationConstant.toolbarSpreadsheetSearchCellTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.SEARCH_CELL);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetSetRowTitle(),
                        localizationConstant.toolbarSpreadsheetSetRowTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.SET_ROW);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetUpdateWorksheetMetaTitle(),
                        localizationConstant.toolbarSpreadsheetUpdateWorksheetMetaTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.UPDATE_WORKSHEET_METADATA);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetUsernameLoginTitle(),
                        localizationConstant.toolbarSpreadsheetUsernameLoginTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.USERNAME_LOGIN);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        toolbar.go(view.getToolbarPanel());
        rootElementPresenter.go(view.getWorkspacePanel());

        super.go(container);
    }

    /** {@inheritDoc} */
    @Override
    public void onElementChanged() {
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertyChanged() {
        notifyListeners();
    }

    public void addListener(@Nonnull EditorChangeListener listener) {
        listeners.add(listener);
    }

    public void notifyListeners() {
        for (EditorChangeListener listener : listeners) {
            listener.onChanged();
        }
    }

    /** @return a serialized text type of diagram */
    @Nonnull
    public String serialize() {
        return rootElement.serialize();
    }

    /**
     * Convert a text type of diagram to a graphical type.
     *
     * @param content
     *         content that need to be parsed
     */
    public void deserialize(@Nonnull String content) {
        rootElement.deserialize(content);
        rootElementPresenter.updateView();
    }

    /** {@inheritDoc} */
    @Override
    public void onHidePanelButtonClicked() {
        view.setVisiblePropertyPanel(false);
    }

    /** {@inheritDoc} */
    @Override
    public void onShowPropertyButtonClicked() {
        view.setVisiblePropertyPanel(true);
    }

    public interface EditorChangeListener {
        /** Performs some actions when editor was changed. */
        void onChanged();
    }
}