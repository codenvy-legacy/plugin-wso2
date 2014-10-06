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
package com.codenvy.ide.client.elements.mediators;

import com.codenvy.ide.client.elements.AbstractElementTest;
import com.codenvy.ide.client.elements.NameSpace;
import com.google.inject.Provider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.Action.REMOVE;
import static com.codenvy.ide.client.elements.mediators.Action.SET;
import static com.codenvy.ide.client.elements.mediators.Property.DESCRIPTION;
import static com.codenvy.ide.client.elements.mediators.Property.DataType;
import static com.codenvy.ide.client.elements.mediators.Property.DataType.STRING;
import static com.codenvy.ide.client.elements.mediators.Property.NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.Property.PROPERTY_ACTION;
import static com.codenvy.ide.client.elements.mediators.Property.PROPERTY_DATA_TYPE;
import static com.codenvy.ide.client.elements.mediators.Property.PROPERTY_NAME;
import static com.codenvy.ide.client.elements.mediators.Property.PROPERTY_SCOPE;
import static com.codenvy.ide.client.elements.mediators.Property.Scope;
import static com.codenvy.ide.client.elements.mediators.Property.Scope.AXIS2;
import static com.codenvy.ide.client.elements.mediators.Property.Scope.AXIS2_CLIENT;
import static com.codenvy.ide.client.elements.mediators.Property.Scope.OPERATION;
import static com.codenvy.ide.client.elements.mediators.Property.Scope.SYNAPSE;
import static com.codenvy.ide.client.elements.mediators.Property.Scope.TRANSPORT;
import static com.codenvy.ide.client.elements.mediators.Property.VALUE_EXPRESSION;
import static com.codenvy.ide.client.elements.mediators.Property.VALUE_LITERAL;
import static com.codenvy.ide.client.elements.mediators.Property.VALUE_STRING_CAPTURE_GROUP;
import static com.codenvy.ide.client.elements.mediators.Property.VALUE_STRING_PATTERN;
import static com.codenvy.ide.client.elements.mediators.Property.VALUE_TYPE;
import static com.codenvy.ide.client.elements.mediators.ValueType.EXPRESSION;
import static com.codenvy.ide.client.elements.mediators.ValueType.LITERAL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Valeriy Svydenko
 */
public class PropertyTest extends AbstractElementTest<Property> {

    private static final String PATH_TO_EXAMPLES = "mediators/property/serialize/";
    private static final String SOME_STRING      = "string";

    private static final Scope           PROPERTY_SCOPE_DEFAULT_VALUE     = SYNAPSE;
    private static final Action          PROPERTY_ACTION_DEFAULT_VALUE    = SET;
    private static final ValueType       VALUE_TYPE_DEFAULT_VALUE         = LITERAL;
    private static final DataType        PROPERTY_DATA_TYPE_DEFAULT_VALUE = STRING;
    private static final String          PROPERTY_NAME_DEFAULT_VALUE      = "property_name";
    private static final String          VALUE_LITERAL_DEFAULT_VALUE      = "value";
    private static final String          VALUE_EXPRESSION_DEFAULT_VALUE   = "/default/expression";
    private static final String          STRING_PATTERN_DEFAULT_VALUE     = "";
    private static final String          CAPTURE_GROUP_DEFAULT_VALUE      = "";
    private static final String          DESCRIPTION_DEFAULT_VALUE        = "";
    private static final List<NameSpace> NAME_SPACES                      = new ArrayList<>();

    @Mock
    private NameSpace           nameSpace;
    @Mock
    private Provider<NameSpace> nameSpaceProvider;

    @Before
    public void setUp() throws Exception {
        when(resources.property()).thenReturn(icon);
        when(nameSpaceProvider.get()).thenReturn(nameSpace);

        entity = new Property(resources, branchProvider, elementCreatorsManager, nameSpaceProvider);
    }

    @Override
    public void elementTitleShouldBeInitializedWithDefaultValue() throws Exception {
        assertEquals(Property.ELEMENT_NAME, entity.getTitle());
    }

    @Override
    public void elementNameShouldBeDefinedWithDefaultValue() throws Exception {
        assertEquals(Property.ELEMENT_NAME, entity.getElementName());
    }

    @Override
    public void serializeNameShouldBeDefinedWithDefaultValue() throws Exception {
        assertEquals(Property.SERIALIZATION_NAME, entity.getSerializationName());
    }

    @Override
    public void possibleToChangeBranchAmountParamShouldBeInitializedWithDefaultValue() throws Exception {
        assertFalse(entity.isPossibleToAddBranches());
    }

    @Override
    public void needToShowTitleAndIconParamShouldBeInitializedWithDefaultValue() throws Exception {
        assertTrue(entity.needsToShowIconAndTitle());
    }

    @Override
    public void elementIconShouldBeInitializedWithDefaultValue() throws Exception {
        verify(resources).property();
        assertEquals(icon, entity.getIcon());
    }

    @Override
    public void elementShouldBeNotEqualedForAnotherInstanceOfElement() throws Exception {
        Property anotherElement = new Property(resources, branchProvider, elementCreatorsManager, nameSpaceProvider);
        assertFalse(entity.equals(anotherElement));
    }

    @Override
    public void constructorPrepareOperationShouldBeDone() throws Exception {
        assertDefaultConfiguration();
    }

    private void assertDefaultConfiguration() {
        assertConfiguration(PROPERTY_NAME_DEFAULT_VALUE,
                            PROPERTY_ACTION_DEFAULT_VALUE,
                            VALUE_TYPE_DEFAULT_VALUE,
                            PROPERTY_DATA_TYPE_DEFAULT_VALUE,
                            VALUE_LITERAL_DEFAULT_VALUE,
                            VALUE_EXPRESSION_DEFAULT_VALUE,
                            STRING_PATTERN_DEFAULT_VALUE,
                            PROPERTY_SCOPE_DEFAULT_VALUE,
                            CAPTURE_GROUP_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            NAME_SPACES);
    }

    private void assertConfiguration(@Nonnull String propertyName,
                                     @Nonnull Action propertyAction,
                                     @Nonnull ValueType valueType,
                                     @Nonnull DataType propertyDataType,
                                     @Nonnull String valueLiteral,
                                     @Nonnull String valueExpression,
                                     @Nonnull String stringPattern,
                                     @Nonnull Scope propertyScope,
                                     @Nonnull String captureGroup,
                                     @Nonnull String description,
                                     @Nonnull List<NameSpace> nameSpaces) {
        assertEquals(propertyName, entity.getProperty(PROPERTY_NAME));
        assertEquals(propertyAction, entity.getProperty(PROPERTY_ACTION));
        assertEquals(description, entity.getProperty(DESCRIPTION));
        assertEquals(valueType, entity.getProperty(VALUE_TYPE));
        assertEquals(propertyDataType, entity.getProperty(PROPERTY_DATA_TYPE));
        assertEquals(valueLiteral, entity.getProperty(VALUE_LITERAL));
        assertEquals(valueExpression, entity.getProperty(VALUE_EXPRESSION));
        assertEquals(stringPattern, entity.getProperty(VALUE_STRING_PATTERN));
        assertEquals(propertyScope, entity.getProperty(PROPERTY_SCOPE));
        assertEquals(captureGroup, entity.getProperty(VALUE_STRING_CAPTURE_GROUP));
        assertEquals(nameSpaces, entity.getProperty(NAMESPACES));
    }

    @Test
    public void serializationShouldBeDoneWithDefaultParameter() throws Exception {
        assertContentAndValue(PATH_TO_EXAMPLES + "Default", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithDefaultParameters() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "Default"));

        assertDefaultConfiguration();
    }

    @Test
    public void serializationShouldBeDoneWhenPropertyActionIsRemove() throws Exception {
        entity.putProperty(PROPERTY_ACTION, REMOVE);

        assertContentAndValue(PATH_TO_EXAMPLES + "PropertyActionIsRemove", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenPropertyActionIsRemove() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "PropertyActionIsRemove"));

        assertConfiguration(PROPERTY_NAME_DEFAULT_VALUE,
                            REMOVE,
                            VALUE_TYPE_DEFAULT_VALUE,
                            PROPERTY_DATA_TYPE_DEFAULT_VALUE,
                            VALUE_LITERAL_DEFAULT_VALUE,
                            VALUE_EXPRESSION_DEFAULT_VALUE,
                            STRING_PATTERN_DEFAULT_VALUE,
                            PROPERTY_SCOPE_DEFAULT_VALUE,
                            CAPTURE_GROUP_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            NAME_SPACES);
    }

    @Test
    public void serializationShouldBeDoneWhenPropertyActionIsSet() throws Exception {
        entity.putProperty(PROPERTY_ACTION, SET);

        assertContentAndValue(PATH_TO_EXAMPLES + "Default", entity.serialize());
    }

    @Test
    public void serializationShouldBeDoneWhenValueTypeIsLiteral() throws Exception {
        entity.putProperty(VALUE_TYPE, LITERAL);

        assertContentAndValue(PATH_TO_EXAMPLES + "Default", entity.serialize());
    }

    @Test
    public void serializationShouldBeDoneWhenValueTypeIsExpression() throws Exception {
        entity.putProperty(VALUE_TYPE, EXPRESSION);

        assertContentAndValue(PATH_TO_EXAMPLES + "ValueTypeIsExpression", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenValueTypeIsExpression() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "ValueTypeIsExpression"));

        assertConfiguration(PROPERTY_NAME_DEFAULT_VALUE,
                            PROPERTY_ACTION_DEFAULT_VALUE,
                            EXPRESSION,
                            PROPERTY_DATA_TYPE_DEFAULT_VALUE,
                            VALUE_EXPRESSION_DEFAULT_VALUE,
                            VALUE_EXPRESSION_DEFAULT_VALUE,
                            STRING_PATTERN_DEFAULT_VALUE,
                            PROPERTY_SCOPE_DEFAULT_VALUE,
                            CAPTURE_GROUP_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            NAME_SPACES);
    }

    @Test
    public void serializationShouldBeDoneWithDescription() throws Exception {
        entity.putProperty(DESCRIPTION, SOME_STRING);

        assertContentAndValue(PATH_TO_EXAMPLES + "Description", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithDescription() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "Description"));

        assertConfiguration(PROPERTY_NAME_DEFAULT_VALUE,
                            PROPERTY_ACTION_DEFAULT_VALUE,
                            VALUE_TYPE_DEFAULT_VALUE,
                            PROPERTY_DATA_TYPE_DEFAULT_VALUE,
                            VALUE_LITERAL_DEFAULT_VALUE,
                            VALUE_EXPRESSION_DEFAULT_VALUE,
                            STRING_PATTERN_DEFAULT_VALUE,
                            PROPERTY_SCOPE_DEFAULT_VALUE,
                            CAPTURE_GROUP_DEFAULT_VALUE,
                            SOME_STRING,
                            NAME_SPACES);
    }

    @Test
    public void serializationShouldBeDoneWithStringPattern() throws Exception {
        entity.putProperty(VALUE_STRING_PATTERN, SOME_STRING);

        assertContentAndValue(PATH_TO_EXAMPLES + "StringPattern", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithStringPattern() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "StringPattern"));

        assertConfiguration(PROPERTY_NAME_DEFAULT_VALUE,
                            PROPERTY_ACTION_DEFAULT_VALUE,
                            VALUE_TYPE_DEFAULT_VALUE,
                            PROPERTY_DATA_TYPE_DEFAULT_VALUE,
                            VALUE_LITERAL_DEFAULT_VALUE,
                            VALUE_EXPRESSION_DEFAULT_VALUE,
                            SOME_STRING,
                            PROPERTY_SCOPE_DEFAULT_VALUE,
                            CAPTURE_GROUP_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            NAME_SPACES);
    }

    @Test
    public void serializationShouldBeDoneWithStringCapturingGroup() throws Exception {
        entity.putProperty(VALUE_STRING_CAPTURE_GROUP, SOME_STRING);

        assertContentAndValue(PATH_TO_EXAMPLES + "CapturingGroup", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithStringCapturingGroup() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "CapturingGroup"));

        assertConfiguration(PROPERTY_NAME_DEFAULT_VALUE,
                            PROPERTY_ACTION_DEFAULT_VALUE,
                            VALUE_TYPE_DEFAULT_VALUE,
                            PROPERTY_DATA_TYPE_DEFAULT_VALUE,
                            VALUE_LITERAL_DEFAULT_VALUE,
                            VALUE_EXPRESSION_DEFAULT_VALUE,
                            STRING_PATTERN_DEFAULT_VALUE,
                            PROPERTY_SCOPE_DEFAULT_VALUE,
                            SOME_STRING,
                            DESCRIPTION_DEFAULT_VALUE,
                            NAME_SPACES);
    }

    @Test
    public void serializationShouldBeDoneWithScope() throws Exception {
        entity.putProperty(PROPERTY_SCOPE, TRANSPORT);

        assertContentAndValue(PATH_TO_EXAMPLES + "Scope", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithScope() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "Scope"));

        assertConfiguration(PROPERTY_NAME_DEFAULT_VALUE,
                            PROPERTY_ACTION_DEFAULT_VALUE,
                            VALUE_TYPE_DEFAULT_VALUE,
                            PROPERTY_DATA_TYPE_DEFAULT_VALUE,
                            VALUE_LITERAL_DEFAULT_VALUE,
                            VALUE_EXPRESSION_DEFAULT_VALUE,
                            STRING_PATTERN_DEFAULT_VALUE,
                            TRANSPORT,
                            CAPTURE_GROUP_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            NAME_SPACES);
    }

    @Test
    public void serializationShouldBeDoneWithAllParameters() throws Exception {
        entity.putProperty(PROPERTY_ACTION, SET);
        entity.putProperty(VALUE_TYPE, LITERAL);
        entity.putProperty(DESCRIPTION, SOME_STRING);
        entity.putProperty(VALUE_STRING_PATTERN, SOME_STRING);
        entity.putProperty(VALUE_STRING_CAPTURE_GROUP, SOME_STRING);
        entity.putProperty(PROPERTY_SCOPE, SYNAPSE);

        assertContentAndValue(PATH_TO_EXAMPLES + "FullAttributes", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithAllParameters() throws Exception {

        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "FullAttributes"));

        assertConfiguration(PROPERTY_NAME_DEFAULT_VALUE,
                            PROPERTY_ACTION_DEFAULT_VALUE,
                            VALUE_TYPE_DEFAULT_VALUE,
                            PROPERTY_DATA_TYPE_DEFAULT_VALUE,
                            VALUE_LITERAL_DEFAULT_VALUE,
                            VALUE_EXPRESSION_DEFAULT_VALUE,
                            SOME_STRING,
                            SYNAPSE,
                            SOME_STRING,
                            SOME_STRING,
                            NAME_SPACES);
    }

    @Test
    public void serializationShouldBeDoneWhenNameSpaceNotEmpty() throws Exception {
        List<NameSpace> nameSpaces = new ArrayList<>();

        entity.putProperty(NAMESPACES, nameSpaces);

        assertTrue(nameSpaces.isEmpty());

        entity.applyAttribute("xmlns:a", "b");

        assertEquals(1, nameSpaces.size());
        assertEquals(nameSpace, nameSpaces.get(0));
    }

    @Test
    public void deserializationShouldBeDoneWithIncorrectAttribute() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "IncorrectAttribute"));

        assertDefaultConfiguration();
    }

    @Test
    public void transportValueOfScopeShouldBeReturned() throws Exception {
        assertEquals(TRANSPORT, Scope.getItemByValue("transport"));
    }

    @Test
    public void axis2ValueOfScopeShouldBeReturned() throws Exception {
        assertEquals(AXIS2, Scope.getItemByValue("axis2"));
    }

    @Test
    public void axis2ClientValueOfScopeShouldBeReturned() throws Exception {
        assertEquals(AXIS2_CLIENT, Scope.getItemByValue("axis2-client"));
    }

    @Test
    public void operationValueOfScopeShouldBeReturned() throws Exception {
        assertEquals(OPERATION, Scope.getItemByValue("operation"));
    }

    @Test
    public void synapseValueOfScopeShouldBeReturned1() throws Exception {
        assertEquals(SYNAPSE, Scope.getItemByValue("Synapse"));
    }

    @Test
    public void synapseValueOfScopeShouldBeReturned2() throws Exception {
        assertEquals(SYNAPSE, Scope.getItemByValue(SOME_STRING));
    }

    @Test
    public void setValueOfActionShouldBeReturned() throws Exception {
        assertEquals(SET, Action.getItemByValue("set"));
    }

    @Test
    public void removeValueOfActionShouldBeReturned() throws Exception {
        assertEquals(REMOVE, Action.getItemByValue("remove"));
    }

}