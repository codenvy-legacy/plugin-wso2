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
package com.codenvy.ide.ext.wso2.client.action;

import com.codenvy.ide.api.notification.Notification;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.ui.action.ActionEvent;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.codenvy.ide.ext.wso2.client.upload.ImportFilePresenter;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Method;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Here we're testing {@link ImportSynapseAction}.
 *
 * @author Valeriy Svydenko
 */
@RunWith(MockitoJUnitRunner.class)
public class ImportSynapseActionTest {

    @Mock
    private AsyncProvider<ImportFilePresenter> importFilePresenter;
    @Mock
    private NotificationManager                notificationManager;
    @Mock
    private ImportFilePresenter                presenter;
    @Mock
    private WSO2Resources                      wso2Resources;
    @Mock
    private LocalizationConstant               locale;
    @Mock
    private ActionEvent                        actionEvent;
    @InjectMocks
    private ImportSynapseAction                action;

    @SuppressWarnings({"unchecked", "NonJREEmulationClassesInClientCode"})
    @Test
    public void importWindowShouldBeNotShow() {
        final Throwable throwable = mock(Throwable.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                AsyncCallback<Void> callback = (AsyncCallback<Void>)arguments[0];
                Method onFailure = GwtReflectionUtils.getMethod(callback.getClass(), "onFailure");
                onFailure.invoke(callback, throwable);
                return callback;
            }
        }).when(importFilePresenter).get((AsyncCallback<ImportFilePresenter>)anyObject());

        action.actionPerformed(actionEvent);

        verify(throwable).getMessage();
        verify(notificationManager).showNotification((Notification)anyObject());
    }

    @SuppressWarnings({"unchecked", "NonJREEmulationClassesInClientCode"})
    @Test
    public void importWindowShouldBeShow() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                AsyncCallback<Void> callback = (AsyncCallback<Void>)arguments[0];
                Method onSuccess = GwtReflectionUtils.getMethod(callback.getClass(), "onSuccess");
                onSuccess.invoke(callback, presenter);
                return callback;
            }
        }).when(importFilePresenter).get((AsyncCallback<ImportFilePresenter>)anyObject());

        action.actionPerformed(actionEvent);

        verify(presenter).showDialog();
    }

}
