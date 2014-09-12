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

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

/**
 * The abstract view's representation of tool bar. It provides an ability to show all elements which tool bar contains.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
@ImplementedBy(ToolbarViewImpl.class)
public abstract class ToolbarView extends AbstractView<ToolbarView.ActionDelegate> {

    /**
     * Interface defines methods of {@link ToolbarPresenter} which calls from view. These methods defines
     * some actions when user click on an icon of element.
     */
    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /** Performs some actions in response to a user clicked on Log icon of tool bar. */
        void onLogButtonClicked();

        /** Performs some actions in response to a user clicked on Property icon of tool bar. */
        void onPropertyButtonClicked();

        /** Performs some actions in response to a user clicked on Payload icon of tool bar. */
        void onPayloadFactoryButtonClicked();

        /** Performs some actions in response to a user clicked on Send icon of tool bar. */
        void onSendButtonClicked();

        /** Performs some actions in response to a user clicked on Header icon of tool bar. */
        void onHeaderButtonClicked();

        /** Performs some actions in response to a user clicked on Respond icon of tool bar. */
        void onRespondButtonClicked();

        /** Performs some actions in response to a user clicked on Filter icon of tool bar. */
        void onFilterButtonClicked();

        /** Performs some actions in response to a user clicked on Switch icon of tool bar. */
        void onSwitchButtonClicked();

        /** Performs some actions in response to a user clicked on Sequence icon of tool bar. */
        void onSequenceButtonClicked();

        /** Performs some actions in response to a user clicked on Enrich icon of tool bar. */
        void onEnrichButtonClicked();

        /** Performs some actions in response to a user clicked on LoopBack icon of tool bar. */
        void onLoopBackButtonClicked();

        /** Performs some actions in response to a user clicked on CallTemplate icon of tool bar. */
        void onCallTemplateButtonClicked();

        /** Performs some actions in response to a user clicked on Call icon of tool bar. */
        void onCallButtonClicked();

        /** Performs some actions in response to a user clicked on Address endpoint icon of tool bar. */
        void onAddressEndpointButtonClicked();

        /** Performs some actions in response to a user clicked on SalesForce init icon of tool bar. */
        void onSalesForceInitClicked();

        /** Performs some actions in response to a user clicked on SalesForce create icon of tool bar. */
        void onSalesForceCreateClicked();

        /** Performs some actions in response to a user clicked on SalesForce delete icon of tool bar. */
        void onSalesForceDeleteClicked();

        /** Performs some actions in response to a user clicked on SalesForce update icon of tool bar. */
        void onSalesForceUpdateClicked();

        /** Performs some actions in response to a user clicked on SalesForce update icon of tool bar. */
        void onSalesForceDescribeGlobalClicked();

        /** Performs some actions in response to a user clicked on SalesForce update icon of tool bar. */
        void onSalesForceDescribeSubjectClicked();

        /** Performs some actions in response to a user clicked on SalesForce update icon of tool bar. */
        void onSalesForceDescribeSubjectsClicked();

        /** Performs some actions in response to a user clicked on SalesForce empty recycle bin icon of tool bar. */
        void onSalesForceEmptyRecycleBinClicked();

        /** Performs some actions in response to a user clicked on SalesForce logout icon of tool bar. */
        void onSalesForceLogOutClicked();

        /** Performs some actions in response to a user clicked on SalesForce get user information icon of tool bar. */
        void onSalesForceGetUserInfoClicked();

        /** Performs some actions in response to a user clicked on SalesForce query icon of tool bar. */
        void onSalesForceQueryClicked();

        /** Performs some actions in response to a user clicked on SalesForce query all icon of tool bar. */
        void onSalesForceQueryAllClicked();

        /** Performs some actions in response to a user clicked on SalesForce query more icon of tool bar. */
        void onSalesForceQueryMoreClicked();

        /** Performs some actions in response to a user clicked on SalesForce reset password icon of tool bar. */
        void onSalesForceResetPasswordClicked();

        /** Performs some actions in response to a user clicked on SalesForce retrive icon of tool bar. */
        void onSalesForceRetriveClicked();

        /** Performs some actions in response to a user clicked on SalesForce search icon of tool bar. */
        void onSalesForceSearchClicked();

        /** Performs some actions in response to a user clicked on SalesForce send email icon of tool bar. */
        void onSalesForceSendEmailClicked();

        /** Performs some actions in response to a user clicked on SalesForce send email message icon of tool bar. */
        void onSalesForceSendEmailMessageClicked();

        /** Performs some actions in response to a user clicked on SalesForce set password icon of tool bar. */
        void onSalesForceSetPasswordClicked();

        /** Performs some actions in response to a user clicked on SalesForce undelete icon of tool bar. */
        void onSalesForceUndeleteClicked();

        /** Performs some actions in response to a user clicked on SalesForce upset icon of tool bar. */
        void onSalesForceUpsetClicked();

        /** Performs some actions in response to a user clicked on jira add attachment to issue id icon of tool bar. */
        void onAddAttachmentToIssueIdClicked();

        /** Performs some actions in response to a user clicked on jira create filter icon of tool bar. */
        void onCreateFilterClicked();

        /** Performs some actions in response to a user clicked on jira create issue icon of tool bar. */
        void onCreateIssueClicked();

        /** Performs some actions in response to a user clicked on jira delete avatar for project icon of tool bar. */
        void onDeleteAvatarForProjectClicked();

        /** Performs some actions in response to a user clicked on jira delete comment icon of tool bar. */
        void onDeleteCommentClicked();

        /** Performs some actions in response to a user clicked on jira delete filter icon of tool bar. */
        void onDeleteFilterClicked();

        /** Performs some actions in response to a user clicked on jira get dashboard icon of tool bar. */
        void onGetDashboardClicked();

        /** Performs some actions in response to a user clicked on jira do transition icon of tool bar. */
        void onDoTransitionClicked();

        /** Performs some actions in response to a user clicked on jira get avatars for project icon of tool bar. */
        void onGetAvatarsForProjectClicked();

        /** Performs some actions in response to a user clicked on jira get comments icon of tool bar. */
        void onGetCommentsClicked();

        /** Performs some actions in response to a user clicked on jira get components of project icon of tool bar. */
        void onGetComponentsOfProjectClicked();

        /** Performs some actions in response to a user clicked on jira get dashboard icon of tool bar. */
        void onGetDashboardByIdClicked();

        /** Performs some actions in response to a user clicked on jira get favourites filter icon of tool bar. */
        void onGetFavouritesFilterClicked();

        /** Performs some actions in response to a user clicked on jira get filter by id icon of tool bar. */
        void onGetFilterByIdClicked();

        /** Performs some actions in response to a user clicked on jira get group icon of tool bar. */
        void onGetGroupClicked();

        /** Performs some actions in response to a user clicked on jira get issue icon of tool bar. */
        void onGetIssueClicked();

        /** Performs some actions in response to a user clicked on jira get issue priorities icon of tool bar. */
        void onGetIssuePriorityesClicked();

        /** Performs some actions in response to a user clicked on jira get issue priority by id icon of tool bar. */
        void onGetIssuePriorityByIdClicked();

        /** Performs some actions in response to a user clicked on jira get issue type by id icon of tool bar. */
        void onGetIssueTypeByIdClicked();

        /** Performs some actions in response to a user clicked on jira get issue types icon of tool bar. */
        void onGetIssueTypesClicked();

        /** Performs some actions in response to a user clicked on jira get issues for user icon of tool bar. */
        void onGetIssuesForUserClicked();

        /** Performs some actions in response to a user clicked on jira get project icon of tool bar. */
        void onGetProjectClicked();

        /** Performs some actions in response to a user clicked on jira get roles by id of project icon of tool bar. */
        void onGetRolesByIdOfProjectClicked();

        /** Performs some actions in response to a user clicked on jira get roles of project icon of tool bar. */
        void onGetRolesOfProjectClicked();

        /** Performs some actions in response to a user clicked on jira get statuses of project icon of tool bar. */
        void onGetStatusesOfProjectClicked();

        /** Performs some actions in response to a user clicked on jira get transitions icon of tool bar. */
        void onGetTransitionsClicked();

        /** Performs some actions in response to a user clicked on jira get user icon of tool bar. */
        void onGetUserClicked();

        /** Performs some actions in response to a user clicked on jira get user assignable project icon of tool bar. */
        void onGetUserAssignableProjectClicked();

        /** Performs some actions in response to a user clicked on jira get user permissions icon of tool bar. */
        void onGetUserPermissionsClicked();

        /** Performs some actions in response to a user clicked on jira get versions of project icon of tool bar. */
        void onGetVersionsOfProjectClicked();

        /** Performs some actions in response to a user clicked on jira get votes for issue icon of tool bar. */
        void onGetVotesForIssueClicked();

        /** Performs some actions in response to a user clicked on jira init icon of tool bar. */
        void onInitClicked();

        /** Performs some actions in response to a user clicked on jira post comment icon of tool bar. */
        void onPostCommentClicked();

        /** Performs some actions in response to a user clicked on jira search assignable user icon of tool bar. */
        void onSearchAssignableUserClicked();

        /** Performs some actions in response to a user clicked on jira search assignable user multi project icon of tool bar. */
        void onSearchAssignableUserMultiProjectClicked();

        /** Performs some actions in response to a user clicked on jira search issue viewable users icon of tool bar. */
        void onSearchIssueViewableUsersClicked();

        /** Performs some actions in response to a user clicked on jira search jira icon of tool bar. */
        void onSearchJiraClicked();

        /** Performs some actions in response to a user clicked on jira search user icon of tool bar. */
        void onSearchUserClicked();

        /** Performs some actions in response to a user clicked on jira set actors to role of project icon of tool bar. */
        void onSetActorsToRoleOfProjectClicked();

        /** Performs some actions in response to a user clicked on jira update comment icon of tool bar. */
        void onUpdateCommentClicked();

        /** Performs some actions in response to a user clicked on jira update filter icon of tool bar. */
        void onUpdateFilterByIdClicked();

        /** Performs some actions in response to a user clicked on jira update issue icon of tool bar. */
        void onUpdateIssueClicked();

        /** Performs some actions in response to a user clicked on jira update issue assignable icon of tool bar. */
        void onUpdateIssueAssigneeClicked();

        /** Performs some actions in response to a user clicked on create spreadsheet icon of tool bar. */
        void onCreateSpreadsheetClicked();

        /** Performs some actions in response to a user clicked on create worksheet icon of tool bar. */
        void onSpreadsheetCreateWorksheetClicked();

        /** Performs some actions in response to a user clicked on delete worksheet icon of tool bar. */
        void onSpreadsheetDeleteWorksheetClicked();

        /** Performs some actions in response to a user clicked on get all cells icon of tool bar. */
        void onSpreadsheetGetAllCellsClicked();

        /** Performs some actions in response to a user clicked on get all cells CSV icon of tool bar. */
        void onSpreadsheetGetAllCellsCSVClicked();

        /** Performs some actions in response to a user clicked on get all spreadsheets icon of tool bar. */
        void onSpreadsheetGetAllSpreadsheetsClicked();

        /** Performs some actions in response to a user clicked on get all worksheets icon of tool bar. */
        void onSpreadsheetGetAllWorksheetsClicked();

        /** Performs some actions in response to a user clicked on get authors icon of tool bar. */
        void onSpreadsheetGetAuthorsClicked();

        /** Performs some actions in response to a user clicked on get cell range icon of tool bar. */
        void onSpreadsheetGetCellRangeClicked();

        /** Performs some actions in response to a user clicked on get cell range CSV icon of tool bar. */
        void onSpreadsheetGetCellRangeCSVClicked();

        /** Performs some actions in response to a user clicked on get column headers icon of tool bar. */
        void onSpreadsheetGetColumnHeadersClicked();

        /** Performs some actions in response to a user clicked on get spreadsheet by title icon of tool bar. */
        void onSpreadsheetGetSpreadsheetByTitleClicked();

        /** Performs some actions in response to a user clicked on spreadsheet get worksheet by title icon of tool bar. */
        void onSpreadsheetGetWorksheetByTitleClicked();

        /** Performs some actions in response to a user clicked on spreadsheet import CSV icon of tool bar. */
        void onSpreadsheetImportCSVClicked();

        /** Performs some actions in response to a user clicked on spreadsheet init icon of tool bar. */
        void onSpreadsheetInitClicked();

        /** Performs some actions in response to a user clicked on spreadsheet purge worksheet icon of tool bar. */
        void onSpreadsheetPurgeWorksheetClicked();

        /** Performs some actions in response to a user clicked on spreadsheet search cell icon of tool bar. */
        void onSpreadsheetSearchCellClicked();

        /** Performs some actions in response to a user clicked on spreadsheet set row icon of tool bar. */
        void onSpreadsheetSetRowClicked();

        /** Performs some actions in response to a user clicked on update worksheet icon of tool bar. */
        void onSpreadsheetUpdateWorksheetClicked();

        /** Performs some actions in response to a user clicked on spreadsheet username login icon of tool bar. */
        void onSpreadsheetUsernameLoginClicked();
    }

}