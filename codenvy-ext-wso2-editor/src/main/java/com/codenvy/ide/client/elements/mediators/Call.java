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
package com.codenvy.ide.client.elements.mediators;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.AbstractElement;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.Element;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.util.StringUtils;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
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

    public static final Key<String>           REGISTRY_KEY  = new Key<>("RegistryKey");
    public static final Key<EndpointType>     ENDPOINT_TYPE = new Key<>("EndpointType");
    public static final Key<String>           X_PATH        = new Key<>("XPath");
    public static final Key<String>           DESCRIPTION   = new Key<>("Description");
    public static final Key<Array<NameSpace>> NAMESPACES    = new Key<>("NameSpaces");

    private static final String KEY_ATTRIBUTE_NAME            = "key";
    private static final String KEY_EXPRESSION_ATTRIBUTE_NAME = "key-expression";
    private static final String DESCRIPTION_ATTRIBUTE_NAME    = "description";
    private static final String ENDPOINT_PROPERTY_NAME        = "endpoint";

    private static final List<String> PROPERTIES = Arrays.asList(ENDPOINT_PROPERTY_NAME);

    private final Provider<NameSpace> nameSpaceProvider;

    private boolean isKeyAttributeFound;
    private boolean isKeyExpressionAttributeFound;

    @Inject
    public Call(EditorResources resources,
                Provider<Branch> branchProvider,
                ElementCreatorsManager elementCreatorsManager,
                Provider<NameSpace> nameSpaceProvider) {

        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.call(),
              branchProvider,
              elementCreatorsManager);

        this.nameSpaceProvider = nameSpaceProvider;

        putProperty(REGISTRY_KEY, "/default/key");
        putProperty(ENDPOINT_TYPE, INLINE);
        putProperty(X_PATH, "/default/expression");
        putProperty(DESCRIPTION, "");
        putProperty(NAMESPACES, Collections.<NameSpace>createArray());

        Branch branch = branchProvider.get();
        branch.setParent(this);

        branches.add(branch);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String serialize() {
        String description = getProperty(DESCRIPTION);

        String content = '<' + SERIALIZATION_NAME + ((description != null && description.isEmpty())
                                                     ? ""
                                                     : ' ' + DESCRIPTION_ATTRIBUTE_NAME + "=\"" + description + '"');

        EndpointType endpointType = getProperty(ENDPOINT_TYPE);

        if (endpointType == null) {
            return "";
        }

        switch (endpointType) {
            case INLINE:
                return content + ">\n" +
                       '<' + ENDPOINT_PROPERTY_NAME + ">\n" +
                       branches.get(0).serialize() +
                       "</" + ENDPOINT_PROPERTY_NAME + ">\n" +
                       "</" + SERIALIZATION_NAME + '>';

            case REGISTRYKEY:
                return content + ">\n" +
                       '<' + ENDPOINT_PROPERTY_NAME + ' ' + KEY_ATTRIBUTE_NAME + "=\"" + getProperty(REGISTRY_KEY) + "\"/>\n" +
                       "</call>";

            case XPATH:
                Array<NameSpace> nameSpaces = getProperty(NAMESPACES);
                String serializedNameSpaces = convertNameSpaceToXMLFormat(nameSpaces);

                return content + ">\n" +
                       '<' + ENDPOINT_PROPERTY_NAME + ' ' + serializedNameSpaces +
                       KEY_EXPRESSION_ATTRIBUTE_NAME + "=\"" + getProperty(X_PATH) + "\"/>\n" + "</" + SERIALIZATION_NAME + '>';

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
            putProperty(ENDPOINT_TYPE, NONE);
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

                readXMLAttributes(node);

                if (isKeyAttributeFound) {
                    putProperty(ENDPOINT_TYPE, REGISTRYKEY);
                } else if (isKeyExpressionAttributeFound) {
                    putProperty(ENDPOINT_TYPE, XPATH);
                } else {
                    putProperty(ENDPOINT_TYPE, INLINE);
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
    protected void applyAttribute(@Nonnull String attributeName, @Nonnull String attributeValue) {
        switch (attributeName) {
            case DESCRIPTION_ATTRIBUTE_NAME:
                putProperty(DESCRIPTION, attributeValue);
                break;

            case KEY_ATTRIBUTE_NAME:
                putProperty(REGISTRY_KEY, attributeValue);
                isKeyAttributeFound = true;
                break;

            case KEY_EXPRESSION_ATTRIBUTE_NAME:
                putProperty(X_PATH, attributeValue);
                isKeyExpressionAttributeFound = true;
                break;

            default:
                applyNameSpaces(attributeName, attributeValue);
        }
    }

    private void applyNameSpaces(@Nonnull String attributeName, @Nonnull String attributeValue) {
        if (!StringUtils.startsWith(PREFIX, attributeName, true)) {
            return;
        }

        String name = StringUtils.trimStart(attributeName, PREFIX + ':');

        NameSpace nameSpace = nameSpaceProvider.get();

        nameSpace.setPrefix(name);
        nameSpace.setUri(attributeValue);

        Array<NameSpace> nameSpaces = getProperty(NAMESPACES);
        if (nameSpaces != null) {
            nameSpaces.add(nameSpace);
        }
    }

    public enum EndpointType {
        INLINE, NONE, REGISTRYKEY, XPATH;

        public static final String TYPE_NAME = "EndpointType";
    }

}