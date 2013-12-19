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

import com.codenvy.ide.annotations.NotNull;
import com.codenvy.ide.api.resources.ResourceProvider;
import com.codenvy.ide.api.ui.action.Action;
import com.codenvy.ide.api.ui.action.ActionEvent;
import com.codenvy.ide.api.ui.wizard.DefaultWizard;
import com.codenvy.ide.api.ui.wizard.DefaultWizardFactory;
import com.codenvy.ide.api.ui.wizard.WizardDialog;
import com.codenvy.ide.api.ui.wizard.WizardDialogFactory;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.codenvy.ide.ext.wso2.client.wizard.files.CreateEndpointPage;
import com.codenvy.ide.resources.model.Project;
import com.codenvy.ide.resources.model.Property;
import com.google.inject.Inject;
import com.google.inject.Provider;

import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_CONFIGURATION_PROJECT_ID;
import static com.codenvy.ide.ext.wso2.shared.Constants.WSO2_PROJECT_ID;
import static com.codenvy.ide.resources.model.ProjectDescription.PROPERTY_MIXIN_NATURES;
import static com.codenvy.ide.resources.model.ProjectDescription.PROPERTY_PRIMARY_NATURE;

/**
 * The action for creating a endpoint.
 *
 * @author Andrey Plotnikov
 */
public class CreateEndpointAction extends Action {

    private WizardDialog                 dialog;
    private LocalizationConstant         locale;
    private WizardDialogFactory          wizardDialogFactory;
    private DefaultWizardFactory         defaultWizardFactory;
    private Provider<CreateEndpointPage> createEndpointPage;
    private ResourceProvider             resourceProvider;

    @Inject
    public CreateEndpointAction(LocalizationConstant locale,
                                WizardDialogFactory wizardDialogFactory,
                                DefaultWizardFactory defaultWizardFactory,
                                Provider<CreateEndpointPage> createEndpointPage,
                                WSO2Resources resources,
                                ResourceProvider resourceProvider) {

        super(locale.wso2ActionsCreateEndpointTitle(), null, resources.endpointIcon());

        this.locale = locale;
        this.wizardDialogFactory = wizardDialogFactory;
        this.defaultWizardFactory = defaultWizardFactory;
        this.createEndpointPage = createEndpointPage;
        this.resourceProvider = resourceProvider;
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

    /** {@inheritDoc} */
    @Override
    public void update(ActionEvent e) {
        boolean visible = false;

        Project activeProject = resourceProvider.getActiveProject();
        if (activeProject != null) {
            Property primaryNature = activeProject.getProperty(PROPERTY_PRIMARY_NATURE);
            Property secondaryNature = activeProject.getProperty(PROPERTY_MIXIN_NATURES);

            boolean hasPrimaryNatureValue = hasValue(WSO2_PROJECT_ID, primaryNature.getValue());
            boolean hasSecondaryNatureValue = hasValue(ESB_CONFIGURATION_PROJECT_ID, secondaryNature.getValue());

            visible = hasPrimaryNatureValue && hasSecondaryNatureValue;
        }

        e.getPresentation().setVisible(visible);
    }

    /**
     * Returns whether list of values contains the given value.
     *
     * @param value
     *         value that need to find
     * @param values
     *         list of values
     * @return <code>true</code> if the value is included in the list, and <code>false</code> if it's not
     */
    private boolean hasValue(@NotNull String value, @NotNull Array<String> values) {
        for (String val : values.asIterable()) {
            if (value.equals(val)) {
                return true;
            }
        }
        return false;
    }
}