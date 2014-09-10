/*
 * Copyright 2014 Codenvy, S.A.
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
 * The Class describes Init connector for jira group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 */
public class InitJira extends AbstractConnector {

    public static final String ELEMENT_NAME       = "Init";
    public static final String SERIALIZATION_NAME = "jira.init";

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String URI      = "uri";

    private static final List<String> PROPERTIES = Arrays.asList(USERNAME, PASSWORD, URI);

    private String userName;
    private String password;
    private String uri;

    private String userNameExpression;
    private String passwordExpression;
    private String uriExpression;

    private Array<NameSpace> userNameNS;
    private Array<NameSpace> passwordNS;
    private Array<NameSpace> uriNS;

    @Inject
    public InitJira(EditorResources resources, Provider<Branch> branchProvider, MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        password = "";
        userName = "";
        uri = "";

        passwordExpression = "";
        userNameExpression = "";
        uriExpression = "";

        uriNS = createArray();
        passwordNS = createArray();
        userNameNS = createArray();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(USERNAME, isInline ? userName : userNameExpression);
        properties.put(PASSWORD, isInline ? password : passwordExpression);
        properties.put(URI, isInline ? uri : uriExpression);

        return convertPropertiesToXMLFormat(properties);
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
                    password = nodeValue;
                } else {
                    passwordExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case PASSWORD:
                if (isInline) {
                    userName = nodeValue;
                } else {
                    userNameExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case URI:
                if (isInline) {
                    uri = nodeValue;
                } else {
                    uriExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;
        }
    }

    @Nonnull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@Nonnull String userName) {
        this.userName = userName;
    }

    @Nonnull
    public String getPassword() {
        return password;
    }

    public void setPassword(@Nonnull String password) {
        this.password = password;
    }

    @Nonnull
    public String getUri() {
        return uri;
    }

    public void setUri(@Nonnull String uri) {
        this.uri = uri;
    }

    @Nonnull
    public String getUserNameExpression() {
        return userNameExpression;
    }

    public void setUserNameExpression(@Nonnull String userNameExpression) {
        this.userNameExpression = userNameExpression;
    }

    @Nonnull
    public String getPasswordExpression() {
        return passwordExpression;
    }

    public void setPasswordExpression(@Nonnull String passwordExpression) {
        this.passwordExpression = passwordExpression;
    }

    @Nonnull
    public String getUriExpression() {
        return uriExpression;
    }

    public void setUriExpression(@Nonnull String uriExpression) {
        this.uriExpression = uriExpression;
    }

    @Nonnull
    public Array<NameSpace> getUserNameNS() {
        return userNameNS;
    }

    public void setUserNameNS(@Nonnull Array<NameSpace> userNameNS) {
        this.userNameNS = userNameNS;
    }

    @Nonnull
    public Array<NameSpace> getPasswordNS() {
        return passwordNS;
    }

    public void setPasswordNS(@Nonnull Array<NameSpace> passwordNS) {
        this.passwordNS = passwordNS;
    }

    @Nonnull
    public Array<NameSpace> getUriNS() {
        return uriNS;
    }

    public void setUriNS(@Nonnull Array<NameSpace> uriNS) {
        this.uriNS = uriNS;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.jiraIcon();
    }
}
