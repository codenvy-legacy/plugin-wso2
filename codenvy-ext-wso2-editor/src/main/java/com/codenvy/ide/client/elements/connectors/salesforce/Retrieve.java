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
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.Inline;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NamespacedPropertyEditor;

/**
 * The Class describes Retrieve connector for Salesforce group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class Retrieve extends AbstractConnector {

    public static final String ELEMENT_NAME       = "Retrieve";
    public static final String SERIALIZATION_NAME = "salesforce.retrieve";

    private static final String FIELD_LIST  = "fieldList";
    private static final String OBJECT_TYPE = "objectType";
    private static final String OBJECT_IDS  = "objectIDS";

    private static final List<String> PROPERTIES = Arrays.asList(FIELD_LIST, OBJECT_TYPE, OBJECT_IDS);

    private String          fieldList;
    private String          objectType;
    private String          objectIDS;
    private String          fieldListInline;
    private String          objectTypeInline;
    private String          objectIDSInline;
    private List<NameSpace> objectTypeNameSpaces;
    private List<NameSpace> objectIDSNameSpaces;
    private List<NameSpace> fieldListNameSpaces;

    @Inject
    public Retrieve(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.salesforce(),
              branchProvider,
              elementCreatorsManager);

        fieldList = "";
        objectType = "";
        objectIDS = "";
        fieldListInline = "";
        objectTypeInline = "";
        objectIDSInline = "";

        fieldListNameSpaces = new ArrayList<>();
        objectTypeNameSpaces = new ArrayList<>();
        objectIDSNameSpaces = new ArrayList<>();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(FIELD_LIST, isInline ? fieldListInline : fieldList);
        properties.put(OBJECT_TYPE, isInline ? objectTypeInline : objectType);
        properties.put(OBJECT_IDS, isInline ? objectIDSInline : objectIDS);

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();
        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case FIELD_LIST:
                if (isInline) {
                    fieldListInline = nodeValue;
                } else {
                    fieldList = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case OBJECT_TYPE:
                if (isInline) {
                    objectTypeInline = nodeValue;
                } else {
                    objectType = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case OBJECT_IDS:
                if (isInline) {
                    objectIDSInline = nodeValue;
                } else {
                    objectIDS = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;
        }
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
    public List<NameSpace> getFieldListNameSpaces() {
        return fieldListNameSpaces;
    }

    public void setFieldListNameSpaces(@Nonnull List<NameSpace> fieldListNameSpaces) {
        this.fieldListNameSpaces = fieldListNameSpaces;
    }

    @Nonnull
    public List<NameSpace> getObjectTypeNameSpaces() {
        return objectTypeNameSpaces;
    }

    public void setObjectTypeNameSpaces(@Nonnull List<NameSpace> objectTypeNameSpaces) {
        this.objectTypeNameSpaces = objectTypeNameSpaces;
    }

    @Nonnull
    public List<NameSpace> getObjectIDSNameSpaces() {
        return objectIDSNameSpaces;
    }

    public void setObjectIDSNameSpaces(@Nonnull List<NameSpace> objectIDSNameSpaces) {
        this.objectIDSNameSpaces = objectIDSNameSpaces;
    }
}
