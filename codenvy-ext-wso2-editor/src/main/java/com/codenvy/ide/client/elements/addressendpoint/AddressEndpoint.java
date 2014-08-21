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
package com.codenvy.ide.client.elements.addressendpoint;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.AbstractShape;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
import com.codenvy.ide.collections.Array;
import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static com.codenvy.ide.client.elements.addressendpoint.AddressEndpoint.AddressingVersion.FINAL;
import static com.codenvy.ide.client.elements.addressendpoint.AddressEndpoint.Format.LEAVE_AS_IS;
import static com.codenvy.ide.client.elements.addressendpoint.AddressEndpoint.TimeoutAction.never;

/**
 * @author Andrey Plotnikov
 */
public class AddressEndpoint extends AbstractShape {

    public static final String ELEMENT_NAME       = "Address";
    public static final String SERIALIZATION_NAME = "address";

    private static final List<String> PROPERTIES = Collections.emptyList();
//    private static final List<String> PROPERTIES = Arrays.asList(ENDPOINT_PROPERTY_NAME);

    private Format format;
    private String uri;

    private String suspendErrorCode;
    private int    suspendInitialDuration;
    private int    suspendMaximumDuration;
    private double suspendProgressionFactor;

    private String retryErrorCodes;
    private int    retryCount;
    private int    retryDelay;

    private Array<Property> properties;
    private Optimize        optimize;
    private String          description;

    private boolean isReliableMessagingEnabled;
    private String  reliableMessagingPolicy;

    private boolean isSecurityEnabled;
    private String  securityPolicy;

    private boolean           isAddressingEnabled;
    private AddressingVersion addressingVersion;
    private boolean           isAddressingSeparateListener;

    private int           timeoutDuration;
    private TimeoutAction timeoutAction;

    @Inject
    public AddressEndpoint(EditorResources resources, Provider<Branch> branchProvider, MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        format = LEAVE_AS_IS;
        uri = "http://www.example.org/service";

        suspendErrorCode = "";
        suspendInitialDuration = -1;
        suspendMaximumDuration = 0;
        suspendProgressionFactor = -1.0;

        retryErrorCodes = "";
        retryCount = 0;
        retryDelay = 0;

        properties = com.codenvy.ide.collections.Collections.createArray();
        optimize = Optimize.LEAVE_AS_IS;
        description = "";

        isReliableMessagingEnabled = false;
        reliableMessagingPolicy = "/default/key";

        isSecurityEnabled = false;
        securityPolicy = "/default/key";

        isAddressingEnabled = false;
        addressingVersion = FINAL;
        isAddressingSeparateListener = false;

        timeoutDuration = 0;
        timeoutAction = never;
    }

    @Nonnull
    public Format getFormat() {
        return format;
    }

    public void setFormat(@Nonnull Format format) {
        this.format = format;
    }

    @Nonnull
    public String getUri() {
        return uri;
    }

    public void setUri(@Nonnull String uri) {
        this.uri = uri;
    }

    @Nonnull
    public String getSuspendErrorCode() {
        return suspendErrorCode;
    }

    public void setSuspendErrorCode(@Nonnull String suspendErrorCode) {
        this.suspendErrorCode = suspendErrorCode;
    }

    @Nonnegative
    public int getSuspendInitialDuration() {
        return suspendInitialDuration;
    }

    public void setSuspendInitialDuration(@Nonnegative int suspendInitialDuration) {
        this.suspendInitialDuration = suspendInitialDuration;
    }

    @Nonnegative
    public int getSuspendMaximumDuration() {
        return suspendMaximumDuration;
    }

    public void setSuspendMaximumDuration(@Nonnegative int suspendMaximumDuration) {
        this.suspendMaximumDuration = suspendMaximumDuration;
    }

    @Nonnegative
    public double getSuspendProgressionFactory() {
        return suspendProgressionFactor;
    }

    public void setSuspendProgressionFactory(@Nonnegative double suspendProgressionFactor) {
        this.suspendProgressionFactor = suspendProgressionFactor;
    }

    @Nonnull
    public String getRetryErrorCodes() {
        return retryErrorCodes;
    }

    public void setRetryErrorCodes(@Nonnull String retryErrorCodes) {
        this.retryErrorCodes = retryErrorCodes;
    }

    @Nonnegative
    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(@Nonnegative int retryCount) {
        this.retryCount = retryCount;
    }

    @Nonnegative
    public int getRetryDelay() {
        return retryDelay;
    }

    public void setRetryDelay(@Nonnegative int retryDelay) {
        this.retryDelay = retryDelay;
    }

    @Nonnull
    public Array<Property> getProperties() {
        return properties;
    }

    public void setProperties(@Nonnull Array<Property> properties) {
        this.properties = properties;
    }

    @Nonnull
    public Optimize getOptimize() {
        return optimize;
    }

    public void setOptimize(@Nonnull Optimize optimize) {
        this.optimize = optimize;
    }

    @Nonnull
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nonnull String description) {
        this.description = description;
    }

    public boolean isReliableMessagingEnabled() {
        return isReliableMessagingEnabled;
    }

    public void setReliableMessagingEnabled(boolean isReliableMessagingEnabled) {
        this.isReliableMessagingEnabled = isReliableMessagingEnabled;
    }

    @Nonnull
    public String getReliableMessagingPolicy() {
        return reliableMessagingPolicy;
    }

    public void setReliableMessagingPolicy(@Nonnull String reliableMessagingPolicy) {
        this.reliableMessagingPolicy = reliableMessagingPolicy;
    }

    public boolean isSecurityEnabled() {
        return isSecurityEnabled;
    }

    public void setSecurityEnabled(boolean isSecurityEnabled) {
        this.isSecurityEnabled = isSecurityEnabled;
    }

    @Nonnull
    public String getSecurityPolicy() {
        return securityPolicy;
    }

    public void setSecurityPolicy(@Nonnull String securityPolicy) {
        this.securityPolicy = securityPolicy;
    }

    public boolean isAddressingEnabled() {
        return isAddressingEnabled;
    }

    public void setAddressingEnabled(boolean isAddressingEnabled) {
        this.isAddressingEnabled = isAddressingEnabled;
    }

    @Nonnull
    public AddressingVersion getAddressingVersion() {
        return addressingVersion;
    }

    public void setAddressingVersion(@Nonnull AddressingVersion addressingVersion) {
        this.addressingVersion = addressingVersion;
    }

    public boolean isAddressingSeparateListener() {
        return isAddressingSeparateListener;
    }

    public void setAddressingSeparateListener(boolean isAddressingSeparateListener) {
        this.isAddressingSeparateListener = isAddressingSeparateListener;
    }

    @Nonnegative
    public int getTimeoutDuration() {
        return timeoutDuration;
    }

    public void setTimeoutDuration(@Nonnegative int timeoutDuration) {
        this.timeoutDuration = timeoutDuration;
    }

    @Nonnull
    public TimeoutAction getTimeoutAction() {
        return timeoutAction;
    }

    public void setTimeoutAction(@Nonnull TimeoutAction timeoutAction) {
        this.timeoutAction = timeoutAction;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.address();
    }

    public enum Format {
        LEAVE_AS_IS, soap11, soap12, pox, get, REST;

        public static final String TYPE_NAME = "AddressEndpointFormat";
    }

    public enum Optimize {
        LEAVE_AS_IS, mtom, swa;

        public static final String TYPE_NAME = "Optimize";
    }

    public enum AddressingVersion {
        FINAL("final"), SUBMISSION("submission");

        public static final String TYPE_NAME = "AddressingVersion";

        private final String value;

        AddressingVersion(@Nonnull String value) {
            this.value = value;
        }

        @Nonnull
        public String getValue() {
            return value;
        }

    }

    public enum TimeoutAction {
        never, discard, fault;

        public static final String TYPE_NAME = "TimeoutAction";
    }

}