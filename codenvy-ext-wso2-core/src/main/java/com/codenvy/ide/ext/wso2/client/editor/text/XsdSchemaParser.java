/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 *  [2012] - [2014] Codenvy, S.A. 
 *  All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
 */
package com.codenvy.ide.ext.wso2.client.editor.text;

import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The utility class that provides an ability to format XSD and to get the attributes of the tag.
 *
 * @author Valeriy Svydenko
 */
@Singleton
public class XsdSchemaParser {

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
    public Array<String> getAttributes(String tag) {
        this.tag = tag;
        com.google.gwt.xml.client.Document xml = XMLParser.parse(resources.xmlSchemaDefinition().getText());
        NodeList childNodes = xml.getFirstChild().getChildNodes();
        Array<String> attributesName = Collections.createArray();

        findComplexTypeNodes(childNodes, attributesName);

        return attributesName;
    }

    /**
     * Compute attribute names.
     *
     * @param nodes
     *         list of parent node
     * @param attributesName
     *         array of attribute names.
     */
    private void computeAttributeNames(NodeList nodes, Array<String> attributesName) {
        nodes = getNodesWithAttributes("xsd:complexcontent", nodes);
        nodes = getNodesWithAttributes("xsd:extension", nodes);

        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeName().equalsIgnoreCase("xsd:attribute")) {
                NamedNodeMap attributesOfAttributeNode = nodes.item(i).getAttributes();
                for (int j = 0; j < attributesOfAttributeNode.getLength(); j++) {
                    if (attributesOfAttributeNode.item(j).getNodeName().equals("name")) {
                        String attributeValue = attributesOfAttributeNode.item(j).getNodeValue();
                        if (attributeValue.toLowerCase().startsWith(tag)) {
                            attributeValue = attributeValue.substring(tag.length(), attributeValue.length());
                        }
                        attributesName
                                .add(attributeValue.substring(0, 1).toLowerCase() + attributeValue.substring(1, attributeValue.length()));
                        break;
                    }
                }
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
    private NodeList getNodesWithAttributes(String nodeName, NodeList attributeNodes) {
        for (int i = 0; i < attributeNodes.getLength(); i++) {
            if (attributeNodes.item(i).getNodeName().equalsIgnoreCase(nodeName)) {
                attributeNodes = attributeNodes.item(i).getChildNodes();
                return attributeNodes;
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
    private void findComplexTypeNodes(NodeList nodes, Array<String> attributesName) {
        String complexTypeTag = "xsd:complexType";
        for (int i = 0; i < nodes.getLength(); i++) {
            Node child = nodes.item(i);
            if (child.getNodeName().equalsIgnoreCase(complexTypeTag)) {
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
    private void computeChildOfComplexTypeNode(NamedNodeMap attributes, Node parent, Array<String> attributesName) {
        for (int i = 0; i < attributes.getLength(); i++) {
            if (attributes.item(i).getNodeName().equals("name") &&
                (attributes.item(i).getNodeValue().equalsIgnoreCase(tag + "mediator") ||
                 attributes.item(i).getNodeValue().equalsIgnoreCase(tag + "endpoint"))) {
                computeAttributeNames(parent.getChildNodes(), attributesName);
                return;
            }
        }
    }
}
