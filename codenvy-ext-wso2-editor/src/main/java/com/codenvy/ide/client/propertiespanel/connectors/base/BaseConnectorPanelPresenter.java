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
package com.codenvy.ide.client.propertiespanel.connectors.base;

import com.codenvy.ide.client.elements.connectors.salesforce.BaseSalesforce;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.connectors.base.parameter.ConnectorParameterCallBack;
import com.codenvy.ide.client.propertiespanel.connectors.base.parameter.ParameterPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static com.codenvy.ide.client.elements.connectors.salesforce.BaseSalesforce.AvailableConfigs.EMPTY;
import static com.codenvy.ide.client.elements.connectors.salesforce.BaseSalesforce.AvailableConfigs.SELECT_FROM_CONFIG;
import static com.codenvy.ide.client.elements.connectors.salesforce.BaseSalesforce.ParameterEditorType;

/**
 * The presenter that provides a business logic of base connector properties panel for salesforce connectors.
 *
 * @author Valeriy Svydenko
 */
public class BaseConnectorPanelPresenter extends AbstractPropertiesPanel<BaseSalesforce, BaseConnectorPanelView>
        implements BaseConnectorPanelView.ActionDelegate {

    /** The listener for detecting a moment of changing base property value. */
    public interface BasePropertyChangedListener {
        /**
         * Performs any actions appropriate in response to the user having  changed property value.
         *
         * @param parameterEditorType
         *         editor parameter that was selected
         * @param configRef
         *         configRef parameter that was changed
         */
        void onPropertyChanged(@Nonnull ParameterEditorType parameterEditorType, @Nonnull String configRef);
    }

    private final PropertyTypeManager               propertyTypeManager;
    private final ParameterPresenter                parameterPresenter;
    private final List<BasePropertyChangedListener> listeners;
    private final ConnectorParameterCallBack        callBack;

    @Inject
    public BaseConnectorPanelPresenter(BaseConnectorPanelView view,
                                       PropertyTypeManager propertyTypeManager,
                                       BaseSalesforce base,
                                       ParameterPresenter parameterPresenter) {

        super(view, propertyTypeManager);
        super.setElement(base);

        listeners = new ArrayList<>();

        this.parameterPresenter = parameterPresenter;
        this.propertyTypeManager = propertyTypeManager;

        this.view.addAvailableConfigs(EMPTY.getValue());
        this.view.addAvailableConfigs(SELECT_FROM_CONFIG.getValue());

        this.callBack = new ConnectorParameterCallBack() {
            @Override
            public void onAddressPropertyChanged(@Nonnull String name) {
                if (!name.isEmpty()) {
                    element.addAvailableConfig(name);

                    BaseConnectorPanelPresenter.this.view.setConfigRef(name);
                    BaseConnectorPanelPresenter.this.view.addAvailableConfigs(name);

                    notifyListeners(BaseConnectorPanelPresenter.this.view.getConfigRef());
                }
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        view.setParameterEditorType(propertyTypeManager.getValuesByName(ParameterEditorType.TYPE_NAME));
        view.selectParameterEditorType(element.getParameterEditorType().name());

        view.setConfigRef(element.getConfigRef());

        view.setNewConfig(element.getNewConfig());

        view.setAvailableConfigs(element.getAvailableConfigs());
        view.selectAvailableConfigs(EMPTY.getValue());
    }

    /** {@inheritDoc} */
    @Override
    public void onParameterEditorTypeChanged() {
        element.setParameterEditorType(ParameterEditorType.valueOf(view.getParameterEditorType()));

        notifyListeners(view.getConfigRef());
    }

    /** {@inheritDoc} */
    @Override
    public void onAvailableConfigsChanged() {
        String value = view.getAvailableConfig();

        if (!SELECT_FROM_CONFIG.getValue().equals(value)) {
            view.setConfigRef(value);
            element.setConfigRef(value);

            notifyListeners(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onConfigRefChanged() {
        String configValue = view.getConfigRef();

        element.setConfigRef(configValue);

        notifyListeners(configValue);
    }

    /** {@inheritDoc} */
    @Override
    public void showConfigParameterWindow() {
        parameterPresenter.showDialog(callBack);
    }

    /**
     * Sets new value of configRef to the special place on the view.
     *
     * @param configRef
     *         parameter value
     */
    public void setConfigRef(@Nonnull String configRef) {
        view.setConfigRef(configRef);
    }

    /**
     * Add a listener for detecting a moment of changing selected property value.
     *
     * @param listener
     *         listener that need to be added
     */
    public void addListener(@Nonnull BasePropertyChangedListener listener) {
        listeners.add(listener);
    }

    /** Notify all listeners about changing selected base property value. */
    public void notifyListeners(@Nonnull String configRef) {
        for (BasePropertyChangedListener listener : listeners) {
            listener.onPropertyChanged(ParameterEditorType.valueOf(view.getParameterEditorType()), configRef);
        }
    }
}
