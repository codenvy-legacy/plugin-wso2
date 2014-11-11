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
 * The Class describes DestroyStatus connector for twitter group of connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class DestroyStatus extends AbstractConnector {

    public static final String ELEMENT_NAME       = "DestroyStatus";
    public static final String SERIALIZATION_NAME = "twitter.destroyStatus";

    public static final Key<String> KEY_CONSUMER_KEY        = new Key<>("ConsumerKey");
    public static final Key<String> KEY_CONSUMER_SECRET     = new Key<>("ConsumerSecret");
    public static final Key<String> KEY_ACCESS_TOKEN        = new Key<>("accessToken");
    public static final Key<String> KEY_ACCESS_TOKEN_SECRET = new Key<>("accessTokenSecret");
    public static final Key<String> KEY_STATUS_ID           = new Key<>("statusId");

    public static final Key<String> KEY_CONSUMER_KEY_EXPR        = new Key<>("consumerKeyExpr");
    public static final Key<String> KEY_CONSUMER_SECRET_EXPR     = new Key<>("consumerSecretExpr");
    public static final Key<String> KEY_ACCESS_TOKEN_EXPR        = new Key<>("accessTokenExpr");
    public static final Key<String> KEY_ACCESS_TOKEN_SECRET_EXPR = new Key<>("accessTokenSecretExpr");
    public static final Key<String> KEY_STATUS_ID_EXPR           = new Key<>("statusIdExpr");

    public static final Key<List<NameSpace>> KEY_CONSUMER_KEY_NS        = new Key<>("consumerKeyNS");
    public static final Key<List<NameSpace>> KEY_CONSUMER_SECRET_NS     = new Key<>("consumerSecretNS");
    public static final Key<List<NameSpace>> KEY_ACCESS_TOKEN_NS        = new Key<>("accessTokenNS");
    public static final Key<List<NameSpace>> KEY_ACCESS_TOKEN_SECRET_NS = new Key<>("accessTokenSecretNS");
    public static final Key<List<NameSpace>> KEY_STATUS_ID_NS           = new Key<>("statusIdNS");

    private static final String CONSUMER_KEY        = "consumerKey";
    private static final String CONSUMER_SECRET     = "consumerSecret";
    private static final String ACCESS_TOKEN        = "accessToken";
    private static final String ACCESS_TOKEN_SECRET = "accessTokenSecret";
    private static final String STATUS_ID           = "statusId";

    private static final List<String> PROPERTIES = Arrays.asList(CONSUMER_KEY,
                                                                 CONSUMER_SECRET,
                                                                 ACCESS_TOKEN,
                                                                 ACCESS_TOKEN_SECRET,
                                                                 STATUS_ID);

    @Inject
    public DestroyStatus(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              resources.twitterElement(),
              branchProvider,
              elementCreatorsManager);

        putProperty(KEY_CONSUMER_KEY, "");
        putProperty(KEY_CONSUMER_SECRET, "");
        putProperty(KEY_ACCESS_TOKEN, "");
        putProperty(KEY_ACCESS_TOKEN_SECRET, "");
        putProperty(KEY_STATUS_ID, "");

        putProperty(KEY_CONSUMER_KEY_EXPR, "");
        putProperty(KEY_CONSUMER_SECRET_EXPR, "");
        putProperty(KEY_ACCESS_TOKEN_EXPR, "");
        putProperty(KEY_ACCESS_TOKEN_SECRET_EXPR, "");
        putProperty(KEY_STATUS_ID_EXPR, "");

        putProperty(KEY_CONSUMER_KEY_NS, new ArrayList<NameSpace>());
        putProperty(KEY_CONSUMER_SECRET_NS, new ArrayList<NameSpace>());
        putProperty(KEY_ACCESS_TOKEN_NS, new ArrayList<NameSpace>());
        putProperty(KEY_ACCESS_TOKEN_SECRET_NS, new ArrayList<NameSpace>());
        putProperty(KEY_STATUS_ID_NS, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE));

        properties.put(CONSUMER_KEY, isInline ? getProperty(KEY_CONSUMER_KEY) : getProperty(KEY_CONSUMER_KEY_EXPR));
        properties.put(CONSUMER_SECRET, isInline ? getProperty(KEY_CONSUMER_SECRET) : getProperty(KEY_CONSUMER_SECRET_EXPR));
        properties.put(ACCESS_TOKEN, isInline ? getProperty(KEY_ACCESS_TOKEN) : getProperty(KEY_ACCESS_TOKEN_EXPR));
        properties.put(ACCESS_TOKEN_SECRET, isInline ? getProperty(KEY_ACCESS_TOKEN_SECRET) : getProperty(KEY_ACCESS_TOKEN_SECRET_EXPR));
        properties.put(STATUS_ID, isInline ? getProperty(KEY_STATUS_ID) : getProperty(KEY_STATUS_ID_EXPR));

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        switch (node.getNodeName()) {
            case CONSUMER_KEY:
                adaptProperty(nodeValue, KEY_CONSUMER_KEY, KEY_CONSUMER_KEY_EXPR);
                break;

            case CONSUMER_SECRET:
                adaptProperty(nodeValue, KEY_CONSUMER_SECRET, KEY_CONSUMER_SECRET_EXPR);
                break;

            case ACCESS_TOKEN:
                adaptProperty(nodeValue, KEY_ACCESS_TOKEN, KEY_ACCESS_TOKEN_EXPR);
                break;

            case ACCESS_TOKEN_SECRET:
                adaptProperty(nodeValue, KEY_ACCESS_TOKEN_SECRET, KEY_ACCESS_TOKEN_SECRET_EXPR);
                break;

            case STATUS_ID:
                adaptProperty(nodeValue, KEY_STATUS_ID, KEY_STATUS_ID_EXPR);
                break;

            default:
        }
    }
}