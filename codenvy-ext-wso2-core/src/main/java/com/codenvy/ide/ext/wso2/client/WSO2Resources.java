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
package com.codenvy.ide.ext.wso2.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

/**
 * Resources for WSO2 plugin.
 *
 * @author Dmitry Kuleshov
 */
public interface WSO2Resources extends ClientBundle {

    @Source("xml.js")
    DataResource xmlParserJS();

    @Source("xml_file.png")
    ImageResource xmlFileIcon();

    @Source("endpoint-template.txt")
    TextResource endpointTemplate();

    @Source("sequence-template.txt")
    TextResource sequenceTemplate();

    @Source("endpoint-icon.png")
    ImageResource endpointIcon();

    @Source("sequence-icon.png")
    ImageResource sequenceIcon();

    @Source("endpoint-wizard.png")
    ImageResource endpoint_wizard();

    @Source("sequence-wizard.png")
    ImageResource sequence_wizard();

    @Source("esb-project-wizard.png")
    ImageResource esb_project_wizard();

}