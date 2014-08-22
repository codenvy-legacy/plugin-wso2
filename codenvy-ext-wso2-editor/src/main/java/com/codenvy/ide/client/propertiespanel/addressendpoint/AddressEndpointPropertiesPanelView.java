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
package com.codenvy.ide.client.propertiespanel.addressendpoint;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * The abstract view's representation of 'Address' endpoint properties panel. It provides an ability to show all available properties of
 * the endpoint and edit it.
 *
 * @author Andrey Plotnikov
 */
@ImplementedBy(AddressEndpointPropertiesPanelViewImpl.class)
public abstract class AddressEndpointPropertiesPanelView extends AbstractView<AddressEndpointPropertiesPanelView.ActionDelegate> {

    /**
     * Changes content of the format field.
     *
     * @param formats
     *         new content of the field
     */
    public abstract void setFormats(@Nonnull List<String> formats);

    /**
     * Selects an item from list of formats in the format field.
     *
     * @param format
     *         new selected format
     */
    public abstract void selectFormat(@Nonnull String format);

    /** @return content of the format field */
    @Nonnull
    public abstract String getFormat();

    /**
     * Changes content of the uri field.
     *
     * @param uri
     *         new content of the field
     */
    public abstract void setUri(@Nonnull String uri);

    /** @return content of the uri field */
    @Nonnull
    public abstract String getUri();

    /**
     * Changes content of the suspend error codes field.
     *
     * @param suspendErrorCodes
     *         new content of the field
     */
    public abstract void setSuspendErrorCodes(@Nonnull String suspendErrorCodes);

    /** @return content of the suspend error codes field */
    @Nonnull
    public abstract String getSuspendErrorCodes();

    /**
     * Changes content of the suspend initial duration field.
     *
     * @param suspendInitialDuration
     *         new content of the field
     */
    public abstract void setSuspendInitialDuration(@Nonnull String suspendInitialDuration);

    /** @return content of the suspend initial duration field */
    @Nonnull
    public abstract String getSuspendInitialDuration();

    /**
     * Changes content of the suspend maximum duration field.
     *
     * @param suspendMaximumDuration
     *         new content of the field
     */
    public abstract void setSuspendMaximumDuration(@Nonnull String suspendMaximumDuration);

    /** @return content of the suspend maximum duration field */
    @Nonnull
    public abstract String getSuspendMaximumDuration();

    /**
     * Changes content of the suspend progression factory field.
     *
     * @param suspendProgressionFactory
     *         new content of the field
     */
    public abstract void setSuspendProgressionFactory(@Nonnull String suspendProgressionFactory);

    /** @return content of the suspend progression factory field */
    @Nonnull
    public abstract String getSuspendProgressionFactory();

    /**
     * Changes content of the retry error codes field.
     *
     * @param retryErrorCodes
     *         new content of the field
     */
    public abstract void setRetryErrorCodes(@Nonnull String retryErrorCodes);

    /** @return content of the retry error codes field */
    @Nonnull
    public abstract String getRetryErrorCodes();

    /**
     * Changes content of the retry count field.
     *
     * @param retryCount
     *         new content of the field
     */
    public abstract void setRetryCount(@Nonnull String retryCount);

    /** @return content of the retry count field */
    @Nonnull
    public abstract String getRetryCount();

    /**
     * Changes content of the retry delay field.
     *
     * @param retryDelay
     *         new content of the field
     */
    public abstract void setRetryDelay(@Nonnull String retryDelay);

    /** @return content of the retry delay field */
    @Nonnull
    public abstract String getRetryDelay();

    /**
     * Changes content of the properties field.
     *
     * @param properties
     *         new content of the field
     */
    public abstract void setProperties(@Nonnull String properties);

    /**
     * Changes content of the optimize field.
     *
     * @param optimizes
     *         new content of the field
     */
    public abstract void setOptimizes(@Nonnull List<String> optimizes);

    /**
     * Selects an item from list of formats in the optimize field.
     *
     * @param optimize
     *         new selected format
     */
    public abstract void selectOptimize(@Nonnull String optimize);

    /** @return content of the optimize field */
    @Nonnull
    public abstract String getOptimize();

    /**
     * Changes content of the description field.
     *
     * @param description
     *         new content of the field
     */
    public abstract void setDescription(@Nonnull String description);

    /** @return content of the description field */
    @Nonnull
    public abstract String getDescription();

    /**
     * Changes content of the reliable messaging enabled field.
     *
     * @param states
     *         new content of the field
     */
    public abstract void setReliableMessagingEnabledStates(@Nonnull List<String> states);

    /**
     * Selects an item from list of formats in the reliable messaging enabled field.
     *
     * @param state
     *         new selected format
     */
    public abstract void selectReliableMessagingEnabledState(@Nonnull String state);

    /** @return content of the reliable messaging enabled field */
    @Nonnull
    public abstract String getReliableMessagingEnabled();

    /**
     * Changes visible state of the reliable messaging enabled panel.
     *
     * @param visible
     *         <code>true</code> the panel will be shown, <code>false</code> it will not
     */
    public abstract void setVisibleReliableMessagingEnabledPanel(boolean visible);

    /**
     * Changes content of the reliable messaging policy field.
     *
     * @param reliableMessagingPolicy
     *         new content of the field
     */
    public abstract void setReliableMessagingPolicy(@Nonnull String reliableMessagingPolicy);

    /** @return content of the reliable messaging policy field */
    @Nonnull
    public abstract String getReliableMessagingPolicy();

    /**
     * Changes content of the security enabled field.
     *
     * @param states
     *         new content of the field
     */
    public abstract void setSecurityEnabledStates(@Nonnull List<String> states);

    /**
     * Selects an item from list of formats in the security enabled field.
     *
     * @param state
     *         new selected format
     */
    public abstract void selectSecurityEnabledState(@Nonnull String state);

    /** @return content of the security enabled field */
    @Nonnull
    public abstract String getSecurityEnabled();

    /**
     * Changes visible state of the security enabled panel.
     *
     * @param visible
     *         <code>true</code> the panel will be shown, <code>false</code> it will not
     */
    public abstract void setVisibleSecurityPolicyPanel(boolean visible);

    /**
     * Changes content of the security policy field.
     *
     * @param securityPolicy
     *         new content of the field
     */
    public abstract void setSecurityPolicy(@Nonnull String securityPolicy);

    /** @return content of the security policy field */
    @Nonnull
    public abstract String getSecurityPolicy();

    /**
     * Changes content of the addressing enabled field.
     *
     * @param states
     *         new content of the field
     */
    public abstract void setAddressingEnabledStates(@Nonnull List<String> states);

    /**
     * Selects an item from list of formats in the addressing enabled field.
     *
     * @param state
     *         new selected format
     */
    public abstract void selectAddressingEnabledState(@Nonnull String state);

    /** @return content of the addressing enabled field */
    @Nonnull
    public abstract String getAddressingEnabled();

    /**
     * Changes visible state of the visible addressing panel.
     *
     * @param visible
     *         <code>true</code> the panel will be shown, <code>false</code> it will not
     */
    public abstract void setVisibleAddressingPanel(boolean visible);

    /**
     * Changes content of the addressing version field.
     *
     * @param addressingVersions
     *         new content of the field
     */
    public abstract void setAddressingVersions(@Nonnull List<String> addressingVersions);

    /**
     * Selects an item from list of formats in the addressing version field.
     *
     * @param addressingVersion
     *         new selected format
     */
    public abstract void selectAddressingVersion(@Nonnull String addressingVersion);

    /** @return content of the addressing version field */
    @Nonnull
    public abstract String getAddressingVersion();

    /**
     * Changes content of the addressing separator listener field.
     *
     * @param states
     *         new content of the field
     */
    public abstract void setAddressingSeparatorListenerStates(@Nonnull List<String> states);

    /**
     * Selects an item from list of formats in the addressing separator listener field.
     *
     * @param state
     *         new selected format
     */
    public abstract void selectAddressingSeparatorListenerState(@Nonnull String state);

    /** @return content of the addressing separator listener field */
    @Nonnull
    public abstract String getAddressingSeparatorListener();

    /**
     * Changes content of the timeout duration field.
     *
     * @param timeoutDuration
     *         new content of the field
     */
    public abstract void setTimeoutDuration(@Nonnull String timeoutDuration);

    /** @return content of the timeout duration field */
    @Nonnull
    public abstract String getTimeoutDuration();

    /**
     * Changes content of the timeout action field.
     *
     * @param actions
     *         new content of the field
     */
    public abstract void setTimeoutActions(@Nonnull List<String> actions);

    /**
     * Selects an item from list of formats in the timeout action field.
     *
     * @param action
     *         new selected format
     */
    public abstract void selectTimeoutAction(@Nonnull String action);

    /** @return content of the timeout action field */
    @Nonnull
    public abstract String getTimeoutAction();

    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /** Performs some actions in response to user's changing format field. */
        void onFormatChanged();

        /** Performs some actions in response to user's changing uri field. */
        void onUriChanged();

        /** Performs some actions in response to user's changing suspend error codes field. */
        void onSuspendErrorCodesChanged();

        /** Performs some actions in response to user's changing suspend initial duration field. */
        void onSuspendInitialDurationChanged();

        /** Performs some actions in response to user's changing suspend maximum duration field. */
        void onSuspendMaximumDurationChanged();

        /** Performs some actions in response to user's changing suspend progression factory field. */
        void onSuspendProgressionFactoryChanged();

        /** Performs some actions in response to user's changing retry error codes field. */
        void onRetryErrorCodesChanged();

        /** Performs some actions in response to user's changing retry count field. */
        void onRetryCountChanged();

        /** Performs some actions in response to user's changing retry delay field. */
        void onRetryDelayChanged();

        /** Performs some actions in response to user's editing 'Properties' field. */
        void onEditPropertiesButtonClicked();

        /** Performs some actions in response to user's changing optimize field. */
        void onOptimizeChanged();

        /** Performs some actions in response to user's changing description field. */
        void onDescriptionChanged();

        /** Performs some actions in response to user's changing reliable messaging enabled field. */
        void onReliableMessagingEnabledChanged();

        /** Performs some actions in response to user's changing reliable messaging policy field. */
        void onReliableMessagingPolicyChanged();

        /** Performs some actions in response to user's changing security enabled field. */
        void onSecurityEnabledChanged();

        /** Performs some actions in response to user's changing security policy field. */
        void onSecurityPolicyChanged();

        /** Performs some actions in response to user's changing addressing enabled field. */
        void onAddressingEnabledChanged();

        /** Performs some actions in response to user's changing addressing version field. */
        void onAddressingVersionChanged();

        /** Performs some actions in response to user's changing addressing separator listener field. */
        void onAddressingSeparatorListenerChanged();

        /** Performs some actions in response to user's changing addressing separator listener field. */
        void onTimeoutDurationChanged();

        /** Performs some actions in response to user's changing addressing separator listener field. */
        void onTimeoutActionChanged();

    }

}