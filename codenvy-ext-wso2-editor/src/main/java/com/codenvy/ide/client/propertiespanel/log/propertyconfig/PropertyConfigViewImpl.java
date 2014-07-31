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
package com.codenvy.ide.client.propertiespanel.log.propertyconfig;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.log.Log;
import com.codenvy.ide.client.elements.log.Property;
import com.codenvy.ide.collections.Array;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Shnurenko
 */
public class PropertyConfigViewImpl extends DialogBox implements PropertyConfigView {

    interface LogPropertiesConfigurationViewImplUiBinder extends UiBinder<Widget, PropertyConfigViewImpl> {
    }

    @UiField(provided = true)
    CellTable tableOfProperties;
    @UiField(provided = true)
    ListBox   logLevelListBox;
    @UiField(provided = true)
    ListBox   logCategoryListBox;
    @UiField
    TextBox   logSeparatorTextBox;
    @UiField
    Button    btnAdd;
    @UiField
    Button    btnRemove;
    @UiField
    Button    btnCancel;
    @UiField
    Button    btnSaveChanges;
    @UiField
    Button    btnEdit;
    @UiField
    TextBox   nameTextBox;
    @UiField
    TextBox   valueExpressionTextBox;

    private ActionDelegate delegate;

    @Inject
    public PropertyConfigViewImpl(LogPropertiesConfigurationViewImplUiBinder ourUiBinder,
                                  WSO2EditorLocalizationConstant localizationConstant) {
        this.tableOfProperties = createTable(localizationConstant);
        this.logLevelListBox = createLogLevelListBox();
        this.logCategoryListBox = createLogCategoryListBox();

        this.add(ourUiBinder.createAndBindUi(this));
    }

    private CellTable<Property> createTable(final WSO2EditorLocalizationConstant localizationConstant) {
        final CellTable<Property> table = new CellTable<>();

        final SingleSelectionModel<Property> selectionModel = new SingleSelectionModel<>();
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                final Property selectedObject = selectionModel.getSelectedObject();
                delegate.onSelectedProperty(selectedObject);
            }
        });
        table.setSelectionModel(selectionModel);

        TextColumn<Property> name = new TextColumn<Property>() {
            @Override
            public String getValue(Property object) {
                return object.getName();
            }
        };

        TextColumn<Property> expression = new TextColumn<Property>() {
            @Override
            public String getValue(Property object) {
                return object.getExpression();
            }
        };

        ButtonCell namespaceEditor = new ButtonCell();

        Column<Property, String> namespaceEditorButton = new Column<Property, String>(namespaceEditor) {
            @Override
            public String getValue(Property object) {
                return localizationConstant.buttonEditConfig();
            }
        };
        namespaceEditorButton.setFieldUpdater(new FieldUpdater<Property, String>() {
            @Override
            public void update(int index, Property object, String value) {
                delegate.onSelectedProperty(selectionModel.getSelectedObject());
                delegate.onEditPropertiesButtonClicked();
            }
        });

        final List<String> typeProperty = new ArrayList<>();
        typeProperty.add(Type.LITERAL.name());
        typeProperty.add(Type.EXPRESSION.name());
        SelectionCell categoryCell = new SelectionCell(typeProperty);

        Column<Property, String> type = new Column<Property, String>(categoryCell) {
            @Override
            public String getValue(Property object) {
                return "";
            }
        };

        table.addColumn(name, localizationConstant.columnName());
        table.addColumn(type, localizationConstant.columnType());
        table.addColumn(expression, localizationConstant.columnExpression());
        table.addColumn(namespaceEditorButton);

        table.setColumnWidth(name, 210, Style.Unit.PX);
        table.setColumnWidth(type, 120, Style.Unit.PX);
        table.setColumnWidth(expression, 210, Style.Unit.PX);
        table.setColumnWidth(namespaceEditorButton, 30, Style.Unit.PX);

        table.setLoadingIndicator(null);

        return table;
    }

    private ListBox createLogCategoryListBox() {
        ListBox listBox = new ListBox();

        listBox.addItem(Log.LogCategory.TRACE.name());
        listBox.addItem(Log.LogCategory.DEBUG.name());
        listBox.addItem(Log.LogCategory.INFO.name());
        listBox.addItem(Log.LogCategory.WARN.name());
        listBox.addItem(Log.LogCategory.ERROR.name());
        listBox.addItem(Log.LogCategory.FATAL.name());

        return listBox;
    }

    private ListBox createLogLevelListBox() {
        ListBox listBox = new ListBox();

        listBox.addItem(Log.LogLevel.SIMPLE.name());
        listBox.addItem(Log.LogLevel.HEADERS.name());
        listBox.addItem(Log.LogLevel.FULL.name());
        listBox.addItem(Log.LogLevel.CUSTOM.name());

        return listBox;
    }

    @UiHandler("btnCancel")
    public void onCancelButtonClicked(ClickEvent event) {
        delegate.onCancelButtonClicked();
    }

    @UiHandler("btnAdd")
    public void onAddNewPropertyButtonClicked(ClickEvent event) {
        delegate.onAddPropertyButtonClicked();
    }

    @UiHandler("btnRemove")
    public void onRemovePropertyButtonClicked(ClickEvent event) {
        delegate.onRemovePropertyButtonClicked();
    }

    @UiHandler("btnSaveChanges")
    public void onOkButtonClicked(ClickEvent event) {
        delegate.onOkButtonClicked();
    }

    @UiHandler("btnEdit")
    public void onEditPropertyButtonClicked(ClickEvent event) {
        delegate.onEditButtonClicked();
    }

    /** {@inheritDoc} */
    @Override
    public void showWindow() {
        center();
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
    public void setProperties(@Nonnull Array<Property> propertyList) {
        List<Property> list = new ArrayList<>();

        for (Property property : propertyList.asIterable()) {
            list.add(property);
        }

        tableOfProperties.setRowData(list);
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    public String getName() {
        return nameTextBox.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setName(@Nonnull String text) {
        this.nameTextBox.setText(text);
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    public String getValueExpression() {
        return valueExpressionTextBox.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setValueExpression(@Nonnull String text) {
        this.valueExpressionTextBox.setText(text);
    }

}
