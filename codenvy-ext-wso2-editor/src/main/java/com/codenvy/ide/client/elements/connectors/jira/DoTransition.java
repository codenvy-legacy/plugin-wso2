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
    private String issueIdOrKey;
    private String updateComment;
    private String updateAssignee;
    private String resolution;
    private String transitionId;

    private String issueIdOrKeyExpression;
    private String updateCommentExpression;
    private String updateAssigneeExpression;
    private String resolutionExpression;
    private String transitionIdExpression;

    private List<NameSpace> issieIOrKeyNS;
    private List<NameSpace> updateCommentNS;
    private List<NameSpace> updateAssigneeNS;
    private List<NameSpace> resolutionNS;
    private List<NameSpace> transitionIdNS;

    @Inject
    public DoTransition(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.jiraIcon(),
              branchProvider,
              elementCreatorsManager);

        issueIdOrKey = "";
        updateComment = "";
        updateAssignee = "";
        resolution = "";
        transitionId = "";

        issueIdOrKeyExpression = "";
        updateCommentExpression = "";
        resolutionExpression = "";
        updateAssigneeExpression = "";
        transitionIdExpression = "";

        resolutionNS = new ArrayList<>();
        issieIOrKeyNS = new ArrayList<>();
        updateAssigneeNS = new ArrayList<>();
        updateCommentNS = new ArrayList<>();
        transitionIdNS = new ArrayList<>();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(ISSUE_ID_OR_KEY, isInline ? issueIdOrKey : issueIdOrKeyExpression);
        properties.put(UPDATE_COMMENT, isInline ? updateComment : updateCommentExpression);
        properties.put(UPDATE_ASSIGNEE, isInline ? updateAssignee : updateAssigneeExpression);
        properties.put(RESOLUTION, isInline ? resolution : resolutionExpression);
        properties.put(TRANSITION_ID, isInline ? transitionId : transitionIdExpression);

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

            case UPDATE_COMMENT:
                if (isInline) {
                    updateComment = nodeValue;
                } else {
                    updateCommentExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case UPDATE_ASSIGNEE:
                if (isInline) {
                    updateAssignee = nodeValue;
                } else {
                    updateAssigneeExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case RESOLUTION:
                if (isInline) {
                    resolution = nodeValue;
                } else {
                    resolutionExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case TRANSITION_ID:
                if (isInline) {
                    transitionId = nodeValue;
                } else {
                    transitionIdExpression = nodeValue;

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
    public String getUpdateComment() {
        return updateComment;
    }

    public void setUpdateComment(@Nonnull String updateComment) {
        this.updateComment = updateComment;
    }

    @Nonnull
    public String getUpdateAssignee() {
        return updateAssignee;
    }

    public void setUpdateAssignee(@Nonnull String updateAssignee) {
        this.updateAssignee = updateAssignee;
    }

    @Nonnull
    public String getResolution() {
        return resolution;
    }

    public void setResolution(@Nonnull String resolution) {
        this.resolution = resolution;
    }

    @Nonnull
    public String getTransitionId() {
        return transitionId;
    }

    public void setTransitionId(@Nonnull String transitionId) {
        this.transitionId = transitionId;
    }

    @Nonnull
    public String getIssueIdOrKeyExpression() {
        return issueIdOrKeyExpression;
    }

    public void setIssueIdOrKeyExpression(@Nonnull String issueIdOrKeyExpression) {
        this.issueIdOrKeyExpression = issueIdOrKeyExpression;
    }

    @Nonnull
    public String getUpdateCommentExpression() {
        return updateCommentExpression;
    }

    public void setUpdateCommentExpression(@Nonnull String updateCommentExpression) {
        this.updateCommentExpression = updateCommentExpression;
    }

    @Nonnull
    public String getUpdateAssigneeExpression() {
        return updateAssigneeExpression;
    }

    public void setUpdateAssigneeExpression(@Nonnull String updateAssigneeExpression) {
        this.updateAssigneeExpression = updateAssigneeExpression;
    }

    @Nonnull
    public String getResolutionExpression() {
        return resolutionExpression;
    }

    public void setResolutionExpression(@Nonnull String resolutionExpression) {
        this.resolutionExpression = resolutionExpression;
    }

    @Nonnull
    public String getTransitionIdExpression() {
        return transitionIdExpression;
    }

    public void setTransitionIdExpression(@Nonnull String transitionIdExpression) {
        this.transitionIdExpression = transitionIdExpression;
    }

    @Nonnull
    public List<NameSpace> getIssieIOrKeyNS() {
        return issieIOrKeyNS;
    }

    public void setIssieIOrKeyNS(@Nonnull List<NameSpace> issieIOrKeyNS) {
        this.issieIOrKeyNS = issieIOrKeyNS;
    }

    @Nonnull
    public List<NameSpace> getUpdateCommentNS() {
        return updateCommentNS;
    }

    public void setUpdateCommentNS(@Nonnull List<NameSpace> updateCommentNS) {
        this.updateCommentNS = updateCommentNS;
    }

    @Nonnull
    public List<NameSpace> getUpdateAssigneeNS() {
        return updateAssigneeNS;
    }

    public void setUpdateAssigneeNS(@Nonnull List<NameSpace> updateAssigneeNS) {
        this.updateAssigneeNS = updateAssigneeNS;
    }

    @Nonnull
    public List<NameSpace> getResolutionNS() {
        return resolutionNS;
    }

    public void setResolutionNS(@Nonnull List<NameSpace> resolutionNS) {
        this.resolutionNS = resolutionNS;
    }

    @Nonnull
    public List<NameSpace> getTransitionIdNS() {
        return transitionIdNS;
    }

    public void setTransitionIdNS(@Nonnull List<NameSpace> transitionIdNS) {
        this.transitionIdNS = transitionIdNS;
    }

}
