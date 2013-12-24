/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 * [2012] - [2013] Codenvy, S.A. 
 * All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
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