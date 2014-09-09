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
package com.codenvy.ide.ext.wso2.client.editor.text;

import com.codenvy.ide.texteditor.api.TextEditorPartView;

import javax.annotation.Nonnull;

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
    @Nonnull
    TagAutoCompleter createAutoCompleter(@Nonnull TextEditorPartView editor);
}