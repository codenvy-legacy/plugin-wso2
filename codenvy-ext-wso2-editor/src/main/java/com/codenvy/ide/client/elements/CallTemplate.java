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
public class CallTemplate extends RootElement {
    public static final String ELEMENT_NAME       = "CallTemplate";
    public static final String SERIALIZATION_NAME = "callTemplate";

    private static final String AVAILABLE_TEMPLATES_PROPERTY_NAME = "AvailableTemplates";
    private static final String TARGET_TEMPLATE_PROPERTY_NAME     = "TargetTemplate";
    private static final String PARAMETERS_PROPERTY_NAME          = "Parameters";
    private static final String DESCRIPTION_PROPERTY_NAME         = "Description";

    private static final List<String> PROPERTIES          = Arrays.asList(AVAILABLE_TEMPLATES_PROPERTY_NAME,
                                                                          TARGET_TEMPLATE_PROPERTY_NAME,
                                                                          PARAMETERS_PROPERTY_NAME,
                                                                          DESCRIPTION_PROPERTY_NAME);
    private static final List<String> INTERNAL_PROPERTIES = Arrays.asList(X_PROPERTY_NAME,
                                                                          Y_PROPERTY_NAME,
                                                                          UUID_PROPERTY_NAME,
                                                                          AVAILABLE_TEMPLATES_PROPERTY_NAME,
                                                                          TARGET_TEMPLATE_PROPERTY_NAME,
                                                                          PARAMETERS_PROPERTY_NAME,
                                                                          DESCRIPTION_PROPERTY_NAME);

    private String availableTemplates;
    private String targetTemplate;
    private String parameters;
    private String description;

    public CallTemplate() {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, INTERNAL_PROPERTIES);

        availableTemplates = "enter_templates";
        targetTemplate = "enter_target_template";
        parameters = "enter_parameters";
        description = "enter_description";
    }

    @Nullable
    public String getAvailableTemplates() {
        return availableTemplates;
    }

    public void setAvailableTemplates(@Nullable String availableTemplates) {
        this.availableTemplates = availableTemplates;
    }

    @Nullable
    public String getTargetTemplate() {
        return targetTemplate;
    }

    public void setTargetTemplate(@Nullable String targetTemplate) {
        this.targetTemplate = targetTemplate;
    }

    @Nullable
    public String getParameters() {
        return parameters;
    }

    public void setParameters(@Nullable String parameters) {
        this.parameters = parameters;
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
        return "availableTemplates=\"" + availableTemplates + "\" " +
               "targetTemplate=\"" + targetTemplate + "\" " +
               "parameters=\"" + parameters + "\" " +
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
            case AVAILABLE_TEMPLATES_PROPERTY_NAME:
                availableTemplates = String.valueOf(nodeValue);
                break;
            case TARGET_TEMPLATE_PROPERTY_NAME:
                targetTemplate = String.valueOf(nodeValue);
                break;
            case PARAMETERS_PROPERTY_NAME:
                parameters = String.valueOf(nodeValue);
                break;
            case DESCRIPTION_PROPERTY_NAME:
                description = String.valueOf(nodeValue);
                break;
        }
    }

}