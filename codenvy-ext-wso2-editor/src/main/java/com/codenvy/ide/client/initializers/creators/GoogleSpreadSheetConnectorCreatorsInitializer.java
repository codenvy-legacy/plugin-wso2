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

package com.codenvy.ide.client.initializers.creators;

import com.codenvy.ide.client.constants.GoogleSpreadsheedConnectorCreatingState;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.CreateSpreadsheet;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.CreateWorksheet;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.DeleteWorksheet;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetAllCells;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetAllCellsCSV;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetAllSpreadsheets;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetAllWorksheets;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetAuthors;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetCellRange;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetCellRangeCSV;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetColumnHeaders;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetSpreadsheetsByTitle;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetWorksheetsByTitle;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.ImportCSV;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.InitSpreadsheet;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.PurgeWorkshet;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.SearchCell;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.SetRow;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.UpdateWorksheetMetadata;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.UsernameLogin;
import com.codenvy.ide.client.initializers.Initializer;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author Andrey Plotnikov
 */
public class GoogleSpreadSheetConnectorCreatorsInitializer implements Initializer {

    private final ElementCreatorsManager            manager;
    private final Provider<CreateSpreadsheet>       createSpreadsheetProvider;
    private final Provider<CreateWorksheet>         createWorksheetProvider;
    private final Provider<DeleteWorksheet>         deleteWorksheetProvider;
    private final Provider<GetAllCells>             getAllCellsProvider;
    private final Provider<GetAllCellsCSV>          getAllCellsCSVProvider;
    private final Provider<GetAllSpreadsheets>      getAllSpreadsheetsProvider;
    private final Provider<GetAllWorksheets>        getAllWorksheetsProvider;
    private final Provider<GetAuthors>              getAuthorsProvider;
    private final Provider<GetCellRange>            getCellRangeProvider;
    private final Provider<GetColumnHeaders>        getColumnHeadersProvider;
    private final Provider<GetSpreadsheetsByTitle>  getSpreadsheetsByTitleProvider;
    private final Provider<GetWorksheetsByTitle>    getWorksheetsByTitleProvider;
    private final Provider<ImportCSV>               importCSVProvider;
    private final Provider<InitSpreadsheet>         initSpreadsheetProvider;
    private final Provider<PurgeWorkshet>           purgeWorkshetProvider;
    private final Provider<SearchCell>              searchCellProvider;
    private final Provider<SetRow>                  setRowProvider;
    private final Provider<UpdateWorksheetMetadata> updateWorksheetMetadataProvider;
    private final Provider<UsernameLogin>           usernameLoginProvider;
    private final Provider<GetCellRangeCSV>         getCellRangeCSVProvider;

    @Inject
    public GoogleSpreadSheetConnectorCreatorsInitializer(ElementCreatorsManager manager,
                                                         Provider<CreateSpreadsheet> createSpreadsheetProvider,
                                                         Provider<CreateWorksheet> createWorksheetProvider,
                                                         Provider<DeleteWorksheet> deleteWorksheetProvider,
                                                         Provider<GetAllCells> getAllCellsProvider,
                                                         Provider<GetAllCellsCSV> getAllCellsCSVProvider,
                                                         Provider<GetAllSpreadsheets> getAllSpreadsheetsProvider,
                                                         Provider<GetAllWorksheets> getAllWorksheetsProvider,
                                                         Provider<GetAuthors> getAuthorsProvider,
                                                         Provider<GetCellRange> getCellRangeProvider,
                                                         Provider<GetColumnHeaders> getColumnHeadersProvider,
                                                         Provider<GetSpreadsheetsByTitle> getSpreadsheetsByTitleProvider,
                                                         Provider<GetWorksheetsByTitle> getWorksheetsByTitleProvider,
                                                         Provider<ImportCSV> importCSVProvider,
                                                         Provider<InitSpreadsheet> initSpreadsheetProvider,
                                                         Provider<PurgeWorkshet> purgeWorkshetProvider,
                                                         Provider<SearchCell> searchCellProvider,
                                                         Provider<SetRow> setRowProvider,
                                                         Provider<UpdateWorksheetMetadata> updateWorksheetMetadataProvider,
                                                         Provider<UsernameLogin> usernameLoginProvider,
                                                         Provider<GetCellRangeCSV> getCellRangeCSVProvider) {
        this.manager = manager;
        this.createSpreadsheetProvider = createSpreadsheetProvider;
        this.createWorksheetProvider = createWorksheetProvider;
        this.deleteWorksheetProvider = deleteWorksheetProvider;
        this.getAllCellsProvider = getAllCellsProvider;
        this.getAllCellsCSVProvider = getAllCellsCSVProvider;
        this.getAllSpreadsheetsProvider = getAllSpreadsheetsProvider;
        this.getAllWorksheetsProvider = getAllWorksheetsProvider;
        this.getAuthorsProvider = getAuthorsProvider;
        this.getCellRangeProvider = getCellRangeProvider;
        this.getColumnHeadersProvider = getColumnHeadersProvider;
        this.getSpreadsheetsByTitleProvider = getSpreadsheetsByTitleProvider;
        this.getWorksheetsByTitleProvider = getWorksheetsByTitleProvider;
        this.importCSVProvider = importCSVProvider;
        this.initSpreadsheetProvider = initSpreadsheetProvider;
        this.purgeWorkshetProvider = purgeWorkshetProvider;
        this.searchCellProvider = searchCellProvider;
        this.setRowProvider = setRowProvider;
        this.updateWorksheetMetadataProvider = updateWorksheetMetadataProvider;
        this.usernameLoginProvider = usernameLoginProvider;
        this.getCellRangeCSVProvider = getCellRangeCSVProvider;
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        manager.register(CreateSpreadsheet.ELEMENT_NAME,
                         CreateSpreadsheet.SERIALIZATION_NAME,
                         GoogleSpreadsheedConnectorCreatingState.CREATING_SPREADSHEET,
                         createSpreadsheetProvider);

        manager.register(CreateWorksheet.ELEMENT_NAME,
                         CreateWorksheet.SERIALIZATION_NAME,
                         GoogleSpreadsheedConnectorCreatingState.CREATING_WORKSHEET,
                         createWorksheetProvider);

        manager.register(DeleteWorksheet.ELEMENT_NAME,
                         DeleteWorksheet.SERIALIZATION_NAME,
                         GoogleSpreadsheedConnectorCreatingState.DELETE_WORKSHEET,
                         deleteWorksheetProvider);

        manager.register(GetAllCells.ELEMENT_NAME,
                         GetAllCells.SERIALIZATION_NAME,
                         GoogleSpreadsheedConnectorCreatingState.GET_ALL_CELLS,
                         getAllCellsProvider);

        manager.register(GetAllCellsCSV.ELEMENT_NAME,
                         GetAllCellsCSV.SERIALIZATION_NAME,
                         GoogleSpreadsheedConnectorCreatingState.GET_ALL_CELLS_CSV,
                         getAllCellsCSVProvider);

        manager.register(GetAllWorksheets.ELEMENT_NAME,
                         GetAllWorksheets.SERIALIZATION_NAME,
                         GoogleSpreadsheedConnectorCreatingState.GET_ALL_WORKSHEETS,
                         getAllWorksheetsProvider);

        manager.register(GetAllSpreadsheets.ELEMENT_NAME,
                         GetAllSpreadsheets.SERIALIZATION_NAME,
                         GoogleSpreadsheedConnectorCreatingState.GET_ALL_SPREADSHEETS,
                         getAllSpreadsheetsProvider);

        manager.register(GetAuthors.ELEMENT_NAME,
                         GetAuthors.SERIALIZATION_NAME,
                         GoogleSpreadsheedConnectorCreatingState.GET_AUTHORS,
                         getAuthorsProvider);

        manager.register(GetCellRange.ELEMENT_NAME,
                         GetCellRange.SERIALIZATION_NAME,
                         GoogleSpreadsheedConnectorCreatingState.GET_CELL_RANGE,
                         getCellRangeProvider);

        manager.register(GetCellRangeCSV.ELEMENT_NAME,
                         GetCellRangeCSV.SERIALIZATION_NAME,
                         GoogleSpreadsheedConnectorCreatingState.GET_CELL_RANGE_CSV,
                         getCellRangeCSVProvider);

        manager.register(GetColumnHeaders.ELEMENT_NAME,
                         GetColumnHeaders.SERIALIZATION_NAME,
                         GoogleSpreadsheedConnectorCreatingState.GET_COLUMN_HEADERS,
                         getColumnHeadersProvider);

        manager.register(GetSpreadsheetsByTitle.ELEMENT_NAME,
                         GetSpreadsheetsByTitle.SERIALIZATION_NAME,
                         GoogleSpreadsheedConnectorCreatingState.GET_SPREADSHEET_BY_TITLE,
                         getSpreadsheetsByTitleProvider);

        manager.register(GetWorksheetsByTitle.ELEMENT_NAME,
                         GetWorksheetsByTitle.SERIALIZATION_NAME,
                         GoogleSpreadsheedConnectorCreatingState.GET_WORKSHEET_BY_TITLE,
                         getWorksheetsByTitleProvider);

        manager.register(ImportCSV.ELEMENT_NAME,
                         ImportCSV.SERIALIZATION_NAME,
                         GoogleSpreadsheedConnectorCreatingState.IMPORT_CSV,
                         importCSVProvider);

        manager.register(InitSpreadsheet.ELEMENT_NAME,
                         InitSpreadsheet.SERIALIZATION_NAME,
                         GoogleSpreadsheedConnectorCreatingState.INIT,
                         initSpreadsheetProvider);

        manager.register(PurgeWorkshet.ELEMENT_NAME,
                         PurgeWorkshet.SERIALIZATION_NAME,
                         GoogleSpreadsheedConnectorCreatingState.PURGE_WORKSHEET,
                         purgeWorkshetProvider);

        manager.register(SearchCell.ELEMENT_NAME,
                         SearchCell.SERIALIZATION_NAME,
                         GoogleSpreadsheedConnectorCreatingState.SEARCH_CELL,
                         searchCellProvider);

        manager.register(SetRow.ELEMENT_NAME,
                         SetRow.SERIALIZATION_NAME,
                         GoogleSpreadsheedConnectorCreatingState.SET_ROW,
                         setRowProvider);

        manager.register(UpdateWorksheetMetadata.ELEMENT_NAME,
                         UpdateWorksheetMetadata.SERIALIZATION_NAME,
                         GoogleSpreadsheedConnectorCreatingState.UPDATE_WORKSHEET_METADATA,
                         updateWorksheetMetadataProvider);

        manager.register(UsernameLogin.ELEMENT_NAME,
                         UsernameLogin.SERIALIZATION_NAME,
                         GoogleSpreadsheedConnectorCreatingState.USERNAME_LOGIN,
                         usernameLoginProvider);
    }

}