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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.Inline;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NamespacedPropertyEditor;

/**
 * The Class describes GetMentionsTimeLine connector for twitter group of connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class GetMentionsTimeLine extends AbstractConnector {

    public static final String ELEMENT_NAME       = "GetMentionsTimeLine";
    public static final String SERIALIZATION_NAME = "twitter.getMentionsTimeline";

    private static final String CONSUMER_KEY        = "consumerKey";
    private static final String CONSUMER_SECRET     = "consumerSecret";
    private static final String ACCESS_TOKEN        = "accessToken";
    private static final String ACCESS_TOKEN_SECRET = "accessTokenSecret";
    private static final String COUNT               = "count";
    private static final String PAGE                = "page";
    private static final String SINCE_ID            = "sinceId";
    private static final String MAX_ID              = "maxId";

    private static final List<String> PROPERTIES = Arrays.asList(CONSUMER_KEY,
                                                                 CONSUMER_SECRET,
                                                                 ACCESS_TOKEN,
                                                                 ACCESS_TOKEN_SECRET,
                                                                 COUNT,
                                                                 PAGE,
                                                                 SINCE_ID,
                                                                 MAX_ID);

    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessTokenSecret;
    private String count;
    private String page;
    private String sinceId;
    private String maxId;

    private String consumerKeyExpr;
    private String consumerSecretExpr;
    private String accessTokenExpr;
    private String accessTokenSecretExpr;
    private String countExpr;
    private String pageExpr;
    private String sinceIdExpr;
    private String maxIdExpr;

    private List<NameSpace> consumerKeyNS;
    private List<NameSpace> consumerSecretNS;
    private List<NameSpace> accessTokenNS;
    private List<NameSpace> accessTokenSecretNS;
    private List<NameSpace> countNS;
    private List<NameSpace> pageNS;
    private List<NameSpace> sinceIdNS;
    private List<NameSpace> maxIdNS;

    @Inject
    public GetMentionsTimeLine(EditorResources resources,
                               Provider<Branch> branchProvider,
                               ElementCreatorsManager elementCreatorsManager) {
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
        count = "";
        page = "";
        sinceId = "";
        maxId = "";

        consumerKeyExpr = "";
        accessTokenExpr = "";
        accessTokenSecretExpr = "";
        consumerSecretExpr = "";
        countExpr = "";
        pageExpr = "";
        sinceIdExpr = "";
        maxIdExpr = "";

        consumerKeyNS = new ArrayList<>();
        consumerSecretNS = new ArrayList<>();
        accessTokenNS = new ArrayList<>();
        accessTokenSecretNS = new ArrayList<>();
        countNS = new ArrayList<>();
        pageNS = new ArrayList<>();
        sinceIdNS = new ArrayList<>();
        maxIdNS = new ArrayList<>();
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
        properties.put(COUNT, isInline ? count : countExpr);
        properties.put(PAGE, isInline ? page : pageExpr);
        properties.put(SINCE_ID, isInline ? sinceId : sinceIdExpr);
        properties.put(MAX_ID, isInline ? maxId : maxIdExpr);

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

            case COUNT:
                if (isInline) {
                    count = nodeValue;
                } else {
                    countExpr = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case PAGE:
                if (isInline) {
                    page = nodeValue;
                } else {
                    pageExpr = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case SINCE_ID:
                if (isInline) {
                    sinceId = nodeValue;
                } else {
                    sinceIdExpr = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case MAX_ID:
                if (isInline) {
                    maxId = nodeValue;
                } else {
                    maxIdExpr = nodeValue;

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
    public String getCount() {
        return count;
    }

    public void setCount(@Nonnull String count) {
        this.count = count;
    }

    @Nonnull
    public String getPage() {
        return page;
    }

    public void setPage(@Nonnull String page) {
        this.page = page;
    }

    @Nonnull
    public String getSinceId() {
        return sinceId;
    }

    public void setSinceId(@Nonnull String sinceId) {
        this.sinceId = sinceId;
    }

    @Nonnull
    public String getMaxId() {
        return maxId;
    }

    public void setMaxId(@Nonnull String maxId) {
        this.maxId = maxId;
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
    public String getCountExpr() {
        return countExpr;
    }

    public void setCountExpr(@Nonnull String countExpr) {
        this.countExpr = countExpr;
    }

    @Nonnull
    public String getPageExpr() {
        return pageExpr;
    }

    public void setPageExpr(@Nonnull String pageExpr) {
        this.pageExpr = pageExpr;
    }

    @Nonnull
    public String getSinceIdExpr() {
        return sinceIdExpr;
    }

    public void setSinceIdExpr(@Nonnull String sinceIdExpr) {
        this.sinceIdExpr = sinceIdExpr;
    }

    @Nonnull
    public String getMaxIdExpr() {
        return maxIdExpr;
    }

    public void setMaxIdExpr(@Nonnull String maxIdExpr) {
        this.maxIdExpr = maxIdExpr;
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
    public List<NameSpace> getCountNS() {
        return countNS;
    }

    public void setCountNS(@Nonnull List<NameSpace> countNS) {
        this.countNS = countNS;
    }

    @Nonnull
    public List<NameSpace> getPageNS() {
        return pageNS;
    }

    public void setPageNS(@Nonnull List<NameSpace> pageNS) {
        this.pageNS = pageNS;
    }

    @Nonnull
    public List<NameSpace> getSinceIdNS() {
        return sinceIdNS;
    }

    public void setSinceIdNS(@Nonnull List<NameSpace> sinceIdNS) {
        this.sinceIdNS = sinceIdNS;
    }

    @Nonnull
    public List<NameSpace> getMaxIdNS() {
        return maxIdNS;
    }

    public void setMaxIdNS(@Nonnull List<NameSpace> maxIdNS) {
        this.maxIdNS = maxIdNS;
    }


}