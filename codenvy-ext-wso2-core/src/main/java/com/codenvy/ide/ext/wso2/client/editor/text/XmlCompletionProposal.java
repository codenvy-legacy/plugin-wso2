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
