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
 * The Class describes DeleteAvatarForProject connector for jira group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class DeleteAvatarForProject extends AbstractConnector {

    public static final String ELEMENT_NAME       = "DeleteAvatarForProject";
    public static final String SERIALIZATION_NAME = "jira.deleteAvatarForProject";

    public static final Key<String> PROJECT_KEY_INL = new Key<>("ProjectKeyInl");
    public static final Key<String> AVATAR_ID_INL   = new Key<>("avatarIdInl");

    public static final Key<String> PROJECT_KEY_EXPR = new Key<>("projectKeyExpr");
    public static final Key<String> AVATAR_ID_EXPR   = new Key<>("avatarIdExpr");

    public static final Key<List<NameSpace>> PROJECT_KEY_NS = new Key<>("projectKeyNameSpace");
    public static final Key<List<NameSpace>> AVATAR_ID_NS   = new Key<>("avatarIdNameSpace");

    private static final String PROJECT_KEY = "projectKey";
    private static final String AVATAR_ID   = "avatarId";

    private static final List<String> PROPERTIES = Arrays.asList(PROJECT_KEY, AVATAR_ID);

    @Inject
    public DeleteAvatarForProject(EditorResources resources,
                                  Provider<Branch> branchProvider,
                                  ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              resources.jiraIcon(),
              branchProvider,
              elementCreatorsManager);

        putProperty(PROJECT_KEY_INL, "");
        putProperty(AVATAR_ID_INL, "");

        putProperty(PROJECT_KEY_EXPR, "");
        putProperty(AVATAR_ID_EXPR, "");

        putProperty(PROJECT_KEY_NS, new ArrayList<NameSpace>());
        putProperty(AVATAR_ID_NS, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE));

        properties.put(PROJECT_KEY, isInline ? getProperty(PROJECT_KEY_INL) : getProperty(PROJECT_KEY_EXPR));
        properties.put(AVATAR_ID, isInline ? getProperty(AVATAR_ID_INL) : getProperty(AVATAR_ID_EXPR));

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        if (PROJECT_KEY.equals(nodeName)) {
            adaptProperty(nodeValue, PROJECT_KEY_INL, PROJECT_KEY_EXPR);
        }

        if (AVATAR_ID.equals(nodeName)) {
            adaptProperty(nodeValue, AVATAR_ID_INL, AVATAR_ID_EXPR);
        }
    }
}