/*
 * CODENVY CONFIDENTIAL
 * __________________
 *
 * [2012] - [2014] Codenvy, S.A.
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
 */
package com.codenvy.ide.ext.wso2.client.editor;

import com.codenvy.ide.api.editor.CodenvyTextEditor;
import com.codenvy.ide.api.editor.DocumentProvider;
import com.codenvy.ide.api.editor.EditorInput;
import com.codenvy.ide.api.editor.EditorPartPresenter;
import com.codenvy.ide.api.notification.Notification;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.ui.workspace.PropertyListener;
import com.codenvy.ide.ext.wso2.client.commons.XMLParserUtil;
import com.codenvy.ide.ext.wso2.client.editor.graphical.GraphicEditor;
import com.codenvy.ide.ext.wso2.client.editor.text.XmlEditorConfiguration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.inject.Provider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.wso2.developerstudio.eclipse.gmf.esb.EsbSequence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link ESBConfEditor}.
 *
 * @author Andrey Plotnikov
 */
@RunWith(MockitoJUnitRunner.class)
public class ESBConfEditorTest {

    @Mock
    private ESBConfEditorView                view;
    @Mock
    private DocumentProvider                 documentProvider;
    @Mock
    private Provider<CodenvyTextEditor>      editorProvider;
    @Mock
    private Provider<XmlEditorConfiguration> xmlEditorConfigurationProvider;
    @Mock
    private NotificationManager              notificationManager;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private GraphicEditor                    graphicEditor;
    @Mock
    private CodenvyTextEditor                textEditor;
    @Mock
    private EditorInput                      editorInput;
    @Mock
    private XMLParserUtil                    xmlParserUtil;
    @Mock
    private ESBToXMLMapper                   esbToXMLMapper;
    private ESBConfEditor                    esbConfEditor;

    @Before
    public void setUp() throws Exception {
        XmlEditorConfiguration xmlEditorConfiguration = mock(XmlEditorConfiguration.class);
        when(xmlEditorConfigurationProvider.get()).thenReturn(xmlEditorConfiguration);

        when(editorProvider.get()).thenReturn(textEditor);

        esbConfEditor = new ESBConfEditor(view,
                                          documentProvider,
                                          editorProvider,
                                          xmlEditorConfigurationProvider,
                                          notificationManager,
                                          graphicEditor,
                                          esbToXMLMapper,
                                          xmlParserUtil);

        verify(view).setDelegate(eq(esbConfEditor));
        verify(editorProvider).get();
        verify(textEditor).initialize(eq(xmlEditorConfiguration), eq(documentProvider), eq(notificationManager));
        verify(textEditor).addPropertyListener((PropertyListener)anyObject());
        verify(graphicEditor).addPropertyListener((PropertyListener)anyObject());
    }

    @Test
    public void editorsShouldBeInitialized() throws Exception {
        esbConfEditor.init(editorInput);

        verify(view).setEnableTextEditorButton(eq(false));
        verify(view).setEnableGraphicalEditorButton(eq(true));
        verify(view).setEnableBothEditorButton(eq(true));
        verify(view).showTextEditor(eq(textEditor));

        verify(graphicEditor).init(eq(editorInput));
        verify(textEditor).init(eq(editorInput));
    }

    @Test
    public void changesInTextEditorShouldBeSaved() throws Exception {
        when(textEditor.isDirty()).thenReturn(true);

        esbConfEditor.doSave();

        verify(textEditor).doSave();
        verify(graphicEditor, never()).doSave();
    }

    @Test
    public void changesInGraphicalEditorShouldBeSaved() throws Exception {
        when(graphicEditor.isDirty()).thenReturn(true);

        esbConfEditor.doSave();

        verify(textEditor, never()).doSave();
        verify(graphicEditor).doSave();
    }

    @Test
    public void changesInBothEditorsShouldBeSaved() throws Exception {
        when(textEditor.isDirty()).thenReturn(true);
        when(graphicEditor.isDirty()).thenReturn(true);

        esbConfEditor.doSave();

        verify(textEditor).doSave();
        verify(graphicEditor).doSave();
    }

    @Test
    public void doSaveAsMethodShouldBeExecuted() throws Exception {
        esbConfEditor.doSaveAs();

        verify(textEditor).doSaveAs();
    }

    @Test
    public void activateMethodShouldBeExecuted() throws Exception {
        esbConfEditor.activate();

        verify(textEditor).activate();
    }

    @Test
    public void noChangesShouldBeSaved() throws Exception {
        esbConfEditor.doSave();

        verify(textEditor, never()).doSave();
        verify(graphicEditor, never()).doSave();
    }

    @Test
    public void editorShouldBeDirtyWhenTextEditorIsDirty() throws Exception {
        when(textEditor.isDirty()).thenReturn(true);

        assertTrue(esbConfEditor.isDirty());
    }

    @Test
    public void editorShouldBeDirtyWhenWhenGraphicalEditorIsDirty() throws Exception {
        when(graphicEditor.isDirty()).thenReturn(true);

        assertTrue(esbConfEditor.isDirty());
    }

    @Test
    public void editorShouldBeDirtyWhenWhenBothEditorsAreDirty() throws Exception {
        when(textEditor.isDirty()).thenReturn(true);
        when(graphicEditor.isDirty()).thenReturn(true);

        assertTrue(esbConfEditor.isDirty());
    }

    @Test
    public void editorShouldBeNotDirty() throws Exception {
        assertFalse(esbConfEditor.isDirty());
    }

    @Test
    public void titleShouldContainAsterisk() throws Exception {
        esbConfEditor.init(editorInput);

        when(textEditor.isDirty()).thenReturn(true);

        String title = esbConfEditor.getTitle();

        assertNotNull(title);
        assertTrue(title.contains("*"));

        verify(editorInput).getName();
    }

    @Test
    public void titleShouldNotContainAsterisk() throws Exception {
        esbConfEditor.init(editorInput);
        when(editorInput.getName()).thenReturn("name");

        String title = esbConfEditor.getTitle();

        assertNotNull(title);
        assertFalse(title.contains("*"));

        verify(editorInput).getName();
    }

    @Test
    public void imageShouldBeReturned() throws Exception {
        ImageResource image = mock(ImageResource.class);
        when(editorInput.getImageResource()).thenReturn(image);

        esbConfEditor.init(editorInput);

        assertEquals(image, esbConfEditor.getTitleImage());
        verify(editorInput).getImageResource();
    }

    @Test
    public void tooltipShouldBeEmpty() throws Exception {
        assertNull(esbConfEditor.getTitleToolTip());
    }

    @Test
    public void viewShouldBeShown() throws Exception {
        AcceptsOneWidget container = mock(AcceptsOneWidget.class);

        esbConfEditor.go(container);

        verify(container).setWidget(eq(view));
    }

    @Test
    public void textEditorShouldBeShown() throws Exception {
        esbConfEditor.onTextEditorButtonClicked();

        verify(view).setEnableTextEditorButton(eq(false));
        verify(view).setEnableGraphicalEditorButton(eq(true));
        verify(view).setEnableBothEditorButton(eq(true));

        verify(view).showTextEditor(eq(textEditor));
    }

    @Test
    public void graphicalEditorShouldBeShown() throws Exception {
        esbConfEditor.onGraphicalEditorButtonClicked();

        verify(view).setEnableTextEditorButton(eq(true));
        verify(view).setEnableGraphicalEditorButton(eq(false));
        verify(view).setEnableBothEditorButton(eq(true));

        verify(view).showGraphicalEditor(eq(graphicEditor));
    }

    @Test
    public void bothEditorsShouldBeShown() throws Exception {
        esbConfEditor.onAssociateEditorButtonClicked();

        verify(view).setEnableTextEditorButton(eq(true));
        verify(view).setEnableGraphicalEditorButton(eq(true));
        verify(view).setEnableBothEditorButton(eq(false));

        verify(view).showEditors(eq(graphicEditor), eq(textEditor));
    }

    @Test
    public void contentOfTextEditorShouldBeChangedIfPropertyIsDirty() throws Exception {
        Document document = mock(Document.class);
        com.codenvy.ide.text.Document txtDocument = mock(com.codenvy.ide.text.Document.class);
        when(esbToXMLMapper.transform((EsbSequence)anyObject())).thenReturn(document);
        when(textEditor.getDocument()).thenReturn(txtDocument);
        when(xmlParserUtil.formatXMLString((Node)anyObject())).thenReturn("some text");

        esbConfEditor.propertyChanged(graphicEditor, EditorPartPresenter.PROP_DIRTY);

        verify(txtDocument).set(eq("some text"));
    }

    @Test
    public void sequenceTransformationGenerateTheException() throws Exception {
        Exception exception = mock(Exception.class);
        doThrow(exception).when(esbToXMLMapper).transform((EsbSequence)anyObject());

        esbConfEditor.propertyChanged(graphicEditor, EditorPartPresenter.PROP_DIRTY);

        verify(notificationManager).showNotification((Notification)anyObject());
    }
}