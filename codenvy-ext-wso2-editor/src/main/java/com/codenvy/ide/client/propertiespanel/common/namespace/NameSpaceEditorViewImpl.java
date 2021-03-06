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
package com.codenvy.ide.client.propertiespanel.common.namespace;

import com.codenvy.ide.client.CellTableResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.ui.window.Window;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides a graphical representation of dialog window for editing name spaces of property.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 * @author Andrey Plotnikov
 */
public class NameSpaceEditorViewImpl extends Window implements NameSpaceEditorView {

    @Singleton
    interface NameSpaceEditorViewImplUiBinder extends UiBinder<Widget, NameSpaceEditorViewImpl> {
    }

    @UiField
    TextBox expression;
    @UiField
    TextBox prefixTextBox;
    @UiField
    TextBox uriTextBox;
    @UiField
    Label   title;

    @UiField(provided = true)
    final CellTable<NameSpace> nameSpacesTable;

    @UiField(provided = true)
    final WSO2EditorLocalizationConstant locale;
    @UiField(provided = true)
    final CellTableResources             resource;

    private ActionDelegate delegate;

    @Inject
    public NameSpaceEditorViewImpl(NameSpaceEditorViewImplUiBinder uiBinder,
                                   WSO2EditorLocalizationConstant locale,
                                   CellTableResources resource) {
        this.locale = locale;
        this.resource = resource;
        this.nameSpacesTable = createTable(resource);

        this.setTitle(locale.editorTitle());
        this.setWidget(uiBinder.createAndBindUi(this));

        Button btnCancel = createButton(locale.buttonCancel(), "namespace-button-cancel", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onCancelButtonClicked();
            }
        });
        getFooter().add(btnCancel);

        Button btnOk = createButton(locale.buttonOk(), "namespace-button-ok", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onOkButtonClicked();
            }
        });
        getFooter().add(btnOk);
    }

    private CellTable<NameSpace> createTable(@Nonnull CellTableResources resource) {
        CellTable<NameSpace> table = new CellTable<>(0, resource);

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
    public void onAddNameSpaceButtonClicked(@SuppressWarnings("UnusedParameters") ClickEvent event) {
        delegate.onAddNameSpaceButtonClicked();
    }

    @UiHandler("btnEdit")
    public void onEditButtonClicked(@SuppressWarnings("UnusedParameters") ClickEvent event) {
        delegate.onEditButtonClicked();
    }

    @UiHandler("btnRemove")
    public void onRemoveButtonClicked(@SuppressWarnings("UnusedParameters") ClickEvent event) {
        delegate.onRemoveButtonClicked();
    }

    @UiHandler("selectPathButton")
    public void onSelectXPathButtonClicked(@SuppressWarnings("UnusedParameters") ClickEvent event) {
        delegate.onSelectXPathButtonClicked();
    }

    /** {@inheritDoc} */
    @Override
    public void showWindow() {
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
    @Nonnull
    @Override
    public String getExpression() {
        return expression.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setDelegate(@Nonnull ActionDelegate delegate) {
        this.delegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public void setNameSpaces(@Nonnull List<NameSpace> nameSpacesList) {
        List<NameSpace> list = new ArrayList<>();

        for (NameSpace property : nameSpacesList) {
            list.add(property);
        }

        nameSpacesTable.setRowData(list);
    }

    /** {@inheritDoc} */
    @Override
    protected void onClose() {
        hide();
    }

    /** {@inheritDoc} */
    @Override
    public void setNameSpaceLabelName(@Nonnull String nameSpace) {
        title.setText(nameSpace);
    }

    /** {@inheritDoc} */
    @Override
    public void setExpression(@Nullable String expression) {
        this.expression.setText(expression);
    }

}
