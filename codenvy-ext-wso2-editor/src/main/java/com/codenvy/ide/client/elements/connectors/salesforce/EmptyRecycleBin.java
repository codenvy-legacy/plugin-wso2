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
package com.codenvy.ide.client.elements.connectors.salesforce;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.AbstractShape;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.elements.connectors.salesforce.GeneralProperty.ParameterEditorType.Inline;

/**
 * The Class describes EmptyRecycleBin connector for Salesforce group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 */
public class EmptyRecycleBin extends AbstractShape {

    public static final String ELEMENT_NAME       = "EmptyRecycleBin";
    public static final String SERIALIZATION_NAME = "salesforce.emptyRecycleBin";
    public static final String CONFIG_KEY         = "configKey";
    public static final String ALL_OR_NONE        = "allOrNone";
    public static final String SUBJECTS           = "sobjects";

    private static final List<String> PROPERTIES = Arrays.asList(ALL_OR_NONE, SUBJECTS);

    private String                              configKey;
    private String                              allOrNone;
    private String                              subject;
    private String                              allOrNoneExpr;
    private String                              subjectExpression;
    private GeneralProperty.ParameterEditorType parameterEditorType;
    private Array<NameSpace>                    allOrNoneNameSpaces;
    private Array<NameSpace>                    subjectsNameSpaces;

    @Inject
    public EmptyRecycleBin(EditorResources resources, Provider<Branch> branchProvider, MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        configKey = "";
        allOrNone = "";
        subject = "";
        allOrNoneExpr = "";
        subjectExpression = "";

        parameterEditorType = Inline;

        allOrNoneNameSpaces = Collections.createArray();
        subjectsNameSpaces = Collections.createArray();
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        return configKey == null || configKey.isEmpty() ? "" : CONFIG_KEY + "=\"" + configKey + "\"";
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        return Inline.equals(parameterEditorType) ? convertPropertiesToXml(allOrNone, subject)
                                                  : convertPropertiesToXml(allOrNoneExpr, subjectExpression);
    }

    @Nonnull
    private String convertPropertiesToXml(@Nonnull String allOrNone, @Nonnull String subject) {
        StringBuilder result = new StringBuilder("");

        if (!allOrNone.isEmpty()) {
            result.append('<').append(ALL_OR_NONE).append('>').append(allOrNone).append("</").append(ALL_OR_NONE).append('>');
        }

        if (!subject.isEmpty()) {
            result.append('<').append(SUBJECTS).append('>').append(subject).append("</").append(SUBJECTS).append('>');
        }

        return result.toString();
    }

    /** {@inheritDoc} */
    @Override
    public void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();
        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case ALL_OR_NONE:
                if (isInline) {
                    allOrNone = nodeValue;
                } else {
                    allOrNoneExpr = nodeValue;
                }
                break;

            case SUBJECTS:
                if (isInline) {
                    subject = nodeValue;
                } else {
                    subjectExpression = nodeValue;
                }
                break;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void applyAttributes(@Nonnull Node node) {
        if (node.hasAttributes()) {
            Node attribute = node.getAttributes().item(0);

            configKey = attribute.getNodeValue();
        }
    }

    @Nonnull
    public String getConfigRef() {
        return configKey;
    }

    public void setConfigRef(@Nonnull String configRef) {
        this.configKey = configRef;
    }

    @Nonnull
    public String getAllOrNone() {
        return allOrNone;
    }

    public void setAllOrNone(@Nonnull String allOrNone) {
        this.allOrNone = allOrNone;
    }

    @Nonnull
    public String getSubject() {
        return subject;
    }

    public void setSubject(@Nonnull String subjects) {
        this.subject = subjects;
    }

    @Nonnull
    public Array<NameSpace> getAllOrNoneNameSpaces() {
        return allOrNoneNameSpaces;
    }

    public void setAllOrNoneNameSpaces(@Nonnull Array<NameSpace> allOrNoneNameSpaces) {
        this.allOrNoneNameSpaces = allOrNoneNameSpaces;
    }

    @Nonnull
    public Array<NameSpace> getSubjectsNameSpaces() {
        return subjectsNameSpaces;
    }

    public void setSubjectsNameSpaces(@Nonnull Array<NameSpace> subjectsNameSpaces) {
        this.subjectsNameSpaces = subjectsNameSpaces;
    }

    @Nonnull
    public GeneralProperty.ParameterEditorType getParameterEditorType() {
        return parameterEditorType;
    }

    public void setParameterEditorType(@Nonnull GeneralProperty.ParameterEditorType parameterEditorType) {
        this.parameterEditorType = parameterEditorType;
    }

    @Nonnull
    public String getAllOrNoneExpr() {
        return allOrNoneExpr;
    }

    public void setAllOrNoneExpr(@Nonnull String allOrNoneExpr) {
        this.allOrNoneExpr = allOrNoneExpr;
    }

    @Nonnull
    public String getSubjectExpression() {
        return subjectExpression;
    }

    public void setSubjectExpression(@Nonnull String subjectExpression) {
        this.subjectExpression = subjectExpression;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.salesforce();
    }
}
