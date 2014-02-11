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

import org.genmymodel.gmmf.common.ModelWidgetCSS;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

/**
 * Resources for WSO2 plugin.
 *
 * @author Dmitry Kuleshov
 */
public interface WSO2Resources extends ClientBundle {

    public interface WSO2Style extends CssResource {

        String importErrorFont();

    }

    @Source({"css/WSO2Style.css"})
    WSO2Style wso2Style();

    @Source({"css/workspace.css"})
    ModelWidgetCSS wso2GraphicalEditorStyle();
    
    @Source("xml.js")
    DataResource xmlParserJS();

    @Source("xml_file.png")
    ImageResource xmlFileIcon();

    @Source("endpoint-template.txt")
    TextResource endpointTemplate();

    @Source("sequence-template.txt")
    TextResource sequenceTemplate();

    @Source("proxy-service.txt")
    TextResource proxyServiceTemplate();

    @Source("local-entry-template.txt")
    TextResource localEntryTemplate();

    @Source("wso2-action-group-icon.png")
    ImageResource wso2GroupIcon();

    @Source("endpoint-icon.png")
    ImageResource endpointIcon();

    @Source("sequence-icon.png")
    ImageResource sequenceIcon();

    @Source("proxy-service-icon.png")
    ImageResource proxyServiceIcon();

    @Source("local-entry-icon.png")
    ImageResource localEntryIcon();

    @Source("endpoint-wizard.png")
    ImageResource endpoint_wizard();

    @Source("sequence-wizard.png")
    ImageResource sequence_wizard();

    @Source("proxy-service-wizard.png")
    ImageResource proxy_service_wizard();

    @Source("local-entry-wizard.png")
    ImageResource local_entry_wizard();

    @Source("new-project-wizard-icon.png")
    ImageResource wso2_project_wizard();

    @Source("esb-project-wizard.png")
    ImageResource esb_project_wizard();

    @Source("esb-template-icon.png")
    ImageResource esb_template_icon();

    @Source("upload.png")
    ImageResource uploadIcon();

    @Source("synapse-icon.png")
    ImageResource synapseIcon();
    
    /* We should find a better solution to reuse the WorkspaceCSS
     * It implied a refactory, working on it (29-01-2014)
     */
	@Source("icons/align-top-edges.png")
	ImageResource alignTopEdgesIcon();
	
	@Source("icons/align-vertical-centers.png")
	ImageResource alignVerticalCentersIcon();
	
	@Source("icons/align-bottom-edges.png")
	ImageResource alignBottomEdgesIcon();
	
	@Source("icons/align-left-edges.png")
	ImageResource alignLeftEdgesIcon();
	
	@Source("icons/align-horizontal-centers.png")
	ImageResource alignHorizontalCentersIcon();
	
	@Source("icons/align-right-edges.png")
	ImageResource alignRightEdgesIcon();
	
	@Source("icons/distribute-vertical.png")
	ImageResource distributeVerticalIcon();
	
	@Source("icons/distribute-horizontal.png")
	ImageResource distributeHorizontalIcon();

}