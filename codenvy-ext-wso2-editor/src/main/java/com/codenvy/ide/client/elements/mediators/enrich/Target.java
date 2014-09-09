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
package com.codenvy.ide.client.elements.mediators.enrich;

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

import static com.codenvy.ide.client.elements.NameSpace.PREFIX;

/**
 * The class which describes state of Target element of Enrich mediator and also has methods for changing it. Also the class contains
 * the business logic that allows to display serialization representation depending of the current state of element. Deserelization
 * mechanism allows to restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Andrey Plotnikov
 */
public class Target {

    public static final String ACTION_ATTRIBUTE_NAME   = "action";
    public static final String TYPE_ATTRIBUTE_NAME     = "type";
    public static final String XPATH_ATTRIBUTE_NAME    = "xpath";
    public static final String PROPERTY_ATTRIBUTE_NAME = "property";

    private final Provider<NameSpace> nameSpaceProvider;

    private TargetAction     action;
    private TargetType       type;
    private String           xpath;
    private String           property;
    private Array<NameSpace> nameSpaces;

    @Inject
    public Target(Provider<NameSpace> nameSpaceProvider) {
        this.nameSpaceProvider = nameSpaceProvider;

        this.xpath = "/default/xpath";
        this.property = "target_property";

        this.action = TargetAction.replace;
        this.type = TargetType.custom;

        this.nameSpaces = Collections.createArray();
    }

    /** Serialization representation attributes of target property of element. */
    private String serializeAttributes() {
        StringBuilder result = new StringBuilder();

        if (!TargetAction.replace.equals(action)) {
            result.append(ACTION_ATTRIBUTE_NAME).append("=\"").append(action).append('"').append(" ");
        }

        switch (type) {
            case custom:
                result.append(XPATH_ATTRIBUTE_NAME).append("=\"").append(xpath).append('"').append(" ");
                break;

            case property:
                result.append(TYPE_ATTRIBUTE_NAME).append("=\"").append(type).append('"').append(" ");
                result.append(PROPERTY_ATTRIBUTE_NAME).append("=\"").append(property).append('"').append(" ");
                break;

            case body:
                result.append(TYPE_ATTRIBUTE_NAME).append("=\"").append(type).append('"').append(" ");
                break;

            case envelope:
                result.append(TYPE_ATTRIBUTE_NAME).append("=\"").append(type).append('"').append(" ");
                break;
        }

        return result.toString();
    }

    /** @return serialized representation of the target element */
    @Nonnull
    public String serialize() {
        StringBuilder result = new StringBuilder();

        StringBuilder targetNameSpaces = new StringBuilder();

        for (NameSpace nameSpace : nameSpaces.asIterable()) {
            targetNameSpaces.append(nameSpace.toString()).append(' ');
        }

        result.append("<target ").append(targetNameSpaces).append(" ").append(serializeAttributes()).append("/>\n");

        return result.toString();
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
                case ACTION_ATTRIBUTE_NAME:
                    action = TargetAction.valueOf(nodeValue);
                    break;

                case TYPE_ATTRIBUTE_NAME:
                    type = TargetType.valueOf(nodeValue);
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

    /** @return action value of target */
    @Nonnull
    public TargetAction getAction() {
        return action;
    }

    /**
     * Sets action value for target.
     *
     * @param action
     *         value which need to set to element
     */
    public void setAction(@Nonnull TargetAction action) {
        this.action = action;
    }

    /** @return type value of target */
    @Nonnull
    public TargetType getType() {
        return type;
    }

    /**
     * Sets type value for target.
     *
     * @param type
     *         value which need to set to element
     */
    public void setType(@Nonnull TargetType type) {
        this.type = type;
    }

    /** @return xpath value of target */
    @Nonnull
    public String getXpath() {
        return xpath;
    }

    /**
     * Sets xpath value for target.
     *
     * @param xpath
     *         value which need to set to element
     */
    public void setXpath(@Nullable String xpath) {
        this.xpath = xpath;
    }

    /** @return property value of target */
    @Nonnull
    public String getProperty() {
        return property;
    }

    /**
     * Sets property value for target.
     *
     * @param property
     *         value which need to set to element
     */
    public void setProperty(@Nonnull String property) {
        this.property = property;
    }

    /** @return list name spaces of target */
    @Nonnull
    public Array<NameSpace> getNameSpaces() {
        return nameSpaces;
    }

    /**
     * Sets list of name spaces for target.
     *
     * @param nameSpaces
     *         list which need to set to element
     */
    public void setNameSpaces(Array<NameSpace> nameSpaces) {
        this.nameSpaces = nameSpaces;
    }

    public enum TargetAction {
        replace, child, sibling;

        public static final String TYPE_NAME = "TargetAction";
    }

    public enum TargetType {
        custom, envelope, body, property;

        public static final String TYPE_NAME = "TargetType";
    }
}
