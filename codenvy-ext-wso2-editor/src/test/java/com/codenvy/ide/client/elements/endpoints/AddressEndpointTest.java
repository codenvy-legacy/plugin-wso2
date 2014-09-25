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

package com.codenvy.ide.client.elements.endpoints;

import com.codenvy.ide.client.elements.AbstractElementTest;
import com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint;
import com.codenvy.ide.client.elements.endpoints.addressendpoint.Property;
import com.google.gwt.xml.client.Node;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.ADDRESSING_ENABLED;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.ADDRESSING_SEPARATE_LISTENER;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.ADDRESSING_VERSION;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.AddressingVersion;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.AddressingVersion.FINAL;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.AddressingVersion.SUBMISSION;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.DESCRIPTION;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.FORMAT;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.Format;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.Format.LEAVE_AS_IS;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.Format.SOUP11;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.OPTIMIZE;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.Optimize;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.Optimize.MTOM;
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
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.TimeoutAction.DISCARD;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.TimeoutAction.NEVER;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.URI;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Andrey Plotnikov
 */
public class AddressEndpointTest extends AbstractElementTest<AddressEndpoint> {

    private static final String ROOT_PATH        = "endpoints/address/";
    private static final String SERIALIZE_PATH   = ROOT_PATH + "serialize/";
    private static final String DESERIALIZE_PATH = ROOT_PATH + "deserialize/";

    private static final Format            FORMAT_DEFAULT_VALUE                       = LEAVE_AS_IS;
    private static final String            URI_VALUE                                  = "uri";
    private static final String            URI_DEFAULT_VALUE                          = "http://www.example.org/service";
    private static final String            SUSPEND_ERROR_CODE_VALUE                   = "code";
    private static final String            SUSPEND_ERROR_CODE_DEFAULT_VALUE           = "";
    private static final Integer           SUSPEND_INITIAL_DURATION_VALUE             = 100;
    private static final Integer           SUSPEND_INITIAL_DURATION_DEFAULT_VALUE     = -1;
    private static final Integer           SUSPEND_MAXIMUM_DURATION_VALUE             = 1;
    private static final Integer           SUSPEND_MAXIMUM_DURATION_DEFAULT_VALUE     = 0;
    private static final Double            SUSPEND_PROGRESSION_FACTORY_VALUE          = 1.;
    private static final Double            SUSPEND_PROGRESSION_FACTORY_DEFAULT_VALUE  = -1.;
    private static final String            RETRY_ERROR_CODES_VALUE                    = "code";
    private static final String            RETRY_ERROR_CODES_DEFAULT_VALUE            = "";
    private static final Integer           RETRY_COUNT_VALUE                          = 1;
    private static final Integer           RETRY_COUNT_DEFAULT_VALUE                  = 0;
    private static final Integer           RETRY_DELAY_VALUE                          = 1;
    private static final Integer           RETRY_DELAY_DEFAULT_VALUE                  = 0;
    private static final List<Property>    PROPERTIES_DEFAULT_VALUE                   = new ArrayList<>();
    private static final Optimize          OPTIMIZE_DEFAULT_VALUE                     = Optimize.LEAVE_AS_IS;
    private static final String            DESCRIPTION_VALUE                          = "description";
    private static final String            DESCRIPTION_DEFAULT_VALUE                  = "";
    private static final Boolean           RELIABLE_MESSAGING_ENABLED_DEFAULT_VALUE   = false;
    private static final String            RELIABLE_MESSAGING_POLICY_VALUE            = "policy value";
    private static final String            RELIABLE_MESSAGING_POLICY_DEFAULT_VALUE    = "/default/key";
    private static final Boolean           SECURITY_ENABLED_DEFAULT_VALUE             = false;
    private static final String            SECURITY_POLICY_VALUE                      = "policy value";
    private static final String            SECURITY_POLICY_DEFAULT_VALUE              = "/default/key";
    private static final Boolean           ADDRESSING_ENABLED_DEFAULT_VALUE           = false;
    private static final AddressingVersion ADDRESSING_VERSION_DEFAULT_VALUE           = FINAL;
    private static final Boolean           ADDRESSING_SEPARATE_LISTENER_DEFAULT_VALUE = false;
    private static final Integer           TIMEOUT_DURATION_VALUE                     = 1;
    private static final Integer           TIMEOUT_DURATION_DEFAULT_VALUE             = 0;
    private static final TimeoutAction     TIMEOUT_ACTION_DEFAULT_VALUE               = NEVER;

    private static final String PROPERTY_SERIALIZE_CONTENT = "<property name=\"property_name\" value=\"property_value\"/>\n";

    @Mock
    private Property property;

    @Before
    public void setUp() throws Exception {
        when(resources.address()).thenReturn(icon);
        when(propertyProvider.get()).thenReturn(property);

        entity = new AddressEndpoint(resources, branchProvider, elementCreatorsManager, propertyProvider);
    }

    @Override
    public void elementTitleShouldBeInitializedWithDefaultValue() throws Exception {
        assertEquals(AddressEndpoint.ELEMENT_NAME, entity.getTitle());
    }

    @Override
    public void elementNameShouldBeDefinedWithDefaultValue() throws Exception {
        assertEquals(AddressEndpoint.ELEMENT_NAME, entity.getElementName());
    }

    @Override
    public void serializeNameShouldBeDefinedWithDefaultValue() throws Exception {
        assertEquals(AddressEndpoint.SERIALIZATION_NAME, entity.getSerializationName());
    }

    @Override
    public void possibleToChangeBranchAmountParamShouldBeInitializedWithDefaultValue() throws Exception {
        assertFalse(entity.isPossibleToAddBranches());
    }

    @Override
    public void needToShowTitleAndIconParamShouldBeInitializedWithDefaultValue() throws Exception {
        assertTrue(entity.needsToShowIconAndTitle());
    }

    @Override
    public void elementIconShouldBeInitializedWithDefaultValue() throws Exception {
        verify(resources).address();
        assertEquals(icon, entity.getIcon());
    }

    @Override
    public void elementShouldBeNotEqualedForAnotherInstanceOfElement() throws Exception {
        AddressEndpoint otherElement = new AddressEndpoint(resources, branchProvider, elementCreatorsManager, propertyProvider);
        assertFalse(entity.equals(otherElement));
    }

    @Override
    public void constructorPrepareOperationShouldBeDone() throws Exception {
        assertDefaultConfiguration();
    }

    private void assertDefaultConfiguration() {
        assertConfiguration(FORMAT_DEFAULT_VALUE,
                            URI_DEFAULT_VALUE,
                            SUSPEND_ERROR_CODE_DEFAULT_VALUE,
                            SUSPEND_INITIAL_DURATION_DEFAULT_VALUE,
                            SUSPEND_MAXIMUM_DURATION_DEFAULT_VALUE,
                            SUSPEND_PROGRESSION_FACTORY_DEFAULT_VALUE,
                            RETRY_ERROR_CODES_DEFAULT_VALUE,
                            RETRY_COUNT_DEFAULT_VALUE,
                            RETRY_DELAY_DEFAULT_VALUE,
                            PROPERTIES_DEFAULT_VALUE,
                            OPTIMIZE_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_ENABLED_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_POLICY_DEFAULT_VALUE,
                            SECURITY_ENABLED_DEFAULT_VALUE,
                            SECURITY_POLICY_DEFAULT_VALUE,
                            ADDRESSING_ENABLED_DEFAULT_VALUE,
                            ADDRESSING_VERSION_DEFAULT_VALUE,
                            ADDRESSING_SEPARATE_LISTENER_DEFAULT_VALUE,
                            TIMEOUT_DURATION_DEFAULT_VALUE,
                            TIMEOUT_ACTION_DEFAULT_VALUE);
    }

    private void assertConfiguration(Format format,
                                     String uri,
                                     String suspendErrorCode,
                                     Integer suspendInitialDuration,
                                     Integer suspendMaximumDuration,
                                     Double suspendProgressionFactory,
                                     String retryErrorCodes,
                                     Integer retryCount,
                                     Integer retryDelay,
                                     List<Property> properties,
                                     Optimize optimize,
                                     String description,
                                     Boolean isReliableMessagingEnabled,
                                     String reliableMessagingPolicy,
                                     Boolean isSecurityEnabled,
                                     String securityPolicy,
                                     Boolean isAddressingEnabled,
                                     AddressingVersion addressingVersion,
                                     Boolean isAddressingSeparateListener,
                                     Integer timeoutDuration,
                                     TimeoutAction timeoutAction) {
        assertEquals(format, entity.getProperty(FORMAT));
        assertEquals(uri, entity.getProperty(URI));

        assertEquals(suspendErrorCode, entity.getProperty(SUSPEND_ERROR_CODE));
        assertEquals(suspendInitialDuration, entity.getProperty(SUSPEND_INITIAL_DURATION));
        assertEquals(suspendMaximumDuration, entity.getProperty(SUSPEND_MAXIMUM_DURATION));
        assertEquals(suspendProgressionFactory, entity.getProperty(SUSPEND_PROGRESSION_FACTORY));

        assertEquals(retryErrorCodes, entity.getProperty(RETRY_ERROR_CODES));
        assertEquals(retryCount, entity.getProperty(RETRY_COUNT));
        assertEquals(retryDelay, entity.getProperty(RETRY_DELAY));

        assertEquals(properties, entity.getProperty(PROPERTIES));
        assertEquals(optimize, entity.getProperty(OPTIMIZE));
        assertEquals(description, entity.getProperty(DESCRIPTION));

        assertEquals(isReliableMessagingEnabled, entity.getProperty(RELIABLE_MESSAGING_ENABLED));
        assertEquals(reliableMessagingPolicy, entity.getProperty(RELIABLE_MESSAGING_POLICY));

        assertEquals(isSecurityEnabled, entity.getProperty(SECURITY_ENABLED));
        assertEquals(securityPolicy, entity.getProperty(SECURITY_POLICY));

        assertEquals(isAddressingEnabled, entity.getProperty(ADDRESSING_ENABLED));
        assertEquals(addressingVersion, entity.getProperty(ADDRESSING_VERSION));
        assertEquals(isAddressingSeparateListener, entity.getProperty(ADDRESSING_SEPARATE_LISTENER));

        assertEquals(timeoutDuration, entity.getProperty(TIMEOUT_DURATION));
        assertEquals(timeoutAction, entity.getProperty(TIMEOUT_ACTION));
    }

    @Test
    public void serializationShouldBeDoneWithDefaultValues() throws Exception {
        assertContentAndValue(SERIALIZE_PATH + "DefaultSerialization", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneForDefaultRepresentation() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(DESERIALIZE_PATH + "DefaultDeserialization"));

        assertDefaultConfiguration();
    }

    @Test
    public void serializationShouldBeDoneWhenFormatPropertyIsChanged() throws Exception {
        entity.putProperty(FORMAT, SOUP11);

        assertContentAndValue(SERIALIZE_PATH + "FormatProperty", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenFormatPropertyIsChanged() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(DESERIALIZE_PATH + "FormatProperty"));

        assertConfiguration(SOUP11,
                            URI_DEFAULT_VALUE,
                            SUSPEND_ERROR_CODE_DEFAULT_VALUE,
                            SUSPEND_INITIAL_DURATION_DEFAULT_VALUE,
                            SUSPEND_MAXIMUM_DURATION_DEFAULT_VALUE,
                            SUSPEND_PROGRESSION_FACTORY_DEFAULT_VALUE,
                            RETRY_ERROR_CODES_DEFAULT_VALUE,
                            RETRY_COUNT_DEFAULT_VALUE,
                            RETRY_DELAY_DEFAULT_VALUE,
                            PROPERTIES_DEFAULT_VALUE,
                            OPTIMIZE_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_ENABLED_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_POLICY_DEFAULT_VALUE,
                            SECURITY_ENABLED_DEFAULT_VALUE,
                            SECURITY_POLICY_DEFAULT_VALUE,
                            ADDRESSING_ENABLED_DEFAULT_VALUE,
                            ADDRESSING_VERSION_DEFAULT_VALUE,
                            ADDRESSING_SEPARATE_LISTENER_DEFAULT_VALUE,
                            TIMEOUT_DURATION_DEFAULT_VALUE,
                            TIMEOUT_ACTION_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenURIPropertyIsChanged() throws Exception {
        entity.putProperty(URI, URI_VALUE);

        assertContentAndValue(SERIALIZE_PATH + "URIProperty", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenURIPropertyIsChanged() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(DESERIALIZE_PATH + "URIProperty"));

        assertConfiguration(FORMAT_DEFAULT_VALUE,
                            URI_VALUE,
                            SUSPEND_ERROR_CODE_DEFAULT_VALUE,
                            SUSPEND_INITIAL_DURATION_DEFAULT_VALUE,
                            SUSPEND_MAXIMUM_DURATION_DEFAULT_VALUE,
                            SUSPEND_PROGRESSION_FACTORY_DEFAULT_VALUE,
                            RETRY_ERROR_CODES_DEFAULT_VALUE,
                            RETRY_COUNT_DEFAULT_VALUE,
                            RETRY_DELAY_DEFAULT_VALUE,
                            PROPERTIES_DEFAULT_VALUE,
                            OPTIMIZE_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_ENABLED_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_POLICY_DEFAULT_VALUE,
                            SECURITY_ENABLED_DEFAULT_VALUE,
                            SECURITY_POLICY_DEFAULT_VALUE,
                            ADDRESSING_ENABLED_DEFAULT_VALUE,
                            ADDRESSING_VERSION_DEFAULT_VALUE,
                            ADDRESSING_SEPARATE_LISTENER_DEFAULT_VALUE,
                            TIMEOUT_DURATION_DEFAULT_VALUE,
                            TIMEOUT_ACTION_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenSuspendErrorCodePropertyIsChanged() throws Exception {
        entity.putProperty(SUSPEND_ERROR_CODE, SUSPEND_ERROR_CODE_VALUE);

        assertContentAndValue(SERIALIZE_PATH + "SuspendErrorCode", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenSuspendErrorCodePropertyIsChanged() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(DESERIALIZE_PATH + "SuspendErrorCode"));

        assertConfiguration(FORMAT_DEFAULT_VALUE,
                            URI_DEFAULT_VALUE,
                            SUSPEND_ERROR_CODE_VALUE,
                            SUSPEND_INITIAL_DURATION_DEFAULT_VALUE,
                            SUSPEND_MAXIMUM_DURATION_DEFAULT_VALUE,
                            SUSPEND_PROGRESSION_FACTORY_DEFAULT_VALUE,
                            RETRY_ERROR_CODES_DEFAULT_VALUE,
                            RETRY_COUNT_DEFAULT_VALUE,
                            RETRY_DELAY_DEFAULT_VALUE,
                            PROPERTIES_DEFAULT_VALUE,
                            OPTIMIZE_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_ENABLED_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_POLICY_DEFAULT_VALUE,
                            SECURITY_ENABLED_DEFAULT_VALUE,
                            SECURITY_POLICY_DEFAULT_VALUE,
                            ADDRESSING_ENABLED_DEFAULT_VALUE,
                            ADDRESSING_VERSION_DEFAULT_VALUE,
                            ADDRESSING_SEPARATE_LISTENER_DEFAULT_VALUE,
                            TIMEOUT_DURATION_DEFAULT_VALUE,
                            TIMEOUT_ACTION_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenSuspendInitialDurationPropertyIsChanged() throws Exception {
        entity.putProperty(SUSPEND_INITIAL_DURATION, SUSPEND_INITIAL_DURATION_VALUE);

        assertContentAndValue(SERIALIZE_PATH + "SuspendInitialDuration", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenSuspendInitialDurationPropertyIsChanged() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(DESERIALIZE_PATH + "SuspendInitialDuration"));

        assertConfiguration(FORMAT_DEFAULT_VALUE,
                            URI_DEFAULT_VALUE,
                            SUSPEND_ERROR_CODE_DEFAULT_VALUE,
                            SUSPEND_INITIAL_DURATION_VALUE,
                            SUSPEND_MAXIMUM_DURATION_DEFAULT_VALUE,
                            SUSPEND_PROGRESSION_FACTORY_DEFAULT_VALUE,
                            RETRY_ERROR_CODES_DEFAULT_VALUE,
                            RETRY_COUNT_DEFAULT_VALUE,
                            RETRY_DELAY_DEFAULT_VALUE,
                            PROPERTIES_DEFAULT_VALUE,
                            OPTIMIZE_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_ENABLED_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_POLICY_DEFAULT_VALUE,
                            SECURITY_ENABLED_DEFAULT_VALUE,
                            SECURITY_POLICY_DEFAULT_VALUE,
                            ADDRESSING_ENABLED_DEFAULT_VALUE,
                            ADDRESSING_VERSION_DEFAULT_VALUE,
                            ADDRESSING_SEPARATE_LISTENER_DEFAULT_VALUE,
                            TIMEOUT_DURATION_DEFAULT_VALUE,
                            TIMEOUT_ACTION_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeNotDoneWhenSuspendInitialDurationPropertyIsChangedButValueIsIncorrect() throws Exception {
        entity.putProperty(SUSPEND_INITIAL_DURATION, -1);

        assertContentAndValue(SERIALIZE_PATH + "DefaultSerialization", entity.serialize());
    }

    @Test
    public void serializationShouldBeNotDoneWhenSuspendMaxDurationIsChangedAndBlockIsNotShown() throws Exception {
        entity.putProperty(SUSPEND_MAXIMUM_DURATION, SUSPEND_MAXIMUM_DURATION_VALUE);

        assertContentAndValue(SERIALIZE_PATH + "DefaultSerialization", entity.serialize());
    }

    @Test
    public void serializationShouldBeNotDoneWhenSuspendMaxDurationIsChangedButValueIsIncorrect() throws Exception {
        entity.putProperty(SUSPEND_INITIAL_DURATION, SUSPEND_INITIAL_DURATION_VALUE);
        entity.putProperty(SUSPEND_MAXIMUM_DURATION, -1);

        assertContentAndValue(SERIALIZE_PATH + "SuspendMaximumDurationEmpty", entity.serialize());
    }

    @Test
    public void serializationShouldBeDoneWhenSuspendMaxDurationIsChanged() throws Exception {
        entity.putProperty(SUSPEND_INITIAL_DURATION, SUSPEND_INITIAL_DURATION_VALUE);
        entity.putProperty(SUSPEND_MAXIMUM_DURATION, SUSPEND_MAXIMUM_DURATION_VALUE);

        assertContentAndValue(SERIALIZE_PATH + "SuspendMaximumDuration", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenSuspendMaxDurationIsChanged() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(DESERIALIZE_PATH + "SuspendMaximumDuration"));

        assertConfiguration(FORMAT_DEFAULT_VALUE,
                            URI_DEFAULT_VALUE,
                            SUSPEND_ERROR_CODE_DEFAULT_VALUE,
                            SUSPEND_INITIAL_DURATION_VALUE,
                            SUSPEND_MAXIMUM_DURATION_VALUE,
                            SUSPEND_PROGRESSION_FACTORY_DEFAULT_VALUE,
                            RETRY_ERROR_CODES_DEFAULT_VALUE,
                            RETRY_COUNT_DEFAULT_VALUE,
                            RETRY_DELAY_DEFAULT_VALUE,
                            PROPERTIES_DEFAULT_VALUE,
                            OPTIMIZE_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_ENABLED_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_POLICY_DEFAULT_VALUE,
                            SECURITY_ENABLED_DEFAULT_VALUE,
                            SECURITY_POLICY_DEFAULT_VALUE,
                            ADDRESSING_ENABLED_DEFAULT_VALUE,
                            ADDRESSING_VERSION_DEFAULT_VALUE,
                            ADDRESSING_SEPARATE_LISTENER_DEFAULT_VALUE,
                            TIMEOUT_DURATION_DEFAULT_VALUE,
                            TIMEOUT_ACTION_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenSuspendMaxDurationIsChanged2() throws Exception {
        entity.putProperty(SUSPEND_ERROR_CODE, SUSPEND_ERROR_CODE_VALUE);
        entity.putProperty(SUSPEND_MAXIMUM_DURATION, SUSPEND_MAXIMUM_DURATION_VALUE);

        assertContentAndValue(SERIALIZE_PATH + "SuspendMaximumDuration2", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenSuspendMaxDurationIsChanged2() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(DESERIALIZE_PATH + "SuspendMaximumDuration2"));

        assertConfiguration(FORMAT_DEFAULT_VALUE,
                            URI_DEFAULT_VALUE,
                            SUSPEND_ERROR_CODE_VALUE,
                            SUSPEND_INITIAL_DURATION_DEFAULT_VALUE,
                            SUSPEND_MAXIMUM_DURATION_VALUE,
                            SUSPEND_PROGRESSION_FACTORY_DEFAULT_VALUE,
                            RETRY_ERROR_CODES_DEFAULT_VALUE,
                            RETRY_COUNT_DEFAULT_VALUE,
                            RETRY_DELAY_DEFAULT_VALUE,
                            PROPERTIES_DEFAULT_VALUE,
                            OPTIMIZE_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_ENABLED_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_POLICY_DEFAULT_VALUE,
                            SECURITY_ENABLED_DEFAULT_VALUE,
                            SECURITY_POLICY_DEFAULT_VALUE,
                            ADDRESSING_ENABLED_DEFAULT_VALUE,
                            ADDRESSING_VERSION_DEFAULT_VALUE,
                            ADDRESSING_SEPARATE_LISTENER_DEFAULT_VALUE,
                            TIMEOUT_DURATION_DEFAULT_VALUE,
                            TIMEOUT_ACTION_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeNotDoneWhenSuspendProgressionFactoryIsChangedAndBlockIsNotShown() throws Exception {
        entity.putProperty(SUSPEND_PROGRESSION_FACTORY, SUSPEND_PROGRESSION_FACTORY_VALUE);

        assertContentAndValue(SERIALIZE_PATH + "DefaultSerialization", entity.serialize());
    }

    @Test
    public void serializationShouldBeNotDoneWhenSuspendProgressionFactoryIsChangedButValueIsIncorrect() throws Exception {
        entity.putProperty(SUSPEND_INITIAL_DURATION, SUSPEND_INITIAL_DURATION_VALUE);
        entity.putProperty(SUSPEND_PROGRESSION_FACTORY, -1.);

        assertContentAndValue(SERIALIZE_PATH + "SuspendInitialDuration", entity.serialize());
    }

    @Test
    public void serializationShouldBeDoneWhenSuspendProgressionFactoryIsChanged() throws Exception {
        entity.putProperty(SUSPEND_INITIAL_DURATION, SUSPEND_INITIAL_DURATION_VALUE);
        entity.putProperty(SUSPEND_PROGRESSION_FACTORY, SUSPEND_PROGRESSION_FACTORY_VALUE);

        assertContentAndValue(SERIALIZE_PATH + "SuspendProgressionFactory", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenSuspendProgressionFactoryIsChanged() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(DESERIALIZE_PATH + "SuspendProgressionFactory"));

        assertConfiguration(FORMAT_DEFAULT_VALUE,
                            URI_DEFAULT_VALUE,
                            SUSPEND_ERROR_CODE_DEFAULT_VALUE,
                            SUSPEND_INITIAL_DURATION_VALUE,
                            SUSPEND_MAXIMUM_DURATION_DEFAULT_VALUE,
                            SUSPEND_PROGRESSION_FACTORY_VALUE,
                            RETRY_ERROR_CODES_DEFAULT_VALUE,
                            RETRY_COUNT_DEFAULT_VALUE,
                            RETRY_DELAY_DEFAULT_VALUE,
                            PROPERTIES_DEFAULT_VALUE,
                            OPTIMIZE_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_ENABLED_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_POLICY_DEFAULT_VALUE,
                            SECURITY_ENABLED_DEFAULT_VALUE,
                            SECURITY_POLICY_DEFAULT_VALUE,
                            ADDRESSING_ENABLED_DEFAULT_VALUE,
                            ADDRESSING_VERSION_DEFAULT_VALUE,
                            ADDRESSING_SEPARATE_LISTENER_DEFAULT_VALUE,
                            TIMEOUT_DURATION_DEFAULT_VALUE,
                            TIMEOUT_ACTION_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenSuspendProgressionFactoryIsChanged2() throws Exception {
        entity.putProperty(SUSPEND_ERROR_CODE, SUSPEND_ERROR_CODE_VALUE);
        entity.putProperty(SUSPEND_PROGRESSION_FACTORY, SUSPEND_PROGRESSION_FACTORY_VALUE);

        assertContentAndValue(SERIALIZE_PATH + "SuspendProgressionFactory2", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenSuspendProgressionFactoryIsChanged2() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(DESERIALIZE_PATH + "SuspendProgressionFactory2"));

        assertConfiguration(FORMAT_DEFAULT_VALUE,
                            URI_DEFAULT_VALUE,
                            SUSPEND_ERROR_CODE_VALUE,
                            SUSPEND_INITIAL_DURATION_DEFAULT_VALUE,
                            SUSPEND_MAXIMUM_DURATION_DEFAULT_VALUE,
                            SUSPEND_PROGRESSION_FACTORY_VALUE,
                            RETRY_ERROR_CODES_DEFAULT_VALUE,
                            RETRY_COUNT_DEFAULT_VALUE,
                            RETRY_DELAY_DEFAULT_VALUE,
                            PROPERTIES_DEFAULT_VALUE,
                            OPTIMIZE_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_ENABLED_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_POLICY_DEFAULT_VALUE,
                            SECURITY_ENABLED_DEFAULT_VALUE,
                            SECURITY_POLICY_DEFAULT_VALUE,
                            ADDRESSING_ENABLED_DEFAULT_VALUE,
                            ADDRESSING_VERSION_DEFAULT_VALUE,
                            ADDRESSING_SEPARATE_LISTENER_DEFAULT_VALUE,
                            TIMEOUT_DURATION_DEFAULT_VALUE,
                            TIMEOUT_ACTION_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenRetryErrorCodesIsChanged() throws Exception {
        entity.putProperty(RETRY_ERROR_CODES, RETRY_ERROR_CODES_VALUE);

        assertContentAndValue(SERIALIZE_PATH + "RetryErrorCodes", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenRetryErrorCodesIsChanged() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(DESERIALIZE_PATH + "RetryErrorCodes"));

        assertConfiguration(FORMAT_DEFAULT_VALUE,
                            URI_DEFAULT_VALUE,
                            SUSPEND_ERROR_CODE_DEFAULT_VALUE,
                            SUSPEND_INITIAL_DURATION_DEFAULT_VALUE,
                            SUSPEND_MAXIMUM_DURATION_DEFAULT_VALUE,
                            SUSPEND_PROGRESSION_FACTORY_DEFAULT_VALUE,
                            RETRY_ERROR_CODES_VALUE,
                            RETRY_COUNT_DEFAULT_VALUE,
                            RETRY_DELAY_DEFAULT_VALUE,
                            PROPERTIES_DEFAULT_VALUE,
                            OPTIMIZE_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_ENABLED_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_POLICY_DEFAULT_VALUE,
                            SECURITY_ENABLED_DEFAULT_VALUE,
                            SECURITY_POLICY_DEFAULT_VALUE,
                            ADDRESSING_ENABLED_DEFAULT_VALUE,
                            ADDRESSING_VERSION_DEFAULT_VALUE,
                            ADDRESSING_SEPARATE_LISTENER_DEFAULT_VALUE,
                            TIMEOUT_DURATION_DEFAULT_VALUE,
                            TIMEOUT_ACTION_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenRetryCountIsChangedButGroupIsNotShown() throws Exception {
        entity.putProperty(RETRY_COUNT, RETRY_COUNT_VALUE);

        assertContentAndValue(SERIALIZE_PATH + "DefaultSerialization", entity.serialize());
    }

    @Test
    public void serializationShouldBeDoneWhenRetryCountIsChangedButValueIsIncorrect() throws Exception {
        entity.putProperty(RETRY_ERROR_CODES, RETRY_ERROR_CODES_VALUE);
        entity.putProperty(RETRY_COUNT, -1);

        assertContentAndValue(SERIALIZE_PATH + "RetryErrorCodes", entity.serialize());
    }

    @Test
    public void serializationShouldBeDoneWhenRetryCountIsChanged() throws Exception {
        entity.putProperty(RETRY_ERROR_CODES, RETRY_ERROR_CODES_VALUE);
        entity.putProperty(RETRY_COUNT, RETRY_COUNT_VALUE);

        assertContentAndValue(SERIALIZE_PATH + "RetryCount", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenRetryCountIsChanged() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(DESERIALIZE_PATH + "RetryCount"));

        assertConfiguration(FORMAT_DEFAULT_VALUE,
                            URI_DEFAULT_VALUE,
                            SUSPEND_ERROR_CODE_DEFAULT_VALUE,
                            SUSPEND_INITIAL_DURATION_DEFAULT_VALUE,
                            SUSPEND_MAXIMUM_DURATION_DEFAULT_VALUE,
                            SUSPEND_PROGRESSION_FACTORY_DEFAULT_VALUE,
                            RETRY_ERROR_CODES_VALUE,
                            RETRY_COUNT_VALUE,
                            RETRY_DELAY_DEFAULT_VALUE,
                            PROPERTIES_DEFAULT_VALUE,
                            OPTIMIZE_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_ENABLED_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_POLICY_DEFAULT_VALUE,
                            SECURITY_ENABLED_DEFAULT_VALUE,
                            SECURITY_POLICY_DEFAULT_VALUE,
                            ADDRESSING_ENABLED_DEFAULT_VALUE,
                            ADDRESSING_VERSION_DEFAULT_VALUE,
                            ADDRESSING_SEPARATE_LISTENER_DEFAULT_VALUE,
                            TIMEOUT_DURATION_DEFAULT_VALUE,
                            TIMEOUT_ACTION_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenRetryCountIsChanged2() throws Exception {
        entity.putProperty(RETRY_DELAY, RETRY_DELAY_VALUE);
        entity.putProperty(RETRY_COUNT, RETRY_COUNT_VALUE);

        assertContentAndValue(SERIALIZE_PATH + "RetryCount2", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenRetryCountIsChanged2() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(DESERIALIZE_PATH + "RetryCount2"));

        assertConfiguration(FORMAT_DEFAULT_VALUE,
                            URI_DEFAULT_VALUE,
                            SUSPEND_ERROR_CODE_DEFAULT_VALUE,
                            SUSPEND_INITIAL_DURATION_DEFAULT_VALUE,
                            SUSPEND_MAXIMUM_DURATION_DEFAULT_VALUE,
                            SUSPEND_PROGRESSION_FACTORY_DEFAULT_VALUE,
                            RETRY_ERROR_CODES_DEFAULT_VALUE,
                            RETRY_COUNT_VALUE,
                            RETRY_DELAY_VALUE,
                            PROPERTIES_DEFAULT_VALUE,
                            OPTIMIZE_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_ENABLED_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_POLICY_DEFAULT_VALUE,
                            SECURITY_ENABLED_DEFAULT_VALUE,
                            SECURITY_POLICY_DEFAULT_VALUE,
                            ADDRESSING_ENABLED_DEFAULT_VALUE,
                            ADDRESSING_VERSION_DEFAULT_VALUE,
                            ADDRESSING_SEPARATE_LISTENER_DEFAULT_VALUE,
                            TIMEOUT_DURATION_DEFAULT_VALUE,
                            TIMEOUT_ACTION_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenRetryDelayIsChangedButValueIsIncorrect() throws Exception {
        entity.putProperty(RETRY_DELAY, -1);

        assertContentAndValue(SERIALIZE_PATH + "DefaultSerialization", entity.serialize());
    }

    @Test
    public void serializationShouldBeDoneWhenRetryDelayIsChanged() throws Exception {
        entity.putProperty(RETRY_DELAY, RETRY_DELAY_VALUE);

        assertContentAndValue(SERIALIZE_PATH + "RetryDelay", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenRetryDelayIsChanged() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(DESERIALIZE_PATH + "RetryDelay"));

        assertConfiguration(FORMAT_DEFAULT_VALUE,
                            URI_DEFAULT_VALUE,
                            SUSPEND_ERROR_CODE_DEFAULT_VALUE,
                            SUSPEND_INITIAL_DURATION_DEFAULT_VALUE,
                            SUSPEND_MAXIMUM_DURATION_DEFAULT_VALUE,
                            SUSPEND_PROGRESSION_FACTORY_DEFAULT_VALUE,
                            RETRY_ERROR_CODES_DEFAULT_VALUE,
                            RETRY_COUNT_DEFAULT_VALUE,
                            RETRY_DELAY_VALUE,
                            PROPERTIES_DEFAULT_VALUE,
                            OPTIMIZE_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_ENABLED_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_POLICY_DEFAULT_VALUE,
                            SECURITY_ENABLED_DEFAULT_VALUE,
                            SECURITY_POLICY_DEFAULT_VALUE,
                            ADDRESSING_ENABLED_DEFAULT_VALUE,
                            ADDRESSING_VERSION_DEFAULT_VALUE,
                            ADDRESSING_SEPARATE_LISTENER_DEFAULT_VALUE,
                            TIMEOUT_DURATION_DEFAULT_VALUE,
                            TIMEOUT_ACTION_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenPropertiesParamIsChanged() throws Exception {
        when(property.serialize()).thenReturn(PROPERTY_SERIALIZE_CONTENT);
        entity.putProperty(PROPERTIES, Arrays.asList(property));

        assertContentAndValue(SERIALIZE_PATH + "Properties", entity.serialize());
        verify(property).serialize();
    }

    @Test
    public void deserializationShouldBeDoneWhenPropertiesParamIsChanged() throws Exception {
        assertDefaultConfiguration();

        List<Property> properties = Arrays.asList(property);

        entity.deserialize(getContent(DESERIALIZE_PATH + "Properties"));

        verify(property).deserialize(any(Node.class));
        assertConfiguration(FORMAT_DEFAULT_VALUE,
                            URI_DEFAULT_VALUE,
                            SUSPEND_ERROR_CODE_DEFAULT_VALUE,
                            SUSPEND_INITIAL_DURATION_DEFAULT_VALUE,
                            SUSPEND_MAXIMUM_DURATION_DEFAULT_VALUE,
                            SUSPEND_PROGRESSION_FACTORY_DEFAULT_VALUE,
                            RETRY_ERROR_CODES_DEFAULT_VALUE,
                            RETRY_COUNT_DEFAULT_VALUE,
                            RETRY_DELAY_DEFAULT_VALUE,
                            properties,
                            OPTIMIZE_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_ENABLED_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_POLICY_DEFAULT_VALUE,
                            SECURITY_ENABLED_DEFAULT_VALUE,
                            SECURITY_POLICY_DEFAULT_VALUE,
                            ADDRESSING_ENABLED_DEFAULT_VALUE,
                            ADDRESSING_VERSION_DEFAULT_VALUE,
                            ADDRESSING_SEPARATE_LISTENER_DEFAULT_VALUE,
                            TIMEOUT_DURATION_DEFAULT_VALUE,
                            TIMEOUT_ACTION_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenOptimizeIsChanged() throws Exception {
        entity.putProperty(OPTIMIZE, MTOM);

        assertContentAndValue(SERIALIZE_PATH + "Optimize", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenOptimizeIsChanged() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(DESERIALIZE_PATH + "Optimize"));

        assertConfiguration(FORMAT_DEFAULT_VALUE,
                            URI_DEFAULT_VALUE,
                            SUSPEND_ERROR_CODE_DEFAULT_VALUE,
                            SUSPEND_INITIAL_DURATION_DEFAULT_VALUE,
                            SUSPEND_MAXIMUM_DURATION_DEFAULT_VALUE,
                            SUSPEND_PROGRESSION_FACTORY_DEFAULT_VALUE,
                            RETRY_ERROR_CODES_DEFAULT_VALUE,
                            RETRY_COUNT_DEFAULT_VALUE,
                            RETRY_DELAY_DEFAULT_VALUE,
                            PROPERTIES_DEFAULT_VALUE,
                            MTOM,
                            DESCRIPTION_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_ENABLED_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_POLICY_DEFAULT_VALUE,
                            SECURITY_ENABLED_DEFAULT_VALUE,
                            SECURITY_POLICY_DEFAULT_VALUE,
                            ADDRESSING_ENABLED_DEFAULT_VALUE,
                            ADDRESSING_VERSION_DEFAULT_VALUE,
                            ADDRESSING_SEPARATE_LISTENER_DEFAULT_VALUE,
                            TIMEOUT_DURATION_DEFAULT_VALUE,
                            TIMEOUT_ACTION_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenDescriptionIsChanged() throws Exception {
        entity.putProperty(DESCRIPTION, DESCRIPTION_VALUE);

        assertContentAndValue(SERIALIZE_PATH + "Description", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenDescriptionIsChanged() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(DESERIALIZE_PATH + "Description"));

        assertConfiguration(FORMAT_DEFAULT_VALUE,
                            URI_DEFAULT_VALUE,
                            SUSPEND_ERROR_CODE_DEFAULT_VALUE,
                            SUSPEND_INITIAL_DURATION_DEFAULT_VALUE,
                            SUSPEND_MAXIMUM_DURATION_DEFAULT_VALUE,
                            SUSPEND_PROGRESSION_FACTORY_DEFAULT_VALUE,
                            RETRY_ERROR_CODES_DEFAULT_VALUE,
                            RETRY_COUNT_DEFAULT_VALUE,
                            RETRY_DELAY_DEFAULT_VALUE,
                            PROPERTIES_DEFAULT_VALUE,
                            OPTIMIZE_DEFAULT_VALUE,
                            DESCRIPTION_VALUE,
                            RELIABLE_MESSAGING_ENABLED_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_POLICY_DEFAULT_VALUE,
                            SECURITY_ENABLED_DEFAULT_VALUE,
                            SECURITY_POLICY_DEFAULT_VALUE,
                            ADDRESSING_ENABLED_DEFAULT_VALUE,
                            ADDRESSING_VERSION_DEFAULT_VALUE,
                            ADDRESSING_SEPARATE_LISTENER_DEFAULT_VALUE,
                            TIMEOUT_DURATION_DEFAULT_VALUE,
                            TIMEOUT_ACTION_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeDoneWhenReliableMessageEnabledIsChanged() throws Exception {
        entity.putProperty(RELIABLE_MESSAGING_ENABLED, true);

        assertContentAndValue(SERIALIZE_PATH + "ReliableMessageEnabled", entity.serialize());
    }

    @Test
    public void serializationShouldBeDoneWhenReliableMessagePolicyIsChanged() throws Exception {
        entity.putProperty(RELIABLE_MESSAGING_ENABLED, true);
        entity.putProperty(RELIABLE_MESSAGING_POLICY, RELIABLE_MESSAGING_POLICY_VALUE);

        assertContentAndValue(SERIALIZE_PATH + "ReliableMessagePolicy", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenReliableMessagePolicyIsChanged() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(DESERIALIZE_PATH + "ReliableMessagePolicy"));

        assertConfiguration(FORMAT_DEFAULT_VALUE,
                            URI_DEFAULT_VALUE,
                            SUSPEND_ERROR_CODE_DEFAULT_VALUE,
                            SUSPEND_INITIAL_DURATION_DEFAULT_VALUE,
                            SUSPEND_MAXIMUM_DURATION_DEFAULT_VALUE,
                            SUSPEND_PROGRESSION_FACTORY_DEFAULT_VALUE,
                            RETRY_ERROR_CODES_DEFAULT_VALUE,
                            RETRY_COUNT_DEFAULT_VALUE,
                            RETRY_DELAY_DEFAULT_VALUE,
                            PROPERTIES_DEFAULT_VALUE,
                            OPTIMIZE_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            true,
                            RELIABLE_MESSAGING_POLICY_VALUE,
                            SECURITY_ENABLED_DEFAULT_VALUE,
                            SECURITY_POLICY_DEFAULT_VALUE,
                            ADDRESSING_ENABLED_DEFAULT_VALUE,
                            ADDRESSING_VERSION_DEFAULT_VALUE,
                            ADDRESSING_SEPARATE_LISTENER_DEFAULT_VALUE,
                            TIMEOUT_DURATION_DEFAULT_VALUE,
                            TIMEOUT_ACTION_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeNotDoneWhenReliableMessagePolicyIsChangedButGroupIsNotShown() throws Exception {
        entity.putProperty(RELIABLE_MESSAGING_POLICY, RELIABLE_MESSAGING_POLICY_VALUE);

        assertContentAndValue(SERIALIZE_PATH + "DefaultSerialization", entity.serialize());
    }

    @Test
    public void serializationShouldBeDoneWhenSecurityEnabledIsChanged() throws Exception {
        entity.putProperty(SECURITY_ENABLED, true);

        assertContentAndValue(SERIALIZE_PATH + "SecurityEnabled", entity.serialize());
    }

    @Test
    public void serializationShouldBeDoneWhenSecurityPolicyIsChanged() throws Exception {
        entity.putProperty(SECURITY_ENABLED, true);
        entity.putProperty(SECURITY_POLICY, SECURITY_POLICY_VALUE);

        assertContentAndValue(SERIALIZE_PATH + "SecurityPolicy", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenSecurityPolicyIsChanged() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(DESERIALIZE_PATH + "SecurityPolicy"));

        assertConfiguration(FORMAT_DEFAULT_VALUE,
                            URI_DEFAULT_VALUE,
                            SUSPEND_ERROR_CODE_DEFAULT_VALUE,
                            SUSPEND_INITIAL_DURATION_DEFAULT_VALUE,
                            SUSPEND_MAXIMUM_DURATION_DEFAULT_VALUE,
                            SUSPEND_PROGRESSION_FACTORY_DEFAULT_VALUE,
                            RETRY_ERROR_CODES_DEFAULT_VALUE,
                            RETRY_COUNT_DEFAULT_VALUE,
                            RETRY_DELAY_DEFAULT_VALUE,
                            PROPERTIES_DEFAULT_VALUE,
                            OPTIMIZE_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_ENABLED_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_POLICY_DEFAULT_VALUE,
                            true,
                            SECURITY_POLICY_VALUE,
                            ADDRESSING_ENABLED_DEFAULT_VALUE,
                            ADDRESSING_VERSION_DEFAULT_VALUE,
                            ADDRESSING_SEPARATE_LISTENER_DEFAULT_VALUE,
                            TIMEOUT_DURATION_DEFAULT_VALUE,
                            TIMEOUT_ACTION_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeNotDoneWhenSecurityPolicyIsChangedButGroupIsNotShown() throws Exception {
        entity.putProperty(SECURITY_POLICY, SECURITY_POLICY_VALUE);

        assertContentAndValue(SERIALIZE_PATH + "DefaultSerialization", entity.serialize());
    }

    @Test
    public void serializationShouldBeDoneWhenAddressingEnabledIsChanged() throws Exception {
        entity.putProperty(ADDRESSING_ENABLED, true);

        assertContentAndValue(SERIALIZE_PATH + "AddressingEnabled", entity.serialize());
    }

    @Test
    public void serializationShouldBeDoneWhenAddressingVersionIsChanged() throws Exception {
        entity.putProperty(ADDRESSING_ENABLED, true);
        entity.putProperty(ADDRESSING_VERSION, SUBMISSION);

        assertContentAndValue(SERIALIZE_PATH + "AddressingVersion", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenAddressingVersionIsChanged() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(DESERIALIZE_PATH + "AddressingVersion"));

        assertConfiguration(FORMAT_DEFAULT_VALUE,
                            URI_DEFAULT_VALUE,
                            SUSPEND_ERROR_CODE_DEFAULT_VALUE,
                            SUSPEND_INITIAL_DURATION_DEFAULT_VALUE,
                            SUSPEND_MAXIMUM_DURATION_DEFAULT_VALUE,
                            SUSPEND_PROGRESSION_FACTORY_DEFAULT_VALUE,
                            RETRY_ERROR_CODES_DEFAULT_VALUE,
                            RETRY_COUNT_DEFAULT_VALUE,
                            RETRY_DELAY_DEFAULT_VALUE,
                            PROPERTIES_DEFAULT_VALUE,
                            OPTIMIZE_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_ENABLED_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_POLICY_DEFAULT_VALUE,
                            SECURITY_ENABLED_DEFAULT_VALUE,
                            SECURITY_POLICY_DEFAULT_VALUE,
                            true,
                            SUBMISSION,
                            ADDRESSING_SEPARATE_LISTENER_DEFAULT_VALUE,
                            TIMEOUT_DURATION_DEFAULT_VALUE,
                            TIMEOUT_ACTION_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeNotDoneWhenAddressingVersionIsChangedButGroupIsNotShown() throws Exception {
        entity.putProperty(ADDRESSING_VERSION, SUBMISSION);

        assertContentAndValue(SERIALIZE_PATH + "DefaultSerialization", entity.serialize());
    }

    @Test
    public void serializationShouldBeDoneWhenAddressingSeparateListenerIsChanged() throws Exception {
        entity.putProperty(ADDRESSING_ENABLED, true);
        entity.putProperty(ADDRESSING_SEPARATE_LISTENER, true);

        assertContentAndValue(SERIALIZE_PATH + "AddressingSeparateListener", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenAddressingSeparateListenerIsChanged() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(DESERIALIZE_PATH + "AddressingSeparateListener"));

        assertConfiguration(FORMAT_DEFAULT_VALUE,
                            URI_DEFAULT_VALUE,
                            SUSPEND_ERROR_CODE_DEFAULT_VALUE,
                            SUSPEND_INITIAL_DURATION_DEFAULT_VALUE,
                            SUSPEND_MAXIMUM_DURATION_DEFAULT_VALUE,
                            SUSPEND_PROGRESSION_FACTORY_DEFAULT_VALUE,
                            RETRY_ERROR_CODES_DEFAULT_VALUE,
                            RETRY_COUNT_DEFAULT_VALUE,
                            RETRY_DELAY_DEFAULT_VALUE,
                            PROPERTIES_DEFAULT_VALUE,
                            OPTIMIZE_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_ENABLED_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_POLICY_DEFAULT_VALUE,
                            SECURITY_ENABLED_DEFAULT_VALUE,
                            SECURITY_POLICY_DEFAULT_VALUE,
                            true,
                            ADDRESSING_VERSION_DEFAULT_VALUE,
                            true,
                            TIMEOUT_DURATION_DEFAULT_VALUE,
                            TIMEOUT_ACTION_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeNotDoneWhenAddressingSeparateListenerIsChangedButGroupIsNotShown() throws Exception {
        entity.putProperty(ADDRESSING_SEPARATE_LISTENER, true);

        assertContentAndValue(SERIALIZE_PATH + "DefaultSerialization", entity.serialize());
    }

    @Test
    public void serializationShouldBeDoneWhenTimeoutDurationIsChanged() throws Exception {
        entity.putProperty(TIMEOUT_DURATION, TIMEOUT_DURATION_VALUE);

        assertContentAndValue(SERIALIZE_PATH + "TimeoutDuration", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenTimeoutDurationIsChanged() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(DESERIALIZE_PATH + "TimeoutDuration"));

        assertConfiguration(FORMAT_DEFAULT_VALUE,
                            URI_DEFAULT_VALUE,
                            SUSPEND_ERROR_CODE_DEFAULT_VALUE,
                            SUSPEND_INITIAL_DURATION_DEFAULT_VALUE,
                            SUSPEND_MAXIMUM_DURATION_DEFAULT_VALUE,
                            SUSPEND_PROGRESSION_FACTORY_DEFAULT_VALUE,
                            RETRY_ERROR_CODES_DEFAULT_VALUE,
                            RETRY_COUNT_DEFAULT_VALUE,
                            RETRY_DELAY_DEFAULT_VALUE,
                            PROPERTIES_DEFAULT_VALUE,
                            OPTIMIZE_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_ENABLED_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_POLICY_DEFAULT_VALUE,
                            SECURITY_ENABLED_DEFAULT_VALUE,
                            SECURITY_POLICY_DEFAULT_VALUE,
                            ADDRESSING_ENABLED_DEFAULT_VALUE,
                            ADDRESSING_VERSION_DEFAULT_VALUE,
                            ADDRESSING_SEPARATE_LISTENER_DEFAULT_VALUE,
                            TIMEOUT_DURATION_VALUE,
                            TIMEOUT_ACTION_DEFAULT_VALUE);
    }

    @Test
    public void serializationShouldBeNotDoneWhenTimeoutDurationIsChangedButValueIsIncorrect() throws Exception {
        entity.putProperty(TIMEOUT_DURATION, -1);

        assertContentAndValue(SERIALIZE_PATH + "DefaultSerialization", entity.serialize());
    }

    @Test
    public void serializationShouldBeDoneWhenTimeoutActionIsChanged() throws Exception {
        entity.putProperty(TIMEOUT_ACTION, DISCARD);

        assertContentAndValue(SERIALIZE_PATH + "TimeoutAction", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenTimeoutActionIsChanged() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(DESERIALIZE_PATH + "TimeoutAction"));

        assertConfiguration(FORMAT_DEFAULT_VALUE,
                            URI_DEFAULT_VALUE,
                            SUSPEND_ERROR_CODE_DEFAULT_VALUE,
                            SUSPEND_INITIAL_DURATION_DEFAULT_VALUE,
                            SUSPEND_MAXIMUM_DURATION_DEFAULT_VALUE,
                            SUSPEND_PROGRESSION_FACTORY_DEFAULT_VALUE,
                            RETRY_ERROR_CODES_DEFAULT_VALUE,
                            RETRY_COUNT_DEFAULT_VALUE,
                            RETRY_DELAY_DEFAULT_VALUE,
                            PROPERTIES_DEFAULT_VALUE,
                            OPTIMIZE_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_ENABLED_DEFAULT_VALUE,
                            RELIABLE_MESSAGING_POLICY_DEFAULT_VALUE,
                            SECURITY_ENABLED_DEFAULT_VALUE,
                            SECURITY_POLICY_DEFAULT_VALUE,
                            ADDRESSING_ENABLED_DEFAULT_VALUE,
                            ADDRESSING_VERSION_DEFAULT_VALUE,
                            ADDRESSING_SEPARATE_LISTENER_DEFAULT_VALUE,
                            TIMEOUT_DURATION_DEFAULT_VALUE,
                            DISCARD);
    }

    @Test
    public void serializationShouldBeDoneWhenAllPropertiesAreAdded() throws Exception {
        entity.putProperty(FORMAT, SOUP11);
        entity.putProperty(URI, URI_VALUE);

        entity.putProperty(SUSPEND_ERROR_CODE, SUSPEND_ERROR_CODE_VALUE);
        entity.putProperty(SUSPEND_INITIAL_DURATION, SUSPEND_INITIAL_DURATION_VALUE);
        entity.putProperty(SUSPEND_MAXIMUM_DURATION, SUSPEND_MAXIMUM_DURATION_VALUE);
        entity.putProperty(SUSPEND_PROGRESSION_FACTORY, SUSPEND_PROGRESSION_FACTORY_VALUE);

        entity.putProperty(RETRY_ERROR_CODES, RETRY_ERROR_CODES_VALUE);
        entity.putProperty(RETRY_DELAY, RETRY_DELAY_VALUE);
        entity.putProperty(RETRY_COUNT, RETRY_COUNT_VALUE);

        when(property.serialize()).thenReturn(PROPERTY_SERIALIZE_CONTENT);
        entity.putProperty(PROPERTIES, Arrays.asList(property));
        entity.putProperty(OPTIMIZE, MTOM);
        entity.putProperty(DESCRIPTION, DESCRIPTION_VALUE);

        entity.putProperty(RELIABLE_MESSAGING_ENABLED, true);
        entity.putProperty(RELIABLE_MESSAGING_POLICY, RELIABLE_MESSAGING_POLICY_VALUE);

        entity.putProperty(SECURITY_ENABLED, true);
        entity.putProperty(SECURITY_POLICY, SECURITY_POLICY_VALUE);

        entity.putProperty(ADDRESSING_ENABLED, true);
        entity.putProperty(ADDRESSING_VERSION, SUBMISSION);
        entity.putProperty(ADDRESSING_SEPARATE_LISTENER, true);

        entity.putProperty(TIMEOUT_DURATION, TIMEOUT_DURATION_VALUE);
        entity.putProperty(TIMEOUT_ACTION, DISCARD);

        assertContentAndValue(SERIALIZE_PATH + "FullSerialization", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenAllPropertiesAreAdded() throws Exception {
        assertDefaultConfiguration();

        List<Property> properties = Arrays.asList(property);

        entity.deserialize(getContent(DESERIALIZE_PATH + "FullDeserialization"));

        verify(property).deserialize(any(Node.class));

        assertConfiguration(SOUP11,
                            URI_VALUE,
                            SUSPEND_ERROR_CODE_VALUE,
                            SUSPEND_INITIAL_DURATION_VALUE,
                            SUSPEND_MAXIMUM_DURATION_VALUE,
                            SUSPEND_PROGRESSION_FACTORY_VALUE,
                            RETRY_ERROR_CODES_VALUE,
                            RETRY_COUNT_VALUE,
                            RETRY_DELAY_VALUE,
                            properties,
                            MTOM,
                            DESCRIPTION_VALUE,
                            true,
                            RELIABLE_MESSAGING_POLICY_VALUE,
                            true,
                            SECURITY_POLICY_VALUE,
                            true,
                            SUBMISSION,
                            true,
                            TIMEOUT_DURATION_VALUE,
                            DISCARD);
    }

    @Test
    public void deserializationShouldBeDoneWhenIncorrectPropertiesAreAdded() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(DESERIALIZE_PATH + "IncorrectValue"));

        assertConfiguration(FORMAT_DEFAULT_VALUE,
                            URI_DEFAULT_VALUE,
                            SUSPEND_ERROR_CODE_DEFAULT_VALUE,
                            SUSPEND_INITIAL_DURATION_DEFAULT_VALUE,
                            SUSPEND_MAXIMUM_DURATION_DEFAULT_VALUE,
                            SUSPEND_PROGRESSION_FACTORY_DEFAULT_VALUE,
                            RETRY_ERROR_CODES_DEFAULT_VALUE,
                            RETRY_COUNT_DEFAULT_VALUE,
                            RETRY_DELAY_DEFAULT_VALUE,
                            PROPERTIES_DEFAULT_VALUE,
                            OPTIMIZE_DEFAULT_VALUE,
                            DESCRIPTION_DEFAULT_VALUE,
                            true,
                            RELIABLE_MESSAGING_POLICY_DEFAULT_VALUE,
                            true,
                            SECURITY_POLICY_DEFAULT_VALUE,
                            true,
                            ADDRESSING_VERSION_DEFAULT_VALUE,
                            ADDRESSING_SEPARATE_LISTENER_DEFAULT_VALUE,
                            TIMEOUT_DURATION_DEFAULT_VALUE,
                            TIMEOUT_ACTION_DEFAULT_VALUE);
    }

}