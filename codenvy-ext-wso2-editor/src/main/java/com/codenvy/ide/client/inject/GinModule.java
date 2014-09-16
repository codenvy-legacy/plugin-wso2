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
import com.codenvy.ide.client.inject.factories.EditorFactory;
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

/**
 * @author Andrey Plotnikov
 */
public class GinModule extends AbstractGinModule {

    /** {@inheritDoc} */
    @Override
    protected void configure() {
        install(new GinFactoryModuleBuilder().build(EditorFactory.class));

        install(new GinFactoryModuleBuilder().implement(ElementView.class, ElementViewImpl.class).build(ElementWidgetFactory.class));

        install(new GinFactoryModuleBuilder().implement(ToolbarGroupView.class, ToolbarGroupViewImpl.class)
                                             .implement(ToolbarItemView.class, ToolbarItemViewImpl.class)
                                             .build(ToolbarFactory.class));

        install(new GinFactoryModuleBuilder().implement(PropertyGroupView.class, PropertyGroupViewImpl.class)
                                             .build(PropertiesPanelWidgetFactory.class));
    }

}