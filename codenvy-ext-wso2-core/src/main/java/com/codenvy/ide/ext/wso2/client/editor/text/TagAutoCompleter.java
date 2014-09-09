/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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

import javax.annotation.Nonnull;

/**
 * This very class provides support for the XML tag auto completion. By tag auto completions we mean adding enclosing tags during editing
 * of an XML document with all required formatting.
 *
 * @author Dmitry Kuleshov
 * @author Dmitry Shnurenko
 */
public class TagAutoCompleter implements AutoEditStrategy {

    private TextEditorPartView editor;

    @Inject
    public TagAutoCompleter(@Assisted TextEditorPartView editor) {
        this.editor = editor;
    }

    /** {@inheritDoc} */
    @Override
    public void customizeDocumentCommand(@Nonnull Document document, @Nonnull DocumentCommand command) {
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

    private boolean isCompletionApplicable(@Nonnull Document document, @Nonnull DocumentCommand command) throws BadLocationException {
        if (command.text.charAt(0) == '>') {
            int offset = editor.getSelection().getSelectedRange().offset;
            String line = getLineAtCursor(document, offset);
            int ltNumber = countSymbolOccurrence(line, '<');
            int gtNumber = countSymbolOccurrence(line, '>');
            return ltNumber - gtNumber - 1 == 0;
        }
        return false;
    }

    private int countSymbolOccurrence(@Nonnull String line, char symbol) {
        int number = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == symbol) {
                number++;
            }
        }
        return number;
    }

    @Nonnull
    private String getLineAtCursor(@Nonnull Document document, int offset) throws BadLocationException {
        Region line = document.getLineInformationOfOffset(offset);
        return document.get(line.getOffset(), line.getLength());
    }

    @Nonnull
    private String getTagToBeCompleted(@Nonnull String line) {
        int tagStartPosition = line.lastIndexOf('<') + 1;
        String substring = line.substring(tagStartPosition);
        int tagEndPosition = tagStartPosition + substring.indexOf(" ");

        return tagEndPosition < tagStartPosition ? substring : line.substring(tagStartPosition, tagEndPosition);
    }

    @Nonnull
    private String getTagCompletion(@Nonnull DocumentCommand command, @Nonnull String tag) {
        return command.text + "</" + tag + '>';
    }
}
