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

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.common.ContentFormatter;
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * The abstract implementation of {@link Element}. It contains the implementation of general methods which might not be changed.
 *
 * @author Andrey Plotnikov
 */
public abstract class AbstractElement implements Element, Comparable<AbstractElement> {

    private String  id;
    private Element parent;
    private String  title;
    private int     x;
    private int     y;

    private final String                  elementName;
    private final List<String>            properties;
    private final String                  serializationName;
    private final boolean                 isPossibleToAddBranches;
    private final boolean                 needsToShowIconAndTitle;
    private final MediatorCreatorsManager mediatorCreatorsManager;

    protected final EditorResources  resources;
    protected final Provider<Branch> branchProvider;
    protected final List<Branch>     branches;

    protected AbstractElement(@Nonnull String elementName,
                              @Nonnull String title,
                              @Nonnull String serializationName,
                              @Nonnull List<String> properties,
                              boolean isPossibleToAddBranches,
                              boolean needsToShowIconAndTitle,
                              @Nonnull EditorResources resources,
                              @Nonnull Provider<Branch> branchProvider,
                              @Nonnull MediatorCreatorsManager mediatorCreatorsManager) {
        this.elementName = elementName;
        this.title = title;
        this.serializationName = serializationName;
        this.properties = properties;

        id = UUID.get();

        this.isPossibleToAddBranches = isPossibleToAddBranches;
        this.needsToShowIconAndTitle = needsToShowIconAndTitle;

        this.branches = new ArrayList<>();

        this.resources = resources;
        this.branchProvider = branchProvider;
        this.mediatorCreatorsManager = mediatorCreatorsManager;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getId() {
        return id;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getTitle() {
        return title;
    }

    /** {@inheritDoc} */
    @Override
    public void setTitle(@Nonnull String title) {
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
    @Nonnull
    @Override
    public String getElementName() {
        return elementName;
    }

    /** @return the name which needs to be shown in the serialization view of element */
    @Nonnull
    public String getSerializationName() {
        return serializationName;
    }

    /** {@inheritDoc} */
    @Override
    public void setBranchesAmount(int amount) {
        if (!isPossibleToAddBranches || amount < 0) {
            return;
        }

        int size = branches.size();

        if (size > amount) {
            for (int i = size; i > amount; i--) {
                branches.remove(i - 1);
            }
        } else if (size < amount) {
            for (int i = size; i < amount; i++) {
                Branch branch = branchProvider.get();
                branch.setParent(this);

                branches.add(branch);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public int getBranchesAmount() {
        return branches.size();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public List<Branch> getBranches() {
        return Collections.unmodifiableList(branches);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPossibleToAddBranches() {
        return isPossibleToAddBranches;
    }

    /** {@inheritDoc} */
    @Override
    public int getX() {
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public void setX(int x) {
        this.x = x;
    }

    /** {@inheritDoc} */
    @Override
    public int getY() {
        return y;
    }

    /** {@inheritDoc} */
    @Override
    public void setY(int y) {
        this.y = y;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public int compareTo(@Nonnull AbstractElement element) {
        if (x < element.getX() || (x == element.getX() && y < element.getY())) {
            return -1;
        } else if (x > element.getX() || (x == element.getX() && y > element.getY())) {
            return 1;
        }

        return 0;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String serialize() {
        String serializeAttributes = serializeAttributes();

        StringBuilder content = new StringBuilder('<' + getSerializationName()
                                                  + (serializeAttributes.isEmpty() ? "" : ' ' + serializeAttributes) + ">\n");

        content.append(serializeProperties());

        for (Branch branch : getBranches()) {
            content.append(branch.serialize());
        }

        content.append("</").append(getSerializationName()).append(">\n");

        return content.toString();
    }

    /** @return diagram element properties in text format */
    @Nonnull
    protected String serializeProperties() {
        return "";
    }

    /** @return serialization representation of element attributes */
    @Nonnull
    protected String serializeAttributes() {
        return "";
    }

    /** {@inheritDoc} */
    @Override
    public void deserialize(@Nonnull String content) {
        Document xml = XMLParser.parse(ContentFormatter.trimXML(content));

        deserialize(xml.getFirstChild());
    }

    /** {@inheritDoc} */
    @Override
    public void deserialize(@Nonnull Node node) {
        branches.clear();
        Branch generalBranch = null;

        applyAttributes(node);

        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            String name = item.getNodeName();

            if (isProperty(name)) {
                applyProperty(item);
                continue;
            }

            Element element = createElement(name);

            if (element == null) {
                Branch branch = branchProvider.get();
                branch.setParent(this);
                branch.deserialize(item);

                branches.add(branch);
            } else {
                if (generalBranch == null) {
                    generalBranch = branchProvider.get();
                    generalBranch.setParent(this);
                }

                element.deserialize(item);

                generalBranch.addElement(element);
            }
        }

        if (generalBranch != null) {
            branches.add(generalBranch);
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean needsToShowIconAndTitle() {
        return needsToShowIconAndTitle;
    }

    /**
     * Returns <code>true</code> if a given XML tag name is property name.
     *
     * @param name
     *         XML tag name
     * @return <code>true</code> if a given XML tag name is property name, <code>false</code> if it is not
     */
    protected boolean isProperty(@Nonnull String name) {
        return properties.contains(name);
    }

    /**
     * Apply property from XML node to the diagram element.
     *
     * @param node
     *         XML node that need to be analyzed
     */
    protected void applyProperty(@Nonnull Node node) {
    }

    /**
     * Apply attributes from XML node to the diagram element
     *
     * @param node
     *         XML node that need to be analyzed
     */
    protected void applyAttributes(@Nonnull Node node) {
    }

    /**
     * Convert attributes of diagram element to XML attribute format.
     *
     * @param attributes
     *         element's properties
     * @return XML format of element's attributes
     */
    protected String convertAttributesToXMLFormat(@Nonnull Map<String, String> attributes) {
        StringBuilder content = new StringBuilder();

        for (Iterator iterator = attributes.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry entry = (Map.Entry)iterator.next();
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

    /**
     * Convert properties of diagram element to XML attribute format.
     *
     * @param properties
     *         element's properties
     * @return XML format of element's attributes
     */
    protected String convertPropertiesToXMLFormat(@Nonnull Map<String, String> properties) {
        StringBuilder content = new StringBuilder();

        for (Iterator iterator = properties.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry entry = (Map.Entry)iterator.next();
            String value = (String)entry.getValue();

            if (value != null && !value.isEmpty()) {
                content.append('<').append(entry.getKey()).append('>').append(value).append("</").append(entry.getKey()).append('>');
            }

            if (iterator.hasNext()) {
                content.append('\n');
            }
        }

        return content.toString();
    }

    /**
     * Return instance of found element if it is possible or <code>null</code>.
     *
     * @param elementName
     *         name of element that needs to be created
     * @return ans instance of diagram element or <code>null</code>
     */
    @Nullable
    protected Element createElement(@Nonnull String elementName) {
        Provider<? extends Element> provider = mediatorCreatorsManager.getProviderBySerializeName(elementName);

        return provider == null ? null : provider.get();
    }

}