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
package com.codenvy.ide.client.elements.mediators.payload;

import com.codenvy.ide.client.elements.AbstractEntityElement;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.util.StringUtils;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.codenvy.ide.client.elements.NameSpace.PREFIX;

/**
 * The class which describes state of Arg property of PayloadFactory mediator and also has methods for changing it. Also the class contains
 * the business  logic that allows to display serialization representation depending of the current state of element. Deserelization
 * mechanism allows to restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 * @author Andrey Plotnikov
 */
public class Arg extends AbstractEntityElement {
    private static final String EVALUATOR_ATTRIBUTE_NAME  = "evaluator";
    private static final String EXPRESSION_ATTRIBUTE_NAME = "expression";
    private static final String VALUE_ATTRIBUTE_NAME      = "value";

    private final Provider<NameSpace> nameSpaceProvider;
    private final Provider<Arg>       argProvider;

    private ArgType type;
    private String  value;

    private String           expression;
    private Evaluator        evaluator;
    private Array<NameSpace> nameSpaces;

    @Inject
    public Arg(Provider<NameSpace> nameSpaceProvider, Provider<Arg> argProvider) {
        this.nameSpaceProvider = nameSpaceProvider;
        this.argProvider = argProvider;

        this.expression = "/default/expression";
        this.type = ArgType.Value;
        this.value = "default";
        this.evaluator = Evaluator.xml;
        this.nameSpaces = Collections.createArray();
    }

    /** @return expression value of element */
    @Nonnull
    public String getExpression() {
        return expression;
    }

    /**
     * Set expression value to element
     *
     * @param expression
     *         value which need to set
     */
    public void setExpression(@Nullable String expression) {
        this.expression = expression;
    }

    /** @return type of arg */
    @Nonnull
    public ArgType getType() {
        return type;
    }

    /**
     * Set type of arg element of payload factory mediator
     *
     * @param type
     *         value which need to set
     */
    public void setType(@Nonnull ArgType type) {
        this.type = type;
    }

    /** @return value of arg */
    @Nonnull
    public String getValue() {
        return value;
    }

    /**
     * Set value of arg element of payload factory mediator
     *
     * @param value
     *         value which need to set
     */
    public void setValue(@Nullable String value) {
        this.value = value;
    }

    /** @return list of namespaces of arg element */
    @Nonnull
    public Array<NameSpace> getNameSpaces() {
        return nameSpaces;
    }

    /**
     * Set list of namespaces to arg element of payload factory mediator
     *
     * @param nameSpaces
     *         list of namespaces which need to set
     */
    public void setNameSpaces(@Nonnull Array<NameSpace> nameSpaces) {
        this.nameSpaces = nameSpaces;
    }

    /** @return evaluator value of arg */
    @Nonnull
    public Evaluator getEvaluator() {
        return evaluator;
    }

    /**
     * Sets evaluator value for element.
     *
     * @param evaluator
     *         value which need to set to element
     */
    public void setEvaluator(@Nullable Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    /** @return serialization representation of element attributes */
    @Nonnull
    private String serializeAttributes() {
        Map<String, String> prop = new LinkedHashMap<>();

        if (ArgType.Expression.equals(type)) {
            prop.put(EVALUATOR_ATTRIBUTE_NAME, evaluator.name());
            prop.put(EXPRESSION_ATTRIBUTE_NAME, expression);
        } else {
            prop.put(VALUE_ATTRIBUTE_NAME, value);
        }

        return convertNameSpaceToXMLFormat(nameSpaces) + convertAttributesToXMLFormat(prop);
    }

    /** @return serialization representation of element */
    @Nonnull
    public String serialize() {
        return "<arg " + serializeAttributes() + "/>\n";
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

            String attributeName = attributeNode.getNodeName();
            String attributeValue = attributeNode.getNodeValue();

            switch (attributeName) {
                case EVALUATOR_ATTRIBUTE_NAME:
                    evaluator = Evaluator.valueOf(attributeValue);
                    break;

                case EXPRESSION_ATTRIBUTE_NAME:
                    expression = attributeValue;

                    type = ArgType.Expression;
                    break;

                case VALUE_ATTRIBUTE_NAME:
                    value = attributeValue;
                    break;

                default:
                    if (StringUtils.startsWith(PREFIX, attributeName, true)) {
                        String name = StringUtils.trimStart(attributeName, PREFIX + ':');

                        NameSpace nameSpace = nameSpaceProvider.get();

                        nameSpace.setPrefix(name);
                        nameSpace.setUri(attributeValue);

                        nameSpaces.add(nameSpace);
                    }
            }
        }
    }

    /** @return copy of element */
    @Nonnull
    public Arg clone() {
        Arg arg = argProvider.get();
        arg.setNameSpaces(nameSpaces);

        return arg;
    }

    public enum ArgType {
        Value, Expression
    }

    public enum Evaluator {
        xml, json
    }
}
