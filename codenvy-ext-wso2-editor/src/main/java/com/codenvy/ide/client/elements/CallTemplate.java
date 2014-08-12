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
import com.codenvy.ide.client.elements.log.Property;
import com.codenvy.ide.client.elements.payload.PayloadFactory;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Class describes CallTemplate mediator.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class CallTemplate extends RootElement {
    public static final String ELEMENT_NAME       = "CallTemplate";
    public static final String SERIALIZATION_NAME = "callTemplate";

    private static final String AVAILABLE_TEMPLATES_PROPERTY_NAME = "AvailableTemplates";
    private static final String TARGET_TEMPLATE_PROPERTY_NAME     = "target";
    private static final String PARAMETERS_PROPERTY_NAME          = "with-param";
    private static final String DESCRIPTION_PROPERTY_NAME         = "description";

    private static final List<String> PROPERTIES = Arrays.asList(PARAMETERS_PROPERTY_NAME);

    private String          availableTemplates;
    private String          targetTemplate;
    private String          description;
    private Array<Property> parameters;

    @Inject
    public CallTemplate(EditorResources resources,
                        Provider<Branch> branchProvider,
                        Provider<Log> logProvider,
                        Provider<Enrich> enrichProvider,
                        Provider<Filter> filterProvider,
                        Provider<Header> headerProvider,
                        Provider<Call> callProvider,
                        Provider<CallTemplate> callTemplateProvider,
                        Provider<LoopBack> loopBackProvider,
                        Provider<PayloadFactory> payloadFactoryProvider,
                        Provider<com.codenvy.ide.client.elements.Property> propertyProvider,
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

        parameters = Collections.createArray();
    }

    /** @return value of available template */
    @Nullable
    public String getAvailableTemplates() {
        return availableTemplates;
    }

    /**
     * Set available template.
     *
     * @param availableTemplates
     *         values of available template
     */
    public void setAvailableTemplates(@Nullable String availableTemplates) {
        this.availableTemplates = availableTemplates;
    }

    /** @return value of target template */
    @Nullable
    public String getTargetTemplate() {
        return targetTemplate;
    }

    /**
     * Set target template.
     *
     * @param targetTemplate
     *         values of target template
     */
    public void setTargetTemplate(@Nullable String targetTemplate) {
        this.targetTemplate = targetTemplate;
    }

    /** @return list of parameters of element */
    @Nonnull
    public Array<Property> getParameters() {
        return parameters;
    }

    /**
     * Set list of parameters to element.
     *
     * @param parameters
     *         parameters of CallTemplate mediator
     */
    public void setParameters(@Nonnull Array<Property> parameters) {
        this.parameters = parameters;
    }

    /** @return value of description */
    @Nullable
    public String getDescription() {
        return description;
    }

    /**
     * Set description.
     *
     * @param description
     *         description of CallTemplate mediator
     */
    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        LinkedHashMap<String, String> prop = new LinkedHashMap<>();

        prop.put(TARGET_TEMPLATE_PROPERTY_NAME, targetTemplate);
        prop.put(DESCRIPTION_PROPERTY_NAME, description);

        return prepareSerialization(prop);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        StringBuilder result = new StringBuilder();

        for (Property property : parameters.asIterable()) {
            StringBuilder nameSpaces = new StringBuilder();

            for (NameSpace nameSpace : property.getNameSpaces().asIterable()) {
                nameSpaces.append(nameSpace.toString()).append(' ');
            }

            result.append("<with-param ").append(nameSpaces).append("name=\"").append(property.getName()).append("\" value=\"")
                  .append(property.getExpression()).append("\"/>");
        }

        return result.toString();
    }

    /** {@inheritDoc} */
    @Override
    public void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();

        Node item = node.getChildNodes().item(0);
        String nodeValue = "";
        if (item != null) {
            nodeValue = item.getNodeValue();
        }

        switch (nodeName) {
            case AVAILABLE_TEMPLATES_PROPERTY_NAME:
                availableTemplates = String.valueOf(nodeValue);
                break;

            case TARGET_TEMPLATE_PROPERTY_NAME:
                targetTemplate = String.valueOf(nodeValue);
                break;

            case PARAMETERS_PROPERTY_NAME:
                //TODO create property using editor factory
                Property property = new Property(null, null);
                property.applyAttributes(node);

                parameters.add(property);
                break;

            case DESCRIPTION_PROPERTY_NAME:
                description = String.valueOf(nodeValue);
                break;

            default:
        }
    }

    /** {@inheritDoc} */
    @Override
    public void applyAttributes(@Nonnull Node node) {
        NamedNodeMap attributeMap = node.getAttributes();

        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attributeNode = attributeMap.item(i);

            switch (attributeNode.getNodeName()) {
                case AVAILABLE_TEMPLATES_PROPERTY_NAME:
                    availableTemplates = attributeNode.getNodeValue();
                    break;

                case TARGET_TEMPLATE_PROPERTY_NAME:
                    targetTemplate = attributeNode.getNodeValue();
                    break;

                case DESCRIPTION_PROPERTY_NAME:
                    description = attributeNode.getNodeValue();
                    break;
            }
        }
    }

    public enum AvailableTemplates {
        SELECT_FROM_TEMPLATE("Select From Templates"), SDF("sdf"), EMPTY("");

        public static final String TYPE_NAME = "AvailableTemplatesType";

        private String value;

        AvailableTemplates(String value) {
            this.value = value;
        }

        @NotNull
        public String getValue() {
            return value;
        }
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.callTemplate();
    }
}