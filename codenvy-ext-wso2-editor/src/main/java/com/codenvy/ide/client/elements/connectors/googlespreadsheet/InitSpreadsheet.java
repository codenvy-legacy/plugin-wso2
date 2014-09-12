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
 * The Class describes InitSpreadsheet connector for GoogleSpreadsheet group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 */
public class InitSpreadsheet extends AbstractConnector {

    public static final String ELEMENT_NAME       = "Init";
    public static final String SERIALIZATION_NAME = "googlespreadsheet.init";

    private static final String OAUTH_CONSUMER_KEY        = "oauthConsumerKey";
    private static final String OAUTH_CONSUMER_SECRET     = "oauthConsumerSecret";
    private static final String OAUTH_ACCESS_TOKEN        = "oauthAccessToken";
    private static final String OAUTH_ACCESS_TOKEN_SECRET = "oauthAccessTokenSecret";

    private static final List<String> PROPERTIES = Arrays.asList(OAUTH_CONSUMER_KEY,
                                                                 OAUTH_CONSUMER_SECRET,
                                                                 OAUTH_ACCESS_TOKEN,
                                                                 OAUTH_ACCESS_TOKEN_SECRET);

    private String oauthConsumerKey;
    private String oauthConsumerSecret;
    private String oauthAccessToken;
    private String oauthAccessTokenSecret;

    private String oauthConsumerKeyExpression;
    private String oauthConsumerSecretExpression;
    private String oauthAccessTokenExpression;
    private String oauthAccessTokenSecretExpression;

    private Array<NameSpace> oauthConsumerKeyNS;
    private Array<NameSpace> oauthConsumerSecretNS;
    private Array<NameSpace> oauthAccessTokenNS;
    private Array<NameSpace> oauthAccessTokenSecretNS;

    @Inject
    public InitSpreadsheet(EditorResources resources,
                           Provider<Branch> branchProvider,
                           MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        oauthConsumerKey = "";
        oauthConsumerSecret = "";
        oauthAccessToken = "";
        oauthAccessTokenSecret = "";

        oauthConsumerKeyExpression = "";
        oauthConsumerSecretExpression = "";
        oauthAccessTokenExpression = "";
        oauthAccessTokenSecretExpression = "";

        oauthConsumerKeyNS = createArray();
        oauthConsumerSecretNS = createArray();
        oauthAccessTokenNS = createArray();
        oauthAccessTokenSecretNS = createArray();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(OAUTH_CONSUMER_KEY, isInline ? oauthConsumerKey : oauthConsumerKeyExpression);
        properties.put(OAUTH_CONSUMER_SECRET, isInline ? oauthConsumerSecret : oauthConsumerSecretExpression);
        properties.put(OAUTH_ACCESS_TOKEN, isInline ? oauthAccessToken : oauthAccessTokenExpression);
        properties.put(OAUTH_ACCESS_TOKEN_SECRET, isInline ? oauthAccessTokenSecret : oauthAccessTokenSecretExpression);

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();
        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case OAUTH_CONSUMER_KEY:
                if (isInline) {
                    oauthConsumerKey = nodeValue;
                } else {
                    oauthConsumerKeyExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case OAUTH_CONSUMER_SECRET:
                if (isInline) {
                    oauthConsumerSecret = nodeValue;
                } else {
                    oauthConsumerSecretExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case OAUTH_ACCESS_TOKEN:
                if (isInline) {
                    oauthAccessToken = nodeValue;
                } else {
                    oauthAccessTokenExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case OAUTH_ACCESS_TOKEN_SECRET:
                if (isInline) {
                    oauthAccessTokenSecret = nodeValue;
                } else {
                    oauthAccessTokenSecretExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;
        }
    }

    @Nonnull
    public String getOauthConsumerKey() {
        return oauthConsumerKey;
    }

    public void setOauthConsumerKey(@Nonnull String oauthConsumerKey) {
        this.oauthConsumerKey = oauthConsumerKey;
    }

    @Nonnull
    public String getOauthConsumerSecret() {
        return oauthConsumerSecret;
    }

    public void setOauthConsumerSecret(@Nonnull String oauthConsumerSecret) {
        this.oauthConsumerSecret = oauthConsumerSecret;
    }

    @Nonnull
    public String getOauthAccessToken() {
        return oauthAccessToken;
    }

    public void setOauthAccessToken(@Nonnull String oauthAccessToken) {
        this.oauthAccessToken = oauthAccessToken;
    }

    @Nonnull
    public String getOauthAccessTokenSecret() {
        return oauthAccessTokenSecret;
    }

    public void setOauthAccessTokenSecret(@Nonnull String oauthAccessTokenSecret) {
        this.oauthAccessTokenSecret = oauthAccessTokenSecret;
    }

    @Nonnull
    public String getOauthConsumerKeyExpression() {
        return oauthConsumerKeyExpression;
    }

    public void setOauthConsumerKeyExpression(@Nonnull String oauthConsumerKeyExpression) {
        this.oauthConsumerKeyExpression = oauthConsumerKeyExpression;
    }

    @Nonnull
    public String getOauthConsumerSecretExpression() {
        return oauthConsumerSecretExpression;
    }

    public void setOauthConsumerSecretExpression(@Nonnull String oauthConsumerSecretExpression) {
        this.oauthConsumerSecretExpression = oauthConsumerSecretExpression;
    }

    @Nonnull
    public String getOauthAccessTokenExpression() {
        return oauthAccessTokenExpression;
    }

    public void setOauthAccessTokenExpression(@Nonnull String oauthAccessTokenExpression) {
        this.oauthAccessTokenExpression = oauthAccessTokenExpression;
    }

    @Nonnull
    public String getOauthAccessTokenSecretExpression() {
        return oauthAccessTokenSecretExpression;
    }

    public void setOauthAccessTokenSecretExpression(@Nonnull String oauthAccessTokenSecretExpression) {
        this.oauthAccessTokenSecretExpression = oauthAccessTokenSecretExpression;
    }

    @Nonnull
    public Array<NameSpace> getOauthConsumerKeyNS() {
        return oauthConsumerKeyNS;
    }

    public void setOauthConsumerKeyNS(@Nonnull Array<NameSpace> oauthConsumerKeyNS) {
        this.oauthConsumerKeyNS = oauthConsumerKeyNS;
    }

    @Nonnull
    public Array<NameSpace> getOauthConsumerSecretNS() {
        return oauthConsumerSecretNS;
    }

    public void setOauthConsumerSecretNS(@Nonnull Array<NameSpace> oauthConsumerSecretNS) {
        this.oauthConsumerSecretNS = oauthConsumerSecretNS;
    }

    @Nonnull
    public Array<NameSpace> getOauthAccessTokenNS() {
        return oauthAccessTokenNS;
    }

    public void setOauthAccessTokenNS(@Nonnull Array<NameSpace> oauthAccessTokenNS) {
        this.oauthAccessTokenNS = oauthAccessTokenNS;
    }

    @Nonnull
    public Array<NameSpace> getOauthAccessTokenSecretNS() {
        return oauthAccessTokenSecretNS;
    }

    public void setOauthAccessTokenSecretNS(@Nonnull Array<NameSpace> oauthAccessTokenSecretNS) {
        this.oauthAccessTokenSecretNS = oauthAccessTokenSecretNS;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.googleSpreadsheetElement();
    }
}