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

package com.codenvy.ide.ext.wso2.client.upload;

import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.parts.ConsolePart;
import com.codenvy.ide.api.resources.ResourceProvider;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.dto.DtoFactory;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2ClientService;
import com.codenvy.ide.ext.wso2.shared.FileInfo;
import com.codenvy.ide.resources.model.Folder;
import com.codenvy.ide.resources.model.Project;
import com.codenvy.ide.resources.model.Resource;
import com.codenvy.ide.rest.AsyncRequestCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Method;

import static com.codenvy.ide.ext.wso2.shared.Constants.MAIN_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SRC_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SYNAPSE_CONFIG_FOLDER_NAME;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link ImportFilePresenter}.
 *
 * @author Valeriy Svydenko
 */
@RunWith(MockitoJUnitRunner.class)
public class ImportFilePresenterTest {
    private static final String MESSAGE        = "message";
    private static final String NOT_VALID_NAME = "configurationName";
    private static final String VALID_NAME     = "configurationName.xml";

    @Mock(answer = RETURNS_DEEP_STUBS)
    private Folder   parentFolder;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private FileInfo fileInfo;
    @Mock
    private Project  activeProject;
    @Mock
    private Folder   src;
    @Mock
    private Folder   main;

    @Mock
    private Folder   synapse_config;
    @Mock
    private Resource file;

    @Mock
    ImportFileView       view;
    @Mock
    ConsolePart          console;
    @Mock
    NotificationManager  notification;
    @Mock
    LocalizationConstant local;
    @Mock
    private EventBus          eventBus;
    @Mock
    private WSO2ClientService service;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private DtoFactory        dtoFactory;
    @InjectMocks
    ImportFilePresenter importFilePresenter;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private   ResourceProvider resourceProvider;
    protected String           parentFolderName;

    @Test
    public void importButtonAndUrlFieldShouldBeDisable() {
        importFilePresenter.showDialog();

        verify(view).setEnabledImportButton(eq(false));
        verify(view).setEnterUrlFieldEnabled(eq(false));
    }

    @Test
    public void closeButtonShouldBeExecuted() {
        importFilePresenter.onCancelClicked();

        verify(view).close();
    }

    public void prepareWhenUrlButtonChosen() {
        importFilePresenter.onUseUrlChosen();

        when(view.getUrl()).thenReturn(MESSAGE);
        when(view.isUseUrl()).thenReturn(true);
    }

    @Test
    public void buttonImportShouldBeDisableWhenFileNameIsNotValid() {
        when(view.getFileName()).thenReturn(NOT_VALID_NAME);
        importFilePresenter.onFileNameChanged();

        verify(view).setEnabledImportButton(eq(false));
    }

    @Test
    public void buttonImportShouldBeEnableWhenFileNameIsValid() {
        when(view.getFileName()).thenReturn(VALID_NAME);
        importFilePresenter.onFileNameChanged();

        verify(view).setEnabledImportButton(eq(true));
    }

    @Test
    public void buttonImportShouldBeDisableWhenFileNameIsInvalid() {
        importFilePresenter.onFileNameChangedWithInvalidFormat();

        verify(view).setEnabledImportButton(eq(false));
    }

    @Test
    public void buttonImportShouldBeEnableWhenUrlChangedAndUrlIsNotEmpty() {
        when(view.getUrl()).thenReturn(MESSAGE);
        when(view.isUseUrl()).thenReturn(true);

        importFilePresenter.onUrlChanged();

        verify(view).setEnabledImportButton(eq(true));
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

    @SuppressWarnings({"unchecked", "NonJREEmulationClassesInClientCode"})
    @Test
    public void onFailureMethodInSubmitCallbackShouldBeExecutedWhenSomeProblemHappened() throws Exception {
        final Throwable throwable = mock(Throwable.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                AsyncRequestCallback<Void> callback = (AsyncRequestCallback<Void>)arguments[1];
                Method onFailure = GwtReflectionUtils.getMethod(callback.getClass(), "onFailure");
                onFailure.invoke(callback, throwable);
                return callback;
            }
        }).when(service).detectConfigurationFile((FileInfo)anyObject(), (AsyncRequestCallback<String>)anyObject());

        when(local.wso2ImportDialogError()).thenReturn(MESSAGE);
        when(dtoFactory.createDto(Matchers.<Class<FileInfo>>anyObject()))
                .thenReturn(mock(FileInfo.class, Mockito.RETURNS_MOCKS));
        when(view.getFileName()).thenReturn(MESSAGE);
        when(resourceProvider.getActiveProject().getName()).thenReturn(MESSAGE);

        importFilePresenter.onSubmitComplete("");

        verify(view).setMessage(Matchers.eq(MESSAGE));
    }

    @SuppressWarnings({"unchecked", "NonJREEmulationClassesInClientCode"})
    @Test
    public void onSuccessMethodInSubmitCallbackShouldBeExecutedWhenNoProblemHappened() throws Exception {
        when(dtoFactory.createDto((Class<FileInfo>)anyObject())).thenReturn(fileInfo);
        when(fileInfo.withFileName(anyString()).withProjectName(anyString())).thenReturn(fileInfo);

        when(view.getFileName()).thenReturn(MESSAGE);
        when(resourceProvider.getActiveProject().getName()).thenReturn(MESSAGE);
        when(fileInfo.getFileName()).thenReturn(MESSAGE);

        when(resourceProvider.getActiveProject()).thenReturn(activeProject);
        when(activeProject.getChildren()).thenReturn(Collections.<Resource>createArray(src));
        when(src.getChildren()).thenReturn(Collections.<Resource>createArray(main));
        when(main.getChildren()).thenReturn(Collections.<Resource>createArray(synapse_config));
        when(synapse_config.getChildren()).thenReturn(Collections.<Resource>createArray(parentFolder));

        when(src.getName()).thenReturn(SRC_FOLDER_NAME);
        when(main.getName()).thenReturn(MAIN_FOLDER_NAME);
        when(synapse_config.getName()).thenReturn(SYNAPSE_CONFIG_FOLDER_NAME);
        when(parentFolder.getName()).thenReturn(parentFolderName);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                AsyncRequestCallback<Void> callback = (AsyncRequestCallback<Void>)arguments[1];
                Method onSuccess = GwtReflectionUtils.getMethod(callback.getClass(), "onSuccess");
                onSuccess.invoke(callback, "");
                return callback;
            }
        }).when(service).detectConfigurationFile((FileInfo)anyObject(), (AsyncRequestCallback<String>)anyObject());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                AsyncCallback<Void> callback = (AsyncCallback<Void>)arguments[1];
                Method onSuccess = GwtReflectionUtils.getMethod(callback.getClass(), "onSuccess");
                onSuccess.invoke(callback, parentFolder);
                return callback;
            }
        }).when(activeProject).refreshTree((Folder)anyObject(), (AsyncCallback<Folder>)anyObject());


        importFilePresenter.onSubmitComplete("");

        verify(view).close();

    }
}