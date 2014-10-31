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

import com.google.inject.Provider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.elements.AbstractEntityElement.Key;
import static com.codenvy.ide.client.elements.NameSpace.PREFIX_KEY;
import static com.codenvy.ide.client.elements.NameSpace.URI;
import static com.codenvy.ide.client.elements.NameSpace.applyNameSpace;
import static com.codenvy.ide.client.elements.NameSpace.convertNameSpacesToXML;
import static com.codenvy.ide.client.elements.NameSpace.copyNameSpaceList;
import static com.codenvy.ide.client.elements.NameSpace.isNameSpace;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NameSpaceTest extends AbstractEntityTest<NameSpace> {

    private static final String PREFIX_VALUE = "prefix";
    private static final String URI_VALUE    = "uri";

    private static final String CONTENT             = "xmlns:prefix=\"uri\"";
    private static final String SOME_TEXT           = "someText";
    private static final String CORRECT_ATTRIBUTE   = "xmlns:prefix";
    private static final String INCORRECT_ATTRIBUTE = "xmln";

    @Mock
    private Provider<NameSpace> nameSpaceProvider;
    @Mock
    private NameSpace           nameSpace;
    @Mock
    private List<NameSpace>     nameSpaces;

    @Before
    public void setUp() {
        when(nameSpaceProvider.get()).thenReturn(nameSpace);
        when(nameSpace.toString()).thenReturn(CONTENT);

        entity = new NameSpace(nameSpaceProvider);
    }

    @Test
    public void nameSpaceShouldBeCopied() throws Exception {
        NameSpace nameSpace = new NameSpace(nameSpaceProvider);

        when(nameSpaceProvider.get()).thenReturn(nameSpace);
        NameSpace copy = entity.copy();

        verify(nameSpaceProvider).get();

        assertThat(entity, not(sameInstance(copy)));
        assertThat(entity.equals(copy), is(true));
    }

    @Test
    public void allParametersShouldBeSetWhenNameSpaceCopy() throws Exception {
        entity.copy();

        verify(nameSpaceProvider).get();

        verify(nameSpace).putProperty(PREFIX_KEY, "");
        verify(nameSpace).putProperty(URI, "");
    }

    @Test
    public void trueValueShouldBeReturnedWhenCurrentNameIsNameSpace() throws Exception {
        assertThat(isNameSpace(CONTENT), is(true));
    }

    @Test
    public void falseValueShouldBeReturnedWhenCurrentNameIsNotNameSpace() throws Exception {
        assertThat(isNameSpace(SOME_TEXT), is(false));
    }

    @Test
    public void nameSpaceShouldBeApplied() throws Exception {
        applyNameSpace(nameSpaceProvider, nameSpaces, CORRECT_ATTRIBUTE, SOME_TEXT);

        verify(nameSpaceProvider).get();
        verify(nameSpace).putProperty(PREFIX_KEY, "prefix");
        verify(nameSpace).putProperty(URI, SOME_TEXT);
        verify(nameSpaces).add(nameSpace);
    }

    @Test
    public void nameSpaceShouldBeAppliedWhenAttributeNameIsIncorrect() throws Exception {
        applyNameSpace(nameSpaceProvider, nameSpaces, INCORRECT_ATTRIBUTE, SOME_TEXT);

        verifyNeverCalledMethods();
    }

    private void verifyNeverCalledMethods() {
        verify(nameSpaceProvider, never()).get();
        verify(nameSpace, never()).putProperty(any(Key.class), anyString());
        verify(nameSpace, never()).putProperty(any(Key.class), anyString());
        verify(nameSpaces, never()).add(nameSpace);
    }

    @Test
    public void nameSpaceShouldBeAppliedWhenNameSpaceListIsNull() throws Exception {
        applyNameSpace(nameSpaceProvider, null, CORRECT_ATTRIBUTE, SOME_TEXT);

        verifyNeverCalledMethods();
    }

    @Test
    public void namesSpacesShouldBeConvertedToXml() throws Exception {
        String result = convertNameSpacesToXML(Arrays.asList(nameSpace));

        assertThat(result, equalTo(CONTENT + ' '));
    }

    @Test
    public void emptyStringShouldBeReturnedWhenNameSpaceListIsNull() throws Exception {
        String result = convertNameSpacesToXML(null);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void nameSpaceStringRepresentationShouldBeReturned() throws Exception {
        entity.putProperty(PREFIX_KEY, PREFIX_VALUE);
        entity.putProperty(URI, URI_VALUE);

        assertThat(entity.toString(), equalTo(CONTENT));
    }

    @Test
    public void nameSpaceListShouldBeCopied() throws Exception {
        when(nameSpace.copy()).thenReturn(nameSpace);

        List<NameSpace> list = new ArrayList<>();
        list.add(nameSpace);

        List<NameSpace> otherList = copyNameSpaceList(list);

        assertThat(list, not(sameInstance(otherList)));
        assertThat(list.equals(otherList), is(true));
    }

    @Test
    public void emptyListShouldBeReturnedWhenCurrentListIsNull() {
        List<NameSpace> list = copyNameSpaceList(null);

        assertThat(list.isEmpty(), is(true));
    }

    @Test
    public void sameHashCodeShouldBeReturnedForDifferentEntityWithSameState() throws Exception {
        NameSpace nameSpace = new NameSpace(nameSpaceProvider);

        assertThat(entity.hashCode(), equalTo(nameSpace.hashCode()));
    }

}