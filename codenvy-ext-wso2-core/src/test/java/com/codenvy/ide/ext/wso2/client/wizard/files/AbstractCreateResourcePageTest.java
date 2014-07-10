/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.ext.wso2.client.wizard.files;

import com.codenvy.ide.api.editor.EditorAgent;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.resources.FileType;
import com.codenvy.ide.api.resources.ResourceProvider;
import com.codenvy.ide.api.resources.model.File;
import com.codenvy.ide.api.resources.model.Folder;
import com.codenvy.ide.api.resources.model.Project;
import com.codenvy.ide.api.resources.model.Resource;
import com.codenvy.ide.api.ui.wizard.Wizard;
import com.codenvy.ide.api.ui.wizard.WizardPage;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.codenvy.ide.ext.wso2.client.wizard.files.view.CreateResourceView;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_XML_EXTENSION;
import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_XML_MIME_TYPE;
import static com.codenvy.ide.ext.wso2.shared.Constants.MAIN_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SRC_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SYNAPSE_CONFIG_FOLDER_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Answers.RETURNS_MOCKS;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The basic test for testing create WSO2 resource pages.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitriy Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractCreateResourcePageTest {

    public static final String EMPTY_TEXT         = "";
    public static final String SOME_TEXT          = "someText";
    public static final String FULL_RESOURCE_NAME = SOME_TEXT + '.' + ESB_XML_EXTENSION;

    @Mock
    private Wizard.UpdateDelegate delegate;
    @Mock
    private Project               activeProject;
    @Mock
    private Folder                src;
    @Mock
    private Folder                main;
    @Mock
    private Folder                synapse_config;

    @Mock(answer = RETURNS_DEEP_STUBS)
    protected Folder                     parentFolder;
    @Mock
    protected AcceptsOneWidget           container;
    @Mock
    protected ResourceProvider           resourceProvider;
    @Mock
    protected EditorAgent                editorAgent;
    @Mock
    protected LocalizationConstant       locale;
    @Mock
    protected NotificationManager        notificationManager;
    @Mock
    protected CreateResourceView         view;
    @Mock(answer = RETURNS_MOCKS)
    protected WSO2Resources              resources;
    @Mock(answer = RETURNS_DEEP_STUBS)
    protected FileType                   fileType;
    protected String                     parentFolderName;
    protected AbstractCreateResourcePage page;

    @Before
    public void setUp() throws Exception {
        verify(view).setDelegate((CreateResourceView.ActionDelegate)anyObject());
        verify(view).setResourceNameTitle(anyString());

        when(resourceProvider.getActiveProject()).thenReturn(activeProject);

        when(src.getName()).thenReturn(SRC_FOLDER_NAME);
        when(main.getName()).thenReturn(MAIN_FOLDER_NAME);
        when(synapse_config.getName()).thenReturn(SYNAPSE_CONFIG_FOLDER_NAME);
        when(parentFolder.getName()).thenReturn(parentFolderName);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return src;
            }
        }).when(activeProject).findChildByName(anyString());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return main;
            }
        }).when(src).findChildByName(anyString());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return synapse_config;
            }
        }).when(main).findChildByName(anyString());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return parentFolder;
            }
        }).when(synapse_config).findChildByName(anyString());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                AsyncCallback callback = (AsyncCallback)args[1];
                Folder folder = (Folder)args[0];
                callback.onSuccess(folder);
                return null;
            }
        }).when(activeProject).refreshChildren((Folder)anyObject(), (AsyncCallback)anyObject());

        page.setUpdateDelegate(delegate);

        when(fileType.getMimeTypes().get(0)).thenReturn(ESB_XML_MIME_TYPE);
        when(fileType.getExtension()).thenReturn(ESB_XML_EXTENSION);
    }

    @Test
    public void folderTreeShouldBeCreatedWhenExistOnlyRootFolder() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Resource answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(activeProject).findChildByName(anyString());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(src).findChildByName(anyString());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(main).findChildByName(anyString());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(synapse_config).findChildByName(anyString());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                AsyncCallback callBack = (AsyncCallback)args[2];
                callBack.onSuccess(src);
                return null;
            }
        }).when(activeProject).createFolder(eq(activeProject), eq(SRC_FOLDER_NAME), (AsyncCallback)anyObject());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                AsyncCallback callBack = (AsyncCallback)args[2];
                callBack.onSuccess(main);
                return null;
            }
        }).when(activeProject).createFolder(eq(src), eq(MAIN_FOLDER_NAME), (AsyncCallback)anyObject());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                AsyncCallback callBack = (AsyncCallback)args[2];
                callBack.onSuccess(synapse_config);
                return null;
            }
        }).when(activeProject).createFolder(eq(main), eq(SYNAPSE_CONFIG_FOLDER_NAME), (AsyncCallback)anyObject());

        page.go(container);

        verify(activeProject).createFolder(eq(activeProject), eq(SRC_FOLDER_NAME), (AsyncCallback)anyObject());
        verify(activeProject).createFolder(eq(src), eq(MAIN_FOLDER_NAME), (AsyncCallback)anyObject());
        verify(activeProject).createFolder(eq(main), eq(SYNAPSE_CONFIG_FOLDER_NAME), (AsyncCallback)anyObject());
        verify(activeProject).createFolder(eq(synapse_config), eq(parentFolderName), (AsyncCallback)anyObject());

        verify(container).setWidget(view);
        verify(resourceProvider).getActiveProject();
    }

    @Test
    public void folderTreeShouldBeCreatedWhenExistSrcLevelFolder() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return src;
            }
        }).when(activeProject).findChildByName(anyString());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(src).findChildByName(anyString());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(main).findChildByName(anyString());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(synapse_config).findChildByName(anyString());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                AsyncCallback callBack = (AsyncCallback)args[2];
                callBack.onSuccess(main);
                return null;
            }
        }).when(activeProject).createFolder(eq(src), eq(MAIN_FOLDER_NAME), (AsyncCallback)anyObject());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                AsyncCallback callBack = (AsyncCallback)args[2];
                callBack.onSuccess(synapse_config);
                return null;
            }
        }).when(activeProject).createFolder(eq(main), eq(SYNAPSE_CONFIG_FOLDER_NAME), (AsyncCallback)anyObject());

        page.go(container);

        verify(activeProject).createFolder(eq(src), eq(MAIN_FOLDER_NAME), (AsyncCallback)anyObject());
        verify(activeProject).createFolder(eq(main), eq(SYNAPSE_CONFIG_FOLDER_NAME), (AsyncCallback)anyObject());
        verify(activeProject).createFolder(eq(synapse_config), eq(parentFolderName), (AsyncCallback)anyObject());

        verify(activeProject, never()).createFolder(eq(activeProject), eq(SRC_FOLDER_NAME), (AsyncCallback)anyObject());

        verify(container).setWidget(view);
        verify(resourceProvider).getActiveProject();
    }

    @Test
    public void folderTreeShouldBeCreatedWhenExistMainLevelFolder() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return src;
            }
        }).when(activeProject).findChildByName(anyString());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return main;
            }
        }).when(src).findChildByName(anyString());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(main).findChildByName(anyString());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(synapse_config).findChildByName(anyString());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                AsyncCallback callBack = (AsyncCallback)args[2];
                callBack.onSuccess(synapse_config);
                return null;
            }
        }).when(activeProject).createFolder(eq(main), eq(SYNAPSE_CONFIG_FOLDER_NAME), (AsyncCallback)anyObject());

        page.go(container);

        verify(activeProject).createFolder(eq(main), eq(SYNAPSE_CONFIG_FOLDER_NAME), (AsyncCallback)anyObject());
        verify(activeProject).createFolder(eq(synapse_config), eq(parentFolderName), (AsyncCallback)anyObject());

        verify(activeProject, never()).createFolder(eq(activeProject), eq(SRC_FOLDER_NAME), (AsyncCallback)anyObject());
        verify(activeProject, never()).createFolder(eq(src), eq(MAIN_FOLDER_NAME), (AsyncCallback)anyObject());

        verify(container).setWidget(view);
        verify(resourceProvider).getActiveProject();
    }

    @Test
    public void folderTreeShouldBeCreatedWhenExistSynapseConfigLevelFolder() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return src;
            }
        }).when(activeProject).findChildByName(anyString());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return main;
            }
        }).when(src).findChildByName(anyString());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return synapse_config;
            }
        }).when(main).findChildByName(anyString());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(synapse_config).findChildByName(anyString());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                AsyncCallback callBack = (AsyncCallback)args[2];
                callBack.onSuccess(synapse_config);
                return null;
            }
        }).when(activeProject).createFolder(eq(main), eq(SYNAPSE_CONFIG_FOLDER_NAME), (AsyncCallback)anyObject());

        page.go(container);

        verify(activeProject).createFolder(eq(synapse_config), eq(parentFolderName), (AsyncCallback)anyObject());

        verify(activeProject, never()).createFolder(eq(activeProject), eq(SRC_FOLDER_NAME), (AsyncCallback)anyObject());
        verify(activeProject, never()).createFolder(eq(src), eq(MAIN_FOLDER_NAME), (AsyncCallback)anyObject());
        verify(activeProject, never()).createFolder(eq(main), eq(SYNAPSE_CONFIG_FOLDER_NAME), (AsyncCallback)anyObject());

        verify(container).setWidget(view);
        verify(resourceProvider).getActiveProject();
    }

    @Test
    public void pageShouldBeNotCompletedWhenFileWithTheSameNameIsExisted() throws Exception {
        page.go(container);

        when(view.getResourceName()).thenReturn(SOME_TEXT);

        Folder folder = mock(Folder.class);
        when(folder.getName()).thenReturn(FULL_RESOURCE_NAME);

        when(parentFolder.getChildren()).thenReturn(Collections.<Resource>createArray(folder));

        page.onValueChanged();

        assertEquals(false, page.isCompleted());
    }

    @Test
    public void pageShouldBeNotCompletedWhenParentFolderIsNull() throws Exception {
        when(view.getResourceName()).thenReturn(SOME_TEXT);

        assertEquals(false, page.isCompleted());
    }

    @Test
    public void pageShouldBeNotCompletedWhenFileNameIsIncorrect() throws Exception {
        List<String> names = Arrays.asList("", "$projectName", "project%Name", "projectName!");

        page.go(container);

        for (String name : names) {
            when(view.getResourceName()).thenReturn(name);
            page.onValueChanged();

            assertEquals(false, page.isCompleted());
        }
    }

    @Test
    public void pageShouldBeCompleted() throws Exception {
        page.go(container);

        when(view.getResourceName()).thenReturn(SOME_TEXT);
        page.onValueChanged();

        assertEquals(true, page.isCompleted());
    }

    @Test
    public void pageFieldsShouldBeCleanedWhenPageIsOpening() throws Exception {
        page.focusComponent();

        verify(view).setResourceName(eq(EMPTY_TEXT));
    }

    @Test
    public void viewShouldBeShown() throws Exception {
        page.go(container);

        verify(container).setWidget(view);
    }

    @Test
    public void parentFolderShouldBeFound() throws Exception {
        page.go(container);

        verify(activeProject).findChildByName(anyString());
        verify(src).findChildByName(anyString());
        verify(main).findChildByName(anyString());
        verify(synapse_config).findChildByName(anyString());

        verify(activeProject, never()).createFolder(eq(activeProject), eq(SRC_FOLDER_NAME), (AsyncCallback)anyObject());
        verify(activeProject, never()).createFolder(eq(src), eq(MAIN_FOLDER_NAME), (AsyncCallback)anyObject());
        verify(activeProject, never()).createFolder(eq(main), eq(SYNAPSE_CONFIG_FOLDER_NAME), (AsyncCallback)anyObject());
        verify(activeProject, never()).createFolder(eq(synapse_config), eq(parentFolderName), (AsyncCallback)anyObject());

        verify(container).setWidget(view);
        verify(resourceProvider).getActiveProject();
    }

    @Test
    public void controlsShouldBeUpdated() throws Exception {
        page.go(container);
        page.onValueChanged();

        verify(delegate).updateControls();
    }

    @Test
    public void incorrectNameNoticeShouldBeShown() throws Exception {
        when(view.getResourceName()).thenReturn("projectName!");
        when(locale.wizardFileResourceNoticeIncorrectName()).thenReturn(SOME_TEXT);

        page.go(container);
        page.onValueChanged();

        assertEquals(SOME_TEXT, page.getNotice());

        verify(locale).wizardFileResourceNoticeIncorrectName();
    }

    @Test
    public void fileExistsNoticeShouldBeShown() throws Exception {
        when(view.getResourceName()).thenReturn(SOME_TEXT);
        when(locale.wizardFileResourceNoticeFileExists()).thenReturn(SOME_TEXT);

        Folder folder = mock(Folder.class);
        when(folder.getName()).thenReturn(FULL_RESOURCE_NAME);

        when(parentFolder.getChildren()).thenReturn(Collections.<Resource>createArray(folder));

        page.go(container);
        page.onValueChanged();

        assertEquals(SOME_TEXT, page.getNotice());

        verify(locale).wizardFileResourceNoticeFileExists();
    }

    @Test
    public void emptyNoticeShouldBeShown() throws Exception {
        when(view.getResourceName()).thenReturn("projectName");

        page.go(container);
        page.onValueChanged();

        assertNull(page.getNotice());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void onFailureMethodInCommitCallbackShouldBeExecuted() throws Exception {
        final Throwable throwable = mock(Throwable.class);
        when(view.getResourceName()).thenReturn(SOME_TEXT);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                AsyncCallback<File> callback = (AsyncCallback<File>)arguments[4];
                callback.onFailure(throwable);
                return callback;
            }
        }).when(activeProject).createFile((Folder)anyObject(), anyString(), anyString(), anyString(), (AsyncCallback<File>)anyObject());
        WizardPage.CommitCallback commitCallback = mock(WizardPage.CommitCallback.class);

        page.go(container);

        page.commit(commitCallback);

        verify(activeProject).createFile(eq(parentFolder), eq(FULL_RESOURCE_NAME), anyString(), eq(ESB_XML_MIME_TYPE),
                                         (AsyncCallback<File>)anyObject());
        verify(commitCallback).onFailure(throwable);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void onSuccessMethodInCommitCallbackShouldBeExecuted() throws Exception {
        final File file = mock(File.class);
        when(view.getResourceName()).thenReturn(SOME_TEXT);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                AsyncCallback<File> callback = (AsyncCallback<File>)arguments[4];
                callback.onSuccess(file);
                return callback;
            }
        }).when(activeProject).createFile((Folder)anyObject(), anyString(), anyString(), anyString(), (AsyncCallback<File>)anyObject());
        WizardPage.CommitCallback commitCallback = mock(WizardPage.CommitCallback.class);

        page.go(container);

        page.commit(commitCallback);

        verify(activeProject).createFile(eq(parentFolder), eq(FULL_RESOURCE_NAME), anyString(), eq(ESB_XML_MIME_TYPE),
                                         (AsyncCallback<File>)anyObject());
        verify(commitCallback).onSuccess();
        verify(editorAgent).openEditor(eq(file));
    }
}