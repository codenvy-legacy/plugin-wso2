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
public class Switch_mediator extends RootElement {
    public static final String ELEMENT_NAME       = "Switch";
    public static final String SERIALIZATION_NAME = "switch";

    private static final String SOURCE_XPATH_PROPERTY_NAME       = "SourceXpath";
    private static final String CASE_BRANCHES_TYPE_PROPERTY_NAME = "CaseBranches";

    private static final List<String> PROPERTIES          = Arrays.asList(SOURCE_XPATH_PROPERTY_NAME,
                                                                          CASE_BRANCHES_TYPE_PROPERTY_NAME);
    private static final List<String> INTERNAL_PROPERTIES = Arrays.asList(X_PROPERTY_NAME,
                                                                          Y_PROPERTY_NAME,
                                                                          UUID_PROPERTY_NAME,
                                                                          SOURCE_XPATH_PROPERTY_NAME,
                                                                          CASE_BRANCHES_TYPE_PROPERTY_NAME);
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

    private String sourceXpath;
    private String caseBranches;

    public Switch_mediator() {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, INTERNAL_PROPERTIES);

        sourceXpath = "default/xpath";
        caseBranches = "enter_case_branches";

        components.addAll(COMPONENTS);
    }

    @Nullable
    public String getSourceXpath() {
        return sourceXpath;
    }

    public void setSourceXpath(@Nullable String sourceXpath) {
        this.sourceXpath = sourceXpath;
    }

    @Nullable
    public String getCaseBranches() {
        return caseBranches;
    }

    public void setCaseBranches(@Nullable String caseBranches) {
        this.caseBranches = caseBranches;
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeProperties() {
        return "sourceXpath=\"" + sourceXpath + "\" " +
               "caseBranches=\"" + caseBranches + "\" ";
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
            case SOURCE_XPATH_PROPERTY_NAME:
                sourceXpath = String.valueOf(nodeValue);
                break;
            case CASE_BRANCHES_TYPE_PROPERTY_NAME:
                caseBranches = String.valueOf(nodeValue);
                break;
        }
    }

}