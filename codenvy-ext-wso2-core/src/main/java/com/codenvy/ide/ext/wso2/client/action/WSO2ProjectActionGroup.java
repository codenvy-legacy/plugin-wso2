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
import com.codenvy.ide.api.ui.action.ActionManager;
import com.codenvy.ide.api.ui.action.DefaultActionGroup;
import com.codenvy.ide.resources.model.Project;

import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_CONFIGURATION_PROJECT_ID;

/**
 * The main action group for WSO2 plugin. This group contains all action those are provided with the plugin.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class WSO2ProjectActionGroup extends DefaultActionGroup {

    public static final String PROPERTY_PROJECT_TYPE = "vfs:projectType";

    private ResourceProvider resourceProvider;

    public WSO2ProjectActionGroup(ActionManager actionManager,
                                  ResourceProvider resourceProvider) {

        super(null, false, actionManager);

        this.resourceProvider = resourceProvider;
    }

    /** {@inheritDoc} */
    @Override
    public void update(ActionEvent e) {
        boolean visible = false;

        Project activeProject = resourceProvider.getActiveProject();

        if (activeProject != null) {
            String projectType = (String)activeProject.getPropertyValue(PROPERTY_PROJECT_TYPE);
            visible = ESB_CONFIGURATION_PROJECT_ID.equals(projectType);
        }

        e.getPresentation().setVisible(visible);
    }
}