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
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
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
 * The Class describes CreateIssue connector for jira group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class CreateIssue extends AbstractConnector {

    public static final String ELEMENT_NAME       = "CreateIssue";
    public static final String SERIALIZATION_NAME = "jira.createIssue";

    private static final String PROJECT_KEY = "projectKey";
    private static final String SUMMARY     = "summary";
    private static final String DESCRIPTION = "description";
    private static final String ISSUE_TYPE  = "issueType";

    private static final List<String> PROPERTIES = Arrays.asList(PROJECT_KEY, SUMMARY, DESCRIPTION, ISSUE_TYPE);

    private String projectKey;
    private String summary;
    private String description;
    private String issueType;

    private String projectKeyExpression;
    private String summaryExpression;
    private String descriptionExpression;
    private String issueTypeExpression;

    private Array<NameSpace> projectKeyNS;
    private Array<NameSpace> summaryNS;
    private Array<NameSpace> descriptionNS;
    private Array<NameSpace> issueTypeNS;

    @Inject
    public CreateIssue(EditorResources resources, Provider<Branch> branchProvider, MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.jiraIcon(),
              branchProvider,
              mediatorCreatorsManager);

        projectKey = "";
        summary = "";
        description = "";
        issueType = "";

        projectKeyExpression = "";
        summaryExpression = "";
        issueTypeExpression = "";
        descriptionExpression = "";

        issueTypeNS = createArray();
        projectKeyNS = createArray();
        descriptionNS = createArray();
        summaryNS = createArray();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(PROJECT_KEY, isInline ? projectKey : projectKeyExpression);
        properties.put(SUMMARY, isInline ? summary : summaryExpression);
        properties.put(DESCRIPTION, isInline ? description : descriptionExpression);
        properties.put(ISSUE_TYPE, isInline ? issueType : issueTypeExpression);

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

            case SUMMARY:
                if (isInline) {
                    summary = nodeValue;
                } else {
                    summaryExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case DESCRIPTION:
                if (isInline) {
                    description = nodeValue;
                } else {
                    descriptionExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case ISSUE_TYPE:
                if (isInline) {
                    issueType = nodeValue;
                } else {
                    issueTypeExpression = nodeValue;

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
    public String getSummary() {
        return summary;
    }

    public void setSummary(@Nonnull String summary) {
        this.summary = summary;
    }

    @Nonnull
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nonnull String description) {
        this.description = description;
    }

    @Nonnull
    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(@Nonnull String issueType) {
        this.issueType = issueType;
    }

    @Nonnull
    public String getProjectKeyExpression() {
        return projectKeyExpression;
    }

    public void setProjectKeyExpression(@Nonnull String projectKeyExpression) {
        this.projectKeyExpression = projectKeyExpression;
    }

    @Nonnull
    public String getSummaryExpression() {
        return summaryExpression;
    }

    public void setSummaryExpression(@Nonnull String summaryExpression) {
        this.summaryExpression = summaryExpression;
    }

    @Nonnull
    public String getDescriptionExpression() {
        return descriptionExpression;
    }

    public void setDescriptionExpression(@Nonnull String descriptionExpression) {
        this.descriptionExpression = descriptionExpression;
    }

    @Nonnull
    public String getIssueTypeExpression() {
        return issueTypeExpression;
    }

    public void setIssueTypeExpression(@Nonnull String issueTypeExpression) {
        this.issueTypeExpression = issueTypeExpression;
    }

    @Nonnull
    public Array<NameSpace> getProjectKeyNS() {
        return projectKeyNS;
    }

    public void setProjectKeyNS(@Nonnull Array<NameSpace> projectKeyNS) {
        this.projectKeyNS = projectKeyNS;
    }

    @Nonnull
    public Array<NameSpace> getSummaryNS() {
        return summaryNS;
    }

    public void setSummaryNS(@Nonnull Array<NameSpace> summaryNS) {
        this.summaryNS = summaryNS;
    }

    @Nonnull
    public Array<NameSpace> getDescriptionNS() {
        return descriptionNS;
    }

    public void setDescriptionNS(@Nonnull Array<NameSpace> descriptionNS) {
        this.descriptionNS = descriptionNS;
    }

    @Nonnull
    public Array<NameSpace> getIssueTypeNS() {
        return issueTypeNS;
    }

    public void setIssueTypeNS(@Nonnull Array<NameSpace> issueTypeNS) {
        this.issueTypeNS = issueTypeNS;
    }

}