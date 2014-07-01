/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.ext.wso2.server;

import com.codenvy.api.vfs.server.ContentStream;
import com.codenvy.api.vfs.server.VirtualFile;
import com.codenvy.api.vfs.server.VirtualFileSystemRegistry;
import com.codenvy.api.vfs.server.exceptions.VirtualFileSystemException;
import com.codenvy.dto.server.DtoFactory;
import com.codenvy.ide.ext.wso2.server.rest.WSO2RestService;
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

import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_XML_MIME_TYPE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Testing {@link WSO2RestService} functionality.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
@RunWith(MockitoJUnitRunner.class)
public class WSO2RestServiceTest {

    public static final String PROJECT_NAME                  = "projectName";
    public static final String FILE_NAME                     = "fileName";
    public static final String BASE_URI                      = "http://localhost";
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
        verify(synapseFile).updateContent(eq(ESB_XML_MIME_TYPE), (InputStream)anyObject(), anyString());
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
    public void fileShouldBeNotDetectWhenVirtualFileSystemExceptionHappened() throws Exception {
        prepareForFileModificationRequestTest();

        FileInfo fileInfo = DtoFactory.getInstance().createDto(FileInfo.class).withFileName(FILE_NAME).withNewFileName(FILE_NAME)
                                      .withProjectName(PROJECT_NAME);

        byte[] data = DtoFactory.getInstance().toJson(fileInfo).getBytes();

        VirtualFileSystemException exception = mock(VirtualFileSystemException.class);
        when(exception.getMessage()).thenReturn("does not exists.");

        doThrow(exception).when(synapseFile).moveTo((VirtualFile)anyObject(), anyString());

        ContainerResponse response = prepareResponseLauncherService("POST", "detect", data);

        assertEquals(200, response.getStatus());
        verify(synapseFile).updateContent(eq(ESB_XML_MIME_TYPE), (InputStream)anyObject(), anyString());
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
}