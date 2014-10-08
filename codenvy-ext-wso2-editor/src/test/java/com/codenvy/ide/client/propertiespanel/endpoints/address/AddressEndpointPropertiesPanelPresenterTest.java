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

import com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint;
import com.codenvy.ide.client.elements.endpoints.address.Property;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanelTest;
import com.codenvy.ide.client.propertiespanel.endpoints.address.property.PropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.list.ListPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.ADDRESSING_ENABLED;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.ADDRESSING_SEPARATE_LISTENER;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.ADDRESSING_VERSION;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.AddressingVersion;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.AddressingVersion.FINAL;
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
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.TimeoutAction.NEVER;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.URI;
import static com.codenvy.ide.client.initializers.propertytype.CommonPropertyTypeInitializer.BOOLEAN_TYPE_NAME;
import static com.codenvy.ide.client.propertiespanel.endpoints.address.property.PropertyPresenter.PropertiesChangedCallback;
import static com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter.EditButtonClickedListener;
import static java.lang.Boolean.TRUE;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Andrey Plotnikov
 */
public class AddressEndpointPropertiesPanelPresenterTest extends AbstractPropertiesPanelTest<AddressEndpointPropertiesPanelPresenter> {

    private static final String  NOT_DIGIT = "not digit";
    private static final Integer INTEGER   = 2;
    private static final Double  DOUBLE    = 2.;

    private static final String BASIC_GROUP_TITLE = "basic group";
    private static final String FORMAT_TITLE      = "format title";
    private static final String URI_TITLE         = "uri title";

    private static final String SUSPEND_GROUP_TITLE               = "suspend group";
    private static final String SUSPEND_ERROR_CODE_TITLE          = "suspend error code title";
    private static final String SUSPEND_INITIAL_DURATION_TITLE    = "suspend initial duration title";
    private static final String SUSPEND_MAXIMUM_DURATION_TITLE    = "suspend maximum duration title";
    private static final String SUSPEND_PROGRESSION_FACTORY_TITLE = "suspend progression factory title";

    private static final String TIMEOUT_STATE_GROUP_TITLE = "timeout state group";
    private static final String RETRY_ERROR_CODE_TITLE    = "retry error code title";
    private static final String RETRY_COUNT_TITLE         = "retry count title";
    private static final String RETRY_DELAY_TITLE         = "retry delay title";

    private static final String MISC_GROUP_TITLE  = "misc group";
    private static final String PROPERTIES_TITLE  = "properties title";
    private static final String OPTIMIZE_TITLE    = "optimize title";
    private static final String DESCRIPTION_TITLE = "description title";

    private static final String QOS_GROUP_TITLE                    = "qos group";
    private static final String RELIABLE_MESSAGING_ENABLED_TITLE   = "reliable messaging enabled title";
    private static final String RELIABLE_MESSAGE_POLICY_TITLE      = "reliable message policy title";
    private static final String SECURITY_ENABLED_TITLE             = "security enabled title";
    private static final String SECURITY_POLICY_TITLE              = "security policy title";
    private static final String ADDRESSING_ENABLED_TITLE           = "addressing enabled title";
    private static final String ADDRESSING_VERSION_TITLE           = "addressing version title";
    private static final String ADDRESSING_SEPARATE_LISTENER_TITLE = "addressing separate listener title";

    private static final String TIMEOUT_GROUP_TITLE    = "timeout group";
    private static final String TIMEOUT_DURATION_TITLE = "timeout duration title";
    private static final String TIMEOUT_ACTION_TITLE   = "timeout action title";

    private static final String PROPERTY_NAME = "property name";

    @Mock
    private PropertyPresenter propertyPresenter;
    @Mock
    private AddressEndpoint   element;
    @Mock
    private Property          property;

    @Mock
    private PropertyGroupPresenter  basicGroup;
    @Mock
    private ListPropertyPresenter   format;
    @Mock
    private SimplePropertyPresenter uri;

    private PropertyValueChangedListener formatListener;
    private PropertyValueChangedListener uriListener;

    @Mock
    private PropertyGroupPresenter  suspendStateGroup;
    @Mock
    private SimplePropertyPresenter suspendErrorCode;
    @Mock
    private SimplePropertyPresenter suspendInitialDuration;
    @Mock
    private SimplePropertyPresenter suspendMaximumDuration;
    @Mock
    private SimplePropertyPresenter suspendProgressionFactory;

    private PropertyValueChangedListener suspendErrorListener;
    private PropertyValueChangedListener suspendInitDurListener;
    private PropertyValueChangedListener suspendMaxDurListener;
    private PropertyValueChangedListener suspendProgressFactoryListener;

    @Mock
    private PropertyGroupPresenter  timeoutStateGroup;
    @Mock
    private SimplePropertyPresenter retryErrorCode;
    @Mock
    private SimplePropertyPresenter retryCount;
    @Mock
    private SimplePropertyPresenter retryDelay;

    private PropertyValueChangedListener retryErrorListener;
    private PropertyValueChangedListener retryCountListener;
    private PropertyValueChangedListener retryDelayListener;

    @Mock
    private PropertyGroupPresenter   miscGroup;
    @Mock
    private ComplexPropertyPresenter properties;
    @Mock
    private ListPropertyPresenter    optimize;
    @Mock
    private SimplePropertyPresenter  description;

    private EditButtonClickedListener    propertiesBtnListener;
    private PropertyValueChangedListener optimizeListener;
    private PropertyValueChangedListener descriptionListener;

    @Mock
    private PropertyGroupPresenter  qosGroup;
    @Mock
    private ListPropertyPresenter   reliableMessagingEnabled;
    @Mock
    private SimplePropertyPresenter reliableMessagePolicy;
    @Mock
    private ListPropertyPresenter   securityEnabled;
    @Mock
    private SimplePropertyPresenter securityPolicy;
    @Mock
    private ListPropertyPresenter   addressingEnabled;
    @Mock
    private ListPropertyPresenter   addressingVersion;
    @Mock
    private ListPropertyPresenter   addressingSeparateListener;

    private PropertyValueChangedListener reliableMessEnableListener;
    private PropertyValueChangedListener reliableMessagePolicyListener;
    private PropertyValueChangedListener securityEnabledListener;
    private PropertyValueChangedListener securityPolicyListener;
    private PropertyValueChangedListener addressEnabledListener;
    private PropertyValueChangedListener addressVersionListener;
    private PropertyValueChangedListener addressSeparateListener;

    @Mock
    private PropertyGroupPresenter  timeoutGroup;
    @Mock
    private SimplePropertyPresenter timeoutDuration;
    @Mock
    private ListPropertyPresenter   timeoutAction;

    private PropertyValueChangedListener timeoutDurationListener;
    private PropertyValueChangedListener timeoutActionListener;

    @Before
    public void setUp() throws Exception {
        prepareMocks();

        presenter = new AddressEndpointPropertiesPanelPresenter(view, propertyTypeManager, locale, propertyPanelFactory, propertyPresenter);
        presenter.addListener(listener);
        presenter.setElement(element);

        constructorOperationShouldBeDone();
    }

    private void prepareMocks() {
        when(property.getProperty(eq(Property.NAME))).thenReturn(PROPERTY_NAME);
        when(locale.addressEndpointEndpointProperty(anyString())).thenReturn(PROPERTY_NAME);

        prepareBasicGroupAndItems();
        prepareSuspendGroupAndItems();
        prepareTimeoutStateGroupAndItems();
        prepareMiscGroupAndItems();
        prepareQoSGroupAndItems();
        prepareTimeoutGroupAndItems();
    }

    private void prepareBasicGroupAndItems() {
        when(locale.addressEndpointBasic()).thenReturn(BASIC_GROUP_TITLE);
        when(locale.addressEndpointFormat()).thenReturn(FORMAT_TITLE);
        when(locale.addressEndpointUri()).thenReturn(URI_TITLE);

        prepareCreatingGroup(BASIC_GROUP_TITLE, basicGroup);
        prepareCreatingListProperty(FORMAT_TITLE, format);
        prepareCreatingSimpleProperty(URI_TITLE, uri);
    }

    private void prepareSuspendGroupAndItems() {
        when(locale.addressEndpointSuspendState()).thenReturn(SUSPEND_GROUP_TITLE);
        when(locale.addressEndpointSuspendErrorCodes()).thenReturn(SUSPEND_ERROR_CODE_TITLE);
        when(locale.addressEndpointSuspendInitialDuration()).thenReturn(SUSPEND_INITIAL_DURATION_TITLE);
        when(locale.addressEndpointSuspendMaximumDuration()).thenReturn(SUSPEND_MAXIMUM_DURATION_TITLE);
        when(locale.addressEndpointSuspendProgressionFactory()).thenReturn(SUSPEND_PROGRESSION_FACTORY_TITLE);

        prepareCreatingGroup(SUSPEND_GROUP_TITLE, suspendStateGroup);
        prepareCreatingSimpleProperty(SUSPEND_ERROR_CODE_TITLE, suspendErrorCode);
        prepareCreatingSimpleProperty(SUSPEND_INITIAL_DURATION_TITLE, suspendInitialDuration);
        prepareCreatingSimpleProperty(SUSPEND_MAXIMUM_DURATION_TITLE, suspendMaximumDuration);
        prepareCreatingSimpleProperty(SUSPEND_PROGRESSION_FACTORY_TITLE, suspendProgressionFactory);
    }

    private void prepareTimeoutStateGroupAndItems() {
        when(locale.addressEndpointEndpointTimeoutState()).thenReturn(TIMEOUT_STATE_GROUP_TITLE);
        when(locale.addressEndpointRetryErrorCodes()).thenReturn(RETRY_ERROR_CODE_TITLE);
        when(locale.addressEndpointRetryCount()).thenReturn(RETRY_COUNT_TITLE);
        when(locale.addressEndpointRetryDelay()).thenReturn(RETRY_DELAY_TITLE);

        prepareCreatingGroup(TIMEOUT_STATE_GROUP_TITLE, timeoutStateGroup);
        prepareCreatingSimpleProperty(RETRY_ERROR_CODE_TITLE, retryErrorCode);
        prepareCreatingSimpleProperty(RETRY_COUNT_TITLE, retryCount);
        prepareCreatingSimpleProperty(RETRY_DELAY_TITLE, retryDelay);
    }

    private void prepareMiscGroupAndItems() {
        when(locale.addressEndpointMisc()).thenReturn(MISC_GROUP_TITLE);
        when(locale.addressEndpointProperties()).thenReturn(PROPERTIES_TITLE);
        when(locale.addressEndpointOptimize()).thenReturn(OPTIMIZE_TITLE);
        when(locale.description()).thenReturn(DESCRIPTION_TITLE);

        prepareCreatingGroup(MISC_GROUP_TITLE, miscGroup);
        prepareCreatingComplexProperty(PROPERTIES_TITLE, properties);
        prepareCreatingListProperty(OPTIMIZE_TITLE, optimize);
        prepareCreatingSimpleProperty(DESCRIPTION_TITLE, description);
    }

    private void prepareQoSGroupAndItems() {
        when(locale.addressEndpointQos()).thenReturn(QOS_GROUP_TITLE);
        when(locale.addressEndpointReliableMessagingEnabled()).thenReturn(RELIABLE_MESSAGING_ENABLED_TITLE);
        when(locale.addressEndpointReliableMessagingPolicy()).thenReturn(RELIABLE_MESSAGE_POLICY_TITLE);
        when(locale.addressEndpointSecurityEnabled()).thenReturn(SECURITY_ENABLED_TITLE);
        when(locale.addressEndpointSecurityPolicy()).thenReturn(SECURITY_POLICY_TITLE);
        when(locale.addressEndpointAddressingEnabled()).thenReturn(ADDRESSING_ENABLED_TITLE);
        when(locale.addressEndpointAddressingVersion()).thenReturn(ADDRESSING_VERSION_TITLE);
        when(locale.addressEndpointAddressingSeparateListener()).thenReturn(ADDRESSING_SEPARATE_LISTENER_TITLE);

        prepareCreatingGroup(QOS_GROUP_TITLE, qosGroup);
        prepareCreatingListProperty(RELIABLE_MESSAGING_ENABLED_TITLE, reliableMessagingEnabled);
        prepareCreatingSimpleProperty(RELIABLE_MESSAGE_POLICY_TITLE, reliableMessagePolicy);
        prepareCreatingListProperty(SECURITY_ENABLED_TITLE, securityEnabled);
        prepareCreatingSimpleProperty(SECURITY_POLICY_TITLE, securityPolicy);
        prepareCreatingListProperty(ADDRESSING_ENABLED_TITLE, addressingEnabled);
        prepareCreatingListProperty(ADDRESSING_VERSION_TITLE, addressingVersion);
        prepareCreatingListProperty(ADDRESSING_SEPARATE_LISTENER_TITLE, addressingSeparateListener);
    }

    private void prepareTimeoutGroupAndItems() {
        when(locale.addressEndpointTimeout()).thenReturn(TIMEOUT_GROUP_TITLE);
        when(locale.addressEndpointTimeoutDuration()).thenReturn(TIMEOUT_DURATION_TITLE);
        when(locale.addressEndpointTimeoutAction()).thenReturn(TIMEOUT_ACTION_TITLE);

        prepareCreatingGroup(TIMEOUT_GROUP_TITLE, timeoutGroup);
        prepareCreatingSimpleProperty(TIMEOUT_DURATION_TITLE, timeoutDuration);
        prepareCreatingListProperty(TIMEOUT_ACTION_TITLE, timeoutAction);
    }

    private void constructorOperationShouldBeDone() {
        verify(view).setDelegate(presenter);

        basicGroupShouldBePrepared();
        suspendGroupShouldBePrepared();
        timeoutStateGroupShouldBePrepared();
        miscGroupShouldBePrepared();
        qosGroupShouldBePrepared();
        timeoutGroupShouldBePrepared();
    }

    private void basicGroupShouldBePrepared() {
        verify(locale).addressEndpointBasic();
        verify(locale).addressEndpointFormat();
        verify(locale).addressEndpointUri();

        groupShouldBeAdded(basicGroup);
        itemsShouldBeAdded(basicGroup, format, uri);

        formatListener = getListPropertyChangedListener(FORMAT_TITLE);
        uriListener = getSimplePropertyChangedListener(URI_TITLE);
    }

    private void suspendGroupShouldBePrepared() {
        verify(locale).addressEndpointSuspendState();
        verify(locale).addressEndpointSuspendErrorCodes();
        verify(locale).addressEndpointSuspendInitialDuration();
        verify(locale).addressEndpointSuspendMaximumDuration();
        verify(locale).addressEndpointSuspendProgressionFactory();

        groupShouldBeAdded(suspendStateGroup);
        itemsShouldBeAdded(suspendStateGroup, suspendErrorCode, suspendInitialDuration, suspendMaximumDuration, suspendProgressionFactory);

        suspendErrorListener = getSimplePropertyChangedListener(SUSPEND_ERROR_CODE_TITLE);
        suspendInitDurListener = getSimplePropertyChangedListener(SUSPEND_INITIAL_DURATION_TITLE);
        suspendMaxDurListener = getSimplePropertyChangedListener(SUSPEND_MAXIMUM_DURATION_TITLE);
        suspendProgressFactoryListener = getSimplePropertyChangedListener(SUSPEND_PROGRESSION_FACTORY_TITLE);
    }

    private void timeoutStateGroupShouldBePrepared() {
        verify(locale).addressEndpointEndpointTimeoutState();
        verify(locale).addressEndpointRetryErrorCodes();
        verify(locale).addressEndpointRetryCount();
        verify(locale).addressEndpointRetryDelay();

        groupShouldBeAdded(timeoutStateGroup);
        itemsShouldBeAdded(timeoutStateGroup, retryErrorCode, retryCount, retryDelay);

        retryErrorListener = getSimplePropertyChangedListener(RETRY_ERROR_CODE_TITLE);
        retryCountListener = getSimplePropertyChangedListener(RETRY_COUNT_TITLE);
        retryDelayListener = getSimplePropertyChangedListener(RETRY_DELAY_TITLE);
    }

    private void miscGroupShouldBePrepared() {
        verify(locale).addressEndpointMisc();
        verify(locale).addressEndpointProperties();
        verify(locale).addressEndpointOptimize();
        verify(locale).description();

        groupShouldBeAdded(miscGroup);
        itemsShouldBeAdded(miscGroup, properties, optimize, description);

        propertiesBtnListener = getEditButtonClickedListener(PROPERTIES_TITLE);
        optimizeListener = getListPropertyChangedListener(OPTIMIZE_TITLE);
        descriptionListener = getSimplePropertyChangedListener(DESCRIPTION_TITLE);
    }

    private void qosGroupShouldBePrepared() {
        verify(locale).addressEndpointQos();
        verify(locale).addressEndpointReliableMessagingEnabled();
        verify(locale).addressEndpointReliableMessagingPolicy();
        verify(locale).addressEndpointSecurityEnabled();
        verify(locale).addressEndpointSecurityPolicy();
        verify(locale).addressEndpointAddressingEnabled();
        verify(locale).addressEndpointAddressingVersion();
        verify(locale).addressEndpointAddressingSeparateListener();

        groupShouldBeAdded(qosGroup);
        itemsShouldBeAdded(qosGroup,
                           reliableMessagingEnabled,
                           reliableMessagePolicy,
                           securityEnabled,
                           securityPolicy,
                           addressingEnabled,
                           addressingVersion,
                           addressingSeparateListener);

        reliableMessEnableListener = getListPropertyChangedListener(RELIABLE_MESSAGING_ENABLED_TITLE);
        reliableMessagePolicyListener = getSimplePropertyChangedListener(RELIABLE_MESSAGE_POLICY_TITLE);
        securityEnabledListener = getListPropertyChangedListener(SECURITY_ENABLED_TITLE);
        securityPolicyListener = getSimplePropertyChangedListener(SECURITY_POLICY_TITLE);
        addressEnabledListener = getListPropertyChangedListener(ADDRESSING_ENABLED_TITLE);
        addressVersionListener = getListPropertyChangedListener(ADDRESSING_VERSION_TITLE);
        addressSeparateListener = getListPropertyChangedListener(ADDRESSING_SEPARATE_LISTENER_TITLE);
    }

    private void timeoutGroupShouldBePrepared() {
        verify(locale).addressEndpointTimeout();
        verify(locale).addressEndpointTimeoutDuration();
        verify(locale).addressEndpointTimeoutAction();

        groupShouldBeAdded(timeoutGroup);
        itemsShouldBeAdded(timeoutGroup, timeoutDuration, timeoutAction);

        timeoutDurationListener = getSimplePropertyChangedListener(TIMEOUT_DURATION_TITLE);
        timeoutActionListener = getListPropertyChangedListener(TIMEOUT_ACTION_TITLE);
    }

    @Override
    public void groupsShouldBeShown() {
        presenter.go(container);

        verify(container).setWidget(view);

        groupsShouldBeUnfolded(basicGroup, suspendStateGroup, timeoutStateGroup, miscGroup, qosGroup, timeoutGroup);
    }

    @Test
    public void formatValueShouldBeShown() throws Exception {
        preparePropertyTypeManager(Format.TYPE_NAME, VALUES);

        presenter.go(container);

        propertyTypeManagerShouldBeUsed(Format.TYPE_NAME);
        listValuesShouldBeSet(format, VALUES);
        noListItemShouldBeSelected(format);
    }

    @Test
    public void formatValueShouldBeSelected() throws Exception {
        preparePropertyTypeManager(Format.TYPE_NAME, VALUES);
        prepareElement(element, FORMAT, Format.LEAVE_AS_IS);

        presenter.go(container);

        propertyTypeManagerShouldBeUsed(Format.TYPE_NAME);
        listValuesShouldBeSet(format, VALUES);
        listItemShouldBeSelected(format, Format.LEAVE_AS_IS.getValue());
    }

    @Test
    public void uriValueShouldBeShown() throws Exception {
        prepareElement(element, URI, URI_TITLE);

        presenter.go(container);

        simplePropertyShouldBePrepared(uri, element, URI, URI_TITLE);
    }

    @Test
    public void suspendErrorCodeShouldBeShown() throws Exception {
        prepareElement(element, SUSPEND_ERROR_CODE, SUSPEND_ERROR_CODE_TITLE);

        presenter.go(container);

        simplePropertyShouldBePrepared(suspendErrorCode, element, SUSPEND_ERROR_CODE, SUSPEND_ERROR_CODE_TITLE);
    }

    @Test
    public void suspendInitialDurationShouldBeShown() throws Exception {
        prepareElement(element, SUSPEND_INITIAL_DURATION, INTEGER);

        presenter.go(container);

        simplePropertyShouldBePrepared(suspendInitialDuration, element, SUSPEND_INITIAL_DURATION, INTEGER.toString());
    }

    @Test
    public void suspendMaximumDurationShouldBeShown() throws Exception {
        prepareElement(element, SUSPEND_MAXIMUM_DURATION, INTEGER);

        presenter.go(container);

        simplePropertyShouldBePrepared(suspendMaximumDuration, element, SUSPEND_MAXIMUM_DURATION, INTEGER.toString());
    }

    @Test
    public void suspendProgressFactoryShouldBeShown() throws Exception {
        prepareElement(element, SUSPEND_PROGRESSION_FACTORY, DOUBLE);

        presenter.go(container);

        simplePropertyShouldBePrepared(suspendProgressionFactory, element, SUSPEND_PROGRESSION_FACTORY, DOUBLE.toString());
    }

    @Test
    public void retryErrorCodeShouldBeShown() throws Exception {
        prepareElement(element, RETRY_ERROR_CODES, RETRY_ERROR_CODE_TITLE);

        presenter.go(container);

        simplePropertyShouldBePrepared(retryErrorCode, element, RETRY_ERROR_CODES, RETRY_ERROR_CODE_TITLE);
    }

    @Test
    public void retryCountShouldBeShown() throws Exception {
        prepareElement(element, RETRY_COUNT, INTEGER);

        presenter.go(container);

        simplePropertyShouldBePrepared(retryCount, element, RETRY_COUNT, INTEGER.toString());
    }

    @Test
    public void retryDelayShouldBeShown() throws Exception {
        prepareElement(element, RETRY_DELAY, INTEGER);

        presenter.go(container);

        simplePropertyShouldBePrepared(retryDelay, element, RETRY_DELAY, INTEGER.toString());
    }

    @Test
    public void propertiesShouldBeShown() throws Exception {
        List<Property> testProperties = Collections.emptyList();
        prepareElement(element, PROPERTIES, testProperties);

        presenter.go(container);

        propertyValueShouldBeReturned(element, PROPERTIES);
        complexPropertyValueShouldBeChanged(properties, "");
    }

    @Test
    public void propertiesShouldBeShownWhenItIsEmpty() throws Exception {
        prepareElement(element, PROPERTIES, null);

        presenter.go(container);

        propertyValueShouldBeReturned(element, PROPERTIES);
        complexPropertyValueShouldBeChanged(properties, "");
    }

    @Test
    public void propertiesShouldBeShownWhenItContainsAFewProperties() throws Exception {
        List<Property> testProperties = Arrays.asList(property, property);
        prepareElement(element, PROPERTIES, testProperties);

        presenter.go(container);

        propertyValueShouldBeReturned(element, PROPERTIES);
        propertiesStringValueShouldBeShown();
    }

    private void propertiesStringValueShouldBeShown() {
        verify(property, times(2)).getProperty(Property.NAME);
        complexPropertyValueShouldBeChanged(properties, PROPERTY_NAME + ", " + PROPERTY_NAME);
    }

    @Test
    public void optimizeShouldBeSelected() throws Exception {
        preparePropertyTypeManager(Optimize.TYPE_NAME, VALUES);
        prepareElement(element, OPTIMIZE, Optimize.LEAVE_AS_IS);

        presenter.go(container);

        listValuesShouldBeSet(optimize, VALUES);
        listItemShouldBeSelected(optimize, Optimize.LEAVE_AS_IS.getValue());

        propertyValueShouldBeReturned(element, OPTIMIZE);
        propertyTypeManagerShouldBeUsed(Optimize.TYPE_NAME);
    }

    @Test
    public void optimizeShouldBeShown() throws Exception {
        preparePropertyTypeManager(Optimize.TYPE_NAME, VALUES);
        prepareElement(element, OPTIMIZE, null);

        presenter.go(container);

        listValuesShouldBeSet(optimize, VALUES);
        noListItemShouldBeSelected(optimize);

        propertyValueShouldBeReturned(element, OPTIMIZE);
        propertyTypeManagerShouldBeUsed(Optimize.TYPE_NAME);
    }

    @Test
    public void descriptionShouldBeShown() throws Exception {
        prepareElement(element, DESCRIPTION, DESCRIPTION_TITLE);

        presenter.go(container);

        simplePropertyShouldBePrepared(description, element, DESCRIPTION, DESCRIPTION_TITLE);
    }

    @Test
    public void reliableMessagingShouldBeNotShown() throws Exception {
        prepareElement(element, RELIABLE_MESSAGING_ENABLED, null);

        presenter.go(container);

        propertyValueShouldBeReturned(element, RELIABLE_MESSAGING_ENABLED);

        noListValuesShouldBeSet(reliableMessagingEnabled);
        noListItemShouldBeSelected(reliableMessagingEnabled);

        visibleStateShouldBeTheSame(reliableMessagePolicy);
        simplePropertyValueShouldBeTheSame(reliableMessagePolicy);
    }

    @Test
    public void reliableMessagePolicyShouldBeShown() throws Exception {
        preparePropertyTypeManager(BOOLEAN_TYPE_NAME, VALUES);
        prepareElement(element, RELIABLE_MESSAGING_ENABLED, true);
        prepareElement(element, RELIABLE_MESSAGING_POLICY, RELIABLE_MESSAGE_POLICY_TITLE);

        presenter.go(container);

        propertyValueShouldBeReturned(element, RELIABLE_MESSAGING_ENABLED);

        listValuesShouldBeSet(reliableMessagingEnabled, VALUES);
        listItemShouldBeSelected(reliableMessagingEnabled, TRUE.toString());

        visibleStateShouldBeChanged(reliableMessagePolicy, true);
        simplePropertyValueShouldBeChanged(reliableMessagePolicy, RELIABLE_MESSAGE_POLICY_TITLE);

        propertyTypeManagerShouldBeUsed(BOOLEAN_TYPE_NAME);
    }

    @Test
    public void securityShouldBeNotShown() throws Exception {
        prepareElement(element, SECURITY_ENABLED, null);

        presenter.go(container);

        propertyValueShouldBeReturned(element, SECURITY_ENABLED);

        noListValuesShouldBeSet(securityEnabled);
        noListItemShouldBeSelected(securityEnabled);

        visibleStateShouldBeTheSame(securityPolicy);
        simplePropertyValueShouldBeTheSame(securityPolicy);
    }

    @Test
    public void securityPolicyShouldBeShown() throws Exception {
        preparePropertyTypeManager(BOOLEAN_TYPE_NAME, VALUES);
        prepareElement(element, SECURITY_ENABLED, true);
        prepareElement(element, SECURITY_POLICY, SECURITY_POLICY_TITLE);

        presenter.go(container);

        propertyValueShouldBeReturned(element, SECURITY_ENABLED);

        listValuesShouldBeSet(securityEnabled, VALUES);
        listItemShouldBeSelected(securityEnabled, TRUE.toString());

        visibleStateShouldBeChanged(securityPolicy, true);
        simplePropertyValueShouldBeChanged(securityPolicy, SECURITY_POLICY_TITLE);

        propertyTypeManagerShouldBeUsed(BOOLEAN_TYPE_NAME);
    }

    @Test
    public void addressingShouldBeNotShown() throws Exception {
        prepareElement(element, ADDRESSING_ENABLED, null);

        presenter.go(container);

        propertyValueShouldBeReturned(element, ADDRESSING_ENABLED);

        noListValuesShouldBeSet(addressingEnabled);
        noListItemShouldBeSelected(addressingEnabled);

        visibleStateShouldBeTheSame(addressingVersion);
        noListValuesShouldBeSet(addressingVersion);
        noListItemShouldBeSelected(addressingVersion);

        visibleStateShouldBeTheSame(addressingSeparateListener);
        noListValuesShouldBeSet(addressingVersion);
        noListItemShouldBeSelected(addressingSeparateListener);
    }

    @Test
    public void addressingShouldBeShown() throws Exception {
        preparePropertyTypeManager(BOOLEAN_TYPE_NAME, VALUES);
        preparePropertyTypeManager(AddressingVersion.TYPE_NAME, VALUES);
        prepareElement(element, ADDRESSING_ENABLED, true);
        prepareElement(element, ADDRESSING_VERSION, null);
        prepareElement(element, ADDRESSING_SEPARATE_LISTENER, true);

        presenter.go(container);

        propertyValueShouldBeReturned(element, ADDRESSING_ENABLED);

        listValuesShouldBeSet(addressingEnabled, VALUES);
        listItemShouldBeSelected(addressingEnabled, TRUE.toString());

        visibleStateShouldBeChanged(addressingVersion, true);
        listValuesShouldBeSet(addressingVersion, VALUES);

        noListItemShouldBeSelected(addressingVersion);

        visibleStateShouldBeChanged(addressingSeparateListener, true);
        listValuesShouldBeSet(addressingSeparateListener, VALUES);
        listItemShouldBeSelected(addressingSeparateListener, TRUE.toString());

        propertyTypeManagerShouldBeUsed(BOOLEAN_TYPE_NAME);
        propertyTypeManagerShouldBeUsed(AddressingVersion.TYPE_NAME);
    }

    @Test
    public void addressingShouldBeShownAndAddressingVersionShouldBeSelected() throws Exception {
        preparePropertyTypeManager(BOOLEAN_TYPE_NAME, VALUES);
        preparePropertyTypeManager(AddressingVersion.TYPE_NAME, VALUES);
        prepareElement(element, ADDRESSING_ENABLED, true);
        prepareElement(element, ADDRESSING_VERSION, FINAL);
        prepareElement(element, ADDRESSING_SEPARATE_LISTENER, true);

        presenter.go(container);

        propertyValueShouldBeReturned(element, ADDRESSING_ENABLED);
        listValuesShouldBeSet(addressingEnabled, VALUES);
        listItemShouldBeSelected(addressingEnabled, TRUE.toString());

        visibleStateShouldBeChanged(addressingVersion, true);
        listValuesShouldBeSet(addressingVersion, VALUES);

        listItemShouldBeSelected(addressingVersion, FINAL.getValue());

        visibleStateShouldBeChanged(addressingSeparateListener, true);
        listValuesShouldBeSet(addressingSeparateListener, VALUES);
        listItemShouldBeSelected(addressingSeparateListener, TRUE.toString());

        propertyTypeManagerShouldBeUsed(BOOLEAN_TYPE_NAME);
        propertyTypeManagerShouldBeUsed(AddressingVersion.TYPE_NAME);
    }

    @Test
    public void timeoutActionShouldBeShown() throws Exception {
        preparePropertyTypeManager(TimeoutAction.TYPE_NAME, VALUES);
        prepareElement(element, TIMEOUT_ACTION, null);

        presenter.go(container);

        listValuesShouldBeSet(timeoutAction, VALUES);
        noListItemShouldBeSelected(timeoutAction);
        propertyTypeManagerShouldBeUsed(TimeoutAction.TYPE_NAME);
    }

    @Test
    public void timeoutActionShouldBeSelected() throws Exception {
        preparePropertyTypeManager(TimeoutAction.TYPE_NAME, VALUES);
        prepareElement(element, TIMEOUT_ACTION, NEVER);

        presenter.go(container);

        listValuesShouldBeSet(timeoutAction, VALUES);
        listItemShouldBeSelected(timeoutAction, NEVER.getValue());
        propertyTypeManagerShouldBeUsed(TimeoutAction.TYPE_NAME);
    }

    @Test
    public void timeoutDurationShouldBeShown() throws Exception {
        prepareElement(element, TIMEOUT_DURATION, INTEGER);

        presenter.go(container);

        simplePropertyShouldBePrepared(timeoutDuration, element, TIMEOUT_DURATION, INTEGER.toString());
    }

    @Test
    public void formatPropertyListenerShouldBeDone() throws Exception {
        formatListener.onPropertyChanged("LEAVE_AS_IS");

        propertyChangedGeneralListenerShouldBeExecuted(element, FORMAT, Format.LEAVE_AS_IS);
    }

    @Test
    public void uriPropertyListenerShouldBeDone() throws Exception {
        uriListener.onPropertyChanged(URI_TITLE);

        propertyValueShouldBeChanged(element, URI, URI_TITLE);
        listenerShouldBeNotified();
    }

    @Test
    public void suspendErrorCodePropertyListenerShouldBeDone() throws Exception {
        suspendErrorListener.onPropertyChanged(SUSPEND_ERROR_CODE_TITLE);

        propertyChangedGeneralListenerShouldBeExecuted(element, SUSPEND_ERROR_CODE, SUSPEND_ERROR_CODE_TITLE);
    }

    @Test
    public void suspendInitialDurationPropertyListenerShouldBeDone() throws Exception {
        suspendInitDurListener.onPropertyChanged(INTEGER.toString());

        propertyChangedGeneralListenerShouldBeExecuted(element, SUSPEND_INITIAL_DURATION, INTEGER);
    }

    @Test
    public void suspendInitialDurationPropertyListenerShouldBeDoneWhenIncorrectValue() throws Exception {
        prepareElement(element, SUSPEND_INITIAL_DURATION, INTEGER);

        suspendInitDurListener.onPropertyChanged(NOT_DIGIT);

        simplePropertyValueShouldBeChanged(suspendInitialDuration, INTEGER.toString());
        verify(element, never()).putProperty(eq(SUSPEND_INITIAL_DURATION), anyInt());
        noListenerShouldBeNotified();
    }

    @Test
    public void suspendMaximumDurationPropertyListenerShouldBeDone() throws Exception {
        suspendMaxDurListener.onPropertyChanged(INTEGER.toString());

        propertyChangedGeneralListenerShouldBeExecuted(element, SUSPEND_MAXIMUM_DURATION, INTEGER);
    }

    @Test
    public void suspendMaximumDurationPropertyListenerShouldBeDoneWhenIncorrectValue() throws Exception {
        prepareElement(element, SUSPEND_MAXIMUM_DURATION, INTEGER);

        suspendMaxDurListener.onPropertyChanged(NOT_DIGIT);

        simplePropertyValueShouldBeChanged(suspendMaximumDuration, INTEGER.toString());
        verify(element, never()).putProperty(eq(SUSPEND_MAXIMUM_DURATION), anyInt());
        noListenerShouldBeNotified();
    }

    @Test
    public void suspendProgressionFactoryPropertyListenerShouldBeDone() throws Exception {
        suspendProgressFactoryListener.onPropertyChanged(DOUBLE.toString());

        propertyChangedGeneralListenerShouldBeExecuted(element, SUSPEND_PROGRESSION_FACTORY, DOUBLE);
    }

    @Test
    public void suspendProgressionFactoryPropertyListenerShouldBeDoneWhenIncorrectValue() throws Exception {
        prepareElement(element, SUSPEND_PROGRESSION_FACTORY, DOUBLE);

        suspendProgressFactoryListener.onPropertyChanged(NOT_DIGIT);

        simplePropertyValueShouldBeChanged(suspendProgressionFactory, DOUBLE.toString());
        verify(element, never()).putProperty(eq(SUSPEND_PROGRESSION_FACTORY), anyDouble());
        noListenerShouldBeNotified();
    }

    @Test
    public void retryErrorCodePropertyListenerShouldBeDone() throws Exception {
        retryErrorListener.onPropertyChanged(RETRY_ERROR_CODE_TITLE);

        propertyChangedGeneralListenerShouldBeExecuted(element, RETRY_ERROR_CODES, RETRY_ERROR_CODE_TITLE);
    }

    @Test
    public void retryCountPropertyListenerShouldBeDone() throws Exception {
        retryCountListener.onPropertyChanged(INTEGER.toString());

        propertyChangedGeneralListenerShouldBeExecuted(element, RETRY_COUNT, INTEGER);
    }

    @Test
    public void retryCountPropertyListenerShouldBeDoneWhenIncorrectValue() throws Exception {
        prepareElement(element, RETRY_COUNT, INTEGER);

        retryCountListener.onPropertyChanged(NOT_DIGIT);

        simplePropertyValueShouldBeChanged(retryCount, INTEGER.toString());
        verify(element, never()).putProperty(eq(RETRY_COUNT), anyInt());
        noListenerShouldBeNotified();
    }

    @Test
    public void retryDelayPropertyListenerShouldBeDone() throws Exception {
        retryDelayListener.onPropertyChanged(INTEGER.toString());

        propertyChangedGeneralListenerShouldBeExecuted(element, RETRY_DELAY, INTEGER);
    }

    @Test
    public void retryDelayPropertyListenerShouldBeDoneWhenIncorrectValue() throws Exception {
        prepareElement(element, RETRY_DELAY, INTEGER);

        retryDelayListener.onPropertyChanged(NOT_DIGIT);

        simplePropertyValueShouldBeChanged(retryDelay, INTEGER.toString());
        verify(element, never()).putProperty(eq(RETRY_DELAY), anyInt());
        noListenerShouldBeNotified();
    }

    @Test
    public void propertiesButtonClickedListenerShouldBeDone() throws Exception {
        List<Property> testProperties = new ArrayList<>();

        prepareElement(element, PROPERTIES, testProperties);

        propertiesBtnListener.onEditButtonClicked();

        ArgumentCaptor<PropertiesChangedCallback> propertiesChangedCallbackArgumentCaptor =
                ArgumentCaptor.forClass(PropertiesChangedCallback.class);

        verify(propertyPresenter).showDialog(propertiesChangedCallbackArgumentCaptor.capture(), eq(testProperties));
        noListenerShouldBeNotified();

        reset(element);
        reset(view);

        testProperties = Arrays.asList(property, property);

        PropertiesChangedCallback callback = propertiesChangedCallbackArgumentCaptor.getValue();
        callback.onPropertiesChanged(testProperties);

        propertiesStringValueShouldBeShown();
        propertyChangedGeneralListenerShouldBeExecuted(element, PROPERTIES, testProperties);
    }

    @Test
    public void propertiesButtonClickedListenerShouldBeNotDoneWhenPropertiesAreAbsent() throws Exception {
        propertiesBtnListener.onEditButtonClicked();

        verify(propertyPresenter, never()).showDialog(any(PropertiesChangedCallback.class), anyListOf(Property.class));
        noListenerShouldBeNotified();
    }

    @Test
    public void optimizePropertyListenerShouldBeDone() throws Exception {
        optimizeListener.onPropertyChanged("LEAVE_AS_IS");

        propertyChangedGeneralListenerShouldBeExecuted(element, OPTIMIZE, Optimize.LEAVE_AS_IS);
    }

    @Test
    public void descriptionPropertyListenerShouldBeDone() throws Exception {
        descriptionListener.onPropertyChanged(DESCRIPTION_TITLE);

        propertyChangedGeneralListenerShouldBeExecuted(element, DESCRIPTION, DESCRIPTION_TITLE);
    }

    @Test
    public void reliableMessagingEnablePropertyListenerShouldBeDone() throws Exception {
        reliableMessEnableListener.onPropertyChanged(TRUE.toString());

        visibleStateShouldBeChanged(reliableMessagePolicy, true);
        propertyChangedGeneralListenerShouldBeExecuted(element, RELIABLE_MESSAGING_ENABLED, true);
    }

    @Test
    public void reliableMessagePolicyPropertyListenerShouldBeDone() throws Exception {
        reliableMessagePolicyListener.onPropertyChanged(RELIABLE_MESSAGE_POLICY_TITLE);

        propertyChangedGeneralListenerShouldBeExecuted(element, RELIABLE_MESSAGING_POLICY, RELIABLE_MESSAGE_POLICY_TITLE);
    }

    @Test
    public void securityEnabledPropertyListenerShouldBeDone() throws Exception {
        securityEnabledListener.onPropertyChanged(TRUE.toString());

        visibleStateShouldBeChanged(securityPolicy, true);
        propertyChangedGeneralListenerShouldBeExecuted(element, SECURITY_ENABLED, true);
    }

    @Test
    public void securityPolicyPropertyListenerShouldBeDone() throws Exception {
        securityPolicyListener.onPropertyChanged(SECURITY_POLICY_TITLE);

        propertyChangedGeneralListenerShouldBeExecuted(element, SECURITY_POLICY, SECURITY_POLICY_TITLE);
    }

    @Test
    public void addressingEnabledPropertyListenerShouldBeDone() throws Exception {
        addressEnabledListener.onPropertyChanged(TRUE.toString());

        visibleStateShouldBeChanged(addressingVersion, true);
        visibleStateShouldBeChanged(addressingSeparateListener, true);

        propertyChangedGeneralListenerShouldBeExecuted(element, ADDRESSING_ENABLED, true);
    }

    @Test
    public void addressingVersionPropertyListenerShouldBeDone() throws Exception {
        addressVersionListener.onPropertyChanged("final");

        propertyChangedGeneralListenerShouldBeExecuted(element, ADDRESSING_VERSION, FINAL);
    }

    @Test
    public void addressingSeparateListenerPropertyListenerShouldBeDone() throws Exception {
        addressSeparateListener.onPropertyChanged(TRUE.toString());

        propertyChangedGeneralListenerShouldBeExecuted(element, ADDRESSING_SEPARATE_LISTENER, true);
    }

    @Test
    public void timeoutDurationPropertyListenerShouldBeDone() throws Exception {
        timeoutDurationListener.onPropertyChanged(INTEGER.toString());

        propertyChangedGeneralListenerShouldBeExecuted(element, TIMEOUT_DURATION, INTEGER);
    }

    @Test
    public void timeoutDurationPropertyListenerShouldBeDoneWhenIncorrectValue() throws Exception {
        prepareElement(element, TIMEOUT_DURATION, INTEGER);

        timeoutDurationListener.onPropertyChanged(NOT_DIGIT);

        simplePropertyValueShouldBeChanged(timeoutDuration, INTEGER.toString());
        verify(element, never()).putProperty(eq(TIMEOUT_DURATION), anyInt());
        noListenerShouldBeNotified();
    }

    @Test
    public void timeoutActionPropertyListenerShouldBeDone() throws Exception {
        timeoutActionListener.onPropertyChanged("never");

        propertyChangedGeneralListenerShouldBeExecuted(element, TIMEOUT_ACTION, NEVER);
    }

}