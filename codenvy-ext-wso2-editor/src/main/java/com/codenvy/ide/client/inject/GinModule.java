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
package com.codenvy.ide.client.inject;

import com.codenvy.ide.client.elements.widgets.element.ElementView;
import com.codenvy.ide.client.elements.widgets.element.ElementViewImpl;
import com.codenvy.ide.client.initializers.Initializer;
import com.codenvy.ide.client.initializers.propertiespanel.EndpointsPropertiesPanelInitializer;
import com.codenvy.ide.client.initializers.propertiespanel.GeneralElementsPropertiesPanelInitializer;
import com.codenvy.ide.client.initializers.propertiespanel.GoogleSpreadSheetPropertiesPanelInitializer;
import com.codenvy.ide.client.initializers.propertiespanel.JiraConnectorPropertiesPanelInitializer;
import com.codenvy.ide.client.initializers.propertiespanel.MediatorsPropertiesPanelInitializer;
import com.codenvy.ide.client.initializers.propertiespanel.SalesForceConnectorPropertiesPanelInitializer;
import com.codenvy.ide.client.initializers.propertiespanel.TwitterConnectorPropertiesPanelInitializer;
import com.codenvy.ide.client.initializers.propertytype.CommonPropertyTypeInitializer;
import com.codenvy.ide.client.initializers.propertytype.ConnectorPropertyTypeInitializer;
import com.codenvy.ide.client.initializers.propertytype.EndpointsPropertyTypeInitializer;
import com.codenvy.ide.client.initializers.propertytype.MediatorsPropertyTypeInitializer;
import com.codenvy.ide.client.initializers.validators.ConnectionsValidatorInitializer;
import com.codenvy.ide.client.initializers.validators.InnerElementsValidatorInitializer;
import com.codenvy.ide.client.inject.factories.ElementWidgetFactory;
import com.codenvy.ide.client.inject.factories.PropertiesPanelWidgetFactory;
import com.codenvy.ide.client.inject.factories.ToolbarFactory;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupView;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupViewImpl;
import com.codenvy.ide.client.toolbar.group.ToolbarGroupView;
import com.codenvy.ide.client.toolbar.group.ToolbarGroupViewImpl;
import com.codenvy.ide.client.toolbar.item.ToolbarItemView;
import com.codenvy.ide.client.toolbar.item.ToolbarItemViewImpl;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.gwt.inject.client.multibindings.GinMultibinder;

/**
 * @author Andrey Plotnikov
 */
public class GinModule extends AbstractGinModule {

    /** {@inheritDoc} */
    @Override
    protected void configure() {
        install(new GinFactoryModuleBuilder().implement(ElementView.class, ElementViewImpl.class).build(ElementWidgetFactory.class));

        install(new GinFactoryModuleBuilder().implement(ToolbarGroupView.class, ToolbarGroupViewImpl.class)
                                             .implement(ToolbarItemView.class, ToolbarItemViewImpl.class)
                                             .build(ToolbarFactory.class));

        install(new GinFactoryModuleBuilder().implement(PropertyGroupView.class, PropertyGroupViewImpl.class)
                                             .build(PropertiesPanelWidgetFactory.class));

        GinMultibinder<Initializer> initializers = GinMultibinder.newSetBinder(binder(), Initializer.class);

        initializers.addBinding().to(GeneralElementsPropertiesPanelInitializer.class);
        initializers.addBinding().to(MediatorsPropertiesPanelInitializer.class);
        initializers.addBinding().to(EndpointsPropertiesPanelInitializer.class);
        initializers.addBinding().to(SalesForceConnectorPropertiesPanelInitializer.class);
        initializers.addBinding().to(JiraConnectorPropertiesPanelInitializer.class);
        initializers.addBinding().to(TwitterConnectorPropertiesPanelInitializer.class);
        initializers.addBinding().to(GoogleSpreadSheetPropertiesPanelInitializer.class);

        initializers.addBinding().to(MediatorsPropertyTypeInitializer.class);
        initializers.addBinding().to(EndpointsPropertyTypeInitializer.class);
        initializers.addBinding().to(ConnectorPropertyTypeInitializer.class);
        initializers.addBinding().to(CommonPropertyTypeInitializer.class);

        initializers.addBinding().to(ConnectionsValidatorInitializer.class);
        initializers.addBinding().to(InnerElementsValidatorInitializer.class);
    }

}