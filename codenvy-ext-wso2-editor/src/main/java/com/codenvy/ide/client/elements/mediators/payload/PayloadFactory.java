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
package com.codenvy.ide.client.elements.mediators.payload;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.AbstractElement;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.mediators.payload.Format.FORMAT_MEDIA_TYPE;
import static com.codenvy.ide.client.elements.mediators.payload.Format.FORMAT_TYPE;
import static com.codenvy.ide.client.elements.mediators.payload.Format.FormatType.INLINE;
import static com.codenvy.ide.client.elements.mediators.payload.Format.FormatType.REGISTRY;
import static com.codenvy.ide.client.elements.mediators.payload.Format.MediaType;

/**
 * The class which describes state of PayloadFactory mediator and also has methods for changing it. Also the class contains the business
 * logic that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving. For more information about PayloadFactory mediator go to
 * <a href=" https://docs.wso2.com/display/ESB460/PayLoad+Mediator"> https://docs.wso2.com/display/ESB460/PayLoad+Mediator</a>
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class PayloadFactory extends AbstractElement {
    public static final String ELEMENT_NAME       = "PayloadFactory";
    public static final String SERIALIZATION_NAME = "payloadFactory";

    public static final Key<Format>     FORMAT      = new Key<>("PayloadFormat");
    public static final Key<String>     DESCRIPTION = new Key<>("PayloadDescription");
    public static final Key<Array<Arg>> ARGS        = new Key<>("PayloadArgs");

    private static final String FORMAT_PROPERTY_NAME       = "format";
    private static final String ARGS_PROPERTY_NAME         = "args";
    private static final String MEDIA_TYPE_ATTRIBUTE_NAME  = "media-type";
    private static final String DESCRIPTION_ATTRIBUTE_NAME = "description";

    private static final List<String> PROPERTIES = Arrays.asList(FORMAT_PROPERTY_NAME,
                                                                 ARGS_PROPERTY_NAME);

    private final Provider<Arg> argProvider;

    @Inject
    public PayloadFactory(EditorResources resources,
                          Provider<Branch> branchProvider,
                          ElementCreatorsManager elementCreatorsManager,
                          Provider<Arg> argProvider,
                          Format format) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.payloadFactory(),
              branchProvider,
              elementCreatorsManager);

        this.argProvider = argProvider;

        putProperty(FORMAT, format);
        putProperty(DESCRIPTION, "");
        putProperty(ARGS, Collections.<Arg>createArray());
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        Format format = getProperty(FORMAT);
        if (format == null) {
            return "";
        }

        MediaType mediaType = format.getProperty(FORMAT_MEDIA_TYPE);

        if (mediaType == null) {
            return "";
        }

        Map<String, String> prop = new LinkedHashMap<>();

        prop.put(MEDIA_TYPE_ATTRIBUTE_NAME, mediaType.getValue());
        prop.put(DESCRIPTION_ATTRIBUTE_NAME, getProperty(DESCRIPTION));

        return convertAttributesToXMLFormat(prop);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Array<Arg> args = getProperty(ARGS);
        Format format = getProperty(FORMAT);

        if (args == null || format == null) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        if (!args.isEmpty()) {
            result.append('<').append(ARGS_PROPERTY_NAME).append('>');

            for (Arg arg : args.asIterable()) {
                result.append(arg.serialize());
            }

            result.append("</").append(ARGS_PROPERTY_NAME).append('>');
        } else {
            result.append('<').append(ARGS_PROPERTY_NAME).append("/>");
        }

        return format.serialize() + result.toString();
    }

    /** {@inheritDoc} */
    @Override
    protected void applyAttribute(@Nonnull String attributeName, @Nonnull String attributeValue) {
        Format format = getProperty(FORMAT);

        if (format == null) {
            return;
        }

        if (MEDIA_TYPE_ATTRIBUTE_NAME.equals(attributeName)) {
            format.putProperty(FORMAT_MEDIA_TYPE, MediaType.getItemByValue(attributeValue));
        } else {
            putProperty(DESCRIPTION, attributeValue);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();

        if (FORMAT_PROPERTY_NAME.equals(nodeName)) {
            applyFormatProperty(node);
        } else {
            applyArgsProperty(node);
        }
    }

    private void applyFormatProperty(@Nonnull Node node) {
        Format format = getProperty(FORMAT);

        if (format == null) {
            return;
        }

        format.applyAttributes(node);
        format.putProperty(FORMAT_TYPE, REGISTRY);

        if (node.hasChildNodes()) {
            format.applyProperty(node);
            format.putProperty(FORMAT_TYPE, INLINE);
        }
    }

    private void applyArgsProperty(@Nonnull Node node) {
        Array<Arg> args = getProperty(ARGS);

        if (!node.hasChildNodes() || args == null) {
            return;
        }

        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);

            Arg arg = argProvider.get();
            arg.applyAttributes(childNode);

            args.add(arg);
        }
    }

}