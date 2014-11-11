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
 * The Class describes GetUserAssignableProjects connector for jira group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class GetUserAssignableProjects extends AbstractConnector {

    public static final String ELEMENT_NAME       = "GetUserAssignableProjects";
    public static final String SERIALIZATION_NAME = "jira.getUserAssignableProjects";

    public static final Key<String> USER_NAME_INL   = new Key<>("userNameInl");
    public static final Key<String> PROJECT_KEY_INL = new Key<>("projectKeyInl");
    public static final Key<String> MAX_RESULT_INL  = new Key<>("maxResultInl");
    public static final Key<String> START_FROM_INL  = new Key<>("startFromInl");

    public static final Key<String> USER_NAME_EXPR   = new Key<>("userNameExpr");
    public static final Key<String> PROJECT_KEY_EXPR = new Key<>("projectKeyExpr");
    public static final Key<String> MAX_RESULT_EXPR  = new Key<>("maxResultExpr");
    public static final Key<String> START_FROM_EXPR  = new Key<>("startFromExpr");

    public static final Key<List<NameSpace>> USER_NAME_NS   = new Key<>("userNameNameSpace");
    public static final Key<List<NameSpace>> PROJECT_KEY_NS = new Key<>("projectKeyNameSpace");
    public static final Key<List<NameSpace>> MAX_RESULT_NS  = new Key<>("maxResultNameSpace");
    public static final Key<List<NameSpace>> START_FROM_NS  = new Key<>("startFromNameSpace");

    private static final String USER_NAME   = "username";
    private static final String PROJECT_KEY = "projectKey";
    private static final String MAX_RESULT  = "maxResult";
    private static final String START_FROM  = "startFrom";

    private static final List<String> PROPERTIES = Arrays.asList(USER_NAME,
                                                                 PROJECT_KEY,
                                                                 MAX_RESULT,
                                                                 START_FROM);

    @Inject
    public GetUserAssignableProjects(EditorResources resources,
                                     Provider<Branch> branchProvider,
                                     ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              resources.jiraIcon(),
              branchProvider,
              elementCreatorsManager);

        putProperty(USER_NAME_INL, "");
        putProperty(PROJECT_KEY_INL, "");
        putProperty(MAX_RESULT_INL, "");
        putProperty(START_FROM_INL, "");

        putProperty(USER_NAME_EXPR, "");
        putProperty(PROJECT_KEY_EXPR, "");
        putProperty(MAX_RESULT_EXPR, "");
        putProperty(START_FROM_EXPR, "");

        putProperty(USER_NAME_NS, new ArrayList<NameSpace>());
        putProperty(PROJECT_KEY_NS, new ArrayList<NameSpace>());
        putProperty(MAX_RESULT_NS, new ArrayList<NameSpace>());
        putProperty(START_FROM_NS, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE));

        properties.put(USER_NAME, isInline ? getProperty(USER_NAME_INL) : getProperty(USER_NAME_EXPR));
        properties.put(PROJECT_KEY, isInline ? getProperty(PROJECT_KEY_INL) : getProperty(PROJECT_KEY_EXPR));
        properties.put(MAX_RESULT, isInline ? getProperty(MAX_RESULT_INL) : getProperty(MAX_RESULT_EXPR));
        properties.put(START_FROM, isInline ? getProperty(START_FROM_INL) : getProperty(START_FROM_EXPR));

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        switch (node.getNodeName()) {
            case USER_NAME:
                adaptProperty(nodeValue, USER_NAME_INL, USER_NAME_EXPR);
                break;

            case PROJECT_KEY:
                adaptProperty(nodeValue, PROJECT_KEY_INL, PROJECT_KEY_EXPR);
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
