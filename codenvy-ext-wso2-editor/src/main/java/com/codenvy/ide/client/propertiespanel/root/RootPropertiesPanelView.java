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
package com.codenvy.ide.client.propertiespanel.root;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;

/**
 * The abstract view's representation of root element properties panel. It provides an ability to show all available properties of
 * the mediator and edit it.
 *
 * @author Andrey Plotnikov
 */
@ImplementedBy(RootPropertiesPanelViewImpl.class)
public abstract class RootPropertiesPanelView extends AbstractView<RootPropertiesPanelView.ActionDelegate> {

    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /** Performs any actions appropriate in response to the user having changed name field on properties panel. */
        void onNameChanged();

        /** Performs any actions appropriate in response to the user having changed onError field on properties panel. */
        void onOnErrorChanged();

    }

    /** @return content of the name field */
    @Nonnull
    public abstract String getName();

    /**
     * Changes content of the name field.
     *
     * @param name
     *         new content of the field
     */
    public abstract void setName(@Nonnull String name);

    /** @return content of the name field */
    @Nonnull
    public abstract String getOnError();

    /**
     * Changes content of the onError field.
     *
     * @param onError
     *         new content of the field
     */
    public abstract void setOnError(@Nonnull String onError);

}