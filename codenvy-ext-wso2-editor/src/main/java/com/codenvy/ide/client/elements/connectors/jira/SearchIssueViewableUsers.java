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
 * The Class describes SearchAssignableUser connector for jira group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class SearchIssueViewableUsers extends AbstractConnector {

    public static final String ELEMENT_NAME       = "SearchAssignableUser";
    public static final String SERIALIZATION_NAME = "jira.searchAssignableUser";

    private static final String USERNAME    = "username";
    private static final String ISSUE_KEY   = "issueKey";
    private static final String PROJECT_KEY = "projectKey";
    private static final String START_AT    = "startAt";
    private static final String MAX_RESULTS = "maxResults";

    private static final List<String> PROPERTIES = Arrays.asList(USERNAME, ISSUE_KEY, PROJECT_KEY, START_AT, MAX_RESULTS);

    private String userName;
    private String issueKey;
    private String projectKey;
    private String startAt;
    private String maxResults;

    private String userNameExpression;
    private String issueKeyExpression;
    private String projectKeyExpression;
    private String startAtExpression;
    private String maxResultsExpression;

    private Array<NameSpace> userNameNS;
    private Array<NameSpace> projectNS;
    private Array<NameSpace> issueKeyNS;
    private Array<NameSpace> startAtNS;
    private Array<NameSpace> maxResultsNS;

    @Inject
    public SearchIssueViewableUsers(EditorResources resources,
                                    Provider<Branch> branchProvider,
                                    MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.jiraIcon(),
              branchProvider,
              mediatorCreatorsManager);

        userName = "";
        issueKey = "";
        projectKey = "";
        startAt = "";
        maxResults = "";

        userNameExpression = "";
        projectKeyExpression = "";
        startAtExpression = "";
        issueKeyExpression = "";
        maxResultsExpression = "";

        startAtNS = createArray();
        userNameNS = createArray();
        issueKeyNS = createArray();
        projectNS = createArray();
        maxResultsNS = createArray();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(USERNAME, isInline ? userName : userNameExpression);
        properties.put(ISSUE_KEY, isInline ? issueKey : issueKeyExpression);
        properties.put(PROJECT_KEY, isInline ? projectKey : projectKeyExpression);
        properties.put(START_AT, isInline ? startAt : startAtExpression);
        properties.put(MAX_RESULTS, isInline ? maxResults : maxResultsExpression);

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();
        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case USERNAME:
                if (isInline) {
                    userName = nodeValue;
                } else {
                    userNameExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case PROJECT_KEY:
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

            case START_AT:
                if (isInline) {
                    startAt = nodeValue;
                } else {
                    startAtExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case MAX_RESULTS:
                if (isInline) {
                    maxResults = nodeValue;
                } else {
                    maxResultsExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;
        }
    }

    @Nonnull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@Nonnull String userName) {
        this.userName = userName;
    }

    @Nonnull
    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(@Nonnull String issueKey) {
        this.issueKey = issueKey;
    }

    @Nonnull
    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(@Nonnull String projectKey) {
        this.projectKey = projectKey;
    }

    @Nonnull
    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(@Nonnull String startAt) {
        this.startAt = startAt;
    }

    @Nonnull
    public String getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(@Nonnull String maxResults) {
        this.maxResults = maxResults;
    }

    @Nonnull
    public String getUserNameExpression() {
        return userNameExpression;
    }

    public void setUserNameExpression(@Nonnull String userNameExpression) {
        this.userNameExpression = userNameExpression;
    }

    @Nonnull
    public String getIssueKeyExpression() {
        return issueKeyExpression;
    }

    public void setIssueKeyExpression(@Nonnull String issueKeyExpression) {
        this.issueKeyExpression = issueKeyExpression;
    }

    @Nonnull
    public String getProjectKeyExpression() {
        return projectKeyExpression;
    }

    public void setProjectKeyExpression(@Nonnull String projectKeyExpression) {
        this.projectKeyExpression = projectKeyExpression;
    }

    @Nonnull
    public String getStartAtExpression() {
        return startAtExpression;
    }

    public void setStartAtExpression(@Nonnull String startAtExpression) {
        this.startAtExpression = startAtExpression;
    }

    @Nonnull
    public String getMaxResultsExpression() {
        return maxResultsExpression;
    }

    public void setMaxResultsExpression(@Nonnull String maxResultsExpression) {
        this.maxResultsExpression = maxResultsExpression;
    }

    @Nonnull
    public Array<NameSpace> getUserNameNS() {
        return userNameNS;
    }

    public void setUserNameNS(@Nonnull Array<NameSpace> userNameNS) {
        this.userNameNS = userNameNS;
    }

    @Nonnull
    public Array<NameSpace> getProjectNS() {
        return projectNS;
    }

    public void setProjectNS(@Nonnull Array<NameSpace> projectNS) {
        this.projectNS = projectNS;
    }

    @Nonnull
    public Array<NameSpace> getIssueKeyNS() {
        return issueKeyNS;
    }

    public void setIssueKeyNS(@Nonnull Array<NameSpace> issueKeyNS) {
        this.issueKeyNS = issueKeyNS;
    }

    @Nonnull
    public Array<NameSpace> getStartAtNS() {
        return startAtNS;
    }

    public void setStartAtNS(@Nonnull Array<NameSpace> startAtNS) {
        this.startAtNS = startAtNS;
    }

    @Nonnull
    public Array<NameSpace> getMaxResultsNS() {
        return maxResultsNS;
    }

    public void setMaxResultsNS(@Nonnull Array<NameSpace> maxResultsNS) {
        this.maxResultsNS = maxResultsNS;
    }
}