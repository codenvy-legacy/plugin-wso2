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

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.Inline;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NamespacedPropertyEditor;

/**
 * The Class describes Query connector for QueryAll group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class QueryAll extends AbstractConnector {

    public static final String ELEMENT_NAME       = "QueryAll";
    public static final String SERIALIZATION_NAME = "salesforce.queryAll";

    private static final String BATCH_SIZE   = "batchSize";
    private static final String QUERY_STRING = "queryString";

    private static final List<String> PROPERTIES = Arrays.asList(BATCH_SIZE, QUERY_STRING);

    private String              batchSize;
    private String              queryString;
    private String              batchSizeExpr;
    private String              queryStringExpr;
    private ParameterEditorType parameterEditorType;
    private List<NameSpace>     batchSizeNameSpaces;
    private List<NameSpace>     queryStringNameSpaces;

    @Inject
    public QueryAll(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.salesforce(),
              branchProvider,
              elementCreatorsManager);

        batchSize = "";
        queryString = "";
        batchSizeExpr = "";
        queryStringExpr = "";

        parameterEditorType = Inline;

        batchSizeNameSpaces = new ArrayList<>();
        queryStringNameSpaces = new ArrayList<>();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(BATCH_SIZE, isInline ? batchSize : batchSizeExpr);
        properties.put(QUERY_STRING, isInline ? queryString : queryStringExpr);

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case BATCH_SIZE:
                if (isInline) {
                    batchSize = nodeValue;
                } else {
                    batchSizeExpr = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case QUERY_STRING:
                if (isInline) {
                    queryString = nodeValue;
                } else {
                    queryStringExpr = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

        }
    }

    @Nonnull
    public String getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(@Nonnull String batchSize) {
        this.batchSize = batchSize;
    }

    @Nonnull
    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(@Nonnull String queryString) {
        this.queryString = queryString;
    }

    @Nonnull
    public List<NameSpace> getBatchSizeNameSpaces() {
        return batchSizeNameSpaces;
    }

    public void setBatchSizeNameSpaces(@Nonnull List<NameSpace> batchSizeNameSpaces) {
        this.batchSizeNameSpaces = batchSizeNameSpaces;
    }

    @Nonnull
    public List<NameSpace> getQueryStringNameSpaces() {
        return queryStringNameSpaces;
    }

    public void setQueryStringNameSpaces(@Nonnull List<NameSpace> queryStringNameSpaces) {
        this.queryStringNameSpaces = queryStringNameSpaces;
    }

    @Nonnull
    public ParameterEditorType getParameterEditorType() {
        return parameterEditorType;
    }

    public void setParameterEditorType(@Nonnull ParameterEditorType parameterEditorType) {
        this.parameterEditorType = parameterEditorType;
    }

    @Nonnull
    public String getBatchSizeExpr() {
        return batchSizeExpr;
    }

    public void setBatchSizeExpr(@Nonnull String batchSizeExpr) {
        this.batchSizeExpr = batchSizeExpr;
    }

    @Nonnull
    public String getQueryStringExpr() {
        return queryStringExpr;
    }

    public void setQueryStringExpr(@Nonnull String queryStringExpr) {
        this.queryStringExpr = queryStringExpr;
    }

}
