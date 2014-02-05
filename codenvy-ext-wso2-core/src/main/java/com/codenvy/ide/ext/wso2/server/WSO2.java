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
package com.codenvy.ide.ext.wso2.server;

import com.codenvy.api.vfs.server.exceptions.NotSupportedException;
import com.codenvy.ide.ext.git.server.provider.GitVendorService;
import com.codenvy.ide.ext.git.server.provider.rest.ProviderException;
import com.codenvy.ide.ext.ssh.server.SshKey;
import com.codenvy.ide.ext.ssh.server.SshKeyStoreException;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Contains methods for retrieving data from WSO2 git server and processing it before sending to client side.
 *
 * @author Andrey Plotnikov
 */
public class WSO2 extends GitVendorService {

    @Inject
    protected WSO2(@Named("wso2.vendorBaseHost") String vendorBaseHost, @Named("wso2.vendorUrlPattern") String vendorUrlPattern) {
        super("wso2", vendorBaseHost, vendorUrlPattern, new String[0], true);
    }

    /** {@inheritDoc} */
    @Override
    public void uploadNewPublicKey(SshKey publicKey) throws ProviderException {
        throw new NotSupportedException("This operation is not supported for this service.");
    }

    /** {@inheritDoc} */
    @Override
    public void generateAndUploadNewPublicKey() throws SshKeyStoreException, ProviderException {
        throw new NotSupportedException("This operation is not supported for this service.");
    }

    /** {@inheritDoc} */
    @Override
    public boolean isRepositoryPrivate(String repositoryName) {
        return false;
    }
}