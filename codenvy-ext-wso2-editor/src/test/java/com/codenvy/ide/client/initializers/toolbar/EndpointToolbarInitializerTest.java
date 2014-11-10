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
import com.codenvy.ide.client.constants.EndpointCreatingState;
import com.codenvy.ide.client.constants.ToolbarGroupIds;
import com.codenvy.ide.client.toolbar.ToolbarPresenter;
import com.google.gwt.resources.client.ImageResource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public class EndpointToolbarInitializerTest {

    private static final String SOME_TEXT_1 = "some text1";
    private static final String SOME_TEXT_2 = "some text2";

    @Mock
    private ToolbarPresenter               toolbar;
    @Mock
    private EditorResources                resources;
    @Mock
    private WSO2EditorLocalizationConstant locale;

    @InjectMocks
    private EndpointToolbarInitializer initializer;

    @Test
    public void endpointToolbarGroupShouldBeInitialized() throws Exception {
        when(locale.toolbarGroupEndpoints()).thenReturn(SOME_TEXT_1);

        initializer.initialize();

        verify(toolbar).addGroup(ToolbarGroupIds.ENDPOINTS, SOME_TEXT_1);
        verify(locale).toolbarGroupEndpoints();
    }

    @Test
    public void endpointToolbarItemShouldBeInitialized() throws Exception {
        ImageResource image = mock(ImageResource.class);
        when(locale.toolbarAddressEndpointTitle()).thenReturn(SOME_TEXT_1);
        when(locale.toolbarAddressEndpointTooltip()).thenReturn(SOME_TEXT_2);
        when(resources.addressToolbar()).thenReturn(image);

        initializer.initialize();

        verify(toolbar).addItem(ToolbarGroupIds.ENDPOINTS, SOME_TEXT_1, SOME_TEXT_2, image, EndpointCreatingState.ADDRESS);
        verify(locale).toolbarAddressEndpointTitle();
        verify(locale).toolbarAddressEndpointTooltip();
        verify(resources).addressToolbar();
    }

}