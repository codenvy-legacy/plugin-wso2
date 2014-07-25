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
package com.codenvy.ide.client.propertiespanel.log.logPropertiesConfigurationDialogWindow.nameSpaceEditorDialogWindow;

import com.codenvy.ide.client.elements.Log;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Dmitry Shnurenko
 */
public class NameSpaceEditorViewImpl extends DialogBox implements NameSpaceEditorView {
    interface NameSpaceEditorViewImplUiBinder extends UiBinder<Widget, NameSpaceEditorViewImpl> {
    }

    @UiField(provided = true)
    CellTable nameSpacesTable;
    @UiField
    TextBox   logSeparatorTextBox;
    @UiField
    Button    selectPathButton;
    @UiField
    TextBox   prefixTextBox;
    @UiField
    TextBox   uriTextBox;
    @UiField
    Button    addNamespaceButton;
    @UiField
    Button    editNamespaceButton;
    @UiField
    Button    removeNamespaceButton;
    @UiField
    Button    cancelButton;
    @UiField
    Button    okButton;


    private ActionDelegate delegate;

    @Inject
    public NameSpaceEditorViewImpl(NameSpaceEditorViewImplUiBinder uiBinder) {
        this.nameSpacesTable = createTable();
        add(uiBinder.createAndBindUi(this));
    }

    private CellTable<Log.Property.NameSpace> createTable() {
        CellTable<Log.Property.NameSpace> table = new CellTable<>();

        Column<Log.Property.NameSpace, String> nameSpace = new Column<Log.Property.NameSpace, String>(new TextCell()) {
            @Override
            public String getValue(Log.Property.NameSpace object) {
                return object.toString();
            }
        };
        table.setLoadingIndicator(null);

        table.addColumn(nameSpace);
        table.setColumnWidth(nameSpace, 570, Style.Unit.PX);

        final SingleSelectionModel<Log.Property.NameSpace> selectionModel = new SingleSelectionModel<>();
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                final Log.Property.NameSpace selectedObject = selectionModel.getSelectedObject();
                delegate.onSelectedNameSpace(selectedObject);
            }
        });
        table.setSelectionModel(selectionModel);
        return table;
    }

    @UiHandler("addNamespaceButton")
    public void onAddNameSpaceButtonClicked(ClickEvent event) {
        delegate.onAddNameSpaceButtonClicked();
    }

    @UiHandler("cancelButton")
    public void onCancelButtonClicked(ClickEvent event) {
        delegate.onCancelButtonClicked();
    }

    @UiHandler("addNamespaceButton")
    public void onOkButtonClicked(ClickEvent event) {
        delegate.onOkButtonClicked();
    }

    @UiHandler("editNamespaceButton")
    public void onEditButtonClicked(ClickEvent event) {
        delegate.onEditButtonClicked();
    }

    @UiHandler("removeNamespaceButton")
    public void onRemoveButtonClicked(ClickEvent event){
        delegate.onRemoveButtonClicked();
    }

    @UiHandler("selectPathButton")
    public void onSelectXPathButtonClicked(ClickEvent event) {
        delegate.onSelectXPathButtonClicked();
    }

    @Override
    public void showNameSpaceEditorDialogWindow() {
        show();
    }

    @Override
    public void hideNameSpaceEditorDialogWindow() {
        hide();
    }

    @Override
    public String getPrefixText() {
        return prefixTextBox.getText();
    }

    @Override
    public String getUriText() {
        return uriTextBox.getText();
    }

    @Override
    public void setPrefixText(String text) {
        prefixTextBox.setText(text);
    }

    @Override
    public void setUriText(String text) {
        uriTextBox.setText(text);
    }

    @Override
    public void setDelegate(@Nonnull ActionDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void setNameSpacesList(List<Log.Property.NameSpace> nameSpacesList) {
        nameSpacesTable.setRowData(nameSpacesList);
    }
}
