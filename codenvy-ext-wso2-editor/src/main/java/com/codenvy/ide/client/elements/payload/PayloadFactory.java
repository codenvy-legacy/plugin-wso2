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

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.Call;
import com.codenvy.ide.client.elements.CallTemplate;
import com.codenvy.ide.client.elements.Filter;
import com.codenvy.ide.client.elements.Header;
import com.codenvy.ide.client.elements.LoopBack;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.Property;
import com.codenvy.ide.client.elements.Respond;
import com.codenvy.ide.client.elements.RootElement;
import com.codenvy.ide.client.elements.Send;
import com.codenvy.ide.client.elements.Sequence;
import com.codenvy.ide.client.elements.Switch;
import com.codenvy.ide.client.elements.enrich.Enrich;
import com.codenvy.ide.client.elements.log.Log;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static com.codenvy.ide.client.elements.payload.PayloadFactory.FormatType.Inline;
import static com.codenvy.ide.client.elements.payload.PayloadFactory.MediaType.json;
import static com.codenvy.ide.client.elements.payload.PayloadFactory.MediaType.xml;

/**
 * Class describes PayloadFactory mediator.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class PayloadFactory extends RootElement {
    public static final String ELEMENT_NAME       = "PayloadFactory";
    public static final String SERIALIZATION_NAME = "payloadFactory";

    private static final String PAYLOAD_FORMAT_PROPERTY_NAME = "PayloadFormat";
    private static final String FORMAT_PROPERTY_NAME         = "format";
    private static final String ARGS_PROPERTY_NAME           = "args";
    private static final String MEDIA_TYPE_PROPERTY_NAME     = "media-type";
    private static final String DESCRIPTION_PROPERTY_NAME    = "description";

    private static final List<String> PROPERTIES = Arrays.asList(PAYLOAD_FORMAT_PROPERTY_NAME,
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

    @Inject
    public PayloadFactory(EditorResources resources,
                          Provider<Branch> branchProvider,
                          Provider<Log> logProvider,
                          Provider<Enrich> enrichProvider,
                          Provider<Filter> filterProvider,
                          Provider<Header> headerProvider,
                          Provider<Call> callProvider,
                          Provider<CallTemplate> callTemplateProvider,
                          Provider<LoopBack> loopBackProvider,
                          Provider<PayloadFactory> payloadFactoryProvider,
                          Provider<Property> propertyProvider,
                          Provider<Respond> respondProvider,
                          Provider<Send> sendProvider,
                          Provider<Sequence> sequenceProvider,
                          Provider<Switch> switchProvider) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              resources,
              branchProvider,
              false,
              true,
              logProvider,
              enrichProvider,
              filterProvider,
              headerProvider,
              callProvider,
              callTemplateProvider,
              loopBackProvider,
              payloadFactoryProvider,
              propertyProvider,
              respondProvider,
              sendProvider,
              sequenceProvider,
              switchProvider);

        payloadFormat = Inline.name();
        format = "<inline/>";
        mediaType = xml.name();
        formatKey = "/default/key";
        args = Collections.createArray();
    }

    /** @return value of payload format */
    @Nullable
    public String getPayloadFormat() {
        return payloadFormat;
    }

    /**
     * Set payload format.
     *
     * @param payloadFormat
     *         value of payload format
     */
    public void setPayloadFormat(@Nullable String payloadFormat) {
        this.payloadFormat = payloadFormat;
    }

    /** @return value of format */
    @Nonnull
    public String getFormat() {
        return format;
    }

    /**
     * Set format.
     *
     * @param format
     *         value of format
     */
    public void setFormat(@Nullable String format) {
        this.format = format;
    }

    /** @return value of payload format key */
    @Nullable
    public String getFormatKey() {
        return formatKey;
    }

    /**
     * Set format key.
     *
     * @param formatKey
     *         value of format key
     */
    public void setFormatKey(@Nullable String formatKey) {
        this.formatKey = formatKey;
    }

    /** @return value of args */
    @Nonnull
    public Array<Arg> getArgs() {
        return args;
    }

    /**
     * Set args.
     *
     * @param args
     *         property arguments
     */
    public void setArgs(@Nullable Array<Arg> args) {
        this.args = args;
    }

    /** @return value of media type */
    @Nullable
    public String getMediaType() {
        return mediaType;
    }

    /**
     * Set media type.
     *
     * @param mediaType
     *         value of media type property
     */
    public void setMediaType(@Nullable String mediaType) {
        this.mediaType = mediaType;
    }

    /** @return value of description */
    @Nullable
    public String getDescription() {
        return description;
    }

    /**
     * Set description.
     *
     * @param description
     *         description of PayloadFactory
     */
    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        LinkedHashMap<String, String> prop = new LinkedHashMap<>();

        prop.put(MEDIA_TYPE_PROPERTY_NAME, mediaType);
        prop.put(DESCRIPTION_PROPERTY_NAME, description);

        return convertPropertiesToXMLFormat(prop);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
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

                result.append("<arg ").append(nameSpaces).append("evaluator=\"").append(arg.getEvaluator()).append("\" expression=\"")
                      .append(arg.getValue()).append("\"/>");

            }
            result.append("</args>");
        }

        return result.toString();
    }

    /** {@inheritDoc} */
    @Override
    public void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();

        Node item = node.getChildNodes().item(0);
        String nodeValue = "";
        if (item != null) {
            nodeValue = item.getNodeValue();
        }

        switch (nodeName) {
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

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.payloadFactory();
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