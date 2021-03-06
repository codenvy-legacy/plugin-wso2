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
package com.codenvy.ide.client.initializers.validators;

import com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint;
import com.codenvy.ide.client.elements.mediators.LoopBack;
import com.codenvy.ide.client.elements.mediators.Respond;
import com.codenvy.ide.client.initializers.Initializer;
import com.codenvy.ide.client.validators.ConnectionsValidator;
import com.google.inject.Inject;

/**
 * @author Andrey Plotnikov
 */
public class ConnectionsValidatorInitializer implements Initializer {

    private final ConnectionsValidator validator;

    @Inject
    public ConnectionsValidatorInitializer(ConnectionsValidator validator) {
        this.validator = validator;
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        validator.addDisallowAllRule(Respond.ELEMENT_NAME);
        validator.addDisallowAllRule(LoopBack.ELEMENT_NAME);
        validator.addDisallowAllRule(AddressEndpoint.ELEMENT_NAME);
    }

}