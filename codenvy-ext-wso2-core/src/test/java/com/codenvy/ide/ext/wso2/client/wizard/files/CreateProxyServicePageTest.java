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

import org.junit.Ignore;
import org.junit.Test;

import static com.codenvy.ide.ext.wso2.shared.Constants.PROXY_SERVICE_FOLDER_NAME;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link CreateProxyServicePage}.
 *
 * @author Andrey Plotnikov
 */
public class CreateProxyServicePageTest extends AbstractCreateResourcePageTest {

    @Override
    public void setUp() throws Exception {
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

        verify(locale).wizardFileProxyServiceTitle();
        verify(locale).wizardFileProxyServiceFieldsName();
        verify(resources).proxy_service_wizard();

        parentFolderName = PROXY_SERVICE_FOLDER_NAME;

        super.setUp();
    }

    @Ignore("not ready yet")
    @Test
    public void emptyResourceNameNoticeShouldBeShown() throws Exception {
        when(view.getResourceName()).thenReturn(EMPTY_TEXT);
        when(locale.wizardFileProxyServiceNoticeEmptyName()).thenReturn(SOME_TEXT);

        page.go(container);
        page.onValueChanged();

        assertEquals(SOME_TEXT, page.getNotice());

        verify(locale).wizardFileProxyServiceNoticeEmptyName();
    }

    @Ignore("not ready yet")
    @Override
    public void onFailureMethodInCommitCallbackShouldBeExecuted() throws Exception {
        super.onFailureMethodInCommitCallbackShouldBeExecuted();

        verify(resources).proxyServiceTemplate();
        verify(view, times(2)).getResourceName();
    }

    @Ignore("not ready yet")
    @Override
    public void onSuccessMethodInCommitCallbackShouldBeExecuted() throws Exception {
        super.onSuccessMethodInCommitCallbackShouldBeExecuted();

        verify(resources).proxyServiceTemplate();
        verify(view, times(2)).getResourceName();
    }
}