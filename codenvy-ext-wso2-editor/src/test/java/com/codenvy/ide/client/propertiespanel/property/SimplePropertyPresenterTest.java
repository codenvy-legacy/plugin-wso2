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

import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Andrey Plotnikov
 */
@RunWith(MockitoJUnitRunner.class)
public class SimplePropertyPresenterTest {

    private static final String STRING = "some text";

    @Mock
    private PropertyValueChangedListener listener;
    @Mock
    private SimplePropertyView           view;
    @InjectMocks
    private SimplePropertyPresenter      presenter;

    @Test
    public void viewShouldBePrepared() throws Exception {
        verify(view).setDelegate(presenter);
    }

    @Test
    public void viewShouldBeReturned() throws Exception {
        assertEquals(view, presenter.getView());
    }

    @Test
    public void titleShouldBeSet() throws Exception {
        presenter.setTitle(STRING);

        verify(view).setTitle(STRING);
    }

    @Test
    public void propertyShouldBeSet() throws Exception {
        presenter.setProperty(STRING);

        verify(view).setProperty(STRING);
    }

    @Test
    public void visibleStateShouldBeChanged() throws Exception {
        presenter.setVisible(true);

        verify(view).setVisible(true);
    }

    @Test
    public void listenerShouldBeAdded() throws Exception {
        presenter.addPropertyValueChangedListener(listener);
        presenter.notifyPropertyValueChangedListener();

        verify(listener).onPropertyChanged(anyString());
    }

    @Test
    public void listenerShouldBeRemoved() throws Exception {
        presenter.addPropertyValueChangedListener(listener);
        presenter.notifyPropertyValueChangedListener();

        verify(listener).onPropertyChanged(anyString());

        reset(listener);

        presenter.removePropertyValueChangedListeners();
        presenter.notifyPropertyValueChangedListener();

        verify(listener, never()).onPropertyChanged(anyString());
    }

    @Test
    public void listenersShouldBeNotifiedWithPropertyValue() throws Exception {
        when(view.getProperty()).thenReturn(STRING);

        presenter.addPropertyValueChangedListener(listener);
        presenter.notifyPropertyValueChangedListener();

        verify(listener).onPropertyChanged(STRING);
    }

    @Test
    public void listenerShouldBeNotifiedWhenProeprtyIsChanged() throws Exception {
        presenter.addPropertyValueChangedListener(listener);
        presenter.onPropertyChanged();

        verify(listener).onPropertyChanged(anyString());
    }

}