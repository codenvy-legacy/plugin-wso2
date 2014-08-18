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
package com.codenvy.ide.client.elements.enrich;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.AbstractShape;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.Call;
import com.codenvy.ide.client.elements.CallTemplate;
import com.codenvy.ide.client.elements.Filter;
import com.codenvy.ide.client.elements.Header;
import com.codenvy.ide.client.elements.LoopBack;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.Property;
import com.codenvy.ide.client.elements.Respond;
import com.codenvy.ide.client.elements.Send;
import com.codenvy.ide.client.elements.Sequence;
import com.codenvy.ide.client.elements.Switch;
import com.codenvy.ide.client.elements.log.Log;
import com.codenvy.ide.client.elements.payload.PayloadFactory;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class Enrich extends AbstractShape {
    public static final String ELEMENT_NAME       = "Enrich";
    public static final String SERIALIZATION_NAME = "enrich";

    private static final String TARGET_ELEMENT = "target";
    private static final String SOURCE_ELEMENT = "source";

    private static final String DESCRIPTION = "description";

    private static final List<String> PROPERTIES = Arrays.asList(TARGET_ELEMENT, SOURCE_ELEMENT);

    private String description;
    private Source source;
    private Target target;

    @Inject
    public Enrich(EditorResources resources,
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

        this.description = "";

        //TODO use edit factory to create entity
        this.source = new Source();
        this.target = new Target();
    }

    /** @return source entity of enrich element */
    @Nonnull
    public Source getSource() {
        return source;
    }

    /**
     * Sets source entity of enrich element.
     *
     * @param source
     *         entity which need to set to element
     */
    public void setSource(@Nonnull Source source) {
        this.source = source;
    }

    /** @return target entity of enrich element */
    @Nonnull
    public Target getTarget() {
        return target;
    }

    /**
     * Sets target entity of enrich element.
     *
     * @param target
     *         entity which need to set to element
     */
    public void setTarget(@Nonnull Target target) {
        this.target = target;
    }

    /** @return description value of enrich element */
    @Nullable
    public String getDescription() {
        return description;
    }

    /**
     * Sets description value to enrich element
     *
     * @param description
     *         value which need to set to element
     */
    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        StringBuilder result = new StringBuilder();

        StringBuilder targetNameSpaces = new StringBuilder();
        StringBuilder sourceNameSpaces = new StringBuilder();

        for (NameSpace nameSpace : source.getNameSpaces().asIterable()) {
            sourceNameSpaces.append(nameSpace.toString()).append(' ');
        }

        for (NameSpace nameSpace : target.getNameSpaces().asIterable()) {
            targetNameSpaces.append(nameSpace.toString()).append(' ');
        }

        result.append("<source ").append(sourceNameSpaces).append(serializeSourceAttributes()).append(">\n");

        if (source.getType().equals(SourceType.inline.name()) && source.getInlineType().equals(InlineType.SourceXML.name())) {
            String xml = source.getSourceXML();

            int index = xml.indexOf(">");

            String tag = xml.substring(0, index);

            String tagName = xml.substring(0, tag.contains("/") ? index - 1 : index);
            String restString = xml.substring(tag.contains("/") ? index - 1 : index);

            result.append(tagName).append(' ').append("xmlns=\"\"").append(restString);
        }

        result.append("</source>");

        result.append("<target ").append(targetNameSpaces).append(serializeTargetAttributes()).append("/>\n");

        return result.toString();
    }

    /** Serialization representation attributes of source property of element. */
    private String serializeSourceAttributes() {
        Map<String, String> attributes = new LinkedHashMap<>();

        setDefaultSourceAttributes(attributes);

        if (source.getClone().equalsIgnoreCase(CloneSource.FALSE.name())) {
            attributes.remove(Source.CLONE_SOURCE);
        }

        switch (SourceType.valueOf(source.getType())) {
            case custom:
                attributes.remove(Source.SOURCE_TYPE);
                attributes.remove(Source.INLINE_REGISTRY_KEY);
                attributes.remove(Source.PROPERTY);
                break;

            case property:
                attributes.remove(Source.INLINE_REGISTRY_KEY);
                attributes.remove(Source.XPATH);
                break;

            case inline:
                attributes.remove(Source.PROPERTY);
                attributes.remove(Source.XPATH);

                if (source.getInlineType().equals(InlineType.SourceXML.name())) {
                    attributes.remove(Source.INLINE_REGISTRY_KEY);
                }
                break;

            default:
                attributes.remove(Source.PROPERTY);
                attributes.remove(Source.INLINE_REGISTRY_KEY);
                attributes.remove(Source.XPATH);
                break;
        }

        return convertPropertiesToXMLFormat(attributes);
    }

    /**
     * Sets default visualization of source element attributes.
     *
     * @param attributes
     *         list of default attributes
     */
    private void setDefaultSourceAttributes(@Nonnull Map<String, String> attributes) {
        attributes.put(Source.CLONE_SOURCE, source.getClone());
        attributes.put(Source.XPATH, source.getXpath());
        attributes.put(Source.SOURCE_TYPE, source.getType());
        attributes.put(Source.PROPERTY, source.getProperty());
        attributes.put(Source.INLINE_REGISTRY_KEY, source.getInlRegisterKey());
    }

    /**
     * Sets default visualization of target element attributes.
     *
     * @param attributes
     *         list of default attributes
     */
    private void setDefaultTargetAttributes(@Nonnull Map<String, String> attributes) {
        attributes.put(Target.TYPE, target.getType());
        attributes.put(Target.ACTION, target.getAction());
        attributes.put(Target.PROPERTY, target.getProperty());
        attributes.put(Target.XPATH, target.getXpath());
    }

    /** Serialization representation attributes of target property of element. */
    private String serializeTargetAttributes() {
        Map<String, String> attributes = new LinkedHashMap<>();

        setDefaultTargetAttributes(attributes);

        if (target.getAction().equals(TargetAction.replace.name())) {
            attributes.remove(Target.ACTION);
        }

        switch (TargetType.valueOf(target.getType())) {
            case custom:
                attributes.remove(Target.TYPE);
                attributes.remove(Target.PROPERTY);
                break;

            case property:
                attributes.remove(Target.XPATH);
                break;

            default:
                attributes.remove(Target.PROPERTY);
                attributes.remove(Target.XPATH);
                break;
        }

        return convertPropertiesToXMLFormat(attributes);
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        Map<String, String> attributes = new LinkedHashMap<>();

        attributes.put(DESCRIPTION, description);

        return convertPropertiesToXMLFormat(attributes);
    }

    /** {@inheritDoc} */
    @Override
    public void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();

        switch (nodeName) {
            case TARGET_ELEMENT:
                //TODO create entity using editor factory
                Target target = new Target();
                target.applyAttributes(node);
                break;

            case SOURCE_ELEMENT:
                //TODO create entity using editor factory
                Source source = new Source();

                source.applyAttributes(node);

                if (node.hasChildNodes()) {
                    String document = node.getOwnerDocument().toString();

                    node.getFirstChild().getOwnerDocument().getDocumentElement().removeAttribute("xmlns");

                    int indexOpen = document.indexOf(">");
                    int indexClose = document.lastIndexOf("<");

                    String result = document.substring(indexOpen + 1, indexClose);

                    source.setSourceXML(result);
                }
                break;
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void applyAttributes(@Nonnull Node node) {
        NamedNodeMap attributeMap = node.getAttributes();

        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attributeNode = attributeMap.item(i);

            switch (attributeNode.getNodeName()) {
                case DESCRIPTION:
                    description = String.valueOf(node);
                    break;
            }
        }
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.enrich();
    }

    public enum SourceType {
        custom, envelope, body, property, inline;

        public static final String TYPE_NAME = "EnrichSourceType";
    }

    public enum TargetAction {
        replace, child, sibling;

        public static final String TYPE_NAME = "EnrichTargetAction";
    }

    public enum TargetType {
        custom, envelope, body, property;

        public static final String TYPE_NAME = "EnrichTargetType";
    }

    public enum InlineType {
        SourceXML, RegistryKey;

        public static final String INLINE_TYPE = "Inline type";
    }

    public enum CloneSource {
        TRUE, FALSE
    }
}