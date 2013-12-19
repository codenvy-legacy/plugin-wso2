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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.mockito.Answers.RETURNS_MOCKS;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Here we're testing {@link ImportFilePresenter}.
 *
 * @author Valeriy Svydenko
 */
@Listeners(value = {MockitoTestNGListener.class})
public class ImportFilePresenterTest {
    public static final String  MESSAGE         = "message";
    public static final Boolean DISABLE_ELEMENT = false;
    public static final Boolean ENABLE_ELEMENT  = true;

    @Mock(answer = RETURNS_MOCKS)
    ImportFileView      view;
    @Mock
    ConsolePart         console;
    @Mock
    NotificationManager notification;
    @InjectMocks
    ImportFilePresenter importFilePresenter;

    @BeforeMethod
    public void setUp() {
        importFilePresenter.showDialog();
    }

    @Test
    public void testShowDialog() {
        verify(view).setEnabledImportButton(eq(DISABLE_ELEMENT));
        verify(view).setEnterUrlFieldEnabled(eq(DISABLE_ELEMENT));
        verify(view).setUseLocalPath(eq(ENABLE_ELEMENT));
    }

    @Test
    public void onCancelClicked() {
        importFilePresenter.onCancelClicked();
        verify(view).close();
    }

    @Test
    public void testWhenUrlRadioButtonChosen() {
        importFilePresenter.onUseUrlChosen();

        when(view.getUrl()).thenReturn(MESSAGE);
        when(view.isUseUrl()).thenReturn(true);

        verify(view).setEnterUrlFieldEnabled(eq(ENABLE_ELEMENT));
        verify(view).setEnabledImportButton(eq(DISABLE_ELEMENT));
        verify(view).setUseLocalPath(ENABLE_ELEMENT);

        assertEquals(MESSAGE, view.getUrl());

        importFilePresenter.onUrlChanged();
        verify(view).setEnabledImportButton(eq(ENABLE_ELEMENT));
    }

    @Test
    public void testWhenLocalFileRadioButtonChosen() {
        importFilePresenter.onUseLocalPathChosen();

        when(view.getFileName()).thenReturn(MESSAGE);
        when(view.isUseLocalPath()).thenReturn(true);

        verify(view).setEnabledImportButton(eq(DISABLE_ELEMENT));
        assertEquals(MESSAGE, view.getFileName());

        importFilePresenter.onFileNameChanged();

        verify(view).setEnabledImportButton(eq(ENABLE_ELEMENT));
    }
}
