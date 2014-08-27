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
package com.codenvy.ide.ext.wso2.client;

import com.codenvy.ide.MimeType;
import com.codenvy.ide.ext.wso2.shared.FileInfo;
import com.codenvy.ide.rest.AsyncRequestCallback;
import com.codenvy.ide.rest.AsyncRequestFactory;
import com.codenvy.ide.rest.HTTPHeader;
import com.codenvy.ide.ui.loader.Loader;
import com.codenvy.ide.util.Config;
import com.google.gwt.http.client.RequestException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import javax.annotation.Nonnull;

import static com.codenvy.ide.rest.HTTPHeader.CONTENT_TYPE;

/**
 * The implementation of {@link WSO2ClientService}.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
@Singleton
public class WSO2ClientServiceImpl implements WSO2ClientService {

    private static final String TEMPLATE_BASE_URL         = "/wso2/" + Config.getWorkspaceId();
    private static final String DETECT_CONFIGURATION_FILE = TEMPLATE_BASE_URL + "/detect";
    private static final String UPLOAD_CONFIGURATION_FILE = TEMPLATE_BASE_URL + "/upload";
    private static final String MODIFY_CONFIGURATION_FILE = TEMPLATE_BASE_URL + "/file";
    private static final String GET_WSO2_SERVICE_INFO     = TEMPLATE_BASE_URL + "/info";

    private final String              restContext;
    private final Loader              loader;
    private final AsyncRequestFactory asyncRequestFactory;

    @Inject
    public WSO2ClientServiceImpl(@Named("restContext") String restContext,
                                 Loader loader,
                                 AsyncRequestFactory asyncRequestFactory) {
        this.restContext = restContext;
        this.loader = loader;
        this.asyncRequestFactory = asyncRequestFactory;
    }

    /** {@inheritDoc} */
    @Override
    public void detectConfigurationFile(@Nonnull FileInfo fileInfo, @Nonnull AsyncRequestCallback<String> callback)
            throws RequestException {
        String requestUrl = restContext + DETECT_CONFIGURATION_FILE;

        loader.setMessage("Importing file...");

        asyncRequestFactory.createPostRequest(requestUrl, fileInfo, true).loader(loader).header(CONTENT_TYPE, MimeType.APPLICATION_JSON)
                           .send(callback);
    }

    /** {@inheritDoc} */
    @Override
    public void uploadFile(@Nonnull FileInfo fileInfo, @Nonnull AsyncRequestCallback<String> callback) throws RequestException {
        String requestUrl = restContext + UPLOAD_CONFIGURATION_FILE;

        loader.setMessage("Importing file...");

        asyncRequestFactory.createPostRequest(requestUrl, fileInfo, true).loader(loader).header(CONTENT_TYPE, MimeType.APPLICATION_JSON)
                           .send(callback);
    }

    /** {@inheritDoc} */
    @Override
    public void modifyFile(@Nonnull FileInfo fileInfo, @Nonnull String operation, @Nonnull AsyncRequestCallback<String> callback)
            throws RequestException {
        String requestUrl = restContext + MODIFY_CONFIGURATION_FILE + "/" + operation;

        asyncRequestFactory.createPostRequest(requestUrl, fileInfo, true).header(CONTENT_TYPE, MimeType.APPLICATION_JSON)
                           .send(callback);
    }

    /** {@inheritDoc} */
    @Override
    public void getWSO2ServiceInfo(@Nonnull AsyncRequestCallback<String> callback) throws RequestException {
        String url = restContext + GET_WSO2_SERVICE_INFO;

        asyncRequestFactory.createGetRequest(url).header(HTTPHeader.ACCEPT, MimeType.APPLICATION_JSON)
                           .send(callback);
    }
}