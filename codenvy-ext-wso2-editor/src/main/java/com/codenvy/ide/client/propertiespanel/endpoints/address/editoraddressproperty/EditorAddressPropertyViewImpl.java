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
package com.codenvy.ide.client.propertiespanel.endpoints.address.editoraddressproperty;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.endpoints.addressendpoint.Property;
import com.codenvy.ide.client.elements.mediators.ValueType;
import com.codenvy.ide.ui.window.Window;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;

/**
 * Provides a graphical representation of dialog window for editing properties of address endpoint.
 *
 * @author Dmitry Shnurenko
 */
public class EditorAddressPropertyViewImpl extends Window implements EditorAddressPropertyView {

    @Singleton
    interface EditorAddressPropertyViewImplUiBinder extends UiBinder<Widget, EditorAddressPropertyViewImpl> {
    }

    @UiField
    TextBox name;
    @UiField
    TextBox value;
    @UiField
    ListBox type;
    @UiField
    ListBox scope;

    @UiField
    Button addNameSpaceButton;

    @UiField(provided = true)
    EditorResources res;
    @UiField(provided = true)
    final WSO2EditorLocalizationConstant locale;

    private ActionDelegate delegate;

    @Inject
    public EditorAddressPropertyViewImpl(WSO2EditorLocalizationConstant locale,
                                         EditorResources res,
                                         EditorAddressPropertyViewImplUiBinder uiBinder) {
        this.locale = locale;
        this.res = res;

        this.setTitle(locale.editPropAdrrEndTableTitle());

        this.setWidget(uiBinder.createAndBindUi(this));

        initTypeListBox();
        initScopeListBox();

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

    @UiHandler("scope")
    public void onValueScopeChanged(ChangeEvent event) {
        delegate.onValueScopeChanged();
    }

    @UiHandler("type")
    public void onValueTypeChanged(ChangeEvent event) {
        delegate.onValueTypeChanged();
    }

    @UiHandler("addNameSpaceButton")
    public void onAddNameSpaceButtonClicked(ClickEvent event) {
        delegate.onAddNameSpaceBtnClicked();
    }

    /** Adds type parameters to list box */
    private void initTypeListBox() {
        type.addItem(ValueType.LITERAL.name());
        type.addItem(ValueType.EXPRESSION.name());
    }

    /** Adds scope parameters to list box */
    private void initScopeListBox() {
        scope.addItem(Property.Scope.DEFAULT.getValue());
        scope.addItem(Property.Scope.AXIS2.getValue());
        scope.addItem(Property.Scope.AXIS2_CLIENT.getValue());
        scope.addItem(Property.Scope.TRANSPORT.getValue());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getName() {
        return name.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setName(@Nonnull String name) {
        this.name.setText(name);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getValue() {
        return value.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setValue(@Nonnull String value) {
        this.value.setText(value);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getType() {
        int index = type.getSelectedIndex();
        return index != -1 ? type.getValue(type.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void selectType(@Nonnull String type) {
        for (int i = 0; i < this.type.getItemCount(); i++) {
            if (this.type.getValue(i).equals(type)) {
                this.type.setItemSelected(i, true);
                return;
            }
        }
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getScope() {
        int index = scope.getSelectedIndex();
        return index != -1 ? scope.getValue(scope.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void selectScope(@Nonnull String scope) {
        for (int i = 0; i < this.scope.getItemCount(); i++) {
            if (this.scope.getValue(i).equals(scope)) {
                this.scope.setItemSelected(i, true);
                return;
            }
        }
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
    public void setNameSpaceBtnVisible(boolean isVisible) {
        addNameSpaceButton.setVisible(isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void setTextBoxEnable(boolean isEnable) {
        value.setEnabled(isEnable);
    }

    /** {@inheritDoc} */
    @Override
    public void setDelegate(@Nonnull ActionDelegate delegate) {
        this.delegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    protected void onClose() {
        hide();
    }
}
