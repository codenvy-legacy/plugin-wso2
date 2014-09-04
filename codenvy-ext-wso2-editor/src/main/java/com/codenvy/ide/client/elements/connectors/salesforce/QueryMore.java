/*
 * Copyright 2014 Codenvy, S.A.
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
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.elements.connectors.salesforce.AbstractSalesForceConnector.ParameterEditorType.Inline;
import static com.codenvy.ide.client.elements.connectors.salesforce.AbstractSalesForceConnector.ParameterEditorType
        .NamespacedPropertyEditor;

/**
 * The Class describes QueryMore connector for Salesforce group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 */
public class QueryMore extends AbstractSalesForceConnector {

    public static final String ELEMENT_NAME       = "QueryMore";
    public static final String SERIALIZATION_NAME = "salesforce.queryMore";
    public static final String BATCH_SIZE         = "batchSize";

    private static final List<String> PROPERTIES = Arrays.asList(BATCH_SIZE);

    private String              batchSize;
    private String              batchSizeExpr;
    private ParameterEditorType parameterEditorType;
    private Array<NameSpace>    batchSizeNameSpaces;

    @Inject
    public QueryMore(EditorResources resources, Provider<Branch> branchProvider, MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        batchSize = "";
        batchSizeExpr = "";

        parameterEditorType = Inline;

        batchSizeNameSpaces = Collections.createArray();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        return Inline.equals(parameterEditorType) ? convertPropertiesToXml(batchSize)
                                                  : convertPropertiesToXml(batchSizeExpr);
    }

    private String convertPropertiesToXml(@Nonnull String batchSize) {
        StringBuilder result = new StringBuilder("");

        if (!batchSize.isEmpty()) {
            result.append('<').append(BATCH_SIZE).append('>').append(batchSize).append("</").append(BATCH_SIZE).append('>');
        }

        return result.toString();
    }

    /** {@inheritDoc} */
    @Override
    public void applyProperty(@Nonnull Node node) {
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
    public Array<NameSpace> getBatchSizeNameSpaces() {
        return batchSizeNameSpaces;
    }

    public void setBatchSizeNameSpaces(@Nonnull Array<NameSpace> batchSizeNameSpaces) {
        this.batchSizeNameSpaces = batchSizeNameSpaces;
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

}
