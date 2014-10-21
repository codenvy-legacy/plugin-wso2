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

import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.google.inject.Inject;

import java.util.Arrays;

import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.AddressingVersion;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.Format;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.Optimize;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.TimeoutAction;

/**
 * @author Andrey Plotnikov
 */
public class EndpointsPropertyTypeInitializer extends AbstractPropertyTypeInitializer {

    @Inject
    public EndpointsPropertyTypeInitializer(PropertyTypeManager manager) {
        super(manager);
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        manager.register(Format.TYPE_NAME, Arrays.asList(Format.LEAVE_AS_IS.getValue(),
                                                         Format.SOUP11.getValue(),
                                                         Format.SOUP12.getValue(),
                                                         Format.POX.getValue(),
                                                         Format.GET.getValue(),
                                                         Format.REST.getValue()));

        manager.register(Optimize.TYPE_NAME, Arrays.asList(Optimize.LEAVE_AS_IS.getValue(),
                                                           Optimize.MTOM.getValue(),
                                                           Optimize.SWA.getValue()));

        manager.register(AddressingVersion.TYPE_NAME, Arrays.asList(AddressingVersion.FINAL.getValue(),
                                                                    AddressingVersion.SUBMISSION.getValue()));

        manager.register(TimeoutAction.TYPE_NAME, Arrays.asList(TimeoutAction.NEVER.getValue(),
                                                                TimeoutAction.DISCARD.getValue(),
                                                                TimeoutAction.FAULT.getValue()));
    }

}