/*
 * Copyright [2014] Codenvy, S.A.
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
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.elements.connectors.salesforce.AbstractSalesForceConnector.ParameterEditorType;
import static com.codenvy.ide.client.elements.connectors.salesforce.AbstractSalesForceConnector.ParameterEditorType.Inline;
import static com.codenvy.ide.collections.Collections.createArray;

/**
 * The Class describes Upset connector for Salesforce group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 */
public class Upset extends AbstractShape {

    public static final String ELEMENT_NAME       = "Upset";
    public static final String SERIALIZATION_NAME = "salesforce.upset";
    public static final String CONFIG_KEY         = "configKey";
    public static final String ALL_OR_NONE        = "allOrNone";
    public static final String EXTERNAL_ID        = "externalId";
    public static final String TRUNCATE           = "allowFieldTruncate";
    public static final String SUBJECTS           = "sobjects";

    private static final List<String> PROPERTIES = Arrays.asList(ALL_OR_NONE, TRUNCATE, EXTERNAL_ID, SUBJECTS);

    private String              configRef;
    private String              allOrNone;
    private String              truncate;
    private String              externalId;
    private String              subjects;
    private String              allOrNoneInline;
    private String              truncateInline;
    private String              externalIdInline;
    private String              subjectsInline;
    private ParameterEditorType parameterEditorType;
    private Array<NameSpace>    truncateNameSpaces;
    private Array<NameSpace>    subjectsNameSpaces;
    private Array<NameSpace>    externalIdNameSpaces;
    private Array<NameSpace>    allOrNoneNameSpaces;

    @Inject
    public Upset(EditorResources resources, Provider<Branch> branchProvider, MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        allOrNone = "";
        truncate = "";
        subjects = "";
        externalId = "";
        allOrNoneInline = "";
        externalIdInline = "";
        truncateInline = "";
        subjectsInline = "";

        parameterEditorType = ParameterEditorType.Inline;

        allOrNoneNameSpaces = createArray();
        externalIdNameSpaces = createArray();
        truncateNameSpaces = createArray();
        subjectsNameSpaces = createArray();
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        return configRef == null || configRef.isEmpty() ? "" : CONFIG_KEY + "=\"" + configRef + "\"";
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        return Inline.equals(parameterEditorType) ? convertPropertiesToXml(allOrNoneInline,
                                                                           truncateInline,
                                                                           subjectsInline,
                                                                           externalIdInline)
                                                  : convertPropertiesToXml(allOrNone, truncate, subjects, externalId);
    }

    @Nonnull
    private String convertPropertiesToXml(@Nonnull String allOrNone,
                                          @Nonnull String truncate,
                                          @Nonnull String subjects,
                                          @Nonnull String externalId) {
        StringBuilder result = new StringBuilder();

        if (!allOrNone.isEmpty()) {
            result.append('<').append(ALL_OR_NONE).append('>').append(allOrNone).append("</").append(ALL_OR_NONE).append('>');
        }

        if (!truncate.isEmpty()) {
            result.append('<').append(TRUNCATE).append('>').append(truncate).append("</").append(TRUNCATE).append('>');
        }

        if (!subjects.isEmpty()) {
            result.append('<').append(SUBJECTS).append('>').append(subjects).append("</").append(SUBJECTS).append('>');
        }

        if (!externalId.isEmpty()) {
            result.append('<').append(SUBJECTS).append('>').append(externalId).append("</").append(SUBJECTS).append('>');
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
                    allOrNoneInline = nodeValue;
                } else {
                    allOrNone = nodeValue;
                }
                break;

            case TRUNCATE:
                if (isInline) {
                    truncateInline = nodeValue;
                } else {
                    truncate = nodeValue;
                }
                break;

            case SUBJECTS:
                if (isInline) {
                    subjectsInline = nodeValue;
                } else {
                    subjects = nodeValue;
                }
                break;

            case EXTERNAL_ID:
                if (isInline) {
                    externalIdInline = nodeValue;
                } else {
                    externalId = nodeValue;
                }
                break;

        }
    }

    /** {@inheritDoc} */
    @Override
    public void applyAttributes(@Nonnull Node node) {
        NamedNodeMap attributeMap = node.getAttributes();

        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attributeNode = attributeMap.item(i);

            String attributeValue = attributeNode.getNodeValue();

            switch (attributeNode.getNodeName()) {
                case CONFIG_KEY:
                    configRef = attributeValue;
                    break;
            }
        }
    }

    public void setParameterEditorType(@Nonnull ParameterEditorType parameterEditorType) {
        this.parameterEditorType = parameterEditorType;
    }

    @Nonnull
    public String getConfigRef() {
        return configRef;
    }

    public void setConfigRef(@Nonnull String configRef) {
        this.configRef = configRef;
    }

    @Nonnull
    public String getAllOrNone() {
        return allOrNone;
    }

    public void setAllOrNone(@Nullable String allOrNone) {
        this.allOrNone = allOrNone;
    }

    @Nonnull
    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(@Nullable String externalId) {
        this.externalId = externalId;
    }

    @Nonnull
    public String getTruncate() {
        return truncate;
    }

    public void setTruncate(@Nullable String truncate) {
        this.truncate = truncate;
    }

    @Nonnull
    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(@Nullable String subjects) {
        this.subjects = subjects;
    }

    @Nonnull
    public String getAllOrNoneInlineInline() {
        return allOrNoneInline;
    }

    public void setAllOrNoneInlineInline(@Nonnull String allOrNoneInline) {
        this.allOrNoneInline = allOrNoneInline;
    }

    @Nonnull
    public String getExternalIdInline() {
        return externalIdInline;
    }

    public void setExternalIdInline(@Nonnull String externalId) {
        this.externalIdInline = externalId;
    }

    @Nonnull
    public String getTruncateInline() {
        return truncateInline;
    }

    public void setTruncateInline(@Nonnull String truncateInline) {
        this.truncateInline = truncateInline;
    }

    @Nonnull
    public String getSubjectsInline() {
        return subjectsInline;
    }

    public void setSubjectsInline(@Nonnull String subjectsInline) {
        this.subjectsInline = subjectsInline;
    }

    @Nonnull
    public Array<NameSpace> getAllOrNoneNameSpaces() {
        return allOrNoneNameSpaces;
    }

    public void setAllOrNoneNameSpaces(@Nonnull Array<NameSpace> allOrNoneNameSpaces) {
        this.allOrNoneNameSpaces = allOrNoneNameSpaces;
    }

    @Nonnull
    public Array<NameSpace> getExternalIdNameSpaces() {
        return externalIdNameSpaces;
    }

    public void setExternalIdNameSpaces(@Nonnull Array<NameSpace> externalIdNameSpaces) {
        this.externalIdNameSpaces = externalIdNameSpaces;
    }

    @Nonnull
    public Array<NameSpace> getTruncateNameSpaces() {
        return truncateNameSpaces;
    }

    public void setTruncateNameSpaces(@Nonnull Array<NameSpace> truncateNameSpaces) {
        this.truncateNameSpaces = truncateNameSpaces;
    }

    @Nonnull
    public ParameterEditorType getParameterEditorType() {
        return parameterEditorType;
    }

    @Nonnull
    public Array<NameSpace> getSubjectsNameSpaces() {
        return subjectsNameSpaces;
    }

    public void setSubjectsNameSpaces(@Nonnull Array<NameSpace> subjectsNameSpaces) {
        this.subjectsNameSpaces = subjectsNameSpaces;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.salesforce();
    }
}
