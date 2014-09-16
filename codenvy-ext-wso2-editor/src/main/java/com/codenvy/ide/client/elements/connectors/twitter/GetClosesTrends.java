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
import static com.codenvy.ide.collections.Collections.createArray;

/**
 * The Class describes GetClosesTrends connector for twitter group of connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class GetClosesTrends extends AbstractConnector {

    public static final String ELEMENT_NAME       = "GetClosesTrends";
    public static final String SERIALIZATION_NAME = "twitter.getClosestTrends";

    private static final String LATITUDE  = "latitude";
    private static final String LONGITUDE = "longitude";

    private static final List<String> PROPERTIES = Arrays.asList(LATITUDE, LONGITUDE);

    private String latitude;
    private String longitude;

    private String latitudeExpr;
    private String longitudeExpr;

    private Array<NameSpace> latitudeNS;
    private Array<NameSpace> longitudeNS;

    @Inject
    public GetClosesTrends(EditorResources resources, Provider<Branch> branchProvider, MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.twitterElement(),
              branchProvider,
              mediatorCreatorsManager);

        latitude = "";
        longitude = "";

        latitudeExpr = "";
        longitudeExpr = "";

        latitudeNS = createArray();
        longitudeNS = createArray();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(LATITUDE, isInline ? latitude : latitudeExpr);
        properties.put(LONGITUDE, isInline ? longitude : longitudeExpr);

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
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
        }
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
    public Array<NameSpace> getLatitudeNS() {
        return latitudeNS;
    }

    public void setLatitudeNS(@Nonnull Array<NameSpace> latitudeNS) {
        this.latitudeNS = latitudeNS;
    }

    @Nonnull
    public Array<NameSpace> getLongitudeNS() {
        return longitudeNS;
    }

    public void setLongitudeNS(@Nonnull Array<NameSpace> longitudeNS) {
        this.longitudeNS = longitudeNS;
    }
}
