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
package com.codenvy.ide.client.propertiespanel.common.propertyconfig;

import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.mediators.log.Property;
import com.codenvy.ide.client.propertiespanel.common.namespace.AddPropertyCallback;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.log.Property.EXPRESSION;
import static com.codenvy.ide.client.elements.mediators.log.Property.NAME;
import static com.codenvy.ide.client.elements.mediators.log.Property.NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.log.Property.VALUE;
import static com.codenvy.ide.client.elements.mediators.log.Property.copyPropertyList;

/**
 * The class provides the business logic that allows editor to react on user's action related to change of element's property.
 * Logic which provides the class allows add, remove and edit properties of mediator via special dialog window.
 *
 * @author Dmitry Shnurenko
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class PropertyConfigPresenter implements PropertyConfigView.ActionDelegate {

    private final PropertyConfigView       propertiesView;
    private final NameSpaceEditorPresenter nameSpacePresenter;
    private final Provider<Property>       propertyProvider;
    private final AddNameSpacesCallBack    addNameSpacesCallBack;

    private List<Property>      arrayTemporary;
    private Property            selectedProperty;
    private AddPropertyCallback addPropertyCallback;
    private int                 index;

    @Inject
    public PropertyConfigPresenter(PropertyConfigView propertyConfigView,
                                   NameSpaceEditorPresenter nameSpacePresenter,
                                   Provider<Property> propertyProvider) {
        this.propertiesView = propertyConfigView;
        this.nameSpacePresenter = nameSpacePresenter;
        this.propertyProvider = propertyProvider;
        this.index = -1;

        this.propertiesView.setDelegate(this);

        this.addNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nullable String expression) {
                selectedProperty.putProperty(NAMESPACES, nameSpaces);
                selectedProperty.putProperty(EXPRESSION, expression);
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
        String value = propertiesView.getValue().isEmpty() ? "property_value" : propertiesView.getValue();

        Property property = propertyProvider.get();

        property.putProperty(NAME, name);
        property.putProperty(VALUE, value);

        propertiesView.setName("");
        propertiesView.setValue("");

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
        List<NameSpace> nameSpaces = selectedProperty.getProperty(NAMESPACES);

        if (nameSpaces == null) {
            return;
        }

        nameSpacePresenter.showDefaultWindow(nameSpaces, addNameSpacesCallBack);
    }

    /** {@inheritDoc} */
    @Override
    public void onEditButtonClicked() {
        String name = selectedProperty.getProperty(NAME);
        String expression = selectedProperty.getProperty(EXPRESSION);

        if (name == null || expression == null) {
            return;
        }

        propertiesView.setName(name);
        propertiesView.setValue(expression);

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
    public void showConfigWindow(@Nonnull List<Property> properties, @Nonnull String title, @Nonnull AddPropertyCallback callback) {
        arrayTemporary = copyPropertyList(properties);
        addPropertyCallback = callback;

        propertiesView.setProperties(arrayTemporary);

        propertiesView.showWindow(title);
    }
}
