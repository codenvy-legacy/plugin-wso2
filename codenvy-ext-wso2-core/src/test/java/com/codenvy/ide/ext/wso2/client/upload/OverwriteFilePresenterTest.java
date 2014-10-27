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

import com.codenvy.ide.api.app.AppContext;
import com.codenvy.ide.api.app.CurrentProject;
import com.codenvy.ide.api.event.RefreshProjectTreeEvent;
import com.codenvy.ide.api.notification.Notification;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.dto.DtoFactory;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2ClientService;
import com.codenvy.ide.ext.wso2.client.commons.WSO2AsyncRequestCallback;
import com.codenvy.ide.ext.wso2.client.upload.overwrite.OverwriteFilePresenter;
import com.codenvy.ide.ext.wso2.client.upload.overwrite.OverwriteFileView;
import com.codenvy.ide.ext.wso2.shared.FileInfo;
import com.codenvy.ide.rest.AsyncRequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.web.bindery.event.shared.EventBus;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.codenvy.ide.ext.wso2.TestUtil.invokeOnFailureCallbackMethod;
import static com.codenvy.ide.ext.wso2.TestUtil.invokeOnSuccessCallbackMethod;
import static com.codenvy.ide.ext.wso2.client.upload.ImportFilePresenter.ViewCloseHandler;
import static com.codenvy.ide.ext.wso2.shared.Constants.DELETE_FILE_OPERATION;
import static com.codenvy.ide.ext.wso2.shared.Constants.OVERWRITE_FILE_OPERATION;
import static com.codenvy.ide.ext.wso2.shared.Constants.RENAME_FILE_OPERATION;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link OverwriteFilePresenter}
 *
 * @author Valeriy Svydenko
 * @author Andrey Plotnikov
 * @author Dmitriy Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public class OverwriteFilePresenterTest {

    private static final String FILE_NAME    = "fileName";
    private static final String PROJECT_NAME = "projectName";
    private static final String SOME_TEXT    = "someText";

    @Captor
    private ArgumentCaptor<WSO2AsyncRequestCallback<Void>> callbackCaptor;
    @Captor
    private ArgumentCaptor<Notification>                   notificationCaptor;

    @Mock
    private OverwriteFileView      view;
    @Mock
    private AppContext             appContext;
    @Mock
    private NotificationManager    notificationManager;
    @Mock
    private WSO2ClientService      service;
    @Mock
    private EventBus               eventBus;
    @Mock
    private LocalizationConstant   local;
    @Mock
    private ViewCloseHandler       closeHandler;
    @InjectMocks
    private OverwriteFilePresenter overwriteFilePresenter;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private DtoFactory             dtoFactory;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private CurrentProject         currentProject;

    @Before
    public void setUp() throws Exception {
        when(appContext.getCurrentProject()).thenReturn(currentProject);
        when(currentProject.getProjectDescription().getName()).thenReturn(PROJECT_NAME);
    }

    @Test
    public void delegateShouldBeSet() throws Exception {
        verify(view).setDelegate(overwriteFilePresenter);
    }

    @Test
    public void dialogWindowShouldBeShown() throws Exception {
        when(local.wso2ImportFileAlreadyExists()).thenReturn(SOME_TEXT);

        overwriteFilePresenter.showDialog(FILE_NAME, closeHandler);

        verify(local).wso2ImportFileAlreadyExists();

        verify(view).setMessage(SOME_TEXT);
        verify(view).setFileName(FILE_NAME);
        verify(view).setEnabledRenameButton(false);

        verify(view).showDialog();
    }

    @Test
    public void nameShouldBeChangedWhenFileWithSameNameNotExist() throws Exception {
        when(view.getFileName()).thenReturn(SOME_TEXT);

        overwriteFilePresenter.showDialog(FILE_NAME, closeHandler);
        overwriteFilePresenter.onNameChanged();

        verify(view).setEnabledRenameButton(true);
    }

    @Test
    public void nameShouldNotBeChangedWhenExistFileWithSameName() throws Exception {
        when(view.getFileName()).thenReturn(FILE_NAME);

        overwriteFilePresenter.showDialog(FILE_NAME, closeHandler);
        overwriteFilePresenter.onNameChanged();

        verify(view, times(2)).setEnabledRenameButton(false);
    }

    @Test
    public void fileShouldBeDeletedWhenCancelButtonClicked() throws Exception {
        prepareMocks(DELETE_FILE_OPERATION);

        verify(eventBus, never()).fireEvent(any(RefreshProjectTreeEvent.class));
        verify(notificationManager, never()).showNotification(any(Notification.class));
    }

    private void prepareMocks(String operation) throws Exception {
        when(dtoFactory.createDto(Matchers.<Class<FileInfo>>anyObject())).thenReturn(mock(FileInfo.class, RETURNS_MOCKS));

        switch (operation) {
            case DELETE_FILE_OPERATION:
                overwriteFilePresenter.onCancelButtonClicked();
                break;

            case RENAME_FILE_OPERATION:
                overwriteFilePresenter.onRenameButtonClicked();
                break;

            case OVERWRITE_FILE_OPERATION:
                overwriteFilePresenter.onOverwriteButtonClicked();
                break;
        }

        verify(appContext).getCurrentProject();
        verify(view).getFileName();

        verify(service).modifyFile(any(FileInfo.class), eq(operation), callbackCaptor.capture());

        WSO2AsyncRequestCallback<Void> callback = callbackCaptor.getValue();
        invokeOnSuccessCallbackMethod(callback.getClass(), callback, (Void)null);

        verify(view).close();
    }

    @Test
    public void fileShouldBeRenamedWhenRenameButtonClicked() throws Exception {
        overwriteFilePresenter.showDialog(FILE_NAME, closeHandler);

        prepareMocks(RENAME_FILE_OPERATION);

        verify(eventBus).fireEvent(any(RefreshProjectTreeEvent.class));
        verify(closeHandler).onCloseView();
        verify(notificationManager, never()).showNotification(any(Notification.class));
    }

    @Test
    public void fileShouldBeOverwritten() throws Exception {
        overwriteFilePresenter.showDialog(FILE_NAME, closeHandler);

        prepareMocks(OVERWRITE_FILE_OPERATION);

        verify(eventBus).fireEvent(any(RefreshProjectTreeEvent.class));
        verify(closeHandler).onCloseView();
        verify(notificationManager, never()).showNotification(any(Notification.class));
    }

    @Test
    public void fileShouldNotBeModifiedWhenCurrentProjectIsNull() throws Exception {
        when(appContext.getCurrentProject()).thenReturn(null);
        when(dtoFactory.createDto(Matchers.<Class<FileInfo>>anyObject())).thenReturn(mock(FileInfo.class, RETURNS_MOCKS));

        overwriteFilePresenter.onCancelButtonClicked();

        verify(appContext).getCurrentProject();
        verify(service, never()).modifyFile(any(FileInfo.class), anyString(), Matchers.<WSO2AsyncRequestCallback<Void>>anyObject());
    }

    @Test
    public void requestExceptionShouldBeThrown() throws Exception {
        when(appContext.getCurrentProject()).thenReturn(currentProject);
        when(dtoFactory.createDto(Matchers.<Class<FileInfo>>anyObject())).thenReturn(mock(FileInfo.class, RETURNS_MOCKS));

        doThrow(new RequestException()).when(service).modifyFile(any(FileInfo.class),
                                                                 anyString(),
                                                                 Matchers.<WSO2AsyncRequestCallback<Void>>anyObject());

        overwriteFilePresenter.onCancelButtonClicked();

        verify(notificationManager).showNotification(notificationCaptor.capture());

        assertNotification();
    }

    private void assertNotification() {
        verify(notificationManager).showNotification(notificationCaptor.capture());

        Notification notification = notificationCaptor.getValue();

        assertThat(notification.isError(), is(true));
    }

    @Test
    public void onFailureCallbackMethodShouldBeCalled() throws Exception {
        Throwable throwable = mock(Throwable.class);

        when(dtoFactory.createDto(Matchers.<Class<FileInfo>>anyObject())).thenReturn(mock(FileInfo.class, RETURNS_MOCKS));

        overwriteFilePresenter.onCancelButtonClicked();

        verify(service).modifyFile(any(FileInfo.class), eq(DELETE_FILE_OPERATION), callbackCaptor.capture());

        WSO2AsyncRequestCallback<Void> callback = callbackCaptor.getValue();
        invokeOnFailureCallbackMethod(AsyncRequestCallback.class, callback, throwable);

        assertNotification();
    }
}