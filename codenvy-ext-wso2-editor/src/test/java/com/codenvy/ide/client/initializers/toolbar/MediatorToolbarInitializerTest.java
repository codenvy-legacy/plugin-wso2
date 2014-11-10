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
import com.google.gwt.resources.client.ImageResource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public class MediatorToolbarInitializerTest {

    private static final String SOME_TEXT_1 = "some text1";
    private static final String SOME_TEXT_2 = "some text2";

    @Mock
    private ImageResource image;

    @Mock
    private ToolbarPresenter               toolbar;
    @Mock
    private EditorResources                resources;
    @Mock
    private WSO2EditorLocalizationConstant locale;
    @InjectMocks
    private MediatorToolbarInitializer     initializer;

    @Test
    public void mediatorsToolbarGroupShouldBeCreated() throws Exception {
        when(locale.toolbarGroupMediators()).thenReturn(SOME_TEXT_1);

        initializer.initialize();

        verify(toolbar).addGroup(ToolbarGroupIds.MEDIATORS, SOME_TEXT_1);
        verify(locale).toolbarGroupMediators();
    }

    @Test
    public void callToolbarItemShouldBeInitialized() throws Exception {
        when(locale.toolbarCallTitle()).thenReturn(SOME_TEXT_1);
        when(locale.toolbarCallTooltip()).thenReturn(SOME_TEXT_2);
        when(resources.callToolbar()).thenReturn(image);

        initializer.initialize();

        verify(toolbar).addItem(ToolbarGroupIds.MEDIATORS, SOME_TEXT_1, SOME_TEXT_2, image, MediatorCreatingState.CALL_MEDIATOR);
        verify(locale).toolbarCallTitle();
        verify(locale).toolbarCallTooltip();
        verify(resources).callToolbar();
    }

    @Test
    public void callTemplateToolbarItemShouldBeInitialized() throws Exception {
        when(locale.toolbarCallTemplateTitle()).thenReturn(SOME_TEXT_1);
        when(locale.toolbarCallTemplateTooltip()).thenReturn(SOME_TEXT_2);
        when(resources.callTemplateToolbar()).thenReturn(image);

        initializer.initialize();

        verify(toolbar).addItem(ToolbarGroupIds.MEDIATORS, SOME_TEXT_1, SOME_TEXT_2, image, MediatorCreatingState.CALLTEMPLATE);
        verify(locale).toolbarCallTemplateTitle();
        verify(locale).toolbarCallTemplateTooltip();
        verify(resources).callTemplateToolbar();
    }

    @Test
    public void logToolbarItemShouldBeInitialized() throws Exception {
        when(locale.toolbarLogTitle()).thenReturn(SOME_TEXT_1);
        when(locale.toolbarLogTooltip()).thenReturn(SOME_TEXT_2);
        when(resources.logToolbar()).thenReturn(image);

        initializer.initialize();

        verify(toolbar).addItem(ToolbarGroupIds.MEDIATORS, SOME_TEXT_1, SOME_TEXT_2, image, MediatorCreatingState.LOG);
        verify(locale).toolbarLogTitle();
        verify(locale).toolbarLogTooltip();
        verify(resources).logToolbar();
    }

    @Test
    public void loopBackToolbarItemShouldBeInitialized() throws Exception {
        when(locale.toolbarLoopBackTitle()).thenReturn(SOME_TEXT_1);
        when(locale.toolbarLoopBackTooltip()).thenReturn(SOME_TEXT_2);
        when(resources.loopBackToolbar()).thenReturn(image);

        initializer.initialize();

        verify(toolbar).addItem(ToolbarGroupIds.MEDIATORS, SOME_TEXT_1, SOME_TEXT_2, image, MediatorCreatingState.LOOPBACK);
        verify(locale).toolbarLoopBackTitle();
        verify(locale).toolbarLoopBackTooltip();
        verify(resources).loopBackToolbar();
    }

    @Test
    public void propertyToolbarItemShouldBeInitialized() throws Exception {
        when(locale.toolbarPropertyTitle()).thenReturn(SOME_TEXT_1);
        when(locale.toolbarPropertyTooltip()).thenReturn(SOME_TEXT_2);
        when(resources.propertyToolbar()).thenReturn(image);

        initializer.initialize();

        verify(toolbar).addItem(ToolbarGroupIds.MEDIATORS, SOME_TEXT_1, SOME_TEXT_2, image, MediatorCreatingState.PROPERTY);
        verify(locale).toolbarPropertyTitle();
        verify(locale).toolbarPropertyTooltip();
        verify(resources).propertyToolbar();
    }

    @Test
    public void respondToolbarItemShouldBeInitialized() throws Exception {
        when(locale.toolbarRespondTitle()).thenReturn(SOME_TEXT_1);
        when(locale.toolbarRespondTooltip()).thenReturn(SOME_TEXT_2);
        when(resources.respondToolbar()).thenReturn(image);

        initializer.initialize();

        verify(toolbar).addItem(ToolbarGroupIds.MEDIATORS, SOME_TEXT_1, SOME_TEXT_2, image, MediatorCreatingState.RESPOND);
        verify(locale).toolbarRespondTitle();
        verify(locale).toolbarRespondTooltip();
        verify(resources).respondToolbar();
    }

    @Test
    public void sendToolbarItemShouldBeInitialized() throws Exception {
        when(locale.toolbarSendTitle()).thenReturn(SOME_TEXT_1);
        when(locale.toolbarSendTooltip()).thenReturn(SOME_TEXT_2);
        when(resources.sendToolbar()).thenReturn(image);

        initializer.initialize();

        verify(toolbar).addItem(ToolbarGroupIds.MEDIATORS, SOME_TEXT_1, SOME_TEXT_2, image, MediatorCreatingState.SEND);
        verify(locale).toolbarSendTitle();
        verify(locale).toolbarSendTooltip();
        verify(resources).sendToolbar();
    }

    @Test
    public void sequenceToolbarItemShouldBeInitialized() throws Exception {
        when(locale.toolbarSequenceTitle()).thenReturn(SOME_TEXT_1);
        when(locale.toolbarSequenceTooltip()).thenReturn(SOME_TEXT_2);
        when(resources.sequenceToolbar()).thenReturn(image);

        initializer.initialize();

        verify(toolbar).addItem(ToolbarGroupIds.MEDIATORS, SOME_TEXT_1, SOME_TEXT_2, image, MediatorCreatingState.SEQUENCE);
        verify(locale).toolbarSequenceTitle();
        verify(locale).toolbarSequenceTooltip();
        verify(resources).sequenceToolbar();
    }

    @Test
    public void filterToolbarItemShouldBeInitialized() throws Exception {
        when(locale.toolbarFilterTitle()).thenReturn(SOME_TEXT_1);
        when(locale.toolbarFilterTooltip()).thenReturn(SOME_TEXT_2);
        when(resources.filterToolbar()).thenReturn(image);

        initializer.initialize();

        verify(toolbar).addItem(ToolbarGroupIds.MEDIATORS, SOME_TEXT_1, SOME_TEXT_2, image, MediatorCreatingState.FILTER);
        verify(locale).toolbarFilterTitle();
        verify(locale).toolbarFilterTooltip();
        verify(resources).filterToolbar();
    }

    @Test
    public void switchToolbarItemShouldBeInitialized() throws Exception {
        when(locale.toolbarSwitchTitle()).thenReturn(SOME_TEXT_1);
        when(locale.toolbarSwitchTooltip()).thenReturn(SOME_TEXT_2);
        when(resources.switchToolbar()).thenReturn(image);

        initializer.initialize();

        verify(toolbar).addItem(ToolbarGroupIds.MEDIATORS, SOME_TEXT_1, SOME_TEXT_2, image, MediatorCreatingState.SWITCH);
        verify(locale).toolbarSwitchTitle();
        verify(locale).toolbarSwitchTooltip();
        verify(resources).switchToolbar();
    }

    @Test
    public void headerToolbarItemShouldBeInitialized() throws Exception {
        when(locale.toolbarHeaderTitle()).thenReturn(SOME_TEXT_1);
        when(locale.toolbarHeaderTooltip()).thenReturn(SOME_TEXT_2);
        when(resources.headerToolbar()).thenReturn(image);

        initializer.initialize();

        verify(toolbar).addItem(ToolbarGroupIds.MEDIATORS, SOME_TEXT_1, SOME_TEXT_2, image, MediatorCreatingState.HEADER);
        verify(locale).toolbarHeaderTitle();
        verify(locale).toolbarHeaderTooltip();
        verify(resources).headerToolbar();
    }

    @Test
    public void payloadFactoryToolbarItemShouldBeInitialized() throws Exception {
        when(locale.toolbarPayloadFactoryTitle()).thenReturn(SOME_TEXT_1);
        when(locale.toolbarPayloadFactoryTooltip()).thenReturn(SOME_TEXT_2);
        when(resources.payloadFactoryToolbar()).thenReturn(image);

        initializer.initialize();

        verify(toolbar).addItem(ToolbarGroupIds.MEDIATORS, SOME_TEXT_1, SOME_TEXT_2, image, MediatorCreatingState.PAYLOAD);
        verify(locale).toolbarPayloadFactoryTitle();
        verify(locale).toolbarPayloadFactoryTooltip();
        verify(resources).payloadFactoryToolbar();
    }

    @Test
    public void enrichToolbarItemShouldBeInitialized() throws Exception {
        when(locale.toolbarEnrichTitle()).thenReturn(SOME_TEXT_1);
        when(locale.toolbarEnrichTooltip()).thenReturn(SOME_TEXT_2);
        when(resources.enrichToolbar()).thenReturn(image);

        initializer.initialize();

        verify(toolbar).addItem(ToolbarGroupIds.MEDIATORS, SOME_TEXT_1, SOME_TEXT_2, image, MediatorCreatingState.ENRICH);
        verify(locale).toolbarEnrichTitle();
        verify(locale).toolbarEnrichTooltip();
        verify(resources).enrichToolbar();
    }

}