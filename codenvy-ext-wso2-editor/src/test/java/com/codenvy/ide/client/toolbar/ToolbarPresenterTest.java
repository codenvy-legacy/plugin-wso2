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

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.inject.factories.ToolbarFactory;
import com.codenvy.ide.client.toolbar.group.ToolbarGroupPresenter;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * @author Andrey Plotnikov
 */
@RunWith(MockitoJUnitRunner.class)
public class ToolbarPresenterTest {

    private static final String GROUP_ID     = "group id";
    private static final String TITLE        = "title";
    private static final String TOOLTIP      = "tooltip";
    private static final String EDITOR_STATE = "editor state";

    @Mock
    private ImageResource                  icon;
    @Mock
    private ToolbarView                    view;
    @Mock(answer = Answers.RETURNS_MOCKS)
    private ToolbarFactory                 toolbarFactory;
    @Mock
    private WSO2EditorLocalizationConstant locale;
    @InjectMocks
    private ToolbarPresenter               presenter;

    @Test
    public void viewShouldBePrepared() throws Exception {
        verify(view).setDelegate(eq(presenter));
    }

    @Test
    public void toolbarGroupShouldBeAdded() throws Exception {
        presenter.addGroup(GROUP_ID, TITLE);

        verify(locale, never()).errorToolbarGroupWasAlreadyRegistered(eq(GROUP_ID));
        verify(toolbarFactory).createToolbarGroupPresenter(eq(TITLE));
    }

    @Test
    public void toolbarGroupShouldBeNotAdded() throws Exception {
        presenter.addGroup(GROUP_ID, TITLE);

        reset(toolbarFactory);

        presenter.addGroup(GROUP_ID, TITLE);

        verify(locale).errorToolbarGroupWasAlreadyRegistered(eq(GROUP_ID));
        verify(toolbarFactory, never()).createToolbarGroupPresenter(eq(TITLE));
    }

    @Test
    public void toolbarItemShouldBeAdded() throws Exception {
        presenter.addGroup(GROUP_ID, TITLE);

        reset(toolbarFactory);

        presenter.addItem(GROUP_ID, TITLE, TOOLTIP, icon, EDITOR_STATE);

        verify(locale, never()).errorToolbarGroupHasNotRegisteredYet(eq(GROUP_ID));
        verify(locale, never()).errorToolbarEditorStateWasAlreadyAdded(eq(EDITOR_STATE));
        verify(toolbarFactory).createToolbarItemPresenter(eq(TITLE), eq(TOOLTIP), eq(icon), eq(EDITOR_STATE));
    }

    @Test
    public void toolbarItemShouldBeNotAddedWhenGroupHasNotRegistered() throws Exception {
        presenter.addItem(GROUP_ID, TITLE, TOOLTIP, icon, EDITOR_STATE);

        verify(locale).errorToolbarGroupHasNotRegisteredYet(eq(GROUP_ID));
        verify(locale, never()).errorToolbarEditorStateWasAlreadyAdded(eq(EDITOR_STATE));
        verify(toolbarFactory, never()).createToolbarItemPresenter(eq(TITLE), eq(TOOLTIP), eq(icon), eq(EDITOR_STATE));
    }

    @Test
    public void toolbarItemShouldBeNotAddedWhenEditorStateHasBeenRegistered() throws Exception {
        presenter.addGroup(GROUP_ID, TITLE);
        presenter.addItem(GROUP_ID, TITLE, TOOLTIP, icon, EDITOR_STATE);

        reset(toolbarFactory);

        presenter.addItem(GROUP_ID, TITLE, TOOLTIP, icon, EDITOR_STATE);

        verify(locale, never()).errorToolbarGroupHasNotRegisteredYet(eq(GROUP_ID));
        verify(locale).errorToolbarEditorStateWasAlreadyAdded(eq(EDITOR_STATE));
        verify(toolbarFactory, never()).createToolbarItemPresenter(eq(TITLE), eq(TOOLTIP), eq(icon), eq(EDITOR_STATE));
    }

    @Test
    public void viewShouldBeDrawn() throws Exception {
        AcceptsOneWidget container = mock(AcceptsOneWidget.class);
        ToolbarGroupPresenter toolbarGroup = mock(ToolbarGroupPresenter.class);

        when(toolbarFactory.createToolbarGroupPresenter(anyString())).thenReturn(toolbarGroup);
        presenter.addGroup(GROUP_ID, TITLE);

        presenter.go(container);

        verify(container).setWidget(eq(view));
        verify(view).addGroup(eq(toolbarGroup));
    }

}