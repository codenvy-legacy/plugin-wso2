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
package com.codenvy.ide.client.propertiespanel.addressendpoint;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint;
import com.codenvy.ide.client.elements.endpoints.addressendpoint.Property;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.addressendpoint.property.PropertyPresenter;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.List;

import static com.codenvy.ide.client.editor.WSO2Editor.BOOLEAN_TYPE_NAME;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.AddressingVersion;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.Format;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.Optimize;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.TimeoutAction;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Address endpoint
 * depending on user's changes of properties.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class AddressEndpointPropertiesPanelPresenter
        extends AbstractPropertiesPanel<AddressEndpoint, AddressEndpointPropertiesPanelView>
        implements AddressEndpointPropertiesPanelView.ActionDelegate {

    private final WSO2EditorLocalizationConstant              locale;
    private final PropertyPresenter                           propertyPresenter;
    private final PropertyPresenter.PropertiesChangedCallback callback;

    @Inject
    public AddressEndpointPropertiesPanelPresenter(AddressEndpointPropertiesPanelView view,
                                                   PropertyTypeManager propertyTypeManager,
                                                   WSO2EditorLocalizationConstant locale,
                                                   PropertyPresenter propertyPresenter) {
        super(view, propertyTypeManager);

        this.locale = locale;
        this.propertyPresenter = propertyPresenter;

        this.callback = new PropertyPresenter.PropertiesChangedCallback() {
            @Override
            public void onPropertiesChanged(@Nonnull Array<Property> properties) {
                element.setProperties(properties);

                showProperties(properties);

                notifyListeners();
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onFormatChanged() {
        element.setFormat(Format.valueOf(view.getFormat()));

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onUriChanged() {
        element.setUri(view.getUri());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSuspendErrorCodesChanged() {
        element.setSuspendErrorCode(view.getSuspendErrorCodes());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSuspendInitialDurationChanged() {
        String suspendInitialDuration = view.getSuspendInitialDuration();

        try {
            int value = Integer.valueOf(suspendInitialDuration);

            element.setSuspendInitialDuration(value);

            notifyListeners();
        } catch (NumberFormatException e) {
            view.setSuspendInitialDuration(String.valueOf(element.getSuspendInitialDuration()));
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onSuspendMaximumDurationChanged() {
        String suspendMaximumDuration = view.getSuspendMaximumDuration();

        try {
            int value = Integer.valueOf(suspendMaximumDuration);

            element.setSuspendMaximumDuration(value);

            notifyListeners();
        } catch (NumberFormatException e) {
            view.setSuspendMaximumDuration(String.valueOf(element.getSuspendMaximumDuration()));
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onSuspendProgressionFactoryChanged() {
        String suspendProgressionFactory = view.getSuspendProgressionFactory();

        try {
            double value = Double.valueOf(suspendProgressionFactory);

            element.setSuspendProgressionFactory(value);

            notifyListeners();
        } catch (NumberFormatException e) {
            view.setSuspendProgressionFactory(String.valueOf(element.getSuspendProgressionFactory()));
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onRetryErrorCodesChanged() {
        element.setRetryErrorCodes(view.getRetryErrorCodes());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onRetryCountChanged() {
        String retryCount = view.getRetryCount();

        try {
            int value = Integer.valueOf(retryCount);

            element.setRetryCount(value);

            notifyListeners();
        } catch (NumberFormatException e) {
            view.setRetryCount(String.valueOf(element.getRetryCount()));
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onRetryDelayChanged() {
        String retryDelay = view.getRetryDelay();

        try {
            int value = Integer.valueOf(retryDelay);

            element.setRetryDelay(value);

            notifyListeners();
        } catch (NumberFormatException e) {
            view.setRetryDelay(String.valueOf(element.getRetryDelay()));
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onEditPropertiesButtonClicked() {
        propertyPresenter.showDialog(callback, element.getProperties());
    }

    /** {@inheritDoc} */
    @Override
    public void onOptimizeChanged() {
        element.setOptimize(Optimize.valueOf(view.getOptimize()));

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onDescriptionChanged() {
        element.setDescription(view.getDescription());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onReliableMessagingEnabledChanged() {
        boolean enabled = Boolean.valueOf(view.getReliableMessagingEnabled());

        element.setReliableMessagingEnabled(enabled);
        view.setVisibleReliableMessagingEnabledPanel(enabled);

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onReliableMessagingPolicyChanged() {
        element.setReliableMessagingPolicy(view.getReliableMessagingPolicy());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSecurityEnabledChanged() {
        boolean enabled = Boolean.valueOf(view.getSecurityEnabled());

        element.setSecurityEnabled(enabled);
        view.setVisibleSecurityPolicyPanel(enabled);

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSecurityPolicyChanged() {
        element.setSecurityPolicy(view.getSecurityPolicy());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onAddressingEnabledChanged() {
        boolean enabled = Boolean.valueOf(view.getAddressingEnabled());

        element.setAddressingEnabled(enabled);
        view.setVisibleAddressingPanel(enabled);

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onAddressingVersionChanged() {
        element.setAddressingVersion(AddressingVersion.getItemByValue(view.getAddressingVersion()));

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onAddressingSeparatorListenerChanged() {
        element.setAddressingSeparateListener(Boolean.valueOf(view.getAddressingSeparatorListener()));

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onTimeoutDurationChanged() {
        String timeoutDuration = view.getTimeoutDuration();

        try {
            int value = Integer.valueOf(timeoutDuration);

            element.setTimeoutDuration(value);

            notifyListeners();
        } catch (NumberFormatException e) {
            view.setTimeoutDuration(String.valueOf(element.getTimeoutDuration()));
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onTimeoutActionChanged() {
        element.setTimeoutAction(TimeoutAction.valueOf(view.getTimeoutAction()));

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        view.setFormats(propertyTypeManager.getValuesByName(Format.TYPE_NAME));
        view.selectFormat(element.getFormat().name());

        view.setUri(element.getUri());

        view.setSuspendErrorCodes(element.getSuspendErrorCode());
        view.setSuspendInitialDuration(String.valueOf(element.getSuspendInitialDuration()));
        view.setSuspendMaximumDuration(String.valueOf(element.getSuspendMaximumDuration()));
        view.setSuspendProgressionFactory(String.valueOf(element.getSuspendProgressionFactory()));

        view.setRetryErrorCodes(element.getRetryErrorCodes());
        view.setRetryCount(String.valueOf(element.getRetryCount()));
        view.setRetryDelay(String.valueOf(element.getRetryDelay()));

        showProperties(element.getProperties());

        view.setOptimizes(propertyTypeManager.getValuesByName(Optimize.TYPE_NAME));
        view.selectOptimize(element.getOptimize().name());

        view.setDescription(element.getDescription());

        List<String> booleanValues = propertyTypeManager.getValuesByName(BOOLEAN_TYPE_NAME);

        boolean reliableMessagingEnabled = element.isReliableMessagingEnabled();

        view.setReliableMessagingEnabledStates(booleanValues);
        view.selectReliableMessagingEnabledState(String.valueOf(reliableMessagingEnabled));

        view.setVisibleReliableMessagingEnabledPanel(reliableMessagingEnabled);
        view.setReliableMessagingPolicy(element.getReliableMessagingPolicy());

        boolean securityEnabled = element.isSecurityEnabled();

        view.setSecurityEnabledStates(booleanValues);
        view.selectSecurityEnabledState(String.valueOf(securityEnabled));

        view.setVisibleSecurityPolicyPanel(securityEnabled);
        view.setSecurityPolicy(element.getSecurityPolicy());

        boolean addressingEnabled = element.isAddressingEnabled();

        view.setAddressingEnabledStates(booleanValues);
        view.selectAddressingEnabledState(String.valueOf(addressingEnabled));

        view.setVisibleAddressingPanel(addressingEnabled);

        view.setAddressingVersions(propertyTypeManager.getValuesByName(AddressingVersion.TYPE_NAME));
        view.selectAddressingVersion(element.getAddressingVersion().name());

        view.setAddressingSeparatorListenerStates(propertyTypeManager.getValuesByName(BOOLEAN_TYPE_NAME));
        view.selectAddressingSeparatorListenerState(String.valueOf(element.isAddressingSeparateListener()));

        view.setTimeoutDuration(String.valueOf(element.getTimeoutDuration()));

        view.setTimeoutActions(propertyTypeManager.getValuesByName(TimeoutAction.TYPE_NAME));
        view.selectTimeoutAction(element.getTimeoutAction().name());
    }

    /**
     * Shows value of properties in special place on the view.
     *
     * @param properties
     *         list of properties which must to be displayed
     */
    private void showProperties(@Nonnull Array<Property> properties) {
        StringBuilder content = new StringBuilder();
        int size = properties.size() - 1;

        for (int i = 0; i <= size; i++) {
            Property property = properties.get(i);

            content.append(locale.addressEndpointEndpointProperty(property.getName()));

            if (i != size) {
                content.append(", ");
            }
        }

        view.setProperties(content.toString());
    }

}