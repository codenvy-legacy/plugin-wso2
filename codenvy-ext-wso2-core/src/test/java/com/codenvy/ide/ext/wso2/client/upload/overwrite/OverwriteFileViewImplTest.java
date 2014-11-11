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
package com.codenvy.ide.ext.wso2.client.upload.overwrite;

import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwtmockito.GwtMockitoTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.codenvy.ide.ext.wso2.client.upload.overwrite.OverwriteFileView.ActionDelegate;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(GwtMockitoTestRunner.class)
public class OverwriteFileViewImplTest {

    private static final String SOME_TEXT = "someText";

    @Mock
    private LocalizationConstant  locale;
    @Mock
    private WSO2Resources         res;
    @Mock
    private ActionDelegate        delegate;
    @InjectMocks
    private OverwriteFileViewImpl view;

    @Before
    public void setUp() throws Exception {
        view.setDelegate(delegate);
    }

    @Test
    public void constructorShouldBeDone() throws Exception {
        verify(locale).wso2FileOverwriteTitle();
        verify(locale).wso2ButtonCancel();
        verify(locale).wso2ButtonRename();
        verify(locale).wso2ButtonOverwrite();
    }

    @Test
    public void fileNameShouldBeReturned() throws Exception {
        view.getFileName();

        verify(view.fileName).getText();
    }

    @Test
    public void fileNameShouldBeSet() throws Exception {
        view.setFileName(SOME_TEXT);

        verify(view.fileName).setText(SOME_TEXT);
    }

    @Test
    public void messageShouldBeSet() throws Exception {
        view.setMessage(SOME_TEXT);

        verify(view.message).setHTML(SOME_TEXT);
    }

    @Test
    public void fileNameShouldBeChanged() throws Exception {
        KeyUpEvent keyUpEvent = mock(KeyUpEvent.class);

        view.onFileNameChanged(keyUpEvent);

        verify(delegate).onNameChanged();
    }
}