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
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetWorksheetsByTitle;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GoogleSpreadsheetPropertyManager;
import com.codenvy.ide.client.inject.factories.PropertiesPanelWidgetFactory;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.base.AbstractConnectorPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.base.parameter.ParameterPresenter;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.list.ListPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.PARAMETER_EDITOR_TYPE;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NAME_SPACED_PROPERTY_EDITOR;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetWorksheetsByTitle.SPREADSHEET_NAME_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetWorksheetsByTitle.SPREADSHEET_NAME_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetWorksheetsByTitle.SPREADSHEET_NAME_NS_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetWorksheetsByTitle.TITLE_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetWorksheetsByTitle.TITLE_KEY;
import static com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetWorksheetsByTitle.TITLE_NS_KEY;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class GetWorksheetsByTitleConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<GetWorksheetsByTitle> {
    private ComplexPropertyPresenter spreadsheetNameNS;
    private ComplexPropertyPresenter titleNS;
    private SimplePropertyPresenter  spreadsheetName;
    private SimplePropertyPresenter  title;

    @Inject
    public GetWorksheetsByTitleConnectorPresenter(WSO2EditorLocalizationConstant locale,
                                                  NameSpaceEditorPresenter nameSpacePresenter,
                                                  PropertiesPanelView view,
                                                  GoogleSpreadsheetPropertyManager googleSpreadsheetPropertyManager,
                                                  ParameterPresenter parameterPresenter,
                                                  PropertyTypeManager propertyTypeManager,
                                                  PropertiesPanelWidgetFactory propertiesPanelWidgetFactory,
                                                  Provider<ListPropertyPresenter> listPropertyPresenterProvider,
                                                  Provider<ComplexPropertyPresenter> complexPropertyPresenterProvider,
                                                  Provider<SimplePropertyPresenter> simplePropertyPresenterProvider) {
        super(view,
              googleSpreadsheetPropertyManager,
              parameterPresenter,
              nameSpacePresenter,
              propertyTypeManager,
              locale,
              propertiesPanelWidgetFactory,
              listPropertyPresenterProvider,
              complexPropertyPresenterProvider,
              simplePropertyPresenterProvider);

        prepareView();
    }

    private void prepareView() {
        spreadsheetNameNS = createComplexPanel(locale.spreadsheetCreateSpreadsheetSpreadsheetName(),
                                               SPREADSHEET_NAME_NS_KEY,
                                               SPREADSHEET_NAME_EXPRESSION_KEY);
        titleNS = createComplexPanel(locale.spreadsheetGetSpreadsheetsTitle(), TITLE_NS_KEY, TITLE_EXPRESSION_KEY);

        spreadsheetName = createSimplePanel(locale.spreadsheetCreateSpreadsheetSpreadsheetName(), SPREADSHEET_NAME_KEY);
        title = createSimplePanel(locale.spreadsheetGetSpreadsheetsTitle(), TITLE_KEY);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        ParameterEditorType property = element.getProperty(PARAMETER_EDITOR_TYPE);
        boolean isNameSpaced = NAME_SPACED_PROPERTY_EDITOR.equals(property);

        spreadsheetName.setVisible(!isNameSpaced);
        title.setVisible(!isNameSpaced);

        spreadsheetNameNS.setVisible(isNameSpaced);
        titleNS.setVisible(isNameSpaced);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        spreadsheetName.setProperty(element.getProperty(SPREADSHEET_NAME_KEY));
        title.setProperty(element.getProperty(TITLE_KEY));

        spreadsheetNameNS.setProperty(element.getProperty(SPREADSHEET_NAME_EXPRESSION_KEY));
        titleNS.setProperty(element.getProperty(TITLE_EXPRESSION_KEY));
    }
}
