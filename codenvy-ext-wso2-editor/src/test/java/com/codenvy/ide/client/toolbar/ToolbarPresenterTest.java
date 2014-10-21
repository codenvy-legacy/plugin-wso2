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
import com.codenvy.ide.client.elements.Element;
import com.codenvy.ide.client.inject.factories.ToolbarFactory;
import com.codenvy.ide.client.managers.SelectionManager;
import com.codenvy.ide.client.toolbar.group.ToolbarGroupPresenter;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.codenvy.ide.client.toolbar.group.ToolbarGroupPresenter.OpenGroupToolbarListener;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isNull;
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

    private static final String FIRST_GROUP_ID  = "group id one";
    private static final String FIRST_TITLE     = "title one";
    private static final String SECOND_GROUP_ID = "group id two";
    private static final String SECOND_TITLE    = "title two";
    private static final String TOOLTIP         = "tooltip";
    private static final String EDITOR_STATE    = "editor state";

    @Mock
    private ImageResource                  icon;
    @Mock
    private ToolbarView                    view;
    @Mock(answer = Answers.RETURNS_MOCKS)
    private ToolbarFactory                 toolbarFactory;
    @Mock
    private WSO2EditorLocalizationConstant locale;
    @Mock
    private SelectionManager               selectionManager;
    @InjectMocks
    private ToolbarPresenter               presenter;

    @Test
    public void viewShouldBePrepared() throws Exception {
        verify(view).setDelegate(presenter);
    }

    @Test
    public void toolbarGroupShouldBeAdded() throws Exception {
        presenter.addGroup(FIRST_GROUP_ID, FIRST_TITLE);

        verify(locale, never()).errorToolbarGroupWasAlreadyRegistered(FIRST_GROUP_ID);
        verify(toolbarFactory).createToolbarGroupPresenter(FIRST_TITLE);
    }

    @Test
    public void toolbarGroupShouldBeNotAdded() throws Exception {
        presenter.addGroup(FIRST_GROUP_ID, FIRST_TITLE);

        reset(toolbarFactory);

        presenter.addGroup(FIRST_GROUP_ID, FIRST_TITLE);

        verify(locale).errorToolbarGroupWasAlreadyRegistered(FIRST_GROUP_ID);
        verify(toolbarFactory, never()).createToolbarGroupPresenter(FIRST_TITLE);
    }

    @Test
    public void toolbarItemShouldBeAdded() throws Exception {
        presenter.addGroup(FIRST_GROUP_ID, FIRST_TITLE);

        reset(toolbarFactory);

        presenter.addItem(FIRST_GROUP_ID, FIRST_TITLE, TOOLTIP, icon, EDITOR_STATE);

        verify(locale, never()).errorToolbarGroupHasNotRegisteredYet(FIRST_GROUP_ID);
        verify(locale, never()).errorToolbarEditorStateWasAlreadyAdded(EDITOR_STATE);
        verify(toolbarFactory).createToolbarItemPresenter(FIRST_TITLE, TOOLTIP, icon, EDITOR_STATE);
    }

    @Test
    public void toolbarItemShouldBeNotAddedWhenGroupHasNotRegistered() throws Exception {
        presenter.addItem(FIRST_GROUP_ID, FIRST_TITLE, TOOLTIP, icon, EDITOR_STATE);

        verify(locale).errorToolbarGroupHasNotRegisteredYet(FIRST_GROUP_ID);
        verify(locale, never()).errorToolbarEditorStateWasAlreadyAdded(EDITOR_STATE);
        verify(toolbarFactory, never()).createToolbarItemPresenter(FIRST_TITLE, TOOLTIP, icon, EDITOR_STATE);
    }

    @Test
    public void toolbarItemShouldBeNotAddedWhenEditorStateHasBeenRegistered() throws Exception {
        presenter.addGroup(FIRST_GROUP_ID, FIRST_TITLE);
        presenter.addItem(FIRST_GROUP_ID, FIRST_TITLE, TOOLTIP, icon, EDITOR_STATE);

        reset(toolbarFactory);

        presenter.addItem(FIRST_GROUP_ID, FIRST_TITLE, TOOLTIP, icon, EDITOR_STATE);

        verify(locale, never()).errorToolbarGroupHasNotRegisteredYet(FIRST_GROUP_ID);
        verify(locale).errorToolbarEditorStateWasAlreadyAdded(EDITOR_STATE);
        verify(toolbarFactory, never()).createToolbarItemPresenter(FIRST_TITLE, TOOLTIP, icon, EDITOR_STATE);
    }

    @Test
    public void viewShouldBeDrawn() throws Exception {
        AcceptsOneWidget container = mock(AcceptsOneWidget.class);
        ToolbarGroupPresenter toolbarGroup = mock(ToolbarGroupPresenter.class);

        when(toolbarFactory.createToolbarGroupPresenter(anyString())).thenReturn(toolbarGroup);
        presenter.addGroup(FIRST_GROUP_ID, FIRST_TITLE);

        presenter.go(container);

        verify(container).setWidget(view);
        verify(view).addGroup(toolbarGroup);
    }

    @Test
    public void onlyOneGroupShouldBeOpened() throws Exception {
        ToolbarGroupPresenter toolbarGroupOne = mock(ToolbarGroupPresenter.class);
        ToolbarGroupPresenter toolbarGroupTwo = mock(ToolbarGroupPresenter.class);

        when(toolbarFactory.createToolbarGroupPresenter(FIRST_TITLE)).thenReturn(toolbarGroupOne);
        when(toolbarFactory.createToolbarGroupPresenter(SECOND_TITLE)).thenReturn(toolbarGroupTwo);

        presenter.addGroup(FIRST_GROUP_ID, FIRST_TITLE);
        presenter.addGroup(SECOND_GROUP_ID, SECOND_TITLE);

        presenter.onOpenToolbarGroup(toolbarGroupOne);

        verify(toolbarGroupOne).unfold();
        verify(toolbarGroupOne, never()).fold();
        verify(toolbarGroupOne).addListener(any(OpenGroupToolbarListener.class));

        verify(toolbarGroupTwo, never()).unfold();
        verify(toolbarGroupTwo).fold();
        verify(toolbarGroupTwo).addListener(any(OpenGroupToolbarListener.class));
    }

    @Test
    public void noElementShouldBeSelectedWhenOneClickedOnEmptyPlace() throws Exception {
        presenter.onMainPanelClicked();

        verify(selectionManager).setElement(isNull(Element.class));
    }

}