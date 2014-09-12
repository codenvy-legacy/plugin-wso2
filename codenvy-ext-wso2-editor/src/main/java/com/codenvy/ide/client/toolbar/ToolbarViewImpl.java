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
package com.codenvy.ide.client.toolbar;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Provides a graphical representation of tool bar.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class ToolbarViewImpl extends ToolbarView {

    @Singleton
    interface ToolbarViewImplUiBinder extends UiBinder<Widget, ToolbarViewImpl> {
    }

    @Inject
    public ToolbarViewImpl(ToolbarViewImplUiBinder ourUiBinder) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @UiHandler("log")
    public void onLogButtonClicked(ClickEvent event) {
        delegate.onLogButtonClicked();
    }

    @UiHandler("property")
    public void onPropertyButtonClicked(ClickEvent event) {
        delegate.onPropertyButtonClicked();
    }

    @UiHandler("payloadFactory")
    public void onPayloadFactoryButtonClicked(ClickEvent event) {
        delegate.onPayloadFactoryButtonClicked();
    }

    @UiHandler("send")
    public void onSendButtonClicked(ClickEvent event) {
        delegate.onSendButtonClicked();
    }

    @UiHandler("header")
    public void onHeaderButtonClicked(ClickEvent event) {
        delegate.onHeaderButtonClicked();
    }

    @UiHandler("respond")
    public void onRespondButtonClicked(ClickEvent event) {
        delegate.onRespondButtonClicked();
    }

    @UiHandler("filter")
    public void onFilterButtonClicked(ClickEvent event) {
        delegate.onFilterButtonClicked();
    }

    @UiHandler("switchMediator")
    public void onSwitch_mediatorButtonClicked(ClickEvent event) {
        delegate.onSwitchButtonClicked();
    }

    @UiHandler("sequence")
    public void onSequenceButtonClicked(ClickEvent event) {
        delegate.onSequenceButtonClicked();
    }

    @UiHandler("enrich")
    public void onEnrichButtonClicked(ClickEvent event) {
        delegate.onEnrichButtonClicked();
    }

    @UiHandler("loopBack")
    public void onLoopBackButtonClicked(ClickEvent event) {
        delegate.onLoopBackButtonClicked();
    }

    @UiHandler("callTemplate")
    public void onCallTemplateButtonClicked(ClickEvent event) {
        delegate.onCallTemplateButtonClicked();
    }

    @UiHandler("call")
    public void onCallButtonClicked(ClickEvent event) {
        delegate.onCallButtonClicked();
    }

    @UiHandler("addressEndpoint")
    public void onAddressEndpointButtonClicked(ClickEvent event) {
        delegate.onAddressEndpointButtonClicked();
    }

    @UiHandler("salesForceInitConnector")
    public void onSalesForceInitClicked(ClickEvent event) {
        delegate.onSalesForceInitClicked();
    }

    @UiHandler("salesForceCreateConnector")
    public void onSalesForceCreateClicked(ClickEvent event) {
        delegate.onSalesForceCreateClicked();
    }

    @UiHandler("salesForceDeleteConnector")
    public void onSalesForceDeleteClicked(ClickEvent event) {
        delegate.onSalesForceDeleteClicked();
    }

    @UiHandler("salesForceUpdateConnector")
    public void onSalesForceUpdateClicked(ClickEvent event) {
        delegate.onSalesForceUpdateClicked();
    }

    @UiHandler("salesForceDescribeGlobalConnector")
    public void onSalesForceDescribeGlobalClicked(ClickEvent event) {
        delegate.onSalesForceDescribeGlobalClicked();
    }

    @UiHandler("salesForceDescribeSobjectConnector")
    public void onSalesForceDescribeSubjectClicked(ClickEvent event) {
        delegate.onSalesForceDescribeSubjectClicked();
    }

    @UiHandler("salesForceDescribeSobjectsConnector")
    public void onSalesForceDescribeSubjectsClicked(ClickEvent event) {
        delegate.onSalesForceDescribeSubjectsClicked();
    }

    @UiHandler("salesForceQueryConnector")
    public void onSalesForceQueryClicked(ClickEvent event) {
        delegate.onSalesForceQueryClicked();
    }

    @UiHandler("salesForceQueryAllConnector")
    public void onSalesForceQueryAllClicked(ClickEvent event) {
        delegate.onSalesForceQueryAllClicked();
    }

    @UiHandler("salesForceQueryMoreConnector")
    public void onSalesForceQueryMoreClicked(ClickEvent event) {
        delegate.onSalesForceQueryMoreClicked();
    }

    @UiHandler("salesForceResetPasswordConnector")
    public void onSalesForceResetPasswordClicked(ClickEvent event) {
        delegate.onSalesForceResetPasswordClicked();
    }

    @UiHandler("salesForceRetriveConnector")
    public void onSalesForceRetriveClicked(ClickEvent event) {
        delegate.onSalesForceRetriveClicked();
    }

    @UiHandler("salesForceSearchConnector")
    public void onSalesForceSearchClicked(ClickEvent event) {
        delegate.onSalesForceSearchClicked();
    }

    @UiHandler("salesForceSendEmailConnector")
    public void onSalesForceSendEmailClicked(ClickEvent event) {
        delegate.onSalesForceSendEmailClicked();
    }

    @UiHandler("salesForceSendEmailMessageConnector")
    public void onSalesForceSendEmailMessageClicked(ClickEvent event) {
        delegate.onSalesForceSendEmailMessageClicked();
    }

    @UiHandler("salesForceSetPasswordConnector")
    public void onSalesForceSetPasswordClicked(ClickEvent event) {
        delegate.onSalesForceSetPasswordClicked();
    }

    @UiHandler("salesForceUndeleteConnector")
    public void onSalesForceUndeleteClicked(ClickEvent event) {
        delegate.onSalesForceUndeleteClicked();
    }

    @UiHandler("salesForceUpsetConnector")
    public void onSalesForceUpsetClicked(ClickEvent event) {
        delegate.onSalesForceUpsetClicked();
    }

    @UiHandler("emptyRecycleConnector")
    public void onSalesForceEmptyRecycleBinClicked(ClickEvent event) {
        delegate.onSalesForceEmptyRecycleBinClicked();
    }

    @UiHandler("logOutConnector")
    public void onSalesForceLogOutClicked(ClickEvent event) {
        delegate.onSalesForceLogOutClicked();
    }

    @UiHandler("getUserInfoConnector")
    public void onSalesForceGetUserInfoClicked(ClickEvent event) {
        delegate.onSalesForceGetUserInfoClicked();
    }

    @UiHandler("jiraAddAttachmentToIssueId")
    public void onAddAttachmentToIssueIdClicked(ClickEvent event) {
        delegate.onAddAttachmentToIssueIdClicked();
    }

    @UiHandler("jiraCreateFilter")
    public void onCreateFilterClicked(ClickEvent event) {
        delegate.onCreateFilterClicked();
    }

    @UiHandler("jiraCreateIssue")
    public void onCreateIssueClicked(ClickEvent event) {
        delegate.onCreateIssueClicked();
    }

    @UiHandler("jiraDeleteAvatarForProject")
    public void onDeleteAvatarForProjectClicked(ClickEvent event) {
        delegate.onDeleteAvatarForProjectClicked();
    }

    @UiHandler("jiraDeleteComment")
    public void onDeleteCommentClicked(ClickEvent event) {
        delegate.onDeleteCommentClicked();
    }

    @UiHandler("jiraDeleteFilter")
    public void onDeleteFilterClicked(ClickEvent event) {
        delegate.onDeleteFilterClicked();
    }

    @UiHandler("jiraGetDashboard")
    public void onGetDashboardClicked(ClickEvent event) {
        delegate.onGetDashboardClicked();
    }

    @UiHandler("jiraDoTransition")
    public void onDoTransitionClicked(ClickEvent event) {
        delegate.onDoTransitionClicked();
    }

    @UiHandler("jiraGetAvatarsForProject")
    public void onGetAvatarsForProjectClicked(ClickEvent event) {
        delegate.onGetAvatarsForProjectClicked();
    }

    @UiHandler("jiraGetComments")
    public void onGetCommentsClicked(ClickEvent event) {
        delegate.onGetCommentsClicked();
    }

    @UiHandler("jiraGetComponentsOfProject")
    public void onGetComponentsOfProjectClicked(ClickEvent event) {
        delegate.onGetComponentsOfProjectClicked();
    }

    @UiHandler("jiraGetDashboardById")
    public void onGetDashboardByIdClicked(ClickEvent event) {
        delegate.onGetDashboardByIdClicked();
    }

    @UiHandler("jiraGetFavouriteFilters")
    public void onGetFavouritesFilterClicked(ClickEvent event) {
        delegate.onGetFavouritesFilterClicked();
    }

    @UiHandler("jiraGetFilterById")
    public void onGetFilterByIdClicked(ClickEvent event) {
        delegate.onGetFilterByIdClicked();
    }

    @UiHandler("jiraGetGroup")
    public void onGetGroupClicked(ClickEvent event) {
        delegate.onGetGroupClicked();
    }

    @UiHandler("jiraGetIssue")
    public void onGetIssueClicked(ClickEvent event) {
        delegate.onGetIssueClicked();
    }

    @UiHandler("jiraGetIssuePriorities")
    public void onGetIssuePriorityesClicked(ClickEvent event) {
        delegate.onGetIssuePriorityesClicked();
    }

    @UiHandler("jiraGetIssuePriorityById")
    public void onGetIssuePriorityByIdClicked(ClickEvent event) {
        delegate.onGetIssuePriorityByIdClicked();
    }

    @UiHandler("jiraGetIssueTypeById")
    public void onGetIssueTypeByIdClicked(ClickEvent event) {
        delegate.onGetIssueTypeByIdClicked();
    }

    @UiHandler("jiraGetIssueTypes")
    public void onGetIssueTypesClicked(ClickEvent event) {
        delegate.onGetIssueTypesClicked();
    }

    @UiHandler("jiraGetIssuesForUser")
    public void onGetIssuesForUserClicked(ClickEvent event) {
        delegate.onGetIssuesForUserClicked();
    }

    @UiHandler("jiraGetProject")
    public void onGetProjectClicked(ClickEvent event) {
        delegate.onGetProjectClicked();
    }

    @UiHandler("jiraGetRolesByIdOfProject")
    public void onGetRolesByIdOfProjectClicked(ClickEvent event) {
        delegate.onGetRolesByIdOfProjectClicked();
    }

    @UiHandler("jiraGetRolesOfProject")
    public void onGetRolesOfProjectClicked(ClickEvent event) {
        delegate.onGetRolesOfProjectClicked();
    }

    @UiHandler("jiraGetStatusesOfProject")
    public void onGetStatusesOfProjectClicked(ClickEvent event) {
        delegate.onGetStatusesOfProjectClicked();
    }

    @UiHandler("jiraGetTransitions")
    public void onGetTransitionsClicked(ClickEvent event) {
        delegate.onGetTransitionsClicked();
    }

    @UiHandler("jiraGetUser")
    public void onGetUserClicked(ClickEvent event) {
        delegate.onGetUserClicked();
    }

    @UiHandler("jiraGetUserAssignableProjects")
    public void onGetUserAssignableProjectClicked(ClickEvent event) {
        delegate.onGetUserAssignableProjectClicked();
    }

    @UiHandler("jiraGetUserPermissions")
    public void onGetUserPermissionsClicked(ClickEvent event) {
        delegate.onGetUserPermissionsClicked();
    }

    @UiHandler("jiraGetVersionsOfProject")
    public void onGetVersionsOfProjectClicked(ClickEvent event) {
        delegate.onGetVersionsOfProjectClicked();
    }

    @UiHandler("jiraGetVotesForIssue")
    public void onGetVotesForIssueClicked(ClickEvent event) {
        delegate.onGetVotesForIssueClicked();
    }

    @UiHandler("jiraInit")
    public void onInitClicked(ClickEvent event) {
        delegate.onInitClicked();
    }

    @UiHandler("jiraPostComment")
    public void onPostCommentClicked(ClickEvent event) {
        delegate.onPostCommentClicked();
    }

    @UiHandler("jiraSearchAssignableUser")
    public void onSearchAssignableUserClicked(ClickEvent event) {
        delegate.onSearchAssignableUserClicked();
    }

    @UiHandler("jiraSearchAssignableUserMultiProject")
    public void onSearchAssignableUserMultiProjectClicked(ClickEvent event) {
        delegate.onSearchAssignableUserMultiProjectClicked();
    }

    @UiHandler("jiraSearchIssueViewableUsers")
    public void onSearchIssueViewableUsersClicked(ClickEvent event) {
        delegate.onSearchIssueViewableUsersClicked();
    }

    @UiHandler("jiraSearchJira")
    public void onSearchJiraClicked(ClickEvent event) {
        delegate.onSearchJiraClicked();
    }

    @UiHandler("jiraSearchUser")
    public void onSearchUserClicked(ClickEvent event) {
        delegate.onSearchUserClicked();
    }

    @UiHandler("jiraSetActorsToRoleOfProject")
    public void onSetActorsToRoleOfProjectClicked(ClickEvent event) {
        delegate.onSetActorsToRoleOfProjectClicked();
    }

    @UiHandler("jiraUpdateComment")
    public void onUpdateCommentClicked(ClickEvent event) {
        delegate.onUpdateCommentClicked();
    }

    @UiHandler("jiraUpdateFilterById")
    public void onUpdateFilterByIdClicked(ClickEvent event) {
        delegate.onUpdateFilterByIdClicked();
    }

    @UiHandler("jiraUpdateIssue")
    public void onUpdateIssueClicked(ClickEvent event) {
        delegate.onUpdateIssueClicked();
    }

    @UiHandler("jiraUpdateIssueAssignee")
    public void onUpdateIssueAssigneeClicked(ClickEvent event) {
        delegate.onUpdateIssueAssigneeClicked();
    }

    @UiHandler("spreadsheetCreateSpreadsheet")
    public void onCreateSpreadsheetClicked(ClickEvent event) {
        delegate.onCreateSpreadsheetClicked();
    }

    @UiHandler("spreadsheetCreateWorksheet")
    public void onSpreadsheetCreateWorksheetClicked(ClickEvent event) {
        delegate.onSpreadsheetCreateWorksheetClicked();
    }

    @UiHandler("spreadsheetDeleteWorksheet")
    public void onSpreadsheetDeleteWorksheetClicked(ClickEvent event) {
        delegate.onSpreadsheetDeleteWorksheetClicked();
    }

    @UiHandler("spreadsheetGetAllCellsCSV")
    public void onSpreadsheetGetAllCellsCSVClicked(ClickEvent event) {
        delegate.onSpreadsheetGetAllCellsCSVClicked();
    }

    @UiHandler("spreadsheetGetAllCells")
    public void onSpreadsheetGetAllCellsClicked(ClickEvent event) {
        delegate.onSpreadsheetGetAllCellsClicked();
    }

    @UiHandler("spreadsheetGetAllWorksheets")
    public void onSpreadsheetGetAllWorksheetsClicked(ClickEvent event) {
        delegate.onSpreadsheetGetAllWorksheetsClicked();
    }

    @UiHandler("spreadsheetGetAllSpreadsheets")
    public void onSpreadsheetGetAllSpreadsheetsClicked(ClickEvent event) {
        delegate.onSpreadsheetGetAllSpreadsheetsClicked();
    }

    @UiHandler("spreadsheetGetAuthors")
    public void onSpreadsheetGetAuthorsClicked(ClickEvent event) {
        delegate.onSpreadsheetGetAuthorsClicked();
    }

    @UiHandler("spreadsheetGetCellRange")
    public void onSpreadsheetGetCellRangeClicked(ClickEvent event) {
        delegate.onSpreadsheetGetCellRangeClicked();
    }

    @UiHandler("spreadsheetGetCellRangeCSV")
    public void onSpreadsheetGetCellRangeCSVClicked(ClickEvent event) {
        delegate.onSpreadsheetGetCellRangeCSVClicked();
    }

    @UiHandler("spreadsheetGetColumnHeaders")
    public void onSpreadsheetGetColumnHeadersClicked(ClickEvent event) {
        delegate.onSpreadsheetGetColumnHeadersClicked();
    }

    @UiHandler("spreadsheetGetSpreadsheetByTitle")
    public void onSpreadsheetGetSpreadsheetByTitleClicked(ClickEvent event) {
        delegate.onSpreadsheetGetSpreadsheetByTitleClicked();
    }

    @UiHandler("spreadsheetGetWorksheetByTitle")
    public void onSpreadsheetGetWorksheetByTitleClicked(ClickEvent event) {
        delegate.onSpreadsheetGetWorksheetByTitleClicked();
    }

    @UiHandler("spreadsheetImportCSV")
    public void onSpreadsheetImportCSVClicked(ClickEvent event) {
        delegate.onSpreadsheetImportCSVClicked();
    }

    @UiHandler("spreadsheetInit")
    public void onSpreadsheetInitClicked(ClickEvent event) {
        delegate.onSpreadsheetInitClicked();
    }

    @UiHandler("spreadsheetPurgeWorksheet")
    public void onSpreadsheetPurgeWorksheetClicked(ClickEvent event) {
        delegate.onSpreadsheetPurgeWorksheetClicked();
    }

    @UiHandler("spreadsheetSearchCell")
    public void onSpreadsheetSearchCellClicked(ClickEvent event) {
        delegate.onSpreadsheetSearchCellClicked();
    }

    @UiHandler("spreadsheetSetRow")
    public void onSpreadsheetSetRowClicked(ClickEvent event) {
        delegate.onSpreadsheetSetRowClicked();
    }

    @UiHandler("spreadsheetUpdateWorksheetMeta")
    public void onSpreadsheetUpdateWorksheetClicked(ClickEvent event) {
        delegate.onSpreadsheetUpdateWorksheetClicked();
    }

    @UiHandler("spreadsheetUsernameLogin")
    public void onSpreadsheetUsernameLoginClicked(ClickEvent event) {
        delegate.onSpreadsheetUsernameLoginClicked();
    }

}