/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package com.codenvy.ide.client.elements;

import com.codenvy.ide.client.managers.MediatorCreatorsManager;
import com.codenvy.ide.util.StringUtils;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains a business logic for changing, serialization, deserialization of element which is a branch.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class BranchImpl implements Branch {

    private final String                  id;
    private final List<AbstractElement>   elements;
    private final Map<String, String>     attributes;
    private final MediatorCreatorsManager mediatorCreatorsManager;

    private String  title;
    private String  name;
    private Element parent;

    @Inject
    public BranchImpl(MediatorCreatorsManager mediatorCreatorsManager) {
        this.mediatorCreatorsManager = mediatorCreatorsManager;

        id = UUID.get();

        elements = new ArrayList<>();
        attributes = new LinkedHashMap<>();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getId() {
        return id;
    }

    /** {@inheritDoc} */
    @Override
    public void setName(@Nullable String name) {
        this.name = name;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public String getTitle() {
        return title;
    }

    /** {@inheritDoc} */
    @Override
    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public Element getParent() {
        return parent;
    }

    /** {@inheritDoc} */
    @Override
    public void setParent(@Nullable Element parent) {
        this.parent = parent;
    }

    /** {@inheritDoc} */
    @Override
    public void addElement(@Nonnull Element element) {
        elements.add((AbstractElement)element);
    }

    /** {@inheritDoc} */
    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public void removeElement(@Nonnull Element element) {
        elements.remove(element);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public List<Element> getElements() {
        Collections.sort(elements);

        List<Element> result = new ArrayList<>();
        result.addAll(elements);

        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasElements() {
        return !elements.isEmpty();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String serialize() {
        StringBuilder content = new StringBuilder();

        if (name != null) {
            content.append('<').append(name).append(' ').append(convertAttributesToXML()).append('>');
        }

        for (Element element : getElements()) {
            content.append(element.serialize());
        }

        if (name != null) {
            content.append("</").append(name).append('>');
        }

        return content.toString();
    }

    /** @return xml representation of attributes of element */
    @Nonnull
    private String convertAttributesToXML() {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> attribute : attributes.entrySet()) {
            result.append(attribute.getKey()).append("=\"").append(attribute.getValue()).append("\" ");
        }

        return result.toString();
    }

    /** {@inheritDoc} */
    @Override
    public void deserialize(@Nonnull Node node) {
        String name = node.getNodeName();
        NodeList childNodes = node.getChildNodes();

        this.name = name;
        this.title = StringUtils.capitalizeFirstLetter(name);

        deserializeAttributes(node);

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            String nodeName = item.getNodeName();

            Provider<? extends Element> elementProvider = mediatorCreatorsManager.getProviderBySerializeName(nodeName);
            Element element = elementProvider == null ? null : elementProvider.get();

            if (element == null) {
                continue;
            }

            element.deserialize(item);
            elements.add((AbstractElement)element);
        }
    }

    /**
     * Deserialize attributes of current node.
     *
     * @param node
     *         XML node that need to be deserialized
     */
    private void deserializeAttributes(@Nonnull Node node) {
        attributes.clear();

        NamedNodeMap attributeMap = node.getAttributes();

        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attributeNode = attributeMap.item(i);

            attributes.put(attributeNode.getNodeName(), attributeNode.getNodeValue());
        }
    }

    /** {@inheritDoc} */
    @Override
    public void addAttribute(@Nonnull String name, @Nonnull String value) {
        attributes.put(name, value);
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public String getAttributeByName(@Nonnull String name) {
        return attributes.get(name);
    }

}