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

import com.codenvy.ide.client.elements.AbstractEntityTest;
import com.codenvy.ide.client.elements.NameSpace;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;
import com.google.inject.Provider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.payload.Arg.ARG_EVALUATOR;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.ARG_EXPRESSION;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.ARG_NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.ARG_TYPE;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.ARG_VALUE;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.ArgType;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.ArgType.EXPRESSION;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.ArgType.VALUE;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.Evaluator;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.Evaluator.JSON;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.Evaluator.XML;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Valeriy Svydenko
 */
public class ArgTest extends AbstractEntityTest<Arg> {
    private static final String RESOURCES_PATH = "mediators/payload/arg/serialize/";

    private static final ArgType         ARG_TYPE_DEFAULT_VALUE       = VALUE;
    private static final Evaluator       ARG_EVALUATOR_DEFAULT_VALUE  = XML;
    private static final String          ARG_EXPRESSION_DEFAULT_VALUE = "/default/expression";
    private static final String          ARG_VALUE_DEFAULT_VALUE      = "default";
    private static final List<NameSpace> ARG_NAMESPACE_DEFAULT_VALUE  = new ArrayList<>();

    @Mock
    private Provider<Arg>       argProvider;
    @Mock
    private Provider<NameSpace> nameSpaceProvider;
    @Mock
    private NameSpace           nameSpace;
    @Mock
    private Arg                 arg;

    @Captor
    private ArgumentCaptor<ArrayList<NameSpace>> spaceArgumentCaptor;
    @Captor
    private ArgumentCaptor<String>               stringArgumentCaptor;
    @Captor
    private ArgumentCaptor<ArgType>              argTypeArgumentCaptor;
    @Captor
    private ArgumentCaptor<Evaluator>            evaluatorArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        when(argProvider.get()).thenReturn(arg);
        when(nameSpaceProvider.get()).thenReturn(nameSpace);

        entity = new Arg(nameSpaceProvider, argProvider);
    }

    private void assertConfiguration(ArgType type,
                                     Evaluator evaluator,
                                     String expression,
                                     String value,
                                     List<NameSpace> nameSpaces) {

        assertEquals(type, entity.getProperty(ARG_TYPE));
        assertEquals(evaluator, entity.getProperty(ARG_EVALUATOR));
        assertEquals(expression, entity.getProperty(ARG_EXPRESSION));
        assertEquals(value, entity.getProperty(ARG_VALUE));
        assertEquals(nameSpaces, entity.getProperty(ARG_NAMESPACES));
    }

    private void assertDefaultConfiguration() {
        assertConfiguration(ARG_TYPE_DEFAULT_VALUE,
                            ARG_EVALUATOR_DEFAULT_VALUE,
                            ARG_EXPRESSION_DEFAULT_VALUE,
                            ARG_VALUE_DEFAULT_VALUE,
                            ARG_NAMESPACE_DEFAULT_VALUE);
    }

    @Test
    public void allPropertiesShouldBeCopied() throws Exception {
        entity.putProperty(ARG_TYPE, ARG_TYPE_DEFAULT_VALUE);
        entity.putProperty(ARG_EVALUATOR, ARG_EVALUATOR_DEFAULT_VALUE);
        entity.putProperty(ARG_EXPRESSION, ARG_EXPRESSION_DEFAULT_VALUE);
        entity.putProperty(ARG_VALUE, ARG_VALUE_DEFAULT_VALUE);
        entity.putProperty(ARG_NAMESPACES, ARG_NAMESPACE_DEFAULT_VALUE);

        Arg newProperty = entity.copy();

        verify(arg).putProperty(eq(ARG_TYPE), argTypeArgumentCaptor.capture());
        verify(arg).putProperty(eq(ARG_EVALUATOR), evaluatorArgumentCaptor.capture());
        verify(arg).putProperty(eq(ARG_EXPRESSION), stringArgumentCaptor.capture());
        verify(arg).putProperty(eq(ARG_VALUE), stringArgumentCaptor.capture());
        verify(arg).putProperty(eq(ARG_NAMESPACES), spaceArgumentCaptor.capture());

        assertEquals(ARG_TYPE_DEFAULT_VALUE, argTypeArgumentCaptor.getValue());
        assertEquals(ARG_EVALUATOR_DEFAULT_VALUE, evaluatorArgumentCaptor.getValue());
        assertEquals(ARG_EXPRESSION_DEFAULT_VALUE, stringArgumentCaptor.getAllValues().get(0));
        assertEquals(ARG_VALUE_DEFAULT_VALUE, stringArgumentCaptor.getAllValues().get(1));
        assertEquals(ARG_NAMESPACE_DEFAULT_VALUE, spaceArgumentCaptor.getValue());

        assertDefaultConfiguration();

        assertEquals(arg, newProperty);
    }

    @Test
    public void serializationShouldBeDoneWithDefaultValues() throws Exception {
        assertContentAndValue(RESOURCES_PATH + "Default", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneForDefaultRepresentation() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(RESOURCES_PATH + "Default"));
        entity.applyAttributes(xml.getFirstChild());

        assertDefaultConfiguration();
    }

    @Test
    public void serializationShouldBeNotDoneWhenEvaluatorIsNull() throws Exception {
        entity.putProperty(ARG_EVALUATOR, null);

        assertContentAndValue(RESOURCES_PATH + "EvaluatorIsNull", entity.serialize());
    }

    @Test
    public void serializationShouldBeDoneWhenEvaluatorAttributeIsXml() throws Exception {
        entity.putProperty(ARG_EVALUATOR, XML);
        entity.putProperty(ARG_TYPE, EXPRESSION);

        assertContentAndValue(RESOURCES_PATH + "EvaluatorIsXml", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenEvaluatorAttributeIsXml() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(RESOURCES_PATH + "EvaluatorIsXml"));
        entity.applyAttributes(xml.getFirstChild());

        assertConfiguration(EXPRESSION, XML, ARG_EXPRESSION_DEFAULT_VALUE, ARG_VALUE_DEFAULT_VALUE, ARG_NAMESPACE_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenEvaluatorAttributeIsJson() throws Exception {
        entity.putProperty(ARG_EVALUATOR, JSON);
        entity.putProperty(ARG_TYPE, EXPRESSION);

        assertContentAndValue(RESOURCES_PATH + "EvaluatorIsJSON", entity.serialize());
    }

    @Test
    public void serializationShouldBeDoneWhenNameSpaceNotEmpty() throws Exception {
        List<NameSpace> nameSpaces = new ArrayList<>();

        entity.putProperty(ARG_NAMESPACES, nameSpaces);

        assertTrue(nameSpaces.isEmpty());

        entity.applyAttribute("xmlns:a", "b");

        assertEquals(1, nameSpaces.size());
        assertEquals(nameSpace, nameSpaces.get(0));
    }

    @Test
    public void deserializationShouldBeDoneWhenEvaluatorAttributeIsJson() throws Exception {
        assertDefaultConfiguration();

        Document xml = XMLParser.parse(getContent(RESOURCES_PATH + "EvaluatorIsJSON"));
        entity.applyAttributes(xml.getFirstChild());

        assertConfiguration(EXPRESSION, JSON, ARG_EXPRESSION_DEFAULT_VALUE, ARG_VALUE_DEFAULT_VALUE, ARG_NAMESPACE_DEFAULT_VALUE);
    }

    @Test
    public void copyArgsListShouldBeDone() throws Exception {
        List<Arg> argList = Arrays.asList(entity);
        List<Arg> copyArgList = Arg.copyArgsList(argList);

        assertEquals(argList, copyArgList);
        assertNotSame(argList, copyArgList);
    }

    @Test
    public void copyArgsListShouldBeNotDoneIfListToCopeIsNull() throws Exception {
        List<Arg> copyArgList = Arg.copyArgsList(null);

        assertTrue(copyArgList.isEmpty());
    }

    @Test
    public void valueValueOfArgTypeShouldBeReturned() throws Exception {
        assertEquals(VALUE, ArgType.getItemByValue("Value"));
    }

    @Test
    public void valueOfArgTypeShouldBeReturned() throws Exception {
        assertEquals("Value", VALUE.getValue());
        assertEquals("Expression", EXPRESSION.getValue());
    }

    @Test
    public void expressionValueOfArgTypeShouldBeReturned() throws Exception {
        assertEquals(EXPRESSION, ArgType.getItemByValue("Expression"));
    }

    @Test
    public void jsonValueOfEvaluatorShouldBeReturned() throws Exception {
        assertEquals(JSON, Evaluator.getItemByValue("json"));
    }

    @Test
    public void xmlValueOfEvaluatorShouldBeReturned() throws Exception {
        assertEquals(XML, Evaluator.getItemByValue("xml"));
    }

}