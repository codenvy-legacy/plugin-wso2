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

import com.codenvy.ide.ext.wso2.client.wizard.files.CreateLocalEntryPage;
import com.google.inject.Provider;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;

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

    @BeforeMethod
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