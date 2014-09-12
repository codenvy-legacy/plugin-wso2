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
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
import com.codenvy.ide.collections.Array;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.Inline;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NamespacedPropertyEditor;
import static com.codenvy.ide.collections.Collections.createArray;

/**
 * The Class describes SetRow connector for GoogleSpreadsheet group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 */
public class SetRow extends AbstractConnector {

    public static final String ELEMENT_NAME       = "SetRow";
    public static final String SERIALIZATION_NAME = "googlespreadsheet.setRow";

    private static final String SPREADSHEET_NAME = "spreadsheetName";
    private static final String WORKSHEET_NAME   = "worksheetName";
    private static final String ROW_ID           = "rowId";
    private static final String ROW_DATA         = "rowData";

    private static final List<String> PROPERTIES = Arrays.asList(SPREADSHEET_NAME, WORKSHEET_NAME, ROW_ID, ROW_DATA);

    private String spreadsheetName;
    private String worksheetName;
    private String rowId;
    private String rowData;

    private String spreadsheetNameExpression;
    private String worksheetNameExpression;
    private String rowIdExpression;
    private String rowDataExpression;

    private Array<NameSpace> spreadsheetNameNS;
    private Array<NameSpace> worksheetNameNS;
    private Array<NameSpace> rowIdNS;
    private Array<NameSpace> rowDataNS;

    @Inject
    public SetRow(EditorResources resources,
                  Provider<Branch> branchProvider,
                  MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        spreadsheetName = "";
        worksheetName = "";
        rowId = "";
        rowData = "";

        spreadsheetNameExpression = "";
        worksheetNameExpression = "";
        rowIdExpression = "";
        rowDataExpression = "";

        spreadsheetNameNS = createArray();
        worksheetNameNS = createArray();
        rowIdNS = createArray();
        rowDataNS = createArray();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(SPREADSHEET_NAME, isInline ? spreadsheetName : spreadsheetNameExpression);
        properties.put(WORKSHEET_NAME, isInline ? worksheetName : worksheetNameExpression);
        properties.put(ROW_ID, isInline ? rowId : rowIdExpression);
        properties.put(ROW_DATA, isInline ? rowData : rowDataExpression);

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

            case ROW_ID:
                if (isInline) {
                    rowId = nodeValue;
                } else {
                    rowIdExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case ROW_DATA:
                if (isInline) {
                    rowData = nodeValue;
                } else {
                    rowDataExpression = nodeValue;

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
    public Array<NameSpace> getWorksheetNameNS() {
        return worksheetNameNS;
    }

    public void setWorksheetNameNS(@Nonnull Array<NameSpace> worksheetNameNS) {
        this.worksheetNameNS = worksheetNameNS;
    }

    @Nonnull
    public String getRowId() {
        return rowId;
    }

    public void setRowId(@Nonnull String rowId) {
        this.rowId = rowId;
    }

    @Nonnull
    public String getRowData() {
        return rowData;
    }

    public void setRowData(@Nonnull String rowData) {
        this.rowData = rowData;
    }

    @Nonnull
    public String getRowIdExpression() {
        return rowIdExpression;
    }

    public void setRowIdExpression(@Nonnull String rowIdExpression) {
        this.rowIdExpression = rowIdExpression;
    }

    @Nonnull
    public String getRowDataExpression() {
        return rowDataExpression;
    }

    public void setRowDataExpression(@Nonnull String rowDataExpression) {
        this.rowDataExpression = rowDataExpression;
    }

    @Nonnull
    public Array<NameSpace> getRowIdNS() {
        return rowIdNS;
    }

    public void setRowIdNS(@Nonnull Array<NameSpace> rowIdNS) {
        this.rowIdNS = rowIdNS;
    }

    @Nonnull
    public Array<NameSpace> getRowDataNS() {
        return rowDataNS;
    }

    public void setRowDataNS(@Nonnull Array<NameSpace> rowDataNS) {
        this.rowDataNS = rowDataNS;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.googleSpreadsheetElement();
    }
}