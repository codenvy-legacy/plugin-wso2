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
import com.codenvy.ide.client.validators.InnerElementsValidator;

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
public class InnerElementsValidatorInitializerTest {

    @Mock
    private InnerElementsValidator            validator;
    @InjectMocks
    private InnerElementsValidatorInitializer initializer;

    @Test
    public void ruleShouldBeAddedToCallMediator() throws Exception {
        initializer.initialize();

        verify(validator).addAllowRule(Call.ELEMENT_NAME, AddressEndpoint.ELEMENT_NAME);
    }

    @Test
    public void ruleShouldBeAddedToSendMediator() throws Exception {
        initializer.initialize();

        verify(validator).addAllowRule(Send.ELEMENT_NAME, AddressEndpoint.ELEMENT_NAME);
    }

    @Test
    public void disallowRuleShouldBeAddedToRootElement() throws Exception {
        initializer.initialize();

        verify(validator).addDisallowRule(RootElement.ELEMENT_NAME, AddressEndpoint.ELEMENT_NAME);
    }

    @Test
    public void disallowRuleShouldBeAddedToFilterMediator() throws Exception {
        initializer.initialize();

        verify(validator).addDisallowRule(Filter.ELEMENT_NAME, AddressEndpoint.ELEMENT_NAME);
    }

    @Test
    public void disallowRuleShouldBeAddedToSwitchMediator() throws Exception {
        initializer.initialize();

        verify(validator).addDisallowRule(Switch.ELEMENT_NAME, AddressEndpoint.ELEMENT_NAME);
    }

}