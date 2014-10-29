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

import com.codenvy.ide.client.elements.AbstractElementTest;
import com.google.gwt.xml.client.Node;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.codenvy.ide.client.elements.mediators.enrich.Enrich.DESCRIPTION;
import static com.codenvy.ide.client.elements.mediators.enrich.Enrich.SOURCE;
import static com.codenvy.ide.client.elements.mediators.enrich.Enrich.TARGET;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Dmitry Shnurenko
 */
public class EnrichTest extends AbstractElementTest<Enrich> {

    private static final String PATH_TO_EXAMPLES = "mediators/enrich/serialize/";

    private static final String DESCRIPTION_DEFAULT_VALUE = "";
    private static final String DESCRIPTION_VALUE         = "description";

    private static final String SOURCE_SERIALIZATION_CONTENT = "<source xpath=\"/default/xpath\"/>\n";
    private static final String TARGET_SERIALIZATION_CONTENT = "<target xpath=\"/default/xpath\"/>\n";

    @Mock
    private Target target;
    @Mock
    private Source source;
    @Mock
    private Node   node;

    @Before
    public void setUp() {
        when(resources.enrich()).thenReturn(icon);

        entity = new Enrich(resources, branchProvider, elementCreatorsManager, source, target);
    }

    @Override
    public void elementTitleShouldBeInitializedWithDefaultValue() throws Exception {
        assertEquals(Enrich.ELEMENT_NAME, entity.getTitle());
    }

    @Override
    public void elementNameShouldBeDefinedWithDefaultValue() throws Exception {
        assertEquals(Enrich.ELEMENT_NAME, entity.getElementName());
    }

    @Override
    public void serializeNameShouldBeDefinedWithDefaultValue() throws Exception {
        assertEquals(Enrich.SERIALIZATION_NAME, entity.getSerializationName());
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
        verify(resources).enrich();
        assertEquals(icon, entity.getIcon());
    }

    @Override
    public void elementShouldBeNotEqualedForAnotherInstanceOfElement() throws Exception {
        Enrich otherElement = new Enrich(resources, branchProvider, elementCreatorsManager, source, target);
        assertFalse(entity.equals(otherElement));
    }

    @Override
    public void constructorPrepareOperationShouldBeDone() throws Exception {
        assertDefaultConfiguration();
    }

    private void assertDefaultConfiguration() {
        assertConfiguration(DESCRIPTION_DEFAULT_VALUE);
    }

    private void assertConfiguration(String description) {
        assertEquals(description, entity.getProperty(DESCRIPTION));
    }

    @Test
    public void serializationShouldBeDoneWithDefaultParameters() throws Exception {
        when(source.serialize()).thenReturn(SOURCE_SERIALIZATION_CONTENT);
        when(target.serialize()).thenReturn(TARGET_SERIALIZATION_CONTENT);

        assertContentAndValue(PATH_TO_EXAMPLES + "DefaultParameters", entity.serialize());

        verify(source).serialize();
        verify(target).serialize();
    }

    @Test
    public void deserializationShouldBeDoneWithDefaultParameters() throws Exception {
        entity.deserialize(getContent(PATH_TO_EXAMPLES + "DefaultParameters"));

        assertDefaultConfiguration();
    }

    @Test
    public void emptyStringShouldBeReturnedWhenSourceIsNull() throws Exception {
        entity.putProperty(SOURCE, null);

        assertEquals("", entity.serializeProperties());
    }

    @Test
    public void emptyStringShouldBeReturnedWhenTargetIsNull() throws Exception {
        entity.putProperty(TARGET, null);

        assertEquals("", entity.serializeProperties());
    }

    @Test
    public void serializationShouldBeDoneWithDescription() throws Exception {
        when(source.serialize()).thenReturn(SOURCE_SERIALIZATION_CONTENT);
        when(target.serialize()).thenReturn(TARGET_SERIALIZATION_CONTENT);

        entity.putProperty(DESCRIPTION, DESCRIPTION_VALUE);

        assertContentAndValue(PATH_TO_EXAMPLES + "Description", entity.serialize());

        verify(source).serialize();
        verify(target).serialize();
    }

    @Test
    public void methodShouldBeReturnedWhenSourceIsNull() throws Exception {
        entity.putProperty(SOURCE, null);

        entity.deserialize(node);

        verify(node, never()).getAttributes();
    }

    @Test
    public void methodShouldBeReturnedWhenTargetIsNull() throws Exception {
        entity.putProperty(TARGET, null);

        entity.deserialize(node);

        verify(node, never()).getAttributes();
    }

    @Test
    public void deserializationShouldBeDoneWithDescription() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "Description"));

        assertConfiguration(DESCRIPTION_VALUE);

        verify(target).deserialize(any(Node.class));
        verify(source).applyProperty(any(Node.class));
    }

    @Test
    public void deserializationShouldBeDoneWithIncorrectNode() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "IncorrectTag"));

        assertDefaultConfiguration();
    }

}