/*
 * Copyright [2014] Codenvy, S.A.
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
package com.codenvy.ide.client.elements.payload;

import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Presenter for view which support editing argument property of the Payload mediator.
 *
 * @author Valeriy Svydenko
 */
public class Arg {
    private static final String ARG_ELEMENT_EVALUATOR = "evaluator";
    private static final String ARG_ELEMENT_VALUE     = "expression";

    private static final String PROPERTY_NAMESPACE_PREFIX = "xmlns:";

    private String           type;
    private String           value;
    private String           evaluator;
    private Array<NameSpace> nameSpaces;

    public Arg(@Nullable String value, @Nullable String evaluator) {
        this.value = value;
        this.evaluator = evaluator;
        this.nameSpaces = Collections.createArray();
    }

    /** @return type of arg */
    @Nonnull
    public String getType() {
        return type;
    }

    /** Set type of arg */
    public void setType(@Nonnull String type) {
        this.type = type;
    }

    /** @return value of arg */
    @Nonnull
    public String getValue() {
        return value;
    }

    /** Set value of arg */
    public void setValue(@Nonnull String value) {
        this.value = value;
    }

    /** @return namespaces of arg */
    @Nonnull
    public Array<NameSpace> getNameSpaces() {
        return nameSpaces;
    }

    /** Set namespaces of arg */
    public void setNameSpaces(@Nonnull Array<NameSpace> nameSpaces) {
        this.nameSpaces = nameSpaces;
    }

    /** @return evaluator value of arg */
    @Nonnull
    public String getEvaluator() {
        return evaluator;
    }

    /**
     * Apply attributes from XML node to the diagram element
     *
     * @param node
     *         XML node that need to be analyzed
     */
    public void applyAttributes(@Nonnull Node node) {
        NamedNodeMap attributeMap = node.getAttributes();

        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attributeNode = attributeMap.item(i);

            switch (attributeNode.getNodeName()) {
                case ARG_ELEMENT_EVALUATOR:
                    evaluator = attributeNode.getNodeValue();
                    break;

                case ARG_ELEMENT_VALUE:
                    value = attributeNode.getNodeValue();
                    break;

                case PROPERTY_NAMESPACE_PREFIX:
                    //TODO create nameSpace using editor factory
                    NameSpace nameSpace = new NameSpace(null, null);
                    nameSpace.applyAttributes(node);

                    nameSpaces.add(nameSpace);
                    break;
            }
        }
    }

    /** @return copy of element */
    @Nonnull
    public Arg clone() {
        //TODO create arg using editor factory
        Arg arg = new Arg(value, evaluator);
        arg.setNameSpaces(nameSpaces);

        return arg;
    }

    public enum Evaluator {
        xml, json
    }
}
