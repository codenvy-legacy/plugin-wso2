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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.INLINE;

/**
 * The Class describes Upset connector for Salesforce group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class Upset extends AbstractConnector {

    public static final String ELEMENT_NAME       = "Upset";
    public static final String SERIALIZATION_NAME = "salesforce.upset";

    public static final Key<String> ALL_OR_NONE_KEY          = new Key<>("AllOrNone");
    public static final Key<String> EXTERNAL_ID_KEY          = new Key<>("ExternalId");
    public static final Key<String> ALLOW_FIELD_TRUNCATE_KEY = new Key<>("AllowFieldTruncate");
    public static final Key<String> SUBJECTS_KEY             = new Key<>("Subjects");

    public static final Key<String> ALL_OR_NONE_EXPRESSION_KEY          = new Key<>("AllOrNoneExpression");
    public static final Key<String> EXTERNAL_ID_EXPRESSION_KEY          = new Key<>("ExternalIdExpression");
    public static final Key<String> ALLOW_FIELD_TRUNCATE_EXPRESSION_KEY = new Key<>("AllowFieldTruncateExpression");
    public static final Key<String> SUBJECTS_EXPRESSION_KEY             = new Key<>("SubjectsExpression");

    public static final Key<List<NameSpace>> ALL_OR_NONE_NS_KEY          = new Key<>("AllOrNoneNS");
    public static final Key<List<NameSpace>> EXTERNAL_ID_NS_KEY          = new Key<>("ExternalIdNS");
    public static final Key<List<NameSpace>> ALLOW_FIELD_TRUNCATE_NS_KEY = new Key<>("AllowFieldTruncateNS");
    public static final Key<List<NameSpace>> SUBJECTS_NS_KEY             = new Key<>("SubjectsNS");

    private static final String ALL_OR_NONE = "allOrNone";
    private static final String EXTERNAL_ID = "externalId";
    private static final String TRUNCATE    = "allowFieldTruncate";
    private static final String SUBJECTS    = "sobjects";

    private static final List<String> PROPERTIES = Arrays.asList(ALL_OR_NONE, TRUNCATE, EXTERNAL_ID, SUBJECTS);

    @Inject
    public Upset(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              resources.salesforce(),
              branchProvider,
              elementCreatorsManager);

        putProperty(ALL_OR_NONE_KEY, "");
        putProperty(EXTERNAL_ID_KEY, "");
        putProperty(ALLOW_FIELD_TRUNCATE_KEY, "");
        putProperty(SUBJECTS_KEY, "");

        putProperty(ALL_OR_NONE_EXPRESSION_KEY, "");
        putProperty(EXTERNAL_ID_EXPRESSION_KEY, "");
        putProperty(ALLOW_FIELD_TRUNCATE_EXPRESSION_KEY, "");
        putProperty(SUBJECTS_EXPRESSION_KEY, "");

        putProperty(ALL_OR_NONE_NS_KEY, new ArrayList<NameSpace>());
        putProperty(EXTERNAL_ID_NS_KEY, new ArrayList<NameSpace>());
        putProperty(ALLOW_FIELD_TRUNCATE_NS_KEY, new ArrayList<NameSpace>());
        putProperty(SUBJECTS_NS_KEY, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE));

        properties.put(ALL_OR_NONE, isInline ? getProperty(ALL_OR_NONE_KEY) : getProperty(ALL_OR_NONE_EXPRESSION_KEY));
        properties.put(EXTERNAL_ID, isInline ? getProperty(EXTERNAL_ID_KEY) : getProperty(EXTERNAL_ID_EXPRESSION_KEY));
        properties.put(TRUNCATE, isInline ? getProperty(ALLOW_FIELD_TRUNCATE_KEY) : getProperty(ALLOW_FIELD_TRUNCATE_EXPRESSION_KEY));
        properties.put(SUBJECTS, isInline ? getProperty(SUBJECTS_KEY) : getProperty(SUBJECTS_EXPRESSION_KEY));

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        switch (nodeName) {
            case ALL_OR_NONE:
                adaptProperty(nodeValue, ALL_OR_NONE_KEY, ALL_OR_NONE_EXPRESSION_KEY);
                break;

            case EXTERNAL_ID:
                adaptProperty(nodeValue, EXTERNAL_ID_KEY, EXTERNAL_ID_EXPRESSION_KEY);
                break;

            case TRUNCATE:
                adaptProperty(nodeValue, ALLOW_FIELD_TRUNCATE_KEY, ALLOW_FIELD_TRUNCATE_EXPRESSION_KEY);
                break;

            case SUBJECTS:
                adaptProperty(nodeValue, SUBJECTS_KEY, SUBJECTS_EXPRESSION_KEY);
                break;

            default:
        }
    }

}