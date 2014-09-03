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
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
import com.codenvy.ide.collections.Array;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.elements.connectors.salesforce.AbstractSalesForceConnector.ParameterEditorType.Inline;
import static com.codenvy.ide.client.elements.connectors.salesforce.AbstractSalesForceConnector.ParameterEditorType.NamespacedPropertyEditor;
import static com.codenvy.ide.collections.Collections.createArray;

/**
 * The Class describes DescribeSobject connector for Salesforce group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class DescribeSubject extends AbstractSalesForceConnector {

    public static final String ELEMENT_NAME       = "describeSobject";
    public static final String SERIALIZATION_NAME = "salesforce.describeSobject";
    public static final String SUBJECT            = "sobject";

    private static final List<String> PROPERTIES = Arrays.asList(SUBJECT);

    private String           subject;
    private String           subjectsInline;
    private Array<NameSpace> subjectsNameSpaces;

    @Inject
    public DescribeSubject(EditorResources resources, Provider<Branch> branchProvider, MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        subject = "";
        subjectsInline = "";

        subjectsNameSpaces = createArray();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        return Inline.equals(parameterEditorType) ? prepareProperties(subjectsInline) : prepareProperties(subject);
    }

    @Nonnull
    private String prepareProperties(@Nonnull String subject) {
        return !subject.isEmpty() ? '<' + SUBJECT + '>' + subject + "</" + SUBJECT + '>' : "";
    }

    /** {@inheritDoc} */
    @Override
    public void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();
        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case SUBJECT:
                if (isInline) {
                    subjectsInline = nodeValue;
                } else {
                    subject = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;
        }
    }

    @Nonnull
    public String getSubject() {
        return subject;
    }

    public void setSubject(@Nullable String subject) {
        this.subject = subject;
    }

    @Nonnull
    public String getSubjectInline() {
        return subjectsInline;
    }

    public void setSubjectInline(@Nonnull String subjectsInline) {
        this.subjectsInline = subjectsInline;
    }

    @Nonnull
    public Array<NameSpace> getSubjectsNameSpaces() {
        return subjectsNameSpaces;
    }

    public void setSubjectsNameSpaces(@Nonnull Array<NameSpace> subjectsNameSpaces) {
        this.subjectsNameSpaces = subjectsNameSpaces;
    }

}
