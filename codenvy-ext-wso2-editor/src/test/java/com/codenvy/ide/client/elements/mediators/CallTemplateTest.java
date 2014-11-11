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
import com.codenvy.ide.client.elements.mediators.log.Property;
import com.google.inject.Provider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.CallTemplate.AVAILABLE_TEMPLATES;
import static com.codenvy.ide.client.elements.mediators.CallTemplate.AvailableTemplates;
import static com.codenvy.ide.client.elements.mediators.CallTemplate.AvailableTemplates.EMPTY;
import static com.codenvy.ide.client.elements.mediators.CallTemplate.AvailableTemplates.SDF;
import static com.codenvy.ide.client.elements.mediators.CallTemplate.AvailableTemplates.SELECT_FROM_TEMPLATE;
import static com.codenvy.ide.client.elements.mediators.CallTemplate.DESCRIPTION;
import static com.codenvy.ide.client.elements.mediators.CallTemplate.PARAMETERS;
import static com.codenvy.ide.client.elements.mediators.CallTemplate.TARGET_TEMPLATES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Dmitry Shnurenko
 */
public class CallTemplateTest extends AbstractElementTest<CallTemplate> {

    private static final String PATH_TO_EXAMPLES = "mediators/calltemplate/serialize/";

    private static final AvailableTemplates AVAILABLE_TEMPLATES_DEFAULT_VALUE = EMPTY;
    private static final String             TARGET_TEMPLATES_DEFAULT_VALUE    = "";
    private static final String             TARGET_TEMPLATE_VALUE             = "target";
    private static final String             DESCRIPTION_DEFAULT_VALUE         = "";
    private static final String             DESCRIPTION_VALUE                 = "description";
    private static final List<Property>     PARAMETERS_DEFAULT_VALUE          = new ArrayList<>();

    private static final String PROPERTY_SERIALIZE_CONTENT = "<with-param name=\"property_name\" value=\"property_value\"/>\n";

    @Mock
    private Property           property;
    @Mock
    private Provider<Property> propertyProvider;

    @Before
    public void setUp() throws Exception {
        when(resources.callTemplate()).thenReturn(icon);
        when(propertyProvider.get()).thenReturn(property);

        entity = new CallTemplate(resources, branchProvider, elementCreatorsManager, propertyProvider);
    }

    @Override
    public void elementTitleShouldBeInitializedWithDefaultValue() throws Exception {
        assertEquals(CallTemplate.ELEMENT_NAME, entity.getTitle());
    }

    @Override
    public void elementNameShouldBeDefinedWithDefaultValue() throws Exception {
        assertEquals(CallTemplate.ELEMENT_NAME, entity.getElementName());
    }

    @Override
    public void serializeNameShouldBeDefinedWithDefaultValue() throws Exception {
        assertEquals(CallTemplate.SERIALIZATION_NAME, entity.getSerializationName());
    }

    @Override
    public void possibleToChangeBranchAmountParamShouldBeInitializedWithDefaultValue() throws Exception {
        assertFalse(entity.isPossibleToAddBranches());
    }

    @Override
    public void needToShowTitleAndIconParamShouldBeInitializedWithDefaultValue() throws Exception {
        assertFalse(entity.isRoot());
    }

    @Override
    public void elementIconShouldBeInitializedWithDefaultValue() throws Exception {
        verify(resources).callTemplate();
        assertEquals(icon, entity.getIcon());
    }

    @Override
    public void elementShouldBeNotEqualedForAnotherInstanceOfElement() throws Exception {
        CallTemplate anotherElement = new CallTemplate(resources, branchProvider, elementCreatorsManager, propertyProvider);
        assertFalse(entity.equals(anotherElement));
    }

    @Override
    public void constructorPrepareOperationShouldBeDone() throws Exception {
        assertDefaultConfiguration();
    }

    private void assertConfiguration(AvailableTemplates availableTemplates, String target, String description, List<Property> parameters) {
        assertEquals(availableTemplates, entity.getProperty(AVAILABLE_TEMPLATES));
        assertEquals(target, entity.getProperty(TARGET_TEMPLATES));
        assertEquals(description, entity.getProperty(DESCRIPTION));
        assertEquals(parameters, entity.getProperty(PARAMETERS));
    }

    private void assertDefaultConfiguration() {
        assertConfiguration(AVAILABLE_TEMPLATES_DEFAULT_VALUE,
                            TARGET_TEMPLATES_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            PARAMETERS_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWithDefaultParameter() throws Exception {
        assertContentAndValue(PATH_TO_EXAMPLES + "DefaultParameter", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithDefaultParameters() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "DefaultParameter"));

        assertDefaultConfiguration();

    }

    @Test
    public void serializationShouldBeDoneWithTargetParameter() throws Exception {
        entity.putProperty(TARGET_TEMPLATES, TARGET_TEMPLATE_VALUE);

        assertContentAndValue(PATH_TO_EXAMPLES + "TargetParameter", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithTargetParameter() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "TargetParameter"));

        assertConfiguration(EMPTY, TARGET_TEMPLATE_VALUE, DESCRIPTION_DEFAULT_VALUE, PARAMETERS_DEFAULT_VALUE);
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

        assertConfiguration(EMPTY, TARGET_TEMPLATES_DEFAULT_VALUE, DESCRIPTION_VALUE, PARAMETERS_DEFAULT_VALUE);
    }

    @Test
    public void emptyStringShouldBeReturnedWhenDescriptionIsNull() throws Exception {
        entity.putProperty(DESCRIPTION, null);

        assertEquals("target=\"\"", entity.serializeAttributes());
    }

    @Test
    public void serializationShouldBeReturnedWhenParameterIsNull() throws Exception {
        entity.putProperty(PARAMETERS, null);

        assertEquals("", entity.serializeProperties());
    }

    @Test
    public void serializationShouldBeDoneWithInnerParameter() throws Exception {
        when(property.serializeWithParam()).thenReturn(PROPERTY_SERIALIZE_CONTENT);
        entity.putProperty(PARAMETERS, Arrays.asList(property));

        assertContentAndValue(PATH_TO_EXAMPLES + "Parameters", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithInnerParameter() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "Parameters"));

        assertConfiguration(EMPTY, TARGET_TEMPLATES_DEFAULT_VALUE, DESCRIPTION_DEFAULT_VALUE, Arrays.asList(property));
    }

    @Test
    public void serializationShouldBeDoneWithAllParameters() throws Exception {
        when(property.serializeWithParam()).thenReturn(PROPERTY_SERIALIZE_CONTENT);

        entity.putProperty(AVAILABLE_TEMPLATES, AVAILABLE_TEMPLATES_DEFAULT_VALUE);
        entity.putProperty(TARGET_TEMPLATES, TARGET_TEMPLATE_VALUE);
        entity.putProperty(DESCRIPTION, DESCRIPTION_VALUE);
        entity.putProperty(PARAMETERS, Arrays.asList(property));

        assertContentAndValue(PATH_TO_EXAMPLES + "AllParameters", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithAllParameters() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "AllParameters"));

        assertConfiguration(EMPTY, TARGET_TEMPLATE_VALUE, DESCRIPTION_VALUE, Arrays.asList(property));
    }

    @Test
    public void selectFromTemplateShouldBeReturned() throws Exception {
        assertEquals(SELECT_FROM_TEMPLATE, AvailableTemplates.getItemByValue("Select From Templates"));
    }

    @Test
    public void deserializationShouldBeDoneWithIncorrectNode() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "IncorrectAttribute"));

        assertDefaultConfiguration();
    }

    @Test
    public void sdfShouldBeReturned() throws Exception {
        assertEquals(SDF, AvailableTemplates.getItemByValue("sdf"));
    }

    @Test
    public void emptyValueShouldBeReturned() throws Exception {
        assertEquals(EMPTY, AvailableTemplates.getItemByValue(""));
    }

    @Test
    public void valueAvailableTemplatesShouldBeReturned() throws Exception {
        assertEquals("", EMPTY.getValue());
    }

}