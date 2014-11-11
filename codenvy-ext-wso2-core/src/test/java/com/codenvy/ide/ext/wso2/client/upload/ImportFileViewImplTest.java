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
package com.codenvy.ide.ext.wso2.client.upload;

import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwtmockito.GwtMockitoTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.codenvy.ide.ext.wso2.client.upload.ImportFileView.ActionDelegate;
import static com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import static com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(GwtMockitoTestRunner.class)
public class ImportFileViewImplTest {

    public static final String SOME_TEXT = "someText";

    @Captor
    private ArgumentCaptor<SubmitCompleteHandler> handlerCaptor;
    @Mock
    private WSO2Resources                         res;
    @Mock
    private LocalizationConstant                  locale;
    @Mock
    private Button                                btnImport;
    @Mock
    private ActionDelegate                        delegate;
    @Mock
    private SubmitCompleteEvent                   submitCompleteEvent;
    @Mock
    private ClickEvent                            clickEvent;
    @InjectMocks
    private ImportFileViewImpl                    view;

    @Before
    public void setUp() throws Exception {
        view.setDelegate(delegate);
    }

    @Test
    public void constructorShouldBeDone() throws Exception {
        verify(locale).wso2ImportDialogTitle();
        verify(locale).wso2ButtonCancel();
        verify(locale).wso2ButtonImport();
        verify(view.uploadForm).addSubmitCompleteHandler(any(SubmitCompleteHandler.class));
    }

    @Test
    public void submitCompleteHandlerShouldBeDone() throws Exception {
        verify(view.uploadForm).addSubmitCompleteHandler(handlerCaptor.capture());

        SubmitCompleteHandler completeHandler = handlerCaptor.getValue();
        completeHandler.onSubmitComplete(submitCompleteEvent);

        verify(delegate).onSubmitComplete(anyString());
    }

    @Test
    public void urlShouldBeReturned() throws Exception {
        view.getUrl();

        verify(view.url).getText();
    }

    @Test
    public void urlShouldBeSet() throws Exception {
        view.setUrl(SOME_TEXT);

        verify(view.url).setText(SOME_TEXT);
    }

    @Test
    public void useUrlValueShouldBeGot() throws Exception {
        view.isUseUrl();

        verify(view.useUrl).getValue();
    }

    @Test
    public void useLocalPathValueShouldBeGot() throws Exception {
        view.isUseLocalPath();

        verify(view.useLocalPath).getValue();
    }

    @Test
    public void useUrlValueShouldBeSet() throws Exception {
        view.setUseUrl(true);

        verify(view.useUrl).setValue(true);
    }

    @Test
    public void useLocalPathValueShouldBeSet() throws Exception {
        view.setUseLocalPath(true);

        verify(view.useLocalPath).setValue(true);
    }

    @Test
    public void messageShouldBeSet() throws Exception {
        view.setMessage(SOME_TEXT);

        verify(view.message).setHTML(SOME_TEXT);
    }

    @Test
    public void enterUrlEnableShouldBeSet() throws Exception {
        view.setEnterUrlFieldEnabled(true);

        verify(view.url).setEnabled(true);
    }

    @Test
    public void actionShouldBeSet() throws Exception {
        view.setAction(SOME_TEXT);

        verify(view.uploadForm).setAction(SOME_TEXT);
    }

    @Test
    public void uploadFormShouldBeSubmitted() throws Exception {
        view.submit();

        verify(view.uploadForm).submit();
    }

    @Test
    public void dialogShouldBeClosed() throws Exception {
        view.close();

        verify(view.uploadForm).remove(any(FileUpload.class));
        verify(view.url).setText("");
    }

    @Test
    public void urlChangeShouldBeClicked() throws Exception {
        KeyUpEvent keyUpEvent = mock(KeyUpEvent.class);

        view.onUrlChanged(keyUpEvent);

        verify(delegate).onUrlChanged();
    }

}