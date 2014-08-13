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
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Class describes Respond mediator.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class Respond extends RootElement {
    public static final String ELEMENT_NAME       = "Respond";
    public static final String SERIALIZATION_NAME = "respond";

    private static final String DESCRIPTION_PROPERTY_NAME = "description";

    private static final List<String> PROPERTIES = Arrays.asList(DESCRIPTION_PROPERTY_NAME);

    private String description;

    @Inject
    public Respond(EditorResources resources,
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
        LinkedHashMap<String, String> prop = new LinkedHashMap<>();

        prop.put(DESCRIPTION_PROPERTY_NAME, description);

        return convertPropertiesToXMLFormat(prop);
    }

    /** {@inheritDoc} */
    @Override
    public void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        switch (nodeName) {
            case DESCRIPTION_PROPERTY_NAME:
                description = String.valueOf(nodeValue);
                break;

            default:
        }
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.respond();
    }

}