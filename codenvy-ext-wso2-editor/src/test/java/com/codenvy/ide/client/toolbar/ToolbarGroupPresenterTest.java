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

import com.codenvy.ide.client.inject.factories.ToolbarFactory;
import com.codenvy.ide.client.toolbar.group.ToolbarGroupPresenter;
import com.codenvy.ide.client.toolbar.group.ToolbarGroupView;
import com.codenvy.ide.client.toolbar.item.ToolbarItemPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Andrey Plotnikov
 */
@RunWith(MockitoJUnitRunner.class)
public class ToolbarGroupPresenterTest {

    private static final String STRING = "some text";

    @Mock
    private ToolbarFactory   toolbarFactory;
    @Mock
    private ToolbarGroupView view;

    private ToolbarGroupPresenter presenter;

    @Before
    public void setUp() throws Exception {
        when(toolbarFactory.createToolbarGroupView(anyString())).thenReturn(view);

        presenter = new ToolbarGroupPresenter(toolbarFactory, STRING);
    }

    @Test
    public void viewShouldBePrepared() throws Exception {
        verify(toolbarFactory).createToolbarGroupView(eq(STRING));
        verify(view).setDelegate(eq(presenter));

        verify(view).setVisibleItemsPanel(false);
        verify(view).defaultIcon();
    }

    @Test
    public void viewShouldBeReturned() throws Exception {
        assertEquals(view, presenter.getView());
    }

    @Test
    public void toolbarItemShouldBeAdded() throws Exception {
        ToolbarItemPresenter toolbarItem = mock(ToolbarItemPresenter.class);

        presenter.addItem(toolbarItem);

        verify(view).addItem(eq(toolbarItem));
    }

    @Test
    public void itemsPanelShouldBeShown() throws Exception {
        reset(view);

        presenter.onItemClicked();

        verify(view).setVisibleItemsPanel(true);
        verify(view).rotateIcon();

    }

    @Test
    public void itemsPanelShouldBeNotShown() throws Exception {
        presenter.onItemClicked();

        reset(view);

        presenter.onItemClicked();

        verify(view).setVisibleItemsPanel(false);
        verify(view).defaultIcon();
    }

}