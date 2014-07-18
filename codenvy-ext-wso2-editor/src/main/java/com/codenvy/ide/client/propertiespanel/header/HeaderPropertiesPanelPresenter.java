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
package com.codenvy.ide.client.propertiespanel.header;

import com.codenvy.ide.client.elements.Header;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertytypes.PropertyTypeManager;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

/**
 * @author Andrey Plotnikov
 */
public class HeaderPropertiesPanelPresenter extends AbstractPropertiesPanel<Header> implements HeaderPropertiesPanelView.ActionDelegate {

    @Inject
    public HeaderPropertiesPanelPresenter(HeaderPropertiesPanelView view, PropertyTypeManager propertyTypeManager) {
        super(view, propertyTypeManager);
    }

    /** {@inheritDoc} */
    @Override
    public void onHeaderActionChanged() {
        element.setHeaderAction(((HeaderPropertiesPanelView)view).getHeaderAction());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onScopeChanged() {
        element.setScope(((HeaderPropertiesPanelView)view).getScope());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onValueTypeChanged() {
        element.setValueType(((HeaderPropertiesPanelView)view).getValueType());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onValueLiteralChanged() {
        element.setValueLiteral(((HeaderPropertiesPanelView)view).getValueLiteral());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onHeaderNameChanged() {
        element.setHeaderName(((HeaderPropertiesPanelView)view).getHeaderName());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void go(AcceptsOneWidget container) {
        super.go(container);

        ((HeaderPropertiesPanelView)view).setHeaderAction(propertyTypeManager.getValuesOfTypeByName("HeaderAction"));
        ((HeaderPropertiesPanelView)view).selectHeaderAction(element.getHeaderAction());
        ((HeaderPropertiesPanelView)view).setScope(propertyTypeManager.getValuesOfTypeByName("ScopeType"));
        ((HeaderPropertiesPanelView)view).selectScope(element.getScope());
        ((HeaderPropertiesPanelView)view).setValueType(propertyTypeManager.getValuesOfTypeByName("HeaderValueType"));
        ((HeaderPropertiesPanelView)view).selectValueType(element.getValueType());
        ((HeaderPropertiesPanelView)view).setValueLiteral(element.getValueLiteral());
        ((HeaderPropertiesPanelView)view).setHeaderName(element.getHeaderName());
    }

}