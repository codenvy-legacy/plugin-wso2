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
package com.codenvy.ide.client;

/**
 * The list of states that can be taken by editor.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public enum State {
    CREATING_NOTHING,

    CREATING_LOG,
    CREATING_PROPERTY,
    CREATING_PAYLOADFACTORY,
    CREATING_SEND,
    CREATING_HEADER,
    CREATING_RESPOND,
    CREATING_FILTER,
    CREATING_SWITCH,
    CREATING_SEQUENCE,
    CREATING_ENRICH,
    CREATING_LOOPBACK,
    CREATING_CALLTEMPLATE,
    CREATING_CALL,
    CREATING_ADDRESS_ENDPOINT,

    CREATING_SALESFORCE_INIT,
    CREATING_SALESFORCE_CREATE,
    CREATING_SALESFORCE_UPDATE,
    CREATING_SALESFORCE_DELETE,
    CREATING_SALESFORCE_DESCRIBE_GLOBAL,
    CREATING_SALESFORCE_DESCRIBE_SUBJECT,
    CREATING_SALESFORCE_DESCRIBE_SUBJECTS,
    CREATING_SALESFORCE_EMPTY_RECYCLE_BIN,
    CREATING_SALESFORCE_LOGOUT,
    CREATING_SALESFORCE_QUERY,
    CREATING_SALESFORCE_QURY_ALL,
    CREATING_SALESFORCE_QUERY_MORE,
    CREATING_SALESFORCE_RESET_PASSWORD,
    CREATING_SALESFORCE_RETRIVE,
    CREATING_SALESFORCE_SEARCH,
    CREATING_SALESFORCE_SEND_EMAIL,
    CREATING_SALESFORCE_SEND_EMAIL_MESSAGE,
    CREATING_SALESFORCE_SET_PASSWORD,
    CREATING_SALESFORCE_UNDELETE,
    CREATING_SALESFORCE_UPSET,
    CREATING_SALESFORCE_GET_USER_INFORMATION,

    CREATING_JIRA_ADD_ATTACHMENT_TO_ISSUE_ID,
    CREATING_JIRA_CREATE_FILTER,
    CREATING_JIRA_CREATE_ISSUE,
    CREATING_JIRA_DELETE_AVATAR_FOR_PROJECT,
    CREATING_JIRA_DELETE_COMMENT,
    CREATING_JIRA_DELETE_FILTER,
    CREATING_JIRA_GET_DASHBOARD,
    CREATING_JIRA_DO_TRANSITION,
    CREATING_JIRA_GET_AVATARS_FOR_PROJECT,
    CREATING_JIRA_GET_COMMENTS,
    CREATING_JIRA_GET_COMPONENTS_OF_PROJECT,
    CREATING_JIRA_GET_DASHBOARD_BY_ID,
    CREATING_JIRA_GET_FAVOURITE_FILTERS,
    CREATING_JIRA_GET_FILTER_BY_ID,
    CREATING_JIRA_GET_GROUP,
    CREATING_JIRA_GET_ISSUE,
    CREATING_JIRA_GET_ISSUE_PRIORITIES,
    CREATING_JIRA_GET_ISSUE_PRIORITY_BY_ID,
    CREATING_JIRA_GET_ISSUE_TYPE_BY_ID,
    CREATING_JIRA_GET_ISSUE_TYPES,
    CREATING_JIRA_GET_ISSUES_FOR_USER,
    CREATING_JIRA_GET_PROJECT,
    CREATING_JIRA_GET_ROLES_BY_ID_OF_PROJECT,
    CREATING_JIRA_GET_ROLES_OF_PROJECT,
    CREATING_JIRA_GET_STATUSES_OF_PROJECT,
    CREATING_JIRA_GET_TRANSITIONS,
    CREATING_JIRA_GET_USER,
    CREATING_JIRA_GET_USER_ASSIGNABLE_PROJECT,
    CREATING_JIRA_GET_USER_PERMISSIONS,
    CREATING_JIRA_GET_VERSIONS_OF_PROJECT,
    CREATING_JIRA_GET_VOTES_FOR_ISSUE,
    CREATING_JIRA_INIT,
    CREATING_JIRA_POST_COMMENT,
    CREATING_JIRA_SEARCH_ASSIGNABLE_USER,
    CREATING_JIRA_SEARCH_ASSIGNABLE_USER_MULTI_PROJECT,
    CREATING_JIRA_SEARCH_ISSUE_VIEWABLE_USERS,
    CREATING_JIRA_SEARCH_JIRA,
    CREATING_JIRA_SEARCH_USER,
    CREATING_JIRA_SET_ACTORS_TO_ROLE_OF_PROJECT,
    CREATING_JIRA_UPDATE_COMMENT,
    CREATING_JIRA_UPDATE_FILTER_BY_ID,
    CREATING_JIRA_UPDATE_ISSUE,
    CREATING_JIRA_UPDATE_ISSUE_ASSIGNEE,

    CREATING_TWITTER_DESTROY_STATUS,
    CREATING_TWITTER_GET_CLOTHES_TRENDS,
    CREATING_TWITTER_GET_DIRECT_MESSAGES,
    CREATING_TWITTER_GET_FOLLOWERS,
    CREATING_TWITTER_GET_FOLLOWERS_IDS,
    CREATING_TWITTER_GET_FRIENDS,
    CREATING_TWITTER_GET_FRIENDS_IDS,
    CREATING_TWITTER_GET_HOME_TIME_LINE,
    CREATING_TWITTER_GET_MENTIONS_TIME_LINE,
    CREATING_TWITTER_GET_RETWEETS_OF_MINE,
    CREATING_TWITTER_GET_SENT_DIRECT_MESSAGE,
    CREATING_TWITTER_GET_TOP_TREND_PLACES,
    CREATING_TWITTER_GET_USER_TIME_LINE,
    CREATING_TWITTER_INIT,

    CREATING_SPREADSHEET_CREATE_SPREADSHEET,
    CREATING_SPREADSHEET_CREATE_WORKSHEET,
    CREATING_SPREADSHEET_DELETE_WORKSHEET,
    CREATING_SPREADSHEET_GET_ALL_CELLS,
    CREATING_SPREADSHEET_GET_ALL_CELLS_CSV,
    CREATING_SPREADSHEET_GET_ALL_SPREADSHEETS,
    CREATING_SPREADSHEET_GET_ALL_WORKSHEETS,
    CREATING_SPREADSHEET_GET_AUTHORS,
    CREATING_SPREADSHEET_GET_CELL_RANGE,
    CREATING_SPREADSHEET_GET_CELL_RANGE_CSV,
    CREATING_SPREADSHEET_GET_COLUMN_HEADERS,
    CREATING_SPREADSHEET_GET_SPREADSHEET_BY_TITLE,
    CREATING_SPREADSHEET_GET_WORKSHEET_BY_TITLE,
    CREATING_SPREADSHEET_IMPORT_CSV,
    CREATING_SPREADSHEET_INIT,
    CREATING_SPREADSHEET_PURGE_WORKSHEET,
    CREATING_SPREADSHEET_SEARCH_CELL,
    CREATING_SPREADSHEET_SET_ROW,
    CREATING_SPREADSHEET_UPDATE_WORKSHEET_META,
    CREATING_SPREADSHEET_USERNAME_LOGIN
}