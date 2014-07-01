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
package com.codenvy.ide.ext.wso2.client.commons;

import com.codenvy.ide.collections.Array;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTestWithMockito;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link XsdSchemaParser}.
 *
 * @author Valeriy Svydenko
 */
@GwtModule("com.codenvy.ide.ext.wso2.WSO2")
public class XsdSchemaParserTest extends GwtTestWithMockito {
    @Mock(answer = RETURNS_DEEP_STUBS)
    private WSO2Resources   resources;
    private XsdSchemaParser xsdSchemaParser;

    @Before
    public void setUp() throws Exception {
        xsdSchemaParser = new XsdSchemaParser(resources);
    }

    @Test
    public void logMediatorShouldHaveThreeAttributes() {
        when(resources.xmlSchemaDefinition().getText()).thenReturn("<xsd:schema xmlns:ecore=\"http://www.eclipse.org/emf/2002/Ecore\" " +
                                                                   "xmlns:esb=\"http:///org/wso2/developerstudio/eclipse/gmf/esb\" " +
                                                                   "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" ecore:nsPrefix=\"esb\"" +
                                                                   " ecore:package=\"org.wso2.developerstudio.eclipse.gmf.esb\" " +
                                                                   "targetNamespace=\"http:///org/wso2/developerstudio/eclipse/gmf/esb\">\n" +
                                                                   "<xsd:complexType name=\"LogMediator\">\n" +
                                                                   "   <xsd:complexContent>\n" +
                                                                   "    <xsd:extension base=\"esb:Mediator\">\n" +
                                                                   "       <xsd:attribute ecore:unsettable=\"false\" " +
                                                                   "name=\"logLevel\"></xsd:attribute>\n" +
                                                                   "       <xsd:attribute default=\"\" ecore:unsettable=\"false\" " +
                                                                   "name=\"logSeparator\"></xsd:attribute>\n" +
                                                                   "     </xsd:extension>\n" +
                                                                   "   </xsd:complexContent>\n" +
                                                                   "</xsd:complexType>\n" +
                                                                   "</xsd:schema>");

        Array<String> attributes = xsdSchemaParser.getAttributes("log");

        assertEquals(3, attributes.size());
        assertEquals("level", attributes.get(0));
        assertEquals("separator", attributes.get(1));
        assertEquals("description", attributes.get(2));
    }

    @Test
    public void logMediatorShouldHaveOnlyDescriptionAttribute() {
        when(resources.xmlSchemaDefinition().getText()).thenReturn("<xsd:schema xmlns:ecore=\"http://www.eclipse.org/emf/2002/Ecore\" " +
                                                                   "xmlns:esb=\"http:///org/wso2/developerstudio/eclipse/gmf/esb\" " +
                                                                   "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" ecore:nsPrefix=\"esb\"" +
                                                                   " ecore:package=\"org.wso2.developerstudio.eclipse.gmf.esb\" " +
                                                                   "targetNamespace=\"http:///org/wso2/developerstudio/eclipse/gmf/esb\">\n" +
                                                                   "<xsd:complexType name=\"LogMediator\">\n" +
                                                                   "   <xsd:complexContent>\n" +
                                                                   "        <xsd:sequence>\n" +
                                                                   "          <xsd:element ecore:resolveProxies=\"true\" minOccurs=\"0\" " +
                                                                   "name=\"inputConnector\" type=\"esb:LogMediatorInputConnector\"/>\n" +
                                                                   "          <xsd:element ecore:resolveProxies=\"true\" minOccurs=\"0\" " +
                                                                   "name=\"outputConnector\" type=\"esb:LogMediatorOutputConnector\"/>\n" +
                                                                   "          <xsd:element ecore:resolveProxies=\"true\" " +
                                                                   "maxOccurs=\"unbounded\" minOccurs=\"0\" name=\"properties\" " +
                                                                   "type=\"esb:LogProperty\"/>\n" +
                                                                   "        </xsd:sequence>\n" +
                                                                   "   </xsd:complexContent>\n" +
                                                                   "</xsd:complexType>\n" +
                                                                   "</xsd:schema>");

        Array<String> attributes = xsdSchemaParser.getAttributes("log");

        assertEquals(1, attributes.size());
        assertEquals("description", attributes.get(0));
    }

    @Test
    public void logMediatorShouldHaveOnlyDescriptionAttributeIfExtensionNodeDoesNotExist() {
        when(resources.xmlSchemaDefinition().getText()).thenReturn("<xsd:schema xmlns:ecore=\"http://www.eclipse.org/emf/2002/Ecore\" " +
                                                                   "xmlns:esb=\"http:///org/wso2/developerstudio/eclipse/gmf/esb\" " +
                                                                   "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" ecore:nsPrefix=\"esb\"" +
                                                                   " ecore:package=\"org.wso2.developerstudio.eclipse.gmf.esb\" " +
                                                                   "targetNamespace=\"http:///org/wso2/developerstudio/eclipse/gmf/esb\">\n" +
                                                                   "<xsd:complexType name=\"LogMediator\">\n" +
                                                                   "   <xsd:complexContent>\n" +
                                                                   "   </xsd:complexContent>\n" +
                                                                   "</xsd:complexType>\n" +
                                                                   "</xsd:schema>");

        Array<String> attributes = xsdSchemaParser.getAttributes("log");

        assertEquals(1, attributes.size());
        assertEquals("description", attributes.get(0));
    }

    @Test
    public void addressEndpointShouldHaveTwoAttributes() {
        when(resources.xmlSchemaDefinition().getText()).thenReturn("<xsd:schema xmlns:ecore=\"http://www.eclipse.org/emf/2002/Ecore\" " +
                                                                   "xmlns:esb=\"http:///org/wso2/developerstudio/eclipse/gmf/esb\" " +
                                                                   "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" ecore:nsPrefix=\"esb\"" +
                                                                   " ecore:package=\"org.wso2.developerstudio.eclipse.gmf.esb\" " +
                                                                   "targetNamespace=\"http:///org/wso2/developerstudio/eclipse/gmf/esb\">\n" +
                                                                   "<xsd:complexType name=\"AddressEndPoint\">\n" +
                                                                   "    <xsd:complexContent>\n" +
                                                                   "      <xsd:extension base=\"esb:AbstractEndPoint\">\n" +
                                                                   "        <xsd:sequence>\n" +
                                                                   "          <xsd:element ecore:resolveProxies=\"true\" minOccurs=\"0\" " +
                                                                   "name=\"inputConnector\" " +
                                                                   "type=\"esb:AddressEndPointInputConnector\"></xsd:element>\n" +
                                                                   "          <xsd:element ecore:resolveProxies=\"true\" minOccurs=\"0\" " +
                                                                   "name=\"outputConnector\" " +
                                                                   "type=\"esb:AddressEndPointOutputConnector\"></xsd:element>\n" +
                                                                   "        </xsd:sequence>\n" +
                                                                   "        <xsd:attribute default=\"http://www.example.org/service\" " +
                                                                   "ecore:name=\"URI\" ecore:unsettable=\"false\" name=\"URI\" " +
                                                                   "type=\"ecore:EString\"></xsd:attribute>\n" +
                                                                   "      </xsd:extension>\n" +
                                                                   "    </xsd:complexContent>\n" +
                                                                   "  </xsd:complexType>\n" +
                                                                   "</xsd:schema>");

        Array<String> attributes = xsdSchemaParser.getAttributes("address");

        assertEquals(2, attributes.size());
        assertEquals("URI", attributes.get(0));
        assertEquals("description", attributes.get(1));
    }
}
