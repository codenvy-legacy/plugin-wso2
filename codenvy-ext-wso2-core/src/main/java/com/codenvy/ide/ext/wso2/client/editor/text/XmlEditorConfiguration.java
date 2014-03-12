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

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_XML_EXTENSION;
import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_XML_MIME_TYPE;

/**
 * The XML file type editor configuration.
 *
 * @author Dmitry Kuleshov
 * @author Valeriy Svydenko
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
    @Override
    public Parser getParser(@NotNull TextEditorPartView view) {
        CmParser parser = getParserForMime(ESB_XML_MIME_TYPE);
        parser.setNameAndFactory(ESB_XML_EXTENSION, new BasicTokenFactory());
        return parser;
    }

    /** {@inheritDoc} */
    @Override
    public AutoEditStrategy[] getAutoEditStrategies(TextEditorPartView view, String contentType) {
        return new AutoEditStrategy[]{autoCompleterFactory.createAutoCompleter(view)};
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public StringMap<CodeAssistProcessor> getContentAssistantProcessors(@NotNull TextEditorPartView view) {
        StringMap<CodeAssistProcessor> map = Collections.createStringMap();
        map.put(Document.DEFAULT_CONTENT_TYPE, new XmlCodeAssistProcessor(xsdSchemaParser));
        return map;
    }
}