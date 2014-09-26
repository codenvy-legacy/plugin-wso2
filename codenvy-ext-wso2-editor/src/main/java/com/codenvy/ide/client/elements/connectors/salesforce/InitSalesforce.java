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
package com.codenvy.ide.client.elements.connectors.salesforce;

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
 * The Class describes Init connector for Salesforce group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class InitSalesforce extends AbstractConnector {

    public static final String ELEMENT_NAME       = "Init";
    public static final String SERIALIZATION_NAME = "salesforce.init";

    public static final Key<String> USERNAME_KEY    = new Key<>("Username");
    public static final Key<String> PASSWORD_KEY    = new Key<>("Password");
    public static final Key<String> LOGIN_URL_KEY   = new Key<>("LoginUrl");
    public static final Key<String> FORCE_LOGIN_KEY = new Key<>("ForceLogin");

    public static final Key<String> USERNAME_EXPRESSION_KEY    = new Key<>("UsernameExpression");
    public static final Key<String> PASSWORD_EXPRESSION_KEY    = new Key<>("PasswordExpression");
    public static final Key<String> LOGIN_URL_EXPRESSION_KEY   = new Key<>("LoginUrlExpression");
    public static final Key<String> FORCE_LOGIN_EXPRESSION_KEY = new Key<>("ForceLoginExpression");

    public static final Key<List<NameSpace>> USERNAME_NS_KEY    = new Key<>("UsernameNS");
    public static final Key<List<NameSpace>> PASSWORD_NS_KEY    = new Key<>("PasswordNS");
    public static final Key<List<NameSpace>> LOGIN_URL_NS_KEY   = new Key<>("LoginUrlNS");
    public static final Key<List<NameSpace>> FORCE_LOGIN_NS_KEY = new Key<>("ForceLoginNS");

    private static final String USERNAME    = "username";
    private static final String PASSWORD    = "password";
    private static final String LOGIN_URL   = "loginUrl";
    private static final String FORCE_LOGIN = "forceLogin";

    private static final List<String> PROPERTIES = Arrays.asList(USERNAME, PASSWORD, LOGIN_URL, FORCE_LOGIN);

    @Inject
    public InitSalesforce(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.salesforce(),
              branchProvider,
              elementCreatorsManager);

        putProperty(USERNAME_KEY, "");
        putProperty(PASSWORD_KEY, "");
        putProperty(LOGIN_URL_KEY, "");
        putProperty(FORCE_LOGIN_KEY, "");

        putProperty(USERNAME_EXPRESSION_KEY, "");
        putProperty(PASSWORD_EXPRESSION_KEY, "");
        putProperty(LOGIN_URL_EXPRESSION_KEY, "");
        putProperty(FORCE_LOGIN_EXPRESSION_KEY, "");

        putProperty(USERNAME_NS_KEY, new ArrayList<NameSpace>());
        putProperty(PASSWORD_NS_KEY, new ArrayList<NameSpace>());
        putProperty(LOGIN_URL_NS_KEY, new ArrayList<NameSpace>());
        putProperty(FORCE_LOGIN_NS_KEY, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE));

        properties.put(USERNAME, isInline ? getProperty(USERNAME_KEY) : getProperty(USERNAME_EXPRESSION_KEY));
        properties.put(PASSWORD, isInline ? getProperty(PASSWORD_KEY) : getProperty(PASSWORD_EXPRESSION_KEY));
        properties.put(LOGIN_URL, isInline ? getProperty(LOGIN_URL_KEY) : getProperty(LOGIN_URL_EXPRESSION_KEY));
        properties.put(FORCE_LOGIN, isInline ? getProperty(FORCE_LOGIN_KEY) : getProperty(FORCE_LOGIN_EXPRESSION_KEY));

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        switch (nodeName) {
            case USERNAME:
                adaptProperty(nodeValue, USERNAME_KEY, USERNAME_EXPRESSION_KEY);
                break;

            case PASSWORD:
                adaptProperty(nodeValue, PASSWORD_KEY, PASSWORD_EXPRESSION_KEY);
                break;

            case LOGIN_URL:
                adaptProperty(nodeValue, LOGIN_URL_KEY, LOGIN_URL_EXPRESSION_KEY);
                break;

            case FORCE_LOGIN:
                adaptProperty(nodeValue, FORCE_LOGIN_KEY, FORCE_LOGIN_EXPRESSION_KEY);
                break;

            default:
        }
    }

}
