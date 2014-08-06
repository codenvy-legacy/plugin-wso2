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
package com.codenvy.ide.client.elements.payload;

import com.codenvy.ide.client.elements.AbstractElement;
import com.codenvy.ide.client.elements.AbstractShape;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.RootElement;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static com.codenvy.ide.client.elements.payload.PayloadFactory.FormatType.Inline;
import static com.codenvy.ide.client.elements.payload.PayloadFactory.MediaType.json;
import static com.codenvy.ide.client.elements.payload.PayloadFactory.MediaType.xml;

/**
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class PayloadFactory extends RootElement {
    public static final String ELEMENT_NAME       = "PayloadFactory";
    public static final String SERIALIZATION_NAME = "payloadFactory";

    private static final String PAYLOAD_FORMAT_PROPERTY_NAME = "PayloadFormat";
    private static final String FORMAT_PROPERTY_NAME         = "Format";
    private static final String ARGS_PROPERTY_NAME           = "args";
    private static final String MEDIA_TYPE_PROPERTY_NAME     = "MediaType";
    private static final String DESCRIPTION_PROPERTY_NAME    = "Description";

    private static final List<String> PROPERTIES          = Arrays.asList(PAYLOAD_FORMAT_PROPERTY_NAME,
                                                                          FORMAT_PROPERTY_NAME,
                                                                          ARGS_PROPERTY_NAME,
                                                                          MEDIA_TYPE_PROPERTY_NAME,
                                                                          DESCRIPTION_PROPERTY_NAME);
    private static final List<String> INTERNAL_PROPERTIES = Arrays.asList(X_PROPERTY_NAME,
                                                                          Y_PROPERTY_NAME,
                                                                          UUID_PROPERTY_NAME,
                                                                          PAYLOAD_FORMAT_PROPERTY_NAME,
                                                                          FORMAT_PROPERTY_NAME,
                                                                          ARGS_PROPERTY_NAME,
                                                                          MEDIA_TYPE_PROPERTY_NAME,
                                                                          DESCRIPTION_PROPERTY_NAME);

    private String     payloadFormat;
    private String     format;
    private String     formatKey;
    private String     mediaType;
    private String     description;
    private Array<Arg> args;

    public PayloadFactory() {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, INTERNAL_PROPERTIES);

        payloadFormat = Inline.name();
        format = "<inline/>";
        mediaType = xml.name();
        formatKey = "/default/key";
        args = Collections.createArray();
    }

    @Nullable
    public String getPayloadFormat() {
        return payloadFormat;
    }

    public void setPayloadFormat(@Nullable String payloadFormat) {
        this.payloadFormat = payloadFormat;
    }

    @Nullable
    public String getFormat() {
        return format;
    }

    public void setFormat(@Nullable String format) {
        this.format = format;
    }

    @Nullable
    public String getFormatKey() {
        return formatKey;
    }

    public void setFormatKey(@Nullable String formatKey) {
        this.formatKey = formatKey;
    }

    @Nullable
    public Array<Arg> getArgs() {
        return args;
    }

    public void setArgs(@Nullable Array<Arg> args) {
        this.args = args;
    }

    @Nullable
    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(@Nullable String mediaType) {
        this.mediaType = mediaType;
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
    protected String serializeAttributes() {
        LinkedHashMap<String, String> prop = new LinkedHashMap<>();

        prop.put("media-type", mediaType);
        prop.put("description", description);

        return prepareSerialization(prop);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperty() {
        StringBuilder result = new StringBuilder();
        result.append("<format");
        if (payloadFormat.equals(FormatType.Inline.name())) {
            result.append(">");
            if (json.name().equals(mediaType)) {
                String jsonFormat;
                jsonFormat = format.replace("<", "&lt;");
                jsonFormat = jsonFormat.replace(">", "&gt;");
                result.append(jsonFormat);
            } else {
                Document document = XMLParser.parse(format);
                document.getDocumentElement().setAttribute("xmlns", "");
                result.append(document);
            }
            result.append("</format>");
        } else {
            result.append(" key=\"").append(formatKey).append("\"/>");
        }

        if (args.isEmpty()) {
            result.append("<args/>");
        } else {
            result.append("<args>");

            for (Arg arg : args.asIterable()) {
                StringBuilder nameSpaces = new StringBuilder();

                for (NameSpace nameSpace : arg.getNameSpaces().asIterable()) {
                    nameSpaces.append(nameSpace.toString()).append(' ');
                }

                result.append("    <arg ").append(nameSpaces).append("evaluator=\"").append(arg.getEvaluator()).append("\" expression=\"")
                      .append(arg.getValue()).append("\"/>\n");

            }
            result.append("</args>");
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
            case PAYLOAD_FORMAT_PROPERTY_NAME:
                payloadFormat = String.valueOf(nodeValue);
                break;
            case FORMAT_PROPERTY_NAME:
                format = String.valueOf(nodeValue);
                break;
            case ARGS_PROPERTY_NAME:
                //TODO create property using editor factory
                Arg arg = new Arg(null, null);
                arg.applyAttributes(node);

                args.add(arg);
                break;
            case MEDIA_TYPE_PROPERTY_NAME:
                mediaType = String.valueOf(nodeValue);
                break;
            case DESCRIPTION_PROPERTY_NAME:
                description = String.valueOf(nodeValue);
                break;
        }
    }

    public enum MediaType {
        xml, json;

        public static final String TYPE_NAME = "MediaType";
    }

    public enum FormatType {
        Inline, Registry;

        public static final String TYPE_NAME = "PayloadFormatType";
    }

}