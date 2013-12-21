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
import com.codenvy.ide.ext.wso2.shared.ESBProjectInfo;
import com.codenvy.ide.ext.wso2.shared.FileInfo;
import com.codenvy.ide.rest.AsyncRequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.inject.ImplementedBy;

/**
 * The service for working with 'WSO2' plugin's rest services.
 *
 * @author Andrey Plotnikov
 */
@ImplementedBy(WSO2ClientServiceImpl.class)
public interface WSO2ClientService {

    /**
     * Create 'ESB Configuration Project' with given name.
     *
     * @param projectInfo
     *         information about ESB Configuration Project
     * @param callback
     *         callback that need to execute when the answer is come
     * @throws RequestException
     */
    void createESBConfProject(@NotNull ESBProjectInfo projectInfo, @NotNull AsyncRequestCallback<Void> callback) throws RequestException;

    /**
     * Detect configuration file with given name.
     *
     * @param fileInfo
     *         information about configuration file
     * @param callback
     *         callback that need to execute when the answer is come
     * @throws RequestException
     */
    void detectConfigurationFile(@NotNull FileInfo fileInfo, @NotNull AsyncRequestCallback<String> callback) throws RequestException;

    /**
     * Upload a configuration file from url.
     *
     * @param fileInfo
     *         information about configuration file
     * @param callback
     *         callback that need to execute when the answer is come
     * @throws RequestException
     */
    void uploadFile(@NotNull FileInfo fileInfo, @NotNull AsyncRequestCallback<Void> callback) throws RequestException;
}