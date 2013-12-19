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

import com.codenvy.ide.annotations.NotNull;
import com.codenvy.ide.api.ui.wizard.newresource.NewResourceProvider;
import com.codenvy.ide.ext.wso2.shared.Constants;
import com.codenvy.ide.resources.model.File;
import com.codenvy.ide.resources.model.Folder;
import com.codenvy.ide.resources.model.Project;
import com.codenvy.ide.resources.model.Resource;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;


/**
 * Responsible for the creation of a new WSO2 ESB configuration files, which are XML files.
 *
 * @author Dmitry Kuleshov
 */
public class NewEsbXmlFileProvider extends NewResourceProvider {
    private WSO2Resources xmlEditorResource;

    @Inject
    public NewEsbXmlFileProvider(WSO2Resources xmlEditorResource) {
        super("ESB XML file", "ESB XML file", xmlEditorResource.xmlFileIcon(), Constants.ESB_XML_EXTENSION);
        this.xmlEditorResource = xmlEditorResource;
    }

    /** {@inheritDoc} */
    @Override
    public void create(@NotNull String name, @NotNull final Folder parent, @NotNull final Project project,
                       @NotNull final AsyncCallback<Resource> callback) {
        String content = "<tag></tag>";
        project.createFile(parent, name + '.' + getExtension(), content, Constants.ESB_XML_MIME_TYPE, new AsyncCallback<File>() {
            @Override
            public void onSuccess(File file) {
                callback.onSuccess(file);
            }

            @Override
            public void onFailure(Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }
}