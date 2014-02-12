/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 * [2012] - [2013] Codenvy, S.A. 
 * All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
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

    String MAIN_FOLDER_NAME             = "main";
    String SRC_FOLDER_NAME              = "src";
    String SYNAPSE_CONFIG_FOLDER_NAME   = "synapse-config";

    String ENDPOINTS_FOLDER_NAME        = "endpoints";
    String SEQUENCE_FOLDER_NAME         = "sequences";
    String PROXY_SERVICE_FOLDER_NAME    = "proxy-services";
    String LOCAL_ENTRY_FOLDER_NAME      = "local-entries";

    String WSO2_PROJECT_ID              = "WSO2Project";
    String ESB_CONFIGURATION_PROJECT_ID = "ESBConfigurationProject";
    String ESB_PROJECT_DESCRIPTION      = "ESB Configuration project.";
    String PROJECT_MIME_TYPE            = "text/vnd.ideproject+directory";

    String WSO2_MAIN_ACTION_GROUP       = "WSO2MainActionGroup";
    String WSO2_ACTION_GROUP            = "WSO2ProjectActionGroup";
    String WSO2_NEW_RESOURCE_GROUP      = "WSO2NewResourceGroup";
    String WSO2_IMPORT_RESOURCE_GROUP   = "WSO2ImportResourceGroup";
    String IMPORT_SYNAPSE_ACTION        = "ImportSynapseAction";
    String CREATE_ENDPOINT_ACTION       = "CreateEndpointAction";
    String CREATE_SEQUENCE_ACTION       = "CreateSequenceAction";
    String CREATE_PROXY_SERVICE_ACTION  = "CreateProxyServiceAction";
    String CREATE_LOCAL_ENTRY_ACTION    = "CreateLocalEntryAction";
    String LOGIN_WSO2_ACTION            = "LoginWSO2Action";

    /** Dedicated mime type name for WSO2 ESB configuration files */
    String ESB_XML_MIME_TYPE            = "text/xml-esb";

    /** Extension name for WSO2 ESB configuration files */
    String ESB_XML_EXTENSION            = "xml";

}
