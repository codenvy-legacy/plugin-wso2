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
package com.codenvy.ide.client.elements.log;

import com.codenvy.ide.client.elements.AbstractElement;
import com.codenvy.ide.client.elements.AbstractShape;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.RootElement;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Class describes entity which presented as Log mediator.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class Log extends RootElement {

    public static final String ELEMENT_NAME       = "Log";
    public static final String SERIALIZATION_NAME = "log";

    private static final String CATEGORY_NAME    = "category";
    private static final String LEVEL_NAME       = "level";
    private static final String SEPARATOR_NAME   = "separator";
    private static final String PROPERTIES_NAME  = "property";
    private static final String DESCRIPTION_NAME = "description";

    private static final List<String> PROPERTIES          = Arrays.asList(PROPERTIES_NAME);
    private static final List<String> INTERNAL_PROPERTIES = Arrays.asList(X_PROPERTY_NAME,
                                                                          Y_PROPERTY_NAME,
                                                                          UUID_PROPERTY_NAME,
                                                                          PROPERTIES_NAME);

    private String          logCategory;
    private String          logLevel;
    private String          logSeparator;
    private String          description;
    private Array<Property> properties;

    public Log() {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, INTERNAL_PROPERTIES);

        logCategory = LogCategory.INFO.name();
        logLevel = LogLevel.SIMPLE.name();
        logSeparator = "";
        description = "";
        properties = Collections.createArray();
    }

    /** @return category value of element */
    @Nullable
    public String getLogCategory() {
        return logCategory;
    }

    /** Sets value of category to element */
    public void setLogCategory(@Nullable String logCategory) {
        this.logCategory = logCategory;
    }

    /** @return level value of element */
    @Nullable
    public String getLogLevel() {
        return logLevel;
    }

    /** Sets value of level to element */
    public void setLogLevel(@Nullable String logLevel) {
        this.logLevel = logLevel;
    }

    /** @return separator value of element */
    @Nullable
    public String getLogSeparator() {
        return logSeparator;
    }

    /** Sets value of separator to element */
    public void setLogSeparator(@Nullable String logSeparator) {
        this.logSeparator = logSeparator;
    }

    /** @return list of properties of element */
    @Nonnull
    public Array<Property> getLogProperties() {
        return properties;
    }

    /** Sets list of properties to element */
    public void setLogProperties(@Nonnull Array<Property> logProperties) {
        this.properties = logProperties;
    }

    /** @return category value of element */
    @Nullable
    public String getDescription() {
        return description;
    }

    /** Sets value of description to element */
    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        LinkedHashMap<String, String> attributes = new LinkedHashMap<>();

        if (!logCategory.equals(LogCategory.INFO.name())) {
            attributes.put(CATEGORY_NAME, logCategory);
        }

        if (!logLevel.equals(LogLevel.SIMPLE.name())) {
            attributes.put(LEVEL_NAME, logLevel);
        }

        attributes.put(SEPARATOR_NAME, logSeparator);
        attributes.put(DESCRIPTION_NAME, description);

        return prepareSerialization(attributes);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperty() {
        StringBuilder result = new StringBuilder();

        for (Property property : properties.asIterable()) {
            StringBuilder nameSpaces = new StringBuilder();

            for (NameSpace nameSpace : property.getNameSpaces().asIterable()) {
                nameSpaces.append(nameSpace.toString()).append(' ');
            }

            result.append("    <property ").append(nameSpaces).append("name=\"").append(property.getName()).append("\" value=\"")
                  .append(property.getExpression()).append("\"/>\n");
        }

        return result.toString();
    }

    /** {@inheritDoc} */
    @Override
    public void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        switch (nodeName) {
            case AbstractShape.X_PROPERTY_NAME:
                setX(Integer.valueOf(nodeValue));
                break;

            case AbstractShape.Y_PROPERTY_NAME:
                setY(Integer.valueOf(nodeValue));
                break;

            case AbstractElement.UUID_PROPERTY_NAME:
                id = nodeValue;
                break;

            case PROPERTIES_NAME:
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

            switch (attributeNode.getNodeName()) {
                case CATEGORY_NAME:
                    logCategory = attributeNode.getNodeValue();
                    break;

                case LEVEL_NAME:
                    logLevel = attributeNode.getNodeValue();
                    break;

                case SEPARATOR_NAME:
                    logSeparator = attributeNode.getNodeValue();
                    break;

                case DESCRIPTION_NAME:
                    description = attributeNode.getNodeValue();
                    break;
            }
        }
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