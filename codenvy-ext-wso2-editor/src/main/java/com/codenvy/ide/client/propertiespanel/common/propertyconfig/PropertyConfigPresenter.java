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

import com.codenvy.ide.client.elements.mediators.log.Property;
import com.codenvy.ide.client.propertiespanel.common.addpropertydialog.AddPropertyCallBack;
import com.codenvy.ide.client.propertiespanel.common.addpropertydialog.AddPropertyLogPresenter;
import com.codenvy.ide.client.propertiespanel.common.addpropertydialog.general.AddPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.common.namespace.AddPropertyCallback;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.log.Property.NAME;
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

    private final PropertyConfigView             view;
    private final AddPropertyPresenter<Property> addPropertyLogPresenter;
    private final AddPropertyCallBack<Property>  editorAddPropertyCallBack;
    private final AddPropertyCallBack<Property>  editPropertyCallBack;

    private List<Property>      temporaryList;
    private Property            selectedProperty;
    private AddPropertyCallback addPropertyCallback;

    @Inject
    public PropertyConfigPresenter(PropertyConfigView propertyConfigView, final AddPropertyLogPresenter addPropertyLogPresenter) {
        this.view = propertyConfigView;
        this.addPropertyLogPresenter = addPropertyLogPresenter;

        this.view.setDelegate(this);

        this.editorAddPropertyCallBack = new AddPropertyCallBack<Property>() {
            @Override
            public void onPropertyChanged(@Nonnull Property property) {
                if (!temporaryList.contains(property)) {
                    temporaryList.add(property);

                    PropertyConfigPresenter.this.view.setProperties(temporaryList);

                    addPropertyLogPresenter.hideDialog();
                } else {
                    PropertyConfigPresenter.this.view.showErrorMessage();
                }
            }
        };

        this.editPropertyCallBack = new AddPropertyCallBack<Property>() {
            @Override
            public void onPropertyChanged(@Nonnull Property property) {
                int index = temporaryList.indexOf(selectedProperty);

                String innerPropertyName = property.getProperty(NAME);
                String selectedPropertyName = selectedProperty.getProperty(NAME);

                if (innerPropertyName == null || selectedPropertyName == null) {
                    return;
                }

                if (innerPropertyName.equals(selectedPropertyName) || !temporaryList.contains(property)) {
                    temporaryList.set(index, property);

                    PropertyConfigPresenter.this.view.setProperties(temporaryList);

                    addPropertyLogPresenter.hideDialog();

                } else {
                    PropertyConfigPresenter.this.view.showErrorMessage();
                }
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
        view.hideWindow();
    }

    /** {@inheritDoc} */
    @Override
    public void onAddPropertyButtonClicked() {
        addPropertyLogPresenter.showDialog(null, editorAddPropertyCallBack);
    }

    /** {@inheritDoc} */
    @Override
    public void onRemovePropertyButtonClicked() {
        temporaryList.remove(selectedProperty);

        view.setProperties(temporaryList);
    }

    /** {@inheritDoc} */
    @Override
    public void onOkButtonClicked() {
        addPropertyCallback.onPropertiesChanged(temporaryList);

        view.hideWindow();
    }

    /** {@inheritDoc} */
    @Override
    public void onEditButtonClicked() {
        addPropertyLogPresenter.showDialog(selectedProperty, editPropertyCallBack);
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
        temporaryList = copyPropertyList(properties);
        addPropertyCallback = callback;

        view.setProperties(temporaryList);

        view.showWindow(title);
    }
}
