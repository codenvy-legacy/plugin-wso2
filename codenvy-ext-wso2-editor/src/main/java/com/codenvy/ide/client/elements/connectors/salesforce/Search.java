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
import com.codenvy.ide.collections.Array;
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
import static com.codenvy.ide.collections.Collections.createArray;

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

    private static final String SEARCH_STRING = "searchString";

    private static final List<String> PROPERTIES = Arrays.asList(SEARCH_STRING);

    private String           searchString;
    private String           searchStringInline;
    private Array<NameSpace> searchStringNameSpaces;

    @Inject
    public Search(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.salesforce(),
              branchProvider,
              elementCreatorsManager);

        searchString = "";
        searchStringInline = "";

        searchStringNameSpaces = createArray();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(SEARCH_STRING, isInline ? searchStringInline : searchString);

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();
        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case SEARCH_STRING:
                if (isInline) {
                    searchStringInline = nodeValue;
                } else {
                    searchString = nodeValue;
                }
                break;
        }
    }

    @Nonnull
    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(@Nullable String subject) {
        this.searchString = subject;
    }

    @Nonnull
    public String getSearchStringInline() {
        return searchStringInline;
    }

    public void setSearchStringInline(@Nonnull String searchStringInline) {
        this.searchStringInline = searchStringInline;
    }

    @Nonnull
    public Array<NameSpace> getSearchStringNameSpaces() {
        return searchStringNameSpaces;
    }

    public void setSearchStringNameSpaces(@Nonnull Array<NameSpace> searchStringNameSpaces) {
        this.searchStringNameSpaces = searchStringNameSpaces;
    }
}
