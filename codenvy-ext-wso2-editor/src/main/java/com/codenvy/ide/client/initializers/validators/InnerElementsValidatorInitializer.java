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

import com.codenvy.ide.client.elements.RootElement;
import com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint;
import com.codenvy.ide.client.elements.mediators.Call;
import com.codenvy.ide.client.elements.mediators.Filter;
import com.codenvy.ide.client.elements.mediators.Send;
import com.codenvy.ide.client.elements.mediators.Switch;
import com.codenvy.ide.client.initializers.Initializer;
import com.codenvy.ide.client.validators.InnerElementsValidator;
import com.google.inject.Inject;

/**
 * @author Andrey Plotnikov
 */
public class InnerElementsValidatorInitializer implements Initializer {

    private final InnerElementsValidator validator;

    @Inject
    public InnerElementsValidatorInitializer(InnerElementsValidator validator) {
        this.validator = validator;
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        validator.addAllowRule(Call.ELEMENT_NAME, AddressEndpoint.ELEMENT_NAME);
        validator.addAllowRule(Send.ELEMENT_NAME, AddressEndpoint.ELEMENT_NAME);
        validator.addDisallowRule(RootElement.ELEMENT_NAME, AddressEndpoint.ELEMENT_NAME);
        validator.addDisallowRule(Filter.ELEMENT_NAME, AddressEndpoint.ELEMENT_NAME);
        validator.addDisallowRule(Switch.ELEMENT_NAME, AddressEndpoint.ELEMENT_NAME);
    }

}