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
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.Inline;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NamespacedPropertyEditor;

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

    private static final String USERNAME    = "username";
    private static final String PASSWORD    = "password";
    private static final String LOGIN_URL   = "loginUrl";
    private static final String FORCE_LOGIN = "forceLogin";

    private static final List<String> PROPERTIES = Arrays.asList(USERNAME, PASSWORD, LOGIN_URL, FORCE_LOGIN);

    private String username;
    private String password;
    private String loginUrl;
    private String forceLogin;

    private String usernameInline;
    private String passwordInline;
    private String loginUrlInline;
    private String forceLoginInline;

    private List<NameSpace> passwordNameSpaces;
    private List<NameSpace> loginUrlNameSpaces;
    private List<NameSpace> forceLoginNameSpaces;
    private List<NameSpace> usernameNameSpaces;

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

        username = "";
        password = "";
        loginUrl = "";
        forceLogin = "";

        usernameInline = "";
        passwordInline = "";
        forceLoginInline = "";
        loginUrlInline = "";

        usernameNameSpaces = new ArrayList<>();
        passwordNameSpaces = new ArrayList<>();
        forceLoginNameSpaces = new ArrayList<>();
        loginUrlNameSpaces = new ArrayList<>();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(USERNAME, isInline ? usernameInline : username);
        properties.put(PASSWORD, isInline ? passwordInline : password);
        properties.put(LOGIN_URL, isInline ? loginUrlInline : loginUrl);
        properties.put(FORCE_LOGIN, isInline ? forceLoginInline : forceLogin);

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case USERNAME:
                if (isInline) {
                    usernameInline = nodeValue;
                } else {
                    username = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case PASSWORD:
                if (isInline) {
                    passwordInline = nodeValue;
                } else {
                    password = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case LOGIN_URL:
                if (isInline) {
                    loginUrlInline = nodeValue;
                } else {
                    loginUrl = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case FORCE_LOGIN:
                if (isInline) {
                    forceLoginInline = nodeValue;
                } else {
                    forceLogin = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;
        }
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
    public List<NameSpace> getUsernameNameSpaces() {
        return usernameNameSpaces;
    }

    public void setUsernameNameSpaces(@Nonnull List<NameSpace> usernameNameSpaces) {
        this.usernameNameSpaces = usernameNameSpaces;
    }

    @Nonnull
    public List<NameSpace> getPasswordNameSpaces() {
        return passwordNameSpaces;
    }

    public void setPasswordNameSpaces(@Nonnull List<NameSpace> passwordNameSpaces) {
        this.passwordNameSpaces = passwordNameSpaces;
    }

    @Nonnull
    public List<NameSpace> getLoginUrlNameSpaces() {
        return loginUrlNameSpaces;
    }

    public void setLoginUrlNameSpaces(@Nonnull List<NameSpace> loginUrlNameSpaces) {
        this.loginUrlNameSpaces = loginUrlNameSpaces;
    }

    @Nonnull
    public List<NameSpace> getForceLoginNameSpaces() {
        return forceLoginNameSpaces;
    }

    public void setForceLoginNameSpaces(@Nonnull List<NameSpace> forceLoginNameSpaces) {
        this.forceLoginNameSpaces = forceLoginNameSpaces;
    }
}
