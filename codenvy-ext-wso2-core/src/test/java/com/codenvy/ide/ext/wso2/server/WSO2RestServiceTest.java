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
import com.codenvy.api.vfs.server.exceptions.VirtualFileSystemException;
import com.codenvy.api.vfs.shared.dto.Property;
import com.codenvy.dto.server.DtoFactory;
import com.codenvy.ide.ext.git.shared.GitUrlVendorInfo;
import com.codenvy.ide.ext.wso2.server.rest.WSO2RestService;
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
import org.junit.Ignore;
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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

    public static final String PROJECT_NAME                  = "projectName";
    public static final String FILE_NAME                     = "fileName";
    public static final String BASE_URI                      = "http://localhost";
    public static final String POM_CONTENT                   = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                                               "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 " +
                                                               "http://maven.apache" +
                                                               ".org/xsd/maven-4.0.0.xsd\"\n" +
                                                               "         xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
                                                               "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                                                               "</project>";
    public static final String SYNAPSE_CONFIGURATION_CONTENT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                                               "<sequence></sequence>";

    @Mock(answer = RETURNS_DEEP_STUBS)
    private VirtualFileSystemRegistry vfsRegistry;
    @Mock
    private WSO2                      wso2;
    @Mock
    private VirtualFile               synapseFile;
    @InjectMocks
    private WSO2RestService           service;
    private ResourceLauncher          launcher;

    @Before
    public void setUp() throws Exception {
        DependencySupplierImpl dependencies = new DependencySupplierImpl();
        dependencies.addComponent(VirtualFileSystemRegistry.class, vfsRegistry);
        dependencies.addComponent(WSO2.class, wso2);

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

    private ContainerResponse prepareResponseLauncherService(String method, String path, byte[] data) throws Exception {
        Map<String, List<String>> headers = new HashMap<>(1);
        headers.put("Content-Type", Arrays.asList("application/json"));

        return launcher.service(method, "/wso2/dev-monit/" + path, BASE_URI, headers, data, null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void projectShouldBeCreated() throws Exception {
        ESBProjectInfo projectInfo =
                DtoFactory.getInstance().createDto(ESBProjectInfo.class).withProjectName(PROJECT_NAME).withGroupID("groupID")
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

        when(vfsRegistry.getProvider(anyString()).getMountPoint(anyBoolean()).getRoot()).thenReturn(rootFolder);
        when(rootFolder.createFolder(anyString())).thenReturn(projectFolder);

        byte[] data = DtoFactory.getInstance().toJson(projectInfo).getBytes();

        ContainerResponse response = prepareResponseLauncherService("POST", "templates/esbconf", data);

        assertEquals(204, response.getStatus());

        verify(rootFolder).createFolder(eq(PROJECT_NAME));

        verify(projectFolder).unzip((InputStream)anyObject(), eq(true));
        verify(projectFolder).updateProperties((List<Property>)anyObject(), eq((String)null));

        verify(pomFile).updateContent(anyString(), (InputStream)anyObject(), eq((String)null));
    }

    private void prepareForFileModificationRequestTest() throws VirtualFileSystemException {
        InputStream is = new ByteArrayInputStream(SYNAPSE_CONFIGURATION_CONTENT.getBytes());
        ContentStream contentStream = new ContentStream(null, is, null);

        when(vfsRegistry.getProvider(anyString()).getMountPoint(anyBoolean()).getVirtualFile(anyString())).thenReturn(synapseFile);
        when(vfsRegistry.getProvider(anyString()).getMountPoint(anyBoolean()).getVirtualFile(anyString()).getName())
                .thenReturn("synapseName");
        when(synapseFile.getContent()).thenReturn(contentStream);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void fileShouldBeDetected() throws Exception {
        prepareForFileModificationRequestTest();

        FileInfo fileInfo = DtoFactory.getInstance().createDto(FileInfo.class).withFileName(FILE_NAME).withNewFileName(FILE_NAME)
                                      .withProjectName(PROJECT_NAME);

        byte[] data = DtoFactory.getInstance().toJson(fileInfo).getBytes();

        ContainerResponse response = prepareResponseLauncherService("POST", "detect", data);

        assertEquals(200, response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void fileShouldBeDeleted() throws Exception {
        prepareForFileModificationRequestTest();

        FileInfo fileInfo = DtoFactory.getInstance().createDto(FileInfo.class).withFileName(FILE_NAME).withNewFileName(FILE_NAME)
                                      .withProjectName(PROJECT_NAME);

        byte[] data = DtoFactory.getInstance().toJson(fileInfo).getBytes();

        ContainerResponse response = prepareResponseLauncherService("POST", "file/delete", data);

        verify(synapseFile).delete(anyString());
        assertEquals(200, response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void fileShouldBeUploadWithRenamingName() throws Exception {
        prepareForFileModificationRequestTest();

        FileInfo fileInfo = DtoFactory.getInstance().createDto(FileInfo.class).withFileName(FILE_NAME).withNewFileName(FILE_NAME)
                                      .withProjectName(PROJECT_NAME);

        byte[] data = DtoFactory.getInstance().toJson(fileInfo).getBytes();

        ContainerResponse response = prepareResponseLauncherService("POST", "file/rename", data);

        verify(synapseFile).rename(eq(FILE_NAME), anyString(), anyString());
        assertEquals(200, response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void fileShouldBeUploadWithSomeProblem() throws Exception {
        prepareForFileModificationRequestTest();

        FileInfo fileInfo = DtoFactory.getInstance().createDto(FileInfo.class).withFileName(FILE_NAME).withNewFileName(FILE_NAME)
                                      .withProjectName(PROJECT_NAME);

        byte[] data = DtoFactory.getInstance().toJson(fileInfo).getBytes();

        ContainerResponse response = prepareResponseLauncherService("POST", "upload", data);

        assertEquals(500, response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Ignore
    @Test
    public void fileShouldBeUpload() throws Exception {
        prepareForFileModificationRequestTest();
        //TODO need some url only with xml content.
        String fileForUpload = "http://www.w3schools.com/xml/note.xml";

        FileInfo fileInfo = DtoFactory.getInstance().createDto(FileInfo.class).withFileName(fileForUpload).withNewFileName(FILE_NAME)
                                      .withProjectName(PROJECT_NAME);

        byte[] data = DtoFactory.getInstance().toJson(fileInfo).getBytes();

        ContainerResponse response = prepareResponseLauncherService("POST", "upload", data);

        assertEquals(200, response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void fileShouldBeDetectedWithSomeProblem() throws Exception {
        prepareForFileModificationRequestTest();
        when(vfsRegistry.getProvider(anyString()).getMountPoint(anyBoolean()).getVirtualFile(anyString())).thenReturn(null);

        FileInfo fileInfo = DtoFactory.getInstance().createDto(FileInfo.class).withFileName(FILE_NAME).withNewFileName(FILE_NAME)
                                      .withProjectName(PROJECT_NAME);

        byte[] data = DtoFactory.getInstance().toJson(fileInfo).getBytes();

        ContainerResponse response = prepareResponseLauncherService("POST", "detect", data);

        assertEquals(500, response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void fileShouldBeOverwrittenWithSomeProblem() throws Exception {
        prepareForFileModificationRequestTest();
        when(vfsRegistry.getProvider(anyString()).getMountPoint(anyBoolean()).getVirtualFile(anyString())).thenReturn(null);

        FileInfo fileInfo = DtoFactory.getInstance().createDto(FileInfo.class).withFileName(FILE_NAME).withNewFileName(FILE_NAME)
                                      .withProjectName(PROJECT_NAME);

        byte[] data = DtoFactory.getInstance().toJson(fileInfo).getBytes();

        ContainerResponse response = prepareResponseLauncherService("POST", "file/overwrite", data);

        assertEquals(500, response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void fileShouldBeUploadWithOverwritingContent() throws Exception {
        prepareForFileModificationRequestTest();

        FileInfo fileInfo = DtoFactory.getInstance().createDto(FileInfo.class).withFileName(FILE_NAME).withNewFileName(FILE_NAME)
                                      .withProjectName(PROJECT_NAME);

        byte[] data = DtoFactory.getInstance().toJson(fileInfo).getBytes();

        ContainerResponse response = prepareResponseLauncherService("POST", "file/overwrite", data);

        verify(synapseFile).updateContent(anyString(), (InputStream)anyObject(), anyString());
        verify(synapseFile).delete(anyString());
        assertEquals(200, response.getStatus());
    }

    @Test
    public void informationOfWSO2ConfigurationShouldBeReturned() throws Exception {
        String vendorName = "vendorName";
        String vendorBaseHost = "vendorBaseHost";
        List<String> vendorOAuthScopes = Arrays.asList("vendorOAuthScopes");

        when(wso2.getVendorName()).thenReturn(vendorName);
        when(wso2.getVendorBaseHost()).thenReturn(vendorBaseHost);
        when(wso2.getVendorOAuthScopes()).thenReturn(vendorOAuthScopes);

        ContainerResponse response = prepareResponseLauncherService("GET", "info", null);

        GitUrlVendorInfo info = (GitUrlVendorInfo)response.getEntity();

        assertEquals(200, response.getStatus());

        assertEquals(info.getVendorName(), vendorName);
        assertEquals(info.getVendorBaseHost(), vendorBaseHost);
        assertArrayEquals(info.getOAuthScopes().toArray(), vendorOAuthScopes.toArray());
        assertFalse(info.isGivenUrlSSH());
    }
}