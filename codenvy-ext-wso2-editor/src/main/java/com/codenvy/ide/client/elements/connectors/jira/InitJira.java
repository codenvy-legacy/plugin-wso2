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
 * The Class describes Init connector for jira group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class InitJira extends AbstractConnector {

    public static final String ELEMENT_NAME       = "Init";
    public static final String SERIALIZATION_NAME = "jira.init";

    public static final Key<String> USER_NAME_INL = new Key<>("userNameInl");
    public static final Key<String> PASSWORD_INL  = new Key<>("passwordInl");
    public static final Key<String> URI_INL       = new Key<>("uriInl");

    public static final Key<String> USER_NAME_EXPR = new Key<>("userNameExpr");
    public static final Key<String> PASSWORD_EXPR  = new Key<>("passwordExpr");
    public static final Key<String> URI_EXPR       = new Key<>("uriExpr");

    public static final Key<List<NameSpace>> USER_NAME_NS = new Key<>("userNameNameSpace");
    public static final Key<List<NameSpace>> PASSWORD_NS  = new Key<>("passwordNameSpace");
    public static final Key<List<NameSpace>> URI_NS       = new Key<>("uriNameSpace");

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String URI      = "uri";

    private static final List<String> PROPERTIES = Arrays.asList(USERNAME, PASSWORD, URI);

    @Inject
    public InitJira(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              resources.jiraIcon(),
              branchProvider,
              elementCreatorsManager);

        putProperty(USER_NAME_INL, "");
        putProperty(PASSWORD_INL, "");
        putProperty(URI_INL, "");


        putProperty(USER_NAME_EXPR, "");
        putProperty(PASSWORD_EXPR, "");
        putProperty(URI_EXPR, "");

        putProperty(USER_NAME_NS, new ArrayList<NameSpace>());
        putProperty(PASSWORD_NS, new ArrayList<NameSpace>());
        putProperty(URI_NS, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE));

        properties.put(USERNAME, isInline ? getProperty(USER_NAME_INL) : getProperty(USER_NAME_EXPR));
        properties.put(PASSWORD, isInline ? getProperty(PASSWORD_INL) : getProperty(PASSWORD_EXPR));
        properties.put(URI, isInline ? getProperty(URI_INL) : getProperty(URI_EXPR));

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        switch (node.getNodeName()) {
            case USERNAME:
                adaptProperty(nodeValue, USER_NAME_INL, USER_NAME_EXPR);
                break;

            case PASSWORD:
                adaptProperty(nodeValue, PASSWORD_INL, PASSWORD_EXPR);
                break;

            case URI:
                adaptProperty(nodeValue, URI_INL, URI_EXPR);
                break;

            default:
        }
    }
}
