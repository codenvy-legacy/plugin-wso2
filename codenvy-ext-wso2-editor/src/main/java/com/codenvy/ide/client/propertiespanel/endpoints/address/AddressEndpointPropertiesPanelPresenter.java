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
import com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint;
import com.codenvy.ide.client.elements.endpoints.address.Property;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.managers.SelectionManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.PropertyPanelFactory;
import com.codenvy.ide.client.propertiespanel.endpoints.address.property.PropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.list.ListPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.ADDRESSING_ENABLED;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.ADDRESSING_SEPARATE_LISTENER;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.ADDRESSING_VERSION;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.AddressingVersion;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.DESCRIPTION;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.FORMAT;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.Format;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.OPTIMIZE;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.Optimize;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.PROPERTIES;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.RELIABLE_MESSAGING_ENABLED;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.RELIABLE_MESSAGING_POLICY;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.RETRY_COUNT;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.RETRY_DELAY;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.RETRY_ERROR_CODES;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.SECURITY_ENABLED;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.SECURITY_POLICY;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.SUSPEND_ERROR_CODE;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.SUSPEND_INITIAL_DURATION;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.SUSPEND_MAXIMUM_DURATION;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.SUSPEND_PROGRESSION_FACTORY;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.TIMEOUT_ACTION;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.TIMEOUT_DURATION;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.TimeoutAction;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.URI;
import static com.codenvy.ide.client.initializers.propertytype.CommonPropertyTypeInitializer.BOOLEAN_TYPE_NAME;
import static com.codenvy.ide.client.propertiespanel.endpoints.address.property.PropertyPresenter.PropertiesChangedCallback;
import static com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter.EditButtonClickedListener;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Address endpoint
 * depending on user's changes of properties.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class AddressEndpointPropertiesPanelPresenter extends AbstractPropertiesPanel<AddressEndpoint>
        implements PropertiesPanelView.ActionDelegate {

    private final PropertyPresenter         propertyPresenter;
    private final PropertiesChangedCallback callback;

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
                                                   PropertyPanelFactory propertyPanelFactory,
                                                   PropertyPresenter propertyPresenter,
                                                   SelectionManager selectionManager) {
        super(view, propertyTypeManager, locale, propertyPanelFactory, selectionManager);

        this.propertyPresenter = propertyPresenter;

        this.callback = new PropertiesChangedCallback() {
            @Override
            public void onPropertiesChanged(@Nonnull List<Property> properties) {
                element.putProperty(PROPERTIES, properties);

                showProperties(properties);

                notifyListeners();
            }
        };

        prepareView();
    }

    private void prepareView() {
        prepareBasicGroupView();

        prepareSuspendStateGroupView();

        prepareTimeoutStateGroupView();

        prepareMiscGroupView();

        prepareQosGroupView();

        prepareTimeoutGroupView();
    }

    private void prepareBasicGroupView() {
        PropertyGroupPresenter basicGroup = createGroup(locale.addressEndpointBasic());

        PropertyValueChangedListener formatListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(FORMAT, Format.getItemByValue(property));

                notifyListeners();
            }
        };
        format = createListProperty(basicGroup, locale.addressEndpointFormat(), formatListener);

        PropertyValueChangedListener uriListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(URI, property);

                notifyListeners();
            }
        };
        uri = createSimpleProperty(basicGroup, locale.addressEndpointUri(), uriListener);
    }

    private void prepareSuspendStateGroupView() {
        PropertyGroupPresenter suspendStateGroup = createGroup(locale.addressEndpointSuspendState());

        PropertyValueChangedListener suspendErrorListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(SUSPEND_ERROR_CODE, property);

                notifyListeners();
            }
        };
        suspendErrorCode = createSimpleProperty(suspendStateGroup, locale.addressEndpointSuspendErrorCodes(), suspendErrorListener);

        PropertyValueChangedListener suspendInitDurListener = new PropertyValueChangedListener() {
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
        };
        suspendInitialDuration = createSimpleProperty(suspendStateGroup,
                                                      locale.addressEndpointSuspendInitialDuration(),
                                                      suspendInitDurListener);

        PropertyValueChangedListener suspendMaxDurListener = new PropertyValueChangedListener() {
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
        };
        suspendMaximumDuration = createSimpleProperty(suspendStateGroup,
                                                      locale.addressEndpointSuspendMaximumDuration(),
                                                      suspendMaxDurListener);

        PropertyValueChangedListener suspendProgressFactoryListener = new PropertyValueChangedListener() {
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
        };
        suspendProgressionFactory = createSimpleProperty(suspendStateGroup,
                                                         locale.addressEndpointSuspendProgressionFactory(),
                                                         suspendProgressFactoryListener);
    }

    private void prepareTimeoutStateGroupView() {
        PropertyGroupPresenter timeoutStateGroup = createGroup(locale.addressEndpointEndpointTimeoutState());

        PropertyValueChangedListener retryErrorListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(RETRY_ERROR_CODES, property);

                notifyListeners();
            }
        };
        retryErrorCode = createSimpleProperty(timeoutStateGroup, locale.addressEndpointRetryErrorCodes(), retryErrorListener);

        PropertyValueChangedListener retryCountListener = new PropertyValueChangedListener() {
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
        };
        retryCount = createSimpleProperty(timeoutStateGroup, locale.addressEndpointRetryCount(), retryCountListener);

        PropertyValueChangedListener retryDelayListener = new PropertyValueChangedListener() {
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
        };
        retryDelay = createSimpleProperty(timeoutStateGroup, locale.addressEndpointRetryDelay(), retryDelayListener);
    }

    private void prepareMiscGroupView() {
        PropertyGroupPresenter miscGroup = createGroup(locale.addressEndpointMisc());

        EditButtonClickedListener propertiesBtnListener = new EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                List<Property> properties = element.getProperty(PROPERTIES);

                if (properties != null) {
                    propertyPresenter.showDialog(callback, properties);
                }
            }
        };
        properties = createComplexProperty(miscGroup, locale.addressEndpointProperties(), propertiesBtnListener);

        PropertyValueChangedListener optimizeListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(OPTIMIZE, Optimize.getItemByValue(property));

                notifyListeners();
            }
        };
        optimize = createListProperty(miscGroup, locale.addressEndpointOptimize(), optimizeListener);

        PropertyValueChangedListener descriptionListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(DESCRIPTION, property);

                notifyListeners();
            }
        };
        description = createSimpleProperty(miscGroup, locale.description(), descriptionListener);
    }

    private void prepareQosGroupView() {
        PropertyGroupPresenter qosGroup = createGroup(locale.addressEndpointQos());

        PropertyValueChangedListener reliableMessEnableListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                Boolean visible = Boolean.valueOf(property);

                element.putProperty(RELIABLE_MESSAGING_ENABLED, visible);

                reliableMessagePolicy.setVisible(visible);

                notifyListeners();
            }
        };
        reliableMessagingEnabled =
                createListProperty(qosGroup, locale.addressEndpointReliableMessagingEnabled(), reliableMessEnableListener);

        PropertyValueChangedListener reliableMessageListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(RELIABLE_MESSAGING_POLICY, property);

                notifyListeners();
            }
        };
        reliableMessagePolicy = createSimpleProperty(qosGroup, locale.addressEndpointReliableMessagingPolicy(), reliableMessageListener);

        PropertyValueChangedListener securityEnabledListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                boolean visible = Boolean.valueOf(property);

                element.putProperty(SECURITY_ENABLED, visible);

                securityPolicy.setVisible(visible);

                notifyListeners();
            }
        };
        securityEnabled = createListProperty(qosGroup, locale.addressEndpointSecurityEnabled(), securityEnabledListener);

        PropertyValueChangedListener securityPolicyListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(SECURITY_POLICY, property);

                notifyListeners();
            }
        };
        securityPolicy = createSimpleProperty(qosGroup, locale.addressEndpointSecurityPolicy(), securityPolicyListener);

        PropertyValueChangedListener addressEnabledListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                boolean visible = Boolean.valueOf(property);

                element.putProperty(ADDRESSING_ENABLED, visible);

                addressingVersion.setVisible(visible);
                addressingSeparateListener.setVisible(visible);

                notifyListeners();
            }
        };
        addressingEnabled = createListProperty(qosGroup, locale.addressEndpointAddressingEnabled(), addressEnabledListener);

        PropertyValueChangedListener addressVersionListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(ADDRESSING_VERSION, AddressingVersion.getItemByValue(property));

                notifyListeners();
            }
        };
        addressingVersion = createListProperty(qosGroup, locale.addressEndpointAddressingVersion(), addressVersionListener);

        PropertyValueChangedListener addressSeparateListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(ADDRESSING_SEPARATE_LISTENER, Boolean.valueOf(property));

                notifyListeners();
            }
        };
        addressingSeparateListener =
                createListProperty(qosGroup, locale.addressEndpointAddressingSeparateListener(), addressSeparateListener);
    }

    private void prepareTimeoutGroupView() {
        PropertyGroupPresenter timeoutGroup = createGroup(locale.addressEndpointTimeout());

        PropertyValueChangedListener timeoutDurationListener = new PropertyValueChangedListener() {
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
        };
        timeoutDuration = createSimpleProperty(timeoutGroup, locale.addressEndpointTimeoutDuration(), timeoutDurationListener);

        PropertyValueChangedListener timeoutActionListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(TIMEOUT_ACTION, TimeoutAction.getItemByValue(property));

                notifyListeners();
            }
        };
        timeoutAction = createListProperty(timeoutGroup, locale.addressEndpointTimeoutAction(), timeoutActionListener);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        displayBasicGroup();

        displaySuspendStateGroup();

        displayTimeoutStateGroup();

        displayMiscGroup();

        displayQosGroup();

        displayTimeoutGroup();
    }

    private void displayBasicGroup() {
        format.setValues(propertyTypeManager.getValuesByName(Format.TYPE_NAME));
        Format formatValue = element.getProperty(FORMAT);

        if (formatValue != null) {
            format.selectValue(formatValue.getValue());
        }

        uri.setProperty(element.getProperty(URI));
    }

    private void displaySuspendStateGroup() {
        suspendErrorCode.setProperty(element.getProperty(SUSPEND_ERROR_CODE));
        suspendInitialDuration.setProperty(String.valueOf(element.getProperty(SUSPEND_INITIAL_DURATION)));
        suspendMaximumDuration.setProperty(String.valueOf(element.getProperty(SUSPEND_MAXIMUM_DURATION)));
        suspendProgressionFactory.setProperty(String.valueOf(element.getProperty(SUSPEND_PROGRESSION_FACTORY)));
    }

    private void displayTimeoutStateGroup() {
        retryErrorCode.setProperty(element.getProperty(RETRY_ERROR_CODES));
        retryCount.setProperty(String.valueOf(element.getProperty(RETRY_COUNT)));
        retryDelay.setProperty(String.valueOf(element.getProperty(RETRY_DELAY)));
    }

    private void displayMiscGroup() {
        showProperties(element.getProperty(PROPERTIES));

        optimize.setValues(propertyTypeManager.getValuesByName(Optimize.TYPE_NAME));
        Optimize optimizeValue = element.getProperty(OPTIMIZE);

        if (optimizeValue != null) {
            optimize.selectValue(optimizeValue.getValue());
        }

        description.setProperty(element.getProperty(DESCRIPTION));
    }

    private void displayQosGroup() {
        List<String> booleanValues = propertyTypeManager.getValuesByName(BOOLEAN_TYPE_NAME);

        Boolean isReliableMessagingEnabled = element.getProperty(RELIABLE_MESSAGING_ENABLED);
        if (isReliableMessagingEnabled != null) {
            reliableMessagingEnabled.setValues(booleanValues);
            reliableMessagingEnabled.selectValue(isReliableMessagingEnabled.toString());

            reliableMessagePolicy.setVisible(isReliableMessagingEnabled);
            reliableMessagePolicy.setProperty(element.getProperty(RELIABLE_MESSAGING_POLICY));
        }

        Boolean isSecurityEnabled = element.getProperty(SECURITY_ENABLED);
        if (isSecurityEnabled != null) {
            securityEnabled.setValues(booleanValues);
            securityEnabled.selectValue(isSecurityEnabled.toString());

            securityPolicy.setVisible(isSecurityEnabled);
            securityPolicy.setProperty(element.getProperty(SECURITY_POLICY));
        }

        Boolean isAddressingEnabled = element.getProperty(ADDRESSING_ENABLED);
        if (isAddressingEnabled != null) {
            addressingEnabled.setValues(booleanValues);
            addressingEnabled.selectValue(isAddressingEnabled.toString());

            addressingVersion.setVisible(isAddressingEnabled);
            addressingVersion.setValues(propertyTypeManager.getValuesByName(AddressingVersion.TYPE_NAME));

            AddressingVersion addressingVersionValue = element.getProperty(ADDRESSING_VERSION);
            if (addressingVersionValue != null) {
                addressingVersion.selectValue(addressingVersionValue.getValue());
            }

            addressingSeparateListener.setVisible(isAddressingEnabled);
            addressingSeparateListener.setValues(booleanValues);
            addressingSeparateListener.selectValue(String.valueOf(element.getProperty(ADDRESSING_SEPARATE_LISTENER)));
        }
    }

    private void displayTimeoutGroup() {
        timeoutDuration.setProperty(String.valueOf(element.getProperty(TIMEOUT_DURATION)));

        timeoutAction.setValues(propertyTypeManager.getValuesByName(TimeoutAction.TYPE_NAME));
        TimeoutAction timeoutActionValue = element.getProperty(TIMEOUT_ACTION);

        if (timeoutActionValue != null) {
            timeoutAction.selectValue(timeoutActionValue.getValue());
        }
    }

    /**
     * Shows value of properties in special place on the view.
     *
     * @param propertiesArray
     *         list of properties which must to be displayed
     */
    private void showProperties(@Nullable List<Property> propertiesArray) {
        if (propertiesArray == null) {
            properties.setProperty("");
            return;
        }

        StringBuilder content = new StringBuilder();

        for (Iterator<Property> iterator = propertiesArray.iterator(); iterator.hasNext(); ) {
            Property property = iterator.next();
            String name = property.getProperty(Property.NAME);

            if (name != null) {
                content.append(locale.addressEndpointEndpointProperty(name));
                content.append(iterator.hasNext() ? ", " : "");
            }
        }

        properties.setProperty(content.toString());
    }

}