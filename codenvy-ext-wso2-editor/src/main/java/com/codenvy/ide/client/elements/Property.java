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

import com.google.gwt.xml.client.Node;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

/**
 * @author Andrey Plotnikov
 */
public class Property extends RootElement {
    public static final String ELEMENT_NAME       = "Property";
    public static final String SERIALIZATION_NAME = "property";

    private static final String PROPERTY_NAME_PROPERTY_NAME              = "PropertyName";
    private static final String PROPERTY_ACTION_PROPERTY_NAME            = "PropertyAction";
    private static final String VALUE_TYPE_PROPERTY_NAME                 = "ValueType";
    private static final String PROPERTY_DATA_TYPE_PROPERTY_NAME         = "PropertyDataType";
    private static final String VALUE_LITERAL_PROPERTY_NAME              = "ValueLiteral";
    private static final String VALUE_STRING_PATTERN_PROPERTY_NAME       = "ValueStringPattern";
    private static final String VALUE_STRING_CAPTURE_GROUP_PROPERTY_NAME = "ValueStringCaptureGroup";
    private static final String PROPERTY_SCOPE_PROPERTY_NAME             = "PropertyScope";
    private static final String DESCRIPTION_PROPERTY_NAME                = "Description";

    private static final List<String> PROPERTIES                        = Arrays.asList(PROPERTY_NAME_PROPERTY_NAME,
                                                                                        PROPERTY_ACTION_PROPERTY_NAME,
                                                                                        VALUE_TYPE_PROPERTY_NAME,
                                                                                        PROPERTY_DATA_TYPE_PROPERTY_NAME,
                                                                                        VALUE_LITERAL_PROPERTY_NAME,
                                                                                        VALUE_STRING_PATTERN_PROPERTY_NAME,
                                                                                        VALUE_STRING_CAPTURE_GROUP_PROPERTY_NAME,
                                                                                        PROPERTY_SCOPE_PROPERTY_NAME,
                                                                                        DESCRIPTION_PROPERTY_NAME);
    private static final List<String> INTERNAL_PROPERTIES               = Arrays.asList(X_PROPERTY_NAME,
                                                                                        Y_PROPERTY_NAME,
                                                                                        UUID_PROPERTY_NAME,
                                                                                        AUTO_ALIGN_PROPERTY_NAME,
                                                                                        PROPERTY_NAME_PROPERTY_NAME,
                                                                                        PROPERTY_ACTION_PROPERTY_NAME,
                                                                                        VALUE_TYPE_PROPERTY_NAME,
                                                                                        PROPERTY_DATA_TYPE_PROPERTY_NAME,
                                                                                        VALUE_LITERAL_PROPERTY_NAME,
                                                                                        VALUE_STRING_PATTERN_PROPERTY_NAME,
                                                                                        VALUE_STRING_CAPTURE_GROUP_PROPERTY_NAME,
                                                                                        PROPERTY_SCOPE_PROPERTY_NAME,
                                                                                        DESCRIPTION_PROPERTY_NAME);
    private static final List<String> AVAILABLE_FOR_CONNECTION_ELEMENTS = Arrays.asList(Log.ELEMENT_NAME,
                                                                                        Property.ELEMENT_NAME,
                                                                                        PayloadFactory.ELEMENT_NAME,
                                                                                        Send.ELEMENT_NAME,
                                                                                        Header.ELEMENT_NAME,
                                                                                        Respond.ELEMENT_NAME,
                                                                                        Filter.ELEMENT_NAME,
                                                                                        Switch_mediator.ELEMENT_NAME,
                                                                                        Sequence.ELEMENT_NAME,
                                                                                        Enrich.ELEMENT_NAME,
                                                                                        LoopBack.ELEMENT_NAME,
                                                                                        CallTemplate.ELEMENT_NAME,
                                                                                        Call.ELEMENT_NAME);

    private String  propertyName;
    private String  propertyAction;
    private String  valueType;
    private String  propertyDataType;
    private String  valueLiteral;
    private String  valueStringPattern;
    private Integer valueStringCaptureGroup;
    private String  propertyScope;
    private String  description;

    public Property() {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, INTERNAL_PROPERTIES);

        propertyName = "propertyName";
        propertyAction = "set";
        valueType = "LITERAL";
        propertyDataType = "$STRING";
        valueLiteral = "some_Value";
        valueStringPattern = "enter_value_string_pattern";
        valueStringCaptureGroup = 0;
        propertyScope = "Synapse";
        description = "enter_description";

        targetElements.put(Connection.CONNECTION_NAME, AVAILABLE_FOR_CONNECTION_ELEMENTS);
    }

    @Nullable
    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(@Nullable String propertyName) {
        this.propertyName = propertyName;
    }

    @Nullable
    public String getPropertyAction() {
        return propertyAction;
    }

    public void setPropertyAction(@Nullable String propertyAction) {
        this.propertyAction = propertyAction;
    }

    @Nullable
    public String getValueType() {
        return valueType;
    }

    public void setValueType(@Nullable String valueType) {
        this.valueType = valueType;
    }

    @Nullable
    public String getPropertyDataType() {
        return propertyDataType;
    }

    public void setPropertyDataType(@Nullable String propertyDataType) {
        this.propertyDataType = propertyDataType;
    }

    @Nullable
    public String getValueLiteral() {
        return valueLiteral;
    }

    public void setValueLiteral(@Nullable String valueLiteral) {
        this.valueLiteral = valueLiteral;
    }

    @Nullable
    public String getValueStringPattern() {
        return valueStringPattern;
    }

    public void setValueStringPattern(@Nullable String valueStringPattern) {
        this.valueStringPattern = valueStringPattern;
    }

    @Nullable
    public Integer getValueStringCaptureGroup() {
        return valueStringCaptureGroup;
    }

    public void setValueStringCaptureGroup(@Nullable Integer valueStringCaptureGroup) {
        this.valueStringCaptureGroup = valueStringCaptureGroup;
    }

    @Nullable
    public String getPropertyScope() {
        return propertyScope;
    }

    public void setPropertyScope(@Nullable String propertyScope) {
        this.propertyScope = propertyScope;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeProperties() {
        return "propertyName=\"" + propertyName + "\" " +
               "propertyAction=\"" + propertyAction + "\" " +
               "valueType=\"" + valueType + "\" " +
               "propertyDataType=\"" + propertyDataType + "\" " +
               "valueLiteral=\"" + valueLiteral + "\" " +
               "valueStringPattern=\"" + valueStringPattern + "\" " +
               "valueStringCaptureGroup=\"" + valueStringCaptureGroup + "\" " +
               "propertyScope=\"" + propertyScope + "\" " +
               "description=\"" + description + "\" ";
    }

    /** {@inheritDoc} */
    @Override
    public void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        switch (nodeName) {
            case AbstractShape.X_PROPERTY_NAME:
                setX(Integer.valueOf(nodeValue));
                break;
            case AbstractShape.Y_PROPERTY_NAME:
                setY(Integer.valueOf(nodeValue));
                break;
            case AbstractElement.UUID_PROPERTY_NAME:
                id = nodeValue;
                break;
            case AbstractShape.AUTO_ALIGN_PROPERTY_NAME:
                setAutoAlignmentParam(Boolean.valueOf(nodeValue));
                break;
            case PROPERTY_NAME_PROPERTY_NAME:
                propertyName = String.valueOf(nodeValue);
                break;
            case PROPERTY_ACTION_PROPERTY_NAME:
                propertyAction = String.valueOf(nodeValue);
                break;
            case VALUE_TYPE_PROPERTY_NAME:
                valueType = String.valueOf(nodeValue);
                break;
            case PROPERTY_DATA_TYPE_PROPERTY_NAME:
                propertyDataType = String.valueOf(nodeValue);
                break;
            case VALUE_LITERAL_PROPERTY_NAME:
                valueLiteral = String.valueOf(nodeValue);
                break;
            case VALUE_STRING_PATTERN_PROPERTY_NAME:
                valueStringPattern = String.valueOf(nodeValue);
                break;
            case VALUE_STRING_CAPTURE_GROUP_PROPERTY_NAME:
                valueStringCaptureGroup = Integer.valueOf(nodeValue);
                break;
            case PROPERTY_SCOPE_PROPERTY_NAME:
                propertyScope = String.valueOf(nodeValue);
                break;
            case DESCRIPTION_PROPERTY_NAME:
                description = String.valueOf(nodeValue);
                break;
        }
    }

}