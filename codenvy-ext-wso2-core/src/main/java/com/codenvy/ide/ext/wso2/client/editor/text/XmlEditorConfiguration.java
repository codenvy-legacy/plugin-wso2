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
package com.codenvy.ide.ext.wso2.client.editor.text;

import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.collections.StringMap;
import com.codenvy.ide.ext.wso2.client.commons.XsdSchemaParser;
import com.codenvy.ide.text.Document;
import com.codenvy.ide.texteditor.api.AutoEditStrategy;
import com.codenvy.ide.texteditor.api.TextEditorConfiguration;
import com.codenvy.ide.texteditor.api.TextEditorPartView;
import com.codenvy.ide.texteditor.api.codeassistant.CodeAssistProcessor;
import com.codenvy.ide.texteditor.api.parser.BasicTokenFactory;
import com.codenvy.ide.texteditor.api.parser.CmParser;
import com.codenvy.ide.texteditor.api.parser.Parser;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_XML_EXTENSION;
import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_XML_MIME_TYPE;

/**
 * The XML file type editor configuration.
 *
 * @author Dmitry Kuleshov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class XmlEditorConfiguration extends TextEditorConfiguration {

    private AutoCompleterFactory autoCompleterFactory;
    private XsdSchemaParser      xsdSchemaParser;

    @Inject
    public XmlEditorConfiguration(AutoCompleterFactory autoCompleterFactory,
                                  XsdSchemaParser xsdSchemaParser) {
        this.autoCompleterFactory = autoCompleterFactory;
        this.xsdSchemaParser = xsdSchemaParser;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public AutoEditStrategy[] getAutoEditStrategies(@Nonnull TextEditorPartView view, @Nonnull String contentType) {
        return new AutoEditStrategy[]{autoCompleterFactory.createAutoCompleter(view)};
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public Parser getParser(@Nonnull TextEditorPartView view) {
        CmParser parser = getParserForMime(ESB_XML_MIME_TYPE);
        parser.setNameAndFactory(ESB_XML_EXTENSION, new BasicTokenFactory());
        return parser;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public StringMap<CodeAssistProcessor> getContentAssistantProcessors(@Nonnull TextEditorPartView view) {
        StringMap<CodeAssistProcessor> map = Collections.createStringMap();
        map.put(Document.DEFAULT_CONTENT_TYPE, new XmlCodeAssistProcessor(xsdSchemaParser));
        return map;
    }
}