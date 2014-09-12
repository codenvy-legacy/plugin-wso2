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
import com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter;
import com.codenvy.ide.client.elements.connectors.twitter.TwitterPropertyManager;
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
 * @author Dmitry Shnurenko
 */
public class SearchTwitterConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<SearchTwitter> {

    private final WSO2EditorLocalizationConstant locale;
    private final NameSpaceEditorPresenter       nameSpacePresenter;
    private final AddNameSpacesCallBack          searchCallBack;
    private final AddNameSpacesCallBack          langCallBack;
    private final AddNameSpacesCallBack          localeCallBack;
    private final AddNameSpacesCallBack          maxIdCallBack;
    private final AddNameSpacesCallBack          sinceCallBack;
    private final AddNameSpacesCallBack          sinceIdCallBack;
    private final AddNameSpacesCallBack          geocodeCallBack;
    private final AddNameSpacesCallBack          radiusCallBack;
    private final AddNameSpacesCallBack          unitCallBack;
    private final AddNameSpacesCallBack          untilCallBack;
    private final AddNameSpacesCallBack          countCallBack;

    @Inject
    public SearchTwitterConnectorPresenter(WSO2EditorLocalizationConstant locale,
                                           NameSpaceEditorPresenter nameSpacePresenter,
                                           GeneralPropertiesPanelView view,
                                           TwitterPropertyManager twitterPropertyManager,
                                           ParameterPresenter parameterPresenter,
                                           PropertyTypeManager propertyTypeManager) {
        super(view, twitterPropertyManager, parameterPresenter, propertyTypeManager);

        this.locale = locale;

        this.nameSpacePresenter = nameSpacePresenter;

        this.searchCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setSearchNS(nameSpaces);
                element.setSearchExpr(expression);

                SearchTwitterConnectorPresenter.this.view.setFirstTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.langCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setLangNS(nameSpaces);
                element.setLangExpr(expression);

                SearchTwitterConnectorPresenter.this.view.setSecondTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.localeCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setLocaleNS(nameSpaces);
                element.setLocaleExpr(expression);

                SearchTwitterConnectorPresenter.this.view.setThirdTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.maxIdCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setMaxIdNS(nameSpaces);
                element.setMaxIdExpr(expression);

                SearchTwitterConnectorPresenter.this.view.setFourthTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.sinceCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setSinceNS(nameSpaces);
                element.setSinceExpr(expression);

                SearchTwitterConnectorPresenter.this.view.setFifthTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.sinceIdCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setSinceIdNS(nameSpaces);
                element.setSinceIdExpr(expression);

                SearchTwitterConnectorPresenter.this.view.setSixthTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.geocodeCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setGeocodeNS(nameSpaces);
                element.setGeocodeExpr(expression);

                SearchTwitterConnectorPresenter.this.view.setSeventhTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.radiusCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setRadiusNS(nameSpaces);
                element.setRadiusExpr(expression);

                SearchTwitterConnectorPresenter.this.view.setEighthTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.unitCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setUnitNS(nameSpaces);
                element.setUnitExpr(expression);

                SearchTwitterConnectorPresenter.this.view.setNinthTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.untilCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setUntilNS(nameSpaces);
                element.setUntilExpr(expression);

                SearchTwitterConnectorPresenter.this.view.setTenthTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.countCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setCountNS(nameSpaces);
                element.setCountExpr(expression);

                SearchTwitterConnectorPresenter.this.view.setEleventhTextBoxValue(expression);

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
        view.setVisibleSixthButton(isEquals);
        view.setVisibleSeventhButton(isEquals);
        view.setVisibleEighthButton(isEquals);
        view.setVisibleNinthButton(isEquals);
        view.setVisibleTenthButton(isEquals);
        view.setVisibleEleventhButton(isEquals);

        view.setEnableFirstTextBox(!isEquals);
        view.setEnableSecondTextBox(!isEquals);
        view.setEnableThirdTextBox(!isEquals);
        view.setEnableFourthTextBox(!isEquals);
        view.setEnableFifthTextBox(!isEquals);
        view.setEnableSixthTextBox(!isEquals);
        view.setEnableSeventhTextBox(!isEquals);
        view.setEnableEighthTextBox(!isEquals);
        view.setEnableNinthTextBox(!isEquals);
        view.setEnableTenthTextBox(!isEquals);
        view.setEnableEleventhTextBox(!isEquals);

        view.setFirstTextBoxValue(isEquals ? element.getSearchExpr() : element.getSearch());
        view.setSecondTextBoxValue(isEquals ? element.getLangExpr() : element.getLang());
        view.setThirdTextBoxValue(isEquals ? element.getLocaleExpr() : element.getLocale());
        view.setFourthTextBoxValue(isEquals ? element.getMaxIdExpr() : element.getMaxId());
        view.setFifthTextBoxValue(isEquals ? element.getSinceExpr() : element.getSince());
        view.setSixthTextBoxValue(isEquals ? element.getSinceIdExpr() : element.getSinceId());
        view.setSeventhTextBoxValue(isEquals ? element.getGeocodeExpr() : element.getGeocode());
        view.setEighthTextBoxValue(isEquals ? element.getRadiusExpr() : element.getRadius());
        view.setNinthTextBoxValue(isEquals ? element.getUnitExpr() : element.getUnit());
        view.setFirstTextBoxValue(isEquals ? element.getUntilExpr() : element.getUntil());
        view.setEleventhTextBoxValue(isEquals ? element.getCountExpr() : element.getCount());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFirstTextBoxValueChanged() {
        element.setSearch(view.getFirstTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSecondTextBoxValueChanged() {
        element.setLang(view.getSecondTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onThirdTextBoxValueChanged() {
        element.setLocale(view.getThirdTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFourthTextBoxValueChanged() {
        element.setMaxId(view.getFourthTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFifthTextBoxValueChanged() {
        element.setSince(view.getFifthTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSixthTextBoxValueChanged() {
        element.setSinceId(view.getSixthTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSeventhTextBoxValueChanged() {
        element.setGeocode(view.getSeventhTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onEighthTextBoxValueChanged() {
        element.setRadius(view.getEighthTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onNinthTextBoxValueChanged() {
        element.setUnit(view.getNinthTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onTenthTextBoxValueChanged() {
        element.setUntil(view.getTenthTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onEleventhTextBoxValueChanged() {
        element.setCount(view.getEleventhTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFirstButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getSearchNS(),
                                                    searchCallBack,
                                                    locale.connectorExpression(),
                                                    element.getSearchExpr());
    }

    /** {@inheritDoc} */
    @Override
    public void onSecondButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getLangNS(),
                                                    langCallBack,
                                                    locale.connectorExpression(),
                                                    element.getLangExpr());
    }

    /** {@inheritDoc} */
    @Override
    public void onThirdButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getLocaleNS(),
                                                    localeCallBack,
                                                    locale.connectorExpression(),
                                                    element.getLocaleExpr());
    }

    /** {@inheritDoc} */
    @Override
    public void onFourthButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getMaxIdNS(),
                                                    maxIdCallBack,
                                                    locale.connectorExpression(),
                                                    element.getMaxIdExpr());
    }

    /** {@inheritDoc} */
    @Override
    public void onFifthButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getSinceNS(),
                                                    sinceCallBack,
                                                    locale.connectorExpression(),
                                                    element.getSinceExpr());
    }

    /** {@inheritDoc} */
    @Override
    public void onSixthButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getSinceIdNS(),
                                                    sinceIdCallBack,
                                                    locale.connectorExpression(),
                                                    element.getSinceIdExpr());
    }

    /** {@inheritDoc} */
    @Override
    public void onSeventhButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getGeocodeNS(),
                                                    geocodeCallBack,
                                                    locale.connectorExpression(),
                                                    element.getGeocodeExpr());
    }

    /** {@inheritDoc} */
    @Override
    public void onEighthButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getRadiusNS(),
                                                    radiusCallBack,
                                                    locale.connectorExpression(),
                                                    element.getRadiusExpr());
    }

    /** {@inheritDoc} */
    @Override
    public void onNinthButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getUnitNS(),
                                                    unitCallBack,
                                                    locale.connectorExpression(),
                                                    element.getUnitExpr());
    }

    /** {@inheritDoc} */
    @Override
    public void onTenthButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getUntilNS(),
                                                    untilCallBack,
                                                    locale.connectorExpression(),
                                                    element.getUntilExpr());
    }

    /** {@inheritDoc} */
    @Override
    public void onEleventhButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getCountNS(),
                                                    countCallBack,
                                                    locale.connectorExpression(),
                                                    element.getCountExpr());
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
        view.setVisibleNinthPanel(true);
        view.setVisibleTenthPanel(true);
        view.setVisibleEleventhPanel(true);

        view.setFirstLabelTitle(locale.twitterSearch());
        view.setSecondLabelTitle(locale.twitterLang());
        view.setThirdLabelTitle(locale.twitterLocale());
        view.setFourthLabelTitle(locale.twitterMaxId());
        view.setFifthLabelTitle(locale.twitterSince());
        view.setSixthLabelTitle(locale.twitterSinceId());
        view.setSeventhLabelTitle(locale.twitterGeocode());
        view.setEighthLabelTitle(locale.twitterRadius());
        view.setNinthLabelTitle(locale.twitterUnit());
        view.setTenthLabelTitle(locale.twitterUntil());
        view.setEleventhLabelTitle(locale.twitterCount());
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        redesignViewToCurrentConnector();
        onParameterEditorTypeChanged();
    }
}
