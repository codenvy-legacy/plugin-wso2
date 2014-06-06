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

package com.codenvy.ide.ext.wso2.server.oauth;

import com.codenvy.api.auth.shared.dto.OAuthToken;
import com.codenvy.ide.security.oauth.server.OAuthAuthenticationException;
import com.codenvy.ide.security.oauth.server.OAuthAuthenticator;
import com.codenvy.ide.security.oauth.shared.User;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.CredentialStore;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;

import java.util.Collections;
import java.util.HashSet;

/**
 * OAuth authentication for wso2 account.
 *
 * @author Valeriy Svydenko
 */
public class WSO2OAuthAuthenticator extends OAuthAuthenticator {

    public WSO2OAuthAuthenticator(CredentialStore credentialStore, GoogleClientSecrets clientSecrets) {
        super(new AuthorizationCodeFlow.Builder(BearerToken.authorizationHeaderAccessMethod(),
                                                new NetHttpTransport(),
                                                new JacksonFactory(),
                                                new GenericUrl(clientSecrets.getDetails().getTokenUri()),
                                                new ClientParametersAuthentication(clientSecrets.getDetails().getClientId(),
                                                                                   clientSecrets.getDetails().getClientSecret()),
                                                clientSecrets.getDetails().getClientId(),
                                                clientSecrets.getDetails().getAuthUri())
                      .setScopes(Collections.<String>emptyList())
                      .setCredentialStore(credentialStore).build(),
              new HashSet<>(clientSecrets.getDetails().getRedirectUris()));

    }

    /** {@inheritDoc} */
    @Override
    public User getUser(OAuthToken accessToken) throws OAuthAuthenticationException {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public String getOAuthProvider() {
        return "wso2";
    }
}