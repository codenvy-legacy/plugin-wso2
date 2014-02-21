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
package com.codenvy.ide.ext.wso2.client.commons;

import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Class with utility methods for formatting XML.
 *
 * @author Valeriy Svydenko
 */
@Singleton
public class XMLParserUtil {

    private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private static final String tab             = "    ";

    @Inject
    public XMLParserUtil() {
    }

    /**
     * Parse string to XML elements.
     *
     * @param document
     * @param outputXml
     * @param level
     * @return
     */
    private String parse(Node document, StringBuilder outputXml, int level) {
        boolean lastNodeIsText = false;

        NodeList nodeList = document.getChildNodes();

        if (level > 0) {
            outputXml.append("\n");
            for (int k = 0; k < level; k++) {
                outputXml.append(tab);
            }
        }

        outputXml.append("<").append(document.getNodeName());

        NamedNodeMap attributes = document.getAttributes();
        if (attributes.getLength() > 0) {
            for (int i = 0; i < attributes.getLength(); i++) {
                Node item = attributes.item(i);
                outputXml.append(" ").append(item.getNodeName()).append("=\"").append(item.getNodeValue()).append("\"");
            }
        }

        outputXml.append(">");

        int nodeCount = nodeList.getLength();
        for (int i = 0; i < nodeCount; i++) {
            Node currentNode = nodeList.item(i);
            if (currentNode.getNodeName().equals("#text")) {
                lastNodeIsText = true;
                outputXml.append(currentNode.toString());
                outputXml.append("</").append(document.getNodeName()).append(">");
            } else if (currentNode.getChildNodes().getLength() > 0) {
                lastNodeIsText = false;
                parse(currentNode, outputXml, ++level);
                level--;
            } else {
                lastNodeIsText = false;
                outputXml.append("\n");
                for (int k = 0; k <= level; k++) {
                    outputXml.append(tab);
                }
                outputXml.append(currentNode);
            }
        }
        if (!lastNodeIsText) {
            outputXml.append("\n");
            for (int k = 0; k < level; k++) {
                outputXml.append(tab);
            }
            outputXml.append("</").append(document.getNodeName()).append(">");
        }

        return outputXml.toString();
    }

    /**
     * Format XML to more readable.
     *
     * @param XMLNode
     * @return
     */
    public String formatXMLString(Node XMLNode) {
        StringBuilder outputXml = new StringBuilder();
        outputXml.append(XML_DECLARATION).append("\n");
        return parse(XMLNode, outputXml, 0).toString();
    }
}
