/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package com.codenvy.ide.ext.wso2.client.inject;

import com.codenvy.ide.api.extension.ExtensionGinModule;
import com.codenvy.ide.api.filetypes.FileType;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.codenvy.ide.ext.wso2.client.editor.ESBXmlFileType;
import com.codenvy.ide.ext.wso2.client.editor.graphical.EditorViewFactory;
import com.codenvy.ide.ext.wso2.client.editor.graphical.GraphicEditorView;
import com.codenvy.ide.ext.wso2.client.editor.graphical.GraphicEditorViewImpl;
import com.codenvy.ide.ext.wso2.client.editor.text.AutoCompleterFactory;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;

import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_XML_EXTENSION;
import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_XML_MIME_TYPE;


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
        install(new GinFactoryModuleBuilder()
                        .implement(GraphicEditorView.class, GraphicEditorViewImpl.class)
                        .build(EditorViewFactory.class));
    }

    /**
     * Create an instance of FileType.
     *
     * @param wso2Resources
     *         resources which need to create FileType
     * @return an instance of FileType
     */
    @Provides
    @ESBXmlFileType
    @Singleton
    protected FileType createEsbXmlFileType(@Nonnull WSO2Resources wso2Resources) {
        return new FileType(wso2Resources.xmlFileIcon(), ESB_XML_MIME_TYPE, ESB_XML_EXTENSION);
    }

}