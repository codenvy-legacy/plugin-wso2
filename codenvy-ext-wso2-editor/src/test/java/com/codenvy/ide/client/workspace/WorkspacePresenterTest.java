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
package com.codenvy.ide.client.workspace;

import com.codenvy.ide.client.elements.Element;
import com.codenvy.ide.client.elements.RootElement;
import com.codenvy.ide.client.elements.widgets.element.ElementChangedListener;
import com.codenvy.ide.client.elements.widgets.element.ElementPresenter;
import com.codenvy.ide.client.inject.factories.ElementWidgetFactory;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * @author Andrey Plotnikov
 */
@RunWith(MockitoJUnitRunner.class)
public class WorkspacePresenterTest {

    private static final int    VIEW_HEIGHT    = 1_000;
    private static final int    VIEW_WIDTH     = 1_000;
    private static final int    ELEMENT_HEIGHT = 100;
    private static final int    ELEMENT_WIDTH  = 100;
    private static final String CONTENT        = "some content";

    @Mock
    private WorkspaceView          view;
    @Mock
    private ElementWidgetFactory   elementWidgetFactory;
    @Mock
    private RootElement            element;
    @Mock
    private ElementPresenter       elementWidget;
    @Mock
    private ElementChangedListener listener;

    private WorkspacePresenter presenter;

    @Before
    public void setUp() throws Exception {
        when(elementWidgetFactory.createElementPresenter(any(Element.class))).thenReturn(elementWidget);

        presenter = new WorkspacePresenter(view, elementWidgetFactory, element);
        presenter.addElementChangedListener(listener);
    }

    private void prepareViewSize(int height, int width) {
        when(view.getHeight()).thenReturn(height);
        when(view.getWidth()).thenReturn(width);
    }

    private void prepareElementWidget(int height, int width) {
        when(elementWidget.getHeight()).thenReturn(height);
        when(elementWidget.getWidth()).thenReturn(width);
    }

    private void prepareDefaultUseCase() {
        prepareViewSize(ELEMENT_HEIGHT, ELEMENT_WIDTH);
        prepareElementWidget(VIEW_HEIGHT, VIEW_WIDTH);
    }

    private void elementWidgetShouldBeNotResized() {
        verify(elementWidget, never()).setHeight(anyInt());
        verify(elementWidget, never()).setWidth(anyInt());
    }

    @Test
    public void viewShouldBePrepared() throws Exception {
        verify(view).setDelegate(presenter);
        verify(elementWidgetFactory).createElementPresenter(element);
        verify(elementWidget).addElementChangedListener(presenter);
        verify(view).setElement(elementWidget);
    }

    @Test
    public void elementShouldBeResizedWhenParentSizeIsBigger() throws Exception {
        prepareViewSize(VIEW_HEIGHT, VIEW_WIDTH);
        prepareElementWidget(ELEMENT_HEIGHT, ELEMENT_WIDTH);

        presenter.resize();

        verify(elementWidget).setHeight(VIEW_HEIGHT);
        verify(elementWidget).setWidth(VIEW_WIDTH);
    }

    @Test
    public void elementShouldBeResizedWhenElementSizeIsBigger() throws Exception {
        prepareDefaultUseCase();

        presenter.resize();

        elementWidgetShouldBeNotResized();
    }

    @Test
    public void listenerShouldBeNotified() throws Exception {
        prepareDefaultUseCase();

        presenter.onElementChanged();

        elementWidgetShouldBeNotResized();
        verify(listener).onElementChanged();
    }

    @Test
    public void elementShouldBeSerialized() throws Exception {
        when(element.serialize()).thenReturn(CONTENT);

        assertEquals(CONTENT, presenter.serialize());

        verify(element).serialize();
    }

    @Test
    public void elementShouldBeDeserialized() throws Exception {
        prepareDefaultUseCase();

        presenter.deserialize(CONTENT);

        verify(element).deserialize(CONTENT);

        elementWidgetShouldBeNotResized();
    }

    @Test
    public void viewShouldBeShown() throws Exception {
        prepareDefaultUseCase();

        AcceptsOneWidget container = mock(AcceptsOneWidget.class);

        presenter.go(container);

        verify(container).setWidget(view);

        elementWidgetShouldBeNotResized();
    }

    @Test
    public void workspaceShouldBeResizedWhenBrowserWindowWasChanged() throws Exception {
        prepareViewSize(VIEW_HEIGHT, VIEW_WIDTH);
        prepareElementWidget(ELEMENT_HEIGHT, ELEMENT_WIDTH);

        presenter.onWindowResize();

        verify(elementWidget).setHeight(VIEW_HEIGHT);
        verify(elementWidget).setWidth(VIEW_WIDTH);
    }

}