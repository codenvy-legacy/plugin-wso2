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
package com.codenvy.ide.client.propertiespanel.payloadfactory;

import com.codenvy.ide.client.elements.PayloadFactory;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertytypes.PropertyTypeManager;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

/**
 * @author Andrey Plotnikov
 */
public class PayloadFactoryPropertiesPanelPresenter extends AbstractPropertiesPanel<PayloadFactory>
        implements PayloadFactoryPropertiesPanelView.ActionDelegate {

    @Inject
    public PayloadFactoryPropertiesPanelPresenter(PayloadFactoryPropertiesPanelView view, PropertyTypeManager propertyTypeManager) {
        super(view, propertyTypeManager);
    }

    /** {@inheritDoc} */
    @Override
    public void onPayloadFormatChanged() {
        element.setPayloadFormat(((PayloadFactoryPropertiesPanelView)view).getPayloadFormat());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFormatChanged() {
        element.setFormat(((PayloadFactoryPropertiesPanelView)view).getFormat());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onArgsChanged() {
        element.setArgs(((PayloadFactoryPropertiesPanelView)view).getArgs());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onMediaTypeChanged() {
        element.setMediaType(((PayloadFactoryPropertiesPanelView)view).getMediaType());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onDescriptionChanged() {
        element.setDescription(((PayloadFactoryPropertiesPanelView)view).getDescription());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        ((PayloadFactoryPropertiesPanelView)view).setPayloadFormat(propertyTypeManager.getValuesOfTypeByName("PayloadFormatType"));
        ((PayloadFactoryPropertiesPanelView)view).selectPayloadFormat(element.getPayloadFormat());
        ((PayloadFactoryPropertiesPanelView)view).setFormat(element.getFormat());
        ((PayloadFactoryPropertiesPanelView)view).setArgs(element.getArgs());
        ((PayloadFactoryPropertiesPanelView)view).setMediaType(propertyTypeManager.getValuesOfTypeByName("MediaType"));
        ((PayloadFactoryPropertiesPanelView)view).selectMediaType(element.getMediaType());
        ((PayloadFactoryPropertiesPanelView)view).setDescription(element.getDescription());
    }

}