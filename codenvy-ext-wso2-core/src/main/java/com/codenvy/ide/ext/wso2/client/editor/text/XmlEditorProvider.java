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

import com.codenvy.ide.api.editor.EditorPartPresenter;
import com.codenvy.ide.api.editor.EditorProvider;
import com.codenvy.ide.ext.wso2.client.editor.ESBConfEditor;
import com.google.inject.Inject;
import com.google.inject.Provider;


/**
 * Editor Provider for WSO2 ESB configuration files, which are XML files.
 *
 * @author Dmitry Kuleshov
 */
public class XmlEditorProvider implements EditorProvider {

    private Provider<ESBConfEditor> esbConfEditor;

    @Inject
    public XmlEditorProvider(Provider<ESBConfEditor> esbConfEditor) {
        this.esbConfEditor = esbConfEditor;
    }

    /** {@inheritDoc} */
    @Override
    public EditorPartPresenter getEditor() {
        return esbConfEditor.get();
    }
}