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

import com.codenvy.ide.annotations.NotNull;
import com.codenvy.ide.texteditor.api.TextEditorPartView;

/**
 * The factory for creating instances of {@link TagAutoCompleter}.
 *
 * @author Andrey Plotnikov
 */
public interface AutoCompleterFactory {

    /**
     * Create an instance of {@link TagAutoCompleter} with a given editor.
     *
     * @param editor
     *         editor that need to be used with a created autocompleter
     * @return an instance of {@link TagAutoCompleter}
     */
    TagAutoCompleter createAutoCompleter(@NotNull TextEditorPartView editor);
}