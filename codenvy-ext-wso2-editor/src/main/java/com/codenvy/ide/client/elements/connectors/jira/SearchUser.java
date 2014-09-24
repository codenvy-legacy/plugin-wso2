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
 * The Class describes SearchUser connector for jira group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class SearchUser extends AbstractConnector {

    public static final String ELEMENT_NAME       = "SearchUser";
    public static final String SERIALIZATION_NAME = "jira.searchUser";

    private static final String USERNAME         = "username";
    private static final String START_AT         = "startAt";
    private static final String MAX_RESULTS      = "maxResults";
    private static final String INCLUDE_ACTIVE   = "includeActive";
    private static final String INCLUDE_INACTIVE = "includeInactive";

    private static final List<String> PROPERTIES = Arrays.asList(USERNAME, START_AT, MAX_RESULTS, INCLUDE_ACTIVE, INCLUDE_INACTIVE);

    private String userName;
    private String startAt;
    private String maxResults;
    private String includeActive;
    private String includeInactive;

    private String userNameExpression;
    private String startAtExpression;
    private String maxResultsExpression;
    private String includeActiveExpression;
    private String includeInactiveExpression;

    private List<NameSpace> userNameNS;
    private List<NameSpace> includeInactiveNS;
    private List<NameSpace> includeActiveNS;
    private List<NameSpace> startAtNS;
    private List<NameSpace> maxResultsNS;

    @Inject
    public SearchUser(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.jiraIcon(),
              branchProvider,
              elementCreatorsManager);

        userName = "";
        startAt = "";
        maxResults = "";
        includeActive = "";
        includeInactive = "";

        userNameExpression = "";
        maxResultsExpression = "";
        includeActiveExpression = "";
        startAtExpression = "";
        includeInactiveExpression = "";

        userNameNS = new ArrayList<>();
        startAtNS = new ArrayList<>();
        maxResultsNS = new ArrayList<>();
        includeActiveNS = new ArrayList<>();
        includeInactiveNS = new ArrayList<>();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(USERNAME, isInline ? userName : userNameExpression);
        properties.put(START_AT, isInline ? startAt : startAtExpression);
        properties.put(MAX_RESULTS, isInline ? maxResults : maxResultsExpression);
        properties.put(INCLUDE_ACTIVE, isInline ? includeActive : includeActiveExpression);
        properties.put(INCLUDE_INACTIVE, isInline ? includeInactive : includeInactiveExpression);

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

            case MAX_RESULTS:
                if (isInline) {
                    maxResults = nodeValue;
                } else {
                    maxResultsExpression = nodeValue;

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

            case INCLUDE_ACTIVE:
                if (isInline) {
                    includeActive = nodeValue;
                } else {
                    includeActiveExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case INCLUDE_INACTIVE:
                if (isInline) {
                    includeInactive = nodeValue;
                } else {
                    includeInactiveExpression = nodeValue;

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
    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(@Nonnull String shartAt) {
        this.startAt = shartAt;
    }

    @Nonnull
    public String getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(@Nonnull String maxResults) {
        this.maxResults = maxResults;
    }

    @Nonnull
    public String getIncludeActive() {
        return includeActive;
    }

    public void setIncludeActive(@Nonnull String includeActive) {
        this.includeActive = includeActive;
    }

    @Nonnull
    public String getIncludeInactive() {
        return includeInactive;
    }

    public void setIncludeInactive(@Nonnull String includeInactive) {
        this.includeInactive = includeInactive;
    }

    @Nonnull
    public String getUserNameExpression() {
        return userNameExpression;
    }

    public void setUserNameExpression(@Nonnull String userNameExpression) {
        this.userNameExpression = userNameExpression;
    }

    @Nonnull
    public String getStartAtExpression() {
        return startAtExpression;
    }

    public void setStartAtExpression(@Nonnull String ctartAtExpression) {
        this.startAtExpression = ctartAtExpression;
    }

    @Nonnull
    public String getMaxResultsExpression() {
        return maxResultsExpression;
    }

    public void setMaxResultsExpression(@Nonnull String maxResultsExpression) {
        this.maxResultsExpression = maxResultsExpression;
    }

    @Nonnull
    public String getIncludeActiveExpression() {
        return includeActiveExpression;
    }

    public void setIncludeActiveExpression(@Nonnull String includeActiveExpression) {
        this.includeActiveExpression = includeActiveExpression;
    }

    @Nonnull
    public String getIncludeInactiveExpression() {
        return includeInactiveExpression;
    }

    public void setIncludeInactiveExpression(@Nonnull String includeInactiveExpression) {
        this.includeInactiveExpression = includeInactiveExpression;
    }

    @Nonnull
    public List<NameSpace> getUserNameNS() {
        return userNameNS;
    }

    public void setUserNameNS(@Nonnull List<NameSpace> userNameNS) {
        this.userNameNS = userNameNS;
    }

    @Nonnull
    public List<NameSpace> getIncludeInactiveNS() {
        return includeInactiveNS;
    }

    public void setIncludeInactiveNS(@Nonnull List<NameSpace> includeInactiveNS) {
        this.includeInactiveNS = includeInactiveNS;
    }

    @Nonnull
    public List<NameSpace> getIncludeActiveNS() {
        return includeActiveNS;
    }

    public void setIncludeActiveNS(@Nonnull List<NameSpace> includeActiveNS) {
        this.includeActiveNS = includeActiveNS;
    }

    @Nonnull
    public List<NameSpace> getChartAtNS() {
        return startAtNS;
    }

    public void setStartAtNS(@Nonnull List<NameSpace> startAtNS) {
        this.startAtNS = startAtNS;
    }

    @Nonnull
    public List<NameSpace> getMaxResultsNS() {
        return maxResultsNS;
    }

    public void setMaxResultsNS(@Nonnull List<NameSpace> maxResultsNS) {
        this.maxResultsNS = maxResultsNS;
    }
}
