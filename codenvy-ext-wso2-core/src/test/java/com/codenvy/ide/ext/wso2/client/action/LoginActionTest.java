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

import com.codenvy.api.user.gwt.client.UserServiceClient;
import com.codenvy.ide.api.notification.Notification;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.ui.action.ActionEvent;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.rest.DtoUnmarshallerFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.codenvy.ide.security.oauth.OAuthStatus.FAILED;
import static com.codenvy.ide.security.oauth.OAuthStatus.LOGGED_IN;
import static org.mockito.Matchers.anyObject;
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

    @Mock
    private NotificationManager    notificationManager;
    @Mock
    private UserServiceClient      userService;
    @Mock
    private LocalizationConstant   locale;
    @Mock
    private ActionEvent            actionEvent;
    @Mock
    private DtoUnmarshallerFactory dtoUnmarshallerFactory;
    @Mock
    private Throwable              throwable;
    private LoginAction            action;

    @Before
    public void setUp() throws Exception {
        action = new LoginAction(notificationManager, REST_CONTEXT, userService, locale, dtoUnmarshallerFactory);

        verify(locale).loginActionTitle();
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