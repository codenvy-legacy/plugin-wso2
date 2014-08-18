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

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.enrich.Enrich;
import com.codenvy.ide.client.elements.log.Log;
import com.codenvy.ide.client.elements.payload.PayloadFactory;
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
import static com.codenvy.ide.client.elements.Property.Action.set;
import static com.codenvy.ide.client.elements.Property.DataType.STRING;
import static com.codenvy.ide.client.elements.Property.Scope.SYNAPSE;
import static com.codenvy.ide.client.elements.Property.ValueType.EXPRESSION;
import static com.codenvy.ide.client.elements.Property.ValueType.LITERAL;

/**
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class Property extends AbstractShape {
    public static final String ELEMENT_NAME       = "Property";
    public static final String SERIALIZATION_NAME = "property";

    private static final String NAME_ATTRIBUTE                 = "name";
    private static final String ACTION_ATTRIBUTE               = "action";
    private static final String DATA_TYPE_ATTRIBUTE            = "type";
    private static final String VALUE_LITERAL_ATTRIBUTE        = "value";
    private static final String VALUE_EXPRESSION_ATTRIBUTE     = "expression";
    private static final String STRING_PATTERN_ATTRIBUTE       = "pattern";
    private static final String STRING_CAPTURE_GROUP_ATTRIBUTE = "group";
    private static final String SCOPE_ATTRIBUTE                = "scope";
    private static final String DESCRIPTION_ATTRIBUTE          = "description";

    private static final List<String> PROPERTIES = java.util.Collections.emptyList();

    private String           propertyName;
    private Action           propertyAction;
    private ValueType        valueType;
    private DataType         propertyDataType;
    private String           valueLiteral;
    private String           valueExpression;
    private String           valueStringPattern;
    private String           valueStringCaptureGroup;
    private Scope            propertyScope;
    private String           description;
    private Array<NameSpace> nameSpaces;

    @Inject
    public Property(EditorResources resources,
                    Provider<Branch> branchProvider,
                    Provider<Log> logProvider,
                    Provider<Enrich> enrichProvider,
                    Provider<Filter> filterProvider,
                    Provider<Header> headerProvider,
                    Provider<Call> callProvider,
                    Provider<CallTemplate> callTemplateProvider,
                    Provider<LoopBack> loopBackProvider,
                    Provider<PayloadFactory> payloadFactoryProvider,
                    Provider<Property> propertyProvider,
                    Provider<Respond> respondProvider,
                    Provider<Send> sendProvider,
                    Provider<Sequence> sequenceProvider,
                    Provider<Switch> switchProvider) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              resources,
              branchProvider,
              false,
              true,
              logProvider,
              enrichProvider,
              filterProvider,
              headerProvider,
              callProvider,
              callTemplateProvider,
              loopBackProvider,
              payloadFactoryProvider,
              propertyProvider,
              respondProvider,
              sendProvider,
              sequenceProvider,
              switchProvider);

        propertyAction = set;
        valueType = LITERAL;
        propertyDataType = STRING;
        propertyScope = SYNAPSE;
        propertyName = "property_name";
        valueLiteral = "value";
        valueExpression = "/default/expression";
        valueStringPattern = "";
        valueStringCaptureGroup = "";
        nameSpaces = Collections.createArray();
    }

    /** @return namespaces which contain in property */
    @Nonnull
    public Array<NameSpace> getNameSpaces() {
        return nameSpaces;
    }

    /**
     * Sets name spaces to element
     *
     * @param nameSpaces
     *         list which need to set to element
     */
    public void setNameSpaces(@Nonnull Array<NameSpace> nameSpaces) {
        this.nameSpaces = nameSpaces;
    }

    /** @return name of property */
    @Nullable
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * Sets property name to element
     *
     * @param propertyName
     *         value which need to set to element
     */
    public void setPropertyName(@Nullable String propertyName) {
        this.propertyName = propertyName;
    }

    /** @return action of property */
    @Nonnull
    public Action getPropertyAction() {
        return propertyAction;
    }

    /**
     * Sets action to element
     *
     * @param propertyAction
     *         value which need to set to element
     */
    public void setPropertyAction(@Nullable Action propertyAction) {
        this.propertyAction = propertyAction;
    }

    /** @return value type of property */
    @Nonnull
    public ValueType getValueType() {
        return valueType;
    }

    /**
     * Sets value type to element
     *
     * @param valueType
     *         value which need to set to element
     */
    public void setValueType(@Nullable ValueType valueType) {
        this.valueType = valueType;
    }

    /** @return data type of property */
    @Nonnull
    public DataType getPropertyDataType() {
        return propertyDataType;
    }

    /**
     * Sets data type to element
     *
     * @param propertyDataType
     *         value which need to set to element
     */
    public void setPropertyDataType(@Nullable DataType propertyDataType) {
        this.propertyDataType = propertyDataType;
    }

    /** @return value literal of property */
    @Nullable
    public String getValueLiteral() {
        return valueLiteral;
    }

    /**
     * Sets value literal to element
     *
     * @param valueLiteral
     *         value which need to set to element
     */
    public void setValueLiteral(@Nullable String valueLiteral) {
        this.valueLiteral = valueLiteral;
    }

    /** @return value expression of property */
    @Nullable
    public String getValueExpression() {
        return valueExpression;
    }

    /**
     * Sets value expression to element
     *
     * @param valueExpression
     *         value which need to set to element
     */
    public void setValueExpression(@Nullable String valueExpression) {
        this.valueExpression = valueExpression;
    }

    /** @return value of string pattern of property */
    @Nullable
    public String getValueStringPattern() {
        return valueStringPattern;
    }

    /**
     * Sets string pattern to element
     *
     * @param valueStringPattern
     *         value which need to set to element
     */
    public void setValueStringPattern(@Nullable String valueStringPattern) {
        this.valueStringPattern = valueStringPattern;
    }

    /** @return capture group of property */
    @Nullable
    public String getValueStringCaptureGroup() {
        return valueStringCaptureGroup;
    }

    /**
     * Sets capture group to element
     *
     * @param valueStringCaptureGroup
     *         value which need to set to element
     */
    public void setValueStringCaptureGroup(@Nullable String valueStringCaptureGroup) {
        this.valueStringCaptureGroup = valueStringCaptureGroup;
    }

    /** @return scope of property */
    @Nonnull
    public Scope getPropertyScope() {
        return propertyScope;
    }

    /**
     * Sets scope to element
     *
     * @param propertyScope
     *         value which need to set to element
     */
    public void setPropertyScope(@Nullable Scope propertyScope) {
        this.propertyScope = propertyScope;
    }

    /** @return description of property */
    @Nullable
    public String getDescription() {
        return description;
    }

    /**
     * Sets description to element
     *
     * @param description
     *         value which need to set to element
     */
    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        Map<String, String> attributes = new LinkedHashMap<>();
        StringBuilder spaces = new StringBuilder();

        for (NameSpace nameSpace : nameSpaces.asIterable()) {
            spaces.append(nameSpace.toString()).append(' ');
        }

        setDefaultAttributes(attributes);

        attributes.remove(valueType.equals(EXPRESSION) ? VALUE_LITERAL_ATTRIBUTE : VALUE_EXPRESSION_ATTRIBUTE);

        if (propertyAction.equals(set)) {
            attributes.remove(ACTION_ATTRIBUTE);
        } else {
            attributes.remove(VALUE_EXPRESSION_ATTRIBUTE);
            attributes.remove(VALUE_LITERAL_ATTRIBUTE);
            attributes.remove(DATA_TYPE_ATTRIBUTE);
        }

        return spaces + convertPropertiesToXMLFormat(attributes);
    }

    /**
     * Sets default visualization of property element attributes.
     *
     * @param attributes
     *         list of default attributes
     */
    private void setDefaultAttributes(@Nonnull Map<String, String> attributes) {
        attributes.put(NAME_ATTRIBUTE, propertyName);
        attributes.put(VALUE_EXPRESSION_ATTRIBUTE, valueExpression);
        attributes.put(VALUE_LITERAL_ATTRIBUTE, valueLiteral);
        attributes.put(SCOPE_ATTRIBUTE, propertyScope.name());
        attributes.put(ACTION_ATTRIBUTE, propertyAction.name());
        attributes.put(DATA_TYPE_ATTRIBUTE, propertyDataType.name());
        attributes.put(STRING_CAPTURE_GROUP_ATTRIBUTE, valueStringCaptureGroup);
        attributes.put(STRING_PATTERN_ATTRIBUTE, valueStringPattern);
        attributes.put(DESCRIPTION_ATTRIBUTE, description);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyAttributes(@Nonnull Node node) {
        NamedNodeMap attributeMap = node.getAttributes();

        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attributeNode = attributeMap.item(i);

            String nodeValue = attributeNode.getNodeValue();
            String nodeName = attributeNode.getNodeName();

            switch (nodeName) {
                case NAME_ATTRIBUTE:
                    propertyName = String.valueOf(nodeValue);
                    break;

                case ACTION_ATTRIBUTE:
                    propertyAction = Action.valueOf(nodeValue);
                    break;

                case DATA_TYPE_ATTRIBUTE:
                    propertyDataType = DataType.valueOf(nodeValue);
                    break;

                case VALUE_LITERAL_ATTRIBUTE:
                    valueLiteral = String.valueOf(nodeValue);
                    break;

                case VALUE_EXPRESSION_ATTRIBUTE:
                    valueExpression = String.valueOf(nodeValue);
                    valueType = EXPRESSION;
                    break;

                case STRING_PATTERN_ATTRIBUTE:
                    valueStringPattern = String.valueOf(nodeValue);
                    break;

                case STRING_CAPTURE_GROUP_ATTRIBUTE:
                    valueStringCaptureGroup = String.valueOf(nodeValue);
                    break;

                case SCOPE_ATTRIBUTE:
                    propertyScope = Scope.valueOf(nodeValue);
                    break;

                case DESCRIPTION_ATTRIBUTE:
                    description = String.valueOf(nodeValue);
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

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.property();
    }

    public enum Action {
        set, remove;

        public static final String TYPE_NAME = "Action";
    }

    public enum ValueType {
        LITERAL, EXPRESSION;

        public static final String TYPE_NAME = "ValueType";
    }

    public enum DataType {
        STRING, INTEGER, BOOLEAN, DOUBLE, FLOAT, LONG, SHORT, OM;

        public static final String TYPE_NAME = "PropertyDataType";
    }

    public enum Scope {
        SYNAPSE("Synapse"), TRANSPORT("transport"), AXIS2("axis2"), AXIS2_CLIENT("axis2_client"), OPERATION("operation");

        public static final String TYPE_NAME = "PropertyScopeType";

        private String value;

        Scope(String value) {
            this.value = value;
        }

        @Nonnull
        public String getValue() {
            return value;
        }
    }

}