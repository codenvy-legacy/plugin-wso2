/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 *  [2012] - [2014] Codenvy, S.A. 
 *  All Rights Reserved.
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

import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
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

/**
 * Implementation of {@link CodeAssistProcessor} for XML files.
 *
 * @author Valeriy Svydenko
 */
public class XmlCodeAssistProcessor implements CodeAssistProcessor {

    private XsdSchemaParser xsdSchemaParser;
    private String          textBeforeCursor = "";

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

        findTextBeforeCursor(view);
        String triggeringString = computePrefixString(textBeforeCursor);

        Log.info(getClass(), "textBefore = "  + textBeforeCursor);

        if (textBeforeCursor.lastIndexOf('<') < textBeforeCursor.lastIndexOf('>') || triggeringString == null) {
            return;
        }

        try {
            String currentLine = getLineAtCursor(view.getDocument(), offset);
            String tag = getTagToBeCompleted(currentLine);
            Array<String> attributes = xsdSchemaParser.getAttributes(tag);

            InvocationContext context = new InvocationContext(triggeringString, offset);
            if (attributes.size() > 0) {
                XmlCompletionProposal[] proposals = prepareProposals(attributes, context, triggeringString);
                if (proposals.length > 0) {
                    callback.proposalComputed(proposals);
                }
            }
            return;
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
    private String computePrefixString(String textBeforeCursor) {
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
    private XmlCompletionProposal[] prepareProposals(Array<String> attributes, InvocationContext context, String prefix) {

        Array<String> actualProposals = Collections.createArray();
        for (int i = 0; i < attributes.size(); i++) {
            if (attributes.get(i).startsWith(prefix)) {
                actualProposals.add(attributes.get(i));
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
     * Finds a text that contains before position of the cursor.
     *
     * @param view
     *         the view whose document is used to compute the proposals
     */
    private void findTextBeforeCursor(TextEditorPartView view) {
        Document document = view.getDocument();
        Position cursor = view.getSelection().getCursorPosition();
        try {
            int line = document.getLineOfOffset(cursor.offset);
            Region region = document.getLineInformation(line);
            int column = cursor.getOffset() - region.getOffset();

            boolean parsingLineWithCursor = true;

            while (line >= 0) {
                StringBuilder text = new StringBuilder("");
                if (parsingLineWithCursor) {
                    Region information = document.getLineInformation(line);
                    text.append(document.get(information.getOffset(), column));
                    parsingLineWithCursor = false;
                } else {
                    Region information = document.getLineInformation(line);
                    text.append(document.get(information.getOffset(), information.getLength()));
                }

                textBeforeCursor = text.append(textBeforeCursor).toString();

                line--;
            }
        } catch (BadLocationException e) {
            Log.error(getClass(), e);
        }
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
    private String getTagToBeCompleted(String line) {
        int tagStartPosition = line.lastIndexOf('<') + 1;
        String substring = line.substring(tagStartPosition);
        int tagEndPosition = tagStartPosition + substring.indexOf(" ");

        return tagEndPosition < tagStartPosition ? substring : line.substring(tagStartPosition, tagEndPosition);
    }

}
