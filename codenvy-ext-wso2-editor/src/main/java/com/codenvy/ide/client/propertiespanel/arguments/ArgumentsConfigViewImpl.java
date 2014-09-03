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
package com.codenvy.ide.client.propertiespanel.arguments;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.mediators.payload.Arg;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.ui.window.Window;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.payload.Arg.ArgType;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.Evaluator.json;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.Evaluator.xml;
import static com.google.gwt.dom.client.Style.Unit.PX;

/**
 * Provides a graphical representation of dialog window for editing argument's properties of element.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class ArgumentsConfigViewImpl extends Window implements ArgumentsConfigView {

    @Singleton
    interface ArgumentsConfigViewImplUiBinder extends UiBinder<Widget, ArgumentsConfigViewImpl> {
    }

    @UiField
    TextBox valueExpressionTextBox;

    @UiField
    ListBox valueEvaluator;

    @UiField
    ListBox typeValue;

    @UiField(provided = true)
    final WSO2EditorLocalizationConstant locale;

    @UiField(provided = true)
    final CellTable<Arg> args;

    private ActionDelegate delegate;

    @Inject
    public ArgumentsConfigViewImpl(WSO2EditorLocalizationConstant localizationConstant, ArgumentsConfigViewImplUiBinder uiBinder) {
        this.locale = localizationConstant;

        this.args = createTable(localizationConstant);

        this.setTitle(localizationConstant.argsConfigurationTitle());
        this.setWidget(uiBinder.createAndBindUi(this));

        Button btnCancel = createButton(localizationConstant.buttonCancel(), "args-configuration-cancel", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onCancelButtonClicked();
            }
        });
        getFooter().add(btnCancel);

        Button btnOk = createButton(localizationConstant.buttonOk(), "args-configuration-ok", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onOkButtonClicked();
            }
        });
        getFooter().add(btnOk);

    }

    private CellTable<Arg> createTable(@Nonnull final WSO2EditorLocalizationConstant localizationConstant) {
        final CellTable<Arg> table = new CellTable<>();

        final SingleSelectionModel<Arg> selectionModel = new SingleSelectionModel<>();
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                delegate.onSelectedArg(selectionModel.getSelectedObject());
            }
        });
        table.setSelectionModel(selectionModel);

        TextColumn<Arg> value = new TextColumn<Arg>() {
            @Override
            public String getValue(Arg object) {
                return object.getValue();
            }
        };

        TextColumn<Arg> type = new TextColumn<Arg>() {
            @Override
            public String getValue(Arg object) {
                return object.getType().name();
            }
        };

        ButtonCell namespaceEditor = new ButtonCell();

        Column<Arg, String> namespaceEditorButton = new Column<Arg, String>(namespaceEditor) {
            @Override
            public String getValue(Arg object) {
                return localizationConstant.buttonEditConfig();
            }
        };

        namespaceEditorButton.setFieldUpdater(new FieldUpdater<Arg, String>() {
            @Override
            public void update(int index, Arg object, String value) {
                delegate.onSelectedArg(selectionModel.getSelectedObject());
                delegate.onEditArgsButtonClicked();
            }
        });

        TextColumn<Arg> evaluator = new TextColumn<Arg>() {
            @Override
            public String getValue(Arg object) {
                return String.valueOf(object.getEvaluator());
            }
        };

        table.addColumn(type, localizationConstant.columnType());
        table.addColumn(value, localizationConstant.columnValue());
        table.addColumn(namespaceEditorButton);
        table.addColumn(evaluator, localizationConstant.columnEvaluator());

        table.setColumnWidth(type, 120, PX);
        table.setColumnWidth(value, 210, PX);
        table.setColumnWidth(namespaceEditorButton, 30, PX);
        table.setColumnWidth(evaluator, 120, PX);

        table.setLoadingIndicator(null);

        return table;
    }

    @UiHandler("btnAdd")
    public void onAddNewPropertyButtonClicked(ClickEvent event) {
        delegate.onAddArgButtonClicked();
    }

    @UiHandler("btnRemove")
    public void onRemovePropertyButtonClicked(ClickEvent event) {
        delegate.onRemoveArgButtonClicked();
    }

    @UiHandler("btnEdit")
    public void onEditPropertyButtonClicked(ClickEvent event) {
        delegate.onEditButtonClicked();
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
    public void setDelegate(@Nonnull ActionDelegate delegate) {
        this.delegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public void setArgs(@Nonnull Array<Arg> argsList) {
        List<Arg> list = new ArrayList<>();

        for (Arg arg : argsList.asIterable()) {
            list.add(arg);
        }

        args.setRowData(list);
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    public String getTypeValue() {
        int index = typeValue.getSelectedIndex();
        return index != -1 ? typeValue.getValue(typeValue.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setTypeValue() {
        this.typeValue.clear();
        this.typeValue.addItem(ArgType.Expression.name());
        this.typeValue.addItem(ArgType.Value.name());
    }

    /** {@inheritDoc} */
    @Override
    public void selectType(@Nonnull String type) {
        for (int i = 0; i < this.typeValue.getItemCount(); i++) {
            if (this.typeValue.getValue(i).equals(type)) {
                this.typeValue.setItemSelected(i, true);
                return;
            }
        }
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

    /** {@inheritDoc} */
    @Override
    @Nonnull
    public String getEvaluator() {
        int index = valueEvaluator.getSelectedIndex();
        return index != -1 ? valueEvaluator.getValue(valueEvaluator.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void selectEvaluator(@Nonnull String target) {
        for (int i = 0; i < this.valueEvaluator.getItemCount(); i++) {
            if (this.valueEvaluator.getValue(i).equals(target)) {
                this.valueEvaluator.setItemSelected(i, true);
                return;
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setEvaluator() {
        this.valueEvaluator.clear();
        this.valueEvaluator.addItem(json.name());
        this.valueEvaluator.addItem(xml.name());
    }

    /** {@inheritDoc} */
    @Override
    public void clearEvaluator() {
        valueEvaluator.clear();
    }

    /** {@inheritDoc} */
    @Override
    protected void onClose() {
        this.hide();
    }

}