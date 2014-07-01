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

import com.codenvy.ide.ext.wso2.client.wizard.files.CreateLocalEntryPage;
import com.google.inject.Provider;

import org.junit.Before;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link CreateLocalEntryAction}.
 *
 * @author Andrey Plotnikov
 */
public class CreateLocalEntryActionTest extends AbstractCreateResourceActionTest {

    @Mock
    private Provider<CreateLocalEntryPage> createLocalEntryPage;

    @Before
    public void setUp() throws Exception {
        page = createLocalEntryPage;
        action = new CreateLocalEntryAction(locale, wizardDialogFactory, defaultWizardFactory, createLocalEntryPage, resources);

        verify(locale).wso2ActionsCreateLocalEntryTitle();
        verify(resources).localEntryIcon();
    }

    @Override
    public void wizardShouldBeCreated() throws Exception {
        when(locale.wizardFileLocalEntryTitle()).thenReturn(SOME_TEXT);

        super.wizardShouldBeCreated();
    }

    @Override
    public void wizardShouldBeNotRecreated() throws Exception {
        when(locale.wizardFileLocalEntryTitle()).thenReturn(SOME_TEXT);

        super.wizardShouldBeNotRecreated();
    }
}