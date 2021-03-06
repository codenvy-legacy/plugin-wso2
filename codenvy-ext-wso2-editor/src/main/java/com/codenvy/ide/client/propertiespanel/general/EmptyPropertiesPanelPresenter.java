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
package com.codenvy.ide.client.propertiespanel.general;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.Element;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.managers.SelectionManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.PropertyPanelFactory;
import com.google.inject.Inject;

/**
 * The class that shows when no diagram element is selected. It is empty properties panel.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class EmptyPropertiesPanelPresenter extends AbstractPropertiesPanel<Element> {

    @Inject
    public EmptyPropertiesPanelPresenter(PropertiesPanelView view,
                                         PropertyTypeManager propertyTypeManager,
                                         WSO2EditorLocalizationConstant locale,
                                         PropertyPanelFactory propertyPanelFactory,
                                         SelectionManager selectionManager) {

        super(view, propertyTypeManager, locale, propertyPanelFactory, selectionManager);
    }

}