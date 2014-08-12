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
package com.codenvy.ide.client.propertiespanel.send;

import com.codenvy.ide.client.elements.Send;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertytypes.PropertyTypeManager;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

/**
 * @author Andrey Plotnikov
 */
public class SendPropertiesPanelPresenter extends AbstractPropertiesPanel<Send, SendPropertiesPanelView>
        implements SendPropertiesPanelView.ActionDelegate {

    @Inject
    public SendPropertiesPanelPresenter(SendPropertiesPanelView view, PropertyTypeManager propertyTypeManager) {
        super(view, propertyTypeManager);
    }

    /** {@inheritDoc} */
    @Override
    public void onSkipSerializationChanged() {
        element.setSkipSerialization(view.getSkipSerialization());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onReceivingSequencerTypeChanged() {
        element.setReceivingSequencerType(view.getReceivingSequencerType());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onBuildMessageBeforeSendingChanged() {
        element.setBuildMessageBeforeSending(view.getBuildMessageBeforeSending());

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
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        view.setSkipSerialization(propertyTypeManager.getValuesOfTypeByName("EBoolean"));
        view.selectSkipSerialization(element.getSkipSerialization());
        view.setReceivingSequencerType(propertyTypeManager.getValuesOfTypeByName("ReceivingSequenceType"));
        view.selectReceivingSequencerType(element.getReceivingSequencerType());
        view.setBuildMessageBeforeSending(propertyTypeManager.getValuesOfTypeByName("EBoolean"));
        view.selectBuildMessageBeforeSending(element.getBuildMessageBeforeSending());
        view.setDescription(element.getDescription());
    }

}