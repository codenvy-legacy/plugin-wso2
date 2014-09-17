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
package com.codenvy.ide.client.propertiespanel.connectors.jira;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.connectors.jira.CreateFilter;
import com.codenvy.ide.client.elements.connectors.jira.JiraPropertyManager;
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
 * @author Dmitry Shnurenko
 */
public class CreateFilterConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<CreateFilter> {

    private final WSO2EditorLocalizationConstant locale;
    private final NameSpaceEditorPresenter       nameSpacePresenter;
    private final AddNameSpacesCallBack          filterNameCallBack;
    private final AddNameSpacesCallBack          jqlTypeCallBack;
    private final AddNameSpacesCallBack          descriptionCallBack;
    private final AddNameSpacesCallBack          favouriteCallBack;

    @Inject
    public CreateFilterConnectorPresenter(WSO2EditorLocalizationConstant locale,
                                          NameSpaceEditorPresenter nameSpacePresenter,
                                          GeneralPropertiesPanelView view,
                                          JiraPropertyManager jiraPropertyManager,
                                          ParameterPresenter parameterPresenter,
                                          PropertyTypeManager propertyTypeManager) {
        super(view, jiraPropertyManager, parameterPresenter, propertyTypeManager);

        this.locale = locale;

        this.nameSpacePresenter = nameSpacePresenter;

        this.filterNameCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setFilterNameNS(nameSpaces);
                element.setFilterNameExpression(expression);

                CreateFilterConnectorPresenter.this.view.setFirstTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.jqlTypeCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setJqlTypeNS(nameSpaces);
                element.setJqlTypeExpression(expression);

                CreateFilterConnectorPresenter.this.view.setSecondTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.descriptionCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setDescriptionNS(nameSpaces);
                element.setDescriptionExpression(expression);

                CreateFilterConnectorPresenter.this.view.setThirdTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.favouriteCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setFavouriteNS(nameSpaces);
                element.setFavouriteExpression(expression);

                CreateFilterConnectorPresenter.this.view.setFourthTextBoxValue(expression);

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
        element.setFilterName(view.getFirstTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSecondTextBoxValueChanged() {
        element.setJqlType(view.getSecondTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onThirdTextBoxValueChanged() {
        element.setDescription(view.getThirdTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFourthTextBoxValueChanged() {
        element.setFavourite(view.getFourthTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFirstButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getFilterNameNS(),
                                                    filterNameCallBack,
                                                    locale.connectorExpression(),
                                                    element.getFilterNameExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onSecondButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getJqlTypeNS(),
                                                    jqlTypeCallBack,
                                                    locale.connectorExpression(),
                                                    element.getJqlTypeExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onThirdButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getDescriptionNS(),
                                                    descriptionCallBack,
                                                    locale.connectorExpression(),
                                                    element.getDescriptionExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onFourthButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getFavouriteNS(),
                                                    favouriteCallBack,
                                                    locale.connectorExpression(),
                                                    element.getFavouriteExpression());
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

        view.setEnableFirstTextBox(!isEquals);
        view.setEnableSecondTextBox(!isEquals);
        view.setEnableThirdTextBox(!isEquals);
        view.setEnableFourthTextBox(!isEquals);

        view.setFirstTextBoxValue(isEquals ? element.getFilterNameExpression() : element.getFilterName());
        view.setSecondTextBoxValue(isEquals ? element.getJqlTypeExpression() : element.getJqlType());
        view.setThirdTextBoxValue(isEquals ? element.getDescriptionExpression() : element.getDescription());
        view.setFourthTextBoxValue(isEquals ? element.getFavouriteExpression() : element.getFavourite());
    }

    private void redesignViewToCurrentConnector() {
        view.setVisibleFirstPanel(true);
        view.setVisibleSecondPanel(true);
        view.setVisibleThirdPanel(true);
        view.setVisibleFourthPanel(true);

        view.setFirstLabelTitle(locale.jiraFilterName());
        view.setSecondLabelTitle(locale.jiraJqlType());
        view.setThirdLabelTitle(locale.jiraDescription());
        view.setFourthLabelTitle(locale.jiraFavourite());
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        redesignViewToCurrentConnector();
    }
}
