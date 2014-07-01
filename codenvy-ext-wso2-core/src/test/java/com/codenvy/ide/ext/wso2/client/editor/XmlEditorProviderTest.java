/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.ext.wso2.client.editor;

import com.codenvy.ide.api.editor.EditorPartPresenter;
import com.codenvy.ide.ext.wso2.client.editor.text.XmlEditorConfiguration;
import com.codenvy.ide.ext.wso2.client.editor.text.XmlEditorProvider;
import com.google.inject.Provider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
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
    private ESBConfEditor           esbConfEditor;
    @Mock
    private Provider<ESBConfEditor> esbConfEditorProvider;
    @InjectMocks
    private XmlEditorProvider       xmlEditorProvider;

    @Before
    public void setUp() throws Exception {
        when(esbConfEditorProvider.get()).thenReturn(esbConfEditor);
    }

    @Test
    public void editorShouldBePrepared() throws Exception {
        EditorPartPresenter editor = xmlEditorProvider.getEditor();

        assertEquals(esbConfEditor, editor);
        verify(esbConfEditorProvider).get();
    }
}