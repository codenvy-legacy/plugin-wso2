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
package com.codenvy.ide.client.elements.mediators;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.AbstractElement;
import com.codenvy.ide.client.elements.Branch;
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
import java.util.List;

import static com.codenvy.ide.client.elements.NameSpace.PREFIX;
import static com.codenvy.ide.client.elements.mediators.Sequence.ReferringType.Dynamic;
import static com.codenvy.ide.client.elements.mediators.Sequence.ReferringType.Static;

/**
 * The class which describes state of Sequence mediator and also has methods for changing it. Also the class contains the business
 * logic that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving. For more information about Sequence mediator go to
 * <a href=" https://docs.wso2.com/display/ESB460/Sequence+Mediator"> https://docs.wso2.com/display/ESB460/Sequence+Mediator</a>
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class Sequence extends AbstractElement {
    public static final String ELEMENT_NAME       = "Sequence";
    public static final String SERIALIZATION_NAME = "sequence";

    private static final String KEY_ATTRIBUTE_NAME = "key";

    private static final List<String> PROPERTIES = java.util.Collections.emptyList();

    private ReferringType    referringType;
    private String           staticReferenceKey;
    private String           dynamicReferenceKey;
    private Array<NameSpace> nameSpaces;

    @Inject
    public Sequence(EditorResources resources, Provider<Branch> branchProvider, MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        referringType = Static;
        staticReferenceKey = "";
        dynamicReferenceKey = "/default/expression";
        nameSpaces = Collections.createArray();
    }

    /** @return referring type of sequence */
    @Nonnull
    public ReferringType getReferringType() {
        return referringType;
    }

    /**
     * Changes referring type of sequence.
     *
     * @param referringType
     *         new referring type of sequence
     */
    public void setReferringType(@Nonnull ReferringType referringType) {
        this.referringType = referringType;
    }

    /** @return static reference key of sequence */
    @Nullable
    public String getStaticReferenceKey() {
        return staticReferenceKey;
    }

    /**
     * Changes static reference key of sequence.
     *
     * @param staticReferenceKey
     *         new static reference key of sequence
     */
    public void setStaticReferenceKey(@Nullable String staticReferenceKey) {
        this.staticReferenceKey = staticReferenceKey;
    }

    /** @return dynamic reference key of sequence */
    @Nullable
    public String getDynamicReferenceKey() {
        return dynamicReferenceKey;
    }

    /**
     * Changes dynamic reference key of sequence.
     *
     * @param dynamicReferenceKey
     *         new dynamic reference key of sequence
     */
    public void setDynamicReferenceKey(@Nullable String dynamicReferenceKey) {
        this.dynamicReferenceKey = dynamicReferenceKey;
    }

    /** @return namespaces which contain in sequence */
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
    @Override
    @Nonnull
    protected String serializeAttributes() {
        if (referringType.equals(Static)) {
            return KEY_ATTRIBUTE_NAME + "=\"" + staticReferenceKey + '"';
        }

        StringBuilder spaces = new StringBuilder();

        for (NameSpace nameSpace : nameSpaces.asIterable()) {
            spaces.append(nameSpace.toString()).append(' ');
        }

        return spaces + KEY_ATTRIBUTE_NAME + "=\"{" + dynamicReferenceKey + "}\"";
    }

    /** {@inheritDoc} */
    @Override
    protected void applyAttributes(@Nonnull Node node) {
        nameSpaces.clear();

        NamedNodeMap attributeMap = node.getAttributes();

        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attributeNode = attributeMap.item(i);

            String nodeValue = attributeNode.getNodeValue();
            String nodeName = attributeNode.getNodeName();

            switch (nodeName) {
                case KEY_ATTRIBUTE_NAME:
                    if (StringUtils.startsWith("{", nodeValue, true)) {
                        referringType = Dynamic;

                        int startPosition = nodeValue.indexOf("{") + 1;
                        int endPosition = nodeValue.lastIndexOf("}");

                        dynamicReferenceKey = nodeValue.substring(startPosition, endPosition);
                    } else {
                        referringType = Static;
                        staticReferenceKey = nodeValue;
                    }
                    break;

                default:
                    if (StringUtils.startsWith(PREFIX, nodeName, true)) {
                        String name = StringUtils.trimStart(nodeName, PREFIX + ':');
                        //TODO create entity using edit factory
                        NameSpace nameSpace = new NameSpace(name, nodeValue);

                        nameSpaces.add(nameSpace);
                    }
            }
        }
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.sequence();
    }

    public enum ReferringType {
        Dynamic, Static;

        public static final String TYPE_NAME = "ReferringType";
    }

}