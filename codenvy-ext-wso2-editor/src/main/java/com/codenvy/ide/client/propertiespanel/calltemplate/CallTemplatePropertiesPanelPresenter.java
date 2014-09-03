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

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.mediators.CallTemplate;
import com.codenvy.ide.client.elements.mediators.log.Property;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.log.AddPropertyCallback;
import com.codenvy.ide.client.propertiespanel.propertyconfig.PropertyConfigPresenter;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.mediators.CallTemplate.AvailableTemplates;
import static com.codenvy.ide.client.elements.mediators.CallTemplate.AvailableTemplates.TYPE_NAME;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of CallTemplate mediator
 * depending on user's changes of properties.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class CallTemplatePropertiesPanelPresenter extends AbstractPropertiesPanel<CallTemplate, CallTemplatePropertiesPanelView>
        implements CallTemplatePropertiesPanelView.ActionDelegate {

    private final PropertyConfigPresenter        propertyConfigPresenter;
    private final AddPropertyCallback            addPropertyCallback;
    private final WSO2EditorLocalizationConstant local;

    @Inject
    public CallTemplatePropertiesPanelPresenter(CallTemplatePropertiesPanelView view,
                                                PropertyTypeManager propertyTypeManager,
                                                PropertyConfigPresenter propertyConfigPresenter,
                                                final WSO2EditorLocalizationConstant local) {
        super(view, propertyTypeManager);

        this.propertyConfigPresenter = propertyConfigPresenter;
        this.local = local;

        this.addPropertyCallback = new AddPropertyCallback() {

            @Override
            public void onPropertiesChanged(@Nonnull Array<Property> properties) {
                element.setParameters(properties);

                CallTemplatePropertiesPanelPresenter.this.view.setParameters(properties.isEmpty()
                                                                             ? ""
                                                                             : local.calltemplateLabelParameters());

                notifyListeners();
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onAvailableTemplatesChanged() {
        element.setAvailableTemplates(AvailableTemplates.valueOf(view.getAvailableTemplates()));

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onTargetTemplateChanged() {
        element.setTargetTemplate(view.getTargetTemplate());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onDescriptionChanged() {
        element.setDescription(view.getDescription());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onParameterButtonClicked() {
        propertyConfigPresenter.showConfigWindow(element.getParameters(),
                                                 local.callTemplateConfigTitle(),
                                                 addPropertyCallback);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        view.selectAvailableTemplate(element.getAvailableTemplates().getValue());
        view.setAvailableTemplates(propertyTypeManager.getValuesByName(TYPE_NAME));

        view.setTargetTemplate(element.getTargetTemplate());
        view.setDescription(element.getDescription());

        view.setParameters(element.getParameters().isEmpty() ? "" : local.calltemplateLabelParameters());
    }

}