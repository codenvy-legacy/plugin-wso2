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

import javax.annotation.Nonnull;

/**
 * The abstract view's representation of dialog window for creating resource.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
@ImplementedBy(CreateResourceViewImpl.class)
public interface CreateResourceView extends View<CreateResourceView.ActionDelegate> {

    /** Required for delegating functions in the view. */
    public interface ActionDelegate {

        /** Performs some actions in response to a user's changing resource name. */
        void onValueChanged();
    }

    /**
     * Sets title of dialog window for creating resource.
     *
     * @param title
     *         title which need to set
     */
    void setResourceNameTitle(@Nonnull String title);

    /** @return resource name from special view's name */
    @Nonnull
    String getResourceName();

    /**
     * Sets resource name to special place on view.
     *
     * @param name
     *         value of name which need to set
     */
    void setResourceName(@Nonnull String name);

}