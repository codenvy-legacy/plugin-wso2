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
package com.codenvy.ide.client.propertiespanel.enrich;

import com.codenvy.ide.client.elements.Enrich;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertytypes.PropertyTypeManager;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

/**
 * @author Andrey Plotnikov
 */
public class EnrichPropertiesPanelPresenter extends AbstractPropertiesPanel<Enrich>
        implements EnrichPropertiesPanelView.ActionDelegate {

    @Inject
    public EnrichPropertiesPanelPresenter(EnrichPropertiesPanelView view, PropertyTypeManager propertyTypeManager) {
        super(view, propertyTypeManager);
    }

    /** {@inheritDoc} */
    @Override
    public void onCloneSourceChanged() {
        element.setCloneSource(((EnrichPropertiesPanelView)view).getCloneSource());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSourceTypeChanged() {
        element.setSourceType(((EnrichPropertiesPanelView)view).getSourceType());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSourceXpathChanged() {
        element.setSourceXpath(((EnrichPropertiesPanelView)view).getSourceXpath());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onTargetActionChanged() {
        element.setTargetAction(((EnrichPropertiesPanelView)view).getTargetAction());
        notifyListeners();
    }

    @Override
    public void onTargetTypeChanged() {
        element.setTargetType(((EnrichPropertiesPanelView)view).getTargetType());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onTargetXpathChanged() {
        element.setTargetXpath(((EnrichPropertiesPanelView)view).getTargetXpath());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onDescriptionChanged() {
        element.setDescription(((EnrichPropertiesPanelView)view).getDescription());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        ((EnrichPropertiesPanelView)view).setCloneSource(propertyTypeManager.getValuesOfTypeByName("EBoolean"));
        ((EnrichPropertiesPanelView)view).selectCloneSource(element.getCloneSource());
        ((EnrichPropertiesPanelView)view).setSourceType(propertyTypeManager.getValuesOfTypeByName("EnrichSourceType"));
        ((EnrichPropertiesPanelView)view).selectSourceType(element.getSourceType());
        ((EnrichPropertiesPanelView)view).setSourceXpath(element.getSourceXpath());
        ((EnrichPropertiesPanelView)view).setTargetAction(propertyTypeManager.getValuesOfTypeByName("EnrichTargetAction"));
        ((EnrichPropertiesPanelView)view).selectTargetAction(element.getTargetAction());
        ((EnrichPropertiesPanelView)view).setTargetType(propertyTypeManager.getValuesOfTypeByName("EnrichTargetType"));
        ((EnrichPropertiesPanelView)view).selectTargetType(element.getTargetType());
        ((EnrichPropertiesPanelView)view).setTargetXpath(element.getTargetXpath());
        ((EnrichPropertiesPanelView)view).setDescription(element.getDescription());
    }

}