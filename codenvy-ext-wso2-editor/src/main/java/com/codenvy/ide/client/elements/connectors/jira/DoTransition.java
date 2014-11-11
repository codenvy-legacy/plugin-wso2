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
 * The Class describes DoTransition connector for jira group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class DoTransition extends AbstractConnector {

    public static final String ELEMENT_NAME       = "DoTransition";
    public static final String SERIALIZATION_NAME = "jira.doTransition";

    public static final Key<String> ISSUE_ID_OR_KEY_INL = new Key<>("issueIdOrKeyInl");
    public static final Key<String> UPDATE_COMMENT_INL  = new Key<>("updateCommentInl");
    public static final Key<String> UPDATE_ASSIGNEE_INL = new Key<>("updateAssigneeInl");
    public static final Key<String> RESOLUTION_INL      = new Key<>("resolutionInl");
    public static final Key<String> TRANSITION_ID_INL   = new Key<>("transitionIdInl");

    public static final Key<String> ISSUE_ID_OR_KEY_EXPR = new Key<>("issueIdOrKeyExpr");
    public static final Key<String> UPDATE_COMMENT_EXPR  = new Key<>("updateCommentExpr");
    public static final Key<String> UPDATE_ASSIGNEE_EXPR = new Key<>("updateAssigneeExpr");
    public static final Key<String> RESOLUTION_EXPR      = new Key<>("resolutionExpr");
    public static final Key<String> TRANSITION_ID_EXPR   = new Key<>("transitionIdExpr");

    public static final Key<List<NameSpace>> ISSUE_ID_OR_KEY_NS = new Key<>("issueIdOrKeyNameSpace");
    public static final Key<List<NameSpace>> UPDATE_COMMENT_NS  = new Key<>("updateCommentNameSpace");
    public static final Key<List<NameSpace>> UPDATE_ASSIGNEE_NS = new Key<>("updateAssigneeNameSpace");
    public static final Key<List<NameSpace>> RESOLUTION_NS      = new Key<>("resolutionNameSpace");
    public static final Key<List<NameSpace>> TRANSITION_ID_NS   = new Key<>("transitionIdNameSpace");

    private static final String ISSUE_ID_OR_KEY = "issueIdOrKey";
    private static final String UPDATE_COMMENT  = "updateComment";
    private static final String UPDATE_ASSIGNEE = "updateAssignee";
    private static final String RESOLUTION      = "resolution";
    private static final String TRANSITION_ID   = "transitionId";

    private static final List<String> PROPERTIES = Arrays.asList(ISSUE_ID_OR_KEY,
                                                                 UPDATE_COMMENT,
                                                                 UPDATE_ASSIGNEE,
                                                                 RESOLUTION,
                                                                 TRANSITION_ID);

    @Inject
    public DoTransition(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              resources.jiraIcon(),
              branchProvider,
              elementCreatorsManager);

        putProperty(ISSUE_ID_OR_KEY_INL, "");
        putProperty(UPDATE_COMMENT_INL, "");
        putProperty(UPDATE_ASSIGNEE_INL, "");
        putProperty(RESOLUTION_INL, "");
        putProperty(TRANSITION_ID_INL, "");

        putProperty(ISSUE_ID_OR_KEY_EXPR, "");
        putProperty(UPDATE_COMMENT_EXPR, "");
        putProperty(UPDATE_ASSIGNEE_EXPR, "");
        putProperty(RESOLUTION_EXPR, "");
        putProperty(TRANSITION_ID_EXPR, "");

        putProperty(ISSUE_ID_OR_KEY_NS, new ArrayList<NameSpace>());
        putProperty(UPDATE_COMMENT_NS, new ArrayList<NameSpace>());
        putProperty(UPDATE_ASSIGNEE_NS, new ArrayList<NameSpace>());
        putProperty(RESOLUTION_NS, new ArrayList<NameSpace>());
        putProperty(TRANSITION_ID_NS, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE));

        properties.put(ISSUE_ID_OR_KEY, isInline ? getProperty(ISSUE_ID_OR_KEY_INL) : getProperty(ISSUE_ID_OR_KEY_EXPR));
        properties.put(UPDATE_COMMENT, isInline ? getProperty(UPDATE_COMMENT_INL) : getProperty(UPDATE_COMMENT_EXPR));
        properties.put(UPDATE_ASSIGNEE, isInline ? getProperty(UPDATE_ASSIGNEE_INL) : getProperty(UPDATE_ASSIGNEE_EXPR));
        properties.put(RESOLUTION, isInline ? getProperty(RESOLUTION_INL) : getProperty(RESOLUTION_EXPR));
        properties.put(TRANSITION_ID, isInline ? getProperty(TRANSITION_ID_INL) : getProperty(TRANSITION_ID_EXPR));

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        switch (node.getNodeName()) {
            case ISSUE_ID_OR_KEY:
                adaptProperty(nodeValue, ISSUE_ID_OR_KEY_INL, ISSUE_ID_OR_KEY_EXPR);
                break;

            case UPDATE_COMMENT:
                adaptProperty(nodeValue, UPDATE_COMMENT_INL, UPDATE_COMMENT_EXPR);
                break;

            case UPDATE_ASSIGNEE:
                adaptProperty(nodeValue, UPDATE_ASSIGNEE_INL, UPDATE_ASSIGNEE_EXPR);
                break;

            case RESOLUTION:
                adaptProperty(nodeValue, RESOLUTION_INL, RESOLUTION_EXPR);
                break;

            case TRANSITION_ID:
                adaptProperty(nodeValue, TRANSITION_ID_INL, TRANSITION_ID_EXPR);
                break;

            default:
        }
    }
}
