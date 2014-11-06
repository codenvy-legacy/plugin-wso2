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

import com.codenvy.ide.client.CellTableResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.mediators.ValueType;
import com.codenvy.ide.client.elements.mediators.log.Property;
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

import static com.codenvy.ide.client.elements.mediators.log.Property.NAME;
import static com.codenvy.ide.client.elements.mediators.log.Property.TYPE;
import static com.codenvy.ide.client.elements.mediators.log.Property.VALUE;

/**
 * Provides a graphical representation of dialog window for editing property.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 * @author Andrey Plotnikov
 */
public class PropertyConfigViewImpl extends Window implements PropertyConfigView {

    @Singleton
    interface LogPropertiesConfigurationViewImplUiBinder extends UiBinder<Widget, PropertyConfigViewImpl> {
    }

    @UiField(provided = true)
    final CellTable<Property> tableOfProperties;

    @UiField(provided = true)
    final WSO2EditorLocalizationConstant locale;
    @UiField(provided = true)
    final CellTableResources             resource;

    private ActionDelegate delegate;

    @Inject
    public PropertyConfigViewImpl(WSO2EditorLocalizationConstant localizationConstant,
                                  LogPropertiesConfigurationViewImplUiBinder uiBinder,
                                  CellTableResources resource) {
        this.locale = localizationConstant;
        this.resource = resource;
        this.tableOfProperties = createTable(localizationConstant, resource);

        setWidget(uiBinder.createAndBindUi(this));

        Button btnCancel = createButton(localizationConstant.buttonCancel(), "property-configuration-cancel", new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                delegate.onCancelButtonClicked();
            }
        });
        getFooter().add(btnCancel);

        Button btnOk = createButton(localizationConstant.buttonOk(), "property-configuration-ok", new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                delegate.onOkButtonClicked();
            }
        });
        getFooter().add(btnOk);
    }

    /**
     * Returns cell table entity. Adds column names and values to table. Sets selection model to table.
     *
     * @param localizationConstant
     *         localization constant which contains special names of element in current table
     */
    private CellTable<Property> createTable(@Nonnull final WSO2EditorLocalizationConstant localizationConstant,
                                            @Nonnull CellTableResources resource) {

        final CellTable<Property> table = new CellTable<>(0, resource);

        final SingleSelectionModel<Property> selectionModel = new SingleSelectionModel<>();
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                delegate.onSelectedProperty(selectionModel.getSelectedObject());
            }
        });
        table.setSelectionModel(selectionModel);

        TextColumn<Property> name = new TextColumn<Property>() {
            @Override
            public String getValue(Property property) {
                String name = property.getProperty(NAME);

                return name == null ? "" : name;
            }
        };

        TextColumn<Property> expression = new TextColumn<Property>() {
            @Override
            public String getValue(Property property) {
                String value = property.getProperty(VALUE);

                return value == null ? "" : value;
            }
        };

        TextColumn<Property> type = new TextColumn<Property>() {
            @Override
            public String getValue(Property property) {
                ValueType type = property.getProperty(TYPE);

                return type == null ? "" : type.name();
            }
        };

        table.addColumn(name, localizationConstant.columnName());
        table.addColumn(type, localizationConstant.columnType());
        table.addColumn(expression, localizationConstant.columnExpression());

        table.setColumnWidth(name, 210, Style.Unit.PX);
        table.setColumnWidth(type, 120, Style.Unit.PX);
        table.setColumnWidth(expression, 210, Style.Unit.PX);

        table.setLoadingIndicator(null);

        return table;
    }

    @UiHandler("btnAdd")
    public void onAddNewPropertyButtonClicked(@SuppressWarnings("UnusedParameters") ClickEvent event) {
        delegate.onAddPropertyButtonClicked();
    }

    @UiHandler("btnRemove")
    public void onRemovePropertyButtonClicked(@SuppressWarnings("UnusedParameters") ClickEvent event) {
        delegate.onRemovePropertyButtonClicked();
    }

    @UiHandler("btnEdit")
    public void onEditPropertyButtonClicked(@SuppressWarnings("UnusedParameters") ClickEvent event) {
        delegate.onEditButtonClicked();
    }

    /** {@inheritDoc} */
    @Override
    public void showWindow(@Nonnull String title) {
        setTitle(title);
        show();
    }

    /** {@inheritDoc} */
    @Override
    public void hideWindow() {
        hide();
    }

    /** {@inheritDoc} */
    @Override
    public void setDelegate(@Nonnull ActionDelegate delegate) {
        this.delegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public void setProperties(@Nonnull List<Property> propertyList) {
        List<Property> list = new ArrayList<>();

        for (Property property : propertyList) {
            list.add(property);
        }

        tableOfProperties.setRowData(list);
    }

    /** {@inheritDoc} */
    @Override
    protected void onClose() {
        hide();
    }

}