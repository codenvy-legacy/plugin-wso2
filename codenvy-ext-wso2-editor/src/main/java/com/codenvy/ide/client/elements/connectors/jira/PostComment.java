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
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.Inline;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NamespacedPropertyEditor;
import static com.codenvy.ide.collections.Collections.createArray;

/**
 * The Class describes PostComment connector for jira group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 */
public class PostComment extends AbstractConnector {

    public static final String ELEMENT_NAME       = "PostComment";
    public static final String SERIALIZATION_NAME = "jira.postComment";

    private static final String ISSUE_ID_OR_KEY = "issueIdOrKey";
    private static final String COMMENT         = "comment";
    private static final String VISIBLE_ROLE    = "visibleRole";

    private static final List<String> PROPERTIES = Arrays.asList(ISSUE_ID_OR_KEY, COMMENT, VISIBLE_ROLE);

    private String issueIdOrKey;
    private String comment;
    private String visibleRole;

    private String issueIdOrKeyExpression;
    private String commentExpression;
    private String visibleRoleExpression;

    private Array<NameSpace> issieIOrKeyNS;
    private Array<NameSpace> commentNS;
    private Array<NameSpace> visibleRoleNS;

    @Inject
    public PostComment(EditorResources resources, Provider<Branch> branchProvider, MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        issueIdOrKey = "";
        comment = "";
        visibleRole = "";

        issueIdOrKeyExpression = "";
        commentExpression = "";
        visibleRoleExpression = "";

        issieIOrKeyNS = createArray();
        visibleRoleNS = createArray();
        commentNS = createArray();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(ISSUE_ID_OR_KEY, isInline ? issueIdOrKey : issueIdOrKeyExpression);
        properties.put(COMMENT, isInline ? comment : commentExpression);
        properties.put(VISIBLE_ROLE, isInline ? visibleRole : visibleRoleExpression);

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();
        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case ISSUE_ID_OR_KEY:
                if (isInline) {
                    issueIdOrKey = nodeValue;
                } else {
                    issueIdOrKeyExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case COMMENT:
                if (isInline) {
                    comment = nodeValue;
                } else {
                    commentExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case VISIBLE_ROLE:
                if (isInline) {
                    visibleRole = nodeValue;
                } else {
                    visibleRoleExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;
        }
    }

    @Nonnull
    public String getIssueIdOrKey() {
        return issueIdOrKey;
    }

    public void setIssueIdOrKey(@Nonnull String issueIdOrKey) {
        this.issueIdOrKey = issueIdOrKey;
    }

    @Nonnull
    public String getComment() {
        return comment;
    }

    public void setComment(@Nonnull String comment) {
        this.comment = comment;
    }

    @Nonnull
    public String getVisibleRole() {
        return visibleRole;
    }

    public void setVisibleRole(@Nonnull String visibleRole) {
        this.visibleRole = visibleRole;
    }

    @Nonnull
    public String getIssueIdOrKeyExpression() {
        return issueIdOrKeyExpression;
    }

    public void setIssueIdOrKeyExpression(@Nonnull String issueIdOrKeyExpression) {
        this.issueIdOrKeyExpression = issueIdOrKeyExpression;
    }

    @Nonnull
    public String getCommentExpression() {
        return commentExpression;
    }

    public void setCommentExpression(@Nonnull String commentExpression) {
        this.commentExpression = commentExpression;
    }

    @Nonnull
    public String getVisibleRoleExpression() {
        return visibleRoleExpression;
    }

    public void setVisibleRoleExpression(@Nonnull String visibleRoleExpression) {
        this.visibleRoleExpression = visibleRoleExpression;
    }

    @Nonnull
    public Array<NameSpace> getIssieIOrKeyNS() {
        return issieIOrKeyNS;
    }

    public void setIssieIOrKeyNS(@Nonnull Array<NameSpace> issieIOrKeyNS) {
        this.issieIOrKeyNS = issieIOrKeyNS;
    }

    @Nonnull
    public Array<NameSpace> getCommentNS() {
        return commentNS;
    }

    public void setCommentNS(@Nonnull Array<NameSpace> commentNS) {
        this.commentNS = commentNS;
    }

    @Nonnull
    public Array<NameSpace> getVisibleRoleNS() {
        return visibleRoleNS;
    }

    public void setVisibleRoleNS(@Nonnull Array<NameSpace> visibleRoleNS) {
        this.visibleRoleNS = visibleRoleNS;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.jiraIcon();
    }
}