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
 * The Class describes GetIssuePriorityById connector for jira group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class GetIssuePriorityById extends AbstractConnector {

    public static final String ELEMENT_NAME       = "GetIssuePriorityById";
    public static final String SERIALIZATION_NAME = "jira.getIssuePriorityById";

    public static final Key<String>          ISSUE_PRIORITY_ID_INL  = new Key<>("issuePriorityIdInl");
    public static final Key<String>          ISSUE_PRIORITY_ID_EXPR = new Key<>("issuePriorityIdExpr");
    public static final Key<List<NameSpace>> ISSUE_PRIORITY_ID_NS   = new Key<>("issuePriorityIdNameSpace");

    private static final String ISSUE_PRIORITY_ID = "issuePriorityId";

    private static final List<String> PROPERTIES = Arrays.asList(ISSUE_PRIORITY_ID);

    @Inject
    public GetIssuePriorityById(EditorResources resources,
                                Provider<Branch> branchProvider,
                                ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              resources.jiraIcon(),
              branchProvider,
              elementCreatorsManager);

        putProperty(ISSUE_PRIORITY_ID_INL, "");
        putProperty(ISSUE_PRIORITY_ID_EXPR, "");
        putProperty(ISSUE_PRIORITY_ID_NS, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE));

        properties.put(ISSUE_PRIORITY_ID, isInline ? getProperty(ISSUE_PRIORITY_ID_INL) : getProperty(ISSUE_PRIORITY_ID_EXPR));

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        if (ISSUE_PRIORITY_ID.equals(nodeName)) {
            adaptProperty(nodeValue, ISSUE_PRIORITY_ID_INL, ISSUE_PRIORITY_ID_EXPR);
        }
    }
}
