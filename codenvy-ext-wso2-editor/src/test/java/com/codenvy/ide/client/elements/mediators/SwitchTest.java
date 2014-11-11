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
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.NameSpace;
import com.google.inject.Provider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.Switch.CASE_SERIALIZATION_NAME;
import static com.codenvy.ide.client.elements.mediators.Switch.CASE_TITLE;
import static com.codenvy.ide.client.elements.mediators.Switch.DEFAULT_CASE_TITLE;
import static com.codenvy.ide.client.elements.mediators.Switch.DEFAULT_SERIALIZATION_NAME;
import static com.codenvy.ide.client.elements.mediators.Switch.ELEMENT_NAME;
import static com.codenvy.ide.client.elements.mediators.Switch.NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.Switch.REGEXP_ATTRIBUTE_DEFAULT_VALUE;
import static com.codenvy.ide.client.elements.mediators.Switch.REGEXP_ATTRIBUTE_NAME;
import static com.codenvy.ide.client.elements.mediators.Switch.SERIALIZATION_NAME;
import static com.codenvy.ide.client.elements.mediators.Switch.SOURCE_XPATH;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Dmitry Shnurenko
 */
public class SwitchTest extends AbstractElementTest<Switch> {

    private static final String PATH_TO_EXAMPLES = "mediators/switch/serialize/";

    private static final String          SOURCE_XPATH_DEFAULT_VALUE = "default/xpath";
    private static final String          SOURCE_X_PATH_VALUE        = "xpathValue";
    private static final List<NameSpace> NAMESPACES_DEFAULT_VALUE   = Collections.emptyList();

    private static final String FIRST_SERIALIZATION_CONTENT_WITH_ELEMENT   = "<case regex=\".*+\">\n<log/>\n</case>\n";
    private static final String DEFAULT_SERIALIZATION_CONTENT_WITH_ELEMENT = "<default>\n<call/>\n</default>\n";
    private static final String FIRST_SERIALIZATION_CONTENT_DEFAULT        = "<case regex=\".*+\"/>\n";
    private static final String DEFAULT_SERIALIZATION_CONTENT_DEFAULT      = "<default/>\n";

    @Mock
    private Provider<NameSpace> nameSpaceProvider;
    @Mock
    private NameSpace           nameSpace;
    @Mock
    private Branch              firstBranch;
    @Mock
    private Branch              defaultBranch;

    @Before
    public void setUp() throws Exception {
        when(nameSpaceProvider.get()).thenReturn(nameSpace);
        when(nameSpace.toString()).thenReturn("xmlns:prefix=\"uri\"");

        when(resources.switchMediator()).thenReturn(icon);

        when(branchProvider.get()).thenReturn(firstBranch, defaultBranch, branch);

        entity = new Switch(resources, branchProvider, elementCreatorsManager, nameSpaceProvider);
    }

    @Test
    public void branchesShouldBeCreatedAndAddedToParent() throws Exception {
        verify(branchProvider, times(2)).get();

        verify(firstBranch).setParent(entity);
        verify(firstBranch).setTitle(CASE_TITLE);
        verify(firstBranch).setName(CASE_SERIALIZATION_NAME);
        verify(firstBranch).addAttribute(REGEXP_ATTRIBUTE_NAME, REGEXP_ATTRIBUTE_DEFAULT_VALUE);

        verify(defaultBranch).setParent(entity);
        verify(defaultBranch).setTitle(DEFAULT_CASE_TITLE);
        verify(defaultBranch).setName(DEFAULT_SERIALIZATION_NAME);
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
        assertThat(entity.isPossibleToAddBranches(), is(true));
    }

    @Override
    public void needToShowTitleAndIconParamShouldBeInitializedWithDefaultValue() throws Exception {
        assertThat(entity.isRoot(), is(false));
    }

    @Override
    public void elementIconShouldBeInitializedWithDefaultValue() throws Exception {
        verify(resources).switchMediator();
        assertThat(entity.getIcon(), equalTo(icon));
    }

    @Override
    public void elementShouldBeNotEqualedForAnotherInstanceOfElement() throws Exception {
        Switch otherElement = new Switch(resources, branchProvider, elementCreatorsManager, nameSpaceProvider);

        assertThat(entity.equals(otherElement), is(false));
    }

    @Override
    public void branchesAmountShouldBeNotChangedWhenNewAmountIsLessThanZero() throws Exception {
        branchesAmountShouldBeNotChangedWhenNewAmountIsLessThanZero(2);
    }

    @Override
    public void branchesAmountShouldBeNotChangedWhenSizeTheSame() throws Exception {
        branchesAmountShouldBeNotChangedWhenSizeTheSame(2);
    }

    @Override
    public void branchesAmountShouldBeChanged() throws Exception {
        branchesAmountShouldBeChanged(2);
    }

    @Override
    public void addingBranchManuallyShouldBeImpossible() throws Exception {
        addingBranchManuallyShouldBeImpossible(2);
    }

    @Override
    public void removingBranchManuallyShouldBeImpossible() throws Exception {
        removingBranchManuallyShouldBeImpossible(2);
    }

    @Override
    public void constructorPrepareOperationShouldBeDone() throws Exception {
        assertDefaultConfiguration();
    }

    private void assertDefaultConfiguration() {
        assertConfiguration(SOURCE_XPATH_DEFAULT_VALUE, NAMESPACES_DEFAULT_VALUE);
    }

    private void assertConfiguration(String sourceXpath, List<NameSpace> nameSpaces) {
        assertThat(entity.getProperty(SOURCE_XPATH), equalTo(sourceXpath));
        assertThat(entity.getProperty(NAMESPACES), equalTo(nameSpaces));
    }

    @Test
    public void serializationShouldBeDoneWithDefaultParameters() throws Exception {
        prepareMocksContent(FIRST_SERIALIZATION_CONTENT_DEFAULT, DEFAULT_SERIALIZATION_CONTENT_DEFAULT);

        assertContentAndValue(PATH_TO_EXAMPLES + "DefaultParameters", entity.serialize());
    }

    private void prepareMocksContent(String firstContent, String defaultContent) {
        when(firstBranch.serialize()).thenReturn(firstContent);
        when(defaultBranch.serialize()).thenReturn(defaultContent);
    }

    @Test
    public void deserializationShouldBeDoneWithDefaultParameters() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "DefaultParameters"));

        assertDefaultConfiguration();
    }

    @Test
    public void serializationShouldBeDoneWithInnerElements() throws Exception {
        prepareMocksContent(FIRST_SERIALIZATION_CONTENT_WITH_ELEMENT, DEFAULT_SERIALIZATION_CONTENT_WITH_ELEMENT);

        entity.putProperty(SOURCE_XPATH, SOURCE_X_PATH_VALUE);
        entity.putProperty(NAMESPACES, Arrays.asList(nameSpace));

        assertContentAndValue(PATH_TO_EXAMPLES + "WithInnerElements", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithInnerElements() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "WithInnerElements"));

        assertConfiguration(SOURCE_X_PATH_VALUE, NAMESPACES_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWithUnDefaultCountBranches() throws Exception {
        Branch branch = mock(Branch.class);

        when(branchProvider.get()).thenReturn(firstBranch, branch, defaultBranch);

        prepareMocksContent(FIRST_SERIALIZATION_CONTENT_DEFAULT, DEFAULT_SERIALIZATION_CONTENT_DEFAULT);
        when(branch.serialize()).thenReturn(FIRST_SERIALIZATION_CONTENT_DEFAULT);

        entity.setBranchesAmount(3);

        assertContentAndValue(PATH_TO_EXAMPLES + "UnDefaultBranchCount", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithNameSpaces() throws Exception {
        List<NameSpace> nameSpaces = new ArrayList<>();

        entity.putProperty(NAMESPACES, nameSpaces);

        assertThat(nameSpaces.isEmpty(), is(true));

        entity.applyAttribute("xmlns:prefix", "uri");

        assertThat(nameSpaces.size(), is(1));
        assertThat(nameSpaces, hasItem(equalTo(nameSpace)));
    }

    @Test
    public void deserializationShouldBeDoneWithIncorrectParameters() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "IncorrectParameters"));

        assertDefaultConfiguration();
    }

}