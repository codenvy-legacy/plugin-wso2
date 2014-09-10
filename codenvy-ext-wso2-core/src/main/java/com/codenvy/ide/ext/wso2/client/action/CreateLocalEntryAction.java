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
import com.codenvy.ide.api.ui.wizard.WizardDialog;
import com.codenvy.ide.api.ui.wizard.WizardDialogFactory;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.codenvy.ide.ext.wso2.client.wizard.files.CreateLocalEntryPage;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * The action for creating a local entry.
 *
 * @author Andrey Plotnikov
 */
public class CreateLocalEntryAction extends Action {

    private WizardDialog                   dialog;
    private LocalizationConstant           locale;
    private WizardDialogFactory            wizardDialogFactory;
    private DefaultWizardFactory           defaultWizardFactory;
    private Provider<CreateLocalEntryPage> createLocalEntryPage;

    @Inject
    public CreateLocalEntryAction(LocalizationConstant locale,
                                  WizardDialogFactory wizardDialogFactory,
                                  DefaultWizardFactory defaultWizardFactory,
                                  Provider<CreateLocalEntryPage> createLocalEntryPage,
                                  WSO2Resources resources) {

        super(locale.wso2ActionsCreateLocalEntryTitle(), null, resources.localEntryIcon());

        this.locale = locale;
        this.wizardDialogFactory = wizardDialogFactory;
        this.defaultWizardFactory = defaultWizardFactory;
        this.createLocalEntryPage = createLocalEntryPage;
    }

    /** {@inheritDoc} */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (dialog == null) {
            DefaultWizard wizard = defaultWizardFactory.create(locale.wizardFileLocalEntryTitle());
            wizard.addPage(createLocalEntryPage);

            dialog = wizardDialogFactory.create(wizard);
        }

        dialog.show();
    }
}