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
package com.codenvy.ide.client.elements;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.codenvy.ide.client.elements.RootElement.ELEMENT_NAME;
import static com.codenvy.ide.client.elements.RootElement.NAME;
import static com.codenvy.ide.client.elements.RootElement.ON_ERROR;
import static com.codenvy.ide.client.elements.RootElement.SERIALIZATION_NAME;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Dmitry Shnurenko
 */
public class RootElementTest extends AbstractElementTest<RootElement> {

    private static final String PATH_TO_EXAMPLES = "rootelement/serialize/";

    private static final String NAME_DEFAULT_VALUE     = "";
    private static final String NAME_VALUE             = "nameValue";
    private static final String ON_ERROR_DEFAULT_VALUE = "";
    private static final String ON_ERROR_VALUE         = "onErrorValue";

    @Mock
    private Branch branch;

    @Before
    public void setUp() throws Exception {
        when(branchProvider.get()).thenReturn(branch);

        entity = new RootElement(branchProvider, elementCreatorsManager);
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
        assertThat(entity.isRoot(), is(true));
    }

    @Override
    public void elementIconShouldBeInitializedWithDefaultValue() throws Exception {

    }

    @Override
    public void elementShouldBeNotEqualedForAnotherInstanceOfElement() throws Exception {
        RootElement otherElement = new RootElement(branchProvider, elementCreatorsManager);

        assertThat(entity.equals(otherElement), is(false));
    }

    @Override
    public void constructorPrepareOperationShouldBeDone() throws Exception {
        assertDefaultConfiguration();
    }

    private void assertDefaultConfiguration() {
        assertConfiguration(NAME_DEFAULT_VALUE, ON_ERROR_DEFAULT_VALUE);
    }

    private void assertConfiguration(String name, String onError) {
        assertThat(entity.getProperty(NAME), equalTo(name));
        assertThat(entity.getProperty(ON_ERROR), equalTo(onError));
    }

    @Test
    public void serializationShouldBeDoneWithDefaultParameters() throws Exception {
        entity.putProperty(NAME, NAME_VALUE);

        assertContentAndValue(PATH_TO_EXAMPLES + "DefaultValues", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithDefaultParameters() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "DefaultValues"));

        assertConfiguration(NAME_VALUE, ON_ERROR_DEFAULT_VALUE);
    }

    @Test
    public void serializationAttributesShouldBeDone() throws Exception {
        //we can't use gwt test for this use case, because it returns incorrect result
        // and we forced test protected method serializeAttributes()
        final String ATTRIBUTES_CONTENT = "name=\"nameValue\" onError=\"onErrorValue\"";

        entity.putProperty(NAME, NAME_VALUE);
        entity.putProperty(ON_ERROR, ON_ERROR_VALUE);

        assertThat(entity.serializeAttributes(), equalTo(ATTRIBUTES_CONTENT));
    }

    @Test
    public void deserializationAttributesShouldBeDone() throws Exception {
        //we can't use gwt test for this use case, because it returns incorrect result
        // and we forced test protected method serializeAttributes()
        entity.applyAttribute("onError", ON_ERROR_VALUE);

        assertThat(entity.getProperty(ON_ERROR), equalTo(ON_ERROR_VALUE));
    }

    @Test
    public void rootBranchShouldBeCreated() throws Exception {
        entity.deserialize(getContent(PATH_TO_EXAMPLES + "DefaultValues"));

        verify(branchProvider).get();
        verify(branch).setParent(entity);
    }

    @Test
    public void deserializationShouldBeDoneWithIncorrectParameters() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "IncorrectAttributes"));

        assertDefaultConfiguration();
    }
}