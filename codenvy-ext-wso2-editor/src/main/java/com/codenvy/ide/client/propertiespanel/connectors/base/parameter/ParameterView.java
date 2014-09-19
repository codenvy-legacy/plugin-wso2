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
package com.codenvy.ide.client.propertiespanel.connectors.base.parameter;

import com.codenvy.ide.api.mvp.View;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;

/**
 * The abstract view's representation of the graphical part that provides an ability to show and edit new configuration parameters of
 * connector.
 *
 * @author Valeriy Svydenko
 */
@ImplementedBy(ParameterViewImpl.class)
public interface ParameterView extends View<ParameterView.ActionDelegate> {

    interface ActionDelegate {

        /** Performs any actions appropriate in response to the user having pressed the Ok button. */
        void onOkButtonClicked();

        /** Performs any actions appropriate in response to the user having pressed the Cancel button. */
        void onCancelButtonClicked();

        /** Performs any actions appropriate in response to the user having pressed the username button. */
        void onUsernameBtnClicked();

        /** Performs any actions appropriate in response to the user having changed an username type field. */
        void onUsernameTypeChanged();

        /** Performs any actions appropriate in response to the user having pressed the password button. */
        void onPasswordBtnClicked();

        /** Performs any actions appropriate in response to the user having changed an password type field. */
        void onPasswordTypeChanged();

        /** Performs any actions appropriate in response to the user having pressed the login url button. */
        void onLoginUrlBtnClicked();

        /** Performs any actions appropriate in response to the user having changed an login url type field. */
        void onLoginUrlTypeChanged();

        /** Performs any actions appropriate in response to the user having pressed the force login button. */
        void onForceLoginBtnClicked();

        /** Performs any actions appropriate in response to the user having changed an force login type field. */
        void onForceLoginTypeChanged();
    }

    /** Displays the view. */
    void showDialog();

    /** Hides the view. */
    void hideDialog();

    /**
     * Set name of parameter into place on view.
     *
     * @param name
     *         name of parameter.
     */
    void setName(@Nonnull String name);

    /** @return name of parameter. */
    @Nonnull
    String getName();

    /** @return username parameter type. */
    @Nonnull
    String getUsernameType();

    /**
     * Select username parameter type in place on view.
     *
     * @param parameterType
     *         username parameter type.
     */
    void selectUsernameType(@Nonnull String parameterType);

    /**
     * Sets visible panel for adding name spaces on view.
     *
     * @param isVisible
     *         <code>true</code> to show panel, <code>false</code> not to show
     */
    void setUsernamePanelVisible(boolean isVisible);

    /**
     * Set username value into place on view.
     *
     * @param value
     *         name of parameter.
     */
    void setUsernameValue(@Nonnull String value);

    /**
     * Set username namespace value into place on view.
     *
     * @param value
     *         name of parameter.
     */
    void setUsernameNamespaceValue(@Nonnull String value);

    /** @return loginUrl parameter type. */
    @Nonnull
    String getLoginUrlType();

    /**
     * Select loginUrl parameter type in place on view.
     *
     * @param parameterType
     *         password parameter type.
     */
    void selectLoginUrlType(@Nonnull String parameterType);

    /**
     * Sets visible panel for adding name spaces on view for the loginUrl field.
     *
     * @param isVisible
     *         <code>true</code> to show panel, <code>false</code> not to show
     */
    void setLoginUrlPanelVisible(boolean isVisible);

    /**
     * Set loginUrl value into place on view.
     *
     * @param value
     *         name of parameter.
     */
    void setLoginUrlValue(@Nonnull String value);

    /**
     * Set loginUrl namespace value into place on view.
     *
     * @param value
     *         name of parameter.
     */
    void setLoginUrlNamespaceValue(@Nonnull String value);

    /** @return password parameter type. */
    @Nonnull
    String getPasswordType();

    /**
     * Select password parameter type in place on view.
     *
     * @param parameterType
     *         password parameter type.
     */
    void selectPasswordType(@Nonnull String parameterType);

    /**
     * Sets visible panel for adding name spaces on view for the password field.
     *
     * @param isVisible
     *         <code>true</code> to show panel, <code>false</code> not to show
     */
    void setPasswordPanelVisible(boolean isVisible);

    /**
     * Set password value into place on view.
     *
     * @param value
     *         name of parameter.
     */
    void setPasswordValue(@Nonnull String value);

    /**
     * Set password namespace value into place on view.
     *
     * @param value
     *         name of parameter.
     */
    void setPasswordNamespaceValue(@Nonnull String value);

    /** @return forceLogin parameter type. */
    @Nonnull
    String getForceLoginType();

    /**
     * Select forceLogin parameter type in place on view.
     *
     * @param parameterType
     *         forceLogin parameter type.
     */
    void selectForceLoginType(@Nonnull String parameterType);

    /**
     * Sets visible panel for adding name spaces on view for the forceLogin field.
     *
     * @param isVisible
     *         <code>true</code> to show panel, <code>false</code> not to show
     */
    void setForceLoginPanelVisible(boolean isVisible);

    /**
     * Set forceLogin value into place on view.
     *
     * @param value
     *         name of parameter.
     */
    void setForceLoginValue(@Nonnull String value);

    /**
     * Set forceLogin namespace value into place on view.
     *
     * @param value
     *         name of parameter.
     */
    void setForceLoginNamespaceValue(@Nonnull String value);

}