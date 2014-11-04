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
package com.codenvy.ide.client.propertiespanel.mediators.arguments;

import com.codenvy.ide.client.CellTableResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.mediators.payload.Arg;
import com.codenvy.ide.ui.dialogs.info.Info;
import com.codenvy.ide.ui.window.Window;
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

import static com.codenvy.ide.client.elements.mediators.payload.Arg.ARG_EVALUATOR;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.ARG_TYPE;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.ARG_VALUE;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.ArgType;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.Evaluator;
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

    @UiField(provided = true)
    final CellTableResources             resource;
    @UiField(provided = true)
    final WSO2EditorLocalizationConstant locale;

    @UiField(provided = true)
    final CellTable<Arg> args;

    private final Info info;

    private ActionDelegate delegate;

    @Inject
    public ArgumentsConfigViewImpl(WSO2EditorLocalizationConstant locale,
                                   ArgumentsConfigViewImplUiBinder uiBinder,
                                   CellTableResources resource) {
        this.locale = locale;
        this.resource = resource;

        this.info = new Info(locale.nameAlreadyExistsError());
        this.info.setTitle(locale.errorMessage());

        this.args = createTable(locale, resource);

        this.setTitle(locale.argsConfigurationTitle());
        this.setWidget(uiBinder.createAndBindUi(this));

        Button btnCancel = createButton(locale.buttonCancel(), "args-configuration-cancel", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onCancelButtonClicked();
            }
        });
        getFooter().add(btnCancel);

        Button btnOk = createButton(locale.buttonOk(), "args-configuration-ok", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onOkButtonClicked();
            }
        });
        getFooter().add(btnOk);

    }

    private CellTable<Arg> createTable(@Nonnull final WSO2EditorLocalizationConstant localizationConstant,
                                       @Nonnull CellTableResources resource) {

        final CellTable<Arg> table = new CellTable<>(0, resource);

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
                return object.getProperty(ARG_VALUE);
            }
        };

        TextColumn<Arg> type = new TextColumn<Arg>() {
            @Override
            public String getValue(Arg arg) {
                ArgType argType = arg.getProperty(ARG_TYPE);

                return argType == null ? "" : argType.getValue();
            }
        };

        TextColumn<Arg> evaluator = new TextColumn<Arg>() {
            @Override
            public String getValue(Arg arg) {
                Evaluator evaluator = arg.getProperty(ARG_EVALUATOR);

                return evaluator == null ? "" : evaluator.getValue();
            }
        };

        table.addColumn(type, localizationConstant.columnType());
        table.addColumn(value, localizationConstant.columnValue());
        table.addColumn(evaluator, localizationConstant.columnEvaluator());

        table.setColumnWidth(type, 120, PX);
        table.setColumnWidth(value, 210, PX);
        table.setColumnWidth(evaluator, 120, PX);

        table.setLoadingIndicator(null);

        return table;
    }

    @UiHandler("btnAdd")
    public void onAddNewPropertyButtonClicked(@SuppressWarnings("UnusedParameters") ClickEvent event) {
        delegate.onAddArgButtonClicked();
    }

    @UiHandler("btnRemove")
    public void onRemovePropertyButtonClicked(@SuppressWarnings("UnusedParameters") ClickEvent event) {
        delegate.onRemoveArgButtonClicked();
    }

    @UiHandler("btnEdit")
    public void onEditPropertyButtonClicked(@SuppressWarnings("UnusedParameters") ClickEvent event) {
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
    public void showErrorMessage() {
        info.show();
    }

    /** {@inheritDoc} */
    @Override
    public void setArgs(@Nonnull List<Arg> argsList) {
        List<Arg> list = new ArrayList<>();

        for (Arg arg : argsList) {
            list.add(arg);
        }

        args.setRowData(list);
    }

    /** {@inheritDoc} */
    @Override
    protected void onClose() {
        this.hide();
    }

}