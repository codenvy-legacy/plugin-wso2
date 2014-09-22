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
package com.codenvy.ide.client.elements;

import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.inject.Inject;

/**
 * Class describes entity which presented name space.
 *
 * @author Dmitry Shnurenko
 * @author Andrey Plotnikov
 */
public class NameSpace {

    public static final String PREFIX = "xmlns";

    private final Provider<NameSpace> nameSpaceProvider;

    private String prefix;
    private String uri;

    @Inject
    public NameSpace(Provider<NameSpace> nameSpaceProvider) {
        this.nameSpaceProvider = nameSpaceProvider;
    }

    /** @return value of prefix of namespace */
    @Nonnull
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /** @return value of uri of namespace */
    @Nonnull
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    /** @return copy of namespace */
    @Nonnull
    public NameSpace copy() {
        NameSpace nameSpace = nameSpaceProvider.get();

        nameSpace.setPrefix(prefix);
        nameSpace.setUri(uri);

        return nameSpace;
    }

    /** @return string representation of the namespace */
    @Nonnull
    public String toString() {
        return PREFIX + ':' + prefix + "=\"" + uri + '"';
    }
}
