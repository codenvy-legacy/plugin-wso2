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
package com.codenvy.ide.client.propertiespanel.connectors.salesforce.logout;

import com.codenvy.ide.client.elements.connectors.salesforce.LogOut;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.connectors.base.BaseConnectorPanelPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.connectors.salesforce.GeneralProperty.ParameterEditorType;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Logout connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 */
public class LogOutPresenter extends AbstractPropertiesPanel<LogOut, LogOutView>
        implements BaseConnectorPanelPresenter.BasePropertyChangedListener {

    private final BaseConnectorPanelPresenter baseConnectorPresenter;

    @Inject
    public LogOutPresenter(BaseConnectorPanelPresenter baseConnectorPresenter,
                           LogOutView view,
                           PropertyTypeManager propertyTypeManager) {

        super(view, propertyTypeManager);

        this.baseConnectorPresenter = baseConnectorPresenter;
        this.baseConnectorPresenter.addListener(this);
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertyChanged(@Nonnull ParameterEditorType parameterEditorType, @Nonnull String configRef) {
        element.setParameterEditorType(parameterEditorType);
        element.setConfigRef(configRef);

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        view.setGeneralPanel(baseConnectorPresenter);

        baseConnectorPresenter.setConfigRef(element.getConfigRef());
        baseConnectorPresenter.setParameterEditorType(element.getParameterEditorType());
    }
}
