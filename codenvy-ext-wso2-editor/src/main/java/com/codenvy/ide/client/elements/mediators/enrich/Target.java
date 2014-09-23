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
package com.codenvy.ide.client.elements.mediators.enrich;

import com.codenvy.ide.client.elements.AbstractEntityElement;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.util.StringUtils;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.NameSpace.PREFIX;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TargetAction.REPLACE;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TargetType.CUSTOM;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TargetType.PROPERTY;

/**
 * The class which describes state of Target element of Enrich mediator and also has methods for changing it. Also the class contains
 * the business logic that allows to display serialization representation depending of the current state of element. Deserelization
 * mechanism allows to restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class Target extends AbstractEntityElement {

    public static final String TARGET_SERIALIZATION_NAME = "target";

    public static final Key<TargetAction>    TARGET_ACTION     = new Key<>("EnrichTargetAction");
    public static final Key<TargetType>      TARGET_TYPE       = new Key<>("EnrichTargetType");
    public static final Key<String>          TARGET_XPATH      = new Key<>("EnrichTargetXpath");
    public static final Key<String>          TARGET_PROPERTY   = new Key<>("EnrichTargetProperty");
    public static final Key<List<NameSpace>> TARGET_NAMESPACES = new Key<>("EnrichTargetNamespaces");

    private static final String ACTION_ATTRIBUTE_NAME   = "action";
    private static final String TYPE_ATTRIBUTE_NAME     = "type";
    private static final String XPATH_ATTRIBUTE_NAME    = "xpath";
    private static final String PROPERTY_ATTRIBUTE_NAME = "property";

    private final Provider<NameSpace> nameSpaceProvider;

    @Inject
    public Target(Provider<NameSpace> nameSpaceProvider) {
        this.nameSpaceProvider = nameSpaceProvider;

        putProperty(TARGET_ACTION, REPLACE);
        putProperty(TARGET_TYPE, CUSTOM);
        putProperty(TARGET_XPATH, "/default/xpath");
        putProperty(TARGET_PROPERTY, "target_property");
        putProperty(TARGET_NAMESPACES, java.util.Collections.<NameSpace>emptyList());
    }

    /** Serialization representation attributes of target property of element. */
    @Nonnull
    private String serializeAttributes() {
        TargetAction targetAction = getProperty(TARGET_ACTION);
        TargetType targetType = getProperty(TARGET_TYPE);

        if (targetAction == null || targetType == null) {
            return "";
        }

        Map<String, String> prop = new LinkedHashMap<>();

        if (!REPLACE.equals(targetAction)) {
            prop.put(ACTION_ATTRIBUTE_NAME, targetAction.getValue());
        }

        switch (targetType) {
            case CUSTOM:
                prop.put(XPATH_ATTRIBUTE_NAME, getProperty(TARGET_XPATH));
                break;

            case PROPERTY:
                prop.put(PROPERTY_ATTRIBUTE_NAME, getProperty(TARGET_PROPERTY));
                prop.put(TYPE_ATTRIBUTE_NAME, targetType.getValue());
                break;

            default:
                prop.put(TYPE_ATTRIBUTE_NAME, targetType.getValue());
                break;
        }

        return convertAttributesToXMLFormat(prop);
    }

    /** @return serialized representation of the target element */
    @Nonnull
    public String serialize() {
        return '<' + TARGET_SERIALIZATION_NAME + convertNameSpaceToXMLFormat(getProperty(TARGET_NAMESPACES)) +
               ' ' + serializeAttributes() + "/>\n";
    }

    /**
     * Deserialize diagram element with all inner elements.
     *
     * @param node
     *         XML node that need to be deserialized
     */
    public void deserialize(@Nonnull Node node) {
        readXMLAttributes(node);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyAttribute(@Nonnull String attributeName, @Nonnull String attributeValue) {
        switch (attributeName) {
            case ACTION_ATTRIBUTE_NAME:
                putProperty(TARGET_ACTION, TargetAction.getItemByValue(attributeValue));
                break;

            case TYPE_ATTRIBUTE_NAME:
                putProperty(TARGET_TYPE, TargetType.getItemByValue(attributeValue));
                break;

            case XPATH_ATTRIBUTE_NAME:
                putProperty(TARGET_XPATH, attributeValue);
                break;

            case PROPERTY_ATTRIBUTE_NAME:
                putProperty(TARGET_PROPERTY, attributeValue);
                putProperty(TARGET_TYPE, PROPERTY);
                break;

            default:
                applyNameSpaces(attributeName, attributeValue);
        }
    }

    private void applyNameSpaces(@Nonnull String attributeName, @Nonnull String attributeValue) {
        List<NameSpace> nameSpaces = getProperty(TARGET_NAMESPACES);

        if (!StringUtils.startsWith(PREFIX, attributeName, true) || nameSpaces == null) {
            return;
        }

        String name = StringUtils.trimStart(attributeName, PREFIX + ':');

        NameSpace nameSpace = nameSpaceProvider.get();

        nameSpace.setPrefix(name);
        nameSpace.setUri(attributeValue);

        nameSpaces.add(nameSpace);
    }


    public enum TargetAction {
        REPLACE("replace"), CHILD("child"), SIBLING("sibling");

        public static final String TYPE_NAME = "EnrichTargetAction";

        private final String value;

        TargetAction(@Nonnull String value) {
            this.value = value;
        }

        @Nonnull
        public String getValue() {
            return value;
        }

        @Nonnull
        public static TargetAction getItemByValue(@Nonnull String value) {
            switch (value) {
                case "replace":
                    return REPLACE;

                case "child":
                    return CHILD;

                default:
                    return SIBLING;
            }
        }
    }

    public enum TargetType {
        CUSTOM("custom"), ENVELOPE("envelope"), BODY("body"), PROPERTY("property");

        public static final String TYPE_NAME = "EnrichTargetType";

        private final String value;

        TargetType(@Nonnull String value) {
            this.value = value;
        }

        @Nonnull
        public String getValue() {
            return value;
        }

        @Nonnull
        public static TargetType getItemByValue(@Nonnull String value) {
            switch (value) {
                case "custom":
                    return CUSTOM;

                case "envelope":
                    return ENVELOPE;

                case "body":
                    return BODY;

                default:
                    return PROPERTY;
            }
        }
    }
}