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
import com.codenvy.ide.collections.Array;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.Inline;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NamespacedPropertyEditor;
import static com.codenvy.ide.collections.Collections.createArray;

/**
 * The Class describes GetUserPermissions connector for jira group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class GetUserPermissions extends AbstractConnector {

    public static final String ELEMENT_NAME       = "GetUserPermissions";
    public static final String SERIALIZATION_NAME = "jira.getUserPermissions";

    private static final String PROJECT_KEY = "projectKey";
    private static final String PROJECT_ID  = "projectId";
    private static final String ISSUE_KEY   = "issueKey";
    private static final String ISSUE_ID    = "issueId";

    private static final List<String> PROPERTIES = Arrays.asList(PROJECT_KEY, PROJECT_ID, ISSUE_KEY, ISSUE_ID);

    private String projectKey;
    private String projectId;
    private String issueKey;
    private String issueId;

    private String projectKeyExpression;
    private String projectIdExpression;
    private String issueKeyExpression;
    private String issueIdExpression;

    private Array<NameSpace> projectKeyNS;
    private Array<NameSpace> projectIdNS;
    private Array<NameSpace> issueKeyNS;
    private Array<NameSpace> issueIdNS;

    @Inject
    public GetUserPermissions(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.jiraIcon(),
              branchProvider,
              elementCreatorsManager);

        projectId = "";
        projectKey = "";
        issueKey = "";
        issueId = "";

        projectIdExpression = "";
        projectKeyExpression = "";
        issueIdExpression = "";
        issueKeyExpression = "";

        issueIdNS = createArray();
        projectIdNS = createArray();
        issueKeyNS = createArray();
        projectKeyNS = createArray();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(PROJECT_KEY, isInline ? projectKey : projectKeyExpression);
        properties.put(PROJECT_ID, isInline ? projectId : projectIdExpression);
        properties.put(ISSUE_KEY, isInline ? issueKey : issueKeyExpression);
        properties.put(ISSUE_ID, isInline ? issueId : issueIdExpression);

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
                    projectId = nodeValue;
                } else {
                    projectIdExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case PROJECT_ID:
                if (isInline) {
                    projectKey = nodeValue;
                } else {
                    projectKeyExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case ISSUE_KEY:
                if (isInline) {
                    issueKey = nodeValue;
                } else {
                    issueKeyExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case ISSUE_ID:
                if (isInline) {
                    issueId = nodeValue;
                } else {
                    issueIdExpression = nodeValue;

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
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(@Nonnull String projectId) {
        this.projectId = projectId;
    }

    @Nonnull
    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(@Nonnull String issueKey) {
        this.issueKey = issueKey;
    }

    @Nonnull
    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(@Nonnull String issueId) {
        this.issueId = issueId;
    }

    @Nonnull
    public String getProjectKeyExpression() {
        return projectKeyExpression;
    }

    public void setProjectKeyExpression(@Nonnull String projectKeyExpression) {
        this.projectKeyExpression = projectKeyExpression;
    }

    @Nonnull
    public String getProjectIdExpression() {
        return projectIdExpression;
    }

    public void setProjectIdExpression(@Nonnull String projectIdExpression) {
        this.projectIdExpression = projectIdExpression;
    }

    @Nonnull
    public String getIssueKeyExpression() {
        return issueKeyExpression;
    }

    public void setIssueKeyExpression(@Nonnull String issueKeyExpression) {
        this.issueKeyExpression = issueKeyExpression;
    }

    @Nonnull
    public String getIssueIdExpression() {
        return issueIdExpression;
    }

    public void setIssueIdExpression(@Nonnull String issueIdExpression) {
        this.issueIdExpression = issueIdExpression;
    }

    @Nonnull
    public Array<NameSpace> getProjectKeyNS() {
        return projectKeyNS;
    }

    public void setProjectKeyNS(@Nonnull Array<NameSpace> projectKeyNS) {
        this.projectKeyNS = projectKeyNS;
    }

    @Nonnull
    public Array<NameSpace> getProjectIdNS() {
        return projectIdNS;
    }

    public void setProjectIdNS(@Nonnull Array<NameSpace> projectIdNS) {
        this.projectIdNS = projectIdNS;
    }

    @Nonnull
    public Array<NameSpace> getIssueKeyNS() {
        return issueKeyNS;
    }

    public void setIssueKeyNS(@Nonnull Array<NameSpace> issueKeyNS) {
        this.issueKeyNS = issueKeyNS;
    }

    @Nonnull
    public Array<NameSpace> getIssueIdNS() {
        return issueIdNS;
    }

    public void setIssueIdNS(@Nonnull Array<NameSpace> issueIdNS) {
        this.issueIdNS = issueIdNS;
    }

}
