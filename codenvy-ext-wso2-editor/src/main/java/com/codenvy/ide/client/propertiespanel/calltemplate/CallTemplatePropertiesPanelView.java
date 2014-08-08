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
package com.codenvy.ide.client.propertiespanel.calltemplate;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * The view of {@link CallTemplatePropertiesPanelPresenter}
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
@ImplementedBy(CallTemplatePropertiesPanelViewImpl.class)
public abstract class CallTemplatePropertiesPanelView extends AbstractView<CallTemplatePropertiesPanelView.ActionDelegate> {

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
    @NotNull
    public abstract String getAvailableTemplates();

    /**
     * Sets available template value to the special place on the view.
     *
     * @param availableTemplates
     *         values of available template
     */
    public abstract void setAvailableTemplates(@NotNull List<String> availableTemplates);

    /**
     * Select available template in place on view.
     *
     * @param availableTemplate
     *         available template value
     */
    public abstract void selectAvailableTemplate(@NotNull String availableTemplate);

    /** @return target template value from the special place on the view */
    @NotNull
    public abstract String getTargetTemplate();

    /**
     * Sets target template value to the special place on the view.
     *
     * @param targetTemplate
     *         values of target template
     */
    public abstract void setTargetTemplate(String targetTemplate);

    /** @return description value from the special place on the view */
    @NotNull
    public abstract String getDescription();

    /**
     * Sets parameters value to the special place on the view.
     *
     * @param parameter
     *         value of parameter property
     */
    public abstract void setParameters(@NotNull String parameter);

    /**
     * Sets description value to the special place on the view.
     *
     * @param description
     *         description of CallTemplate mediator.
     */
    public abstract void setDescription(@NotNull String description);

}