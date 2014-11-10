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
package com.codenvy.ide.client.managers;

import com.codenvy.ide.client.elements.Element;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.codenvy.ide.client.managers.SelectionManager.SelectionStateListener;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectionManagerTest {

    @Mock
    private Element                element;
    @Mock
    private SelectionStateListener listener;
    @InjectMocks
    private SelectionManager       manager;

    @Test
    public void elementShouldBeReturned() throws Exception {
        manager.setElement(element);

        assertThat(manager.getElement(), is(sameInstance(element)));
    }

    @Test
    public void elementShouldBeSetAndListenersNotified() throws Exception {
        manager.addListener(listener);

        manager.setElement(element);

        verify(listener).onStateChanged(element);
    }

    @Test
    public void listenerShouldBeRemoved() throws Exception {
        manager.addListener(listener);
        manager.notifyListeners();

        verify(listener).onStateChanged(any(Element.class));
        reset(listener);

        manager.removeListener(listener);
        manager.notifyListeners();

        verify(listener, never()).onStateChanged(any(Element.class));
    }
}