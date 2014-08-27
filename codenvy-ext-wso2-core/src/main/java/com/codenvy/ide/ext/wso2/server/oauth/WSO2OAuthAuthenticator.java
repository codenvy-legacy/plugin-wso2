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

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashSet;

/**
 * OAuth authentication for wso2 account.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
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
                                                clientSecrets.getDetails().getAuthUri()
              )
                      .setScopes(Collections.<String>emptyList())
                      .setCredentialStore(credentialStore).build(),
              new HashSet<>(clientSecrets.getDetails().getRedirectUris())
             );

    }

    /** {@inheritDoc} */
    @Override
    public User getUser(@Nonnull OAuthToken accessToken) throws OAuthAuthenticationException {
        return null;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getOAuthProvider() {
        return "wso2";
    }
}