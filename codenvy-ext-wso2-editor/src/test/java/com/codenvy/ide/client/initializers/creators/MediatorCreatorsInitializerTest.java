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
import com.google.inject.Provider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public class MediatorCreatorsInitializerTest {

    @Mock
    private Provider<Log>            logProvider;
    @Mock
    private Provider<Enrich>         enrichProvider;
    @Mock
    private Provider<Filter>         filterProvider;
    @Mock
    private Provider<Header>         headerProvider;
    @Mock
    private Provider<Call>           callProvider;
    @Mock
    private Provider<CallTemplate>   callTemplateProvider;
    @Mock
    private Provider<LoopBack>       loopBackProvider;
    @Mock
    private Provider<PayloadFactory> payloadFactoryProvider;
    @Mock
    private Provider<Property>       propertyProvider;
    @Mock
    private Provider<Respond>        respondProvider;
    @Mock
    private Provider<Send>           sendProvider;
    @Mock
    private Provider<Sequence>       sequenceProvider;
    @Mock
    private Provider<Switch>         switchProvider;
    @Mock
    private ElementCreatorsManager   manager;

    private MediatorCreatorsInitializer initializer;

    @Before
    public void setUp() throws Exception {
        initializer = new MediatorCreatorsInitializer(manager,
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
    }

    @Test
    public void logMediatorShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(Log.ELEMENT_NAME, Log.SERIALIZATION_NAME, MediatorCreatingState.LOG, logProvider);
    }

    @Test
    public void enrichMediatorShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(Enrich.ELEMENT_NAME, Enrich.SERIALIZATION_NAME, MediatorCreatingState.ENRICH, enrichProvider);
    }

    @Test
    public void filterMediatorShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(Filter.ELEMENT_NAME, Filter.SERIALIZATION_NAME, MediatorCreatingState.FILTER, filterProvider);
    }

    @Test
    public void headerMediatorShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(Header.ELEMENT_NAME, Header.SERIALIZATION_NAME, MediatorCreatingState.HEADER, headerProvider);
    }

    @Test
    public void callMediatorShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(Call.ELEMENT_NAME, Call.SERIALIZATION_NAME, MediatorCreatingState.CALL_MEDIATOR, callProvider);
    }

    @Test
    public void callTemplateMediatorShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(CallTemplate.ELEMENT_NAME,
                                 CallTemplate.SERIALIZATION_NAME,
                                 MediatorCreatingState.CALLTEMPLATE,
                                 callTemplateProvider);
    }

    @Test
    public void loopBackMediatorShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(LoopBack.ELEMENT_NAME, LoopBack.SERIALIZATION_NAME, MediatorCreatingState.LOOPBACK, loopBackProvider);
    }

    @Test
    public void payloadFactoryMediatorInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(PayloadFactory.ELEMENT_NAME,
                                 PayloadFactory.SERIALIZATION_NAME,
                                 MediatorCreatingState.PAYLOAD,
                                 payloadFactoryProvider);
    }

    @Test
    public void propertyMediatorShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(Property.ELEMENT_NAME, Property.SERIALIZATION_NAME, MediatorCreatingState.PROPERTY, propertyProvider);
    }

    @Test
    public void respondMediatorShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(Respond.ELEMENT_NAME, Respond.SERIALIZATION_NAME, MediatorCreatingState.RESPOND, respondProvider);
    }

    @Test
    public void sendMediatorShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(Send.ELEMENT_NAME, Send.SERIALIZATION_NAME, MediatorCreatingState.SEND, sendProvider);
    }

    @Test
    public void sequenceMediatorShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(Sequence.ELEMENT_NAME, Sequence.SERIALIZATION_NAME, MediatorCreatingState.SEQUENCE, sequenceProvider);
    }

    @Test
    public void switchMediatorShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(Switch.ELEMENT_NAME, Switch.SERIALIZATION_NAME, MediatorCreatingState.SWITCH, switchProvider);
    }

}