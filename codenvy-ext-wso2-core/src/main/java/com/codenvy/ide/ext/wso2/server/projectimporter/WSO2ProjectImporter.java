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
package com.codenvy.ide.ext.wso2.server.projectimporter;

import com.codenvy.api.core.ConflictException;
import com.codenvy.api.core.ForbiddenException;
import com.codenvy.api.core.ServerException;
import com.codenvy.api.core.UnauthorizedException;
import com.codenvy.api.core.util.LineConsumerFactory;
import com.codenvy.api.project.server.FolderEntry;
import com.codenvy.api.project.server.VirtualFileEntry;
import com.codenvy.ide.ext.git.server.GitConnectionFactory;
import com.codenvy.ide.ext.git.server.GitProjectImporter;
import com.codenvy.vfs.impl.fs.LocalPathResolver;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.IOException;
import java.util.Map;

import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_XML_MIME_TYPE;
import static com.codenvy.ide.ext.wso2.shared.Constants.FACTORY_IMPORTER_ID;

/**
 * Provide possibility for importing WSO2 ESB.
 *
 * @author Valeriy Svydenko
 */
@Singleton
public class WSO2ProjectImporter extends GitProjectImporter {

    @Inject
    public WSO2ProjectImporter(GitConnectionFactory gitConnectionFactory, LocalPathResolver localPathResolver) {
        super(gitConnectionFactory, localPathResolver);
    }

    /** {@inheritDoc} */
    @Override
    public String getId() {
        return FACTORY_IMPORTER_ID;
    }

    /** {@inheritDoc} */
    @Override
    public void importSources(FolderEntry baseFolder, String location, Map<String, String> parameters, LineConsumerFactory consumerFactory)
            throws ForbiddenException, ConflictException, UnauthorizedException, IOException, ServerException {

        super.importSources(baseFolder, location, parameters, consumerFactory);

        recTreeView(baseFolder);
    }

    private void recTreeView(FolderEntry parent) throws ServerException {
        for (VirtualFileEntry virtualFileEntry : parent.getChildren()) {
            if (virtualFileEntry.isFile()) {
                setESBMimeTypeForConfigurationFiles(virtualFileEntry);
            } else {
                recTreeView((FolderEntry)virtualFileEntry);
            }
        }
    }

    private void setESBMimeTypeForConfigurationFiles(VirtualFileEntry virtualFileEntry) throws ServerException {
        String fileName = virtualFileEntry.getName();
        if (fileName.endsWith(".xml") && !("pom.xml".equals(fileName))) {
            virtualFileEntry.getVirtualFile().setMediaType(ESB_XML_MIME_TYPE);
        }
    }
}
