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
package com.codenvy.ide.client.propertiespanel.mediators;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.mediators.CallTemplate;
import com.codenvy.ide.client.elements.mediators.log.Property;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.PropertyPanelFactory;
import com.codenvy.ide.client.propertiespanel.common.namespace.AddPropertyCallback;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.PropertyConfigPresenter;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.list.ListPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.CallTemplate.AVAILABLE_TEMPLATES;
import static com.codenvy.ide.client.elements.mediators.CallTemplate.AvailableTemplates;
import static com.codenvy.ide.client.elements.mediators.CallTemplate.DESCRIPTION;
import static com.codenvy.ide.client.elements.mediators.CallTemplate.PARAMETERS;
import static com.codenvy.ide.client.elements.mediators.CallTemplate.TARGET_TEMPLATES;
import static com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter.EditButtonClickedListener;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of CallTemplate mediator
 * depending on user's changes of properties.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class CallTemplatePropertiesPanelPresenter extends AbstractPropertiesPanel<CallTemplate> {

    private final PropertyConfigPresenter propertyConfigPresenter;
    private final AddPropertyCallback     addPropertyCallback;

    private ListPropertyPresenter    availableTemplates;
    private SimplePropertyPresenter  targetTemplate;
    private ComplexPropertyPresenter parameters;
    private SimplePropertyPresenter  description;

    @Inject
    public CallTemplatePropertiesPanelPresenter(PropertiesPanelView view,
                                                PropertyTypeManager propertyTypeManager,
                                                PropertyConfigPresenter propertyConfigPresenter,
                                                final WSO2EditorLocalizationConstant locale,
                                                PropertyPanelFactory propertyPanelFactory) {

        super(view, propertyTypeManager, locale, propertyPanelFactory);

        this.propertyConfigPresenter = propertyConfigPresenter;

        this.addPropertyCallback = new AddPropertyCallback() {
            @Override
            public void onPropertiesChanged(@Nonnull List<Property> properties) {
                element.putProperty(PARAMETERS, properties);

                parameters.setProperty(properties.isEmpty() ? "" : locale.calltemplateLabelParameters());

                notifyListeners();
            }
        };

        prepareView();
    }

    private void prepareView() {
        PropertyGroupPresenter basicGroup = createGroup(locale.miscGroupTitle());

        PropertyValueChangedListener availableTemplatesListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(AVAILABLE_TEMPLATES, AvailableTemplates.getItemByValue(property));

                notifyListeners();
            }
        };
        availableTemplates = createListProperty(basicGroup, locale.availableTemplate(), availableTemplatesListener);

        PropertyValueChangedListener targetTemplateListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(TARGET_TEMPLATES, property);

                notifyListeners();
            }
        };
        targetTemplate = createSimpleProperty(basicGroup, locale.targetTemplate(), targetTemplateListener);

        EditButtonClickedListener parametersBtnListener = new EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                List<Property> parameters = element.getProperty(PARAMETERS);

                if (parameters != null) {
                    propertyConfigPresenter.showConfigWindow(parameters, locale.calltemplateParameters(), addPropertyCallback);
                }
            }
        };
        parameters = createComplexProperty(basicGroup, locale.calltemplateParameters(), parametersBtnListener);

        PropertyValueChangedListener descriptionListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(DESCRIPTION, property);

                notifyListeners();
            }
        };
        description = createSimpleProperty(basicGroup, locale.description(), descriptionListener);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        availableTemplates.setValues(propertyTypeManager.getValuesByName(AvailableTemplates.TYPE_NAME));
        AvailableTemplates availableTemplatesValue = element.getProperty(AVAILABLE_TEMPLATES);

        if (availableTemplatesValue != null) {
            availableTemplates.selectValue(availableTemplatesValue.getValue());
        }

        targetTemplate.setProperty(element.getProperty(TARGET_TEMPLATES));
        parameters.setProperty(locale.calltemplateLabelParameters());
        description.setProperty(element.getProperty(DESCRIPTION));
    }

}