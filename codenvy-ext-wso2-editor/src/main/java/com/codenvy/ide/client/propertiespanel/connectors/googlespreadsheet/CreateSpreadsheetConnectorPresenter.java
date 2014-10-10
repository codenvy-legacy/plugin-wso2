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
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.CreateSpreadsheet;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GoogleSpreadsheetPropertyManager;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.managers.SelectionManager;
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
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.CreateSpreadsheet.SPREADSHEET_NAME;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.CreateSpreadsheet.SPREADSHEET_NAME_EXPRESSION;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.CreateSpreadsheet.SPREADSHEET_NAME_NS;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.CreateSpreadsheet.WORKSHEET_COUNT;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.CreateSpreadsheet.WORKSHEET_COUNT_EXPRESSION;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.CreateSpreadsheet.WORKSHEET_COUNT_NS;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class CreateSpreadsheetConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<CreateSpreadsheet> {
    private ComplexPropertyPresenter spreadsheetNameNS;
    private ComplexPropertyPresenter worksheetCountNS;
    private SimplePropertyPresenter  spreadsheetName;
    private SimplePropertyPresenter  worksheetCount;

    @Inject
    public CreateSpreadsheetConnectorPresenter(WSO2EditorLocalizationConstant locale,
                                               NameSpaceEditorPresenter nameSpacePresenter,
                                               PropertiesPanelView view,
                                               GoogleSpreadsheetPropertyManager googleSpreadsheetPropertyManager,
                                               ParameterPresenter parameterPresenter,
                                               PropertyTypeManager propertyTypeManager,
                                               PropertyPanelFactory propertyPanelFactory,
                                               SelectionManager selectionManager) {
        super(view,
              googleSpreadsheetPropertyManager,
              parameterPresenter,
              nameSpacePresenter,
              propertyTypeManager,
              locale,
              propertyPanelFactory,
              selectionManager);

        prepareView();
    }

    private void prepareView() {
        spreadsheetNameNS = createComplexConnectorProperty(locale.spreadsheetCreateSpreadsheetSpreadsheetName(),
                                                           SPREADSHEET_NAME_NS,
                                                           SPREADSHEET_NAME_EXPRESSION);

        worksheetCountNS = createComplexConnectorProperty(locale.spreadsheetCreateSpreadsheetWorksheetCount(),
                                                          WORKSHEET_COUNT_NS,
                                                          WORKSHEET_COUNT_EXPRESSION);

        spreadsheetName = createSimpleConnectorProperty(locale.spreadsheetCreateSpreadsheetSpreadsheetName(), SPREADSHEET_NAME);

        worksheetCount = createSimpleConnectorProperty(locale.spreadsheetCreateSpreadsheetWorksheetCount(), WORKSHEET_COUNT);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        ParameterEditorType property = element.getProperty(PARAMETER_EDITOR_TYPE);
        boolean isNameSpaced = NAME_SPACED_PROPERTY_EDITOR.equals(property);

        spreadsheetName.setVisible(!isNameSpaced);
        worksheetCount.setVisible(!isNameSpaced);

        spreadsheetNameNS.setVisible(isNameSpaced);
        worksheetCountNS.setVisible(isNameSpaced);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        spreadsheetName.setProperty(element.getProperty(SPREADSHEET_NAME));
        worksheetCount.setProperty(element.getProperty(WORKSHEET_COUNT));
        spreadsheetNameNS.setProperty(element.getProperty(SPREADSHEET_NAME_EXPRESSION));
        worksheetCountNS.setProperty(element.getProperty(WORKSHEET_COUNT_EXPRESSION));
    }
}
