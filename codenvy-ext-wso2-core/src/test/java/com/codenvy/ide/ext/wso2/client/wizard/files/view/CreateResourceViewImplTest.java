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
package com.codenvy.ide.ext.wso2.client.wizard.files.view;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwtmockito.GwtMockitoTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.codenvy.ide.ext.wso2.client.wizard.files.view.CreateResourceView.ActionDelegate;
import static org.mockito.Mockito.verify;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(GwtMockitoTestRunner.class)
public class CreateResourceViewImplTest {

    private static final String SOME_TEXT = "someText";

    @Mock
    private ActionDelegate         delegate;
    @Mock
    private KeyUpEvent             keyUpEvent;
    @InjectMocks
    private CreateResourceViewImpl view;

    @Before
    public void setUp() throws Exception {
        view.setDelegate(delegate);
    }

    @Test
    public void resourceNameTitleShouldBeSet() throws Exception {
        view.setResourceNameTitle(SOME_TEXT);

        verify(view.resourceNameTitle).setText(SOME_TEXT);
    }

    @Test
    public void resourceNameShouldBeReturned() throws Exception {
        view.getResourceName();

        verify(view.resourceName).getText();
    }

    @Test
    public void resourceNameShouldBeSet() throws Exception {
        view.setResourceName(SOME_TEXT);

        verify(view.resourceName).setText(SOME_TEXT);
    }

    @Test
    public void valueShouldBeChanged() throws Exception {
        view.onValueChanged(keyUpEvent);

        verify(delegate).onValueChanged();
    }

}