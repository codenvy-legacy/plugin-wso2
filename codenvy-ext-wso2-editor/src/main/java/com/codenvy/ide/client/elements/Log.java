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
package com.codenvy.ide.client.elements;

import com.google.gwt.xml.client.Node;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

/**
 * @author Andrey Plotnikov
 */
public class Log extends RootElement {
    public static final String ELEMENT_NAME       = "Log";
    public static final String SERIALIZATION_NAME = "log";

    private static final String LOG_CATEGORY_PROPERTY_NAME   = "LogCategory";
    private static final String LOG_LEVEL_PROPERTY_NAME      = "LogLevel";
    private static final String LOG_SEPARATOR_PROPERTY_NAME  = "LogSeparator";
    private static final String LOG_PROPERTIES_PROPERTY_NAME = "LogProperties";
    private static final String DESCRIPTION_PROPERTY_NAME    = "Description";

    private static final List<String> PROPERTIES          = Arrays.asList(LOG_CATEGORY_PROPERTY_NAME,
                                                                          LOG_LEVEL_PROPERTY_NAME,
                                                                          LOG_SEPARATOR_PROPERTY_NAME,
                                                                          LOG_PROPERTIES_PROPERTY_NAME,
                                                                          DESCRIPTION_PROPERTY_NAME);
    private static final List<String> INTERNAL_PROPERTIES = Arrays.asList(X_PROPERTY_NAME,
                                                                          Y_PROPERTY_NAME,
                                                                          UUID_PROPERTY_NAME,
                                                                          AUTO_ALIGN_PROPERTY_NAME,
                                                                          LOG_CATEGORY_PROPERTY_NAME,
                                                                          LOG_LEVEL_PROPERTY_NAME,
                                                                          LOG_SEPARATOR_PROPERTY_NAME,
                                                                          LOG_PROPERTIES_PROPERTY_NAME,
                                                                          DESCRIPTION_PROPERTY_NAME);

    private String logCategory;
    private String logLevel;
    private String logSeparator;
    private String logProperties;
    private String description;

    public Log() {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, INTERNAL_PROPERTIES);

        logCategory = "INFO";
        logLevel = "SIMPLE";
        logSeparator = "enter_text";
        logProperties = "enter_properties";
        description = "enter_description";
    }

    @Nullable
    public String getLogCategory() {
        return logCategory;
    }

    public void setLogCategory(@Nullable String logCategory) {
        this.logCategory = logCategory;
    }

    @Nullable
    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(@Nullable String logLevel) {
        this.logLevel = logLevel;
    }

    @Nullable
    public String getLogSeparator() {
        return logSeparator;
    }

    public void setLogSeparator(@Nullable String logSeparator) {
        this.logSeparator = logSeparator;
    }

    @Nullable
    public String getLogProperties() {
        return logProperties;
    }

    public void setLogProperties(@Nullable String logProperties) {
        this.logProperties = logProperties;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeProperties() {
        return "logCategory=\"" + logCategory + "\" " +
               "logLevel=\"" + logLevel + "\" " +
               "logSeparator=\"" + logSeparator + "\" " +
               "logProperties=\"" + logProperties + "\" " +
               "description=\"" + description + "\" ";
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
            case AbstractShape.AUTO_ALIGN_PROPERTY_NAME:
                setAutoAlignmentParam(Boolean.valueOf(nodeValue));
                break;
            case LOG_CATEGORY_PROPERTY_NAME:
                logCategory = String.valueOf(nodeValue);
                break;
            case LOG_LEVEL_PROPERTY_NAME:
                logLevel = String.valueOf(nodeValue);
                break;
            case LOG_SEPARATOR_PROPERTY_NAME:
                logSeparator = String.valueOf(nodeValue);
                break;
            case LOG_PROPERTIES_PROPERTY_NAME:
                logProperties = String.valueOf(nodeValue);
                break;
            case DESCRIPTION_PROPERTY_NAME:
                description = String.valueOf(nodeValue);
                break;
        }
    }

}