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
package com.codenvy.ide.ext.wso2.client.inject;

import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_XML_EXTENSION;
import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_XML_MIME_TYPE;

import com.codenvy.ide.api.extension.ExtensionGinModule;
import com.codenvy.ide.api.resources.FileType;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.codenvy.ide.ext.wso2.client.editor.AutoCompleterFactory;
import com.codenvy.ide.ext.wso2.client.editor.ESBGraphicalFileType;
import com.codenvy.ide.ext.wso2.client.editor.ESBXmlFileType;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Provides;
import com.google.inject.Singleton;


/**
 * The module that contains configuration of the client side part of the plugin.
 *
 * @author Valeriy Svydenko
 */
@ExtensionGinModule
public class GinModule extends AbstractGinModule {

    /** {@inheritDoc} */
    @Override
    protected void configure() {
        install(new GinFactoryModuleBuilder().build(AutoCompleterFactory.class));
    }

    @Provides
    @ESBXmlFileType
    @Singleton
    protected FileType esbXmlFileType(WSO2Resources wso2Resources) {
        return new FileType(wso2Resources.xmlFileIcon(), ESB_XML_MIME_TYPE, ESB_XML_EXTENSION);
    }
    
    @Provides
    @ESBGraphicalFileType
    @Singleton
    protected FileType esbGraphicalFileType(WSO2Resources wso2Resources) {
        return new FileType(wso2Resources.xmlFileIcon(), ESB_XML_MIME_TYPE, ESB_XML_EXTENSION);
    }
}