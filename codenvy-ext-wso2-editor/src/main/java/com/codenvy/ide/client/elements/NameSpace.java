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

import com.codenvy.ide.collections.Array;
import com.codenvy.ide.util.StringUtils;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Class describes entity which presented name space.
 *
 * @author Dmitry Shnurenko
 */
public class NameSpace {

    private String prefix;
    private String uri;

    public NameSpace(@Nullable String prefix, @Nullable String uri) {
        this.prefix = prefix;
        this.uri = uri;
    }

    /**
     * Apply attributes from XML node to current namespace
     *
     * @param node
     *         XML node that need to be analyzed
     */
    public void applyAttributes(@Nonnull Node node) {
        NamedNodeMap attributeMap = node.getAttributes();

        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attributeNode = attributeMap.item(i);

            String nodeName = attributeNode.getNodeName();
            Array<String> attrName = StringUtils.split(nodeName, ":");
            prefix = attrName.get(1);
            uri = attributeNode.getNodeValue();
        }
    }

    /** @return prefix of namespace */
    public String getPrefix() {
        return prefix;
    }

    /** @return uri of namespace */
    public String getUri() {
        return uri;
    }

    /** @return copy of namespace */
    public NameSpace clone() {
        //TODO create nameSpace using editor factory
        return new NameSpace(prefix, uri);
    }

    /** @return string representation of the namespace */
    public String toString() {
        return "xmlns:" + prefix + "=\"" + uri + '"';
    }
}
