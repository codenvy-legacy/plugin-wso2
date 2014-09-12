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
package com.codenvy.ide.client.elements.connectors.googlespreadsheet;

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
 * The Class describes UsernameLogin connector for GoogleSpreadsheet group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 */
public class UsernameLogin extends AbstractConnector {

    public static final String ELEMENT_NAME       = "UsernameLogin";
    public static final String SERIALIZATION_NAME = "googlespreadsheet.usernameLogin";

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final List<String> PROPERTIES = Arrays.asList(USERNAME, PASSWORD);

    private String username;
    private String password;

    private String usernameExpression;
    private String passwordExpression;

    private Array<NameSpace> usernameNS;
    private Array<NameSpace> passwordNS;

    @Inject
    public UsernameLogin(EditorResources resources,
                         Provider<Branch> branchProvider,
                         MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        username = "";
        password = "";

        usernameExpression = "";
        passwordExpression = "";

        usernameNS = createArray();
        passwordNS = createArray();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(USERNAME, isInline ? username : usernameExpression);
        properties.put(PASSWORD, isInline ? password : passwordExpression);

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
                    username = nodeValue;
                } else {
                    usernameExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case PASSWORD:
                if (isInline) {
                    password = nodeValue;
                } else {
                    passwordExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;
        }
    }

    @Nonnull
    public String getUsername() {
        return username;
    }

    public void setUsername(@Nonnull String username) {
        this.username = username;
    }

    @Nonnull
    public String getPassword() {
        return password;
    }

    public void setPassword(@Nonnull String password) {
        this.password = password;
    }

    @Nonnull
    public String getUsernameExpression() {
        return usernameExpression;
    }

    public void setUsernameExpression(@Nonnull String usernameExpression) {
        this.usernameExpression = usernameExpression;
    }

    @Nonnull
    public String getPasswordExpression() {
        return passwordExpression;
    }

    public void setPasswordExpression(@Nonnull String passwordExpression) {
        this.passwordExpression = passwordExpression;
    }

    @Nonnull
    public Array<NameSpace> getUsernameNS() {
        return usernameNS;
    }

    public void setUsernameNS(@Nonnull Array<NameSpace> usernameNS) {
        this.usernameNS = usernameNS;
    }

    @Nonnull
    public Array<NameSpace> getPasswordNS() {
        return passwordNS;
    }

    public void setPasswordNS(@Nonnull Array<NameSpace> passwordNS) {
        this.passwordNS = passwordNS;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.googleSpreadsheetElement();
    }
}