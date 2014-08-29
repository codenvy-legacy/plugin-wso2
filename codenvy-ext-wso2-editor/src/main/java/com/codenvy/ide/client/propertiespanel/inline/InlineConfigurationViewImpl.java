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
package com.codenvy.ide.client.propertiespanel.inline;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.ui.window.Window;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;


/**
 * Provides a graphical representation of dialog window for editing inline content of element.
 *
 * @author Valeriy Svydenko
 */
public class InlineConfigurationViewImpl extends Window implements InlineConfigurationView {

    @Singleton
    interface FormatConfigurationUiBinder extends UiBinder<Widget, InlineConfigurationViewImpl> {
    }

    @UiField
    TextArea format;

    Button btnOk;
    Button btnCancel;

    private ActionDelegate delegate;

    @Inject
    public InlineConfigurationViewImpl(WSO2EditorLocalizationConstant local, FormatConfigurationUiBinder uiBinder) {
        Widget widget = uiBinder.createAndBindUi(this);

        this.setWidget(widget);

        btnCancel = createButton(local.buttonCancel(), "format-cancel", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onCancelClicked();
            }
        });
        getFooter().add(btnCancel);

        btnOk = createButton(local.buttonOk(), "format-ok", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onOkClicked(format.getText());
            }
        });
        getFooter().add(btnOk);
    }

    /** {@inheritDoc} */
    @Override
    public void setDelegate(@Nonnull ActionDelegate delegate) {
        this.delegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    protected void onClose() {
        this.hide();
    }

    /** {@inheritDoc} */
    @Override
    public void closeDialog() {
        this.hide();
    }

    @Override
    /** {@inheritDoc} */
    public void setEnableBtnOk() {
        btnOk.setEnabled(true);
    }

    /** {@inheritDoc} */
    @Override
    public void showDialog(@Nonnull String content) {
        format.setText(content);
        btnOk.setEnabled(false);

        this.show();
    }

    /** {@inheritDoc} */
    @Override
    public void setWindowTitle(@Nonnull String title) {
        this.setTitle(title);
    }

    @UiHandler("format")
    public void onFormatContentChanged(KeyUpEvent event) {
        delegate.onValueChanged();
    }

}