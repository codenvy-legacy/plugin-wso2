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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.Inline;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NamespacedPropertyEditor;
import static java.util.Collections.emptyList;

/**
 * The Class describes CreateFilter connector for jira group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class CreateFilter extends AbstractConnector {

    public static final String ELEMENT_NAME       = "CreateFilter";
    public static final String SERIALIZATION_NAME = "jira.createFilter";

    private static final String FILTER_NAME = "filterName";
    private static final String JQL_TYPE    = "jqlType";
    private static final String DESCRIPTION = "description";
    private static final String FAVOURITE   = "favourite";

    private static final List<String> PROPERTIES = Arrays.asList(FILTER_NAME, JQL_TYPE, DESCRIPTION, FAVOURITE);

    private String filterName;
    private String jqlType;
    private String description;
    private String favourite;

    private String filterNameExpression;
    private String jqlTypeExpression;
    private String descriptionExpression;
    private String favouriteExpression;

    private List<NameSpace> filterNameNS;
    private List<NameSpace> jqlTypeNS;
    private List<NameSpace> descriptionNS;
    private List<NameSpace> favouriteNS;

    @Inject
    public CreateFilter(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.jiraIcon(),
              branchProvider,
              elementCreatorsManager);

        filterName = "";
        jqlType = "";
        description = "";
        favourite = "";

        filterNameExpression = "";
        jqlTypeExpression = "";
        favouriteExpression = "";
        descriptionExpression = "";

        favouriteNS = emptyList();
        filterNameNS = emptyList();
        descriptionNS = emptyList();
        jqlTypeNS = emptyList();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(FILTER_NAME, isInline ? filterName : filterNameExpression);
        properties.put(JQL_TYPE, isInline ? jqlType : jqlTypeExpression);
        properties.put(DESCRIPTION, isInline ? description : descriptionExpression);
        properties.put(FAVOURITE, isInline ? favourite : favouriteExpression);

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();
        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case FILTER_NAME:
                if (isInline) {
                    filterName = nodeValue;
                } else {
                    filterNameExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case JQL_TYPE:
                if (isInline) {
                    jqlType = nodeValue;
                } else {
                    jqlTypeExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case DESCRIPTION:
                if (isInline) {
                    description = nodeValue;
                } else {
                    descriptionExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case FAVOURITE:
                if (isInline) {
                    favourite = nodeValue;
                } else {
                    favouriteExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;
        }
    }

    @Nonnull
    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(@Nonnull String filterName) {
        this.filterName = filterName;
    }

    @Nonnull
    public String getJqlType() {
        return jqlType;
    }

    public void setJqlType(@Nonnull String jqlType) {
        this.jqlType = jqlType;
    }

    @Nonnull
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nonnull String description) {
        this.description = description;
    }

    @Nonnull
    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(@Nonnull String favourite) {
        this.favourite = favourite;
    }

    @Nonnull
    public String getFilterNameExpression() {
        return filterNameExpression;
    }

    public void setFilterNameExpression(@Nonnull String filterNameExpression) {
        this.filterNameExpression = filterNameExpression;
    }

    @Nonnull
    public String getJqlTypeExpression() {
        return jqlTypeExpression;
    }

    public void setJqlTypeExpression(@Nonnull String jqlTypeExpression) {
        this.jqlTypeExpression = jqlTypeExpression;
    }

    @Nonnull
    public String getDescriptionExpression() {
        return descriptionExpression;
    }

    public void setDescriptionExpression(@Nonnull String descriptionExpression) {
        this.descriptionExpression = descriptionExpression;
    }

    @Nonnull
    public String getFavouriteExpression() {
        return favouriteExpression;
    }

    public void setFavouriteExpression(@Nonnull String favouriteExpression) {
        this.favouriteExpression = favouriteExpression;
    }

    @Nonnull
    public List<NameSpace> getFilterNameNS() {
        return filterNameNS;
    }

    public void setFilterNameNS(@Nonnull List<NameSpace> filterNameNS) {
        this.filterNameNS = filterNameNS;
    }

    @Nonnull
    public List<NameSpace> getJqlTypeNS() {
        return jqlTypeNS;
    }

    public void setJqlTypeNS(@Nonnull List<NameSpace> jqlTypeNS) {
        this.jqlTypeNS = jqlTypeNS;
    }

    @Nonnull
    public List<NameSpace> getDescriptionNS() {
        return descriptionNS;
    }

    public void setDescriptionNS(@Nonnull List<NameSpace> descriptionNS) {
        this.descriptionNS = descriptionNS;
    }

    @Nonnull
    public List<NameSpace> getFavouriteNS() {
        return favouriteNS;
    }

    public void setFavouriteNS(@Nonnull List<NameSpace> favouriteNS) {
        this.favouriteNS = favouriteNS;
    }
}