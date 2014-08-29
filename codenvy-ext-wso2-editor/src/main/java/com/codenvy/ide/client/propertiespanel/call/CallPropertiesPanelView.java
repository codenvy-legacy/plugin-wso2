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
package com.codenvy.ide.client.propertiespanel.call;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * The abstract view's representation of 'Call' mediator properties panel. It provides an ability to show all available properties of
 * the mediator and edit it.
 *
 * @author Andrey Plotnikov
 */
@ImplementedBy(CallPropertiesPanelViewImpl.class)
public abstract class CallPropertiesPanelView extends AbstractView<CallPropertiesPanelView.ActionDelegate> {

    /**
     * Interface defines methods of {@link CallPropertiesPanelPresenter} which calls from view. These methods defines
     * some actions when user change properties of Call mediator.
     */
    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /** Performs some actions in response to user's changing endpoint type field. */
        void onEndpointTypeChanged();

        /** Performs some actions in response to user's changing endpoint register key field. */
        void onEndpointRegisterKeyChanged();

        /** Performs some actions in response to user's changing description field. */
        void onDescriptionChanged();

        /** Performs some actions in response to user's editing registry xpath. */
        void onEditRegistryXpathButtonClicked();

    }

    /** @return content of the endpoint type field */
    @Nonnull
    public abstract String getEndpointType();

    /**
     * Selects an item from list of endpoint types in the endpoint type field.
     *
     * @param endpointType
     *         new selected type
     */
    public abstract void selectEndpointType(@Nonnull String endpointType);

    /**
     * Changes content of the endpoint type field.
     *
     * @param endpointTypes
     *         new content of the field
     */
    public abstract void setEndpointTypes(@Nullable List<String> endpointTypes);

    /** @return content of the endpoint registry key field */
    @Nonnull
    public abstract String getEndpointRegistryKey();

    /**
     * Changes content of the endpoint registry key field.
     *
     * @param registryKey
     *         new content of the endpoint registry key field
     */
    public abstract void setEndpointRegistryKey(@Nonnull String registryKey);

    /**
     * Changes content of the endpoint xpath field.
     *
     * @param xpath
     *         new content of the endpoint xpath field
     */
    public abstract void setEndpointXpath(@Nullable String xpath);

    /** @return content of the description field */
    @Nonnull
    public abstract String getDescription();

    /**
     * Changes content of the description field.
     *
     * @param description
     *         new content of the description field
     */
    public abstract void setDescription(@Nonnull String description);

    /**
     * Changes visible state of the endpoint registry key panel.
     *
     * @param visible
     *         <code>true</code> the panel will be shown, <code>false</code> it will not
     */
    public abstract void setVisibleEndpointRegistryKeyPanel(boolean visible);

    /**
     * Changes visible state of the endpoint xpath panel.
     *
     * @param visible
     *         <code>true</code> the panel will be shown, <code>false</code> it will not
     */
    public abstract void setVisibleEndpointXpathPanel(boolean visible);

}