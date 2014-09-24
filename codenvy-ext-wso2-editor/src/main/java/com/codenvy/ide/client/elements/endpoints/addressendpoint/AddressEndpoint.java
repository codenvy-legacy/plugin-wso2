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
package com.codenvy.ide.client.elements.endpoints.addressendpoint;

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

import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.AddressingVersion.FINAL;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.Format.LEAVE_AS_IS;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint.TimeoutAction.NEVER;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.Property.SERIALIZE_NAME;

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
              true,
              resources.address(),
              branchProvider,
              elementCreatorsManager);

        this.propertyProvider = propertyProvider;

        putProperty(FORMAT, LEAVE_AS_IS);
        putProperty(URI, "http://www.example.org/service");

        putProperty(SUSPEND_ERROR_CODE, "");
        putProperty(SUSPEND_INITIAL_DURATION, -1);
        putProperty(SUSPEND_MAXIMUM_DURATION, 0);
        putProperty(SUSPEND_PROGRESSION_FACTORY, -1.0);

        putProperty(RETRY_ERROR_CODES, "");
        putProperty(RETRY_COUNT, 0);
        putProperty(RETRY_DELAY, 0);

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

        putProperty(TIMEOUT_DURATION, 0);
        putProperty(TIMEOUT_ACTION, NEVER);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeAttributes() {
        Map<String, String> attributes = new LinkedHashMap<>();

        attributes.put(URI_ATTRIBUTE, getProperty(URI));

        Format format = getProperty(FORMAT);

        if (format != null && !LEAVE_AS_IS.equals(format)) {
            attributes.put(FORMAT_ATTRIBUTE, format.getValue());
        }

        Optimize optimize = getProperty(OPTIMIZE);

        if (optimize != null && !Optimize.LEAVE_AS_IS.equals(optimize)) {
            attributes.put(OPTIMIZE_ATTRIBUTE, optimize.getValue());
        }

        return convertAttributesToXMLFormat(attributes);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        String content = "";
        String value;

        Boolean isAddressingEnabled = getProperty(ADDRESSING_ENABLED);
        AddressingVersion addressingVersion = getProperty(ADDRESSING_VERSION);
        Boolean isAddressingSeparateListener = getProperty(ADDRESSING_SEPARATE_LISTENER);

        if (isAddressingEnabled != null && addressingVersion != null && isAddressingSeparateListener != null && isAddressingEnabled) {
            content += '<' + ENABLE_ADDRESSING_PROPERTY + ' ' +
                       VERSION_ATTRIBUTE + "=\"" + addressingVersion.getValue() + '"' +
                       (isAddressingSeparateListener ? ' ' + SEPARATE_LISTENER_ATTRIBUTE + "=\"true\"" : "") + "/>\n";
        }

        Boolean isReliableMessagingEnabled = getProperty(RELIABLE_MESSAGING_ENABLED);
        String reliableMessagingPolicy = getProperty(RELIABLE_MESSAGING_POLICY);

        if (isReliableMessagingEnabled != null && reliableMessagingPolicy != null && isReliableMessagingEnabled) {
            content += '<' + ENABLE_RM_PROPERTY + ' ' + POLICY_ATTRIBUTE + "=\"" + reliableMessagingPolicy + "\"/>\n";
        }

        Boolean isSecurityEnabled = getProperty(SECURITY_ENABLED);
        String securityPolicy = getProperty(SECURITY_POLICY);

        if (isSecurityEnabled != null && securityPolicy != null && isSecurityEnabled) {
            content += '<' + ENABLE_SEC_PROPERTY + ' ' + POLICY_ATTRIBUTE + "=\"" + securityPolicy + "\"/>\n";
        }

        Integer timeoutDuration = getProperty(TIMEOUT_DURATION);
        TimeoutAction timeoutAction = getProperty(TIMEOUT_ACTION);

        if (timeoutDuration != null && timeoutAction != null) {
            value = convertNumberValueToXMLAttribute(timeoutDuration, 1, DURATION_PROPERTY) +
                    (NEVER.equals(timeoutAction) ?
                     "" :
                     convertStringValueToXMLAttribute(timeoutAction.getValue(), ACTION_PROPERTY));

            content += convertStringValueToXMLAttribute(value, TIMEOUT_PROPERTY);
        }

        String suspendErrorCode = getProperty(SUSPEND_ERROR_CODE);
        Integer suspendInitialDuration = getProperty(SUSPEND_INITIAL_DURATION);
        Integer suspendMaximumDuration = getProperty(SUSPEND_MAXIMUM_DURATION);
        Double suspendProgressionFactor = getProperty(SUSPEND_PROGRESSION_FACTORY);

        if (suspendErrorCode != null && suspendInitialDuration != null && suspendMaximumDuration != null &&
            suspendProgressionFactor != null && (!suspendErrorCode.isEmpty() || suspendInitialDuration >= 0)) {
            value = convertStringValueToXMLAttribute(suspendErrorCode, ERROR_CODES_PROPERTY) +
                    convertNumberValueToXMLAttribute(suspendInitialDuration, 0, INITIAL_DURATION_PROPERTY) +
                    convertNumberValueToXMLAttribute(suspendMaximumDuration, 0, MAXIMUM_DURATION_PROPERTY) +
                    convertNumberValueToXMLAttribute(suspendProgressionFactor, 0, PROGRESSION_FACTOR_PROPERTY);

            content += convertStringValueToXMLAttribute(value, SUSPEND_ON_FAILURE_PROPERTY);
        }

        String retryErrorCodes = getProperty(RETRY_ERROR_CODES);
        Integer retryCount = getProperty(RETRY_COUNT);
        Integer retryDelay = getProperty(RETRY_DELAY);

        if (retryErrorCodes != null && retryCount != null && retryDelay != null && (!retryErrorCodes.isEmpty() || retryDelay > 0)) {
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

        List<Property> properties = getProperty(PROPERTIES);

        if (properties != null) {
            for (Property property : properties) {
                content.append(property.serialize());
            }
        }

        String description = getProperty(DESCRIPTION);

        if (description != null) {
            content.append(convertStringValueToXMLAttribute(description, DESCRIPTION_PROPERTY));
        }

        return super.serialize() + content;
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
        return value.isEmpty() ?
               "" :
               '<' + tagName + ">\n" +
               value + '\n' +
               "</" + tagName + ">\n";
    }

    /**
     * Returns xml representation of attributes of address endpoint which are numbers.
     *
     * @param value
     *         value of attribute which need to display
     * @param lowLimitValue
     *         minimum value of the attribute
     * @param tagName
     *         name of tag of attribute which need to display
     */
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
                applyElementProperty(node);
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
    private void applyElementProperty(@Nonnull Node node) {
        readXMLAttributes(node);

        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            String name = item.getNodeName();

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
    }

    private void applyPolicyAttribute(@Nonnull Node node) {
        if (!node.hasAttributes()) {
            return;
        }

        Node attributeNode = node.getAttributes().item(0);

        boolean isEnableRmProperty = ENABLE_RM_PROPERTY.equals(node.getNodeName());

        putProperty(isEnableRmProperty ? RELIABLE_MESSAGING_POLICY : SECURITY_POLICY, attributeNode.getNodeValue());
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

            switch (attributeName) {
                case VERSION_ATTRIBUTE:
                    putProperty(ADDRESSING_VERSION, AddressingVersion.getItemByValue(attributeValue));
                    break;

                case SEPARATE_LISTENER_ATTRIBUTE:
                    putProperty(ADDRESSING_SEPARATE_LISTENER, true);
                    break;

                default:
            }
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

            if (DURATION_PROPERTY.equals(propertyName)) {
                putProperty(TIMEOUT_DURATION, Integer.valueOf(propertyValue));
            } else {
                putProperty(TIMEOUT_ACTION, TimeoutAction.getItemByValue(propertyValue));
            }
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

                default:
                    return REST;
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

                default:
                    return SWA;
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

                default:
                    return FAULT;
            }
        }
    }

}