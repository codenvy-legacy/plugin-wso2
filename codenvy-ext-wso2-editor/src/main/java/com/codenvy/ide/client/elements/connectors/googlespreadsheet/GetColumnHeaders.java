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
package com.codenvy.ide.client.elements.connectors.googlespreadsheet;

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
 * The Class describes GetColumnHeaders connector for GoogleSpreadsheet group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 */
public class GetColumnHeaders extends AbstractConnector {

    public static final String ELEMENT_NAME       = "GetColumnHeaders";
    public static final String SERIALIZATION_NAME = "googlespreadsheet.getColumnHeaders";

    public static final Key<String>          SPREADSHEET_NAME_KEY            = new Key<>("SpreadsheetName");
    public static final Key<String>          WORKSHEET_NAME_KEY              = new Key<>("WorksheetName");
    public static final Key<String>          SPREADSHEET_NAME_EXPRESSION_KEY = new Key<>("SpreadsheetNameExpression");
    public static final Key<String>          WORKSHEET_NAME_EXPRESSION_KEY   = new Key<>("WorksheetNameExpression");
    public static final Key<List<NameSpace>> SPREADSHEET_NAME_NS_KEY         = new Key<>("SpreadsheetNameNS");
    public static final Key<List<NameSpace>> WORKSHEET_NAME_NS_KEY           = new Key<>("WorksheetNameNS");

    private static final String SPREADSHEET_NAME = "spreadsheetName";
    private static final String WORKSHEET_NAME   = "worksheetName";

    private static final List<String> PROPERTIES = Arrays.asList(SPREADSHEET_NAME, WORKSHEET_NAME);

    @Inject
    public GetColumnHeaders(EditorResources resources,
                            Provider<Branch> branchProvider,
                            ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.googleSpreadsheetElement(),
              branchProvider,
              elementCreatorsManager);

        putProperty(SPREADSHEET_NAME_KEY, "");
        putProperty(WORKSHEET_NAME_KEY, "");

        putProperty(SPREADSHEET_NAME_EXPRESSION_KEY, "");
        putProperty(WORKSHEET_NAME_EXPRESSION_KEY, "");

        putProperty(SPREADSHEET_NAME_NS_KEY, new ArrayList<NameSpace>());
        putProperty(WORKSHEET_NAME_NS_KEY, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE));

        properties.put(SPREADSHEET_NAME, isInline ? getProperty(SPREADSHEET_NAME_KEY) : getProperty(SPREADSHEET_NAME_EXPRESSION_KEY));
        properties.put(WORKSHEET_NAME, isInline ? getProperty(WORKSHEET_NAME_KEY) : getProperty(WORKSHEET_NAME_EXPRESSION_KEY));

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        if (SPREADSHEET_NAME.equals(nodeName)) {
            adaptProperty(nodeValue, SPREADSHEET_NAME_KEY, SPREADSHEET_NAME_EXPRESSION_KEY);
        }

        if (WORKSHEET_NAME.equals(nodeName)) {
            adaptProperty(nodeValue, WORKSHEET_NAME_KEY, WORKSHEET_NAME_EXPRESSION_KEY);
        }
    }

}