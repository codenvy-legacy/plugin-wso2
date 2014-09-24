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
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Class describes entity which presented name space.
 *
 * @author Dmitry Shnurenko
 * @author Andrey Plotnikov
 */
public class NameSpace extends AbstractEntityElement {

    public static final String PREFIX = "xmlns";

    public static final Key<String> PREFIX_KEY = new Key<>("NameSpacePrefix");
    public static final Key<String> URI        = new Key<>("NameSpaceUri");

    private final Provider<NameSpace> nameSpaceProvider;

    @Inject
    public NameSpace(Provider<NameSpace> nameSpaceProvider) {
        this.nameSpaceProvider = nameSpaceProvider;

        putProperty(PREFIX_KEY, "");
        putProperty(URI, "");
    }

    /** Returns copy of element. */
    public NameSpace copy() {
        NameSpace nameSpace = nameSpaceProvider.get();

        nameSpace.putProperty(PREFIX_KEY, getProperty(PREFIX_KEY));
        nameSpace.putProperty(URI, getProperty(URI));

        return nameSpace;
    }

    /** @return string representation of the namespace */
    @Nonnull
    public String toString() {
        return PREFIX + ':' + getProperty(PREFIX_KEY) + "=\"" + getProperty(URI) + '"';
    }

    /**
     * Returns copy of list. If list which we send to method is null, method return empty list. If list isn't null
     * method returns copy of list.
     *
     * @param listToCopy
     *         list which need to copy
     */
    public static List<NameSpace> copyNameSpaceList(@Nullable List<NameSpace> listToCopy) {
        List<NameSpace> properties = new ArrayList<>();

        if (listToCopy == null) {
            return properties;
        }

        for (NameSpace nameSpace : listToCopy) {
            properties.add(nameSpace);
        }

        return properties;
    }
}
