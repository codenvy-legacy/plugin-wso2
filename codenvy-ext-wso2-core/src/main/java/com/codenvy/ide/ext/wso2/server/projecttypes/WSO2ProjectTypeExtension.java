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

import com.codenvy.api.project.server.ProjectTypeDescriptionRegistry;
import com.codenvy.api.project.server.ProjectTypeExtension;
import com.codenvy.api.project.shared.Attribute;
import com.codenvy.api.project.shared.ProjectTemplateDescription;
import com.codenvy.api.project.shared.ProjectType;
import com.codenvy.ide.Constants;
import com.google.inject.Singleton;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_CONFIGURATION_PROJECT_ID;
import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_CONFIGURATION_PROJECT_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.WSO2_PROJECT_ID;

/**
 * @author Valeriy Svydenko
 */
@Singleton
public class WSO2ProjectTypeExtension implements ProjectTypeExtension {

    @Inject
    public WSO2ProjectTypeExtension(ProjectTypeDescriptionRegistry registry) {
        registry.registerProjectType(this);
    }

    @Override
    public ProjectType getProjectType() {
        return new ProjectType(ESB_CONFIGURATION_PROJECT_ID, WSO2_PROJECT_ID, WSO2_PROJECT_ID);
    }

    @Override
    public List<Attribute> getPredefinedAttributes() {
        return Arrays.asList(new Attribute(Constants.LANGUAGE, WSO2_PROJECT_ID),
                             new Attribute(Constants.FRAMEWORK, WSO2_PROJECT_ID));
    }

    @Override
    public List<ProjectTemplateDescription> getTemplates() {
        return Arrays.asList(new ProjectTemplateDescription("zip",
                                                            ESB_CONFIGURATION_PROJECT_NAME,
                                                            "This is a simple ESB configuration project.",
                                                            "templates/esbproject.zip"));
    }

}