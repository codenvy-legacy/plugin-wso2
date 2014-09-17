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
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetCellRange;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GoogleSpreadsheetPropertyManager;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.client.propertiespanel.connectors.base.AbstractConnectorPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.base.GeneralPropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.connectors.base.parameter.ParameterPresenter;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NamespacedPropertyEditor;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class GetCellRangeConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<GetCellRange> {

    private final WSO2EditorLocalizationConstant locale;
    private final NameSpaceEditorPresenter       nameSpacePresenter;
    private final AddNameSpacesCallBack          spreadsheetNameCallBack;
    private final AddNameSpacesCallBack          worksheetNameCallBack;
    private final AddNameSpacesCallBack          minRowCallBack;
    private final AddNameSpacesCallBack          maxRowNameCallBack;
    private final AddNameSpacesCallBack          minColumnNameCallBack;
    private final AddNameSpacesCallBack          maxColumnNameCallBack;

    @Inject
    public GetCellRangeConnectorPresenter(WSO2EditorLocalizationConstant locale,
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
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setSpreadsheetNameNS(nameSpaces);
                element.setSpreadsheetNameExpression(expression);

                GetCellRangeConnectorPresenter.this.view.setFirstTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.worksheetNameCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setWorksheetNameNS(nameSpaces);
                element.setWorksheetNameExpression(expression);

                GetCellRangeConnectorPresenter.this.view.setSecondTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.minRowCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setMinRowNS(nameSpaces);
                element.setMinRowExpression(expression);

                GetCellRangeConnectorPresenter.this.view.setThirdTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.maxRowNameCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setMaxRowNS(nameSpaces);
                element.setMaxRowExpression(expression);

                GetCellRangeConnectorPresenter.this.view.setFourthTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.minColumnNameCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setMaxColumnNS(nameSpaces);
                element.setMinColumnExpression(expression);

                GetCellRangeConnectorPresenter.this.view.setFifthTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.maxColumnNameCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setMaxColumnNS(nameSpaces);
                element.setMaxColumnExpression(expression);

                GetCellRangeConnectorPresenter.this.view.setSixthTextBoxValue(expression);

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
        element.setMinRow(view.getThirdTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFourthTextBoxValueChanged() {
        element.setMaxRow(view.getFourthTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFifthTextBoxValueChanged() {
        element.setMinColumn(view.getFifthTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSixthTextBoxValueChanged() {
        element.setMaxColumn(view.getSixthTextBoxValue());

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
        nameSpacePresenter.showWindowWithParameters(element.getWorksheetCountNS(),
                                                    worksheetNameCallBack,
                                                    locale.connectorExpression(),
                                                    element.getWorksheetNameExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onThirdButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getMinRowNS(),
                                                    minRowCallBack,
                                                    locale.connectorExpression(),
                                                    element.getMinRowExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onFourthButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getMaxRowNS(),
                                                    maxRowNameCallBack,
                                                    locale.connectorExpression(),
                                                    element.getMaxRowExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onFifthButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getMinColumnNS(),
                                                    minColumnNameCallBack,
                                                    locale.connectorExpression(),
                                                    element.getMinColumnExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onSixthButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getMaxColumnNS(),
                                                    maxColumnNameCallBack,
                                                    locale.connectorExpression(),
                                                    element.getMaxColumnExpression());
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
        view.setVisibleSixthButton(isEquals);

        view.setEnableFirstTextBox(!isEquals);
        view.setEnableSecondTextBox(!isEquals);
        view.setEnableThirdTextBox(!isEquals);
        view.setEnableFourthTextBox(!isEquals);
        view.setEnableFifthTextBox(!isEquals);
        view.setEnableSixthTextBox(!isEquals);

        view.setFirstTextBoxValue(isEquals ? element.getSpreadsheetNameExpression() : element.getSpreadsheetName());
        view.setSecondTextBoxValue(isEquals ? element.getWorksheetNameExpression() : element.getWorksheetName());
        view.setThirdTextBoxValue(isEquals ? element.getMinRowExpression() : element.getMinRow());
        view.setFourthTextBoxValue(isEquals ? element.getMaxRowExpression() : element.getMaxRow());
        view.setFifthTextBoxValue(isEquals ? element.getMinColumnExpression() : element.getMinColumn());
        view.setSixthTextBoxValue(isEquals ? element.getMaxColumnExpression() : element.getMaxColumn());
    }

    private void redesignViewToCurrentConnector() {
        view.setVisibleFirstPanel(true);
        view.setVisibleSecondPanel(true);
        view.setVisibleThirdPanel(true);
        view.setVisibleFourthPanel(true);
        view.setVisibleFifthPanel(true);
        view.setVisibleSixthPanel(true);

        view.setFirstLabelTitle(locale.spreadsheetCreateSpreadsheetSpreadsheetName());
        view.setSecondLabelTitle(locale.spreadsheetCreateWorksheetWorksheetName());
        view.setThirdLabelTitle(locale.spreadsheetGetCellRangeMinRow());
        view.setFourthLabelTitle(locale.spreadsheetGetCellRangeMaxRow());
        view.setFifthLabelTitle(locale.spreadsheetGetCellRangeMinColumn());
        view.setSixthLabelTitle(locale.spreadsheetGetCellRangeMaxColumn());
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        redesignViewToCurrentConnector();
    }
}
