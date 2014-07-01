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

import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.ext.wso2.client.commons.XsdSchemaParser;
import com.codenvy.ide.ext.wso2.client.editor.text.XmlCodeAssistProcessor;
import com.codenvy.ide.text.Document;
import com.codenvy.ide.text.Position;
import com.codenvy.ide.text.Region;
import com.codenvy.ide.texteditor.api.CodeAssistCallback;
import com.codenvy.ide.texteditor.api.TextEditorPartView;
import com.codenvy.ide.texteditor.api.codeassistant.CompletionProposal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Answers.RETURNS_MOCKS;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link com.codenvy.ide.ext.wso2.client.editor.text.XmlCodeAssistProcessor}.
 *
 * @author Valeriy Svydenko
 */
@RunWith(MockitoJUnitRunner.class)
public class XmlCodeAssistProcessorTest {
    private XmlCodeAssistProcessor xmlCodeAssistProcessor;
    @Mock
    private XsdSchemaParser        xsdSchemaParser;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private TextEditorPartView     textEditorPartView;
    @Mock
    private CodeAssistCallback     callback;
    @Mock(answer = RETURNS_MOCKS)
    private Document               document;
    @Mock(answer = RETURNS_MOCKS)
    private Position               position;
    @Mock(answer = RETURNS_MOCKS)
    private Region                 region;

    @Before
    public void setUp() throws Exception {
        this.xmlCodeAssistProcessor = new XmlCodeAssistProcessor(xsdSchemaParser);
    }

    @Test
    public void completionProposalsShouldBeReturnCallbackWithNullWhenSomethingIsSelected() {
        when(textEditorPartView.getSelection().hasSelection()).thenReturn(true);

        xmlCodeAssistProcessor.computeCompletionProposals(textEditorPartView, 0, callback);

        verify(callback).proposalComputed(Matchers.<CompletionProposal[]>eq(null));
        verify(textEditorPartView, never()).getDocument();
    }

    private void prepareTriggerString(String text) throws Exception {
        when(textEditorPartView.getSelection().hasSelection()).thenReturn(false);
        when(textEditorPartView.getDocument()).thenReturn(document);
        when(textEditorPartView.getSelection().getCursorPosition()).thenReturn(position);
        when(document.getLineOfOffset(anyInt())).thenReturn(2);
        when(document.getLineInformation(anyInt())).thenReturn(region);
        when(document.get(anyInt(), anyInt())).thenReturn(text);
        when(document.getNumberOfLines()).thenReturn(3);
    }

    @Test
    public void completionProposalsShouldBeCompletedWhenLastTagSymbolIsGreaterThanSymbol() throws Exception {
        prepareTriggerString("<tag> text");

        xmlCodeAssistProcessor.computeCompletionProposals(textEditorPartView, 0, callback);

        verify(document, never()).getLineInformationOfOffset(anyInt());
    }

    @Test
    public void completionProposalsShouldBeCompletedWhenTriggeringStringStartsWithLessThanSymbol() throws Exception {
        prepareTriggerString("<text");

        xmlCodeAssistProcessor.computeCompletionProposals(textEditorPartView, 0, callback);

        verify(document, never()).getLineInformationOfOffset(anyInt());
    }

    @Test
    public void completionProposalsShouldBeCompletedWhenTriggeringStringStartsWithSlashSymbol() throws Exception {
        prepareTriggerString(" text/");

        xmlCodeAssistProcessor.computeCompletionProposals(textEditorPartView, 0, callback);

        verify(document, never()).getLineInformationOfOffset(anyInt());
    }

    @Test
    public void completionProposalsShouldBeCompletedIfNoProposalsForAttribute() throws Exception {
        prepareTriggerString("<log attribute=\"value\"");
        Array<String> attributesName = Collections.createArray();
        attributesName.add("attribute");
        when(xsdSchemaParser.getAttributes("log")).thenReturn(attributesName);

        xmlCodeAssistProcessor.computeCompletionProposals(textEditorPartView, 0, callback);

        verify(callback).proposalComputed(Matchers.<CompletionProposal[]>eq(null));
    }

    private void prepareTwoProposals() throws Exception {
        prepareTriggerString("<log attribute=\"value\" ");
        Array<String> attributesName = Collections.createArray();
        attributesName.add("attribute");
        attributesName.add("attribute1");
        attributesName.add("attribute2");
        when(xsdSchemaParser.getAttributes("log")).thenReturn(attributesName);
    }

    @Test
    public void completionProposalsShouldBeCompletedWithTwoProposalsForAttribute() throws Exception {
        prepareTwoProposals();

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();
                CompletionProposal[] proposals = (CompletionProposal[])arguments[0];

                assertEquals(2, proposals.length);
                assertEquals("attribute1", proposals[0].getDisplayString());
                assertEquals("attribute2", proposals[1].getDisplayString());

                return null;
            }
        }).when(callback).proposalComputed((CompletionProposal[])anyObject());

        xmlCodeAssistProcessor.computeCompletionProposals(textEditorPartView, 0, callback);

        verify(callback).proposalComputed((CompletionProposal[])notNull());
    }

    private void prepareOneProposal() throws Exception {
        prepareTriggerString("<log at");
        Array<String> attributesName = Collections.createArray();
        attributesName.add("attribute");
        attributesName.add("description");
        attributesName.add("tag");
        when(xsdSchemaParser.getAttributes("log")).thenReturn(attributesName);
    }

    @Test
    public void completionProposalsShouldBeCompletedWithOneProposal() throws Exception {
        prepareOneProposal();

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();
                CompletionProposal[] proposals = (CompletionProposal[])arguments[0];

                assertEquals(1, proposals.length);
                assertEquals("attribute", proposals[0].getDisplayString());

                return null;
            }
        }).when(callback).proposalComputed((CompletionProposal[])anyObject());

        xmlCodeAssistProcessor.computeCompletionProposals(textEditorPartView, 0, callback);

        verify(callback).proposalComputed((CompletionProposal[])notNull());
    }

    private void prepareOneProposal2() throws Exception {
        prepareTriggerString("<log at1=\"v1\" at2=\"v2\" at");
        Array<String> attributesName = Collections.createArray();
        attributesName.add("at1");
        attributesName.add("at2");
        attributesName.add("attribute");
        when(xsdSchemaParser.getAttributes("log")).thenReturn(attributesName);
    }

    @Test
    public void completionProposalsShouldBeCompletedWithOneProposal2() throws Exception {
        prepareOneProposal2();

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();
                CompletionProposal[] proposals = (CompletionProposal[])arguments[0];

                assertEquals(1, proposals.length);
                assertEquals("attribute", proposals[0].getDisplayString());

                return null;
            }
        }).when(callback).proposalComputed((CompletionProposal[])anyObject());

        xmlCodeAssistProcessor.computeCompletionProposals(textEditorPartView, 0, callback);

        verify(callback).proposalComputed((CompletionProposal[])notNull());
    }
}
