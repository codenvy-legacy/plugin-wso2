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
import com.codenvy.ide.client.elements.connectors.jira.JiraPropertyManager;
import com.codenvy.ide.client.elements.connectors.jira.SearchIssueViewableUsers;
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
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class SearchIssueViewAbleUsersConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<SearchIssueViewableUsers> {

    private final WSO2EditorLocalizationConstant locale;
    private final NameSpaceEditorPresenter       nameSpacePresenter;
    private final AddNameSpacesCallBack          userNameCallBack;
    private final AddNameSpacesCallBack          issueKeyCallBack;
    private final AddNameSpacesCallBack          projectKeyCallBack;
    private final AddNameSpacesCallBack          startAtCallBack;
    private final AddNameSpacesCallBack          maxResultsCallBack;

    @Inject
    public SearchIssueViewAbleUsersConnectorPresenter(WSO2EditorLocalizationConstant locale,
                                                      NameSpaceEditorPresenter nameSpacePresenter,
                                                      GeneralPropertiesPanelView view,
                                                      JiraPropertyManager jiraPropertyManager,
                                                      ParameterPresenter parameterPresenter,
                                                      PropertyTypeManager propertyTypeManager) {
        super(view, jiraPropertyManager, parameterPresenter, propertyTypeManager);

        this.locale = locale;

        this.nameSpacePresenter = nameSpacePresenter;

        this.userNameCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setUserNameNS(nameSpaces);
                element.setUserNameExpression(expression);

                SearchIssueViewAbleUsersConnectorPresenter.this.view.setFirstTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.issueKeyCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setIssueKeyNS(nameSpaces);
                element.setIssueKeyExpression(expression);

                SearchIssueViewAbleUsersConnectorPresenter.this.view.setSecondTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.projectKeyCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setProjectNS(nameSpaces);
                element.setProjectKeyExpression(expression);

                SearchIssueViewAbleUsersConnectorPresenter.this.view.setThirdTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.startAtCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setStartAtNS(nameSpaces);
                element.setStartAtExpression(expression);

                SearchIssueViewAbleUsersConnectorPresenter.this.view.setFourthTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.maxResultsCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setMaxResultsNS(nameSpaces);
                element.setMaxResultsExpression(expression);

                SearchIssueViewAbleUsersConnectorPresenter.this.view.setFifthTextBoxValue(expression);

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
        element.setUserName(view.getFirstTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSecondTextBoxValueChanged() {
        element.setIssueKey(view.getSecondTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onThirdTextBoxValueChanged() {
        element.setProjectKey(view.getThirdTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFourthTextBoxValueChanged() {
        element.setStartAt(view.getFourthTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFifthTextBoxValueChanged() {
        element.setMaxResults(view.getFifthTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFirstButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getUserNameNS(),
                                                    userNameCallBack,
                                                    locale.connectorExpression(),
                                                    element.getUserNameExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onSecondButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getProjectNS(),
                                                    projectKeyCallBack,
                                                    locale.connectorExpression(),
                                                    element.getProjectKeyExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onThirdButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getIssueKeyNS(),
                                                    issueKeyCallBack,
                                                    locale.connectorExpression(),
                                                    element.getIssueKeyExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onFourthButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getStartAtNS(),
                                                    startAtCallBack,
                                                    locale.connectorExpression(),
                                                    element.getStartAtExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onFifthButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getMaxResultsNS(),
                                                    maxResultsCallBack,
                                                    locale.connectorExpression(),
                                                    element.getMaxResultsExpression());
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

        view.setFirstTextBoxValue(isEquals ? element.getUserNameExpression() : element.getUserName());
        view.setSecondTextBoxValue(isEquals ? element.getProjectKeyExpression() : element.getProjectKey());
        view.setThirdTextBoxValue(isEquals ? element.getIssueKeyExpression() : element.getIssueKey());
        view.setFourthTextBoxValue(isEquals ? element.getStartAtExpression() : element.getStartAt());
        view.setFifthTextBoxValue(isEquals ? element.getMaxResultsExpression() : element.getMaxResults());
    }

    private void redesignViewToCurrentConnector() {
        view.setVisibleFirstPanel(true);
        view.setVisibleSecondPanel(true);
        view.setVisibleThirdPanel(true);
        view.setVisibleFourthPanel(true);
        view.setVisibleFifthPanel(true);

        view.setFirstLabelTitle(locale.connectorUsername());
        view.setSecondLabelTitle(locale.jiraIssueKey());
        view.setThirdLabelTitle(locale.jiraProjectKey());
        view.setFourthLabelTitle(locale.jiraStartAt());
        view.setFifthLabelTitle(locale.jiraMaxResults());
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        redesignViewToCurrentConnector();
    }
}
