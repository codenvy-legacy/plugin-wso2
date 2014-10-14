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

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter.EditButtonClickedListener;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class ComplexPropertyPresenterTest extends AbstractPropertyPresenterTest<ComplexPropertyPresenter, ComplexPropertyView> {

    private static final String STRING = "some text";

    @Mock
    private EditButtonClickedListener listener;

    @Before
    public void setUp() throws Exception {
        view = mock(ComplexPropertyView.class);
        presenter = new ComplexPropertyPresenter(view);
    }

    @Test
    public void propertyShouldBeSet() throws Exception {
        presenter.setProperty(STRING);

        verify(view).setProperty(STRING);
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