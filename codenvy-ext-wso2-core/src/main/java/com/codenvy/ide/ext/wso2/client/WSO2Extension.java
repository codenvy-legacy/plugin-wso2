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
package com.codenvy.ide.ext.wso2.client;


import com.codenvy.ide.api.extension.Extension;
import com.codenvy.ide.api.template.TemplateAgent;
import com.codenvy.ide.api.ui.wizard.template.AbstractTemplatePage;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.ext.wso2.client.wizard.project.CreateESBConfProjectPage;
import com.codenvy.ide.resources.ProjectTypeAgent;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Extension used to demonstrate the WSO2 plugins.
 *
 * @author Valeriy Svydenko
 */
@Singleton
@Extension(title = "Integration Flow WSO2 plugin", version = "1.0.0-M1")
public class WSO2Extension {
    public static final String WSO2_PROJECT_ID              = "WSO2Project";
    public static final String ESB_CONFIGURATION_PROJECT_ID = "ESBConfigurationProject";

    @Inject
    public WSO2Extension(LocalizationConstant locale,
                         ProjectTypeAgent projectTypeAgent,
                         TemplateAgent templateAgent,
                         Provider<CreateESBConfProjectPage> createESBConfProjectPage) {

        projectTypeAgent.register(WSO2_PROJECT_ID,
                                  locale.wso2ProjectTitle(),
                                  null,
                                  WSO2_PROJECT_ID,
                                  Collections.<String>createArray());

        templateAgent.register(ESB_CONFIGURATION_PROJECT_ID,
                               locale.wso2ProjectEsbTitle(),
                               null,
                               WSO2_PROJECT_ID,
                               Collections.<String>createArray(ESB_CONFIGURATION_PROJECT_ID),
                               Collections.<Provider<? extends AbstractTemplatePage>>createArray(createESBConfProjectPage));
    }
}