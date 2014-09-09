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
package com.codenvy.ide.client.propertiespanel.connectors.base;

import com.codenvy.ide.client.elements.connectors.AbstractConnector;
import com.codenvy.ide.client.elements.connectors.salesforce.GeneralPropertyManager;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.connectors.base.parameter.ConnectorParameterCallBack;
import com.codenvy.ide.client.propertiespanel.connectors.base.parameter.ParameterPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.AvailableConfigs.SELECT_FROM_CONFIG;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties. This business logic is general for all connectors.
 *
 * @author Dmitry Shnurenko
 */
public abstract class GeneralConnectorPanelPresenter<T extends AbstractConnector>
        extends AbstractPropertiesPanel<T, GeneralConnectorPanelView>
        implements GeneralConnectorPanelView.ActionDelegate, GeneralPropertyManager.GeneralPropertyListener {

    private final ParameterPresenter         parameterPresenter;
    private final GeneralPropertyManager     generalPropertyManager;
    private final ConnectorParameterCallBack parameterCallBack;

    protected GeneralConnectorPanelPresenter(@Nonnull GeneralConnectorPanelView view,
                                             @Nonnull GeneralPropertyManager generalPropertyManager,
                                             @Nonnull ParameterPresenter parameterPresenter,
                                             @Nonnull PropertyTypeManager propertyTypeManager) {
        super(view, propertyTypeManager);

        this.generalPropertyManager = generalPropertyManager;
        this.generalPropertyManager.addListener(this);

        this.parameterPresenter = parameterPresenter;

        this.parameterCallBack = new ConnectorParameterCallBack() {
            @Override
            public void onAddressPropertyChanged(@Nonnull String name) {
                GeneralConnectorPanelPresenter.this.generalPropertyManager.addNewConfig(name);
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onParameterEditorTypeChanged() {
        element.setParameterEditorType(ParameterEditorType.valueOf(view.getParameterEditorType()));

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onAvailableConfigsChanged() {
        String value = view.getAvailableConfig();

        if (!SELECT_FROM_CONFIG.getValue().equals(value)) {
            view.setConfigRef(value);
            element.setConfigRef(value);
        }

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onGeneralPropertyChanged(@Nonnull String property) {
        view.addAvailableConfigs(property);
    }

    /** {@inheritDoc} */
    @Override
    public void onConfigRefChanged() {
        element.setConfigRef(view.getConfigRef());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onCreateNewConfigBtnClicked() {
        parameterPresenter.showDialog(parameterCallBack);
    }

    /** {@inheritDoc} */
    @Override
    public void onFirstTextBoxValueChanged() {

    }

    /** {@inheritDoc} */
    @Override
    public void onSecondTextBoxValueChanged() {

    }

    /** {@inheritDoc} */
    @Override
    public void onThirdTextBoxValueChanged() {

    }

    /** {@inheritDoc} */
    @Override
    public void onFourthTextBoxValueChanged() {

    }

    /** {@inheritDoc} */
    @Override
    public void onFirstButtonClicked() {

    }

    /** {@inheritDoc} */
    @Override
    public void onSecondButtonClicked() {

    }

    /** {@inheritDoc} */
    @Override
    public void onThirdButtonClicked() {

    }

    /** {@inheritDoc} */
    @Override
    public void onFourthButtonClicked() {

    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        view.setConfigRef(element.getConfigRef());

        view.setAvailableConfigs(generalPropertyManager.getAvailableConfigs());
        view.setParameterEditorType(propertyTypeManager.getValuesByName(ParameterEditorType.TYPE_NAME));

        view.selectParameterEditorType(element.getParameterEditorType());
    }
}
