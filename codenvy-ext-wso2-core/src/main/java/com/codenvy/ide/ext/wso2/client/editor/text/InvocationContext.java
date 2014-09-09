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

import javax.annotation.Nonnull;

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
    public InvocationContext(@Nonnull String prefix, int offset) {
        this.prefix = prefix;
        this.offset = offset;
    }

    /** @return the value of offset */
    public int getOffset() {
        return offset;
    }

    /** @return the value of prefix */
    @Nonnull
    public String getPrefix() {
        return prefix;
    }
}
