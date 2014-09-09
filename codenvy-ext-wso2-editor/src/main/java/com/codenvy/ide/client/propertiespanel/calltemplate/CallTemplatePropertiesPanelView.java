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
package com.codenvy.ide.client.propertiespanel.calltemplate;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * The abstract view's representation of 'CallTemplate' mediator properties panel. It provides an ability to show all available properties
 * of the mediator and edit it.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
@ImplementedBy(CallTemplatePropertiesPanelViewImpl.class)
public abstract class CallTemplatePropertiesPanelView extends AbstractView<CallTemplatePropertiesPanelView.ActionDelegate> {

    /**
     * Interface defines methods of {@link CallTemplatePropertiesPanelPresenter} which calls from view. These methods defines
     * some actions when user change properties of CallTemplate mediator.
     */
    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /** Performs any actions appropriate in response when available template field changed. */
        void onAvailableTemplatesChanged();

        /** Performs any actions appropriate in response when target template field changed. */
        void onTargetTemplateChanged();

        /** Performs any actions appropriate in response when description field changed. */
        void onDescriptionChanged();

        /** Performs any actions appropriate in response to the user click edit parameter button. */
        void onParameterButtonClicked();
    }

    /** @return available template value from the special place on the view */
    @Nonnull
    public abstract String getAvailableTemplates();

    /**
     * Sets available template value to the special place on the view.
     *
     * @param availableTemplates
     *         values of available template
     */
    public abstract void setAvailableTemplates(@Nonnull List<String> availableTemplates);

    /**
     * Select available template in place on view.
     *
     * @param availableTemplate
     *         available template value
     */
    public abstract void selectAvailableTemplate(@Nonnull String availableTemplate);

    /** @return target template value from the special place on the view */
    @Nonnull
    public abstract String getTargetTemplate();

    /**
     * Sets target template value to the special place on the view.
     *
     * @param targetTemplate
     *         values of target template
     */
    public abstract void setTargetTemplate(@Nonnull String targetTemplate);

    /** @return description value from the special place on the view */
    @Nonnull
    public abstract String getDescription();

    /**
     * Sets parameters value to the special place on the view.
     *
     * @param parameter
     *         value of parameter property
     */
    public abstract void setParameters(@Nonnull String parameter);

    /**
     * Sets description value to the special place on the view.
     *
     * @param description
     *         description of CallTemplate mediator.
     */
    public abstract void setDescription(@Nullable String description);

}