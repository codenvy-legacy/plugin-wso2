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
package com.codenvy.ide.ext.wso2.client.commons;

import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The utility class that provides an ability to format XSD and to get the attributes of the tag.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
@Singleton
public class XsdSchemaParser {
    public static final String NAME_NODE            = "name";
    public static final String MEDIATOR_NODE        = "mediator";
    public static final String ENDPOINT_NODE        = "endpoint";
    public static final String COMPLEX_CONTENT_NODE = "complexcontent";
    public static final String EXTENSION_NODE       = "extension";
    public static final String ATTRIBUTE_NODE       = "attribute";
    public static final String COMPLEX_TYPE_NODE    = "complexType";
    public static final String PREFIX_NODE          = "xsd:";

    private WSO2Resources resources;
    private String        tag;

    @Inject
    public XsdSchemaParser(WSO2Resources resources) {
        this.resources = resources;
    }

    /**
     * Returns an array of tag attributes.
     *
     * @param tag
     *         name of the tag
     * @return an array of attributes.
     */
    public Array<String> getAttributes(@Nonnull String tag) {
        this.tag = tag;
        Document xml = XMLParser.parse(resources.xmlSchemaDefinition().getText());
        NodeList childNodes = xml.getFirstChild().getChildNodes();
        Array<String> attributesName = Collections.createArray();

        findComplexTypeNodes(childNodes, attributesName);
        attributesName.add("description");

        return attributesName;
    }

    /**
     * Compute attributes.
     *
     * @param nodes
     *         list of parent node
     * @param attributesName
     *         array of attribute names.
     */
    private void computeAttributes(@Nullable NodeList nodes, @Nonnull Array<String> attributesName) {
        nodes = getNodesWithAttributes(COMPLEX_CONTENT_NODE, nodes);
        nodes = getNodesWithAttributes(EXTENSION_NODE, nodes);
        if (nodes != null) {
            for (int i = 0; i < nodes.getLength(); i++) {

                Node node = nodes.item(i);

                if (ATTRIBUTE_NODE.equalsIgnoreCase(node.getNodeName()) ||
                    (PREFIX_NODE + ATTRIBUTE_NODE).equalsIgnoreCase(node.getNodeName())) {
                    NamedNodeMap attributesOfAttributeNode = node.getAttributes();
                    computeAttributeNames(attributesOfAttributeNode, attributesName);
                }
            }
        }
    }

    /**
     * Compute name of attributes.
     *
     * @param attributesOfAttributeNode
     *         a map with attributes
     * @param attributesName
     *         an array with names of attributes.
     */
    private void computeAttributeNames(@Nonnull NamedNodeMap attributesOfAttributeNode, @Nonnull Array<String> attributesName) {
        for (int i = 0; i < attributesOfAttributeNode.getLength(); i++) {

            Node attribute = attributesOfAttributeNode.item(i);

            if (NAME_NODE.equals(attribute.getNodeName())) {
                String attributeValue = attribute.getNodeValue();
                if (attributeValue.toLowerCase().startsWith(tag)) {
                    attributeValue = attributeValue.substring(tag.length(), attributeValue.length());
                    attributeValue = attributeValue.substring(0, 1).toLowerCase() + attributeValue.substring(1, attributeValue.length());
                }
                attributesName.add(attributeValue);
                break;
            }
        }
    }

    /**
     * Gets list of nodes that have the attributes.
     *
     * @param nodeName
     *         name of the node
     * @param attributeNodes
     *         list of the attributes
     * @return the list of nodes with the the attributes.
     */
    private NodeList getNodesWithAttributes(@Nonnull String nodeName, @Nullable NodeList attributeNodes) {
        if (attributeNodes != null) {

            for (int i = 0; i < attributeNodes.getLength(); i++) {
                if (attributeNodes.item(i).getNodeName().equalsIgnoreCase(nodeName) ||
                    attributeNodes.item(i).getNodeName().equalsIgnoreCase(PREFIX_NODE + nodeName)) {
                    attributeNodes = attributeNodes.item(i).getChildNodes();
                    return attributeNodes;
                }
            }
        }
        return null;
    }

    /**
     * Finds nodes with 'complexType' name.
     *
     * @param nodes
     *         list of nodes
     * @param attributesName
     *         array of attributes.
     */
    private void findComplexTypeNodes(@Nonnull NodeList nodes, @Nonnull Array<String> attributesName) {
        for (int i = 0; i < nodes.getLength(); i++) {

            Node child = nodes.item(i);

            if (COMPLEX_TYPE_NODE.equalsIgnoreCase(child.getNodeName()) ||
                (PREFIX_NODE + COMPLEX_TYPE_NODE).equalsIgnoreCase(child.getNodeName())) {
                computeChildOfComplexTypeNode(child.getAttributes(), child, attributesName);
            }
            if (attributesName.size() > 0) {
                return;
            }
        }
    }

    /**
     * Computes child of node with 'complexType' name.
     *
     * @param attributes
     *         map of attributes
     * @param parent
     *         parent node
     * @param attributesName
     *         array of attribute names.
     */
    private void computeChildOfComplexTypeNode(@Nonnull NamedNodeMap attributes,
                                               @Nonnull Node parent,
                                               @Nonnull Array<String> attributesName) {

        for (int i = 0; i < attributes.getLength(); i++) {

            String nodeValue = attributes.item(i).getNodeValue();

            if (NAME_NODE.equals(attributes.item(i).getNodeName()) &&
                ((tag + MEDIATOR_NODE).equalsIgnoreCase(nodeValue) || (tag + ENDPOINT_NODE).equalsIgnoreCase(nodeValue))) {
                computeAttributes(parent.getChildNodes(), attributesName);
                return;
            }
        }
    }
}
