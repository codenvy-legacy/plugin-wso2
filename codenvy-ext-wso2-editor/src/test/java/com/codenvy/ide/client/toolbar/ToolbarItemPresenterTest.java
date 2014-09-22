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
package com.codenvy.ide.client.toolbar;

import com.codenvy.ide.client.EditorState;
import com.codenvy.ide.client.inject.factories.ToolbarFactory;
import com.codenvy.ide.client.toolbar.item.ToolbarItemPresenter;
import com.codenvy.ide.client.toolbar.item.ToolbarItemView;
import com.google.gwt.resources.client.ImageResource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Andrey Plotnikov
 */
@RunWith(MockitoJUnitRunner.class)
public class ToolbarItemPresenterTest {

    private static final String STRING = "some text";

    @Mock
    private EditorState     editorState;
    @Mock
    private ImageResource   icon;
    @Mock
    private ToolbarFactory  toolbarFactory;
    @Mock
    private ToolbarItemView view;

    private ToolbarItemPresenter presenter;

    @Before
    public void setUp() throws Exception {
        when(toolbarFactory.createToolbarItemView(anyString(), anyString(), any(ImageResource.class))).thenReturn(view);

        presenter = new ToolbarItemPresenter(editorState, toolbarFactory, STRING, STRING, icon, STRING);
    }

    @Test
    public void viewShouldBePrepared() throws Exception {
        verify(toolbarFactory).createToolbarItemView(STRING, STRING, icon);
        verify(view).setDelegate(presenter);
    }

    @Test
    public void viewShouldBeReturned() throws Exception {
        assertEquals(view, presenter.getView());
    }

    @Test
    public void editorStateShouldBeChanged() throws Exception {
        presenter.onItemClicked();

        verify(editorState).setState(STRING);
    }

}