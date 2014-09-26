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

    public static final Key<String> PROJECT_KEY_INL = new Key<>("ProjectKeyInl");
    public static final Key<String> SUMMARY_INL     = new Key<>("summaryInl");
    public static final Key<String> DESCRIPTION_INL = new Key<>("descriptionInl");
    public static final Key<String> ISSUE_TYPE_INL  = new Key<>("issueTypeInl");

    public static final Key<String> PROJECT_KEY_EXPR = new Key<>("projectKeyExpr");
    public static final Key<String> SUMMARY_EXPR     = new Key<>("summaryExpr");
    public static final Key<String> DESCRIPTION_EXPR = new Key<>("descriptionExpr");
    public static final Key<String> ISSUE_TYPE_EXPR  = new Key<>("issueTypeExpr");

    public static final Key<List<NameSpace>> PROJECT_KEY_NS = new Key<>("projectKeyNameSpace");
    public static final Key<List<NameSpace>> SUMMARY_NS     = new Key<>("summaryNameSpace");
    public static final Key<List<NameSpace>> DESCRIPTION_NS = new Key<>("descriptionNameSpace");
    public static final Key<List<NameSpace>> ISSUE_TYPE_NS  = new Key<>("issueTypeNameSpace");

    private static final String PROJECT_KEY = "projectKey";
    private static final String SUMMARY     = "summary";
    private static final String DESCRIPTION = "description";
    private static final String ISSUE_TYPE  = "issueType";

    private static final List<String> PROPERTIES = Arrays.asList(PROJECT_KEY, SUMMARY, DESCRIPTION, ISSUE_TYPE);

    @Inject
    public CreateIssue(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.jiraIcon(),
              branchProvider,
              elementCreatorsManager);

        putProperty(PROJECT_KEY_INL, "");
        putProperty(SUMMARY_INL, "");
        putProperty(DESCRIPTION_INL, "");
        putProperty(ISSUE_TYPE_INL, "");

        putProperty(PROJECT_KEY_EXPR, "");
        putProperty(SUMMARY_EXPR, "");
        putProperty(DESCRIPTION_EXPR, "");
        putProperty(ISSUE_TYPE_EXPR, "");

        putProperty(PROJECT_KEY_NS, new ArrayList<NameSpace>());
        putProperty(SUMMARY_NS, new ArrayList<NameSpace>());
        putProperty(DESCRIPTION_NS, new ArrayList<NameSpace>());
        putProperty(ISSUE_TYPE_NS, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE));

        properties.put(PROJECT_KEY, isInline ? getProperty(PROJECT_KEY_INL) : getProperty(PROJECT_KEY_EXPR));
        properties.put(SUMMARY, isInline ? getProperty(SUMMARY_INL) : getProperty(SUMMARY_EXPR));
        properties.put(DESCRIPTION, isInline ? getProperty(DESCRIPTION_INL) : getProperty(DESCRIPTION_EXPR));
        properties.put(ISSUE_TYPE, isInline ? getProperty(ISSUE_TYPE_INL) : getProperty(ISSUE_TYPE_EXPR));

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        switch (node.getNodeName()) {
            case PROJECT_KEY:
                adaptProperty(nodeValue, PROJECT_KEY_INL, PROJECT_KEY_EXPR);
                break;

            case SUMMARY:
                adaptProperty(nodeValue, SUMMARY_INL, SUMMARY_EXPR);
                break;

            case DESCRIPTION:
                adaptProperty(nodeValue, DESCRIPTION_INL, DESCRIPTION_EXPR);
                break;

            case ISSUE_TYPE:
                adaptProperty(nodeValue, ISSUE_TYPE_INL, ISSUE_TYPE_EXPR);
                break;

            default:
        }
    }
}