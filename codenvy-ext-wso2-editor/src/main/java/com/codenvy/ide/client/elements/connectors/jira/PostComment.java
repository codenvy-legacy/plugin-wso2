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
 * The Class describes PostComment connector for jira group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class PostComment extends AbstractConnector {

    public static final String ELEMENT_NAME       = "PostComment";
    public static final String SERIALIZATION_NAME = "jira.postComment";

    public static final Key<String> ISSUE_ID_OR_KEY_INL = new Key<>("issueIdOrKeyInl");
    public static final Key<String> COMMENT_INL         = new Key<>("updateCommentInl");
    public static final Key<String> VISIBLE_ROLE_INL    = new Key<>("updateAssigneeInl");

    public static final Key<String> ISSUE_ID_OR_KEY_EXPR = new Key<>("issueIdOrKeyExpr");
    public static final Key<String> COMMENT_EXPR         = new Key<>("updateCommentExpr");
    public static final Key<String> VISIBLE_ROLE_EXPR    = new Key<>("updateAssigneeExpr");

    public static final Key<List<NameSpace>> ISSUE_ID_OR_KEY_NS = new Key<>("issueIdOrKeyNameSpace");
    public static final Key<List<NameSpace>> COMMENT_NS         = new Key<>("updateCommentNameSpace");
    public static final Key<List<NameSpace>> VISIBLE_ROLE_NS    = new Key<>("updateAssigneeNameSpace");

    private static final String ISSUE_ID_OR_KEY = "issueIdOrKey";
    private static final String COMMENT         = "comment";
    private static final String VISIBLE_ROLE    = "visibleRole";

    private static final List<String> PROPERTIES = Arrays.asList(ISSUE_ID_OR_KEY,
                                                                 COMMENT,
                                                                 VISIBLE_ROLE);

    @Inject
    public PostComment(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.jiraIcon(),
              branchProvider,
              elementCreatorsManager);

        putProperty(ISSUE_ID_OR_KEY_INL, "");
        putProperty(COMMENT_INL, "");
        putProperty(VISIBLE_ROLE_INL, "");

        putProperty(ISSUE_ID_OR_KEY_EXPR, "");
        putProperty(COMMENT_EXPR, "");
        putProperty(VISIBLE_ROLE_EXPR, "");

        putProperty(ISSUE_ID_OR_KEY_NS, new ArrayList<NameSpace>());
        putProperty(COMMENT_NS, new ArrayList<NameSpace>());
        putProperty(VISIBLE_ROLE_NS, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE));

        properties.put(ISSUE_ID_OR_KEY, isInline ? getProperty(ISSUE_ID_OR_KEY_INL) : getProperty(ISSUE_ID_OR_KEY_EXPR));
        properties.put(COMMENT, isInline ? getProperty(COMMENT_INL) : getProperty(COMMENT_EXPR));
        properties.put(VISIBLE_ROLE, isInline ? getProperty(VISIBLE_ROLE_INL) : getProperty(VISIBLE_ROLE_EXPR));

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

            case COMMENT:
                adaptProperty(nodeValue, COMMENT_INL, COMMENT_EXPR);
                break;

            case VISIBLE_ROLE:
                adaptProperty(nodeValue, VISIBLE_ROLE_INL, VISIBLE_ROLE_EXPR);
                break;

            default:
        }
    }
}