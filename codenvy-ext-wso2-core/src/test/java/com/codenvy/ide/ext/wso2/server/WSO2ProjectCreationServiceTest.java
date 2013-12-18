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
import com.jayway.restassured.response.Response;

import org.everrest.assured.EverrestJetty;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Testing {@link WSO2ProjectCreationService} functionality.
 *
 * @author Andrey Plotnikov
 */
@Listeners(value = {EverrestJetty.class, MockitoTestNGListener.class})
public class WSO2ProjectCreationServiceTest {
    public static final String POM_CONTENT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                             "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\"\n" +
                                             "         xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
                                             "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                                             "</project>";

    @Mock(answer = RETURNS_DEEP_STUBS)
    private VirtualFileSystemRegistry  vfsRegistry;
    @InjectMocks
    private WSO2ProjectCreationService service;

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

        Response response = given().header("Content-Type", "application/json").body(DtoFactory.getInstance().toJson(projectInfo)).when()
                .post("/dev-monit/templates/esbconf");

        assertEquals(response.getStatusCode(), 204);

        verify(rootFolder).createFolder(eq(projectName));

        verify(projectFolder).unzip((InputStream)anyObject(), eq(true));
        verify(projectFolder).updateProperties((List<Property>)anyObject(), eq((String)null));

        verify(pomFile).updateContent(anyString(), (InputStream)anyObject(), eq((String)null));
    }
}