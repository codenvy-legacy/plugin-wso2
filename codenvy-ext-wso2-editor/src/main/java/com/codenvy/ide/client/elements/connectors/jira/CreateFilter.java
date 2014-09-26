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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.INLINE;

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

    public static final Key<String> FILTER_NAME_INL = new Key<>("filterNameInl");
    public static final Key<String> JQL_TYPE_INL    = new Key<>("jqlTypeInl");
    public static final Key<String> DESCRIPTION_INL = new Key<>("descriptionInl");
    public static final Key<String> FAVOURITE_INL   = new Key<>("favouriteInl");

    public static final Key<String> FILTER_NAME_EXPR = new Key<>("filterNameExpr");
    public static final Key<String> JQL_TYPE_EXPR    = new Key<>("jqlTypeExpr");
    public static final Key<String> DESCRIPTION_EXPR = new Key<>("descriptionExpr");
    public static final Key<String> FAVOURITE_EXPR   = new Key<>("favouriteExpr");

    public static final Key<List<NameSpace>> FILTER_NAME_NS = new Key<>("filterNameNameSpace");
    public static final Key<List<NameSpace>> JQL_TYPE_NS    = new Key<>("jqlTypeNameSpace");
    public static final Key<List<NameSpace>> DESCRIPTION_NS = new Key<>("descriptionNameSpace");
    public static final Key<List<NameSpace>> FAVOURITE_NS   = new Key<>("favouriteNameSpace");

    private static final String FILTER_NAME = "filterName";
    private static final String JQL_TYPE    = "jqlType";
    private static final String DESCRIPTION = "description";
    private static final String FAVOURITE   = "favourite";

    private static final List<String> PROPERTIES = Arrays.asList(FILTER_NAME, JQL_TYPE, DESCRIPTION, FAVOURITE);

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

        putProperty(FILTER_NAME_INL, "");
        putProperty(JQL_TYPE_INL, "");
        putProperty(DESCRIPTION_INL, "");
        putProperty(FAVOURITE_INL, "");

        putProperty(FILTER_NAME_EXPR, "");
        putProperty(JQL_TYPE_EXPR, "");
        putProperty(DESCRIPTION_EXPR, "");
        putProperty(FAVOURITE_EXPR, "");

        putProperty(FILTER_NAME_NS, new ArrayList<NameSpace>());
        putProperty(JQL_TYPE_NS, new ArrayList<NameSpace>());
        putProperty(DESCRIPTION_NS, new ArrayList<NameSpace>());
        putProperty(FAVOURITE_NS, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE));

        properties.put(FILTER_NAME, isInline ? getProperty(FILTER_NAME_INL) : getProperty(FILTER_NAME_EXPR));
        properties.put(JQL_TYPE, isInline ? getProperty(JQL_TYPE_INL) : getProperty(JQL_TYPE_EXPR));
        properties.put(DESCRIPTION, isInline ? getProperty(DESCRIPTION_INL) : getProperty(DESCRIPTION_EXPR));
        properties.put(FAVOURITE, isInline ? getProperty(FAVOURITE_INL) : getProperty(FAVOURITE_EXPR));

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        switch (node.getNodeName()) {
            case FILTER_NAME:
                adaptProperty(nodeValue, FILTER_NAME_INL, FILTER_NAME_EXPR);
                break;

            case JQL_TYPE:
                adaptProperty(nodeValue, JQL_TYPE_INL, JQL_TYPE_EXPR);
                break;

            case DESCRIPTION:
                adaptProperty(nodeValue, DESCRIPTION_INL, DESCRIPTION_EXPR);
                break;

            case FAVOURITE:
                adaptProperty(nodeValue, FAVOURITE_INL, FAVOURITE_EXPR);
                break;

            default:
        }
    }
}