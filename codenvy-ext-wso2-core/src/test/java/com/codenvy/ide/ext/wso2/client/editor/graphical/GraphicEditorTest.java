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
package com.codenvy.ide.ext.wso2.client.editor.graphical;

import com.codenvy.ide.api.editor.EditorInput;
import com.codenvy.ide.client.editor.WSO2Editor;
import com.codenvy.ide.client.inject.Injector;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Andrey Plotnikov
 */
@RunWith(MockitoJUnitRunner.class)
public class GraphicEditorTest {

    private static final String TEXT = "some text";

    @Mock
    private EditorViewFactory          editorViewFactory;
    @Mock
    private Injector                   editorInjector;
    @Mock
    private GraphicEditorView          view;
    @Mock
    private WSO2Editor                 editor;
    @Mock
    private AsyncCallback<EditorInput> callback;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private EditorInput                input;

    private GraphicEditor graphicEditor;

    @Before
    public void setUp() throws Exception {
        when(editorInjector.getEditor()).thenReturn(editor);
        when(editorViewFactory.getEditorView(any(WSO2Editor.class))).thenReturn(view);

        graphicEditor = new GraphicEditor(editorViewFactory, editorInjector);
    }

    @Test
    public void editorComponentsShouldBePrepared() throws Exception {
        verify(editor).addListener(graphicEditor);
        verify(view).setDelegate(graphicEditor);
    }

    @Test
    public void dirtyStateShouldBeChangedWhenDoSaveMethodIsExecuted() throws Exception {
        assertFalse(graphicEditor.isDirty());

        graphicEditor.onChanged();

        assertTrue(graphicEditor.isDirty());

        graphicEditor.doSave();

        assertFalse(graphicEditor.isDirty());
    }

    @Test
    public void dirtyStateShouldBeChangedWhenDoSaveMethodWithCallbackIsExecuted() throws Exception {
        assertFalse(graphicEditor.isDirty());

        graphicEditor.onChanged();

        assertTrue(graphicEditor.isDirty());

        graphicEditor.doSave(callback);

        assertFalse(graphicEditor.isDirty());
        verify(callback).onSuccess(any(EditorInput.class));
    }

    @Test
    public void editorTitleShouldBeTheSameAsFileName() throws Exception {
        when(input.getFile().getName()).thenReturn(TEXT);

        graphicEditor.init(input);

        assertEquals(TEXT, graphicEditor.getTitle());
    }

    @Test
    public void editorIconShouldBeTheSameAsEditorInputIcon() throws Exception {
        ImageResource icon = mock(ImageResource.class);
        when(input.getImageResource()).thenReturn(icon);

        graphicEditor.init(input);

        assertEquals(icon, graphicEditor.getTitleImage());
    }

    @Test
    public void tooltipShouldBeEmpty() throws Exception {
        assertNull(graphicEditor.getTitleToolTip());
    }

    @Test
    public void viewShouldBeShown() throws Exception {
        AcceptsOneWidget container = mock(AcceptsOneWidget.class);

        graphicEditor.go(container);

        verify(container).setWidget(view);
    }

    @Test
    public void serializationShouldBeGotFromWSO2Editor() throws Exception {
        when(editor.serialize()).thenReturn(TEXT);

        assertEquals(TEXT, graphicEditor.serialize());
    }

    @Test
    public void deserializationShouldBeExecutedOnWSO2Editor() throws Exception {
        graphicEditor.deserialize(TEXT);

        verify(editor).deserialize(TEXT);
    }

    @Test
    public void toolbarVisibilityShouldBeChanged() throws Exception {
        graphicEditor.changeToolbarVisibility();

        verify(editor).changeToolbarPanelVisibility();
    }

    @Test
    public void propertiesPanelVisibilityShouldBeChanged() throws Exception {
        graphicEditor.changePropertyPanelVisibility();

        verify(editor).changePropertyPanelVisibility();
    }

    @Test
    public void editorShouldBeResized() throws Exception {
        graphicEditor.resizeEditor();

        verify(editor).onEditorDOMChanged();
    }

    @Test
    public void diagramOrientationShouldBeChanged() throws Exception {
        graphicEditor.setHorizontalOrientation(true);

        verify(editor).changeDiagramOrientation(true);
    }

}