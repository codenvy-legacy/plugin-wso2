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
 * The Class describes GetVotesForIssue connector for jira group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class GetVotesForIssue extends AbstractConnector {

    public static final String ELEMENT_NAME       = "GetVotesForIssue";
    public static final String SERIALIZATION_NAME = "jira.getVotesForIssue";

    private static final String ISSUE_ID_OR_KEY = "issueIdOrKey";

    private static final List<String> PROPERTIES = Arrays.asList(ISSUE_ID_OR_KEY);

    private String           issueIdOrKey;
    private String           issueIdOrKeyExpression;
    private Array<NameSpace> issueIdOrKeyNameSpaces;

    @Inject
    public GetVotesForIssue(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
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
        issueIdOrKeyExpression = "";

        issueIdOrKeyNameSpaces = createArray();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(ISSUE_ID_OR_KEY, isInline ? issueIdOrKey : issueIdOrKeyExpression);

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
    public String getIssueIdOrKeyExpression() {
        return issueIdOrKeyExpression;
    }

    public void setIssueIdOrKeyExpression(@Nonnull String issueIdOrKeyExpression) {
        this.issueIdOrKeyExpression = issueIdOrKeyExpression;
    }

    @Nonnull
    public Array<NameSpace> getIssueIdOrKeyNameSpaces() {
        return issueIdOrKeyNameSpaces;
    }

    public void setIssueIdOrKeyNameSpaces(@Nonnull Array<NameSpace> issueIdOrKeyNameSpaces) {
        this.issueIdOrKeyNameSpaces = issueIdOrKeyNameSpaces;
    }
}
