/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 * [2012] - [2014] Codenvy, S.A. 
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
package com.codenvy.ide.ext.wso2.client.action;

import com.codenvy.ide.api.notification.Notification;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.ui.action.ActionEvent;
import com.codenvy.ide.api.user.UserClientService;
import com.codenvy.ide.dto.DtoFactory;
import com.codenvy.ide.ext.git.shared.GitUrlVendorInfo;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2ClientService;
import com.codenvy.ide.rest.AsyncRequestCallback;
import com.google.gwt.http.client.RequestException;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Method;

import static com.codenvy.ide.security.oauth.OAuthStatus.FAILED;
import static com.codenvy.ide.security.oauth.OAuthStatus.LOGGED_IN;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Here we're testing {@link LoginAction}.
 *
 * @author Andrey Plotnikov
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginActionTest {

    private static final String REST_CONTEXT = "rest/context";
    private static final String SOME_CONTEXT = "someContext";

    @Mock
    private WSO2ClientService    service;
    @Mock
    private NotificationManager  notificationManager;
    @Mock
    private UserClientService    userService;
    @Mock
    private DtoFactory           dtoFactory;
    @Mock
    private LocalizationConstant locale;
    @Mock
    private ActionEvent          actionEvent;
    @Mock
    private Throwable            throwable;
    private LoginAction          action;

    @Before
    public void setUp() throws Exception {
        action = new LoginAction(service, notificationManager, REST_CONTEXT, userService, dtoFactory, locale);

        verify(locale).loginActionTitle();
    }


    @SuppressWarnings({"unchecked", "NonJREEmulationClassesInClientCode"})
    @Test
    public void notificationShouldBeShownWhenGetUserRequestIsFailed() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                AsyncRequestCallback<String> callback = (AsyncRequestCallback<String>)arguments[0];
                Method onFailure = GwtReflectionUtils.getMethod(callback.getClass(), "onFailure");
                onFailure.invoke(callback, throwable);
                return callback;
            }
        }).when(userService).getUser((AsyncRequestCallback<String>)anyObject());

        action.actionPerformed(actionEvent);

        verify(notificationManager).showNotification((Notification)anyObject());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void notificationShouldBeShownWhenGetUserRequestThrowRequestException() throws Exception {
        doThrow(RequestException.class).when(userService).getUser((AsyncRequestCallback<String>)anyObject());

        action.actionPerformed(actionEvent);

        verify(notificationManager).showNotification((Notification)anyObject());
    }

    @SuppressWarnings({"unchecked", "NonJREEmulationClassesInClientCode"})
    @Test
    public void notificationShouldBeShownWhenGetWSO2ServiceInfoRequestIsFailed() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                AsyncRequestCallback<String> callback = (AsyncRequestCallback<String>)arguments[0];
                Method onFailure = GwtReflectionUtils.getMethod(callback.getClass(), "onSuccess");
                onFailure.invoke(callback, SOME_CONTEXT);
                return callback;
            }
        }).when(userService).getUser((AsyncRequestCallback<String>)anyObject());
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                AsyncRequestCallback<String> callback = (AsyncRequestCallback<String>)arguments[0];
                Method onFailure = GwtReflectionUtils.getMethod(callback.getClass(), "onFailure");
                onFailure.invoke(callback, throwable);
                return callback;
            }
        }).when(service).getWSO2ServiceInfo((AsyncRequestCallback<GitUrlVendorInfo>)anyObject());

        action.actionPerformed(actionEvent);

        verify(notificationManager).showNotification((Notification)anyObject());
    }

    @SuppressWarnings({"unchecked", "NonJREEmulationClassesInClientCode"})
    @Test
    public void notificationShouldBeShownWhenGetWSO2ServiceInfoRequestThrowRequestException() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                AsyncRequestCallback<String> callback = (AsyncRequestCallback<String>)arguments[0];
                Method onFailure = GwtReflectionUtils.getMethod(callback.getClass(), "onSuccess");
                onFailure.invoke(callback, SOME_CONTEXT);
                return callback;
            }
        }).when(userService).getUser((AsyncRequestCallback<String>)anyObject());
        doThrow(RequestException.class).when(service).getWSO2ServiceInfo((AsyncRequestCallback<GitUrlVendorInfo>)anyObject());

        action.actionPerformed(actionEvent);

        verify(notificationManager).showNotification((Notification)anyObject());
    }

    @Test
    public void notificationShouldBeShownWhenAuthorizationIsSuccessful() throws Exception {
        action.onAuthenticated(LOGGED_IN);

        verify(notificationManager).showNotification((Notification)anyObject());
    }

    @Test
    public void notificationShouldBeNotShownWhenAuthorizationIsFailed() throws Exception {
        action.onAuthenticated(FAILED);

        verify(notificationManager, never()).showNotification((Notification)anyObject());
    }
}