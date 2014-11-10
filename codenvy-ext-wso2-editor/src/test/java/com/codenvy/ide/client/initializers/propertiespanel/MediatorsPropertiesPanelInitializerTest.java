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
package com.codenvy.ide.client.initializers.propertiespanel;

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
import com.codenvy.ide.client.managers.PropertiesPanelManager;
import com.codenvy.ide.client.propertiespanel.mediators.CallPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.CallTemplatePropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.EnrichPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.FilterPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.HeaderPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.LogPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.LoopBackPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.PayloadFactoryPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.PropertyPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.RespondPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.SendPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.SequencePropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.SwitchPropertiesPanelPresenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public class MediatorsPropertiesPanelInitializerTest {

    @Mock
    private PropertiesPanelManager                 manager;
    @Mock
    private LogPropertiesPanelPresenter            logPropertiesPanel;
    @Mock
    private PropertyPropertiesPanelPresenter       propertyPropertiesPanel;
    @Mock
    private PayloadFactoryPropertiesPanelPresenter payloadFactoryPropertiesPanel;
    @Mock
    private SendPropertiesPanelPresenter           sendPropertiesPanel;
    @Mock
    private HeaderPropertiesPanelPresenter         headerPropertiesPanel;
    @Mock
    private RespondPropertiesPanelPresenter        respondPropertiesPanel;
    @Mock
    private FilterPropertiesPanelPresenter         filterPropertiesPanel;
    @Mock
    private SwitchPropertiesPanelPresenter         switchPropertiesPanel;
    @Mock
    private SequencePropertiesPanelPresenter       sequencePropertiesPanel;
    @Mock
    private EnrichPropertiesPanelPresenter         enrichPropertiesPanel;
    @Mock
    private LoopBackPropertiesPanelPresenter       loopBackPropertiesPanel;
    @Mock
    private CallTemplatePropertiesPanelPresenter   callTemplatePropertiesPanel;
    @Mock
    private CallPropertiesPanelPresenter           callPropertiesPanel;

    @InjectMocks
    private MediatorsPropertiesPanelInitializer initializer;

    @Test
    public void logMediatorPropertiesPanelShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(Log.class, logPropertiesPanel);
    }

    @Test
    public void propertyMediatorPropertiesPanelShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(Property.class, propertyPropertiesPanel);
    }

    @Test
    public void payloadFactoryMediatorPropertiesPanelShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(PayloadFactory.class, payloadFactoryPropertiesPanel);
    }

    @Test
    public void sendMediatorPropertiesPanelShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(Send.class, sendPropertiesPanel);
    }

    @Test
    public void headerMediatorPropertiesPanelShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(Header.class, headerPropertiesPanel);
    }

    @Test
    public void respondMediatorPropertiesPanelShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(Respond.class, respondPropertiesPanel);
    }

    @Test
    public void filterMediatorPropertiesPanelShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(Filter.class, filterPropertiesPanel);
    }

    @Test
    public void switchMediatorPropertiesPanelShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(Switch.class, switchPropertiesPanel);
    }

    @Test
    public void sequenceMediatorPropertiesPanelShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(Sequence.class, sequencePropertiesPanel);
    }

    @Test
    public void enrichMediatorPropertiesPanelShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(Enrich.class, enrichPropertiesPanel);
    }

    @Test
    public void loopBackMediatorPropertiesPanelShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(LoopBack.class, loopBackPropertiesPanel);
    }

    @Test
    public void callTemplateMediatorPropertiesPanelShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(CallTemplate.class, callTemplatePropertiesPanel);
    }

    @Test
    public void callMediatorPropertiesPanelShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(Call.class, callPropertiesPanel);
    }

}