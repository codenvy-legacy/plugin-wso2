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
package com.codenvy.ide.ext.wso2.client.wizard.files;

import com.codenvy.api.project.gwt.client.ProjectServiceClient;
import com.codenvy.ide.api.app.AppContext;
import com.codenvy.ide.api.app.CurrentProject;
import com.codenvy.ide.api.editor.EditorAgent;
import com.codenvy.ide.api.filetypes.FileType;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.projecttree.generic.FolderNode;
import com.codenvy.ide.api.wizard.Wizard;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.codenvy.ide.ext.wso2.client.wizard.files.view.CreateResourceView;
import com.codenvy.ide.rest.DtoUnmarshallerFactory;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_XML_EXTENSION;
import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_XML_MIME_TYPE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Answers.RETURNS_MOCKS;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The basic test for testing create WSO2 resource pages.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitriy Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractCreateResourcePageTest {

    public static final String EMPTY_TEXT         = "";
    public static final String SOME_TEXT          = "someText";
    public static final String FULL_RESOURCE_NAME = SOME_TEXT + '.' + ESB_XML_EXTENSION;

    @Mock
    private Wizard.UpdateDelegate delegate;
    @Mock
    private CurrentProject        currentProject;

    @Mock(answer = RETURNS_DEEP_STUBS)
    protected FolderNode             parentFolder;
    @Mock
    protected AcceptsOneWidget       container;
    @Mock
    protected AppContext             appContext;
    @Mock
    protected ProjectServiceClient   projectServiceClient;
    @Mock
    protected EditorAgent            editorAgent;
    @Mock
    protected EventBus               eventBus;
    @Mock
    protected LocalizationConstant   locale;
    @Mock
    protected NotificationManager    notificationManager;
    @Mock
    protected CreateResourceView     view;
    @Mock(answer = RETURNS_MOCKS)
    protected WSO2Resources          resources;
    @Mock(answer = RETURNS_DEEP_STUBS)
    protected FileType               fileType;
    @Mock
    protected DtoUnmarshallerFactory dtoUnmarshallerFactory;

    protected String                     parentFolderName;
    protected AbstractCreateResourcePage page;

    @Before
    public void setUp() throws Exception {
        verify(view).setDelegate((CreateResourceView.ActionDelegate)anyObject());
        verify(view).setResourceNameTitle(anyString());

        when(appContext.getCurrentProject()).thenReturn(currentProject);

        page.setUpdateDelegate(delegate);

        when(fileType.getMimeTypes().get(0)).thenReturn(ESB_XML_MIME_TYPE);
        when(fileType.getExtension()).thenReturn(ESB_XML_EXTENSION);
    }

    @Test
    public void folderTreeShouldBeCreatedWhenExistOnlyRootFolder() throws Exception {
        // TODO fix test
    }

    @Test
    public void folderTreeShouldBeCreatedWhenExistSrcLevelFolder() throws Exception {
        // TODO fix test
    }

    @Test
    public void folderTreeShouldBeCreatedWhenExistMainLevelFolder() throws Exception {
        // TODO fix test
    }

    @Test
    public void folderTreeShouldBeCreatedWhenExistSynapseConfigLevelFolder() throws Exception {
        // TODO fix test
    }

    @Test
    public void pageShouldBeNotCompletedWhenFileWithTheSameNameIsExisted() throws Exception {
        // TODO fix test
    }

    @Ignore("not ready yet")
    @Test
    public void pageShouldBeNotCompletedWhenParentFolderIsNull() throws Exception {
        when(view.getResourceName()).thenReturn(SOME_TEXT);

        assertEquals(false, page.isCompleted());
    }

    @Ignore("not ready yet")
    @Test
    public void pageShouldBeNotCompletedWhenFileNameIsIncorrect() throws Exception {
        List<String> names = Arrays.asList("", "$projectName", "project%Name", "projectName!");

        page.go(container);

        for (String name : names) {
            when(view.getResourceName()).thenReturn(name);
            page.onValueChanged();

            assertFalse(page.isCompleted());
        }
    }

    @Ignore("not ready yet")
    @Test
    public void pageShouldBeCompleted() throws Exception {
        page.go(container);

        when(view.getResourceName()).thenReturn(SOME_TEXT);
        page.onValueChanged();

        assertEquals(true, page.isCompleted());
    }

    @Test
    public void pageFieldsShouldBeCleanedWhenPageIsOpening() throws Exception {
        page.focusComponent();

        verify(view).setResourceName(eq(EMPTY_TEXT));
    }

    @Ignore("not ready yet")
    @Test
    public void viewShouldBeShown() throws Exception {
        page.go(container);

        verify(container).setWidget(view);
    }

    @Test
    public void parentFolderShouldBeFound() throws Exception {
        // TODO fix test
    }

    @Ignore("not ready yet")
    @Test
    public void controlsShouldBeUpdated() throws Exception {
        page.go(container);
        page.onValueChanged();

        verify(delegate).updateControls();
    }

    @Ignore("not ready yet")
    @Test
    public void incorrectNameNoticeShouldBeShown() throws Exception {
        when(view.getResourceName()).thenReturn("projectName!");
        when(locale.wizardFileResourceNoticeIncorrectName()).thenReturn(SOME_TEXT);

        page.go(container);
        page.onValueChanged();

        assertEquals(SOME_TEXT, page.getNotice());

        verify(locale).wizardFileResourceNoticeIncorrectName();
    }

    @Test
    public void fileExistsNoticeShouldBeShown() throws Exception {
        // TODO fix test
    }

    @Ignore("not ready yet")
    @Test
    public void emptyNoticeShouldBeShown() throws Exception {
        when(view.getResourceName()).thenReturn("projectName");

        page.go(container);
        page.onValueChanged();

        assertNull(page.getNotice());
    }

    @Ignore("not ready yet")
    @Test
    public void onFailureMethodInCommitCallbackShouldBeExecuted() throws Exception {
    }

    @Ignore("not ready yet")
    @Test
    public void onSuccessMethodInCommitCallbackShouldBeExecuted() throws Exception {
    }

}