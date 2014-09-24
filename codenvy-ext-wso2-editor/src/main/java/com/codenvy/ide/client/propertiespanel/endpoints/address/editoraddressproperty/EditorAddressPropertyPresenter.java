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
package com.codenvy.ide.client.propertiespanel.endpoints.address.editoraddressproperty;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.endpoints.addressendpoint.Property;
import com.codenvy.ide.client.elements.mediators.ValueType;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static com.codenvy.ide.client.elements.endpoints.addressendpoint.Property.NAME;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.Property.NAMESPACES;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.Property.SCOPE;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.Property.Scope;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.Property.TYPE;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.Property.VALUE;
import static com.codenvy.ide.client.elements.mediators.ValueType.EXPRESSION;

/**
 * The class provides the business logic that allows editor to react on user's action related to change of Address's
 * endpoint properties. Logic which provides the class allows add, remove and edit properties of Address endpoint via
 * special dialog window.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
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
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                selectedProperty.putProperty(NAMESPACES, nameSpaces);
                selectedProperty.putProperty(Property.EXPRESSION, expression);
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onOkButtonClicked() {
        selectedProperty.putProperty(NAME, view.getName());
        selectedProperty.putProperty(VALUE, view.getValue());
        selectedProperty.putProperty(SCOPE, Scope.getItemByValue(view.getScope()));
        selectedProperty.putProperty(TYPE, ValueType.valueOf(view.getType()));

        callBack.onAddressPropertyChanged(selectedProperty);
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

        selectedProperty.putProperty(TYPE, type);

        boolean isVisible = EXPRESSION.equals(type);

        view.setTextBoxEnable(!isVisible);
        view.setNameSpaceBtnVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void onValueScopeChanged() {
        selectedProperty.putProperty(SCOPE, Scope.getItemByValue(view.getScope()));
    }

    /** {@inheritDoc} */
    @Override
    public void onAddNameSpaceBtnClicked() {
        List<NameSpace> nameSpaces = selectedProperty.getProperty(NAMESPACES);

        if (nameSpaces == null) {
            return;
        }

        nameSpacePresenter.showWindowWithParameters(nameSpaces,
                                                    nameSpacesCallBack,
                                                    local.headerValueExpression(),
                                                    selectedProperty.getProperty(Property.EXPRESSION));
    }

    /** Hides dialog window for editing properties. */
    public void hideDialog() {
        view.hideWindow();
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

        selectedProperty = property == null ? propertyProvider.get() : property.copy();

        boolean isVisible = EXPRESSION.equals(selectedProperty.getProperty(TYPE));

        view.setTextBoxEnable(!isVisible);
        view.setNameSpaceBtnVisible(isVisible);

        String name = selectedProperty.getProperty(NAME);
        String value = selectedProperty.getProperty(VALUE);
        Scope scope = selectedProperty.getProperty(SCOPE);
        ValueType type = selectedProperty.getProperty(TYPE);

        if (name == null || value == null || scope == null || type == null) {
            return;
        }

        view.setName(name);
        view.setValue(value);
        view.selectScope(scope.getValue());
        view.selectType(type.name());

        view.showWindow();
    }

}
