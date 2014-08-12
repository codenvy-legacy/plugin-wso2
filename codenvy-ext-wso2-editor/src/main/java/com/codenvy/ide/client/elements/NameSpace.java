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
package com.codenvy.ide.client.elements;

import javax.annotation.Nonnull;

/**
 * Class describes entity which presented name space.
 *
 * @author Dmitry Shnurenko
 */
public class NameSpace {

    public static final String PREFIX = "xmlns";

    private final String prefix;
    private final String uri;

    public NameSpace(@Nonnull String prefix, @Nonnull String uri) {
        this.prefix = prefix;
        this.uri = uri;
    }

    /** @return prefix of namespace */
    @Nonnull
    public String getPrefix() {
        return prefix;
    }

    /** @return uri of namespace */
    @Nonnull
    public String getUri() {
        return uri;
    }

    /** @return copy of namespace */
    @Nonnull
    public NameSpace clone() {
        //TODO create nameSpace using editor factory
        return new NameSpace(prefix, uri);
    }

    /** @return string representation of the namespace */
    @Nonnull
    public String toString() {
        return PREFIX + ':' + prefix + "=\"" + uri + '"';
    }
}
