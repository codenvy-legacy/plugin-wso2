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
package com.codenvy.ide.client.elements.mediators.log;

import com.codenvy.ide.client.elements.AbstractEntityElement;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.mediators.ValueType;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.codenvy.ide.client.elements.NameSpace.applyNameSpace;
import static com.codenvy.ide.client.elements.NameSpace.convertNameSpacesToXML;
import static com.codenvy.ide.client.elements.NameSpace.copyNameSpaceList;
import static com.codenvy.ide.client.elements.mediators.ValueType.LITERAL;

/**
 * The class which describes state of property element of Log mediator and also has methods for changing it. Also the class contains the
 * business logic that allows to display serialization representation depending of the current state of element. Deserelization mechanism
 * allows to restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class Property extends AbstractEntityElement {

    public static final Key<String>          NAME       = new Key<>("MediatorPropertyName");
    public static final Key<ValueType>       TYPE       = new Key<>("MediatorPropertyType");
    public static final Key<String>          VALUE      = new Key<>("MediatorPropertyValue");
    public static final Key<String>          EXPRESSION = new Key<>("MediatorPropertyExpression");
    public static final Key<List<NameSpace>> NAMESPACES = new Key<>("MediatorPropertyNameSpaces");

    private static final String NAME_ATTRIBUTE       = "name";
    private static final String VALUE_ATTRIBUTE      = "value";
    private static final String EXPRESSION_ATTRIBUTE = "expression";

    private static final String WITH_PARAM_SERIALIZATION_NAME = "with-param";
    private static final String PROPERTY_SERIALIZATION_NAME   = "property";

    private final Provider<Property>  propertyProvider;
    private final Provider<NameSpace> nameSpaceProvider;

    @Inject
    public Property(Provider<Property> propertyProvider, Provider<NameSpace> nameSpaceProvider) {
        this.propertyProvider = propertyProvider;
        this.nameSpaceProvider = nameSpaceProvider;

        putProperty(NAME, "property_name");
        putProperty(TYPE, LITERAL);
        putProperty(VALUE, "property_value");
        putProperty(EXPRESSION, "/default/expression");
        putProperty(NAMESPACES, new ArrayList<NameSpace>());
    }

    /** Returns serialization representation CallTemplate element's property. */
    @Nonnull
    public String serializeWithParam() {
        return serializePropertyParameter(WITH_PARAM_SERIALIZATION_NAME);

    }

    /** Returns serialization representation element's property. */
    @Nonnull
    public String serializeProperty() {
        return serializePropertyParameter(PROPERTY_SERIALIZATION_NAME);
    }

    private String serializePropertyParameter(@Nonnull String propertyName) {
        ValueType type = getProperty(TYPE);

        StringBuilder result = new StringBuilder();

        result.append('<').append(propertyName).append(" name=\"").append(getProperty(NAME)).append('"');

        if (LITERAL.equals(type)) {
            result.append(" value=\"").append(getProperty(VALUE));
        } else {
            result.append(' ').append(convertNameSpacesToXML(getProperty(NAMESPACES))).append("expression=\"")
                  .append(getProperty(EXPRESSION));
        }

        result.append("\"/>");

        return result.toString();
    }

    /**
     * Apply attributes from XML node to the diagram element
     *
     * @param node
     *         XML node that need to be analyzed
     */
    public void applyAttributes(@Nonnull Node node) {
        NamedNodeMap attributeMap = node.getAttributes();

        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attributeNode = attributeMap.item(i);

            String nodeName = attributeNode.getNodeName();
            String nodeValue = attributeNode.getNodeValue();

            switch (nodeName) {
                case NAME_ATTRIBUTE:
                    putProperty(NAME, nodeValue);
                    break;

                case EXPRESSION_ATTRIBUTE:
                    putProperty(EXPRESSION, nodeValue);
                    putProperty(TYPE, ValueType.EXPRESSION);
                    break;

                case VALUE_ATTRIBUTE:
                    putProperty(VALUE, nodeValue);
                    break;

                default:
                    applyNameSpace(nameSpaceProvider, getProperty(NAMESPACES), nodeName, nodeValue);
            }
        }
    }

    /** Returns copy of element. */
    public Property copy() {
        Property property = propertyProvider.get();

        property.putProperty(NAME, getProperty(NAME));
        property.putProperty(TYPE, getProperty(TYPE));
        property.putProperty(VALUE, getProperty(VALUE));
        property.putProperty(EXPRESSION, getProperty(EXPRESSION));
        property.putProperty(NAMESPACES, copyNameSpaceList(getProperty(NAMESPACES)));

        return property;
    }

    /**
     * Returns copy of list. If list which we send to method is null, method return empty list. If list isn't null
     * method returns copy of list.
     *
     * @param listToCopy
     *         list which need to copy
     */
    public static List<Property> copyPropertyList(@Nullable List<Property> listToCopy) {
        List<Property> properties = new ArrayList<>();

        if (listToCopy == null) {
            return properties;
        }

        for (Property property : listToCopy) {
            properties.add(property);
        }

        return properties;
    }

}
