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
package com.codenvy.ide.client.elements.mediators.log;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.AbstractElement;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.mediators.log.Log.LogCategory.INFO;
import static com.codenvy.ide.client.elements.mediators.log.Log.LogLevel.SIMPLE;

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

    public static final Key<LogCategory>    LOG_CATEGORY   = new Key<>("LogCategory");
    public static final Key<LogLevel>       LOG_LEVEL      = new Key<>("LogLevel");
    public static final Key<String>         LOG_SEPARATOR  = new Key<>("LogSeparator");
    public static final Key<String>         DESCRIPTION    = new Key<>("LogDescription");
    public static final Key<List<Property>> LOG_PROPERTIES = new Key<>("LogProperties");

    private static final String CATEGORY_ATTRIBUTE_NAME    = "category";
    private static final String LEVEL_ATTRIBUTE_NAME       = "level";
    private static final String SEPARATOR_ATTRIBUTE_NAME   = "separator";
    private static final String DESCRIPTION_ATTRIBUTE_NAME = "description";

    private static final String PROPERTIES_PROPERTY_NAME = "property";

    private static final List<String> PROPERTIES = Arrays.asList(PROPERTIES_PROPERTY_NAME);

    private final Provider<Property> propertyProvider;

    @Inject
    public Log(EditorResources resources,
               Provider<Branch> branchProvider,
               ElementCreatorsManager elementCreatorsManager,
               Provider<Property> propertyProvider) {

        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.log(),
              branchProvider,
              elementCreatorsManager);

        this.propertyProvider = propertyProvider;

        putProperty(LOG_CATEGORY, INFO);
        putProperty(LOG_LEVEL, SIMPLE);
        putProperty(LOG_SEPARATOR, "");
        putProperty(DESCRIPTION, "");
        putProperty(LOG_PROPERTIES, new ArrayList<Property>());
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        Map<String, String> attributes = new LinkedHashMap<>();

        LogCategory logCategory = getProperty(LOG_CATEGORY);

        if (logCategory != null && !logCategory.equals(INFO)) {
            attributes.put(CATEGORY_ATTRIBUTE_NAME, logCategory.name());
        }

        LogLevel logLevel = getProperty(LOG_LEVEL);

        if (logLevel != null && !logLevel.equals(SIMPLE)) {
            attributes.put(LEVEL_ATTRIBUTE_NAME, logLevel.name().toLowerCase());
        }

        attributes.put(SEPARATOR_ATTRIBUTE_NAME, getProperty(LOG_SEPARATOR));
        attributes.put(DESCRIPTION_ATTRIBUTE_NAME, getProperty(DESCRIPTION));

        return convertAttributesToXML(attributes);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        List<Property> properties = getProperty(LOG_PROPERTIES);

        if (properties == null) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        for (Property property : properties) {
            result.append(property.serializeProperty());
        }

        return result.toString();
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();

        if (PROPERTIES_PROPERTY_NAME.equals(nodeName)) {
            Property property = propertyProvider.get();

            property.applyAttributes(node);

            List<Property> properties = getProperty(LOG_PROPERTIES);

            if (properties != null) {
                properties.add(property);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void applyAttribute(@Nonnull String attributeName, @Nonnull String attributeValue) {
        switch (attributeName) {
            case CATEGORY_ATTRIBUTE_NAME:
                putProperty(LOG_CATEGORY, LogCategory.valueOf(attributeValue));
                break;

            case LEVEL_ATTRIBUTE_NAME:
                putProperty(LOG_LEVEL, LogLevel.valueOf(attributeValue));
                break;

            case SEPARATOR_ATTRIBUTE_NAME:
                putProperty(LOG_SEPARATOR, attributeValue);
                break;

            case DESCRIPTION_ATTRIBUTE_NAME:
                putProperty(DESCRIPTION, attributeValue);
                break;

            default:
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