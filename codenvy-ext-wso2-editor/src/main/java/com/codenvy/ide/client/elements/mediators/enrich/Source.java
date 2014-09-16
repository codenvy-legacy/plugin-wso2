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

import com.codenvy.ide.client.elements.AbstractEntityElement;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.util.StringUtils;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.codenvy.ide.client.elements.NameSpace.PREFIX;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.InlineType.RegistryKey;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.InlineType.SourceXML;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SourceType.custom;

/**
 * The class which describes state of Source element of Enrich mediator and also has methods for changing it. Also the class contains
 * the business logic that allows to display serialization representation depending of the current state of element. Deserelization
 * mechanism allows to restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class Source extends AbstractEntityElement {

    public static final String CLONE_SOURCE_ATTRIBUTE_NAME        = "clone";
    public static final String SOURCE_TYPE_ATTRIBUTE_NAME         = "type";
    public static final String INLINE_REGISTRY_KEY_ATTRIBUTE_NAME = "key";
    public static final String XPATH_ATTRIBUTE_NAME               = "xpath";
    public static final String PROPERTY_ATTRIBUTE_NAME            = "property";

    private final Provider<NameSpace> nameSpaceProvider;

    private boolean          clone;
    private SourceType       type;
    private InlineType       inlineType;
    private String           inlRegisterKey;
    private String           sourceXML;
    private String           xpath;
    private String           property;
    private Array<NameSpace> nameSpaces;

    @Inject
    public Source(Provider<NameSpace> nameSpaceProvider) {
        this.nameSpaceProvider = nameSpaceProvider;

        this.clone = false;
        this.type = custom;
        this.inlineType = SourceXML;

        this.inlRegisterKey = "/default/sequence";
        this.sourceXML = "<inline/>";
        this.xpath = "/default/xpath";
        this.property = "source_property";

        this.nameSpaces = Collections.createArray();
    }

    /** Serialization representation attributes of source property of element. */
    @Nonnull
    private String serializeAttributes() {
        Map<String, String> prop = new LinkedHashMap<>();

        if (clone) {
            prop.put(CLONE_SOURCE_ATTRIBUTE_NAME, String.valueOf(clone));
        }

        switch (type) {
            case custom:
                prop.put(XPATH_ATTRIBUTE_NAME, xpath);

                break;

            case property:
                prop.put(SOURCE_TYPE_ATTRIBUTE_NAME, type.name());
                prop.put(PROPERTY_ATTRIBUTE_NAME, property);

                break;

            case inline:
                prop.put(SOURCE_TYPE_ATTRIBUTE_NAME, type.name());

                if (inlineType.equals(RegistryKey)) {
                    prop.put(INLINE_REGISTRY_KEY_ATTRIBUTE_NAME, inlRegisterKey);
                }

                break;

            case envelope:
                prop.put(SOURCE_TYPE_ATTRIBUTE_NAME, type.name());

                break;

            case body:
                prop.put(SOURCE_TYPE_ATTRIBUTE_NAME, type.name());

                break;
        }

        return convertAttributesToXMLFormat(prop);
    }

    /** @return serialized representation of the source element */
    @Nonnull
    public String serialize() {
        StringBuilder result = new StringBuilder();

        result.append("<source ").append(convertNameSpaceToXMLFormat(nameSpaces)).append(serializeAttributes()).append(">\n");

        if (type.equals(SourceType.inline) && inlineType.equals(InlineType.SourceXML)) {

            int index = sourceXML.indexOf(">");

            String tag = sourceXML.substring(0, index);

            String tagName = sourceXML.substring(0, tag.contains("/") ? index - 1 : index);
            String restString = sourceXML.substring(tag.contains("/") ? index - 1 : index);

            result.append(tagName).append(" " + PREFIX + "=\"\"").append(restString);
        }

        result.append("</source>");

        return result.toString();
    }

    /**
     * Apply attributes from XML node to the diagram element
     *
     * @param node
     *         XML node that need to be analyzed
     */
    private void applyAttributes(@Nonnull Node node) {
        NamedNodeMap attributeMap = node.getAttributes();

        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attributeNode = attributeMap.item(i);

            String nodeName = attributeNode.getNodeName();
            String nodeValue = attributeNode.getNodeValue();

            switch (nodeName) {
                case CLONE_SOURCE_ATTRIBUTE_NAME:
                    clone = Boolean.valueOf(nodeValue);
                    break;

                case SOURCE_TYPE_ATTRIBUTE_NAME:
                    type = SourceType.valueOf(nodeValue);
                    break;

                case INLINE_REGISTRY_KEY_ATTRIBUTE_NAME:
                    inlRegisterKey = nodeValue;

                    type = SourceType.inline;
                    inlineType = InlineType.RegistryKey;
                    break;

                case XPATH_ATTRIBUTE_NAME:
                    xpath = nodeValue;
                    break;

                case PROPERTY_ATTRIBUTE_NAME:
                    property = nodeValue;
                    break;

                default:
                    if (StringUtils.startsWith(PREFIX, nodeName, true)) {
                        String name = StringUtils.trimStart(nodeName, PREFIX + ':');

                        NameSpace nameSpace = nameSpaceProvider.get();

                        nameSpace.setPrefix(name);
                        nameSpace.setUri(nodeValue);

                        nameSpaces.add(nameSpace);
                    }
            }
        }
    }

    /**
     * Apply properties from XML node to the diagram element
     *
     * @param node
     *         XML node that need to be analyzed
     */
    public void applyProperty(@Nonnull Node node) {
        applyAttributes(node);

        if (node.hasChildNodes()) {
            String item = node.getChildNodes().item(0).toString();

            int indexFirst = item.indexOf(" ");
            int indexLast = item.indexOf(">");

            String tagName = item.substring(0, indexLast);

            String xmlns = tagName.substring(indexFirst, tagName.contains("/") ? indexLast - 1 : indexLast);

            sourceXML = item.replace(xmlns, "");
        }
    }

    /** @return source clone value of source */
    public boolean getClone() {
        return clone;
    }

    /**
     * Sets source clone value for source.
     *
     * @param clone
     *         value which need to set to element
     */
    public void setClone(boolean clone) {
        this.clone = clone;
    }

    /** @return source type value of source */
    @Nonnull
    public SourceType getType() {
        return type;
    }

    /**
     * Sets source type value for source.
     *
     * @param type
     *         value which need to set to element
     */
    public void setType(@Nonnull SourceType type) {
        this.type = type;
    }

    /** @return inline type value of source */
    @Nonnull
    public InlineType getInlineType() {
        return inlineType;
    }

    /**
     * Sets inline type value for source.
     *
     * @param inlineType
     *         value which need to set to element
     */
    public void setInlineType(@Nonnull InlineType inlineType) {
        this.inlineType = inlineType;
    }

    /** @return inline registry key value of source */
    @Nonnull
    public String getInlRegisterKey() {
        return inlRegisterKey;
    }

    /**
     * Sets inline registry key value for source.
     *
     * @param inlRegisterKey
     *         value which need to set to element
     */
    public void setInlRegisterKey(@Nonnull String inlRegisterKey) {
        this.inlRegisterKey = inlRegisterKey;
    }

    /** @return source xml value of source */
    @Nonnull
    public String getSourceXML() {
        return sourceXML;
    }

    /**
     * Sets source xml value for source.
     *
     * @param sourceXML
     *         value which need to set to element
     */
    public void setSourceXML(@Nonnull String sourceXML) {
        this.sourceXML = sourceXML;
    }

    /** @return xpath value of source */
    @Nonnull
    public String getXpath() {
        return xpath;
    }

    /**
     * Sets source xpath value for source.
     *
     * @param xpath
     *         value which need to set to element
     */
    public void setXpath(@Nullable String xpath) {
        this.xpath = xpath;
    }

    /** @return property value of source */
    @Nonnull
    public String getProperty() {
        return property;
    }

    /**
     * Sets property value for source.
     *
     * @param property
     *         value which need to set to element
     */
    public void setProperty(@Nonnull String property) {
        this.property = property;
    }

    /** @return list of name spaces of source */
    @Nonnull
    public Array<NameSpace> getNameSpaces() {
        return nameSpaces;
    }

    /**
     * Sets list of name spaces for source.
     *
     * @param nameSpaces
     *         value which need to set to element
     */
    public void setNameSpaces(@Nonnull Array<NameSpace> nameSpaces) {
        this.nameSpaces = nameSpaces;
    }

    public enum SourceType {
        custom, envelope, body, property, inline;

        public static final String TYPE_NAME = "SourceType";
    }

    public enum InlineType {
        SourceXML, RegistryKey;

        public static final String INLINE_TYPE = "Inline type";
    }
}
