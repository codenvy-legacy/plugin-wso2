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
 * The Class describes SendDirectMessage connector for twitter group of connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 */
public class SendDirectMessage extends AbstractConnector {

    public static final String ELEMENT_NAME       = "SendDirectMessage";
    public static final String SERIALIZATION_NAME = "twitter.sendDirectMessage";

    private static final String CONSUMER_KEY        = "consumerKey";
    private static final String CONSUMER_SECRET     = "consumerSecret";
    private static final String ACCESS_TOKEN        = "accessToken";
    private static final String ACCESS_TOKEN_SECRET = "accessTokenSecret";
    private static final String USER_ID             = "userID";
    private static final String SCREEN_NAME         = "screenName";
    private static final String MESSAGE             = "message";

    private static final List<String> PROPERTIES =
            Arrays.asList(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, ACCESS_TOKEN_SECRET, USER_ID, SCREEN_NAME, MESSAGE);

    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessTokenSecret;
    private String userID;
    private String screenName;
    private String message;

    private String consumerKeyExpr;
    private String consumerSecretExpr;
    private String accessTokenExpr;
    private String accessTokenSecretExpr;
    private String userIDExpr;
    private String screenNameExpr;
    private String messageExpr;

    private Array<NameSpace> consumerKeyNS;
    private Array<NameSpace> consumerSecretNS;
    private Array<NameSpace> accessTokenNS;
    private Array<NameSpace> accessTokenSecretNS;
    private Array<NameSpace> userIdNS;
    private Array<NameSpace> screenNameNS;
    private Array<NameSpace> messageNS;

    @Inject
    public SendDirectMessage(EditorResources resources, Provider<Branch> branchProvider, MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        consumerKey = "";
        consumerSecret = "";
        accessToken = "";
        accessTokenSecret = "";
        userID = "";
        screenName = "";
        message = "";

        consumerKeyExpr = "";
        accessTokenExpr = "";
        accessTokenSecretExpr = "";
        consumerSecretExpr = "";
        userIDExpr = "";
        screenNameExpr = "";
        messageExpr = "";

        consumerKeyNS = createArray();
        consumerSecretNS = createArray();
        accessTokenNS = createArray();
        accessTokenSecretNS = createArray();
        userIdNS = createArray();
        screenNameNS = createArray();
        messageNS = createArray();
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
        properties.put(USER_ID, isInline ? userID : userIDExpr);
        properties.put(SCREEN_NAME, isInline ? screenName : screenNameExpr);
        properties.put(MESSAGE, isInline ? message : messageExpr);

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

            case USER_ID:
                if (isInline) {
                    userID = nodeValue;
                } else {
                    userIDExpr = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case SCREEN_NAME:
                if (isInline) {
                    screenName = nodeValue;
                } else {
                    screenNameExpr = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case MESSAGE:
                if (isInline) {
                    message = nodeValue;
                } else {
                    messageExpr = nodeValue;

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
    public String getUserID() {
        return userID;
    }

    public void setUserID(@Nonnull String userID) {
        this.userID = userID;
    }

    @Nonnull
    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(@Nonnull String screenName) {
        this.screenName = screenName;
    }

    @Nonnull
    public String getMessage() {
        return message;
    }

    public void setMessage(@Nonnull String message) {
        this.message = message;
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
    public String getUserIDExpr() {
        return userIDExpr;
    }

    public void setUserIDExpr(@Nonnull String userIDExpr) {
        this.userIDExpr = userIDExpr;
    }

    @Nonnull
    public String getScreenNameExpr() {
        return screenNameExpr;
    }

    public void setScreenNameExpr(@Nonnull String screenNameExpr) {
        this.screenNameExpr = screenNameExpr;
    }

    @Nonnull
    public String getMessageExpr() {
        return messageExpr;
    }

    public void setMessageExpr(@Nonnull String messageExpr) {
        this.messageExpr = messageExpr;
    }

    @Nonnull
    public Array<NameSpace> getConsumerKeyNS() {
        return consumerKeyNS;
    }

    public void setConsumerKeyNS(@Nonnull Array<NameSpace> consumerKeyNS) {
        this.consumerKeyNS = consumerKeyNS;
    }

    @Nonnull
    public Array<NameSpace> getConsumerSecretNS() {
        return consumerSecretNS;
    }

    public void setConsumerSecretNS(@Nonnull Array<NameSpace> consumerSecretNS) {
        this.consumerSecretNS = consumerSecretNS;
    }

    @Nonnull
    public Array<NameSpace> getAccessTokenNS() {
        return accessTokenNS;
    }

    public void setAccessTokenNS(@Nonnull Array<NameSpace> accessTokenNS) {
        this.accessTokenNS = accessTokenNS;
    }

    @Nonnull
    public Array<NameSpace> getAccessTokenSecretNS() {
        return accessTokenSecretNS;
    }

    public void setAccessTokenSecretNS(@Nonnull Array<NameSpace> accessTokenSecretNS) {
        this.accessTokenSecretNS = accessTokenSecretNS;
    }

    @Nonnull
    public Array<NameSpace> getUserIdNS() {
        return userIdNS;
    }

    public void setUserIdNS(@Nonnull Array<NameSpace> userIdNS) {
        this.userIdNS = userIdNS;
    }

    @Nonnull
    public Array<NameSpace> getScreenNameNS() {
        return screenNameNS;
    }

    public void setScreenNameNS(@Nonnull Array<NameSpace> screenNameNS) {
        this.screenNameNS = screenNameNS;
    }

    @Nonnull
    public Array<NameSpace> getMessageNS() {
        return messageNS;
    }

    public void setMessageNS(@Nonnull Array<NameSpace> messageNS) {
        this.messageNS = messageNS;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.twitterElement();
    }
}
