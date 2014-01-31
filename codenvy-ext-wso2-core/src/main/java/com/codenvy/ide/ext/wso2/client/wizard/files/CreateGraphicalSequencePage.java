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
package com.codenvy.ide.ext.wso2.client.wizard.files;

import static com.codenvy.ide.ext.wso2.shared.Constants.SEQUENCE_FOLDER_NAME;

import javax.validation.constraints.NotNull;

import com.codenvy.ide.api.editor.EditorAgent;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.resources.FileType;
import com.codenvy.ide.api.resources.ResourceProvider;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.codenvy.ide.ext.wso2.client.editor.ESBGraphicalFileType;
import com.codenvy.ide.ext.wso2.client.wizard.files.view.CreateResourceView;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The wizard page provides creating 'Graphical Sequence'.
 *
 * @author Thomas Legrand
 */
// TODO This class has never used
@Singleton
public class CreateGraphicalSequencePage extends AbstractCreateResourcePage {

    @Inject
    public CreateGraphicalSequencePage(CreateResourceView view,
                              LocalizationConstant locale,
                              ResourceProvider resourceProvider,
                              WSO2Resources resources,
                              EditorAgent editorAgent,
                              @ESBGraphicalFileType FileType esbGraphicalFileType,
                              NotificationManager notificationManager) {

        super(view, locale.wizardFileGraphicalSequenceTitle(), resources.sequence_wizard(), locale, resourceProvider, editorAgent,
              SEQUENCE_FOLDER_NAME, esbGraphicalFileType, notificationManager);

        view.setResourceNameTitle(locale.wizardFileGraphicalSequenceFieldsName());
    }

    /** {@inheritDoc} */
    @Override
    public String getNotice() {
        if (view.getResourceName().isEmpty()) {
            return locale.wizardFileGraphicalSequenceNoticeEmptyName();
        }

        return super.getNotice();
    }

    /** {@inheritDoc} */
    @Override
    public void commit(@NotNull CommitCallback callback) {
        super.commit(callback);
    }
}