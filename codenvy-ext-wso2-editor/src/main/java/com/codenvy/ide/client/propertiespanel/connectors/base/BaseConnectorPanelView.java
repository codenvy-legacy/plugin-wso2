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
package com.codenvy.ide.client.propertiespanel.connectors.base;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import java.util.List;

import static com.codenvy.ide.client.elements.connectors.salesforce.GeneralProperty.ParameterEditorType;

/**
 * The view of {@link BaseConnectorPanelPresenter}
 *
 * @author Valeriy Svydenko
 */
@ImplementedBy(BaseConnectorPanelViewImpl.class)
public abstract class BaseConnectorPanelView extends AbstractView<BaseConnectorPanelView.ActionDelegate> {

    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /** Performs any actions appropriate in response to the user having changed parameter editor type field. */
        void onParameterEditorTypeChanged();

        /** Performs any actions appropriate in response to the user having changed available configs field. */
        void onAvailableConfigsChanged();

        /**
         * Performs any actions appropriate in response to the user having changed configRef field.
         */
        void onConfigRefChanged();

        /** Performs any actions appropriate in response to the showing connector configuration parameter window. */
        void showConfigParameterWindow();

    }

    /** @return parameter editor type value from the special place on the view */
    @Nonnull
    public abstract String getParameterEditorType();

    /** @return parameter available config from the special place on the view */
    @Nonnull
    public abstract String getAvailableConfig();

    /**
     * Select parameter editor type in place on view.
     *
     * @param state
     *         value of parameter editor type
     */
    public abstract void selectParameterEditorType(@Nonnull String state);

    /**
     * Set parameter editor types value.
     *
     * @param states
     *         values of parameter editor type
     */
    public abstract void setParameterEditorType(@Nonnull List<String> states);

    /**
     * Sets new config value to the special place on the view which uses for showing NewConfig parameter.
     *
     * @param newConfig
     *         value of new config
     */
    public abstract void setNewConfig(@Nonnull String newConfig);

    /**
     * Select available configs in place on view.
     *
     * @param state
     *         value of available configs
     */
    public abstract void selectAvailableConfigs(@Nonnull String state);

    /**
     * Sets available configs to the special place on the view which uses for showing Available Config parameter.
     *
     * @param configs
     *         all available configs
     */
    public abstract void setAvailableConfigs(@Nonnull List<String> configs);

    /**
     * Add available config.
     *
     * @param state
     *         value of available config
     */
    public abstract void addAvailableConfigs(@Nonnull String state);

    /** @return configuration reference value from the special place on the view which uses for showing configRef parameter */
    @Nonnull
    public abstract String getConfigRef();

    /**
     * Sets configuration reference value to the special place on the view which uses for showing password parameter.
     *
     * @param configRef
     *         value of configuration reference
     */
    public abstract void setConfigRef(@Nonnull String configRef);

    /**
     * Select parameter editor type to the special place on the view.
     *
     * @param parameterEditorType
     *         parameter value
     */
    public abstract void selectParameterEditorType(@Nonnull ParameterEditorType parameterEditorType);

}
