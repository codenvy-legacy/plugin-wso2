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
import com.codenvy.ide.client.elements.mediators.Respond;
import com.codenvy.ide.client.inject.factories.PropertiesPanelWidgetFactory;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.mediators.Respond.DESCRIPTION;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Respond mediator
 * depending on user's changes of properties.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class RespondPropertiesPanelPresenter extends AbstractPropertiesPanel<Respond> {

    private final SimplePropertyPresenter description;

    @Inject
    public RespondPropertiesPanelPresenter(PropertiesPanelView view,
                                           PropertyTypeManager propertyTypeManager,
                                           PropertiesPanelWidgetFactory propertiesPanelWidgetFactory,
                                           SimplePropertyPresenter descriptionPropertyPresenter,
                                           WSO2EditorLocalizationConstant locale) {
        super(view, propertyTypeManager);

        PropertyGroupPresenter basicGroup = propertiesPanelWidgetFactory.createPropertyGroupPresenter(locale.miscGroupTitle());
        this.view.addGroup(basicGroup);

        description = descriptionPropertyPresenter;
        description.setTitle(locale.addressEndpointDescription());
        description.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(DESCRIPTION, property);

                notifyListeners();
            }
        });
        basicGroup.addItem(descriptionPropertyPresenter);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        description.setProperty(element.getProperty(DESCRIPTION));
    }

}