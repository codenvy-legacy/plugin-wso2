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
 * The Class describes GetIssueTypeById connector for jira group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class GetIssueTypeById extends AbstractConnector {

    public static final String ELEMENT_NAME       = "GetIssueTypeById";
    public static final String SERIALIZATION_NAME = "jira.getIssueTypeById";

    private static final String ISSUE_TYPE_ID = "issueTypeId";

    private static final List<String> PROPERTIES = Arrays.asList(ISSUE_TYPE_ID);

    private String           issueTypeId;
    private String           issueTypeIdExpression;
    private Array<NameSpace> issueTypeIdNS;

    @Inject
    public GetIssueTypeById(EditorResources resources, Provider<Branch> branchProvider, MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.jiraIcon(),
              branchProvider,
              mediatorCreatorsManager);

        issueTypeId = "";
        issueTypeIdExpression = "";

        issueTypeIdNS = createArray();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(ISSUE_TYPE_ID, isInline ? issueTypeId : issueTypeIdExpression);

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case ISSUE_TYPE_ID:
                if (isInline) {
                    issueTypeId = nodeValue;
                } else {
                    issueTypeIdExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;
        }
    }

    @Nonnull
    public String getIssueTypeId() {
        return issueTypeId;
    }

    public void setIssueTypeId(@Nonnull String issueTypeId) {
        this.issueTypeId = issueTypeId;
    }

    @Nonnull
    public String getIssueTypeIdExpression() {
        return issueTypeIdExpression;
    }

    public void setIssueTypeIdExpression(@Nonnull String issueTypeIdExpression) {
        this.issueTypeIdExpression = issueTypeIdExpression;
    }

    @Nonnull
    public Array<NameSpace> getIssueTypeIdNS() {
        return issueTypeIdNS;
    }

    public void setIssueTypeIdNS(@Nonnull Array<NameSpace> issueTypeIdNS) {
        this.issueTypeIdNS = issueTypeIdNS;
    }

}
