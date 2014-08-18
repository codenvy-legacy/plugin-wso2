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
package com.codenvy.ide.client.propertiespanel.root;

import com.codenvy.ide.client.elements.RootElement;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertytypes.PropertyTypeManager;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

/**
 * The presenter that provides a business logic of root element properties panel. It provides an ability to work with all properties
 * of root element.
 *
 * @author Andrey Plotnikov
 */
public class RootPropertiesPanelPresenter extends AbstractPropertiesPanel<RootElement, RootPropertiesPanelView>
        implements RootPropertiesPanelView.ActionDelegate {

    @Inject
    public RootPropertiesPanelPresenter(RootPropertiesPanelView view, PropertyTypeManager propertyTypeManager) {
        super(view, propertyTypeManager);
    }

    /** {@inheritDoc} */
    @Override
    public void onNameChanged() {
        element.setName(view.getName());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onOnErrorChanged() {
        element.setOnError(view.getOnError());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        view.setName(element.getName());
        view.setOnError(element.getOnError());
    }

}