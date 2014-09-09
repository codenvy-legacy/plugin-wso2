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
package com.codenvy.ide.client.elements.mediators;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.AbstractElement;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.Element;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint;
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.util.StringUtils;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.elements.NameSpace.PREFIX;
import static com.codenvy.ide.client.elements.mediators.Call.EndpointType.INLINE;
import static com.codenvy.ide.client.elements.mediators.Call.EndpointType.NONE;
import static com.codenvy.ide.client.elements.mediators.Call.EndpointType.REGISTRYKEY;
import static com.codenvy.ide.client.elements.mediators.Call.EndpointType.XPATH;

/**
 * The class which describes state of Call mediator and also has methods for changing it. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving. For more information about Call mediator go to
 * <a href=" https://docs.wso2.com/display/ESB460/Call+Mediator"> https://docs.wso2.com/display/ESB460/Call+Mediator</a>
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class Call extends AbstractElement {
    public static final String ELEMENT_NAME       = "Call";
    public static final String SERIALIZATION_NAME = "call";

    private static final String KEY_ATTRIBUTE_NAME            = "key";
    private static final String KEY_EXPRESSION_ATTRIBUTE_NAME = "key-expression";
    private static final String DESCRIPTION_ATTRIBUTE_NAME    = "description";
    private static final String ENDPOINT_PROPERTY_NAME        = "endpoint";

    private static final List<String> PROPERTIES = Arrays.asList(ENDPOINT_PROPERTY_NAME);
    private static final List<String> COMPONENTS = Arrays.asList(AddressEndpoint.ELEMENT_NAME);

    private final Provider<NameSpace> nameSpaceProvider;

    private EndpointType     endpointType;
    private String           registryKey;
    private String           xPath;
    private String           description;
    private Array<NameSpace> nameSpaces;

    private boolean isKeyAttributeFound;
    private boolean isKeyExpressionAttributeFound;

    @Inject
    public Call(EditorResources resources,
                Provider<Branch> branchProvider,
                MediatorCreatorsManager mediatorCreatorsManager,
                Provider<NameSpace> nameSpaceProvider) {

        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        this.nameSpaceProvider = nameSpaceProvider;

        endpointType = INLINE;
        registryKey = "/default/key";
        xPath = "/default/expression";
        description = "";
        nameSpaces = Collections.createArray();

        components.addAll(COMPONENTS);

        Branch branch = branchProvider.get();
        branch.setParent(this);

        branches.add(branch);
    }

    /** @return endpoint type of call mediator */
    @Nonnull
    public EndpointType getEndpointType() {
        return endpointType;
    }

    /**
     * Sets endpoint type parameter to element
     *
     * @param endpointType
     *         value which need to set to element
     */
    public void setEndpointType(@Nonnull EndpointType endpointType) {
        this.endpointType = endpointType;
    }

    /** @return registry key of call mediator */
    @Nonnull
    public String getRegistryKey() {
        return registryKey;
    }

    /**
     * Sets registry key parameter to element
     *
     * @param registryKey
     *         value which need to set to element
     */
    public void setRegistryKey(@Nonnull String registryKey) {
        this.registryKey = registryKey;
    }

    /** @return xpath of call mediator */
    @Nullable
    public String getXpath() {
        return xPath;
    }

    /**
     * Sets xpath parameter to element
     *
     * @param xPath
     *         value which need to set to element
     */
    public void setXpath(@Nullable String xPath) {
        this.xPath = xPath;
    }

    /** @return description of call mediator */
    @Nonnull
    public String getDescription() {
        return description;
    }

    /**
     * Sets description to element
     *
     * @param description
     *         value which need to set to element
     */
    public void setDescription(@Nonnull String description) {
        this.description = description;
    }

    /** @return list of namespaces which contain in call */
    @Nonnull
    public Array<NameSpace> getNameSpaces() {
        return nameSpaces;
    }

    /**
     * Changes list of name spaces.
     *
     * @param nameSpaces
     *         list of name spaces which needs to set in element
     */
    public void setNameSpaces(@Nonnull Array<NameSpace> nameSpaces) {
        this.nameSpaces = nameSpaces;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String serialize() {
        String content = "<call" + (description.isEmpty() ? "" : ' ' + DESCRIPTION_ATTRIBUTE_NAME + "=\"" + description + '"');

        switch (endpointType) {
            case INLINE:
                return content + ">\n" +
                       '<' + ENDPOINT_PROPERTY_NAME + ">\n" +
                       branches.get(0).serialize() +
                       "</" + ENDPOINT_PROPERTY_NAME + ">\n" +
                       "</call>";

            case REGISTRYKEY:
                return content + ">\n" +
                       '<' + ENDPOINT_PROPERTY_NAME + ' ' + KEY_ATTRIBUTE_NAME + "=\"" + registryKey + "\"/>\n" +
                       "</call>";

            case XPATH:
                StringBuilder nameSpaces = new StringBuilder();

                for (NameSpace nameSpace : this.nameSpaces.asIterable()) {
                    nameSpaces.append(nameSpace.toString()).append(' ');
                }

                return content + ">\n" +
                       '<' + ENDPOINT_PROPERTY_NAME + ' ' + nameSpaces + KEY_EXPRESSION_ATTRIBUTE_NAME + "=\"" + xPath + "\"/>\n" +
                       "</call>";

            case NONE:
            default:
                return content + "/>";
        }
    }

    /** {@inheritDoc} */
    @Override
    public void deserialize(@Nonnull Node node) {
        super.deserialize(node);

        if (!node.hasChildNodes()) {
            endpointType = NONE;
        }

        if (branches.isEmpty()) {
            Branch branch = branchProvider.get();
            branch.setParent(this);

            branches.add(branch);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();

        switch (nodeName) {
            case ENDPOINT_PROPERTY_NAME:
                isKeyAttributeFound = false;
                isKeyExpressionAttributeFound = false;

                applyAttributes(node);

                if (isKeyAttributeFound) {
                    endpointType = REGISTRYKEY;
                } else if (isKeyExpressionAttributeFound) {
                    endpointType = XPATH;
                } else {
                    endpointType = INLINE;
                }

                Branch branch = branchProvider.get();
                branch.setParent(this);

                if (node.hasChildNodes()) {
                    Node item = node.getChildNodes().item(0);

                    Element element = createElement(item.getNodeName());

                    if (element != null) {
                        element.deserialize(node);
                        branch.addElement(element);
                    }
                }

                branches.add(branch);
                break;

            default:
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void applyAttributes(@Nonnull Node node) {
        NamedNodeMap attributeMap = node.getAttributes();

        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attributeNode = attributeMap.item(i);

            String nodeValue = attributeNode.getNodeValue();
            String nodeName = attributeNode.getNodeName();

            switch (nodeName) {
                case DESCRIPTION_ATTRIBUTE_NAME:
                    description = nodeValue;
                    break;

                case KEY_ATTRIBUTE_NAME:
                    registryKey = nodeValue;
                    isKeyAttributeFound = true;
                    break;

                case KEY_EXPRESSION_ATTRIBUTE_NAME:
                    xPath = nodeValue;
                    isKeyExpressionAttributeFound = true;
                    break;

                default:
                    if (StringUtils.startsWith(PREFIX, nodeName, true)) {
                        String name = StringUtils.trimStart(nodeName, PREFIX + ':');

                        NameSpace nameSpace = nameSpaceProvider.get();

                        nameSpace.setPrefix(name);
                        nameSpace.setUri(nodeValue);

                        nameSpaces.add(nameSpace);
                    }
            }
        }
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.call();
    }

    public enum EndpointType {
        INLINE, NONE, REGISTRYKEY, XPATH;

        public static final String TYPE_NAME = "EndpointType";
    }

}