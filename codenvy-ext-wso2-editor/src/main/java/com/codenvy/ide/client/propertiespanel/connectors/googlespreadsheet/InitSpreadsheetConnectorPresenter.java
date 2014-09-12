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
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.InitSpreadsheet;
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
public class InitSpreadsheetConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<InitSpreadsheet> {

    private final WSO2EditorLocalizationConstant locale;
    private final NameSpaceEditorPresenter       nameSpacePresenter;
    private final AddNameSpacesCallBack          oauthConsumerKeyCallBack;
    private final AddNameSpacesCallBack          oauthConsumerSecretCallBack;
    private final AddNameSpacesCallBack          oauthAccessTokenCallBack;
    private final AddNameSpacesCallBack          oauthAccessTokenSecretCallBack;

    @Inject
    public InitSpreadsheetConnectorPresenter(WSO2EditorLocalizationConstant locale,
                                             NameSpaceEditorPresenter nameSpacePresenter,
                                             GeneralPropertiesPanelView view,
                                             GoogleSpreadsheetPropertyManager googleSpreadsheetPropertyManager,
                                             ParameterPresenter parameterPresenter,
                                             PropertyTypeManager propertyTypeManager) {
        super(view, googleSpreadsheetPropertyManager, parameterPresenter, propertyTypeManager);

        this.locale = locale;

        this.nameSpacePresenter = nameSpacePresenter;

        this.oauthConsumerKeyCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setOauthConsumerKeyNS(nameSpaces);
                element.setOauthConsumerKeyExpression(expression);

                InitSpreadsheetConnectorPresenter.this.view.setFirstTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.oauthConsumerSecretCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setOauthConsumerSecretNS(nameSpaces);
                element.setOauthConsumerSecretExpression(expression);

                InitSpreadsheetConnectorPresenter.this.view.setSecondTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.oauthAccessTokenCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setOauthAccessTokenNS(nameSpaces);
                element.setOauthAccessTokenExpression(expression);

                InitSpreadsheetConnectorPresenter.this.view.setThirdTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.oauthAccessTokenSecretCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setOauthAccessTokenSecretNS(nameSpaces);
                element.setOauthAccessTokenSecretExpression(expression);

                InitSpreadsheetConnectorPresenter.this.view.setFourthTextBoxValue(expression);

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

        view.setFirstTextBoxValue(isEquals ? element.getOauthConsumerKeyExpression() : element.getOauthConsumerKey());
        view.setSecondTextBoxValue(isEquals ? element.getOauthConsumerSecretExpression() : element.getOauthConsumerSecret());
        view.setThirdTextBoxValue(isEquals ? element.getOauthAccessTokenExpression() : element.getOauthAccessToken());
        view.setFourthTextBoxValue(isEquals ? element.getOauthAccessTokenSecretExpression() : element.getOauthAccessTokenSecret());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFirstTextBoxValueChanged() {
        element.setOauthConsumerKey(view.getFirstTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSecondTextBoxValueChanged() {
        element.setOauthConsumerSecret(view.getSecondTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onThirdTextBoxValueChanged() {
        element.setOauthAccessToken(view.getThirdTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFourthTextBoxValueChanged() {
        element.setOauthAccessTokenSecret(view.getFourthTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFirstButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getOauthConsumerKeyNS(),
                                                    oauthConsumerKeyCallBack,
                                                    locale.connectorExpression(),
                                                    element.getOauthConsumerKeyExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onSecondButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getOauthConsumerSecretNS(),
                                                    oauthConsumerSecretCallBack,
                                                    locale.connectorExpression(),
                                                    element.getOauthConsumerSecretExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onThirdButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getOauthAccessTokenNS(),
                                                    oauthAccessTokenCallBack,
                                                    locale.connectorExpression(),
                                                    element.getOauthAccessTokenExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onFourthButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getOauthAccessTokenSecretNS(),
                                                    oauthAccessTokenSecretCallBack,
                                                    locale.connectorExpression(),
                                                    element.getOauthAccessTokenSecretExpression());
    }

    private void redesignViewToCurrentConnector() {
        view.setVisibleFirstPanel(true);
        view.setVisibleSecondPanel(true);
        view.setVisibleThirdPanel(true);
        view.setVisibleFourthPanel(true);

        view.setFirstLabelTitle(locale.spreadsheetInitOauthConsumerKey());
        view.setSecondLabelTitle(locale.spreadsheetInitOauthConsumerSecret());
        view.setThirdLabelTitle(locale.spreadsheetInitOauthAccessToken());
        view.setFourthLabelTitle(locale.spreadsheetInitOauthAccessTokenSecret());
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        redesignViewToCurrentConnector();
        onParameterEditorTypeChanged();
    }
}
