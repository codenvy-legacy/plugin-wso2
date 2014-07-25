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
public class Enrich extends RootElement {
    public static final String ELEMENT_NAME       = "Enrich";
    public static final String SERIALIZATION_NAME = "enrich";

    private static final String CLONE_SOURCE_PROPERTY_NAME  = "CloneSource";
    private static final String SOURCE_TYPE_PROPERTY_NAME   = "SourceType";
    private static final String SOURCE_XPATH_PROPERTY_NAME  = "SourceXpath";
    private static final String TARGET_ACTION_PROPERTY_NAME = "TargetAction";
    private static final String TARGET_TYPE_PROPERTY_NAME   = "TargetType";
    private static final String TARGET_XPATH_PROPERTY_NAME  = "TargetXpath";
    private static final String DESCRIPTION_PROPERTY_NAME   = "Description";

    private static final List<String> PROPERTIES          = Arrays.asList(CLONE_SOURCE_PROPERTY_NAME,
                                                                          SOURCE_TYPE_PROPERTY_NAME,
                                                                          SOURCE_XPATH_PROPERTY_NAME,
                                                                          TARGET_ACTION_PROPERTY_NAME,
                                                                          TARGET_TYPE_PROPERTY_NAME,
                                                                          TARGET_XPATH_PROPERTY_NAME,
                                                                          DESCRIPTION_PROPERTY_NAME);
    private static final List<String> INTERNAL_PROPERTIES = Arrays.asList(X_PROPERTY_NAME,
                                                                          Y_PROPERTY_NAME,
                                                                          UUID_PROPERTY_NAME,
                                                                          CLONE_SOURCE_PROPERTY_NAME,
                                                                          SOURCE_TYPE_PROPERTY_NAME,
                                                                          SOURCE_XPATH_PROPERTY_NAME,
                                                                          TARGET_ACTION_PROPERTY_NAME,
                                                                          TARGET_TYPE_PROPERTY_NAME,
                                                                          TARGET_XPATH_PROPERTY_NAME,
                                                                          DESCRIPTION_PROPERTY_NAME);

    private String cloneSource;
    private String sourceType;
    private String sourceXpath;
    private String targetAction;
    private String targetType;
    private String targetXpath;
    private String description;

    public Enrich() {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, INTERNAL_PROPERTIES);

        cloneSource = "false";
        sourceType = "custom";
        sourceXpath = "default/xpath";
        targetAction = "replace";
        targetType = "custom";
        targetXpath = "default/xpath";
        description = "enter_description";
    }

    @Nullable
    public String getCloneSource() {
        return cloneSource;
    }

    public void setCloneSource(@Nullable String cloneSource) {
        this.cloneSource = cloneSource;
    }

    @Nullable
    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(@Nullable String sourceType) {
        this.sourceType = sourceType;
    }

    @Nullable
    public String getSourceXpath() {
        return sourceXpath;
    }

    public void setSourceXpath(@Nullable String sourceXpath) {
        this.sourceXpath = sourceXpath;
    }

    @Nullable
    public String getTargetAction() {
        return targetAction;
    }

    public void setTargetAction(@Nullable String targetAction) {
        this.targetAction = targetAction;
    }

    @Nullable
    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(@Nullable String targetType) {
        this.targetType = targetType;
    }

    @Nullable
    public String getTargetXpath() {
        return targetXpath;
    }

    public void setTargetXpath(@Nullable String targetXpath) {
        this.targetXpath = targetXpath;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeProperties() {
        return "cloneSource=" + "\"" + cloneSource + "\" " +
               "sourceType=" + "\"" + sourceType + "\" " +
               "sourceXpath=" + "\"" + sourceXpath + "\" " +
               "targetAction=" + "\"" + targetAction + "\" " +
               "targetType=" + "\"" + targetType + "\" " +
               "targetXpath=" + "\"" + targetXpath + "\" " +
               "description=" + "\"" + description + "\" ";
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
            case CLONE_SOURCE_PROPERTY_NAME:
                cloneSource = String.valueOf(nodeValue);
                break;
            case SOURCE_TYPE_PROPERTY_NAME:
                sourceType = String.valueOf(nodeValue);
                break;
            case SOURCE_XPATH_PROPERTY_NAME:
                sourceXpath = String.valueOf(nodeValue);
                break;
            case TARGET_ACTION_PROPERTY_NAME:
                targetAction = String.valueOf(nodeValue);
                break;
            case TARGET_TYPE_PROPERTY_NAME:
                targetType = String.valueOf(nodeValue);
                break;
            case TARGET_XPATH_PROPERTY_NAME:
                targetXpath = String.valueOf(nodeValue);
                break;
            case DESCRIPTION_PROPERTY_NAME:
                description = String.valueOf(nodeValue);
                break;
        }
    }

}