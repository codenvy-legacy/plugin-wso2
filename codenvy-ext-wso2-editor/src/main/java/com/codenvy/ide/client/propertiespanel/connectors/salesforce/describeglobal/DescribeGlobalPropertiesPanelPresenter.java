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
package com.codenvy.ide.client.propertiespanel.connectors.salesforce.describeglobal;

import com.codenvy.ide.client.elements.connectors.salesforce.DescribeGlobal;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.connectors.base.BaseConnectorPanelPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.connectors.salesforce.GeneralProperty.ParameterEditorType;

/**
 * The presenter that provides a business logic of 'DescribeGlobal' connector properties panel for salesforce connectors.
 *
 * @author Valeriy Svydenko
 */
public class DescribeGlobalPropertiesPanelPresenter extends AbstractPropertiesPanel<DescribeGlobal, DescribeGlobalPropertiesPanelView>
        implements BaseConnectorPanelPresenter.BasePropertyChangedListener {

    private final BaseConnectorPanelPresenter baseConnectorPresenter;

    @Inject
    public DescribeGlobalPropertiesPanelPresenter(DescribeGlobalPropertiesPanelView view,
                                                  PropertyTypeManager propertyTypeManager,
                                                  BaseConnectorPanelPresenter baseConnectorPresenter) {
        super(view, propertyTypeManager);

        this.baseConnectorPresenter = baseConnectorPresenter;
        this.baseConnectorPresenter.addListener(this);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        view.addBaseConnector(baseConnectorPresenter);

        baseConnectorPresenter.setConfigRef(element.getConfigRef());
        baseConnectorPresenter.selectParameterEditorType(element.getParameterEditorType());
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertyChanged(@Nonnull ParameterEditorType parameterEditorType, @Nonnull String configRef) {
        element.setParameterEditorType(parameterEditorType);
        element.setConfigRef(configRef);

        notifyListeners();
    }
}