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
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.CreateWorksheet;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GoogleSpreadsheetPropertyManager;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.connectors.base.AbstractConnectorPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.base.GeneralPropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.connectors.base.parameter.ParameterPresenter;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
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
 */
public class CreateWorksheetConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<CreateWorksheet> {

    private final WSO2EditorLocalizationConstant locale;
    private final NameSpaceEditorPresenter       nameSpacePresenter;
    private final AddNameSpacesCallBack          spreadsheetNameCallBack;
    private final AddNameSpacesCallBack          worksheetNameCallBack;
    private final AddNameSpacesCallBack          worksheetRowsCallBack;
    private final AddNameSpacesCallBack          worksheetColumnsCallBack;

    @Inject
    public CreateWorksheetConnectorPresenter(WSO2EditorLocalizationConstant locale,
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

                CreateWorksheetConnectorPresenter.this.view.setSecondTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.worksheetNameCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setWorksheetNameNS(nameSpaces);
                element.setWorksheetNameExpression(expression);

                CreateWorksheetConnectorPresenter.this.view.setFirstTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.worksheetRowsCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.seWorksheetRowsNS(nameSpaces);
                element.setWorksheetRowsExpression(expression);

                CreateWorksheetConnectorPresenter.this.view.setThirdTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.worksheetColumnsCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setworksheetColumnsNameNS(nameSpaces);
                element.setWorksheetColumnsExpression(expression);

                CreateWorksheetConnectorPresenter.this.view.setFourthTextBoxValue(expression);

                notifyListeners();
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onParameterEditorTypeChanged() {
        ParameterEditorType editorType = ParameterEditorType.valueOf(view.getParameterEditorType());
        element.setParameterEditorType(editorType);

        boolean isEquals = NamespacedPropertyEditor.equals(editorType);

        view.setVisibleFirstButton(isEquals);
        view.setVisibleSecondButton(isEquals);
        view.setVisibleThirdButton(isEquals);
        view.setVisibleFourthButton(isEquals);

        view.setEnableFirstTextBox(!isEquals);
        view.setEnableSecondTextBox(!isEquals);
        view.setEnableThirdTextBox(!isEquals);
        view.setEnableFourthTextBox(!isEquals);

        view.setFirstTextBoxValue(isEquals ? element.getSpreadsheetNameExpression() : element.getSpreadsheetName());
        view.setSecondTextBoxValue(isEquals ? element.getWorksheetNameExpression() : element.getWorksheetName());
        view.setThirdTextBoxValue(isEquals ? element.getWorksheetRowsExpression() : element.getWorksheetRows());
        view.setFourthTextBoxValue(isEquals ? element.getWorksheetColumnsExpression() : element.getWorksheetColumns());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFirstTextBoxValueChanged() {
        element.setWorksheetName(view.getFirstTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSecondTextBoxValueChanged() {
        element.setSpreadsheetName(view.getSecondTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onThirdTextBoxValueChanged() {
        element.setWorksheetRows(view.getThirdTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFourthTextBoxValueChanged() {
        element.setWorksheetColumns(view.getFourthTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFirstButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getWorksheetNameNS(),
                                                    worksheetNameCallBack,
                                                    locale.connectorExpression(),
                                                    element.getWorksheetNameExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onSecondButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getSpreadsheetNameNS(),
                                                    spreadsheetNameCallBack,
                                                    locale.connectorExpression(),
                                                    element.getSpreadsheetNameExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onThirdButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getWorksheetRowsNS(),
                                                    worksheetRowsCallBack,
                                                    locale.connectorExpression(),
                                                    element.getWorksheetRowsExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onFourthButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getWorksheetColumnsNS(),
                                                    worksheetColumnsCallBack,
                                                    locale.connectorExpression(),
                                                    element.getWorksheetColumnsExpression());
    }

    private void redesignViewToCurrentConnector() {
        view.setVisibleFirstPanel(true);
        view.setVisibleSecondPanel(true);
        view.setVisibleThirdPanel(true);
        view.setVisibleFourthPanel(true);

        view.setFirstLabelTitle(locale.spreadsheetCreateWorksheetWorksheetName());
        view.setSecondLabelTitle(locale.spreadsheetCreateWorksheetSpreadsheetName());
        view.setThirdLabelTitle(locale.spreadsheetCreateWorksheetWorksheetRows());
        view.setFourthLabelTitle(locale.spreadsheetCreateWorksheetWorksheetColumns());
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        redesignViewToCurrentConnector();
        onParameterEditorTypeChanged();
    }
}
