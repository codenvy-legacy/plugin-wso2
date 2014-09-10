/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
 * The Class describes GetFollowersIds connector for twitter group of connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 */
public class GetFollowersIds extends AbstractConnector {

    public static final String ELEMENT_NAME       = "GetFollowersIds";
    public static final String SERIALIZATION_NAME = "twitter.getFollowersIds";

    private static final String SCREEN_NAME = "screenName";
    private static final String USER_ID     = "userID";
    private static final String CURSOR      = "cursor";

    private static final List<String> PROPERTIES = Arrays.asList(SCREEN_NAME, USER_ID, CURSOR);

    private String screenName;
    private String userId;
    private String cursor;

    private String screenNameExpr;
    private String userIdExpr;
    private String cursorExpr;

    private Array<NameSpace> screenNameNS;
    private Array<NameSpace> userIdNS;
    private Array<NameSpace> cursorNS;

    @Inject
    public GetFollowersIds(EditorResources resources, Provider<Branch> branchProvider, MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        screenName = "";
        userId = "";
        cursor = "";

        screenNameExpr = "";
        cursorExpr = "";
        userIdExpr = "";

        cursorNS = createArray();
        screenNameNS = createArray();
        userIdNS = createArray();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(SCREEN_NAME, isInline ? screenName : screenNameExpr);
        properties.put(USER_ID, isInline ? userId : userIdExpr);
        properties.put(CURSOR, isInline ? cursor : cursorExpr);

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    public void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case SCREEN_NAME:
                if (isInline) {
                    screenName = nodeValue;
                } else {
                    screenNameExpr = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case USER_ID:
                if (isInline) {
                    userId = nodeValue;
                } else {
                    userIdExpr = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case CURSOR:
                if (isInline) {
                    cursor = nodeValue;
                } else {
                    cursorExpr = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;
        }
    }

    @Nonnull
    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(@Nonnull String screenName) {
        this.screenName = screenName;
    }

    @Nonnull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@Nonnull String userId) {
        this.userId = userId;
    }

    @Nonnull
    public String getCursor() {
        return cursor;
    }

    public void setCursor(@Nonnull String cursor) {
        this.cursor = cursor;
    }

    @Nonnull
    public String getScreenNameExpr() {
        return screenNameExpr;
    }

    public void setScreenNameExpr(@Nonnull String screenNameExpr) {
        this.screenNameExpr = screenNameExpr;
    }

    @Nonnull
    public String getUserIdExpr() {
        return userIdExpr;
    }

    public void setUserIdExpr(@Nonnull String userIdExpr) {
        this.userIdExpr = userIdExpr;
    }

    @Nonnull
    public String getCursorExpr() {
        return cursorExpr;
    }

    public void setCursorExpr(@Nonnull String cursorExpr) {
        this.cursorExpr = cursorExpr;
    }

    @Nonnull
    public Array<NameSpace> getScreenNameNS() {
        return screenNameNS;
    }

    public void setScreenNameNS(@Nonnull Array<NameSpace> screenNameNS) {
        this.screenNameNS = screenNameNS;
    }

    @Nonnull
    public Array<NameSpace> getUserIdNS() {
        return userIdNS;
    }

    public void setUserIdNS(@Nonnull Array<NameSpace> userIdNS) {
        this.userIdNS = userIdNS;
    }

    @Nonnull
    public Array<NameSpace> getCursorNS() {
        return cursorNS;
    }

    public void setCursorNS(@Nonnull Array<NameSpace> cursorNS) {
        this.cursorNS = cursorNS;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.twitterElement();
    }
}
