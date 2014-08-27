/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 * [2012] - [2014] Codenvy, S.A. 
 * All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
 */
package com.codenvy.ide.client.propertiespanel.addressendpoint.property;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.ValueType;
import com.codenvy.ide.client.elements.addressendpoint.Property;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.ui.dialogs.info.Info;
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

/**
 * Provides a graphical representation of dialog window for editing property of address endpoint.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class PropertyViewImpl extends Window implements PropertyView {

    @Singleton
    interface PropertyViewImplUiBinder extends UiBinder<Widget, PropertyViewImpl> {
    }

    @UiField
    CellTable<Property> properties;

    @UiField(provided = true)
    final WSO2EditorLocalizationConstant locale;

    private final Info info;

    private ActionDelegate delegate;

    @Inject
    public PropertyViewImpl(PropertyViewImplUiBinder ourUiBinder, WSO2EditorLocalizationConstant locale) {
        this.locale = locale;

        setTitle(locale.endpointPropertiesTitle());
        setWidget(ourUiBinder.createAndBindUi(this));

        info = new Info(locale.endpointTableError());
        info.setTitle(locale.errorMessage());

        initializeTable();

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
    private void initializeTable() {
        final SingleSelectionModel<Property> selectionModel = new SingleSelectionModel<>();
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                delegate.onPropertySelected(selectionModel.getSelectedObject());
            }
        });
        properties.setSelectionModel(selectionModel);

        TextColumn<Property> name = new TextColumn<Property>() {
            @Override
            public String getValue(Property object) {
                return object.getName();
            }
        };

        TextColumn<Property> value = new TextColumn<Property>() {
            @Override
            public String getValue(Property object) {
                if (ValueType.LITERAL.equals(object.getType())) {
                    return object.getValue();
                }

                return object.getExpression();
            }
        };

        TextColumn<Property> type = new TextColumn<Property>() {
            @Override
            public String getValue(Property object) {
                return object.getType().name();
            }
        };

        TextColumn<Property> scope = new TextColumn<Property>() {
            @Override
            public String getValue(Property object) {
                return object.getScope().getValue();
            }
        };

        properties.addColumn(name, locale.columnName());
        properties.addColumn(value, locale.columnValue());
        properties.addColumn(type, locale.columnType());
        properties.addColumn(scope, locale.columnScope());

        properties.setColumnWidth(name, 170, Style.Unit.PX);
        properties.setColumnWidth(value, 170, Style.Unit.PX);
        properties.setColumnWidth(type, 60, Style.Unit.PX);
        properties.setColumnWidth(scope, 60, Style.Unit.PX);

        properties.setLoadingIndicator(null);
    }

    /** {@inheritDoc} */
    @Override
    public void setDelegate(@Nonnull ActionDelegate delegate) {
        this.delegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public void setProperties(@Nonnull Array<Property> properties) {
        List<Property> list = new ArrayList<>();

        for (Property property : properties.asIterable()) {
            list.add(property);
        }

        this.properties.setRowData(list);
    }

    /** {@inheritDoc} */
    @Override
    public void showErrorMessage() {
        info.show();
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
    public void onAddButtonClicked(ClickEvent event) {
        delegate.onAddButtonClicked();
    }

    @UiHandler("btnEdit")
    public void onEditButtonClicked(ClickEvent event) {
        delegate.onEditButtonClicked();
    }

    @UiHandler("btnRemove")
    public void onRemoveButtonClicked(ClickEvent event) {
        delegate.onRemoveButtonClicked();
    }

}