/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package com.codenvy.ide.client.propertiespanel.respond;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import javax.annotation.Nullable;

/**
 * The abstract view's representation of 'Respond' mediator properties panel. It provides an ability to show all available properties of
 * the mediator and edit it.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
@ImplementedBy(RespondPropertiesPanelViewImpl.class)
public abstract class RespondPropertiesPanelView extends AbstractView<RespondPropertiesPanelView.ActionDelegate> {

    /**
     * Interface defines methods of {@link RespondPropertiesPanelPresenter} which calls from view. These methods defines
     * some actions when user changes properties of Respond mediator.
     */
    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /** Performs some actions in response to user's changing description field. */
        void onDescriptionChanged();

    }

    /** @return description value from special view's place */
    @Nullable
    public abstract String getDescription();

    /**
     * Sets description value to special place on view.
     *
     * @param description
     *         description value which need to set to special place on view
     */
    public abstract void setDescription(@Nullable String description);

}