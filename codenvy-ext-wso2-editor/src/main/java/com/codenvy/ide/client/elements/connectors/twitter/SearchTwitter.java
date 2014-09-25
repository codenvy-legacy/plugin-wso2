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
 * The Class describes Search connector for twitter group of connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class SearchTwitter extends AbstractConnector {

    public static final String ELEMENT_NAME       = "Search";
    public static final String SERIALIZATION_NAME = "twitter.search";

    public static final Key<String> SEARCH_INL   = new Key<>("searchInl");
    public static final Key<String> LANG_INL     = new Key<>("langInl");
    public static final Key<String> LOCALE_INL   = new Key<>("localeInl");
    public static final Key<String> MAX_ID_INL   = new Key<>("maxIdInl");
    public static final Key<String> SINCE_INL    = new Key<>("sinceInl");
    public static final Key<String> SINCE_ID_INL = new Key<>("sinceIdInl");
    public static final Key<String> GEOCODE_INL  = new Key<>("geocodeInl");
    public static final Key<String> RADIUS_INL   = new Key<>("radiusInl");
    public static final Key<String> UNIT_INL     = new Key<>("unitInl");
    public static final Key<String> UNTIL_INL    = new Key<>("untilInl");
    public static final Key<String> COUNT_INL    = new Key<>("countInl");

    public static final Key<String> SEARCH_EXPR   = new Key<>("searchExpr");
    public static final Key<String> LANG_EXPR     = new Key<>("langExpr");
    public static final Key<String> LOCALE_EXPR   = new Key<>("localeExpr");
    public static final Key<String> MAX_ID_EXPR   = new Key<>("maxIdExpr");
    public static final Key<String> SINCE_EXPR    = new Key<>("sinceExpr");
    public static final Key<String> SINCE_ID_EXPR = new Key<>("sinceIdExpr");
    public static final Key<String> GEOCODE_EXPR  = new Key<>("geocodeExpr");
    public static final Key<String> RADIUS_EXPR   = new Key<>("radiusExpr");
    public static final Key<String> UNIT_EXPR     = new Key<>("unitExpr");
    public static final Key<String> UNTIL_EXPR    = new Key<>("untilExpr");
    public static final Key<String> COUNT_EXPR    = new Key<>("countExpr");

    public static final Key<List<NameSpace>> SEARCH_NS   = new Key<>("searchNameSpace");
    public static final Key<List<NameSpace>> LANG_NS     = new Key<>("searchNameSpace");
    public static final Key<List<NameSpace>> LOCALE_NS   = new Key<>("searchNameSpace");
    public static final Key<List<NameSpace>> MAX_ID_NS   = new Key<>("searchNameSpace");
    public static final Key<List<NameSpace>> SINCE_NS    = new Key<>("searchNameSpace");
    public static final Key<List<NameSpace>> SINCE_ID_NS = new Key<>("searchNameSpace");
    public static final Key<List<NameSpace>> GEOCODE_NS  = new Key<>("searchNameSpace");
    public static final Key<List<NameSpace>> RADIUS_NS   = new Key<>("searchNameSpace");
    public static final Key<List<NameSpace>> UNIT_NS     = new Key<>("searchNameSpace");
    public static final Key<List<NameSpace>> UNTIL_NS    = new Key<>("searchNameSpace");
    public static final Key<List<NameSpace>> COUNT_NS    = new Key<>("searchNameSpace");

    private static final String SEARCH   = "search";
    private static final String LANG     = "lang";
    private static final String LOCALE   = "locale";
    private static final String MAX_ID   = "maxId";
    private static final String SINCE    = "since";
    private static final String SINCE_ID = "sinceId";
    private static final String GEOCODE  = "geocode";
    private static final String RADIUS   = "radius";
    private static final String UNIT     = "unit";
    private static final String UNTIL    = "until";
    private static final String COUNT    = "count";

    private static final List<String> PROPERTIES =
            Arrays.asList(SEARCH, LANG, LOCALE, MAX_ID, SINCE, SINCE_ID, GEOCODE, RADIUS, UNIT, UNTIL, COUNT);

    @Inject
    public SearchTwitter(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.twitterElement(),
              branchProvider,
              elementCreatorsManager);

        putProperty(SEARCH_INL, "");
        putProperty(LANG_INL, "");
        putProperty(LOCALE_INL, "");
        putProperty(MAX_ID_INL, "");
        putProperty(SINCE_INL, "");
        putProperty(SINCE_ID_INL, "");
        putProperty(GEOCODE_INL, "");
        putProperty(RADIUS_INL, "");
        putProperty(UNIT_INL, "");
        putProperty(UNTIL_INL, "");
        putProperty(COUNT_INL, "");

        putProperty(SEARCH_EXPR, "");
        putProperty(LANG_EXPR, "");
        putProperty(LOCALE_EXPR, "");
        putProperty(MAX_ID_EXPR, "");
        putProperty(SINCE_EXPR, "");
        putProperty(SINCE_ID_EXPR, "");
        putProperty(GEOCODE_EXPR, "");
        putProperty(RADIUS_EXPR, "");
        putProperty(UNIT_EXPR, "");
        putProperty(UNTIL_EXPR, "");
        putProperty(COUNT_EXPR, "");

        putProperty(SEARCH_NS, new ArrayList<NameSpace>());
        putProperty(LANG_NS, new ArrayList<NameSpace>());
        putProperty(LOCALE_NS, new ArrayList<NameSpace>());
        putProperty(MAX_ID_NS, new ArrayList<NameSpace>());
        putProperty(SINCE_NS, new ArrayList<NameSpace>());
        putProperty(SINCE_ID_NS, new ArrayList<NameSpace>());
        putProperty(GEOCODE_NS, new ArrayList<NameSpace>());
        putProperty(RADIUS_NS, new ArrayList<NameSpace>());
        putProperty(UNIT_NS, new ArrayList<NameSpace>());
        putProperty(UNTIL_NS, new ArrayList<NameSpace>());
        putProperty(COUNT_NS, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE));

        properties.put(SEARCH, isInline ? getProperty(SEARCH_INL) : getProperty(SEARCH_EXPR));
        properties.put(LANG, isInline ? getProperty(LANG_INL) : getProperty(LANG_EXPR));
        properties.put(LOCALE, isInline ? getProperty(LOCALE_INL) : getProperty(LOCALE_EXPR));
        properties.put(MAX_ID, isInline ? getProperty(MAX_ID_INL) : getProperty(MAX_ID_EXPR));
        properties.put(SINCE, isInline ? getProperty(SINCE_INL) : getProperty(SINCE_EXPR));
        properties.put(SINCE_ID, isInline ? getProperty(SINCE_ID_INL) : getProperty(SINCE_ID_EXPR));
        properties.put(GEOCODE, isInline ? getProperty(GEOCODE_INL) : getProperty(GEOCODE_EXPR));
        properties.put(RADIUS, isInline ? getProperty(RADIUS_INL) : getProperty(RADIUS_EXPR));
        properties.put(UNIT, isInline ? getProperty(UNIT_INL) : getProperty(UNIT_EXPR));
        properties.put(UNTIL, isInline ? getProperty(UNTIL_INL) : getProperty(UNTIL_EXPR));
        properties.put(COUNT, isInline ? getProperty(COUNT_INL) : getProperty(COUNT_EXPR));

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        switch (node.getNodeName()) {
            case SEARCH:
                adaptProperty(nodeValue, SEARCH_INL, SEARCH_EXPR);
                break;

            case LANG:
                adaptProperty(nodeValue, LANG_INL, LANG_EXPR);
                break;

            case LOCALE:
                adaptProperty(nodeValue, LOCALE_INL, LOCALE_EXPR);
                break;

            case MAX_ID:
                adaptProperty(nodeValue, MAX_ID_INL, MAX_ID_EXPR);
                break;

            case SINCE:
                adaptProperty(nodeValue, SINCE_INL, SINCE_EXPR);
                break;

            case SINCE_ID:
                adaptProperty(nodeValue, SINCE_ID_INL, SINCE_ID_EXPR);
                break;

            case GEOCODE:
                adaptProperty(nodeValue, GEOCODE_INL, GEOCODE_EXPR);
                break;

            case RADIUS:
                adaptProperty(nodeValue, RADIUS_INL, RADIUS_EXPR);
                break;

            case UNIT:
                adaptProperty(nodeValue, UNIT_INL, UNIT_EXPR);
                break;

            case UNTIL:
                adaptProperty(nodeValue, UNTIL_INL, UNTIL_EXPR);
                break;

            case COUNT:
                adaptProperty(nodeValue, COUNT_INL, COUNT_EXPR);
                break;
            default:
        }
    }
}
