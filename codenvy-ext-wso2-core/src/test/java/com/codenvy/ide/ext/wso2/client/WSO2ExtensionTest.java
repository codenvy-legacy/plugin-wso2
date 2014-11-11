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

import com.codenvy.ide.api.action.ActionManager;
import com.codenvy.ide.api.action.DefaultActionGroup;
import com.codenvy.ide.api.app.AppContext;
import com.codenvy.ide.api.editor.EditorRegistry;
import com.codenvy.ide.api.filetypes.FileType;
import com.codenvy.ide.api.filetypes.FileTypeRegistry;
import com.codenvy.ide.api.icon.Icon;
import com.codenvy.ide.api.icon.IconRegistry;
import com.codenvy.ide.api.projecttype.wizard.ProjectTypeWizardRegistry;
import com.codenvy.ide.api.projecttype.wizard.ProjectWizard;
import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.EditorResources.EditorCSS;
import com.codenvy.ide.ext.wso2.client.WSO2Resources.WSO2Style;
import com.codenvy.ide.ext.wso2.client.action.CreateEndpointAction;
import com.codenvy.ide.ext.wso2.client.action.CreateLocalEntryAction;
import com.codenvy.ide.ext.wso2.client.action.CreateProxyServiceAction;
import com.codenvy.ide.ext.wso2.client.action.CreateSequenceAction;
import com.codenvy.ide.ext.wso2.client.action.ImportSynapseAction;
import com.codenvy.ide.ext.wso2.client.editor.text.XmlEditorProvider;
import com.codenvy.ide.ext.wso2.client.wizard.project.WSO2PagePresenter;
import com.google.inject.Provider;
import com.googlecode.gwt.test.Mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.codenvy.ide.api.action.IdeActions.GROUP_MAIN_CONTEXT_MENU;
import static com.codenvy.ide.api.action.IdeActions.GROUP_MAIN_MENU;
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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public class WSO2ExtensionTest {

    @Mock
    private WSO2Resources               wso2Resources;
    @Mock
    private EditorResources             editorResources;
    @Mock
    private Provider<WSO2PagePresenter> wso2PagePresenterProvider;
    @Mock
    private ProjectTypeWizardRegistry   projectTypeWizardRegistry;
    @Mock
    private ProjectWizard               projectWizard;
    @Mock
    private IconRegistry                iconRegistry;
    @Mock
    private WSO2Style                   wso2Style;
    @Mock
    private EditorCSS                   editorCSS;
    @Mock
    private Icon                        icon;

    private WSO2Extension extension;

    @Before
    public void setUp() throws Exception {
        when(wso2Resources.wso2Style()).thenReturn(wso2Style);
        when(editorResources.editorCSS()).thenReturn(editorCSS);

        extension = new WSO2Extension(wso2Resources,
                                      editorResources,
                                      wso2PagePresenterProvider,
                                      projectTypeWizardRegistry,
                                      projectWizard,
                                      iconRegistry);
    }

    @Test
    public void styleShouldBeInjected() throws Exception {
        verify(wso2Resources).wso2Style();
        verify(wso2Style).ensureInjected();
    }

    @Test
    public void resourcesShouldBeInjected() throws Exception {
        verify(editorResources).editorCSS();
        verify(editorCSS).ensureInjected();
    }

    @Test
    public void projectWizardShouldAdded() throws Exception {
        verify(projectWizard).addPage(wso2PagePresenterProvider);
        verify(projectTypeWizardRegistry).addWizard(ESB_CONFIGURATION_PROJECT_ID, projectWizard);
    }

    @Test
    public void iconsShouldBeRegistered() throws Exception {
        verify(iconRegistry, times(4)).registerIcon(any(Icon.class));

        verify(wso2Resources).xmlIcon();
        verify(wso2Resources, times(3)).newProjectIcon();
    }

    @Test
    public void xmlEditorShouldBeInit() throws Exception {
        EditorRegistry editorRegistry = mock(EditorRegistry.class);
        FileTypeRegistry fileTypeRegistry = mock(FileTypeRegistry.class);
        XmlEditorProvider xmlEditorProvider = mock(XmlEditorProvider.class);
        FileType fileType = mock(FileType.class);

        extension.initXmlEditor(editorRegistry, fileTypeRegistry, xmlEditorProvider, fileType);

        verify(editorRegistry).registerDefaultEditor(fileType, xmlEditorProvider);
        verify(fileTypeRegistry).registerFileType(fileType);
        verify(editorRegistry).register(fileType, xmlEditorProvider);
    }

    @Test
    public void actionsShouldBeInit() throws Exception {
        LocalizationConstant locale = mock(LocalizationConstant.class);
        WSO2Resources wso2Resources = mock(WSO2Resources.class);
        AppContext appContext = mock(AppContext.class);
        ActionManager actionManager = mock(ActionManager.class);
        ImportSynapseAction importSynapseAction = mock(ImportSynapseAction.class);
        CreateEndpointAction createEndpointAction = mock(CreateEndpointAction.class);
        CreateSequenceAction createSequenceAction = mock(CreateSequenceAction.class);
        CreateProxyServiceAction createProxyServiceAction = mock(CreateProxyServiceAction.class);
        CreateLocalEntryAction createLocalEntryAction = mock(CreateLocalEntryAction.class);

        DefaultActionGroup wso2MainMenu = spy(new DefaultActionGroup(null));
        DefaultActionGroup wso2ContextMenu = spy(new DefaultActionGroup(null));

        DefaultActionGroup wso2MainGroup = spy(new DefaultActionGroup(null));

        when(actionManager.getAction(GROUP_MAIN_MENU)).thenReturn(wso2MainMenu);
        when(actionManager.getAction(GROUP_MAIN_CONTEXT_MENU)).thenReturn(wso2ContextMenu);

        extension.initActions(locale,
                              wso2Resources,
                              appContext,
                              actionManager,
                              importSynapseAction,
                              createEndpointAction,
                              createSequenceAction,
                              createProxyServiceAction,
                              createLocalEntryAction);

        verify(actionManager).getAction(GROUP_MAIN_MENU);
        verify(actionManager).getAction(GROUP_MAIN_CONTEXT_MENU);

        verify(locale).wso2MainActionTitle();
        verify(actionManager).registerAction(eq(WSO2_MAIN_ACTION_GROUP), any(DefaultActionGroup.class));

        verify(wso2Resources).wso2GroupIcon();

        verify(actionManager).registerAction(eq(WSO2_ACTION_GROUP), any(DefaultActionGroup.class));
        verify(actionManager).registerAction(eq(WSO2_NEW_RESOURCE_GROUP), any(DefaultActionGroup.class));
        verify(actionManager).registerAction(eq(WSO2_IMPORT_RESOURCE_GROUP), any(DefaultActionGroup.class));

        verify(actionManager).registerAction(IMPORT_SYNAPSE_ACTION, importSynapseAction);
        verify(actionManager).registerAction(CREATE_ENDPOINT_ACTION, createEndpointAction);
        verify(actionManager).registerAction(CREATE_SEQUENCE_ACTION, createSequenceAction);
        verify(actionManager).registerAction(CREATE_PROXY_SERVICE_ACTION, createProxyServiceAction);
        verify(actionManager).registerAction(CREATE_LOCAL_ENTRY_ACTION, createLocalEntryAction);
    }

}