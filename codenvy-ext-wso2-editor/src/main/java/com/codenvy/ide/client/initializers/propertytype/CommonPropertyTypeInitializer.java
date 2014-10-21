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

import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.google.inject.Inject;

import java.util.Arrays;

/**
 * @author Andrey Plotnikov
 */
public class CommonPropertyTypeInitializer extends AbstractPropertyTypeInitializer {

    public static final String BOOLEAN_TYPE_NAME = "Boolean";

    @Inject
    public CommonPropertyTypeInitializer(PropertyTypeManager manager) {
        super(manager);
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        manager.register(BOOLEAN_TYPE_NAME, Arrays.asList(Boolean.FALSE.toString(), Boolean.TRUE.toString()));
    }

}