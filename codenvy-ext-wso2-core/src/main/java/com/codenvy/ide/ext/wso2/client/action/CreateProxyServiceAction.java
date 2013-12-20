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
import com.codenvy.ide.api.ui.wizard.WizardDialog;
import com.codenvy.ide.api.ui.wizard.WizardDialogFactory;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.codenvy.ide.ext.wso2.client.wizard.files.CreateProxyServicePage;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * The action for creating a proxy service.
 *
 * @author Andrey Plotnikov
 */
public class CreateProxyServiceAction extends Action {

    private WizardDialog                     dialog;
    private LocalizationConstant             locale;
    private WizardDialogFactory              wizardDialogFactory;
    private DefaultWizardFactory             defaultWizardFactory;
    private Provider<CreateProxyServicePage> createProxyServicePage;

    @Inject
    public CreateProxyServiceAction(LocalizationConstant locale,
                                    WizardDialogFactory wizardDialogFactory,
                                    DefaultWizardFactory defaultWizardFactory,
                                    Provider<CreateProxyServicePage> createProxyServicePage,
                                    WSO2Resources resources) {

        super(locale.wso2ActionsCreateProxyServiceTitle(), null, resources.proxyServiceIcon());

        this.locale = locale;
        this.wizardDialogFactory = wizardDialogFactory;
        this.defaultWizardFactory = defaultWizardFactory;
        this.createProxyServicePage = createProxyServicePage;
    }

    /** {@inheritDoc} */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (dialog == null) {
            DefaultWizard wizard = defaultWizardFactory.create(locale.wizardFileProxyServiceTitle());
            wizard.addPage(createProxyServicePage);

            dialog = wizardDialogFactory.create(wizard);
        }

        dialog.show();
    }
}