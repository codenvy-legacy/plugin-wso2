/*
 * Copyright [2014] Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.client.elements.connectors.salesforce;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.AbstractShape;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
import com.codenvy.ide.collections.Array;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.elements.connectors.salesforce.AbstractSalesForceConnector.ParameterEditorType;
import static com.codenvy.ide.client.elements.connectors.salesforce.AbstractSalesForceConnector.ParameterEditorType.Inline;
import static com.codenvy.ide.collections.Collections.createArray;

/**
 * The Class describes Search connector for Salesforce group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 */
public class Search extends AbstractShape {

    public static final String ELEMENT_NAME       = "Search";
    public static final String SERIALIZATION_NAME = "salesforce.search";
    public static final String SEARCH_STRING      = "searchString";
    public static final String CONFIG_KEY         = "configKey";

    private static final List<String> PROPERTIES = Arrays.asList(SEARCH_STRING);

    private String              configRef;
    private String              searchString;
    private String              searchStringInline;
    private ParameterEditorType parameterEditorType;
    private Array<NameSpace>    searchStringNameSpaces;

    @Inject
    public Search(EditorResources resources, Provider<Branch> branchProvider, MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        searchString = "";
        searchStringInline = "";

        searchStringNameSpaces = createArray();

        parameterEditorType = ParameterEditorType.Inline;
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        return configRef == null || configRef.isEmpty() ? "" : CONFIG_KEY + "=\"" + configRef + "\"";
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        return Inline.equals(parameterEditorType) ? prepareProperties(searchStringInline) : prepareProperties(searchString);
    }

    @Nonnull
    private String prepareProperties(@Nonnull String subject) {
        return !subject.isEmpty() ? '<' + SEARCH_STRING + '>' + this.searchString + "</" + SEARCH_STRING + '>' : "";
    }

    /** {@inheritDoc} */
    @Override
    public void applyProperty(@Nonnull Node node) {
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

    /** {@inheritDoc} */
    @Override
    public void applyAttributes(@Nonnull Node node) {
        if (node.hasAttributes()) {
            Node attribute = node.getAttributes().item(0);

            configRef = attribute.getNodeValue();
        }
    }

    public void setParameterEditorType(@Nonnull ParameterEditorType parameterEditorType) {
        this.parameterEditorType = parameterEditorType;
    }

    @Nonnull
    public String getConfigRef() {
        return configRef;
    }

    public void setConfigRef(@Nonnull String configRef) {
        this.configRef = configRef;
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
    public ParameterEditorType getParameterEditorType() {
        return parameterEditorType;
    }

    @Nonnull
    public Array<NameSpace> getSearchStringNameSpaces() {
        return searchStringNameSpaces;
    }

    public void setSearchStringNameSpaces(@Nonnull Array<NameSpace> searchStringNameSpaces) {
        this.searchStringNameSpaces = searchStringNameSpaces;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.salesforce();
    }
}
