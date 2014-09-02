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

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.propertiespanel.connectors.base.BaseConnectorPanelPresenter;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;

/**
 * Provides a graphical representation of DescribeGlobal connector property panel for editing property of DescribeGlobal connector.
 *
 * @author Valeriy Svydenko
 */
public class DescribeGlobalPropertiesPanelViewImpl extends DescribeGlobalPropertiesPanelView {

    @Singleton
    interface DescribeGlobalPropertiesPanelViewImplUiBinder extends UiBinder<Widget, DescribeGlobalPropertiesPanelViewImpl> {
    }

    @UiField
    SimplePanel baseConnector;

    @UiField(provided = true)
    final EditorResources                res;
    @UiField(provided = true)
    final WSO2EditorLocalizationConstant loc;

    @Inject
    public DescribeGlobalPropertiesPanelViewImpl(DescribeGlobalPropertiesPanelViewImplUiBinder ourUiBinder,
                                                 EditorResources res,
                                                 WSO2EditorLocalizationConstant loc) {

        this.res = res;
        this.loc = loc;

        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Override
    public void addBaseConnector(@Nonnull BaseConnectorPanelPresenter base) {
        base.go(baseConnector);
    }

}
