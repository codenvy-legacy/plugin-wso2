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
package com.codenvy.ide.client.elements.mediators.enrich;

import com.codenvy.ide.client.elements.AbstractEntityTest;
import com.codenvy.ide.client.elements.NameSpace;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;
import com.google.inject.Provider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.enrich.Source.InlineType;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.InlineType.REGISTRY_KEY;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.InlineType.SOURCE_XML;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SOURCE_CLONE;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SOURCE_INLINE_REGISTER_KEY;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SOURCE_INLINE_TYPE;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SOURCE_NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SOURCE_PROPERTY;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SOURCE_TYPE;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SOURCE_XPATH;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SourceType;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SourceType.BODY;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SourceType.CUSTOM;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SourceType.ENVELOPE;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SourceType.INLINE;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SourceType.PROPERTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SourceTest extends AbstractEntityTest<Source> {

    private static final String PATH_TO_EXAMPLES = "mediators/enrich/source/serialize/";

    private static final boolean         CLONE_DEFAULT_VALUE        = false;
    private static final SourceType      TYPE_DEFAULT_VALUE         = CUSTOM;
    private static final InlineType      INLINE_TYPE_DEFAULT_VALUE  = SOURCE_XML;
    private static final String          REGISTRY_KEY_DEFAULT_VALUE = "/default/sequence";
    private static final String          XML_DEFAULT_VALUE          = "<inline/>";
    private static final String          XML_VALUE                  = "<inline xmlns=\"\"></inline>";
    private static final String          XPATH_DEFAULT_VALUE        = "/default/xpath";
    private static final String          PROPERTY_DEFAULT_VALUE     = "source_property";
    private static final List<NameSpace> NAMESPACES_DEFAULT         = new ArrayList<>();

    @Mock
    private Provider<NameSpace> nameSpaceProvider;
    @Mock
    private NameSpace           nameSpace;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private Node                node;

    @Before
    public void setUp() throws Exception {
        when(nameSpaceProvider.get()).thenReturn(nameSpace);

        entity = new Source(nameSpaceProvider);

        assertDefaultConfiguration();
    }

    private void assertDefaultConfiguration() {
        assertConfiguration(CLONE_DEFAULT_VALUE,
                            TYPE_DEFAULT_VALUE,
                            INLINE_TYPE_DEFAULT_VALUE,
                            REGISTRY_KEY_DEFAULT_VALUE,
                            XML_DEFAULT_VALUE,
                            XPATH_DEFAULT_VALUE,
                            PROPERTY_DEFAULT_VALUE,
                            NAMESPACES_DEFAULT);
    }

    private void assertConfiguration(boolean clone,
                                     SourceType type,
                                     InlineType inlineType,
                                     String registerKey,
                                     String inlineXml,
                                     String xPath,
                                     String property,
                                     List<NameSpace> nameSpaces) {

        assertEquals(clone, entity.getProperty(SOURCE_CLONE));
        assertEquals(type, entity.getProperty(SOURCE_TYPE));
        assertEquals(inlineType, entity.getProperty(SOURCE_INLINE_TYPE));
        assertEquals(registerKey, entity.getProperty(SOURCE_INLINE_REGISTER_KEY));
        assertEquals(inlineXml, entity.getProperty(Source.SOURCE_XML));
        assertEquals(xPath, entity.getProperty(SOURCE_XPATH));
        assertEquals(property, entity.getProperty(SOURCE_PROPERTY));
        assertEquals(nameSpaces, entity.getProperty(SOURCE_NAMESPACES));
    }

    @Test
    public void serializationShouldBeDoneWithDefaultParameters() throws Exception {
        assertContentAndValue(PATH_TO_EXAMPLES + "DefaultParameter", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithDefaultParameters() throws Exception {
        Document xml = XMLParser.parse(getContent(PATH_TO_EXAMPLES + "DefaultParameter"));
        entity.applyProperty(xml.getFirstChild());

        assertDefaultConfiguration();
    }

    @Test
    public void serializationShouldBeDoneWithCloneSourceTrueParameter() throws Exception {
        entity.putProperty(SOURCE_CLONE, true);

        assertContentAndValue(PATH_TO_EXAMPLES + "CloneTrue", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithCloneResourceTrue() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(PATH_TO_EXAMPLES + "CloneTrue"));
        entity.applyProperty(xml.getFirstChild());

        assertConfiguration(true,
                            TYPE_DEFAULT_VALUE,
                            INLINE_TYPE_DEFAULT_VALUE,
                            REGISTRY_KEY_DEFAULT_VALUE,
                            XML_DEFAULT_VALUE,
                            XPATH_DEFAULT_VALUE,
                            PROPERTY_DEFAULT_VALUE,
                            NAMESPACES_DEFAULT);
    }

    @Test
    public void serializationShouldBeDoneWithSourceTypeEnvelope() throws Exception {
        entity.putProperty(SOURCE_TYPE, ENVELOPE);

        assertContentAndValue(PATH_TO_EXAMPLES + "SourceTypeEnvelope", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithSourceTypeEnvelope() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(PATH_TO_EXAMPLES + "SourceTypeEnvelope"));
        entity.applyProperty(xml.getFirstChild());

        assertConfiguration(false,
                            ENVELOPE,
                            INLINE_TYPE_DEFAULT_VALUE,
                            REGISTRY_KEY_DEFAULT_VALUE,
                            XML_DEFAULT_VALUE,
                            XPATH_DEFAULT_VALUE,
                            PROPERTY_DEFAULT_VALUE,
                            NAMESPACES_DEFAULT);
    }

    @Test
    public void serializationShouldBeDoneWithSourceTypeBody() throws Exception {
        entity.putProperty(SOURCE_TYPE, BODY);

        assertContentAndValue(PATH_TO_EXAMPLES + "SourceTypeBody", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithSourceTypeBody() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(PATH_TO_EXAMPLES + "SourceTypeBody"));
        entity.applyProperty(xml.getFirstChild());

        assertConfiguration(false,
                            BODY,
                            INLINE_TYPE_DEFAULT_VALUE,
                            REGISTRY_KEY_DEFAULT_VALUE,
                            XML_DEFAULT_VALUE,
                            XPATH_DEFAULT_VALUE,
                            PROPERTY_DEFAULT_VALUE,
                            NAMESPACES_DEFAULT);
    }

    @Test
    public void serializationShouldBeDoneWithSourceTypeProperty() throws Exception {
        entity.putProperty(SOURCE_TYPE, PROPERTY);

        assertContentAndValue(PATH_TO_EXAMPLES + "SourceTypeProperty", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithSourceTypeProperty() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(PATH_TO_EXAMPLES + "SourceTypeProperty"));
        entity.applyProperty(xml.getFirstChild());

        assertConfiguration(false,
                            PROPERTY,
                            INLINE_TYPE_DEFAULT_VALUE,
                            REGISTRY_KEY_DEFAULT_VALUE,
                            XML_DEFAULT_VALUE,
                            XPATH_DEFAULT_VALUE,
                            PROPERTY_DEFAULT_VALUE,
                            NAMESPACES_DEFAULT);
    }

    @Test
    public void serializationShouldBeDoneWithSourceTypeInlineAttribute() throws Exception {
        entity.putProperty(SOURCE_TYPE, INLINE);

        assertContentAndValue(PATH_TO_EXAMPLES + "SourceTypeInline", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithSourceTypeInlineAttribute() throws Exception {
        assertDefaultConfiguration();

        when(node.getChildNodes().item(0).toString()).thenReturn(XML_VALUE);
        entity.applyProperty(node);

        assertConfiguration(false,
                            CUSTOM,
                            INLINE_TYPE_DEFAULT_VALUE,
                            REGISTRY_KEY_DEFAULT_VALUE,
                            XML_DEFAULT_VALUE,
                            XPATH_DEFAULT_VALUE,
                            PROPERTY_DEFAULT_VALUE,
                            NAMESPACES_DEFAULT);
    }

    @Test
    public void deserializationShouldBeDoneWithPropertyAttribute() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(PATH_TO_EXAMPLES + "SourceTypeProperty"));
        entity.applyProperty(xml.getFirstChild());

        assertConfiguration(false,
                            PROPERTY,
                            INLINE_TYPE_DEFAULT_VALUE,
                            REGISTRY_KEY_DEFAULT_VALUE,
                            XML_DEFAULT_VALUE,
                            XPATH_DEFAULT_VALUE,
                            PROPERTY_DEFAULT_VALUE,
                            NAMESPACES_DEFAULT);
    }

    @Test
    public void deserializationShouldBeDoneWithNameSpacesAttribute() throws Exception {
        List<NameSpace> nameSpaces = new ArrayList<>();

        entity.putProperty(SOURCE_NAMESPACES, nameSpaces);

        assertTrue(nameSpaces.isEmpty());

        entity.applyAttribute("xmlns:a", "b");

        assertEquals(1, nameSpaces.size());
        assertEquals(nameSpace, nameSpaces.get(0));
    }

    @Test
    public void emptyElementShouldBeReturnedWhenSourceCloneIsNull() throws Exception {
        entity.putProperty(SOURCE_CLONE, null);

        assertContentAndValue(PATH_TO_EXAMPLES + "SourceCloneIsNull", entity.serialize());
    }

    @Test
    public void emptyElementShouldBeReturnedWhenSourceTypeIsNull() throws Exception {
        entity.putProperty(SOURCE_TYPE, null);

        assertContentAndValue(PATH_TO_EXAMPLES + "SourceTypeIsNull", entity.serialize());
    }

    @Test
    public void elementWithoutXpathBeReturnedWhenSourceXmlIsNull() throws Exception {
        entity.putProperty(SOURCE_TYPE, INLINE);
        entity.putProperty(SOURCE_INLINE_TYPE, SOURCE_XML);
        entity.putProperty(Source.SOURCE_XML, null);

        assertContentAndValue(PATH_TO_EXAMPLES + "SourceXmlIsNull", entity.serialize());
    }

    @Test
    public void serializationShouldBeDoneWithInlineRegistryKey() throws Exception {
        entity.putProperty(SOURCE_TYPE, INLINE);
        entity.putProperty(SOURCE_INLINE_TYPE, REGISTRY_KEY);

        assertContentAndValue(PATH_TO_EXAMPLES + "InlineTypeRegistryKey", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithRegistryKeyAttribute() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(PATH_TO_EXAMPLES + "InlineTypeRegistryKey"));
        entity.applyProperty(xml.getFirstChild());

        assertConfiguration(false,
                            INLINE,
                            REGISTRY_KEY,
                            REGISTRY_KEY_DEFAULT_VALUE,
                            XML_DEFAULT_VALUE,
                            XPATH_DEFAULT_VALUE,
                            PROPERTY_DEFAULT_VALUE,
                            NAMESPACES_DEFAULT);
    }

    @Test
    public void methodShouldBeReturnedWhenXmlIsNull() throws Exception {
        entity.putProperty(Source.SOURCE_XML, null);

        entity.applyProperty(node);

        verify(node, never()).hasChildNodes();
    }

    @Test
    public void deserializeShouldBeDoneWithInlineXml() throws Exception {
        when(node.getChildNodes().item(0).toString()).thenReturn(XML_VALUE);
        when(node.hasChildNodes()).thenReturn(true);

        entity.putProperty(Source.SOURCE_XML, XML_VALUE);

        entity.applyProperty(node);

        assertEquals("<inline></inline>", entity.getProperty(Source.SOURCE_XML));
    }

    @Test
    public void deserializationShouldBeDoneWithIncorrectAttirbute() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(PATH_TO_EXAMPLES + "IncorrectAttribute"));
        entity.applyProperty(xml.getFirstChild());

        assertDefaultConfiguration();
    }

    @Test
    public void customSourceTypeShouldBeReturned() throws Exception {
        assertEquals(CUSTOM, SourceType.getItemByValue("custom"));
    }

    @Test
    public void sourceXmlInlineTypeShouldBeReturned() throws Exception {
        assertEquals(SOURCE_XML, InlineType.getItemByValue("SourceXML"));
    }

    @Test
    public void registryKeyInlineTypeShouldBeReturned() throws Exception {
        assertEquals(REGISTRY_KEY, InlineType.getItemByValue("RegistryKey"));
    }

    @Test
    public void valueOfInlineTypeShouldBeReturned() throws Exception {
        assertEquals("RegistryKey", REGISTRY_KEY.getValue());
    }
}