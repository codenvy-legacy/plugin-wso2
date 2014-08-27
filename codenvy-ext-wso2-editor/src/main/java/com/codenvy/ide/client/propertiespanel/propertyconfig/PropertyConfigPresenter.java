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
package com.codenvy.ide.client.propertiespanel.propertyconfig;

import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.log.Property;
import com.codenvy.ide.client.propertiespanel.log.AddPropertyCallback;
import com.codenvy.ide.client.propertiespanel.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The presenter that provides a business logic of dialog window for editing properties.
 *
 * @author Dmitry Shnurenko
 */
public class PropertyConfigPresenter implements PropertyConfigView.ActionDelegate {

    private final PropertyConfigView       propertiesView;
    private final NameSpaceEditorPresenter nameSpacePresenter;
    private final AddNameSpacesCallBack    addNameSpacesCallBack;
    private       Array<Property>          arrayTemporary;
    private       Property                 selectedProperty;
    private       AddPropertyCallback      addPropertyCallback;
    private       int                      index;

    @Inject
    public PropertyConfigPresenter(PropertyConfigView propertyConfigView, NameSpaceEditorPresenter nameSpacePresenter) {
        this.propertiesView = propertyConfigView;
        this.nameSpacePresenter = nameSpacePresenter;
        this.index = -1;

        this.propertiesView.setDelegate(this);

        this.addNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nullable String expression) {
                selectedProperty.setNameSpaces(nameSpaces);
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onSelectedProperty(@Nonnull Property property) {
        this.selectedProperty = property;
    }

    /** {@inheritDoc} */
    @Override
    public void onCancelButtonClicked() {
        propertiesView.hideWindow();
    }

    /** {@inheritDoc} */
    @Override
    public void onAddPropertyButtonClicked() {
        String name = propertiesView.getName().isEmpty() ? "property_name" : propertiesView.getName();
        String expression = propertiesView.getValueExpression().isEmpty() ? "property_value" : propertiesView.getValueExpression();

        Property property = new Property(name, expression);

        propertiesView.setName("");
        propertiesView.setValueExpression("");

        if (index != -1) {
            arrayTemporary.set(index, property);
            index = -1;
        } else {
            arrayTemporary.add(property);
        }

        propertiesView.setProperties(arrayTemporary);
    }

    /** {@inheritDoc} */
    @Override
    public void onRemovePropertyButtonClicked() {
        arrayTemporary.remove(selectedProperty);

        propertiesView.setProperties(arrayTemporary);
    }

    /** {@inheritDoc} */
    @Override
    public void onOkButtonClicked() {
        addPropertyCallback.onPropertiesChanged(arrayTemporary);

        propertiesView.hideWindow();
    }

    /** {@inheritDoc} */
    @Override
    public void onEditPropertiesButtonClicked() {
        nameSpacePresenter.showDefaultWindow(selectedProperty.getNameSpaces(), addNameSpacesCallBack);
    }

    /** {@inheritDoc} */
    @Override
    public void onEditButtonClicked() {
        propertiesView.setName(selectedProperty.getName());
        propertiesView.setValueExpression(selectedProperty.getExpression());

        index = arrayTemporary.indexOf(selectedProperty);

        propertiesView.setProperties(arrayTemporary);
    }

    /**
     * Shows dialog window for editing properties.
     *
     * @param properties
     *         properties which need to be edited.
     * @param title
     *         title which need to set to dialog window
     * @param callback
     *         callback that need to be handled when properties editing is successful
     */
    public void showConfigWindow(@Nonnull Array<Property> properties, @Nonnull String title, @Nonnull AddPropertyCallback callback) {
        arrayTemporary = Collections.createArray();
        addPropertyCallback = callback;

        for (Property property : properties.asIterable()) {
            arrayTemporary.add(property.clone());
        }

        propertiesView.setProperties(arrayTemporary);

        propertiesView.showWindow(title);
    }
}
