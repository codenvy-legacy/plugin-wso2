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
import com.codenvy.ide.client.elements.connectors.jira.DoTransition;
import com.codenvy.ide.client.elements.connectors.jira.JiraPropertyManager;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.connectors.base.AbstractConnectorPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.base.GeneralPropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.connectors.base.parameter.ParameterPresenter;
import com.codenvy.ide.client.propertiespanel.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.propertyconfig.AddNameSpacesCallBack;
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
public class DoTransitionConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<DoTransition> {

    private final WSO2EditorLocalizationConstant locale;
    private final NameSpaceEditorPresenter       nameSpacePresenter;
    private final AddNameSpacesCallBack          issueIdOrKeyCallBack;
    private final AddNameSpacesCallBack          updateCommentCallBack;
    private final AddNameSpacesCallBack          updateAssigneeCallBack;
    private final AddNameSpacesCallBack          resolutionCallBack;
    private final AddNameSpacesCallBack          transitionIdCallBack;

    @Inject
    public DoTransitionConnectorPresenter(WSO2EditorLocalizationConstant locale,
                                          NameSpaceEditorPresenter nameSpacePresenter,
                                          GeneralPropertiesPanelView view,
                                          JiraPropertyManager jiraPropertyManager,
                                          ParameterPresenter parameterPresenter,
                                          PropertyTypeManager propertyTypeManager) {
        super(view, jiraPropertyManager, parameterPresenter, propertyTypeManager);

        this.locale = locale;

        this.nameSpacePresenter = nameSpacePresenter;

        this.issueIdOrKeyCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setIssieIOrKeyNS(nameSpaces);
                element.setIssueIdOrKeyExpression(expression);

                DoTransitionConnectorPresenter.this.view.setFirstTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.updateCommentCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setUpdateCommentNS(nameSpaces);
                element.setUpdateCommentExpression(expression);

                DoTransitionConnectorPresenter.this.view.setSecondTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.updateAssigneeCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setUpdateAssigneeNS(nameSpaces);
                element.setUpdateAssigneeExpression(expression);

                DoTransitionConnectorPresenter.this.view.setThirdTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.resolutionCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setResolutionNS(nameSpaces);
                element.setResolutionExpression(expression);

                DoTransitionConnectorPresenter.this.view.setFourthTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.transitionIdCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setTransitionIdNS(nameSpaces);
                element.setTransitionIdExpression(expression);

                DoTransitionConnectorPresenter.this.view.setFifthTextBoxValue(expression);

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
        view.setVisibleFifthButton(isEquals);

        view.setEnableFirstTextBox(!isEquals);
        view.setEnableSecondTextBox(!isEquals);
        view.setEnableThirdTextBox(!isEquals);
        view.setEnableFourthTextBox(!isEquals);
        view.setEnableFifthTextBox(!isEquals);

        view.setFirstTextBoxValue(isEquals ? element.getIssueIdOrKeyExpression() : element.getIssueIdOrKey());
        view.setSecondTextBoxValue(isEquals ? element.getUpdateCommentExpression() : element.getUpdateComment());
        view.setThirdTextBoxValue(isEquals ? element.getUpdateAssigneeExpression() : element.getUpdateAssignee());
        view.setFourthTextBoxValue(isEquals ? element.getResolutionExpression() : element.getResolution());
        view.setFifthTextBoxValue(isEquals ? element.getTransitionIdExpression() : element.getTransitionId());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFirstTextBoxValueChanged() {
        element.setIssueIdOrKey(view.getFirstTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSecondTextBoxValueChanged() {
        element.setUpdateComment(view.getSecondTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onThirdTextBoxValueChanged() {
        element.setUpdateAssignee(view.getThirdTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFourthTextBoxValueChanged() {
        element.setResolution(view.getFourthTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFifthTextBoxValueChanged() {
        element.setTransitionId(view.getFifthTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFirstButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getIssieIOrKeyNS(),
                                                    issueIdOrKeyCallBack,
                                                    locale.connectorExpression(),
                                                    element.getIssueIdOrKeyExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onSecondButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getUpdateCommentNS(),
                                                    updateCommentCallBack,
                                                    locale.connectorExpression(),
                                                    element.getUpdateCommentExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onThirdButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getUpdateAssigneeNS(),
                                                    updateAssigneeCallBack,
                                                    locale.connectorExpression(),
                                                    element.getUpdateAssigneeExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onFourthButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getResolutionNS(),
                                                    resolutionCallBack,
                                                    locale.connectorExpression(),
                                                    element.getResolutionExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onFifthButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getTransitionIdNS(),
                                                    transitionIdCallBack,
                                                    locale.connectorExpression(),
                                                    element.getTransitionIdExpression());
    }

    private void redesignViewToCurrentConnector() {
        view.setVisibleFirstPanel(true);
        view.setVisibleSecondPanel(true);
        view.setVisibleThirdPanel(true);
        view.setVisibleFourthPanel(true);
        view.setVisibleFifthPanel(true);

        view.setFirstLabelTitle(locale.jiraIssueIdOrKey());
        view.setSecondLabelTitle(locale.jiraUpdateComment());
        view.setThirdLabelTitle(locale.jiraUpdateAssignee());
        view.setFourthLabelTitle(locale.jiraResolution());
        view.setFifthLabelTitle(locale.jiraTransitionId());
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        redesignViewToCurrentConnector();
        onParameterEditorTypeChanged();
    }
}
