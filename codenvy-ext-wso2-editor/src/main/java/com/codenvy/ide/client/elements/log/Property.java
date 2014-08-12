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
package com.codenvy.ide.client.elements.log;

import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.util.StringUtils;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.codenvy.ide.client.elements.NameSpace.PREFIX;

/**
 * Class describes entity which presented property of mediator.
 *
 * @author Dmitry Shnurenko
 */
public class Property {

    private static final String PROPERTY_ELEMENT_NAME  = "name";
    private static final String PROPERTY_ELEMENT_VALUE = "value";

    private String           name;
    private String           expression;
    private Array<NameSpace> nameSpaces;

    public Property(@Nullable String name, @Nullable String expression) {
        this.name = name;
        this.expression = expression;
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
                case PROPERTY_ELEMENT_NAME:
                    name = nodeValue;
                    break;

                case PROPERTY_ELEMENT_VALUE:
                    expression = nodeValue;
                    break;

                case PREFIX:
                    String name = StringUtils.trimStart(nodeName, PREFIX);
                    //TODO create entity using edit factory
                    NameSpace nameSpace = new NameSpace(name, nodeValue);

                    nameSpaces.add(nameSpace);
                    break;
            }
        }
    }

    /** @return namespaces which contain in property */
    @Nonnull
    public Array<NameSpace> getNameSpaces() {
        return nameSpaces;
    }

    /** Sets namespaces to property */
    public void setNameSpaces(@Nonnull Array<NameSpace> nameSpaces) {
        this.nameSpaces = nameSpaces;
    }

    /** @return name of namespace */
    @Nonnull
    public String getName() {
        return name;
    }

    /** Sets name of namespace */
    public void setName(@Nonnull String name) {
        this.name = name;
    }

    /** @return expression of namespace */
    @Nonnull
    public String getExpression() {
        return expression;
    }

    /** @return copy of element */
    public Property clone() {
        //TODO create property using editor factory
        Property property = new Property(name, expression);
        property.setNameSpaces(nameSpaces);

        return property;
    }

}
