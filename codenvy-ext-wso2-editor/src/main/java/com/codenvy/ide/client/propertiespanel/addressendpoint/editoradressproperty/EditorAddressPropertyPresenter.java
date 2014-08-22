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
package com.codenvy.ide.client.propertiespanel.addressendpoint.editoradressproperty;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.ValueType;
import com.codenvy.ide.client.elements.addressendpoint.Property;
import com.codenvy.ide.client.propertiespanel.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.collections.Array;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.codenvy.ide.client.elements.ValueType.EXPRESSION;
import static com.codenvy.ide.client.elements.addressendpoint.Property.Scope;

/**
 * @author Dmitry Shnurenko
 */
public class EditorAddressPropertyPresenter implements EditorAddressPropertyView.ActionDelegate {

    private final WSO2EditorLocalizationConstant local;
    private final EditorAddressPropertyView      view;
    private final NameSpaceEditorPresenter       nameSpacePresenter;
    private final AddNameSpacesCallBack          nameSpacesCallBack;
    private final Provider<Property>             propertyProvider;

    private EditorAddressPropertyCallBack callBack;
    private Property                      selectedProperty;

    @Inject
    public EditorAddressPropertyPresenter(EditorAddressPropertyView view,
                                          Provider<Property> propertyProvider,
                                          NameSpaceEditorPresenter nameSpacePresenter,
                                          WSO2EditorLocalizationConstant local) {
        this.view = view;
        this.local = local;
        this.propertyProvider = propertyProvider;
        this.nameSpacePresenter = nameSpacePresenter;

        this.view.setDelegate(this);

        this.nameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                selectedProperty.setNameSpaces(nameSpaces);
                selectedProperty.setExpression(expression);
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onOkButtonClicked() {
        selectedProperty.setName(view.getName());
        selectedProperty.setValue(view.getValue());
        selectedProperty.setScope(Scope.getItemByValue(view.getScope()));
        selectedProperty.setType(ValueType.valueOf(view.getType()));

        callBack.onAddressPropertyChanged(selectedProperty);

        view.hideWindow();
    }

    /** {@inheritDoc} */
    @Override
    public void onCancelButtonClicked() {
        view.hideWindow();
    }

    /** {@inheritDoc} */
    @Override
    public void onValueTypeChanged() {
        ValueType type = ValueType.valueOf(view.getType());

        selectedProperty.setType(type);

        boolean isVisible = EXPRESSION.equals(type);

        view.setTextBoxEnable(!isVisible);
        view.setNameSpaceBtnVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void onValueScopeChanged() {
        selectedProperty.setScope(Scope.getItemByValue(view.getScope()));
    }

    /** {@inheritDoc} */
    @Override
    public void onAddNameSpaceBtnClicked() {
        nameSpacePresenter.showWindowWithParameters(selectedProperty.getNameSpaces(),
                                                    nameSpacesCallBack,
                                                    local.headerValueExpression(),
                                                    selectedProperty.getExpression());

    }

    /**
     * Shows dialog window for editing properties of address end point.
     *
     * @param property
     *         selected property which need to edit
     * @param callBack
     *         callback to add edited property to list of properties
     */
    public void showDialog(@Nullable Property property, @Nonnull EditorAddressPropertyCallBack callBack) {
        this.callBack = callBack;

        selectedProperty = property == null ? propertyProvider.get() : property.clone();

        boolean isVisible = EXPRESSION.equals(selectedProperty.getType());

        view.setTextBoxEnable(!isVisible);
        view.setNameSpaceBtnVisible(isVisible);

        view.setName(selectedProperty.getName());
        view.setValue(selectedProperty.getValue());
        view.selectScope(selectedProperty.getScope().getValue());
        view.selectType(selectedProperty.getType().name());

        view.showWindow();
    }
}
