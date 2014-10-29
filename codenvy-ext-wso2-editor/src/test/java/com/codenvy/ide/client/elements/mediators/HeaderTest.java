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

import com.codenvy.ide.client.elements.AbstractElementTest;
import com.codenvy.ide.client.elements.NameSpace;
import com.google.gwt.xml.client.Node;
import com.google.inject.Provider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.Action.REMOVE;
import static com.codenvy.ide.client.elements.mediators.Action.SET;
import static com.codenvy.ide.client.elements.mediators.Header.ACTION;
import static com.codenvy.ide.client.elements.mediators.Header.ELEMENT_NAME;
import static com.codenvy.ide.client.elements.mediators.Header.EXPRESSION;
import static com.codenvy.ide.client.elements.mediators.Header.EXPRESSION_NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.Header.HEADER_NAME;
import static com.codenvy.ide.client.elements.mediators.Header.HEADER_NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.Header.HeaderValueType;
import static com.codenvy.ide.client.elements.mediators.Header.HeaderValueType.INLINE;
import static com.codenvy.ide.client.elements.mediators.Header.HeaderValueType.LITERAL;
import static com.codenvy.ide.client.elements.mediators.Header.INLINE_XML;
import static com.codenvy.ide.client.elements.mediators.Header.SCOPE;
import static com.codenvy.ide.client.elements.mediators.Header.SERIALIZATION_NAME;
import static com.codenvy.ide.client.elements.mediators.Header.ScopeType;
import static com.codenvy.ide.client.elements.mediators.Header.ScopeType.SYNAPSE;
import static com.codenvy.ide.client.elements.mediators.Header.ScopeType.TRANSPORT;
import static com.codenvy.ide.client.elements.mediators.Header.VALUE_LITERAL;
import static com.codenvy.ide.client.elements.mediators.Header.VALUE_TYPE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;

/**
 * @author Dmitry Shnurenko
 */
public class HeaderTest extends AbstractElementTest<Header> {

    private static final String PATH_TO_EXAMPLES = "mediators/header/serialize/";

    private static final Action          ACTION_DEFAULT_VALUE        = SET;
    private static final HeaderValueType VALUE_TYPE_DEFAULT_VALUE    = LITERAL;
    private static final ScopeType       SCOPE_DEFAULT_VALUE         = SYNAPSE;
    private static final String          HEADER_NAME_DEFAULT_VALUE   = "To";
    private static final String          HEADER_NAME_VALUE           = "headerName";
    private static final String          VALUE_LITERAL_DEFAULT_VALUE = "header_value";
    private static final String          VALUE_LITERAL_VALUE         = "valueLiteral";
    private static final String          EXPRESSION_DEFAULT_VALUE    = "/default/expression";
    private static final String          EXPRESSION_VALUE            = "expression";
    private static final String          INLINE_XML_DEFAULT_VALUE    = "";
    private static final String          INLINE_XML_VALUE            = "<a><b/></a>";

    private static final List<NameSpace> HEADER_NAMESPACE_DEFAULT_VALUE      = Collections.emptyList();
    private static final List<NameSpace> EXPRESSION_NAMESPACES_DEFAULT_VALUE = Collections.emptyList();

    @Mock(answer = RETURNS_DEEP_STUBS)
    private Node                node;
    @Mock
    private Provider<NameSpace> nameSpaceProvider;
    @Mock
    private NameSpace           headerNameSpace;
    @Mock
    private NameSpace           expressionNameSpace;

    @Before
    public void setUp() throws Exception {
        when(headerNameSpace.toString()).thenReturn("xmlns:prefix1=\"uri1\"");
        when(expressionNameSpace.toString()).thenReturn("xmlns:prefix=\"uri\"");

        when(resources.header()).thenReturn(icon);

        entity = new Header(resources, branchProvider, elementCreatorsManager, nameSpaceProvider);
    }

    @Override
    public void elementTitleShouldBeInitializedWithDefaultValue() throws Exception {
        assertThat(entity.getTitle(), equalTo(ELEMENT_NAME));
    }

    @Override
    public void elementNameShouldBeDefinedWithDefaultValue() throws Exception {
        assertThat(entity.getElementName(), equalTo(ELEMENT_NAME));
    }

    @Override
    public void serializeNameShouldBeDefinedWithDefaultValue() throws Exception {
        assertThat(entity.getSerializationName(), equalTo(SERIALIZATION_NAME));
    }

    @Override
    public void possibleToChangeBranchAmountParamShouldBeInitializedWithDefaultValue() throws Exception {
        assertThat(entity.isPossibleToAddBranches(), is(false));
    }

    @Override
    public void needToShowTitleAndIconParamShouldBeInitializedWithDefaultValue() throws Exception {
        assertThat(entity.needsToShowIconAndTitle(), is(true));
    }

    @Override
    public void elementIconShouldBeInitializedWithDefaultValue() throws Exception {
        assertThat(entity.getIcon(), equalTo(icon));
    }

    @Override
    public void elementShouldBeNotEqualedForAnotherInstanceOfElement() throws Exception {
        Header anotherElement = new Header(resources, branchProvider, elementCreatorsManager, nameSpaceProvider);

        assertThat(entity.equals(anotherElement), is(false));
    }

    @Override
    public void constructorPrepareOperationShouldBeDone() throws Exception {
        assertDefaultConfiguration();
    }

    private void assertDefaultConfiguration() throws Exception {
        assertConfiguration(ACTION_DEFAULT_VALUE,
                            VALUE_TYPE_DEFAULT_VALUE,
                            SCOPE_DEFAULT_VALUE,
                            HEADER_NAME_DEFAULT_VALUE,
                            VALUE_LITERAL_DEFAULT_VALUE,
                            EXPRESSION_DEFAULT_VALUE,
                            INLINE_XML_DEFAULT_VALUE,
                            HEADER_NAMESPACE_DEFAULT_VALUE,
                            EXPRESSION_NAMESPACES_DEFAULT_VALUE);
    }

    private void assertConfiguration(Action action,
                                     HeaderValueType valueType,
                                     ScopeType scopeType,
                                     String headerName,
                                     String valueLiteral,
                                     String expression,
                                     String inlineXml,
                                     List<NameSpace> headerNamespaces,
                                     List<NameSpace> expressionNamespaces) {

        assertThat(entity.getProperty(ACTION), equalTo(action));
        assertThat(entity.getProperty(VALUE_TYPE), equalTo(valueType));
        assertThat(entity.getProperty(SCOPE), equalTo(scopeType));
        assertThat(entity.getProperty(HEADER_NAME), equalTo(headerName));
        assertThat(entity.getProperty(VALUE_LITERAL), equalTo(valueLiteral));
        assertThat(entity.getProperty(EXPRESSION), equalTo(expression));
        assertThat(entity.getProperty(INLINE_XML), equalTo(inlineXml));
        assertThat(entity.getProperty(HEADER_NAMESPACES), equalTo(headerNamespaces));
        assertThat(entity.getProperty(EXPRESSION_NAMESPACES), equalTo(expressionNamespaces));
    }

    @Test
    public void serializationShouldBeDoneWithDefaultParameters() throws Exception {
        assertContentAndValue(PATH_TO_EXAMPLES + "DefaultParameters", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithDefaultParameters() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "DefaultParameters"));

        assertDefaultConfiguration();
    }

    @Test
    public void serializationShouldBeDoneWhenHeaderActionRemove() throws Exception {
        entity.putProperty(ACTION, REMOVE);

        assertContentAndValue(PATH_TO_EXAMPLES + "HeaderActionRemove", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenHeaderActionRemove() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "HeaderActionRemove"));

        assertConfiguration(REMOVE,
                            VALUE_TYPE_DEFAULT_VALUE,
                            SCOPE_DEFAULT_VALUE,
                            HEADER_NAME_DEFAULT_VALUE,
                            VALUE_LITERAL_DEFAULT_VALUE,
                            EXPRESSION_DEFAULT_VALUE,
                            INLINE_XML_DEFAULT_VALUE,
                            HEADER_NAMESPACE_DEFAULT_VALUE,
                            EXPRESSION_NAMESPACES_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenScopeIsTransport() throws Exception {
        entity.putProperty(SCOPE, TRANSPORT);

        assertContentAndValue(PATH_TO_EXAMPLES + "ScopeTransport", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenScopeIsTransport() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "ScopeTransport"));

        assertConfiguration(ACTION_DEFAULT_VALUE,
                            VALUE_TYPE_DEFAULT_VALUE,
                            TRANSPORT,
                            HEADER_NAME_DEFAULT_VALUE,
                            VALUE_LITERAL_DEFAULT_VALUE,
                            EXPRESSION_DEFAULT_VALUE,
                            INLINE_XML_DEFAULT_VALUE,
                            HEADER_NAMESPACE_DEFAULT_VALUE,
                            EXPRESSION_NAMESPACES_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenValueTypeExpression() throws Exception {
        entity.putProperty(VALUE_TYPE, HeaderValueType.EXPRESSION);
        entity.putProperty(EXPRESSION, EXPRESSION_VALUE);

        assertContentAndValue(PATH_TO_EXAMPLES + "ValueTypeExpression", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenValueTypeExpression() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "ValueTypeExpression"));

        assertConfiguration(ACTION_DEFAULT_VALUE,
                            HeaderValueType.EXPRESSION,
                            SCOPE_DEFAULT_VALUE,
                            HEADER_NAME_DEFAULT_VALUE,
                            VALUE_LITERAL_DEFAULT_VALUE,
                            EXPRESSION_VALUE,
                            INLINE_XML_DEFAULT_VALUE,
                            HEADER_NAMESPACE_DEFAULT_VALUE,
                            EXPRESSION_NAMESPACES_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenValueTypeInline() throws Exception {
        entity.putProperty(VALUE_TYPE, INLINE);
        entity.putProperty(INLINE_XML, INLINE_XML_VALUE);

        assertContentAndValue(PATH_TO_EXAMPLES + "ValueTypeInline", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenValueTypeInline() throws Exception {
        when(node.hasChildNodes()).thenReturn(true);
        when(node.getChildNodes().item(0).toString()).thenReturn("<a xmlns=\"\"><b/></a>");

        entity.deserialize(node);

        assertThat(entity.getProperty(VALUE_TYPE), equalTo(INLINE));
        assertThat(entity.getProperty(INLINE_XML), equalTo(INLINE_XML_VALUE));
    }

    @Test
    public void serializationShouldBeDoneWithHeaderNameSpaces() throws Exception {
        entity.putProperty(VALUE_TYPE, LITERAL);
        entity.putProperty(HEADER_NAME, HEADER_NAME_VALUE);
        entity.putProperty(VALUE_LITERAL, VALUE_LITERAL_VALUE);
        entity.putProperty(HEADER_NAMESPACES, Arrays.asList(headerNameSpace));

        assertContentAndValue(PATH_TO_EXAMPLES + "WithHeaderNameSpaces", entity.serialize());
    }

    @Test
    public void serializationShouldBeDoneWithHeaderAndExpressionNamespaces() throws Exception {
        entity.putProperty(VALUE_TYPE, HeaderValueType.EXPRESSION);
        entity.putProperty(HEADER_NAME, HEADER_NAME_VALUE);
        entity.putProperty(EXPRESSION, EXPRESSION_VALUE);
        entity.putProperty(HEADER_NAMESPACES, Arrays.asList(headerNameSpace));
        entity.putProperty(EXPRESSION_NAMESPACES, Arrays.asList(expressionNameSpace));

        assertContentAndValue(PATH_TO_EXAMPLES + "WithHeaderAndExpressionNS", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithNameSpaces() throws Exception {
        when(nameSpaceProvider.get()).thenReturn(expressionNameSpace);
        List<NameSpace> nameSpaces = new ArrayList<>();

        entity.putProperty(EXPRESSION_NAMESPACES, nameSpaces);

        assertThat(nameSpaces.isEmpty(), is(true));

        entity.applyAttribute("xmlns:prefix", "uri");

        assertThat(nameSpaces.size(), is(1));
        assertThat(nameSpaces.get(0), equalTo(expressionNameSpace));
    }

    @Test
    public void elementStateShouldNotBeChangedWhenAttributesIsIncorrect() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(PATH_TO_EXAMPLES + "IncorrectAttributes"));

        assertDefaultConfiguration();
    }

}