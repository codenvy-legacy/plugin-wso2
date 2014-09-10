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
 * The Class describes DeleteAvatarForProject connector for jira group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 */
public class DeleteAvatarForProject extends AbstractConnector {

    public static final  String ELEMENT_NAME       = "DeleteAvatarForProject";
    public static final  String SERIALIZATION_NAME = "jira.deleteAvatarForProject";

    private static final String PROJECT_KEY        = "projectKey";
    private static final String AVATAR_ID          = "avatarId";

    private static final List<String> PROPERTIES = Arrays.asList(PROJECT_KEY, AVATAR_ID);

    private String projectKey;
    private String avatarId;

    private String projectKeyExpression;
    private String avatarIdExpression;

    private Array<NameSpace> projectKeyNS;
    private Array<NameSpace> avatarIdNS;

    @Inject
    public DeleteAvatarForProject(EditorResources resources,
                                  Provider<Branch> branchProvider,
                                  MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        projectKey = "";
        avatarId = "";

        projectKeyExpression = "";
        avatarIdExpression = "";

        projectKeyNS = createArray();
        avatarIdNS = createArray();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(PROJECT_KEY, isInline ? projectKey : projectKeyExpression);
        properties.put(AVATAR_ID, isInline ? avatarId : avatarIdExpression);

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();
        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case PROJECT_KEY:
                if (isInline) {
                    projectKey = nodeValue;
                } else {
                    projectKeyExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case AVATAR_ID:
                if (isInline) {
                    avatarId = nodeValue;
                } else {
                    avatarIdExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;
        }
    }

    @Nonnull
    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(@Nonnull String projectKey) {
        this.projectKey = projectKey;
    }

    @Nonnull
    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(@Nonnull String avatarId) {
        this.avatarId = avatarId;
    }

    @Nonnull
    public String getProjectKeyExpression() {
        return projectKeyExpression;
    }

    public void setProjectKeyExpression(@Nonnull String projectKeyExpression) {
        this.projectKeyExpression = projectKeyExpression;
    }

    @Nonnull
    public String getAvatarIdExpression() {
        return avatarIdExpression;
    }

    public void setAvatarIdExpression(@Nonnull String avatarIdExpression) {
        this.avatarIdExpression = avatarIdExpression;
    }

    @Nonnull
    public Array<NameSpace> getProjectKeyNS() {
        return projectKeyNS;
    }

    public void setProjectKeyNS(@Nonnull Array<NameSpace> projectKeyNS) {
        this.projectKeyNS = projectKeyNS;
    }

    @Nonnull
    public Array<NameSpace> getAvatarIdNS() {
        return avatarIdNS;
    }

    public void setAvatarIdNS(@Nonnull Array<NameSpace> avatarIdNS) {
        this.avatarIdNS = avatarIdNS;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.jiraIcon();
    }

}