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
import com.codenvy.ide.client.initializers.Initializer;
import com.codenvy.ide.client.managers.PropertiesPanelManager;
import com.codenvy.ide.client.propertiespanel.mediators.SwitchPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.CallPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.calltemplate.CallTemplatePropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.enrich.EnrichPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.filter.FilterPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.header.HeaderPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.log.LogPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.loopback.LoopBackPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.payloadfactory.PayloadFactoryPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.property.PropertyPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.respond.RespondPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.send.SendPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.sequence.SequencePropertiesPanelPresenter;
import com.google.inject.Inject;

/**
 * @author Andrey Plotnikov
 */
public class MediatorsPropertiesPanelInitializer implements Initializer {

    private final PropertiesPanelManager                 propertiesPanelManager;
    private final LogPropertiesPanelPresenter            logPropertiesPanel;
    private final PropertyPropertiesPanelPresenter       propertyPropertiesPanel;
    private final PayloadFactoryPropertiesPanelPresenter payloadFactoryPropertiesPanel;
    private final SendPropertiesPanelPresenter           sendPropertiesPanel;
    private final HeaderPropertiesPanelPresenter         headerPropertiesPanel;
    private final RespondPropertiesPanelPresenter        respondPropertiesPanel;
    private final FilterPropertiesPanelPresenter         filterPropertiesPanel;
    private final SwitchPropertiesPanelPresenter         switchPropertiesPanel;
    private final SequencePropertiesPanelPresenter       sequencePropertiesPanel;
    private final EnrichPropertiesPanelPresenter         enrichPropertiesPanel;
    private final LoopBackPropertiesPanelPresenter       loopBackPropertiesPanel;
    private final CallTemplatePropertiesPanelPresenter   callTemplatePropertiesPanel;
    private final CallPropertiesPanelPresenter           callPropertiesPanel;

    @Inject
    public MediatorsPropertiesPanelInitializer(PropertiesPanelManager propertiesPanelManager,
                                               LogPropertiesPanelPresenter logPropertiesPanel,
                                               PropertyPropertiesPanelPresenter propertyPropertiesPanel,
                                               PayloadFactoryPropertiesPanelPresenter payloadFactoryPropertiesPanel,
                                               SendPropertiesPanelPresenter sendPropertiesPanel,
                                               HeaderPropertiesPanelPresenter headerPropertiesPanel,
                                               RespondPropertiesPanelPresenter respondPropertiesPanel,
                                               FilterPropertiesPanelPresenter filterPropertiesPanel,
                                               SwitchPropertiesPanelPresenter switchPropertiesPanel,
                                               SequencePropertiesPanelPresenter sequencePropertiesPanel,
                                               EnrichPropertiesPanelPresenter enrichPropertiesPanel,
                                               LoopBackPropertiesPanelPresenter loopBackPropertiesPanel,
                                               CallTemplatePropertiesPanelPresenter callTemplatePropertiesPanel,
                                               CallPropertiesPanelPresenter callPropertiesPanel) {
        this.propertiesPanelManager = propertiesPanelManager;
        this.logPropertiesPanel = logPropertiesPanel;
        this.propertyPropertiesPanel = propertyPropertiesPanel;
        this.payloadFactoryPropertiesPanel = payloadFactoryPropertiesPanel;
        this.sendPropertiesPanel = sendPropertiesPanel;
        this.headerPropertiesPanel = headerPropertiesPanel;
        this.respondPropertiesPanel = respondPropertiesPanel;
        this.filterPropertiesPanel = filterPropertiesPanel;
        this.switchPropertiesPanel = switchPropertiesPanel;
        this.sequencePropertiesPanel = sequencePropertiesPanel;
        this.enrichPropertiesPanel = enrichPropertiesPanel;
        this.loopBackPropertiesPanel = loopBackPropertiesPanel;
        this.callTemplatePropertiesPanel = callTemplatePropertiesPanel;
        this.callPropertiesPanel = callPropertiesPanel;
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        propertiesPanelManager.register(Log.class, logPropertiesPanel);
        propertiesPanelManager.register(Property.class, propertyPropertiesPanel);
        propertiesPanelManager.register(PayloadFactory.class, payloadFactoryPropertiesPanel);
        propertiesPanelManager.register(Send.class, sendPropertiesPanel);
        propertiesPanelManager.register(Header.class, headerPropertiesPanel);
        propertiesPanelManager.register(Respond.class, respondPropertiesPanel);
        propertiesPanelManager.register(Filter.class, filterPropertiesPanel);
        propertiesPanelManager.register(Switch.class, switchPropertiesPanel);
        propertiesPanelManager.register(Sequence.class, sequencePropertiesPanel);
        propertiesPanelManager.register(Enrich.class, enrichPropertiesPanel);
        propertiesPanelManager.register(LoopBack.class, loopBackPropertiesPanel);
        propertiesPanelManager.register(CallTemplate.class, callTemplatePropertiesPanel);
        propertiesPanelManager.register(Call.class, callPropertiesPanel);
    }

}