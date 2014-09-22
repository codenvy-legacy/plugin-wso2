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
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.util.StringUtils;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.List;

import static com.codenvy.ide.client.elements.NameSpace.PREFIX;
import static com.codenvy.ide.client.elements.mediators.Sequence.ReferringType.DYNAMIC;
import static com.codenvy.ide.client.elements.mediators.Sequence.ReferringType.STATIC;

/**
 * The class which describes state of Sequence mediator and also has methods for changing it. Also the class contains the business
 * logic that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving. For more information about Sequence mediator go to
 * <a href=" https://docs.wso2.com/display/ESB460/Sequence+Mediator"> https://docs.wso2.com/display/ESB460/Sequence+Mediator</a>
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Valeriy Svydenko
 */
public class Sequence extends AbstractElement {
    public static final String ELEMENT_NAME       = "Sequence";
    public static final String SERIALIZATION_NAME = "sequence";

    public static final Key<Array<NameSpace>> NAMESPACES             = new Key<>("NameSpaces");
    public static final Key<ReferringType>    REFERRING_TYPE         = new Key<>("ReferringType");
    public static final Key<String>           STATIC_REFERENCE_TYPE  = new Key<>("StaticReferenceType");
    public static final Key<String>           DYNAMIC_REFERENCE_TYPE = new Key<>("DynamicReferenceType");

    private static final String KEY_ATTRIBUTE_NAME = "key";

    private static final List<String> PROPERTIES = java.util.Collections.emptyList();

    private final Provider<NameSpace> nameSpaceProvider;

    @Inject
    public Sequence(EditorResources resources,
                    Provider<Branch> branchProvider,
                    ElementCreatorsManager elementCreatorsManager,
                    Provider<NameSpace> nameSpaceProvider) {

        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.sequence(),
              branchProvider,
              elementCreatorsManager);

        this.nameSpaceProvider = nameSpaceProvider;

        putProperty(REFERRING_TYPE, STATIC);
        putProperty(STATIC_REFERENCE_TYPE, "");
        putProperty(DYNAMIC_REFERENCE_TYPE, "/default/expression");
        putProperty(NAMESPACES, Collections.<NameSpace>createArray());
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        ReferringType referringType = getProperty(REFERRING_TYPE);

        if (referringType != null && referringType.equals(STATIC)) {
            return KEY_ATTRIBUTE_NAME + "=\"" + getProperty(STATIC_REFERENCE_TYPE) + '"';
        }

        return convertNameSpaceToXMLFormat(getProperty(NAMESPACES)) + KEY_ATTRIBUTE_NAME + "=\"{" +
               getProperty(DYNAMIC_REFERENCE_TYPE) + "}\"";
    }

    @Override
    public void deserialize(@Nonnull Node node) {
        Array<NameSpace> nameSpaces = getProperty(NAMESPACES);
        if (nameSpaces != null) {
            nameSpaces.clear();
        }

        super.deserialize(node);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyAttribute(@Nonnull String attributeName, @Nonnull String attributeValue) {
        if (KEY_ATTRIBUTE_NAME.equals(attributeName)) {
            adaptKeyNameAttribute(attributeValue);
        } else {
            adaptNameSpaceAttribute(attributeName, attributeValue);
        }
    }

    private void adaptKeyNameAttribute(@Nonnull String value) {
        if (StringUtils.startsWith("{", value, true)) {
            putProperty(REFERRING_TYPE, DYNAMIC);

            int startPosition = value.indexOf("{") + 1;
            int endPosition = value.lastIndexOf("}");

            putProperty(DYNAMIC_REFERENCE_TYPE, value.substring(startPosition, endPosition));
        } else {
            putProperty(REFERRING_TYPE, STATIC);
            putProperty(STATIC_REFERENCE_TYPE, value);
        }
    }

    private void adaptNameSpaceAttribute(@Nonnull String attributeName, @Nonnull String attributeValue) {
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

    public enum ReferringType {
        DYNAMIC("Dynamic"), STATIC("Static");

        public static final String TYPE_NAME = "ReferringType";

        private final String value;

        ReferringType(@Nonnull String value) {
            this.value = value;
        }

        @Nonnull
        public String getValue() {
            return value;
        }

        @Nonnull
        public static ReferringType getItemByValue(@Nonnull String value) {
            switch (value) {
                case "Static":
                    return STATIC;

                case "Dynamic":
                default:
                    return DYNAMIC;
            }
        }
    }

}