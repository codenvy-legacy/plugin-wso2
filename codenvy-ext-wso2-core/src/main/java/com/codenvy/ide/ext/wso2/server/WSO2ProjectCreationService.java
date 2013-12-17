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
package com.codenvy.ide.ext.wso2.server;

import com.codenvy.api.vfs.server.MountPoint;
import com.codenvy.api.vfs.server.VirtualFile;
import com.codenvy.api.vfs.server.VirtualFileSystemProvider;
import com.codenvy.api.vfs.server.VirtualFileSystemRegistry;
import com.codenvy.api.vfs.server.exceptions.InvalidArgumentException;
import com.codenvy.api.vfs.server.exceptions.VirtualFileSystemException;
import com.codenvy.api.vfs.shared.PropertyFilter;
import com.codenvy.api.vfs.shared.dto.Property;
import com.codenvy.commons.env.EnvironmentContext;
import com.codenvy.dto.server.DtoFactory;
import com.codenvy.ide.annotations.NotNull;
import com.codenvy.ide.ext.wso2.shared.ESBProjectInfo;

import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_CONFIGURATION_PROJECT_ID;
import static com.codenvy.ide.ext.wso2.shared.Constants.WSO2_PROJECT_ID;
import static com.codenvy.ide.resources.model.ProjectDescription.PROPERTY_MIXIN_NATURES;
import static com.codenvy.ide.resources.model.ProjectDescription.PROPERTY_PRIMARY_NATURE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * RESTful service for creating 'WSO2' projects.
 *
 * @author Andrey Plotnikov
 */
@Path("{ws-name}/templates")
public class WSO2ProjectCreationService {
    private static final Log LOG = ExoLogger.getLogger(WSO2ProjectCreationService.class);

    @Inject
    private VirtualFileSystemRegistry vfsRegistry;

    @Path("esbconf")
    @POST
    @Consumes(APPLICATION_JSON)
    public void createESBConfProject(ESBProjectInfo projectInfo) throws VirtualFileSystemException, IOException {
        ArrayList<Property> properties = new ArrayList<>();

        addProperty("vfs:mimeType", Collections.singletonList("text/vnd.ideproject+directory"), properties);
        addProperty("vfs:projectType", Collections.singletonList(WSO2_PROJECT_ID), properties);
        addProperty(PROPERTY_PRIMARY_NATURE, Collections.singletonList(WSO2_PROJECT_ID), properties);
        addProperty(PROPERTY_MIXIN_NATURES, Collections.singletonList(ESB_CONFIGURATION_PROJECT_ID), properties);
        addProperty("exoide:projectDescription", Collections.singletonList("ESB Configuration Project"), properties);

        String projectName = projectInfo.getProjectName();
        createProject(projectName, "templates/esbproject.zip", properties);

        MavenXpp3Reader pomReader = new MavenXpp3Reader();
        MavenXpp3Writer pomWriter = new MavenXpp3Writer();

        VirtualFileSystemProvider vfsProvider = vfsRegistry.getProvider(getVfsID());
        MountPoint mountPoint = vfsProvider.getMountPoint(false);
        VirtualFile pomFile = mountPoint.getVirtualFile(projectName + "/pom.xml");
        InputStream pomContent = pomFile.getContent().getStream();

        try {
            Model pom = pomReader.read(pomContent, false);

            pom.setGroupId(projectInfo.getGroupID());
            pom.setArtifactId(projectInfo.getArtifactID());
            pom.setVersion(projectInfo.getVersion());

            Parent parentPom = new Parent();
            parentPom.setVersion(projectInfo.getVersion());
            parentPom.setGroupId(projectInfo.getParentGroupID());
            parentPom.setArtifactId(projectInfo.getParentArtifactID());

            pom.setParent(parentPom);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pomWriter.write(stream, pom);

            pomFile.updateContent(pomFile.getMediaType(), new ByteArrayInputStream(stream.toByteArray()), null);
        } catch (XmlPullParserException e) {
            LOG.warn("Error occurred while setting project coordinates.", e);
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /** @return virtual file system id */
    private String getVfsID() {
        return (String)EnvironmentContext.getCurrent().getVariable(EnvironmentContext.WORKSPACE_ID);
    }

    /**
     * Adds property with given name and value in the given properties list.
     *
     * @param name
     *         name that need the property contains
     * @param values
     *         value that need the property contains
     * @param properties
     *         list where the property need to be added
     */
    private void addProperty(@NotNull String name, @NotNull List<String> values, @NotNull List<Property> properties) {
        Property property = DtoFactory.getInstance().createDto(Property.class).withName(name).withValue(values);
        properties.add(property);
    }

    /**
     * Create project from zip file.
     *
     * @param name
     *         name that the project need to has
     * @param templatePath
     *         place where zip file exists
     * @param properties
     *         properties those need to be applied
     * @throws VirtualFileSystemException
     * @throws IOException
     */
    private void createProject(@NotNull String name,
                               @NotNull String templatePath,
                               @NotNull List<Property> properties) throws VirtualFileSystemException, IOException {

        InputStream templateStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(templatePath);

        if (templateStream == null) {
            throw new InvalidArgumentException("Can't find template");
        }

        VirtualFileSystemProvider provider = vfsRegistry.getProvider(getVfsID());

        MountPoint mountPoint = provider.getMountPoint(false);
        VirtualFile root = mountPoint.getRoot();
        VirtualFile projectFolder = root.createFolder(name);
        projectFolder.unzip(templateStream, true);
        updateProperties(properties, projectFolder);
    }

    /**
     * Apply properties for a project.
     *
     * @param properties
     *         properties those need to be applied
     * @param projectFolder
     *         project where need to apply properties
     * @throws VirtualFileSystemException
     */
    private void updateProperties(@NotNull List<Property> properties, @NotNull VirtualFile projectFolder)
            throws VirtualFileSystemException {
        List<Property> propertyList = projectFolder.getProperties(PropertyFilter.ALL_FILTER);
        propertyList.addAll(properties);
        projectFolder.updateProperties(propertyList, null);
    }
}