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

import com.codenvy.ide.collections.Array;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The abstract class which contains general methods for serialization/deserialization properties and attributes.
 *
 * @author Valeriy Svydenko
 */
public abstract class AbstractEntityElement {

    private final Map<Key<Object>, Object> properties;

    protected AbstractEntityElement() {
        properties = new HashMap<>();
    }

    /**
     * Put a property to available properties list.
     *
     * @param key
     *         key that identifies property
     * @param value
     *         value that need to add
     * @param <T>
     *         type of property
     */
    public <T> void putProperty(@NotNull Key<T> key, @NotNull T value) {
        //noinspection unchecked
        properties.put((Key<Object>)key, value);
    }

    /**
     * Get a property value from available properties list.
     *
     * @param key
     *         key that identifies a property
     * @param <T>
     *         type of a property
     * @return a property that is mapped to this key or <code>null</code> if no value is not mapped
     */
    @Nullable
    public <T> T getProperty(@NotNull Key<T> key) {
        //noinspection unchecked,SuspiciousMethodCalls
        return (T)properties.get(key);
    }

    /**
     * Convert attributes of diagram element to XML attribute format.
     *
     * @param attributes
     *         element's properties
     * @return XML format of element's attributes
     */
    @Nonnull
    protected String convertAttributesToXMLFormat(@Nonnull Map<String, String> attributes) {
        StringBuilder content = new StringBuilder();

        for (Iterator iterator = attributes.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry entry = (Map.Entry)iterator.next();
            String value = (String)entry.getValue();

            if (value != null && !value.isEmpty()) {
                content.append(entry.getKey()).append("=\"").append(value).append('"');
            }

            if (iterator.hasNext()) {
                content.append(' ');
            }
        }

        return content.toString();
    }

    /**
     * Convert properties of diagram element to XML attribute format.
     *
     * @param properties
     *         element's properties
     * @return XML format of element's attributes
     */
    @Nonnull
    protected String convertPropertiesToXMLFormat(@Nonnull Map<String, String> properties) {
        StringBuilder content = new StringBuilder();

        for (Iterator iterator = properties.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry entry = (Map.Entry)iterator.next();
            String value = (String)entry.getValue();

            if (value != null && !value.isEmpty()) {
                content.append('<').append(entry.getKey()).append('>').append(value).append("</").append(entry.getKey()).append('>');
            }

            if (iterator.hasNext()) {
                content.append('\n');
            }
        }

        return content.toString();
    }

    /**
     * Convert name spaces of element to string.
     *
     * @param nameSpaces
     *         element's name spaces
     * @return name spaces parameters as string
     */
    @Nonnull
    protected String convertNameSpaceToXMLFormat(@Nonnull Array<NameSpace> nameSpaces) {
        StringBuilder result = new StringBuilder();

        for (NameSpace nameSpace : nameSpaces.asIterable()) {
            result.append(nameSpace.toString()).append(' ');
        }

        return result.toString();
    }

    public static class Key<T> {

        private final String name;

        public Key(@Nonnull String name) {
            this.name = name;
        }

        /** {@inheritDoc} */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Key)) return false;

            Key key = (Key)o;
            return name.equals(key.name);
        }

        /** {@inheritDoc} */
        @Override
        public int hashCode() {
            return name.hashCode();
        }

    }

}