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

import com.codenvy.ide.util.StringUtils;
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
 * @author Valeriy Svydenko
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

    /**
     * Check if input string is a serialized Name Space element.
     *
     * @param name
     *         inputting attribute
     * @return true if inputting string is a name space element
     */
    public static boolean isNameSpace(@Nonnull String name) {
        return StringUtils.startsWith(PREFIX, name, true);
    }

    /**
     * Adds new property into Name Space element.
     *
     * @param nameSpaceProvider
     *         provider of Name Space
     * @param nameSpaces
     *         current list of Name Spaces
     * @param attributeName
     *         name of Name Space attribute
     * @param attributeValue
     *         value of Name Space attribute
     */
    public static void applyNameSpace(@Nonnull Provider<NameSpace> nameSpaceProvider,
                                      @Nullable List<NameSpace> nameSpaces,
                                      @Nonnull String attributeName,
                                      @Nonnull String attributeValue) {
        if (!isNameSpace(attributeName) || nameSpaces == null) {
            return;
        }

        NameSpace nameSpace = nameSpaceProvider.get();

        String name = StringUtils.trimStart(attributeName, PREFIX + ':');

        nameSpace.putProperty(PREFIX_KEY, name);
        nameSpace.putProperty(URI, attributeValue);
        nameSpaces.add(nameSpace);
    }

    /**
     * Convert name spaces of element to string.
     *
     * @param nameSpaces
     *         element's name spaces
     * @return name spaces parameters as string
     */
    @Nonnull
    public static String convertNameSpacesToXML(@Nullable List<NameSpace> nameSpaces) {
        if (nameSpaces == null) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        for (NameSpace nameSpace : nameSpaces) {
            result.append(nameSpace.toString()).append(' ');
        }

        return result.toString();
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
