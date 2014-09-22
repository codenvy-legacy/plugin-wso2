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
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.util.StringUtils;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.codenvy.ide.client.elements.NameSpace.PREFIX;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.InlineType.REGISTRY_KEY;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SourceType.CUSTOM;

/**
 * The class which describes state of Source element of Enrich mediator and also has methods for changing it. Also the class contains
 * the business logic that allows to display serialization representation depending of the current state of element. Deserelization
 * mechanism allows to restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class Source extends AbstractEntityElement {

    public static final String SOURCE_SERIALIZATION_NAME = "source";

    public static final Key<Boolean>          SOURCE_CLONE               = new Key<>("EnrichSourceClone");
    public static final Key<SourceType>       SOURCE_TYPE                = new Key<>("EnrichSourceType");
    public static final Key<InlineType>       SOURCE_INLINE_TYPE         = new Key<>("EnrichSourceInlineType");
    public static final Key<String>           SOURCE_INLINE_REGISTER_KEY = new Key<>("EnrichSourceInlineRegistryKey");
    public static final Key<String>           SOURCE_XML                 = new Key<>("EnrichSourceXml");
    public static final Key<String>           SOURCE_XPATH               = new Key<>("EnrichSourceXPath");
    public static final Key<String>           SOURCE_PROPERTY            = new Key<>("EnrichSourceProperty");
    public static final Key<Array<NameSpace>> SOURCE_NAMESPACES          = new Key<>("EnrichSourceNamespaces");

    private static final String CLONE_SOURCE_ATTRIBUTE_NAME        = "clone";
    private static final String SOURCE_TYPE_ATTRIBUTE_NAME         = "type";
    private static final String INLINE_REGISTRY_KEY_ATTRIBUTE_NAME = "key";
    private static final String XPATH_ATTRIBUTE_NAME               = "xpath";
    private static final String PROPERTY_ATTRIBUTE_NAME            = "property";

    private final Provider<NameSpace> nameSpaceProvider;

    @Inject
    public Source(Provider<NameSpace> nameSpaceProvider) {
        this.nameSpaceProvider = nameSpaceProvider;

        putProperty(SOURCE_CLONE, false);
        putProperty(SOURCE_TYPE, CUSTOM);
        putProperty(SOURCE_INLINE_TYPE, InlineType.SOURCE_XML);
        putProperty(SOURCE_INLINE_REGISTER_KEY, "/default/sequence");
        putProperty(SOURCE_XML, "<inline/>");
        putProperty(SOURCE_XPATH, "/default/xpath");
        putProperty(SOURCE_PROPERTY, "source_property");
        putProperty(SOURCE_NAMESPACES, Collections.<NameSpace>createArray());
    }

    /** Serialization representation attributes of source property of element. */
    @Nonnull
    private String serializeAttributes() {
        Boolean cloneSource = getProperty(SOURCE_CLONE);
        SourceType type = getProperty(SOURCE_TYPE);

        if (cloneSource == null || type == null) {
            return "";
        }

        Map<String, String> prop = new LinkedHashMap<>();

        if (Boolean.TRUE.equals(cloneSource)) {
            prop.put(CLONE_SOURCE_ATTRIBUTE_NAME, String.valueOf(cloneSource));
        }

        switch (type) {
            case CUSTOM:
                prop.put(XPATH_ATTRIBUTE_NAME, getProperty(SOURCE_XPATH));
                break;

            case PROPERTY:
                prop.put(SOURCE_TYPE_ATTRIBUTE_NAME, type.getValue());
                prop.put(PROPERTY_ATTRIBUTE_NAME, getProperty(SOURCE_PROPERTY));
                break;

            case INLINE:
                prop.put(SOURCE_TYPE_ATTRIBUTE_NAME, type.getValue());
                serializeInlineTypeAttributes(prop);
                break;

            default:
                prop.put(SOURCE_TYPE_ATTRIBUTE_NAME, type.getValue());

        }

        return convertAttributesToXMLFormat(prop);
    }

    private void serializeInlineTypeAttributes(@Nonnull Map<String, String> prop) {
        InlineType inlineType = getProperty(SOURCE_INLINE_TYPE);

        if (REGISTRY_KEY.equals(inlineType)) {
            prop.put(INLINE_REGISTRY_KEY_ATTRIBUTE_NAME, getProperty(SOURCE_INLINE_REGISTER_KEY));
        }

    }

    /** @return serialized representation of the source element */
    @Nonnull
    public String serialize() {
        Array<NameSpace> nameSpaces = getProperty(SOURCE_NAMESPACES);
        SourceType type = getProperty(SOURCE_TYPE);
        InlineType inlineType = getProperty(SOURCE_INLINE_TYPE);
        String sourceXML = getProperty(SOURCE_XML);

        if (sourceXML == null) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        result.append('<').append(SOURCE_SERIALIZATION_NAME).append(' ')
              .append(convertNameSpaceToXMLFormat(nameSpaces)).append(serializeAttributes()).append(">\n");

        if (SourceType.INLINE.equals(type) && InlineType.SOURCE_XML.equals(inlineType)) {

            int index = sourceXML.indexOf(">");

            String tag = sourceXML.substring(0, index);

            String tagName = sourceXML.substring(0, tag.contains("/") ? index - 1 : index);
            String restString = sourceXML.substring(tag.contains("/") ? index - 1 : index);

            result.append(tagName).append(" " + PREFIX + "=\"\"").append(restString);
        }

        result.append("</").append(SOURCE_SERIALIZATION_NAME).append('>');

        return result.toString();
    }


    /** {@inheritDoc} */
    @Override
    protected void applyAttribute(@Nonnull String attributeName, @Nonnull String attributeValue) {
        switch (attributeName) {
            case CLONE_SOURCE_ATTRIBUTE_NAME:
                putProperty(SOURCE_CLONE, Boolean.valueOf(attributeValue));
                break;

            case SOURCE_TYPE_ATTRIBUTE_NAME:
                putProperty(SOURCE_TYPE, SourceType.getItemByValue(attributeValue));
                break;

            case INLINE_REGISTRY_KEY_ATTRIBUTE_NAME:
                applyInlineRegistryKeyAttribute(attributeValue);
                break;

            case XPATH_ATTRIBUTE_NAME:
                putProperty(SOURCE_XPATH, attributeValue);
                break;

            case PROPERTY_ATTRIBUTE_NAME:
                putProperty(SOURCE_PROPERTY, attributeValue);
                break;

            default:
                applyNameSpaces(attributeName, attributeValue);
        }
    }

    private void applyInlineRegistryKeyAttribute(@Nonnull String attributeValue) {
        putProperty(SOURCE_INLINE_REGISTER_KEY, attributeValue);
        putProperty(SOURCE_TYPE, SourceType.INLINE);
        putProperty(SOURCE_INLINE_TYPE, InlineType.REGISTRY_KEY);
    }

    private void applyNameSpaces(@Nonnull String attributeName, @Nonnull String attributeValue) {
        Array<NameSpace> nameSpaces = getProperty(SOURCE_NAMESPACES);

        if (!StringUtils.startsWith(PREFIX, attributeName, true) || nameSpaces == null) {
            return;
        }

        String name = StringUtils.trimStart(attributeName, PREFIX + ':');

        NameSpace nameSpace = nameSpaceProvider.get();

        nameSpace.setPrefix(name);
        nameSpace.setUri(attributeValue);

        nameSpaces.add(nameSpace);
    }

    /**
     * Apply properties from XML node to the diagram element
     *
     * @param node
     *         XML node that need to be analyzed
     */
    public void applyProperty(@Nonnull Node node) {
        readXMLAttributes(node);

        String sourceXML = getProperty(SOURCE_XML);

        if (sourceXML == null || !node.hasChildNodes()) {
            return;
        }

        String item = node.getChildNodes().item(0).toString();

        int indexFirst = item.indexOf(" ");
        int indexLast = item.indexOf(">");

        String tagName = item.substring(0, indexLast);

        String xmlns = tagName.substring(indexFirst, tagName.contains("/") ? indexLast - 1 : indexLast);

        sourceXML = item.replace(xmlns, "");

        putProperty(SOURCE_XML, sourceXML);
    }

    public enum SourceType {
        CUSTOM("custom"), ENVELOPE("envelope"), BODY("body"), PROPERTY("property"), INLINE("inline");

        public static final String TYPE_NAME = "EnrichSourceType";

        private final String value;

        SourceType(@Nonnull String value) {
            this.value = value;
        }

        @Nonnull
        public String getValue() {
            return value;
        }

        @Nonnull
        public static SourceType getItemByValue(@Nonnull String value) {
            switch (value) {
                case "custom":
                    return CUSTOM;

                case "envelope":
                    return ENVELOPE;

                case "body":
                    return BODY;

                case "property":
                    return PROPERTY;

                default:
                    return INLINE;
            }
        }
    }

    public enum InlineType {
        SOURCE_XML("SourceXML"), REGISTRY_KEY("RegistryKey");

        public static final String TYPE_NAME = "EnrichInlineType";

        private final String value;

        InlineType(@Nonnull String value) {
            this.value = value;
        }

        @Nonnull
        public String getValue() {
            return value;
        }

        @Nonnull
        public static InlineType getItemByValue(@Nonnull String value) {

            if ("SourceXML".equals(value)) {
                return SOURCE_XML;
            } else {
                return REGISTRY_KEY;
            }
        }
    }
}
