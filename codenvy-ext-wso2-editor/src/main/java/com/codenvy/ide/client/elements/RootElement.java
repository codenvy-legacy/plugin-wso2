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
package com.codenvy.ide.client.elements;

import com.codenvy.ide.client.common.ContentFormatter;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The class which describes state of Root element and also has methods for changing it. Also the class contains the business
 * logic that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving. Root element is a container which contains all elements
 * which we create in our ESB project.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class RootElement extends AbstractElement {
    public static final String ELEMENT_NAME       = "RootElement";
    public static final String SERIALIZATION_NAME = "sequence";

    private static final String NAME_SPACE_ATTRIBUTE = " xmlns=\"http://ws.apache.org/ns/synapse\" ";
    private static final String XML_HEADER           = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

    private static final String NAME_ATTRIBUTE_NAME     = "name";
    private static final String ON_ERROR_ATTRIBUTE_NAME = "onError";

    private static final List<String> PROPERTIES = Collections.emptyList();

    private String name;
    private String onError;

    @Inject
    public RootElement(Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, false, null, branchProvider, elementCreatorsManager);

        this.name = "";
        this.onError = "";
    }

    /** @return name of root element */
    @Nonnull
    public String getName() {
        return name;
    }

    /**
     * Sets name to root element
     *
     * @param name
     *         value which need to set to element
     */
    public void setName(@Nonnull String name) {
        this.name = name;
    }

    /** @return error value of root element */
    @Nonnull
    public String getOnError() {
        return onError;
    }

    /**
     * Sets value of error to element
     *
     * @param onError
     *         value which need to set to element
     */
    public void setOnError(@Nonnull String onError) {
        this.onError = onError;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String serialize() {
        String xml = ContentFormatter.formatXML(ContentFormatter.trimXML(super.serialize()));

        /*
         * Adds XML file header and name space for root element. We have problem with formatting XML content with name space
         * in the root node so we add the name space of root element after formatting operation.
         */
        int index = xml.indexOf(SERIALIZATION_NAME) + SERIALIZATION_NAME.length();
        return XML_HEADER + xml.subSequence(0, index) + NAME_SPACE_ATTRIBUTE + xml.subSequence(index + 1, xml.length());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeAttributes() {
        Map<String, String> attributes = new LinkedHashMap<>();
        attributes.put(ON_ERROR_ATTRIBUTE_NAME, onError);

        String onErrorAttribute = convertAttributesToXMLFormat(attributes);

        return NAME_ATTRIBUTE_NAME + "=\"" + name + "\"" + (onErrorAttribute.isEmpty() ? "" : ' ' + onErrorAttribute);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyAttribute(@Nonnull String attributeName, @Nonnull String attributeValue) {
        switch (attributeName) {
            case NAME_ATTRIBUTE_NAME:
                name = attributeValue;
                break;

            case ON_ERROR_ATTRIBUTE_NAME:
                onError = attributeValue;
                break;

            default:
        }
    }

    /** {@inheritDoc} */
    @Override
    public void deserialize(@Nonnull String content) {
        super.deserialize(content);

        if (branches.isEmpty()) {
            Branch branch = branchProvider.get();
            branch.setParent(this);

            branches.add(branch);
        }
    }

}