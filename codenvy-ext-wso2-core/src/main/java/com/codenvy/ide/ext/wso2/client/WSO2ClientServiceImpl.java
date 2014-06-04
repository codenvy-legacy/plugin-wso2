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
import com.google.inject.name.Named;

import javax.validation.constraints.NotNull;

import static com.codenvy.ide.rest.HTTPHeader.CONTENT_TYPE;

/**
 * The implementation of {@link WSO2ClientService}.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
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
    public void detectConfigurationFile(@NotNull FileInfo fileInfo, @NotNull AsyncRequestCallback<String> callback)
            throws RequestException {
        String requestUrl = restContext + DETECT_CONFIGURATION_FILE;

        loader.setMessage("Importing file...");

        asyncRequestFactory.createPostRequest(requestUrl, fileInfo, true).loader(loader).header(CONTENT_TYPE, MimeType.APPLICATION_JSON)
                           .send(callback);
    }

    /** {@inheritDoc} */
    @Override
    public void uploadFile(@NotNull FileInfo fileInfo, @NotNull AsyncRequestCallback<String> callback) throws RequestException {
        String requestUrl = restContext + UPLOAD_CONFIGURATION_FILE;

        loader.setMessage("Importing file...");

        asyncRequestFactory.createPostRequest(requestUrl, fileInfo, true).loader(loader).header(CONTENT_TYPE, MimeType.APPLICATION_JSON)
                           .send(callback);
    }

    /** {@inheritDoc} */
    @Override
    public void modifyFile(@NotNull FileInfo fileInfo, @NotNull String operation, @NotNull AsyncRequestCallback<String> callback)
            throws RequestException {
        String requestUrl = restContext + MODIFY_CONFIGURATION_FILE + "/" + operation;

        asyncRequestFactory.createPostRequest(requestUrl, fileInfo, true).header(CONTENT_TYPE, MimeType.APPLICATION_JSON)
                           .send(callback);
    }

    /** {@inheritDoc} */
    @Override
    public void getWSO2ServiceInfo(@NotNull AsyncRequestCallback<String> callback) throws RequestException {
        String url = restContext + GET_WSO2_SERVICE_INFO;

        asyncRequestFactory.createGetRequest(url).header(HTTPHeader.ACCEPT, MimeType.APPLICATION_JSON)
                           .send(callback);
    }
}