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
package com.codenvy.ide.client.propertiespanel.endpoints.address.property;

import com.codenvy.ide.client.CellTableResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.endpoints.address.Property;
import com.codenvy.ide.client.elements.mediators.ValueType;
import com.codenvy.ide.ui.window.Window;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static com.codenvy.ide.client.elements.endpoints.address.Property.EXPRESSION;
import static com.codenvy.ide.client.elements.endpoints.address.Property.NAME;
import static com.codenvy.ide.client.elements.endpoints.address.Property.SCOPE;
import static com.codenvy.ide.client.elements.endpoints.address.Property.TYPE;
import static com.codenvy.ide.client.elements.endpoints.address.Property.VALUE;

/**
 * Provides a graphical representation of dialog window for editing property of address endpoint.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class PropertyViewImpl extends Window implements PropertyView {

    @Singleton
    interface PropertyViewImplUiBinder extends UiBinder<Widget, PropertyViewImpl> {
    }

    @UiField(provided = true)
    final CellTable<Property> properties;

    @UiField(provided = true)
    final WSO2EditorLocalizationConstant locale;
    @UiField(provided = true)
    final CellTableResources             resource;

    private ActionDelegate delegate;

    @Inject
    public PropertyViewImpl(PropertyViewImplUiBinder ourUiBinder, WSO2EditorLocalizationConstant locale, CellTableResources resource) {
        this.locale = locale;
        this.resource = resource;

        this.properties = createTable(resource);

        setTitle(locale.endpointPropertiesTitle());
        setWidget(ourUiBinder.createAndBindUi(this));

        Button btnCancel = createButton(locale.buttonCancel(), "endpoint-properties-cancel", new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                delegate.onCancelButtonClicked();
            }
        });
        getFooter().add(btnCancel);

        Button btnOk = createButton(locale.buttonOk(), "endpoint-properties-ok", new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                delegate.onOkButtonClicked();
            }
        });
        getFooter().add(btnOk);
    }

    /** Adds columns names and values to table. Sets selection model to table. */
    private CellTable<Property> createTable(@Nonnull CellTableResources resource) {
        CellTable<Property> table = new CellTable<>(0, resource);

        final SingleSelectionModel<Property> selectionModel = new SingleSelectionModel<>();
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                delegate.onPropertySelected(selectionModel.getSelectedObject());
            }
        });
        table.setSelectionModel(selectionModel);

        TextColumn<Property> name = new TextColumn<Property>() {
            @Override
            public String getValue(Property property) {
                return property.getProperty(NAME);
            }
        };

        TextColumn<Property> value = new TextColumn<Property>() {
            @Override
            public String getValue(Property property) {
                if (ValueType.LITERAL.equals(property.getProperty(TYPE))) {
                    return property.getProperty(VALUE);
                }

                return property.getProperty(EXPRESSION);
            }
        };

        TextColumn<Property> type = new TextColumn<Property>() {
            @Override
            public String getValue(Property property) {
                ValueType type = property.getProperty(TYPE);

                if (type == null) {
                    return "";
                }

                return type.name();
            }
        };

        TextColumn<Property> scope = new TextColumn<Property>() {
            @Override
            public String getValue(Property property) {
                Property.Scope scope = property.getProperty(SCOPE);

                if (scope == null) {
                    return "";
                }

                return scope.getValue();
            }
        };

        table.addColumn(name, locale.columnName());
        table.addColumn(value, locale.columnValue());
        table.addColumn(type, locale.columnType());
        table.addColumn(scope, locale.columnScope());

        table.setColumnWidth(name, 150, Style.Unit.PX);
        table.setColumnWidth(value, 150, Style.Unit.PX);
        table.setColumnWidth(type, 60, Style.Unit.PX);
        table.setColumnWidth(scope, 100, Style.Unit.PX);

        table.setLoadingIndicator(null);

        return table;
    }

    /** {@inheritDoc} */
    @Override
    public void setDelegate(@Nonnull ActionDelegate delegate) {
        this.delegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public void setProperties(@Nonnull List<Property> properties) {
        List<Property> list = new ArrayList<>();

        for (Property property : properties) {
            list.add(property);
        }

        this.properties.setRowData(list);
    }

    /** {@inheritDoc} */
    @Override
    public void showDialog() {
        show();
    }

    /** {@inheritDoc} */
    @Override
    public void hideDialog() {
        hide();
    }

    /** {@inheritDoc} */
    @Override
    protected void onClose() {
        hide();
    }

    @UiHandler("btnAdd")
    public void onAddButtonClicked(@SuppressWarnings("UnusedParameters") ClickEvent event) {
        delegate.onAddButtonClicked();
    }

    @UiHandler("btnEdit")
    public void onEditButtonClicked(@SuppressWarnings("UnusedParameters") ClickEvent event) {
        delegate.onEditButtonClicked();
    }

    @UiHandler("btnRemove")
    public void onRemoveButtonClicked(@SuppressWarnings("UnusedParameters") ClickEvent event) {
        delegate.onRemoveButtonClicked();
    }

}