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
import com.codenvy.ide.collections.Array;
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
import static com.codenvy.ide.collections.Collections.createArray;

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

    private String spreadsheetName;
    private String worksheetName;
    private String minRow;
    private String maxRow;
    private String minColumn;
    private String maxColumn;

    private String spreadsheetNameExpression;
    private String worksheetNameExpression;
    private String minRowExpression;
    private String maxRowExpression;
    private String minColumnExpression;
    private String maxColumnExpression;

    private Array<NameSpace> spreadsheetNameNS;
    private Array<NameSpace> worksheetNameNS;
    private Array<NameSpace> minRowNS;
    private Array<NameSpace> maxRowNS;
    private Array<NameSpace> minColumnNS;
    private Array<NameSpace> maxColumnNS;

    @Inject
    public GetCellRangeCSV(EditorResources resources,
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

        spreadsheetName = "";
        worksheetName = "";
        minRow = "";
        maxRow = "";
        minColumn = "";
        maxColumn = "";

        spreadsheetNameExpression = "";
        worksheetNameExpression = "";
        minRowExpression = "";
        maxRowExpression = "";
        minColumnExpression = "";
        maxColumnExpression = "";

        spreadsheetNameNS = createArray();
        worksheetNameNS = createArray();
        minRowNS = createArray();
        maxRowNS = createArray();
        minColumnNS = createArray();
        maxColumnNS = createArray();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(SPREADSHEET_NAME, isInline ? spreadsheetName : spreadsheetNameExpression);
        properties.put(WORKSHEET_NAME, isInline ? worksheetName : worksheetNameExpression);
        properties.put(MIN_ROW, isInline ? minRow : minRowExpression);
        properties.put(MAX_ROW, isInline ? maxRow : maxRowExpression);
        properties.put(MIN_COLUMN, isInline ? minColumn : minColumnExpression);
        properties.put(MAX_COLUMN, isInline ? maxColumn : maxColumnExpression);

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();
        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case SPREADSHEET_NAME:
                if (isInline) {
                    spreadsheetName = nodeValue;
                } else {
                    spreadsheetNameExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case WORKSHEET_NAME:
                if (isInline) {
                    worksheetName = nodeValue;
                } else {
                    worksheetNameExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case MIN_ROW:
                if (isInline) {
                    minRow = nodeValue;
                } else {
                    minRowExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case MAX_ROW:
                if (isInline) {
                    maxRow = nodeValue;
                } else {
                    maxRowExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case MIN_COLUMN:
                if (isInline) {
                    minColumn = nodeValue;
                } else {
                    minColumnExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case MAX_COLUMN:
                if (isInline) {
                    maxColumn = nodeValue;
                } else {
                    maxColumnExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;
        }
    }

    @Nonnull
    public String getSpreadsheetName() {
        return spreadsheetName;
    }

    public void setSpreadsheetName(@Nonnull String spreadsheetName) {
        this.spreadsheetName = spreadsheetName;
    }

    @Nonnull
    public String getSpreadsheetNameExpression() {
        return spreadsheetNameExpression;
    }

    public void setSpreadsheetNameExpression(@Nonnull String spreadsheetNameExpression) {
        this.spreadsheetNameExpression = spreadsheetNameExpression;
    }

    @Nonnull
    public Array<NameSpace> getSpreadsheetNameNS() {
        return spreadsheetNameNS;
    }

    public void setSpreadsheetNameNS(@Nonnull Array<NameSpace> spreadsheetNameNS) {
        this.spreadsheetNameNS = spreadsheetNameNS;
    }

    @Nonnull
    public String getWorksheetName() {
        return worksheetName;
    }

    public void setWorksheetName(@Nonnull String worksheetName) {
        this.worksheetName = worksheetName;
    }

    @Nonnull
    public String getWorksheetNameExpression() {
        return worksheetNameExpression;
    }

    public void setWorksheetNameExpression(@Nonnull String worksheetNameExpression) {
        this.worksheetNameExpression = worksheetNameExpression;
    }

    @Nonnull
    public Array<NameSpace> getWorksheetCountNS() {
        return worksheetNameNS;
    }

    public void setWorksheetNameNS(@Nonnull Array<NameSpace> worksheetNameNS) {
        this.worksheetNameNS = worksheetNameNS;
    }

    @Nonnull
    public String getMinRow() {
        return minRow;
    }

    public void setMinRow(@Nonnull String minRow) {
        this.minRow = minRow;
    }

    @Nonnull
    public String getMaxRow() {
        return maxRow;
    }

    public void setMaxRow(@Nonnull String maxRow) {
        this.maxRow = maxRow;
    }

    @Nonnull
    public String getMinColumn() {
        return minColumn;
    }

    public void setMinColumn(@Nonnull String minColumn) {
        this.minColumn = minColumn;
    }

    @Nonnull
    public String getMaxColumn() {
        return maxColumn;
    }

    public void setMaxColumn(@Nonnull String maxColumn) {
        this.maxColumn = maxColumn;
    }

    @Nonnull
    public String getMinRowExpression() {
        return minRowExpression;
    }

    public void setMinRowExpression(@Nonnull String minRowExpression) {
        this.minRowExpression = minRowExpression;
    }

    @Nonnull
    public String getMaxRowExpression() {
        return maxRowExpression;
    }

    public void setMaxRowExpression(@Nonnull String maxRowExpression) {
        this.maxRowExpression = maxRowExpression;
    }

    @Nonnull
    public String getMinColumnExpression() {
        return minColumnExpression;
    }

    public void setMinColumnExpression(@Nonnull String minColumnExpression) {
        this.minColumnExpression = minColumnExpression;
    }

    @Nonnull
    public String getMaxColumnExpression() {
        return maxColumnExpression;
    }

    public void setMaxColumnExpression(@Nonnull String maxColumnExpression) {
        this.maxColumnExpression = maxColumnExpression;
    }

    @Nonnull
    public Array<NameSpace> getMinRowNS() {
        return minRowNS;
    }

    public void setMinRowNS(@Nonnull Array<NameSpace> minRowNS) {
        this.minRowNS = minRowNS;
    }

    @Nonnull
    public Array<NameSpace> getMaxRowNS() {
        return maxRowNS;
    }

    public void setMaxRowNS(@Nonnull Array<NameSpace> maxRowNS) {
        this.maxRowNS = maxRowNS;
    }

    @Nonnull
    public Array<NameSpace> getMinColumnNS() {
        return minColumnNS;
    }

    public void setMinColumnNS(@Nonnull Array<NameSpace> minColumnNS) {
        this.minColumnNS = minColumnNS;
    }

    @Nonnull
    public Array<NameSpace> getMaxColumnNS() {
        return maxColumnNS;
    }

    public void setMaxColumnNS(@Nonnull Array<NameSpace> maxColumnNS) {
        this.maxColumnNS = maxColumnNS;
    }

}