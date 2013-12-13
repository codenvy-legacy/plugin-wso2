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
package com.codenvy.ide.ext.wso2.client.wizard.project;

import com.codenvy.ide.annotations.NotNull;
import com.codenvy.ide.api.ui.wizard.template.AbstractTemplatePage;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import static com.codenvy.ide.ext.wso2.client.WSO2Extension.ESB_CONFIGURATION_PROJECT_ID;

/**
 * The wizard page provides creating an empty ESB configuration project. Also checks inputted information on the page.
 *
 * @author Andrey Plotnikov
 */
public class CreateESBConfProjectPage extends AbstractTemplatePage implements CreateESBConfProjectView.ActionDelegate {

    private CreateESBConfProjectView view;
    private LocalizationConstant     locale;

    @Inject
    public CreateESBConfProjectPage(CreateESBConfProjectView view, LocalizationConstant locale) {
        super(locale.wizardProjectTitle(), null, ESB_CONFIGURATION_PROJECT_ID);

        this.view = view;
        this.view.setDelegate(this);
        this.locale = locale;

        this.view.setArtifactID("");
        this.view.setGroupID("");
        this.view.setVersion("");
        this.view.setParentPomConfEnable(false);
        this.view.setParentArtifactID("");
        this.view.setParentGroupID("");
        this.view.setParentVersion("");
    }

    /** {@inheritDoc} */
    @Override
    public boolean canSkip() {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public String getNotice() {
        if (view.getGroupID().isEmpty()) {
            return locale.wizardProjectNoticeEmptyGroupId();
        }

        if (view.getArtifactID().isEmpty()) {
            return locale.wizardProjectNoticeEmptyArtifactId();
        }

        if (view.getVersion().isEmpty()) {
            return locale.wizardProjectNoticeEmptyVersion();
        }

        if (view.isParentPomConfEnable()) {
            if (view.getParentGroupID().isEmpty()) {
                return locale.wizardProjectNoticeEmptyParentGroupId();
            }

            if (view.getParentArtifactID().isEmpty()) {
                return locale.wizardProjectNoticeEmptyParentArtifactId();
            }

            if (view.getParentVersion().isEmpty()) {
                return locale.wizardProjectNoticeEmptyParentVersion();
            }
        }

        return locale.wizardProjectNoticeGeneral();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isCompleted() {
        boolean isPomConfEmpty = view.getArtifactID().isEmpty() || view.getGroupID().isEmpty() || view.getVersion().isEmpty();
        boolean isParentPomConfEmpty =
                view.getParentArtifactID().isEmpty() || view.getParentGroupID().isEmpty() || view.getParentVersion().isEmpty();

        return view.isParentPomConfEnable() ? !isParentPomConfEmpty && !isPomConfEmpty : !isPomConfEmpty;
    }

    /** {@inheritDoc} */
    @Override
    public void go(AcceptsOneWidget container) {
        container.setWidget(view);
    }

    /** {@inheritDoc} */
    @Override
    public void commit(@NotNull CommitCallback callback) {
        // TODO need to add creating project on server side. It must be fixed in WSOTWO-2
        super.commit(callback);
    }

    /** {@inheritDoc} */
    @Override
    public void onValueChanged() {
        delegate.updateControls();
    }
}