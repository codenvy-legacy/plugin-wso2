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

import com.codenvy.ide.collections.StringMap;
import com.codenvy.ide.ext.wso2.client.editor.text.AutoCompleterFactory;
import com.codenvy.ide.ext.wso2.client.editor.text.TagAutoCompleter;
import com.codenvy.ide.ext.wso2.client.editor.text.XmlCodeAssistProcessor;
import com.codenvy.ide.ext.wso2.client.editor.text.XmlEditorConfiguration;
import com.codenvy.ide.text.Document;
import com.codenvy.ide.texteditor.api.AutoEditStrategy;
import com.codenvy.ide.texteditor.api.TextEditorPartView;
import com.codenvy.ide.texteditor.api.codeassistant.CodeAssistProcessor;
import com.codenvy.ide.texteditor.api.parser.CmParser;
import com.google.inject.Provider;
import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTestWithMockito;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Answers.RETURNS_MOCKS;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link XmlEditorConfiguration}.
 *
 * @author Andrey Plotnikov
 */
@GwtModule("com.codenvy.ide.ext.wso2.WSO2")
public class XmlEditorConfigurationTest extends GwtTestWithMockito {

    @Mock
    private TextEditorPartView               view;
    @Mock(answer = RETURNS_MOCKS)
    private AutoCompleterFactory             autoCompleterFactory;
    @Mock
    private Provider<XmlCodeAssistProcessor> xmlCodeAssistProcessorProvider;

    @InjectMocks
    private XmlEditorConfiguration xmlEditorConfiguration;

    @Test
    public void parserShouldBeInitialized() throws Exception {
        CmParser parser = (CmParser)xmlEditorConfiguration.getParser(view);

        assertNotNull(parser);
    }

    @Test
    public void configurationShouldContainTagAutoCompleter() throws Exception {
        AutoEditStrategy[] autoEditStrategies = xmlEditorConfiguration.getAutoEditStrategies(view, "contentType");

        assertNotNull(autoEditStrategies);
        assertEquals(1, autoEditStrategies.length);

        AutoEditStrategy autoEditStrategy = autoEditStrategies[0];
        assertTrue(autoEditStrategy instanceof TagAutoCompleter);

        verify(autoCompleterFactory).createAutoCompleter(eq(view));
    }

    @Test
    public void configurationShouldContainCodeAssistProcessor() {
        XmlCodeAssistProcessor codeAssistProcessor = mock(XmlCodeAssistProcessor.class);
        when(xmlCodeAssistProcessorProvider.get()).thenReturn(codeAssistProcessor);

        StringMap<CodeAssistProcessor> codeAssistProcessorStringMap = xmlEditorConfiguration.getContentAssistantProcessors(view);

        assertNotNull(codeAssistProcessorStringMap);
        assertEquals(1, codeAssistProcessorStringMap.size());

        CodeAssistProcessor processor = codeAssistProcessorStringMap.get(Document.DEFAULT_CONTENT_TYPE);
        assertEquals(codeAssistProcessor, processor);

        verify(xmlCodeAssistProcessorProvider).get();
    }
}