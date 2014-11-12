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

import com.codenvy.ide.client.SonarAwareGwtRunner;
import com.codenvy.ide.client.common.ContentFormatter;
import com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;
import com.google.inject.Provider;
import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTestWithMockito;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@GwtModule("com.codenvy.ide.WSO2Editor")
@RunWith(SonarAwareGwtRunner.class)
public class BranchTest extends GwtTestWithMockito {

    private static final String PATH_TO_EXAMPLES = "branch/serialize/DeserializeContent";

    private static final String SOME_TEXT             = "someText";
    private static final String ATTRIBUTE_NAME        = "name";
    private static final String SERIALIZATION_CONTENT = "<someText name=\"someText\" >someText</someText>";

    @Mock
    private ElementCreatorsManager    elementCreatorsManager;
    @Mock
    private Provider<AddressEndpoint> addressEndpointProvider;
    @Mock
    private AbstractElement           element;
    @InjectMocks
    private Branch                    branch;

    @Test
    public void elementShouldBeCreatedWithUniqueId() throws Exception {
        Branch otherBranch = new Branch(elementCreatorsManager);

        assertThat(branch.getId(), is(not(sameInstance(otherBranch.getId()))));
    }

    @Test
    public void branchTitleShouldBeSetAndGot() throws Exception {
        branch.setTitle(SOME_TEXT);

        assertThat(branch.getTitle(), equalTo(SOME_TEXT));
    }

    @Test
    public void branchParentShouldBeSetAndGot() throws Exception {
        Element parent = mock(Element.class);
        branch.setParent(parent);

        assertThat(branch.getParent(), equalTo(parent));
    }

    @Test
    public void elementShouldBeAddedToBranch() throws Exception {
        Element parent = mock(Element.class);
        branch.setParent(parent);

        branch.addElement(element);

        verify(element).setParent(parent);
        assertThat(branch.getElements().size(), is(1));
        assertThat(branch.getElements(), hasItem(sameInstance(element)));
    }

    @Test
    public void elementShouldBeRemovedFromBranch() throws Exception {
        branch.addElement(element);
        branch.addElement(element);

        assertThat(branch.getElements().size(), is(2));
        assertThat(branch.getElements(), hasItem(sameInstance(element)));
        assertThat(branch.getElements(), hasItem(sameInstance(element)));

        branch.removeElement(element);

        assertThat(branch.getElements().size(), is(1));
        assertThat(branch.getElements(), hasItem(sameInstance(element)));
    }

    @Test
    public void hasElementsShouldBeReturnedTrueWhenElementAddedToBranch() throws Exception {
        branch.addElement(element);

        assertThat(branch.hasElements(), is(true));
    }

    @Test
    public void hasElementsShouldBeReturnedFalseWhenBranchHasNotElement() throws Exception {
        assertThat(branch.hasElements(), is(false));
    }

    @Test
    public void attributeShouldBeAddedAndGot() throws Exception {
        branch.addAttribute(ATTRIBUTE_NAME, SOME_TEXT);

        assertThat(branch.getAttributeByName(ATTRIBUTE_NAME), equalTo(SOME_TEXT));
    }

    @Test
    public void branchWithAttributeAndElementShouldBeSerialized() throws Exception {
        when(element.serialize()).thenReturn(SOME_TEXT);

        branch.setName(SOME_TEXT);

        branch.addAttribute(ATTRIBUTE_NAME, SOME_TEXT);
        branch.addElement(element);

        assertThat(branch.serialize(), equalTo(SERIALIZATION_CONTENT));
        verify(element).serialize();
    }

    @Test
    public void branchShouldBeDeserialized() throws Exception {
        AddressEndpoint addressEndpoint = mock(AddressEndpoint.class);

        prepareMocksAndCallDeserialize(addressEndpoint);

        verify(addressEndpoint).deserialize(any(Node.class));
        verify(addressEndpointProvider).get();

        assertThat(branch.getElements().size(), is(1));
        assertThat(branch.getElements(), hasItem(sameInstance(addressEndpoint)));
        assertThat(branch.getAttributeByName(ATTRIBUTE_NAME), equalTo(SOME_TEXT));
    }

    private void prepareMocksAndCallDeserialize(AddressEndpoint element) throws Exception {
        when(elementCreatorsManager.getProviderBySerializeName(eq("address"))).then(new Answer<Object>() {
            @Override
            public Provider<? extends Element> answer(InvocationOnMock invocation) throws Throwable {
                // we must use do answer for returning needed provider. in other case it is impossible.
                return addressEndpointProvider;
            }
        });
        when(addressEndpointProvider.get()).thenReturn(element);

        branch.deserialize(getNode(PATH_TO_EXAMPLES));
    }

    @Nonnull
    private Node getNode(@Nonnull String path) throws IOException {
        //noinspection NonJREEmulationClassesInClientCode
        String file = getClass().getResource(path).getFile();
        //noinspection NonJREEmulationClassesInClientCode
        String content = new String(Files.readAllBytes(Paths.get(file)));
        Document xml = XMLParser.parse(ContentFormatter.trimXML(content));

        return xml.getFirstChild();
    }

    @Test
    public void deserializationShouldBeContinuedWhenElementIsNull() throws Exception {
        AddressEndpoint addressEndpoint = mock(AddressEndpoint.class);

        prepareMocksAndCallDeserialize(null);

        verify(addressEndpoint, never()).deserialize(any(Node.class));
        assertThat(branch.getElements().size(), is(0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void elementsListShouldNotBeChanged() throws Exception {
        List<Element> elements = branch.getElements();

        elements.add(element);
    }

}