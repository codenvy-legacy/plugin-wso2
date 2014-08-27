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


import com.codenvy.ide.security.oauth.server.OAuthAuthenticator;
import com.codenvy.ide.security.oauth.server.OAuthAuthenticatorProvider;
import com.google.api.client.auth.oauth2.MemoryCredentialStore;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Arrays;

/**
 * OAuth provider for wso2 account.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
@Singleton
public class WSO2OAuthAuthenticatorProvider implements OAuthAuthenticatorProvider {
    private final WSO2OAuthAuthenticator wso2OAuthAuthenticator;

    @Inject
    public WSO2OAuthAuthenticatorProvider(@Named("security.local.oauth.wso2-client-id") String clientId,
                                          @Named("security.local.oauth.wso2-client-secret") String clientSecret,
                                          @Named("security.local.oauth.wso2-auth-uri") String authUri,
                                          @Named("security.local.oauth.wso2-token-uri") String tokenUri,
                                          @Named("security.local.oauth.wso2-redirect-uris") String redirectUris
                                         ) {
        GoogleClientSecrets.Details web = new GoogleClientSecrets.Details();

        web.setAuthUri(authUri);
        web.setClientId(clientId);
        web.setClientSecret(clientSecret);
        web.setRedirectUris(Arrays.asList(redirectUris));
        web.setTokenUri(tokenUri);

        GoogleClientSecrets secrets = new GoogleClientSecrets();
        secrets.setWeb(web);

        wso2OAuthAuthenticator = new WSO2OAuthAuthenticator(new MemoryCredentialStore(), secrets);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public OAuthAuthenticator getAuthenticator() {
        return wso2OAuthAuthenticator;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getId() {
        return "wso2";
    }
}
