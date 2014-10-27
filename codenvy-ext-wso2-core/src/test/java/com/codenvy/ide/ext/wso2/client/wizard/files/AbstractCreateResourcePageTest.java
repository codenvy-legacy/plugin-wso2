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
package com.codenvy.ide.ext.wso2.client.wizard.files;

import com.codenvy.api.project.gwt.client.ProjectServiceClient;
import com.codenvy.api.project.shared.dto.ItemReference;
import com.codenvy.api.project.shared.dto.TreeElement;
import com.codenvy.ide.api.app.AppContext;
import com.codenvy.ide.api.app.CurrentProject;
import com.codenvy.ide.api.editor.EditorAgent;
import com.codenvy.ide.api.event.RefreshProjectTreeEvent;
import com.codenvy.ide.api.filetypes.FileType;
import com.codenvy.ide.api.notification.Notification;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.projecttree.generic.FileNode;
import com.codenvy.ide.api.wizard.WizardContext;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.codenvy.ide.ext.wso2.client.commons.WSO2AsyncRequestCallback;
import com.codenvy.ide.ext.wso2.client.wizard.files.view.CreateResourceView;
import com.codenvy.ide.rest.AsyncRequestCallback;
import com.codenvy.ide.rest.DtoUnmarshallerFactory;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;

import static com.codenvy.ide.api.wizard.Wizard.UpdateDelegate;
import static com.codenvy.ide.api.wizard.WizardPage.CommitCallback;
import static com.codenvy.ide.collections.Collections.createArray;
import static com.codenvy.ide.ext.wso2.TestUtil.invokeOnFailureCallbackMethod;
import static com.codenvy.ide.ext.wso2.TestUtil.invokeOnSuccessCallbackMethod;
import static com.codenvy.ide.ext.wso2.client.wizard.files.AbstractCreateResourcePage.DEPTH;
import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_XML_EXTENSION;
import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_XML_MIME_TYPE;
import static com.codenvy.ide.ext.wso2.shared.Constants.MAIN_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SRC_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SYNAPSE_CONFIG_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SYNAPSE_CONFIG_PATH;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
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

    private static final String PATH_TO_PROJECT       = "path";
    private static final String PATH_TO_PARENT_FOLDER = PATH_TO_PROJECT + SYNAPSE_CONFIG_PATH;

    protected static final String SOME_TEXT                = "someText";
    private static final   String SOME_TEXT_WITH_EXTENSION = "someText.xml";
    private static final   String ERROR                    = "error";

    @Captor
    private ArgumentCaptor<WSO2AsyncRequestCallback<TreeElement>>          treeCallbackCaptor;
    @Captor
    private ArgumentCaptor<WSO2AsyncRequestCallback<ItemReference>>        createFolderCallBackCaptor;
    @Captor
    private ArgumentCaptor<WSO2AsyncRequestCallback<Array<ItemReference>>> childrenCallbackCaptor;
    @Captor
    private ArgumentCaptor<WSO2AsyncRequestCallback<ItemReference>>        createFileCallbackCaptor;
    @Captor
    private ArgumentCaptor<Notification>                                   notificationCaptor;

    @Mock
    private TreeElement rootFolder;
    @Mock
    private TreeElement srcFolder;
    @Mock
    private TreeElement mainFolder;
    @Mock
    private TreeElement synapseFolder;
    @Mock
    private TreeElement parentFolder;

    @Mock
    private ItemReference rootFolderItem;
    @Mock
    private ItemReference srcFolderItem;
    @Mock
    private ItemReference mainFolderItem;
    @Mock
    private ItemReference synapseFolderItem;
    @Mock
    private ItemReference parentFolderItem;
    @Mock
    private ItemReference item;

    @Mock
    private Throwable        throwable;
    @Mock
    private UpdateDelegate   delegate;
    @Mock
    private CommitCallback   commitCallback;
    @Mock
    private AcceptsOneWidget container;

    @Mock
    protected AppContext             appContext;
    @Mock
    protected ProjectServiceClient   projectServiceClient;
    @Mock
    protected EditorAgent            editorAgent;
    @Mock
    protected EventBus               eventBus;
    @Mock
    protected LocalizationConstant   locale;
    @Mock
    protected NotificationManager    notificationManager;
    @Mock
    protected DtoUnmarshallerFactory dtoUnmarshallerFactory;
    @Mock
    protected TextResource           textResource;

    @Mock
    protected CreateResourceView view;
    @Mock(answer = RETURNS_DEEP_STUBS)
    protected CurrentProject     currentProject;
    @Mock(answer = RETURNS_DEEP_STUBS)
    protected WSO2Resources      resources;
    @Mock(answer = RETURNS_DEEP_STUBS)
    protected FileType           fileType;

    protected String                     parentFolderName;
    protected AbstractCreateResourcePage page;

    @Before
    public void setUp() throws Exception {
        when(appContext.getCurrentProject()).thenReturn(currentProject);

        page.setUpdateDelegate(delegate);

        when(fileType.getMimeTypes().get(0)).thenReturn(ESB_XML_MIME_TYPE);
        when(fileType.getExtension()).thenReturn(ESB_XML_EXTENSION);

        when(parentFolder.getNode()).thenReturn(parentFolderItem);
        when(parentFolderItem.getPath()).thenReturn(SYNAPSE_CONFIG_PATH);
        when(view.getResourceName()).thenReturn(SOME_TEXT);
    }

    @Test
    public void viewShouldBePrepared() throws Exception {
        verify(view).setDelegate(page);
        verify(view).setResourceNameTitle(SOME_TEXT);
    }

    @Test
    public void parentFolderShouldBeCreatedWhenExistOnlyRootFolder() throws Exception {
        when(rootFolder.getChildren()).thenReturn(Collections.EMPTY_LIST);

        verifyCreatingParentFolder();
    }

    private void verifyCreatingParentFolder() throws Exception {
        when(item.getPath()).thenReturn(SYNAPSE_CONFIG_PATH);
        when(view.getResourceName()).thenReturn(SOME_TEXT);
        when(currentProject.getProjectDescription().getPath()).thenReturn(PATH_TO_PROJECT);

        page.go(container);

        verify(projectServiceClient).getTree(eq(PATH_TO_PROJECT), eq(DEPTH), treeCallbackCaptor.capture());

        executeGetTreeCallback();

        page.commit(commitCallback);

        verify(projectServiceClient).createFolder(eq(PATH_TO_PROJECT + SYNAPSE_CONFIG_PATH + parentFolderName),
                                                  createFolderCallBackCaptor.capture());

        executeCreateFolderCallback();

        verify(projectServiceClient).createFile(eq(SYNAPSE_CONFIG_PATH),
                                                eq(SOME_TEXT_WITH_EXTENSION),
                                                eq(SOME_TEXT),
                                                eq(ESB_XML_MIME_TYPE),
                                                createFileCallbackCaptor.capture());
    }

    protected void executeGetTreeCallback() throws Exception {
        WSO2AsyncRequestCallback<TreeElement> treeCallback = treeCallbackCaptor.getValue();

        invokeOnSuccessCallbackMethod(treeCallback.getClass(), treeCallback, rootFolder);
    }

    protected void executeCreateFolderCallback() throws Exception {
        WSO2AsyncRequestCallback<ItemReference> createFolderCallback = createFolderCallBackCaptor.getValue();

        invokeOnSuccessCallbackMethod(createFolderCallback.getClass(), createFolderCallback, item);
    }

    @Test
    public void parentFolderShouldBeCreatedWhenExistSrcLevelFolder() throws Exception {
        configureMock(rootFolder, srcFolder, srcFolderItem, SRC_FOLDER_NAME);

        verifyCreatingParentFolder();
    }

    protected void configureMock(TreeElement parentFolder, TreeElement currentFolder, ItemReference item, String folderName) {
        when(parentFolder.getChildren()).thenReturn(Arrays.asList(currentFolder));
        when(currentFolder.getNode()).thenReturn(item);
        when(item.getName()).thenReturn(folderName);
    }

    @Test
    public void parentFolderShouldBeCreatedWhenExistMainLevelFolder() throws Exception {
        configureMock(rootFolder, srcFolder, srcFolderItem, SRC_FOLDER_NAME);
        configureMock(srcFolder, mainFolder, mainFolderItem, MAIN_FOLDER_NAME);

        verifyCreatingParentFolder();
    }

    @Test
    public void parentFolderShouldBeCreatedWhenExistSynapseLevelFolder() throws Exception {
        configureMock(rootFolder, srcFolder, srcFolderItem, SRC_FOLDER_NAME);
        configureMock(srcFolder, mainFolder, mainFolderItem, MAIN_FOLDER_NAME);
        configureMock(mainFolder, synapseFolder, synapseFolderItem, SYNAPSE_CONFIG_FOLDER_NAME);

        verifyCreatingParentFolder();
    }

    @Test
    public void parentFolderShouldBeFound() throws Exception {
        configureParentFolder();

        page.go(container);

        verify(projectServiceClient).getTree(eq(PATH_TO_PROJECT), eq(DEPTH), treeCallbackCaptor.capture());

        executeGetTreeCallback();

        verify(projectServiceClient).getChildren(eq(PATH_TO_PROJECT + SYNAPSE_CONFIG_PATH), childrenCallbackCaptor.capture());
    }

    private void configureParentFolder() {
        when(parentFolder.getNode().getPath()).thenReturn(PATH_TO_PARENT_FOLDER);
        when(currentProject.getProjectDescription().getPath()).thenReturn(PATH_TO_PROJECT);

        configureMock(rootFolder, srcFolder, srcFolderItem, SRC_FOLDER_NAME);
        configureMock(srcFolder, mainFolder, mainFolderItem, MAIN_FOLDER_NAME);
        configureMock(mainFolder, synapseFolder, synapseFolderItem, SYNAPSE_CONFIG_FOLDER_NAME);
        configureMock(synapseFolder, parentFolder, parentFolderItem, parentFolderName);
    }

    @Test
    public void fileShouldBeCreated() throws Exception {
        configureParentFolder();
        when(parentFolder.getNode().getPath()).thenReturn(PATH_TO_PARENT_FOLDER + parentFolderName);

        page.go(container);

        verify(projectServiceClient).getTree(eq(PATH_TO_PROJECT), eq(DEPTH), treeCallbackCaptor.capture());

        executeGetTreeCallback();

        page.commit(commitCallback);

        verify(projectServiceClient, never()).createFolder(anyString(), any(WSO2AsyncRequestCallback.class));

        verify(projectServiceClient).createFile(eq(PATH_TO_PROJECT + SYNAPSE_CONFIG_PATH + parentFolderName),
                                                eq(SOME_TEXT_WITH_EXTENSION),
                                                eq(SOME_TEXT),
                                                eq(ESB_XML_MIME_TYPE),
                                                createFileCallbackCaptor.capture());

        executeCreateFileCallback();

        verify(editorAgent).openEditor(any(FileNode.class));
        verify(eventBus).fireEvent(any(RefreshProjectTreeEvent.class));
        verify(commitCallback).onSuccess();
    }

    private void executeCreateFileCallback() throws Exception {
        WSO2AsyncRequestCallback<ItemReference> createFileCallback = createFileCallbackCaptor.getValue();

        invokeOnSuccessCallbackMethod(createFileCallback.getClass(), createFileCallback, item);
    }

    @Test
    public void parentFolderShouldNotBeCreatedBecauseProjectNotExist() throws Exception {
        when(currentProject.getProjectDescription().getPath()).thenReturn(PATH_TO_PROJECT);

        page.go(container);

        verify(projectServiceClient).getTree(eq(PATH_TO_PROJECT), eq(DEPTH), treeCallbackCaptor.capture());

        WSO2AsyncRequestCallback<TreeElement> treeCallback = treeCallbackCaptor.getValue();

        invokeOnSuccessCallbackMethod(treeCallback.getClass(), treeCallback, (TreeElement)null);

        verify(projectServiceClient, never()).getChildren(anyString(), any(WSO2AsyncRequestCallback.class));
    }

    @Test
    public void errorMessageShouldBeShownWhenFileExist() throws Exception {
        prepareItems("anotherText", SOME_TEXT, SOME_TEXT);

        assertThat(page.getNotice(), equalTo(ERROR));
        assertThat(page.isCompleted(), is(false));
        verify(locale).wizardFileResourceNoticeFileExists();
    }

    private void prepareItems(String valueOne, String valueTwo, String valueThree) throws Exception {
        ItemReference secondItem = mock(ItemReference.class);
        ItemReference thirdItem = mock(ItemReference.class);

        when(view.getResourceName()).thenReturn(SOME_TEXT);

        when(item.getName()).thenReturn(valueOne + '.' + ESB_XML_EXTENSION);
        when(secondItem.getName()).thenReturn(valueTwo + '.' + ESB_XML_EXTENSION);
        when(thirdItem.getName()).thenReturn(valueThree + '.' + ESB_XML_EXTENSION);

        when(currentProject.getProjectDescription().getPath()).thenReturn(PATH_TO_PROJECT);
        when(locale.wizardFileResourceNoticeFileExists()).thenReturn(ERROR);

        configureParentFolder();

        page.go(container);

        verify(projectServiceClient).getTree(eq(PATH_TO_PROJECT), eq(DEPTH), treeCallbackCaptor.capture());
        executeGetTreeCallback();

        verify(projectServiceClient).getChildren(eq(PATH_TO_PARENT_FOLDER), childrenCallbackCaptor.capture());

        executeChildrenCallback(item, secondItem, thirdItem);

        page.onValueChanged();
    }

    @Test
    public void errorMessageShouldNotBeShownWhenItemsHasDifferentNames() throws Exception {
        prepareItems("firstText", "anotherText", "text");

        page.onValueChanged();

        assertNull(page.getNotice());
        verify(locale, never()).wizardFileResourceNoticeFileExists();
    }

    @Test
    public void errorMessageShouldBeShownWhenItemNameIsIncorrect() throws Exception {
        when(view.getResourceName()).thenReturn("na*me");
        when(locale.wizardFileResourceNoticeIncorrectName()).thenReturn(ERROR);

        page.onValueChanged();

        assertThat(page.getNotice(), equalTo(ERROR));
        verify(locale).wizardFileResourceNoticeIncorrectName();
        verify(locale, never()).wizardFileResourceNoticeFileExists();
    }

    private void executeChildrenCallback(@Nonnull ItemReference... items) throws Exception {
        WSO2AsyncRequestCallback<Array<ItemReference>> childrenCallBack = childrenCallbackCaptor.getValue();

        invokeOnSuccessCallbackMethod(childrenCallBack.getClass(), childrenCallBack, createArray(items));
    }

    @Test
    public void noMessageShouldBeShownWhenPageCompleted() throws Exception {
        when(view.getResourceName()).thenReturn(SOME_TEXT);
        page.isCompleted();

        assertNull(page.getNotice());
        verify(locale, never()).wizardFileResourceNoticeFileExists();
        verify(locale, never()).wizardFileResourceNoticeIncorrectName();
    }

    @Test
    public void widgetShouldBeSet() throws Exception {
        page.go(container);

        verify(container).setWidget(view);
    }

    @Test
    public void componentShouldBeFocused() throws Exception {
        page.focusComponent();

        verify(view).setResourceName("");
    }

    @Test
    public void fileShouldNotBeCreatedIfProjectNotExist() throws Exception {
        when(appContext.getCurrentProject()).thenReturn(null);

        page.go(container);

        verify(container).setWidget(view);
        verify(projectServiceClient, never()).getTree(anyString(), anyInt(), any(AsyncRequestCallback.class));
    }

    @Test
    public void pageShouldNotBeCompletedWhenItemNameIsEmpty() throws Exception {
        when(view.getResourceName()).thenReturn("");

        assertThat(page.isCompleted(), is(false));
    }

    @Test
    public void pageShouldNotBeCompletedWhenNameIsIncorrect() throws Exception {
        when(view.getResourceName()).thenReturn("in/*correct");

        page.onValueChanged();

        assertThat(page.isCompleted(), is(false));
    }

    @Test
    public void treeCallbackFailureMethodShouldBeCalled() throws Exception {
        when(currentProject.getProjectDescription().getPath()).thenReturn(PATH_TO_PROJECT);

        page.go(container);

        verify(projectServiceClient).getTree(eq(PATH_TO_PROJECT), eq(DEPTH), treeCallbackCaptor.capture());

        WSO2AsyncRequestCallback<TreeElement> treeCallback = treeCallbackCaptor.getValue();

        invokeOnFailureCallbackMethod(WSO2AsyncRequestCallback.class, treeCallback, throwable);

        assertNotification();
    }

    private void assertNotification() {
        verify(notificationManager).showNotification(notificationCaptor.capture());

        Notification notification = notificationCaptor.getValue();

        assertThat(notification.isError(), is(true));
    }

    @Test
    public void childrenCallbackFailureMethodShouldBeCalled() throws Exception {
        configureParentFolder();

        page.go(container);

        verify(projectServiceClient).getTree(eq(PATH_TO_PROJECT), eq(DEPTH), treeCallbackCaptor.capture());

        executeGetTreeCallback();

        verify(projectServiceClient).getChildren(eq(PATH_TO_PROJECT + SYNAPSE_CONFIG_PATH), childrenCallbackCaptor.capture());

        WSO2AsyncRequestCallback<Array<ItemReference>> childrenCallback = childrenCallbackCaptor.getValue();

        invokeOnFailureCallbackMethod(WSO2AsyncRequestCallback.class, childrenCallback, throwable);

        assertNotification();
    }

    @Test
    public void createFileFailureCallbackShouldBeCalled() throws Exception {
        configureParentFolder();

        page.go(container);

        verify(projectServiceClient).getTree(eq(PATH_TO_PROJECT), eq(DEPTH), treeCallbackCaptor.capture());

        executeGetTreeCallback();

        page.commit(commitCallback);

        verify(projectServiceClient).createFile(eq(PATH_TO_PROJECT + SYNAPSE_CONFIG_PATH),
                                                eq(SOME_TEXT_WITH_EXTENSION),
                                                eq(SOME_TEXT),
                                                eq(ESB_XML_MIME_TYPE),
                                                createFileCallbackCaptor.capture());

        WSO2AsyncRequestCallback<ItemReference> createFileCallback = createFileCallbackCaptor.getValue();

        invokeOnFailureCallbackMethod(WSO2AsyncRequestCallback.class, createFileCallback, throwable);

        assertNotification();
    }

    @Test
    public void createFolderFailureCallbackShouldBeCalled() throws Exception {
        page.commit(commitCallback);

        verify(projectServiceClient).createFolder(anyString(), createFolderCallBackCaptor.capture());

        WSO2AsyncRequestCallback<ItemReference> createFolderCallback = createFolderCallBackCaptor.getValue();

        invokeOnFailureCallbackMethod(WSO2AsyncRequestCallback.class, createFolderCallback, throwable);

        assertNotification();
    }

    @Test
    public void doOptionsShouldBeDone() throws Exception {
        WizardContext wizardContext = mock(WizardContext.class);
        page.setContext(wizardContext);

        page.removeOptions();

        verify(wizardContext, never()).clear();
        verify(wizardContext, never()).removeData(any(WizardContext.Key.class));
    }

    @Test
    public void createdFileShouldBeRelatedInParentFolder() throws Exception {
        configureParentFolder();

        TreeElement otherFolder = mock(TreeElement.class);
        TreeElement correctFolder = mock(TreeElement.class);

        when(correctFolder.getNode()).thenReturn(parentFolderItem);
        when(parentFolderItem.getName()).thenReturn(parentFolderName);

        when(otherFolder.getNode()).thenReturn(item);
        when(item.getName()).thenReturn(SOME_TEXT);

        when(synapseFolder.getChildren()).thenReturn(Arrays.asList(otherFolder, correctFolder));

        page.go(container);

        verify(projectServiceClient).getTree(eq(PATH_TO_PROJECT), eq(DEPTH), treeCallbackCaptor.capture());

        executeGetTreeCallback();

        verify(parentFolder).getNode();
    }

}