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
import com.codenvy.ide.client.initializers.Initializer;
import com.codenvy.ide.client.toolbar.ToolbarPresenter;

import javax.annotation.Nonnull;

/**
 * @author Andrey Plotnikov
 */
public abstract class AbstractToolbarInitializer implements Initializer {

    protected final ToolbarPresenter               toolbar;
    protected final EditorResources                resources;
    protected final WSO2EditorLocalizationConstant locale;

    protected AbstractToolbarInitializer(@Nonnull ToolbarPresenter toolbar,
                                         @Nonnull EditorResources resources,
                                         @Nonnull WSO2EditorLocalizationConstant locale) {
        this.toolbar = toolbar;
        this.resources = resources;
        this.locale = locale;
    }

}