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

import com.codenvy.ide.client.mvp.AbstractView;
import com.codenvy.ide.client.propertiespanel.connectors.base.BaseConnectorPanelPresenter;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;

/**
 * The view of {@link DescribeGlobalPropertiesPanelPresenter}
 *
 * @author Valeriy Svydenko
 */
@ImplementedBy(DescribeGlobalPropertiesPanelViewImpl.class)
public abstract class DescribeGlobalPropertiesPanelView extends AbstractView<AbstractView.ActionDelegate> {

    /**
     * Adds base elements of connector's property panel.
     *
     * @param base
     *         presenter of base connector
     */
    public abstract void addBaseConnector(@Nonnull BaseConnectorPanelPresenter base);

}
