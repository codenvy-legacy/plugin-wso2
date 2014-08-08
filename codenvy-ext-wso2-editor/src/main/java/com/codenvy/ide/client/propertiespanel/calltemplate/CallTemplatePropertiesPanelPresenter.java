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
import com.codenvy.ide.client.elements.CallTemplate;
import com.codenvy.ide.client.elements.log.Property;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.log.AddPropertyCallback;
import com.codenvy.ide.client.propertiespanel.log.propertyconfig.PropertyConfigPresenter;
import com.codenvy.ide.client.propertytypes.PropertyTypeManager;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.CallTemplate.AvailableTemplates.TYPE_NAME;

/**
 * Presenter for view which support editing CallTemplate mediator.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class CallTemplatePropertiesPanelPresenter extends AbstractPropertiesPanel<CallTemplate>
        implements CallTemplatePropertiesPanelView.ActionDelegate {

    private final PropertyConfigPresenter        propertyConfigPresenter;
    private final AddPropertyCallback            addPropertyCallback;
    private final WSO2EditorLocalizationConstant local;

    @Inject
    public CallTemplatePropertiesPanelPresenter(CallTemplatePropertiesPanelView view,
                                                PropertyTypeManager propertyTypeManager,
                                                PropertyConfigPresenter propertyConfigPresenter,
                                                WSO2EditorLocalizationConstant local) {
        super(view, propertyTypeManager);

        this.propertyConfigPresenter = propertyConfigPresenter;
        this.local = local;

        this.addPropertyCallback = new AddPropertyCallback() {

            @Override
            public void onPropertiesChanged(@Nonnull Array<Property> properties) {
                element.setParameters(properties);

                ((CallTemplatePropertiesPanelView)CallTemplatePropertiesPanelPresenter.this.view)
                        .setParameters(properties.isEmpty() ? "" : "Call Template Parameters");

                notifyListeners();
            }
        };
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
    public void onDescriptionChanged() {
        element.setDescription(((CallTemplatePropertiesPanelView)view).getDescription());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onParameterButtonClicked() {
        propertyConfigPresenter.showConfigWindow(element.getParameters(),
                                                 local.propertiespanelCallTemplateConfigurationTitle(),
                                                 addPropertyCallback);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        ((CallTemplatePropertiesPanelView)view).selectAvailableTemplate(element.getAvailableTemplates());
        ((CallTemplatePropertiesPanelView)view).setAvailableTemplates(propertyTypeManager.getValuesOfTypeByName(TYPE_NAME));
        ((CallTemplatePropertiesPanelView)view).setTargetTemplate(element.getTargetTemplate());
        ((CallTemplatePropertiesPanelView)view).setDescription(element.getDescription());
        ((CallTemplatePropertiesPanelView)view).setParameters(element.getParameters().isEmpty() ? "" : "Call Template Parameters");
    }

}