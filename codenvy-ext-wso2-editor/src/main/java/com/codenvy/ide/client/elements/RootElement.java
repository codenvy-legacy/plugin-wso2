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
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import static java.util.Map.Entry;

/**
 * The main element of the diagram.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class RootElement extends AbstractShape {
    public static final String ELEMENT_NAME       = "RootElement";
    public static final String SERIALIZATION_NAME = "sequence";

    private static final List<String> PROPERTIES = Collections.emptyList();
    private static final List<String> COMPONENTS = Arrays.asList(Log.ELEMENT_NAME,
                                                                 Property.ELEMENT_NAME,
                                                                 PayloadFactory.ELEMENT_NAME,
                                                                 Send.ELEMENT_NAME,
                                                                 Header.ELEMENT_NAME,
                                                                 Respond.ELEMENT_NAME,
                                                                 Filter.ELEMENT_NAME,
                                                                 Switch.ELEMENT_NAME,
                                                                 Sequence.ELEMENT_NAME,
                                                                 Enrich.ELEMENT_NAME,
                                                                 LoopBack.ELEMENT_NAME,
                                                                 CallTemplate.ELEMENT_NAME,
                                                                 Call.ELEMENT_NAME);

    private final Provider<Log>            logProvider;
    private final Provider<Enrich>         enrichProvider;
    private final Provider<Filter>         filterProvider;
    private final Provider<Header>         headerProvider;
    private final Provider<Call>           callProvider;
    private final Provider<CallTemplate>   callTemplateProvider;
    private final Provider<LoopBack>       loopBackProvider;
    private final Provider<PayloadFactory> payloadFactoryProvider;
    private final Provider<Property>       propertyProvider;
    private final Provider<Respond>        respondProvider;
    private final Provider<Send>           sendProvider;
    private final Provider<Sequence>       sequenceProvider;
    private final Provider<Switch>         switchProvider;

    public RootElement(@Nonnull String elementName,
                       @Nonnull String title,
                       @Nonnull String serializationName,
                       @Nonnull List<String> properties,
                       @Nonnull EditorResources resources,
                       @Nonnull Provider<Branch> branchProvider,
                       boolean isPossibleToAddBranches,
                       boolean needsToShowIconAndTitle,
                       @Nonnull Provider<Log> logProvider,
                       @Nonnull Provider<Enrich> enrichProvider,
                       @Nonnull Provider<Filter> filterProvider,
                       @Nonnull Provider<Header> headerProvider,
                       @Nonnull Provider<Call> callProvider,
                       @Nonnull Provider<CallTemplate> callTemplateProvider,
                       @Nonnull Provider<LoopBack> loopBackProvider,
                       @Nonnull Provider<PayloadFactory> payloadFactoryProvider,
                       @Nonnull Provider<Property> propertyProvider,
                       @Nonnull Provider<Respond> respondProvider,
                       @Nonnull Provider<Send> sendProvider,
                       @Nonnull Provider<Sequence> sequenceProvider,
                       @Nonnull Provider<Switch> switchProvider) {
        super(elementName,
              title,
              serializationName,
              properties,
              resources,
              branchProvider,
              isPossibleToAddBranches,
              needsToShowIconAndTitle);

        this.logProvider = logProvider;
        this.enrichProvider = enrichProvider;
        this.filterProvider = filterProvider;
        this.headerProvider = headerProvider;
        this.callProvider = callProvider;
        this.callTemplateProvider = callTemplateProvider;
        this.loopBackProvider = loopBackProvider;
        this.payloadFactoryProvider = payloadFactoryProvider;
        this.propertyProvider = propertyProvider;
        this.respondProvider = respondProvider;
        this.sendProvider = sendProvider;
        this.sequenceProvider = sequenceProvider;
        this.switchProvider = switchProvider;
    }

    @Inject
    public RootElement(EditorResources resources,
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
        this(ELEMENT_NAME,
             ELEMENT_NAME,
             SERIALIZATION_NAME,
             PROPERTIES,
             resources,
             branchProvider,
             false,
             false,
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

        components.addAll(COMPONENTS);

        branches.add(branchProvider.get());
    }

    /** {@inheritDoc} */
    @Override
    public Shape createElement(@Nonnull String elementName) {
        switch (elementName) {
            case Log.SERIALIZATION_NAME:
                return logProvider.get();

            case Property.SERIALIZATION_NAME:
                return propertyProvider.get();

            case PayloadFactory.SERIALIZATION_NAME:
                return payloadFactoryProvider.get();

            case Send.SERIALIZATION_NAME:
                return sendProvider.get();

            case Header.SERIALIZATION_NAME:
                return headerProvider.get();

            case Respond.SERIALIZATION_NAME:
                return respondProvider.get();

            case Filter.SERIALIZATION_NAME:
                return filterProvider.get();

            case Switch.SERIALIZATION_NAME:
                return switchProvider.get();

            case Sequence.SERIALIZATION_NAME:
                return sequenceProvider.get();

            case Enrich.SERIALIZATION_NAME:
                return enrichProvider.get();

            case LoopBack.SERIALIZATION_NAME:
                return loopBackProvider.get();

            case CallTemplate.SERIALIZATION_NAME:
                return callTemplateProvider.get();

            case Call.SERIALIZATION_NAME:
                return callProvider.get();

            default:
                return null;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void deserialize(@Nonnull String content) {
        super.deserialize(content);

        if (branches.isEmpty()) {
            branches.add(branchProvider.get());
        }
    }

    /**
     * Convert properties of diagram element to XML attribute format.
     *
     * @param attributes
     *         element's properties
     * @return XML format of element's attributes
     */
    protected String convertPropertiesToXMLFormat(@NotNull LinkedHashMap<String, String> attributes) {
        StringBuilder content = new StringBuilder();

        for (Iterator iterator = attributes.entrySet().iterator(); iterator.hasNext(); ) {
            Entry entry = (Entry)iterator.next();
            String value = (String)entry.getValue();

            if (value != null && !value.isEmpty()) {
                content.append(entry.getKey()).append("=\"").append(value).append('"');
            }

            if (iterator.hasNext()) {
                content.append(' ');
            }
        }

        return content.toString();
    }
}