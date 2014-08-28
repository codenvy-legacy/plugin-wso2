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

import com.codenvy.ide.ext.wso2.shared.FileInfo;
import com.codenvy.ide.rest.AsyncRequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;

/**
 * The service for working with 'WSO2' plugin's rest services.
 *
 * @author Andrey Plotnikov
 */
@ImplementedBy(WSO2ClientServiceImpl.class)
public interface WSO2ClientService {

    /**
     * Detect configuration file with given name.
     *
     * @param fileInfo
     *         information about configuration file
     * @param callback
     *         callback that need to execute when the answer is come
     * @throws RequestException
     */
    void detectConfigurationFile(@Nonnull FileInfo fileInfo, @Nonnull AsyncRequestCallback<String> callback) throws RequestException;

    /**
     * Upload a configuration file from url.
     *
     * @param fileInfo
     *         information about configuration file
     * @param callback
     *         callback that need to execute when the answer is come
     * @throws RequestException
     */
    void uploadFile(@Nonnull FileInfo fileInfo, @Nonnull AsyncRequestCallback<String> callback) throws RequestException;

    /**
     * Modify configuration file with given name.
     *
     * @param fileInfo
     *         information about configuration file
     * @param callback
     *         callback that need to execute when the answer is come
     * @throws RequestException
     */
    void modifyFile(@Nonnull FileInfo fileInfo, @Nonnull String operation, @Nonnull AsyncRequestCallback<String> callback)
            throws RequestException;
}