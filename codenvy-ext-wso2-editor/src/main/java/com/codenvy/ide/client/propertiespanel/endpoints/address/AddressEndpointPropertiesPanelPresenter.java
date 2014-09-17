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
package com.codenvy.ide.client.propertiespanel.endpoints.address;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint;
import com.codenvy.ide.client.elements.endpoints.addressendpoint.Property;
import com.codenvy.ide.client.inject.factories.PropertiesPanelWidgetFactory;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.endpoints.address.property.PropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.list.ListPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static com.codenvy.ide.client.editor.WSO2Editor.BOOLEAN_TYPE_NAME;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.ADDRESSING_ENABLED;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.ADDRESSING_SEPARATE_LISTENER;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.ADDRESSING_VERSION;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.AddressingVersion;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.DESCRIPTION;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.FORMAT;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.Format;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.OPTIMIZE;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.Optimize;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.PROPERTIES;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.RELIABLE_MESSAGING_ENABLED;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.RELIABLE_MESSAGING_POLICY;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.RETRY_COUNT;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.RETRY_DELAY;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.RETRY_ERROR_CODES;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.SECURITY_ENABLED;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.SECURITY_POLICY;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.SUSPEND_ERROR_CODE;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.SUSPEND_INITIAL_DURATION;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.SUSPEND_MAXIMUM_DURATION;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.SUSPEND_PROGRESSION_FACTORY;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.TIMEOUT_ACTION;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.TIMEOUT_DURATION;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.TimeoutAction;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.URI;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Address endpoint
 * depending on user's changes of properties.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class AddressEndpointPropertiesPanelPresenter extends AbstractPropertiesPanel<AddressEndpoint, PropertiesPanelView> {

    private final WSO2EditorLocalizationConstant              locale;
    private final PropertyPresenter                           propertyPresenter;
    private final PropertyPresenter.PropertiesChangedCallback callback;

    private ListPropertyPresenter   format;
    private SimplePropertyPresenter uri;

    private SimplePropertyPresenter suspendErrorCode;
    private SimplePropertyPresenter suspendInitialDuration;
    private SimplePropertyPresenter suspendMaximumDuration;
    private SimplePropertyPresenter suspendProgressionFactory;

    private SimplePropertyPresenter retryErrorCode;
    private SimplePropertyPresenter retryCount;
    private SimplePropertyPresenter retryDelay;

    private ComplexPropertyPresenter properties;
    private ListPropertyPresenter    optimize;
    private SimplePropertyPresenter  description;

    private ListPropertyPresenter   reliableMessagingEnabled;
    private SimplePropertyPresenter reliableMessagePolicy;

    private ListPropertyPresenter   securityEnabled;
    private SimplePropertyPresenter securityPolicy;

    private ListPropertyPresenter addressingEnabled;
    private ListPropertyPresenter addressingVersion;
    private ListPropertyPresenter addressingSeparateListener;

    private SimplePropertyPresenter timeoutDuration;
    private ListPropertyPresenter   timeoutAction;

    @Inject
    public AddressEndpointPropertiesPanelPresenter(PropertiesPanelView view,
                                                   PropertyTypeManager propertyTypeManager,
                                                   WSO2EditorLocalizationConstant locale,
                                                   PropertyPresenter propertyPresenter,
                                                   PropertiesPanelWidgetFactory propertiesPanelWidgetFactory,
                                                   Provider<SimplePropertyPresenter> simplePropertyPresenterProvider,
                                                   Provider<ListPropertyPresenter> listPropertyPresenterProvider,
                                                   Provider<ComplexPropertyPresenter> complexPropertyPresenterProvider) {
        super(view, propertyTypeManager);

        this.locale = locale;
        this.propertyPresenter = propertyPresenter;

        this.callback = new PropertyPresenter.PropertiesChangedCallback() {
            @Override
            public void onPropertiesChanged(@Nonnull Array<Property> properties) {
                element.putProperty(PROPERTIES, properties);

                showProperties(properties);

                notifyListeners();
            }
        };

        prepareView(propertiesPanelWidgetFactory,
                    simplePropertyPresenterProvider,
                    listPropertyPresenterProvider,
                    complexPropertyPresenterProvider);
    }

    private void prepareView(PropertiesPanelWidgetFactory propertiesPanelWidgetFactory,
                             Provider<SimplePropertyPresenter> simplePropertyPresenterProvider,
                             Provider<ListPropertyPresenter> listPropertyPresenterProvider,
                             Provider<ComplexPropertyPresenter> complexPropertyPresenterProvider) {
        PropertyGroupPresenter basicGroup = propertiesPanelWidgetFactory.createPropertyGroupPresenter(locale.addressEndpointBasic());
        view.addGroup(basicGroup);

        format = listPropertyPresenterProvider.get();
        format.setTitle(locale.addressEndpointFormat());
        format.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(FORMAT, Format.valueOf(property));

                notifyListeners();
            }
        });
        basicGroup.addItem(format);

        uri = simplePropertyPresenterProvider.get();
        uri.setTitle(locale.addressEndpointUri());
        uri.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(URI, property);

                notifyListeners();
            }
        });
        basicGroup.addItem(uri);

        PropertyGroupPresenter suspendStateGroup =
                propertiesPanelWidgetFactory.createPropertyGroupPresenter(locale.addressEndpointSuspendState());
        view.addGroup(suspendStateGroup);

        suspendErrorCode = simplePropertyPresenterProvider.get();
        suspendErrorCode.setTitle(locale.addressEndpointSuspendErrorCodes());
        suspendErrorCode.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(SUSPEND_ERROR_CODE, property);

                notifyListeners();
            }
        });
        suspendStateGroup.addItem(suspendErrorCode);

        suspendInitialDuration = simplePropertyPresenterProvider.get();
        suspendInitialDuration.setTitle(locale.addressEndpointSuspendInitialDuration());
        suspendInitialDuration.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                try {
                    int value = Integer.valueOf(property);

                    element.putProperty(SUSPEND_INITIAL_DURATION, value);

                    notifyListeners();
                } catch (NumberFormatException e) {
                    suspendInitialDuration.setProperty(String.valueOf(element.getProperty(SUSPEND_INITIAL_DURATION)));
                }
            }
        });
        suspendStateGroup.addItem(suspendInitialDuration);

        suspendMaximumDuration = simplePropertyPresenterProvider.get();
        suspendMaximumDuration.setTitle(locale.addressEndpointSuspendMaximumDuration());
        suspendMaximumDuration.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                try {
                    int value = Integer.valueOf(property);

                    element.putProperty(SUSPEND_MAXIMUM_DURATION, value);

                    notifyListeners();
                } catch (NumberFormatException e) {
                    suspendMaximumDuration.setProperty(String.valueOf(element.getProperty(SUSPEND_MAXIMUM_DURATION)));
                }
            }
        });
        suspendStateGroup.addItem(suspendMaximumDuration);

        suspendProgressionFactory = simplePropertyPresenterProvider.get();
        suspendProgressionFactory.setTitle(locale.addressEndpointSuspendProgressionFactory());
        suspendProgressionFactory.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                try {
                    double value = Double.valueOf(property);

                    element.putProperty(SUSPEND_PROGRESSION_FACTORY, value);

                    notifyListeners();
                } catch (NumberFormatException e) {
                    suspendProgressionFactory.setProperty(String.valueOf(element.getProperty(SUSPEND_PROGRESSION_FACTORY)));
                }
            }
        });
        suspendStateGroup.addItem(suspendProgressionFactory);

        PropertyGroupPresenter timeoutStateGroup =
                propertiesPanelWidgetFactory.createPropertyGroupPresenter(locale.addressEndpointEndpointTimeoutState());
        view.addGroup(timeoutStateGroup);

        retryErrorCode = simplePropertyPresenterProvider.get();
        retryErrorCode.setTitle(locale.addressEndpointRetryErrorCodes());
        retryErrorCode.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(RETRY_ERROR_CODES, property);

                notifyListeners();
            }
        });
        timeoutStateGroup.addItem(retryErrorCode);

        retryCount = simplePropertyPresenterProvider.get();
        retryCount.setTitle(locale.addressEndpointRetryCount());
        retryCount.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                try {
                    int value = Integer.valueOf(property);

                    element.putProperty(RETRY_COUNT, value);

                    notifyListeners();
                } catch (NumberFormatException e) {
                    retryCount.setProperty(String.valueOf(element.getProperty(RETRY_COUNT)));
                }
            }
        });
        timeoutStateGroup.addItem(retryCount);

        retryDelay = simplePropertyPresenterProvider.get();
        retryDelay.setTitle(locale.addressEndpointRetryDelay());
        retryDelay.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                try {
                    int value = Integer.valueOf(property);

                    element.putProperty(RETRY_DELAY, value);

                    notifyListeners();
                } catch (NumberFormatException e) {
                    retryDelay.setProperty(String.valueOf(element.getProperty(RETRY_DELAY)));
                }
            }
        });
        timeoutStateGroup.addItem(retryDelay);

        PropertyGroupPresenter miscGroup = propertiesPanelWidgetFactory.createPropertyGroupPresenter(locale.addressEndpointMisc());
        view.addGroup(miscGroup);

        properties = complexPropertyPresenterProvider.get();
        properties.setTitle(locale.addressEndpointProperties());
        properties.addEditButtonClickedListener(new ComplexPropertyPresenter.EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                Array<Property> properties = element.getProperty(PROPERTIES);

                if (properties != null) {
                    propertyPresenter.showDialog(callback, properties);
                }
            }
        });
        miscGroup.addItem(properties);

        optimize = listPropertyPresenterProvider.get();
        optimize.setTitle(locale.addressEndpointOptimize());
        optimize.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(OPTIMIZE, Optimize.valueOf(property));

                notifyListeners();
            }
        });
        miscGroup.addItem(optimize);

        description = simplePropertyPresenterProvider.get();
        description.setTitle(locale.addressEndpointDescription());
        description.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(DESCRIPTION, property);

                notifyListeners();
            }
        });
        miscGroup.addItem(description);

        PropertyGroupPresenter qosGroup = propertiesPanelWidgetFactory.createPropertyGroupPresenter(locale.addressEndpointQos());
        view.addGroup(qosGroup);

        reliableMessagingEnabled = listPropertyPresenterProvider.get();
        reliableMessagingEnabled.setTitle(locale.addressEndpointReliableMessagingEnabled());
        reliableMessagingEnabled.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                Boolean visible = Boolean.valueOf(property);

                element.putProperty(RELIABLE_MESSAGING_ENABLED, visible);

                reliableMessagePolicy.setVisible(visible);

                notifyListeners();
            }
        });
        qosGroup.addItem(reliableMessagingEnabled);

        reliableMessagePolicy = simplePropertyPresenterProvider.get();
        reliableMessagePolicy.setTitle(locale.addressEndpointReliableMessagingPolicy());
        reliableMessagePolicy.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(RELIABLE_MESSAGING_POLICY, property);

                notifyListeners();
            }
        });
        qosGroup.addItem(reliableMessagePolicy);

        securityEnabled = listPropertyPresenterProvider.get();
        securityEnabled.setTitle(locale.addressEndpointSecurityEnabled());
        securityEnabled.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                boolean visible = Boolean.valueOf(property);

                element.putProperty(SECURITY_ENABLED, visible);

                securityPolicy.setVisible(visible);

                notifyListeners();
            }
        });
        qosGroup.addItem(securityEnabled);

        securityPolicy = simplePropertyPresenterProvider.get();
        securityPolicy.setTitle(locale.addressEndpointSecurityPolicy());
        securityPolicy.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(SECURITY_POLICY, property);

                notifyListeners();
            }
        });
        qosGroup.addItem(securityPolicy);

        addressingEnabled = listPropertyPresenterProvider.get();
        addressingEnabled.setTitle(locale.addressEndpointAddressingEnabled());
        addressingEnabled.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                boolean visible = Boolean.valueOf(property);

                element.putProperty(ADDRESSING_ENABLED, visible);

                addressingVersion.setVisible(visible);
                addressingSeparateListener.setVisible(visible);

                notifyListeners();
            }
        });
        qosGroup.addItem(addressingEnabled);

        addressingVersion = listPropertyPresenterProvider.get();
        addressingVersion.setTitle(locale.addressEndpointAddressingVersion());
        addressingVersion.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(ADDRESSING_VERSION, AddressingVersion.getItemByValue(property));

                notifyListeners();
            }
        });
        qosGroup.addItem(addressingVersion);

        addressingSeparateListener = listPropertyPresenterProvider.get();
        addressingSeparateListener.setTitle(locale.addressEndpointAddressingSeparateListener());
        addressingSeparateListener.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(ADDRESSING_SEPARATE_LISTENER, Boolean.valueOf(property));

                notifyListeners();
            }
        });
        qosGroup.addItem(addressingSeparateListener);

        PropertyGroupPresenter timeoutGroup = propertiesPanelWidgetFactory.createPropertyGroupPresenter(locale.addressEndpointTimeout());
        view.addGroup(timeoutGroup);

        timeoutDuration = simplePropertyPresenterProvider.get();
        timeoutDuration.setTitle(locale.addressEndpointTimeoutDuration());
        timeoutDuration.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                try {
                    int value = Integer.valueOf(property);

                    element.putProperty(TIMEOUT_DURATION, value);

                    notifyListeners();
                } catch (NumberFormatException e) {
                    timeoutDuration.setProperty(String.valueOf(element.getProperty(TIMEOUT_DURATION)));
                }
            }
        });
        timeoutGroup.addItem(timeoutDuration);

        timeoutAction = listPropertyPresenterProvider.get();
        timeoutAction.setTitle(locale.addressEndpointTimeoutAction());
        timeoutAction.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(TIMEOUT_ACTION, TimeoutAction.valueOf(property));

                notifyListeners();
            }
        });
        timeoutGroup.addItem(timeoutAction);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        format.setValues(propertyTypeManager.getValuesByName(Format.TYPE_NAME));
        Format format = element.getProperty(FORMAT);

        if (format != null) {
            this.format.selectValue(format.name());
        }

        uri.setProperty(element.getProperty(URI));

        suspendErrorCode.setProperty(element.getProperty(SUSPEND_ERROR_CODE));
        suspendInitialDuration.setProperty(String.valueOf(element.getProperty(SUSPEND_INITIAL_DURATION)));
        suspendMaximumDuration.setProperty(String.valueOf(element.getProperty(SUSPEND_MAXIMUM_DURATION)));
        suspendProgressionFactory.setProperty(String.valueOf(element.getProperty(SUSPEND_PROGRESSION_FACTORY)));

        retryErrorCode.setProperty(element.getProperty(RETRY_ERROR_CODES));
        retryCount.setProperty(String.valueOf(element.getProperty(RETRY_COUNT)));
        retryDelay.setProperty(String.valueOf(element.getProperty(RETRY_DELAY)));

        showProperties(element.getProperty(PROPERTIES));

        optimize.setValues(propertyTypeManager.getValuesByName(Optimize.TYPE_NAME));
        Optimize optimize = element.getProperty(OPTIMIZE);

        if (optimize != null) {
            this.optimize.selectValue(optimize.name());
        }

        description.setProperty(element.getProperty(DESCRIPTION));

        List<String> booleanValues = propertyTypeManager.getValuesByName(BOOLEAN_TYPE_NAME);

        Boolean isReliableMessagingEnabled = element.getProperty(RELIABLE_MESSAGING_ENABLED);
        if (isReliableMessagingEnabled != null) {
            this.reliableMessagingEnabled.setValues(booleanValues);
            this.reliableMessagingEnabled.selectValue(isReliableMessagingEnabled.toString());

            reliableMessagePolicy.setVisible(isReliableMessagingEnabled);
            reliableMessagePolicy.setProperty(element.getProperty(RELIABLE_MESSAGING_POLICY));
        }

        Boolean isSecurityEnabled = element.getProperty(SECURITY_ENABLED);
        if (isSecurityEnabled != null) {
            this.securityEnabled.setValues(booleanValues);
            this.securityEnabled.selectValue(isSecurityEnabled.toString());

            securityPolicy.setVisible(isSecurityEnabled);
            securityPolicy.setProperty(element.getProperty(SECURITY_POLICY));
        }

        Boolean isAddressingEnabled = element.getProperty(ADDRESSING_ENABLED);
        if (isAddressingEnabled != null) {
            addressingEnabled.setValues(booleanValues);
            addressingEnabled.selectValue(isAddressingEnabled.toString());

            addressingVersion.setVisible(isAddressingEnabled);
            addressingVersion.setValues(propertyTypeManager.getValuesByName(AddressingVersion.TYPE_NAME));

            AddressingVersion addressingVersion = element.getProperty(ADDRESSING_VERSION);
            if (addressingVersion != null) {
                this.addressingVersion.selectValue(addressingVersion.name());
            }

            addressingSeparateListener.setVisible(isAddressingEnabled);
            addressingSeparateListener.setValues(booleanValues);
            addressingSeparateListener.selectValue(String.valueOf(element.getProperty(ADDRESSING_SEPARATE_LISTENER)));
        }

        timeoutDuration.setProperty(String.valueOf(element.getProperty(TIMEOUT_DURATION)));

        timeoutAction.setValues(propertyTypeManager.getValuesByName(TimeoutAction.TYPE_NAME));
        TimeoutAction timeoutAction = element.getProperty(TIMEOUT_ACTION);

        if (timeoutAction != null) {
            this.timeoutAction.selectValue(timeoutAction.name());
        }
    }

    /**
     * Shows value of properties in special place on the view.
     *
     * @param properties
     *         list of properties which must to be displayed
     */
    private void showProperties(@Nullable Array<Property> properties) {
        if (properties == null) {
            this.properties.setProperty("");
            return;
        }

        StringBuilder content = new StringBuilder();
        int size = properties.size() - 1;

        for (int i = 0; i <= size; i++) {
            Property property = properties.get(i);

            content.append(locale.addressEndpointEndpointProperty(property.getName()));

            if (i != size) {
                content.append(", ");
            }
        }

        this.properties.setProperty(content.toString());
    }

}