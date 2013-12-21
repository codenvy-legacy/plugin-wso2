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

package com.codenvy.ide.ext.wso2.client.upload;

import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.parts.ConsolePart;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link ImportFilePresenter}.
 *
 * @author Valeriy Svydenko
 */
@Listeners(value = {MockitoTestNGListener.class})
public class ImportFilePresenterTest {
    public static final String MESSAGE = "message";

    @Mock
    ImportFileView      view;
    @Mock
    ConsolePart         console;
    @Mock
    NotificationManager notification;
    @InjectMocks
    ImportFilePresenter importFilePresenter;

    @Test
    public void importButtonAndUrlFieldShouldBeDisable() {
        importFilePresenter.showDialog();

        verify(view).setEnabledImportButton(eq(false));
        verify(view).setEnterUrlFieldEnabled(eq(false));
    }

    @Test
    public void closeButtonShouldBeExecuted() {
        importFilePresenter.onCancelClicked();

        verify(view).close();
    }

    public void prepareWhenUrlButtonChosen() {
        importFilePresenter.onUseUrlChosen();

        when(view.getUrl()).thenReturn(MESSAGE);
        when(view.isUseUrl()).thenReturn(true);
    }
}