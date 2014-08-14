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
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

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

    private static final String IF_BRANCH_TITLE                = "Then";
    private static final String ELSE_BRANCH_TITLE              = "Else";
    private static final String IF_BRANCH_SERIALIZATION_NAME   = "then";
    private static final String ELSE_BRANCH_SERIALIZATION_NAME = "else";

    private static final List<String> PROPERTIES = Arrays.asList(CONDITION_TYPE_PROPERTY_NAME,
                                                                 SOURCE_PROPERTY_NAME,
                                                                 REGULAR_EXPRESSION_PROPERTY_NAME);
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

    private String conditionType;
    private String source;
    private String regularExpression;

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

        conditionType = SOURCE_AND_REGEX.name();
        source = "get-property";
        regularExpression = "default/regex";

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
            case CONDITION_TYPE_PROPERTY_NAME:
                conditionType = String.valueOf(nodeValue);
                break;

            case SOURCE_PROPERTY_NAME:
                source = String.valueOf(nodeValue);
                break;

            case REGULAR_EXPRESSION_PROPERTY_NAME:
                regularExpression = String.valueOf(nodeValue);
                break;

            default:
        }
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.filter();
    }

    public enum FilterConditionType {
        SOURCE_AND_REGEX, XPATH;

        public static final String TYPE_NAME = "FilterConditionType";
    }

}