/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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