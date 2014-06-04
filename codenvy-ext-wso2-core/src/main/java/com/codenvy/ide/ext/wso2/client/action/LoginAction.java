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
import com.codenvy.api.user.shared.dto.User;
import com.codenvy.ide.api.notification.Notification;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.ui.action.Action;
import com.codenvy.ide.api.ui.action.ActionEvent;
import com.codenvy.ide.dto.DtoFactory;
import com.codenvy.ide.ext.git.shared.GitUrlVendorInfo;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2ClientService;
import com.codenvy.ide.ext.wso2.client.commons.WSO2AsyncRequestCallback;
import com.codenvy.ide.rest.DtoUnmarshallerFactory;
import com.codenvy.ide.rest.StringUnmarshaller;
import com.codenvy.ide.security.oauth.JsOAuthWindow;
import com.codenvy.ide.security.oauth.OAuthCallback;
import com.codenvy.ide.security.oauth.OAuthStatus;
import com.codenvy.ide.util.Config;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import javax.validation.constraints.NotNull;

import static com.codenvy.ide.api.notification.Notification.Type.ERROR;
import static com.codenvy.ide.api.notification.Notification.Type.INFO;
import static com.codenvy.ide.security.oauth.OAuthStatus.LOGGED_IN;

/**
 * The action for OAuth authorization on WSO2 AppFactory.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class LoginAction extends Action implements OAuthCallback {
    private final DtoUnmarshallerFactory dtoUnmarshallerFactory;
    private final WSO2ClientService      service;
    private final NotificationManager    notificationManager;
    private final String                 restContext;
    private final UserServiceClient      userService;
    private final DtoFactory             dtoFactory;
    private final LocalizationConstant   locale;

    @Inject
    public LoginAction(WSO2ClientService service,
                       NotificationManager notificationManager,
                       @Named("restContext") String restContext,
                       UserServiceClient userService,
                       DtoFactory dtoFactory,
                       LocalizationConstant locale,
                       DtoUnmarshallerFactory dtoUnmarshallerFactory) {
        super(locale.loginActionTitle(), null, null);

        this.service = service;
        this.notificationManager = notificationManager;
        this.restContext = restContext;
        this.userService = userService;
        this.dtoFactory = dtoFactory;
        this.locale = locale;
        this.dtoUnmarshallerFactory = dtoUnmarshallerFactory;
    }

    /** {@inheritDoc} */
    @Override
    public void actionPerformed(ActionEvent e) {
        userService.getCurrentUser(
                new WSO2AsyncRequestCallback<User>(dtoUnmarshallerFactory.newUnmarshaller(User.class), notificationManager) {
                    @Override
                    protected void onSuccess(User user) {
                        final String userId = user.getId();
                        try {
                            service.getWSO2ServiceInfo(
                                    new WSO2AsyncRequestCallback<String>(new StringUnmarshaller(), notificationManager) {
                                        @Override
                                        protected void onSuccess(String result) {
                                            GitUrlVendorInfo gitUrlVendorInfo =
                                                    dtoFactory.createDtoFromJson(result, GitUrlVendorInfo.class);
                                            boolean permitToRedirect =
                                                    Window.confirm(locale.authorizeNeedBodyOauth(gitUrlVendorInfo.getVendorName()));
                                            if (permitToRedirect) {
                                                //TODO reworking OAuth functionality
                                                String authUrl =
                                                        restContext + "/oauth/authenticate?oauth_provider=" +
                                                        gitUrlVendorInfo.getVendorName() +
                                                        "&userId=" + userId + "&redirect_after_login=/ide/" +
                                                        Config.getWorkspaceName();
                                                JsOAuthWindow authWindow =
                                                        new JsOAuthWindow(authUrl, "error.url", 500, 980, LoginAction.this);
                                                authWindow.loginWithOAuth();
                                            }
                                        }
                                    });
                        } catch (RequestException exception) {
                            showException(exception);
                        }
                    }
                });
    }

    private void showException(@NotNull Throwable exception) {
        Notification notification = new Notification(exception.getMessage(), ERROR);
        notificationManager.showNotification(notification);
    }

    /** {@inheritDoc} */
    @Override
    public void onAuthenticated(OAuthStatus authStatus) {
        if (LOGGED_IN.equals(authStatus)) {
            Notification notification = new Notification(locale.loginSuccess(), INFO);
            notificationManager.showNotification(notification);
        }
    }
}