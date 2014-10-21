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
import com.google.inject.Inject;

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
import static com.codenvy.ide.client.elements.mediators.payload.Format.FormatType;
import static com.codenvy.ide.client.elements.mediators.payload.PayloadFactory.MediaType;

/**
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class MediatorsPropertyTypeInitializer extends AbstractPropertyTypeInitializer {

    @Inject
    public MediatorsPropertyTypeInitializer(PropertyTypeManager manager) {
        super(manager);
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        manager.register(EndpointType.TYPE_NAME, Arrays.asList(EndpointType.INLINE.name(),
                                                               EndpointType.NONE.name(),
                                                               EndpointType.REGISTRYKEY.name(),
                                                               EndpointType.XPATH.name()));

        manager.register(AvailableTemplates.TYPE_NAME, Arrays.asList(AvailableTemplates.EMPTY.getValue(),
                                                                     AvailableTemplates.SELECT_FROM_TEMPLATE.getValue(),
                                                                     AvailableTemplates.SDF.getValue()));

        manager.register(ConditionType.TYPE_NAME, Arrays.asList(ConditionType.SOURCE_AND_REGEX.name(),
                                                                ConditionType.XPATH.name()));

        manager.register(Action.TYPE_NAME, Arrays.asList(Action.SET.getValue(), Action.REMOVE.getValue()));

        manager.register(HeaderValueType.TYPE_NAME, Arrays.asList(HeaderValueType.LITERAL.name(),
                                                                  HeaderValueType.EXPRESSION.name(),
                                                                  HeaderValueType.INLINE.name()));

        manager.register(ScopeType.TYPE_NAME, Arrays.asList(ScopeType.SYNAPSE.getValue(), ScopeType.TRANSPORT.getValue()));

        manager.register(DataType.TYPE_NAME, Arrays.asList(DataType.STRING.name(),
                                                           DataType.INTEGER.name(),
                                                           DataType.BOOLEAN.name(),
                                                           DataType.DOUBLE.name(),
                                                           DataType.FLOAT.name(),
                                                           DataType.LONG.name(),
                                                           DataType.SHORT.name(),
                                                           DataType.OM.name()));

        manager.register(Scope.TYPE_NAME, Arrays.asList(Scope.SYNAPSE.getValue(),
                                                        Scope.TRANSPORT.getValue(),
                                                        Scope.AXIS2.getValue(),
                                                        Scope.AXIS2_CLIENT.getValue(),
                                                        Scope.OPERATION.getValue()));

        manager.register(SequenceType.TYPE_NAME, Arrays.asList(SequenceType.DEFAULT.getValue(),
                                                               SequenceType.STATIC.getValue(),
                                                               SequenceType.DYNAMIC.getValue()));

        manager.register(ReferringType.TYPE_NAME, Arrays.asList(ReferringType.STATIC.getValue(),
                                                                ReferringType.DYNAMIC.getValue()));

        manager.register(LogCategory.TYPE_NAME, Arrays.asList(LogCategory.TRACE.name(),
                                                              LogCategory.DEBUG.name(),
                                                              LogCategory.INFO.name(),
                                                              LogCategory.WARN.name(),
                                                              LogCategory.ERROR.name(),
                                                              LogCategory.FATAL.name()));

        manager.register(LogLevel.TYPE_NAME, Arrays.asList(LogLevel.SIMPLE.name(),
                                                           LogLevel.HEADERS.name(),
                                                           LogLevel.FULL.name(),
                                                           LogLevel.CUSTOM.name()));

        manager.register(ValueType.TYPE_NAME, Arrays.asList(ValueType.LITERAL.name(), ValueType.EXPRESSION.name()));

        manager.register(MediaType.TYPE_NAME, Arrays.asList(MediaType.XML.getValue(), MediaType.JSON.getValue()));

        manager.register(FormatType.TYPE_NAME, Arrays.asList(FormatType.INLINE.getValue(), FormatType.REGISTRY.getValue()));

        manager.register(SourceType.TYPE_NAME, Arrays.asList(SourceType.CUSTOM.getValue(),
                                                             SourceType.ENVELOPE.getValue(),
                                                             SourceType.BODY.getValue(),
                                                             SourceType.PROPERTY.getValue(),
                                                             SourceType.INLINE.getValue()));

        manager.register(TargetAction.TYPE_NAME, Arrays.asList(TargetAction.REPLACE.getValue(),
                                                               TargetAction.CHILD.getValue(),
                                                               TargetAction.SIBLING.getValue()));

        manager.register(TargetType.TYPE_NAME, Arrays.asList(TargetType.CUSTOM.getValue(),
                                                             TargetType.ENVELOPE.getValue(),
                                                             TargetType.BODY.getValue(),
                                                             TargetType.PROPERTY.getValue()));

        manager.register(InlineType.TYPE_NAME, Arrays.asList(InlineType.REGISTRY_KEY.getValue(),
                                                             InlineType.SOURCE_XML.getValue()));
    }

}