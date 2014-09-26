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

    public static final Key<String> PROJECT_KEY_INL = new Key<>("projectKeyInl");
    public static final Key<String> PROJECT_ID_INL  = new Key<>("projectIdInl");
    public static final Key<String> ISSUE_KEY_INL   = new Key<>("issueKeyInl");
    public static final Key<String> ISSUE_ID_INL    = new Key<>("issueIdInl");

    public static final Key<String> PROJECT_KEY_EXPR = new Key<>("projectKeyExpr");
    public static final Key<String> PROJECT_ID_EXPR  = new Key<>("projectIdExpr");
    public static final Key<String> ISSUE_KEY_EXPR   = new Key<>("issueKeyExpr");
    public static final Key<String> ISSUE_ID_EXPR    = new Key<>("issueIdExpr");

    public static final Key<List<NameSpace>> PROJECT_KEY_NS = new Key<>("projectKeyNameSpace");
    public static final Key<List<NameSpace>> PROJECT_ID_NS  = new Key<>("projectIdNameSpace");
    public static final Key<List<NameSpace>> ISSUE_KEY_NS   = new Key<>("issueKeyNameSpace");
    public static final Key<List<NameSpace>> ISSUE_ID_NS    = new Key<>("issueIdNameSpace");

    private static final String PROJECT_KEY = "projectKey";
    private static final String PROJECT_ID  = "projectId";
    private static final String ISSUE_KEY   = "issueKey";
    private static final String ISSUE_ID    = "issueId";

    private static final List<String> PROPERTIES = Arrays.asList(PROJECT_ID,
                                                                 PROJECT_KEY,
                                                                 ISSUE_KEY,
                                                                 ISSUE_ID);

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

        putProperty(PROJECT_ID_INL, "");
        putProperty(PROJECT_KEY_INL, "");
        putProperty(ISSUE_KEY_INL, "");
        putProperty(ISSUE_ID_INL, "");

        putProperty(PROJECT_ID_EXPR, "");
        putProperty(PROJECT_KEY_EXPR, "");
        putProperty(ISSUE_ID_EXPR, "");
        putProperty(ISSUE_KEY_EXPR, "");

        putProperty(PROJECT_ID_NS, new ArrayList<NameSpace>());
        putProperty(PROJECT_KEY_NS, new ArrayList<NameSpace>());
        putProperty(ISSUE_KEY_NS, new ArrayList<NameSpace>());
        putProperty(ISSUE_ID_NS, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(NAME_SPACED_PROPERTY_EDITOR);

        properties.put(PROJECT_ID, isInline ? getProperty(PROJECT_ID_INL) : getProperty(PROJECT_ID_EXPR));
        properties.put(PROJECT_KEY, isInline ? getProperty(PROJECT_KEY_INL) : getProperty(PROJECT_KEY_EXPR));
        properties.put(ISSUE_KEY, isInline ? getProperty(ISSUE_KEY_INL) : getProperty(ISSUE_ID_EXPR));
        properties.put(ISSUE_ID, isInline ? getProperty(ISSUE_ID_INL) : getProperty(ISSUE_KEY_EXPR));

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        switch (node.getNodeName()) {
            case PROJECT_ID:
                adaptProperty(nodeValue, PROJECT_ID_INL, PROJECT_ID_EXPR);
                break;

            case PROJECT_KEY:
                adaptProperty(nodeValue, PROJECT_KEY_INL, PROJECT_KEY_EXPR);
                break;

            case ISSUE_KEY:
                adaptProperty(nodeValue, ISSUE_KEY_INL, ISSUE_ID_EXPR);
                break;

            case ISSUE_ID:
                adaptProperty(nodeValue, ISSUE_ID_INL, ISSUE_KEY_EXPR);
                break;

            default:
        }
    }
}
