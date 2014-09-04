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
package com.codenvy.ide.client.propertiespanel.connectors.salesforce.upset;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.connectors.salesforce.GeneralPropertyManager;
import com.codenvy.ide.client.elements.connectors.salesforce.Upset;
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
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NamespacedPropertyEditor;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Upset connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 */
public class UpsetPropertiesPanelPresenter extends GeneralConnectorPanelPresenter<Upset> {

    private final WSO2EditorLocalizationConstant locale;
    private final NameSpaceEditorPresenter       nameSpacePresenter;
    private final AddNameSpacesCallBack          allOrNoneCallBack;
    private final AddNameSpacesCallBack          allowTruncateCallBack;
    private final AddNameSpacesCallBack          externalidCallBack;
    private final AddNameSpacesCallBack          sobjectsCallBack;

    @Inject
    public UpsetPropertiesPanelPresenter(WSO2EditorLocalizationConstant locale,
                                         NameSpaceEditorPresenter nameSpacePresenter,
                                         GeneralConnectorPanelView view,
                                         GeneralPropertyManager generalPropertyManager,
                                         ParameterPresenter parameterPresenter,
                                         PropertyTypeManager propertyTypeManager) {
        super(view, generalPropertyManager, parameterPresenter, propertyTypeManager);

        this.locale = locale;

        this.nameSpacePresenter = nameSpacePresenter;

        this.allOrNoneCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setAllOrNoneNameSpaces(nameSpaces);
                element.setAllOrNone(expression);

                UpsetPropertiesPanelPresenter.this.view.setFirstTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.allowTruncateCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setTruncateNameSpaces(nameSpaces);
                element.setTruncate(expression);

                UpsetPropertiesPanelPresenter.this.view.setSecondTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.externalidCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setExternalIdNameSpaces(nameSpaces);
                element.setExternalId(expression);

                UpsetPropertiesPanelPresenter.this.view.setThirdTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.sobjectsCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setSubjectsNameSpaces(nameSpaces);
                element.setSubjects(expression);

                UpsetPropertiesPanelPresenter.this.view.setFourthTextBoxValue(expression);

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

        view.setFirstTextBoxValue(isEquals ? element.getAllOrNone() : element.getAllOrNoneInline());
        view.setSecondTextBoxValue(isEquals ? element.getTruncate() : element.getTruncateInline());
        view.setThirdTextBoxValue(isEquals ? element.getExternalId() : element.getExternalIdInline());
        view.setFourthTextBoxValue(isEquals ? element.getSubjects() : element.getSubjectsInline());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFirstTextBoxValueChanged() {
        element.setAllOrNoneInline(view.getFirstTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSecondTextBoxValueChanged() {
        element.setTruncateInline(view.getSecondTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onThirdTextBoxValueChanged() {
        element.setExternalIdInline(view.getThirdTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFourthTextBoxValueChanged() {
        element.setSubjectsInline(view.getFourthTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFirstButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getAllOrNoneNameSpaces(),
                                                    allOrNoneCallBack,
                                                    locale.connectorExpression(),
                                                    element.getAllOrNone());
    }

    /** {@inheritDoc} */
    @Override
    public void onSecondButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getTruncateNameSpaces(),
                                                    allowTruncateCallBack,
                                                    locale.connectorExpression(),
                                                    element.getTruncate());
    }

    /** {@inheritDoc} */
    @Override
    public void onThirdButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getExternalIdNameSpaces(),
                                                    externalidCallBack,
                                                    locale.connectorExpression(),
                                                    element.getExternalId());
    }

    /** {@inheritDoc} */
    @Override
    public void onFourthButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getSubjectsNameSpaces(),
                                                    sobjectsCallBack,
                                                    locale.connectorExpression(),
                                                    element.getSubjects());
    }

    private void redesignViewToCurrentConnector() {
        view.setVisibleFirstPanel(true);
        view.setVisibleSecondPanel(true);
        view.setVisibleThirdPanel(true);
        view.setVisibleFourthPanel(true);

        view.setFirstLabelTitle(locale.connectorAllOrNone());
        view.setSecondLabelTitle(locale.connectorAllowFieldTruncate());
        view.setThirdLabelTitle(locale.connectorExternalId());
        view.setFourthLabelTitle(locale.connectorSubjects());
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        redesignViewToCurrentConnector();
        onParameterEditorTypeChanged();
    }
}
