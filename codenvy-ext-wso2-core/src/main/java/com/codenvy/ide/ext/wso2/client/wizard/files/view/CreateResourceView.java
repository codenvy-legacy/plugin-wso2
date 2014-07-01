/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.ext.wso2.client.wizard.files.view;

import com.codenvy.ide.api.mvp.View;
import com.google.inject.ImplementedBy;

import javax.validation.constraints.NotNull;

/**
 * The view for creating WSO2 resources. Provides an ability to input resource name.
 *
 * @author Andrey Plotnikov
 */
@ImplementedBy(CreateResourceViewImpl.class)
public interface CreateResourceView extends View<CreateResourceView.ActionDelegate> {

    /** Required for delegating functions in the view. */
    public interface ActionDelegate {

        /** Performs some actions in response to a user's changing resource name. */
        void onValueChanged();
    }

    void setResourceNameTitle(@NotNull String title);

    @NotNull
    String getResourceName();

    void setResourceName(@NotNull String name);

}