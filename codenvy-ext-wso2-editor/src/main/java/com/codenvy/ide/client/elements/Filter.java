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

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.enrich.Enrich;
import com.codenvy.ide.client.elements.log.Log;
import com.codenvy.ide.client.elements.payload.PayloadFactory;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.util.StringUtils;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static com.codenvy.ide.client.elements.Filter.ConditionType.SOURCE_AND_REGEX;
import static com.codenvy.ide.client.elements.Filter.ConditionType.XPATH;
import static com.codenvy.ide.client.elements.NameSpace.PREFIX;

/**
 * The entity that represents 'Filter' mediator from ESB configuration.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class Filter extends RootElement {
    public static final String ELEMENT_NAME       = "Filter";
    public static final String SERIALIZATION_NAME = "filter";

    private static final String SOURCE_ATTRIBUTE_NAME             = "source";
    private static final String REGULAR_EXPRESSION_ATTRIBUTE_NAME = "regex";
    private static final String XPATH_ATTRIBUTE_NAME              = "xpath";

    private static final String IF_BRANCH_TITLE                = "Then";
    private static final String ELSE_BRANCH_TITLE              = "Else";
    private static final String IF_BRANCH_SERIALIZATION_NAME   = "then";
    private static final String ELSE_BRANCH_SERIALIZATION_NAME = "else";

    private static final List<String> PROPERTIES = java.util.Collections.emptyList();
    private static final List<String> COMPONENTS = Arrays.asList(Log.ELEMENT_NAME,
                                                                 Property.ELEMENT_NAME,
                                                                 PayloadFactory.ELEMENT_NAME,
                                                                 Send.ELEMENT_NAME,
                                                                 Header.ELEMENT_NAME,
                                                                 Respond.ELEMENT_NAME,
                                                                 Filter.ELEMENT_NAME,
                                                                 Switch.ELEMENT_NAME,
                                                                 Sequence.ELEMENT_NAME,
                                                                 Enrich.ELEMENT_NAME,
                                                                 LoopBack.ELEMENT_NAME,
                                                                 CallTemplate.ELEMENT_NAME,
                                                                 Call.ELEMENT_NAME);

    private ConditionType    conditionType;
    private String           source;
    private String           regularExpression;
    private String           xPath;
    private Array<NameSpace> sourceNameSpaces;
    private Array<NameSpace> xPathNameSpaces;

    @Inject
    public Filter(EditorResources resources,
                  Provider<Branch> branchProvider,
                  Provider<Log> logProvider,
                  Provider<Enrich> enrichProvider,
                  Provider<Filter> filterProvider,
                  Provider<Header> headerProvider,
                  Provider<Call> callProvider,
                  Provider<CallTemplate> callTemplateProvider,
                  Provider<LoopBack> loopBackProvider,
                  Provider<PayloadFactory> payloadFactoryProvider,
                  Provider<Property> propertyProvider,
                  Provider<Respond> respondProvider,
                  Provider<Send> sendProvider,
                  Provider<Sequence> sequenceProvider,
                  Provider<Switch> switchProvider) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              resources,
              branchProvider,
              false,
              true,
              logProvider,
              enrichProvider,
              filterProvider,
              headerProvider,
              callProvider,
              callTemplateProvider,
              loopBackProvider,
              payloadFactoryProvider,
              propertyProvider,
              respondProvider,
              sendProvider,
              sequenceProvider,
              switchProvider);

        conditionType = SOURCE_AND_REGEX;
        source = "get-property('To')";
        regularExpression = "default_regex";
        xPath = "/default/xpath";

        sourceNameSpaces = Collections.createArray();
        xPathNameSpaces = Collections.createArray();

        components.addAll(COMPONENTS);

        Branch thenBranch = branchProvider.get();
        thenBranch.setParent(this);
        thenBranch.setTitle(IF_BRANCH_TITLE);
        thenBranch.setName(IF_BRANCH_SERIALIZATION_NAME);

        Branch elseBranch = branchProvider.get();
        elseBranch.setParent(this);
        elseBranch.setTitle(ELSE_BRANCH_TITLE);
        elseBranch.setName(ELSE_BRANCH_SERIALIZATION_NAME);

        branches.addAll(Arrays.asList(thenBranch, elseBranch));
    }

    @Nonnull
    public ConditionType getConditionType() {
        return conditionType;
    }

    public void setConditionType(@Nonnull ConditionType conditionType) {
        this.conditionType = conditionType;
    }

    @Nullable
    public String getSource() {
        return source;
    }

    public void setSource(@Nullable String source) {
        this.source = source;
    }

    @Nonnull
    public String getRegularExpression() {
        return regularExpression;
    }

    public void setRegularExpression(@Nonnull String regularExpression) {
        this.regularExpression = regularExpression;
    }

    @Nullable
    public String getXPath() {
        return xPath;
    }

    public void setXPath(@Nullable String xPath) {
        this.xPath = xPath;
    }

    @Nonnull
    public Array<NameSpace> getSourceNameSpaces() {
        return sourceNameSpaces;
    }

    public void setSourceNameSpaces(@Nonnull Array<NameSpace> sourceNameSpaces) {
        this.sourceNameSpaces = sourceNameSpaces;
    }

    @Nonnull
    public Array<NameSpace> getXPathNameSpaces() {
        return xPathNameSpaces;
    }

    public void setXPathNameSpaces(@Nonnull Array<NameSpace> xPathNameSpaces) {
        this.xPathNameSpaces = xPathNameSpaces;
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        LinkedHashMap<String, String> attributes = new LinkedHashMap<>();

        switch (conditionType) {
            case XPATH:
                attributes.put(XPATH_ATTRIBUTE_NAME, xPath);

                return convertNameSpacesToXMLAttributes(xPathNameSpaces) + convertPropertiesToXMLFormat(attributes);

            case SOURCE_AND_REGEX:
            default:
                attributes.put(SOURCE_ATTRIBUTE_NAME, source);
                attributes.put(REGULAR_EXPRESSION_ATTRIBUTE_NAME, regularExpression);

                return convertNameSpacesToXMLAttributes(sourceNameSpaces) + convertPropertiesToXMLFormat(attributes);
        }
    }

    @Nonnull
    private String convertNameSpacesToXMLAttributes(@Nonnull Array<NameSpace> nameSpaces) {
        StringBuilder result = new StringBuilder();

        for (NameSpace nameSpace : nameSpaces.asIterable()) {
            result.append(nameSpace.toString()).append(' ');
        }

        return result.toString();
    }

    /** {@inheritDoc} */
    @Override
    protected void applyAttributes(@Nonnull Node node) {
        boolean isSourceAttributeFound = false;
        Array<NameSpace> nameSpaces = Collections.createArray();

        NamedNodeMap attributeMap = node.getAttributes();

        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attributeNode = attributeMap.item(i);

            String nodeValue = attributeNode.getNodeValue();
            String nodeName = attributeNode.getNodeName();

            switch (nodeName) {
                case REGULAR_EXPRESSION_ATTRIBUTE_NAME:
                    regularExpression = nodeValue;
                    break;

                case SOURCE_ATTRIBUTE_NAME:
                    source = nodeValue;
                    isSourceAttributeFound = true;
                    break;

                case XPATH_ATTRIBUTE_NAME:
                    xPath = nodeValue;
                    break;

                default:
                    if (StringUtils.startsWith(PREFIX, nodeName, true)) {
                        String name = StringUtils.trimStart(nodeName, PREFIX + ':');
                        //TODO create entity using edit factory
                        NameSpace nameSpace = new NameSpace(name, nodeValue);

                        nameSpaces.add(nameSpace);
                    }
                    break;
            }
        }

        if (isSourceAttributeFound) {
            conditionType = SOURCE_AND_REGEX;
            sourceNameSpaces = nameSpaces;
        } else {
            conditionType = XPATH;
            xPathNameSpaces = nameSpaces;
        }
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.filter();
    }

    public enum ConditionType {
        SOURCE_AND_REGEX, XPATH;

        public static final String TYPE_NAME = "ConditionType";
    }

}