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
 * The Class describes GetTopTrendPlaces connector for jira group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class GetTopTrendPlaces extends AbstractConnector {

    public static final String ELEMENT_NAME       = "GetTopTrendPlaces";
    public static final String SERIALIZATION_NAME = "twitter.getTopTrendPlaces";

    public static final Key<String>          ID_INL  = new Key<>("idInl");
    public static final Key<String>          ID_EXPR = new Key<>("idExpr");
    public static final Key<List<NameSpace>> ID_NS   = new Key<>("idNameSpace");

    private static final String ID = "id";

    private static final List<String> PROPERTIES = Arrays.asList(ID);

    @Inject
    public GetTopTrendPlaces(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.twitterElement(),
              branchProvider,
              elementCreatorsManager);

        putProperty(ID_INL, "");
        putProperty(ID_EXPR, "");
        putProperty(ID_NS, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE));

        properties.put(ID, isInline ? getProperty(ID_INL) : getProperty(ID_EXPR));

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        if (ID.equals(node.getNodeName())) {
            adaptProperty(nodeValue, ID_INL, ID_EXPR);
        }
    }
}
