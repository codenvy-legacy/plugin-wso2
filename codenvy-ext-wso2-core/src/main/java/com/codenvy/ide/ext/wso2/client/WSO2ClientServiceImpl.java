/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache  License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.ext.wso2.client;

import com.codenvy.ide.ext.wso2.shared.FileInfo;
import com.codenvy.ide.rest.AsyncRequestCallback;
import com.codenvy.ide.rest.AsyncRequestFactory;
import com.codenvy.ide.ui.loader.IdeLoader;
import com.codenvy.ide.util.Config;
import com.google.gwt.http.client.RequestException;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import javax.annotation.Nonnull;

import static com.codenvy.ide.MimeType.APPLICATION_JSON;
import static com.codenvy.ide.rest.HTTPHeader.CONTENT_TYPE;

/**
 * Class contains methods which provide ability to upload or modify project's files.
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

    private final String               restContext;
    private final Provider<IdeLoader>  ideLoaderProvider;
    private final AsyncRequestFactory  asyncRequestFactory;
    private final LocalizationConstant localizationConstant;

    @Inject
    public WSO2ClientServiceImpl(@Named("restContext") String restContext,
                                 Provider<IdeLoader> ideLoaderProvider,
                                 AsyncRequestFactory asyncRequestFactory,
                                 LocalizationConstant localizationConstant) {
        this.restContext = restContext;
        this.ideLoaderProvider = ideLoaderProvider;
        this.asyncRequestFactory = asyncRequestFactory;
        this.localizationConstant = localizationConstant;
    }

    /** {@inheritDoc} */
    @Override
    public void detectConfigurationFile(@Nonnull FileInfo fileInfo, @Nonnull AsyncRequestCallback<Void> callback)
            throws RequestException {
        String requestUrl = restContext + DETECT_CONFIGURATION_FILE;

        asyncRequestFactory.createPostRequest(requestUrl, fileInfo)
                           .loader(ideLoaderProvider.get(), localizationConstant.importingFileMessage())
                           .header(CONTENT_TYPE, APPLICATION_JSON)
                           .send(callback);
    }

    /** {@inheritDoc} */
    @Override
    public void uploadFile(@Nonnull FileInfo fileInfo, @Nonnull AsyncRequestCallback<Void> callback) throws RequestException {
        String requestUrl = restContext + UPLOAD_CONFIGURATION_FILE;

        asyncRequestFactory.createPostRequest(requestUrl, fileInfo)
                           .loader(ideLoaderProvider.get(), localizationConstant.importingFileMessage())
                           .header(CONTENT_TYPE, APPLICATION_JSON)
                           .send(callback);
    }

    /** {@inheritDoc} */
    @Override
    public void modifyFile(@Nonnull FileInfo fileInfo, @Nonnull String operation, @Nonnull AsyncRequestCallback<Void> callback)
            throws RequestException {
        String requestUrl = restContext + MODIFY_CONFIGURATION_FILE + "/" + operation;

        asyncRequestFactory.createPostRequest(requestUrl, fileInfo)
                           .loader(ideLoaderProvider.get(), localizationConstant.modifyingFileMessage())
                           .header(CONTENT_TYPE, APPLICATION_JSON)
                           .send(callback);
    }

}