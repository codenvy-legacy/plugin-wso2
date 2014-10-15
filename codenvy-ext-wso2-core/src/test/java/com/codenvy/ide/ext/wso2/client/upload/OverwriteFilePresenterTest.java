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
import com.codenvy.ide.api.projecttree.generic.FolderNode;
import com.codenvy.ide.dto.DtoFactory;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2ClientService;
import com.codenvy.ide.ext.wso2.client.commons.WSO2AsyncRequestCallback;
import com.codenvy.ide.ext.wso2.client.upload.overwrite.OverwriteFilePresenter;
import com.codenvy.ide.ext.wso2.client.upload.overwrite.OverwriteFileView;
import com.codenvy.ide.ext.wso2.shared.FileInfo;
import com.google.gwt.http.client.RequestException;
import com.google.web.bindery.event.shared.EventBus;
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
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Method;

import static com.codenvy.ide.ext.wso2.shared.Constants.MAIN_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SRC_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SYNAPSE_CONFIG_FOLDER_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link OverwriteFilePresenter}
 *
 * @author Valeriy Svydenko
 * @author Andrey Plotnikov
 */
@RunWith(MockitoJUnitRunner.class)
public class OverwriteFilePresenterTest {

    private final String FILE_NAME    = "fileName";
    private final String PROJECT_NAME = "projectName";

    @Mock
    private OverwriteFileView    view;
    @Mock
    private DtoFactory           dtoFactory;
    @Mock
    private NotificationManager  notificationManager;
    @Mock
    private WSO2ClientService    service;
    @Mock
    private CurrentProject       currentProject;
    @Mock
    private EventBus             eventBus;
    @Mock
    private LocalizationConstant local;
    @Mock
    private FolderNode           src;
    @Mock
    private FolderNode           main;
    @Mock
    private FolderNode           synapse_config;

    @Mock(answer = RETURNS_DEEP_STUBS)
    private AppContext                           appContext;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private ProjectServiceClient                 projectServiceClient;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private ImportFilePresenter                  importFilePresenter;
    @Mock
    private ImportFilePresenter.ViewCloseHandler parentViewUtils;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private FolderNode                           parentFolder;
    @InjectMocks
    private OverwriteFilePresenter               overwriteFilePresenter;

    @Before
    public void setUp() throws Exception {
        verify(view).setDelegate(eq(overwriteFilePresenter));
    }

    @Test
    public void overwriteDialogShouldBeInitializedWhenShowDialogExecuted() {
        overwriteFilePresenter.showDialog(FILE_NAME, parentViewUtils);

        verify(view).setMessage(anyString());
        verify(local).wso2ImportFileAlreadyExists();
        verify(view).setFileName(eq(FILE_NAME));
        verify(view).setEnabledRenameButton(eq(false));
        verify(view).showDialog();
    }

    @Test
    public void renameButtonShouldBeEnableWhenNewFileNameIsNotTheSameAsOldFileName() {
        String oldFileName = "oldFileName";
        when(view.getFileName()).thenReturn(FILE_NAME);

        overwriteFilePresenter.showDialog(oldFileName, parentViewUtils);
        overwriteFilePresenter.onNameChanged();

        verify(view).setEnabledRenameButton(eq(true));
    }

    @Test
    public void renameButtonShouldBeDisableWhenNewFileNameAndFileNameAreDifferent() {
        overwriteFilePresenter.showDialog(FILE_NAME, parentViewUtils);

        reset(view);

        when(view.getFileName()).thenReturn(FILE_NAME);

        overwriteFilePresenter.onNameChanged();

        verify(view).setEnabledRenameButton(eq(false));
    }

    @Ignore("not ready yet")
    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Test
    public void cancelButtonShouldBeExecutedWhenSomeProblemHappened() throws Exception {
        //TODO need rework this test

        when(dtoFactory.createDto(Matchers.<Class<FileInfo>>anyObject())).thenReturn(mock(FileInfo.class, RETURNS_MOCKS));

        final Throwable throwable = mock(Throwable.class);
        when(throwable.getMessage()).thenReturn(PROJECT_NAME);

        doAnswer(new Answer() {
            @SuppressWarnings({"unchecked", "NonJREEmulationClassesInClientCode"})
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();

                WSO2AsyncRequestCallback<String> callback = (WSO2AsyncRequestCallback<String>)arguments[2];

                Method onFailure = GwtReflectionUtils.getMethod(callback.getClass(), "onFailure");
                onFailure.invoke(callback, throwable);

                return null;
            }
        }).when(service).modifyFile((FileInfo)anyObject(), anyString(), Matchers.<WSO2AsyncRequestCallback<String>>anyObject());

        when(appContext.getCurrentProject()).thenReturn(currentProject);
        when(view.getFileName()).thenReturn(FILE_NAME);
        when(appContext.getCurrentProject().getProjectDescription().getName()).thenReturn(PROJECT_NAME);

        overwriteFilePresenter.onCancelButtonClicked();

        verify(throwable).getMessage();

        ArgumentCaptor<Notification> notificationArgumentCaptor = ArgumentCaptor.forClass(Notification.class);

        verify(notificationManager).showNotification(notificationArgumentCaptor.capture());

        Notification notification = notificationArgumentCaptor.getValue();

        assertEquals(PROJECT_NAME, notification.getMessage());
        assertTrue(notification.isError());
    }

    private void prepareDataForExecutingTests() {
        when(dtoFactory.createDto(Matchers.<Class<FileInfo>>anyObject())).thenReturn(mock(FileInfo.class, RETURNS_MOCKS));
        when(appContext.getCurrentProject()).thenReturn(currentProject);
        when(view.getFileName()).thenReturn(FILE_NAME);
        when(appContext.getCurrentProject().getProjectDescription().getName()).thenReturn(PROJECT_NAME);

        when(appContext.getCurrentProject()).thenReturn(currentProject);

        /*when(currentProject.findChildByName(eq(SRC_FOLDER_NAME))).thenReturn(src);
        when(src.findChildByName(eq(MAIN_FOLDER_NAME))).thenReturn(main);
        when(main.findChildByName(eq(SYNAPSE_CONFIG_FOLDER_NAME))).thenReturn(synapse_config);*/

        when(src.getName()).thenReturn(SRC_FOLDER_NAME);
        when(main.getName()).thenReturn(MAIN_FOLDER_NAME);
        when(synapse_config.getName()).thenReturn(SYNAPSE_CONFIG_FOLDER_NAME);
    }

    @Ignore("not ready yet")
    @Test
    public void cancelButtonShouldBeExecutedWithoutProblems() throws Exception {
        prepareDataForExecutingTests();

        doAnswer(new Answer() {
            @SuppressWarnings({"unchecked", "NonJREEmulationClassesInClientCode"})
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();

                WSO2AsyncRequestCallback<String> callback = (WSO2AsyncRequestCallback<String>)arguments[2];

                Method onSuccess = GwtReflectionUtils.getMethod(callback.getClass(), "onSuccess");
                onSuccess.invoke(callback, "");

                return null;
            }
        }).when(service).modifyFile((FileInfo)anyObject(), anyString(), Matchers.<WSO2AsyncRequestCallback<String>>anyObject());

        overwriteFilePresenter.onCancelButtonClicked();

        verify(view).close();
        //verify(currentProject, never()).refreshChildren((Folder)anyObject(), Matchers.<AsyncCallback<Folder>>anyObject());
    }

    @Ignore("not ready yet")
    @Test
    public void onFailureMethodInModifyFileShouldBeExecutedWhenRequestExceptionHappened() throws Exception {
        when(dtoFactory.createDto(Matchers.<Class<FileInfo>>anyObject())).thenReturn(mock(FileInfo.class, RETURNS_MOCKS));
        when(appContext.getCurrentProject()).thenReturn(currentProject);
        when(view.getFileName()).thenReturn(FILE_NAME);
        when(appContext.getCurrentProject().getProjectDescription().getName()).thenReturn(PROJECT_NAME);

        doThrow(RequestException.class).when(service).modifyFile(any(FileInfo.class),
                                                                 anyString(),
                                                                 Matchers.<WSO2AsyncRequestCallback<String>>anyObject());


        overwriteFilePresenter.onCancelButtonClicked();

        ArgumentCaptor<Notification> notificationArgumentCaptor = ArgumentCaptor.forClass(Notification.class);

        verify(notificationManager).showNotification(notificationArgumentCaptor.capture());

        Notification notification = notificationArgumentCaptor.getValue();
        assertTrue(notification.isError());
    }

    @Ignore("not ready yet")
    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Test
    public void renameButtonShouldBeExecutedWhenSomeProblemHappened() throws Exception {
        when(dtoFactory.createDto(Matchers.<Class<FileInfo>>anyObject())).thenReturn(mock(FileInfo.class, RETURNS_MOCKS));

        final Throwable throwable = mock(Throwable.class);
        when(throwable.getMessage()).thenReturn(PROJECT_NAME);

        doAnswer(new Answer() {
            @SuppressWarnings({"unchecked", "NonJREEmulationClassesInClientCode"})
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();

                WSO2AsyncRequestCallback<String> callback = (WSO2AsyncRequestCallback<String>)arguments[2];

                Method onFailure = GwtReflectionUtils.getMethod(callback.getClass(), "onFailure");
                onFailure.invoke(callback, throwable);

                return null;
            }
        }).when(service).modifyFile(any(FileInfo.class), anyString(), Matchers.<WSO2AsyncRequestCallback<String>>anyObject());

        when(appContext.getCurrentProject()).thenReturn(currentProject);
        when(view.getFileName()).thenReturn(FILE_NAME);
        when(appContext.getCurrentProject().getProjectDescription().getName()).thenReturn(PROJECT_NAME);

        overwriteFilePresenter.showDialog(FILE_NAME, parentViewUtils);
        overwriteFilePresenter.onRenameButtonClicked();

        verify(parentViewUtils).onCloseView();
        verify(throwable).getMessage();

        ArgumentCaptor<Notification> notificationArgumentCaptor = ArgumentCaptor.forClass(Notification.class);

        verify(notificationManager).showNotification(notificationArgumentCaptor.capture());

        Notification notification = notificationArgumentCaptor.getValue();
        assertEquals(PROJECT_NAME, throwable.getMessage());
        assertTrue(notification.isError());
    }

    @Ignore("not ready yet")
    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Test
    public void overwriteButtonShouldBeExecutedWhenSomeProblemHappened() throws Exception {
        when(dtoFactory.createDto(Matchers.<Class<FileInfo>>anyObject())).thenReturn(mock(FileInfo.class, RETURNS_MOCKS));

        final Throwable throwable = mock(Throwable.class);
        when(throwable.getMessage()).thenReturn(PROJECT_NAME);

        doAnswer(new Answer() {
            @SuppressWarnings({"unchecked", "NonJREEmulationClassesInClientCode"})
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();

                WSO2AsyncRequestCallback<String> callback = (WSO2AsyncRequestCallback<String>)arguments[2];

                Method onFailure = GwtReflectionUtils.getMethod(callback.getClass(), "onFailure");
                onFailure.invoke(callback, throwable);

                return null;
            }
        }).when(service).modifyFile(any(FileInfo.class), anyString(), Matchers.<WSO2AsyncRequestCallback<String>>anyObject());

        when(appContext.getCurrentProject()).thenReturn(currentProject);
        when(view.getFileName()).thenReturn(FILE_NAME);
        when(appContext.getCurrentProject().getProjectDescription().getName()).thenReturn(PROJECT_NAME);

        overwriteFilePresenter.showDialog(FILE_NAME, parentViewUtils);
        overwriteFilePresenter.onOverwriteButtonClicked();

        verify(parentViewUtils).onCloseView();
        verify(throwable).getMessage();

        ArgumentCaptor<Notification> notificationArgumentCaptor = ArgumentCaptor.forClass(Notification.class);

        verify(notificationManager).showNotification(notificationArgumentCaptor.capture());

        Notification notification = notificationArgumentCaptor.getValue();
        assertEquals(PROJECT_NAME, throwable.getMessage());
        assertTrue(notification.isError());
    }

    //TODO need added tests
    @Test
    public void renameButtonShouldBeExecutedAndTreeRefreshedWithoutProblems() throws Exception {
    }

    @Test
    public void overwriteButtonShouldBeExecutedAndTreeRefreshedWithoutProblems() throws Exception {
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Test
    public void overwriteButtonShouldBeExecutedAndTreeRefreshWithSomeProblem() throws Exception {
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Test
    public void refreshButtonShouldBeExecutedAndTreeRefreshWithSomeProblem() throws Exception {
    }

}