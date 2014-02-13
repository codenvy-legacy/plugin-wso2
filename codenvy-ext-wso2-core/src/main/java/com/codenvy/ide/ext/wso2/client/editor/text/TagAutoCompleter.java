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

import com.codenvy.ide.text.BadLocationException;
import com.codenvy.ide.text.Document;
import com.codenvy.ide.text.DocumentCommand;
import com.codenvy.ide.text.Region;
import com.codenvy.ide.texteditor.api.AutoEditStrategy;
import com.codenvy.ide.texteditor.api.TextEditorPartView;
import com.codenvy.ide.util.loging.Log;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * This very class provides support for the XML tag auto completion. By tag auto completions we mean adding enclosing tags during editing
 * of an XML document with all required formatting.
 *
 * @author Dmitry Kuleshov
 */
public class TagAutoCompleter implements AutoEditStrategy {

    private TextEditorPartView editor;

    @Inject
    public TagAutoCompleter(@Assisted TextEditorPartView editor) {
        this.editor = editor;
    }

    /** {@inheritDoc} */
    @Override
    public void customizeDocumentCommand(Document document, DocumentCommand command) {
        try {
            if (isCompletionApplicable(document, command)) {
                final int offset = editor.getSelection().getSelectedRange().offset;
                String line = getLineAtCursor(document, offset);
                String tag = getTagToBeCompleted(line);

                command.text = getTagCompletion(command, tag);
                command.caretOffset = offset + 1;
            }
        } catch (BadLocationException e) {
            Log.error(TagAutoCompleter.class, e);
        }
    }

    private boolean isCompletionApplicable(Document document, DocumentCommand command) throws BadLocationException {
        if (command.text.charAt(0) == '>') {
            int offset = editor.getSelection().getSelectedRange().offset;
            String line = getLineAtCursor(document, offset);
            int ltNumber = countSymbolOccurrence(line, '<');
            int gtNumber = countSymbolOccurrence(line, '>');
            return ltNumber - gtNumber - 1 == 0;
        }
        return false;
    }

    private int countSymbolOccurrence(String line, char symbol) {
        int number = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == symbol) {
                number++;
            }
        }
        return number;
    }

    private String getLineAtCursor(Document document, int offset) throws BadLocationException {
        Region line = document.getLineInformationOfOffset(offset);
        return document.get(line.getOffset(), line.getLength());
    }

    private String getTagToBeCompleted(String line) {
        int tagStartPosition = line.lastIndexOf('<') + 1;
        String substring = line.substring(tagStartPosition);
        int tagEndPosition = tagStartPosition + substring.indexOf(" ");

        return tagEndPosition < tagStartPosition ? substring : line.substring(tagStartPosition, tagEndPosition);
    }

    private String getTagCompletion(DocumentCommand command, String tag) {
        return command.text + "</" + tag + '>';
    }
}
