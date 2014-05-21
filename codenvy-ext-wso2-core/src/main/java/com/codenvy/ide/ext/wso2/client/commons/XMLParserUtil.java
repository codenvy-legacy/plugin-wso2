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

import javax.validation.constraints.NotNull;

/**
 * The utility class that provides an ability to format XML node to readable string XML format.
 *
 * @author Valeriy Svydenko
 * @author Andrey Plotnikov
 */
@Singleton
public class XMLParserUtil {

    private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private static final String TAB             = "    ";

    @Inject
    public XMLParserUtil() {
        // do nothing for now
    }

    /**
     * Parse XML node to text format.
     *
     * @param node
     *         node that need to be formatted
     * @param depth
     *         depth of node in xml
     * @return node in text format
     */
    @NotNull
    private String parse(@NotNull Node node, int depth) {
        StringBuilder outputXml = new StringBuilder();

        if (depth > 0) {
            outputXml.append('\n').append(createTab(depth));
        }

        outputXml.append('<').append(node.getNodeName()).append(transformAttributes(node.getAttributes())).append('>');

        outputXml.append(transformChildrenNodes(node, depth));

        if (!hasTextChildNode(node)) {
            outputXml.append('\n').append(createTab(depth));
        }

        outputXml.append("</").append(node.getNodeName()).append('>');

        return outputXml.toString();
    }

    /**
     * Create tab for a tag with given depth.
     *
     * @param depth
     *         how many tabs need to be added
     * @return tab for a tag
     */
    @NotNull
    private String createTab(int depth) {
        StringBuilder tabs = new StringBuilder();

        for (int i = 0; i < depth; i++) {
            tabs.append(TAB);
        }

        return tabs.toString();
    }

    /**
     * Transform attributes of xml node to string format.
     *
     * @param attributes
     *         attributes that need to be formatted
     * @return node's attributes in text format
     */
    @NotNull
    private String transformAttributes(@NotNull NamedNodeMap attributes) {
        StringBuilder text = new StringBuilder();

        for (int i = 0; i < attributes.getLength(); i++) {
            Node item = attributes.item(i);
            text.append(' ').append(item.getNodeName()).append("=\"").append(item.getNodeValue()).append('"');
        }

        return text.toString();
    }

    /**
     * Transform children nodes of xml node to text format.
     *
     * @param node
     *         node that contains children which need to be formatted
     * @param depth
     *         depth of parent node in xml
     * @return children nodes in text format
     */
    @NotNull
    private String transformChildrenNodes(@NotNull Node node, int depth) {
        StringBuilder text = new StringBuilder();
        NodeList nodeList = node.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);

            if (isTextNode(currentNode)) {
                text.append(currentNode.toString());
            } else if (currentNode.hasChildNodes()) {
                text.append(parse(currentNode, depth + 1));
            } else {
                text.append('\n').append(createTab(depth + 1)).append(currentNode);
            }
        }

        return text.toString();
    }

    /**
     * Returns whether a node is the text node.
     *
     * @param node
     *         node that need to be checked
     * @return <code>true</code> if a node is the text node, and <code>false</code> otherwise
     */
    private boolean isTextNode(@NotNull Node node) {
        return node.getNodeName().equals("#text");
    }

    /**
     * Returns whether a node has a text child node.
     *
     * @param node
     *         node that need to be checked
     * @return <code>true</code> if a node has a text child node, and <code>false</code> otherwise
     */
    private boolean hasTextChildNode(@NotNull Node node) {
        if (node.hasChildNodes()) {
            Node child = node.getChildNodes().item(0);
            return isTextNode(child);
        }

        return false;
    }

    /**
     * Format XML to the more readable state.
     *
     * @param node
     *         node that need to be formatted
     * @return node in xml format with readable state
     */
    @NotNull
    public String formatXML(@NotNull Node node) {
        return XML_DECLARATION + '\n' + parse(node, 0);
    }
}