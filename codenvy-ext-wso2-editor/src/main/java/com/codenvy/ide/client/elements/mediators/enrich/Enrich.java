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
package com.codenvy.ide.client.elements.mediators.enrich;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.AbstractElement;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The class which describes state of Send mediator and also has methods for changing it. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving. For more information about Send mediator go to
 * <a href=" https://docs.wso2.com/display/ESB460/Enrich+Mediator"> https://docs.wso2.com/display/ESB460/Enrich+Mediator</a>
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class Enrich extends AbstractElement {
    public static final String ELEMENT_NAME       = "Enrich";
    public static final String SERIALIZATION_NAME = "enrich";

    private static final String TARGET_PROPERTY_NAME = "target";
    private static final String SOURCE_PROPERTY_NAME = "source";

    private static final String DESCRIPTION_ATTRIBUTE_NAME = "description";

    private static final List<String> PROPERTIES = Arrays.asList(TARGET_PROPERTY_NAME, SOURCE_PROPERTY_NAME);

    private String description;
    private Source source;
    private Target target;

    @Inject
    public Enrich(EditorResources resources,
                  Provider<Branch> branchProvider,
                  MediatorCreatorsManager mediatorCreatorsManager,
                  Source source,
                  Target target) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        this.description = "";

        this.source = source;
        this.target = target;
    }

    /** @return source entity of enrich element */
    @Nonnull
    public Source getSource() {
        return source;
    }

    /**
     * Sets source entity of enrich element.
     *
     * @param source
     *         entity which need to set to element
     */
    public void setSource(@Nonnull Source source) {
        this.source = source;
    }

    /** @return target entity of enrich element */
    @Nonnull
    public Target getTarget() {
        return target;
    }

    /**
     * Sets target entity of enrich element.
     *
     * @param target
     *         entity which need to set to element
     */
    public void setTarget(@Nonnull Target target) {
        this.target = target;
    }

    /** @return description value of enrich element */
    @Nullable
    public String getDescription() {
        return description;
    }

    /**
     * Sets description value to enrich element
     *
     * @param description
     *         value which need to set to element
     */
    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        return source.serialize() + target.serialize();
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        Map<String, String> attributes = new LinkedHashMap<>();

        attributes.put(DESCRIPTION_ATTRIBUTE_NAME, description);

        return convertAttributesToXMLFormat(attributes);
    }

    /** {@inheritDoc} */
    @Override
    public void deserialize(@Nonnull Node node) {
        applyAttributes(node);

        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);

            if (TARGET_PROPERTY_NAME.equals(childNode.getNodeName())) {
                target.applyAttributes(childNode);
            } else {
                source.applyProperty(childNode);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void applyAttributes(@Nonnull Node node) {
        if (node.hasAttributes()) {
            Node attributeNode = node.getAttributes().item(0);

            description = String.valueOf(attributeNode.getNodeValue());
        }
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.enrich();
    }
}