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
package com.codenvy.ide.client.propertiespanel.property.complex;

import com.codenvy.ide.api.mvp.View;
import com.google.inject.ImplementedBy;

import javax.annotation.Nullable;

/**
 * The abstract representation of graphical part of the complex property. It provides an ability to change property title and property
 * value.
 *
 * @author Andrey Plotnikov
 */
@ImplementedBy(ComplexPropertyViewImpl.class)
public interface ComplexPropertyView extends View<ComplexPropertyView.ActionDelegate> {

    /**
     * Changes title of property on the view.
     *
     * @param title
     *         title that needs to be changed
     */
    void setTitle(@Nullable String title);

    /**
     * Changes property value on the view.
     *
     * @param property
     *         property value that need to be set
     */
    void setProperty(@Nullable String property);

    /**
     * Changes visible state of the main panel.
     *
     * @param visible
     *         <code>true</code> the panel will be shown, <code>false</code> it will not
     */
    void setVisible(boolean visible);

    public interface ActionDelegate {
        /** Performs some actions in response to a user's clicking on the 'Edit' button. */
        void onEditButtonClicked();
    }

}