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

    public static final Key<String>          FILTER_ID_INL  = new Key<>("filterIdInl");
    public static final Key<String>          FILTER_ID_EXPR = new Key<>("filterIdExpr");
    public static final Key<List<NameSpace>> FILTER_ID_NS   = new Key<>("filterIdNameSpace");

    private static final String FILTER_ID = "filterId";

    private static final List<String> PROPERTIES = Arrays.asList(FILTER_ID);

    @Inject
    public UpdateFilterById(EditorResources resources,
                            Provider<Branch> branchProvider,
                            ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              resources.jiraIcon(),
              branchProvider,
              elementCreatorsManager);

        putProperty(FILTER_ID_INL, "");
        putProperty(FILTER_ID_EXPR, "");
        putProperty(FILTER_ID_NS, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE));

        properties.put(FILTER_ID, isInline ? getProperty(FILTER_ID_INL) : getProperty(FILTER_ID_EXPR));

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        if (FILTER_ID.equals(node.getNodeName())) {
            adaptProperty(nodeValue, FILTER_ID_INL, FILTER_ID_EXPR);
        }
    }
}
