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
package com.codenvy.ide.ext.wso2.server;

import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.Set;

/**
 * JAX-RS application for 'Integration Flow WSO2' plugin.
 *
 * @author Andrey Plotnikov
 */
public class WSO2Application extends Application {

    /** {@inheritDoc} */
    @Override
    public Set<Class<?>> getClasses() {
        return Collections.<Class<?>>singleton(WSO2ProjectCreationService.class);
    }
}