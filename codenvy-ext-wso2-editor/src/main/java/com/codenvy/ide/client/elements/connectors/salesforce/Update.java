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
package com.codenvy.ide.client.elements.connectors.salesforce;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.connectors.AbstractConnector;
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
import com.codenvy.ide.collections.Array;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.Inline;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NamespacedPropertyEditor;
import static com.codenvy.ide.collections.Collections.createArray;

/**
 * The Class describes Update connector for Salesforce group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class Update extends AbstractConnector {

    public static final String ELEMENT_NAME       = "Update";
    public static final String SERIALIZATION_NAME = "salesforce.update";
    public static final String ALL_OR_NONE        = "allOrNone";
    public static final String TRUNCATE           = "allowFieldTruncate";
    public static final String SUBJECTS           = "sobjects";

    private static final List<String> PROPERTIES = Arrays.asList(ALL_OR_NONE, TRUNCATE, SUBJECTS);

    private String           allOrNone;
    private String           truncate;
    private String           subjects;
    private String           allOrNoneInline;
    private String           truncateInline;
    private String           subjectsInline;
    private Array<NameSpace> truncateNameSpaces;
    private Array<NameSpace> subjectsNameSpaces;
    private Array<NameSpace> allOrNoneNameSpaces;

    @Inject
    public Update(EditorResources resources, Provider<Branch> branchProvider, MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        allOrNone = "";
        truncate = "";
        subjects = "";
        allOrNoneInline = "";
        truncateInline = "";
        subjectsInline = "";

        allOrNoneNameSpaces = createArray();
        truncateNameSpaces = createArray();
        subjectsNameSpaces = createArray();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        return Inline.equals(parameterEditorType) ? convertPropertiesToXml(allOrNoneInline, truncateInline, subjectsInline)
                                                  : convertPropertiesToXml(allOrNone, truncate, subjects);
    }

    @Nonnull
    private String convertPropertiesToXml(@Nonnull String allOrNone,
                                          @Nonnull String truncate,
                                          @Nonnull String subjects) {
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

        return result.toString();
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();
        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case ALL_OR_NONE:
                if (isInline) {
                    allOrNoneInline = nodeValue;
                } else {
                    allOrNone = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case TRUNCATE:
                if (isInline) {
                    truncateInline = nodeValue;
                } else {
                    truncate = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case SUBJECTS:
                if (isInline) {
                    subjectsInline = nodeValue;
                } else {
                    subjects = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

        }
    }

    @Nonnull
    public String getAllOrNone() {
        return allOrNone;
    }

    public void setAllOrNone(@Nullable String allOrNone) {
        this.allOrNone = allOrNone;
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
    public String getAllOrNoneInline() {
        return allOrNoneInline;
    }

    public void setAllOrNoneInline(@Nonnull String allOrNoneInline) {
        this.allOrNoneInline = allOrNoneInline;
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
    public Array<NameSpace> getTruncateNameSpaces() {
        return truncateNameSpaces;
    }

    public void setTruncateNameSpaces(@Nonnull Array<NameSpace> truncateNameSpaces) {
        this.truncateNameSpaces = truncateNameSpaces;
    }

    @Nonnull
    public Array<NameSpace> getSubjectsNameSpaces() {
        return subjectsNameSpaces;
    }

    public void setSubjectsNameSpaces(@Nonnull Array<NameSpace> subjectsNameSpaces) {
        this.subjectsNameSpaces = subjectsNameSpaces;
    }

}
