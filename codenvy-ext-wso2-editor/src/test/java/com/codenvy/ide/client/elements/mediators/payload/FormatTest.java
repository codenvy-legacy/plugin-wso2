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
package com.codenvy.ide.client.elements.mediators.payload;

import com.codenvy.ide.client.elements.AbstractEntityTest;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.codenvy.ide.client.elements.mediators.payload.Format.FORMAT_INLINE;
import static com.codenvy.ide.client.elements.mediators.payload.Format.FORMAT_KEY;
import static com.codenvy.ide.client.elements.mediators.payload.Format.FORMAT_TYPE;
import static com.codenvy.ide.client.elements.mediators.payload.Format.FormatType;
import static com.codenvy.ide.client.elements.mediators.payload.Format.FormatType.INLINE;
import static com.codenvy.ide.client.elements.mediators.payload.Format.FormatType.REGISTRY;
import static org.junit.Assert.assertEquals;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;

/**
 * @author Dmitry Shnurenko
 */
public class FormatTest extends AbstractEntityTest<Format> {

    private static final String PATH_TO_RESOURCES = "mediators/payload/format/serialize/";

    private static final FormatType TYPE                 = INLINE;
    private static final String     KEY                  = "default/key";
    private static final String     INLINE_VALUE         = "<inline/>";
    private static final String     INLINE_STRING_INPUT  = "<inline xmlns=\"\"></inline>";
    private static final String     INLINE_STRING_OUTPUT = "<inline></inline>";

    @Mock(answer = RETURNS_DEEP_STUBS)
    private Node node;

    @Before
    public void setUp() throws Exception {
        entity = new Format();
    }

    private void assertConfiguration(FormatType type, String key, String inline) {
        assertEquals(type, entity.getProperty(FORMAT_TYPE));
        assertEquals(key, entity.getProperty(FORMAT_KEY));
        assertEquals(inline, entity.getProperty(FORMAT_INLINE));
    }

    private void assertDefaultConfiguration() {
        assertConfiguration(TYPE, KEY, INLINE_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWithMediaTypeXml() throws Exception {
        assertContentAndValue(PATH_TO_RESOURCES + "DefaultParameters", entity.serialize(false));
    }

    @Test
    public void serializationShouldBeDoneWithMediaTypeJson() throws Exception {
        assertContentAndValue(PATH_TO_RESOURCES + "MediaTypeJson", entity.serialize(true));
    }

    @Test
    public void deserializationShouldBeDoneWithKeyParameter() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(PATH_TO_RESOURCES + "KeyParameter"));
        entity.applyAttributes(xml.getFirstChild());

        assertDefaultConfiguration();
    }

    @Test
    public void deserializationShouldBeDoneWithoutAttributes() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(PATH_TO_RESOURCES + "WithoutAttributes"));
        entity.applyAttributes(xml.getFirstChild());

        assertDefaultConfiguration();
    }

    @Test
    public void deserializationShouldBeDoneWithInlineAttribute() throws Exception {
        when(node.hasChildNodes()).thenReturn(true);
        when(node.getChildNodes().item(0).toString()).thenReturn(INLINE_STRING_INPUT);

        entity.applyProperty(node);

        assertEquals(INLINE_STRING_OUTPUT, entity.getProperty(FORMAT_INLINE));
    }

    @Test
    public void methodShouldBeReturnedWhenNodeHasNotChildren() throws Exception {
        when(node.hasChildNodes()).thenReturn(false);

        entity.applyProperty(node);

        assertEquals(INLINE_VALUE, entity.getProperty(FORMAT_INLINE));
    }

    @Test
    public void serializationShouldBeDoneWithKey() throws Exception {
        entity.putProperty(FORMAT_TYPE, REGISTRY);

        assertContentAndValue(PATH_TO_RESOURCES + "KeyParameter", entity.serialize(false));
    }

    @Test
    public void emptyStringShouldBeReturnedWhenInlineIsNull() throws Exception {
        entity.putProperty(FORMAT_INLINE, null);

        assertEquals("", entity.serialize(false));
    }

    @Test
    public void inlineFormatTypeShouldBeReturned() throws Exception {
        assertEquals(INLINE, FormatType.getItemByValue("Inline"));
    }

    @Test
    public void registryFormatTypeShouldBeReturned() throws Exception {
        assertEquals(REGISTRY, FormatType.getItemByValue("Registry"));
    }

    @Test
    public void valueOfFormatTypeShouldBeReturned() throws Exception {
        assertEquals("Registry", REGISTRY.getValue());
    }

}