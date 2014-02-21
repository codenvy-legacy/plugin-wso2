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

import com.google.gwt.xml.client.XMLParser;
import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTestWithMockito;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Here we're testing {@link XMLParserUtil}.
 *
 * @author Valeriy Svydenko
 */
@GwtModule("com.codenvy.ide.ext.wso2.WSO2")
public class XMLParserUtilTest extends GwtTestWithMockito {
    private XMLParserUtil xmlParserUtil;
    String inputData;
    String expectedResult;

    @Before
    public void setUp() throws Exception {
        xmlParserUtil = new XMLParserUtil();
    }

    private void prepareOnlyOneNode() {
        inputData = "<sequence  name1=\"value1\" name2=\"value2\"></sequence>";

        expectedResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                         "<sequence name1=\"value1\" name2=\"value2\">\n</sequence>";
    }

    @Test
    public void testFormatXmlIfDocumentHasOnlyOneNode() {
        prepareOnlyOneNode();
        assertEquals(expectedResult, xmlParserUtil.formatXMLString(XMLParser.parse(inputData).getDocumentElement()));
    }

    private void prepareInheritedNodes() {
        inputData = "<sequence name1=\"value1\" name2=\"value2\"><endpoint name1=\"value1\" " +
                    "name2=\"value2\"/></sequence>";

        expectedResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                         "<sequence name1=\"value1\" name2=\"value2\">\n" +
                         "    <endpoint name1=\"value1\" name2=\"value2\"></endpoint>\n" +
                         "</sequence>";
    }

    @Test
    public void testFormatXmlIfDocumentHasInheritedNodes() {
        prepareInheritedNodes();
        assertEquals(expectedResult, xmlParserUtil.formatXMLString(XMLParser.parse(inputData).getDocumentElement()));
    }

    private void prepareInheritedNodes2() {
        inputData = "<sequence name1=\"value1\" name2=\"value2\"><log level=\"SIMPLE\" category=\"INFO\" " +
                    "separator=\"\" description=\"Logger\"/><property name=\"property_name\"><super name=\"name1\"/><A laa=\"laa\"><B " +
                    "up=\"iop\">df</B><C>ddd</C></A></property></sequence>";

        expectedResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                         "<sequence name1=\"value1\" name2=\"value2\">\n" +
                         "    <log level=\"SIMPLE\" category=\"INFO\" separator=\"\" description=\"Logger\"></log>\n" +
                         "    <property name=\"property_name\">\n" +
                         "        <super name=\"name1\"></super>\n" +
                         "        <A laa=\"laa\">\n" +
                         "            <B up=\"iop\">df</B>\n" +
                         "            <C>ddd</C>\n" +
                         "        </A>\n" +
                         "    </property>\n" +
                         "</sequence>";
    }

    @Test
    public void testFormatXmlIfDocumentHasInheritedNodes2() {
        prepareInheritedNodes2();
        assertEquals(expectedResult, xmlParserUtil.formatXMLString(XMLParser.parse(inputData).getDocumentElement()));
    }

    private void prepareIfTwoNodes() {
        inputData = "<root><sequence name1=\"value1\" name=\"value\" name2=\"NewESB\"></sequence><sequence name12=\"value1\" name22=\"value\" " +
                    "name32=\"NewESB\"></sequence></root>";

        expectedResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                         "<root>\n" +
                         "    <sequence name1=\"value1\" name=\"value\" name2=\"NewESB\"></sequence>\n" +
                         "    <sequence name12=\"value1\" name22=\"value\" name32=\"NewESB\"></sequence>\n" +
                         "</root>";
    }

    @Test
    public void testIfXmlHaveTwoNodes() {
        prepareIfTwoNodes();
        assertEquals(expectedResult, xmlParserUtil.formatXMLString(XMLParser.parse(inputData).getDocumentElement()));
    }
}
