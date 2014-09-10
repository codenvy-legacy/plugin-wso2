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
package com.codenvy.ide.ext.wso2.server.rest;

import com.codenvy.api.core.ConflictException;
import com.codenvy.api.core.ForbiddenException;
import com.codenvy.api.core.NotFoundException;
import com.codenvy.api.core.ServerException;
import com.codenvy.api.vfs.server.MountPoint;
import com.codenvy.api.vfs.server.VirtualFile;
import com.codenvy.api.vfs.server.VirtualFileSystemProvider;
import com.codenvy.api.vfs.server.VirtualFileSystemRegistry;
import com.codenvy.commons.env.EnvironmentContext;
import com.codenvy.ide.ext.wso2.shared.FileInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import static com.codenvy.ide.ext.wso2.shared.Constants.ENDPOINTS_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_XML_MIME_TYPE;
import static com.codenvy.ide.ext.wso2.shared.Constants.LOCAL_ENTRY_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.MAIN_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.PROXY_SERVICE_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SEQUENCE_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SRC_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SYNAPSE_CONFIG_FOLDER_NAME;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_HTML;

/**
 * RESTful service for creating 'WSO2' projects.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
@Path("/wso2/{ws-id}")
public class WSO2RestService {
    private static final Logger LOG = LoggerFactory.getLogger(WSO2RestService.class);

    @Inject
    private VirtualFileSystemRegistry vfsRegistry;

    @Path("detect")
    @POST
    @Consumes(APPLICATION_JSON)
    public Response detectConfigurationFile(FileInfo fileInfo) throws ServerException,
                                                                      NotFoundException,
                                                                      ForbiddenException,
                                                                      ConflictException {
        VirtualFileSystemProvider vfsProvider = vfsRegistry.getProvider(getVfsID());
        MountPoint mountPoint = vfsProvider.getMountPoint(false);

        VirtualFile file = mountPoint.getVirtualFile(fileInfo.getProjectName() + File.separator + fileInfo.getFileName());
        file.updateContent(ESB_XML_MIME_TYPE, file.getContent().getStream(), null);

        String result = moveFile(file, mountPoint, fileInfo.getProjectName(), getParentFolderForImportingFile(file));

        return Response.ok(result, TEXT_HTML).build();
    }

    private String moveFile(@Nonnull VirtualFile file,
                            @Nonnull MountPoint mountPoint,
                            @Nonnull String projectName,
                            @Nonnull String parentFolder) throws ServerException, NotFoundException, ForbiddenException, ConflictException {

        String pathToFolder = SRC_FOLDER_NAME + File.separator +
                              MAIN_FOLDER_NAME + File.separator +
                              SYNAPSE_CONFIG_FOLDER_NAME + File.separator +
                              parentFolder;

        String path = projectName + File.separator + pathToFolder;

        try {

            file.moveTo(mountPoint.getVirtualFile(path), null);

        } catch (ForbiddenException | NotFoundException | ConflictException | ServerException e) {

            if (e.getMessage().endsWith("does not exists. ")) {
                mountPoint.getVirtualFile(projectName).createFolder(pathToFolder);

                file.moveTo(mountPoint.getVirtualFile(path), null);

                return parentFolder;
            }

            return e.getMessage();
        }

        return parentFolder;
    }

    @Path("upload")
    @POST
    @Consumes(APPLICATION_JSON)
    public Response uploadConfigurationFile(FileInfo fileInfo) throws ServerException,
                                                                      NotFoundException,
                                                                      ForbiddenException,
                                                                      ConflictException {
        VirtualFileSystemProvider vfsProvider = vfsRegistry.getProvider(getVfsID());
        MountPoint mountPoint = vfsProvider.getMountPoint(false);

        String projectName = fileInfo.getProjectName();
        String filePath = fileInfo.getFileName();

        VirtualFile projectParent = mountPoint.getVirtualFile(projectName);

        String[] pathElements = filePath.split("/");
        String fileName = pathElements[pathElements.length - 1];

        String parentFolder;

        try {
            InputStream is = URI.create(filePath).toURL().openStream();
            VirtualFile file = projectParent.createFile(fileName, ESB_XML_MIME_TYPE, is);

            parentFolder = moveFile(file, mountPoint, projectName, getParentFolderForImportingFile(file));

        } catch (IOException e) {
            LOG.error("Can't create " + fileName + " file", e);
            parentFolder = e.getMessage();
        }

        return Response.ok(parentFolder, TEXT_HTML).build();
    }

    @Path("file/{operation}")
    @POST
    @Consumes(APPLICATION_JSON)
    public Response overwriteConfigurationFile(@PathParam("operation") String operation, FileInfo fileInfo)
            throws ServerException, NotFoundException, ForbiddenException, ConflictException {

        VirtualFileSystemProvider vfsProvider = vfsRegistry.getProvider(getVfsID());
        MountPoint mountPoint = vfsProvider.getMountPoint(false);

        VirtualFile file = mountPoint.getVirtualFile(fileInfo.getProjectName() + File.separator + fileInfo.getFileName());
        String parentFolder = getParentFolderForImportingFile(file);

        VirtualFile oldFile = mountPoint.getVirtualFile(fileInfo.getProjectName() + File.separator +
                                                        SRC_FOLDER_NAME + File.separator +
                                                        MAIN_FOLDER_NAME + File.separator +
                                                        SYNAPSE_CONFIG_FOLDER_NAME + File.separator +
                                                        parentFolder + File.separator +
                                                        fileInfo.getFileName());

        switch (operation) {
            case "rename":
                file.rename(fileInfo.getNewFileName(), file.getMediaType(), null);
                file = mountPoint.getVirtualFile(fileInfo.getProjectName() + File.separator + fileInfo.getNewFileName());
                moveFile(file, mountPoint, fileInfo.getProjectName(), parentFolder);
                break;

            case "overwrite":
                oldFile.updateContent(oldFile.getMediaType(), file.getContent().getStream(), null);

            default:
                file.delete(null);
        }

        return Response.ok(parentFolder, TEXT_HTML).build();
    }

    /**
     * Determines parent folder for importing file.
     *
     * @param virtualFile
     *         importing file
     * @return parent folder for file or empty string if file is not esb configuration
     */
    private String getParentFolderForImportingFile(@Nonnull VirtualFile virtualFile) throws ServerException, ForbiddenException {
        InputStream fileContent = virtualFile.getContent().getStream();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        String parentFolder;

        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document document = dBuilder.parse(fileContent);
            Element documentElement = document.getDocumentElement();

            documentElement.normalize();

            switch (documentElement.getNodeName()) {
                case "endpoint":
                    parentFolder = ENDPOINTS_FOLDER_NAME;
                    break;

                case "sequence":
                    parentFolder = SEQUENCE_FOLDER_NAME;
                    break;

                case "proxy":
                    parentFolder = PROXY_SERVICE_FOLDER_NAME;
                    break;

                case "localEntry":
                    parentFolder = LOCAL_ENTRY_FOLDER_NAME;
                    break;

                default:
                    parentFolder = "";
                    break;
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            virtualFile.delete(null);
            throw new IllegalStateException(e);
        }

        return parentFolder;
    }

    /** @return virtual file system id */
    private String getVfsID() {
        return EnvironmentContext.getCurrent().getWorkspaceId();
    }

}