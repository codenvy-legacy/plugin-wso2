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

import com.codenvy.api.project.server.AttributeDescription;
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
import static com.codenvy.ide.ext.wso2.shared.Constants.WSO2_PROJECT_CATEGORY;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public class WSO2ProjectTypeDescriptionExtensionTest {

    @Mock
    private ProjectTypeDescriptionRegistry      registry;
    @InjectMocks
    private WSO2ProjectTypeDescriptionExtension descriptionExtension;

    @Test
    public void constructorShouldBeDone() throws Exception {
        verify(registry).registerDescription(descriptionExtension);
    }

    @Test
    public void projectTypesShouldBeReturned() throws Exception {
        List<ProjectType> projectTypes = descriptionExtension.getProjectTypes();

        ProjectType projectType = projectTypes.get(0);

        assertThat(projectTypes.size(), is(1));

        assertThat(projectType.getId(), equalTo(ESB_CONFIGURATION_PROJECT_ID));
        assertThat(projectType.getName(), equalTo(ESB_CONFIGURATION_PROJECT_NAME));
        assertThat(projectType.getCategory(), equalTo(WSO2_PROJECT_CATEGORY));
    }

    @Test
    public void attributeDescriptionShouldBeReturned() throws Exception {
        List<AttributeDescription> attributeDescriptions = descriptionExtension.getAttributeDescriptions();

        AttributeDescription language = attributeDescriptions.get(0);
        AttributeDescription framework = attributeDescriptions.get(1);

        assertThat(attributeDescriptions.size(), is(2));

        assertThat(language.getName(), equalTo(LANGUAGE));
        assertThat(framework.getName(), equalTo(FRAMEWORK));
    }
}