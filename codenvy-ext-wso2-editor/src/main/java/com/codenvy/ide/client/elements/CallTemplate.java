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
import com.codenvy.ide.client.elements.log.Property;
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
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
import java.util.List;

/**
 * Class describes CallTemplate mediator.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class CallTemplate extends AbstractShape {
    public static final String ELEMENT_NAME       = "CallTemplate";
    public static final String SERIALIZATION_NAME = "callTemplate";

    private static final String TARGET_ATTRIBUTE_NAME      = "target";
    private static final String DESCRIPTION_ATTRIBUTE_NAME = "description";
    private static final String PARAMETERS_PROPERTY_NAME   = "with-param";

    private static final List<String> PROPERTIES = Arrays.asList(PARAMETERS_PROPERTY_NAME);

    private AvailableTemplates availableTemplates;
    private String             targetTemplate;
    private String             description;
    private Array<Property>    parameters;

    @Inject
    public CallTemplate(EditorResources resources, Provider<Branch> branchProvider, MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        availableTemplates = AvailableTemplates.EMPTY;
        targetTemplate = "";
        description = "";
        parameters = Collections.createArray();
    }

    /** @return value of available template */
    @Nonnull
    public AvailableTemplates getAvailableTemplates() {
        return availableTemplates;
    }

    /**
     * Set available template parameter to element.
     *
     * @param availableTemplates
     *         values of available template
     */
    public void setAvailableTemplates(@Nullable AvailableTemplates availableTemplates) {
        this.availableTemplates = availableTemplates;
    }

    /** @return value of target template */
    @Nonnull
    public String getTargetTemplate() {
        return targetTemplate;
    }

    /**
     * Sets target template to element.
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
     * Sets value of description to element.
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
        return TARGET_ATTRIBUTE_NAME + "=\"" + targetTemplate + "\" " +
               (description.isEmpty() ? "" : DESCRIPTION_ATTRIBUTE_NAME + "=\"" + description + "\"");
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
        Property property = new Property(null, null);

        property.applyAttributes(node);

        parameters.add(property);
    }

    /** {@inheritDoc} */
    @Override
    public void applyAttributes(@Nonnull Node node) {
        NamedNodeMap attributeMap = node.getAttributes();

        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attributeNode = attributeMap.item(i);

            String attributeValue = attributeNode.getNodeValue();

            switch (attributeNode.getNodeName()) {
                case TARGET_ATTRIBUTE_NAME:
                    targetTemplate = attributeValue;
                    break;

                case DESCRIPTION_ATTRIBUTE_NAME:
                    description = attributeValue;
                    break;
            }
        }
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.callTemplate();
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
}