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
package com.codenvy.ide.ext.wso2.server;

import com.codenvy.api.core.ApiException;
import com.codenvy.api.core.ServerException;
import com.codenvy.api.vfs.server.ContentStream;
import com.codenvy.api.vfs.server.VirtualFile;
import com.codenvy.api.vfs.server.VirtualFileSystemRegistry;
import com.codenvy.dto.server.DtoFactory;
import com.codenvy.ide.ext.wso2.server.rest.WSO2RestService;
import com.codenvy.ide.ext.wso2.shared.FileInfo;

import org.everrest.core.ResourceBinder;
import org.everrest.core.impl.ContainerResponse;
import org.everrest.core.impl.EverrestProcessor;
import org.everrest.core.impl.ProviderBinder;
import org.everrest.core.impl.ResourceBinderImpl;
import org.everrest.core.tools.DependencySupplierImpl;
import org.everrest.core.tools.ResourceLauncher;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_XML_MIME_TYPE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.doThrow;
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

    private static final String PROJECT_NAME = "projectName";
    private static final String FILE_NAME    = "fileName";
    private static final String BASE_URI     = "http://localhost";

    private static final String XML_HEADER                        = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
    private static final String SEQUENCE_CONFIGURATION_CONTENT    = XML_HEADER + "<sequence></sequence>";
    private static final String ENDPOINT_CONFIGURATION_CONTENT    = XML_HEADER + "<endpoint></endpoint>";
    private static final String PROXY_CONFIGURATION_CONTENT       = XML_HEADER + "<proxy></proxy>";
    private static final String LOCAL_ENTRY_CONFIGURATION_CONTENT = XML_HEADER + "<localEntry></localEntry>";
    private static final String SOME_FILE_CONTENT                 = XML_HEADER + "<tag></tag>";

    @Mock(answer = RETURNS_DEEP_STUBS)
    private VirtualFileSystemRegistry vfsRegistry;
    @Mock
    private VirtualFile               synapseFile;
    @InjectMocks
    private WSO2RestService           service;

    private ResourceLauncher launcher;

    @Before
    public void setUp() throws Exception {
        DependencySupplierImpl dependencies = new DependencySupplierImpl();
        dependencies.addComponent(VirtualFileSystemRegistry.class, vfsRegistry);

        ResourceBinder resources = new ResourceBinderImpl();
        resources.addResource(WSO2RestService.class, null);

        EverrestProcessor processor = new EverrestProcessor(resources, ProviderBinder.getInstance(), dependencies);

        launcher = new ResourceLauncher(processor);
    }

    private ContainerResponse sendRequest(String method, String path, byte[] data) throws Exception {
        Map<String, List<String>> headers = new HashMap<>(1);
        headers.put("Content-Type", Arrays.asList("application/json"));

        return launcher.service(method, "/wso2/workspaceId/" + path, BASE_URI, headers, data, null);
    }

    private void prepareFileForTesting(String content) throws ApiException {
        InputStream is = new ByteArrayInputStream(content.getBytes());
        ContentStream contentStream = new ContentStream(null, is, null);

        when(vfsRegistry.getProvider(anyString()).getMountPoint(anyBoolean()).getVirtualFile(anyString())).thenReturn(synapseFile);
        when(synapseFile.getName()).thenReturn("synapseName");
        when(synapseFile.getMediaType()).thenReturn(ESB_XML_MIME_TYPE);
        when(synapseFile.getContent()).thenReturn(contentStream);
    }

    private byte[] getRequestData(String fileName) {
        FileInfo fileInfo = DtoFactory.getInstance().createDto(FileInfo.class)
                                      .withFileName(fileName)
                                      .withNewFileName(FILE_NAME)
                                      .withProjectName(PROJECT_NAME);

        return DtoFactory.getInstance().toJson(fileInfo).getBytes();
    }

    private byte[] getRequestData() {
        return getRequestData(FILE_NAME);
    }

    private void fileShouldBeDetected(String fileContent) throws Exception {
        prepareFileForTesting(fileContent);

        ContainerResponse response = sendRequest("POST", "detect", getRequestData());

        assertThat(response.getStatus(), is(204));
        verify(synapseFile).updateContent(eq(ESB_XML_MIME_TYPE), any(InputStream.class), isNull(String.class));
    }

    @Test
    public void sequenceFileShouldBeDetected() throws Exception {
        fileShouldBeDetected(SEQUENCE_CONFIGURATION_CONTENT);
    }

    @Test
    public void endpointFileShouldBeDetected() throws Exception {
        fileShouldBeDetected(ENDPOINT_CONFIGURATION_CONTENT);
    }

    @Test
    public void proxyFileShouldBeDetected() throws Exception {
        fileShouldBeDetected(PROXY_CONFIGURATION_CONTENT);
    }

    @Test
    public void localEntryFileShouldBeDetected() throws Exception {
        fileShouldBeDetected(LOCAL_ENTRY_CONFIGURATION_CONTENT);
    }

    @Test
    public void configurationFileShouldBeNotDetected() throws Exception {
        fileShouldBeDetected(SOME_FILE_CONTENT);
    }

    @Test
    public void fileShouldBeDeleted() throws Exception {
        prepareFileForTesting(SEQUENCE_CONFIGURATION_CONTENT);

        ContainerResponse response = sendRequest("POST", "file/delete", getRequestData());

        verify(synapseFile).delete(isNull(String.class));
        assertThat(response.getStatus(), is(204));
    }

    @Test
    public void fileShouldBeDetectWhenParentFolderDoesNotExist() throws Exception {
        prepareFileForTesting(SEQUENCE_CONFIGURATION_CONTENT);

        doThrow(new ServerException("does not exists. "))
                .doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        // This code needs for not throwing exception when this method execute second time
                        return null;
                    }
                }).when(synapseFile).moveTo(any(VirtualFile.class), anyString());

        ContainerResponse response = sendRequest("POST", "detect", getRequestData());

        assertThat(response.getStatus(), is(204));
        verify(synapseFile).updateContent(eq(ESB_XML_MIME_TYPE), any(InputStream.class), isNull(String.class));
    }

    @Test
    public void fileShouldBeNotDetectWhenVirtualFileSystemExceptionHappened() throws Exception {
        prepareFileForTesting(SEQUENCE_CONFIGURATION_CONTENT);

        doThrow(new ServerException("some message"))
                .doAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        // This code needs for not throwing exception when this method execute second time
                        return null;
                    }
                }).when(synapseFile).moveTo(any(VirtualFile.class), anyString());

        ContainerResponse response = sendRequest("POST", "detect", getRequestData());

        assertThat(response.getStatus(), is(500));
        assertEquals("com.codenvy.api.core.ServerException: some message", response.getResponse().getEntity());
        verify(synapseFile).updateContent(eq(ESB_XML_MIME_TYPE), any(InputStream.class), isNull(String.class));
    }

    @Test
    public void fileShouldBeUploadWithRenamingName() throws Exception {
        prepareFileForTesting(SEQUENCE_CONFIGURATION_CONTENT);

        ContainerResponse response = sendRequest("POST", "file/rename", getRequestData());

        verify(synapseFile).rename(eq(FILE_NAME), eq(ESB_XML_MIME_TYPE), isNull(String.class));

        assertEquals(204, response.getStatus());
    }

    @Test
    public void fileShouldBeUploadWithSomeProblem() throws Exception {
        prepareFileForTesting(SEQUENCE_CONFIGURATION_CONTENT);

        ContainerResponse response = sendRequest("POST", "upload", getRequestData());

        assertThat(response.getStatus(), is(500));
        assertEquals("URI is not absolute", response.getResponse().getEntity());
    }

    @Ignore
    @Test
    public void fileShouldBeUploaded() throws Exception {
        prepareFileForTesting(SEQUENCE_CONFIGURATION_CONTENT);

        when(synapseFile.createFile(eq("note.xml"), eq(ESB_XML_MIME_TYPE), any(InputStream.class))).thenReturn(synapseFile);

        //TODO need some url only with xml content.
        ContainerResponse response = sendRequest("POST", "upload", getRequestData("http://www.w3schools.com/xml/note.xml"));

        assertThat(response.getStatus(), is(204));
    }

    @Ignore
    @Test
    public void fileShouldBeNotUploaded() throws Exception {
        prepareFileForTesting(SEQUENCE_CONFIGURATION_CONTENT);

        //TODO need some url only with xml content.
        ContainerResponse response = sendRequest("POST", "upload", getRequestData("http://www.w3schools.com/xml/myNote.xml"));

        assertThat(response.getStatus(), is(500));
        assertEquals("java.io.FileNotFoundException: http://www.w3schools.com/xml/myNote.xml", response.getResponse().getEntity());
    }

    @Test
    public void fileShouldBeDetectedWithSomeProblem() throws Exception {
        prepareFileForTesting(SEQUENCE_CONFIGURATION_CONTENT);
        when(vfsRegistry.getProvider(anyString()).getMountPoint(anyBoolean()).getVirtualFile(anyString())).thenReturn(null);

        ContainerResponse response = sendRequest("POST", "detect", getRequestData());

        assertThat(response.getStatus(), is(500));
        assertEquals("java.lang.NullPointerException", response.getResponse().getEntity());
    }

    @Test
    public void fileShouldBeOverwrittenWithSomeProblem() throws Exception {
        prepareFileForTesting(SEQUENCE_CONFIGURATION_CONTENT);
        when(vfsRegistry.getProvider(anyString()).getMountPoint(anyBoolean()).getVirtualFile(anyString())).thenReturn(null);

        ContainerResponse response = sendRequest("POST", "file/overwrite", getRequestData());

        assertThat(response.getStatus(), is(500));
        assertEquals("java.lang.NullPointerException", response.getResponse().getEntity());
    }

    @Test
    public void fileShouldBeUploadWithOverwritingContent() throws Exception {
        prepareFileForTesting(SEQUENCE_CONFIGURATION_CONTENT);

        ContainerResponse response = sendRequest("POST", "file/overwrite", getRequestData());

        verify(synapseFile).updateContent(eq(ESB_XML_MIME_TYPE), any(InputStream.class), isNull(String.class));
        verify(synapseFile).delete(isNull(String.class));

        assertThat(response.getStatus(), is(204));
    }

}