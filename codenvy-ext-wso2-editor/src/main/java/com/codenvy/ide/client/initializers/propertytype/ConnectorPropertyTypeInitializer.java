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

import com.codenvy.ide.client.initializers.Initializer;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.google.inject.Inject;

import java.util.Arrays;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.AvailableConfigs;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType;

/**
 * @author Andrey Plotnikov
 */
public class ConnectorPropertyTypeInitializer implements Initializer {

    private final PropertyTypeManager propertyTypeManager;

    @Inject
    public ConnectorPropertyTypeInitializer(PropertyTypeManager propertyTypeManager) {
        this.propertyTypeManager = propertyTypeManager;
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        propertyTypeManager.register(ParameterEditorType.TYPE_NAME, Arrays.asList(ParameterEditorType.Inline.name(),
                                                                                  ParameterEditorType.NamespacedPropertyEditor.name()));

        propertyTypeManager.register(AvailableConfigs.TYPE_NAME, Arrays.asList(AvailableConfigs.EMPTY.getValue(),
                                                                               AvailableConfigs.SELECT_FROM_CONFIG.getValue()));
    }

}