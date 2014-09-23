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
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GoogleSpreadsheetPropertyManager;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.ImportCSV;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.client.propertiespanel.connectors.base.AbstractConnectorPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.base.GeneralPropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.connectors.base.parameter.ParameterPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.List;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NamespacedPropertyEditor;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class ImportCSVConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<ImportCSV> {

    private final WSO2EditorLocalizationConstant locale;
    private final NameSpaceEditorPresenter       nameSpacePresenter;
    private final AddNameSpacesCallBack          spreadsheetNameCallBack;
    private final AddNameSpacesCallBack          worksheetNameCallBack;
    private final AddNameSpacesCallBack          filePathCallBack;
    private final AddNameSpacesCallBack          batchEnableCallBack;
    private final AddNameSpacesCallBack          batchSizeCallBack;

    @Inject
    public ImportCSVConnectorPresenter(WSO2EditorLocalizationConstant locale,
                                       NameSpaceEditorPresenter nameSpacePresenter,
                                       GeneralPropertiesPanelView view,
                                       GoogleSpreadsheetPropertyManager googleSpreadsheetPropertyManager,
                                       ParameterPresenter parameterPresenter,
                                       PropertyTypeManager propertyTypeManager) {
        super(view, googleSpreadsheetPropertyManager, parameterPresenter, propertyTypeManager);

        this.locale = locale;

        this.nameSpacePresenter = nameSpacePresenter;

        this.spreadsheetNameCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setSpreadsheetNameNS(nameSpaces);
                element.setSpreadsheetNameExpression(expression);

                ImportCSVConnectorPresenter.this.view.setFirstTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.worksheetNameCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setWorksheetNameNS(nameSpaces);
                element.setWorksheetCountExpression(expression);

                ImportCSVConnectorPresenter.this.view.setSecondTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.filePathCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setFilePathNS(nameSpaces);
                element.setFilePathExpression(expression);

                ImportCSVConnectorPresenter.this.view.setThirdTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.batchEnableCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setBatchEnableNS(nameSpaces);
                element.setBatchEnableExpression(expression);

                ImportCSVConnectorPresenter.this.view.setFourthTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.batchSizeCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setBatchSizeNS(nameSpaces);
                element.setBatchSizeExpression(expression);

                ImportCSVConnectorPresenter.this.view.setFifthTextBoxValue(expression);

                notifyListeners();
            }
        };

    }

    /** {@inheritDoc} */
    @Override
    public void onParameterEditorTypeChanged() {
        redrawPropertiesPanel();

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFirstTextBoxValueChanged() {
        element.setSpreadsheetName(view.getFirstTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSecondTextBoxValueChanged() {
        element.setWorksheetName(view.getSecondTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onThirdTextBoxValueChanged() {
        element.setFilePath(view.getThirdTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFourthTextBoxValueChanged() {
        element.setBatchEnable(view.getFourthTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFifthTextBoxValueChanged() {
        element.setBatchSize(view.getFifthTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFirstButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getSpreadsheetNameNS(),
                                                    spreadsheetNameCallBack,
                                                    locale.connectorExpression(),
                                                    element.getSpreadsheetNameExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onSecondButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getWorksheetNameNS(),
                                                    worksheetNameCallBack,
                                                    locale.connectorExpression(),
                                                    element.getWorksheetNameExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onThirdButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getFilePathNS(),
                                                    filePathCallBack,
                                                    locale.connectorExpression(),
                                                    element.getFilePathExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onFourthButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getBatchEnableNS(),
                                                    batchEnableCallBack,
                                                    locale.connectorExpression(),
                                                    element.getBatchEnableExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onFifthButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getBatchSizeNS(),
                                                    batchSizeCallBack,
                                                    locale.connectorExpression(),
                                                    element.getBatchSizeExpression());
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        ParameterEditorType editorType = ParameterEditorType.valueOf(view.getParameterEditorType());
        element.setParameterEditorType(editorType);

        boolean isEquals = NamespacedPropertyEditor.equals(editorType);

        view.setVisibleFirstButton(isEquals);
        view.setVisibleSecondButton(isEquals);
        view.setVisibleThirdButton(isEquals);
        view.setVisibleFourthButton(isEquals);
        view.setVisibleFifthButton(isEquals);

        view.setEnableFirstTextBox(!isEquals);
        view.setEnableSecondTextBox(!isEquals);
        view.setEnableThirdTextBox(!isEquals);
        view.setEnableFourthTextBox(!isEquals);
        view.setEnableFifthTextBox(!isEquals);

        view.setFirstTextBoxValue(isEquals ? element.getSpreadsheetNameExpression() : element.getSpreadsheetName());
        view.setSecondTextBoxValue(isEquals ? element.getWorksheetNameExpression() : element.getWorksheetName());
        view.setThirdTextBoxValue(isEquals ? element.getFilePathExpression() : element.getFilePath());
        view.setFourthTextBoxValue(isEquals ? element.getBatchEnableExpression() : element.getBatchEnable());
        view.setFifthTextBoxValue(isEquals ? element.getBatchSizeExpression() : element.getBatchSize());
    }

    private void redesignViewToCurrentConnector() {
        view.setVisibleFirstPanel(true);
        view.setVisibleSecondPanel(true);
        view.setVisibleThirdPanel(true);
        view.setVisibleFourthPanel(true);
        view.setVisibleFifthPanel(true);

        view.setFirstLabelTitle(locale.spreadsheetCreateSpreadsheetSpreadsheetName());
        view.setSecondLabelTitle(locale.spreadsheetCreateWorksheetWorksheetName());
        view.setThirdLabelTitle(locale.spreadsheetImportCSVFilePath());
        view.setFourthLabelTitle(locale.spreadsheetImportCSVBatchEnable());
        view.setFifthLabelTitle(locale.spreadsheetImportCSVBatchSize());
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        redesignViewToCurrentConnector();
    }
}
