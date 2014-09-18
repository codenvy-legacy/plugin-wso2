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
package com.codenvy.ide.client.elements;

import com.codenvy.ide.client.managers.ElementCreatorsManager;
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
 * The entity that represents a diagram element branch. It can have different state. It depends on a mediator. It can be a 'Case' branch of
 * Switch mediator or 'Then' branch of Filter mediator and etc. Contains a business logic for changing, serialization, deserialization of
 * element which is a branch.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class Branch {

    private final String                 id;
    private final List<AbstractElement>  elements;
    private final Map<String, String>    attributes;
    private final ElementCreatorsManager elementCreatorsManager;

    private String  title;
    private String  name;
    private Element parent;

    @Inject
    public Branch(ElementCreatorsManager elementCreatorsManager) {
        this.elementCreatorsManager = elementCreatorsManager;

        id = UUID.get();

        elements = new ArrayList<>();
        attributes = new LinkedHashMap<>();
    }

    /**
     * @return an unique branch identifier. All instances of branches will have an unique identifier. This means that anyone can find
     * needed branch.
     */
    @Nonnull
    public String getId() {
        return id;
    }

    /**
     * Set name of branch. The name will be used for serialization and deserialization like a identifier of the branch.
     *
     * @param name
     *         name that needs to be set
     */
    public void setName(@Nullable String name) {
        this.name = name;
    }

    /** @return title of the branch */
    @Nullable
    public String getTitle() {
        return title;
    }

    /**
     * Set title of the branch. Te title will be shown in the widget.
     *
     * @param title
     *         title that needs to be set
     */
    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    /** @return parent of the branch */
    @Nullable
    public Element getParent() {
        return parent;
    }

    /**
     * Set parent of branch.
     *
     * @param parent
     *         parent that needs to be added
     */
    public void setParent(@Nullable Element parent) {
        this.parent = parent;
    }

    /**
     * Add an inner element into this one element.
     *
     * @param element
     *         element that need to be added
     */
    public void addElement(@Nonnull Element element) {
        elements.add((AbstractElement)element);
    }

    /**
     * Remove a inner element from this one element.
     *
     * @param element
     *         element that need to be removed
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    public void removeElement(@Nonnull Element element) {
        elements.remove(element);
    }

    /** @return a list of inner elements */
    @Nonnull
    public List<Element> getElements() {
        Collections.sort(elements);

        List<Element> result = new ArrayList<>();
        result.addAll(elements);

        return result;
    }

    /** @return true if branch has elements */
    public boolean hasElements() {
        return !elements.isEmpty();
    }

    /** @return serialized representation of the branch */
    @Nonnull
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

    /**
     * Deserialize branch.
     *
     * @param node
     *         XML node that need to be deserialized
     */
    public void deserialize(@Nonnull Node node) {
        String name = node.getNodeName();
        NodeList childNodes = node.getChildNodes();

        this.name = name;
        this.title = StringUtils.capitalizeFirstLetter(name);

        deserializeAttributes(node);

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            String nodeName = item.getNodeName();

            Provider<? extends Element> elementProvider = elementCreatorsManager.getProviderBySerializeName(nodeName);
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

    /**
     * Adds XML attribute.
     *
     * @param name
     *         attribute name
     * @param value
     *         attribute value
     */
    public void addAttribute(@Nonnull String name, @Nonnull String value) {
        attributes.put(name, value);
    }

    /**
     * Returns attribute value by attribute name or <code>null</code> when attribute with a given name is absent.
     *
     * @param name
     *         attribute name that needs to be found
     * @return attribute value
     */
    @Nullable
    public String getAttributeByName(@Nonnull String name) {
        return attributes.get(name);
    }

}