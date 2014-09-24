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
 * The Class describes GetGroup connector for jira group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Vaelriy Svydenko
 */
public class GetGroup extends AbstractConnector {

    public static final String ELEMENT_NAME       = "GetGroup";
    public static final String SERIALIZATION_NAME = "jira.getGroup";

    private static final String GROUP_NAME = "groupName";

    private static final List<String> PROPERTIES = Arrays.asList(GROUP_NAME);

    private String          groupName;
    private String          groupNameExpression;
    private List<NameSpace> groupNameNS;

    @Inject
    public GetGroup(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.jiraIcon(),
              branchProvider,
              elementCreatorsManager);

        groupName = "";
        groupNameExpression = "";

        groupNameNS = new ArrayList<>();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(GROUP_NAME, isInline ? groupName : groupNameExpression);

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case GROUP_NAME:
                if (isInline) {
                    groupName = nodeValue;
                } else {
                    groupNameExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

        }
    }

    @Nonnull
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(@Nonnull String groupName) {
        this.groupName = groupName;
    }

    @Nonnull
    public String getGroupNameExpression() {
        return groupNameExpression;
    }

    public void setGroupNameExpression(@Nonnull String groupNameExpression) {
        this.groupNameExpression = groupNameExpression;
    }

    @Nonnull
    public List<NameSpace> getGroupNameNS() {
        return groupNameNS;
    }

    public void setGroupNameNS(@Nonnull List<NameSpace> groupNameNS) {
        this.groupNameNS = groupNameNS;
    }

}
