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
package com.codenvy.ide.client.propertiespanel.connectors.salesforce.init;

import com.codenvy.ide.client.mvp.AbstractView;
import com.codenvy.ide.client.propertiespanel.connectors.base.BaseConnectorPanelPresenter;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.codenvy.ide.client.elements.connectors.salesforce.BaseSalesforce.ParameterEditorType;

/**
 * The view of {@link InitPropertiesPanelPresenter}
 *
 * @author Valeriy Svydenko
 */
@ImplementedBy(InitPropertiesPanelViewImpl.class)
public abstract class InitPropertiesPanelView extends AbstractView<InitPropertiesPanelView.ActionDelegate> {

    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /** Performs any actions appropriate in response to the user having changed username on properties panel. */
        void onUsernameChanged();

        /** Performs any actions appropriate in response to the user having changed password on properties panel. */
        void onPasswordChanged();

        /** Performs any actions appropriate in response to the user having changed login url on properties panel. */
        void onLoginUrlChanged();

        /** Performs any actions appropriate in response to the user having changed force login on properties panel. */
        void onForceLoginChanged();

        /** Performs any actions appropriate in response to the user having clicked username button on properties panel. */
        void usernameButtonClicked();

        /** Performs any actions appropriate in response to the user having clicked password button on properties panel. */
        void passwordButtonClicked();

        /** Performs any actions appropriate in response to the user having clicked forceLogin button on properties panel. */
        void forceLoginButtonClicked();

        /** Performs any actions appropriate in response to the user having clicked loginUrl button on properties panel. */
        void loginUrlButtonClicked();

    }

    /** @return force login value from the special place on the view which uses for showing forceLogin parameter */
    @Nonnull
    public abstract String getForceLogin();

    /**
     * Sets force login value to the special place on the view which uses for showing forceLogin parameter.
     *
     * @param forceLogin
     *         value of forceLogin parameter
     */
    public abstract void setForceLogin(@Nonnull String forceLogin);

    /** @return login URL value from the special place on the view which uses for showing loginUrl parameter */
    @Nonnull
    public abstract String getLoginUrl();

    /**
     * Sets force login URL to the special place on the view which uses for showing forceUrl parameter.
     *
     * @param loginUrl
     *         value of loginUrl parameter
     */
    public abstract void setLoginUrl(@Nonnull String loginUrl);

    /** @return password value from the special place on the view which uses for showing password parameter */
    @Nonnull
    public abstract String getPassword();

    /**
     * Sets password value to the special place on the view which uses for showing password parameter.
     *
     * @param password
     *         value of password parameter
     */
    public abstract void setPassword(@Nonnull String password);

    /** @return username value from the special place on the view which uses for showing username parameter */
    @Nonnull
    public abstract String getUsername();

    /**
     * Sets username value to the special place on the view which uses for showing username parameter.
     *
     * @param username
     *         value of username
     */
    public abstract void setUsername(@Nonnull String username);

    /**
     * Sets force login value to the special place on the view which uses for showing namespace forceLogin parameter.
     *
     * @param forceLogin
     *         value of forceLogin parameter
     */
    public abstract void setForceLoginNamespace(@Nullable String forceLogin);

    /**
     * Sets force login URL to the special place on the view which uses for showing namespace forceUrl parameter.
     *
     * @param loginUrl
     *         value of loginUrl parameter
     */
    public abstract void setLoginUrlNamespace(@Nullable String loginUrl);

    /**
     * Sets password value to the special place on the view which uses for showing namespace password parameter.
     *
     * @param password
     *         value of password parameter
     */
    public abstract void setPasswordNamespace(@Nullable String password);

    /**
     * Sets username value to the special place on the view which uses for showing namespace username parameter.
     *
     * @param username
     *         value of username
     */
    public abstract void setUsernameNamespace(@Nullable String username);

    /**
     * Adds base elements of connector's property panel.
     *
     * @param base
     *         presenter of base connector
     */
    public abstract void addBaseConnector(@Nonnull BaseConnectorPanelPresenter base);

    /**
     * Displays properties panel to a certain value of parameter editor type.
     *
     * @param parameterEditorType
     *         value of parameter editor type that was selected
     */
    public abstract void onParameterEditorTypeChanged(@Nonnull ParameterEditorType parameterEditorType);

}
