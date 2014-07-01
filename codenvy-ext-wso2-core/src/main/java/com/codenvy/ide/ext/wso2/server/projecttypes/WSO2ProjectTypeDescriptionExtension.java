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
package com.codenvy.ide.ext.wso2.server.projecttypes;


import com.codenvy.api.project.server.ProjectTypeDescriptionExtension;
import com.codenvy.api.project.server.ProjectTypeDescriptionRegistry;
import com.codenvy.api.project.shared.AttributeDescription;
import com.codenvy.api.project.shared.ProjectType;
import com.google.inject.Singleton;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.Constants.FRAMEWORK;
import static com.codenvy.ide.Constants.LANGUAGE;
import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_CONFIGURATION_PROJECT_ID;
import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_CONFIGURATION_PROJECT_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.WSO2_PROJECT_ID;

/**
 * Register WSO2 extension {@link com.codenvy.api.project.server.ProjectTypeDescriptionExtension} to register project types.
 *
 * @author Valeriy Svydenko
 */
@Singleton
public class WSO2ProjectTypeDescriptionExtension implements ProjectTypeDescriptionExtension {
    @Inject
    public WSO2ProjectTypeDescriptionExtension(ProjectTypeDescriptionRegistry registry) {
        registry.registerDescription(this);
    }

    @Override
    public List<ProjectType> getProjectTypes() {
        return Arrays.asList(new ProjectType(ESB_CONFIGURATION_PROJECT_ID, ESB_CONFIGURATION_PROJECT_NAME, WSO2_PROJECT_ID));
    }

    @Override
    public List<AttributeDescription> getAttributeDescriptions() {
        return Arrays.asList(new AttributeDescription(LANGUAGE),
                             new AttributeDescription(FRAMEWORK));
    }
}