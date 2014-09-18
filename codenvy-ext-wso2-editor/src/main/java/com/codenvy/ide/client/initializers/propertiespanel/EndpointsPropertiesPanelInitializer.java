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

package com.codenvy.ide.client.initializers.propertiespanel;

import com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint;
import com.codenvy.ide.client.managers.PropertiesPanelManager;
import com.codenvy.ide.client.propertiespanel.endpoints.address.AddressEndpointPropertiesPanelPresenter;
import com.google.inject.Inject;

/**
 * @author Andrey Plotnikov
 */
public class EndpointsPropertiesPanelInitializer extends AbstractPropertiesPanelInitializer {

    private final AddressEndpointPropertiesPanelPresenter addressEndpointPropertiesPanel;

    @Inject
    public EndpointsPropertiesPanelInitializer(PropertiesPanelManager manager,
                                               AddressEndpointPropertiesPanelPresenter addressEndpointPropertiesPanel) {
        super(manager);
        this.addressEndpointPropertiesPanel = addressEndpointPropertiesPanel;
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        manager.register(AddressEndpoint.class, addressEndpointPropertiesPanel);
    }

}