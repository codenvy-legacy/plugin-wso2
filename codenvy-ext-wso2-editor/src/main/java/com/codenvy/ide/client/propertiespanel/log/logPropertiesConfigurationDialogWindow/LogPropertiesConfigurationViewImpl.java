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
package com.codenvy.ide.client.propertiespanel.log.logPropertiesConfigurationDialogWindow;

import com.codenvy.ide.client.elements.Log;
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
public class LogPropertiesConfigurationViewImpl extends DialogBox implements LogPropertiesConfigurationView {

    interface LogPropertiesConfigurationViewImplUiBinder extends UiBinder<Widget, LogPropertiesConfigurationViewImpl> {
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
    Button    addPropertyToTable;
    @UiField
    Button    removeProperty;
    @UiField
    Button    cancelWindow;
    @UiField
    Button    addPropertyToElement;
    @UiField
    Button    editPropertyButton;
    @UiField
    TextBox   nameTextBox;
    @UiField
    TextBox   valueExpressionTextBox;

    private ButtonCell     namespaceEditor;
    private ActionDelegate delegate;


    @Inject
    public LogPropertiesConfigurationViewImpl(LogPropertiesConfigurationViewImplUiBinder ourUiBinder) {
        // TODO local constant

        this.tableOfProperties = createTable();
        this.logLevelListBox = createLogLevelListBox();
        this.logCategoryListBox = createLogCategoryListBox();

        this.add(ourUiBinder.createAndBindUi(this));
    }

    private CellTable<Log.Property> createTable() {
        final CellTable<Log.Property> table = new CellTable<>();

        TextColumn<Log.Property> name = new TextColumn<Log.Property>() {
            @Override
            public String getValue(Log.Property object) {
                return object.getName();
            }
        };

        TextColumn<Log.Property> expression = new TextColumn<Log.Property>() {
            @Override
            public String getValue(Log.Property object) {
                return object.getExpression();
            }
        };

        namespaceEditor = new ButtonCell();

        Column<Log.Property, String> namespaceEditorButton = new Column<Log.Property, String>(namespaceEditor) {
            @Override
            public String getValue(Log.Property object) {
                return "";
            }
        };
        namespaceEditorButton.setFieldUpdater(new FieldUpdater<Log.Property, String>() {
            @Override
            public void update(int index, Log.Property object, String value) {
                delegate.showNameSpaceEditorWindow();
            }
        });

        final List<String> typeProperty = new ArrayList<>();
        typeProperty.add("LITERAL");
        typeProperty.add("EXPRESSION");
        SelectionCell categoryCell = new SelectionCell(typeProperty);

        Column<Log.Property, String> type = new Column<Log.Property, String>(categoryCell) {
            @Override
            public String getValue(Log.Property object) {
                return object.getType();
            }
        };

        // TODO local constnat
        table.addColumn(name, "Name");
        table.addColumn(type, "Type");
        table.addColumn(expression, "Value/Expression");
        table.addColumn(namespaceEditorButton);

        table.setColumnWidth(name, 210, Style.Unit.PX);
        table.setColumnWidth(type, 120, Style.Unit.PX);
        table.setColumnWidth(expression, 210, Style.Unit.PX);
        table.setColumnWidth(namespaceEditorButton, 30, Style.Unit.PX);

        table.setLoadingIndicator(null);

        final SingleSelectionModel<Log.Property> selectionModel = new SingleSelectionModel<>();
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                final Log.Property selectedObject = selectionModel.getSelectedObject();
                delegate.onSelectedProperty(selectedObject);
            }
        });
        table.setSelectionModel(selectionModel);

        return table;
    }

    private ListBox createLogCategoryListBox() {
        ListBox listBox = new ListBox();
        listBox.addItem("TRACE");
        listBox.addItem("DEBUG");
        listBox.addItem("INFO");
        listBox.addItem("WARN");
        listBox.addItem("ERROR");
        listBox.addItem("FATAL");
        return listBox;
    }

    private ListBox createLogLevelListBox() {
        ListBox listBox = new ListBox();
        listBox.addItem("SAMPLE");
        listBox.addItem("HEADERS");
        listBox.addItem("FULL");
        listBox.addItem("CUSTOM");
        return listBox;
    }

    @UiHandler("cancelWindow")
    public void onCancelButtonClicked(ClickEvent event) {
        delegate.onCancelButtonClicked();
    }

    @UiHandler("addPropertyToTable")
    public void onAddNewPropertyButtonClicked(ClickEvent event) {
        delegate.onAddPropertyButtonClicked();
    }

    @UiHandler("removeProperty")
    public void onRemovePropertyButtonClicked(ClickEvent event) {
        delegate.onRemovePropertyButtonClicked();
    }

    @UiHandler("addPropertyToElement")
    public void onOkButtonClicked(ClickEvent event) {
        delegate.onOkButtonClicked();
    }

    @UiHandler("editPropertyButton")
    public void onEditPropertyButtonClicked(ClickEvent event) {
        delegate.onEditPropertyButtonClicked();
    }

    @Override
    public void showPropertyConfigurationWindow() {
        show();
    }

    @Override
    public void hidePropertyConfigurationWindow() {
        hide();
    }

    @Override
    public void setDelegate(@Nonnull ActionDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void setPropertiesList(List<Log.Property> propertyList) {
        tableOfProperties.setRowData(propertyList);
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    public String getSeparatorText() {
        return logSeparatorTextBox.getText();
    }

    public void setSeparatorText(String text) {
        this.logSeparatorTextBox.setText(text);
    }

    public String getNameText() {
        return nameTextBox.getText();
    }

    public void setNameText(String text) {
        this.nameTextBox.setText(text);
    }

    public String getValueExpression() {
        return valueExpressionTextBox.getText();
    }

    public void setValueExpressionText(String text) {
        this.valueExpressionTextBox.setText(text);
    }

}
