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
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.codenvy.ide.ext.wso2.client.upload.ImportFilePresenter;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * The action for importing configuration files.
 *
 * @author Valeriy Svydenko
 */
public class ImportSynapseAction extends Action {

    private Provider<ImportFilePresenter> importFilePresenter;

    @Inject
    public ImportSynapseAction(LocalizationConstant local,
                               Provider<ImportFilePresenter> importFilePresenter,
                               WSO2Resources wso2Resources) {

        super(local.wso2ImportSynapseConfig(), local.wso2ImportActionDescription(), wso2Resources.synapseIcon());

        this.importFilePresenter = importFilePresenter;
    }

    /** {@inheritDoc} */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ImportFilePresenter presenter = importFilePresenter.get();
        presenter.showDialog();
    }
}