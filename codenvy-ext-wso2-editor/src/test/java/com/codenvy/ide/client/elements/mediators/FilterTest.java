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

import static com.codenvy.ide.client.elements.mediators.Filter.CONDITION_TYPE;
import static com.codenvy.ide.client.elements.mediators.Filter.ConditionType;
import static com.codenvy.ide.client.elements.mediators.Filter.ConditionType.SOURCE_AND_REGEX;
import static com.codenvy.ide.client.elements.mediators.Filter.ConditionType.XPATH;
import static com.codenvy.ide.client.elements.mediators.Filter.ELEMENT_NAME;
import static com.codenvy.ide.client.elements.mediators.Filter.ELSE_BRANCH_SERIALIZATION_NAME;
import static com.codenvy.ide.client.elements.mediators.Filter.ELSE_BRANCH_TITLE;
import static com.codenvy.ide.client.elements.mediators.Filter.IF_BRANCH_SERIALIZATION_NAME;
import static com.codenvy.ide.client.elements.mediators.Filter.IF_BRANCH_TITLE;
import static com.codenvy.ide.client.elements.mediators.Filter.NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.Filter.REGULAR_EXPRESSION;
import static com.codenvy.ide.client.elements.mediators.Filter.SERIALIZATION_NAME;
import static com.codenvy.ide.client.elements.mediators.Filter.SOURCE;
import static com.codenvy.ide.client.elements.mediators.Filter.SOURCE_NAMESPACE;
import static com.codenvy.ide.client.elements.mediators.Filter.XPATH_NAMESPACE;
import static com.codenvy.ide.client.elements.mediators.Filter.X_PATH;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Dmitry Shnurenko
 */
public class FilterTest extends AbstractElementTest<Filter> {

    private static final String PATH_TO_EXAMPLES = "mediators/filter/serialize/";

    private static final ConditionType CONDITION_TYPE_DEFAULT_VALUE     = SOURCE_AND_REGEX;
    private static final String        SOURCE_DEFAULT_VALUE             = "get-property('To')";
    private static final String        SOURCE_VALUE                     = "source";
    private static final String        REGULAR_EXPRESSION_DEFAULT_VALUE = "default_regex";
    private static final String        REGULAR_EXPRESSION_VALUE         = "regularExpression";
    private static final String        X_PATH_DEFAULT_VALUE             = "/default/xpath";
    private static final String        X_PATH_VALUE                     = "xpath";

    private static final List<NameSpace> SOURCE_NAMESPACE_DEFAULT_VALUE = Collections.emptyList();
    private static final List<NameSpace> XPATH_NAMESPACE_DEFAULT_VALUE  = Collections.emptyList();

    private static final String THEN_SERIALIZATION_CONTENT_WITH_ELEMENT = "<then>\n<log/>\n</then>\n";
    private static final String ELSE_SERIALIZATION_CONTENT_WITH_ELEMENT = "<else>\n<call/>\n</else>\n";
    private static final String THEN_SERIALIZATION_CONTENT_DEFAULT      = "<then/>\n";
    private static final String ELSE_SERIALIZATION_CONTENT_DEFAULT      = "<else/>\n";

    @Mock
    private Provider<NameSpace> nameSpaceProvider;
    @Mock
    private Branch              ifBranch;
    @Mock
    private Branch              thenBranch;
    @Mock
    private NameSpace           nameSpace;

    @Before
    public void setUp() throws Exception {
        when(resources.filter()).thenReturn(icon);

        when(branchProvider.get()).thenReturn(ifBranch, thenBranch);

        when(nameSpaceProvider.get()).thenReturn(nameSpace);
        when(nameSpace.toString()).thenReturn("xmlns:prefix=\"uri\"");

        entity = new Filter(resources, branchProvider, elementCreatorsManager, nameSpaceProvider);
    }

    @Test
    public void branchesShouldBeCreatedAndAddedToParent() throws Exception {
        verify(ifBranch).setParent(entity);
        verify(ifBranch).setTitle(IF_BRANCH_TITLE);
        verify(ifBranch).setName(IF_BRANCH_SERIALIZATION_NAME);

        verify(thenBranch).setParent(entity);
        verify(thenBranch).setTitle(ELSE_BRANCH_TITLE);
        verify(thenBranch).setName(ELSE_BRANCH_SERIALIZATION_NAME);
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
        assertThat(entity.needsToShowIconAndTitle(), is(true));
    }

    @Override
    public void elementIconShouldBeInitializedWithDefaultValue() throws Exception {
        verify(resources).filter();
        assertThat(entity.getIcon(), equalTo(icon));
    }

    @Override
    public void elementShouldBeNotEqualedForAnotherInstanceOfElement() throws Exception {
        Filter anotherElement = new Filter(resources, branchProvider, elementCreatorsManager, nameSpaceProvider);

        assertThat(entity.equals(anotherElement), is(false));
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
        assertConfiguration(CONDITION_TYPE_DEFAULT_VALUE,
                            SOURCE_DEFAULT_VALUE,
                            REGULAR_EXPRESSION_DEFAULT_VALUE,
                            X_PATH_DEFAULT_VALUE,
                            SOURCE_NAMESPACE_DEFAULT_VALUE,
                            XPATH_NAMESPACE_DEFAULT_VALUE);
    }

    private void assertConfiguration(ConditionType conditionType,
                                     String source,
                                     String regularExpression,
                                     String xPath,
                                     List<NameSpace> sourceNameSpace,
                                     List<NameSpace> xpathNameSpace) {

        assertThat(entity.getProperty(CONDITION_TYPE), equalTo(conditionType));
        assertThat(entity.getProperty(SOURCE), equalTo(source));
        assertThat(entity.getProperty(REGULAR_EXPRESSION), equalTo(regularExpression));
        assertThat(entity.getProperty(X_PATH), equalTo(xPath));
        assertThat(entity.getProperty(SOURCE_NAMESPACE), equalTo(sourceNameSpace));
        assertThat(entity.getProperty(XPATH_NAMESPACE), equalTo(xpathNameSpace));
    }

    @Test
    public void serializationShouldBeDoneWithDefaultParameters() throws Exception {
        prepareMocksContent(THEN_SERIALIZATION_CONTENT_DEFAULT, ELSE_SERIALIZATION_CONTENT_DEFAULT);

        assertContentAndValue(PATH_TO_EXAMPLES + "DefaultParameters", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithDefaultParameters() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "DefaultParameters"));

        assertDefaultConfiguration();
    }

    private void prepareMocksContent(String thenContent, String elseContent) {
        when(ifBranch.serialize()).thenReturn(thenContent);
        when(thenBranch.serialize()).thenReturn(elseContent);
    }

    @Test
    public void serializationShouldBeDoneWithInnerElements() throws Exception {
        prepareMocksContent(THEN_SERIALIZATION_CONTENT_WITH_ELEMENT, ELSE_SERIALIZATION_CONTENT_WITH_ELEMENT);

        entity.putProperty(SOURCE, SOURCE_VALUE);
        entity.putProperty(REGULAR_EXPRESSION, REGULAR_EXPRESSION_VALUE);

        assertContentAndValue(PATH_TO_EXAMPLES + "WithInnerElements", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithInnerElements() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "WithInnerElements"));

        assertConfiguration(CONDITION_TYPE_DEFAULT_VALUE,
                            SOURCE_VALUE,
                            REGULAR_EXPRESSION_VALUE,
                            X_PATH_DEFAULT_VALUE,
                            SOURCE_NAMESPACE_DEFAULT_VALUE,
                            XPATH_NAMESPACE_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenConditionTypeXpathWithNameSpaces() throws Exception {
        prepareMocksContent(THEN_SERIALIZATION_CONTENT_DEFAULT, ELSE_SERIALIZATION_CONTENT_DEFAULT);

        entity.putProperty(CONDITION_TYPE, XPATH);
        entity.putProperty(X_PATH, X_PATH_VALUE);
        entity.putProperty(XPATH_NAMESPACE, Arrays.asList(nameSpace));

        assertContentAndValue(PATH_TO_EXAMPLES + "ConditionTypeXpathWithNS", entity.serialize());
    }

    @Test
    public void desrializationShouldBeDoneWhenConditionTypeXpath() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "ConditionTypeXpathWithNS"));

        assertConfiguration(XPATH,
                            SOURCE_DEFAULT_VALUE,
                            REGULAR_EXPRESSION_DEFAULT_VALUE,
                            X_PATH_VALUE,
                            SOURCE_NAMESPACE_DEFAULT_VALUE,
                            XPATH_NAMESPACE_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenConditionTypeSourceAndRegexWithNameSpace() throws Exception {
        prepareMocksContent(THEN_SERIALIZATION_CONTENT_DEFAULT, ELSE_SERIALIZATION_CONTENT_DEFAULT);

        entity.putProperty(SOURCE, SOURCE_VALUE);
        entity.putProperty(REGULAR_EXPRESSION, REGULAR_EXPRESSION_VALUE);
        entity.putProperty(SOURCE_NAMESPACE, Arrays.asList(nameSpace));

        assertContentAndValue(PATH_TO_EXAMPLES + "ConditionTypeSourceAndRegexWithNS", entity.serialize());
    }

    @Test
    public void desrializationShouldBeDoneWhenConditionTypeSourceAndRegex() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "ConditionTypeSourceAndRegexWithNS"));

        assertConfiguration(CONDITION_TYPE_DEFAULT_VALUE,
                            SOURCE_VALUE,
                            REGULAR_EXPRESSION_VALUE,
                            X_PATH_DEFAULT_VALUE,
                            SOURCE_NAMESPACE_DEFAULT_VALUE,
                            XPATH_NAMESPACE_DEFAULT_VALUE);
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
    public void emptyStringShouldBeReturnedWhenConditionTypeIsNull() throws Exception {
        entity.putProperty(CONDITION_TYPE, null);

        assertThat(entity.serializeAttributes().isEmpty(), is(true));
    }

    @Test
    public void deserializationShouldBeDoneWithIncorrectValue() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "IncorrectParameters"));

        assertDefaultConfiguration();
    }

}