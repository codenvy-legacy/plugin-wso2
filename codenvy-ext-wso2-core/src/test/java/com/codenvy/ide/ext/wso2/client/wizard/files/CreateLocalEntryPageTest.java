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

import org.junit.Test;

import static com.codenvy.ide.ext.wso2.shared.Constants.LOCAL_ENTRY_FOLDER_NAME;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link CreateLocalEntryPage}.
 *
 * @author Andrey Plotnikov
 * @author Dmitriy Shnurenko
 */
public class CreateLocalEntryPageTest extends AbstractCreateResourcePageTest {

    @Override
    public void setUp() throws Exception {
        when(resources.localEntryTemplate()).thenReturn(textResource);
        when(textResource.getText()).thenReturn(SOME_TEXT);

        when(locale.wizardFileLocalEntryFieldsName()).thenReturn(SOME_TEXT);

        page = new CreateLocalEntryPage(view,
                                        locale,
                                        resources,
                                        editorAgent,
                                        fileType,
                                        notificationManager,
                                        appContext,
                                        eventBus,
                                        projectServiceClient,
                                        dtoUnmarshallerFactory);

        parentFolderName = LOCAL_ENTRY_FOLDER_NAME;

        super.setUp();
    }

    @Test
    public void localEntryPageShouldBePrepared() throws Exception {
        verify(locale).wizardFileLocalEntryTitle();
        verify(locale).wizardFileLocalEntryFieldsName();
        verify(resources).local_entry_wizard();
        verify(resources).localEntryTemplate();
    }

    @Test
    public void warningMessageShouldBeShownWhenLocalEntryNameIsEmpty() throws Exception {
        when(view.getResourceName()).thenReturn("");
        when(locale.wizardFileLocalEntryNoticeEmptyName()).thenReturn(SOME_TEXT);

        assertThat(page.getNotice(), equalTo(SOME_TEXT));

        verify(view).getResourceName();
        verify(locale).wizardFileLocalEntryNoticeEmptyName();
    }
}