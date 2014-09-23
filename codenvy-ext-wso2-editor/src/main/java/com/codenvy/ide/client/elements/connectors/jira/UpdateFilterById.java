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
package com.codenvy.ide.client.elements.connectors.jira;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.connectors.AbstractConnector;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.Inline;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NamespacedPropertyEditor;

/**
 * The Class describes UpdateFilterById connector for jira group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class UpdateFilterById extends AbstractConnector {

    public static final String ELEMENT_NAME       = "UpdateFilterById";
    public static final String SERIALIZATION_NAME = "jira.updateFilterById";

    private static final String FILTER_ID = "filterId";

    private static final List<String> PROPERTIES = Arrays.asList(FILTER_ID);

    private String          filterId;
    private String          filterIdExpression;
    private List<NameSpace> filterIdNS;

    @Inject
    public UpdateFilterById(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.jiraIcon(),
              branchProvider,
              elementCreatorsManager);

        filterId = "";
        filterIdExpression = "";

        filterIdNS = Collections.emptyList();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(FILTER_ID, isInline ? filterId : filterIdExpression);

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();
        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case FILTER_ID:
                if (isInline) {
                    filterId = nodeValue;
                } else {
                    filterIdExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;
        }
    }

    @Nonnull
    public String getFilterId() {
        return filterId;
    }

    public void setFilterId(@Nonnull String filterId) {
        this.filterId = filterId;
    }

    @Nonnull
    public String getFilterIdExpression() {
        return filterIdExpression;
    }

    public void setFilterIdExpression(@Nonnull String filterIdExpression) {
        this.filterIdExpression = filterIdExpression;
    }

    @Nonnull
    public List<NameSpace> getFilterIdNS() {
        return filterIdNS;
    }

    public void setFilterIdNS(@Nonnull List<NameSpace> filterIdNS) {
        this.filterIdNS = filterIdNS;
    }
}
