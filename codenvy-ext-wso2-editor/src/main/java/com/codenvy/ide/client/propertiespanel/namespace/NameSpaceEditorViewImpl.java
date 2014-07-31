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
package com.codenvy.ide.client.propertiespanel.namespace;

import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.collections.Array;
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
import java.util.ArrayList;
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
    Button    btnAdd;
    @UiField
    Button    btnEdit;
    @UiField
    Button    btnRemove;
    @UiField
    Button    btnCancel;
    @UiField
    Button    btnSaveChanges;

    private ActionDelegate delegate;

    @Inject
    public NameSpaceEditorViewImpl(NameSpaceEditorViewImplUiBinder uiBinder) {
        this.nameSpacesTable = createTable();

        add(uiBinder.createAndBindUi(this));
    }

    private CellTable<NameSpace> createTable() {
        CellTable<NameSpace> table = new CellTable<>();

        Column<NameSpace, String> nameSpace = new Column<NameSpace, String>(new TextCell()) {
            @Override
            public String getValue(NameSpace object) {
                return object.toString();
            }
        };

        table.setLoadingIndicator(null);

        table.addColumn(nameSpace);
        table.setColumnWidth(nameSpace, 570, Style.Unit.PX);

        final SingleSelectionModel<NameSpace> selectionModel = new SingleSelectionModel<>();
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                delegate.onSelectedNameSpace(selectionModel.getSelectedObject());
            }
        });
        table.setSelectionModel(selectionModel);

        return table;
    }

    @UiHandler("btnAdd")
    public void onAddNameSpaceButtonClicked(ClickEvent event) {
        delegate.onAddNameSpaceButtonClicked();
    }

    @UiHandler("btnCancel")
    public void onCancelButtonClicked(ClickEvent event) {
        delegate.onCancelButtonClicked();
    }

    @UiHandler("btnSaveChanges")
    public void onOkButtonClicked(ClickEvent event) {
        delegate.onOkButtonClicked();
    }

    @UiHandler("btnEdit")
    public void onEditButtonClicked(ClickEvent event) {
        delegate.onEditButtonClicked();
    }

    @UiHandler("btnRemove")
    public void onRemoveButtonClicked(ClickEvent event) {
        delegate.onRemoveButtonClicked();
    }

    @UiHandler("selectPathButton")
    public void onSelectXPathButtonClicked(ClickEvent event) {
        delegate.onSelectXPathButtonClicked();
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
    @Nonnull
    public String getPrefix() {
        return prefixTextBox.getText();
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    public String getUri() {
        return uriTextBox.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setPrefix(@Nonnull String text) {
        prefixTextBox.setText(text);
    }

    /** {@inheritDoc} */
    @Override
    public void setUri(@Nonnull String text) {
        uriTextBox.setText(text);
    }

    /** {@inheritDoc} */
    @Override
    public void setDelegate(@Nonnull ActionDelegate delegate) {
        this.delegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public void setNameSpaces(@Nonnull Array<NameSpace> nameSpacesList) {
        List<NameSpace> list = new ArrayList<>();

        for (NameSpace property : nameSpacesList.asIterable()) {
            list.add(property);
        }

        nameSpacesTable.setRowData(list);
    }
}
