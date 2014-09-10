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
import static com.codenvy.ide.client.elements.mediators.Send.SequenceType.Default;

/**
 * The class which describes state of Send mediator and also has methods for changing it. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving. For more information about Send mediator go to
 * <a href="https://docs.wso2.com/display/ESB460/Send+Mediator">https://docs.wso2.com/display/ESB460/Send+Mediator</a>
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class Send extends AbstractElement {
    public static final String ELEMENT_NAME       = "Send";
    public static final String SERIALIZATION_NAME = "send";

    private static final String BUILD_MESSAGE_ATTRIBUTE_NAME = "buildmessage";
    private static final String DESCRIPTION_ATTRIBUTE_NAME   = "description";
    private static final String EXPRESSION_ATTRIBUTE_NAME    = "receive";
    private static final String ENDPOINT_PROPERTY_NAME       = "endpoint";

    private static final List<String> PROPERTIES = Arrays.asList(ENDPOINT_PROPERTY_NAME);

    private final Provider<NameSpace> nameSpaceProvider;

    private SequenceType     sequencerType;
    private boolean          skipSerialization;
    private boolean          buildMessage;
    private String           description;
    private String           dynamicExpression;
    private String           staticExpression;
    private Array<NameSpace> nameSpaces;

    @Inject
    public Send(EditorResources resources,
                Provider<Branch> branchProvider,
                MediatorCreatorsManager mediatorCreatorsManager,
                Provider<NameSpace> nameSpaceProvider) {

        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        this.nameSpaceProvider = nameSpaceProvider;

        skipSerialization = false;
        sequencerType = Default;
        buildMessage = false;
        description = "";
        dynamicExpression = "/default/xpath";
        staticExpression = "/default/sequence";
        nameSpaces = Collections.createArray();

        Branch branch = branchProvider.get();
        branch.setParent(this);

        branches.add(branch);
    }

    public void setSkipSerialization(boolean skipSerialization) {
        this.skipSerialization = skipSerialization;
    }

    /** @return value of sequence type of element */
    @Nonnull
    public SequenceType getSequencerType() {
        return sequencerType;
    }

    /**
     * Sets receiving sequence type to element
     *
     * @param receivingSequencerType
     *         value which need to set to element
     */
    public void setSequencerType(@Nullable SequenceType receivingSequencerType) {
        this.sequencerType = receivingSequencerType;
    }

    /** @return value of build message before sending of element */
    public boolean getBuildMessage() {
        return buildMessage;
    }

    /**
     * Sets build message before sending parameter to element
     *
     * @param buildMessage
     *         value which need to set to element
     */
    public void setBuildMessage(boolean buildMessage) {
        this.buildMessage = buildMessage;
    }

    /** @return list of name spaces of element */
    @Nonnull
    public Array<NameSpace> getNameSpaces() {
        return nameSpaces;
    }

    /**
     * Sets list of name spaces to element
     *
     * @param nameSpaces
     *         list of name spaces which need to set to element
     */
    public void setNameSpaces(@Nonnull Array<NameSpace> nameSpaces) {
        this.nameSpaces = nameSpaces;
    }

    /** @return dynamic expression type of element */
    @Nonnull
    public String getDynamicExpression() {
        return dynamicExpression;
    }

    /**
     * Sets dynamic expression to element
     *
     * @param dynamicExpression
     *         value which need to set to element
     */
    public void setDynamicExpression(@Nullable String dynamicExpression) {
        this.dynamicExpression = dynamicExpression;
    }

    /** @return static expression type of element */
    @Nonnull
    public String getStaticExpression() {
        return staticExpression;
    }

    /**
     * Sets static expression to element
     *
     * @param staticExpression
     *         value which need to set to element
     */
    public void setStaticExpression(@Nullable String staticExpression) {
        this.staticExpression = staticExpression;
    }

    /** @return value description of element */
    @Nullable
    public String getDescription() {
        return description;
    }

    /**
     * Sets description value to element
     *
     * @param description
     *         value which need to set to element
     */
    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        StringBuilder result = new StringBuilder();

        StringBuilder spaces = new StringBuilder();

        for (NameSpace nameSpace : nameSpaces.asIterable()) {
            spaces.append(nameSpace.toString());
        }

        switch (sequencerType) {
            case Static:
                result.append(EXPRESSION_ATTRIBUTE_NAME).append("=\"").append(staticExpression).append("\" ");
                break;

            case Dynamic:
                result.append(spaces).append(' ')
                      .append(EXPRESSION_ATTRIBUTE_NAME).append("=\"{").append(dynamicExpression).append("}\" ");
                break;
        }

        result.append(buildMessage
                      ? BUILD_MESSAGE_ATTRIBUTE_NAME + "=\"true\" "
                      : ' ');

        result.append(description.isEmpty() ? ' ' : DESCRIPTION_ATTRIBUTE_NAME + "=\"" + description + '"');

        return result.toString();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String serialize() {
        StringBuilder result = new StringBuilder();

        if (skipSerialization) {
            return "";
        }

        result.append("<send ").append(serializeAttributes()).append(">\n");

        if (branches.get(0).hasElements()) {
            result.append('<').append(ENDPOINT_PROPERTY_NAME).append(">\n");

            result.append(branches.get(0).serialize());

            result.append("</").append(ENDPOINT_PROPERTY_NAME).append(">\n");
        }

        result.append("</send>");

        return result.toString();
    }

    /** {@inheritDoc} */
    @Override
    protected void applyAttributes(@Nonnull Node node) {
        if (node.hasAttributes()) {
            NamedNodeMap attributes = node.getAttributes();

            for (int i = 0; i < attributes.getLength(); i++) {
                Node attributeNode = attributes.item(i);

                String attributeName = attributeNode.getNodeName();
                String attributeValue = attributeNode.getNodeValue();

                switch (attributeName) {
                    case EXPRESSION_ATTRIBUTE_NAME:
                        if (attributeValue.contains("{")) {
                            dynamicExpression = attributeValue.replace("{", "").replace("}", "");

                            sequencerType = SequenceType.Dynamic;
                        } else {
                            staticExpression = attributeValue;

                            sequencerType = SequenceType.Static;
                        }
                        break;

                    case BUILD_MESSAGE_ATTRIBUTE_NAME:
                        buildMessage = Boolean.valueOf(attributeValue);
                        break;

                    case DESCRIPTION_ATTRIBUTE_NAME:
                        description = attributeValue;
                        break;

                    default:
                        if (StringUtils.startsWith(PREFIX, attributeName, true)) {
                            String name = StringUtils.trimStart(attributeName, PREFIX + ':');

                            NameSpace nameSpace = nameSpaceProvider.get();

                            nameSpace.setPrefix(name);
                            nameSpace.setUri(attributeValue);

                            nameSpaces.add(nameSpace);
                        }
                }
            }

        }
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        if (node.hasChildNodes()) {
            String nodeName = node.getNodeName();

            switch (nodeName) {
                case ENDPOINT_PROPERTY_NAME:
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
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void deserialize(@Nonnull Node node) {
        super.deserialize(node);

        if (branches.isEmpty()) {
            Branch branch = branchProvider.get();
            branch.setParent(this);

            branches.add(branch);
        }
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.send();
    }

    public enum SequenceType {
        Default, Static, Dynamic;

        public static final String TYPE_NAME = "ReceivingSequenceType";
    }

}