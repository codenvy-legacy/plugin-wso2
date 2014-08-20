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
 * @author Andrey Plotnikov
 */
@ImplementedBy(AddressEndpointPropertiesPanelViewImpl.class)
public abstract class AddressEndpointPropertiesPanelView extends AbstractView<AddressEndpointPropertiesPanelView.ActionDelegate> {

    public abstract void setFormats(@Nonnull List<String> formats);

    public abstract void selectFormat(@Nonnull String format);

    @Nonnull
    public abstract String getFormat();

    public abstract void setUri(@Nonnull String uri);

    @Nonnull
    public abstract String getUri();

    public abstract void setSuspendErrorCodes(@Nonnull String suspendErrorCodes);

    @Nonnull
    public abstract String getSuspendErrorCodes();

    public abstract void setSuspendInitialDuration(@Nonnull String suspendInitialDuration);

    @Nonnull
    public abstract String getSuspendInitialDuration();

    public abstract void setSuspendMaximumDuration(@Nonnull String suspendMaximumDuration);

    @Nonnull
    public abstract String getSuspendMaximumDuration();

    public abstract void setSuspendProgressionFactory(@Nonnull String suspendProgressionFactory);

    @Nonnull
    public abstract String getSuspendProgressionFactory();

    public abstract void setRetryErrorCodes(@Nonnull String retryErrorCodes);

    @Nonnull
    public abstract String getRetryErrorCodes();

    public abstract void setRetryCount(@Nonnull String retryCount);

    @Nonnull
    public abstract String getRetryCount();

    public abstract void setRetryDelay(@Nonnull String retryDelay);

    @Nonnull
    public abstract String getRetryDelay();

    public abstract void setProperties(@Nonnull String properties);

    public abstract void setOptimizes(@Nonnull List<String> optimizes);

    public abstract void selectOptimize(@Nonnull String optimize);

    @Nonnull
    public abstract String getOptimize();

    public abstract void setDescription(@Nonnull String description);

    @Nonnull
    public abstract String getDescription();

    public abstract void setReliableMessagingEnabledStates(@Nonnull List<String> states);

    public abstract void selectReliableMessagingEnabledState(@Nonnull String state);

    @Nonnull
    public abstract String getReliableMessagingEnabled();

    public abstract void setVisibleReliableMessagingEnabledPanel(boolean visible);

    public abstract void setReliableMessagingPolicy(@Nonnull String reliableMessagingPolicy);

    @Nonnull
    public abstract String getReliableMessagingPolicy();

    public abstract void setSecurityEnabledStates(@Nonnull List<String> states);

    public abstract void selectSecurityEnabledState(@Nonnull String state);

    @Nonnull
    public abstract String getSecurityEnabled();

    public abstract void setVisibleSecurityPolicyPanel(boolean visible);

    public abstract void setSecurityPolicy(@Nonnull String securityPolicy);

    @Nonnull
    public abstract String getSecurityPolicy();

    public abstract void setAddressingEnabledStates(@Nonnull List<String> states);

    public abstract void selectAddressingEnabledState(@Nonnull String state);

    @Nonnull
    public abstract String getAddressingEnabled();

    public abstract void setVisibleAddressingPanel(boolean visible);

    public abstract void setAddressingVersions(@Nonnull List<String> addressingVersions);

    public abstract void selectAddressingVersion(@Nonnull String addressingVersion);

    @Nonnull
    public abstract String getAddressingVersion();

    public abstract void setAddressingSeparatorListenerStates(@Nonnull List<String> states);

    public abstract void selectAddressingSeparatorListenerState(@Nonnull String state);

    @Nonnull
    public abstract String getAddressingSeparatorListener();

    public abstract void setTimeoutDuration(@Nonnull String timeoutDuration);

    @Nonnull
    public abstract String getTimeoutDuration();

    public abstract void setTimeoutActions(@Nonnull List<String> actions);

    public abstract void selectTimeoutAction(@Nonnull String action);

    @Nonnull
    public abstract String getTimeoutAction();

    public interface ActionDelegate extends AbstractView.ActionDelegate {

        void onFormatChanged();

        void onUriChanged();

        void onSuspendErrorCodesChanged();

        void onSuspendInitialDuration();

        void onSuspendMaximumDuration();

        void onSuspendProgressionFactory();

        void onRetryErrorCodesChanged();

        void onRetryCountChanged();

        void onRetryDelayChanged();

        void onEditPropertiesButtonClicked();

        void onOptimizeChanged();

        void onDescriptionChanged();

        void onReliableMessagingEnabledChanged();

        void onReliableMessagingPolicyChanged();

        void onSecurityEnabledChanged();

        void onSecurityPolicyChanged();

        void onAddressingEnabledChanged();

        void onAddressingVersionChanged();

        void onAddressingSeparatorListenerChanged();

        void onTimeoutDurationChanged();

        void onTimeoutActionChanged();

    }

}