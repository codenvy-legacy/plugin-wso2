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

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.INLINE;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NAME_SPACED_PROPERTY_EDITOR;

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

    public static final Key<String> QUERY_INL      = new Key<>("queryInl");
    public static final Key<String> MAX_RESULT_INL = new Key<>("maxResultInl");
    public static final Key<String> START_FROM_INL = new Key<>("startFromInl");

    public static final Key<String> QUERY_EXPR      = new Key<>("queryExpr");
    public static final Key<String> MAX_RESULT_EXPR = new Key<>("maxResultExpr");
    public static final Key<String> START_FROM_EXPR = new Key<>("startFromExpr");

    public static final Key<List<NameSpace>> QUERY_NS      = new Key<>("queryNameSpace");
    public static final Key<List<NameSpace>> MAX_RESULT_NS = new Key<>("maxResultNameSpace");
    public static final Key<List<NameSpace>> START_FROM_NS = new Key<>("startFromNameSpace");

    private static final String QUERY      = "query";
    private static final String MAX_RESULT = "maxResult";
    private static final String START_FROM = "startFrom";

    private static final List<String> PROPERTIES = Arrays.asList(QUERY, MAX_RESULT, START_FROM);

    @Inject
    public SearchJira(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.jiraIcon(),
              branchProvider,
              elementCreatorsManager);

        putProperty(QUERY_INL, "");
        putProperty(MAX_RESULT_INL, "");
        putProperty(START_FROM_INL, "");

        putProperty(QUERY_EXPR, "");
        putProperty(MAX_RESULT_EXPR, "");
        putProperty(START_FROM_EXPR, "");

        putProperty(QUERY_NS, new ArrayList<NameSpace>());
        putProperty(MAX_RESULT_NS, new ArrayList<NameSpace>());
        putProperty(START_FROM_NS, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE));

        properties.put(QUERY, isInline ? getProperty(QUERY_INL) : getProperty(QUERY_EXPR));
        properties.put(MAX_RESULT, isInline ? getProperty(MAX_RESULT_INL) : getProperty(MAX_RESULT_EXPR));
        properties.put(START_FROM, isInline ? getProperty(START_FROM_INL) : getProperty(START_FROM_EXPR));

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        switch (node.getNodeName()) {
            case QUERY:
                adaptProperty(nodeValue, QUERY_INL, QUERY_EXPR);
                break;

            case MAX_RESULT:
                adaptProperty(nodeValue, MAX_RESULT_INL, MAX_RESULT_EXPR);
                break;

            case START_FROM:
                adaptProperty(nodeValue, START_FROM_INL, START_FROM_EXPR);
                break;

            default:
        }
    }
}