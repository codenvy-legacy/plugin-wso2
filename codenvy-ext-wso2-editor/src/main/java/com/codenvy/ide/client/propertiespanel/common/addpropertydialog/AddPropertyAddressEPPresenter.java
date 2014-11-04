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
package com.codenvy.ide.client.propertiespanel.common.addpropertydialog;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.endpoints.address.Property;
import com.codenvy.ide.client.elements.mediators.ValueType;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.PropertyPanelFactory;
import com.codenvy.ide.client.propertiespanel.common.addpropertydialog.general.AddPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.common.addpropertydialog.general.AddPropertyView;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.list.ListPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static com.codenvy.ide.client.elements.endpoints.address.Property.NAME;
import static com.codenvy.ide.client.elements.endpoints.address.Property.NAMESPACES;
import static com.codenvy.ide.client.elements.endpoints.address.Property.SCOPE;
import static com.codenvy.ide.client.elements.endpoints.address.Property.Scope;
import static com.codenvy.ide.client.elements.endpoints.address.Property.TYPE;
import static com.codenvy.ide.client.elements.endpoints.address.Property.VALUE;
import static com.codenvy.ide.client.elements.mediators.ValueType.EXPRESSION;
import static com.codenvy.ide.client.elements.mediators.ValueType.TYPE_NAME;

/**
 * The class provides the business logic that allows editor to react on user's action related to change of Address's
 * endpoint properties. Logic which provides the class allows add, remove and edit properties of Address endpoint via
 * special dialog window.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class AddPropertyAddressEPPresenter extends AddPropertyPresenter<Property> {

    private final static int DIALOG_HEIGHT = 180;

    private final AddNameSpacesCallBack nameSpacesCallBack;

    private SimplePropertyPresenter  name;
    private SimplePropertyPresenter  value;
    private ComplexPropertyPresenter expression;
    private ListPropertyPresenter    type;
    private ListPropertyPresenter    scope;

    @Inject
    public AddPropertyAddressEPPresenter(AddPropertyView view,
                                         PropertyPanelFactory propertyPanelFactory,
                                         PropertyTypeManager propertyTypeManager,
                                         Provider<Property> propertyProvider,
                                         NameSpaceEditorPresenter nameSpacePresenter,
                                         WSO2EditorLocalizationConstant local) {

        super(view, propertyPanelFactory, propertyTypeManager, nameSpacePresenter, local, propertyProvider);

        nameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expressionValue) {
                selectedProperty.putProperty(NAMESPACES, nameSpaces);
                selectedProperty.putProperty(Property.EXPRESSION, expressionValue);

                expression.setProperty(expressionValue);
            }
        };

        initializePanels();
    }

    private void initializePanels() {
        name = createSimplePanel(NAME, local.columnName());

        value = createSimplePanel(VALUE, local.columnValue());

        expression = createComplexPanel(NAMESPACES, Property.EXPRESSION, nameSpacesCallBack, local.columnExpression());

        PropertyValueChangedListener typeListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                ValueType valueType = ValueType.valueOf(property);

                selectedProperty.putProperty(TYPE, valueType);

                boolean visible = EXPRESSION.equals(valueType);

                value.setVisible(!visible);
                expression.setVisible(visible);
            }
        };
        type = propertyPanelFactory.createListProperty(local.columnType(), typeListener);

        PropertyValueChangedListener scopeListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                selectedProperty.putProperty(SCOPE, Scope.getItemByValue(property));
            }
        };
        scope = propertyPanelFactory.createListProperty(local.columnScope(), scopeListener);

        view.addPanel(name);
        view.addPanel(value);
        view.addPanel(expression);
        view.addPanel(type);
        view.addPanel(scope);

        view.setDialogHeight(DIALOG_HEIGHT);
    }

    /** {@inheritDoc} */
    @Override
    public void showDialog(@Nullable Property property, @Nonnull AddPropertyCallBack<Property> callBack) {
        this.callBack = callBack;

        selectedProperty = property == null ? propertyProvider.get() : property.copy();

        ValueType propertyType = selectedProperty.getProperty(TYPE);
        Scope propertyScope = selectedProperty.getProperty(SCOPE);

        if (propertyScope == null || propertyType == null) {
            return;
        }

        boolean expressionVisible = EXPRESSION.equals(propertyType);

        expression.setVisible(expressionVisible);
        value.setVisible(!expressionVisible);

        name.setProperty(selectedProperty.getProperty(NAME));
        value.setProperty(selectedProperty.getProperty(VALUE));
        expression.setProperty(selectedProperty.getProperty(Property.EXPRESSION));

        type.setValues(propertyTypeManager.getValuesByName(TYPE_NAME));
        type.selectValue(propertyType.name());

        scope.setValues(propertyTypeManager.getValuesByName(Scope.TYPE_NAME));
        scope.selectValue(propertyScope.getValue());

        view.setTitle(local.editPropAdrrEndTableTitle());

        view.showWindow();
    }

}