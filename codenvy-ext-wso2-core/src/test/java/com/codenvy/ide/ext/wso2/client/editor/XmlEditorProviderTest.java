/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 * [2012] - [2013] Codenvy, S.A. 
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
import com.codenvy.ide.api.editor.EditorPartPresenter;
import com.codenvy.ide.api.notification.NotificationManager;
import com.google.inject.Provider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link XmlEditorConfiguration}.
 *
 * @author Andrey Plotnikov
 */
@RunWith(MockitoJUnitRunner.class)
public class XmlEditorProviderTest {

    @Mock
    private CodenvyTextEditor           codenvyTextEditor;
    @Mock
    private DocumentProvider            documentProvider;
    @Mock
    private Provider<CodenvyTextEditor> editorProvider;
    @Mock
    private NotificationManager         notificationManager;
    @InjectMocks
    private XmlEditorProvider           xmlEditorProvider;

    @Before
    public void setUp() throws Exception {
        when(editorProvider.get()).thenReturn(codenvyTextEditor);
    }

    @Test
    public void editorShouldBePrepared() throws Exception {
        EditorPartPresenter editor = xmlEditorProvider.getEditor();

        assertEquals(codenvyTextEditor, editor);

        verify(editorProvider).get();
        verify(codenvyTextEditor).initialize((XmlEditorConfiguration)anyObject(), eq(documentProvider), eq(notificationManager));
    }
}