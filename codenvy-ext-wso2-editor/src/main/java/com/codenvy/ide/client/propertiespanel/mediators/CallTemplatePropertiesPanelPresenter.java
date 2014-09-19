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
import com.codenvy.ide.client.inject.factories.PropertiesPanelWidgetFactory;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.common.namespace.AddPropertyCallback;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.PropertyConfigPresenter;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.list.ListPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.mediators.CallTemplate.AVAILABLE_TEMPLATES;
import static com.codenvy.ide.client.elements.mediators.CallTemplate.AvailableTemplates;
import static com.codenvy.ide.client.elements.mediators.CallTemplate.DESCRIPTION;
import static com.codenvy.ide.client.elements.mediators.CallTemplate.PARAMETERS;
import static com.codenvy.ide.client.elements.mediators.CallTemplate.TARGET_TEMPLATES;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of CallTemplate mediator
 * depending on user's changes of properties.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class CallTemplatePropertiesPanelPresenter extends AbstractPropertiesPanel<CallTemplate, PropertiesPanelView> {

    private final PropertyConfigPresenter        propertyConfigPresenter;
    private final AddPropertyCallback            addPropertyCallback;
    private final WSO2EditorLocalizationConstant local;

    private ListPropertyPresenter    availableTemplates;
    private SimplePropertyPresenter  targetTemplate;
    private ComplexPropertyPresenter parameters;
    private SimplePropertyPresenter  description;

    @Inject
    public CallTemplatePropertiesPanelPresenter(PropertiesPanelView view,
                                                PropertyTypeManager propertyTypeManager,
                                                PropertyConfigPresenter propertyConfigPresenter,
                                                WSO2EditorLocalizationConstant local,
                                                PropertiesPanelWidgetFactory propertiesPanelWidgetFactory,
                                                Provider<SimplePropertyPresenter> simplePropertyPresenterProvider,
                                                Provider<ListPropertyPresenter> listPropertyPresenterProvider,
                                                Provider<ComplexPropertyPresenter> complexPropertyPresenterProvider) {
        super(view, propertyTypeManager);

        this.propertyConfigPresenter = propertyConfigPresenter;
        this.local = local;

        this.addPropertyCallback = new AddPropertyCallback() {

            @Override
            public void onPropertiesChanged(@Nonnull Array<Property> properties) {
                element.putProperty(PARAMETERS, properties);

                parameters.setProperty(properties.isEmpty() ? "" :
                                       CallTemplatePropertiesPanelPresenter.this.local.calltemplateLabelParameters());

                notifyListeners();
            }
        };

        prepareView(propertiesPanelWidgetFactory,
                    simplePropertyPresenterProvider,
                    listPropertyPresenterProvider,
                    complexPropertyPresenterProvider);
    }

    private void prepareView(PropertiesPanelWidgetFactory propertiesPanelWidgetFactory,
                             Provider<SimplePropertyPresenter> simplePropertyPresenterProvider,
                             Provider<ListPropertyPresenter> listPropertyPresenterProvider,
                             Provider<ComplexPropertyPresenter> complexPropertyPresenterProvider) {
        PropertyGroupPresenter basicGroup = propertiesPanelWidgetFactory.createPropertyGroupPresenter(local.miscGroupTitle());
        view.addGroup(basicGroup);

        availableTemplates = listPropertyPresenterProvider.get();
        availableTemplates.setTitle(local.availableTemplate());
        availableTemplates.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(AVAILABLE_TEMPLATES, AvailableTemplates.getItemByValue(property));

                notifyListeners();
            }
        });
        basicGroup.addItem(availableTemplates);

        targetTemplate = simplePropertyPresenterProvider.get();
        targetTemplate.setTitle(local.targetTemplate());
        targetTemplate.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(TARGET_TEMPLATES, property);

                notifyListeners();
            }
        });
        basicGroup.addItem(targetTemplate);

        parameters = complexPropertyPresenterProvider.get();
        parameters.setTitle(local.calltemplateParameters());
        parameters.addEditButtonClickedListener(new ComplexPropertyPresenter.EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                Array<Property> parameters = element.getProperty(PARAMETERS);

                if (parameters != null) {
                    propertyConfigPresenter.showConfigWindow(parameters, local.calltemplateParameters(), addPropertyCallback);
                }
            }
        });
        basicGroup.addItem(parameters);

        description = simplePropertyPresenterProvider.get();
        description.setTitle(local.description());
        description.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(DESCRIPTION, property);

                notifyListeners();
            }
        });
        basicGroup.addItem(description);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        availableTemplates.setValues(propertyTypeManager.getValuesByName(AvailableTemplates.TYPE_NAME));
        AvailableTemplates availableTemplates = element.getProperty(AVAILABLE_TEMPLATES);

        if (availableTemplates != null) {
            this.availableTemplates.selectValue(availableTemplates.name());
        }

        targetTemplate.setProperty(element.getProperty(TARGET_TEMPLATES));
        parameters.setProperty(local.calltemplateLabelParameters());
        description.setProperty(element.getProperty(DESCRIPTION));
    }

}