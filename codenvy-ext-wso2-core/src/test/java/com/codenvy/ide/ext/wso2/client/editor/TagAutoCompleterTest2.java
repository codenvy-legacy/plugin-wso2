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

import com.codenvy.ide.ext.wso2.client.editor.text.TagAutoCompleter;
import com.codenvy.ide.text.Document;
import com.codenvy.ide.text.DocumentCommand;
import com.codenvy.ide.texteditor.api.TextEditorPartView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.runners.Parameterized.Parameters;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Answers.RETURNS_MOCKS;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link TagAutoCompleter}. Use case when all inputted words must be not autocompleted.
 *
 * @author Andrey Plotnikov
 */
@RunWith(Parameterized.class)
public class TagAutoCompleterTest2 {

    @Mock(answer = RETURNS_MOCKS)
    private Document           document;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private TextEditorPartView editor;
    @InjectMocks
    private TagAutoCompleter   tagAutoCompleter;
    private String             text;
    private String             nextElement;

    @Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {"<", " "},
                {"<", "a"},
                {"<t name=\"", " "},
                {"<t name=\"", "a"},
                {"<t name=\"name\"", "a"},
                {"<t name=\"name\"", " "},
                {"<t name=\"name\"", "a"},
                {"<t name=\"name\" ", " "},
                {"<t name=\"name\" ", "a"},
                {"<t name=\"name\">", " "},
                {"<t name=\"name\">", "a"},
                {"<t></t>", ">"},
                {"<t></t>", ">>"},
                {"<t></t>", "<"},
                {"<t></t>", "<<"}
// TODO Those tests are skipped because this functional isn't supported yet.
//                {"<t></t", ">"},
//                {"<t name=\"name\"/", ">"}
        });
    }

    public TagAutoCompleterTest2(String text, String nextElement) {
        this.text = text;
        this.nextElement = nextElement;
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void textShouldBeNotCompleted() throws Exception {

        when(document.get(anyInt(), anyInt())).thenReturn(text);

        DocumentCommand command = new DocumentCommand();
        command.text = nextElement;

        tagAutoCompleter.customizeDocumentCommand(document, command);

        assertEquals(nextElement, command.text);
    }
}