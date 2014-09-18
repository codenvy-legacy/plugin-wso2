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
import com.google.inject.Inject;

/**
 * @author Andrey Plotnikov
 */
public class EndpointToolbarInitializer extends AbstractToolbarInitializer {

    @Inject
    public EndpointToolbarInitializer(ToolbarPresenter toolbar, EditorResources resources, WSO2EditorLocalizationConstant locale) {
        super(toolbar, resources, locale);
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        toolbar.addGroup(ToolbarGroupIds.ENDPOINTS, locale.toolbarGroupEndpoints());

        toolbar.addItem(ToolbarGroupIds.ENDPOINTS,
                        locale.toolbarAddressEndpointTitle(),
                        locale.toolbarAddressEndpointTooltip(),
                        resources.addressToolbar(),
                        EndpointCreatingState.ADDRESS);
    }

}
