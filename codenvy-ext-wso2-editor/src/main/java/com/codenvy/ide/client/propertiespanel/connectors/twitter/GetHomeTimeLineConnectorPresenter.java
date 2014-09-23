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
package com.codenvy.ide.client.propertiespanel.connectors.twitter;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.connectors.twitter.GetHomeTimeLine;
import com.codenvy.ide.client.elements.connectors.twitter.TwitterPropertyManager;
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
public class GetHomeTimeLineConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<GetHomeTimeLine> {

    private final WSO2EditorLocalizationConstant locale;
    private final NameSpaceEditorPresenter       nameSpacePresenter;
    private final AddNameSpacesCallBack          consumerKeyCallBack;
    private final AddNameSpacesCallBack          consumerSecretCallBack;
    private final AddNameSpacesCallBack          accessTokenCallBack;
    private final AddNameSpacesCallBack          accessTokenSecretCallBack;
    private final AddNameSpacesCallBack          countCallBack;
    private final AddNameSpacesCallBack          pageCallBack;
    private final AddNameSpacesCallBack          sinceIdCallBack;
    private final AddNameSpacesCallBack          maxIdCallBack;

    @Inject
    public GetHomeTimeLineConnectorPresenter(WSO2EditorLocalizationConstant locale,
                                             NameSpaceEditorPresenter nameSpacePresenter,
                                             GeneralPropertiesPanelView view,
                                             TwitterPropertyManager twitterPropertyManager,
                                             ParameterPresenter parameterPresenter,
                                             PropertyTypeManager propertyTypeManager) {
        super(view, twitterPropertyManager, parameterPresenter, propertyTypeManager);

        this.locale = locale;

        this.nameSpacePresenter = nameSpacePresenter;

        this.consumerKeyCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setConsumerKeyNS(nameSpaces);
                element.setConsumerKeyExpr(expression);

                GetHomeTimeLineConnectorPresenter.this.view.setFirstTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.consumerSecretCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setConsumerSecretNS(nameSpaces);
                element.setConsumerSecretExpr(expression);

                GetHomeTimeLineConnectorPresenter.this.view.setSecondTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.accessTokenCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setAccessTokenNS(nameSpaces);
                element.setAccessTokenExpr(expression);

                GetHomeTimeLineConnectorPresenter.this.view.setThirdTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.accessTokenSecretCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setAccessTokenSecretNS(nameSpaces);
                element.setAccessTokenSecretExpr(expression);

                GetHomeTimeLineConnectorPresenter.this.view.setFourthTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.countCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setCountNS(nameSpaces);
                element.setCountExpr(expression);

                GetHomeTimeLineConnectorPresenter.this.view.setFifthTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.pageCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setPageNS(nameSpaces);
                element.setPageExpr(expression);

                GetHomeTimeLineConnectorPresenter.this.view.setSixthTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.sinceIdCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setSinceIdNS(nameSpaces);
                element.setSinceIdExpr(expression);

                GetHomeTimeLineConnectorPresenter.this.view.setSeventhTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.maxIdCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setMaxIdNS(nameSpaces);
                element.setMaxIdExpr(expression);

                GetHomeTimeLineConnectorPresenter.this.view.setEighthTextBoxValue(expression);

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
        element.setConsumerKey(view.getFirstTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSecondTextBoxValueChanged() {
        element.setConsumerSecret(view.getSecondTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onThirdTextBoxValueChanged() {
        element.setAccessToken(view.getThirdTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFourthTextBoxValueChanged() {
        element.setAccessTokenSecret(view.getFourthTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFifthTextBoxValueChanged() {
        element.setCount(view.getFifthTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSeventhTextBoxValueChanged() {
        element.setPage(view.getSixthTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSixthTextBoxValueChanged() {
        element.setSinceId(view.getSeventhTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onEighthTextBoxValueChanged() {
        element.setMaxId(view.getEighthTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFirstButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getConsumerKeyNS(),
                                                    consumerKeyCallBack,
                                                    locale.connectorExpression(),
                                                    element.getConsumerKeyExpr());
    }

    /** {@inheritDoc} */
    @Override
    public void onSecondButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getAccessTokenNS(),
                                                    consumerSecretCallBack,
                                                    locale.connectorExpression(),
                                                    element.getAccessTokenExpr());
    }

    /** {@inheritDoc} */
    @Override
    public void onThirdButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getConsumerSecretNS(),
                                                    accessTokenCallBack,
                                                    locale.connectorExpression(),
                                                    element.getConsumerSecretExpr());
    }

    /** {@inheritDoc} */
    @Override
    public void onFourthButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getAccessTokenSecretNS(),
                                                    accessTokenSecretCallBack,
                                                    locale.connectorExpression(),
                                                    element.getAccessTokenSecretExpr());
    }

    /** {@inheritDoc} */
    @Override
    public void onFifthButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getCountNS(),
                                                    countCallBack,
                                                    locale.connectorExpression(),
                                                    element.getCountExpr());
    }

    /** {@inheritDoc} */
    @Override
    public void onSixthButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getPageNS(),
                                                    pageCallBack,
                                                    locale.connectorExpression(),
                                                    element.getPageExpr());
    }

    /** {@inheritDoc} */
    @Override
    public void onSeventhButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getSinceIdNS(),
                                                    sinceIdCallBack,
                                                    locale.connectorExpression(),
                                                    element.getSinceIdExpr());
    }

    /** {@inheritDoc} */
    @Override
    public void onEighthButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getMaxIdNS(),
                                                    maxIdCallBack,
                                                    locale.connectorExpression(),
                                                    element.getMaxIdExpr());
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
        view.setVisibleSeventhButton(isEquals);
        view.setVisibleEighthButton(isEquals);

        view.setEnableFirstTextBox(!isEquals);
        view.setEnableSecondTextBox(!isEquals);
        view.setEnableThirdTextBox(!isEquals);
        view.setEnableFourthTextBox(!isEquals);
        view.setEnableFifthTextBox(!isEquals);
        view.setEnableSixthTextBox(!isEquals);
        view.setEnableSeventhTextBox(!isEquals);
        view.setEnableEighthTextBox(!isEquals);

        view.setFirstTextBoxValue(isEquals ? element.getConsumerKeyExpr() : element.getConsumerKey());
        view.setSecondTextBoxValue(isEquals ? element.getConsumerSecretExpr() : element.getConsumerSecret());
        view.setThirdTextBoxValue(isEquals ? element.getAccessTokenExpr() : element.getAccessToken());
        view.setFourthTextBoxValue(isEquals ? element.getAccessTokenSecretExpr() : element.getAccessTokenSecret());
        view.setFifthTextBoxValue(isEquals ? element.getCountExpr() : element.getCount());
        view.setSixthTextBoxValue(isEquals ? element.getPageExpr() : element.getPage());
        view.setSeventhTextBoxValue(isEquals ? element.getSinceIdExpr() : element.getSinceId());
        view.setEighthTextBoxValue(isEquals ? element.getMaxIdExpr() : element.getMaxId());
    }

    private void redesignViewToCurrentConnector() {
        view.setVisibleFirstPanel(true);
        view.setVisibleSecondPanel(true);
        view.setVisibleThirdPanel(true);
        view.setVisibleFourthPanel(true);
        view.setVisibleFifthPanel(true);
        view.setVisibleSixthPanel(true);
        view.setVisibleSeventhPanel(true);
        view.setVisibleEighthPanel(true);

        view.setFirstLabelTitle(locale.twitterConsumerKey());
        view.setSecondLabelTitle(locale.twitterConsumerSecret());
        view.setThirdLabelTitle(locale.twitterAccessToken());
        view.setFourthLabelTitle(locale.twitterAccessTokenSecret());
        view.setFifthLabelTitle(locale.twitterCount());
        view.setSixthLabelTitle(locale.twitterPage());
        view.setSeventhLabelTitle(locale.twitterSinceId());
        view.setEighthLabelTitle(locale.twitterMaxId());
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        redesignViewToCurrentConnector();
    }
}
