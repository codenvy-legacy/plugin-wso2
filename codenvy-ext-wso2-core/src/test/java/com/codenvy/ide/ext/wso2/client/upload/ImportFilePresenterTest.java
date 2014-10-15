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
package com.codenvy.ide.ext.wso2.client.upload;

import com.codenvy.api.project.gwt.client.ProjectServiceClient;
import com.codenvy.ide.api.app.AppContext;
import com.codenvy.ide.api.app.CurrentProject;
import com.codenvy.ide.api.notification.Notification;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.projecttree.generic.FileNode;
import com.codenvy.ide.api.projecttree.generic.FolderNode;
import com.codenvy.ide.dto.DtoFactory;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.SonarAwareGwtRunner;
import com.codenvy.ide.ext.wso2.client.WSO2ClientService;
import com.codenvy.ide.ext.wso2.client.commons.WSO2AsyncRequestCallback;
import com.codenvy.ide.ext.wso2.client.upload.overwrite.OverwriteFilePresenter;
import com.codenvy.ide.ext.wso2.shared.FileInfo;
import com.codenvy.ide.rest.AsyncRequestCallback;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTestWithMockito;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Method;

import static com.codenvy.ide.ext.wso2.client.upload.ImportFilePresenter.ViewCloseHandler;
import static com.codenvy.ide.ext.wso2.shared.Constants.MAIN_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SRC_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SYNAPSE_CONFIG_FOLDER_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link ImportFilePresenter}.
 *
 * @author Valeriy Svydenko
 * @author Andrey Plotnikov
 */
@GwtModule("com.codenvy.ide.ext.wso2.WSO2")
@RunWith(SonarAwareGwtRunner.class)
public class ImportFilePresenterTest extends GwtTestWithMockito {

    private static final String MESSAGE        = "message";
    private static final String NOT_VALID_NAME = "configurationName";
    private static final String VALID_NAME     = "configurationName.xml";
    private static final String PARENT_FOLDER  = "parentFolderName";

    @Mock(answer = RETURNS_DEEP_STUBS)
    private FolderNode parentFolder;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private FolderNode endpointsFolder;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private FileInfo   fileInfo;

    @Mock
    private CurrentProject         currentProject;
    @Mock
    private FolderNode             src;
    @Mock
    private FolderNode             main;
    @Mock
    private OverwriteFilePresenter overwrite;
    @Mock
    private FolderNode             synapse_config;
    @Mock
    private FileNode               file;

    @Mock
    private ImportFileView       view;
    @Mock
    private NotificationManager  notificationManager;
    @Mock
    private LocalizationConstant local;
    @Mock
    private EventBus             eventBus;
    @Mock
    private WSO2ClientService    service;

    @Mock(answer = RETURNS_DEEP_STUBS)
    private AppContext           appContext;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private ProjectServiceClient projectServiceClient;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private DtoFactory           dtoFactory;

    @InjectMocks
    private ImportFilePresenter importFilePresenter;

    @Before
    public void setUp() throws Exception {
        verify(view).setDelegate(eq(importFilePresenter));
    }

    @Test
    public void importButtonAndUrlFieldShouldBeDisable() {
        importFilePresenter.showDialog();

        verify(view).setEnabledImportButton(eq(false));
        verify(view).setEnterUrlFieldEnabled(eq(false));
        verify(view).setMessage(eq(""));
        verify(view).setUseLocalPath(eq(true));
        verify(view).setUseUrl(eq(false));
        verify(view).setUrl(eq(""));
        verify(view).showDialog();
    }

    @Test
    public void closeButtonShouldBeExecuted() {
        importFilePresenter.onCancelClicked();

        verify(view).close();
    }

    @Test
    public void buttonImportShouldBeDisableWhenFileNameIsNotValid() {
        when(view.getFileName()).thenReturn(NOT_VALID_NAME);

        importFilePresenter.onFileNameChanged();

        verify(view).setMessage(eq(local.wso2ImportFileFormatError()));
        verify(view).setEnabledImportButton(eq(false));
    }

    @Test
    public void buttonImportShouldBeEnableWhenFileNameIsValid() {
        when(view.getFileName()).thenReturn(VALID_NAME);
        importFilePresenter.onFileNameChanged();

        verify(view).setMessage(eq(""));
        verify(view).setEnabledImportButton(eq(true));
    }

    @Test
    public void buttonImportShouldBeDisableWhenFileNameIsInvalid() {
        when(local.wso2ImportFileFormatError()).thenReturn(MESSAGE);

        importFilePresenter.onFileNameChangedWithInvalidFormat();

        verify(view).setEnabledImportButton(eq(false));
        verify(view).setMessage(eq(MESSAGE));
        verify(view).setEnabledImportButton(eq(false));
        verify(local).wso2ImportFileFormatError();
    }

    @Test
    public void buttonImportShouldBeEnableWhenUrlChangedAndUrlIsNotEmpty() {
        when(view.getUrl()).thenReturn(MESSAGE);
        when(view.isUseUrl()).thenReturn(true);

        importFilePresenter.onUrlChanged();

        verify(view).setEnabledImportButton(eq(true));
    }

    @Test
    public void buttonImportShouldBeDisableWhenLocalChangedAndUrlIsNotEmpty() {
        when(view.getUrl()).thenReturn(MESSAGE);
        when(view.isUseUrl()).thenReturn(false);

        importFilePresenter.onUrlChanged();

        verify(view).setEnabledImportButton(eq(false));
    }

    @Test
    public void buttonImportShouldBeEnableWhenLocalChangedAndUrlIsEmpty() {
        when(view.getUrl()).thenReturn("");
        when(view.isUseUrl()).thenReturn(false);

        importFilePresenter.onUrlChanged();

        verify(view).setEnabledImportButton(eq(false));
    }

    @Test
    public void buttonImportShouldBeDisableWhenUrlChangedAndUrlIsEmpty() {
        when(view.getUrl()).thenReturn("");
        when(view.isUseUrl()).thenReturn(true);

        importFilePresenter.onUrlChanged();

        verify(view).setEnabledImportButton(eq(false));
    }

    @Test
    public void urlFieldShouldBeEnableWhenUrlButtonChosen() {
        when(view.getUrl()).thenReturn(MESSAGE);

        importFilePresenter.onUseUrlChosen();

        verify(view).setEnterUrlFieldEnabled(eq(true));
        verify(view).setEnabledImportButton(eq(true));
    }

    @Test
    public void localFieldShouldBeEnableWhenLocalButtonChosen() {
        when(view.getFileName()).thenReturn(VALID_NAME);

        importFilePresenter.onUseLocalPathChosen();

        verify(view).setEnabledImportButton(eq(true));
    }

    @Test
    public void notificationShouldBeShowWhenResultMessageIsNotEmpty() throws Exception {
        importFilePresenter.onSubmitComplete(MESSAGE);

        ArgumentCaptor<Notification> notificationArgumentCaptor = ArgumentCaptor.forClass(Notification.class);

        verify(notificationManager).showNotification(notificationArgumentCaptor.capture());

        Notification notification = notificationArgumentCaptor.getValue();

        assertEquals(MESSAGE, notification.getMessage());
        assertTrue(notification.isError());
    }

    @Test
    public void notificationShouldBeShowWithoutHTMLTagsWhenResultMessageIsNotEmpty() throws Exception {
        importFilePresenter.onSubmitComplete("<pre>" + MESSAGE + "</pre>");

        ArgumentCaptor<Notification> notificationArgumentCaptor = ArgumentCaptor.forClass(Notification.class);

        verify(notificationManager).showNotification(notificationArgumentCaptor.capture());

        Notification notification = notificationArgumentCaptor.getValue();

        assertEquals(MESSAGE, notification.getMessage());
        assertTrue(notification.isError());
    }

    @Test
    public void onFailureMethodInSubmitCallbackShouldBeExecutedWhenSomeProblemHappened() throws Exception {
        final Throwable throwable = mock(Throwable.class);

        doAnswer(new Answer() {
            @SuppressWarnings({"NonJREEmulationClassesInClientCode", "unchecked"})
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                AsyncRequestCallback<Void> callback = (AsyncRequestCallback<Void>)arguments[1];

                Method onFailure = GwtReflectionUtils.getMethod(callback.getClass(), "onFailure");
                onFailure.invoke(callback, throwable);

                return callback;
            }
        }).when(service).detectConfigurationFile(any(FileInfo.class), Matchers.<AsyncRequestCallback<String>>anyObject());

        when(local.wso2ImportDialogError()).thenReturn(MESSAGE);
        when(dtoFactory.createDto(Matchers.<Class<FileInfo>>anyObject())).thenReturn(mock(FileInfo.class, RETURNS_MOCKS));
        when(view.getFileName()).thenReturn(MESSAGE);

        //TODO rework this test
        when(appContext.getCurrentProject().getProjectDescription().getName()).thenReturn(MESSAGE);

        importFilePresenter.onSubmitComplete("");

        verify(view).setMessage(Matchers.eq(MESSAGE));
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Test
    public void onFailureMethodOnImportClickedCallbackShouldBeExecutedWhenSomeProblemHappened() throws Exception {
        final Throwable throwable = mock(Throwable.class);
        when(throwable.getMessage()).thenReturn(MESSAGE);

        doAnswer(new Answer() {
            @SuppressWarnings({"NonJREEmulationClassesInClientCode", "unchecked"})
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                WSO2AsyncRequestCallback<Void> callback = (WSO2AsyncRequestCallback<Void>)arguments[1];

                Method onFailure = GwtReflectionUtils.getMethod(callback.getClass(), "onFailure");
                onFailure.invoke(callback, throwable);

                return callback;
            }
        }).when(service).uploadFile(any(FileInfo.class), Matchers.<WSO2AsyncRequestCallback<String>>anyObject());

        when(dtoFactory.createDto(Matchers.<Class<FileInfo>>anyObject()))
                .thenReturn(mock(FileInfo.class, RETURNS_MOCKS));

        importFilePresenter.onImportClicked();

        verify(throwable).getMessage();

        ArgumentCaptor<Notification> notificationArgumentCaptor = ArgumentCaptor.forClass(Notification.class);

        verify(notificationManager).showNotification(notificationArgumentCaptor.capture());

        Notification notification = notificationArgumentCaptor.getValue();

        assertEquals(MESSAGE, notification.getMessage());
        assertTrue(notification.isError());
    }

    @Ignore("not ready yet")
    @Test
    public void onSuccessMethodOnImportClickedCallbackShouldBeExecutedWhenFileUploaded() throws Exception {
        prepareTestForSuccessResultWhenMethodSubmitCalled();

        doAnswer(new Answer() {
            @SuppressWarnings({"unchecked", "NonJREEmulationClassesInClientCode"})
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                WSO2AsyncRequestCallback<Void> callback = (WSO2AsyncRequestCallback<Void>)arguments[1];

                Method onSuccess = GwtReflectionUtils.getMethod(callback.getClass(), "onSuccess");
                onSuccess.invoke(callback, "already exists. ");

                return callback;
            }
        }).when(service).uploadFile(any(FileInfo.class), Matchers.<WSO2AsyncRequestCallback<String>>anyObject());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();
                ViewCloseHandler utils = (ViewCloseHandler)arguments[1];

                utils.onCloseView();

                return utils;
            }
        }).when(overwrite).showDialog(anyString(), any(ViewCloseHandler.class));

        importFilePresenter.onImportClicked();

        verify(overwrite).showDialog(eq(MESSAGE), any(ViewCloseHandler.class));
        verify(view).close();
    }

    @Ignore("not ready yet")
    @Test
    public void seActionShouldBeExecutedWhenImportButtonClicked() throws Exception {
        when(appContext.getCurrentProject()).thenReturn(currentProject);
        when(view.isUseLocalPath()).thenReturn(true);

        importFilePresenter.onImportClicked();

        verify(view).setAction(anyString());
        verify(view).submit();
    }

    private void prepareTestForSuccessResultWhenMethodSubmitCalled() {
        //TODO rework this test
        when(dtoFactory.createDto(Matchers.<Class<FileInfo>>anyObject())).thenReturn(fileInfo);
        when(fileInfo.withFileName(anyString()).withProjectName(anyString())).thenReturn(fileInfo);

        when(view.getFileName()).thenReturn(MESSAGE);
        when(appContext.getCurrentProject().getProjectDescription().getName()).thenReturn(MESSAGE);
        when(fileInfo.getFileName()).thenReturn(MESSAGE);

        when(appContext.getCurrentProject()).thenReturn(currentProject);

        when(src.getName()).thenReturn(SRC_FOLDER_NAME);
        when(main.getName()).thenReturn(MAIN_FOLDER_NAME);
        when(synapse_config.getName()).thenReturn(SYNAPSE_CONFIG_FOLDER_NAME);
        when(parentFolder.getName()).thenReturn(PARENT_FOLDER);
    }

    @Ignore("not ready yet")
    @Test
    public void onSuccessMethodInSubmitCallbackShouldBeExecutedWhenNoProblemHappenedAndResponseContainsAlreadyExist() throws Exception {
        prepareTestForSuccessResultWhenMethodSubmitCalled();

        doAnswer(new Answer() {
            @SuppressWarnings({"unchecked", "NonJREEmulationClassesInClientCode"})
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                AsyncRequestCallback<Void> callback = (AsyncRequestCallback<Void>)arguments[1];

                Method onSuccess = GwtReflectionUtils.getMethod(callback.getClass(), "onSuccess");
                onSuccess.invoke(callback, "already exists. ");

                return callback;
            }
        }).when(service).detectConfigurationFile((FileInfo)anyObject(), Matchers.<AsyncRequestCallback<String>>anyObject());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();
                ViewCloseHandler utils = (ViewCloseHandler)arguments[1];

                utils.onCloseView();

                return utils;
            }
        }).when(overwrite).showDialog(anyString(), any(ViewCloseHandler.class));

        importFilePresenter.onSubmitComplete("");

        verify(overwrite).showDialog(eq(MESSAGE), any(ViewCloseHandler.class));
        verify(view).close();
    }


    //TODO need added tests
    @Test
    public void onSuccessMethodInSubmitCallbackShouldBeExecutedWhenNoProblemHappenedAndResponseIsEmpty() throws Exception {
    }

    @Test
    public void onSuccessMethodInSubmitCallbackShouldBeExecutedWhenNoProblemHappenedAndResponseIsNotEmpty() throws Exception {

    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Test
    public void onFailureMethodInSubmitCallbackShouldBeExecutedWhenSomeProblemHappenedAndResponseIsNotEmpty() throws Exception {

    }

}