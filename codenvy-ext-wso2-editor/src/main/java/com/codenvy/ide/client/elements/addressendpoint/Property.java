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
package com.codenvy.ide.client.elements.addressendpoint;

import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.ValueType;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.util.StringUtils;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.NameSpace.PREFIX;
import static com.codenvy.ide.client.elements.ValueType.EXPRESSION;
import static com.codenvy.ide.client.elements.ValueType.LITERAL;
import static com.codenvy.ide.client.elements.addressendpoint.Property.Scope.DEFAULT;

/**
 * The entity that represents property of 'Address' endpoint from ESB configuration.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class Property {

    public static final String SERIALIZE_NAME = "property";

    private static final String NAME_ATTRIBUTE       = "name";
    private static final String VALUE_ATTRIBUTE      = "value";
    private static final String EXPRESSION_ATTRIBUTE = "expression";
    private static final String SCOPE_ATTRIBUTE      = "scope";

    private final Provider<Property> propertyProvider;

    private String           name;
    private String           value;
    private String           expression;
    private ValueType        type;
    private Scope            scope;
    private Array<NameSpace> nameSpaces;

    @Inject
    public Property(Provider<Property> propertyProvider) {
        this.propertyProvider = propertyProvider;

        name = "property_name";
        value = "property_value";
        expression = "/default/expression";
        type = LITERAL;
        scope = DEFAULT;
        nameSpaces = Collections.createArray();
    }

    /** @return name of property of address endpoint */
    @Nonnull
    public String getName() {
        return name;
    }

    /**
     * Sets name to property element of address endpoint.
     *
     * @param name
     *         value that should be set
     */
    public void setName(@Nonnull String name) {
        this.name = name;
    }

    /** @return value of property of address endpoint */
    @Nonnull
    public String getValue() {
        return value;
    }

    /**
     * Sets value to property element of address endpoint.
     *
     * @param value
     *         value that should be set
     */
    public void setValue(@Nonnull String value) {
        this.value = value;
    }

    /** @return expression of property of address endpoint */
    @Nonnull
    public String getExpression() {
        return expression;
    }

    /**
     * Sets expression to property element of address endpoint.
     *
     * @param expression
     *         value that should be set
     */
    public void setExpression(@Nonnull String expression) {
        this.expression = expression;
    }

    /** @return type of property of address endpoint */
    @Nonnull
    public ValueType getType() {
        return type;
    }

    /**
     * Sets type to property element of address endpoint.
     *
     * @param type
     *         value that should be set
     */
    public void setType(@Nonnull ValueType type) {
        this.type = type;
    }

    /** @return scope of property of address endpoint */
    @Nonnull
    public Scope getScope() {
        return scope;
    }

    /**
     * Sets scope to property element of address endpoint.
     *
     * @param scope
     *         value that should be set
     */
    public void setScope(@Nonnull Scope scope) {
        this.scope = scope;
    }

    /** @return list of name spaces of property of address endpoint */
    @Nonnull
    public Array<NameSpace> getNameSpaces() {
        return nameSpaces;
    }

    /**
     * Sets list of name spaces to property element of address endpoint.
     *
     * @param nameSpaces
     *         list that should be set
     */
    public void setNameSpaces(@Nonnull Array<NameSpace> nameSpaces) {
        this.nameSpaces = nameSpaces;
    }

    /** @return copy of property */
    @Nonnull
    public Property clone() {
        Property property = propertyProvider.get();

        property.setName(name);
        property.setExpression(expression);
        property.setValue(value);
        property.setType(type);
        property.setScope(scope);
        property.setNameSpaces(nameSpaces);

        return property;
    }

    /** @return a serialize representation of Endpoint property */
    @Nonnull
    public String serialize() {
        String startTag = '<' + SERIALIZE_NAME + ' ';
        String nameAttr = NAME_ATTRIBUTE + "=\"" + name + "\" ";
        String scope = (DEFAULT.equals(this.scope) ? "" : ' ' + SCOPE_ATTRIBUTE + "=\"" + this.scope.getValue() + '"');

        if (LITERAL.equals(type)) {
            return startTag + nameAttr +
                   VALUE_ATTRIBUTE + "=\"" + value + '"' +
                   scope + "/>\n";
        }

        StringBuilder nameSpaces = new StringBuilder();

        for (NameSpace nameSpace : this.nameSpaces.asIterable()) {
            nameSpaces.append(nameSpace.toString()).append(' ');
        }

        return startTag + nameSpaces + nameAttr +
               EXPRESSION_ATTRIBUTE + "=\"" + expression + "\" " +
               scope + "/>\n";
    }

    /**
     * Apply attributes from XML node to the diagram element.
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
                    name = nodeValue;
                    break;

                case VALUE_ATTRIBUTE:
                    value = nodeValue;
                    type = LITERAL;
                    break;

                case EXPRESSION_ATTRIBUTE:
                    expression = nodeValue;
                    type = EXPRESSION;
                    break;

                case SCOPE_ATTRIBUTE:
                    scope = Scope.getItemByValue(nodeValue);
                    break;

                default:
                    if (StringUtils.startsWith(PREFIX, nodeName, true)) {
                        String name = StringUtils.trimStart(nodeName, PREFIX + ':');
                        //TODO create entity using edit factory
                        NameSpace nameSpace = new NameSpace(name, nodeValue);

                        nameSpaces.add(nameSpace);
                    }
            }
        }
    }

    public enum Scope {
        DEFAULT("default"), TRANSPORT("transport"), AXIS2("axis2"), AXIS2_CLIENT("axis2_client");

        public static final String TYPE_NAME = "EndpointScopeType";

        private final String value;

        Scope(@Nonnull String value) {
            this.value = value;
        }

        @Nonnull
        public String getValue() {
            return value;
        }

        @Nonnull
        public static Scope getItemByValue(String value) {
            switch (value) {
                case "transport":
                    return TRANSPORT;

                case "axis2":
                    return AXIS2;

                case "axis2_client":
                    return AXIS2_CLIENT;

                default:
                case "default":
                    return DEFAULT;
            }
        }
    }

}