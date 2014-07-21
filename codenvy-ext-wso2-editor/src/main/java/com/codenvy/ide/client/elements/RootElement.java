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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Andrey Plotnikov
 */
public class RootElement extends AbstractShape {
    public static final String ELEMENT_NAME = "rootElement";

    private static final List<String> PROPERTIES                        = Collections.emptyList();
    private static final List<String> INTERNAL_PROPERTIES               = Arrays.asList(X_PROPERTY_NAME,
                                                                                        Y_PROPERTY_NAME,
                                                                                        UUID_PROPERTY_NAME,
                                                                                        AUTO_ALIGN_PROPERTY_NAME);
    private static final List<String> AVAILABLE_FOR_CONNECTION_ELEMENTS = Collections.emptyList();
    private static final List<String> COMPONENTS                        = Arrays.asList(Log.ELEMENT_NAME,
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

    public RootElement(@Nonnull String rootElement, @Nonnull List<String> properties, @Nonnull List<String> internalProperties) {
        super(rootElement, properties, internalProperties);
    }

    public RootElement() {
        this(ELEMENT_NAME, PROPERTIES, INTERNAL_PROPERTIES);

        components.addAll(COMPONENTS);
        targetElements.put(Connection.CONNECTION_NAME, AVAILABLE_FOR_CONNECTION_ELEMENTS);
    }

    @Override
    protected Element findElement(@Nonnull String elementName) {
        switch (elementName) {
            case Log.ELEMENT_NAME:
                return new Log();
            case Property.ELEMENT_NAME:
                return new Property();
            case PayloadFactory.ELEMENT_NAME:
                return new PayloadFactory();
            case Send.ELEMENT_NAME:
                return new Send();
            case Header.ELEMENT_NAME:
                return new Header();
            case Respond.ELEMENT_NAME:
                return new Respond();
            case Filter.ELEMENT_NAME:
                return new Filter();
            case Switch_mediator.ELEMENT_NAME:
                return new Switch_mediator();
            case Sequence.ELEMENT_NAME:
                return new Sequence();
            case Enrich.ELEMENT_NAME:
                return new Enrich();
            case LoopBack.ELEMENT_NAME:
                return new LoopBack();
            case CallTemplate.ELEMENT_NAME:
                return new CallTemplate();
            case Call.ELEMENT_NAME:
                return new Call();
            case Connection.CONNECTION_NAME:
            default:
                return new Connection();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void applyProperty(@Nonnull Node node) {
    }

}