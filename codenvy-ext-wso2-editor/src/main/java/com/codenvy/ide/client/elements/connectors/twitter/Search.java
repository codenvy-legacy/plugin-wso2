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
 * The Class describes Search connector for twitter group of connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 */
public class Search extends AbstractConnector {

    public static final String ELEMENT_NAME       = "Search";
    public static final String SERIALIZATION_NAME = "twitter.search";

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

    private String search;
    private String lang;
    private String locale;
    private String maxId;
    private String since;
    private String sinceId;
    private String geocode;
    private String radius;
    private String unit;
    private String until;
    private String count;

    private String searchExpr;
    private String langExpr;
    private String localeExpr;
    private String maxIdExpr;
    private String sinceExpr;
    private String sinceIdExpr;
    private String geocodeExpr;
    private String radiusExpr;
    private String unitExpr;
    private String untilExpr;
    private String countExpr;

    private Array<NameSpace> searchNS;
    private Array<NameSpace> langNS;
    private Array<NameSpace> localeNS;
    private Array<NameSpace> maxIdNS;
    private Array<NameSpace> sinceNS;
    private Array<NameSpace> sinceIdNS;
    private Array<NameSpace> geocodeNS;
    private Array<NameSpace> radiusNS;
    private Array<NameSpace> unitNS;
    private Array<NameSpace> untilNS;
    private Array<NameSpace> countNS;

    @Inject
    public Search(EditorResources resources, Provider<Branch> branchProvider, MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        search = "";
        lang = "";
        locale = "";
        maxId = "";
        since = "";
        sinceId = "";
        geocode = "";
        radius = "";
        unit = "";
        until = "";
        count = "";

        searchExpr = "";
        langExpr = "";
        localeExpr = "";
        maxIdExpr = "";
        sinceExpr = "";
        sinceIdExpr = "";
        geocodeExpr = "";
        radiusExpr = "";
        unitExpr = "";
        untilExpr = "";
        countExpr = "";

        searchNS = createArray();
        langNS = createArray();
        localeNS = createArray();
        maxIdNS = createArray();
        sinceNS = createArray();
        sinceIdNS = createArray();
        geocodeNS = createArray();
        radiusNS = createArray();
        unitNS = createArray();
        untilNS = createArray();
        countNS = createArray();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(SEARCH, isInline ? search : searchExpr);
        properties.put(LANG, isInline ? lang : langExpr);
        properties.put(LOCALE, isInline ? locale : localeExpr);
        properties.put(MAX_ID, isInline ? maxId : maxIdExpr);
        properties.put(SINCE, isInline ? since : sinceExpr);
        properties.put(SINCE_ID, isInline ? sinceId : sinceIdExpr);
        properties.put(GEOCODE, isInline ? geocode : geocodeExpr);
        properties.put(RADIUS, isInline ? radius : radiusExpr);
        properties.put(UNIT, isInline ? unit : unitExpr);
        properties.put(UNTIL, isInline ? until : untilExpr);
        properties.put(COUNT, isInline ? count : countExpr);

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case SEARCH:
                if (isInline) {
                    search = nodeValue;
                } else {
                    searchExpr = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case LANG:
                if (isInline) {
                    lang = nodeValue;
                } else {
                    langExpr = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case LOCALE:
                if (isInline) {
                    locale = nodeValue;
                } else {
                    localeExpr = nodeValue;

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

            case SINCE:
                if (isInline) {
                    since = nodeValue;
                } else {
                    sinceExpr = nodeValue;

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

            case GEOCODE:
                if (isInline) {
                    geocode = nodeValue;
                } else {
                    geocodeExpr = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case RADIUS:
                if (isInline) {
                    radius = nodeValue;
                } else {
                    radiusExpr = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case UNIT:
                if (isInline) {
                    unit = nodeValue;
                } else {
                    unitExpr = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case UNTIL:
                if (isInline) {
                    until = nodeValue;
                } else {
                    untilExpr = nodeValue;

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
        }
    }

    @Nonnull
    public String getSearch() {
        return search;
    }

    public void setSearch(@Nonnull String search) {
        this.search = search;
    }

    @Nonnull
    public String getLang() {
        return lang;
    }

    public void setLang(@Nonnull String lang) {
        this.lang = lang;
    }

    @Nonnull
    public String getLocale() {
        return locale;
    }

    public void setLocale(@Nonnull String locale) {
        this.locale = locale;
    }

    @Nonnull
    public String getMaxId() {
        return maxId;
    }

    public void setMaxId(@Nonnull String maxId) {
        this.maxId = maxId;
    }

    @Nonnull
    public String getSince() {
        return since;
    }

    public void setSince(@Nonnull String since) {
        this.since = since;
    }

    @Nonnull
    public String getSinceId() {
        return sinceId;
    }

    public void setSinceId(@Nonnull String sinceId) {
        this.sinceId = sinceId;
    }

    @Nonnull
    public String getGeocode() {
        return geocode;
    }

    public void setGeocode(@Nonnull String geocode) {
        this.geocode = geocode;
    }

    @Nonnull
    public String getRadius() {
        return radius;
    }

    public void setRadius(@Nonnull String radius) {
        this.radius = radius;
    }

    @Nonnull
    public String getUnit() {
        return unit;
    }

    public void setUnit(@Nonnull String unit) {
        this.unit = unit;
    }

    @Nonnull
    public String getUntil() {
        return until;
    }

    public void setUntil(@Nonnull String until) {
        this.until = until;
    }

    @Nonnull
    public String getCount() {
        return count;
    }

    public void setCount(@Nonnull String count) {
        this.count = count;
    }

    @Nonnull
    public String getSearchExpr() {
        return searchExpr;
    }

    public void setSearchExpr(@Nonnull String searchExpr) {
        this.searchExpr = searchExpr;
    }

    @Nonnull
    public String getLangExpr() {
        return langExpr;
    }

    public void setLangExpr(@Nonnull String langExpr) {
        this.langExpr = langExpr;
    }

    @Nonnull
    public String getLocaleExpr() {
        return localeExpr;
    }

    public void setLocaleExpr(@Nonnull String localeExpr) {
        this.localeExpr = localeExpr;
    }

    @Nonnull
    public String getMaxIdExpr() {
        return maxIdExpr;
    }

    public void setMaxIdExpr(@Nonnull String maxIdExpr) {
        this.maxIdExpr = maxIdExpr;
    }

    @Nonnull
    public String getSinceExpr() {
        return sinceExpr;
    }

    public void setSinceExpr(@Nonnull String sinceExpr) {
        this.sinceExpr = sinceExpr;
    }

    @Nonnull
    public String getSinceIdExpr() {
        return sinceIdExpr;
    }

    public void setSinceIdExpr(@Nonnull String sinceIdExpr) {
        this.sinceIdExpr = sinceIdExpr;
    }

    @Nonnull
    public String getGeocodeExpr() {
        return geocodeExpr;
    }

    public void setGeocodeExpr(@Nonnull String geocodeExpr) {
        this.geocodeExpr = geocodeExpr;
    }

    @Nonnull
    public String getRadiusExpr() {
        return radiusExpr;
    }

    public void setRadiusExpr(@Nonnull String radiusExpr) {
        this.radiusExpr = radiusExpr;
    }

    @Nonnull
    public String getUnitExpr() {
        return unitExpr;
    }

    public void setUnitExpr(@Nonnull String unitExpr) {
        this.unitExpr = unitExpr;
    }

    @Nonnull
    public String getUntilExpr() {
        return untilExpr;
    }

    public void setUntilExpr(@Nonnull String untilExpr) {
        this.untilExpr = untilExpr;
    }

    @Nonnull
    public String getCountExpr() {
        return countExpr;
    }

    public void setCountExpr(@Nonnull String countExpr) {
        this.countExpr = countExpr;
    }

    @Nonnull
    public Array<NameSpace> getSearchNS() {
        return searchNS;
    }

    public void setSearchNS(@Nonnull Array<NameSpace> searchNS) {
        this.searchNS = searchNS;
    }

    @Nonnull
    public Array<NameSpace> getLangNS() {
        return langNS;
    }

    public void setLangNS(@Nonnull Array<NameSpace> langNS) {
        this.langNS = langNS;
    }

    @Nonnull
    public Array<NameSpace> getLocaleNS() {
        return localeNS;
    }

    public void setLocaleNS(@Nonnull Array<NameSpace> localeNS) {
        this.localeNS = localeNS;
    }

    @Nonnull
    public Array<NameSpace> getMaxIdNS() {
        return maxIdNS;
    }

    public void setMaxIdNS(@Nonnull Array<NameSpace> maxIdNS) {
        this.maxIdNS = maxIdNS;
    }

    @Nonnull
    public Array<NameSpace> getSinceNS() {
        return sinceNS;
    }

    public void setSinceNS(@Nonnull Array<NameSpace> sinceNS) {
        this.sinceNS = sinceNS;
    }

    @Nonnull
    public Array<NameSpace> getSinceIdNS() {
        return sinceIdNS;
    }

    public void setSinceIdNS(@Nonnull Array<NameSpace> sinceIdNS) {
        this.sinceIdNS = sinceIdNS;
    }

    @Nonnull
    public Array<NameSpace> getGeocodeNS() {
        return geocodeNS;
    }

    public void setGeocodeNS(@Nonnull Array<NameSpace> geocodeNS) {
        this.geocodeNS = geocodeNS;
    }

    @Nonnull
    public Array<NameSpace> getRadiusNS() {
        return radiusNS;
    }

    public void setRadiusNS(@Nonnull Array<NameSpace> radiusNS) {
        this.radiusNS = radiusNS;
    }

    @Nonnull
    public Array<NameSpace> getUnitNS() {
        return unitNS;
    }

    public void setUnitNS(@Nonnull Array<NameSpace> unitNS) {
        this.unitNS = unitNS;
    }

    @Nonnull
    public Array<NameSpace> getUntilNS() {
        return untilNS;
    }

    public void setUntilNS(@Nonnull Array<NameSpace> untilNS) {
        this.untilNS = untilNS;
    }

    @Nonnull
    public Array<NameSpace> getCountNS() {
        return countNS;
    }

    public void setCountNS(@Nonnull Array<NameSpace> countNS) {
        this.countNS = countNS;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.twitterElement();
    }
}
