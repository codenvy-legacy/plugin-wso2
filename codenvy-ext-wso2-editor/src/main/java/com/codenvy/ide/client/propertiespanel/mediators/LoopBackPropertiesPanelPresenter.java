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
import com.codenvy.ide.client.elements.mediators.LoopBack;
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

import static com.codenvy.ide.client.elements.mediators.Call.DESCRIPTION;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of LoopBack mediator
 * depending on user's changes of properties.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class LoopBackPropertiesPanelPresenter extends AbstractPropertiesPanel<LoopBack, PropertiesPanelView> {

    private final SimplePropertyPresenter description;

    @Inject
    public LoopBackPropertiesPanelPresenter(PropertiesPanelView view,
                                            PropertyTypeManager propertyTypeManager,
                                            PropertiesPanelWidgetFactory propertiesPanelWidgetFactory,
                                            SimplePropertyPresenter description,
                                            WSO2EditorLocalizationConstant locale) {
        super(view, propertyTypeManager);

        PropertyGroupPresenter basicGroup = propertiesPanelWidgetFactory.createPropertyGroupPresenter(locale.miscGroupTitle());
        this.view.addGroup(basicGroup);

        this.description = description;
        this.description.setTitle(locale.addressEndpointDescription());
        this.description.addPropertyValueChangedListener(new PropertyValueChangedListener() {
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

        description.setProperty(element.getProperty(DESCRIPTION));
    }

}