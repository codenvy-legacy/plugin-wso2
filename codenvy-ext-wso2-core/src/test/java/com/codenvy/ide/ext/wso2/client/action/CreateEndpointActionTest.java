/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.ext.wso2.client.action;

import com.codenvy.ide.ext.wso2.client.wizard.files.CreateEndpointPage;
import com.google.inject.Provider;

import org.junit.Before;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link CreateEndpointAction}.
 *
 * @author Andrey Plotnikov
 */
public class CreateEndpointActionTest extends AbstractCreateResourceActionTest {

    @Mock
    private Provider<CreateEndpointPage> createEndpointPage;

    @Before
    public void setUp() throws Exception {
        page = createEndpointPage;
        action = new CreateEndpointAction(locale, wizardDialogFactory, defaultWizardFactory, createEndpointPage, resources);

        verify(locale).wso2ActionsCreateEndpointTitle();
        verify(resources).endpointIcon();
    }

    @Override
    public void wizardShouldBeCreated() throws Exception {
        when(locale.wizardFileEndpointTitle()).thenReturn(SOME_TEXT);

        super.wizardShouldBeCreated();
    }

    @Override
    public void wizardShouldBeNotRecreated() throws Exception {
        when(locale.wizardFileEndpointTitle()).thenReturn(SOME_TEXT);

        super.wizardShouldBeNotRecreated();
    }
}