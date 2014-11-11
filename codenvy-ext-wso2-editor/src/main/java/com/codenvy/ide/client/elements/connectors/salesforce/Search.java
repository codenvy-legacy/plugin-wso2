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
package com.codenvy.ide.client.elements.connectors.salesforce;

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
 * The Class describes Search connector for Salesforce group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class Search extends AbstractConnector {

    public static final String ELEMENT_NAME       = "Search";
    public static final String SERIALIZATION_NAME = "salesforce.search";

    public static final Key<String>          SEARCH_STRING_KEY            = new Key<>("SearchString");
    public static final Key<String>          SEARCH_STRING_EXPRESSION_KEY = new Key<>("SearchStringExpression");
    public static final Key<List<NameSpace>> SEARCH_STRING_NS_KEY         = new Key<>("SearchStringNS");

    private static final String SEARCH_STRING = "searchString";

    private static final List<String> PROPERTIES = Arrays.asList(SEARCH_STRING);

    @Inject
    public Search(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              resources.salesforce(),
              branchProvider,
              elementCreatorsManager);

        putProperty(SEARCH_STRING_KEY, "");
        putProperty(SEARCH_STRING_EXPRESSION_KEY, "");
        putProperty(SEARCH_STRING_NS_KEY, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE));

        properties.put(SEARCH_STRING, isInline ? getProperty(SEARCH_STRING_KEY) : getProperty(SEARCH_STRING_EXPRESSION_KEY));

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        if (SEARCH_STRING.equals(nodeName)) {
            adaptProperty(nodeValue, SEARCH_STRING_KEY, SEARCH_STRING_EXPRESSION_KEY);
        }
    }

}