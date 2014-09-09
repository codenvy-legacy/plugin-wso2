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
package com.codenvy.ide.ext.wso2.client.action;

import com.codenvy.ide.api.ui.action.Action;
import com.codenvy.ide.api.ui.action.ActionEvent;
import com.codenvy.ide.api.ui.wizard.DefaultWizard;
import com.codenvy.ide.api.ui.wizard.DefaultWizardFactory;
import com.codenvy.ide.api.ui.wizard.Wizard;
import com.codenvy.ide.api.ui.wizard.WizardDialog;
import com.codenvy.ide.api.ui.wizard.WizardDialogFactory;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.codenvy.ide.ext.wso2.client.wizard.files.AbstractCreateResourcePage;
import com.google.inject.Provider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The basic test for testing create WSO2 resource actions.
 *
 * @author Andrey Plotnikov
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractCreateResourceActionTest {

    public static final String SOME_TEXT = "some text";

    @Mock
    protected DefaultWizard                                  wizard;
    @Mock
    protected LocalizationConstant                           locale;
    @Mock
    protected WizardDialogFactory                            wizardDialogFactory;
    @Mock
    protected DefaultWizardFactory                           defaultWizardFactory;
    @Mock
    protected WSO2Resources                                  resources;
    @Mock
    protected WizardDialog                                   dialog;
    @Mock
    protected ActionEvent                                    actionEvent;
    protected Provider<? extends AbstractCreateResourcePage> page;
    protected Action                                         action;

    @Test
    public void wizardShouldBeCreated() throws Exception {
        when(defaultWizardFactory.create(anyString())).thenReturn(wizard);
        when(wizardDialogFactory.create((Wizard)anyObject())).thenReturn(dialog);

        action.actionPerformed(actionEvent);

        verify(defaultWizardFactory).create(eq(SOME_TEXT));
        verify(wizard).addPage(eq(page));
        verify(wizardDialogFactory).create(eq(wizard));
        verify(dialog).show();
    }

    @Test
    public void wizardShouldBeNotRecreated() throws Exception {
        when(defaultWizardFactory.create(anyString())).thenReturn(wizard);
        when(wizardDialogFactory.create((Wizard)anyObject())).thenReturn(dialog);

        action.actionPerformed(actionEvent);
        action.actionPerformed(actionEvent);

        verify(defaultWizardFactory).create(eq(SOME_TEXT));
        verify(wizard).addPage(eq(page));
        verify(wizardDialogFactory).create(eq(wizard));
        verify(dialog, times(2)).show();
    }
}