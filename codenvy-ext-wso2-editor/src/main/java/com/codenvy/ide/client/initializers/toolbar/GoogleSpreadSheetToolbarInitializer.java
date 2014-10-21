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
package com.codenvy.ide.client.initializers.toolbar;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.constants.GoogleSpreadsheedConnectorCreatingState;
import com.codenvy.ide.client.constants.ToolbarGroupIds;
import com.codenvy.ide.client.toolbar.ToolbarPresenter;
import com.google.inject.Inject;

/**
 * @author Andrey Plotnikov
 */
public class GoogleSpreadSheetToolbarInitializer extends AbstractToolbarInitializer {

    @Inject
    public GoogleSpreadSheetToolbarInitializer(ToolbarPresenter toolbar, EditorResources resources, WSO2EditorLocalizationConstant locale) {
        super(toolbar, resources, locale);
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        toolbar.addGroup(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS, locale.toolbarGroupGoogleSpreadsheetConnector());

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        locale.toolbarSpreadsheetCreateSpreadsheetTitle(),
                        locale.toolbarSpreadsheetCreateSpreadsheetTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.CREATING_SPREADSHEET);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        locale.toolbarSpreadsheetCreateWorksheetTitle(),
                        locale.toolbarSpreadsheetCreateWorksheetTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.CREATING_WORKSHEET);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        locale.toolbarSpreadsheetDeleteWorksheetTitle(),
                        locale.toolbarSpreadsheetDeleteWorksheetTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.DELETE_WORKSHEET);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        locale.toolbarSpreadsheetGetAllCellsTitle(),
                        locale.toolbarSpreadsheetGetAllCellsTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_ALL_CELLS);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        locale.toolbarSpreadsheetGetAllCellsCSVTitle(),
                        locale.toolbarSpreadsheetGetAllCellsCSVTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_ALL_CELLS_CSV);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        locale.toolbarSpreadsheetGetAllSpreadsheetsTitle(),
                        locale.toolbarSpreadsheetGetAllSpreadsheetsTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_ALL_SPREADSHEETS);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        locale.toolbarSpreadsheetGetAllWorksheetsTitle(),
                        locale.toolbarSpreadsheetGetAllWorksheetsTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_ALL_WORKSHEETS);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        locale.toolbarSpreadsheetGetAuthorsTitle(),
                        locale.toolbarSpreadsheetGetAuthorsTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_AUTHORS);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        locale.toolbarSpreadsheetGetCellRangeTitle(),
                        locale.toolbarSpreadsheetGetCellRangeTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_CELL_RANGE);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        locale.toolbarSpreadsheetGetCellRangeCSVTitle(),
                        locale.toolbarSpreadsheetGetCellRangeCSVTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_CELL_RANGE_CSV);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        locale.toolbarSpreadsheetGetColumnHeadersTitle(),
                        locale.toolbarSpreadsheetGetColumnHeadersTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_COLUMN_HEADERS);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        locale.toolbarSpreadsheetGetSpreadsheetByTitleTitle(),
                        locale.toolbarSpreadsheetGetSpreadsheetByTitleTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_SPREADSHEET_BY_TITLE);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        locale.toolbarSpreadsheetGetWorksheetByTitleTitle(),
                        locale.toolbarSpreadsheetGetWorksheetByTitleTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_WORKSHEET_BY_TITLE);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        locale.toolbarSpreadsheetImportCSVTitle(),
                        locale.toolbarSpreadsheetImportCSVTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.IMPORT_CSV);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        locale.toolbarSpreadsheetInitTitle(),
                        locale.toolbarSpreadsheetInitTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.INIT);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        locale.toolbarSpreadsheetPurgeWorksheetTitle(),
                        locale.toolbarSpreadsheetPurgeWorksheetTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.PURGE_WORKSHEET);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        locale.toolbarspreadsheetSearchCellTitle(),
                        locale.toolbarSpreadsheetSearchCellTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.SEARCH_CELL);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        locale.toolbarSpreadsheetSetRowTitle(),
                        locale.toolbarSpreadsheetSetRowTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.SET_ROW);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        locale.toolbarSpreadsheetUpdateWorksheetMetaTitle(),
                        locale.toolbarSpreadsheetUpdateWorksheetMetaTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.UPDATE_WORKSHEET_METADATA);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        locale.toolbarSpreadsheetUsernameLoginTitle(),
                        locale.toolbarSpreadsheetUsernameLoginTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.USERNAME_LOGIN);
    }

}