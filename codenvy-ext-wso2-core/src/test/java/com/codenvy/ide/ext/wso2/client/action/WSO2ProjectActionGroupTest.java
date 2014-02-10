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

import com.codenvy.ide.api.resources.ResourceProvider;
import com.codenvy.ide.api.ui.action.ActionEvent;
import com.codenvy.ide.api.ui.action.Presentation;
import com.codenvy.ide.api.ui.wizard.DefaultWizard;
import com.codenvy.ide.api.ui.wizard.DefaultWizardFactory;
import com.codenvy.ide.api.ui.wizard.WizardDialog;
import com.codenvy.ide.api.ui.wizard.WizardDialogFactory;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.codenvy.ide.ext.wso2.client.wizard.files.CreateEndpointPage;
import com.google.inject.Provider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_CONFIGURATION_PROJECT_ID;
import static org.junit.Assert.assertEquals;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link WSO2ProjectActionGroup}.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
@RunWith(MockitoJUnitRunner.class)
public class WSO2ProjectActionGroupTest {

    @Mock
    private DefaultWizard                wizard;
    @Mock
    private LocalizationConstant         locale;
    @Mock
    private WizardDialogFactory          wizardDialogFactory;
    @Mock
    private DefaultWizardFactory         defaultWizardFactory;
    @Mock
    private Provider<CreateEndpointPage> createEndpointPage;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private ResourceProvider             resourceProvider;
    @Mock
    private WSO2Resources                resources;
    @Mock
    private WizardDialog                 dialog;
    @Mock
    private ActionEvent                  actionEvent;
    private Presentation                 presentation;
    @InjectMocks
    private WSO2ProjectActionGroup       action;

    @Before
    public void setUp() throws Exception {
        presentation = new Presentation();
    }

    @Test
    public void actionShouldBeInvisibleWhenNoProjectIsOpened() throws Exception {
        when(actionEvent.getPresentation()).thenReturn(presentation);
        when(resourceProvider.getActiveProject()).thenReturn(null);

        assertEquals(true, presentation.isVisible());

        action.update(actionEvent);

        assertEquals(false, presentation.isVisible());
    }

    @Test
    public void actionShouldBeInvisibleWhenProjectTypeIsNotValid() throws Exception {
        when(actionEvent.getPresentation()).thenReturn(presentation);
        when((resourceProvider.getActiveProject().getPropertyValue(eq("vfs:projectType")))).thenReturn("projectType");

        assertEquals(true, presentation.isVisible());

        action.update(actionEvent);

        assertEquals(false, presentation.isVisible());
    }

    @Test
    public void actionShouldBeVisible() throws Exception {
        when(actionEvent.getPresentation()).thenReturn(presentation);
        when(resourceProvider.getActiveProject().getPropertyValue(eq("vfs:projectType"))).thenReturn(ESB_CONFIGURATION_PROJECT_ID);

        assertEquals(true, presentation.isVisible());

        action.update(actionEvent);

        assertEquals(true, presentation.isVisible());
    }
}