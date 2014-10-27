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

import static com.codenvy.ide.ext.wso2.shared.Constants.PROXY_SERVICE_FOLDER_NAME;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link CreateProxyServicePage}.
 *
 * @author Andrey Plotnikov
 * @author Dmitriy Shnurenko
 */
public class CreateProxyServicePageTest extends AbstractCreateResourcePageTest {

    @Override
    public void setUp() throws Exception {
        when(resources.proxyServiceTemplate()).thenReturn(textResource);
        when(textResource.getText()).thenReturn(SOME_TEXT);

        when(locale.wizardFileProxyServiceFieldsName()).thenReturn(SOME_TEXT);

        page = new CreateProxyServicePage(view,
                                          locale,
                                          resources,
                                          editorAgent,
                                          fileType,
                                          notificationManager,
                                          projectServiceClient,
                                          appContext,
                                          eventBus,
                                          dtoUnmarshallerFactory);

        parentFolderName = PROXY_SERVICE_FOLDER_NAME;

        super.setUp();
    }

    @Test
    public void proxyServicePageShouldBePrepared() throws Exception {
        verify(locale).wizardFileProxyServiceTitle();
        verify(locale).wizardFileProxyServiceFieldsName();
        verify(resources).proxy_service_wizard();
        verify(resources).proxyServiceTemplate();
    }

    @Test
    public void warningMessageShouldBeShownWhenProxyServiceNameIsEmpty() throws Exception {
        when(view.getResourceName()).thenReturn("");
        when(locale.wizardFileProxyServiceNoticeEmptyName()).thenReturn(SOME_TEXT);

        assertThat(page.getNotice(), equalTo(SOME_TEXT));

        verify(view).getResourceName();
        verify(locale).wizardFileProxyServiceNoticeEmptyName();
    }
}