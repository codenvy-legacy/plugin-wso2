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
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.elements.Send.EBoolean.FALSE;
import static com.codenvy.ide.client.elements.Send.ReceivingSequenceType.Default;

/**
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class Send extends RootElement {
    public static final String ELEMENT_NAME       = "Send";
    public static final String SERIALIZATION_NAME = "send";

    private static final String SKIP_SERIALIZATION_PROPERTY_NAME           = "SkipSerialization";
    private static final String RECEIVING_SEQUENCER_TYPE_PROPERTY_NAME     = "ReceivingSequencerType";
    private static final String BUILD_MESSAGE_BEFORE_SENDING_PROPERTY_NAME = "BuildMessageBeforeSending";
    private static final String DESCRIPTION_PROPERTY_NAME                  = "Description";

    private static final List<String> PROPERTIES = Arrays.asList(SKIP_SERIALIZATION_PROPERTY_NAME,
                                                                 RECEIVING_SEQUENCER_TYPE_PROPERTY_NAME,
                                                                 BUILD_MESSAGE_BEFORE_SENDING_PROPERTY_NAME,
                                                                 DESCRIPTION_PROPERTY_NAME);

    private String skipSerialization;
    private String receivingSequencerType;
    private String buildMessageBeforeSending;
    private String description;

    @Inject
    public Send(EditorResources resources,
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

        skipSerialization = FALSE.getValue();
        receivingSequencerType = Default.name();
        buildMessageBeforeSending = "false";
        description = "enter_description";

        branches.add(branchProvider.get());
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
    protected String serializeAttributes() {
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

            default:
        }
    }

    /** {@inheritDoc} */
    @Override
    public void deserialize(@Nonnull Node node) {
        super.deserialize(node);

        if (branches.isEmpty()) {
            Branch branch = branchProvider.get();
            branch.setParent(this);

            branches.add(branch);
        }
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.send();
    }

    public enum ReceivingSequenceType {
        Default, Static;

        public static final String TYPE_NAME = "ReceivingSequenceType";
    }

    public enum EBoolean {
        TRUE("true"), FALSE("false");

        public static final String TYPE_NAME = "EBoolean";

        private String value;

        EBoolean(String s) {
            this.value = s;
        }

        @NotNull
        public String getValue() {
            return value;
        }
    }

}