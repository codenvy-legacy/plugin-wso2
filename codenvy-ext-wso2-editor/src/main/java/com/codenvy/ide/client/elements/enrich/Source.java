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
package com.codenvy.ide.client.elements.enrich;

import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.util.StringUtils;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.codenvy.ide.client.elements.NameSpace.PREFIX;
import static com.codenvy.ide.client.elements.enrich.Enrich.CloneSource.FALSE;
import static com.codenvy.ide.client.elements.enrich.Enrich.InlineType.SourceXML;
import static com.codenvy.ide.client.elements.enrich.Enrich.SourceType.custom;

/**
 * Class describes entity which presented source element of Enrich mediator.
 *
 * @author Dmitry Shnurenko
 */
public class Source {

    public static final String CLONE_SOURCE        = "clone";
    public static final String SOURCE_TYPE         = "type";
    public static final String INLINE_REGISTRY_KEY = "key";
    public static final String XPATH               = "xpath";
    public static final String PROPERTY            = "property";

    private String           clone;
    private String           type;
    private String           inlineType;
    private String           inlRegisterKey;
    private String           sourceXML;
    private String           xpath;
    private String           property;
    private Array<NameSpace> nameSpaces;

    public Source() {
        this.clone = FALSE.name().toLowerCase();
        this.type = custom.name();
        this.inlineType = SourceXML.name();

        this.inlRegisterKey = "/default/sequence";
        this.sourceXML = "<inline/>";
        this.xpath = "/default/xpath";
        this.property = "source_property";

        this.nameSpaces = Collections.createArray();
    }

    /**
     * Apply attributes from XML node to the diagram element
     *
     * @param node
     *         XML node that need to be analyzed
     */
    public void applyAttributes(@Nonnull Node node) {
        NamedNodeMap attributeMap = node.getAttributes();

        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attributeNode = attributeMap.item(i);

            String nodeName = attributeNode.getNodeName();
            String nodeValue = attributeNode.getNodeValue();

            switch (nodeName) {
                case CLONE_SOURCE:
                    clone = nodeValue;
                    break;

                case SOURCE_TYPE:
                    type = nodeValue;
                    break;

                case INLINE_REGISTRY_KEY:
                    inlRegisterKey = nodeValue;
                    break;

                case XPATH:
                    xpath = nodeValue;
                    break;

                case PROPERTY:
                    property = nodeValue;
                    break;

                case PREFIX:
                    String name = StringUtils.trimStart(nodeName, PREFIX + '=');
                    //TODO create entity using edit factory
                    NameSpace nameSpace = new NameSpace(name, nodeValue);

                    nameSpaces.add(nameSpace);
                    break;
            }
        }
    }

    /** @return source clone value of source */
    @Nonnull
    public String getClone() {
        return clone;
    }

    /**
     * Sets source clone value for source.
     *
     * @param clone
     *         value which need to set to element
     */
    public void setClone(@Nonnull String clone) {
        this.clone = clone;
    }

    /** @return source type value of source */
    @Nonnull
    public String getType() {
        return type;
    }

    /**
     * Sets source type value for source.
     *
     * @param type
     *         value which need to set to element
     */
    public void setType(@Nonnull String type) {
        this.type = type;
    }

    /** @return inline type value of source */
    @Nonnull
    public String getInlineType() {
        return inlineType;
    }

    /**
     * Sets inline type value for source.
     *
     * @param inlineType
     *         value which need to set to element
     */
    public void setInlineType(@Nonnull String inlineType) {
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
}
