/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 *  [2012] - [2014] Codenvy, S.A. 
 *  All Rights Reserved.
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
package com.codenvy.ide.ext.wso2.server.projecttypes;

import com.codenvy.api.project.server.ProjectTypeDescriptionRegistry;
import com.codenvy.api.project.server.VfsPropertyValueProvider;
import com.codenvy.api.project.shared.Attribute;
import com.codenvy.api.project.shared.ProjectType;
import com.codenvy.api.project.shared.ProjectTypeExtension;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_CONFIGURATION_PROJECT_ID;
import static com.codenvy.ide.ext.wso2.shared.Constants.WSO2_PROJECT_ID;

/**
 * {@link ProjectTypeExtension} to register WSO2 project type.
 *
 * @author Valeriy Svydenko
 */
@Singleton
public class WSO2ProjectTypeExtension implements ProjectTypeExtension {
    @Inject
    public WSO2ProjectTypeExtension(ProjectTypeDescriptionRegistry registry) {
        registry.registerProjectType(this);
    }

    /** {@inheritDoc} */
    @Override
    public ProjectType getProjectType() {
        return new ProjectType(ESB_CONFIGURATION_PROJECT_ID, "WSO2 Integration Flow Project");
    }

    /** {@inheritDoc} */
    @Override
    public List<Attribute> getPredefinedAttributes() {
        final List<Attribute> list = new ArrayList<>(1);
        // TODO: provide a single value for predefined attributes. Avoid using ValueProviders
        // VfsPropertyValueProvider is used as temporary solution because we don't have any way to get attribute's value on the client side
        list.add(new Attribute("language", new VfsPropertyValueProvider("language", WSO2_PROJECT_ID)));
        return list;
    }
}
