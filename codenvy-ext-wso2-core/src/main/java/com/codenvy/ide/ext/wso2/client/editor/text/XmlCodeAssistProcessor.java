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

import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.ext.wso2.client.commons.XsdSchemaParser;
import com.codenvy.ide.text.BadLocationException;
import com.codenvy.ide.text.Document;
import com.codenvy.ide.text.Position;
import com.codenvy.ide.text.Region;
import com.codenvy.ide.texteditor.api.CodeAssistCallback;
import com.codenvy.ide.texteditor.api.TextEditorPartView;
import com.codenvy.ide.texteditor.api.codeassistant.CodeAssistProcessor;
import com.codenvy.ide.util.loging.Log;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.regexp.shared.SplitResult;

import javax.validation.constraints.NotNull;
import java.util.HashSet;

/**
 * Implementation of {@link CodeAssistProcessor} for XML files.
 *
 * @author Valeriy Svydenko
 */
public class XmlCodeAssistProcessor implements CodeAssistProcessor {

    private XsdSchemaParser xsdSchemaParser;

    public XmlCodeAssistProcessor(XsdSchemaParser xsdSchemaParser) {
        this.xsdSchemaParser = xsdSchemaParser;
    }

    /** {@inheritDoc} */
    @Override
    public void computeCompletionProposals(TextEditorPartView view, int offset, CodeAssistCallback callback) {
        if (view.getSelection().hasSelection()) {
            // Doesn't make much sense to autocomplete attributes when something is selected.
            callback.proposalComputed(null);
            return;
        }

        String textBeforeCursor = getTextBeforeCursor(view);
        String textAfterCursor = getTextAfterCursor(view);
        String triggeringString = computePrefixString(textBeforeCursor);

        if (textBeforeCursor.lastIndexOf('<') < textBeforeCursor.lastIndexOf('>') || triggeringString == null ||
            triggeringString.startsWith("<") || triggeringString.endsWith("/")) {
            return;
        }

        try {
            String currentLine = getLineAtCursor(view.getDocument(), offset);
            String tag = getTagToBeCompleted(currentLine);
            Array<String> attributes = xsdSchemaParser.getAttributes(tag);

            InvocationContext context = new InvocationContext(triggeringString, offset);
            if (attributes.size() > 0) {
                XmlCompletionProposal[] proposals =
                        prepareProposals(attributes, context, triggeringString, textAfterCursor, textBeforeCursor);
                if (proposals.length > 0) {
                    callback.proposalComputed(proposals);
                } else {
                    callback.proposalComputed(null);
                }
            }
        } catch (BadLocationException e) {
            Log.error(getClass(), e);
        }
    }

    /**
     * Finds a prefix into the text before cursor.
     *
     * @param textBeforeCursor
     *         text before cursor
     * @return prefix
     */
    private String computePrefixString(@NotNull String textBeforeCursor) {
        RegExp regexpSpaces = RegExp.compile("\\s+");
        textBeforeCursor = textBeforeCursor.replaceAll("^\\s+", "");

        SplitResult valueParts = regexpSpaces.split(textBeforeCursor);

        return valueParts.get(valueParts.length() - 1).trim();
    }

    /**
     * Prepares array of proposals for the attributes autocomplete.
     *
     * @param attributes
     *         the array of attributes
     * @param context
     *         invocation context for XML attributes assistant
     * @param prefix
     *         the text before cursor.
     */
    private XmlCompletionProposal[] prepareProposals(Array<String> attributes, @NotNull InvocationContext context, @NotNull String prefix,
                                                     @NotNull String textAfterCursor, @NotNull String textBeforeCursor) {

        HashSet<String> introducedAttributes = getIntroducedAttributes(textAfterCursor, textBeforeCursor);

        Array<String> actualProposals = Collections.createArray();
        for (int i = 0; i < attributes.size(); i++) {
            String attribute = attributes.get(i);
            if (attribute.startsWith(prefix)) {
                if (!introducedAttributes.contains(attribute)) {
                    actualProposals.add(attributes.get(i));
                }
            }
        }
        XmlCompletionProposal[] proposals = new XmlCompletionProposal[actualProposals.size()];
        for (int i = 0; i < actualProposals.size(); i++) {
            proposals[i] = new XmlCompletionProposal(actualProposals.get(i));
            proposals[i].setContext(context);
        }

        return proposals;
    }

    /**
     * Gets attributes that was introduce.
     *
     * @return a set with attributes.
     */
    private HashSet<String> getIntroducedAttributes(@NotNull String textAfterCursor, @NotNull String textBeforeCursor) {
        HashSet<String> attributes = new HashSet<String>();
        int indexOfOpenTag = textBeforeCursor.lastIndexOf('<');
        int indexOfCloseTag = textAfterCursor.indexOf('>');
        StringBuilder currentTag = new StringBuilder();
        if (indexOfOpenTag > 0) {
            currentTag.append(textBeforeCursor.substring(indexOfOpenTag, textBeforeCursor.length()));
        }
        if (indexOfCloseTag > 0) {
            currentTag.append(textAfterCursor.substring(0, indexOfCloseTag));
        }
        if (currentTag.toString().length() > 0) {
            RegExp regexpSpaces = RegExp.compile("\\s+");
            String textBeforeCursorWithoutSpaces = currentTag.toString().replaceAll("^\\s+", "");

            SplitResult valueParts = regexpSpaces.split(textBeforeCursorWithoutSpaces);

            for (int i = 0; i < valueParts.length(); i++) {
                String attribute = valueParts.get(i);
                int indexOfEq = attribute.indexOf('=');
                if (indexOfEq > 0) {
                    attributes.add(attribute.substring(0, indexOfEq));
                }
            }
        }
        return attributes;
    }

    /**
     * Finds a text that contains before position of the cursor.
     *
     * @param view
     *         the view whose document is used to compute the proposals
     */
    private String getTextBeforeCursor(@NotNull TextEditorPartView view) {
        StringBuilder textBeforeCursor = new StringBuilder();
        Document document = view.getDocument();
        Position cursor = view.getSelection().getCursorPosition();
        try {
            int line = document.getLineOfOffset(cursor.offset);
            Region region = document.getLineInformation(line);
            int column = cursor.getOffset() - region.getOffset();

            boolean parsingLineWithCursor = true;

            while (line >= 0) {
                if (parsingLineWithCursor) {
                    Region information = document.getLineInformation(line);
                    textBeforeCursor.insert(0, document.get(information.getOffset(), column));
                    parsingLineWithCursor = false;
                } else {
                    Region information = document.getLineInformation(line);
                    textBeforeCursor.insert(0, document.get(information.getOffset(), information.getLength()));
                }
                line--;
            }
        } catch (BadLocationException e) {
            Log.error(getClass(), e);
        }
        return textBeforeCursor.toString();
    }

    /**
     * Finds a text that contains after position of the cursor.
     *
     * @param view
     *         the view whose document is used to compute the proposals
     */
    private String getTextAfterCursor(TextEditorPartView view) {
        StringBuilder textAfterCursor = new StringBuilder();
        Document document = view.getDocument();
        Position cursor = view.getSelection().getCursorPosition();
        try {
            int line = document.getLineOfOffset(cursor.offset);
            Region region = document.getLineInformation(line);
            int column = cursor.getOffset() - region.getOffset();

            boolean parsingLineWithCursor = true;

            while (line < document.getNumberOfLines()) {
                if (parsingLineWithCursor) {
                    Region information = document.getLineInformation(line);
                    textAfterCursor.append(document.get(information.getOffset(), information.getLength()).substring(column));
                    parsingLineWithCursor = false;
                } else {
                    Region information = document.getLineInformation(line);
                    textAfterCursor.append(document.get(information.getOffset(), information.getLength()).trim());
                }
                line++;
            }
        } catch (BadLocationException e) {
            Log.error(getClass(), e);
        }
        return textAfterCursor.toString();
    }

    /** {@inheritDoc} */
    @Override
    public char[] getCompletionProposalAutoActivationCharacters() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public String getErrorMessage() {
        return null;
    }

    /**
     * Returns a string of line with current position of a cursor.
     *
     * @param document
     *         a document from the view
     * @param offset
     *         an offset within the document for which completions should be computed
     * @return the text of the line with cursor.
     * @throws BadLocationException
     */
    private String getLineAtCursor(Document document, int offset) throws BadLocationException {
        Region line = document.getLineInformationOfOffset(offset);
        return document.get(line.getOffset(), line.getLength());
    }

    /**
     * Returns a xml tag from line with current position.
     *
     * @param line
     *         current line
     * @return name of the tag.
     */
    private String getTagToBeCompleted(@NotNull String line) {
        int tagStartPosition = line.lastIndexOf('<') + 1;
        String substring = line.substring(tagStartPosition);
        int tagEndPosition = tagStartPosition + substring.indexOf(' ');

        return tagEndPosition < tagStartPosition ? substring : line.substring(tagStartPosition, tagEndPosition);
    }

}
