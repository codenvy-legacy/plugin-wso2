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
package com.codenvy.ide.client.propertiespanel.switch_mediator;

import com.codenvy.ide.client.elements.Switch;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertytypes.PropertyTypeManager;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

/**
 * @author Andrey Plotnikov
 */
public class Switch_mediatorPropertiesPanelPresenter extends AbstractPropertiesPanel<Switch, Switch_mediatorPropertiesPanelView>
        implements Switch_mediatorPropertiesPanelView.ActionDelegate {

    @Inject
    public Switch_mediatorPropertiesPanelPresenter(Switch_mediatorPropertiesPanelView view, PropertyTypeManager propertyTypeManager) {
        super(view, propertyTypeManager);
    }

    /** {@inheritDoc} */
    @Override
    public void onSourceXpathChanged() {
        element.setSourceXpath(view.getSourceXpath());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onCaseBranchesChanged() {
        element.setCaseBranches(view.getCaseBranches());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        view.setSourceXpath(element.getSourceXpath());
        view.setCaseBranches(element.getCaseBranches());
    }

}