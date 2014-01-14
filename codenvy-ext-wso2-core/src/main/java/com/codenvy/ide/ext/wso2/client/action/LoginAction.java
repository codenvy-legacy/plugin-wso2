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
import com.codenvy.ide.api.ui.action.Action;
import com.codenvy.ide.api.ui.action.ActionEvent;
import com.codenvy.ide.api.user.User;
import com.codenvy.ide.api.user.UserClientService;
import com.codenvy.ide.dto.DtoFactory;
import com.codenvy.ide.ext.git.client.marshaller.GitUrlInfoUnmarshaller;
import com.codenvy.ide.ext.git.shared.GitUrlVendorInfo;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2ClientService;
import com.codenvy.ide.rest.AsyncRequestCallback;
import com.codenvy.ide.rest.StringUnmarshaller;
import com.codenvy.ide.security.oauth.JsOAuthWindow;
import com.codenvy.ide.security.oauth.OAuthCallback;
import com.codenvy.ide.security.oauth.OAuthStatus;
import com.codenvy.ide.util.Utils;
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
 */
public class LoginAction extends Action implements OAuthCallback {

    private WSO2ClientService    service;
    private NotificationManager  notificationManager;
    private String               restContext;
    private UserClientService    userService;
    private DtoFactory           dtoFactory;
    private LocalizationConstant locale;

    @Inject
    public LoginAction(WSO2ClientService service,
                       NotificationManager notificationManager,
                       @Named("restContext") String restContext,
                       UserClientService userService,
                       DtoFactory dtoFactory,
                       LocalizationConstant locale) {

        super(locale.loginActionTitle(), null, null);

        this.service = service;
        this.notificationManager = notificationManager;
        this.restContext = restContext;
        this.userService = userService;
        this.dtoFactory = dtoFactory;
        this.locale = locale;
    }

    /** {@inheritDoc} */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            userService.getUser(new AsyncRequestCallback<String>(new StringUnmarshaller()) {
                @Override
                protected void onSuccess(String result) {
                    final User user = dtoFactory.createDtoFromJson(result, User.class);
                    GitUrlInfoUnmarshaller unmarshaller = new GitUrlInfoUnmarshaller(new GitUrlVendorInfo());
                    try {
                        service.getWSO2ServiceInfo(new AsyncRequestCallback<GitUrlVendorInfo>(unmarshaller) {
                            @Override
                            protected void onSuccess(GitUrlVendorInfo result) {
                                boolean permitToRedirect = Window.confirm(locale.authorizeNeedBodyOauth(result.getVendorName()));
                                if (permitToRedirect) {
                                    String authUrl = restContext + "/oauth/authenticate?oauth_provider=" + result.getVendorName() +
                                                     "&userId=" + user.getUserId() + "&redirect_after_login=/ide/" +
                                                     Utils.getWorkspaceName();
                                    JsOAuthWindow authWindow = new JsOAuthWindow(authUrl, "error.url", 500, 980, LoginAction.this);
                                    authWindow.loginWithOAuth();
                                }
                            }

                            @Override
                            protected void onFailure(Throwable exception) {
                                showException(exception);
                            }
                        });
                    } catch (RequestException e1) {
                        showException(e1);
                    }
                }

                @Override
                protected void onFailure(Throwable exception) {
                    showException(exception);
                }
            });
        } catch (RequestException e1) {
            showException(e1);
        }
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