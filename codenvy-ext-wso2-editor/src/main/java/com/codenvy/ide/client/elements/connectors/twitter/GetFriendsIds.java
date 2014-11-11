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
 * The Class describes GetFriendsIds connector for twitter group of connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class GetFriendsIds extends AbstractConnector {

    public static final String ELEMENT_NAME       = "GetFriendsIds";
    public static final String SERIALIZATION_NAME = "twitter.getFriendsIds";

    public static final Key<String> SCREEN_NAME_INL = new Key<>("screenNameInline");
    public static final Key<String> USER_ID_INL     = new Key<>("userIdInline");
    public static final Key<String> CURSOR_INL      = new Key<>("cursorInline");

    public static final Key<String> SCREEN_NAME_EXPR = new Key<>("screenNameExpression");
    public static final Key<String> USER_ID_EXPR     = new Key<>("userIdExpression");
    public static final Key<String> CURSOR_EXPR      = new Key<>("cursorExpression");

    public static final Key<List<NameSpace>> SCREEN_NAME_NS = new Key<>("screenNameNameSpace");
    public static final Key<List<NameSpace>> USER_ID_NS     = new Key<>("userIdNameSpace");
    public static final Key<List<NameSpace>> CURSOR_NS      = new Key<>("cursorNameSpace");

    private static final String SCREEN_NAME = "screenName";
    private static final String USER_ID     = "userID";
    private static final String CURSOR      = "cursor";

    private static final List<String> PROPERTIES = Arrays.asList(SCREEN_NAME, USER_ID, CURSOR);

    @Inject
    public GetFriendsIds(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              resources.twitterElement(),
              branchProvider,
              elementCreatorsManager);

        putProperty(SCREEN_NAME_INL, "");
        putProperty(USER_ID_INL, "");
        putProperty(CURSOR_INL, "");

        putProperty(SCREEN_NAME_EXPR, "");
        putProperty(USER_ID_EXPR, "");
        putProperty(CURSOR_EXPR, "");

        putProperty(SCREEN_NAME_NS, new ArrayList<NameSpace>());
        putProperty(USER_ID_NS, new ArrayList<NameSpace>());
        putProperty(CURSOR_NS, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE));

        properties.put(SCREEN_NAME, isInline ? getProperty(SCREEN_NAME_INL) : getProperty(SCREEN_NAME_EXPR));
        properties.put(USER_ID, isInline ? getProperty(USER_ID_INL) : getProperty(USER_ID_EXPR));
        properties.put(CURSOR, isInline ? getProperty(CURSOR_INL) : getProperty(CURSOR_EXPR));

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        switch (node.getNodeName()) {
            case SCREEN_NAME:
                adaptProperty(nodeValue, SCREEN_NAME_INL, SCREEN_NAME_EXPR);
                break;

            case USER_ID:
                adaptProperty(nodeValue, USER_ID_INL, USER_ID_EXPR);
                break;

            case CURSOR:
                adaptProperty(nodeValue, CURSOR_INL, CURSOR_EXPR);
                break;

            default:
        }
    }
}
