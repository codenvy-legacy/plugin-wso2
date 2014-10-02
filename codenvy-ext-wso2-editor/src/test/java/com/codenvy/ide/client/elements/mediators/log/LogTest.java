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

import com.codenvy.ide.client.elements.AbstractElementTest;
import com.google.inject.Provider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.log.Log.DESCRIPTION;
import static com.codenvy.ide.client.elements.mediators.log.Log.LOG_CATEGORY;
import static com.codenvy.ide.client.elements.mediators.log.Log.LOG_LEVEL;
import static com.codenvy.ide.client.elements.mediators.log.Log.LOG_PROPERTIES;
import static com.codenvy.ide.client.elements.mediators.log.Log.LOG_SEPARATOR;
import static com.codenvy.ide.client.elements.mediators.log.Log.LogCategory;
import static com.codenvy.ide.client.elements.mediators.log.Log.LogCategory.DEBUG;
import static com.codenvy.ide.client.elements.mediators.log.Log.LogCategory.INFO;
import static com.codenvy.ide.client.elements.mediators.log.Log.LogLevel;
import static com.codenvy.ide.client.elements.mediators.log.Log.LogLevel.CUSTOM;
import static com.codenvy.ide.client.elements.mediators.log.Log.LogLevel.FULL;
import static com.codenvy.ide.client.elements.mediators.log.Log.LogLevel.HEADERS;
import static com.codenvy.ide.client.elements.mediators.log.Log.LogLevel.SIMPLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LogTest extends AbstractElementTest<Log> {

    private static final String ROOT_PATH        = "mediators/log/";
    private static final String PATH_TO_EXAMPLES = ROOT_PATH + "serialize/";

    private static final LogCategory    CATEGORY          = INFO;
    private static final LogLevel       LEVEL             = SIMPLE;
    private static final String         SEPARATOR         = "";
    private static final String         SEPARATOR_VALUE   = "separator";
    private static final String         LOG_DESCRIPTION   = "";
    private static final String         DESCRIPTION_VALUE = "description";
    private static final List<Property> PROPERTIES        = new ArrayList<>();

    private static final String PROPERTY_SERIALIZE_CONTENT = "<property name=\"property_name\" value=\"property_value\"/>\n";

    @Mock
    private Property           property;
    @Mock
    private Provider<Property> propertyProvider;

    @Before
    public void setUp() throws Exception {
        when(resources.log()).thenReturn(icon);
        when(propertyProvider.get()).thenReturn(property);

        entity = new Log(resources, branchProvider, elementCreatorsManager, propertyProvider);
    }


    @Override
    public void elementTitleShouldBeInitializedWithDefaultValue() throws Exception {
        assertEquals(Log.ELEMENT_NAME, entity.getTitle());
    }

    @Override
    public void elementNameShouldBeDefinedWithDefaultValue() throws Exception {
        assertEquals(Log.ELEMENT_NAME, entity.getElementName());
    }

    @Override
    public void serializeNameShouldBeDefinedWithDefaultValue() throws Exception {
        assertEquals(Log.SERIALIZATION_NAME, entity.getSerializationName());
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
        verify(resources).log();
        assertEquals(icon, entity.getIcon());
    }

    @Override
    public void elementShouldBeNotEqualedForAnotherInstanceOfElement() throws Exception {
        Log otherElement = new Log(resources, branchProvider, elementCreatorsManager, propertyProvider);
        assertFalse(entity.equals(otherElement));
    }

    @Override
    public void constructorPrepareOperationShouldBeDone() throws Exception {
        assertDefaultConfiguration();
    }

    private void assertDefaultConfiguration() {
        assertConfiguration(CATEGORY, LEVEL, SEPARATOR, LOG_DESCRIPTION, PROPERTIES);
    }

    private void assertConfiguration(LogCategory category,
                                     LogLevel level,
                                     String separator,
                                     String description,
                                     List<Property> properties) {

        assertEquals(category, entity.getProperty(LOG_CATEGORY));
        assertEquals(level, entity.getProperty(LOG_LEVEL));
        assertEquals(separator, entity.getProperty(LOG_SEPARATOR));
        assertEquals(description, entity.getProperty(DESCRIPTION));
        assertEquals(properties, entity.getProperty(LOG_PROPERTIES));
    }

    @Test
    public void serializationShouldBeDoneWithDefaultValues() throws Exception {
        assertContentAndValue(PATH_TO_EXAMPLES + "DefaultSerialization", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithDefaultProperties() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "DefaultSerialization"));

        assertDefaultConfiguration();
    }

    @Test
    public void serializationShouldBeDoneWithNotDefaultCategory() throws Exception {
        entity.putProperty(LOG_CATEGORY, DEBUG);

        assertContentAndValue(PATH_TO_EXAMPLES + "Category", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithNotDefaultCategory() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "Category"));

        assertConfiguration(DEBUG, SIMPLE, SEPARATOR, LOG_DESCRIPTION, PROPERTIES);
    }

    @Test
    public void serializationShouldBeDoneWithNotDefaultLevel() throws Exception {
        entity.putProperty(LOG_LEVEL, CUSTOM);

        assertContentAndValue(PATH_TO_EXAMPLES + "Level", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithNotDefaultLevel() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "Level"));

        assertConfiguration(INFO, CUSTOM, SEPARATOR, LOG_DESCRIPTION, PROPERTIES);
    }

    @Test
    public void serializationShouldBeDoneWithSeparator() throws Exception {
        entity.putProperty(LOG_SEPARATOR, SEPARATOR_VALUE);

        assertContentAndValue(PATH_TO_EXAMPLES + "Separator", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithSeparator() throws Exception {
        entity.putProperty(LOG_SEPARATOR, SEPARATOR_VALUE);

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "Separator"));

        assertConfiguration(INFO, SIMPLE, SEPARATOR_VALUE, LOG_DESCRIPTION, PROPERTIES);
    }

    @Test
    public void serializationShouldBeDoneWithDescription() throws Exception {
        entity.putProperty(DESCRIPTION, DESCRIPTION_VALUE);

        assertContentAndValue(PATH_TO_EXAMPLES + "Description", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithDescription() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "Description"));

        assertConfiguration(INFO, SIMPLE, SEPARATOR, DESCRIPTION_VALUE, PROPERTIES);
    }

    @Test
    public void serializationShouldBeDoneWithProperties() throws Exception {
        when(property.serializeProperty()).thenReturn(PROPERTY_SERIALIZE_CONTENT);
        entity.putProperty(LOG_PROPERTIES, Arrays.asList(property));

        assertContentAndValue(PATH_TO_EXAMPLES + "Properties", entity.serialize());
        verify(property).serializeProperty();
    }

    @Test
    public void deserializationShouldBeDoneWithProperties() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "Properties"));

        assertConfiguration(INFO, SIMPLE, SEPARATOR, LOG_DESCRIPTION, Arrays.asList(property));
    }

    @Test
    public void serializationShouldBeDoneWithAllParameters() throws Exception {
        when(property.serializeProperty()).thenReturn(PROPERTY_SERIALIZE_CONTENT);

        entity.putProperty(LOG_LEVEL, HEADERS);
        entity.putProperty(LOG_CATEGORY, DEBUG);
        entity.putProperty(LOG_SEPARATOR, SEPARATOR_VALUE);
        entity.putProperty(LOG_PROPERTIES, Arrays.asList(property));
        entity.putProperty(DESCRIPTION, DESCRIPTION_VALUE);

        assertContentAndValue(PATH_TO_EXAMPLES + "AllParameters", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithAllParameters() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "AllParameters"));

        assertConfiguration(DEBUG, HEADERS, SEPARATOR_VALUE, DESCRIPTION_VALUE, Arrays.asList(property));
    }

    @Test
    public void customLogLevelShouldBeReturned() throws Exception {
        assertEquals(CUSTOM, LogLevel.getItemByValue("custom"));
    }

    @Test
    public void simpleLogLevelShouldBeReturned() throws Exception {
        assertEquals(SIMPLE, LogLevel.getItemByValue("simple"));
    }

    @Test
    public void headersLogLevelShouldBeReturned() throws Exception {
        assertEquals(HEADERS, LogLevel.getItemByValue("headers"));
    }

    @Test
    public void fullLogLevelShouldBeReturned() throws Exception {
        assertEquals(FULL, LogLevel.getItemByValue("full"));
    }

    @Test
    public void emptyStringShouldBeReturnedWhenPropertiesIsNull() throws Exception {
        entity.putProperty(LOG_PROPERTIES, null);

        assertEquals("", entity.serializeProperties());
    }
}