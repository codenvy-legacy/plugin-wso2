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
 * The Class describes SearchJira connector for jira group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class SearchJira extends AbstractConnector {

    public static final String ELEMENT_NAME       = "SearchJira";
    public static final String SERIALIZATION_NAME = "jira.searchJira";

    private static final String QUERY      = "query";
    private static final String MAX_RESULT = "maxResult";
    private static final String START_FROM = "startFrom";

    private static final List<String> PROPERTIES = Arrays.asList(QUERY, MAX_RESULT, START_FROM);

    private String query;
    private String maxResult;
    private String startFrom;

    private String queryExpression;
    private String maxResultExpression;
    private String startFromExpression;

    private Array<NameSpace> queryNS;
    private Array<NameSpace> maxResultNS;
    private Array<NameSpace> startFromNS;

    @Inject
    public SearchJira(EditorResources resources, Provider<Branch> branchProvider, MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.jiraIcon(),
              branchProvider,
              mediatorCreatorsManager);

        query = "";
        maxResult = "";
        startFrom = "";

        queryExpression = "";
        startFromExpression = "";
        maxResultExpression = "";

        startFromNS = createArray();
        queryNS = createArray();
        maxResultNS = createArray();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(QUERY, isInline ? query : queryExpression);
        properties.put(MAX_RESULT, isInline ? maxResult : maxResultExpression);
        properties.put(START_FROM, isInline ? startFrom : startFromExpression);

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case QUERY:
                if (isInline) {
                    query = nodeValue;
                } else {
                    queryExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case MAX_RESULT:
                if (isInline) {
                    maxResult = nodeValue;
                } else {
                    maxResultExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case START_FROM:
                if (isInline) {
                    startFrom = nodeValue;
                } else {
                    startFromExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;
        }
    }

    @Nonnull
    public String getQuery() {
        return query;
    }

    public void setQuery(@Nonnull String query) {
        this.query = query;
    }

    @Nonnull
    public String getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(@Nonnull String maxResult) {
        this.maxResult = maxResult;
    }

    @Nonnull
    public String getStartFrom() {
        return startFrom;
    }

    public void setStartFrom(@Nonnull String startFrom) {
        this.startFrom = startFrom;
    }

    @Nonnull
    public String getQueryExpression() {
        return queryExpression;
    }

    public void setQueryExpression(@Nonnull String queryExpression) {
        this.queryExpression = queryExpression;
    }

    @Nonnull
    public String getMaxResultExpression() {
        return maxResultExpression;
    }

    public void setMaxResultExpression(@Nonnull String maxResultExpression) {
        this.maxResultExpression = maxResultExpression;
    }

    @Nonnull
    public String getStartFromExpression() {
        return startFromExpression;
    }

    public void setStartFromExpression(@Nonnull String startFromExpression) {
        this.startFromExpression = startFromExpression;
    }

    @Nonnull
    public Array<NameSpace> getQueryNS() {
        return queryNS;
    }

    public void setQueryNS(@Nonnull Array<NameSpace> queryNS) {
        this.queryNS = queryNS;
    }

    @Nonnull
    public Array<NameSpace> getMaxResultNS() {
        return maxResultNS;
    }

    public void setMaxResultNS(@Nonnull Array<NameSpace> maxResultNS) {
        this.maxResultNS = maxResultNS;
    }

    @Nonnull
    public Array<NameSpace> getStartFromNS() {
        return startFromNS;
    }

    public void setStartFromNS(@Nonnull Array<NameSpace> startFromNS) {
        this.startFromNS = startFromNS;
    }
}