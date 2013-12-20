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
import com.codenvy.ide.api.ui.action.ActionManager;
import com.codenvy.ide.api.ui.action.DefaultActionGroup;
import com.codenvy.ide.api.ui.wizard.template.AbstractTemplatePage;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.ext.wso2.client.action.CreateEndpointAction;
import com.codenvy.ide.ext.wso2.client.action.CreateSequenceAction;
import com.codenvy.ide.ext.wso2.client.action.ImportFileAction;
import com.codenvy.ide.ext.wso2.client.action.WSO2ActionGroup;
import com.codenvy.ide.ext.wso2.client.editor.ESBXmlFileType;
import com.codenvy.ide.ext.wso2.client.editor.XmlEditorProvider;
import com.codenvy.ide.ext.wso2.client.wizard.project.CreateESBConfProjectPage;
import com.codenvy.ide.resources.ProjectTypeAgent;
import com.google.gwt.core.client.ScriptInjector;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import static com.codenvy.ide.api.ui.action.Constraints.FIRST;
import static com.codenvy.ide.api.ui.action.Constraints.LAST;
import static com.codenvy.ide.api.ui.action.IdeActions.GROUP_MAIN_CONTEXT_MENU;
import static com.codenvy.ide.api.ui.action.IdeActions.GROUP_MAIN_MENU;
import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_CONFIGURATION_PROJECT_ID;
import static com.codenvy.ide.ext.wso2.shared.Constants.WSO2_ACTION_GROUP;
import static com.codenvy.ide.ext.wso2.shared.Constants.WSO2_NEW_RESOURCE_GROUP;
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

    @Inject
    public WSO2Extension(LocalizationConstant locale,
                         ProjectTypeAgent projectTypeAgent,
                         TemplateAgent templateAgent,
                         Provider<CreateESBConfProjectPage> createESBConfProjectPage,
                         ActionManager actionManager,
                         ImportFileAction importFileAction,
                         CreateEndpointAction createEndpointAction,
                         CreateSequenceAction createSequenceAction,
                         WSO2Resources wso2Resources,
                         ResourceProvider resourceProvider,
                         EditorRegistry editorRegistry,
                         XmlEditorProvider xmlEditorProvider,
                         @ESBXmlFileType FileType esbXmlFileType) {

        initProject(locale, projectTypeAgent, templateAgent, createESBConfProjectPage);
        initXmlEditor(wso2Resources, resourceProvider, editorRegistry, xmlEditorProvider, esbXmlFileType);
        initActions(locale, resourceProvider, actionManager, importFileAction, createEndpointAction, createSequenceAction);
    }

    private void initXmlEditor(WSO2Resources wso2Resources,
                               ResourceProvider resourceProvider,
                               EditorRegistry editorRegistry,
                               XmlEditorProvider xmlEditorProvider,
                               FileType esbXmlFileType) {

        ScriptInjector.fromUrl(wso2Resources.xmlParserJS().getSafeUri().asString()).setWindow(TOP_WINDOW).inject();

        resourceProvider.registerFileType(esbXmlFileType);

        editorRegistry.register(esbXmlFileType, xmlEditorProvider);
    }

    @SuppressWarnings("unchecked")
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

    private void initActions(LocalizationConstant locale,
                             ResourceProvider resourceProvider,
                             ActionManager actionManager,
                             ImportFileAction importFileAction,
                             CreateEndpointAction createEndpointAction,
                             CreateSequenceAction createSequenceAction) {

        DefaultActionGroup wso2MainMenu = (DefaultActionGroup)actionManager.getAction(GROUP_MAIN_MENU);
        DefaultActionGroup wso2ContextMenu = (DefaultActionGroup)actionManager.getAction(GROUP_MAIN_CONTEXT_MENU);

        DefaultActionGroup wso2ActionGroup = new WSO2ActionGroup(locale, actionManager, resourceProvider);

        actionManager.registerAction(WSO2_ACTION_GROUP, wso2ActionGroup);

        DefaultActionGroup wso2NewGroup = new DefaultActionGroup(locale.wso2ActionNew(), true, actionManager);
        actionManager.registerAction(WSO2_NEW_RESOURCE_GROUP, wso2ActionGroup);

        wso2ActionGroup.add(wso2NewGroup);
        wso2ActionGroup.add(importFileAction);

        wso2NewGroup.add(createEndpointAction);
        wso2NewGroup.add(createSequenceAction);

        wso2MainMenu.add(wso2ActionGroup, LAST);
        wso2MainMenu.addSeparator();

        wso2ContextMenu.add(wso2ActionGroup, FIRST);
        wso2ContextMenu.addSeparator();
    }
}