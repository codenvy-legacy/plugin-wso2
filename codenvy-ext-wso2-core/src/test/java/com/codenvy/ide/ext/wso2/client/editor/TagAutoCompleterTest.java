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

import com.codenvy.ide.api.text.Document;
import com.codenvy.ide.api.text.DocumentCommand;
import com.codenvy.ide.api.texteditor.TextEditorPartView;
import com.codenvy.ide.ext.wso2.client.editor.text.TagAutoCompleter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Answers.RETURNS_MOCKS;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link TagAutoCompleter}. Use case when all inputted words must be autocompleted.
 *
 * @author Andrey Plotnikov
 */
@RunWith(Parameterized.class)
public class TagAutoCompleterTest {

    @Mock(answer = RETURNS_MOCKS)
    private Document           document;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private TextEditorPartView editor;
    @InjectMocks
    private TagAutoCompleter   tagAutoCompleter;

    @Parameter(0)
    public String text;
    @Parameter(1)
    public String autocomplete;

    @Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {"<", "></>"},
                {"< ", "></>"},
                {"<tag", "></tag>"},
                {"<tag ", "></tag>"},
                {"<tag name=\"name\"", "></tag>"},
                {"<tag name=\"name\" ", "></tag>"}});
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void textShouldBeCompleted() throws Exception {
        when(document.get(anyInt(), anyInt())).thenReturn(text);

        DocumentCommand command = new DocumentCommand();
        command.text = ">";

        tagAutoCompleter.customizeDocumentCommand(document, command);

        assertEquals(autocomplete, command.text);
    }

}