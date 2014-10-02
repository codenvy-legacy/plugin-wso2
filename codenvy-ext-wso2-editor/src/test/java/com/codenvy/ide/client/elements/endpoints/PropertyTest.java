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

package com.codenvy.ide.client.elements.endpoints;

import com.codenvy.ide.client.elements.AbstractEntityTest;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.endpoints.addressendpoint.Property;
import com.codenvy.ide.client.elements.mediators.ValueType;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;
import com.google.inject.Provider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.elements.endpoints.addressendpoint.Property.EXPRESSION;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.Property.NAME;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.Property.NAMESPACES;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.Property.SCOPE;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.Property.Scope;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.Property.Scope.AXIS2;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.Property.Scope.AXIS2_CLIENT;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.Property.Scope.DEFAULT;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.Property.Scope.TRANSPORT;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.Property.TYPE;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.Property.VALUE;
import static com.codenvy.ide.client.elements.mediators.ValueType.LITERAL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Valeriy Svydenko
 */
public class PropertyTest extends AbstractEntityTest<Property> {
    private static final String RESOURCES_PATH = "endpoints/address/property/serialize/";

    private static final String          NAME_DEFAULT_VALUE       = "property_name";
    private static final String          VALUE_DEFAULT_VALUE      = "property_value";
    private static final String          EXPRESSION_DEFAULT_VALUE = "/default/expression";
    private static final ValueType       TYPE_DEFAULT_VALUE       = LITERAL;
    private static final Scope           SCOPE_DEFAULT_VALUE      = DEFAULT;
    private static final List<NameSpace> NAMESPACE_DEFAULT_VALUE  = new ArrayList<>();

    @Mock
    private Provider<Property>  propertyProvider;
    @Mock
    private Provider<NameSpace> nameSpaceProvider;
    @Mock
    private Property            property;
    @Mock
    private NameSpace           nameSpace;

    @Captor
    private ArgumentCaptor<ArrayList<NameSpace>> spaceArgumentCaptor;
    @Captor
    private ArgumentCaptor<String>               stringArgumentCaptor;
    @Captor
    private ArgumentCaptor<ValueType>            valueTypeArgumentCaptor;
    @Captor
    private ArgumentCaptor<Scope>                scopeArgumentCaptor;


    @Before
    public void setUp() throws Exception {
        when(propertyProvider.get()).thenReturn(property);
        when(nameSpaceProvider.get()).thenReturn(nameSpace);

        entity = new Property(propertyProvider, nameSpaceProvider);
    }

    private void assertConfiguration(String name,
                                     String value,
                                     String expression,
                                     ValueType type,
                                     Scope scope,
                                     List<NameSpace> nameSpaces) {

        assertEquals(name, entity.getProperty(NAME));
        assertEquals(value, entity.getProperty(VALUE));
        assertEquals(expression, entity.getProperty(EXPRESSION));
        assertEquals(type, entity.getProperty(TYPE));
        assertEquals(scope, entity.getProperty(SCOPE));
        assertEquals(nameSpaces, entity.getProperty(NAMESPACES));
    }

    private void assertDefaultConfiguration() {
        assertConfiguration(NAME_DEFAULT_VALUE,
                            VALUE_DEFAULT_VALUE,
                            EXPRESSION_DEFAULT_VALUE,
                            TYPE_DEFAULT_VALUE,
                            SCOPE_DEFAULT_VALUE,
                            NAMESPACE_DEFAULT_VALUE);
    }

    @Test
    public void allPropertiesShouldBeCopied() throws Exception {
        entity.putProperty(NAME, NAME_DEFAULT_VALUE);
        entity.putProperty(VALUE, VALUE_DEFAULT_VALUE);
        entity.putProperty(EXPRESSION, EXPRESSION_DEFAULT_VALUE);
        entity.putProperty(TYPE, TYPE_DEFAULT_VALUE);
        entity.putProperty(SCOPE, SCOPE_DEFAULT_VALUE);
        entity.putProperty(NAMESPACES, NAMESPACE_DEFAULT_VALUE);

        Property newProperty = entity.copy();

        verify(property).putProperty(eq(NAME), stringArgumentCaptor.capture());
        verify(property).putProperty(eq(VALUE), stringArgumentCaptor.capture());
        verify(property).putProperty(eq(EXPRESSION), stringArgumentCaptor.capture());
        verify(property).putProperty(eq(TYPE), valueTypeArgumentCaptor.capture());
        verify(property).putProperty(eq(SCOPE), scopeArgumentCaptor.capture());
        verify(property).putProperty(eq(NAMESPACES), spaceArgumentCaptor.capture());

        assertEquals(NAME_DEFAULT_VALUE, stringArgumentCaptor.getAllValues().get(0));
        assertEquals(VALUE_DEFAULT_VALUE, stringArgumentCaptor.getAllValues().get(1));
        assertEquals(EXPRESSION_DEFAULT_VALUE, stringArgumentCaptor.getAllValues().get(2));
        assertEquals(TYPE_DEFAULT_VALUE, valueTypeArgumentCaptor.getValue());
        assertEquals(SCOPE_DEFAULT_VALUE, scopeArgumentCaptor.getValue());
        assertEquals(NAMESPACE_DEFAULT_VALUE, spaceArgumentCaptor.getValue());

        assertDefaultConfiguration();

        assertEquals(property, newProperty);
    }

    @Test
    public void serializationShouldBeDoneWithDefaultValues() throws Exception {
        assertContentAndValue(RESOURCES_PATH + "DefaultSerialization", entity.serialize());
    }

    @Test
    public void returnEmptyStringIfScopeIsNull() throws Exception {
        entity.putProperty(SCOPE, null);

        assertTrue(entity.serialize().isEmpty());
    }

    @Test
    public void deserializationShouldBeDoneForDefaultRepresentation() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(RESOURCES_PATH + "DefaultSerialization"));
        entity.deserialize(xml.getFirstChild());

        assertDefaultConfiguration();
    }

    @Test
    public void serializationShouldBeDoneWhenScopeAttributeIsAXIS2() throws Exception {
        entity.putProperty(SCOPE, AXIS2);

        assertContentAndValue(RESOURCES_PATH + "ScopeAttributeIsAXIS2", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenScopeAttributeIsAXIS2() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(RESOURCES_PATH + "ScopeAttributeIsAXIS2"));
        entity.deserialize(xml.getFirstChild());

        assertConfiguration(NAME_DEFAULT_VALUE,
                            VALUE_DEFAULT_VALUE,
                            EXPRESSION_DEFAULT_VALUE,
                            TYPE_DEFAULT_VALUE,
                            AXIS2,
                            NAMESPACE_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenScopeAttributeIsTransport() throws Exception {
        entity.putProperty(SCOPE, TRANSPORT);

        assertContentAndValue(RESOURCES_PATH + "ScopeAttributeIsTransport", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenScopeAttributeIsTransport() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(RESOURCES_PATH + "ScopeAttributeIsTransport"));
        entity.deserialize(xml.getFirstChild());

        assertConfiguration(NAME_DEFAULT_VALUE,
                            VALUE_DEFAULT_VALUE,
                            EXPRESSION_DEFAULT_VALUE,
                            TYPE_DEFAULT_VALUE,
                            TRANSPORT,
                            NAMESPACE_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenScopeAttributeIsAxis2Client() throws Exception {
        entity.putProperty(SCOPE, AXIS2_CLIENT);

        assertContentAndValue(RESOURCES_PATH + "ScopeAttributeIsAxis2Client", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenScopeAttributeIsAxis2Client() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(RESOURCES_PATH + "ScopeAttributeIsAxis2Client"));
        entity.deserialize(xml.getFirstChild());

        assertConfiguration(NAME_DEFAULT_VALUE,
                            VALUE_DEFAULT_VALUE,
                            EXPRESSION_DEFAULT_VALUE,
                            TYPE_DEFAULT_VALUE,
                            AXIS2_CLIENT,
                            NAMESPACE_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenScopeAttributeIsDefault() throws Exception {
        entity.putProperty(SCOPE, DEFAULT);

        assertContentAndValue(RESOURCES_PATH + "DefaultSerialization", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenScopeAttributeDefault() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(RESOURCES_PATH + "DefaultSerialization"));
        entity.deserialize(xml.getFirstChild());

        assertConfiguration(NAME_DEFAULT_VALUE,
                            VALUE_DEFAULT_VALUE,
                            EXPRESSION_DEFAULT_VALUE,
                            TYPE_DEFAULT_VALUE,
                            DEFAULT,
                            NAMESPACE_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenValueTypeIsExpression() throws Exception {
        entity.putProperty(TYPE, ValueType.EXPRESSION);

        assertContentAndValue(RESOURCES_PATH + "ValueTypeIsExpression", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenValueTypeIsExpression() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(RESOURCES_PATH + "ValueTypeIsExpression"));
        entity.deserialize(xml.getFirstChild());

        assertConfiguration(NAME_DEFAULT_VALUE,
                            VALUE_DEFAULT_VALUE,
                            EXPRESSION_DEFAULT_VALUE,
                            ValueType.EXPRESSION,
                            DEFAULT,
                            NAMESPACE_DEFAULT_VALUE);
    }

    @Test
    public void copyEndPointPropertyListShouldBeReturnEmptyListWhenListToCopyIsNull() throws Exception {
        assertTrue(Property.copyEndPointPropertyList(null).isEmpty());
    }

    @Test
    public void copyEndPointPropertyListShouldBeDone() throws Exception {
        List<Property> propertyList = Arrays.asList(entity);
        List<Property> copyPropertyList = Property.copyEndPointPropertyList(propertyList);

        assertEquals(propertyList, copyPropertyList);
        assertNotSame(propertyList, copyPropertyList);
    }

    @Test
    public void transportValueOfScopeShouldBeReturned1() throws Exception {
        assertEquals(TRANSPORT, Scope.getItemByValue("transport"));
    }

    @Test
    public void axis2ValueOfScopeShouldBeReturned1() throws Exception {
        assertEquals(AXIS2, Scope.getItemByValue("axis2"));
    }

    @Test
    public void axis2ClientValueOfScopeShouldBeReturned1() throws Exception {
        assertEquals(AXIS2_CLIENT, Scope.getItemByValue("axis2-client"));
    }

    @Test
    public void defaultValueOfScopeShouldBeReturned1() throws Exception {
        assertEquals(DEFAULT, Scope.getItemByValue("default"));
    }

    @Test
    public void defaultValueOfScopeShouldBeReturned2() throws Exception {
        assertEquals(DEFAULT, Scope.getItemByValue("some text"));
    }

}