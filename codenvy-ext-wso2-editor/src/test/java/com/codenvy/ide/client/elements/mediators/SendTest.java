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
import com.codenvy.ide.client.elements.Element;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint;
import com.google.gwt.xml.client.Node;
import com.google.inject.Provider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.Send.BUILD_MESSAGE;
import static com.codenvy.ide.client.elements.mediators.Send.DESCRIPTION;
import static com.codenvy.ide.client.elements.mediators.Send.DYNAMIC_EXPRESSION;
import static com.codenvy.ide.client.elements.mediators.Send.ELEMENT_NAME;
import static com.codenvy.ide.client.elements.mediators.Send.NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.Send.SEQUENCE_TYPE;
import static com.codenvy.ide.client.elements.mediators.Send.SERIALIZATION_NAME;
import static com.codenvy.ide.client.elements.mediators.Send.SKIP_SERIALIZATION;
import static com.codenvy.ide.client.elements.mediators.Send.STATIC_EXPRESSION;
import static com.codenvy.ide.client.elements.mediators.Send.SequenceType;
import static com.codenvy.ide.client.elements.mediators.Send.SequenceType.DEFAULT;
import static com.codenvy.ide.client.elements.mediators.Send.SequenceType.DYNAMIC;
import static com.codenvy.ide.client.elements.mediators.Send.SequenceType.STATIC;
import static com.codenvy.ide.client.elements.mediators.Send.SequenceType.getItemByValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Dmitry Shnurenko
 */
public class SendTest extends AbstractElementTest<Send> {

    private static final String PATH_TO_EXAMPLES = "mediators/send/serialize/";

    private static final SequenceType    SEQUENCE_TYPE_DEFAULT_VALUE               = DEFAULT;
    private static final Boolean         SKIP_SERIALIZATION_DEFAULT_VALUE          = false;
    private static final Boolean         BUILD_MESSAGE_DEFAULT_VALUE               = false;
    private static final String          DESCRIPTION_DEFAULT_VALUE                 = "";
    private static final String          DESCRIPTION_VALUE                         = "description";
    private static final String          DYNAMIC_EXPRESSION_DEFAULT_VALUE          = "/default/xpath";
    private static final String          DYNAMIC_EXPRESSION_VALUE                  = "{dynamicExpression}";
    private static final String          DYNAMIC_EXPRESSION_VALUE_WITHOUT_BRACKETS = "dynamicExpression";
    private static final String          STATIC_EXPRESSION_DEFAULT_VALUE           = "/default/sequence";
    private static final String          STATIC_EXPRESSION_VALUE                   = "staticExpression";
    private static final List<NameSpace> NAMESPACES_DEFAULT_VALUE                  = Collections.emptyList();

    private static final String ENDPOINT_SERIALIZE_CONTENT = "<address uri=\"http://www.example.org/service\"/>";

    @Mock
    private Provider<AddressEndpoint> addressEndpointProvider;
    @Mock
    private Provider<NameSpace>       nameSpaceProvider;
    @Mock
    private NameSpace                 nameSpace;
    @Mock
    private Branch                    branch;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private Node                      node;

    @Before
    public void setUp() throws Exception {
        when(nameSpaceProvider.get()).thenReturn(nameSpace);
        when(nameSpace.toString()).thenReturn("xmlns:prefix=\"uri\"");

        when(resources.send()).thenReturn(icon);

        when(branchProvider.get()).thenReturn(branch);

        entity = new Send(resources, branchProvider, elementCreatorsManager, nameSpaceProvider);
    }

    @Test
    public void branchShouldBeCreatedAndAddedToParent() throws Exception {
        verify(branchProvider).get();
        verify(branch).setParent(entity);
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
        verify(resources).send();
        assertThat(entity.getIcon(), equalTo(icon));
    }

    @Override
    public void elementShouldBeNotEqualedForAnotherInstanceOfElement() throws Exception {
        Send anotherElement = new Send(resources, branchProvider, elementCreatorsManager, nameSpaceProvider);

        assertThat(entity.equals(anotherElement), is(false));
    }

    @Override
    public void branchesAmountShouldBeNotChangedWhenNewAmountIsLessThanZero() throws Exception {
        branchesAmountShouldBeNotChangedWhenNewAmountIsLessThanZero(1);
    }

    @Override
    public void branchesAmountShouldBeNotChangedWhenSizeTheSame() throws Exception {
        branchesAmountShouldBeNotChangedWhenSizeTheSame(1);
    }

    @Override
    public void branchesAmountShouldBeChanged() throws Exception {
        branchesAmountShouldBeChanged(1);
    }

    @Override
    public void addingBranchManuallyShouldBeImpossible() throws Exception {
        addingBranchManuallyShouldBeImpossible(1);
    }

    @Override
    public void removingBranchManuallyShouldBeImpossible() throws Exception {
        removingBranchManuallyShouldBeImpossible(1);
    }

    @Override
    public void constructorPrepareOperationShouldBeDone() throws Exception {
        assertDefaultConfiguration();
    }

    private void assertDefaultConfiguration() throws Exception {
        assertConfiguration(SEQUENCE_TYPE_DEFAULT_VALUE,
                            SKIP_SERIALIZATION_DEFAULT_VALUE,
                            BUILD_MESSAGE_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            DYNAMIC_EXPRESSION_DEFAULT_VALUE,
                            STATIC_EXPRESSION_DEFAULT_VALUE,
                            NAMESPACES_DEFAULT_VALUE);
    }

    private void assertConfiguration(SequenceType type,
                                     boolean skipSerialization,
                                     boolean buildMessage,
                                     String description,
                                     String dynamicExpression,
                                     String staticExpression,
                                     List<NameSpace> nameSpaces) {

        assertThat(entity.getProperty(SEQUENCE_TYPE), equalTo(type));
        assertThat(entity.getProperty(SKIP_SERIALIZATION), is(skipSerialization));
        assertThat(entity.getProperty(BUILD_MESSAGE), is(buildMessage));
        assertThat(entity.getProperty(DESCRIPTION), equalTo(description));
        assertThat(entity.getProperty(DYNAMIC_EXPRESSION), equalTo(dynamicExpression));
        assertThat(entity.getProperty(STATIC_EXPRESSION), equalTo(staticExpression));
        assertThat(entity.getProperty(NAMESPACES), equalTo(nameSpaces));
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
    public void serializationShouldBeDoneWhenSkipSerializationIsTrue() throws Exception {
        entity.putProperty(SKIP_SERIALIZATION, true);

        assertThat(entity.serialize().isEmpty(), is(true));
    }

    @Test
    public void serializationShouldBeDoneWithAllParameters() throws Exception {
        entity.putProperty(BUILD_MESSAGE, true);
        entity.putProperty(DYNAMIC_EXPRESSION, DYNAMIC_EXPRESSION_VALUE);
        entity.putProperty(SEQUENCE_TYPE, DYNAMIC);
        entity.putProperty(DESCRIPTION, DESCRIPTION_VALUE);
        entity.putProperty(NAMESPACES, Arrays.asList(nameSpace));

        assertContentAndValue(PATH_TO_EXAMPLES + "AllParameters", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithAllParameters() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "AllParameters"));

        assertConfiguration(DYNAMIC,
                            SKIP_SERIALIZATION_DEFAULT_VALUE,
                            true,
                            DESCRIPTION_VALUE,
                            DYNAMIC_EXPRESSION_VALUE_WITHOUT_BRACKETS,
                            STATIC_EXPRESSION_DEFAULT_VALUE,
                            NAMESPACES_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenBuildMessageIsTrue() throws Exception {
        entity.putProperty(BUILD_MESSAGE, true);

        assertContentAndValue(PATH_TO_EXAMPLES + "BuildMessageIsTrue", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenBuildMessageIsTrue() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "BuildMessageIsTrue"));

        assertConfiguration(SEQUENCE_TYPE_DEFAULT_VALUE,
                            SKIP_SERIALIZATION_DEFAULT_VALUE,
                            true,
                            DESCRIPTION_DEFAULT_VALUE,
                            DYNAMIC_EXPRESSION_DEFAULT_VALUE,
                            STATIC_EXPRESSION_DEFAULT_VALUE,
                            NAMESPACES_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenSequenceTypeIsDynamicWithNameSpace() throws Exception {
        entity.putProperty(SEQUENCE_TYPE, DYNAMIC);
        entity.putProperty(DYNAMIC_EXPRESSION, DYNAMIC_EXPRESSION_VALUE);
        entity.putProperty(NAMESPACES, Arrays.asList(nameSpace));

        assertContentAndValue(PATH_TO_EXAMPLES + "SequenceTypeDynamicWithNS", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenSequenceTypeIsDynamicWithNameSpace() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "SequenceTypeDynamicWithNS"));

        assertConfiguration(DYNAMIC,
                            SKIP_SERIALIZATION_DEFAULT_VALUE,
                            BUILD_MESSAGE_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            DYNAMIC_EXPRESSION_VALUE_WITHOUT_BRACKETS,
                            STATIC_EXPRESSION_DEFAULT_VALUE,
                            NAMESPACES_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenSequenceTypeIsStatic() throws Exception {
        entity.putProperty(SEQUENCE_TYPE, STATIC);
        entity.putProperty(STATIC_EXPRESSION, STATIC_EXPRESSION_VALUE);

        assertContentAndValue(PATH_TO_EXAMPLES + "SequenceTypeStatic", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenSequenceTypeIsStatic() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "SequenceTypeStatic"));

        assertConfiguration(STATIC,
                            SKIP_SERIALIZATION_DEFAULT_VALUE,
                            BUILD_MESSAGE_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            DYNAMIC_EXPRESSION_DEFAULT_VALUE,
                            STATIC_EXPRESSION_VALUE,
                            NAMESPACES_DEFAULT_VALUE);
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

        assertConfiguration(SEQUENCE_TYPE_DEFAULT_VALUE,
                            SKIP_SERIALIZATION_DEFAULT_VALUE,
                            BUILD_MESSAGE_DEFAULT_VALUE,
                            DESCRIPTION_VALUE,
                            DYNAMIC_EXPRESSION_DEFAULT_VALUE,
                            STATIC_EXPRESSION_DEFAULT_VALUE,
                            NAMESPACES_DEFAULT_VALUE);
    }

    @Test
    public void deserializationShouldBeDoneWithIncorrectParameters() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "IncorrectParameters"));

        assertDefaultConfiguration();
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
    public void serializationShouldBeDoneWithEndPoint() throws Exception {
        when(branch.hasElements()).thenReturn(true);
        when(branch.serialize()).thenReturn(ENDPOINT_SERIALIZE_CONTENT);

        assertContentAndValue(PATH_TO_EXAMPLES + "WithAddressEP", entity.serialize());

        verify(branch).serialize();
    }

    @Test
    public void deserializationShouldBeDoneWithEndPoint() throws Exception {
        AddressEndpoint addressEndpoint = mock(AddressEndpoint.class);

        when(elementCreatorsManager.getProviderBySerializeName(eq("address"))).then(new Answer<Object>() {
            @Override
            public Provider<? extends Element> answer(InvocationOnMock invocation) throws Throwable {
                // we must use do answer for returning needed provider. in other case it is impossible.
                return addressEndpointProvider;
            }
        });
        when(addressEndpointProvider.get()).thenReturn(addressEndpoint);

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "WithAddressEP"));

        verify(addressEndpoint).deserialize(any(Node.class));
        verify(branch).addElement(addressEndpoint);
    }

    @Test
    public void serializationShouldBeDoneWhenSequenceTypeIsNull() throws Exception {
        entity.putProperty(SEQUENCE_TYPE, null);

        assertContentAndValue(PATH_TO_EXAMPLES + "DefaultParameters", entity.serialize());
    }

    @Test
    public void branchShouldNotBeCreatedWhenNodeHasNotChildren() {
        when(node.hasChildNodes()).thenReturn(false);
        //noinspection unchecked
        reset(branchProvider);

        entity.applyProperty(node);

        verify(branchProvider, never()).get();
    }

    @Test
    public void dynamicSequenceTypeShouldBeReturned() throws Exception {
        assertThat(DYNAMIC, equalTo(getItemByValue("Dynamic")));
    }

    @Test
    public void staticSequenceTypeShouldBeReturned() throws Exception {
        assertThat(STATIC, equalTo(getItemByValue("Static")));
    }

    @Test
    public void defaultSequenceTypeShouldBeReturned() throws Exception {
        assertThat(DEFAULT, equalTo(getItemByValue("Default")));
    }

    @Test
    public void sequenceTypeValueShouldBeReturned() throws Exception {
        assertThat("Dynamic", equalTo(DYNAMIC.getValue()));
    }
}