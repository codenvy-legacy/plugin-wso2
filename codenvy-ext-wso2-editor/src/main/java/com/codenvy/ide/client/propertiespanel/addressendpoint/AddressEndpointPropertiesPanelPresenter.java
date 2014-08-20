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

import com.codenvy.ide.client.elements.AddressEndpoint;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.google.inject.Inject;

/**
 * @author Andrey Plotnikov
 */
public class AddressEndpointPropertiesPanelPresenter
        extends AbstractPropertiesPanel<AddressEndpoint, AddressEndpointPropertiesPanelView>
        implements AddressEndpointPropertiesPanelView.ActionDelegate {

    @Inject
    public AddressEndpointPropertiesPanelPresenter(AddressEndpointPropertiesPanelView view, PropertyTypeManager propertyTypeManager) {
        super(view, propertyTypeManager);
    }

    /** {@inheritDoc} */
    @Override
    public void onFormatChanged() {

    }

    /** {@inheritDoc} */
    @Override
    public void onUriChanged() {

    }

    /** {@inheritDoc} */
    @Override
    public void onSuspendErrorCodesChanged() {

    }

    /** {@inheritDoc} */
    @Override
    public void onSuspendInitialDuration() {

    }

    /** {@inheritDoc} */
    @Override
    public void onSuspendMaximumDuration() {

    }

    /** {@inheritDoc} */
    @Override
    public void onSuspendProgressionFactory() {

    }

    /** {@inheritDoc} */
    @Override
    public void onRetryErrorCodesChanged() {

    }

    /** {@inheritDoc} */
    @Override
    public void onRetryCountChanged() {

    }

    /** {@inheritDoc} */
    @Override
    public void onRetryDelayChanged() {

    }

    /** {@inheritDoc} */
    @Override
    public void onEditPropertiesButtonClicked() {

    }

    /** {@inheritDoc} */
    @Override
    public void onOptimizeChanged() {

    }

    /** {@inheritDoc} */
    @Override
    public void onDescriptionChanged() {

    }

    /** {@inheritDoc} */
    @Override
    public void onReliableMessagingEnabledChanged() {

    }

    /** {@inheritDoc} */
    @Override
    public void onReliableMessagingPolicyChanged() {

    }

    /** {@inheritDoc} */
    @Override
    public void onSecurityEnabledChanged() {

    }

    /** {@inheritDoc} */
    @Override
    public void onSecurityPolicyChanged() {

    }

    /** {@inheritDoc} */
    @Override
    public void onAddressingEnabledChanged() {

    }

    /** {@inheritDoc} */
    @Override
    public void onAddressingVersionChanged() {

    }

    /** {@inheritDoc} */
    @Override
    public void onAddressingSeparatorListenerChanged() {

    }

    /** {@inheritDoc} */
    @Override
    public void onTimeoutDurationChanged() {

    }

    /** {@inheritDoc} */
    @Override
    public void onTimeoutActionChanged() {

    }

}