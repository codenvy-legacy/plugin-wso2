/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
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
    CREATING_NOTHING
}