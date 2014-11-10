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
package com.codenvy.ide.client.initializers.creators;

import com.codenvy.ide.client.constants.EndpointCreatingState;
import com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.google.inject.Provider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public class EndpointCreatorsInitializerTest {

    @Mock
    private Provider<AddressEndpoint>   addressEndpointProvider;
    @Mock
    private ElementCreatorsManager      elementCreatorsManager;
    @InjectMocks
    private EndpointCreatorsInitializer endpointCreatorsInitializer;

    @Test
    public void addressEndpointParametersShouldBeInitialized() throws Exception {
        endpointCreatorsInitializer.initialize();

        verify(elementCreatorsManager).register(AddressEndpoint.ELEMENT_NAME,
                                                AddressEndpoint.SERIALIZATION_NAME,
                                                EndpointCreatingState.ADDRESS,
                                                addressEndpointProvider);
    }
}