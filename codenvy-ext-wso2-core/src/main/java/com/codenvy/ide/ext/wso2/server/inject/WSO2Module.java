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
package com.codenvy.ide.ext.wso2.server.inject;

import com.codenvy.ide.ext.git.server.provider.GitVendorService;
import com.codenvy.ide.ext.wso2.server.WSO2;
import com.codenvy.ide.ext.wso2.server.projecttypes.WSO2ProjectTypeDescriptionExtension;
import com.codenvy.ide.ext.wso2.server.projecttypes.WSO2ProjectTypeExtension;
import com.codenvy.ide.ext.wso2.server.rest.WSO2RestService;
import com.codenvy.inject.DynaModule;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

/**
 * The module that contains configuration of the server side part of the plugin.
 *
 * @author Andrey Plotnikov
 */
@DynaModule
public class WSO2Module extends AbstractModule {

    /** {@inheritDoc} */
    @Override
    protected void configure() {
        bind(WSO2ProjectTypeDescriptionExtension.class);
        bind(WSO2ProjectTypeExtension.class);

        bind(WSO2RestService.class);

        Multibinder<GitVendorService> gitVendorServices = Multibinder.newSetBinder(binder(), GitVendorService.class);
        gitVendorServices.addBinding().to(WSO2.class);
    }
}