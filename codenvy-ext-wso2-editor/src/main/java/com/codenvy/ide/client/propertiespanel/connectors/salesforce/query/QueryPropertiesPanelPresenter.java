/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.client.propertiespanel.connectors.salesforce.query;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.connectors.salesforce.GeneralPropertyManager;
import com.codenvy.ide.client.elements.connectors.salesforce.Query;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.connectors.base.GeneralConnectorPanelPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.base.GeneralConnectorPanelView;
import com.codenvy.ide.client.propertiespanel.connectors.base.parameter.ParameterPresenter;
import com.codenvy.ide.client.propertiespanel.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType
        .NamespacedPropertyEditor;


/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Query connector
 * depending on user's changes of properties.
 *
 * @author Valeriy Svydenko
 */
public class QueryPropertiesPanelPresenter extends GeneralConnectorPanelPresenter<Query> {

    private final WSO2EditorLocalizationConstant locale;
    private final NameSpaceEditorPresenter       nameSpacePresenter;
    private final AddNameSpacesCallBack          batchSizeNameSpacesCallBack;
    private final AddNameSpacesCallBack          queryStringNameSpacesCallBack;

    @Inject
    public QueryPropertiesPanelPresenter(WSO2EditorLocalizationConstant locale,
                                         NameSpaceEditorPresenter nameSpacePresenter,
                                         GeneralConnectorPanelView view,
                                         GeneralPropertyManager generalPropertyManager,
                                         ParameterPresenter parameterPresenter,
                                         PropertyTypeManager propertyTypeManager) {
        super(view, generalPropertyManager, parameterPresenter, propertyTypeManager);

        this.locale = locale;

        this.nameSpacePresenter = nameSpacePresenter;

        this.batchSizeNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setBatchSizeNameSpaces(nameSpaces);
                element.setBatchSizeExpr(expression);

                QueryPropertiesPanelPresenter.this.view.setSecondTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.queryStringNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setQueryStringNameSpaces(nameSpaces);
                element.setQueryStringExpr(expression);

                QueryPropertiesPanelPresenter.this.view.setFirstTextBoxValue(expression);

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

        view.setEnableFirstTextBox(!isEquals);
        view.setEnableSecondTextBox(!isEquals);

        view.setFirstTextBoxValue(isEquals ? element.getBatchSizeExpr() : element.getBatchSize());
        view.setSecondTextBoxValue(isEquals ? element.getQueryStringExpr() : element.getQueryString());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFirstTextBoxValueChanged() {
        element.setBatchSize(view.getFirstTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSecondTextBoxValueChanged() {
        element.setQueryString(view.getSecondTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFirstButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getBatchSizeNameSpaces(),
                                                    queryStringNameSpacesCallBack,
                                                    locale.connectorExpression(),
                                                    element.getBatchSizeExpr());
    }

    /** {@inheritDoc} */
    @Override
    public void onSecondButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getQueryStringNameSpaces(),
                                                    batchSizeNameSpacesCallBack,
                                                    locale.connectorExpression(),
                                                    element.getQueryStringExpr());
    }

    private void redesignViewToCurrentConnector() {
        view.setVisibleFirstPanel(true);
        view.setVisibleSecondPanel(true);

        view.setFirstLabelTitle(locale.connectorBatchSize());
        view.setSecondLabelTitle(locale.connectorQueryString());
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        redesignViewToCurrentConnector();
        onParameterEditorTypeChanged();
    }
}