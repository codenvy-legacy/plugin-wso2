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
package com.codenvy.ide.client.elements;

import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.util.StringUtils;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static com.codenvy.ide.client.elements.Header.HeaderAction.remove;
import static com.codenvy.ide.client.elements.Header.HeaderAction.set;
import static com.codenvy.ide.client.elements.Property.ValueType.LITERAL;

/**
 * Class describes entity which presented as Header mediator.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class Header extends RootElement {
    public static final String ELEMENT_NAME       = "Header";
    public static final String SERIALIZATION_NAME = "header";

    private static final String NAME       = "name";
    private static final String VALUE      = "value";
    private static final String EXPRESSION = "expression";

    private static final String PREFIX = "xmlns:";

    private static final List<String> PROPERTIES          = java.util.Collections.emptyList();
    private static final List<String> INTERNAL_PROPERTIES = Arrays.asList(X_PROPERTY_NAME,
                                                                          Y_PROPERTY_NAME,
                                                                          UUID_PROPERTY_NAME);

    private String action;
    private String scope;
    private String valueType;
    private String headerName;
    private String value;
    private String expression;

    private Array<NameSpace> headerNamespaces;
    private Array<NameSpace> expressionNamespaces;

    public Header() {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, INTERNAL_PROPERTIES);

        headerNamespaces = Collections.createArray();
        expressionNamespaces = Collections.createArray();

        action = set.name();
        scope = "default";
        valueType = LITERAL.name();
        value = "header_Value";
        headerName = "To";
        expression = "/default/expression";
    }

    /** @return list of header namespaces of element */
    @Nonnull
    public Array<NameSpace> getHeaderNamespaces() {
        return headerNamespaces;
    }

    /**
     * Sets list of header namespaces to element
     *
     * @param headerNamespaces
     *         list of header namespaces which need to set to element
     */
    public void setHeaderNamespaces(@Nonnull Array<NameSpace> headerNamespaces) {
        this.headerNamespaces = headerNamespaces;
    }

    /** @return list of expression namespaces of element */
    @Nonnull
    public Array<NameSpace> getExpressionNamespaces() {
        return expressionNamespaces;
    }

    /**
     * Sets list of expression namespaces to element
     *
     * @param expressionNamespaces
     *         list of expression namespaces which need to set to element
     */
    public void setExpressionNamespaces(@Nonnull Array<NameSpace> expressionNamespaces) {
        this.expressionNamespaces = expressionNamespaces;
    }

    /** @return value of header */
    @Nullable
    public String getValue() {
        return value;
    }

    /**
     * Sets value to header
     *
     * @param value
     *         value which need to set to element
     */
    public void setValue(@Nullable String value) {
        this.value = value;
    }

    /** @return expression of header */
    @Nullable
    public String getExpression() {
        return expression;
    }

    /**
     * Sets expression to header
     *
     * @param expression
     *         value of expression which need to set to element
     */
    public void setExpression(@Nullable String expression) {
        this.expression = expression;
    }

    /** @return action of header */
    @Nullable
    public String getAction() {
        return action;
    }

    /**
     * Sets action to header
     *
     * @param action
     *         value of action which need to set to element
     */
    public void setAction(@Nullable String action) {
        this.action = action;
    }

    /** @return scope of header */
    @Nullable
    public String getScope() {
        return scope;
    }

    /**
     * Sets scope to header
     *
     * @param scope
     *         value fo scope which need to set to element
     */
    public void setScope(@Nullable String scope) {
        this.scope = scope;
    }

    /** @return type of header */
    @Nullable
    public String getValueType() {
        return valueType;
    }

    /**
     * Sets type to header
     *
     * @param valueType
     *         value of type which need to set to element
     */
    public void setValueType(@Nullable String valueType) {
        this.valueType = valueType;
    }

    /** @return name of header */
    @Nullable
    public String getHeaderName() {
        return headerName;
    }

    /**
     * Sets name to header
     *
     * @param headerName
     *         value of name which need to set to element
     */
    public void setHeaderName(@Nullable String headerName) {
        this.headerName = headerName;
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        LinkedHashMap<String, String> attributes = new LinkedHashMap<>();

        StringBuilder nameSpaces = new StringBuilder();

        if (!headerNamespaces.isEmpty()) {
            NameSpace nameSpace = headerNamespaces.get(headerNamespaces.size() - 1);

            nameSpaces.append(nameSpace.toString()).append(' ');
        }

        for (NameSpace nameSpace : expressionNamespaces.asIterable()) {
            nameSpaces.append(nameSpace.toString()).append(' ');
        }

        setDefaultAttributes(attributes);

        if (action.equalsIgnoreCase(remove.name())) {
            attributes.remove("expression");
            attributes.remove("value");
        }

        switch (HeaderValueType.valueOf(valueType)) {
            case EXPRESSION:
                attributes.remove("value");
                attributes.remove("action");
                break;

            case INLINE:
                attributes.remove("name");
                attributes.remove("expression");
                attributes.remove("value");
                attributes.remove("action");

                return prepareSerialization(attributes);

            case LITERAL:
                attributes.remove("expression");
                attributes.remove(action.equalsIgnoreCase(set.name()) ? "action" : "value");
                break;
        }

        return nameSpaces.toString() + prepareSerialization(attributes);
    }

    /**
     * Sets default value of attributes of element
     *
     * @param attributes
     *         list of attributes which need to set to element by default
     */
    private void setDefaultAttributes(@Nonnull LinkedHashMap<String, String> attributes) {
        attributes.put("name", headerName);
        attributes.put("scope", scope);
        attributes.put("value", value);
        attributes.put("expression", expression);
        attributes.put("action", action);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyAttributes(@Nonnull Node node) {
        NamedNodeMap attributeMap = node.getAttributes();
        String uriName = "";

        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attributeNode = attributeMap.item(i);

            switch (attributeNode.getNodeName()) {
                case HeaderAction.ACTION:
                    action = attributeNode.getNodeValue();
                    break;

                case ScopeType.SCOPE:
                    scope = attributeNode.getNodeValue();
                    break;

                case VALUE:
                    value = attributeNode.getNodeValue();
                    break;

                case EXPRESSION:
                    expression = attributeNode.getNodeValue();
                    break;

                case NAME:
                    headerName = attributeNode.getNodeValue();
                    Array<String> attrName = StringUtils.split(attributeNode.getNodeName(), ":");
                    uriName = attrName.get(0);
                    break;

                case PREFIX:
                    String uri = attributeNode.getNodeValue();

                    //TODO create entity using edit factory
                    NameSpace nameSpace = new NameSpace(null, null);
                    nameSpace.applyAttributes(node);

                    if (uri.equals(uriName)) {
                        headerNamespaces.add(nameSpace);
                    } else {
                        expressionNamespaces.add(nameSpace);
                    }
                    break;
            }
        }
    }

    public enum HeaderAction {
        set, remove;

        public static final String TYPE_NAME = "HeaderAction";
        public static final String ACTION    = "action";
    }

    public enum HeaderValueType {
        LITERAL, EXPRESSION, INLINE;

        public static final String TYPE_NAME = "HeaderValueType";
    }

    public enum ScopeType {
        Synapse, transport;

        public static final String TYPE_NAME = "ScopeType";
        public static final String SCOPE     = "scope";
    }

}