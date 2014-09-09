/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package com.codenvy.ide.client.elements.mediators.payload;

import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.codenvy.ide.client.elements.NameSpace.PREFIX;

/**
 * The class which describes state of Format property of PayloadFactory mediator and also has methods for changing it. Also the class
 * contains the business  logic that allows to display serialization representation depending of the current state of element.
 * Deserelization mechanism allows to restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Andrey Plotnikov
 */
public class Format {

    public static final String FORMAT_KEY_ATTRIBUTE_NAME = "key";

    private FormatType formatType;
    private MediaType  mediaType;
    private String     formatKey;
    private String     formatInline;

    @Inject
    public Format() {
        this.mediaType = MediaType.xml;
        this.formatType = FormatType.Inline;
        this.formatKey = "default/key";
        this.formatInline = "<inline/>";
    }

    /**
     * Apply attributes from XML node to the diagram element
     *
     * @param node
     *         XML node that need to be analyzed
     */
    public void applyAttributes(@Nonnull Node node) {
        if (node.hasAttributes()) {
            Node attribute = node.getAttributes().item(0);

            formatKey = String.valueOf(attribute.getNodeValue());
        }
    }

    /**
     * Apply properties from XML node to the diagram element
     *
     * @param node
     *         XML node that need to be analyzed
     */
    public void applyProperty(@Nonnull Node node) {
        if (node.hasChildNodes()) {
            String item = node.getChildNodes().item(0).toString();

            int indexFirst = item.indexOf(" ");
            int indexLast = item.indexOf(">");

            String tagName = item.substring(0, indexLast);

            String xmlns = tagName.substring(indexFirst, tagName.contains("/") ? indexLast - 1 : indexLast);

            formatInline = item.replace(xmlns, "");
        }
    }

    /** @return serialization representation of element attributes */
    @Nonnull
    private String serializeAttributes() {
        return FORMAT_KEY_ATTRIBUTE_NAME + "=\"" + formatKey + '"';
    }

    /** @return serialized representation of the source element */
    @Nonnull
    public String serialize() {
        StringBuilder result = new StringBuilder("<format");

        if (mediaType.equals(MediaType.json)) {
            return "<format>" + formatInline.replace("<", "&lt;").replace(">", "&gt;") + "</format>";
        }

        if (FormatType.Inline.equals(formatType)) {
            result.append(">");

            int index = formatInline.indexOf(">");

            String tag = formatInline.substring(0, index);

            String tagName = formatInline.substring(0, tag.contains("/") ? index - 1 : index);
            String restString = formatInline.substring(tag.contains("/") ? index - 1 : index);

            result.append(tagName).append(" " + PREFIX + "=\"\"").append(restString).append("</format>");
        } else {
            result.append(' ').append(serializeAttributes()).append("/>\n");
        }

        return result.toString();
    }

    /** @return format key value of element */
    @Nullable
    public String getFormatKey() {
        return formatKey;
    }

    /**
     * Sets format key value for element.
     *
     * @param formatKey
     *         value which need to set to element
     */
    public void setFormatKey(@Nullable String formatKey) {
        this.formatKey = formatKey;
    }

    /** @return format inline value of element */
    @Nonnull
    public String getFormatInline() {
        return formatInline;
    }

    /**
     * Sets format inline value for element.
     *
     * @param formatInline
     *         value which need to set to element
     */
    public void setFormatInline(@Nullable String formatInline) {
        this.formatInline = formatInline;
    }

    /** @return format type value of element */
    @Nonnull
    public FormatType getFormatType() {
        return formatType;
    }

    /**
     * Sets format type value for element.
     *
     * @param formatType
     *         value which need to set to element
     */
    public void setFormatType(@Nonnull FormatType formatType) {
        this.formatType = formatType;
    }

    /** @return media type value of element */
    @Nonnull
    public MediaType getMediaType() {
        return mediaType;
    }

    /**
     * Sets media type value for element.
     *
     * @param mediaType
     *         value which need to set to element
     */
    public void setMediaType(@Nonnull MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public enum FormatType {
        Inline, Registry;

        public static final String TYPE_NAME = "FormatType";
    }

    public enum MediaType {
        xml, json;

        public static final String TYPE_NAME = "MediaType";
    }
}
