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
package com.codenvy.ide.ext.wso2.client.wizard.files;

import com.codenvy.ide.api.editor.EditorAgent;
import com.codenvy.ide.api.resources.FileType;
import com.codenvy.ide.api.resources.ResourceProvider;
import com.codenvy.ide.api.ui.wizard.Wizard;
import com.codenvy.ide.api.ui.wizard.WizardPage;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.codenvy.ide.ext.wso2.client.wizard.files.view.CreateResourceView;
import com.codenvy.ide.resources.model.File;
import com.codenvy.ide.resources.model.Folder;
import com.codenvy.ide.resources.model.Project;
import com.codenvy.ide.resources.model.Resource;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.codenvy.ide.ext.wso2.client.wizard.files.AbstractCreateResourcePage.MAIN_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.client.wizard.files.AbstractCreateResourcePage.SRC_NAME;
import static com.codenvy.ide.ext.wso2.client.wizard.files.AbstractCreateResourcePage.SYNAPSE_CONFIG_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_XML_EXTENSION;
import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_XML_MIME_TYPE;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Answers.RETURNS_MOCKS;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * The basic test for testing create WSO2 resource pages.
 *
 * @author Andrey Plotnikov
 */
@Listeners(value = {MockitoTestNGListener.class})
public abstract class AbstractCreateResourcePageTest {

    public static final String EMPTY_TEXT         = "";
    public static final String SOME_TEXT          = "some text";
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
    protected CreateResourceView         view;
    @Mock(answer = RETURNS_MOCKS)
    protected WSO2Resources              resources;
    @Mock(answer = RETURNS_DEEP_STUBS)
    protected FileType                   fileType;
    protected String                     parentFolderName;
    protected AbstractCreateResourcePage page;

    @BeforeMethod
    public void setUp() throws Exception {
        when(resourceProvider.getActiveProject()).thenReturn(activeProject);

        when(activeProject.getChildren()).thenReturn(Collections.<Resource>createArray(src));
        when(src.getChildren()).thenReturn(Collections.<Resource>createArray(main));
        when(main.getChildren()).thenReturn(Collections.<Resource>createArray(synapse_config));
        when(synapse_config.getChildren()).thenReturn(Collections.<Resource>createArray(parentFolder));

        when(src.getName()).thenReturn(SRC_NAME);
        when(main.getName()).thenReturn(MAIN_FOLDER_NAME);
        when(synapse_config.getName()).thenReturn(SYNAPSE_CONFIG_FOLDER_NAME);
        when(parentFolder.getName()).thenReturn(parentFolderName);

        page.setUpdateDelegate(delegate);

        when(fileType.getMimeTypes().get(0)).thenReturn(ESB_XML_MIME_TYPE);
        when(fileType.getExtension()).thenReturn(ESB_XML_EXTENSION);
    }

    @Test
    public void pageShouldBeNotCompletedWhenFileWithTheSameNameIsExisted() throws Exception {
        page.go(container);

        when(view.getResourceName()).thenReturn(SOME_TEXT);

        Resource file = mock(Resource.class);
        when(file.getName()).thenReturn(FULL_RESOURCE_NAME);

        when(parentFolder.getChildren()).thenReturn(Collections.<Resource>createArray(file));

        page.onValueChanged();

        assertEquals(page.isCompleted(), false);
    }

    @Test(dataProvider = "resource-names")
    public void pageShouldBeNotCompletedWhenFileNameIsIncorrect(String resourceName) throws Exception {
        page.go(container);

        when(view.getResourceName()).thenReturn(resourceName);
        page.onValueChanged();

        assertEquals(page.isCompleted(), false);
    }

    @DataProvider(name = "resource-names")
    public Object[][] resourceNames() {
        return new Object[][]{{""}, {"$projectName"}, {"project%Name"}, {"projectName!"}};
    }

    @Test
    public void pageShouldBeCompleted() throws Exception {
        page.go(container);

        when(view.getResourceName()).thenReturn(SOME_TEXT);
        page.onValueChanged();

        assertEquals(page.isCompleted(), false);
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

        verify(activeProject).getChildren();
        verify(src).getChildren();
        verify(main).getChildren();
        verify(synapse_config).getChildren();
    }

    @Test
    public void controlsShouldBeUpdated() throws Exception {
        page.go(container);
        page.onValueChanged();

        verify(delegate).updateControls();
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