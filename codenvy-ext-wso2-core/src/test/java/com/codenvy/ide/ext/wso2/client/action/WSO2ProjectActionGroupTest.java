/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
        when((resourceProvider.getActiveProject().getDescription().getProjectTypeId())).thenReturn("projectType");

        assertEquals(true, presentation.isVisible());
        action.update(actionEvent);

        assertEquals(false, presentation.isVisible());
    }

    @Test
    public void actionShouldBeVisible() throws Exception {
        when(actionEvent.getPresentation()).thenReturn(presentation);
        when((resourceProvider.getActiveProject().getDescription().getProjectTypeId())).thenReturn(ESB_CONFIGURATION_PROJECT_ID);

        assertEquals(true, presentation.isVisible());

        action.update(actionEvent);

        assertEquals(true, presentation.isVisible());
    }
}