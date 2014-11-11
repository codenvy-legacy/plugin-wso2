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
 * The Class describes GetCellRangeCSV connector for GoogleSpreadsheet group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 */
public class GetCellRangeCSV extends AbstractConnector {

    public static final String ELEMENT_NAME       = "GetCellRangeCSV";
    public static final String SERIALIZATION_NAME = "googlespreadsheet.getCellRangeCSV";

    public static final Key<String> SPREADSHEET_NAME_KEY = new Key<>("SpreadsheetName");
    public static final Key<String> WORKSHEET_NAME_KEY   = new Key<>("WorksheetName");
    public static final Key<String> MIN_ROW_KEY          = new Key<>("MinRow");
    public static final Key<String> MAX_ROW_KEY          = new Key<>("MaxRow");
    public static final Key<String> MIN_COLUMN_KEY       = new Key<>("MinColumn");
    public static final Key<String> MAX_COLUMN_KEY       = new Key<>("MaxColumn");

    public static final Key<String> SPREADSHEET_NAME_EXPRESSION_KEY = new Key<>("SpreadsheetNameExpression");
    public static final Key<String> WORKSHEET_NAME_EXPRESSION_KEY   = new Key<>("WorksheetNameExpression");
    public static final Key<String> MIN_ROW_EXPRESSION_KEY          = new Key<>("MinRowExpression");
    public static final Key<String> MAX_ROW_EXPRESSION_KEY          = new Key<>("MaxRowExpression");
    public static final Key<String> MIN_COLUMN_EXPRESSION_KEY       = new Key<>("MinColumnExpression");
    public static final Key<String> MAX_COLUMN_EXPRESSION_KEY       = new Key<>("MaxColumnExpression");

    public static final Key<List<NameSpace>> SPREADSHEET_NAME_NS_KEY = new Key<>("SpreadsheetNameNS");
    public static final Key<List<NameSpace>> WORKSHEET_NAME_NS_KEY   = new Key<>("WorksheetNameNS");
    public static final Key<List<NameSpace>> MIN_ROW_NS_KEY          = new Key<>("MinRowNS");
    public static final Key<List<NameSpace>> MAX_ROW_NS_KEY          = new Key<>("MaxRowNS");
    public static final Key<List<NameSpace>> MIN_COLUMN_NS_KEY       = new Key<>("MinColumnNS");
    public static final Key<List<NameSpace>> MAX_COLUMN_NS_KEY       = new Key<>("MaxColumnNS");

    private static final String SPREADSHEET_NAME = "spreadsheetName";
    private static final String WORKSHEET_NAME   = "worksheetName";
    private static final String MIN_ROW          = "minRow";
    private static final String MAX_ROW          = "maxRow";
    private static final String MIN_COLUMN       = "minColumn";
    private static final String MAX_COLUMN       = "maxColumn";

    private static final List<String> PROPERTIES = Arrays.asList(SPREADSHEET_NAME,
                                                                 WORKSHEET_NAME,
                                                                 MIN_ROW,
                                                                 MAX_ROW,
                                                                 MIN_COLUMN,
                                                                 MAX_COLUMN);

    @Inject
    public GetCellRangeCSV(EditorResources resources,
                           Provider<Branch> branchProvider,
                           ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              resources.googleSpreadsheetElement(),
              branchProvider,
              elementCreatorsManager);

        putProperty(SPREADSHEET_NAME_KEY, "");
        putProperty(WORKSHEET_NAME_KEY, "");
        putProperty(MIN_ROW_KEY, "");
        putProperty(MAX_ROW_KEY, "");
        putProperty(MIN_COLUMN_KEY, "");
        putProperty(MAX_COLUMN_KEY, "");

        putProperty(SPREADSHEET_NAME_EXPRESSION_KEY, "");
        putProperty(WORKSHEET_NAME_EXPRESSION_KEY, "");
        putProperty(MIN_COLUMN_EXPRESSION_KEY, "");
        putProperty(MAX_COLUMN_EXPRESSION_KEY, "");
        putProperty(MIN_ROW_EXPRESSION_KEY, "");
        putProperty(MAX_ROW_EXPRESSION_KEY, "");

        putProperty(SPREADSHEET_NAME_NS_KEY, new ArrayList<NameSpace>());
        putProperty(WORKSHEET_NAME_NS_KEY, new ArrayList<NameSpace>());
        putProperty(MIN_ROW_NS_KEY, new ArrayList<NameSpace>());
        putProperty(MAX_ROW_NS_KEY, new ArrayList<NameSpace>());
        putProperty(MIN_COLUMN_NS_KEY, new ArrayList<NameSpace>());
        putProperty(MAX_COLUMN_NS_KEY, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE));

        properties.put(SPREADSHEET_NAME, isInline ? getProperty(SPREADSHEET_NAME_KEY) : getProperty(SPREADSHEET_NAME_EXPRESSION_KEY));
        properties.put(WORKSHEET_NAME, isInline ? getProperty(WORKSHEET_NAME_KEY) : getProperty(WORKSHEET_NAME_EXPRESSION_KEY));
        properties.put(MIN_ROW, isInline ? getProperty(MIN_ROW_KEY) : getProperty(MIN_ROW_EXPRESSION_KEY));
        properties.put(MAX_ROW, isInline ? getProperty(MAX_ROW_KEY) : getProperty(MAX_ROW_EXPRESSION_KEY));
        properties.put(MIN_COLUMN, isInline ? getProperty(MIN_COLUMN_KEY) : getProperty(MIN_COLUMN_EXPRESSION_KEY));
        properties.put(MAX_COLUMN, isInline ? getProperty(MAX_COLUMN_KEY) : getProperty(MAX_COLUMN_EXPRESSION_KEY));

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        switch (nodeName) {
            case SPREADSHEET_NAME:
                adaptProperty(nodeValue, SPREADSHEET_NAME_KEY, SPREADSHEET_NAME_EXPRESSION_KEY);
                break;

            case WORKSHEET_NAME:
                adaptProperty(nodeValue, WORKSHEET_NAME_KEY, WORKSHEET_NAME_EXPRESSION_KEY);
                break;

            case MIN_ROW:
                adaptProperty(nodeValue, MIN_ROW_KEY, MIN_ROW_EXPRESSION_KEY);
                break;

            case MAX_ROW:
                adaptProperty(nodeValue, MAX_ROW_KEY, MAX_ROW_EXPRESSION_KEY);
                break;

            case MIN_COLUMN:
                adaptProperty(nodeValue, MIN_COLUMN_KEY, MIN_COLUMN_EXPRESSION_KEY);
                break;

            case MAX_COLUMN:
                adaptProperty(nodeValue, MAX_COLUMN_KEY, MAX_COLUMN_EXPRESSION_KEY);
                break;

            default:
        }
    }

}