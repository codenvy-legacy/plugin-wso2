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

package com.codenvy.ide.client.initializers.propertytype;

import com.codenvy.ide.client.initializers.Initializer;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.google.inject.Inject;

import java.util.Arrays;

import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.AddressingVersion;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.Format;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.Optimize;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.TimeoutAction;

/**
 * @author Andrey Plotnikov
 */
public class EndpointsPropertyTypeInitializer implements Initializer {

    private final PropertyTypeManager propertyTypeManager;

    @Inject
    public EndpointsPropertyTypeInitializer(PropertyTypeManager propertyTypeManager) {
        this.propertyTypeManager = propertyTypeManager;
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        propertyTypeManager.register(Format.TYPE_NAME, Arrays.asList(Format.LEAVE_AS_IS.name(),
                                                                     Format.soap11.name(),
                                                                     Format.soap12.name(),
                                                                     Format.pox.name(),
                                                                     Format.get.name(),
                                                                     Format.REST.name()));

        propertyTypeManager.register(Optimize.TYPE_NAME, Arrays.asList(Optimize.LEAVE_AS_IS.name(),
                                                                       Optimize.mtom.name(),
                                                                       Optimize.swa.name()));

        propertyTypeManager.register(AddressingVersion.TYPE_NAME, Arrays.asList(AddressingVersion.FINAL.getValue(),
                                                                                AddressingVersion.SUBMISSION.getValue()));

        propertyTypeManager.register(TimeoutAction.TYPE_NAME, Arrays.asList(TimeoutAction.never.name(),
                                                                            TimeoutAction.discard.name(),
                                                                            TimeoutAction.fault.name()));
    }

}