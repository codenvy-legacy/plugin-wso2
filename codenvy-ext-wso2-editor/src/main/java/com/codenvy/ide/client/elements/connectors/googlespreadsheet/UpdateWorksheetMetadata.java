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
 * The Class describes UpdateWorksheetMetadata connector for GoogleSpreadsheet group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 */
public class UpdateWorksheetMetadata extends AbstractConnector {

    public static final String ELEMENT_NAME       = "UpdateWorksheetMetadata";
    public static final String SERIALIZATION_NAME = "googlespreadsheet.updateWorksheetMetadata";

    private static final String SPREADSHEET_NAME   = "spreadsheetName";
    private static final String WORKSHEET_OLD_NAME = "worksheetOldName";
    private static final String WORKSHEET_NEW_NAME = "worksheetNewName";
    private static final String WORKSHEET_ROWS     = "worksheetRows";
    private static final String WORKSHEET_COLUMNS  = "worksheetColumns";

    private static final List<String> PROPERTIES = Arrays.asList(SPREADSHEET_NAME, WORKSHEET_OLD_NAME, WORKSHEET_ROWS, WORKSHEET_COLUMNS);

    private String spreadsheetName;
    private String worksheetOldName;
    private String worksheetNewName;
    private String worksheetRows;
    private String worksheetColumns;

    private String spreadsheetNameExpression;
    private String worksheetOldNameExpression;
    private String worksheetNewNameExpression;
    private String worksheetRowsExpression;
    private String worksheetColumnsExpression;

    private Array<NameSpace> spreadsheetNameNS;
    private Array<NameSpace> worksheetOldNameNS;
    private Array<NameSpace> worksheetNewNameNS;
    private Array<NameSpace> worksheetRowsNS;
    private Array<NameSpace> worksheetColumnsNS;

    @Inject
    public UpdateWorksheetMetadata(EditorResources resources,
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
        worksheetOldName = "";
        worksheetOldName = "";
        worksheetOldName = "";
        worksheetOldName = "";

        spreadsheetNameExpression = "";
        worksheetOldNameExpression = "";
        worksheetOldNameExpression = "";
        worksheetOldNameExpression = "";
        worksheetOldNameExpression = "";

        spreadsheetNameNS = createArray();
        worksheetOldNameNS = createArray();
        worksheetOldNameNS = createArray();
        worksheetOldNameNS = createArray();
        worksheetOldNameNS = createArray();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(SPREADSHEET_NAME, isInline ? spreadsheetName : spreadsheetNameExpression);
        properties.put(WORKSHEET_OLD_NAME, isInline ? worksheetOldName : worksheetOldNameExpression);
        properties.put(WORKSHEET_NEW_NAME, isInline ? worksheetNewName : worksheetNewNameExpression);
        properties.put(WORKSHEET_ROWS, isInline ? worksheetRows : worksheetRowsExpression);
        properties.put(WORKSHEET_COLUMNS, isInline ? worksheetColumns : worksheetColumnsExpression);

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

            case WORKSHEET_OLD_NAME:
                if (isInline) {
                    worksheetOldName = nodeValue;
                } else {
                    worksheetOldNameExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case WORKSHEET_NEW_NAME:
                if (isInline) {
                    worksheetNewName = nodeValue;
                } else {
                    worksheetNewNameExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case WORKSHEET_ROWS:
                if (isInline) {
                    worksheetRows = nodeValue;
                } else {
                    worksheetRowsExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case WORKSHEET_COLUMNS:
                if (isInline) {
                    worksheetColumns = nodeValue;
                } else {
                    worksheetColumnsExpression = nodeValue;

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
    public String getWorksheetOldName() {
        return worksheetOldName;
    }

    public void setWorksheetOldName(@Nonnull String worksheetName) {
        this.worksheetOldName = worksheetName;
    }

    @Nonnull
    public String getWorksheetOldNameExpression() {
        return worksheetOldNameExpression;
    }

    public void setWorksheetOldNameExpression(@Nonnull String worksheetNameExpression) {
        this.worksheetOldNameExpression = worksheetNameExpression;
    }

    @Nonnull
    public Array<NameSpace> getWorksheetOldNameNS() {
        return worksheetOldNameNS;
    }

    public void setWorksheetOldNameNS(@Nonnull Array<NameSpace> worksheetNameNS) {
        this.worksheetOldNameNS = worksheetNameNS;
    }

    @Nonnull
    public String getWorksheetNewName() {
        return worksheetNewName;
    }

    public void setWorksheetNewName(@Nonnull String worksheetNewName) {
        this.worksheetNewName = worksheetNewName;
    }

    @Nonnull
    public String getWorksheetRows() {
        return worksheetRows;
    }

    public void setWorksheetRows(@Nonnull String worksheetRows) {
        this.worksheetRows = worksheetRows;
    }

    @Nonnull
    public String getWorksheetColumns() {
        return worksheetColumns;
    }

    public void setWorksheetColumns(@Nonnull String worksheetColumns) {
        this.worksheetColumns = worksheetColumns;
    }

    @Nonnull
    public String getWorksheetNewNameExpression() {
        return worksheetNewNameExpression;
    }

    public void setWorksheetNewNameExpression(@Nonnull String worksheetNewNameExpression) {
        this.worksheetNewNameExpression = worksheetNewNameExpression;
    }

    @Nonnull
    public String getWorksheetRowsExpression() {
        return worksheetRowsExpression;
    }

    public void setWorksheetRowsExpression(@Nonnull String worksheetRowsExpression) {
        this.worksheetRowsExpression = worksheetRowsExpression;
    }

    @Nonnull
    public String getWorksheetColumnsExpression() {
        return worksheetColumnsExpression;
    }

    public void setWorksheetColumnsExpression(@Nonnull String worksheetColumnsExpression) {
        this.worksheetColumnsExpression = worksheetColumnsExpression;
    }

    @Nonnull
    public Array<NameSpace> getWorksheetNewNameNS() {
        return worksheetNewNameNS;
    }

    public void setWorksheetNewNameNS(@Nonnull Array<NameSpace> worksheetNewNameNS) {
        this.worksheetNewNameNS = worksheetNewNameNS;
    }

    @Nonnull
    public Array<NameSpace> getWorksheetRowsNS() {
        return worksheetRowsNS;
    }

    public void setWorksheetRowsNS(@Nonnull Array<NameSpace> worksheetRowsNS) {
        this.worksheetRowsNS = worksheetRowsNS;
    }

    @Nonnull
    public Array<NameSpace> getWorksheetColumnsNS() {
        return worksheetColumnsNS;
    }

    public void setWorksheetColumnsNS(@Nonnull Array<NameSpace> worksheetColumnsNS) {
        this.worksheetColumnsNS = worksheetColumnsNS;
    }

}