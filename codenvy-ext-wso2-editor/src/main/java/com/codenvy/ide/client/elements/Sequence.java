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
package com.codenvy.ide.client.elements;

import com.google.gwt.xml.client.Node;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

/**
 * @author Andrey Plotnikov
 */
public class Sequence extends RootElement {
    public static final String ELEMENT_NAME       = "Sequence";
    public static final String SERIALIZATION_NAME = "sequence";

    private static final String REFERRING_SEQUENCE_PROPERTY_NAME   = "ReferringSequenceType";
    private static final String STATIC_REFERENCE_KEY_PROPERTY_NAME = "StaticReferenceKey";

    private static final List<String> PROPERTIES          = Arrays.asList(REFERRING_SEQUENCE_PROPERTY_NAME,
                                                                          STATIC_REFERENCE_KEY_PROPERTY_NAME);
    private static final List<String> INTERNAL_PROPERTIES = Arrays.asList(X_PROPERTY_NAME,
                                                                          Y_PROPERTY_NAME,
                                                                          UUID_PROPERTY_NAME,
                                                                          REFERRING_SEQUENCE_PROPERTY_NAME,
                                                                          STATIC_REFERENCE_KEY_PROPERTY_NAME);

    private String referringSequenceType;
    private String staticReferenceKey;

    public Sequence() {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, INTERNAL_PROPERTIES);

        referringSequenceType = "Static";
        staticReferenceKey = "Sequence";
    }

    @Nullable
    public String getReferringSequenceType() {
        return referringSequenceType;
    }

    public void setReferringSequenceType(@Nullable String referringSequenceType) {
        this.referringSequenceType = referringSequenceType;
    }

    @Nullable
    public String getStaticReferenceKey() {
        return staticReferenceKey;
    }

    public void setStaticReferenceKey(@Nullable String staticReferenceKey) {
        this.staticReferenceKey = staticReferenceKey;
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        return "referringSequenceType=\"" + referringSequenceType + "\" " +
               "staticReferenceKey=\"" + staticReferenceKey + "\" ";
    }

    /** {@inheritDoc} */
    @Override
    public void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        switch (nodeName) {
            case AbstractShape.X_PROPERTY_NAME:
                setX(Integer.valueOf(nodeValue));
                break;
            case AbstractShape.Y_PROPERTY_NAME:
                setY(Integer.valueOf(nodeValue));
                break;
            case AbstractElement.UUID_PROPERTY_NAME:
                id = nodeValue;
                break;
            case REFERRING_SEQUENCE_PROPERTY_NAME:
                referringSequenceType = String.valueOf(nodeValue);
                break;
            case STATIC_REFERENCE_KEY_PROPERTY_NAME:
                staticReferenceKey = String.valueOf(nodeValue);
                break;
        }
    }

}