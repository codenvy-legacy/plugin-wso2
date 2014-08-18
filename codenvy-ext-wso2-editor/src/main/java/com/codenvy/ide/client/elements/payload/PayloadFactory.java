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
import com.codenvy.ide.client.elements.AbstractShape;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.Call;
import com.codenvy.ide.client.elements.CallTemplate;
import com.codenvy.ide.client.elements.Filter;
import com.codenvy.ide.client.elements.Header;
import com.codenvy.ide.client.elements.LoopBack;
import com.codenvy.ide.client.elements.Property;
import com.codenvy.ide.client.elements.Respond;
import com.codenvy.ide.client.elements.Send;
import com.codenvy.ide.client.elements.Sequence;
import com.codenvy.ide.client.elements.Switch;
import com.codenvy.ide.client.elements.enrich.Enrich;
import com.codenvy.ide.client.elements.log.Log;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.payload.Format.FormatType;

/**
 * Class describes PayloadFactory mediator.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class PayloadFactory extends AbstractShape {
    public static final String ELEMENT_NAME       = "PayloadFactory";
    public static final String SERIALIZATION_NAME = "payloadFactory";

    private static final String FORMAT_PROPERTY_NAME       = "format";
    private static final String ARGS_PROPERTY_NAME         = "args";
    private static final String MEDIA_TYPE_ATTRIBUTE_NAME  = "media-type";
    private static final String DESCRIPTION_ATTRIBUTE_NAME = "description";

    private static final List<String> PROPERTIES = Arrays.asList(FORMAT_PROPERTY_NAME,
                                                                 ARGS_PROPERTY_NAME);

    private String     description;
    private Format     format;
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

        description = "";
        //TODO create entity using editor factory
        format = new Format();
        args = Collections.createArray();
    }

    /** @return format element of payload mediator */
    @Nonnull
    public Format getFormat() {
        return format;
    }

    /**
     * Set format to payload mediator.
     *
     * @param format
     *         format element which need to set
     */
    public void setFormat(@Nonnull Format format) {
        this.format = format;
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
        Map<String, String> prop = new LinkedHashMap<>();

        prop.put(MEDIA_TYPE_ATTRIBUTE_NAME, format.getMediaType().name());
        prop.put(DESCRIPTION_ATTRIBUTE_NAME, description);

        return convertPropertiesToXMLFormat(prop);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        StringBuilder result = new StringBuilder();

        if (!args.isEmpty()) {
            result.append("<args>\n");

            for (Arg arg : args.asIterable()) {
                result.append(arg.serialize());
            }

            result.append("</args>");
        } else {
            result.append("<args/>");
        }

        return format.serialize() + result.toString();
    }

    /** {@inheritDoc} */
    @Override
    protected void applyAttributes(@Nonnull Node node) {
        NamedNodeMap attributes = node.getAttributes();

        for (int i = 0; i < attributes.getLength(); i++) {
            Node childNode = attributes.item(i);

            String attributeName = childNode.getNodeName();
            String attributeValue = childNode.getNodeValue();

            switch (attributeName) {
                case MEDIA_TYPE_ATTRIBUTE_NAME:
                    format.setMediaType(Format.MediaType.valueOf(attributeValue));
                    break;

                case DESCRIPTION_ATTRIBUTE_NAME:
                    description = attributeValue;
                    break;
            }
        }

    }

    /** {@inheritDoc} */
    @Override
    public void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();

        switch (nodeName) {
            case FORMAT_PROPERTY_NAME:
                format.applyAttributes(node);
                format.setFormatType(FormatType.Registry);

                if (node.hasChildNodes()) {
                    format.applyProperty(node);
                    format.setFormatType(FormatType.Inline);
                }
                break;

            case ARGS_PROPERTY_NAME:
                NodeList childNodes = node.getChildNodes();

                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node childNode = childNodes.item(i);
                    //TODO create property using editor factory
                    Arg arg = new Arg();

                    arg.applyAttributes(childNode);

                    args.add(arg);
                }
                break;
        }
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.payloadFactory();
    }

}