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

import com.codenvy.api.core.ApiException;
import com.codenvy.api.vfs.server.MountPoint;
import com.codenvy.api.vfs.server.VirtualFile;
import com.codenvy.api.vfs.server.VirtualFileSystemProvider;
import com.codenvy.api.vfs.server.VirtualFileSystemRegistry;
import com.codenvy.ide.ext.wso2.shared.FileInfo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import static com.codenvy.ide.ext.wso2.shared.Constants.ENDPOINTS_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_XML_MIME_TYPE;
import static com.codenvy.ide.ext.wso2.shared.Constants.LOCAL_ENTRY_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.MAIN_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.OVERWRITE_FILE_OPERATION;
import static com.codenvy.ide.ext.wso2.shared.Constants.PROXY_SERVICE_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.RENAME_FILE_OPERATION;
import static com.codenvy.ide.ext.wso2.shared.Constants.SEQUENCE_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SRC_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SYNAPSE_CONFIG_FOLDER_NAME;
import static java.io.File.separator;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * RESTful service for creating 'WSO2' projects.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
@Path("/wso2/{ws-id}")
public class WSO2RestService {

    @Inject
    private VirtualFileSystemRegistry vfsRegistry;

    @PathParam("ws-id")
    private String wsId;

    @Path("detect")
    @POST
    @Consumes(APPLICATION_JSON)
    public void detectConfigurationFile(FileInfo fileInfo) throws ApiException {
        VirtualFileSystemProvider vfsProvider = vfsRegistry.getProvider(wsId);
        MountPoint mountPoint = vfsProvider.getMountPoint(false);

        VirtualFile file = mountPoint.getVirtualFile(fileInfo.getProjectName() + separator + fileInfo.getFileName());
        file.updateContent(ESB_XML_MIME_TYPE, file.getContent().getStream(), null);

        moveFile(file, mountPoint, fileInfo.getProjectName(), getParentFolderForImportingFile(file));
    }

    private void moveFile(@Nonnull VirtualFile file,
                          @Nonnull MountPoint mountPoint,
                          @Nonnull String projectName,
                          @Nonnull String parentFolder) throws ApiException {

        String pathToFolder = SRC_FOLDER_NAME + separator +
                              MAIN_FOLDER_NAME + separator +
                              SYNAPSE_CONFIG_FOLDER_NAME + separator +
                              parentFolder;

        String path = projectName + separator + pathToFolder;

        try {
            file.moveTo(mountPoint.getVirtualFile(path), null);
        } catch (ApiException e) {
            if (e.getMessage().endsWith("does not exists. ")) {
                mountPoint.getVirtualFile(projectName).createFolder(pathToFolder);

                file.moveTo(mountPoint.getVirtualFile(path), null);

                return;
            }

            throw new ApiException(e.toString(), e);
        }
    }

    @Path("upload")
    @POST
    @Consumes(APPLICATION_JSON)
    public void uploadConfigurationFile(FileInfo fileInfo) throws ApiException {
        VirtualFileSystemProvider vfsProvider = vfsRegistry.getProvider(wsId);
        MountPoint mountPoint = vfsProvider.getMountPoint(false);

        String projectName = fileInfo.getProjectName();
        String filePath = fileInfo.getFileName();

        VirtualFile projectParent = mountPoint.getVirtualFile(projectName);

        String[] pathElements = filePath.split("/");
        String fileName = pathElements[pathElements.length - 1];

        try (InputStream is = URI.create(filePath).toURL().openStream()) {
            VirtualFile file = projectParent.createFile(fileName, ESB_XML_MIME_TYPE, is);

            moveFile(file, mountPoint, projectName, getParentFolderForImportingFile(file));
        } catch (IOException e) {
            throw new ApiException(e.toString(), e);
        }
    }

    @Path("file/{operation}")
    @POST
    @Consumes(APPLICATION_JSON)
    public void overwriteConfigurationFile(@PathParam("operation") String operation, FileInfo fileInfo) throws ApiException {
        VirtualFileSystemProvider vfsProvider = vfsRegistry.getProvider(wsId);
        MountPoint mountPoint = vfsProvider.getMountPoint(false);

        VirtualFile file = mountPoint.getVirtualFile(fileInfo.getProjectName() + separator + fileInfo.getFileName());
        String parentFolder = getParentFolderForImportingFile(file);

        VirtualFile oldFile = mountPoint.getVirtualFile(fileInfo.getProjectName() + separator +
                                                        SRC_FOLDER_NAME + separator +
                                                        MAIN_FOLDER_NAME + separator +
                                                        SYNAPSE_CONFIG_FOLDER_NAME + separator +
                                                        parentFolder + separator +
                                                        fileInfo.getFileName());

        switch (operation) {
            case RENAME_FILE_OPERATION:
                renameFile(fileInfo, mountPoint, file, parentFolder);
                break;

            case OVERWRITE_FILE_OPERATION:
                oldFile.updateContent(oldFile.getMediaType(), file.getContent().getStream(), null);
                file.delete(null);
                break;

            default:
                file.delete(null);
        }
    }

    private void renameFile(@Nonnull FileInfo fileInfo,
                            @Nonnull MountPoint mountPoint,
                            @Nonnull VirtualFile file,
                            @Nonnull String parentFolder) throws ApiException {

        file.rename(fileInfo.getNewFileName(), file.getMediaType(), null);
        file = mountPoint.getVirtualFile(fileInfo.getProjectName() + separator + fileInfo.getNewFileName());
        moveFile(file, mountPoint, fileInfo.getProjectName(), parentFolder);
    }

    /**
     * Determines parent folder for importing file.
     *
     * @param virtualFile
     *         importing file
     * @return parent folder for file or empty string if file is not esb configuration
     */
    private String getParentFolderForImportingFile(@Nonnull VirtualFile virtualFile) throws ApiException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        String parentFolder;

        try (InputStream fileContent = virtualFile.getContent().getStream()) {
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
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            virtualFile.delete(null);
            throw new IllegalStateException(e.getMessage(), e);
        }

        return parentFolder;
    }

}