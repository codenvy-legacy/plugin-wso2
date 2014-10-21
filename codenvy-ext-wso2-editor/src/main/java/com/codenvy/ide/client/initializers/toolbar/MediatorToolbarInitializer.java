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
package com.codenvy.ide.client.initializers.toolbar;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.constants.MediatorCreatingState;
import com.codenvy.ide.client.constants.ToolbarGroupIds;
import com.codenvy.ide.client.toolbar.ToolbarPresenter;
import com.google.inject.Inject;

/**
 * @author Andrey Plotnikov
 */
public class MediatorToolbarInitializer extends AbstractToolbarInitializer {

    @Inject
    public MediatorToolbarInitializer(ToolbarPresenter toolbar, EditorResources resources, WSO2EditorLocalizationConstant locale) {
        super(toolbar, resources, locale);
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        toolbar.addGroup(ToolbarGroupIds.MEDIATORS, locale.toolbarGroupMediators());

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        locale.toolbarCallTitle(),
                        locale.toolbarCallTooltip(),
                        resources.callToolbar(),
                        MediatorCreatingState.CALL_MEDIATOR);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        locale.toolbarCallTemplateTitle(),
                        locale.toolbarCallTemplateTooltip(),
                        resources.callTemplateToolbar(),
                        MediatorCreatingState.CALLTEMPLATE);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        locale.toolbarLogTitle(),
                        locale.toolbarLogTooltip(),
                        resources.logToolbar(),
                        MediatorCreatingState.LOG);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        locale.toolbarLoopBackTitle(),
                        locale.toolbarLoopBackTooltip(),
                        resources.loopBackToolbar(),
                        MediatorCreatingState.LOOPBACK);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        locale.toolbarPropertyTitle(),
                        locale.toolbarPropertyTooltip(),
                        resources.propertyToolbar(),
                        MediatorCreatingState.PROPERTY);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        locale.toolbarRespondTitle(),
                        locale.toolbarRespondTooltip(),
                        resources.respondToolbar(),
                        MediatorCreatingState.RESPOND);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        locale.toolbarSendTitle(),
                        locale.toolbarSendTooltip(),
                        resources.sendToolbar(),
                        MediatorCreatingState.SEND);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        locale.toolbarSequenceTitle(),
                        locale.toolbarSequenceTooltip(),
                        resources.sequenceToolbar(),
                        MediatorCreatingState.SEQUENCE);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        locale.toolbarFilterTitle(),
                        locale.toolbarFilterTooltip(),
                        resources.filterToolbar(),
                        MediatorCreatingState.FILTER);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        locale.toolbarSwitchTitle(),
                        locale.toolbarSwitchTooltip(),
                        resources.switchToolbar(),
                        MediatorCreatingState.SWITCH);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        locale.toolbarHeaderTitle(),
                        locale.toolbarHeaderTooltip(),
                        resources.headerToolbar(),
                        MediatorCreatingState.HEADER);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        locale.toolbarPayloadFactoryTitle(),
                        locale.toolbarPayloadFactoryTooltip(),
                        resources.payloadFactoryToolbar(),
                        MediatorCreatingState.PAYLOAD);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        locale.toolbarEnrichTitle(),
                        locale.toolbarEnrichTooltip(),
                        resources.enrichToolbar(),
                        MediatorCreatingState.ENRICH);
    }

}