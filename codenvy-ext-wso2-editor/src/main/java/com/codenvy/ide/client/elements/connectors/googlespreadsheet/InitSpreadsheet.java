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
 * The Class describes InitSpreadsheet connector for GoogleSpreadsheet group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 */
public class InitSpreadsheet extends AbstractConnector {

    public static final String ELEMENT_NAME       = "Init";
    public static final String SERIALIZATION_NAME = "googlespreadsheet.init";

    public static final Key<String> OAUTH_CONSUMER_KEY_KEY        = new Key<>("OauthConsumerKey");
    public static final Key<String> OAUTH_CONSUMER_SECRET_KEY     = new Key<>("OauthConsumerSecret");
    public static final Key<String> OAUTH_ACCESS_TOKEN_KEY        = new Key<>("OauthAccessToken");
    public static final Key<String> OAUTH_ACCESS_TOKEN_SECRET_KEY = new Key<>("OauthAccessTokenSecret");

    public static final Key<String> OAUTH_CONSUMER_KEY_EXPRESSION_KEY        = new Key<>("OauthConsumerKeyExpression");
    public static final Key<String> OAUTH_CONSUMER_SECRET_EXPRESSION_KEY     = new Key<>("OauthConsumerSecretExpression");
    public static final Key<String> OAUTH_ACCESS_TOKEN_EXPRESSION_KEY        = new Key<>("OauthAccessTokenExpression");
    public static final Key<String> OAUTH_ACCESS_TOKEN_SECRET_EXPRESSION_KEY = new Key<>("OauthAccessTokenSecretExpression");

    public static final Key<List<NameSpace>> OAUTH_CONSUMER_KEY_NS_KEY        = new Key<>("OauthConsumerKeyNS");
    public static final Key<List<NameSpace>> OAUTH_CONSUMER_SECRET_NS_KEY     = new Key<>("OauthConsumerSecretNS");
    public static final Key<List<NameSpace>> OAUTH_ACCESS_TOKEN_NS_KEY        = new Key<>("OauthAccessTokenNS");
    public static final Key<List<NameSpace>> OAUTH_ACCESS_TOKEN_SECRET_NS_KEY = new Key<>("OauthAccessTokenSecretNS");

    private static final String OAUTH_CONSUMER_KEY        = "oauthConsumerKey";
    private static final String OAUTH_CONSUMER_SECRET     = "oauthConsumerSecret";
    private static final String OAUTH_ACCESS_TOKEN        = "oauthAccessToken";
    private static final String OAUTH_ACCESS_TOKEN_SECRET = "oauthAccessTokenSecret";

    private static final List<String> PROPERTIES = Arrays.asList(OAUTH_CONSUMER_KEY,
                                                                 OAUTH_CONSUMER_SECRET,
                                                                 OAUTH_ACCESS_TOKEN,
                                                                 OAUTH_ACCESS_TOKEN_SECRET);

    @Inject
    public InitSpreadsheet(EditorResources resources,
                           Provider<Branch> branchProvider,
                           ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.googleSpreadsheetElement(),
              branchProvider,
              elementCreatorsManager);

        putProperty(OAUTH_CONSUMER_KEY_KEY, "");
        putProperty(OAUTH_CONSUMER_SECRET_KEY, "");
        putProperty(OAUTH_ACCESS_TOKEN_KEY, "");
        putProperty(OAUTH_ACCESS_TOKEN_SECRET_KEY, "");

        putProperty(OAUTH_CONSUMER_KEY_EXPRESSION_KEY, "");
        putProperty(OAUTH_CONSUMER_SECRET_KEY, "");
        putProperty(OAUTH_ACCESS_TOKEN_EXPRESSION_KEY, "");
        putProperty(OAUTH_ACCESS_TOKEN_SECRET_EXPRESSION_KEY, "");

        putProperty(OAUTH_CONSUMER_KEY_NS_KEY, new ArrayList<NameSpace>());
        putProperty(OAUTH_CONSUMER_SECRET_NS_KEY, new ArrayList<NameSpace>());
        putProperty(OAUTH_ACCESS_TOKEN_NS_KEY, new ArrayList<NameSpace>());
        putProperty(OAUTH_ACCESS_TOKEN_SECRET_NS_KEY, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE));

        properties.put(OAUTH_CONSUMER_KEY, isInline ? getProperty(OAUTH_CONSUMER_KEY_KEY) : getProperty(OAUTH_CONSUMER_KEY_EXPRESSION_KEY));
        properties.put(OAUTH_CONSUMER_SECRET,
                       isInline ? getProperty(OAUTH_CONSUMER_SECRET_KEY) : getProperty(OAUTH_CONSUMER_SECRET_EXPRESSION_KEY));
        properties.put(OAUTH_ACCESS_TOKEN, isInline ? getProperty(OAUTH_ACCESS_TOKEN_KEY) : getProperty(OAUTH_ACCESS_TOKEN_EXPRESSION_KEY));
        properties.put(OAUTH_ACCESS_TOKEN_SECRET,
                       isInline ? getProperty(OAUTH_ACCESS_TOKEN_SECRET_KEY) : getProperty(OAUTH_ACCESS_TOKEN_SECRET_EXPRESSION_KEY));

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        switch (nodeName) {
            case OAUTH_CONSUMER_KEY:
                adaptProperty(nodeValue, OAUTH_CONSUMER_KEY_KEY, OAUTH_CONSUMER_KEY_EXPRESSION_KEY);
                break;

            case OAUTH_CONSUMER_SECRET:
                adaptProperty(nodeValue, OAUTH_CONSUMER_SECRET_KEY, OAUTH_CONSUMER_SECRET_EXPRESSION_KEY);
                break;

            case OAUTH_ACCESS_TOKEN:
                adaptProperty(nodeValue, OAUTH_ACCESS_TOKEN_KEY, OAUTH_ACCESS_TOKEN_EXPRESSION_KEY);
                break;

            case OAUTH_ACCESS_TOKEN_SECRET:
                adaptProperty(nodeValue, OAUTH_ACCESS_TOKEN_SECRET_KEY, OAUTH_ACCESS_TOKEN_SECRET_EXPRESSION_KEY);
                break;

            default:
        }
    }

}