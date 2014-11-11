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

    public static final Key<String> FIELD_LIST_KEY  = new Key<>("FieldList");
    public static final Key<String> OBJECT_TYPE_KEY = new Key<>("ObjectType");
    public static final Key<String> OBJECT_IDS_KEY  = new Key<>("ObjectIDS");

    public static final Key<String> FIELD_LIST_EXPRESSION_KEY  = new Key<>("FieldListExpression");
    public static final Key<String> OBJECT_TYPE_EXPRESSION_KEY = new Key<>("ObjectTypeExpression");
    public static final Key<String> OBJECT_IDS_EXPRESSION_KEY  = new Key<>("ObjectIDSExpression");

    public static final Key<List<NameSpace>> FIELD_LIST_NS_KEY  = new Key<>("FieldListNS");
    public static final Key<List<NameSpace>> OBJECT_TYPE_NS_KEY = new Key<>("ObjectTypeNS");
    public static final Key<List<NameSpace>> OBJECT_IDS_NS_KEY  = new Key<>("ObjectIDSNS");

    private static final String FIELD_LIST  = "fieldList";
    private static final String OBJECT_TYPE = "objectType";
    private static final String OBJECT_IDS  = "objectIDS";

    private static final List<String> PROPERTIES = Arrays.asList(FIELD_LIST, OBJECT_TYPE, OBJECT_IDS);

    @Inject
    public Retrieve(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              resources.salesforce(),
              branchProvider,
              elementCreatorsManager);

        putProperty(FIELD_LIST_KEY, "");
        putProperty(OBJECT_TYPE_KEY, "");
        putProperty(OBJECT_IDS_KEY, "");

        putProperty(FIELD_LIST_EXPRESSION_KEY, "");
        putProperty(OBJECT_TYPE_EXPRESSION_KEY, "");
        putProperty(OBJECT_IDS_EXPRESSION_KEY, "");

        putProperty(FIELD_LIST_NS_KEY, new ArrayList<NameSpace>());
        putProperty(OBJECT_TYPE_NS_KEY, new ArrayList<NameSpace>());
        putProperty(OBJECT_IDS_NS_KEY, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE));

        properties.put(FIELD_LIST, isInline ? getProperty(FIELD_LIST_KEY) : getProperty(FIELD_LIST_EXPRESSION_KEY));
        properties.put(OBJECT_TYPE, isInline ? getProperty(OBJECT_TYPE_KEY) : getProperty(OBJECT_TYPE_EXPRESSION_KEY));
        properties.put(OBJECT_IDS, isInline ? getProperty(OBJECT_IDS_KEY) : getProperty(OBJECT_IDS_EXPRESSION_KEY));

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        if (FIELD_LIST.equals(nodeName)) {
            adaptProperty(nodeValue, FIELD_LIST_KEY, FIELD_LIST_EXPRESSION_KEY);
        }

        if (OBJECT_TYPE.equals(nodeName)) {
            adaptProperty(nodeValue, OBJECT_TYPE_KEY, OBJECT_TYPE_EXPRESSION_KEY);
        }

        if (OBJECT_IDS.equals(nodeName)) {
            adaptProperty(nodeValue, OBJECT_IDS_KEY, OBJECT_IDS_EXPRESSION_KEY);
        }
    }

}