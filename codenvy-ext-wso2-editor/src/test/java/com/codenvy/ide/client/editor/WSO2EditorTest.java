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

package com.codenvy.ide.client.editor;

import com.codenvy.ide.client.initializers.Initializer;
import com.codenvy.ide.client.managers.PropertiesPanelManager;
import com.codenvy.ide.client.toolbar.ToolbarPresenter;
import com.codenvy.ide.client.workspace.WorkspacePresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static com.codenvy.ide.client.editor.WSO2Editor.EditorChangeListener;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Andrey Plotnikov
 */
@RunWith(MockitoJUnitRunner.class)
public class WSO2EditorTest {

    private static final String CONTENT = "some content";

    @Mock
    private WSO2EditorView         view;
    @Mock
    private WorkspacePresenter     workspace;
    @Mock
    private ToolbarPresenter       toolbar;
    @Mock
    private PropertiesPanelManager propertiesPanelManager;
    @Mock
    private Initializer            initializer;
    @Mock
    private EditorChangeListener   listener;
    @Mock
    private AcceptsOneWidget       container;

    private WSO2Editor presenter;

    @Before
    public void setUp() throws Exception {
        when(view.getPropertiesPanel()).thenReturn(container);
        when(view.getToolbarPanel()).thenReturn(container);
        when(view.getWorkspacePanel()).thenReturn(container);

        Set<Initializer> intiailizers = new HashSet<>();
        intiailizers.add(initializer);

        presenter = new WSO2Editor(view, workspace, toolbar, propertiesPanelManager, intiailizers);
        presenter.addListener(listener);
    }

    @Test
    public void constructorActionsShouldBeDone() throws Exception {
        verify(view).setDelegate(presenter);
        verify(view).setVisiblePropertyPanel(true);

        verify(workspace).addElementChangedListener(presenter);

        verify(propertiesPanelManager).addPropertyChangedListener(presenter);
        verify(propertiesPanelManager).setContainer(container);

        verify(initializer).initialize();
    }

    @Test
    public void viewShouldBeShown() throws Exception {
        presenter.go(container);

        verify(container).setWidget(view);
        verify(toolbar).go(container);
        verify(workspace).go(container);
    }

    @Test
    public void listenersShouldBeNotifiedWhenElementWasChanged() throws Exception {
        presenter.onElementChanged();

        verify(listener).onChanged();
    }

    @Test
    public void listenersShouldBeNotifiedWhenPropertyWasChanged() throws Exception {
        presenter.onPropertyChanged();

        verify(listener).onChanged();
    }

    @Test
    public void workspaceShouldBeSerialized() throws Exception {
        when(workspace.serialize()).thenReturn(CONTENT);

        assertEquals(CONTENT, presenter.serialize());
        verify(workspace).serialize();
    }

    @Test
    public void workspaceShouldBeDeserialized() throws Exception {
        presenter.deserialize(CONTENT);

        verify(workspace).deserialize(CONTENT);
    }

    @Test
    public void propertiesPanelShouldBeShown() throws Exception {
        reset(view);

        presenter.onShowPropertyButtonClicked();

        verify(view).setVisiblePropertyPanel(true);
    }

    @Test
    public void propertiesPanelShouldBeHidden() throws Exception {
        reset(view);

        presenter.onHidePanelButtonClicked();

        verify(view).setVisiblePropertyPanel(false);
    }

    @Test
    public void workspaceShouldBeResized() throws Exception {
        presenter.onEditorDOMChanged();

        verify(workspace).resize();
    }

    @Test
    public void toolbarShouldBeShown() {
        reset(view);

        presenter.changeToolbarPanelVisibility();
        verify(view).setToolbarPanelVisibility(eq(false));

        presenter.changeToolbarPanelVisibility();
        verify(view).setToolbarPanelVisibility(eq(true));
    }

    @Test
    public void toolbarPanelShouldBeHidden() {
        presenter.onCloseToolbarButtonClicked();

        verify(view).hideToolbarPanel();
    }

}