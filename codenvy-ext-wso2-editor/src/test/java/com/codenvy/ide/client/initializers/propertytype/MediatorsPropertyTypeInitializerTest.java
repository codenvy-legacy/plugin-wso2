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
package com.codenvy.ide.client.initializers.propertytype;

import com.codenvy.ide.client.elements.mediators.Action;
import com.codenvy.ide.client.elements.mediators.ValueType;
import com.codenvy.ide.client.managers.PropertyTypeManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static com.codenvy.ide.client.elements.mediators.Call.EndpointType;
import static com.codenvy.ide.client.elements.mediators.CallTemplate.AvailableTemplates;
import static com.codenvy.ide.client.elements.mediators.Filter.ConditionType;
import static com.codenvy.ide.client.elements.mediators.Header.HeaderValueType;
import static com.codenvy.ide.client.elements.mediators.Header.ScopeType;
import static com.codenvy.ide.client.elements.mediators.Property.DataType;
import static com.codenvy.ide.client.elements.mediators.Property.Scope;
import static com.codenvy.ide.client.elements.mediators.Send.SequenceType;
import static com.codenvy.ide.client.elements.mediators.Sequence.ReferringType;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.InlineType;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SourceType;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TargetAction;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TargetType;
import static com.codenvy.ide.client.elements.mediators.log.Log.LogCategory;
import static com.codenvy.ide.client.elements.mediators.log.Log.LogLevel;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.ArgType;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.Evaluator;
import static com.codenvy.ide.client.elements.mediators.payload.Format.FormatType;
import static com.codenvy.ide.client.elements.mediators.payload.PayloadFactory.MediaType;
import static org.mockito.Mockito.verify;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public class MediatorsPropertyTypeInitializerTest {

    @Mock
    private PropertyTypeManager              manager;
    @InjectMocks
    private MediatorsPropertyTypeInitializer initializer;

    @Test
    public void callMediatorEndpointTypeShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(EndpointType.TYPE_NAME, Arrays.asList(EndpointType.INLINE.name(),
                                                                       EndpointType.NONE.name(),
                                                                       EndpointType.REGISTRYKEY.name(),
                                                                       EndpointType.XPATH.name()));
    }

    @Test
    public void callTemplateMediatorAvailableTemplatesShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(AvailableTemplates.TYPE_NAME, Arrays.asList(AvailableTemplates.EMPTY.getValue(),
                                                                             AvailableTemplates.SELECT_FROM_TEMPLATE.getValue(),
                                                                             AvailableTemplates.SDF.getValue()));
    }

    @Test
    public void filterMediatorConditionTypeShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(ConditionType.TYPE_NAME, Arrays.asList(ConditionType.SOURCE_AND_REGEX.name(), ConditionType.XPATH.name()));
    }

    @Test
    public void actionShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(Action.TYPE_NAME, Arrays.asList(Action.SET.getValue(), Action.REMOVE.getValue()));
    }

    @Test
    public void headerMediatorHeaderValueTypeShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(HeaderValueType.TYPE_NAME, Arrays.asList(HeaderValueType.LITERAL.name(),
                                                                          HeaderValueType.EXPRESSION.name(),
                                                                          HeaderValueType.INLINE.name()));
    }

    @Test
    public void headerMediatorScopeTypeShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(ScopeType.TYPE_NAME, Arrays.asList(ScopeType.SYNAPSE.getValue(), ScopeType.TRANSPORT.getValue()));
    }

    @Test
    public void propertyMediatorDataTypeShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(DataType.TYPE_NAME, Arrays.asList(DataType.STRING.name(),
                                                                   DataType.INTEGER.name(),
                                                                   DataType.BOOLEAN.name(),
                                                                   DataType.DOUBLE.name(),
                                                                   DataType.FLOAT.name(),
                                                                   DataType.LONG.name(),
                                                                   DataType.SHORT.name(),
                                                                   DataType.OM.name()));
    }

    @Test
    public void propertyMediatorScopeShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(Scope.TYPE_NAME, Arrays.asList(Scope.SYNAPSE.getValue(),
                                                                Scope.TRANSPORT.getValue(),
                                                                Scope.AXIS2.getValue(),
                                                                Scope.AXIS2_CLIENT.getValue(),
                                                                Scope.OPERATION.getValue()));
    }

    @Test
    public void sendMediatorSequenceTypeShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(SequenceType.TYPE_NAME, Arrays.asList(SequenceType.DEFAULT.getValue(),
                                                                       SequenceType.STATIC.getValue(),
                                                                       SequenceType.DYNAMIC.getValue()));
    }

    @Test
    public void sequenceMediatorReferringTypeShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(ReferringType.TYPE_NAME, Arrays.asList(ReferringType.STATIC.getValue(), ReferringType.DYNAMIC.getValue()));
    }

    @Test
    public void logMediatorLogCategoryShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(LogCategory.TYPE_NAME, Arrays.asList(LogCategory.TRACE.name(),
                                                                      LogCategory.DEBUG.name(),
                                                                      LogCategory.INFO.name(),
                                                                      LogCategory.WARN.name(),
                                                                      LogCategory.ERROR.name(),
                                                                      LogCategory.FATAL.name()));
    }

    @Test
    public void logMediatorLogLevelShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(LogLevel.TYPE_NAME, Arrays.asList(LogLevel.SIMPLE.name(),
                                                                   LogLevel.HEADERS.name(),
                                                                   LogLevel.FULL.name(),
                                                                   LogLevel.CUSTOM.name()));
    }

    @Test
    public void valueTypeShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(ValueType.TYPE_NAME, Arrays.asList(ValueType.LITERAL.name(), ValueType.EXPRESSION.name()));
    }

    @Test
    public void payloadFactoryMediatorMediaTypeShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(MediaType.TYPE_NAME, Arrays.asList(MediaType.XML.getValue(), MediaType.JSON.getValue()));
    }

    @Test
    public void formatPropertyFormatTypeShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(FormatType.TYPE_NAME, Arrays.asList(FormatType.INLINE.getValue(), FormatType.REGISTRY.getValue()));
    }

    @Test
    public void sourcePropertySourceTypeShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(SourceType.TYPE_NAME, Arrays.asList(SourceType.CUSTOM.getValue(),
                                                                     SourceType.ENVELOPE.getValue(),
                                                                     SourceType.BODY.getValue(),
                                                                     SourceType.PROPERTY.getValue(),
                                                                     SourceType.INLINE.getValue()));
    }

    @Test
    public void targetPropertyTargetActionShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(TargetAction.TYPE_NAME, Arrays.asList(TargetAction.REPLACE.getValue(),
                                                                       TargetAction.CHILD.getValue(),
                                                                       TargetAction.SIBLING.getValue()));
    }

    @Test
    public void targetPropertyTargetTypeShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(TargetType.TYPE_NAME, Arrays.asList(TargetType.CUSTOM.getValue(),
                                                                     TargetType.ENVELOPE.getValue(),
                                                                     TargetType.BODY.getValue(),
                                                                     TargetType.PROPERTY.getValue()));
    }

    @Test
    public void sourcePropertyInlineTypeShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(InlineType.TYPE_NAME, Arrays.asList(InlineType.REGISTRY_KEY.getValue(), InlineType.SOURCE_XML.getValue()));
    }

    @Test
    public void argPropertyArgTypeShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(ArgType.TYPE_NAME, Arrays.asList(ArgType.VALUE.getValue(), ArgType.EXPRESSION.getValue()));
    }

    @Test
    public void argPropertyEvaluatorShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(Evaluator.TYPE_NAME, Arrays.asList(Evaluator.XML.getValue(), Evaluator.JSON.getValue()));
    }

}