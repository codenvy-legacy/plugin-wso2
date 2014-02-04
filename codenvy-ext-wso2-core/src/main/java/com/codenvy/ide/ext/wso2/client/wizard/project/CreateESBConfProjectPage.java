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

import com.codenvy.ide.api.resources.ResourceProvider;
import com.codenvy.ide.api.ui.wizard.template.AbstractTemplatePage;
import com.codenvy.ide.dto.DtoFactory;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2ClientService;
import com.codenvy.ide.ext.wso2.shared.ESBProjectInfo;
import com.codenvy.ide.resources.model.Project;
import com.codenvy.ide.rest.AsyncRequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.validation.constraints.NotNull;

import static com.codenvy.ide.api.ui.wizard.newproject.NewProjectWizard.PROJECT_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_CONFIGURATION_PROJECT_ID;

/**
 * The wizard page provides creating an empty ESB configuration project. Also checks inputted information on the page.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class CreateESBConfProjectPage extends AbstractTemplatePage implements CreateESBConfProjectView.ActionDelegate {

    private CreateESBConfProjectView view;
    private WSO2ClientService        service;
    private ResourceProvider         resourceProvider;
    private DtoFactory               dtoFactory;
    private LocalizationConstant     locale;

    @Inject
    public CreateESBConfProjectPage(CreateESBConfProjectView view,
                                    LocalizationConstant locale,
                                    WSO2ClientService service,
                                    ResourceProvider resourceProvider,
                                    DtoFactory dtoFactory) {

        super(null, null, ESB_CONFIGURATION_PROJECT_ID);

        /*TODO workaround a Maven Information step in project creation wizard.
        super(locale.wizardProjectTitle(), resources.esb_project_wizard(), ESB_CONFIGURATION_PROJECT_ID);*/

        this.view = view;
        this.service = service;
        this.resourceProvider = resourceProvider;
        this.dtoFactory = dtoFactory;
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

        return null;
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
    public void commit(@NotNull final CommitCallback callback) {
        final String projectName = wizardContext.getData(PROJECT_NAME);

        ESBProjectInfo projectInfo = dtoFactory.createDto(ESBProjectInfo.class)
                                               .withProjectName(projectName)
                                               .withGroupID("com.example.pop")
                                               .withArtifactID("pop")
                                               .withVersion("1.0.0");

        boolean parentPomConfEnable = view.isParentPomConfEnable();
        projectInfo.setParentPomConf(parentPomConfEnable);
        if (parentPomConfEnable) {
            projectInfo = projectInfo.withParentGroupID(view.getParentGroupID())
                                     .withParentArtifactID(view.getParentArtifactID())
                                     .withParentVersion(view.getParentVersion());
        }

        try {
            service.createESBConfProject(projectInfo, new AsyncRequestCallback<Void>() {
                @Override
                protected void onSuccess(Void aVoid) {
                    resourceProvider.getProject(projectName, new AsyncCallback<Project>() {
                        @Override
                        public void onSuccess(Project result) {
                            callback.onSuccess();
                        }

                        @Override
                        public void onFailure(Throwable caught) {
                            callback.onFailure(caught);
                        }
                    });
                }

                @Override
                protected void onFailure(Throwable throwable) {
                    callback.onFailure(throwable);
                }
            });
        } catch (RequestException e) {
            callback.onFailure(e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onValueChanged() {
        delegate.updateControls();
    }

    /** {@inheritDoc} */
    @Override
    public void onParentPomConfChanged() {
        view.setParentPomConfEnable(view.isParentPomConfEnable());
        delegate.updateControls();
    }
}