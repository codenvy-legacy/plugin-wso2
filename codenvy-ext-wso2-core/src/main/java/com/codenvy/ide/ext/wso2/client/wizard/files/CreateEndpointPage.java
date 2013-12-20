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

import com.codenvy.ide.annotations.NotNull;
import com.codenvy.ide.api.editor.EditorAgent;
import com.codenvy.ide.api.resources.FileType;
import com.codenvy.ide.api.resources.ResourceProvider;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.codenvy.ide.ext.wso2.client.editor.ESBXmlFileType;
import com.codenvy.ide.ext.wso2.client.wizard.files.view.CreateResourceView;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import static com.codenvy.ide.ext.wso2.shared.Constants.ENDPOINTS_FOLDER_NAME;

/**
 * The wizard page provides creating 'Endpoint'. Also checks inputted information on the page.
 *
 * @author Andrey Plotnikov
 */
@Singleton
public class CreateEndpointPage extends AbstractCreateResourcePage {

    private WSO2Resources resources;

    @Inject
    public CreateEndpointPage(CreateResourceView view,
                              LocalizationConstant locale,
                              ResourceProvider resourceProvider,
                              WSO2Resources resources,
                              EditorAgent editorAgent,
                              @ESBXmlFileType FileType esbXmlFileType) {

        super(view, locale.wizardFileEndpointTitle(), resources.endpoint_wizard(), locale, resourceProvider, editorAgent,
              ENDPOINTS_FOLDER_NAME, esbXmlFileType);

        this.resources = resources;

        view.setResourceNameTitle(locale.wizardFileEndpointFieldsName());
    }

    /** {@inheritDoc} */
    @Override
    public String getNotice() {
        if (view.getResourceName().isEmpty()) {
            return locale.wizardFileEndpointNoticeEmptyName();
        }

        return super.getNotice();
    }

    /** {@inheritDoc} */
    @Override
    public void commit(@NotNull CommitCallback callback) {
        String endpointTemplate = resources.endpointTemplate().getText();
        content = endpointTemplate.replaceAll("@name", view.getResourceName());

        super.commit(callback);
    }
}