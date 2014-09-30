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
package com.codenvy.ide.client.propertiespanel.connectors.googlespreadsheet;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetCellRange;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GoogleSpreadsheetPropertyManager;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.PropertyPanelFactory;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.base.AbstractConnectorPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.base.parameter.ParameterPresenter;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.PARAMETER_EDITOR_TYPE;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NAME_SPACED_PROPERTY_EDITOR;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetCellRange.MAX_COLUMN_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetCellRange.MAX_COLUMN_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetCellRange.MAX_COLUMN_NS_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetCellRange.MAX_ROW_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetCellRange.MAX_ROW_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetCellRange.MAX_ROW_NS_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetCellRange.MIN_COLUMN_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetCellRange.MIN_COLUMN_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetCellRange.MIN_COLUMN_NS_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetCellRange.MIN_ROW_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetCellRange.MIN_ROW_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetCellRange.MIN_ROW_NS_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetCellRange.SPREADSHEET_NAME_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetCellRange.SPREADSHEET_NAME_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetCellRange.SPREADSHEET_NAME_NS_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetCellRange.WORKSHEET_NAME_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetCellRange.WORKSHEET_NAME_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetCellRange.WORKSHEET_NAME_NS_KEY;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class GetCellRangeConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<GetCellRange> {
    private ComplexPropertyPresenter spreadsheetNameNS;
    private ComplexPropertyPresenter worksheetNameNS;
    private ComplexPropertyPresenter minRowNS;
    private ComplexPropertyPresenter maxRowNS;
    private ComplexPropertyPresenter minColumnNS;
    private ComplexPropertyPresenter maxColumnNS;

    private SimplePropertyPresenter spreadsheetName;
    private SimplePropertyPresenter worksheetName;
    private SimplePropertyPresenter minRows;
    private SimplePropertyPresenter maxRows;
    private SimplePropertyPresenter minColumn;
    private SimplePropertyPresenter maxColumn;

    @Inject
    public GetCellRangeConnectorPresenter(WSO2EditorLocalizationConstant locale,
                                          NameSpaceEditorPresenter nameSpacePresenter,
                                          PropertiesPanelView view,
                                          GoogleSpreadsheetPropertyManager googleSpreadsheetPropertyManager,
                                          ParameterPresenter parameterPresenter,
                                          PropertyTypeManager propertyTypeManager,
                                          PropertyPanelFactory propertyPanelFactory) {
        super(view,
              googleSpreadsheetPropertyManager,
              parameterPresenter,
              nameSpacePresenter,
              propertyTypeManager,
              locale,
              propertyPanelFactory);

        prepareView();
    }

    private void prepareView() {
        spreadsheetNameNS = createComplexConnectorProperty(locale.spreadsheetCreateSpreadsheetSpreadsheetName(),
                                                           SPREADSHEET_NAME_NS_KEY,
                                                           SPREADSHEET_NAME_EXPRESSION_KEY);
        worksheetNameNS = createComplexConnectorProperty(locale.spreadsheetCreateWorksheetWorksheetName(),
                                                         WORKSHEET_NAME_NS_KEY,
                                                         WORKSHEET_NAME_EXPRESSION_KEY);
        minRowNS = createComplexConnectorProperty(locale.spreadsheetGetCellRangeMinRow(), MIN_ROW_NS_KEY, MIN_ROW_EXPRESSION_KEY);
        maxRowNS = createComplexConnectorProperty(locale.spreadsheetGetCellRangeMaxRow(), MAX_ROW_NS_KEY, MAX_ROW_EXPRESSION_KEY);
        minColumnNS = createComplexConnectorProperty(locale.spreadsheetGetCellRangeMinColumn(), MIN_COLUMN_NS_KEY,
                                                     MIN_COLUMN_EXPRESSION_KEY);
        maxColumnNS = createComplexConnectorProperty(locale.spreadsheetGetCellRangeMaxColumn(), MAX_COLUMN_NS_KEY,
                                                     MAX_COLUMN_EXPRESSION_KEY);

        spreadsheetName = createSimpleConnectorProperty(locale.spreadsheetCreateSpreadsheetSpreadsheetName(), SPREADSHEET_NAME_KEY);
        worksheetName = createSimpleConnectorProperty(locale.spreadsheetCreateWorksheetWorksheetName(), WORKSHEET_NAME_KEY);
        minRows = createSimpleConnectorProperty(locale.spreadsheetGetCellRangeMinRow(), MIN_ROW_KEY);
        maxRows = createSimpleConnectorProperty(locale.spreadsheetGetCellRangeMaxRow(), MAX_ROW_KEY);
        minColumn = createSimpleConnectorProperty(locale.spreadsheetGetCellRangeMinColumn(), MIN_COLUMN_KEY);
        maxColumn = createSimpleConnectorProperty(locale.spreadsheetGetCellRangeMaxColumn(), MAX_COLUMN_KEY);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        ParameterEditorType property = element.getProperty(PARAMETER_EDITOR_TYPE);
        boolean isNameSpaced = NAME_SPACED_PROPERTY_EDITOR.equals(property);

        spreadsheetName.setVisible(!isNameSpaced);
        worksheetName.setVisible(!isNameSpaced);
        minRows.setVisible(!isNameSpaced);
        maxRows.setVisible(!isNameSpaced);
        minColumn.setVisible(!isNameSpaced);
        maxColumn.setVisible(!isNameSpaced);

        spreadsheetNameNS.setVisible(isNameSpaced);
        worksheetNameNS.setVisible(isNameSpaced);
        minColumnNS.setVisible(isNameSpaced);
        maxColumnNS.setVisible(isNameSpaced);
        minRowNS.setVisible(isNameSpaced);
        maxRowNS.setVisible(isNameSpaced);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        spreadsheetName.setProperty(element.getProperty(SPREADSHEET_NAME_KEY));
        worksheetName.setProperty(element.getProperty(WORKSHEET_NAME_KEY));
        minRows.setProperty(element.getProperty(MIN_ROW_KEY));
        maxRows.setProperty(element.getProperty(MAX_ROW_KEY));
        minColumn.setProperty(element.getProperty(MIN_COLUMN_KEY));
        maxColumn.setProperty(element.getProperty(MAX_COLUMN_KEY));

        spreadsheetNameNS.setProperty(element.getProperty(SPREADSHEET_NAME_EXPRESSION_KEY));
        worksheetNameNS.setProperty(element.getProperty(WORKSHEET_NAME_EXPRESSION_KEY));
        minRowNS.setProperty(element.getProperty(MIN_ROW_EXPRESSION_KEY));
        maxRowNS.setProperty(element.getProperty(MAX_ROW_EXPRESSION_KEY));
        minColumnNS.setProperty(element.getProperty(MIN_COLUMN_EXPRESSION_KEY));
        maxColumnNS.setProperty(element.getProperty(MAX_COLUMN_EXPRESSION_KEY));
    }
}
