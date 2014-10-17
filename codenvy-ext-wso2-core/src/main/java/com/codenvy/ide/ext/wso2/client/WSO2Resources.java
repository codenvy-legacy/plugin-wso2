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
package com.codenvy.ide.ext.wso2.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;
import com.google.inject.Singleton;

import org.vectomatic.dom.svg.ui.SVGResource;

/**
 * Resources for WSO2 plugin.
 *
 * @author Dmitry Kuleshov
 * @author Justin Trentesaux
 */
@Singleton
public interface WSO2Resources extends ClientBundle {

    public interface WSO2Style extends CssResource {

        String importErrorFont();

        String editorButtonStyle();

        String palettePropertyBtnStyle();

    }

    @Source("esb.xsd")
    TextResource xmlSchemaDefinition();

    @Source({"WSO2GlobalStyle.css"})
    WSO2Style wso2Style();

    @Source("xml.js")
    DataResource xmlParserJS();

    @Source("icons/xml_file.png")
    ImageResource xmlFileIcon();

    @Source("templates/endpoint-template.txt")
    TextResource endpointTemplate();

    @Source("templates/sequence-template.txt")
    TextResource sequenceTemplate();

    @Source("templates/proxy-service.txt")
    TextResource proxyServiceTemplate();

    @Source("templates/local-entry-template.txt")
    TextResource localEntryTemplate();

    @Source("icons/wso2-action-group-icon.png")
    ImageResource wso2GroupIcon();

    @Source("icons/endpoint-icon.png")
    ImageResource endpointIcon();

    @Source("icons/sequence-icon.png")
    ImageResource sequenceIcon();

    @Source("icons/proxy-service-icon.png")
    ImageResource proxyServiceIcon();

    @Source("icons/local-entry-icon.png")
    ImageResource localEntryIcon();

    @Source("icons/endpoint-wizard.png")
    ImageResource endpoint_wizard();

    @Source("icons/sequence-wizard.png")
    ImageResource sequence_wizard();

    @Source("icons/proxy-service-wizard.png")
    ImageResource proxy_service_wizard();

    @Source("icons/local-entry-wizard.png")
    ImageResource local_entry_wizard();

    @Source("icons/new-project-wizard-icon.png")
    ImageResource wso2_project_wizard();

    @Source("icons/esb-project-wizard.png")
    ImageResource esb_project_wizard();

    @Source("icons/esb-template-icon.png")
    ImageResource esb_template_icon();

    @Source("icons/upload.png")
    ImageResource uploadIcon();

    @Source("icons/synapse-icon.png")
    ImageResource synapseIcon();

    @Source("icons/paletteIcon.png")
    ImageResource paletteIcon();

    @Source("icons/propertiesIcon.png")
    ImageResource propertiesIcon();

    @Source("icons/svg/xml.svg")
    SVGResource xmlIcon();

    @Source("icons/svg/newproject-wso2.svg")
    SVGResource newProjectIcon();

}