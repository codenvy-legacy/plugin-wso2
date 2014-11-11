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

import com.codenvy.ide.client.editor.WSO2Editor;
import com.google.gwtmockito.GwtMockitoTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.codenvy.ide.ext.wso2.client.editor.graphical.GraphicEditorView.ActionDelegate;
import static org.mockito.Mockito.verify;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(GwtMockitoTestRunner.class)
public class GraphicEditorViewImplTest {

    @Mock
    private WSO2Editor            editor;
    @Mock
    private ActionDelegate        delegate;
    @InjectMocks
    private GraphicEditorViewImpl graphicEditorView;

    @Before
    public void setUp() throws Exception {
        graphicEditorView.setDelegate(delegate);
    }

    @Test
    public void constructorShouldBeDone() throws Exception {
        verify(editor).go(graphicEditorView.editor);
    }

}