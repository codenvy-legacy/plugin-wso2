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

import com.codenvy.ide.api.action.ActionEvent;
import com.codenvy.ide.api.action.ActionManager;
import com.codenvy.ide.api.action.DefaultActionGroup;
import com.codenvy.ide.api.app.AppContext;
import com.codenvy.ide.api.app.CurrentProject;

import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_CONFIGURATION_PROJECT_ID;

/**
 * The main action group for WSO2 plugin. This group contains all action those are provided with the plugin.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class WSO2ProjectActionGroup extends DefaultActionGroup {
    private final AppContext appContext;

    public WSO2ProjectActionGroup(ActionManager actionManager, AppContext appContext) {
        super(null, false, actionManager);

        this.appContext = appContext;
    }

    /** {@inheritDoc} */
    @Override
    public void update(ActionEvent e) {
        boolean visible = false;

        CurrentProject currentProject = appContext.getCurrentProject();

        if (currentProject != null) {
            String projectType = currentProject.getProjectDescription().getType();
            visible = ESB_CONFIGURATION_PROJECT_ID.equals(projectType);
        }

        e.getPresentation().setVisible(visible);
    }
}