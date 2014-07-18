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
package com.codenvy.ide.client.propertiespanel.calltemplate;

import com.codenvy.ide.client.elements.CallTemplate;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertytypes.PropertyTypeManager;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

/**
 * @author Andrey Plotnikov
 */
public class CallTemplatePropertiesPanelPresenter extends AbstractPropertiesPanel<CallTemplate>
        implements CallTemplatePropertiesPanelView.ActionDelegate {

    @Inject
    public CallTemplatePropertiesPanelPresenter(CallTemplatePropertiesPanelView view, PropertyTypeManager propertyTypeManager) {
        super(view, propertyTypeManager);
    }

    /** {@inheritDoc} */
    @Override
    public void onAvailableTemplatesChanged() {
        element.setAvailableTemplates(((CallTemplatePropertiesPanelView)view).getAvailableTemplates());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onTargetTemplateChanged() {
        element.setTargetTemplate(((CallTemplatePropertiesPanelView)view).getTargetTemplate());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onParametersChanged() {
        element.setParameters(((CallTemplatePropertiesPanelView)view).getParameters());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onDescriptionChanged() {
        element.setDescription(((CallTemplatePropertiesPanelView)view).getDescription());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        ((CallTemplatePropertiesPanelView)view).setAvailableTemplates(element.getAvailableTemplates());
        ((CallTemplatePropertiesPanelView)view).setTargetTemplate(element.getTargetTemplate());
        ((CallTemplatePropertiesPanelView)view).setParameters(element.getParameters());
        ((CallTemplatePropertiesPanelView)view).setDescription(element.getDescription());
    }

}