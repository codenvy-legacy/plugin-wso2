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

import com.codenvy.api.vfs.server.ContentStream;
import com.codenvy.api.vfs.server.VirtualFile;
import com.codenvy.api.vfs.server.VirtualFileSystemRegistry;
import com.codenvy.api.vfs.shared.dto.Property;
import com.codenvy.dto.server.DtoFactory;
import com.codenvy.ide.ext.wso2.shared.ESBProjectInfo;
import com.codenvy.ide.ext.wso2.shared.FileInfo;

import org.everrest.core.RequestHandler;
import org.everrest.core.ResourceBinder;
import org.everrest.core.impl.ApplicationContextImpl;
import org.everrest.core.impl.ApplicationProviderBinder;
import org.everrest.core.impl.ContainerResponse;
import org.everrest.core.impl.EverrestConfiguration;
import org.everrest.core.impl.ProviderBinder;
import org.everrest.core.impl.RequestDispatcher;
import org.everrest.core.impl.RequestHandlerImpl;
import org.everrest.core.impl.ResourceBinderImpl;
import org.everrest.core.tools.DependencySupplierImpl;
import org.everrest.core.tools.ResourceLauncher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Testing {@link WSO2RestService} functionality.
 *
 * @author Andrey Plotnikov
 */
@RunWith(MockitoJUnitRunner.class)
public class WSO2RestServiceTest {

    public static final String BASE_URI                      = "http://localhost";
    public static final String POM_CONTENT                   = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                                               "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 " +
                                                               "http://maven.apache" +
                                                               ".org/xsd/maven-4.0.0.xsd\"\n" +
                                                               "         xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
                                                               "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                                                               "</project>";
    public static final String SYNAPSE_CONFIGURATION_CONTENT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                                               "<sequence xmlns=\"http://ws.apache.org/ns/synapse\" name=\"s\"></sequence>";

    @Mock(answer = RETURNS_DEEP_STUBS)
    private VirtualFileSystemRegistry vfsRegistry;
    @InjectMocks
    private WSO2RestService           service;
    private ResourceLauncher          launcher;

    @Before
    public void setUp() throws Exception {
        DependencySupplierImpl dependencies = new DependencySupplierImpl();
        dependencies.addComponent(VirtualFileSystemRegistry.class, vfsRegistry);

        ResourceBinder resources = new ResourceBinderImpl();
        resources.addResource(WSO2RestService.class, null);

        ProviderBinder providers = new ApplicationProviderBinder();
        RequestHandler requestHandler = new RequestHandlerImpl(new RequestDispatcher(resources),
                                                               providers,
                                                               dependencies,
                                                               new EverrestConfiguration());
        ApplicationContextImpl.setCurrent(new ApplicationContextImpl(null, null, ProviderBinder.getInstance()));

        launcher = new ResourceLauncher(requestHandler);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void projectShouldBeCreated() throws Exception {
        String projectName = "projectName";

        ESBProjectInfo projectInfo =
                DtoFactory.getInstance().createDto(ESBProjectInfo.class).withProjectName(projectName).withGroupID("groupID")
                          .withArtifactID("artifactID").withVersion("version");

        InputStream is = new ByteArrayInputStream(POM_CONTENT.getBytes());
        ContentStream contentStream = new ContentStream(null, is, null);

        VirtualFile projectFolder = mock(VirtualFile.class, RETURNS_MOCKS);
        VirtualFile rootFolder = mock(VirtualFile.class, RETURNS_MOCKS);
        VirtualFile pomFile = mock(VirtualFile.class, RETURNS_MOCKS);

        when(vfsRegistry.getProvider(anyString()).getMountPoint(anyBoolean()).getRoot()).thenReturn(rootFolder);
        when(rootFolder.createFolder(anyString())).thenReturn(projectFolder);
        when(vfsRegistry.getProvider(anyString()).getMountPoint(anyBoolean()).getVirtualFile(anyString())).thenReturn(pomFile);
        when(pomFile.getContent()).thenReturn(contentStream);

        Map<String, List<String>> headers = new HashMap<>(1);
        headers.put("Content-Type", Arrays.asList("application/json"));

        byte[] data = DtoFactory.getInstance().toJson(projectInfo).getBytes();

        ContainerResponse response = launcher.service("POST", "/dev-monit/wso2/templates/esbconf", BASE_URI, headers, data, null);

        assertEquals(204, response.getStatus());

        verify(rootFolder).createFolder(eq(projectName));

        verify(projectFolder).unzip((InputStream)anyObject(), eq(true));
        verify(projectFolder).updateProperties((List<Property>)anyObject(), eq((String)null));

        verify(pomFile).updateContent(anyString(), (InputStream)anyObject(), eq((String)null));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void fileShouldBeDetected() throws Exception {
        String projectName = "projectName";
        String fileName = "fileName";

        FileInfo fileInfo = DtoFactory.getInstance().createDto(FileInfo.class).withFileName(fileName).withNewFileName(fileName)
                                      .withProjectName(projectName);

        InputStream is = new ByteArrayInputStream(SYNAPSE_CONFIGURATION_CONTENT.getBytes());
        ContentStream contentStream = new ContentStream(null, is, null);

        VirtualFile synapseFile = mock(VirtualFile.class, RETURNS_MOCKS);

        when(vfsRegistry.getProvider(anyString()).getMountPoint(anyBoolean()).getVirtualFile(anyString())).thenReturn(synapseFile);
        when(synapseFile.getContent()).thenReturn(contentStream);

        Map<String, List<String>> headers = new HashMap<>(1);
        headers.put("Content-Type", Arrays.asList("application/json"));

        byte[] data = DtoFactory.getInstance().toJson(fileInfo).getBytes();

        ContainerResponse response = launcher.service("POST", "/dev-monit/wso2/detect", BASE_URI, headers, data, null);

        assertEquals(200, response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void fileShouldBeUpload() throws Exception {
        String projectName = "projectName";
        String fileName = "fileName";

        FileInfo fileInfo = DtoFactory.getInstance().createDto(FileInfo.class).withFileName(fileName).withNewFileName(fileName)
                                      .withProjectName(projectName);

        InputStream is = new ByteArrayInputStream(SYNAPSE_CONFIGURATION_CONTENT.getBytes());
        ContentStream contentStream = new ContentStream(null, is, null);

        VirtualFile synapseFile = mock(VirtualFile.class, RETURNS_MOCKS);

        when(vfsRegistry.getProvider(anyString()).getMountPoint(anyBoolean()).getVirtualFile(anyString())).thenReturn(synapseFile);
        when(synapseFile.getContent()).thenReturn(contentStream);

        Map<String, List<String>> headers = new HashMap<>(1);
        headers.put("Content-Type", Arrays.asList("application/json"));

        byte[] data = DtoFactory.getInstance().toJson(fileInfo).getBytes();

        ContainerResponse response = launcher.service("POST", "/dev-monit/wso2/file/delete", BASE_URI, headers, data, null);

        assertEquals(200, response.getStatus());
    }
}