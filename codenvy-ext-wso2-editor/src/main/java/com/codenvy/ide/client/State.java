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
    CREATING_NOTHING
}