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
import com.codenvy.ide.api.event.RefreshProjectTreeEvent;
import com.codenvy.ide.api.notification.Notification;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.projecttree.generic.FileNode;
import com.codenvy.ide.api.projecttree.generic.FolderNode;
import com.codenvy.ide.dto.DtoFactory;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2ClientService;
import com.codenvy.ide.ext.wso2.client.upload.overwrite.OverwriteFilePresenter;
import com.codenvy.ide.ext.wso2.shared.FileInfo;
import com.codenvy.ide.rest.AsyncRequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwtmockito.GwtMockitoTestRunner;
import com.google.web.bindery.event.shared.EventBus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;

import static com.codenvy.ide.ext.wso2.TestUtil.invokeOnFailureCallbackMethod;
import static com.codenvy.ide.ext.wso2.TestUtil.invokeOnSuccessCallbackMethod;
import static com.codenvy.ide.ext.wso2.client.upload.ImportFilePresenter.ViewCloseHandler;
import static com.codenvy.ide.ext.wso2.shared.Constants.ENDPOINTS_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.MAIN_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SRC_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SYNAPSE_CONFIG_FOLDER_NAME;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link ImportFilePresenter}.
 *
 * @author Valeriy Svydenko
 * @author Andrey Plotnikov
 */
@RunWith(GwtMockitoTestRunner.class)
public class ImportFilePresenterTest {

    private static final String MESSAGE        = "message";
    private static final String NOT_VALID_NAME = "configurationName";
    private static final String VALID_NAME     = "configurationName.xml";
    private static final String PARENT_FOLDER  = "parentFolderName";
    private static final String EMPTY_STRING   = "";

    // captors
    @Captor
    private ArgumentCaptor<Notification>               notificationArgumentCaptor;
    @Captor
    private ArgumentCaptor<AsyncRequestCallback<Void>> voidAsyncRequestCallbackCaptor;
    @Captor
    private ArgumentCaptor<ViewCloseHandler>           viewCloseHandlerCaptor;

    // different values
    @Mock(answer = RETURNS_DEEP_STUBS)
    private FolderNode     parentFolder;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private FolderNode     endpointsFolder;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private FileInfo       fileInfo;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private CurrentProject currentProject;

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
    private Throwable        throwable;
    @Mock
    private RequestException requestException;

    // constructor params
    @Mock
    private ImportFileView       view;
    @Mock
    private NotificationManager  notificationManager;
    @Mock
    private LocalizationConstant locale;
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
    private ImportFilePresenter presenter;

    @Test
    public void actionDelegateShouldBeSet() throws Exception {
        verify(view).setDelegate(presenter);
    }

    @Test
    public void importButtonAndUrlFieldShouldBeDisable() {
        presenter.showDialog();

        verify(view).setEnabledImportButton(false);
        verify(view).setEnterUrlFieldEnabled(false);
        verify(view).setMessage(EMPTY_STRING);
        verify(view).setUseLocalPath(true);
        verify(view).setUseUrl(false);
        verify(view).setUrl(EMPTY_STRING);
        verify(view).showDialog();
    }

    @Test
    public void closeButtonShouldBeExecuted() {
        presenter.onCancelClicked();

        verify(view).close();
    }

    @Test
    public void buttonImportShouldBeDisableWhenFileNameIsNotValid() {
        when(view.getFileName()).thenReturn(NOT_VALID_NAME);
        when(locale.wso2ImportFileFormatError()).thenReturn(MESSAGE);

        presenter.onFileNameChanged();

        verify(view).setMessage(MESSAGE);
        verify(locale).wso2ImportFileFormatError();

        verify(view).setEnabledImportButton(false);
    }

    @Test
    public void buttonImportShouldBeEnableWhenFileNameIsValid() {
        when(view.getFileName()).thenReturn(VALID_NAME);
        presenter.onFileNameChanged();

        verify(view).setEnabledImportButton(true);
        verify(view).setMessage(EMPTY_STRING);
    }

    @Test
    public void buttonImportShouldBeDisableWhenFileNameIsInvalid() {
        when(locale.wso2ImportFileFormatError()).thenReturn(MESSAGE);

        presenter.onFileNameChangedWithInvalidFormat();

        verify(locale).wso2ImportFileFormatError();
        verify(view).setEnabledImportButton(false);
        verify(view).setMessage(MESSAGE);
    }

    @Test
    public void buttonImportShouldBeEnableWhenUrlChangedAndUrlIsNotEmpty() {
        when(view.getUrl()).thenReturn(MESSAGE);
        when(view.isUseUrl()).thenReturn(true);

        presenter.onUrlChanged();

        verify(view).setEnabledImportButton(true);
    }

    @Test
    public void buttonImportShouldBeDisableWhenLocalChangedAndUrlIsNotEmpty() {
        when(view.getUrl()).thenReturn(MESSAGE);
        when(view.isUseUrl()).thenReturn(false);

        presenter.onUrlChanged();

        verify(view).setEnabledImportButton(false);
    }

    @Test
    public void buttonImportShouldBeEnableWhenLocalChangedAndUrlIsEmpty() {
        when(view.getUrl()).thenReturn(EMPTY_STRING);
        when(view.isUseUrl()).thenReturn(false);

        presenter.onUrlChanged();

        verify(view).setEnabledImportButton(false);
    }

    @Test
    public void buttonImportShouldBeDisableWhenUrlChangedAndUrlIsEmpty() {
        when(view.getUrl()).thenReturn(EMPTY_STRING);
        when(view.isUseUrl()).thenReturn(true);

        presenter.onUrlChanged();

        verify(view).setEnabledImportButton(false);
    }

    @Test
    public void urlFieldShouldBeEnableWhenUrlButtonChosen() {
        when(view.getUrl()).thenReturn(MESSAGE);

        presenter.onUseUrlChosen();

        verify(view).setEnterUrlFieldEnabled(true);
        verify(view).setEnabledImportButton(true);
    }

    @Test
    public void localFieldShouldBeEnableWhenLocalButtonChosen() {
        when(view.getFileName()).thenReturn(VALID_NAME);

        presenter.onUseLocalPathChosen();

        verify(view).setEnabledImportButton(true);
    }

    @Test
    public void notificationShouldBeShowWhenResultMessageIsNotEmpty() throws Exception {
        presenter.onSubmitComplete(MESSAGE);

        notificationWithExceptionMessageShouldBeShown();
    }

    private void notificationWithExceptionMessageShouldBeShown() {
        verify(notificationManager).showNotification(notificationArgumentCaptor.capture());

        Notification notification = notificationArgumentCaptor.getValue();

        assertThat(notification.isError(), is(true));
    }

    @Test
    public void notificationShouldBeShowWithoutHTMLTagsWhenResultMessageIsNotEmpty() throws Exception {
        presenter.onSubmitComplete("<pre>" + MESSAGE + "</pre>");

        notificationWithExceptionMessageShouldBeShown();
    }

    @Test
    public void onFailureMethodInSubmitCallbackShouldBeExecutedWhenSomeProblemHappened() throws Exception {
        when(locale.wso2ImportDialogError()).thenReturn(MESSAGE);
        when(dtoFactory.createDto(Matchers.<Class<FileInfo>>anyObject())).thenReturn(mock(FileInfo.class, RETURNS_MOCKS));
        when(view.getFileName()).thenReturn(MESSAGE);
        //noinspection ConstantConditions
        when(appContext.getCurrentProject().getProjectDescription().getName()).thenReturn(MESSAGE);
        when(throwable.getMessage()).thenReturn(MESSAGE);

        presenter.onSubmitComplete(EMPTY_STRING);

        verify(service).detectConfigurationFile(any(FileInfo.class), voidAsyncRequestCallbackCaptor.capture());
        AsyncRequestCallback<Void> callback = voidAsyncRequestCallbackCaptor.getValue();
        invokeOnFailureCallbackMethod(AsyncRequestCallback.class, callback, throwable);

        verify(view).setMessage(MESSAGE);
    }

    @Test
    public void onFailureMethodOnImportClickedCallbackShouldBeExecutedWhenSomeProblemHappened() throws Exception {
        when(throwable.getMessage()).thenReturn(MESSAGE);
        when(dtoFactory.createDto(Matchers.<Class<FileInfo>>anyObject())).thenReturn(mock(FileInfo.class, RETURNS_MOCKS));

        presenter.onImportClicked();

        verify(service).uploadFile(any(FileInfo.class), voidAsyncRequestCallbackCaptor.capture());

        AsyncRequestCallback<Void> callback = voidAsyncRequestCallbackCaptor.getValue();
        invokeOnFailureCallbackMethod(AsyncRequestCallback.class, callback, throwable);

        //noinspection ThrowableResultOfMethodCallIgnored
        verify(throwable, times(2)).getMessage();

        notificationWithExceptionMessageShouldBeShown();
    }

    @Test
    public void overwriteDialogShouldBeShownWhenFileUploadedAndAlreadyExists() throws Exception {
        prepareTestForSuccessResultWhenMethodSubmitCalled();
        when(throwable.getMessage()).thenReturn("already exists. \"}");

        presenter.onImportClicked();

        verify(service).uploadFile(any(FileInfo.class), voidAsyncRequestCallbackCaptor.capture());

        AsyncRequestCallback<Void> callback = voidAsyncRequestCallbackCaptor.getValue();
        invokeOnFailureCallbackMethod(callback.getClass(), callback, throwable);

        verify(overwrite).showDialog(eq(MESSAGE), viewCloseHandlerCaptor.capture());

        ViewCloseHandler closeHandler = viewCloseHandlerCaptor.getValue();
        closeHandler.onCloseView();

        verify(view).close();
    }

    @Test
    public void refreshProjectTreeEventShouldBeFiredWhenFileUploaded() throws Exception {
        prepareTestForSuccessResultWhenMethodSubmitCalled();

        presenter.onImportClicked();

        verify(service).uploadFile(any(FileInfo.class), voidAsyncRequestCallbackCaptor.capture());

        AsyncRequestCallback<Void> callback = voidAsyncRequestCallbackCaptor.getValue();
        invokeOnSuccessCallbackMethod(callback.getClass(), callback, (Void)null);

        verify(eventBus).fireEvent(isA(RefreshProjectTreeEvent.class));
        verify(view).close();
    }

    @Test
    public void setActionShouldBeExecutedWhenImportButtonClicked() throws Exception {
        when(appContext.getCurrentProject()).thenReturn(currentProject);
        when(view.isUseLocalPath()).thenReturn(true);

        presenter.onImportClicked();

        verify(view).setAction(anyString());
        verify(view).submit();
    }

    @Test
    public void nothingShouldHappenedWhenCurrentProjectIsEmpty() throws Exception {
        when(appContext.getCurrentProject()).thenReturn(null);
        when(view.isUseLocalPath()).thenReturn(true);

        presenter.onImportClicked();

        verify(view, never()).setAction(anyString());
        verify(view, never()).submit();
        verify(service, never()).uploadFile(any(FileInfo.class), Matchers.<AsyncRequestCallback<Void>>anyObject());
    }

    @Test
    public void exceptionShouldBeShownWhenRequestExceptionWasHappened() throws Exception {
        when(requestException.getMessage()).thenReturn(MESSAGE);
        when(appContext.getCurrentProject()).thenReturn(currentProject);
        when(view.isUseLocalPath()).thenReturn(false);
        when(dtoFactory.createDto(Matchers.<Class<FileInfo>>anyObject())).thenReturn(mock(FileInfo.class, RETURNS_MOCKS));
        doThrow(requestException).when(service).uploadFile(any(FileInfo.class), Matchers.<AsyncRequestCallback<Void>>anyObject());

        presenter.onImportClicked();

        notificationWithExceptionMessageShouldBeShown();
    }

    private void prepareTestForSuccessResultWhenMethodSubmitCalled() {
        when(dtoFactory.createDto(Matchers.<Class<FileInfo>>anyObject())).thenReturn(fileInfo);
        when(fileInfo.withFileName(anyString()).withProjectName(anyString())).thenReturn(fileInfo);

        when(view.getFileName()).thenReturn(MESSAGE);
        //noinspection ConstantConditions
        when(appContext.getCurrentProject().getProjectDescription().getName()).thenReturn(MESSAGE);
        when(fileInfo.getFileName()).thenReturn(MESSAGE);

        when(appContext.getCurrentProject()).thenReturn(currentProject);
        when(currentProject.getProjectDescription().getName()).thenReturn(ENDPOINTS_FOLDER_NAME);

        when(src.getName()).thenReturn(SRC_FOLDER_NAME);
        when(main.getName()).thenReturn(MAIN_FOLDER_NAME);
        when(synapse_config.getName()).thenReturn(SYNAPSE_CONFIG_FOLDER_NAME);
        when(parentFolder.getName()).thenReturn(PARENT_FOLDER);
    }

    @Test
    public void overwriteDialogShouldBeShownWhenNoProblemHappenedAndResponseContainsAlreadyExist() throws Exception {
        prepareTestForSuccessResultWhenMethodSubmitCalled();
        when(throwable.getMessage()).thenReturn("already exists. \"}");

        presenter.onSubmitComplete(EMPTY_STRING);

        verify(service).detectConfigurationFile(any(FileInfo.class), voidAsyncRequestCallbackCaptor.capture());

        AsyncRequestCallback<Void> callback = voidAsyncRequestCallbackCaptor.getValue();
        invokeOnFailureCallbackMethod(callback.getClass(), callback, throwable);

        verify(overwrite).showDialog(eq(MESSAGE), viewCloseHandlerCaptor.capture());

        ViewCloseHandler closeHandler = viewCloseHandlerCaptor.getValue();
        closeHandler.onCloseView();

        verify(view).close();
    }

    @Test
    public void projectTreeShouldBeRefreshedWhenNoProblemHappened() throws Exception {
        prepareTestForSuccessResultWhenMethodSubmitCalled();

        presenter.onSubmitComplete(EMPTY_STRING);

        verify(service).detectConfigurationFile(any(FileInfo.class), voidAsyncRequestCallbackCaptor.capture());

        AsyncRequestCallback<Void> callback = voidAsyncRequestCallbackCaptor.getValue();
        invokeOnSuccessCallbackMethod(callback.getClass(), callback, (Void)null);

        verify(eventBus).fireEvent(isA(RefreshProjectTreeEvent.class));
        verify(view).close();
    }

    @Test
    public void nothingShouldHappenedWhenCurrentProjectIsEmptyAndUserWasClickingOnSubmit() throws Exception {
        when(appContext.getCurrentProject()).thenReturn(null);

        presenter.onSubmitComplete(EMPTY_STRING);

        verify(service, never()).detectConfigurationFile(any(FileInfo.class), Matchers.<AsyncRequestCallback<Void>>anyObject());
        verify(notificationManager, never()).showNotification(any(Notification.class));
    }

    @Test
    public void exceptionShouldBeShownWhenRequestExceptionWasHappenedWhenDetectingConfFile() throws Exception {
        prepareTestForSuccessResultWhenMethodSubmitCalled();

        when(requestException.getMessage()).thenReturn(MESSAGE);
        doThrow(requestException).when(service).detectConfigurationFile(any(FileInfo.class),
                                                                        Matchers.<AsyncRequestCallback<Void>>anyObject());

        presenter.onSubmitComplete(EMPTY_STRING);

        notificationWithExceptionMessageShouldBeShown();
    }

}