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
import com.google.gwt.xml.client.XMLParser;
import com.google.inject.Provider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.enrich.Target.TARGET_ACTION;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TARGET_NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TARGET_PROPERTY;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TARGET_TYPE;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TARGET_XPATH;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TargetAction;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TargetAction.CHILD;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TargetAction.REPLACE;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TargetAction.SIBLING;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TargetType;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TargetType.BODY;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TargetType.CUSTOM;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TargetType.ENVELOPE;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TargetType.PROPERTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class TargetTest extends AbstractEntityTest<Target> {

    private static final String PATH_TO_EXAMPLES = "mediators/enrich/targetelement/serialize/";

    private static final TargetAction    ACTION_DEFAULT     = REPLACE;
    private static final TargetType      TYPE_DEFAULT       = CUSTOM;
    private static final String          XPATH_DEFAULT      = "/default/xpath";
    private static final String          PROPERTY_DEFAULT   = "target_property";
    private static final List<NameSpace> NAMESPACES_DEFAULT = new ArrayList<>();

    @Mock
    private Provider<NameSpace> nameSpaceProvider;
    @Mock
    private NameSpace           nameSpace;

    @Before
    public void setUp() throws Exception {
        when(nameSpaceProvider.get()).thenReturn(nameSpace);

        entity = new Target(nameSpaceProvider);

        assertDefaultConfiguration();
    }

    private void assertDefaultConfiguration() {
        assertConfiguration(ACTION_DEFAULT, TYPE_DEFAULT, XPATH_DEFAULT, PROPERTY_DEFAULT, NAMESPACES_DEFAULT);
    }

    private void assertConfiguration(TargetAction action, TargetType type, String xpath, String property, List<NameSpace> nameSpaces) {
        assertEquals(action, entity.getProperty(TARGET_ACTION));
        assertEquals(type, entity.getProperty(TARGET_TYPE));
        assertEquals(xpath, entity.getProperty(TARGET_XPATH));
        assertEquals(property, entity.getProperty(TARGET_PROPERTY));
        assertEquals(nameSpaces, entity.getProperty(TARGET_NAMESPACES));
    }

    @Test
    public void serializationShouldBeDoneWithDefaultParameter() throws Exception {
        assertContentAndValue(PATH_TO_EXAMPLES + "TargetDefault", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithDefaultParameter() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(PATH_TO_EXAMPLES + "TargetDefault"));
        entity.deserialize(xml.getFirstChild());

        assertDefaultConfiguration();
    }

    @Test
    public void serializationShouldBeDoneWhenTargetTypeBody() throws Exception {
        entity.putProperty(TARGET_TYPE, BODY);

        assertContentAndValue(PATH_TO_EXAMPLES + "TargetTypeBody", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenTargetTypeBody() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(PATH_TO_EXAMPLES + "TargetTypeBody"));
        entity.deserialize(xml.getFirstChild());

        assertConfiguration(ACTION_DEFAULT, BODY, XPATH_DEFAULT, PROPERTY_DEFAULT, NAMESPACES_DEFAULT);
    }

    @Test
    public void serializationShouldBeDoneWhenTargetTypeEnvelope() throws Exception {
        entity.putProperty(TARGET_TYPE, ENVELOPE);

        assertContentAndValue(PATH_TO_EXAMPLES + "TargetTypeEnvelope", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenTargetTypeEnvelope() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(PATH_TO_EXAMPLES + "TargetTypeEnvelope"));
        entity.deserialize(xml.getFirstChild());

        assertConfiguration(ACTION_DEFAULT, ENVELOPE, XPATH_DEFAULT, PROPERTY_DEFAULT, NAMESPACES_DEFAULT);
    }

    @Test
    public void serializationShouldBeDoneWhenTargetTypeProperty() throws Exception {
        entity.putProperty(TARGET_TYPE, PROPERTY);

        assertContentAndValue(PATH_TO_EXAMPLES + "TargetTypeProperty", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenTargetTypeProperty() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(PATH_TO_EXAMPLES + "TargetTypeProperty"));
        entity.deserialize(xml.getFirstChild());

        assertConfiguration(ACTION_DEFAULT, PROPERTY, XPATH_DEFAULT, PROPERTY_DEFAULT, NAMESPACES_DEFAULT);
    }

    @Test
    public void serializationShouldBeDoneWithNameSpaces() throws Exception {
        when(nameSpace.toString()).thenReturn("xmlns:prefix=\"uri\"");

        entity.putProperty(TARGET_NAMESPACES, Arrays.asList(nameSpace));

        assertContentAndValue(PATH_TO_EXAMPLES + "TargetNameSpace", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithNameSpaces() throws Exception {
        List<NameSpace> nameSpaces = new ArrayList<>();

        entity.putProperty(TARGET_NAMESPACES, nameSpaces);

        assertTrue(nameSpaces.isEmpty());

        entity.applyAttribute("xmlns:a", "b");

        assertEquals(1, nameSpaces.size());
        assertEquals(nameSpace, nameSpaces.get(0));
    }

    @Test
    public void serializationShouldBeDoneWithTargetActionChild() throws Exception {
        entity.putProperty(TARGET_ACTION, CHILD);

        assertContentAndValue(PATH_TO_EXAMPLES + "TargetActionChild", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithTargetActionChild() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(PATH_TO_EXAMPLES + "TargetActionChild"));
        entity.deserialize(xml.getFirstChild());

        assertConfiguration(CHILD, TYPE_DEFAULT, XPATH_DEFAULT, PROPERTY_DEFAULT, NAMESPACES_DEFAULT);
    }

    @Test
    public void serializationShouldBeDoneWithTargetActionSibling() throws Exception {
        entity.putProperty(TARGET_ACTION, SIBLING);

        assertContentAndValue(PATH_TO_EXAMPLES + "TargetActionSibling", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithTargetActionSibling() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(PATH_TO_EXAMPLES + "TargetActionSibling"));
        entity.deserialize(xml.getFirstChild());

        assertConfiguration(SIBLING, TYPE_DEFAULT, XPATH_DEFAULT, PROPERTY_DEFAULT, NAMESPACES_DEFAULT);
    }

    @Test
    public void emptyElementShouldBeReturnedWhenTargetTypeIsNull() throws Exception {
        entity.putProperty(TARGET_TYPE, null);

        assertEquals("<target  />\n", entity.serialize());
    }

    @Test
    public void emptyElementShouldBeReturnedWhenTargetActionIsNull() throws Exception {
        entity.putProperty(TARGET_ACTION, null);

        assertEquals("<target  />\n", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithIncorrectAttirbute() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(PATH_TO_EXAMPLES + "IncorrectAttribute"));
        entity.deserialize(xml.getFirstChild());

        assertDefaultConfiguration();
    }

    @Test
    public void customTargetTypeShouldBeReturned() throws Exception {
        assertEquals(CUSTOM, TargetType.getItemByValue("custom"));
    }

    @Test
    public void propertyTargetTypeShouldBeReturned() throws Exception {
        assertEquals(PROPERTY, TargetType.getItemByValue("property"));
    }

    @Test
    public void replaceTargetActionShouldBeReturned() throws Exception {
        assertEquals(REPLACE, TargetAction.getItemByValue("replace"));
    }
}