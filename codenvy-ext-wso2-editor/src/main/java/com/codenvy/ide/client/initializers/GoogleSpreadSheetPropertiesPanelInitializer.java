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

package com.codenvy.ide.client.initializers;

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
import com.codenvy.ide.client.managers.PropertiesPanelManager;
import com.codenvy.ide.client.propertiespanel.connectors.googlespreadsheet.CreateSpreadsheetConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.googlespreadsheet.CreateWorksheetConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.googlespreadsheet.DeleteWorksheetConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.googlespreadsheet.GetAllCellsCSVConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.googlespreadsheet.GetAllCellsConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.googlespreadsheet.GetAllSpreadsheetsConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.googlespreadsheet.GetAllWorksheetsConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.googlespreadsheet.GetAuthorsConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.googlespreadsheet.GetCellCSVRangeConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.googlespreadsheet.GetCellRangeConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.googlespreadsheet.GetColumnHeadersConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.googlespreadsheet.GetSpreadsheetsByTitleConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.googlespreadsheet.GetWorksheetsByTitleConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.googlespreadsheet.ImportCSVConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.googlespreadsheet.InitSpreadsheetConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.googlespreadsheet.PurgeWorksheetConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.googlespreadsheet.SearchCellConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.googlespreadsheet.SetRowConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.googlespreadsheet.UpdateWorksheetMetadataConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.googlespreadsheet.UsernameLoginConnectorPresenter;
import com.google.inject.Inject;

/**
 * @author Andrey Plotnikov
 */
public class GoogleSpreadSheetPropertiesPanelInitializer implements Initializer {

    private final PropertiesPanelManager                    propertiesPanelManager;
    private final CreateSpreadsheetConnectorPresenter       createSpreadsheetPropertiesPanel;
    private final CreateWorksheetConnectorPresenter         createWorksheetPropertiesPanel;
    private final DeleteWorksheetConnectorPresenter         deleteWorksheetPropertiesPanel;
    private final GetAllCellsConnectorPresenter             getAllCellsPropertiesPanel;
    private final GetAllCellsCSVConnectorPresenter          getAllCellsCSVPropertiesPanel;
    private final GetAllSpreadsheetsConnectorPresenter      getAllSpreadsheetsPropertiesPanel;
    private final GetAuthorsConnectorPresenter              getAuthorsPropertiesPanel;
    private final GetCellRangeConnectorPresenter            getCellRangePropertiesPanel;
    private final GetCellCSVRangeConnectorPresenter         getCellCSVRangePropertiesPanel;
    private final GetColumnHeadersConnectorPresenter        getColumnHeadersPropertiesPanel;
    private final GetSpreadsheetsByTitleConnectorPresenter  getSpreadsheetsByTitlePropertiesPanel;
    private final GetAllWorksheetsConnectorPresenter        getAllWorksheetsPropertiesPanel;
    private final GetWorksheetsByTitleConnectorPresenter    getWorksheetsByTitlePropertiesPanel;
    private final ImportCSVConnectorPresenter               importCSVPropertiesPanel;
    private final InitSpreadsheetConnectorPresenter         initSpreadsheetPropertiesPanel;
    private final PurgeWorksheetConnectorPresenter          purgeWorksheetPropertiesPanel;
    private final SearchCellConnectorPresenter              searchCellPropertiesPanel;
    private final SetRowConnectorPresenter                  setRowPropertiesPanel;
    private final UpdateWorksheetMetadataConnectorPresenter updateWorksheetMetadataPropertiesPanel;
    private final UsernameLoginConnectorPresenter           usernameLoginPropertiesPanel;

    @Inject
    public GoogleSpreadSheetPropertiesPanelInitializer(PropertiesPanelManager propertiesPanelManager,
                                                       CreateSpreadsheetConnectorPresenter createSpreadsheetPropertiesPanel,
                                                       CreateWorksheetConnectorPresenter createWorksheetPropertiesPanel,
                                                       DeleteWorksheetConnectorPresenter deleteWorksheetPropertiesPanel,
                                                       GetAllCellsConnectorPresenter getAllCellsPropertiesPanel,
                                                       GetAllCellsCSVConnectorPresenter getAllCellsCSVPropertiesPanel,
                                                       GetAllSpreadsheetsConnectorPresenter getAllSpreadsheetsPropertiesPanel,
                                                       GetAuthorsConnectorPresenter getAuthorsPropertiesPanel,
                                                       GetCellRangeConnectorPresenter getCellRangePropertiesPanel,
                                                       GetCellCSVRangeConnectorPresenter getCellCSVRangePropertiesPanel,
                                                       GetColumnHeadersConnectorPresenter getColumnHeadersPropertiesPanel,
                                                       GetSpreadsheetsByTitleConnectorPresenter getSpreadsheetsByTitlePropertiesPanel,
                                                       GetAllWorksheetsConnectorPresenter getAllWorksheetsPropertiesPanel,
                                                       GetWorksheetsByTitleConnectorPresenter getWorksheetsByTitlePropertiesPanel,
                                                       ImportCSVConnectorPresenter importCSVPropertiesPanel,
                                                       InitSpreadsheetConnectorPresenter initSpreadsheetPropertiesPanel,
                                                       PurgeWorksheetConnectorPresenter purgeWorksheetPropertiesPanel,
                                                       SearchCellConnectorPresenter searchCellPropertiesPanel,
                                                       SetRowConnectorPresenter setRowPropertiesPanel,
                                                       UpdateWorksheetMetadataConnectorPresenter updateWorksheetMetadataPropertiesPanel,
                                                       UsernameLoginConnectorPresenter usernameLoginPropertiesPanel) {
        this.propertiesPanelManager = propertiesPanelManager;
        this.createSpreadsheetPropertiesPanel = createSpreadsheetPropertiesPanel;
        this.createWorksheetPropertiesPanel = createWorksheetPropertiesPanel;
        this.deleteWorksheetPropertiesPanel = deleteWorksheetPropertiesPanel;
        this.getAllCellsPropertiesPanel = getAllCellsPropertiesPanel;
        this.getAllCellsCSVPropertiesPanel = getAllCellsCSVPropertiesPanel;
        this.getAllSpreadsheetsPropertiesPanel = getAllSpreadsheetsPropertiesPanel;
        this.getAuthorsPropertiesPanel = getAuthorsPropertiesPanel;
        this.getCellRangePropertiesPanel = getCellRangePropertiesPanel;
        this.getCellCSVRangePropertiesPanel = getCellCSVRangePropertiesPanel;
        this.getColumnHeadersPropertiesPanel = getColumnHeadersPropertiesPanel;
        this.getSpreadsheetsByTitlePropertiesPanel = getSpreadsheetsByTitlePropertiesPanel;
        this.getAllWorksheetsPropertiesPanel = getAllWorksheetsPropertiesPanel;
        this.getWorksheetsByTitlePropertiesPanel = getWorksheetsByTitlePropertiesPanel;
        this.importCSVPropertiesPanel = importCSVPropertiesPanel;
        this.initSpreadsheetPropertiesPanel = initSpreadsheetPropertiesPanel;
        this.purgeWorksheetPropertiesPanel = purgeWorksheetPropertiesPanel;
        this.searchCellPropertiesPanel = searchCellPropertiesPanel;
        this.setRowPropertiesPanel = setRowPropertiesPanel;
        this.updateWorksheetMetadataPropertiesPanel = updateWorksheetMetadataPropertiesPanel;
        this.usernameLoginPropertiesPanel = usernameLoginPropertiesPanel;
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        propertiesPanelManager.register(CreateSpreadsheet.class, createSpreadsheetPropertiesPanel);
        propertiesPanelManager.register(CreateWorksheet.class, createWorksheetPropertiesPanel);
        propertiesPanelManager.register(DeleteWorksheet.class, deleteWorksheetPropertiesPanel);
        propertiesPanelManager.register(GetAllCells.class, getAllCellsPropertiesPanel);
        propertiesPanelManager.register(GetAllCellsCSV.class, getAllCellsCSVPropertiesPanel);
        propertiesPanelManager.register(GetAllWorksheets.class, getAllWorksheetsPropertiesPanel);
        propertiesPanelManager.register(GetAllSpreadsheets.class, getAllSpreadsheetsPropertiesPanel);
        propertiesPanelManager.register(GetAuthors.class, getAuthorsPropertiesPanel);
        propertiesPanelManager.register(GetCellRange.class, getCellRangePropertiesPanel);
        propertiesPanelManager.register(GetCellRangeCSV.class, getCellCSVRangePropertiesPanel);
        propertiesPanelManager.register(GetColumnHeaders.class, getColumnHeadersPropertiesPanel);
        propertiesPanelManager.register(GetSpreadsheetsByTitle.class, getSpreadsheetsByTitlePropertiesPanel);
        propertiesPanelManager.register(GetWorksheetsByTitle.class, getWorksheetsByTitlePropertiesPanel);
        propertiesPanelManager.register(ImportCSV.class, importCSVPropertiesPanel);
        propertiesPanelManager.register(InitSpreadsheet.class, initSpreadsheetPropertiesPanel);
        propertiesPanelManager.register(PurgeWorkshet.class, purgeWorksheetPropertiesPanel);
        propertiesPanelManager.register(SearchCell.class, searchCellPropertiesPanel);
        propertiesPanelManager.register(SetRow.class, setRowPropertiesPanel);
        propertiesPanelManager.register(UpdateWorksheetMetadata.class, updateWorksheetMetadataPropertiesPanel);
        propertiesPanelManager.register(UsernameLogin.class, usernameLoginPropertiesPanel);
    }

}