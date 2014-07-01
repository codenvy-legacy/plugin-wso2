/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.ext.wso2.client.wizard.files;

import com.codenvy.ide.api.editor.EditorAgent;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.resources.FileType;
import com.codenvy.ide.api.resources.ResourceProvider;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.codenvy.ide.ext.wso2.client.editor.ESBXmlFileType;
import com.codenvy.ide.ext.wso2.client.wizard.files.view.CreateResourceView;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.validation.constraints.NotNull;

import static com.codenvy.ide.ext.wso2.shared.Constants.SEQUENCE_FOLDER_NAME;

/**
 * The wizard page provides creating 'Sequence'. Also checks inputted information on the page.
 *
 * @author Andrey Plotnikov
 */
@Singleton
public class CreateSequencePage extends AbstractCreateResourcePage {

    private WSO2Resources resources;

    @Inject
    public CreateSequencePage(CreateResourceView view,
                              LocalizationConstant locale,
                              ResourceProvider resourceProvider,
                              WSO2Resources resources,
                              EditorAgent editorAgent,
                              @ESBXmlFileType FileType esbXmlFileType,
                              NotificationManager notificationManager) {

        super(view, locale.wizardFileSequenceTitle(), resources.sequence_wizard(), locale, resourceProvider, editorAgent,
              SEQUENCE_FOLDER_NAME, esbXmlFileType, notificationManager);

        this.resources = resources;

        view.setResourceNameTitle(locale.wizardFileSequenceFieldsName());
    }

    /** {@inheritDoc} */
    @Override
    public String getNotice() {
        if (view.getResourceName().isEmpty()) {
            return locale.wizardFileSequenceNoticeEmptyName();
        }

        return super.getNotice();
    }

    /** {@inheritDoc} */
    @Override
    public void commit(@NotNull CommitCallback callback) {
        String sequenceTemplate = resources.sequenceTemplate().getText();
        content = sequenceTemplate.replaceAll("@name", view.getResourceName());

        super.commit(callback);
    }
}