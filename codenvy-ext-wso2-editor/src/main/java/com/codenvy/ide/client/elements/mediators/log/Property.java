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
package com.codenvy.ide.client.elements.mediators.log;

import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.util.StringUtils;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.NameSpace.PREFIX;

/**
 * The class which describes state of property element of Log mediator and also has methods for changing it. Also the class contains the
 * business logic that allows to display serialization representation depending of the current state of element. Deserelization mechanism
 * allows to restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Andrey Plotnikov
 */
public class Property {

    private static final String NAME_ATTRIBUTE  = "name";
    private static final String VALUE_ATTRIBUTE = "value";

    private final Provider<Property>  propertyProvider;
    private final Provider<NameSpace> nameSpaceProvider;

    private String           name;
    private String           expression;
    private Array<NameSpace> nameSpaces;

    @Inject
    public Property(Provider<Property> propertyProvider, Provider<NameSpace> nameSpaceProvider) {
        this.propertyProvider = propertyProvider;
        this.nameSpaceProvider = nameSpaceProvider;
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
                case NAME_ATTRIBUTE:
                    name = nodeValue;
                    break;

                case VALUE_ATTRIBUTE:
                    expression = nodeValue;
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

    /** @return namespaces which contain in property */
    @Nonnull
    public Array<NameSpace> getNameSpaces() {
        return nameSpaces;
    }

    /**
     * Sets namespaces to property
     *
     * @param nameSpaces
     *         list of name spaces that should be set
     */
    public void setNameSpaces(@Nonnull Array<NameSpace> nameSpaces) {
        this.nameSpaces = nameSpaces;
    }

    /** @return name of namespace */
    @Nonnull
    public String getName() {
        return name;
    }

    /**
     * Sets name of namespace
     *
     * @param name
     *         value that should be set
     */
    public void setName(@Nonnull String name) {
        this.name = name;
    }

    /** @return expression of namespace */
    @Nonnull
    public String getExpression() {
        return expression;
    }

    /**
     * Set expression of namespace
     *
     * @param expression
     *         expression that should be set
     */
    public void setExpression(@Nonnull String expression) {
        this.expression = expression;
    }

    /** @return copy of property element */
    public Property clone() {
        Property property = propertyProvider.get();

        property.setName(name);
        property.setExpression(expression);
        property.setNameSpaces(nameSpaces);

        return property;
    }

}
