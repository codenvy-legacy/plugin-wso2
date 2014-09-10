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
package com.codenvy.ide.ext.wso2.server.inject;

import com.codenvy.ide.ext.wso2.server.projecttypes.WSO2ProjectTypeDescriptionExtension;
import com.codenvy.ide.ext.wso2.server.projecttypes.WSO2ProjectTypeExtension;
import com.codenvy.ide.ext.wso2.server.rest.WSO2RestService;
import com.codenvy.inject.DynaModule;
import com.google.inject.AbstractModule;

/**
 * The module that contains configuration of the server side part of the plugin.
 *
 * @author Andrey Plotnikov
 */
@DynaModule
public class WSO2Module extends AbstractModule {

    /** {@inheritDoc} */
    @Override
    protected void configure() {
        bind(WSO2ProjectTypeDescriptionExtension.class);
        bind(WSO2ProjectTypeExtension.class);

        bind(WSO2RestService.class);
    }

}