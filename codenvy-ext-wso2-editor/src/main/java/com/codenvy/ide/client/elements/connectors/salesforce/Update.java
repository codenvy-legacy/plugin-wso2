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
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.Inline;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NamespacedPropertyEditor;

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

    private static final String ALL_OR_NONE = "allOrNone";
    private static final String TRUNCATE    = "allowFieldTruncate";
    private static final String SUBJECTS    = "sobjects";

    private static final List<String> PROPERTIES = Arrays.asList(ALL_OR_NONE, TRUNCATE, SUBJECTS);

    private String          allOrNone;
    private String          truncate;
    private String          subjects;
    private String          allOrNoneInline;
    private String          truncateInline;
    private String          subjectsInline;
    private List<NameSpace> truncateNameSpaces;
    private List<NameSpace> subjectsNameSpaces;
    private List<NameSpace> allOrNoneNameSpaces;

    @Inject
    public Update(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.salesforce(),
              branchProvider,
              elementCreatorsManager);

        allOrNone = "";
        truncate = "";
        subjects = "";
        allOrNoneInline = "";
        truncateInline = "";
        subjectsInline = "";

        allOrNoneNameSpaces = new ArrayList<>();
        truncateNameSpaces = new ArrayList<>();
        subjectsNameSpaces = new ArrayList<>();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(ALL_OR_NONE, isInline ? allOrNoneInline : allOrNone);
        properties.put(TRUNCATE, isInline ? truncateInline : truncate);
        properties.put(SUBJECTS, isInline ? subjectsInline : subjects);

        return convertPropertiesToXMLFormat(properties);
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
    public List<NameSpace> getAllOrNoneNameSpaces() {
        return allOrNoneNameSpaces;
    }

    public void setAllOrNoneNameSpaces(@Nonnull List<NameSpace> allOrNoneNameSpaces) {
        this.allOrNoneNameSpaces = allOrNoneNameSpaces;
    }

    @Nonnull
    public List<NameSpace> getTruncateNameSpaces() {
        return truncateNameSpaces;
    }

    public void setTruncateNameSpaces(@Nonnull List<NameSpace> truncateNameSpaces) {
        this.truncateNameSpaces = truncateNameSpaces;
    }

    @Nonnull
    public List<NameSpace> getSubjectsNameSpaces() {
        return subjectsNameSpaces;
    }

    public void setSubjectsNameSpaces(@Nonnull List<NameSpace> subjectsNameSpaces) {
        this.subjectsNameSpaces = subjectsNameSpaces;
    }

}
