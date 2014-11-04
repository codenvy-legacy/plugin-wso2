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
package com.codenvy.ide.client.propertiespanel.common.addpropertydialog.general;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.AbstractEntityElement;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.PropertyPanelFactory;
import com.codenvy.ide.client.propertiespanel.common.addpropertydialog.AddPropertyCallBack;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static com.codenvy.ide.client.elements.AbstractEntityElement.Key;
import static com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter.EditButtonClickedListener;

/**
 * The class provides general business logic of add property dialog window.
 *
 * @author Dmitry Shnurenko
 */
public abstract class AddPropertyPresenter<T extends AbstractEntityElement> implements AddPropertyView.ActionDelegate {

    protected final PropertyPanelFactory propertyPanelFactory;
    protected final PropertyTypeManager  propertyTypeManager;

    protected final NameSpaceEditorPresenter nameSpacePresenter;

    protected final WSO2EditorLocalizationConstant local;
    protected final AddPropertyView                view;

    protected final Provider<T> propertyProvider;

    protected AddPropertyCallBack<T> callBack;
    protected T                      selectedProperty;

    public AddPropertyPresenter(@Nonnull AddPropertyView view,
                                @Nonnull PropertyPanelFactory propertyPanelFactory,
                                @Nonnull PropertyTypeManager propertyTypeManager,
                                @Nonnull NameSpaceEditorPresenter nameSpacePresenter,
                                @Nonnull WSO2EditorLocalizationConstant local,
                                @Nonnull Provider<T> propertyProvider) {

        this.view = view;
        this.local = local;
        this.propertyPanelFactory = propertyPanelFactory;
        this.propertyTypeManager = propertyTypeManager;
        this.nameSpacePresenter = nameSpacePresenter;
        this.propertyProvider = propertyProvider;

        this.view.setDelegate(this);
    }

    /**
     * Methods creates simple panel and adds listener which allows us change state of property.
     *
     * @param key
     *         property name which need to set property to selected element
     * @param columnName
     *         name of column
     */
    protected SimplePropertyPresenter createSimplePanel(@Nonnull final Key<String> key, @Nonnull String columnName) {
        PropertyValueChangedListener nameListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                selectedProperty.putProperty(key, property);
            }
        };

        return propertyPanelFactory.createSimpleProperty(columnName, nameListener);
    }

    /**
     * Methods creates complex panel and adds listener to button.
     *
     * @param nameSpacesKey
     *         property name which need to set name spaces to selected element
     * @param expressionKey
     *         property name which need to set expression to selected element
     * @param nameSpacesCallBack
     *         callback which need to add action on button
     * @param panelName
     *         name of panel
     */
    protected ComplexPropertyPresenter createComplexPanel(@Nonnull final Key<List<NameSpace>> nameSpacesKey,
                                                          @Nonnull final Key<String> expressionKey,
                                                          @Nonnull final AddNameSpacesCallBack nameSpacesCallBack,
                                                          @Nonnull String panelName) {

        EditButtonClickedListener editButtonListener = new EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                List<NameSpace> nameSpaces = selectedProperty.getProperty(nameSpacesKey);
                String xPath = selectedProperty.getProperty(expressionKey);

                if (xPath == null || nameSpaces == null) {
                    return;
                }
                nameSpacePresenter.showWindowWithParameters(nameSpaces,
                                                            nameSpacesCallBack,
                                                            local.expressionTitle(),
                                                            xPath);
            }
        };

        return propertyPanelFactory.createComplexProperty(panelName, editButtonListener);
    }

    /** Hides dialog window for editing properties. */
    public void hideDialog() {
        view.hideWindow();
    }

    /** {@inheritDoc} */
    @Override
    public void onCancelButtonClicked() {
        view.hideWindow();
    }

    /** {@inheritDoc} */
    @Override
    public void onOkButtonClicked() {
        callBack.onPropertyChanged(selectedProperty);
    }

    /**
     * Shows dialog window for editing properties.
     *
     * @param property
     *         selected property which need to edit
     * @param callBack
     *         callback to add edited property to list of properties
     */
    public abstract void showDialog(@Nullable T property, @Nonnull AddPropertyCallBack<T> callBack);

}
