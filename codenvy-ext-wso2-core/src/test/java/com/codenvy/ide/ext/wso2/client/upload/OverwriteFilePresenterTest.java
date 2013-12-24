/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 *  [2012] - [2013] Codenvy, S.A. 
 *  All Rights Reserved.
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

import com.codenvy.ide.api.notification.Notification;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.resources.ResourceProvider;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.dto.DtoFactory;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2ClientService;
import com.codenvy.ide.ext.wso2.client.upload.overwrite.OverwriteFilePresenter;
import com.codenvy.ide.ext.wso2.client.upload.overwrite.OverwriteFileView;
import com.codenvy.ide.ext.wso2.shared.FileInfo;
import com.codenvy.ide.resources.model.File;
import com.codenvy.ide.resources.model.Folder;
import com.codenvy.ide.resources.model.Project;
import com.codenvy.ide.resources.model.Resource;
import com.codenvy.ide.rest.AsyncRequestCallback;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link OverwriteFilePresenter}
 *
 * @author Valeriy Svydenko
 */
@RunWith(MockitoJUnitRunner.class)
public class OverwriteFilePresenterTest {

    private final String DELETE_FILE_OPERATION    = "delete";
    private final String RENAME_FILE_OPERATION    = "rename";
    private final String OVERWRITE_FILE_OPERATION = "overwrite";
    private final String FILE_NAME                = "fileName";
    private final String PROJECT_NAME             = "projectName";

    @Mock
    private OverwriteFileView                    view;
    @Mock
    private DtoFactory                           dtoFactory;
    @Mock
    private ResourceProvider                     resourceProvider;
    @Mock
    private NotificationManager                  notificationManager;
    @Mock
    private WSO2ClientService                    service;
    @Mock
    private Project                              activeProject;
    @Mock
    private EventBus                             eventBus;
    @Mock
    private LocalizationConstant                 local;
    @Mock
    private Folder                               src;
    @Mock
    private Folder                               main;
    @Mock
    private Folder                               synapse_config;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private ImportFilePresenter                  importFilePresenter;
    @Mock
    private ImportFilePresenter.ViewCloseHandler parentViewUtils;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private Folder                               parentFolder;
    private String                               parentFolderName;
    @InjectMocks
    private OverwriteFilePresenter               overwriteFilePresenter;

    @Before
    public void setUp() throws Exception {
        verify(view).setDelegate(eq(overwriteFilePresenter));
    }

    @SuppressWarnings({"unchecked", "NonJREEmulationClassesInClientCode"})
    @Test
    public void cancelButtonShouldBeExecutedWhenSomeProblemHappened() throws Exception {
        when(dtoFactory.createDto(Matchers.<Class<FileInfo>>anyObject()))
                .thenReturn(mock(FileInfo.class, Mockito.RETURNS_MOCKS));
        final Throwable throwable = mock(Throwable.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();
                AsyncRequestCallback<String> callback = (AsyncRequestCallback<String>)arguments[2];
                Method onFailure = GwtReflectionUtils.getMethod(callback.getClass(), "onFailure");
                onFailure.invoke(callback, throwable);
                return callback;
            }
        }).when(service).modifyFile((FileInfo)anyObject(), anyString(), (AsyncRequestCallback<String>)anyObject());
        when(resourceProvider.getActiveProject()).thenReturn(activeProject);
        when(view.getFileName()).thenReturn(FILE_NAME);
        when(resourceProvider.getActiveProject().getName()).thenReturn(PROJECT_NAME);


        overwriteFilePresenter.onCancelButtonClicked();

        verify(throwable).getMessage();
        verify(notificationManager).showNotification((Notification)anyObject());
    }

    private void prepareDataForExecutingTests() {
        when(dtoFactory.createDto(Matchers.<Class<FileInfo>>anyObject()))
                .thenReturn(mock(FileInfo.class, Mockito.RETURNS_MOCKS));
        when(resourceProvider.getActiveProject()).thenReturn(activeProject);
        when(view.getFileName()).thenReturn(FILE_NAME);
        when(resourceProvider.getActiveProject().getName()).thenReturn(PROJECT_NAME);

        when(resourceProvider.getActiveProject()).thenReturn(activeProject);
        when(activeProject.getChildren()).thenReturn(Collections.<Resource>createArray(src));
        when(src.getChildren()).thenReturn(Collections.<Resource>createArray(main));
        when(main.getChildren()).thenReturn(Collections.<Resource>createArray(synapse_config));
        when(src.getName()).thenReturn(SRC_FOLDER_NAME);
        when(main.getName()).thenReturn(MAIN_FOLDER_NAME);
        when(synapse_config.getName()).thenReturn(SYNAPSE_CONFIG_FOLDER_NAME);
        when(parentFolder.getName()).thenReturn(parentFolderName);

    }

    @SuppressWarnings({"unchecked", "NonJREEmulationClassesInClientCode"})
    @Test
    public void cancelButtonShouldBeExecutedWithoutProblems() throws Exception {
        prepareDataForExecutingTests();
        final Resource file = mock(File.class);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                AsyncRequestCallback<String> callback = (AsyncRequestCallback<String>)arguments[2];
                Method onSuccess = GwtReflectionUtils.getMethod(callback.getClass(), "onSuccess");
                onSuccess.invoke(callback, "");
                return callback;
            }
        }).when(service).modifyFile((FileInfo)anyObject(), anyString(), (AsyncRequestCallback<String>)anyObject());

        overwriteFilePresenter.onCancelButtonClicked();

        verify(view).close();
    }

    @SuppressWarnings({"unchecked", "NonJREEmulationClassesInClientCode"})
    @Test
    public void renameButtonShouldBeExecutedWhenSomeProblemHappened() throws Exception {
        when(dtoFactory.createDto(Matchers.<Class<FileInfo>>anyObject()))
                .thenReturn(mock(FileInfo.class, Mockito.RETURNS_MOCKS));
        final Throwable throwable = mock(Throwable.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();
                AsyncRequestCallback<String> callback = (AsyncRequestCallback<String>)arguments[2];
                Method onFailure = GwtReflectionUtils.getMethod(callback.getClass(), "onFailure");
                onFailure.invoke(callback, throwable);
                return callback;
            }
        }).when(service).modifyFile((FileInfo)anyObject(), anyString(), (AsyncRequestCallback<String>)anyObject());
        when(resourceProvider.getActiveProject()).thenReturn(activeProject);
        when(view.getFileName()).thenReturn(FILE_NAME);
        when(resourceProvider.getActiveProject().getName()).thenReturn(PROJECT_NAME);

/*      overwriteFilePresenter.onRenameButtonClicked();

        verify(parentViewUtils).onCloseView();

        verify(throwable).getMessage();
        verify(notificationManager).showNotification((Notification)anyObject());*/
    }
}
