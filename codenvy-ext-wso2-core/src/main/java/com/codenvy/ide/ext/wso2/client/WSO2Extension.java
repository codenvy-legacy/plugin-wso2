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
package com.codenvy.ide.ext.wso2.client;


import com.codenvy.ide.api.editor.EditorRegistry;
import com.codenvy.ide.api.extension.Extension;
import com.codenvy.ide.api.filetypes.FileType;
import com.codenvy.ide.api.filetypes.FileTypeRegistry;
import com.codenvy.ide.api.resources.ResourceProvider;
import com.codenvy.ide.api.ui.action.ActionManager;
import com.codenvy.ide.api.ui.action.DefaultActionGroup;
import com.codenvy.ide.api.ui.wizard.ProjectTypeWizardRegistry;
import com.codenvy.ide.api.ui.wizard.ProjectWizard;
import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.ext.wso2.client.action.CreateEndpointAction;
import com.codenvy.ide.ext.wso2.client.action.CreateLocalEntryAction;
import com.codenvy.ide.ext.wso2.client.action.CreateProxyServiceAction;
import com.codenvy.ide.ext.wso2.client.action.CreateSequenceAction;
import com.codenvy.ide.ext.wso2.client.action.ImportSynapseAction;
import com.codenvy.ide.ext.wso2.client.action.WSO2ProjectActionGroup;
import com.codenvy.ide.ext.wso2.client.editor.ESBXmlFileType;
import com.codenvy.ide.ext.wso2.client.editor.text.XmlEditorProvider;
import com.codenvy.ide.ext.wso2.client.wizard.project.WSO2PagePresenter;
import com.google.gwt.core.client.ScriptInjector;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import static com.codenvy.ide.api.ui.action.Constraints.FIRST;
import static com.codenvy.ide.api.ui.action.Constraints.LAST;
import static com.codenvy.ide.api.ui.action.IdeActions.GROUP_MAIN_CONTEXT_MENU;
import static com.codenvy.ide.api.ui.action.IdeActions.GROUP_MAIN_MENU;
import static com.codenvy.ide.ext.wso2.shared.Constants.CREATE_ENDPOINT_ACTION;
import static com.codenvy.ide.ext.wso2.shared.Constants.CREATE_LOCAL_ENTRY_ACTION;
import static com.codenvy.ide.ext.wso2.shared.Constants.CREATE_PROXY_SERVICE_ACTION;
import static com.codenvy.ide.ext.wso2.shared.Constants.CREATE_SEQUENCE_ACTION;
import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_CONFIGURATION_PROJECT_ID;
import static com.codenvy.ide.ext.wso2.shared.Constants.IMPORT_SYNAPSE_ACTION;
import static com.codenvy.ide.ext.wso2.shared.Constants.WSO2_ACTION_GROUP;
import static com.codenvy.ide.ext.wso2.shared.Constants.WSO2_IMPORT_RESOURCE_GROUP;
import static com.codenvy.ide.ext.wso2.shared.Constants.WSO2_MAIN_ACTION_GROUP;
import static com.codenvy.ide.ext.wso2.shared.Constants.WSO2_NEW_RESOURCE_GROUP;
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
    public WSO2Extension(WSO2Resources wso2Resources,
                         EditorResources editorResources,
                         Provider<WSO2PagePresenter> wso2PagePresenter,
                         ProjectTypeWizardRegistry projectTypeWizardRegistry,
                         ProjectWizard projectWizard) {
        wso2Resources.wso2Style().ensureInjected();
        editorResources.editorCSS().ensureInjected();

        projectWizard.addPage(wso2PagePresenter);
        projectTypeWizardRegistry.addWizard(ESB_CONFIGURATION_PROJECT_ID, projectWizard);
    }

    @Inject
    public void initXmlEditor(WSO2Resources wso2Resources,
                              EditorRegistry editorRegistry,
                              FileTypeRegistry fileTypeRegistry,
                              XmlEditorProvider xmlEditorProvider,
                              @ESBXmlFileType FileType esbXmlFileType) {

        ScriptInjector.fromUrl(wso2Resources.xmlParserJS().getSafeUri().asString()).setWindow(TOP_WINDOW).inject();

        editorRegistry.registerDefaultEditor(esbXmlFileType, xmlEditorProvider);
        fileTypeRegistry.registerFileType(esbXmlFileType);

        editorRegistry.register(esbXmlFileType, xmlEditorProvider);
    }

    @Inject
    public void initActions(LocalizationConstant locale,
                            WSO2Resources wso2Resources,
                            ResourceProvider resourceProvider,
                            ActionManager actionManager,
                            ImportSynapseAction importSynapseAction,
                            CreateEndpointAction createEndpointAction,
                            CreateSequenceAction createSequenceAction,
                            CreateProxyServiceAction createProxyServiceAction,
                            CreateLocalEntryAction createLocalEntryAction) {

        DefaultActionGroup wso2MainMenu = (DefaultActionGroup)actionManager.getAction(GROUP_MAIN_MENU);
        DefaultActionGroup wso2ContextMenu = (DefaultActionGroup)actionManager.getAction(GROUP_MAIN_CONTEXT_MENU);

        DefaultActionGroup wso2MainGroup = new DefaultActionGroup(locale.wso2MainActionTitle(), true, actionManager);
        actionManager.registerAction(WSO2_MAIN_ACTION_GROUP, wso2MainGroup);

        wso2MainGroup.getTemplatePresentation().setIcon(wso2Resources.wso2GroupIcon());

        DefaultActionGroup wso2ActionGroup = new WSO2ProjectActionGroup(actionManager, resourceProvider);
        actionManager.registerAction(WSO2_ACTION_GROUP, wso2ActionGroup);

        DefaultActionGroup wso2NewGroup = new DefaultActionGroup(locale.wso2ActionNew(), true, actionManager);
        actionManager.registerAction(WSO2_NEW_RESOURCE_GROUP, wso2NewGroup);

        DefaultActionGroup wso2ImportGroup = new DefaultActionGroup(locale.wso2ImportActionTitle(), true, actionManager);
        wso2ImportGroup.getTemplatePresentation().setIcon(wso2Resources.uploadIcon());
        actionManager.registerAction(WSO2_IMPORT_RESOURCE_GROUP, wso2ImportGroup);

        actionManager.registerAction(IMPORT_SYNAPSE_ACTION, importSynapseAction);
        actionManager.registerAction(CREATE_ENDPOINT_ACTION, createEndpointAction);
        actionManager.registerAction(CREATE_SEQUENCE_ACTION, createSequenceAction);
        actionManager.registerAction(CREATE_PROXY_SERVICE_ACTION, createProxyServiceAction);
        actionManager.registerAction(CREATE_LOCAL_ENTRY_ACTION, createLocalEntryAction);

        wso2ActionGroup.add(wso2NewGroup);
        wso2ActionGroup.add(wso2ImportGroup);

        wso2MainGroup.add(wso2ActionGroup);

        wso2NewGroup.add(createEndpointAction);
        wso2NewGroup.add(createSequenceAction);
        wso2NewGroup.add(createProxyServiceAction);
        wso2NewGroup.add(createLocalEntryAction);

        wso2ImportGroup.add(importSynapseAction);

        wso2MainMenu.add(wso2MainGroup, LAST);

        wso2ContextMenu.add(wso2MainGroup, FIRST);
    }
}