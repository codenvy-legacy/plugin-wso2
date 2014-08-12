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
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.elements.Sequence.ReferringSequenceType.Static;

/**
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class Sequence extends RootElement {
    public static final String ELEMENT_NAME       = "Sequence";
    public static final String SERIALIZATION_NAME = "sequence";

    private static final String REFERRING_SEQUENCE_PROPERTY_NAME   = "ReferringSequenceType";
    private static final String STATIC_REFERENCE_KEY_PROPERTY_NAME = "StaticReferenceKey";

    private static final List<String> PROPERTIES = Arrays.asList(REFERRING_SEQUENCE_PROPERTY_NAME,
                                                                 STATIC_REFERENCE_KEY_PROPERTY_NAME);

    private String referringSequenceType;
    private String staticReferenceKey;

    @Inject
    public Sequence(EditorResources resources,
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

        referringSequenceType = Static.name();
        staticReferenceKey = "Sequence";
    }

    @Nullable
    public String getReferringSequenceType() {
        return referringSequenceType;
    }

    public void setReferringSequenceType(@Nullable String referringSequenceType) {
        this.referringSequenceType = referringSequenceType;
    }

    @Nullable
    public String getStaticReferenceKey() {
        return staticReferenceKey;
    }

    public void setStaticReferenceKey(@Nullable String staticReferenceKey) {
        this.staticReferenceKey = staticReferenceKey;
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        return "referringSequenceType=\"" + referringSequenceType + "\" " +
               "staticReferenceKey=\"" + staticReferenceKey + "\" ";
    }

    /** {@inheritDoc} */
    @Override
    public void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        switch (nodeName) {
            case REFERRING_SEQUENCE_PROPERTY_NAME:
                referringSequenceType = String.valueOf(nodeValue);
                break;

            case STATIC_REFERENCE_KEY_PROPERTY_NAME:
                staticReferenceKey = String.valueOf(nodeValue);
                break;

            default:
        }
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.sequence();
    }

    public enum ReferringSequenceType {
        Dynamic, Static;

        public static final String TYPE_NAME = "ReferringSequenceType";
    }

}