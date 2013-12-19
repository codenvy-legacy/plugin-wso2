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


import com.codenvy.ide.api.editor.EditorRegistry;
import com.codenvy.ide.api.extension.Extension;
import com.codenvy.ide.api.resources.FileType;
import com.codenvy.ide.api.resources.ResourceProvider;
import com.codenvy.ide.api.template.TemplateAgent;
import com.codenvy.ide.api.ui.wizard.newresource.NewResourceAgent;
import com.codenvy.ide.api.ui.wizard.template.AbstractTemplatePage;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.ext.wso2.client.wizard.project.CreateESBConfProjectPage;
import com.codenvy.ide.ext.wso2.shared.Constants;
import com.codenvy.ide.resources.ProjectTypeAgent;
import com.google.gwt.core.client.ScriptInjector;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_CONFIGURATION_PROJECT_ID;
import static com.codenvy.ide.ext.wso2.shared.Constants.WSO2_PROJECT_ID;
import static com.google.gwt.core.client.ScriptInjector.TOP_WINDOW;

/**
 * Codenvy IDE3 extension provides functionality for WSO2 integration. This at the time of this writing includes major operations for WSO2
 * ESB configuration manipulation: import/upload, create, edit, remove etc.
 *
 * @author Valeriy Svydenko
 * @author Andrey Plotnikov
 * @author Dmitry Kuleshov
 */
@Singleton
@Extension(title = "WSO2 Integration Flow Plugin", version = "1.0.0-M1")
public class WSO2Extension {

    @SuppressWarnings("unchecked")
    @Inject
    public WSO2Extension(LocalizationConstant locale,
                         ProjectTypeAgent projectTypeAgent,
                         TemplateAgent templateAgent,
                         Provider<CreateESBConfProjectPage> createESBConfProjectPage,
                         WSO2Resources wso2Resources,
                         ResourceProvider resourceProvider,
                         EditorRegistry editorRegistry,
                         XmlEditorProvider xmlEditorProvider,
                         NewResourceAgent newResourceAgent,
                         NewEsbXmlFileProvider newEsbXmlFileProvider) {

        initProject(locale, projectTypeAgent, templateAgent, createESBConfProjectPage);
        initXmlEditor(wso2Resources, resourceProvider, editorRegistry, xmlEditorProvider, newResourceAgent, newEsbXmlFileProvider);
    }

    private void initXmlEditor(WSO2Resources wso2Resources,
                               ResourceProvider resourceProvider,
                               EditorRegistry editorRegistry,
                               XmlEditorProvider xmlEditorProvider,
                               NewResourceAgent newResourceAgent,
                               NewEsbXmlFileProvider newEsbXmlFileProvider) {
        // TODO change String to a Link
        ScriptInjector.fromString(wso2Resources.xmlParserJS().getText()).setWindow(TOP_WINDOW).inject();

        FileType esbXmlFile = new FileType(wso2Resources.xmlFileIcon(), Constants.ESB_XML_MIME_TYPE, Constants.ESB_XML_EXTENSION);
        resourceProvider.registerFileType(esbXmlFile);

        editorRegistry.register(esbXmlFile, xmlEditorProvider);

        newResourceAgent.register(newEsbXmlFileProvider);
    }

    private void initProject(LocalizationConstant locale,
                             ProjectTypeAgent projectTypeAgent,
                             TemplateAgent templateAgent,
                             Provider<CreateESBConfProjectPage> createESBConfProjectPage) {
        projectTypeAgent.register(WSO2_PROJECT_ID,
                                  locale.wso2ProjectTitle(),
                                  null,
                                  WSO2_PROJECT_ID,
                                  Collections.<String>createArray());

        templateAgent.register(ESB_CONFIGURATION_PROJECT_ID,
                               locale.wso2ProjectEsbTitle(),
                               null,
                               WSO2_PROJECT_ID,
                               Collections.<String>createArray(ESB_CONFIGURATION_PROJECT_ID),
                               Collections.<Provider<? extends AbstractTemplatePage>>createArray(createESBConfProjectPage));
    }
}