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
package com.codenvy.ide.client.propertiespanel.property;

import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyView;
import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTestWithMockito;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter.EditButtonClickedListener;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * @author Andrey Plotnikov
 */
@GwtModule("com.codenvy.ide.WSO2Editor")
public class ComplexPropertyPresenterTest extends GwtTestWithMockito {

    private static final String STRING = "some text";

    @Mock
    private EditButtonClickedListener listener;
    @Mock
    private ComplexPropertyView       view;
    @InjectMocks
    private ComplexPropertyPresenter  presenter;

    @Test
    public void viewShouldBePrepared() throws Exception {
        verify(view).setDelegate(eq(presenter));
    }

    @Test
    public void viewShouldBeReturned() throws Exception {
        assertEquals(view, presenter.getView());
    }

    @Test
    public void titleShouldBeSet() throws Exception {
        presenter.setTitle(STRING);

        verify(view).setTitle(eq(STRING));
    }

    @Test
    public void propertyShouldBeSet() throws Exception {
        presenter.setProperty(STRING);

        verify(view).setProperty(eq(STRING));
    }

    @Test
    public void visibleStateShouldBeChanged() throws Exception {
        presenter.setVisible(true);

        verify(view).setVisible(true);
    }

    @Test
    public void listenerShouldBeAdded() throws Exception {
        presenter.addEditButtonClickedListener(listener);
        presenter.notifyEditButtonClickedListeners();

        verify(listener).onEditButtonClicked();
    }

    @Test
    public void listenerShouldBeNotifiedWhenPropertyIsChanged() throws Exception {
        presenter.addEditButtonClickedListener(listener);
        presenter.onEditButtonClicked();

        verify(listener).onEditButtonClicked();
    }

}