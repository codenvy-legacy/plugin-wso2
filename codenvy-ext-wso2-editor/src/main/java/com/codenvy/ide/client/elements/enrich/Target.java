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
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Class describes entity which presented target element of Enrich mediator.
 *
 * @author Dmitry Shnurenko
 */
public class Target {

    public static final String ACTION   = "action";
    public static final String TYPE     = "type";
    public static final String XPATH    = "xpath";
    public static final String PROPERTY = "property";

    private static final String PREFIX = "xmlns=";

    private String           action;
    private String           type;
    private String           xpath;
    private String           property;
    private Array<NameSpace> nameSpaces;

    public Target() {
        this.xpath = "/default/xpath";
        this.property = "target_property";

        this.action = Enrich.TargetAction.replace.name();
        this.type = Enrich.TargetType.custom.name();

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

            switch (attributeNode.getNodeName()) {
                case ACTION:
                    action = attributeNode.getNodeValue();
                    break;

                case TYPE:
                    type = attributeNode.getNodeValue();
                    break;

                case XPATH:
                    xpath = attributeNode.getNodeValue();
                    break;

                case PROPERTY:
                    property = attributeNode.getNodeValue();
                    break;

                case PREFIX:
                    //TODO create entity using editor factory
                    NameSpace nameSpace = new NameSpace(null, null);
                    nameSpace.applyAttributes(node);

                    nameSpaces.add(nameSpace);
                    break;
            }
        }
    }

    /** @return action value of target */
    @Nonnull
    public String getAction() {
        return action;
    }

    /**
     * Sets action value for target.
     *
     * @param action
     *         value which need to set to element
     */
    public void setAction(@Nonnull String action) {
        this.action = action;
    }

    /** @return type value of target */
    @Nonnull
    public String getType() {
        return type;
    }

    /**
     * Sets type value for target.
     *
     * @param type
     *         value which need to set to element
     */
    public void setType(@Nonnull String type) {
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
}
