/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache  License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.ext.wso2.server.projecttypes;

import com.codenvy.api.project.server.Attribute;
import com.codenvy.api.project.server.Builders;
import com.codenvy.api.project.server.ProjectTemplateDescription;
import com.codenvy.api.project.server.ProjectType;
import com.codenvy.api.project.server.ProjectTypeDescriptionRegistry;
import com.codenvy.api.project.server.ProjectTypeExtension;
import com.codenvy.api.project.server.Runners;
import com.codenvy.ide.Constants;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_CONFIGURATION_PROJECT_ID;
import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_CONFIGURATION_PROJECT_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.IMPORTER_TYPE;
import static com.codenvy.ide.ext.wso2.shared.Constants.PROJECT_DESCRIPTION;
import static com.codenvy.ide.ext.wso2.shared.Constants.PROJECT_LOCATION;
import static com.codenvy.ide.ext.wso2.shared.Constants.WSO2_PROJECT_CATEGORY;

/**
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 * @author Andrey Plotnikov
 */
@Singleton
public class WSO2ProjectTypeExtension implements ProjectTypeExtension {

    @Inject
    public WSO2ProjectTypeExtension(ProjectTypeDescriptionRegistry registry) {
        registry.registerProjectType(this);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public ProjectType getProjectType() {
        return new ProjectType(ESB_CONFIGURATION_PROJECT_ID, ESB_CONFIGURATION_PROJECT_NAME, WSO2_PROJECT_CATEGORY);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public List<Attribute> getPredefinedAttributes() {
        return Arrays.asList(new Attribute(Constants.LANGUAGE, ESB_CONFIGURATION_PROJECT_ID),
                             new Attribute(Constants.FRAMEWORK, ESB_CONFIGURATION_PROJECT_ID));
    }

    /** {@inheritDoc} */
    @Override
    @Nullable
    public Builders getBuilders() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    @Nullable
    public Runners getRunners() {
        return null;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public List<ProjectTemplateDescription> getTemplates() {
        return Arrays.asList(new ProjectTemplateDescription(IMPORTER_TYPE,
                                                            ESB_CONFIGURATION_PROJECT_NAME,
                                                            PROJECT_DESCRIPTION,
                                                            PROJECT_LOCATION));
    }

    /** {@inheritDoc} */
    @Override
    @Nullable
    public Map<String, String> getIconRegistry() {
        return null;
    }

}