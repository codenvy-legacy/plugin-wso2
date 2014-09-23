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

import com.codenvy.ide.client.elements.AbstractEntityElement;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.util.StringUtils;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

import static com.codenvy.ide.client.elements.NameSpace.PREFIX;

/**
 * The class which describes state of property element of Log mediator and also has methods for changing it. Also the class contains the
 * business logic that allows to display serialization representation depending of the current state of element. Deserelization mechanism
 * allows to restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class Property extends AbstractEntityElement {

    public static final Key<String>          NAME       = new Key<>("MediatorPropertyName");
    public static final Key<String>          EXPRESSION = new Key<>("MediatorPropertyExpression");
    public static final Key<List<NameSpace>> NAMESPACES = new Key<>("MediatorPropertyNameSpaces");

    private static final String NAME_ATTRIBUTE  = "name";
    private static final String VALUE_ATTRIBUTE = "value";

    private static final String WITH_PARAM_SERIALIZATION_NAME = "with-param";
    private static final String PROPERTY_SERIALIZATION_NAME   = "property";

    private final Provider<Property>  propertyProvider;
    private final Provider<NameSpace> nameSpaceProvider;

    @Inject
    public Property(Provider<Property> propertyProvider, Provider<NameSpace> nameSpaceProvider) {
        this.propertyProvider = propertyProvider;
        this.nameSpaceProvider = nameSpaceProvider;

        putProperty(NAME, "");
        putProperty(EXPRESSION, "");
        putProperty(NAMESPACES, Collections.<NameSpace>emptyList());
    }

    /** Returns serialization representation CallTemplate element's property. */
    @Nonnull
    public String serializeWithParam() {
        return '<' + WITH_PARAM_SERIALIZATION_NAME + ' ' + convertNameSpaceToXMLFormat(getProperty(NAMESPACES)) +
               "name=\"" + getProperty(NAME) + "\" value=\"" + getProperty(EXPRESSION) + "\"/>";

    }

    /** Returns serialization representation element's property. */
    @Nonnull
    public String serializeProperty() {
        return '<' + PROPERTY_SERIALIZATION_NAME + ' ' + convertNameSpaceToXMLFormat(getProperty(NAMESPACES)) +
               "name=\"" + getProperty(NAME) + "\" value=\"" + getProperty(EXPRESSION) + "\"/>";
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
                    putProperty(NAME, nodeValue);
                    break;

                case VALUE_ATTRIBUTE:
                    putProperty(EXPRESSION, nodeValue);
                    break;

                default:
                    applyNameSpaces(nodeName, nodeValue);
            }
        }
    }

    private void applyNameSpaces(@Nonnull String nodeName, @Nonnull String nodeValue) {
        List<NameSpace> nameSpaces = getProperty(NAMESPACES);

        if (!StringUtils.startsWith(PREFIX, nodeName, true) || nameSpaces == null) {
            return;
        }

        String name = StringUtils.trimStart(nodeName, PREFIX + ':');

        NameSpace nameSpace = nameSpaceProvider.get();

        nameSpace.setPrefix(name);
        nameSpace.setUri(nodeValue);

        nameSpaces.add(nameSpace);
    }

    /** @return copy of property element */
    public Property copy() {
        return propertyProvider.get();
    }

}
