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

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTestWithMockito;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Here we're testing {@link XMLParserUtil}.
 *
 * @author Valeriy Svydenko
 */
@GwtModule("com.codenvy.ide.ext.wso2.WSO2")
public class XMLParserUtilTest extends GwtTestWithMockito {
    private Document document;

    public XMLParserUtilTest() {
        document = XMLParser.createDocument();
        Element self = document.createElement("sequence");
        self.setAttribute("xmlns", "http://ws.apache.org/ns/synapse");
        self.setAttribute("name", "value");
        document.appendChild(self);
    }

    private static final String FIRST_INPUT =
            "<sequence xmlns=\"http://ws.apache.org/ns/synapse\" name=\"NewESB\"><log level=\"SIMPLE\" category=\"INFO\" separator=\"\" " +
            "description=\"Logger\"/></sequence>";


    private static final String SECOND_INPUT    =
            "<sequence param=\"value\" name=\"NewESB\"><B up=\"iop\">df</B></sequence>";
    private static final String FIRST_EXPECTED  = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                                  "<sequence xmlns=\"http://ws.apache.org/ns/synapse\" name=\"NewESB\">\n" +
                                                  "    <log level=\"SIMPLE\" category=\"INFO\" separator=\"\" description=\"Logger\"/>\n" +
                                                  "</sequence>";
    private static final String SECOND_EXPECTED = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                                  "<sequence param=\"value\" name=\"NewESB\">\n" +
                                                  "    <B up=\"iop\">df</B>\n" +
                                                  "</sequence>";


    @Test
    public void testFormatXmlChecker() {
        String seq = document.toString();
        assertEquals(SECOND_EXPECTED, XMLParserUtil.formatXMLString(XMLParser.parse(SECOND_INPUT).getDocumentElement()));
    }
}
