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

import com.codenvy.ide.client.elements.enrich.Enrich;
import com.codenvy.ide.client.elements.log.Log;
import com.codenvy.ide.client.elements.payload.PayloadFactory;
import com.google.gwt.xml.client.Node;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.elements.Filter.FilterConditionType.SOURCE_AND_REGEX;

/**
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class Filter extends RootElement {
    public static final String ELEMENT_NAME       = "Filter";
    public static final String SERIALIZATION_NAME = "filter";

    private static final String CONDITION_TYPE_PROPERTY_NAME     = "ConditionType";
    private static final String SOURCE_PROPERTY_NAME             = "Source";
    private static final String REGULAR_EXPRESSION_PROPERTY_NAME = "RegularExpression";

    private static final List<String> PROPERTIES          = Arrays.asList(CONDITION_TYPE_PROPERTY_NAME,
                                                                          SOURCE_PROPERTY_NAME,
                                                                          REGULAR_EXPRESSION_PROPERTY_NAME);
    private static final List<String> INTERNAL_PROPERTIES = Arrays.asList(X_PROPERTY_NAME,
                                                                          Y_PROPERTY_NAME,
                                                                          UUID_PROPERTY_NAME,
                                                                          CONDITION_TYPE_PROPERTY_NAME,
                                                                          SOURCE_PROPERTY_NAME,
                                                                          REGULAR_EXPRESSION_PROPERTY_NAME);
    private static final List<String> COMPONENTS          = Arrays.asList(Log.ELEMENT_NAME,
                                                                          Property.ELEMENT_NAME,
                                                                          PayloadFactory.ELEMENT_NAME,
                                                                          Send.ELEMENT_NAME,
                                                                          Header.ELEMENT_NAME,
                                                                          Respond.ELEMENT_NAME,
                                                                          Filter.ELEMENT_NAME,
                                                                          Switch_mediator.ELEMENT_NAME,
                                                                          Sequence.ELEMENT_NAME,
                                                                          Enrich.ELEMENT_NAME,
                                                                          LoopBack.ELEMENT_NAME,
                                                                          CallTemplate.ELEMENT_NAME,
                                                                          Call.ELEMENT_NAME);

    private String conditionType;
    private String source;
    private String regularExpression;

    public Filter() {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, INTERNAL_PROPERTIES);

        conditionType = SOURCE_AND_REGEX.name();
        source = "get-property";
        regularExpression = "default/regex";

        components.addAll(COMPONENTS);
    }

    @Nullable
    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(@Nullable String conditionType) {
        this.conditionType = conditionType;
    }

    @Nullable
    public String getSource() {
        return source;
    }

    public void setSource(@Nullable String source) {
        this.source = source;
    }

    @Nullable
    public String getRegularExpression() {
        return regularExpression;
    }

    public void setRegularExpression(@Nullable String regularExpression) {
        this.regularExpression = regularExpression;
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        return "conditionType=\"" + conditionType + "\" " +
               "source=\"" + source + "\" " +
               "regularExpression=\"" + regularExpression + "\" ";
    }

    /** {@inheritDoc} */
    @Override
    public void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        switch (nodeName) {
            case AbstractShape.X_PROPERTY_NAME:
                setX(Integer.valueOf(nodeValue));
                break;
            case AbstractShape.Y_PROPERTY_NAME:
                setY(Integer.valueOf(nodeValue));
                break;
            case AbstractElement.UUID_PROPERTY_NAME:
                id = nodeValue;
                break;
            case CONDITION_TYPE_PROPERTY_NAME:
                conditionType = String.valueOf(nodeValue);
                break;
            case SOURCE_PROPERTY_NAME:
                source = String.valueOf(nodeValue);
                break;
            case REGULAR_EXPRESSION_PROPERTY_NAME:
                regularExpression = String.valueOf(nodeValue);
                break;
        }
    }

    public enum FilterConditionType {
        SOURCE_AND_REGEX, XPATH;

        public static final String TYPE_NAME = "FilterConditionType";
    }

}