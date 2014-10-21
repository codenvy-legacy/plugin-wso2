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

import com.codenvy.api.core.notification.EventService;
import com.codenvy.api.core.util.LineConsumer;
import com.codenvy.api.core.util.LineConsumerFactory;
import com.codenvy.api.project.server.FolderEntry;
import com.codenvy.api.project.server.ProjectImporter;
import com.codenvy.api.project.server.VirtualFileEntry;
import com.codenvy.api.user.server.dao.Profile;
import com.codenvy.api.user.server.dao.UserProfileDao;
import com.codenvy.api.vfs.server.VirtualFileSystem;
import com.codenvy.api.vfs.server.VirtualFileSystemRegistry;
import com.codenvy.commons.env.EnvironmentContext;
import com.codenvy.commons.lang.IoUtil;
import com.codenvy.commons.user.UserImpl;
import com.codenvy.dto.server.DtoFactory;
import com.codenvy.ide.ext.git.server.GitConnection;
import com.codenvy.ide.ext.git.server.GitConnectionFactory;
import com.codenvy.ide.ext.git.server.nativegit.CredentialsProvider;
import com.codenvy.ide.ext.git.server.nativegit.NativeGitConnectionFactory;
import com.codenvy.ide.ext.git.server.nativegit.SshKeyUploader;
import com.codenvy.ide.ext.git.shared.InitRequest;
import com.codenvy.ide.ext.ssh.server.SshKeyStore;
import com.codenvy.ide.ext.wso2.server.projectimporter.WSO2ProjectImporter;
import com.codenvy.vfs.impl.fs.LocalFileSystemProvider;
import com.codenvy.vfs.impl.fs.WorkspaceHashLocalFSMountStrategy;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.multibindings.Multibinder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_XML_MIME_TYPE;
import static com.codenvy.ide.ext.wso2.shared.Constants.FACTORY_IMPORTER_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author Valeriy Svydenko
 */
@RunWith(MockitoJUnitRunner.class)
public class WSO2ProjectImporterTest {
    @Mock
    private UserProfileDao userProfileDao;
    @Mock
    private SshKeyStore    sshKeyStore;

    private File                fsRoot;
    private File                gitRepo;
    private VirtualFileSystem   vfs;
    private WSO2ProjectImporter wso2ProjectImporter;

    private DtoFactory dtoFactory = DtoFactory.getInstance();

    @Before
    public void setUp() throws Exception {
        // Bind components
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                Multibinder<ProjectImporter> projectImporterMultibinder = Multibinder.newSetBinder(binder(), ProjectImporter.class);
                projectImporterMultibinder.addBinding().to(WSO2ProjectImporter.class);
                bind(GitConnectionFactory.class).to(NativeGitConnectionFactory.class);
                bind(UserProfileDao.class).toInstance(userProfileDao);
                bind(SshKeyStore.class).toInstance(sshKeyStore);
                Multibinder.newSetBinder(binder(), SshKeyUploader.class);
                Multibinder.newSetBinder(binder(), CredentialsProvider.class);
            }
        });

        // Init virtual file system
        File target = new File(Thread.currentThread().getContextClassLoader().getResource(".").toURI()).getParentFile();
        fsRoot = new File(target, "fs-root");
        assertTrue(fsRoot.mkdirs());
        VirtualFileSystemRegistry registry = new VirtualFileSystemRegistry();
        WorkspaceHashLocalFSMountStrategy mountStrategy = new WorkspaceHashLocalFSMountStrategy(fsRoot, fsRoot);
        LocalFileSystemProvider vfsProvider = new LocalFileSystemProvider("my_vfs", mountStrategy, new EventService(), null, registry);
        registry.registerProvider("my_vfs", vfsProvider);
        vfs = registry.getProvider("my_vfs").newInstance(URI.create(""));

        // set current user
        EnvironmentContext.getCurrent().setUser(new UserImpl("codenvy", "codenvy", null, Arrays.asList("workspace/developer"), false));

        // rules for mock
        Map<String, String> profileAttributes = new HashMap<>();
        profileAttributes.put("firstName", "Codenvy");
        profileAttributes.put("lastName", "Codenvy");
        profileAttributes.put("email", "codenvy@codenvy.com");
        when(userProfileDao.getById("codenvy"))
                .thenReturn(new Profile().withId("codenvy").withUserId("codenvy").withAttributes(profileAttributes));

        // init source git repository
        gitRepo = new File(target, "git");
        assertTrue(gitRepo.mkdirs());
        assertTrue(new File(gitRepo, "src").mkdirs());
        try (BufferedWriter w = Files.newBufferedWriter(new File(gitRepo, "src/sequence.xml").toPath(), Charset.forName("UTF-8"));
             BufferedWriter w2 = Files.newBufferedWriter(new File(gitRepo, "src/pom.xml").toPath(), Charset.forName("UTF-8"))) {

            w.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            w2.write("pom");
        }
        GitConnectionFactory gitFactory = injector.getInstance(GitConnectionFactory.class);

        // init git repository
        GitConnection git = gitFactory.getConnection(gitRepo);
        git.init(dtoFactory.createDto(InitRequest.class).withInitCommit(true));
        git.close();

        wso2ProjectImporter = injector.getInstance(WSO2ProjectImporter.class);
    }

    static class SystemOutLineConsumer implements LineConsumer {
        @Override
        public void writeLine(String line) throws IOException {
            System.out.println(line);
        }

        @Override
        public void close() throws IOException {
        }
    }

    static class SystemOutLineConsumerFactory implements LineConsumerFactory {
        @Override
        public LineConsumer newLineConsumer() {
            return new SystemOutLineConsumer();
        }
    }

    @After
    public void tearDown() throws Exception {
        assertTrue(IoUtil.deleteRecursive(fsRoot));
        assertTrue(IoUtil.deleteRecursive(gitRepo));
    }

    @Test
    public void testImportProjectShouldBeDone() throws Exception {
        FolderEntry folder = new FolderEntry("my-vfs", vfs.getMountPoint().getRoot().createFolder("project"));
        wso2ProjectImporter.importSources(folder,
                                          gitRepo.getAbsolutePath(),
                                          Collections.<String, String>emptyMap(),
                                          new SystemOutLineConsumerFactory());

        VirtualFileEntry sequenceFile = folder.getChild("src/sequence.xml");
        VirtualFileEntry pomFile = folder.getChild("src/pom.xml");

        assertNotNull(folder.getChild("src"));
        assertNotNull(sequenceFile);
        assertNotNull(pomFile);
        assertNotNull(folder.getChild(".git"));
        assertEquals(ESB_XML_MIME_TYPE, sequenceFile.getVirtualFile().getMediaType());
        assertNotEquals(ESB_XML_MIME_TYPE, pomFile.getVirtualFile().getMediaType());
    }

    @Test
    public void idOfImporterShouldBeReturned() {
        assertEquals(FACTORY_IMPORTER_ID, wso2ProjectImporter.getId());
    }

}