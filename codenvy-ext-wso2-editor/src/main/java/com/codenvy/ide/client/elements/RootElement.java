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
import com.codenvy.ide.client.common.ContentFormatter;
import com.codenvy.ide.client.elements.enrich.Enrich;
import com.codenvy.ide.client.elements.log.Log;
import com.codenvy.ide.client.elements.payload.PayloadFactory;
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The main element of the diagram.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class RootElement extends AbstractShape {
    public static final String ELEMENT_NAME       = "RootElement";
    public static final String SERIALIZATION_NAME = "sequence";

    private static final String NAME_SPACE_ATTRIBUTE = " xmlns=\"http://ws.apache.org/ns/synapse\" ";
    private static final String XML_HEADER           = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

    private static final String NAME_ATTRIBUTE_NAME     = "name";
    private static final String ON_ERROR_ATTRIBUTE_NAME = "onError";

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

    private String name;
    private String onError;

    @Inject
    public RootElement(EditorResources resources, Provider<Branch> branchProvider, MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, false, resources, branchProvider, mediatorCreatorsManager);

        this.name = "";
        this.onError = "";

        components.addAll(COMPONENTS);

        branches.add(branchProvider.get());
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public void setName(@Nonnull String name) {
        this.name = name;
    }

    @Nonnull
    public String getOnError() {
        return onError;
    }

    public void setOnError(@Nonnull String onError) {
        this.onError = onError;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String serialize() {
        String xml = ContentFormatter.formatXML(ContentFormatter.trimXML(super.serialize()));

        /*
         * Adds XML file header and name space for root element. We have problem with formatting XML content with name space
         * in the root node so we add the name space of root element after formatting operation.
         */
        int index = xml.indexOf(SERIALIZATION_NAME) + SERIALIZATION_NAME.length();
        return XML_HEADER + xml.subSequence(0, index) + NAME_SPACE_ATTRIBUTE + xml.subSequence(index + 1, xml.length());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeAttributes() {
        Map<String, String> attributes = new LinkedHashMap<>();
        attributes.put(ON_ERROR_ATTRIBUTE_NAME, onError);

        String onErrorAttribute = convertPropertiesToXMLFormat(attributes);

        return NAME_ATTRIBUTE_NAME + "=\"" + name + "\"" + (onErrorAttribute.isEmpty() ? "" : ' ' + onErrorAttribute);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyAttributes(@Nonnull Node node) {
        NamedNodeMap attributeMap = node.getAttributes();

        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attributeNode = attributeMap.item(i);

            String nodeValue = attributeNode.getNodeValue();
            String nodeName = attributeNode.getNodeName();

            switch (nodeName) {
                case NAME_ATTRIBUTE_NAME:
                    name = nodeValue;
                    break;

                case ON_ERROR_ATTRIBUTE_NAME:
                    onError = nodeValue;
                    break;
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void deserialize(@Nonnull String content) {
        super.deserialize(content);

        if (branches.isEmpty()) {
            Branch branch = branchProvider.get();
            branch.setParent(this);

            branches.add(branch);
        }
    }

}