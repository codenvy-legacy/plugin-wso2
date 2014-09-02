/*
 * Copyright [2014] Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.client.elements.connectors.salesforce;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.AbstractShape;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
import com.codenvy.ide.collections.Array;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.elements.connectors.salesforce.GeneralProperty.ParameterEditorType;
import static com.codenvy.ide.client.elements.connectors.salesforce.GeneralProperty.ParameterEditorType.Inline;
import static com.codenvy.ide.collections.Collections.createArray;

/**
 * The Class describes Init connector for Salesforce group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 */
public class Init extends AbstractShape {

    public static final String ELEMENT_NAME       = "Init";
    public static final String SERIALIZATION_NAME = "salesforce.init";
    public static final String CONFIG_KEY         = "configKey";
    public static final String USERNAME           = "username";
    public static final String PASSWORD           = "password";
    public static final String LOGIN_URL          = "loginUrl";
    public static final String FORCE_LOGIN        = "forceLogin";

    private static final List<String> PROPERTIES = Arrays.asList(USERNAME, PASSWORD, LOGIN_URL, FORCE_LOGIN);

    private String              configRef;
    private String              username;
    private String              password;
    private String              loginUrl;
    private String              forceLogin;
    private String              usernameInline;
    private String              passwordInline;
    private String              loginUrlInline;
    private String              forceLoginInline;
    private ParameterEditorType parameterEditorType;
    private Array<NameSpace>    passwordNameSpaces;
    private Array<NameSpace>    loginUrlNameSpaces;
    private Array<NameSpace>    forceLoginNameSpaces;
    private Array<NameSpace>    usernameNameSpaces;

    @Inject
    public Init(EditorResources resources, Provider<Branch> branchProvider, MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        username = "";
        password = "";
        loginUrl = "";
        forceLogin = "";
        usernameInline = "";
        passwordInline = "";
        forceLoginInline = "";
        loginUrlInline = "";

        parameterEditorType = ParameterEditorType.Inline;

        usernameNameSpaces = createArray();
        passwordNameSpaces = createArray();
        forceLoginNameSpaces = createArray();
        loginUrlNameSpaces = createArray();
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        return configRef == null || configRef.isEmpty() ? "" : CONFIG_KEY + "=\"" + configRef + "\"";
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        return Inline.equals(parameterEditorType) ? convertPropertiesToXml(usernameInline, passwordInline, forceLoginInline, loginUrlInline)
                                                  : convertPropertiesToXml(username, password, forceLogin, loginUrl);
    }

    @Nonnull
    private String convertPropertiesToXml(@Nonnull String username,
                                     @Nonnull String password,
                                     @Nonnull String forceLogin,
                                     @Nonnull String loginUrl) {
        StringBuilder result = new StringBuilder();

        if (!username.isEmpty()) {
            result.append('<').append(USERNAME).append('>').append(username).append("</").append(USERNAME).append('>');
        }

        if (!password.isEmpty()) {
            result.append('<').append(PASSWORD).append('>').append(password).append("</").append(PASSWORD).append('>');
        }

        if (!loginUrl.isEmpty()) {
            result.append('<').append(LOGIN_URL).append('>').append(loginUrl).append("</").append(LOGIN_URL).append('>');
        }

        if (!forceLogin.isEmpty()) {
            result.append('<').append(FORCE_LOGIN).append('>').append(forceLogin).append("</").append(FORCE_LOGIN).append('>');
        }

        return result.toString();
    }

    /** {@inheritDoc} */
    @Override
    public void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();
        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case USERNAME:
                if (isInline) {
                    usernameInline = nodeValue;
                } else {
                    username = nodeValue;
                }
                break;

            case PASSWORD:
                if (isInline) {
                    passwordInline = nodeValue;
                } else {
                    password = nodeValue;
                }
                break;

            case LOGIN_URL:
                if (isInline) {
                    loginUrlInline = nodeValue;
                } else {
                    loginUrl = nodeValue;
                }
                break;

            case FORCE_LOGIN:
                if (isInline) {
                    forceLoginInline = nodeValue;
                } else {
                    forceLogin = nodeValue;
                }
                break;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void applyAttributes(@Nonnull Node node) {
        NamedNodeMap attributeMap = node.getAttributes();

        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attributeNode = attributeMap.item(i);

            String attributeValue = attributeNode.getNodeValue();

            switch (attributeNode.getNodeName()) {
                case CONFIG_KEY:
                    configRef = attributeValue;
                    break;
            }
        }
    }

    public void setParameterEditorType(@Nonnull ParameterEditorType parameterEditorType) {
        this.parameterEditorType = parameterEditorType;
    }

    @Nonnull
    public String getConfigRef() {
        return configRef;
    }

    public void setConfigRef(@Nonnull String configRef) {
        this.configRef = configRef;
    }

    @Nonnull
    public String getUsername() {
        return username;
    }

    public void setUsername(@Nullable String username) {
        this.username = username;
    }

    @Nonnull
    public String getPassword() {
        return password;
    }

    public void setPassword(@Nullable String password) {
        this.password = password;
    }

    @Nonnull
    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(@Nullable String loginUrl) {
        this.loginUrl = loginUrl;
    }

    @Nonnull
    public String getUsernameInline() {
        return usernameInline;
    }

    public void setUsernameInline(@Nonnull String usernameInline) {
        this.usernameInline = usernameInline;
    }

    @Nonnull
    public String getPasswordInline() {
        return passwordInline;
    }

    public void setPasswordInline(@Nonnull String passwordInline) {
        this.passwordInline = passwordInline;
    }

    @Nonnull
    public String getLoginUrlInline() {
        return loginUrlInline;
    }

    public void setLoginUrlInline(@Nonnull String loginUrlInline) {
        this.loginUrlInline = loginUrlInline;
    }

    @Nonnull
    public String getForceLoginInline() {
        return forceLoginInline;
    }

    public void setForceLoginInline(@Nonnull String forceLoginInline) {
        this.forceLoginInline = forceLoginInline;
    }

    @Nonnull
    public String getForceLogin() {
        return forceLogin;
    }

    public void setForceLogin(@Nonnull String forceLogin) {
        this.forceLogin = forceLogin;
    }

    @Nonnull
    public Array<NameSpace> getUsernameNameSpaces() {
        return usernameNameSpaces;
    }

    public void setUsernameNameSpaces(@Nonnull Array<NameSpace> usernameNameSpaces) {
        this.usernameNameSpaces = usernameNameSpaces;
    }

    @Nonnull
    public Array<NameSpace> getPasswordNameSpaces() {
        return passwordNameSpaces;
    }

    public void setPasswordNameSpaces(@Nonnull Array<NameSpace> passwordNameSpaces) {
        this.passwordNameSpaces = passwordNameSpaces;
    }

    @Nonnull
    public Array<NameSpace> getLoginUrlNameSpaces() {
        return loginUrlNameSpaces;
    }

    public void setLoginUrlNameSpaces(@Nonnull Array<NameSpace> loginUrlNameSpaces) {
        this.loginUrlNameSpaces = loginUrlNameSpaces;
    }

    @Nonnull
    public Array<NameSpace> getForceLoginNameSpaces() {
        return forceLoginNameSpaces;
    }

    public void setForceLoginNameSpaces(@Nonnull Array<NameSpace> forceLoginNameSpaces) {
        this.forceLoginNameSpaces = forceLoginNameSpaces;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.salesforce();
    }
}
