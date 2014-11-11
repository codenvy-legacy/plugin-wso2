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
import com.codenvy.api.project.server.ProjectTemplateDescription;
import com.codenvy.api.project.server.ProjectType;
import com.codenvy.api.project.server.ProjectTypeDescriptionRegistry;
import com.googlecode.gwt.test.Mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.codenvy.ide.Constants.FRAMEWORK;
import static com.codenvy.ide.Constants.LANGUAGE;
import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_CONFIGURATION_PROJECT_ID;
import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_CONFIGURATION_PROJECT_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.IMPORTER_TYPE;
import static com.codenvy.ide.ext.wso2.shared.Constants.PROJECT_DESCRIPTION;
import static com.codenvy.ide.ext.wso2.shared.Constants.PROJECT_LOCATION;
import static com.codenvy.ide.ext.wso2.shared.Constants.WSO2_PROJECT_CATEGORY;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public class WSO2ProjectTypeExtensionTest {

    @Mock
    private ProjectTypeDescriptionRegistry registry;
    @InjectMocks
    private WSO2ProjectTypeExtension       extension;

    @Test
    public void constructorShouldBeDone() throws Exception {
        verify(registry).registerProjectType(extension);
    }

    @Test
    public void projectTypeShouldBeReturned() throws Exception {
        ProjectType projectType = extension.getProjectType();

        assertThat(projectType.getId(), equalTo(ESB_CONFIGURATION_PROJECT_ID));
        assertThat(projectType.getName(), equalTo(ESB_CONFIGURATION_PROJECT_NAME));
        assertThat(projectType.getCategory(), equalTo(WSO2_PROJECT_CATEGORY));
    }

    @Test
    public void predefinedAttributesShouldBeReturned() throws Exception {
        List<Attribute> attributes = extension.getPredefinedAttributes();

        Attribute language = attributes.get(0);
        Attribute framework = attributes.get(1);

        assertThat(attributes.size(), is(2));

        assertThat(language.getName(), equalTo(LANGUAGE));
        assertThat(language.getValue(), equalTo(ESB_CONFIGURATION_PROJECT_ID));

        assertThat(framework.getName(), equalTo(FRAMEWORK));
        assertThat(framework.getValue(), equalTo(ESB_CONFIGURATION_PROJECT_ID));
    }

    @Test
    public void runnersShouldBeReturned() throws Exception {
        assertThat(extension.getRunners(), is(nullValue()));
    }

    @Test
    public void buildersShouldBeReturned() throws Exception {
        assertThat(extension.getBuilders(), is(nullValue()));
    }

    @Test
    public void templatesShouldBeReturned() throws Exception {
        List<ProjectTemplateDescription> templateDescriptions = extension.getTemplates();

        ProjectTemplateDescription templateDescription = templateDescriptions.get(0);

        assertThat(templateDescriptions.size(), is(1));

        assertThat(templateDescription.getImporterType(), equalTo(IMPORTER_TYPE));
        assertThat(templateDescription.getDisplayName(), equalTo(ESB_CONFIGURATION_PROJECT_NAME));
        assertThat(templateDescription.getDescription(), equalTo(PROJECT_DESCRIPTION));
        assertThat(templateDescription.getLocation(), equalTo(PROJECT_LOCATION));
    }

    @Test
    public void iconRegistryMapShouldBeReturned() throws Exception {
        assertThat(extension.getIconRegistry(), is(nullValue()));
    }

}