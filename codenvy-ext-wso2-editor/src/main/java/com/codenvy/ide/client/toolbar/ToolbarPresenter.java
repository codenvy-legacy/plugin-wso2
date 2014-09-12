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

import com.codenvy.ide.client.EditorState;
import com.codenvy.ide.client.HasState;
import com.codenvy.ide.client.State;
import com.codenvy.ide.client.mvp.AbstractPresenter;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.State.CREATING_ADDRESS_ENDPOINT;
import static com.codenvy.ide.client.State.CREATING_CALL;
import static com.codenvy.ide.client.State.CREATING_CALLTEMPLATE;
import static com.codenvy.ide.client.State.CREATING_ENRICH;
import static com.codenvy.ide.client.State.CREATING_FILTER;
import static com.codenvy.ide.client.State.CREATING_HEADER;
import static com.codenvy.ide.client.State.CREATING_JIRA_ADD_ATTACHMENT_TO_ISSUE_ID;
import static com.codenvy.ide.client.State.CREATING_JIRA_CREATE_FILTER;
import static com.codenvy.ide.client.State.CREATING_JIRA_CREATE_ISSUE;
import static com.codenvy.ide.client.State.CREATING_JIRA_DELETE_AVATAR_FOR_PROJECT;
import static com.codenvy.ide.client.State.CREATING_JIRA_DELETE_COMMENT;
import static com.codenvy.ide.client.State.CREATING_JIRA_DELETE_FILTER;
import static com.codenvy.ide.client.State.CREATING_JIRA_DO_TRANSITION;
import static com.codenvy.ide.client.State.CREATING_JIRA_GET_AVATARS_FOR_PROJECT;
import static com.codenvy.ide.client.State.CREATING_JIRA_GET_COMMENTS;
import static com.codenvy.ide.client.State.CREATING_JIRA_GET_COMPONENTS_OF_PROJECT;
import static com.codenvy.ide.client.State.CREATING_JIRA_GET_DASHBOARD;
import static com.codenvy.ide.client.State.CREATING_JIRA_GET_DASHBOARD_BY_ID;
import static com.codenvy.ide.client.State.CREATING_JIRA_GET_FAVOURITE_FILTERS;
import static com.codenvy.ide.client.State.CREATING_JIRA_GET_FILTER_BY_ID;
import static com.codenvy.ide.client.State.CREATING_JIRA_GET_GROUP;
import static com.codenvy.ide.client.State.CREATING_JIRA_GET_ISSUE;
import static com.codenvy.ide.client.State.CREATING_JIRA_GET_ISSUES_FOR_USER;
import static com.codenvy.ide.client.State.CREATING_JIRA_GET_ISSUE_PRIORITIES;
import static com.codenvy.ide.client.State.CREATING_JIRA_GET_ISSUE_PRIORITY_BY_ID;
import static com.codenvy.ide.client.State.CREATING_JIRA_GET_ISSUE_TYPES;
import static com.codenvy.ide.client.State.CREATING_JIRA_GET_ISSUE_TYPE_BY_ID;
import static com.codenvy.ide.client.State.CREATING_JIRA_GET_PROJECT;
import static com.codenvy.ide.client.State.CREATING_JIRA_GET_ROLES_BY_ID_OF_PROJECT;
import static com.codenvy.ide.client.State.CREATING_JIRA_GET_ROLES_OF_PROJECT;
import static com.codenvy.ide.client.State.CREATING_JIRA_GET_STATUSES_OF_PROJECT;
import static com.codenvy.ide.client.State.CREATING_JIRA_GET_TRANSITIONS;
import static com.codenvy.ide.client.State.CREATING_JIRA_GET_USER;
import static com.codenvy.ide.client.State.CREATING_JIRA_GET_USER_ASSIGNABLE_PROJECT;
import static com.codenvy.ide.client.State.CREATING_JIRA_GET_USER_PERMISSIONS;
import static com.codenvy.ide.client.State.CREATING_JIRA_GET_VERSIONS_OF_PROJECT;
import static com.codenvy.ide.client.State.CREATING_JIRA_GET_VOTES_FOR_ISSUE;
import static com.codenvy.ide.client.State.CREATING_JIRA_INIT;
import static com.codenvy.ide.client.State.CREATING_JIRA_POST_COMMENT;
import static com.codenvy.ide.client.State.CREATING_JIRA_SEARCH_ASSIGNABLE_USER;
import static com.codenvy.ide.client.State.CREATING_JIRA_SEARCH_ASSIGNABLE_USER_MULTI_PROJECT;
import static com.codenvy.ide.client.State.CREATING_JIRA_SEARCH_ISSUE_VIEWABLE_USERS;
import static com.codenvy.ide.client.State.CREATING_JIRA_SEARCH_JIRA;
import static com.codenvy.ide.client.State.CREATING_JIRA_SEARCH_USER;
import static com.codenvy.ide.client.State.CREATING_JIRA_SET_ACTORS_TO_ROLE_OF_PROJECT;
import static com.codenvy.ide.client.State.CREATING_JIRA_UPDATE_COMMENT;
import static com.codenvy.ide.client.State.CREATING_JIRA_UPDATE_FILTER_BY_ID;
import static com.codenvy.ide.client.State.CREATING_JIRA_UPDATE_ISSUE;
import static com.codenvy.ide.client.State.CREATING_JIRA_UPDATE_ISSUE_ASSIGNEE;
import static com.codenvy.ide.client.State.CREATING_LOG;
import static com.codenvy.ide.client.State.CREATING_LOOPBACK;
import static com.codenvy.ide.client.State.CREATING_PAYLOADFACTORY;
import static com.codenvy.ide.client.State.CREATING_PROPERTY;
import static com.codenvy.ide.client.State.CREATING_RESPOND;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_CREATE;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_DELETE;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_DESCRIBE_GLOBAL;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_DESCRIBE_SUBJECT;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_DESCRIBE_SUBJECTS;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_EMPTY_RECYCLE_BIN;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_GET_USER_INFORMATION;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_INIT;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_LOGOUT;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_QUERY;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_QUERY_MORE;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_QURY_ALL;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_RESET_PASSWORD;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_RETRIVE;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_SEARCH;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_SEND_EMAIL;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_SEND_EMAIL_MESSAGE;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_SET_PASSWORD;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_UNDELETE;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_UPDATE;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_UPSET;
import static com.codenvy.ide.client.State.CREATING_SEND;
import static com.codenvy.ide.client.State.CREATING_SEQUENCE;
import static com.codenvy.ide.client.State.CREATING_SPREADSHEET_CREATE_SPREADSHEET;
import static com.codenvy.ide.client.State.CREATING_SPREADSHEET_CREATE_WORKSHEET;
import static com.codenvy.ide.client.State.CREATING_SPREADSHEET_DELETE_WORKSHEET;
import static com.codenvy.ide.client.State.CREATING_SPREADSHEET_GET_ALL_CELLS;
import static com.codenvy.ide.client.State.CREATING_SPREADSHEET_GET_ALL_CELLS_CSV;
import static com.codenvy.ide.client.State.CREATING_SPREADSHEET_GET_ALL_SPREADSHEETS;
import static com.codenvy.ide.client.State.CREATING_SPREADSHEET_GET_ALL_WORKSHEETS;
import static com.codenvy.ide.client.State.CREATING_SPREADSHEET_GET_AUTHORS;
import static com.codenvy.ide.client.State.CREATING_SPREADSHEET_GET_CELL_RANGE;
import static com.codenvy.ide.client.State.CREATING_SPREADSHEET_GET_CELL_RANGE_CSV;
import static com.codenvy.ide.client.State.CREATING_SPREADSHEET_GET_COLUMN_HEADERS;
import static com.codenvy.ide.client.State.CREATING_SPREADSHEET_GET_SPREADSHEET_BY_TITLE;
import static com.codenvy.ide.client.State.CREATING_SPREADSHEET_GET_WORKSHEET_BY_TITLE;
import static com.codenvy.ide.client.State.CREATING_SPREADSHEET_IMPORT_CSV;
import static com.codenvy.ide.client.State.CREATING_SPREADSHEET_INIT;
import static com.codenvy.ide.client.State.CREATING_SPREADSHEET_PURGE_WORKSHEET;
import static com.codenvy.ide.client.State.CREATING_SPREADSHEET_SEARCH_CELL;
import static com.codenvy.ide.client.State.CREATING_SPREADSHEET_SET_ROW;
import static com.codenvy.ide.client.State.CREATING_SPREADSHEET_UPDATE_WORKSHEET_META;
import static com.codenvy.ide.client.State.CREATING_SPREADSHEET_USERNAME_LOGIN;
import static com.codenvy.ide.client.State.CREATING_SWITCH;

/**
 * The presenter that provides a business logic of tool bar. It provides an ability to work with all elements which it contains.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class ToolbarPresenter extends AbstractPresenter<ToolbarView> implements HasState<State>, ToolbarView.ActionDelegate {

    private EditorState<State> state;

    @Inject
    public ToolbarPresenter(ToolbarView view, @Assisted EditorState<State> editorState) {
        super(view);

        this.state = editorState;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public State getState() {
        return state.getState();
    }

    /** {@inheritDoc} */
    @Override
    public void setState(@Nonnull State state) {
        this.state.setState(state);
    }

    /** {@inheritDoc} */
    @Override
    public void onLogButtonClicked() {
        setState(CREATING_LOG);
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertyButtonClicked() {
        setState(CREATING_PROPERTY);
    }

    /** {@inheritDoc} */
    @Override
    public void onPayloadFactoryButtonClicked() {
        setState(CREATING_PAYLOADFACTORY);
    }

    /** {@inheritDoc} */
    @Override
    public void onSendButtonClicked() {
        setState(CREATING_SEND);
    }

    /** {@inheritDoc} */
    @Override
    public void onHeaderButtonClicked() {
        setState(CREATING_HEADER);
    }

    /** {@inheritDoc} */
    @Override
    public void onRespondButtonClicked() {
        setState(CREATING_RESPOND);
    }

    /** {@inheritDoc} */
    @Override
    public void onFilterButtonClicked() {
        setState(CREATING_FILTER);
    }

    /** {@inheritDoc} */
    @Override
    public void onSwitchButtonClicked() {
        setState(CREATING_SWITCH);
    }

    /** {@inheritDoc} */
    @Override
    public void onSequenceButtonClicked() {
        setState(CREATING_SEQUENCE);
    }

    /** {@inheritDoc} */
    @Override
    public void onEnrichButtonClicked() {
        setState(CREATING_ENRICH);
    }

    /** {@inheritDoc} */
    @Override
    public void onLoopBackButtonClicked() {
        setState(CREATING_LOOPBACK);
    }

    /** {@inheritDoc} */
    @Override
    public void onCallTemplateButtonClicked() {
        setState(CREATING_CALLTEMPLATE);
    }

    /** {@inheritDoc} */
    @Override
    public void onCallButtonClicked() {
        setState(CREATING_CALL);
    }

    /** {@inheritDoc} */
    @Override
    public void onAddressEndpointButtonClicked() {
        setState(CREATING_ADDRESS_ENDPOINT);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceInitClicked() {
        setState(CREATING_SALESFORCE_INIT);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceCreateClicked() {
        setState(CREATING_SALESFORCE_CREATE);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceDeleteClicked() {
        setState(CREATING_SALESFORCE_DELETE);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceUpdateClicked() {
        setState(CREATING_SALESFORCE_UPDATE);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceEmptyRecycleBinClicked() {
        setState(CREATING_SALESFORCE_EMPTY_RECYCLE_BIN);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceLogOutClicked() {
        setState(CREATING_SALESFORCE_LOGOUT);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceGetUserInfoClicked() {
        setState(CREATING_SALESFORCE_GET_USER_INFORMATION);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceQueryClicked() {
        setState(CREATING_SALESFORCE_QUERY);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceQueryAllClicked() {
        setState(CREATING_SALESFORCE_QURY_ALL);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceQueryMoreClicked() {
        setState(CREATING_SALESFORCE_QUERY_MORE);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceResetPasswordClicked() {
        setState(CREATING_SALESFORCE_RESET_PASSWORD);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceRetriveClicked() {
        setState(CREATING_SALESFORCE_RETRIVE);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceSearchClicked() {
        setState(CREATING_SALESFORCE_SEARCH);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceSendEmailClicked() {
        setState(CREATING_SALESFORCE_SEND_EMAIL);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceSendEmailMessageClicked() {
        setState(CREATING_SALESFORCE_SEND_EMAIL_MESSAGE);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceSetPasswordClicked() {
        setState(CREATING_SALESFORCE_SET_PASSWORD);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceUndeleteClicked() {
        setState(CREATING_SALESFORCE_UNDELETE);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceUpsetClicked() {
        setState(CREATING_SALESFORCE_UPSET);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceDescribeGlobalClicked() {
        setState(CREATING_SALESFORCE_DESCRIBE_GLOBAL);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceDescribeSubjectClicked() {
        setState(CREATING_SALESFORCE_DESCRIBE_SUBJECT);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceDescribeSubjectsClicked() {
        setState(CREATING_SALESFORCE_DESCRIBE_SUBJECTS);
    }

    /** {@inheritDoc} */
    @Override
    public void onAddAttachmentToIssueIdClicked() {
        setState(CREATING_JIRA_ADD_ATTACHMENT_TO_ISSUE_ID);
    }

    /** {@inheritDoc} */
    @Override
    public void onCreateFilterClicked() {
        setState(CREATING_JIRA_CREATE_FILTER);
    }

    /** {@inheritDoc} */
    @Override
    public void onCreateIssueClicked() {
        setState(CREATING_JIRA_CREATE_ISSUE);
    }

    /** {@inheritDoc} */
    @Override
    public void onDeleteAvatarForProjectClicked() {
        setState(CREATING_JIRA_DELETE_AVATAR_FOR_PROJECT);
    }

    /** {@inheritDoc} */
    @Override
    public void onDeleteCommentClicked() {
        setState(CREATING_JIRA_DELETE_COMMENT);
    }

    /** {@inheritDoc} */
    @Override
    public void onDeleteFilterClicked() {
        setState(CREATING_JIRA_DELETE_FILTER);
    }

    /** {@inheritDoc} */
    @Override
    public void onGetDashboardClicked() {
        setState(CREATING_JIRA_GET_DASHBOARD);
    }

    /** {@inheritDoc} */
    @Override
    public void onDoTransitionClicked() {
        setState(CREATING_JIRA_DO_TRANSITION);
    }

    /** {@inheritDoc} */
    @Override
    public void onGetAvatarsForProjectClicked() {
        setState(CREATING_JIRA_GET_AVATARS_FOR_PROJECT);
    }

    /** {@inheritDoc} */
    @Override
    public void onGetCommentsClicked() {
        setState(CREATING_JIRA_GET_COMMENTS);
    }

    /** {@inheritDoc} */
    @Override
    public void onGetComponentsOfProjectClicked() {
        setState(CREATING_JIRA_GET_COMPONENTS_OF_PROJECT);
    }

    /** {@inheritDoc} */
    @Override
    public void onGetDashboardByIdClicked() {
        setState(CREATING_JIRA_GET_DASHBOARD_BY_ID);
    }

    /** {@inheritDoc} */
    @Override
    public void onGetFavouritesFilterClicked() {
        setState(CREATING_JIRA_GET_FAVOURITE_FILTERS);
    }

    /** {@inheritDoc} */
    @Override
    public void onGetFilterByIdClicked() {
        setState(CREATING_JIRA_GET_FILTER_BY_ID);
    }

    /** {@inheritDoc} */
    @Override
    public void onGetGroupClicked() {
        setState(CREATING_JIRA_GET_GROUP);
    }

    /** {@inheritDoc} */
    @Override
    public void onGetIssueClicked() {
        setState(CREATING_JIRA_GET_ISSUE);
    }

    /** {@inheritDoc} */
    @Override
    public void onGetIssuePriorityesClicked() {
        setState(CREATING_JIRA_GET_ISSUE_PRIORITIES);
    }

    /** {@inheritDoc} */
    @Override
    public void onGetIssuePriorityByIdClicked() {
        setState(CREATING_JIRA_GET_ISSUE_PRIORITY_BY_ID);
    }

    /** {@inheritDoc} */
    @Override
    public void onGetIssueTypeByIdClicked() {
        setState(CREATING_JIRA_GET_ISSUE_TYPE_BY_ID);
    }

    /** {@inheritDoc} */
    @Override
    public void onGetIssueTypesClicked() {
        setState(CREATING_JIRA_GET_ISSUE_TYPES);
    }

    /** {@inheritDoc} */
    @Override
    public void onGetIssuesForUserClicked() {
        setState(CREATING_JIRA_GET_ISSUES_FOR_USER);
    }

    /** {@inheritDoc} */
    @Override
    public void onGetProjectClicked() {
        setState(CREATING_JIRA_GET_PROJECT);
    }

    /** {@inheritDoc} */
    @Override
    public void onGetRolesByIdOfProjectClicked() {
        setState(CREATING_JIRA_GET_ROLES_BY_ID_OF_PROJECT);
    }

    /** {@inheritDoc} */
    @Override
    public void onGetRolesOfProjectClicked() {
        setState(CREATING_JIRA_GET_ROLES_OF_PROJECT);
    }

    /** {@inheritDoc} */
    @Override
    public void onGetStatusesOfProjectClicked() {
        setState(CREATING_JIRA_GET_STATUSES_OF_PROJECT);
    }

    /** {@inheritDoc} */
    @Override
    public void onGetTransitionsClicked() {
        setState(CREATING_JIRA_GET_TRANSITIONS);
    }

    /** {@inheritDoc} */
    @Override
    public void onGetUserClicked() {
        setState(CREATING_JIRA_GET_USER);
    }

    /** {@inheritDoc} */
    @Override
    public void onGetUserAssignableProjectClicked() {
        setState(CREATING_JIRA_GET_USER_ASSIGNABLE_PROJECT);
    }

    /** {@inheritDoc} */
    @Override
    public void onGetUserPermissionsClicked() {
        setState(CREATING_JIRA_GET_USER_PERMISSIONS);
    }

    /** {@inheritDoc} */
    @Override
    public void onGetVersionsOfProjectClicked() {
        setState(CREATING_JIRA_GET_VERSIONS_OF_PROJECT);
    }

    /** {@inheritDoc} */
    @Override
    public void onGetVotesForIssueClicked() {
        setState(CREATING_JIRA_GET_VOTES_FOR_ISSUE);
    }

    /** {@inheritDoc} */
    @Override
    public void onInitClicked() {
        setState(CREATING_JIRA_INIT);
    }

    /** {@inheritDoc} */
    @Override
    public void onPostCommentClicked() {
        setState(CREATING_JIRA_POST_COMMENT);
    }

    /** {@inheritDoc} */
    @Override
    public void onSearchAssignableUserClicked() {
        setState(CREATING_JIRA_SEARCH_ASSIGNABLE_USER);
    }

    /** {@inheritDoc} */
    @Override
    public void onSearchAssignableUserMultiProjectClicked() {
        setState(CREATING_JIRA_SEARCH_ASSIGNABLE_USER_MULTI_PROJECT);
    }

    /** {@inheritDoc} */
    @Override
    public void onSearchIssueViewableUsersClicked() {
        setState(CREATING_JIRA_SEARCH_ISSUE_VIEWABLE_USERS);
    }

    /** {@inheritDoc} */
    @Override
    public void onSearchJiraClicked() {
        setState(CREATING_JIRA_SEARCH_JIRA);
    }

    /** {@inheritDoc} */
    @Override
    public void onSearchUserClicked() {
        setState(CREATING_JIRA_SEARCH_USER);
    }

    /** {@inheritDoc} */
    @Override
    public void onSetActorsToRoleOfProjectClicked() {
        setState(CREATING_JIRA_SET_ACTORS_TO_ROLE_OF_PROJECT);
    }

    /** {@inheritDoc} */
    @Override
    public void onUpdateCommentClicked() {
        setState(CREATING_JIRA_UPDATE_COMMENT);
    }

    /** {@inheritDoc} */
    @Override
    public void onUpdateFilterByIdClicked() {
        setState(CREATING_JIRA_UPDATE_FILTER_BY_ID);
    }

    /** {@inheritDoc} */
    @Override
    public void onUpdateIssueClicked() {
        setState(CREATING_JIRA_UPDATE_ISSUE);
    }

    /** {@inheritDoc} */
    @Override
    public void onUpdateIssueAssigneeClicked() {
        setState(CREATING_JIRA_UPDATE_ISSUE_ASSIGNEE);
    }

    /** {@inheritDoc} */
    @Override
    public void onCreateSpreadsheetClicked() {
        setState(CREATING_SPREADSHEET_CREATE_SPREADSHEET);
    }

    /** {@inheritDoc} */
    @Override
    public void onSpreadsheetCreateWorksheetClicked() {
        setState(CREATING_SPREADSHEET_CREATE_WORKSHEET);
    }

    /** {@inheritDoc} */
    @Override
    public void onSpreadsheetDeleteWorksheetClicked() {
        setState(CREATING_SPREADSHEET_DELETE_WORKSHEET);
    }

    /** {@inheritDoc} */
    @Override
    public void onSpreadsheetGetAllCellsClicked() {
        setState(CREATING_SPREADSHEET_GET_ALL_CELLS);
    }

    /** {@inheritDoc} */
    @Override
    public void onSpreadsheetGetAllCellsCSVClicked() {
        setState(CREATING_SPREADSHEET_GET_ALL_CELLS_CSV);
    }

    /** {@inheritDoc} */
    @Override
    public void onSpreadsheetGetAllSpreadsheetsClicked() {
        setState(CREATING_SPREADSHEET_GET_ALL_SPREADSHEETS);
    }

    /** {@inheritDoc} */
    @Override
    public void onSpreadsheetGetAllWorksheetsClicked() {
        setState(CREATING_SPREADSHEET_GET_ALL_WORKSHEETS);
    }

    /** {@inheritDoc} */
    @Override
    public void onSpreadsheetGetAuthorsClicked() {
        setState(CREATING_SPREADSHEET_GET_AUTHORS);
    }

    /** {@inheritDoc} */
    @Override
    public void onSpreadsheetGetCellRangeClicked() {
        setState(CREATING_SPREADSHEET_GET_CELL_RANGE);
    }

    /** {@inheritDoc} */
    @Override
    public void onSpreadsheetGetCellRangeCSVClicked() {
        setState(CREATING_SPREADSHEET_GET_CELL_RANGE_CSV);
    }

    /** {@inheritDoc} */
    @Override
    public void onSpreadsheetGetColumnHeadersClicked() {
        setState(CREATING_SPREADSHEET_GET_COLUMN_HEADERS);
    }

    /** {@inheritDoc} */
    @Override
    public void onSpreadsheetGetSpreadsheetByTitleClicked() {
        setState(CREATING_SPREADSHEET_GET_SPREADSHEET_BY_TITLE);
    }

    /** {@inheritDoc} */
    @Override
    public void onSpreadsheetGetWorksheetByTitleClicked() {
        setState(CREATING_SPREADSHEET_GET_WORKSHEET_BY_TITLE);
    }

    /** {@inheritDoc} */
    @Override
    public void onSpreadsheetImportCSVClicked() {
        setState(CREATING_SPREADSHEET_IMPORT_CSV);
    }

    /** {@inheritDoc} */
    @Override
    public void onSpreadsheetInitClicked() {
        setState(CREATING_SPREADSHEET_INIT);
    }

    /** {@inheritDoc} */
    @Override
    public void onSpreadsheetPurgeWorksheetClicked() {
        setState(CREATING_SPREADSHEET_PURGE_WORKSHEET);
    }

    /** {@inheritDoc} */
    @Override
    public void onSpreadsheetSearchCellClicked() {
        setState(CREATING_SPREADSHEET_SEARCH_CELL);
    }

    /** {@inheritDoc} */
    @Override
    public void onSpreadsheetSetRowClicked() {
        setState(CREATING_SPREADSHEET_SET_ROW);
    }

    /** {@inheritDoc} */
    @Override
    public void onSpreadsheetUpdateWorksheetClicked() {
        setState(CREATING_SPREADSHEET_UPDATE_WORKSHEET_META);
    }

    /** {@inheritDoc} */
    @Override
    public void onSpreadsheetUsernameLoginClicked() {
        setState(CREATING_SPREADSHEET_USERNAME_LOGIN);
    }

}