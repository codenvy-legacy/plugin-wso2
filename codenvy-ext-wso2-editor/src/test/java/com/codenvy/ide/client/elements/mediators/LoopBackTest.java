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

import org.junit.Before;
import org.junit.Test;

import static com.codenvy.ide.client.elements.mediators.LoopBack.ELEMENT_NAME;
import static com.codenvy.ide.client.elements.mediators.LoopBack.SERIALIZATION_NAME;
import static com.codenvy.ide.client.elements.mediators.Respond.DESCRIPTION;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Dmitry Shnurenko
 */
public class LoopBackTest extends AbstractElementTest<LoopBack> {

    private static final String PATH_TO_EXAMPLES = "mediators/loopBack/serialize/";

    private static final String DESCRIPTION_DEFAULT_VALUE = "";
    private static final String DESCRIPTION_VALUE         = "description";

    @Before
    public void setUp() {
        when(resources.loopBack()).thenReturn(icon);

        entity = new LoopBack(resources, branchProvider, elementCreatorsManager);
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
        verify(resources).loopBack();
        assertThat(entity.getIcon(), equalTo(icon));
    }

    @Override
    public void elementShouldBeNotEqualedForAnotherInstanceOfElement() throws Exception {
        LoopBack otherElement = new LoopBack(resources, branchProvider, elementCreatorsManager);

        assertThat(entity.equals(otherElement), is(false));
    }

    @Override
    public void constructorPrepareOperationShouldBeDone() throws Exception {
        assertDefaultConfiguration();
    }

    private void assertDefaultConfiguration() {
        assertConfiguration(DESCRIPTION_DEFAULT_VALUE);
    }

    private void assertConfiguration(String description) {
        assertThat(entity.getProperty(DESCRIPTION), equalTo(description));
    }

    @Test
    public void serializationShouldBeDoneWithDefaultParameters() throws Exception {
        assertContentAndValue(PATH_TO_EXAMPLES + "DefaultParameters", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithDefaultParameters() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "DefaultParameters"));

        assertDefaultConfiguration();
    }

    @Test
    public void serializationShouldBeDoneWithDescription() throws Exception {
        entity.putProperty(DESCRIPTION, DESCRIPTION_VALUE);

        assertContentAndValue(PATH_TO_EXAMPLES + "WithDescription", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithDescription() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "WithDescription"));

        assertConfiguration(DESCRIPTION_VALUE);
    }

    @Test
    public void deserializationShouldBeDoneWithIncorrectParameters() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "IncorrectParameter"));

        assertDefaultConfiguration();
    }

}