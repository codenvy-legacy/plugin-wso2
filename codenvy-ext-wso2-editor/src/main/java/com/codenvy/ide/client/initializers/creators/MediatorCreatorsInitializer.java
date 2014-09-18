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

package com.codenvy.ide.client.initializers.creators;

import com.codenvy.ide.client.constants.MediatorCreatingState;
import com.codenvy.ide.client.elements.mediators.Call;
import com.codenvy.ide.client.elements.mediators.CallTemplate;
import com.codenvy.ide.client.elements.mediators.Filter;
import com.codenvy.ide.client.elements.mediators.Header;
import com.codenvy.ide.client.elements.mediators.LoopBack;
import com.codenvy.ide.client.elements.mediators.Property;
import com.codenvy.ide.client.elements.mediators.Respond;
import com.codenvy.ide.client.elements.mediators.Send;
import com.codenvy.ide.client.elements.mediators.Sequence;
import com.codenvy.ide.client.elements.mediators.Switch;
import com.codenvy.ide.client.elements.mediators.enrich.Enrich;
import com.codenvy.ide.client.elements.mediators.log.Log;
import com.codenvy.ide.client.elements.mediators.payload.PayloadFactory;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author Andrey Plotnikov
 */
public class MediatorCreatorsInitializer extends AbstractCreatorsInitializer {

    private final Provider<Log>            logProvider;
    private final Provider<Enrich>         enrichProvider;
    private final Provider<Filter>         filterProvider;
    private final Provider<Header>         headerProvider;
    private final Provider<Call>           callProvider;
    private final Provider<CallTemplate>   callTemplateProvider;
    private final Provider<LoopBack>       loopBackProvider;
    private final Provider<PayloadFactory> payloadFactoryProvider;
    private final Provider<Property>       propertyProvider;
    private final Provider<Respond>        respondProvider;
    private final Provider<Send>           sendProvider;
    private final Provider<Sequence>       sequenceProvider;
    private final Provider<Switch>         switchProvider;

    @Inject
    public MediatorCreatorsInitializer(ElementCreatorsManager manager,
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
        super(manager);
        this.logProvider = logProvider;
        this.enrichProvider = enrichProvider;
        this.filterProvider = filterProvider;
        this.headerProvider = headerProvider;
        this.callProvider = callProvider;
        this.callTemplateProvider = callTemplateProvider;
        this.loopBackProvider = loopBackProvider;
        this.payloadFactoryProvider = payloadFactoryProvider;
        this.propertyProvider = propertyProvider;
        this.respondProvider = respondProvider;
        this.sendProvider = sendProvider;
        this.sequenceProvider = sequenceProvider;
        this.switchProvider = switchProvider;
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        manager.register(Log.ELEMENT_NAME, Log.SERIALIZATION_NAME, MediatorCreatingState.LOG, logProvider);
        manager.register(Enrich.ELEMENT_NAME, Enrich.SERIALIZATION_NAME, MediatorCreatingState.ENRICH, enrichProvider);
        manager.register(Filter.ELEMENT_NAME, Filter.SERIALIZATION_NAME, MediatorCreatingState.FILTER, filterProvider);
        manager.register(Header.ELEMENT_NAME, Header.SERIALIZATION_NAME, MediatorCreatingState.HEADER, headerProvider);
        manager.register(Call.ELEMENT_NAME, Call.SERIALIZATION_NAME, MediatorCreatingState.CALL_MEDIATOR, callProvider);

        manager.register(CallTemplate.ELEMENT_NAME,
                         CallTemplate.SERIALIZATION_NAME,
                         MediatorCreatingState.CALLTEMPLATE,
                         callTemplateProvider);

        manager.register(LoopBack.ELEMENT_NAME, LoopBack.SERIALIZATION_NAME, MediatorCreatingState.LOOPBACK, loopBackProvider);

        manager.register(PayloadFactory.ELEMENT_NAME,
                         PayloadFactory.SERIALIZATION_NAME,
                         MediatorCreatingState.PAYLOAD,
                         payloadFactoryProvider);

        manager.register(Property.ELEMENT_NAME, Property.SERIALIZATION_NAME, MediatorCreatingState.PROPERTY, propertyProvider);
        manager.register(Respond.ELEMENT_NAME, Respond.SERIALIZATION_NAME, MediatorCreatingState.RESPOND, respondProvider);
        manager.register(Send.ELEMENT_NAME, Send.SERIALIZATION_NAME, MediatorCreatingState.SEND, sendProvider);
        manager.register(Sequence.ELEMENT_NAME, Sequence.SERIALIZATION_NAME, MediatorCreatingState.SEQUENCE, sequenceProvider);
        manager.register(Switch.ELEMENT_NAME, Switch.SERIALIZATION_NAME, MediatorCreatingState.SWITCH, switchProvider);
    }

}