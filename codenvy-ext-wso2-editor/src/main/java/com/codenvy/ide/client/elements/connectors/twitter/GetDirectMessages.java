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

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.INLINE;

/**
 * The Class describes GetDirectMessages connector for twitter group of connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class GetDirectMessages extends AbstractConnector {

    public static final String ELEMENT_NAME       = "GetDirectMessages";
    public static final String SERIALIZATION_NAME = "twitter.getDirectMessages";

    public static final Key<String> CONSUMER_KEY_INL        = new Key<>("ConsumerKey");
    public static final Key<String> CONSUMER_SECRET_INL     = new Key<>("ConsumerSecret");
    public static final Key<String> ACCESS_TOKEN_INL        = new Key<>("accessToken");
    public static final Key<String> ACCESS_TOKEN_SECRET_INL = new Key<>("accessTokenSecret");
    public static final Key<String> COUNT_INL               = new Key<>("countInl");
    public static final Key<String> PAGE_INL                = new Key<>("pageInl");
    public static final Key<String> SINCE_ID_INL            = new Key<>("sinceIdInl");
    public static final Key<String> MAX_ID_INL              = new Key<>("maxIdInl");

    public static final Key<String> CONSUMER_KEY_EXPR        = new Key<>("consumerKeyExpression");
    public static final Key<String> CONSUMER_SECRET_EXPR     = new Key<>("consumerSecretExpression");
    public static final Key<String> ACCESS_TOKEN_EXPR        = new Key<>("accessTokenExpression");
    public static final Key<String> ACCESS_TOKEN_SECRET_EXPR = new Key<>("accessTokenSecretExpression");
    public static final Key<String> COUNT_EXPR               = new Key<>("countExpression");
    public static final Key<String> PAGE_EXPR                = new Key<>("pageExpression");
    public static final Key<String> SINCE_ID_EXPR            = new Key<>("sinceIdExpression");
    public static final Key<String> MAX_ID_EXPR              = new Key<>("maxIdExpression");

    public static final Key<List<NameSpace>> CONSUMER_KEY_NS        = new Key<>("consumerKeyNameSpace");
    public static final Key<List<NameSpace>> CONSUMER_SECRET_NS     = new Key<>("consumerSecretNameSpace");
    public static final Key<List<NameSpace>> ACCESS_TOKEN_NS        = new Key<>("accessTokenNameSpace");
    public static final Key<List<NameSpace>> ACCESS_TOKEN_SECRET_NS = new Key<>("accessTokenSecretNameSpace");
    public static final Key<List<NameSpace>> COUNT_NS               = new Key<>("countNameSpace");
    public static final Key<List<NameSpace>> PAGE_NS                = new Key<>("pageNameSpace");
    public static final Key<List<NameSpace>> SINCE_ID_NS            = new Key<>("sinceIdNameSpace");
    public static final Key<List<NameSpace>> MAX_ID_NS              = new Key<>("maxIdNameSpace");

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

    @Inject
    public GetDirectMessages(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.twitterElement(),
              branchProvider,
              elementCreatorsManager);

        putProperty(CONSUMER_KEY_INL, "");
        putProperty(CONSUMER_SECRET_INL, "");
        putProperty(ACCESS_TOKEN_INL, "");
        putProperty(ACCESS_TOKEN_SECRET_INL, "");
        putProperty(COUNT_INL, "");
        putProperty(PAGE_INL, "");
        putProperty(SINCE_ID_INL, "");
        putProperty(MAX_ID_INL, "");

        putProperty(CONSUMER_KEY_EXPR, "");
        putProperty(CONSUMER_SECRET_EXPR, "");
        putProperty(ACCESS_TOKEN_EXPR, "");
        putProperty(ACCESS_TOKEN_SECRET_EXPR, "");
        putProperty(COUNT_EXPR, "");
        putProperty(PAGE_EXPR, "");
        putProperty(SINCE_ID_EXPR, "");
        putProperty(MAX_ID_EXPR, "");

        putProperty(CONSUMER_KEY_NS, new ArrayList<NameSpace>());
        putProperty(CONSUMER_SECRET_NS, new ArrayList<NameSpace>());
        putProperty(ACCESS_TOKEN_NS, new ArrayList<NameSpace>());
        putProperty(ACCESS_TOKEN_SECRET_NS, new ArrayList<NameSpace>());
        putProperty(COUNT_NS, new ArrayList<NameSpace>());
        putProperty(PAGE_NS, new ArrayList<NameSpace>());
        putProperty(SINCE_ID_NS, new ArrayList<NameSpace>());
        putProperty(MAX_ID_NS, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE));

        properties.put(CONSUMER_KEY, isInline ? getProperty(CONSUMER_KEY_INL) : getProperty(CONSUMER_KEY_EXPR));
        properties.put(CONSUMER_SECRET, isInline ? getProperty(CONSUMER_SECRET_INL) : getProperty(CONSUMER_SECRET_EXPR));
        properties.put(ACCESS_TOKEN, isInline ? getProperty(ACCESS_TOKEN_INL) : getProperty(ACCESS_TOKEN_EXPR));
        properties.put(ACCESS_TOKEN_SECRET, isInline ? getProperty(ACCESS_TOKEN_SECRET_INL) : getProperty(ACCESS_TOKEN_SECRET_EXPR));
        properties.put(COUNT, isInline ? getProperty(COUNT_INL) : getProperty(COUNT_EXPR));
        properties.put(PAGE, isInline ? getProperty(PAGE_INL) : getProperty(PAGE_EXPR));
        properties.put(SINCE_ID, isInline ? getProperty(SINCE_ID_INL) : getProperty(SINCE_ID_EXPR));
        properties.put(MAX_ID, isInline ? getProperty(MAX_ID_INL) : getProperty(MAX_ID_EXPR));

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        switch (node.getNodeName()) {
            case CONSUMER_KEY:
                adaptProperty(nodeValue, CONSUMER_KEY_INL, CONSUMER_KEY_EXPR);
                break;

            case CONSUMER_SECRET:
                adaptProperty(nodeValue, CONSUMER_SECRET_INL, CONSUMER_SECRET_EXPR);
                break;

            case ACCESS_TOKEN:
                adaptProperty(nodeValue, ACCESS_TOKEN_INL, ACCESS_TOKEN_EXPR);
                break;

            case ACCESS_TOKEN_SECRET:
                adaptProperty(nodeValue, ACCESS_TOKEN_SECRET_INL, ACCESS_TOKEN_SECRET_EXPR);
                break;

            case COUNT:
                adaptProperty(nodeValue, COUNT_INL, COUNT_EXPR);
                break;

            case PAGE:
                adaptProperty(nodeValue, PAGE_INL, PAGE_EXPR);
                break;

            case SINCE_ID:
                adaptProperty(nodeValue, SINCE_ID_INL, SINCE_ID_EXPR);
                break;

            case MAX_ID:
                adaptProperty(nodeValue, MAX_ID_INL, MAX_ID_EXPR);
                break;
            default:
        }
    }
}