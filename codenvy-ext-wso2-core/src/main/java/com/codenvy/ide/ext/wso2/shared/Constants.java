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
 * @author Andrey Plotnikov
 * @author Dmitry Kuleshov
 */
public interface Constants {

    String MAIN_FOLDER_NAME           = "main";
    String SRC_FOLDER_NAME            = "src";
    String SYNAPSE_CONFIG_FOLDER_NAME = "synapse-config";

    String ENDPOINTS_FOLDER_NAME = "endpoints";
    String SEQUENCE_FOLDER_NAME  = "sequences";

    String WSO2_PROJECT_ID              = "WSO2Project";
    String ESB_CONFIGURATION_PROJECT_ID = "ESBConfigurationProject";

    String WSO2_ACTION_GROUP       = "WSO2ActionGroup";
    String WSO2_NEW_RESOURCE_GROUP = "WSO2NewResourceGroup";

    /**
     * Dedicated mime type name for WSO2 ESB configuration files
     */
    String ESB_XML_MIME_TYPE = "text/xml-esb";
    /**
     * Extension name for WSO2 ESB configuration files
     */
    String ESB_XML_EXTENSION = "xml";

}