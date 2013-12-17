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

import com.codenvy.ide.annotations.NotNull;
import com.codenvy.ide.dto.DtoFactory;
import com.codenvy.ide.ext.wso2.shared.ESBProjectInfo;
import com.codenvy.ide.rest.AsyncRequest;
import com.codenvy.ide.rest.AsyncRequestCallback;
import com.codenvy.ide.ui.loader.Loader;
import com.codenvy.ide.util.Utils;
import com.google.gwt.http.client.RequestException;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import static com.codenvy.ide.rest.HTTPHeader.CONTENT_TYPE;
import static com.google.gwt.http.client.RequestBuilder.POST;

/**
 * The implementation of {@link WSO2ClientService}.
 *
 * @author Andrey Plotnikov
 */
public class WSO2ClientServiceImpl implements WSO2ClientService {
    private static final String BASE_URL                = '/' + Utils.getWorkspaceName() + "/templates";
    private static final String CREATE_ESB_CONF_PROJECT = BASE_URL + "/esbconf";

    private String     restContext;
    private Loader     loader;
    private DtoFactory dtoFactory;

    @Inject
    public WSO2ClientServiceImpl(@Named("restContext") String restContext, Loader loader, DtoFactory dtoFactory) {
        this.restContext = restContext;
        this.loader = loader;
        this.dtoFactory = dtoFactory;
    }

    /** {@inheritDoc} */
    @Override
    public void createESBConfProject(@NotNull ESBProjectInfo projectInfo, @NotNull AsyncRequestCallback<Void> callback)
            throws RequestException {
        String requestUrl = restContext + CREATE_ESB_CONF_PROJECT;

        loader.setMessage("Creating new project...");

        AsyncRequest.build(POST, requestUrl).data(dtoFactory.toJson(projectInfo)).header(CONTENT_TYPE, "application/json").loader(loader)
                    .send(callback);
    }
}