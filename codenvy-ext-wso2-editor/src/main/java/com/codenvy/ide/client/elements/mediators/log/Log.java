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
package com.codenvy.ide.client.elements.mediators.log;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.AbstractElement;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The class which describes state of Log mediator and also has methods for changing it. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving. For more information about Log mediator go to
 * <a href=" https://docs.wso2.com/display/ESB460/Log+Mediator"> https://docs.wso2.com/display/ESB460/Log+Mediator</a>
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class Log extends AbstractElement {

    public static final String ELEMENT_NAME       = "Log";
    public static final String SERIALIZATION_NAME = "log";

    private static final String CATEGORY_ATTRIBUTE_NAME    = "category";
    private static final String LEVEL_ATTRIBUTE_NAME       = "level";
    private static final String SEPARATOR_ATTRIBUTE_NAME   = "separator";
    private static final String DESCRIPTION_ATTRIBUTE_NAME = "description";

    private static final String PROPERTIES_PROPERTY_NAME = "property";

    private static final List<String> PROPERTIES = Arrays.asList(PROPERTIES_PROPERTY_NAME);

    private LogCategory     logCategory;
    private LogLevel        logLevel;
    private String          logSeparator;
    private String          description;
    private Array<Property> properties;

    @Inject
    public Log(EditorResources resources, Provider<Branch> branchProvider, MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        logCategory = LogCategory.INFO;
        logLevel = LogLevel.SIMPLE;
        logSeparator = "";
        description = "";
        properties = Collections.createArray();
    }

    /** @return category value of element */
    @Nonnull
    public LogCategory getLogCategory() {
        return logCategory;
    }

    /**
     * Sets value of category to element
     *
     * @param logCategory
     *         value that should be set
     */
    public void setLogCategory(@Nullable LogCategory logCategory) {
        this.logCategory = logCategory;
    }

    /** @return level value of element */
    @Nonnull
    public LogLevel getLogLevel() {
        return logLevel;
    }

    /**
     * Sets value of level to element
     *
     * @param logLevel
     *         value that should be set
     */
    public void setLogLevel(@Nullable LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    /** @return separator value of element */
    @Nullable
    public String getLogSeparator() {
        return logSeparator;
    }

    /**
     * Sets value of separator to element
     *
     * @param logSeparator
     *         value that should be set
     */
    public void setLogSeparator(@Nullable String logSeparator) {
        this.logSeparator = logSeparator;
    }

    /** @return list of properties of element */
    @Nonnull
    public Array<Property> getLogProperties() {
        return properties;
    }

    /**
     * Sets list of properties to element
     *
     * @param logProperties
     *         list of properties that should be set
     */
    public void setLogProperties(@Nonnull Array<Property> logProperties) {
        this.properties = logProperties;
    }

    /** @return category value of element */
    @Nullable
    public String getDescription() {
        return description;
    }

    /**
     * Sets value of description to element
     *
     * @param description
     *         value that should be set
     */
    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        Map<String, String> attributes = new LinkedHashMap<>();

        if (!logCategory.equals(LogCategory.INFO)) {
            attributes.put(CATEGORY_ATTRIBUTE_NAME, logCategory.name());
        }

        if (!logLevel.equals(LogLevel.SIMPLE)) {
            attributes.put(LEVEL_ATTRIBUTE_NAME, logLevel.name());
        }

        attributes.put(SEPARATOR_ATTRIBUTE_NAME, logSeparator);
        attributes.put(DESCRIPTION_ATTRIBUTE_NAME, description);

        return convertPropertiesToXMLFormat(attributes);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        StringBuilder result = new StringBuilder();

        for (Property property : properties.asIterable()) {
            StringBuilder nameSpaces = new StringBuilder();

            for (NameSpace nameSpace : property.getNameSpaces().asIterable()) {
                nameSpaces.append(nameSpace.toString()).append(' ');
            }

            result.append("<property ").append(nameSpaces).append("name=\"").append(property.getName()).append("\" value=\"")
                  .append(property.getExpression()).append("\"/>\n");
        }

        return result.toString();
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();

        switch (nodeName) {
            case PROPERTIES_PROPERTY_NAME:
                //TODO create property using editor factory
                Property property = new Property(null, null);
                property.applyAttributes(node);

                properties.add(property);
                break;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void applyAttributes(@Nonnull Node node) {
        NamedNodeMap attributeMap = node.getAttributes();

        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attributeNode = attributeMap.item(i);

            String attributeValue = attributeNode.getNodeValue();

            switch (attributeNode.getNodeName()) {
                case CATEGORY_ATTRIBUTE_NAME:
                    logCategory = LogCategory.valueOf(attributeValue);
                    break;

                case LEVEL_ATTRIBUTE_NAME:
                    logLevel = LogLevel.valueOf(attributeValue);
                    break;

                case SEPARATOR_ATTRIBUTE_NAME:
                    logSeparator = attributeNode.getNodeValue();
                    break;

                case DESCRIPTION_ATTRIBUTE_NAME:
                    description = attributeNode.getNodeValue();
                    break;
            }
        }
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public ImageResource getIcon() {
        return resources.log();
    }

    public enum LogCategory {
        TRACE, DEBUG, INFO, WARN, ERROR, FATAL;

        public static final String TYPE_NAME = "LogCategory";
    }

    public enum LogLevel {
        SIMPLE, HEADERS, FULL, CUSTOM;

        public static final String TYPE_NAME = "LogLevel";
    }

}