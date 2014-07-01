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
import com.codenvy.api.user.shared.dto.User;
import com.codenvy.ide.api.notification.Notification;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.ui.action.Action;
import com.codenvy.ide.api.ui.action.ActionEvent;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.commons.WSO2AsyncRequestCallback;
import com.codenvy.ide.rest.DtoUnmarshallerFactory;
import com.codenvy.ide.security.oauth.JsOAuthWindow;
import com.codenvy.ide.security.oauth.OAuthCallback;
import com.codenvy.ide.security.oauth.OAuthStatus;
import com.codenvy.ide.ui.dialogs.ask.Ask;
import com.codenvy.ide.ui.dialogs.ask.AskHandler;
import com.codenvy.ide.util.Config;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import static com.codenvy.ide.api.notification.Notification.Type.INFO;
import static com.codenvy.ide.security.oauth.OAuthStatus.LOGGED_IN;

/**
 * The action for OAuth authorization on WSO2 AppFactory.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class LoginAction extends Action implements OAuthCallback {
    private final String WSO2_VENDOR_NAME = "wso2";

    private final DtoUnmarshallerFactory dtoUnmarshallerFactory;
    private final NotificationManager    notificationManager;
    private final String                 restContext;
    private final UserServiceClient      userService;
    private final LocalizationConstant   locale;

    @Inject
    public LoginAction(NotificationManager notificationManager,
                       @Named("restContext") String restContext,
                       UserServiceClient userService,
                       LocalizationConstant locale,
                       DtoUnmarshallerFactory dtoUnmarshallerFactory) {
        super(locale.loginActionTitle(), null, null);

        this.notificationManager = notificationManager;
        this.restContext = restContext;
        this.userService = userService;
        this.locale = locale;
        this.dtoUnmarshallerFactory = dtoUnmarshallerFactory;
    }

    /** {@inheritDoc} */
    @Override
    public void actionPerformed(ActionEvent e) {
        userService.getCurrentUser(
                new WSO2AsyncRequestCallback<User>(dtoUnmarshallerFactory.newUnmarshaller(User.class), notificationManager) {
                    @Override
                    protected void onSuccess(final User user) {
                        Ask ask = new Ask(locale.authorizeNeedTitleOauth(), locale.authorizeNeedBodyOauth(WSO2_VENDOR_NAME),
                                          new AskHandler() {
                                              @Override
                                              public void onOk() {
                                                  showPopUp(user);
                                              }
                                          });
                        ask.show();
                    }
                });
    }

    /** {@inheritDoc} */
    @Override
    public void onAuthenticated(OAuthStatus authStatus) {
        if (LOGGED_IN.equals(authStatus)) {
            Notification notification = new Notification(locale.loginSuccess(), INFO);
            notificationManager.showNotification(notification);
        }
    }

    private void showPopUp(User user) {
        String authUrl = restContext + "/oauth/authenticate?oauth_provider=" + WSO2_VENDOR_NAME + "&userId=" + user.getId() +
                         "&redirect_after_login=" + Window.Location.getProtocol() + "//" + Window.Location.getHost() + "/ide/" +
                         Config.getWorkspaceName();

        JsOAuthWindow authWindow = new JsOAuthWindow(authUrl, "error.url", 500, 980, LoginAction.this);

        authWindow.loginWithOAuth();
    }
}