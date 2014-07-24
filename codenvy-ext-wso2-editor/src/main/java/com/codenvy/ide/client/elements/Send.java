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
public class Send extends RootElement {
    public static final String ELEMENT_NAME       = "Send";
    public static final String SERIALIZATION_NAME = "send";

    private static final String SKIP_SERIALIZATION_PROPERTY_NAME           = "SkipSerialization";
    private static final String RECEIVING_SEQUENCER_TYPE_PROPERTY_NAME     = "ReceivingSequencerType";
    private static final String BUILD_MESSAGE_BEFORE_SENDING_PROPERTY_NAME = "BuildMessageBeforeSending";
    private static final String DESCRIPTION_PROPERTY_NAME                  = "Description";

    private static final List<String> PROPERTIES                        = Arrays.asList(SKIP_SERIALIZATION_PROPERTY_NAME,
                                                                                        RECEIVING_SEQUENCER_TYPE_PROPERTY_NAME,
                                                                                        BUILD_MESSAGE_BEFORE_SENDING_PROPERTY_NAME,
                                                                                        DESCRIPTION_PROPERTY_NAME);
    private static final List<String> INTERNAL_PROPERTIES               = Arrays.asList(X_PROPERTY_NAME,
                                                                                        Y_PROPERTY_NAME,
                                                                                        UUID_PROPERTY_NAME,
                                                                                        AUTO_ALIGN_PROPERTY_NAME,
                                                                                        SKIP_SERIALIZATION_PROPERTY_NAME,
                                                                                        RECEIVING_SEQUENCER_TYPE_PROPERTY_NAME,
                                                                                        BUILD_MESSAGE_BEFORE_SENDING_PROPERTY_NAME,
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

    private String skipSerialization;
    private String receivingSequencerType;
    private String buildMessageBeforeSending;
    private String description;

    public Send() {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, INTERNAL_PROPERTIES);

        skipSerialization = "false";
        receivingSequencerType = "Default";
        buildMessageBeforeSending = "false";
        description = "enter_description";

        targetElements.put(Connection.CONNECTION_NAME, AVAILABLE_FOR_CONNECTION_ELEMENTS);
    }

    @Nullable
    public String getSkipSerialization() {
        return skipSerialization;
    }

    public void setSkipSerialization(@Nullable String skipSerialization) {
        this.skipSerialization = skipSerialization;
    }

    @Nullable
    public String getReceivingSequencerType() {
        return receivingSequencerType;
    }

    public void setReceivingSequencerType(@Nullable String receivingSequencerType) {
        this.receivingSequencerType = receivingSequencerType;
    }

    @Nullable
    public String getBuildMessageBeforeSending() {
        return buildMessageBeforeSending;
    }

    public void setBuildMessageBeforeSending(@Nullable String buildMessageBeforeSending) {
        this.buildMessageBeforeSending = buildMessageBeforeSending;
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
        return "skipSerialization=\"" + skipSerialization + "\" " +
               "receivingSequencerType=\"" + receivingSequencerType + "\" " +
               "buildMessageBeforeSending=\"" + buildMessageBeforeSending + "\" " +
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
            case SKIP_SERIALIZATION_PROPERTY_NAME:
                skipSerialization = String.valueOf(nodeValue);
                break;
            case RECEIVING_SEQUENCER_TYPE_PROPERTY_NAME:
                receivingSequencerType = String.valueOf(nodeValue);
                break;
            case BUILD_MESSAGE_BEFORE_SENDING_PROPERTY_NAME:
                buildMessageBeforeSending = String.valueOf(nodeValue);
                break;
            case DESCRIPTION_PROPERTY_NAME:
                description = String.valueOf(nodeValue);
                break;
        }
    }

}