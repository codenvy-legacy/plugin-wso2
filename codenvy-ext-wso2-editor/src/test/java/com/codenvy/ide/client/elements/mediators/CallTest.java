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
import com.codenvy.ide.client.managers.ElementCreatorsManager;
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

import static com.codenvy.ide.client.elements.mediators.Call.DESCRIPTION;
import static com.codenvy.ide.client.elements.mediators.Call.ELEMENT_NAME;
import static com.codenvy.ide.client.elements.mediators.Call.ENDPOINT_TYPE;
import static com.codenvy.ide.client.elements.mediators.Call.EndpointType;
import static com.codenvy.ide.client.elements.mediators.Call.EndpointType.INLINE;
import static com.codenvy.ide.client.elements.mediators.Call.EndpointType.NONE;
import static com.codenvy.ide.client.elements.mediators.Call.EndpointType.REGISTRYKEY;
import static com.codenvy.ide.client.elements.mediators.Call.EndpointType.XPATH;
import static com.codenvy.ide.client.elements.mediators.Call.NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.Call.REGISTRY_KEY;
import static com.codenvy.ide.client.elements.mediators.Call.SERIALIZATION_NAME;
import static com.codenvy.ide.client.elements.mediators.Call.X_PATH;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CallTest extends AbstractElementTest<Call> {

    private static final String PATH_TO_EXAMPLES = "mediators/call/serialize/";

    private static final String          REGISTER_KEY_DEFAULT_VALUE  = "/default/key";
    private static final String          REGISTER_KEY                = "key";
    private static final EndpointType    ENDPOINT_TYPE_DEFAULT_VALUE = INLINE;
    private static final String          X_PATH_DEFAULT_VALUE        = "/default/expression";
    private static final String          X_PATH_VALUE                = "xpath";
    private static final String          DESCRIPTION_DEFAULT_VALUE   = "";
    private static final String          DESCRIPTION_VALUE           = "description";
    private static final List<NameSpace> NAMESPACES_DEFAULT_VALUE    = Collections.emptyList();

    private static final String ENDPOINT_SERIALIZE_CONTENT = "<address uri=\"http://www.example.org/service\"/>";

    @Mock
    private Provider<NameSpace>         nameSpaceProvider;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private Branch                      branch;
    @Mock
    private NameSpace                   nameSpace;
    @Mock
    private Provider<? extends Element> addressEndpointProvider;
    @Mock
    private ElementCreatorsManager      creatorsManager;

    @Before
    public void setUp() throws Exception {
        when(nameSpaceProvider.get()).thenReturn(nameSpace);

        when(nameSpace.toString()).thenReturn("xmlns:prefix=\"uri\"");

        when(resources.call()).thenReturn(icon);
        when(branchProvider.get()).thenReturn(branch);

        entity = new Call(resources, branchProvider, elementCreatorsManager, nameSpaceProvider);
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
        verify(resources).call();
        assertThat(entity.getIcon(), equalTo(icon));
    }

    @Override
    public void elementShouldBeNotEqualedForAnotherInstanceOfElement() throws Exception {
        Call anotherCall = new Call(resources, branchProvider, elementCreatorsManager, nameSpaceProvider);

        assertThat(entity.equals(anotherCall), is(false));
    }

    @Override
    public void branchesAmountShouldBeNotChangedWhenNewAmountIsLessThanZero() throws Exception {
        branchesAmountShouldBeNotChangedWhenNewAmountIsLessThanZero(1);
    }

    @Override
    public void removingBranchManuallyShouldBeImpossible() throws Exception {
        removingBranchManuallyShouldBeImpossible(1);
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
    public void constructorPrepareOperationShouldBeDone() throws Exception {
        assertDefaultConfiguration();
    }

    private void assertDefaultConfiguration() throws Exception {
        assertConfiguration(REGISTER_KEY_DEFAULT_VALUE,
                            ENDPOINT_TYPE_DEFAULT_VALUE,
                            X_PATH_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            NAMESPACES_DEFAULT_VALUE);
    }

    private void assertConfiguration(String registerKey,
                                     EndpointType endpointType,
                                     String xPath,
                                     String description,
                                     List<NameSpace> nameSpaces) throws Exception {

        assertThat(entity.getProperty(REGISTRY_KEY), equalTo(registerKey));
        assertThat(entity.getProperty(ENDPOINT_TYPE), equalTo(endpointType));
        assertThat(entity.getProperty(X_PATH), equalTo(xPath));
        assertThat(entity.getProperty(DESCRIPTION), equalTo(description));
        assertThat(entity.getProperty(NAMESPACES), equalTo(nameSpaces));
    }

    @Test
    public void serializationShouldBeDoneWithDefaultParameters() throws Exception {
        when(branch.getElements().isEmpty()).thenReturn(true);

        assertContentAndValue(PATH_TO_EXAMPLES + "DefaultParameters", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithDefaultParameters() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "DefaultParameters"));

        assertConfiguration(REGISTER_KEY_DEFAULT_VALUE, NONE, X_PATH_DEFAULT_VALUE, DESCRIPTION_DEFAULT_VALUE, NAMESPACES_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWithEndpoint() throws Exception {
        when(branch.serialize()).thenReturn(ENDPOINT_SERIALIZE_CONTENT);

        assertContentAndValue(PATH_TO_EXAMPLES + "WithAddressEP", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithEndpoint() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "WithAddressEP"));

        assertDefaultConfiguration();
    }

    @Test
    public void serializationShouldBeDoneWithDescription() throws Exception {
        when(branch.getElements().isEmpty()).thenReturn(true);
        entity.putProperty(DESCRIPTION, DESCRIPTION_VALUE);

        assertContentAndValue(PATH_TO_EXAMPLES + "WithDescription", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithDescription() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "WithDescription"));

        assertConfiguration(REGISTER_KEY_DEFAULT_VALUE, NONE, X_PATH_DEFAULT_VALUE, DESCRIPTION_VALUE, NAMESPACES_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenEndPointTypeRegisterKey() throws Exception {
        entity.putProperty(ENDPOINT_TYPE, REGISTRYKEY);
        entity.putProperty(REGISTRY_KEY, REGISTER_KEY);

        assertContentAndValue(PATH_TO_EXAMPLES + "EndpointTypeRegisterKey", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenEndpointTypeRegisterKey() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "EndpointTypeRegisterKey"));

        assertConfiguration(REGISTER_KEY,
                            REGISTRYKEY,
                            X_PATH_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            NAMESPACES_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenEndPointTypeXpath() throws Exception {
        entity.putProperty(ENDPOINT_TYPE, XPATH);
        entity.putProperty(X_PATH, X_PATH_VALUE);

        assertContentAndValue(PATH_TO_EXAMPLES + "EndpointTypeXpath", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenEndpointTypeXpath() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "EndpointTypeXpath"));

        assertConfiguration(REGISTER_KEY_DEFAULT_VALUE, XPATH, X_PATH_VALUE, DESCRIPTION_DEFAULT_VALUE, NAMESPACES_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenEndpointTypeIsNone() throws Exception {
        entity.putProperty(ENDPOINT_TYPE, NONE);

        assertContentAndValue(PATH_TO_EXAMPLES + "DefaultParameters", entity.serialize());
    }

    @Test
    public void serializationShouldBeDoneWithNameSpaces() throws Exception {
        entity.putProperty(ENDPOINT_TYPE, XPATH);
        entity.putProperty(NAMESPACES, Arrays.asList(nameSpace));

        assertContentAndValue(PATH_TO_EXAMPLES + "TypeXpathWithNameSpaces", entity.serialize());
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
    public void emptyStringShouldBeReturnedWhenEndpointTypeIsNull() throws Exception {
        entity.putProperty(ENDPOINT_TYPE, null);

        assertThat(entity.serialize().isEmpty(), is(true));
    }

    @Test
    public void deserilizationShouldNotBeDoneWithIncorrectParameters() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "IncorrectProperty"));

        assertDefaultConfiguration();
    }

    @Test
    public void innerElementDeserializationShouldBeDone() throws Exception {
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

}