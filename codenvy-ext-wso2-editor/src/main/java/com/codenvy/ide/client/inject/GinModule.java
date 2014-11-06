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

import com.codenvy.ide.client.elements.widgets.branch.BranchView;
import com.codenvy.ide.client.elements.widgets.branch.BranchViewImpl;
import com.codenvy.ide.client.elements.widgets.element.ElementView;
import com.codenvy.ide.client.elements.widgets.element.ElementViewImpl;
import com.codenvy.ide.client.initializers.Initializer;
import com.codenvy.ide.client.initializers.creators.EndpointCreatorsInitializer;
import com.codenvy.ide.client.initializers.creators.GoogleSpreadSheetConnectorCreatorsInitializer;
import com.codenvy.ide.client.initializers.creators.JiraConnectorCreatorsInitializer;
import com.codenvy.ide.client.initializers.creators.MediatorCreatorsInitializer;
import com.codenvy.ide.client.initializers.creators.SalesForceConnectorCreatorsInitializer;
import com.codenvy.ide.client.initializers.creators.TwitterConnectorCreatorsInitializer;
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
import com.codenvy.ide.client.initializers.toolbar.EndpointToolbarInitializer;
import com.codenvy.ide.client.initializers.toolbar.GoogleSpreadSheetToolbarInitializer;
import com.codenvy.ide.client.initializers.toolbar.JiraConnectorToolbarInitializer;
import com.codenvy.ide.client.initializers.toolbar.MediatorToolbarInitializer;
import com.codenvy.ide.client.initializers.toolbar.SalesForceConnectorToolbarInitializer;
import com.codenvy.ide.client.initializers.toolbar.TwitterToolbarInitializer;
import com.codenvy.ide.client.initializers.validators.ConnectionsValidatorInitializer;
import com.codenvy.ide.client.initializers.validators.InnerElementsValidatorInitializer;
import com.codenvy.ide.client.inject.factories.ElementWidgetFactory;
import com.codenvy.ide.client.inject.factories.PropertiesGroupFactory;
import com.codenvy.ide.client.inject.factories.ToolbarFactory;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupView;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupViewImpl;
import com.codenvy.ide.client.toolbar.group.ToolbarGroupView;
import com.codenvy.ide.client.toolbar.group.ToolbarGroupViewImpl;
import com.codenvy.ide.client.toolbar.item.ToolbarItemView;
import com.codenvy.ide.client.toolbar.item.ToolbarItemViewImpl;
import com.codenvy.ide.ui.dialogs.DialogFactory;
import com.codenvy.ide.ui.dialogs.confirm.ConfirmDialog;
import com.codenvy.ide.ui.dialogs.confirm.ConfirmDialogPresenter;
import com.codenvy.ide.ui.dialogs.confirm.ConfirmDialogView;
import com.codenvy.ide.ui.dialogs.confirm.ConfirmDialogViewImpl;
import com.codenvy.ide.ui.dialogs.input.InputDialog;
import com.codenvy.ide.ui.dialogs.input.InputDialogPresenter;
import com.codenvy.ide.ui.dialogs.input.InputDialogView;
import com.codenvy.ide.ui.dialogs.input.InputDialogViewImpl;
import com.codenvy.ide.ui.dialogs.message.MessageDialog;
import com.codenvy.ide.ui.dialogs.message.MessageDialogPresenter;
import com.codenvy.ide.ui.dialogs.message.MessageDialogView;
import com.codenvy.ide.ui.dialogs.message.MessageDialogViewImpl;
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
        install(new GinFactoryModuleBuilder().implement(ElementView.class, ElementViewImpl.class)
                                             .implement(BranchView.class, BranchViewImpl.class)
                                             .build(ElementWidgetFactory.class));

        install(new GinFactoryModuleBuilder().implement(ToolbarGroupView.class, ToolbarGroupViewImpl.class)
                                             .implement(ToolbarItemView.class, ToolbarItemViewImpl.class)
                                             .build(ToolbarFactory.class));

        install(new GinFactoryModuleBuilder().implement(PropertyGroupView.class, PropertyGroupViewImpl.class)
                                             .build(PropertiesGroupFactory.class));

        GinMultibinder<Initializer> initializers = GinMultibinder.newSetBinder(binder(), Initializer.class);

        configurePropertiesPanels(initializers);
        configurePropertyTypes(initializers);
        configureValidators(initializers);
        configureCreators(initializers);
        configureToolbar(initializers);

        /*
         * TODO: this configuration was copied from CoreGinModule from ide-core project. It is important because this gin module is
         *       separated from IDE injection circle. In order to update version of IDE one has to review this configuration and
         *       synchronize it.
         */
        bind(MessageDialogView.class).to(MessageDialogViewImpl.class);
        bind(ConfirmDialogView.class).to(ConfirmDialogViewImpl.class);
        bind(InputDialogView.class).to(InputDialogViewImpl.class);

        install(new GinFactoryModuleBuilder().implement(MessageDialog.class, MessageDialogPresenter.class)
                                             .implement(ConfirmDialog.class, ConfirmDialogPresenter.class)
                                             .implement(InputDialog.class, InputDialogPresenter.class)
                                             .build(DialogFactory.class));
    }

    private void configurePropertiesPanels(GinMultibinder<Initializer> initializers) {
        initializers.addBinding().to(GeneralElementsPropertiesPanelInitializer.class);
        initializers.addBinding().to(MediatorsPropertiesPanelInitializer.class);
        initializers.addBinding().to(EndpointsPropertiesPanelInitializer.class);
        initializers.addBinding().to(SalesForceConnectorPropertiesPanelInitializer.class);
        initializers.addBinding().to(JiraConnectorPropertiesPanelInitializer.class);
        initializers.addBinding().to(TwitterConnectorPropertiesPanelInitializer.class);
        initializers.addBinding().to(GoogleSpreadSheetPropertiesPanelInitializer.class);
    }

    private void configurePropertyTypes(GinMultibinder<Initializer> initializers) {
        initializers.addBinding().to(MediatorsPropertyTypeInitializer.class);
        initializers.addBinding().to(EndpointsPropertyTypeInitializer.class);
        initializers.addBinding().to(ConnectorPropertyTypeInitializer.class);
        initializers.addBinding().to(CommonPropertyTypeInitializer.class);
    }

    private void configureValidators(GinMultibinder<Initializer> initializers) {
        initializers.addBinding().to(ConnectionsValidatorInitializer.class);
        initializers.addBinding().to(InnerElementsValidatorInitializer.class);
    }

    private void configureCreators(GinMultibinder<Initializer> initializers) {
        initializers.addBinding().to(MediatorCreatorsInitializer.class);
        initializers.addBinding().to(EndpointCreatorsInitializer.class);
        initializers.addBinding().to(SalesForceConnectorCreatorsInitializer.class);
        initializers.addBinding().to(JiraConnectorCreatorsInitializer.class);
        initializers.addBinding().to(TwitterConnectorCreatorsInitializer.class);
        initializers.addBinding().to(GoogleSpreadSheetConnectorCreatorsInitializer.class);
    }

    private void configureToolbar(GinMultibinder<Initializer> initializers) {
        initializers.addBinding().to(MediatorToolbarInitializer.class);
        initializers.addBinding().to(EndpointToolbarInitializer.class);
        initializers.addBinding().to(SalesForceConnectorToolbarInitializer.class);
        initializers.addBinding().to(JiraConnectorToolbarInitializer.class);
        initializers.addBinding().to(TwitterToolbarInitializer.class);
        initializers.addBinding().to(GoogleSpreadSheetToolbarInitializer.class);
    }

}