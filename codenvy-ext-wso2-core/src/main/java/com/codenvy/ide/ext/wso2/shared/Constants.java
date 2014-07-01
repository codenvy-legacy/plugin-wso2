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
package com.codenvy.ide.ext.wso2.shared;

/**
 * Contains general constants that needed on client and server side.
 *
 * @author Valeriy Svydenko
 * @author Andrey Plotnikov
 * @author Dmitry Kuleshov
 */
public interface Constants {

    String MAIN_FOLDER_NAME           = "main";
    String SRC_FOLDER_NAME            = "src";
    String SYNAPSE_CONFIG_FOLDER_NAME = "synapse-config";

    String ENDPOINTS_FOLDER_NAME     = "endpoints";
    String SEQUENCE_FOLDER_NAME      = "sequences";
    String PROXY_SERVICE_FOLDER_NAME = "proxy-services";
    String LOCAL_ENTRY_FOLDER_NAME   = "local-entries";

    String WSO2_PROJECT_ID                = "WSO2Project";
    String ESB_CONFIGURATION_PROJECT_ID   = "ESBConfigurationProject";
    String ESB_CONFIGURATION_PROJECT_NAME = "ESB Configuration";

    String WSO2_MAIN_ACTION_GROUP      = "WSO2MainActionGroup";
    String WSO2_ACTION_GROUP           = "WSO2ProjectActionGroup";
    String WSO2_NEW_RESOURCE_GROUP     = "WSO2NewResourceGroup";
    String WSO2_IMPORT_RESOURCE_GROUP  = "WSO2ImportResourceGroup";
    String IMPORT_SYNAPSE_ACTION       = "ImportSynapseAction";
    String CREATE_ENDPOINT_ACTION      = "CreateEndpointAction";
    String CREATE_SEQUENCE_ACTION      = "CreateSequenceAction";
    String CREATE_PROXY_SERVICE_ACTION = "CreateProxyServiceAction";
    String CREATE_LOCAL_ENTRY_ACTION   = "CreateLocalEntryAction";
    String LOGIN_WSO2_ACTION           = "LoginWSO2Action";

    /** Dedicated mime type name for WSO2 ESB configuration files */
    String ESB_XML_MIME_TYPE = "text/xml-esb";

    /** Extension name for WSO2 ESB configuration files */
    String ESB_XML_EXTENSION = "xml";

}
