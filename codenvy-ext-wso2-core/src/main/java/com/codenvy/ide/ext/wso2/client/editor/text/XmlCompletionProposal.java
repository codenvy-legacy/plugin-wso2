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

import com.codenvy.ide.text.BadLocationException;
import com.codenvy.ide.text.Document;
import com.codenvy.ide.text.Region;
import com.codenvy.ide.text.RegionImpl;
import com.codenvy.ide.text.edits.ReplaceEdit;
import com.codenvy.ide.texteditor.api.codeassistant.Completion;
import com.codenvy.ide.texteditor.api.codeassistant.CompletionProposal;
import com.codenvy.ide.util.loging.Log;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

/**
 * {@link CompletionProposal} implementation for XML code assistant.
 *
 * @author Valeriy Svydenko
 */
public class XmlCompletionProposal implements CompletionProposal {

    private String            name;
    private InvocationContext context;

    public XmlCompletionProposal(String attributeName) {
        this.name = attributeName;
    }

    /** {@inheritDoc} */
    @Override
    public Widget getAdditionalProposalInfo() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public String getDisplayString() {
        return new SafeHtmlBuilder().appendEscaped(name).toSafeHtml().asString();
    }

    /** {@inheritDoc} */
    @Override
    public Image getImage() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public char[] getTriggerCharacters() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isAutoInsertable() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public void getCompletion(CompletionCallback callback) {
        callback.onCompletion(new Completion() {
            @Override
            public void apply(Document document) {
                String insert = name + "=\"\"";
                ReplaceEdit replaceEdit =
                        new ReplaceEdit(context.getOffset() - context.getPrefix().length(), context.getPrefix().length(), insert);
                try {
                    replaceEdit.apply(document);
                } catch (BadLocationException e) {
                    Log.error(getClass(), e);
                }
            }

            @Override
            public Region getSelection(Document document) {
                return new RegionImpl(context.getOffset() + name.length() - context.getPrefix().length() + 2, 0);
            }
        });
    }

    /**
     * @param context
     *         the context to set
     */
    public void setContext(InvocationContext context) {
        this.context = context;
    }
}
