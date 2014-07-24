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
public class Call extends RootElement {
    public static final String ELEMENT_NAME       = "Call";
    public static final String SERIALIZATION_NAME = "call";

    private static final String ENDPOINT_TYPE_PROPERTY_NAME = "EndpointType";
    private static final String DESCRIPTION_PROPERTY_NAME   = "Description";

    private static final List<String> PROPERTIES                        = Arrays.asList(ENDPOINT_TYPE_PROPERTY_NAME,
                                                                                        DESCRIPTION_PROPERTY_NAME);
    private static final List<String> INTERNAL_PROPERTIES               = Arrays.asList(X_PROPERTY_NAME,
                                                                                        Y_PROPERTY_NAME,
                                                                                        UUID_PROPERTY_NAME,
                                                                                        AUTO_ALIGN_PROPERTY_NAME,
                                                                                        ENDPOINT_TYPE_PROPERTY_NAME,
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

    private String endpointType;
    private String description;

    public Call() {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, INTERNAL_PROPERTIES);

        endpointType = "INLINE";
        description = "enter_description";

        targetElements.put(Connection.CONNECTION_NAME, AVAILABLE_FOR_CONNECTION_ELEMENTS);
    }

    @Nullable
    public String getEndpointType() {
        return endpointType;
    }

    public void setEndpointType(@Nullable String endpointType) {
        this.endpointType = endpointType;
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
        return "endpointType=" + "\"" + endpointType + "\"" + " " +
               "description=" + "\"" + description + "\"" + " ";
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
            case ENDPOINT_TYPE_PROPERTY_NAME:
                endpointType = String.valueOf(nodeValue);
                break;
            case DESCRIPTION_PROPERTY_NAME:
                description = String.valueOf(nodeValue);
                break;
        }
    }

}