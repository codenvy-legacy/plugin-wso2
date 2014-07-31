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
public class Header extends RootElement {
    public static final String ELEMENT_NAME       = "Header";
    public static final String SERIALIZATION_NAME = "header";

    private static final String HEADER_ACTION_PROPERTY_NAME = "HeaderAction";
    private static final String SCOPE_PROPERTY_NAME         = "Scope";
    private static final String VALUE_TYPE_PROPERTY_NAME    = "ValueType";
    private static final String VALUE_LITERAL_PROPERTY_NAME = "ValueLiteral";
    private static final String HEADER_NAME_PROPERTY_NAME   = "HeaderName";

    private static final List<String> PROPERTIES          = Arrays.asList(HEADER_ACTION_PROPERTY_NAME,
                                                                          SCOPE_PROPERTY_NAME,
                                                                          VALUE_TYPE_PROPERTY_NAME,
                                                                          VALUE_LITERAL_PROPERTY_NAME,
                                                                          HEADER_NAME_PROPERTY_NAME);
    private static final List<String> INTERNAL_PROPERTIES = Arrays.asList(X_PROPERTY_NAME,
                                                                          Y_PROPERTY_NAME,
                                                                          UUID_PROPERTY_NAME,
                                                                          HEADER_ACTION_PROPERTY_NAME,
                                                                          SCOPE_PROPERTY_NAME,
                                                                          VALUE_TYPE_PROPERTY_NAME,
                                                                          VALUE_LITERAL_PROPERTY_NAME,
                                                                          HEADER_NAME_PROPERTY_NAME);

    private String headerAction;
    private String scope;
    private String valueType;
    private String valueLiteral;
    private String headerName;

    public Header() {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, INTERNAL_PROPERTIES);

        headerAction = "set";
        scope = "Synapse";
        valueType = "LITERAL";
        valueLiteral = "header_Value";
        headerName = "header_name";
    }

    @Nullable
    public String getHeaderAction() {
        return headerAction;
    }

    public void setHeaderAction(@Nullable String headerAction) {
        this.headerAction = headerAction;
    }

    @Nullable
    public String getScope() {
        return scope;
    }

    public void setScope(@Nullable String scope) {
        this.scope = scope;
    }

    @Nullable
    public String getValueType() {
        return valueType;
    }

    public void setValueType(@Nullable String valueType) {
        this.valueType = valueType;
    }

    @Nullable
    public String getValueLiteral() {
        return valueLiteral;
    }

    public void setValueLiteral(@Nullable String valueLiteral) {
        this.valueLiteral = valueLiteral;
    }

    @Nullable
    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(@Nullable String headerName) {
        this.headerName = headerName;
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        return "headerAction=\"" + headerAction + "\" " +
               "scope=\"" + scope + "\" " +
               "valueType=\"" + valueType + "\" " +
               "valueLiteral=\"" + valueLiteral + "\" " +
               "headerName=\"" + headerName + "\" ";
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
            case HEADER_ACTION_PROPERTY_NAME:
                headerAction = String.valueOf(nodeValue);
                break;
            case SCOPE_PROPERTY_NAME:
                scope = String.valueOf(nodeValue);
                break;
            case VALUE_TYPE_PROPERTY_NAME:
                valueType = String.valueOf(nodeValue);
                break;
            case VALUE_LITERAL_PROPERTY_NAME:
                valueLiteral = String.valueOf(nodeValue);
                break;
            case HEADER_NAME_PROPERTY_NAME:
                headerName = String.valueOf(nodeValue);
                break;
        }
    }

}