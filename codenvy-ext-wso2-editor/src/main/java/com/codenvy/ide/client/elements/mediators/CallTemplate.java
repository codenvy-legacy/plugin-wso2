/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache  License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.client.elements.mediators;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.AbstractElement;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.mediators.log.Property;
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The class which describes state of CallTemplate mediator and also has methods for changing it. Also the class contains the business
 * logic that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving. For more information about CallTemplate mediator go to
 * <a href=" https://docs.wso2.com/display/ESB460/CallTemplate+Mediator"> https://docs.wso2.com/display/ESB460/CallTemplate+Mediator</a>
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class CallTemplate extends AbstractElement {
    public static final String ELEMENT_NAME       = "CallTemplate";
    public static final String SERIALIZATION_NAME = "callTemplate";

    private static final String TARGET_ATTRIBUTE_NAME      = "target";
    private static final String DESCRIPTION_ATTRIBUTE_NAME = "description";
    private static final String PARAMETERS_PROPERTY_NAME   = "with-param";

    private static final List<String> PROPERTIES = Arrays.asList(PARAMETERS_PROPERTY_NAME);

    private final Provider<Property> propertyProvider;

    private AvailableTemplates availableTemplates;
    private String             targetTemplate;
    private String             description;
    private Array<Property>    parameters;

    @Inject
    public CallTemplate(EditorResources resources,
                        Provider<Branch> branchProvider,
                        MediatorCreatorsManager mediatorCreatorsManager,
                        Provider<Property> propertyProvider) {

        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.callTemplate(),
              branchProvider,
              mediatorCreatorsManager);

        this.propertyProvider = propertyProvider;

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
        Map<String, String> prop = new LinkedHashMap<>();

        prop.put(TARGET_ATTRIBUTE_NAME, targetTemplate);
        prop.put(DESCRIPTION_ATTRIBUTE_NAME, description);

        return convertAttributesToXMLFormat(prop);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        StringBuilder result = new StringBuilder();

        for (Property property : parameters.asIterable()) {
            result.append("<with-param ").append(convertNameSpaceToXMLFormat(property.getNameSpaces())).append("name=\"")
                  .append(property.getName()).append("\" value=\"").append(property.getExpression()).append("\"/>");
        }

        return result.toString();
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        Property property = propertyProvider.get();

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

    public enum AvailableTemplates {
        SELECT_FROM_TEMPLATE("Select From Templates"), SDF("sdf"), EMPTY("");

        public static final String TYPE_NAME = "AvailableTemplatesType";

        private final String value;

        AvailableTemplates(@Nonnull String value) {
            this.value = value;
        }

        @Nonnull
        public String getValue() {
            return value;
        }
    }
}