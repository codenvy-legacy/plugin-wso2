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
package com.codenvy.ide.client.elements.mediators;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.AbstractElement;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.NameSpace.applyNameSpace;
import static com.codenvy.ide.client.elements.NameSpace.convertNameSpacesToXML;
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

    public static final Key<ConditionType>   CONDITION_TYPE     = new Key<>("ConditionKey");
    public static final Key<String>          SOURCE             = new Key<>("Source");
    public static final Key<String>          REGULAR_EXPRESSION = new Key<>("RegularExpression");
    public static final Key<String>          X_PATH             = new Key<>("XPath");
    public static final Key<List<NameSpace>> SOURCE_NAMESPACE   = new Key<>("SourceNamespace");
    public static final Key<List<NameSpace>> XPATH_NAMESPACE    = new Key<>("XPathNamespace");
    public static final Key<List<NameSpace>> NAMESPACES         = new Key<>("Namespaces");

    private static final String SOURCE_ATTRIBUTE_NAME             = "source";
    private static final String REGULAR_EXPRESSION_ATTRIBUTE_NAME = "regex";
    private static final String XPATH_ATTRIBUTE_NAME              = "xpath";

    public static final String IF_BRANCH_TITLE                = "Then";
    public static final String ELSE_BRANCH_TITLE              = "Else";
    public static final String IF_BRANCH_SERIALIZATION_NAME   = "then";
    public static final String ELSE_BRANCH_SERIALIZATION_NAME = "else";

    private static final List<String> PROPERTIES = Collections.emptyList();

    private final Provider<NameSpace> nameSpaceProvider;

    boolean isSourceAttributeFound;

    @Inject
    public Filter(EditorResources resources,
                  Provider<Branch> branchProvider,
                  ElementCreatorsManager elementCreatorsManager,
                  Provider<NameSpace> nameSpaceProvider) {

        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.filter(),
              branchProvider,
              elementCreatorsManager);

        this.nameSpaceProvider = nameSpaceProvider;

        putProperty(CONDITION_TYPE, SOURCE_AND_REGEX);
        putProperty(SOURCE, "get-property('To')");
        putProperty(REGULAR_EXPRESSION, "default_regex");
        putProperty(X_PATH, "/default/xpath");

        putProperty(SOURCE_NAMESPACE, new ArrayList<NameSpace>());
        putProperty(XPATH_NAMESPACE, new ArrayList<NameSpace>());

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

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        ConditionType conditionType = getProperty(CONDITION_TYPE);

        if (conditionType == null) {
            return "";
        }

        Map<String, String> attributes = new LinkedHashMap<>();
        String result;

        if (XPATH.equals(conditionType)) {
            attributes.put(XPATH_ATTRIBUTE_NAME, getProperty(X_PATH));
            result = convertNameSpacesToXML(getProperty(XPATH_NAMESPACE));
        } else {
            attributes.put(SOURCE_ATTRIBUTE_NAME, getProperty(SOURCE));
            attributes.put(REGULAR_EXPRESSION_ATTRIBUTE_NAME, getProperty(REGULAR_EXPRESSION));
            result = convertNameSpacesToXML(getProperty(SOURCE_NAMESPACE));
        }

        return result + convertAttributesToXML(attributes);
    }

    /** {@inheritDoc} */
    @Override
    public void deserialize(@Nonnull Node node) {
        isSourceAttributeFound = false;
        List<NameSpace> nameSpaces = new ArrayList<>();

        putProperty(NAMESPACES, nameSpaces);

        super.deserialize(node);

        if (isSourceAttributeFound) {
            putProperty(CONDITION_TYPE, SOURCE_AND_REGEX);
            putProperty(SOURCE_NAMESPACE, nameSpaces);
        } else {
            putProperty(CONDITION_TYPE, XPATH);
            putProperty(XPATH_NAMESPACE, nameSpaces);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void applyAttribute(@Nonnull String attributeName, @Nonnull String attributeValue) {
        switch (attributeName) {
            case REGULAR_EXPRESSION_ATTRIBUTE_NAME:
                putProperty(REGULAR_EXPRESSION, attributeValue);
                break;

            case SOURCE_ATTRIBUTE_NAME:
                putProperty(SOURCE, attributeValue);
                isSourceAttributeFound = true;
                break;

            case XPATH_ATTRIBUTE_NAME:
                putProperty(X_PATH, attributeValue);
                break;

            default:
                applyNameSpace(nameSpaceProvider, getProperty(NAMESPACES), attributeName, attributeValue);
        }
    }

    public enum ConditionType {
        SOURCE_AND_REGEX, XPATH;

        public static final String TYPE_NAME = "ConditionType";
    }

}