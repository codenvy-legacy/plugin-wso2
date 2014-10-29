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
package com.codenvy.ide.client.elements.mediators.log;

import com.codenvy.ide.client.elements.AbstractEntityTest;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.mediators.ValueType;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;
import com.google.inject.Provider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.ValueType.LITERAL;
import static com.codenvy.ide.client.elements.mediators.log.Property.EXPRESSION;
import static com.codenvy.ide.client.elements.mediators.log.Property.NAME;
import static com.codenvy.ide.client.elements.mediators.log.Property.NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.log.Property.TYPE;
import static com.codenvy.ide.client.elements.mediators.log.Property.VALUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Dmitry Shnurenko
 */
public class PropertyTest extends AbstractEntityTest<Property> {

    private static final String PATH_TO_RESOURCES = "mediators/log/property/serialize/";

    private static final ValueType       TYPE_DEFAULT_VALUE        = LITERAL;
    private static final String          NAME_DEFAULT_VALUE        = "property_name";
    private static final String          VALUE_DEFAULT_VALUE       = "property_value";
    private static final String          EXPRESSION_DEFAULT_VALUE  = "/default/expression";
    private static final List<NameSpace> NAME_SPACES_DEFAULT_VALUE = new ArrayList<>();

    @Mock
    private Provider<Property>  propertyProvider;
    @Mock
    private Provider<NameSpace> nameSpaceProvider;
    @Mock
    private Property            property;
    @Mock
    private NameSpace           nameSpace;

    @Before
    public void setUp() throws Exception {
        when(propertyProvider.get()).thenReturn(property);
        when(nameSpaceProvider.get()).thenReturn(nameSpace);

        entity = new Property(propertyProvider, nameSpaceProvider);
    }

    private void assertConfiguration(ValueType type, String name, String value, String expression, List<NameSpace> nameSpaces) {
        assertEquals(type, entity.getProperty(TYPE));
        assertEquals(name, entity.getProperty(NAME));
        assertEquals(value, entity.getProperty(VALUE));
        assertEquals(expression, entity.getProperty(EXPRESSION));
        assertEquals(nameSpaces, entity.getProperty(NAMESPACES));
    }

    private void assertDefaultConfiguration() {
        assertConfiguration(TYPE_DEFAULT_VALUE,
                            NAME_DEFAULT_VALUE,
                            VALUE_DEFAULT_VALUE,
                            EXPRESSION_DEFAULT_VALUE,
                            NAME_SPACES_DEFAULT_VALUE);
    }

    @Test
    public void allPropertiesShouldBeCopied() throws Exception {
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ValueType> typeArgumentCaptor = ArgumentCaptor.forClass(ValueType.class);
        ArgumentCaptor<List> spaceArgumentCaptor = ArgumentCaptor.forClass(List.class);

        entity.putProperty(NAME, NAME_DEFAULT_VALUE);
        entity.putProperty(VALUE, VALUE_DEFAULT_VALUE);
        entity.putProperty(EXPRESSION, EXPRESSION_DEFAULT_VALUE);
        entity.putProperty(TYPE, TYPE_DEFAULT_VALUE);
        entity.putProperty(NAMESPACES, NAME_SPACES_DEFAULT_VALUE);

        Property newProperty = entity.copy();

        verify(property).putProperty(eq(NAME), stringArgumentCaptor.capture());
        verify(property).putProperty(eq(VALUE), stringArgumentCaptor.capture());
        verify(property).putProperty(eq(EXPRESSION), stringArgumentCaptor.capture());
        verify(property).putProperty(eq(TYPE), typeArgumentCaptor.capture());
        //noinspection unchecked
        verify(property).putProperty(eq(NAMESPACES), spaceArgumentCaptor.capture());

        assertEquals(NAME_DEFAULT_VALUE, stringArgumentCaptor.getAllValues().get(0));
        assertEquals(VALUE_DEFAULT_VALUE, stringArgumentCaptor.getAllValues().get(1));
        assertEquals(EXPRESSION_DEFAULT_VALUE, stringArgumentCaptor.getAllValues().get(2));
        assertEquals(TYPE_DEFAULT_VALUE, typeArgumentCaptor.getValue());
        assertEquals(NAME_SPACES_DEFAULT_VALUE, spaceArgumentCaptor.getValue());

        assertDefaultConfiguration();

        assertEquals(newProperty, property);
    }

    @Test
    public void serializationShouldBeDoneWithDefaultValues() throws Exception {
        assertContentAndValue(PATH_TO_RESOURCES + "DefaultParameters", entity.serializeProperty());
    }

    @Test
    public void deserializationShouldBeDoneWithDefaultValues() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(PATH_TO_RESOURCES + "DefaultParameters"));
        entity.applyAttributes(xml.getFirstChild());

        assertDefaultConfiguration();
    }

    @Test
    public void serializationShouldBeDoneWithExpressionParameter() throws Exception {
        entity.putProperty(TYPE, ValueType.EXPRESSION);

        assertContentAndValue(PATH_TO_RESOURCES + "ExpressionParameter", entity.serializeProperty());
    }

    @Test
    public void deserializationShouldBeDoneWithExpressionParameter() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(PATH_TO_RESOURCES + "ExpressionParameter"));
        entity.applyAttributes(xml.getFirstChild());

        assertConfiguration(ValueType.EXPRESSION,
                            NAME_DEFAULT_VALUE,
                            VALUE_DEFAULT_VALUE,
                            EXPRESSION_DEFAULT_VALUE,
                            NAME_SPACES_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneForWithParamPropertyName() throws Exception {
        assertContentAndValue(PATH_TO_RESOURCES + "WithParamPropertyName", entity.serializeWithParam());
    }

    @Test
    public void deserializationShouldBeDoneForWithParamPropertyName() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(PATH_TO_RESOURCES + "WithParamPropertyName"));
        entity.applyAttributes(xml.getFirstChild());

        assertDefaultConfiguration();
    }

    @Test
    public void deserializationShouldBeDoneWithIncorrectPropertyAttrybute() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(PATH_TO_RESOURCES + "IncorrectPropertyAttribute"));
        entity.applyAttributes(xml.getFirstChild());

        assertDefaultConfiguration();
    }

    @Test
    public void deserializationShouldBeDoneWithIncorrectWithParamAttrybute() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(PATH_TO_RESOURCES + "IncorrectWithParamAttribute"));
        entity.applyAttributes(xml.getFirstChild());

        assertDefaultConfiguration();
    }

    @Test
    public void copyPropertyListShouldBeReturnEmptyListWhenListToCopyIsNull() throws Exception {
        assertTrue(Property.copyPropertyList(null).isEmpty());
    }

    @Test
    public void copyPropertyListShouldBeDone() throws Exception {
        List<Property> propertyList = Arrays.asList(entity);
        List<Property> copyPropertyList = Property.copyPropertyList(propertyList);

        assertEquals(propertyList, copyPropertyList);
        assertNotSame(propertyList, copyPropertyList);
    }

}