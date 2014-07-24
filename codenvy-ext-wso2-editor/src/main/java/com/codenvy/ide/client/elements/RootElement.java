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

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Andrey Plotnikov
 */
public class RootElement extends AbstractShape {
    public static final String ELEMENT_NAME       = "RootElement";
    public static final String SERIALIZATION_NAME = "sequence";

    private static final List<String> PROPERTIES          = Collections.emptyList();
    private static final List<String> INTERNAL_PROPERTIES = Arrays.asList(X_PROPERTY_NAME,
                                                                          Y_PROPERTY_NAME,
                                                                          UUID_PROPERTY_NAME,
                                                                          AUTO_ALIGN_PROPERTY_NAME);
    private static final List<String> COMPONENTS          = Arrays.asList(Log.ELEMENT_NAME,
                                                                          Property.ELEMENT_NAME,
                                                                          PayloadFactory.ELEMENT_NAME,
                                                                          Send.ELEMENT_NAME,
                                                                          Header.ELEMENT_NAME,
                                                                          Respond.ELEMENT_NAME,
                                                                          Filter.ELEMENT_NAME,
                                                                          Switch_mediator.ELEMENT_NAME,
                                                                          Sequence.ELEMENT_NAME,
                                                                          Enrich.ELEMENT_NAME,
                                                                          LoopBack.ELEMENT_NAME,
                                                                          CallTemplate.ELEMENT_NAME,
                                                                          Call.ELEMENT_NAME);

    public RootElement(@Nonnull String elementName,
                       @Nonnull String title,
                       @Nonnull String serializationName,
                       @Nonnull List<String> properties,
                       @Nonnull List<String> internalProperties) {
        super(elementName, title, serializationName, properties, internalProperties);
    }

    public RootElement() {
        this(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, INTERNAL_PROPERTIES);

        components.addAll(COMPONENTS);
    }

    /** {@inheritDoc} */
    @Override
    protected Element findElement(@Nonnull String elementName) {
        switch (elementName) {
            case Log.SERIALIZATION_NAME:
                return new Log();
            case Property.SERIALIZATION_NAME:
                return new Property();
            case PayloadFactory.SERIALIZATION_NAME:
                return new PayloadFactory();
            case Send.SERIALIZATION_NAME:
                return new Send();
            case Header.SERIALIZATION_NAME:
                return new Header();
            case Respond.SERIALIZATION_NAME:
                return new Respond();
            case Filter.SERIALIZATION_NAME:
                return new Filter();
            case Switch_mediator.SERIALIZATION_NAME:
                return new Switch_mediator();
            case Sequence.SERIALIZATION_NAME:
                return new Sequence();
            case Enrich.SERIALIZATION_NAME:
                return new Enrich();
            case LoopBack.SERIALIZATION_NAME:
                return new LoopBack();
            case CallTemplate.SERIALIZATION_NAME:
                return new CallTemplate();
            case Call.SERIALIZATION_NAME:
                return new Call();
            case Connection.SERIALIZATION_NAME:
            default:
                return new Connection();
        }
    }

}