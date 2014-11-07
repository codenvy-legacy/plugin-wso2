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
package com.codenvy.ide.ext.wso2.client.editor;

import com.codenvy.ide.api.editor.CodenvyTextEditor;
import com.codenvy.ide.api.editor.EditorInput;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.parts.PropertyListener;
import com.codenvy.ide.api.text.Region;
import com.codenvy.ide.ext.wso2.client.editor.graphical.GraphicEditor;
import com.codenvy.ide.jseditor.client.defaulteditor.DefaultEditorProvider;
import com.codenvy.ide.jseditor.client.document.EmbeddedDocument;
import com.codenvy.ide.jseditor.client.editorconfig.DefaultTextEditorConfiguration;
import com.codenvy.ide.jseditor.client.texteditor.EmbeddedTextEditorPartView;
import com.codenvy.ide.jseditor.client.texteditor.EmbeddedTextEditorPresenter;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.vectomatic.dom.svg.ui.SVGResource;

import static com.codenvy.ide.api.editor.EditorPartPresenter.EditorPartCloseHandler;
import static com.codenvy.ide.api.editor.EditorPartPresenter.PROP_DIRTY;
import static com.codenvy.ide.api.editor.EditorPartPresenter.PROP_INPUT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Andrey Plotnikov
 */
@RunWith(MockitoJUnitRunner.class)
public class ESBConfEditorTest {

    private static final String TEXT = "some text";

    @Mock
    private ESBConfEditorView           view;
    @Mock
    private DefaultEditorProvider       editorProvider;
    @Mock
    private GraphicEditor               graphicEditor;
    @Mock
    private NotificationManager         notificationManager;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private EmbeddedTextEditorPresenter textEditor;
    @Mock
    private EditorInput                 input;
    @Mock
    private AsyncCallback<EditorInput>  callback;
    @Mock
    private AsyncCallback<Void>         closingCallback;
    @Mock
    private EmbeddedTextEditorPartView  textEditorView;

    private ESBConfEditor editor;

    private void prepareDefaultUseCase() {
        when(editorProvider.getEditor()).thenReturn(textEditor);

        editor = new ESBConfEditor(view, editorProvider, graphicEditor, notificationManager);
    }

    @Test
    public void editorComponentsShouldBePrepared() throws Exception {
        prepareDefaultUseCase();

        verify(view).setDelegate(editor);
        verify(view).addGraphicalEditorWidget(graphicEditor);
        verify(view).addTextEditorWidget(textEditor);

        verify(graphicEditor).addPropertyListener(editor);
        verify(textEditor).addPropertyListener(editor);

        verify(textEditor).initialize(any(DefaultTextEditorConfiguration.class), eq(notificationManager));
    }

    @Test
    public void textEditorShouldBeNotPreparedWhenDefaultEditorReturnAnotherOne() throws Exception {
        CodenvyTextEditor codenvyTextEditor = mock(CodenvyTextEditor.class);

        when(editorProvider.getEditor()).thenReturn(codenvyTextEditor);

        editor = new ESBConfEditor(view, editorProvider, graphicEditor, notificationManager);

        verify(view).setDelegate(editor);
        verify(view).addGraphicalEditorWidget(graphicEditor);
        verify(view, never()).addTextEditorWidget(textEditor);

        verify(graphicEditor).addPropertyListener(editor);

        verify(textEditor, never()).addPropertyListener(any(PropertyListener.class));
        verify(codenvyTextEditor, never()).addPropertyListener(any(PropertyListener.class));

        verify(textEditor, never()).initialize(any(DefaultTextEditorConfiguration.class), eq(notificationManager));
    }

    @Test
    public void initProcessShouldBeCoordinatedBetweenBothEditors() throws Exception {
        prepareDefaultUseCase();

        editor.init(input);

        verify(textEditor).init(input);
        verify(graphicEditor).init(input);

        designViewShouldBeEnabled();
    }

    @Test
    public void saveOperationShouldBeCoordinatedBetweenBothEditors() throws Exception {
        prepareDefaultUseCase();

        when(graphicEditor.isDirty()).thenReturn(true);

        editor.doSave();

        verify(graphicEditor).doSave();
        verify(textEditor).doSave();
    }

    @Test
    public void saveOperationShouldBeNotExecuted() throws Exception {
        prepareDefaultUseCase();

        editor.doSave();

        verify(graphicEditor, never()).doSave();
        verify(textEditor, never()).doSave();
    }

    @Test
    public void saveOperationWithCallbackShouldBeCoordinatedBetweenBothEditors() throws Exception {
        prepareDefaultUseCase();

        when(graphicEditor.isDirty()).thenReturn(true);

        editor.doSave(callback);

        verify(graphicEditor).doSave();
        verify(textEditor).doSave(callback);
    }

    @Test
    public void saveAsOperationShouldBeExecutedOnTextEditor() throws Exception {
        prepareDefaultUseCase();

        editor.doSaveAs();

        verify(textEditor).doSaveAs();
    }

    @Test
    public void activeOperationShouldBeExecutedOnTextEditor() throws Exception {
        prepareDefaultUseCase();

        editor.activate();

        verify(textEditor).activate();
    }

    @Test
    public void editorShouldBeDirtyWhenTextEditorIsDirty() throws Exception {
        prepareDefaultUseCase();

        when(textEditor.isDirty()).thenReturn(true);

        assertTrue(editor.isDirty());
    }

    @Test
    public void editorShouldBeDirtyWhenGraphicalEditorIsDirty() throws Exception {
        prepareDefaultUseCase();

        when(graphicEditor.isDirty()).thenReturn(true);

        assertTrue(editor.isDirty());
    }

    @Test
    public void editorShouldBeNotDirty() throws Exception {
        prepareDefaultUseCase();

        assertFalse(editor.isDirty());
    }

    @Test
    public void editorNameShouldHasAsteriskInTitleWhenEditorIsDirty() throws Exception {
        prepareDefaultUseCase();

        when(textEditor.isDirty()).thenReturn(true);
        when(input.getName()).thenReturn(TEXT);
        editor.init(input);

        assertEquals('*' + TEXT, editor.getTitle());
    }

    @Test
    public void editorNameShouldDoesNotHaveAsteriskInTitleWhenEditorIsNotDirty() throws Exception {
        prepareDefaultUseCase();

        when(input.getName()).thenReturn(TEXT);
        editor.init(input);

        assertEquals(TEXT, editor.getTitle());
    }

    @Test
    public void editorIconShouldBeTheSameAsEditorInputIcon() throws Exception {
        prepareDefaultUseCase();

        ImageResource icon = mock(ImageResource.class);
        when(input.getImageResource()).thenReturn(icon);

        editor.init(input);

        assertEquals(icon, editor.getTitleImage());
    }

    @Test
    public void editorSVGIconShouldBeTheSameAsEditorInputIcon() throws Exception {
        prepareDefaultUseCase();

        SVGResource icon = mock(SVGResource.class);
        when(input.getSVGResource()).thenReturn(icon);

        editor.init(input);

        assertEquals(icon, editor.getTitleSVGImage());
    }

    @Test
    public void tooltipShouldBeEmpty() throws Exception {
        prepareDefaultUseCase();

        assertNull(editor.getTitleToolTip());
    }

    @Test
    public void viewShouldBeShown() throws Exception {
        prepareDefaultUseCase();

        AcceptsOneWidget container = mock(AcceptsOneWidget.class);

        editor.go(container);

        verify(container).setWidget(view);
    }

    @Test
    public void sourceViewShouldBeShown() throws Exception {
        prepareDefaultUseCase();

        editor.onSourceViewButtonClicked();

        verify(view).setEnableSourceViewButton(false);
        verify(view).setEnableDesignViewButton(true);
        verify(view).setEnableDualViewButton(true);

        verify(view).showSourceView();
    }

    @Test
    public void designViewShouldBeShown() throws Exception {
        prepareDefaultUseCase();

        editor.onDesignViewButtonClicked();

        designViewShouldBeEnabled();
    }

    private void designViewShouldBeEnabled() {
        verify(view).setEnableSourceViewButton(true);
        verify(view).setEnableDesignViewButton(false);
        verify(view).setEnableDualViewButton(true);

        verify(view).showDesignView();
    }

    @Test
    public void dualViewShouldBeShown() throws Exception {
        prepareDefaultUseCase();

        editor.onDualViewButtonClicked();

        verify(view).setEnableSourceViewButton(true);
        verify(view).setEnableDesignViewButton(true);
        verify(view).setEnableDualViewButton(false);

        verify(view).showDualView();
    }

    @Test
    public void toolbarVisibilityShouldBeChanged() throws Exception {
        prepareDefaultUseCase();

        editor.onChangeToolbarVisibilityClicked();

        verify(graphicEditor).changeToolbarVisibility();
    }

    @Test
    public void editorsShouldBeResizedWhenParentWidgetWasChanged() throws Exception {
        prepareDefaultUseCase();

        when(textEditor.getView()).thenReturn(textEditorView);

        editor.onEditorDOMChanged();

        verify(graphicEditor).resizeEditor();
        verify(textEditorView).onResize();
    }

    @Test
    public void propertiesPanelVisibilityShouldBeChanged() throws Exception {
        prepareDefaultUseCase();

        editor.onPropertyButtonClicked();

        verify(graphicEditor).changePropertyPanelVisibility();
    }

    @Test
    public void closeActionShouldBeExecutedOnClosingCallback() throws Exception {
        prepareDefaultUseCase();

        EditorPartCloseHandler closeHandler = mock(EditorPartCloseHandler.class);
        editor.addCloseHandler(closeHandler);

        editor.onClose(closingCallback);

        verify(textEditor).onClose(closingCallback);
        verify(closeHandler).onClose(editor);
    }

    @Test
    public void textEditorContentShouldBeChanged() throws Exception {
        prepareDefaultUseCase();

        EmbeddedDocument document = mock(EmbeddedDocument.class);
        when(document.getContents()).thenReturn(TEXT);

        when(textEditor.getView().getEmbeddedDocument()).thenReturn(document);
        when(graphicEditor.serialize()).thenReturn(TEXT);

        editor.propertyChanged(graphicEditor, PROP_DIRTY);

        verify(document).replace(any(Region.class), eq(TEXT));
    }

    @Test
    public void graphicalEditorShouldBeDeserializedWhenDirtyPropertyEventIsFired() throws Exception {
        prepareDefaultUseCase();

        when(textEditor.getView().getContents()).thenReturn(TEXT);

        editor.propertyChanged(textEditor, PROP_DIRTY);

        verify(graphicEditor).deserialize(TEXT);
    }

    @Test
    public void graphicalEditorShouldBeDeserializedWhenInputPropertyEventIsFired() throws Exception {
        prepareDefaultUseCase();

        when(textEditor.getView().getContents()).thenReturn(TEXT);

        editor.propertyChanged(textEditor, PROP_INPUT);

        verify(graphicEditor).deserialize(TEXT);
    }

    @Test
    public void graphicalEditorShouldBeDeserializedWhenTextEditorIsChanged() throws Exception {
        prepareDefaultUseCase();

        EmbeddedDocument document = mock(EmbeddedDocument.class);
        when(document.getContents()).thenReturn(TEXT);

        when(textEditor.getView().getEmbeddedDocument()).thenReturn(document);
        when(graphicEditor.serialize()).thenReturn(TEXT);

        editor.propertyChanged(graphicEditor, PROP_DIRTY);

        verify(document).replace(any(Region.class), eq(TEXT));


        editor.propertyChanged(textEditor, PROP_DIRTY);

        verify(graphicEditor, never()).deserialize(anyString());
    }

}