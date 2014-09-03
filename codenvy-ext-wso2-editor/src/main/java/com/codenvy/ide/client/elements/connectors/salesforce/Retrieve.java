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
import com.google.gwt.xml.client.NamedNodeMap;
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
 * The Class describes Retrieve connector for Salesforce group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 */
public class Retrieve extends AbstractShape {

    public static final String ELEMENT_NAME       = "Retrieve";
    public static final String SERIALIZATION_NAME = "salesforce.retrieve";
    public static final String CONFIG_KEY         = "configKey";
    public static final String FIELD_LIST         = "fieldList";
    public static final String OBJECT_TYPE        = "objectType";
    public static final String OBJECT_IDS         = "objectIDS";

    private static final List<String> PROPERTIES = Arrays.asList(FIELD_LIST, OBJECT_TYPE, OBJECT_IDS);

    private String              configRef;
    private String              fieldList;
    private String              objectType;
    private String              objectIDS;
    private String              fieldListInline;
    private String              objectTypeInline;
    private String              objectIDSInline;
    private ParameterEditorType parameterEditorType;
    private Array<NameSpace>    objectTypeNameSpaces;
    private Array<NameSpace>    objectIDSNameSpaces;
    private Array<NameSpace>    fieldListNameSpaces;

    @Inject
    public Retrieve(EditorResources resources, Provider<Branch> branchProvider, MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        fieldList = "";
        objectType = "";
        objectIDS = "";
        fieldListInline = "";
        objectTypeInline = "";
        objectIDSInline = "";

        parameterEditorType = ParameterEditorType.Inline;

        fieldListNameSpaces = createArray();
        objectTypeNameSpaces = createArray();
        objectIDSNameSpaces = createArray();
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
        return Inline.equals(parameterEditorType) ? convertPropertiesToXml(fieldListInline, objectTypeInline, objectIDSInline)
                                                  : convertPropertiesToXml(fieldList, objectType, objectIDS);
    }

    @Nonnull
    private String convertPropertiesToXml(@Nonnull String fieldList,
                                          @Nonnull String objectType,
                                          @Nonnull String objectIDS) {
        StringBuilder result = new StringBuilder();

        if (!fieldList.isEmpty()) {
            result.append('<').append(FIELD_LIST).append('>').append(fieldList).append("</").append(FIELD_LIST).append('>');
        }

        if (!objectType.isEmpty()) {
            result.append('<').append(OBJECT_TYPE).append('>').append(objectType).append("</").append(OBJECT_TYPE).append('>');
        }

        if (!objectIDS.isEmpty()) {
            result.append('<').append(OBJECT_IDS).append('>').append(objectIDS).append("</").append(OBJECT_IDS).append('>');
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
            case FIELD_LIST:
                if (isInline) {
                    fieldListInline = nodeValue;
                } else {
                    fieldList = nodeValue;
                }
                break;

            case OBJECT_TYPE:
                if (isInline) {
                    objectTypeInline = nodeValue;
                } else {
                    objectType = nodeValue;
                }
                break;

            case OBJECT_IDS:
                if (isInline) {
                    objectIDSInline = nodeValue;
                } else {
                    objectIDS = nodeValue;
                }
                break;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void applyAttributes(@Nonnull Node node) {
        NamedNodeMap attributeMap = node.getAttributes();

        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attributeNode = attributeMap.item(i);

            String attributeValue = attributeNode.getNodeValue();

            switch (attributeNode.getNodeName()) {
                case CONFIG_KEY:
                    configRef = attributeValue;
                    break;
            }
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
    public String getFieldList() {
        return fieldList;
    }

    public void setFieldList(@Nullable String fieldList) {
        this.fieldList = fieldList;
    }

    @Nonnull
    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(@Nullable String objectType) {
        this.objectType = objectType;
    }

    @Nonnull
    public String getObjectIDS() {
        return objectIDS;
    }

    public void setObjectIDS(@Nullable String objectIDS) {
        this.objectIDS = objectIDS;
    }

    @Nonnull
    public String getFieldListInline() {
        return fieldListInline;
    }

    public void setFieldListInline(@Nonnull String fieldListInline) {
        this.fieldListInline = fieldListInline;
    }

    @Nonnull
    public String getObjectTypeInline() {
        return objectTypeInline;
    }

    public void setObjectTypeInline(@Nonnull String objectTypeInline) {
        this.objectTypeInline = objectTypeInline;
    }

    @Nonnull
    public String getObjectIDSInline() {
        return objectIDSInline;
    }

    public void setObjectIDSInline(@Nonnull String objectIDSInline) {
        this.objectIDSInline = objectIDSInline;
    }

    @Nonnull
    public Array<NameSpace> getFieldListNameSpaces() {
        return fieldListNameSpaces;
    }

    public void setFieldListNameSpaces(@Nonnull Array<NameSpace> fieldListNameSpaces) {
        this.fieldListNameSpaces = fieldListNameSpaces;
    }

    @Nonnull
    public Array<NameSpace> getObjectTypeNameSpaces() {
        return objectTypeNameSpaces;
    }

    public void setObjectTypeNameSpaces(@Nonnull Array<NameSpace> objectTypeNameSpaces) {
        this.objectTypeNameSpaces = objectTypeNameSpaces;
    }

    @Nonnull
    public Array<NameSpace> getObjectIDSNameSpaces() {
        return objectIDSNameSpaces;
    }

    public void setObjectIDSNameSpaces(@Nonnull Array<NameSpace> objectIDSNameSpaces) {
        this.objectIDSNameSpaces = objectIDSNameSpaces;
    }

    @Nonnull
    public ParameterEditorType getParameterEditorType() {
        return parameterEditorType;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.salesforce();
    }
}
