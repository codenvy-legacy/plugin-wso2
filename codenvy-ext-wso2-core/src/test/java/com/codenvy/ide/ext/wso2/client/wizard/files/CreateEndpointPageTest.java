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
package com.codenvy.ide.ext.wso2.client.wizard.files;

import org.junit.Test;

import static com.codenvy.ide.ext.wso2.shared.Constants.ENDPOINTS_FOLDER_NAME;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link CreateEndpointPage}.
 *
 * @author Andrey Plotnikov
 */
public class CreateEndpointPageTest extends AbstractCreateResourcePageTest {

    @Override
    public void setUp() throws Exception {
        page = new CreateEndpointPage(view, locale, resourceProvider, resources, editorAgent, fileType, notificationManager);

        verify(locale).wizardFileEndpointTitle();
        verify(locale).wizardFileEndpointFieldsName();
        verify(resources).endpoint_wizard();

        parentFolderName = ENDPOINTS_FOLDER_NAME;

        super.setUp();
    }

    @Test
    public void emptyResourceNameNoticeShouldBeShown() throws Exception {
        when(view.getResourceName()).thenReturn(EMPTY_TEXT);
        when(locale.wizardFileEndpointNoticeEmptyName()).thenReturn(SOME_TEXT);

        page.go(container);
        page.onValueChanged();

        assertEquals(SOME_TEXT, page.getNotice());

        verify(locale).wizardFileEndpointNoticeEmptyName();
    }

    @Override
    public void onFailureMethodInCommitCallbackShouldBeExecuted() throws Exception {
        super.onFailureMethodInCommitCallbackShouldBeExecuted();

        verify(resources).endpointTemplate();
        verify(view, times(2)).getResourceName();
    }

    @Override
    public void onSuccessMethodInCommitCallbackShouldBeExecuted() throws Exception {
        super.onSuccessMethodInCommitCallbackShouldBeExecuted();

        verify(resources).endpointTemplate();
        verify(view, times(2)).getResourceName();
    }
}