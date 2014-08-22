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
import com.codenvy.ide.collections.Collections;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.elements.addressendpoint.AddressEndpoint.AddressingVersion.FINAL;
import static com.codenvy.ide.client.elements.addressendpoint.AddressEndpoint.Format.LEAVE_AS_IS;
import static com.codenvy.ide.client.elements.addressendpoint.AddressEndpoint.TimeoutAction.never;

/**
 * The entity that represents 'Address' endpoint from ESB configuration.
 *
 * @author Andrey Plotnikov
 */
public class AddressEndpoint extends AbstractShape {

    public static final String ELEMENT_NAME       = "Address";
    public static final String SERIALIZATION_NAME = "address";

    private static final String URI_ATTRIBUTE      = "uri";
    private static final String FORMAT_ATTRIBUTE   = "format";
    private static final String OPTIMIZE_ATTRIBUTE = "optimize";

    private static final String POLICY_ATTRIBUTE            = "policy";
    private static final String VERSION_ATTRIBUTE           = "version";
    private static final String SEPARATE_LISTENER_ATTRIBUTE = "separateListener";


    private static final String ENABLE_RM_PROPERTY         = "enableRM";
    private static final String ENABLE_ADDRESSING_PROPERTY = "enableAddressing";
    private static final String ENABLE_SEC_PROPERTY        = "enableSec";

    private static final String TIMEOUT_PROPERTY  = "timeout";
    private static final String DURATION_PROPERTY = "duration";
    private static final String ACTION_PROPERTY   = "responseAction";

    private static final String SUSPEND_ON_FAILURE_PROPERTY = "suspendOnFailure";
    private static final String ERROR_CODES_PROPERTY        = "errorCodes";
    private static final String INITIAL_DURATION_PROPERTY   = "initialDuration";
    private static final String PROGRESSION_FACTOR_PROPERTY = "progressionFactor";
    private static final String MAXIMUM_DURATION_PROPERTY   = "maximumDuration";

    private static final String MAKE_FOR_SUSPENSION_PROPERTY       = "markForSuspension";
    private static final String RETRIES_BEFORE_SUSPENSION_PROPERTY = "retriesBeforeSuspension";
    private static final String RETRY_DELAY_PROPERTY               = "retryDelay";

    private static final String DESCRIPTION_PROPERTY = "description";

    private static final List<String> PROPERTIES = Arrays.asList(ENABLE_ADDRESSING_PROPERTY,
                                                                 ENABLE_RM_PROPERTY,
                                                                 ENABLE_SEC_PROPERTY,
                                                                 TIMEOUT_PROPERTY,
                                                                 SUSPEND_ON_FAILURE_PROPERTY,
                                                                 MAKE_FOR_SUSPENSION_PROPERTY);

    private final Provider<Property> propertyProvider;

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
    public AddressEndpoint(EditorResources resources,
                           Provider<Branch> branchProvider,
                           MediatorCreatorsManager mediatorCreatorsManager,
                           Provider<Property> propertyProvider) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        this.propertyProvider = propertyProvider;

        format = LEAVE_AS_IS;
        uri = "http://www.example.org/service";

        suspendErrorCode = "";
        suspendInitialDuration = -1;
        suspendMaximumDuration = 0;
        suspendProgressionFactor = -1.0;

        retryErrorCodes = "";
        retryCount = 0;
        retryDelay = 0;

        properties = Collections.createArray();
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
    @Nonnull
    @Override
    protected String serializeAttributes() {
        return URI_ATTRIBUTE + "=\"" + uri + '"' +
               (LEAVE_AS_IS.equals(format) ? "" : ' ' + FORMAT_ATTRIBUTE + "=\"" + format.name() + '"') +
               (Optimize.LEAVE_AS_IS.equals(optimize) ? "" : ' ' + OPTIMIZE_ATTRIBUTE + "=\"" + optimize.name() + '"');
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        String content = "";
        String value;

        if (isAddressingEnabled) {
            content += '<' + ENABLE_ADDRESSING_PROPERTY + ' ' +
                       VERSION_ATTRIBUTE + "=\"" + addressingVersion.getValue() + '"' +
                       (isAddressingSeparateListener ? ' ' + SEPARATE_LISTENER_ATTRIBUTE + "=\"true\"" : "") + "/>\n";
        }

        if (isReliableMessagingEnabled) {
            content += '<' + ENABLE_RM_PROPERTY + ' ' + POLICY_ATTRIBUTE + "=\"" + reliableMessagingPolicy + "\"/>\n";
        }

        if (isSecurityEnabled) {
            content += '<' + ENABLE_SEC_PROPERTY + ' ' + POLICY_ATTRIBUTE + "=\"" + securityPolicy + "\"/>\n";
        }

        value = convertNumberValueToXMLAttribute(timeoutDuration, 1, DURATION_PROPERTY) +
                (never.equals(timeoutAction) ?
                 "" :
                 convertStringValueToXMLAttribute(timeoutAction.name(), ACTION_PROPERTY));

        content += convertStringValueToXMLAttribute(value, TIMEOUT_PROPERTY);

        if (!suspendErrorCode.isEmpty() || suspendInitialDuration >= 0) {
            value = convertStringValueToXMLAttribute(suspendErrorCode, ERROR_CODES_PROPERTY) +
                    convertNumberValueToXMLAttribute(suspendInitialDuration, 0, INITIAL_DURATION_PROPERTY) +
                    convertNumberValueToXMLAttribute(suspendMaximumDuration, 0, MAXIMUM_DURATION_PROPERTY) +
                    convertNumberValueToXMLAttribute(suspendProgressionFactor, 0, PROGRESSION_FACTOR_PROPERTY);

            content += convertStringValueToXMLAttribute(value, SUSPEND_ON_FAILURE_PROPERTY);
        }

        if (!retryErrorCodes.isEmpty() || retryDelay > 0) {
            value = convertStringValueToXMLAttribute(retryErrorCodes, ERROR_CODES_PROPERTY) +
                    convertNumberValueToXMLAttribute(retryCount, 1, RETRIES_BEFORE_SUSPENSION_PROPERTY) +
                    convertNumberValueToXMLAttribute(retryDelay, 1, RETRY_DELAY_PROPERTY);

            content += convertStringValueToXMLAttribute(value, MAKE_FOR_SUSPENSION_PROPERTY);
        }

        return content;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String serialize() {
        StringBuilder content = new StringBuilder();

        for (Property property : properties.asIterable()) {
            content.append(property.serialize());
        }

        content.append(convertStringValueToXMLAttribute(description, DESCRIPTION_PROPERTY));

        return super.serialize() + content;
    }

    @Nonnull
    private String convertStringValueToXMLAttribute(@Nonnull String value, @Nonnull String tagName) {
        return value.isEmpty() ?
               "" :
               '<' + tagName + ">\n" +
               value + '\n' +
               "</" + tagName + ">\n";
    }

    @Nonnull
    private String convertNumberValueToXMLAttribute(double value, double lowLimitValue, @Nonnull String tagName) {
        return value < lowLimitValue ?
               "" :
               '<' + tagName + ">\n" +
               value + '\n' +
               "</" + tagName + ">\n";
    }

    /** {@inheritDoc} */
    @Override
    public void deserialize(@Nonnull Node node) {
        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);

            applyProperty(item);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void applyAttributes(@Nonnull Node node) {
        NamedNodeMap attributeMap = node.getAttributes();

        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attributeNode = attributeMap.item(i);

            String nodeName = attributeNode.getNodeName();
            String nodeValue = attributeNode.getNodeValue();

            switch (nodeName) {
                case URI_ATTRIBUTE:
                    uri = nodeValue;
                    break;

                case FORMAT_ATTRIBUTE:
                    format = Format.valueOf(nodeValue);
                    break;

                case OPTIMIZE_ATTRIBUTE:
                    optimize = Optimize.valueOf(nodeValue);
                    break;
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();

        switch (nodeName) {
            case SERIALIZATION_NAME:
                applyElementProperty(node);
                break;

            case Property.SERIALIZE_NAME:
                Property property = propertyProvider.get();
                property.applyAttributes(node);

                properties.add(property);
                break;

            case DESCRIPTION_PROPERTY:
                description = node.getChildNodes().item(0).getNodeValue();
                break;
        }
    }

    private void applyElementProperty(@Nonnull Node node) {
        applyAttributes(node);

        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            String name = item.getNodeName();

            switch (name) {
                case ENABLE_ADDRESSING_PROPERTY:
                    applyAddressingProperties(item);
                    break;

                case ENABLE_RM_PROPERTY: {
                    isReliableMessagingEnabled = true;

                    if (node.hasAttributes()) {
                        Node attributeNode = node.getAttributes().item(0);

                        reliableMessagingPolicy = attributeNode.getNodeValue();
                    }
                }
                break;

                case ENABLE_SEC_PROPERTY: {
                    isSecurityEnabled = true;

                    if (node.hasAttributes()) {
                        Node attributeNode = node.getAttributes().item(0);

                        securityPolicy = attributeNode.getNodeValue();
                    }
                }
                break;

                case TIMEOUT_PROPERTY:
                    applyTimeoutProperties(item);
                    break;

                case SUSPEND_ON_FAILURE_PROPERTY:
                    applySuspendOnFailureProperties(item);
                    break;

                case MAKE_FOR_SUSPENSION_PROPERTY:
                    applyRetryProperties(item);
                    break;
            }
        }
    }

    private void applyAddressingProperties(@Nonnull Node node) {
        isAddressingEnabled = true;

        NamedNodeMap attributeMap = node.getAttributes();

        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attributeNode = attributeMap.item(i);

            String attributeName = attributeNode.getNodeName();
            String attributeValue = attributeNode.getNodeValue();

            switch (attributeName) {
                case VERSION_ATTRIBUTE:
                    addressingVersion = AddressingVersion.getItemByValue(attributeValue);
                    break;

                case SEPARATE_LISTENER_ATTRIBUTE:
                    isAddressingSeparateListener = true;
                    break;
            }
        }
    }

    private void applyTimeoutProperties(@Nonnull Node node) {
        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);

            String propertyName = item.getNodeName();
            String propertyValue = item.getChildNodes().item(0).getNodeValue();

            switch (propertyName) {
                case DURATION_PROPERTY:
                    timeoutDuration = Integer.valueOf(propertyValue);
                    break;

                case ACTION_PROPERTY:
                    timeoutAction = TimeoutAction.valueOf(propertyValue);
                    break;
            }
        }
    }

    private void applySuspendOnFailureProperties(@Nonnull Node node) {
        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);

            String propertyName = item.getNodeName();
            String propertyValue = item.getChildNodes().item(0).getNodeValue();

            switch (propertyName) {
                case ERROR_CODES_PROPERTY:
                    suspendErrorCode = propertyValue;
                    break;

                case INITIAL_DURATION_PROPERTY:
                    suspendInitialDuration = Integer.valueOf(propertyValue);
                    break;

                case PROGRESSION_FACTOR_PROPERTY:
                    suspendProgressionFactor = Double.valueOf(propertyValue);
                    break;

                case MAXIMUM_DURATION_PROPERTY:
                    suspendMaximumDuration = Integer.valueOf(propertyValue);
                    break;
            }
        }
    }

    private void applyRetryProperties(@Nonnull Node node) {
        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);

            String propertyName = item.getNodeName();
            String propertyValue = item.getChildNodes().item(0).getNodeValue();

            switch (propertyName) {
                case ERROR_CODES_PROPERTY:
                    retryErrorCodes = propertyValue;
                    break;

                case RETRIES_BEFORE_SUSPENSION_PROPERTY:
                    retryCount = Integer.valueOf(propertyValue);
                    break;

                case RETRY_DELAY_PROPERTY:
                    retryDelay = Integer.valueOf(propertyValue);
                    break;
            }
        }
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

        @Nonnull
        public static AddressingVersion getItemByValue(String value) {
            switch (value) {
                case "submission":
                    return SUBMISSION;

                case "final":
                default:
                    return FINAL;
            }
        }

    }

    public enum TimeoutAction {
        never, discard, fault;

        public static final String TYPE_NAME = "TimeoutAction";
    }

}