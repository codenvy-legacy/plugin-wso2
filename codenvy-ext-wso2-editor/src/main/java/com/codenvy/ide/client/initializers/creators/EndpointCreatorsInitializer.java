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
import com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint;
import com.codenvy.ide.client.initializers.Initializer;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author Andrey Plotnikov
 */
public class EndpointCreatorsInitializer implements Initializer {

    private final ElementCreatorsManager    manager;
    private final Provider<AddressEndpoint> addressEndpointProvider;

    @Inject
    public EndpointCreatorsInitializer(ElementCreatorsManager manager,
                                       Provider<AddressEndpoint> addressEndpointProvider) {
        this.manager = manager;
        this.addressEndpointProvider = addressEndpointProvider;
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        manager.register(AddressEndpoint.ELEMENT_NAME,
                         AddressEndpoint.SERIALIZATION_NAME,
                         EndpointCreatingState.ADDRESS,
                         addressEndpointProvider);
    }

}