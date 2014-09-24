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
package com.codenvy.ide.client.elements.connectors.jira;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.connectors.AbstractConnector;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.Inline;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NamespacedPropertyEditor;

/**
 * The Class describes SetActorsToRoleOfProject connector for jira group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class SetActorsToRoleOfProject extends AbstractConnector {

    public static final String ELEMENT_NAME       = "SetActorsToRoleOfProject";
    public static final String SERIALIZATION_NAME = "jira.setActorsToRoleOfProject";

    private static final String PROJECT_KEY = "projectKey";
    private static final String ROLE_ID     = "roleId";

    private static final List<String> PROPERTIES = Arrays.asList(PROJECT_KEY, ROLE_ID);

    private String projectKey;
    private String roleId;

    private String projectKeyExpression;
    private String roleIdExpression;

    private List<NameSpace> projectKeyNS;
    private List<NameSpace> roleIdNS;

    @Inject
    public SetActorsToRoleOfProject(EditorResources resources,
                                    Provider<Branch> branchProvider,
                                    ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.jiraIcon(),
              branchProvider,
              elementCreatorsManager);

        projectKey = "";
        roleId = "";

        projectKeyExpression = "";
        roleIdExpression = "";

        projectKeyNS = new ArrayList<>();
        roleIdNS = new ArrayList<>();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(PROJECT_KEY, isInline ? projectKey : projectKeyExpression);
        properties.put(ROLE_ID, isInline ? roleId : roleIdExpression);

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case PROJECT_KEY:
                if (isInline) {
                    projectKey = nodeValue;
                } else {
                    projectKeyExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case ROLE_ID:
                if (isInline) {
                    roleId = nodeValue;
                } else {
                    roleIdExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;
        }
    }

    @Nonnull
    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(@Nonnull String projectKey) {
        this.projectKey = projectKey;
    }

    @Nonnull
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(@Nonnull String roleId) {
        this.roleId = roleId;
    }

    @Nonnull
    public String getProjectKeyExpression() {
        return projectKeyExpression;
    }

    public void setProjectKeyExpression(@Nonnull String projectKeyExpression) {
        this.projectKeyExpression = projectKeyExpression;
    }

    @Nonnull
    public String getRoleIdExpression() {
        return roleIdExpression;
    }

    public void setRoleIdExpression(@Nonnull String roleIdExpression) {
        this.roleIdExpression = roleIdExpression;
    }

    @Nonnull
    public List<NameSpace> getProjectKeyNS() {
        return projectKeyNS;
    }

    public void setProjectKeyNS(@Nonnull List<NameSpace> projectKeyNS) {
        this.projectKeyNS = projectKeyNS;
    }

    @Nonnull
    public List<NameSpace> getRoleIdNS() {
        return roleIdNS;
    }

    public void setRoleIdNS(@Nonnull List<NameSpace> roleIdNS) {
        this.roleIdNS = roleIdNS;
    }
}
