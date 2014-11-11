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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.Sequence.DYNAMIC_REFERENCE_TYPE;
import static com.codenvy.ide.client.elements.mediators.Sequence.ELEMENT_NAME;
import static com.codenvy.ide.client.elements.mediators.Sequence.NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.Sequence.REFERRING_TYPE;
import static com.codenvy.ide.client.elements.mediators.Sequence.ReferringType;
import static com.codenvy.ide.client.elements.mediators.Sequence.ReferringType.DYNAMIC;
import static com.codenvy.ide.client.elements.mediators.Sequence.ReferringType.STATIC;
import static com.codenvy.ide.client.elements.mediators.Sequence.SERIALIZATION_NAME;
import static com.codenvy.ide.client.elements.mediators.Sequence.STATIC_REFERENCE_TYPE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Dmitry Shnurenko
 */
public class SequenceTest extends AbstractElementTest<Sequence> {

    private static final String PATH_TO_EXAMPLES = "mediators/sequence/serialize/";

    private static final ReferringType   REFERRING_TYPE_DEFAULT_VALUE         = STATIC;
    private static final String          STATIC_REFERENCE_TYPE_DEFAULT_VALUE  = "Sequence";
    private static final String          DYNAMIC_REFERENCE_TYPE_DEFAULT_VALUE = "/default/expression";
    private static final String          DYNAMIC_REFERENCE_TYPE_VALUE         = "dynamicValue";
    private static final List<NameSpace> NAMESPACES_DEFAULT_VALUE             = Collections.emptyList();

    @Mock
    private Provider<NameSpace> nameSpaceProvider;
    @Mock
    private NameSpace           nameSpace;

    @Before
    public void setUp() throws Exception {
        when(resources.sequence()).thenReturn(icon);

        when(nameSpaceProvider.get()).thenReturn(nameSpace);
        when(nameSpace.toString()).thenReturn("xmlns:prefix=\"uri\"");

        entity = new Sequence(resources, branchProvider, elementCreatorsManager, nameSpaceProvider);
    }

    @Override
    public void elementTitleShouldBeInitializedWithDefaultValue() throws Exception {
        assertThat(entity.getTitle(), equalTo(ELEMENT_NAME));
    }

    @Override
    public void elementNameShouldBeDefinedWithDefaultValue() throws Exception {
        assertThat(entity.getElementName(), equalTo(ELEMENT_NAME));
    }

    @Override
    public void serializeNameShouldBeDefinedWithDefaultValue() throws Exception {
        assertThat(entity.getSerializationName(), equalTo(SERIALIZATION_NAME));
    }

    @Override
    public void possibleToChangeBranchAmountParamShouldBeInitializedWithDefaultValue() throws Exception {
        assertThat(entity.isPossibleToAddBranches(), is(false));
    }

    @Override
    public void needToShowTitleAndIconParamShouldBeInitializedWithDefaultValue() throws Exception {
        assertThat(entity.isRoot(), is(false));
    }

    @Override
    public void elementIconShouldBeInitializedWithDefaultValue() throws Exception {
        verify(resources).sequence();
        assertThat(entity.getIcon(), equalTo(icon));
    }

    @Override
    public void elementShouldBeNotEqualedForAnotherInstanceOfElement() throws Exception {
        Sequence anotherElement = new Sequence(resources, branchProvider, elementCreatorsManager, nameSpaceProvider);

        assertThat(entity.equals(anotherElement), is(false));
    }

    @Override
    public void constructorPrepareOperationShouldBeDone() throws Exception {
        assertDefaultConfiguration();
    }

    private void assertDefaultConfiguration() {
        assertConfiguration(REFERRING_TYPE_DEFAULT_VALUE,
                            STATIC_REFERENCE_TYPE_DEFAULT_VALUE,
                            DYNAMIC_REFERENCE_TYPE_DEFAULT_VALUE,
                            NAMESPACES_DEFAULT_VALUE);
    }

    private void assertConfiguration(ReferringType referringType,
                                     String staticReferenceValue,
                                     String dynamicReferenceValue,
                                     List<NameSpace> nameSpaces) {

        assertThat(entity.getProperty(REFERRING_TYPE), equalTo(referringType));
        assertThat(entity.getProperty(STATIC_REFERENCE_TYPE), equalTo(staticReferenceValue));
        assertThat(entity.getProperty(DYNAMIC_REFERENCE_TYPE), equalTo(dynamicReferenceValue));
        assertThat(entity.getProperty(NAMESPACES), equalTo(nameSpaces));
    }

    @Test
    public void serializationShouldBeDoneWithDefaultParameters() throws Exception {
        assertContentAndValue(PATH_TO_EXAMPLES + "DefaultParameters", entity.serialize());
    }

    @Test
    public void deserializationShouldBedoneWithDefaultParameters() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "DefaultParameters"));

        assertDefaultConfiguration();
    }

    @Test
    public void deserializationShouldBeDoneWithIncorrectParameters() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "IncorrectParameters"));

        assertDefaultConfiguration();
    }

    @Test
    public void serializationShouldBeDoneWhenReferringTypeDynamic() throws Exception {
        entity.putProperty(REFERRING_TYPE, DYNAMIC);
        entity.putProperty(DYNAMIC_REFERENCE_TYPE, DYNAMIC_REFERENCE_TYPE_VALUE);

        assertContentAndValue(PATH_TO_EXAMPLES + "ReferringTypeDynamic", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenReferringTypeDynamic() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "ReferringTypeDynamic"));

        assertConfiguration(DYNAMIC, STATIC_REFERENCE_TYPE_DEFAULT_VALUE, DYNAMIC_REFERENCE_TYPE_VALUE, NAMESPACES_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWithNameSpaces() throws Exception {
        entity.putProperty(REFERRING_TYPE, DYNAMIC);
        entity.putProperty(DYNAMIC_REFERENCE_TYPE, DYNAMIC_REFERENCE_TYPE_VALUE);
        entity.putProperty(NAMESPACES, Arrays.asList(nameSpace));

        assertContentAndValue(PATH_TO_EXAMPLES + "WithNameSpace", entity.serialize());
    }

    @Test
    public void deserializationNameSpacesShouldBeDone() throws Exception {
        List<NameSpace> nameSpaces = new ArrayList<>();

        entity.putProperty(NAMESPACES, nameSpaces);

        assertThat(nameSpaces.isEmpty(), is(true));

        entity.applyAttribute("xmlns:prefix", "uri");

        assertThat(nameSpaces.size(), is(1));
        assertThat(nameSpaces, hasItem(equalTo(nameSpace)));
    }

    @Test
    public void staticReferringTypeShouldBeReturned() throws Exception {
        assertThat(STATIC, equalTo(ReferringType.getItemByValue("Static")));
    }

    @Test
    public void dynamicReferringTypeShouldBeReturned() throws Exception {
        assertThat(DYNAMIC, equalTo(ReferringType.getItemByValue("Dynamic")));
    }

    @Test
    public void referringTypeValueShouldBeReturned() throws Exception {
        assertThat(DYNAMIC.getValue(), equalTo("Dynamic"));
    }

}