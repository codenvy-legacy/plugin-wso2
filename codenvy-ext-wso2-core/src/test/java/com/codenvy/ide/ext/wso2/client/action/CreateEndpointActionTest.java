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

import com.codenvy.ide.ext.wso2.client.wizard.files.CreateEndpointPage;
import com.google.inject.Provider;

import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link CreateEndpointAction}.
 *
 * @author Andrey Plotnikov
 */
@Listeners(value = {MockitoTestNGListener.class})
public class CreateEndpointActionTest extends AbstractCreateResourceActionTest {

    @Mock
    private Provider<CreateEndpointPage> createEndpointPage;

    @BeforeMethod
    public void setUp() throws Exception {
        page = createEndpointPage;
        action = new CreateEndpointAction(locale, wizardDialogFactory, defaultWizardFactory, createEndpointPage, resources);
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