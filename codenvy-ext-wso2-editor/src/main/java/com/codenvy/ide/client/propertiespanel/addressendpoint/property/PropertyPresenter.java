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
package com.codenvy.ide.client.propertiespanel.addressendpoint.property;

import com.codenvy.ide.client.elements.addressendpoint.Property;
import com.codenvy.ide.client.propertiespanel.addressendpoint.editoradressproperty.EditorAddressPropertyCallBack;
import com.codenvy.ide.client.propertiespanel.addressendpoint.editoradressproperty.EditorAddressPropertyPresenter;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

/**
 * The presenter that provides a business logic of editing of 'Properties' property of 'Address' endpoint.
 *
 * @author Andrey Plotnikov
 */
public class PropertyPresenter implements PropertyView.ActionDelegate {

    private final PropertyView                   view;
    private final EditorAddressPropertyPresenter editPropertyPresenter;
    private final EditorAddressPropertyCallBack  addPropertyCallBack;
    private final EditorAddressPropertyCallBack  editPropertyCallBack;

    private Property                  selectedProperty;
    private PropertiesChangedCallback callback;
    private Array<Property>           properties;

    @Inject
    public PropertyPresenter(PropertyView view, EditorAddressPropertyPresenter editPropertyPresenter) {
        this.view = view;
        this.view.setDelegate(this);

        this.editPropertyPresenter = editPropertyPresenter;

        this.addPropertyCallBack = new EditorAddressPropertyCallBack() {
            @Override
            public void onAddressPropertyChanged(@Nonnull Property property) {
                if (!isElementNameContained(property)) {
                    properties.add(property);

                    PropertyPresenter.this.view.setProperties(properties);
                } else {
                    PropertyPresenter.this.view.showErrorMessage();
                }
            }
        };

        this.editPropertyCallBack = new EditorAddressPropertyCallBack() {
            @Override
            public void onAddressPropertyChanged(@Nonnull Property property) {
                int index = properties.indexOf(selectedProperty);

                properties.set(index, property);

                PropertyPresenter.this.view.setProperties(properties);
            }
        };
    }

    /**
     * Returns true if list of properties contains a property with name that already exists.
     *
     * @param property
     *         value of property which need to check
     */
    private boolean isElementNameContained(@Nonnull Property property) {
        for (Property prop : properties.asIterable()) {
            if (property.getName().equals(prop.getName())) {
                return true;
            }
        }

        return false;
    }

    /** {@inheritDoc} */
    @Override
    public void onOkButtonClicked() {
        view.hideDialog();

        callback.onPropertiesChanged(properties);
    }

    /** {@inheritDoc} */
    @Override
    public void onCancelButtonClicked() {
        view.hideDialog();
    }

    /** {@inheritDoc} */
    @Override
    public void onAddButtonClicked() {
        editPropertyPresenter.showDialog(null, addPropertyCallBack);
    }

    /** {@inheritDoc} */
    @Override
    public void onEditButtonClicked() {
        editPropertyPresenter.showDialog(selectedProperty, editPropertyCallBack);
    }

    /** {@inheritDoc} */
    @Override
    public void onRemoveButtonClicked() {
        properties.remove(selectedProperty);
        selectedProperty = null;

        view.setProperties(properties);
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertySelected(@Nonnull Property property) {
        selectedProperty = property;
    }

    public void showDialog(@Nonnull PropertiesChangedCallback callback, @Nonnull Array<Property> properties) {
        selectedProperty = null;

        this.callback = callback;
        this.properties = Collections.createArray();

        for (Property property : properties.asIterable()) {
            this.properties.add(property.clone());
        }

        view.setProperties(this.properties);

        view.showDialog();
    }

    public interface PropertiesChangedCallback {

        void onPropertiesChanged(@Nonnull Array<Property> properties);

    }

}