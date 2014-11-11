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
package com.codenvy.ide.client.elements.endpoints.address;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.AbstractElement;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.AddressingVersion.FINAL;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.Format.LEAVE_AS_IS;
import static com.codenvy.ide.client.elements.endpoints.address.AddressEndpoint.TimeoutAction.NEVER;
import static com.codenvy.ide.client.elements.endpoints.address.Property.SERIALIZE_NAME;

/**
 * The class which describes state of Address endpoint and also has methods for changing it. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving. For detail information about Address endpoint go to
 * <a href=" https://docs.wso2.com/display/ESB460/Address+Endpoint"> https://docs.wso2.com/display/ESB460/Address+Endpoint</a>
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class AddressEndpoint extends AbstractElement {

    public static final String ELEMENT_NAME       = "Address";
    public static final String SERIALIZATION_NAME = "address";

    public static final Key<Format> FORMAT = new Key<>("Format");
    public static final Key<String> URI    = new Key<>("Uri");

    public static final Key<String>  SUSPEND_ERROR_CODE          = new Key<>("SuspendErrorCode");
    public static final Key<Integer> SUSPEND_INITIAL_DURATION    = new Key<>("SuspendInitialDuration");
    public static final Key<Integer> SUSPEND_MAXIMUM_DURATION    = new Key<>("SuspendMaximumDuration");
    public static final Key<Double>  SUSPEND_PROGRESSION_FACTORY = new Key<>("SuspendProgressionFactory");

    public static final Key<String>  RETRY_ERROR_CODES = new Key<>("RetryErrorCodes");
    public static final Key<Integer> RETRY_COUNT       = new Key<>("RetryCount");
    public static final Key<Integer> RETRY_DELAY       = new Key<>("RetryDelay");

    public static final Key<List<Property>> PROPERTIES  = new Key<>("Properties");
    public static final Key<Optimize>       OPTIMIZE    = new Key<>("Optimize");
    public static final Key<String>         DESCRIPTION = new Key<>("Description");

    public static final Key<Boolean> RELIABLE_MESSAGING_ENABLED = new Key<>("ReliableMessagingEnabled");
    public static final Key<String>  RELIABLE_MESSAGING_POLICY  = new Key<>("ReliableMessagingPolicy");

    public static final Key<Boolean> SECURITY_ENABLED = new Key<>("SecurityEnabled");
    public static final Key<String>  SECURITY_POLICY  = new Key<>("SecurityPolicy");

    public static final Key<Boolean>           ADDRESSING_ENABLED           = new Key<>("AddressingEnabled");
    public static final Key<AddressingVersion> ADDRESSING_VERSION           = new Key<>("AddressingVersion");
    public static final Key<Boolean>           ADDRESSING_SEPARATE_LISTENER = new Key<>("AddressingSeparateListener");

    public static final Key<Integer>       TIMEOUT_DURATION = new Key<>("TimeoutDuration");
    public static final Key<TimeoutAction> TIMEOUT_ACTION   = new Key<>("TimeoutAction");

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

    public static final int    DEFAULT_SUSPEND_INITIAL_DURATION    = -1;
    public static final int    DEFAULT_SUSPEND_MAXIMUM_DURATION    = 0;
    public static final double DEFAULT_SUSPEND_PROGRESSION_FACTORY = -1.0;
    public static final int    DEFAULT_RETRY_COUNT                 = 0;
    public static final int    DEFAULT_RETRY_DELAY                 = 0;
    public static final int    DEFAULT_TIMEOUT_DURATION            = 0;

    private static final List<String> SERIALIZATION_PROPERTIES = Arrays.asList(ENABLE_ADDRESSING_PROPERTY,
                                                                               ENABLE_RM_PROPERTY,
                                                                               ENABLE_SEC_PROPERTY,
                                                                               TIMEOUT_PROPERTY,
                                                                               SUSPEND_ON_FAILURE_PROPERTY,
                                                                               MAKE_FOR_SUSPENSION_PROPERTY);

    private final Provider<Property> propertyProvider;

    @Inject
    public AddressEndpoint(EditorResources resources,
                           Provider<Branch> branchProvider,
                           ElementCreatorsManager elementCreatorsManager,
                           Provider<Property> propertyProvider) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              SERIALIZATION_PROPERTIES,
              false,
              false,
              resources.address(),
              branchProvider,
              elementCreatorsManager);

        this.propertyProvider = propertyProvider;

        prepareDefaultElementState();
    }

    private void prepareDefaultElementState() {
        putProperty(FORMAT, LEAVE_AS_IS);
        putProperty(URI, "http://www.example.org/service");

        putProperty(SUSPEND_ERROR_CODE, "");
        putProperty(SUSPEND_INITIAL_DURATION, DEFAULT_SUSPEND_INITIAL_DURATION);
        putProperty(SUSPEND_MAXIMUM_DURATION, DEFAULT_SUSPEND_MAXIMUM_DURATION);
        putProperty(SUSPEND_PROGRESSION_FACTORY, DEFAULT_SUSPEND_PROGRESSION_FACTORY);

        putProperty(RETRY_ERROR_CODES, "");
        putProperty(RETRY_COUNT, DEFAULT_RETRY_COUNT);
        putProperty(RETRY_DELAY, DEFAULT_RETRY_DELAY);

        putProperty(PROPERTIES, new ArrayList<Property>());
        putProperty(OPTIMIZE, Optimize.LEAVE_AS_IS);
        putProperty(DESCRIPTION, "");

        putProperty(RELIABLE_MESSAGING_ENABLED, false);
        putProperty(RELIABLE_MESSAGING_POLICY, "/default/key");

        putProperty(SECURITY_ENABLED, false);
        putProperty(SECURITY_POLICY, "/default/key");

        putProperty(ADDRESSING_ENABLED, false);
        putProperty(ADDRESSING_VERSION, FINAL);
        putProperty(ADDRESSING_SEPARATE_LISTENER, false);

        putProperty(TIMEOUT_DURATION, DEFAULT_TIMEOUT_DURATION);
        putProperty(TIMEOUT_ACTION, NEVER);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeAttributes() {
        Map<String, String> attributes = new LinkedHashMap<>();

        attributes.put(URI_ATTRIBUTE, getProperty(URI));

        addFormatAttributeIfItIsExisted(attributes);

        addOptimizeAttributeIfItIsExisted(attributes);

        return convertAttributesToXML(attributes);
    }

    private void addFormatAttributeIfItIsExisted(@Nonnull Map<String, String> attributes) {
        Format format = getProperty(FORMAT);

        if (format == null || LEAVE_AS_IS.equals(format)) {
            return;
        }

        attributes.put(FORMAT_ATTRIBUTE, format.getValue());
    }

    private void addOptimizeAttributeIfItIsExisted(@Nonnull Map<String, String> attributes) {
        Optimize optimize = getProperty(OPTIMIZE);

        if (optimize == null || Optimize.LEAVE_AS_IS.equals(optimize)) {
            return;
        }

        attributes.put(OPTIMIZE_ATTRIBUTE, optimize.getValue());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        StringBuilder content = new StringBuilder();

        serializeAddressingProperties(content);

        serializeReliableMessagingProperties(content);

        serializeSecurityProperties(content);

        serializeTimeoutProperties(content);

        serializeSuspendProperties(content);

        serializeRetryProperties(content);

        return content.toString();
    }

    private void serializeAddressingProperties(@Nonnull StringBuilder content) {
        Boolean isAddressingEnabled = getProperty(ADDRESSING_ENABLED);
        AddressingVersion addressingVersion = getProperty(ADDRESSING_VERSION);
        Boolean isAddressingSeparateListener = getProperty(ADDRESSING_SEPARATE_LISTENER);

        if (isAddressingEnabled == null || addressingVersion == null || isAddressingSeparateListener == null || !isAddressingEnabled) {
            return;
        }

        content.append('<').append(ENABLE_ADDRESSING_PROPERTY).append(' ')
               .append(VERSION_ATTRIBUTE).append("=\"").append(addressingVersion.getValue()).append('"')
               .append(isAddressingSeparateListener ? ' ' + SEPARATE_LISTENER_ATTRIBUTE + "=\"true\"" : "")
               .append("/>\n");
    }

    private void serializeReliableMessagingProperties(@Nonnull StringBuilder content) {
        Boolean isReliableMessagingEnabled = getProperty(RELIABLE_MESSAGING_ENABLED);
        String reliableMessagingPolicy = getProperty(RELIABLE_MESSAGING_POLICY);

        if (isReliableMessagingEnabled == null || reliableMessagingPolicy == null || !isReliableMessagingEnabled) {
            return;
        }

        content.append('<').append(ENABLE_RM_PROPERTY).append(' ')
               .append(POLICY_ATTRIBUTE).append("=\"").append(reliableMessagingPolicy)
               .append("\"/>\n");
    }

    private void serializeSecurityProperties(@Nonnull StringBuilder content) {
        Boolean isSecurityEnabled = getProperty(SECURITY_ENABLED);
        String securityPolicy = getProperty(SECURITY_POLICY);

        if (isSecurityEnabled == null || securityPolicy == null || !isSecurityEnabled) {
            return;
        }

        content.append('<').append(ENABLE_SEC_PROPERTY).append(' ')
               .append(POLICY_ATTRIBUTE).append("=\"").append(securityPolicy)
               .append("\"/>\n");
    }

    private void serializeTimeoutProperties(@Nonnull StringBuilder content) {
        Integer timeoutDuration = getProperty(TIMEOUT_DURATION);
        TimeoutAction timeoutAction = getProperty(TIMEOUT_ACTION);

        if (timeoutDuration == null || timeoutAction == null) {
            return;
        }

        String value = convertIntegerValueToXMLAttribute(timeoutDuration, 1, DURATION_PROPERTY) +
                       (NEVER.equals(timeoutAction) ? "" :
                        convertStringValueToXMLAttribute(timeoutAction.getValue(), ACTION_PROPERTY));

        content.append(convertStringValueToXMLAttribute(value, TIMEOUT_PROPERTY));
    }

    private void serializeSuspendProperties(@Nonnull StringBuilder content) {
        String suspendErrorCode = getProperty(SUSPEND_ERROR_CODE);
        Integer suspendInitialDuration = getProperty(SUSPEND_INITIAL_DURATION);
        Integer suspendMaximumDuration = getProperty(SUSPEND_MAXIMUM_DURATION);
        Double suspendProgressionFactor = getProperty(SUSPEND_PROGRESSION_FACTORY);

        if (suspendErrorCode == null || suspendInitialDuration == null || suspendMaximumDuration == null ||
            suspendProgressionFactor == null || (suspendErrorCode.isEmpty() && suspendInitialDuration < 0)) {
            return;
        }

        String value = convertStringValueToXMLAttribute(suspendErrorCode, ERROR_CODES_PROPERTY) +
                       convertIntegerValueToXMLAttribute(suspendInitialDuration, 0, INITIAL_DURATION_PROPERTY) +
                       convertIntegerValueToXMLAttribute(suspendMaximumDuration, 0, MAXIMUM_DURATION_PROPERTY) +
                       convertDoubleValueToXMLAttribute(suspendProgressionFactor, 0, PROGRESSION_FACTOR_PROPERTY);

        content.append(convertStringValueToXMLAttribute(value, SUSPEND_ON_FAILURE_PROPERTY));
    }

    private void serializeRetryProperties(@Nonnull StringBuilder content) {
        String retryErrorCodes = getProperty(RETRY_ERROR_CODES);
        Integer retryCount = getProperty(RETRY_COUNT);
        Integer retryDelay = getProperty(RETRY_DELAY);

        if (retryErrorCodes == null || retryCount == null || retryDelay == null || (retryErrorCodes.isEmpty() && retryDelay <= 0)) {
            return;
        }

        String value = convertStringValueToXMLAttribute(retryErrorCodes, ERROR_CODES_PROPERTY) +
                       convertIntegerValueToXMLAttribute(retryCount, 1, RETRIES_BEFORE_SUSPENSION_PROPERTY) +
                       convertIntegerValueToXMLAttribute(retryDelay, 1, RETRY_DELAY_PROPERTY);

        content.append(convertStringValueToXMLAttribute(value, MAKE_FOR_SUSPENSION_PROPERTY));
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String serialize() {
        StringBuilder content = new StringBuilder();

        serializeProperties(content);
        serializeDescription(content);

        return super.serialize() + content;
    }

    private void serializeDescription(StringBuilder content) {
        String description = getProperty(DESCRIPTION);

        if (description == null) {
            return;
        }

        content.append(convertStringValueToXMLAttribute(description, DESCRIPTION_PROPERTY));
    }

    private void serializeProperties(StringBuilder content) {
        List<Property> properties = getProperty(PROPERTIES);

        if (properties == null) {
            return;
        }

        for (Property property : properties) {
            content.append(property.serialize());
        }
    }

    /**
     * Returns xml representation of attributes of address endpoint which to be displayed as siblings of address node.
     *
     * @param value
     *         value of attribute which need to display
     * @param tagName
     *         name of tag of attribute which need to display
     */
    @Nonnull
    private String convertStringValueToXMLAttribute(@Nonnull String value, @Nonnull String tagName) {
        return value.isEmpty() ? "" :
               '<' + tagName + ">\n" +
               value + '\n' +
               "</" + tagName + ">\n";
    }

    /**
     * Returns xml representation of attributes of address endpoint which are integer.
     *
     * @param value
     *         value of attribute which need to display
     * @param lowLimitValue
     *         minimum value of the attribute
     * @param tagName
     *         name of tag of attribute which need to display
     */
    @Nonnull
    private String convertIntegerValueToXMLAttribute(int value, int lowLimitValue, @Nonnull String tagName) {
        return value < lowLimitValue ? "" :
               '<' + tagName + ">\n" +
               value + '\n' +
               "</" + tagName + ">\n";
    }

    /**
     * Returns xml representation of attributes of address endpoint which are double.
     *
     * @param value
     *         value of attribute which need to display
     * @param lowLimitValue
     *         minimum value of the attribute
     * @param tagName
     *         name of tag of attribute which need to display
     */
    @Nonnull
    private String convertDoubleValueToXMLAttribute(double value, double lowLimitValue, @Nonnull String tagName) {
        return value < lowLimitValue ? "" :
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
    protected void applyAttribute(@Nonnull String attributeName, @Nonnull String attributeValue) {
        switch (attributeName) {
            case URI_ATTRIBUTE:
                putProperty(URI, attributeValue);
                break;

            case FORMAT_ATTRIBUTE:
                putProperty(FORMAT, Format.getItemByValue(attributeValue));
                break;

            case OPTIMIZE_ATTRIBUTE:
                putProperty(OPTIMIZE, Optimize.getItemByValue(attributeValue));
                break;

            default:
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();

        switch (nodeName) {
            case SERIALIZATION_NAME:
                applyElementProperties(node);
                break;

            case SERIALIZE_NAME:
                applySerializeNameProperty(node);
                break;

            case DESCRIPTION_PROPERTY:
                putProperty(DESCRIPTION, node.getChildNodes().item(0).getNodeValue());
                break;

            default:
        }
    }

    private void applySerializeNameProperty(@Nonnull Node node) {
        Property property = propertyProvider.get();
        property.deserialize(node);

        List<Property> properties = getProperty(PROPERTIES);

        if (properties != null) {
            properties.add(property);
        }
    }

    /**
     * Apply property from XML node to the diagram element.
     *
     * @param node
     *         XML node that need to be analyzed
     */
    private void applyElementProperties(@Nonnull Node node) {
        readXMLAttributes(node);

        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            String name = item.getNodeName();

            applyElementProperty(name, item);
        }
    }

    private void applyElementProperty(@Nonnull String name, @Nonnull Node item) {
        switch (name) {
            case ENABLE_ADDRESSING_PROPERTY:
                applyAddressingProperties(item);
                break;

            case ENABLE_RM_PROPERTY:
                putProperty(RELIABLE_MESSAGING_ENABLED, true);
                applyPolicyAttribute(item);
                break;

            case ENABLE_SEC_PROPERTY:
                putProperty(SECURITY_ENABLED, true);
                applyPolicyAttribute(item);
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

            default:
        }
    }

    private void applyPolicyAttribute(@Nonnull Node node) {
        // we should use condition because it is impossible to use hasAttributes method because it throws exception
        if (node.getAttributes().getLength() < 1) {
            return;
        }

        Node attributeNode = node.getAttributes().item(0);

        if (!POLICY_ATTRIBUTE.equals(attributeNode.getNodeName())) {
            return;
        }

        String nodeName = node.getNodeName();

        if (ENABLE_RM_PROPERTY.equals(nodeName)) {
            putProperty(RELIABLE_MESSAGING_POLICY, attributeNode.getNodeValue());
        }

        if (ENABLE_SEC_PROPERTY.equals(nodeName)) {
            putProperty(SECURITY_POLICY, attributeNode.getNodeValue());
        }
    }

    /**
     * Apply addressing properties from XML node to the diagram element.
     *
     * @param node
     *         XML node that need to be analyzed
     */
    private void applyAddressingProperties(@Nonnull Node node) {
        putProperty(ADDRESSING_ENABLED, true);

        NamedNodeMap attributeMap = node.getAttributes();

        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attributeNode = attributeMap.item(i);

            String attributeName = attributeNode.getNodeName();
            String attributeValue = attributeNode.getNodeValue();

            applyAddressingAttribute(attributeName, attributeValue);
        }
    }

    private void applyAddressingAttribute(@Nonnull String attributeName, @Nonnull String attributeValue) {
        if (VERSION_ATTRIBUTE.equals(attributeName)) {
            putProperty(ADDRESSING_VERSION, AddressingVersion.getItemByValue(attributeValue));
        }

        if (SEPARATE_LISTENER_ATTRIBUTE.equals(attributeName)) {
            putProperty(ADDRESSING_SEPARATE_LISTENER, true);
        }
    }

    /**
     * Apply timeout properties from XML node to the diagram element.
     *
     * @param node
     *         XML node that need to be analyzed
     */
    private void applyTimeoutProperties(@Nonnull Node node) {
        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);

            String propertyName = item.getNodeName();
            String propertyValue = item.getChildNodes().item(0).getNodeValue();

            applyTimeoutProperty(propertyName, propertyValue);
        }
    }

    private void applyTimeoutProperty(@Nonnull String propertyName, @Nonnull String propertyValue) {
        if (DURATION_PROPERTY.equals(propertyName)) {
            putProperty(TIMEOUT_DURATION, Integer.valueOf(propertyValue));
        }

        if (ACTION_PROPERTY.equals(propertyName)) {
            putProperty(TIMEOUT_ACTION, TimeoutAction.getItemByValue(propertyValue));
        }
    }

    /**
     * Apply suspend on failure properties from XML node to the diagram element.
     *
     * @param node
     *         XML node that need to be analyzed
     */
    private void applySuspendOnFailureProperties(@Nonnull Node node) {
        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);

            String propertyName = item.getNodeName();
            String propertyValue = item.getChildNodes().item(0).getNodeValue();

            applySuspendOnFailureProperty(propertyName, propertyValue);
        }
    }

    private void applySuspendOnFailureProperty(@Nonnull String propertyName, @Nonnull String propertyValue) {
        switch (propertyName) {
            case ERROR_CODES_PROPERTY:
                putProperty(SUSPEND_ERROR_CODE, propertyValue);
                break;

            case INITIAL_DURATION_PROPERTY:
                putProperty(SUSPEND_INITIAL_DURATION, Integer.valueOf(propertyValue));
                break;

            case PROGRESSION_FACTOR_PROPERTY:
                putProperty(SUSPEND_PROGRESSION_FACTORY, Double.valueOf(propertyValue));
                break;

            case MAXIMUM_DURATION_PROPERTY:
                putProperty(SUSPEND_MAXIMUM_DURATION, Integer.valueOf(propertyValue));
                break;

            default:
        }
    }

    /**
     * Apply retry properties from XML node to the diagram element.
     *
     * @param node
     *         XML node that need to be analyzed
     */
    private void applyRetryProperties(@Nonnull Node node) {
        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);

            String propertyName = item.getNodeName();
            String propertyValue = item.getChildNodes().item(0).getNodeValue();

            applyRetryProperty(propertyName, propertyValue);
        }
    }

    private void applyRetryProperty(@Nonnull String propertyName, @Nonnull String propertyValue) {
        switch (propertyName) {
            case ERROR_CODES_PROPERTY:
                putProperty(RETRY_ERROR_CODES, propertyValue);
                break;

            case RETRIES_BEFORE_SUSPENSION_PROPERTY:
                putProperty(RETRY_COUNT, Integer.valueOf(propertyValue));
                break;

            case RETRY_DELAY_PROPERTY:
                putProperty(RETRY_DELAY, Integer.valueOf(propertyValue));
                break;

            default:
        }
    }

    public enum Format {
        LEAVE_AS_IS("LEAVE_AS_IS"), SOUP11("soap11"), SOUP12("soap12"), POX("pox"), GET("get"), REST("REST");

        public static final String TYPE_NAME = "AddressEndpointFormat";

        private final String value;

        Format(@Nonnull String value) {
            this.value = value;
        }

        @Nonnull
        public String getValue() {
            return value;
        }

        @Nonnull
        public static Format getItemByValue(String value) {
            switch (value) {
                case "LEAVE_AS_IS":
                    return LEAVE_AS_IS;

                case "soap11":
                    return SOUP11;

                case "soap12":
                    return SOUP12;

                case "pox":
                    return POX;

                case "get":
                    return GET;

                case "REST":
                    return REST;

                default:
                    return LEAVE_AS_IS;
            }
        }
    }

    public enum Optimize {
        LEAVE_AS_IS("LEAVE_AS_IS"), MTOM("mtom"), SWA("swa");

        public static final String TYPE_NAME = "Optimize";

        private final String value;

        Optimize(@Nonnull String value) {
            this.value = value;
        }

        @Nonnull
        public String getValue() {
            return value;
        }

        @Nonnull
        public static Optimize getItemByValue(String value) {
            switch (value) {
                case "LEAVE_AS_IS":
                    return LEAVE_AS_IS;

                case "mtom":
                    return MTOM;

                case "swa":
                    return SWA;

                default:
                    return LEAVE_AS_IS;
            }
        }
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
        public static AddressingVersion getItemByValue(@Nonnull String value) {
            if ("submission".equals(value)) {
                return SUBMISSION;
            } else {
                return FINAL;
            }
        }

    }

    public enum TimeoutAction {
        NEVER("never"), DISCARD("discard"), FAULT("fault");

        public static final String TYPE_NAME = "TimeoutAction";

        private final String value;

        TimeoutAction(@Nonnull String value) {
            this.value = value;
        }

        @Nonnull
        public String getValue() {
            return value;
        }

        @Nonnull
        public static TimeoutAction getItemByValue(String value) {
            switch (value) {
                case "never":
                    return NEVER;

                case "discard":
                    return DISCARD;

                case "fault":
                    return FAULT;

                default:
                    return NEVER;
            }
        }
    }

}