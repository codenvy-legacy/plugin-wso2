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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.NameSpace.PREFIX;
import static com.codenvy.ide.client.elements.mediators.Send.SequenceType.DEFAULT;
import static com.codenvy.ide.client.elements.mediators.Send.SequenceType.DYNAMIC;
import static com.codenvy.ide.client.elements.mediators.Send.SequenceType.STATIC;

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

    public static final Key<SequenceType>     SEQUENCE_TYPE      = new Key<>("SequenceType");
    public static final Key<Boolean>          SKIP_SERIALIZATION = new Key<>("SkipSerialization");
    public static final Key<Boolean>          BUILD_MESSAGE      = new Key<>("BuildMessage");
    public static final Key<String>           DESCRIPTION        = new Key<>("Description");
    public static final Key<String>           DYNAMIC_EXPRESSION = new Key<>("DynamicExpression");
    public static final Key<String>           STATIC_EXPRESSION  = new Key<>("StaticExpression");
    public static final Key<Array<NameSpace>> NAMESPACES         = new Key<>("NameSpaces");

    private static final String BUILD_MESSAGE_ATTRIBUTE_NAME = "buildmessage";
    private static final String DESCRIPTION_ATTRIBUTE_NAME   = "description";
    private static final String EXPRESSION_ATTRIBUTE_NAME    = "receive";
    private static final String ENDPOINT_PROPERTY_NAME       = "endpoint";

    private static final List<String> PROPERTIES = Arrays.asList(ENDPOINT_PROPERTY_NAME);

    private final Provider<NameSpace> nameSpaceProvider;

    @Inject
    public Send(EditorResources resources,
                Provider<Branch> branchProvider,
                ElementCreatorsManager elementCreatorsManager,
                Provider<NameSpace> nameSpaceProvider) {

        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.send(),
              branchProvider,
              elementCreatorsManager);

        this.nameSpaceProvider = nameSpaceProvider;

        putProperty(SKIP_SERIALIZATION, false);
        putProperty(BUILD_MESSAGE, false);
        putProperty(SEQUENCE_TYPE, DEFAULT);
        putProperty(DESCRIPTION, "");
        putProperty(DYNAMIC_EXPRESSION, "/default/xpath");
        putProperty(STATIC_EXPRESSION, "/default/sequence");
        putProperty(NAMESPACES, Collections.<NameSpace>createArray());

        Branch branch = branchProvider.get();
        branch.setParent(this);

        branches.add(branch);
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        StringBuilder result = new StringBuilder();
        Map<String, String> prop = new LinkedHashMap<>();

        serializeSequenceTypeAttribute(prop, result);

        Boolean buildMessage = getProperty(BUILD_MESSAGE);
        if (buildMessage != null && buildMessage) {
            prop.put(BUILD_MESSAGE_ATTRIBUTE_NAME, "true");
        }

        prop.put(DESCRIPTION_ATTRIBUTE_NAME, getProperty(DESCRIPTION));

        return result.append(convertAttributesToXMLFormat(prop)).toString();
    }

    private void serializeSequenceTypeAttribute(@Nonnull Map<String, String> prop, @Nonnull StringBuilder result) {
        SequenceType sType = getProperty(SEQUENCE_TYPE);
        if (sType == null) {
            return;
        }

        if (sType.equals(STATIC)) {
            prop.put(EXPRESSION_ATTRIBUTE_NAME, getProperty(STATIC_EXPRESSION));
        } else if (sType.equals(DYNAMIC)) {
            prop.put(EXPRESSION_ATTRIBUTE_NAME, getProperty(DYNAMIC_EXPRESSION));
            result.append(convertNameSpaceToXMLFormat(getProperty(NAMESPACES)));
        }
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String serialize() {
        Boolean skipSerialization = getProperty(SKIP_SERIALIZATION);
        if (skipSerialization != null && skipSerialization) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        result.append('<').append(SERIALIZATION_NAME).append(' ').append(serializeAttributes()).append(">\n");

        if (branches.get(0).hasElements()) {
            result.append('<').append(ENDPOINT_PROPERTY_NAME).append(">\n");

            result.append(branches.get(0).serialize());

            result.append("</").append(ENDPOINT_PROPERTY_NAME).append(">\n");
        }

        result.append("</").append(SERIALIZATION_NAME).append('>');

        return result.toString();
    }

    /** {@inheritDoc} */
    @Override
    protected void applyAttribute(@Nonnull String attributeName, @Nonnull String attributeValue) {
        switch (attributeName) {
            case EXPRESSION_ATTRIBUTE_NAME:
                adaptExpressionAttribute(attributeValue);
                break;

            case BUILD_MESSAGE_ATTRIBUTE_NAME:
                putProperty(BUILD_MESSAGE, Boolean.valueOf(attributeValue));
                break;

            case DESCRIPTION_ATTRIBUTE_NAME:
                putProperty(DESCRIPTION, attributeValue);
                break;

            default:
                applyNameSpaces(attributeName, attributeValue);
        }
    }

    private void adaptExpressionAttribute(@Nonnull String attributeValue) {
        if (attributeValue.contains("{")) {
            putProperty(DYNAMIC_EXPRESSION, attributeValue.replace("{", "").replace("}", ""));

            putProperty(SEQUENCE_TYPE, SequenceType.DYNAMIC);
        } else {
            putProperty(STATIC_EXPRESSION, attributeValue);

            putProperty(SEQUENCE_TYPE, SequenceType.STATIC);
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

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        if (node.hasChildNodes()) {
            if (!ENDPOINT_PROPERTY_NAME.equals(node.getNodeName())) {
                return;
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

    public enum SequenceType {
        DEFAULT("Default"), STATIC("Static"), DYNAMIC("Dynamic");

        public static final String TYPE_NAME = "ReceivingSequenceType";

        private final String value;

        SequenceType(@Nonnull String value) {
            this.value = value;
        }

        @Nonnull
        public String getValue() {
            return value;
        }

        @Nonnull
        public static SequenceType getItemByValue(@Nonnull String value) {
            switch (value) {
                case "Dynamic":
                    return DYNAMIC;

                case "Static":
                    return STATIC;

                case "Default":
                default:
                    return DEFAULT;
            }
        }
    }

}