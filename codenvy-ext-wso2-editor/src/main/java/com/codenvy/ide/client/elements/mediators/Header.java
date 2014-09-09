/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package com.codenvy.ide.client.elements.mediators;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.AbstractElement;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.util.StringUtils;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.NameSpace.PREFIX;
import static com.codenvy.ide.client.elements.mediators.Header.HeaderAction.remove;
import static com.codenvy.ide.client.elements.mediators.Header.HeaderAction.set;
import static com.codenvy.ide.client.elements.mediators.Header.HeaderValueType.INLINE;
import static com.codenvy.ide.client.elements.mediators.Header.HeaderValueType.LITERAL;

/**
 * The class which describes state of Header mediator and also has methods for changing it. Also the class contains the business
 * logic that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving. For more information about Header mediator go to
 * <a href=" https://docs.wso2.com/display/ESB460/Header+Mediator"> https://docs.wso2.com/display/ESB460/Header+Mediator</a>
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class Header extends AbstractElement {
    public static final String ELEMENT_NAME       = "Header";
    public static final String SERIALIZATION_NAME = "header";

    private static final String NAME       = "name";
    private static final String VALUE      = "value";
    private static final String EXPRESSION = "expression";
    private static final String ACTION     = "action";
    private static final String SCOPE      = "scope";

    private static final List<String> PROPERTIES = java.util.Collections.emptyList();
    private final Provider<NameSpace> nameSpaceProvider;

    private HeaderAction    action;
    private HeaderValueType valueType;
    private ScopeType       scope;
    private String          headerName;
    private String          value;
    private String          expression;
    private String          inlineXml;

    private Array<NameSpace> headerNamespaces;
    private Array<NameSpace> expressionNamespaces;

    @Inject
    public Header(EditorResources resources,
                  Provider<Branch> branchProvider,
                  MediatorCreatorsManager mediatorCreatorsManager,
                  Provider<NameSpace> nameSpaceProvider) {

        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        this.nameSpaceProvider = nameSpaceProvider;

        headerNamespaces = Collections.createArray();
        expressionNamespaces = Collections.createArray();

        action = set;
        scope = ScopeType.Synapse;
        valueType = LITERAL;
        value = "header_Value";
        headerName = "To";
        expression = "/default/expression";
        inlineXml = "";
    }

    /** @return inline xml of header element */
    @Nonnull
    public String getInlineXml() {
        return inlineXml;
    }

    /**
     * Sets inline xml to header
     *
     * @param inlineXml
     *         value which need to set to element
     */
    public void setInlineXml(@Nullable String inlineXml) {
        this.inlineXml = inlineXml;
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
    @Nonnull
    public String getValue() {
        return value;
    }

    /**
     * Sets value to header
     *
     * @param value
     *         value which need to set to element
     */
    public void setValue(@Nonnull String value) {
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
    @Nonnull
    public HeaderAction getAction() {
        return action;
    }

    /**
     * Sets action to header
     *
     * @param action
     *         value of action which need to set to element
     */
    public void setAction(@Nonnull HeaderAction action) {
        this.action = action;
    }

    /** @return scope of header */
    @Nonnull
    public ScopeType getScope() {
        return scope;
    }

    /**
     * Sets scope to header
     *
     * @param scope
     *         value fo scope which need to set to element
     */
    public void setScope(@Nonnull ScopeType scope) {
        this.scope = scope;
    }

    /** @return type of header */
    @Nonnull
    public HeaderValueType getValueType() {
        return valueType;
    }

    /**
     * Sets type to header
     *
     * @param valueType
     *         value of type which need to set to element
     */
    public void setValueType(@Nonnull HeaderValueType valueType) {
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
    @Nonnull
    @Override
    protected String serializeProperties() {
        if (!valueType.equals(INLINE) || inlineXml.isEmpty()) {
            return "";
        }

        int index = inlineXml.indexOf(">");
        String tag = inlineXml.substring(0, index);

        String tagName = inlineXml.substring(0, tag.contains("/") ? index - 1 : index);
        String restString = inlineXml.substring(tag.contains("/") ? index - 1 : index);

        return tagName + " xmlns=\"\"" + restString;
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        Map<String, String> attributes = new LinkedHashMap<>();

        StringBuilder nameSpaces = new StringBuilder();

        if (!headerNamespaces.isEmpty()) {
            NameSpace nameSpace = headerNamespaces.get(headerNamespaces.size() - 1);

            nameSpaces.append(nameSpace.toString()).append(' ');
        }

        for (NameSpace nameSpace : expressionNamespaces.asIterable()) {
            nameSpaces.append(nameSpace.toString()).append(' ');
        }

        setDefaultAttributes(attributes);

        if (action.equals(remove)) {
            attributes.remove(VALUE);
            attributes.remove(EXPRESSION);
        } else {
            attributes.remove(ACTION);
        }

        attributes.remove(valueType.equals(HeaderValueType.EXPRESSION) ? VALUE : EXPRESSION);

        switch (valueType) {
            case INLINE:
                attributes.remove(VALUE);
                attributes.remove(NAME);
                attributes.remove(EXPRESSION);

                return convertPropertiesToXMLFormat(attributes);
        }

        return nameSpaces + convertPropertiesToXMLFormat(attributes);
    }

    /**
     * Sets default value of attributes of element
     *
     * @param attributes
     *         list of attributes which need to set to element by default
     */
    private void setDefaultAttributes(@Nonnull Map<String, String> attributes) {
        attributes.put(NAME, headerName);
        attributes.put(SCOPE, scope.name());
        attributes.put(VALUE, value);
        attributes.put(EXPRESSION, expression);
        attributes.put(ACTION, action.name());
    }

    /** {@inheritDoc} */
    @Override
    protected void applyAttributes(@Nonnull Node node) {
        NamedNodeMap attributeMap = node.getAttributes();
        boolean isFirst = true;

        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attributeNode = attributeMap.item(i);

            String nodeName = attributeNode.getNodeName();
            String nodeValue = attributeNode.getNodeValue();

            switch (nodeName) {
                case HeaderAction.ACTION:
                    action = HeaderAction.valueOf(nodeValue);
                    break;

                case ScopeType.SCOPE:
                    scope = ScopeType.valueOf(nodeValue);
                    break;

                case VALUE:
                    value = nodeValue;
                    break;

                case EXPRESSION:
                    expression = nodeValue;
                    valueType = HeaderValueType.EXPRESSION;
                    break;

                case NAME:
                    headerName = nodeValue;
                    break;

                default:
                    if (StringUtils.startsWith(PREFIX, nodeName, true)) {
                        String name = StringUtils.trimStart(nodeName, PREFIX + ':');

                        NameSpace nameSpace = nameSpaceProvider.get();

                        nameSpace.setPrefix(name);
                        nameSpace.setUri(nodeValue);

                        if (isFirst) {
                            headerNamespaces.add(nameSpace);
                            isFirst = false;
                        } else {
                            expressionNamespaces.add(nameSpace);
                        }
                    }
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void deserialize(@Nonnull Node node) {
        applyAttributes(node);

        if (node.hasChildNodes()) {
            String item = node.getChildNodes().item(0).toString();

            int indexFirst = item.indexOf(" ");
            int indexLast = item.indexOf(">");

            String tagName = item.substring(0, indexLast);

            String xmlns = tagName.substring(indexFirst, tagName.contains("/") ? indexLast - 1 : indexLast);

            inlineXml = item.replace(xmlns, "");

            valueType = INLINE;
        }
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.header();
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