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

import com.codenvy.ide.api.action.Action;
import com.codenvy.ide.api.action.ActionEvent;
import com.codenvy.ide.api.wizard.DefaultWizard;
import com.codenvy.ide.api.wizard.DefaultWizardFactory;
import com.codenvy.ide.api.wizard.WizardDialog;
import com.codenvy.ide.api.wizard.WizardDialogFactory;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.codenvy.ide.ext.wso2.client.wizard.files.CreateEndpointPage;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * The action for creating an endpoint.
 *
 * @author Andrey Plotnikov
 */
public class CreateEndpointAction extends Action {

    private WizardDialog                 dialog;
    private LocalizationConstant         locale;
    private WizardDialogFactory          wizardDialogFactory;
    private DefaultWizardFactory         defaultWizardFactory;
    private Provider<CreateEndpointPage> createEndpointPage;

    @Inject
    public CreateEndpointAction(LocalizationConstant locale,
                                WizardDialogFactory wizardDialogFactory,
                                DefaultWizardFactory defaultWizardFactory,
                                Provider<CreateEndpointPage> createEndpointPage,
                                WSO2Resources resources) {

        super(locale.wso2ActionsCreateEndpointTitle(), null, resources.endpointIcon());

        this.locale = locale;
        this.wizardDialogFactory = wizardDialogFactory;
        this.defaultWizardFactory = defaultWizardFactory;
        this.createEndpointPage = createEndpointPage;
    }

    /** {@inheritDoc} */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (dialog == null) {
            DefaultWizard wizard = defaultWizardFactory.create(locale.wizardFileEndpointTitle());
            wizard.addPage(createEndpointPage);

            dialog = wizardDialogFactory.create(wizard);
        }

        dialog.show();
    }
}