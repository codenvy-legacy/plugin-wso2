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
package com.codenvy.ide.client.elements.connectors.twitter;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.connectors.AbstractConnector;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.Inline;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NamespacedPropertyEditor;
import static java.util.Collections.emptyList;

/**
 * The Class describes SearchPlaces connector for twitter group of connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class SearchPlaces extends AbstractConnector {

    public static final String ELEMENT_NAME       = "SearchPlaces";
    public static final String SERIALIZATION_NAME = "twitter.searchPlaces";

    private static final String CONSUMER_KEY        = "consumerKey";
    private static final String CONSUMER_SECRET     = "consumerSecret";
    private static final String ACCESS_TOKEN        = "accessToken";
    private static final String ACCESS_TOKEN_SECRET = "accessTokenSecret";
    private static final String LATITUDE            = "latitude";
    private static final String LONGITUDE           = "longitude";
    private static final String QUERY               = "query";

    private static final List<String> PROPERTIES = Arrays.asList(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, ACCESS_TOKEN_SECRET);

    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessTokenSecret;
    private String latitude;
    private String longitude;
    private String query;

    private String consumerKeyExpr;
    private String consumerSecretExpr;
    private String accessTokenExpr;
    private String accessTokenSecretExpr;
    private String latitudeExpr;
    private String longitudeExpr;
    private String queryExpr;

    private List<NameSpace> consumerKeyNS;
    private List<NameSpace> consumerSecretNS;
    private List<NameSpace> accessTokenNS;
    private List<NameSpace> accessTokenSecretNS;
    private List<NameSpace> latitudeNS;
    private List<NameSpace> longitudeNS;
    private List<NameSpace> queryNS;

    @Inject
    public SearchPlaces(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.twitterElement(),
              branchProvider,
              elementCreatorsManager);

        consumerKey = "";
        consumerSecret = "";
        accessToken = "";
        accessTokenSecret = "";
        latitude = "";
        longitude = "";
        query = "";

        consumerKeyExpr = "";
        accessTokenExpr = "";
        accessTokenSecretExpr = "";
        consumerSecretExpr = "";
        latitudeExpr = "";
        longitudeExpr = "";
        queryExpr = "";

        consumerKeyNS = emptyList();
        consumerSecretNS = emptyList();
        accessTokenNS = emptyList();
        accessTokenSecretNS = emptyList();
        latitudeNS = emptyList();
        longitudeNS = emptyList();
        queryNS = emptyList();

    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(CONSUMER_KEY, isInline ? consumerKey : consumerKeyExpr);
        properties.put(CONSUMER_SECRET, isInline ? consumerSecret : consumerSecretExpr);
        properties.put(ACCESS_TOKEN, isInline ? accessToken : accessTokenExpr);
        properties.put(ACCESS_TOKEN_SECRET, isInline ? accessTokenSecret : accessTokenSecretExpr);
        properties.put(LATITUDE, isInline ? latitude : latitudeExpr);
        properties.put(LONGITUDE, isInline ? longitude : longitudeExpr);
        properties.put(QUERY, isInline ? query : queryExpr);

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case CONSUMER_KEY:
                if (isInline) {
                    consumerKey = nodeValue;
                } else {
                    consumerKeyExpr = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case CONSUMER_SECRET:
                if (isInline) {
                    consumerSecret = nodeValue;
                } else {
                    consumerSecretExpr = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case ACCESS_TOKEN:
                if (isInline) {
                    accessToken = nodeValue;
                } else {
                    accessTokenExpr = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case ACCESS_TOKEN_SECRET:
                if (isInline) {
                    accessTokenSecret = nodeValue;
                } else {
                    accessTokenSecretExpr = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case LATITUDE:
                if (isInline) {
                    latitude = nodeValue;
                } else {
                    latitudeExpr = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case LONGITUDE:
                if (isInline) {
                    longitude = nodeValue;
                } else {
                    longitudeExpr = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case QUERY:
                if (isInline) {
                    query = nodeValue;
                } else {
                    queryExpr = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;
        }
    }

    @Nonnull
    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(@Nonnull String consumerKey) {
        this.consumerKey = consumerKey;
    }

    @Nonnull
    public String getConsumerSecret() {
        return consumerSecret;
    }

    public void setConsumerSecret(@Nonnull String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    @Nonnull
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(@Nonnull String accessToken) {
        this.accessToken = accessToken;
    }

    @Nonnull
    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    public void setAccessTokenSecret(@Nonnull String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
    }

    @Nonnull
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(@Nonnull String latitude) {
        this.latitude = latitude;
    }

    @Nonnull
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(@Nonnull String longitude) {
        this.longitude = longitude;
    }

    @Nonnull
    public String getQuery() {
        return query;
    }

    public void setQuery(@Nonnull String query) {
        this.query = query;
    }

    @Nonnull
    public String getConsumerKeyExpr() {
        return consumerKeyExpr;
    }

    public void setConsumerKeyExpr(@Nonnull String consumerKeyExpr) {
        this.consumerKeyExpr = consumerKeyExpr;
    }

    @Nonnull
    public String getConsumerSecretExpr() {
        return consumerSecretExpr;
    }

    public void setConsumerSecretExpr(@Nonnull String consumerSecretExpr) {
        this.consumerSecretExpr = consumerSecretExpr;
    }

    @Nonnull
    public String getAccessTokenExpr() {
        return accessTokenExpr;
    }

    public void setAccessTokenExpr(@Nonnull String accessTokenExpr) {
        this.accessTokenExpr = accessTokenExpr;
    }

    @Nonnull
    public String getAccessTokenSecretExpr() {
        return accessTokenSecretExpr;
    }

    public void setAccessTokenSecretExpr(@Nonnull String accessTokenSecretExpr) {
        this.accessTokenSecretExpr = accessTokenSecretExpr;
    }

    @Nonnull
    public String getLatitudeExpr() {
        return latitudeExpr;
    }

    public void setLatitudeExpr(@Nonnull String latitudeExpr) {
        this.latitudeExpr = latitudeExpr;
    }

    @Nonnull
    public String getLongitudeExpr() {
        return longitudeExpr;
    }

    public void setLongitudeExpr(@Nonnull String longitudeExpr) {
        this.longitudeExpr = longitudeExpr;
    }

    @Nonnull
    public String getQueryExpr() {
        return queryExpr;
    }

    public void setQueryExpr(@Nonnull String queryExpr) {
        this.queryExpr = queryExpr;
    }

    @Nonnull
    public List<NameSpace> getConsumerKeyNS() {
        return consumerKeyNS;
    }

    public void setConsumerKeyNS(@Nonnull List<NameSpace> consumerKeyNS) {
        this.consumerKeyNS = consumerKeyNS;
    }

    @Nonnull
    public List<NameSpace> getConsumerSecretNS() {
        return consumerSecretNS;
    }

    public void setConsumerSecretNS(@Nonnull List<NameSpace> consumerSecretNS) {
        this.consumerSecretNS = consumerSecretNS;
    }

    @Nonnull
    public List<NameSpace> getAccessTokenNS() {
        return accessTokenNS;
    }

    public void setAccessTokenNS(@Nonnull List<NameSpace> accessTokenNS) {
        this.accessTokenNS = accessTokenNS;
    }

    @Nonnull
    public List<NameSpace> getAccessTokenSecretNS() {
        return accessTokenSecretNS;
    }

    public void setAccessTokenSecretNS(@Nonnull List<NameSpace> accessTokenSecretNS) {
        this.accessTokenSecretNS = accessTokenSecretNS;
    }

    @Nonnull
    public List<NameSpace> getLatitudeNS() {
        return latitudeNS;
    }

    public void setLatitudeNS(@Nonnull List<NameSpace> latitudeNS) {
        this.latitudeNS = latitudeNS;
    }

    @Nonnull
    public List<NameSpace> getLongitudeNS() {
        return longitudeNS;
    }

    public void setLongitudeNS(@Nonnull List<NameSpace> longitudeNS) {
        this.longitudeNS = longitudeNS;
    }

    @Nonnull
    public List<NameSpace> getQueryNS() {
        return queryNS;
    }

    public void setQueryNS(@Nonnull List<NameSpace> queryNS) {
        this.queryNS = queryNS;
    }
}
