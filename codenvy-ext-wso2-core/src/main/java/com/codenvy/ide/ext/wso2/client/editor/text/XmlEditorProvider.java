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

import com.codenvy.ide.api.editor.EditorPartPresenter;
import com.codenvy.ide.api.editor.EditorProvider;
import com.codenvy.ide.ext.wso2.client.editor.ESBConfEditor;
import com.google.inject.Inject;
import com.google.inject.Provider;


/**
 * Editor Provider for WSO2 ESB configuration files, which are XML files.
 *
 * @author Dmitry Kuleshov
 * @author Andrey Plotnikov
 */
public class XmlEditorProvider implements EditorProvider {

    private final Provider<ESBConfEditor> esbConfEditor;

    @Inject
    public XmlEditorProvider(Provider<ESBConfEditor> esbConfEditor) {
        this.esbConfEditor = esbConfEditor;
    }

    /** {@inheritDoc} */
    @Override
    public String getId() {
        return "WSO2SequenceEditor";
    }

    /** {@inheritDoc} */
    @Override
    public String getDescription() {
        return "WSO2 Sequence Editor";
    }

    /** {@inheritDoc} */
    @Override
    public EditorPartPresenter getEditor() {
        return esbConfEditor.get();
    }
}