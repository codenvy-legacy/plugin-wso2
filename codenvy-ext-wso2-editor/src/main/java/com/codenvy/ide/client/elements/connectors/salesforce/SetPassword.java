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
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
import com.codenvy.ide.collections.Array;
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
import static com.codenvy.ide.collections.Collections.createArray;

/**
 * The Class describes SetPassword connector for Salesforce group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class SetPassword extends AbstractConnector {

    public static final String ELEMENT_NAME       = "SetPassword";
    public static final String SERIALIZATION_NAME = "salesforce.setPassword";

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final List<String> PROPERTIES = Arrays.asList(USERNAME, PASSWORD);

    private String           username;
    private String           password;
    private String           usernameInline;
    private String           passwordInline;
    private Array<NameSpace> passwordNameSpaces;
    private Array<NameSpace> usernameNameSpaces;

    @Inject
    public SetPassword(EditorResources resources, Provider<Branch> branchProvider, MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        username = "";
        password = "";
        usernameInline = "";
        passwordInline = "";

        usernameNameSpaces = createArray();
        passwordNameSpaces = createArray();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(USERNAME, isInline ? usernameInline : username);
        properties.put(PASSWORD, isInline ? passwordInline : password);

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
                }
                break;

            case PASSWORD:
                if (isInline) {
                    passwordInline = nodeValue;
                } else {
                    password = nodeValue;
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
}
