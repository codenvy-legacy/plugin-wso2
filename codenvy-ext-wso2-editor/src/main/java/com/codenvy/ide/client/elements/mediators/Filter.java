/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package com.codenvy.ide.client.elements.mediators;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.AbstractElement;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.mediators.enrich.Enrich;
import com.codenvy.ide.client.elements.mediators.log.Log;
import com.codenvy.ide.client.elements.mediators.payload.PayloadFactory;
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
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
import java.util.Map;

import static com.codenvy.ide.client.elements.NameSpace.PREFIX;
import static com.codenvy.ide.client.elements.mediators.Filter.ConditionType.SOURCE_AND_REGEX;
import static com.codenvy.ide.client.elements.mediators.Filter.ConditionType.XPATH;

/**
 * The class which describes state of Filter mediator and also has methods for changing it. Also the class contains the business
 * logic that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving. This mediator has two branches 'then' and 'else'. In these
 * branches we can add an element. For more information about Filter mediator go to
 * <a href=" https://docs.wso2.com/display/ESB460/Filter+Mediator"> https://docs.wso2.com/display/ESB460/Filter+Mediator</a>
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class Filter extends AbstractElement {
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

    private final Provider<NameSpace> nameSpaceProvider;

    private ConditionType    conditionType;
    private String           source;
    private String           regularExpression;
    private String           xPath;
    private Array<NameSpace> sourceNameSpaces;
    private Array<NameSpace> xPathNameSpaces;

    @Inject
    public Filter(EditorResources resources,
                  Provider<Branch> branchProvider,
                  MediatorCreatorsManager mediatorCreatorsManager,
                  Provider<NameSpace> nameSpaceProvider) {

        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        this.nameSpaceProvider = nameSpaceProvider;

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

    /** @return condition type of filter mediator */
    @Nonnull
    public ConditionType getConditionType() {
        return conditionType;
    }

    /**
     * Sets condition type parameter to element
     *
     * @param conditionType
     *         value which need to set to element
     */
    public void setConditionType(@Nonnull ConditionType conditionType) {
        this.conditionType = conditionType;
    }

    /** @return source of filter mediator */
    @Nullable
    public String getSource() {
        return source;
    }

    /**
     * Sets source parameter to element
     *
     * @param source
     *         value which need to set to element
     */
    public void setSource(@Nullable String source) {
        this.source = source;
    }

    /** @return regular expression of filter mediator */
    @Nonnull
    public String getRegularExpression() {
        return regularExpression;
    }

    /**
     * Sets regular expression parameter to element
     *
     * @param regularExpression
     *         value which need to set to element
     */
    public void setRegularExpression(@Nonnull String regularExpression) {
        this.regularExpression = regularExpression;
    }

    /** @return xpath of filter mediator */
    @Nullable
    public String getXPath() {
        return xPath;
    }

    /**
     * Sets xpath parameter to element
     *
     * @param xPath
     *         value which need to set to element
     */
    public void setXPath(@Nullable String xPath) {
        this.xPath = xPath;
    }

    /** @return list of source namespaces of filter mediator */
    @Nonnull
    public Array<NameSpace> getSourceNameSpaces() {
        return sourceNameSpaces;
    }

    /**
     * Sets list of source namespaces to element
     *
     * @param sourceNameSpaces
     *         list of source namespaces which need to set to element
     */
    public void setSourceNameSpaces(@Nonnull Array<NameSpace> sourceNameSpaces) {
        this.sourceNameSpaces = sourceNameSpaces;
    }

    /** @return list of xpath namespaces of filter mediator */
    @Nonnull
    public Array<NameSpace> getXPathNameSpaces() {
        return xPathNameSpaces;
    }

    /**
     * Sets list of xpath namespaces to element
     *
     * @param xPathNameSpaces
     *         list which need to set to element
     */
    public void setXPathNameSpaces(@Nonnull Array<NameSpace> xPathNameSpaces) {
        this.xPathNameSpaces = xPathNameSpaces;
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        Map<String, String> attributes = new LinkedHashMap<>();

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

    /** @return xml representation of namespaces which presented as attributes of element */
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

                        NameSpace nameSpace = nameSpaceProvider.get();

                        nameSpace.setPrefix(name);
                        nameSpace.setUri(nodeValue);

                        nameSpaces.add(nameSpace);
                    }
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