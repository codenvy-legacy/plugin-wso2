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
package com.codenvy.ide.client.constants;

/**
 * @author Valeriy Svydenko
 */
public interface GoogleSpreadsheedConnectorCreatingState {

    String CREATING_SPREADSHEET      = "CreatingSpreadsheetGoogleSpreadsheetConnectorCreatingState";
    String CREATING_WORKSHEET        = "CreatingWorksheetGoogleSpreadsheetConnectorCreatingState";
    String DELETE_WORKSHEET          = "DeleteWorksheetGoogleSpreadsheetConnectorCreatingState";
    String GET_ALL_CELLS             = "GetAllCellsGoogleSpreadsheetConnectorCreatingState";
    String GET_ALL_CELLS_CSV         = "GetAllCellsCSVGoogleSpreadsheetConnectorCreatingState";
    String GET_ALL_SPREADSHEETS      = "GetAllSpreadsheetsGoogleSpreadsheetConnectorCreatingState";
    String GET_ALL_WORKSHEETS        = "GetAllWorksheetsGoogleSpreadsheetConnectorCreatingState";
    String GET_AUTHORS               = "GetAuthorsGoogleSpreadsheetConnectorCreatingState";
    String GET_CELL_RANGE            = "GetCellRangeGoogleSpreadsheetConnectorCreatingState";
    String GET_CELL_RANGE_CSV        = "GetCellRangeCSVGoogleSpreadsheetConnectorCreatingState";
    String GET_COLUMN_HEADERS        = "GetColumnHeadersGoogleSpreadsheetConnectorCreatingState";
    String GET_SPREADSHEET_BY_TITLE  = "GetSpreadsheetByTitleGoogleSpreadsheetConnectorCreatingState";
    String GET_WORKSHEET_BY_TITLE    = "GetWorksheetByTitleGoogleSpreadsheetConnectorCreatingState";
    String IMPORT_CSV                = "ImportCSVGoogleSpreadsheetConnectorCreatingState";
    String INIT                      = "InitGoogleSpreadsheetConnectorCreatingState";
    String PURGE_WORKSHEET           = "PurgeWorksheetGoogleSpreadsheetConnectorCreatingState";
    String SEARCH_CELL               = "SearchCellGoogleSpreadsheetConnectorCreatingState";
    String SET_ROW                   = "SetRowGoogleSpreadsheetConnectorCreatingState";
    String UPDATE_WORKSHEET_METADATA = "UpdateWorksheetMetadataGoogleSpreadsheetConnectorCreatingState";
    String USERNAME_LOGIN            = "UsernameLoginGoogleSpreadsheetConnectorCreatingState";
}