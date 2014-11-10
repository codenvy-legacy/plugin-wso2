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
package com.codenvy.ide.client.managers;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.Element;
import com.google.inject.Provider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public class ElementCreatorsManagerTest {

    private static final String NAME               = "name";
    private static final String STATE              = "state";
    private static final String SERIALIZATION_NAME = "serialization_name";

    @Mock
    private Provider<? extends Element>    provider;
    @Mock
    private WSO2EditorLocalizationConstant locale;
    @InjectMocks
    private ElementCreatorsManager         manager;

    @Test
    public void newCreatorShouldBeRegisteredAndReturnedBySerializationName() throws Exception {
        manager.register(NAME, SERIALIZATION_NAME, STATE, provider);

        Provider<? extends Element> testProvider = manager.getProviderBySerializeName(SERIALIZATION_NAME);

        assertEquals(provider, testProvider);

        verify(locale, never()).errorToolbarEditorStateWasAlreadyAdded(anyString());
    }

    @Test
    public void newCreatorShouldNotBeRegisteredWhenSameProvidersContainsTheSameState() throws Exception {
        manager.register(NAME, SERIALIZATION_NAME, STATE, provider);
        manager.register(NAME, SERIALIZATION_NAME, STATE, provider);

        verify(locale).errorToolbarEditorStateWasAlreadyAdded(STATE);
    }

    @Test
    public void providerShouldBeReturnedByState() throws Exception {
        manager.register(NAME, SERIALIZATION_NAME, STATE, provider);

        Provider<? extends Element> testProvider = manager.getProviderByState(STATE);

        assertEquals(provider, testProvider);
    }

    @Test
    public void elementNameShouldBeReturnedByState() throws Exception {
        manager.register(NAME, SERIALIZATION_NAME, STATE, provider);

        String testName = manager.getElementNameByState(STATE);

        assertThat(NAME, is(sameInstance(testName)));
    }

}