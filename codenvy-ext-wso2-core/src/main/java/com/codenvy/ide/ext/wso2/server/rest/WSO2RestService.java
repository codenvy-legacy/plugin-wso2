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
package com.codenvy.ide.ext.wso2.server.rest;

import com.codenvy.api.vfs.server.MountPoint;
import com.codenvy.api.vfs.server.VirtualFile;
import com.codenvy.api.vfs.server.VirtualFileSystemProvider;
import com.codenvy.api.vfs.server.VirtualFileSystemRegistry;
import com.codenvy.api.vfs.server.exceptions.VirtualFileSystemException;
import com.codenvy.commons.env.EnvironmentContext;
import com.codenvy.dto.server.DtoFactory;
import com.codenvy.ide.ext.git.server.provider.rest.ProviderException;
import com.codenvy.ide.ext.git.shared.GitUrlVendorInfo;
import com.codenvy.ide.ext.wso2.server.WSO2;
import com.codenvy.ide.ext.wso2.shared.FileInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import static com.codenvy.commons.env.EnvironmentContext.WORKSPACE_ID;
import static com.codenvy.ide.ext.wso2.shared.Constants.ENDPOINTS_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_XML_MIME_TYPE;
import static com.codenvy.ide.ext.wso2.shared.Constants.LOCAL_ENTRY_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.MAIN_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.PROXY_SERVICE_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SEQUENCE_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SRC_FOLDER_NAME;
import static com.codenvy.ide.ext.wso2.shared.Constants.SYNAPSE_CONFIG_FOLDER_NAME;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * RESTful service for creating 'WSO2' projects.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
@Path("/wso2/{ws-name}")
public class WSO2RestService {
    private static final Logger LOG = LoggerFactory.getLogger(WSO2RestService.class);

    @Inject
    private VirtualFileSystemRegistry vfsRegistry;
    @Inject
    private WSO2                      wso2;

    @Path("detect")
    @POST
    @Consumes(APPLICATION_JSON)
    public Response detectConfigurationFile(FileInfo fileInfo) throws VirtualFileSystemException {
        VirtualFileSystemProvider vfsProvider = vfsRegistry.getProvider(getVfsID());
        MountPoint mountPoint = vfsProvider.getMountPoint(false);
        VirtualFile file = mountPoint.getVirtualFile(fileInfo.getProjectName() + "/" + fileInfo.getFileName());

        String result = moveFile(file, mountPoint, fileInfo.getProjectName(), getParentFolderForImportingFile(file));

        return Response.ok(result, MediaType.TEXT_HTML).build();
    }

    private String moveFile(@NotNull VirtualFile file,
                            @NotNull MountPoint mountPoint,
                            @NotNull String projectName,
                            @NotNull String parentFolder)
            throws VirtualFileSystemException {
        String pathToFolder = SRC_FOLDER_NAME + "/" + MAIN_FOLDER_NAME + "/" + SYNAPSE_CONFIG_FOLDER_NAME + "/" +
                              parentFolder;
        try {
            file.moveTo(mountPoint.getVirtualFile(projectName + "/" + pathToFolder), null);
        } catch (VirtualFileSystemException e) {
            if (e.getMessage().endsWith("does not exists. ")) {
                mountPoint.getVirtualFile(projectName).createFolder(pathToFolder);
                file.moveTo(mountPoint.getVirtualFile(projectName + "/" + pathToFolder), null);
                return parentFolder;
            }
            return e.getMessage();
        }
        return parentFolder;
    }

    @Path("upload")
    @POST
    @Consumes(APPLICATION_JSON)
    public Response uploadConfigurationFile(FileInfo fileInfo) throws VirtualFileSystemException {
        VirtualFileSystemProvider vfsProvider = vfsRegistry.getProvider(getVfsID());
        MountPoint mountPoint = vfsProvider.getMountPoint(false);
        VirtualFile projectParent = mountPoint.getVirtualFile(fileInfo.getProjectName());

        String filePath = fileInfo.getFileName();
        String[] pathElements = filePath.split("/");
        String fileName = pathElements[pathElements.length - 1];

        String parentFolder = "";

        try {
            InputStream is = URI.create(fileInfo.getFileName()).toURL().openStream();
            VirtualFile file = projectParent.createFile(fileName, ESB_XML_MIME_TYPE, is);

            parentFolder = moveFile(file, mountPoint, fileInfo.getProjectName(), getParentFolderForImportingFile(file));

        } catch (IOException e) {
            LOG.error("Can't create " + fileName + " file", e);
            parentFolder = e.getMessage();
        }
        return Response.ok(parentFolder, MediaType.TEXT_HTML).build();
    }

    @Path("file/{operation}")
    @POST
    @Consumes(APPLICATION_JSON)
    public Response overwriteConfigurationFile(@PathParam("operation") String operation, FileInfo fileInfo)
            throws VirtualFileSystemException {
        VirtualFileSystemProvider vfsProvider = vfsRegistry.getProvider(getVfsID());
        MountPoint mountPoint = vfsProvider.getMountPoint(false);
        VirtualFile file = mountPoint.getVirtualFile(fileInfo.getProjectName() + "/" + fileInfo.getFileName());
        String parentFolder = getParentFolderForImportingFile(file);
        VirtualFile oldFile = mountPoint.getVirtualFile(
                fileInfo.getProjectName() + "/" + SRC_FOLDER_NAME + "/" + MAIN_FOLDER_NAME + "/" + SYNAPSE_CONFIG_FOLDER_NAME + "/" +
                parentFolder + "/" + fileInfo.getFileName());
        switch (operation) {
            case "overwrite":
                oldFile.updateContent(oldFile.getMediaType(), file.getContent().getStream(), null);
                file.delete(null);
                break;
            case "rename":
                file.rename(fileInfo.getNewFileName(), file.getMediaType(), null);
                file = mountPoint.getVirtualFile(fileInfo.getProjectName() + "/" + fileInfo.getNewFileName());
                moveFile(file, mountPoint, fileInfo.getProjectName(), parentFolder);
                break;
            default:
                file.delete(null);
                break;
        }
        return Response.ok(parentFolder, MediaType.TEXT_HTML).build();
    }

    @GET
    @Path("info")
    @Produces(APPLICATION_JSON)
    public GitUrlVendorInfo getInfo() throws ProviderException {
        return DtoFactory.getInstance().createDto(GitUrlVendorInfo.class)
                         .withVendorName(wso2.getVendorName())
                         .withVendorBaseHost(wso2.getVendorBaseHost())
                         .withOAuthScopes(wso2.getVendorOAuthScopes());
    }

    /**
     * Determines parent folder for importing file.
     *
     * @param virtualFile
     *         importing file
     * @return parent folder for file or empty string if file is not esb configuration
     */
    private String getParentFolderForImportingFile(VirtualFile virtualFile) throws VirtualFileSystemException {
        InputStream fileContent = virtualFile.getContent().getStream();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        String parentFolder;

        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(fileContent);
            document.getDocumentElement().normalize();

            String rootNode = document.getDocumentElement().getNodeName();
            switch (rootNode) {
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
        return (String)EnvironmentContext.getCurrent().getVariable(WORKSPACE_ID);
    }

}