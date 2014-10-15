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
package com.codenvy.ide.ext.wso2.client.wizard.files;

import com.codenvy.api.project.gwt.client.ProjectServiceClient;
import com.codenvy.ide.api.app.AppContext;
import com.codenvy.ide.api.editor.EditorAgent;
import com.codenvy.ide.api.filetypes.FileType;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.codenvy.ide.ext.wso2.client.editor.ESBXmlFileType;
import com.codenvy.ide.ext.wso2.client.wizard.files.view.CreateResourceView;
import com.codenvy.ide.rest.DtoUnmarshallerFactory;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.codenvy.ide.ext.wso2.shared.Constants.PROXY_SERVICE_FOLDER_NAME;

/**
 * The wizard page provides creating 'Proxy Service'. Also checks inputted information on the page.
 *
 * @author Andrey Plotnikov
 * @author Dmitriy Shnurenko
 * @author Valeriy Svydenko
 */
public class CreateProxyServicePage extends AbstractCreateResourcePage {

    @Inject
    public CreateProxyServicePage(CreateResourceView view,
                                  LocalizationConstant locale,
                                  WSO2Resources resources,
                                  EditorAgent editorAgent,
                                  @ESBXmlFileType FileType esbXmlFileType,
                                  NotificationManager notificationManager,
                                  ProjectServiceClient projectServiceClient,
                                  AppContext appContext,
                                  EventBus eventBus,
                                  DtoUnmarshallerFactory dtoUnmarshallerFactory) {
        super(view,
              locale.wizardFileProxyServiceTitle(),
              locale.wizardFileProxyServiceFieldsName(),
              resources.proxy_service_wizard(),
              locale,
              editorAgent,
              PROXY_SERVICE_FOLDER_NAME,
              esbXmlFileType,
              notificationManager,
              resources,
              projectServiceClient,
              eventBus,
              appContext,
              dtoUnmarshallerFactory);
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public String getNotice() {
        if (view.getResourceName().isEmpty()) {
            return locale.wizardFileProxyServiceNoticeEmptyName();
        }

        return super.getNotice();
    }

    /** {@inheritDoc} */
    @Override
    public void commit(@Nonnull CommitCallback callback) {
        String proxyServiceTemplate = resources.proxyServiceTemplate().getText();
        content = proxyServiceTemplate.replaceAll("@name", view.getResourceName());

        super.commit(callback);
    }
}