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

/**
 * Invocation context for XML attributes assistant, it's hold prefix and offset of current code assistant session.
 *
 * @author Valeriy Svydenko
 */
public class InvocationContext {
    private final String prefix;
    private final int    offset;

    /**
     * @param prefix
     *         the string before cursor
     * @param offset
     *         an offset within the document for which completions should be computed.
     */
    public InvocationContext(String prefix, int offset) {
        this.prefix = prefix;
        this.offset = offset;
    }

    /** @return the offset */
    public int getOffset() {
        return offset;
    }

    /** @return the prefix */
    public String getPrefix() {
        return prefix;
    }
}
