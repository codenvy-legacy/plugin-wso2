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
package com.codenvy.ide.client.propertiespanel.respond;

import com.codenvy.ide.client.elements.Respond;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Respond mediator
 * depending on user's changes of properties.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class RespondPropertiesPanelPresenter extends AbstractPropertiesPanel<Respond, RespondPropertiesPanelView>
        implements RespondPropertiesPanelView.ActionDelegate {

    @Inject
    public RespondPropertiesPanelPresenter(RespondPropertiesPanelView view, PropertyTypeManager propertyTypeManager) {
        super(view, propertyTypeManager);
    }

    /** {@inheritDoc} */
    @Override
    public void onDescriptionChanged() {
        element.setDescription(view.getDescription());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        view.setDescription(element.getDescription());
        onDescriptionChanged();
    }

}