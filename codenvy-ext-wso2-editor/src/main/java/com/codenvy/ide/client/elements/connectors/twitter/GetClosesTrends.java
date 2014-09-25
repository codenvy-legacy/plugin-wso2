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

    public static final Key<String>          LATITUDE_INL   = new Key<>("latitudeInl");
    public static final Key<String>          LONGITUDE_INL  = new Key<>("longitudeInl");
    public static final Key<String>          LATITUDE_EXPR  = new Key<>("latitudeExpr");
    public static final Key<String>          LONGITUDE_EXPR = new Key<>("longitudeExpr");
    public static final Key<List<NameSpace>> LATITUDE_NS    = new Key<>("latitudeNameSpace");
    public static final Key<List<NameSpace>> LONGITUDE_NS   = new Key<>("longitudeNameSpace");

    private static final String LATITUDE  = "latitude";
    private static final String LONGITUDE = "longitude";

    private static final List<String> PROPERTIES = Arrays.asList(LATITUDE, LONGITUDE);

    @Inject
    public GetClosesTrends(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.twitterElement(),
              branchProvider,
              elementCreatorsManager);

        putProperty(LATITUDE_INL, "");
        putProperty(LONGITUDE_INL, "");
        putProperty(LATITUDE_EXPR, "");
        putProperty(LONGITUDE_EXPR, "");
        putProperty(LATITUDE_NS, new ArrayList<NameSpace>());
        putProperty(LONGITUDE_NS, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE));

        properties.put(LATITUDE, isInline ? getProperty(LATITUDE_INL) : getProperty(LATITUDE_EXPR));
        properties.put(LONGITUDE, isInline ? getProperty(LONGITUDE_INL) : getProperty(LONGITUDE_EXPR));

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        if (LATITUDE.equals(nodeName)) {
            adaptProperty(nodeValue, LATITUDE_INL, LATITUDE_EXPR);
        }

        if (LONGITUDE.equals(nodeName)) {
            adaptProperty(nodeValue, LONGITUDE_INL, LONGITUDE_EXPR);
        }
    }
}
